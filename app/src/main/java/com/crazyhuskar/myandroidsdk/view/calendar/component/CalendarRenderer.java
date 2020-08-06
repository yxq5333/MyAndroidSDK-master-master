package com.crazyhuskar.myandroidsdk.view.calendar.component;

import android.content.Context;
import android.graphics.Canvas;

import com.crazyhuskar.myandroidsdk.view.calendar.CalendarUtil;
import com.crazyhuskar.myandroidsdk.view.calendar.Const;
import com.crazyhuskar.myandroidsdk.view.calendar.interf.IDayRenderer;
import com.crazyhuskar.myandroidsdk.view.calendar.interf.OnSelectDateListener;
import com.crazyhuskar.myandroidsdk.view.calendar.model.CalendarDate;
import com.crazyhuskar.myandroidsdk.view.calendar.view.CalendarView;
import com.crazyhuskar.myandroidsdk.view.calendar.view.Day;
import com.crazyhuskar.myandroidsdk.view.calendar.view.Week;


/**
 * Created by ldf on 17/6/26.
 */

public class CalendarRenderer {
    private Week weeks[] = new Week[Const.TOTAL_ROW];	// 行数组，每个元素代表一行
    private CalendarView calendarView;
    private CalendarAttr attr;
    private IDayRenderer dayRenderer;
    private Context context;
    private OnSelectDateListener onSelectDateListener;	// 单元格点击回调事件
    private CalendarDate seedDate; //种子日期
    private CalendarDate selectedDate; //被选中的日期
    private int selectedRowIndex = 0;

    public CalendarRenderer(CalendarView calendarView, CalendarAttr attr , Context context) {
        this.calendarView = calendarView;
        this.attr = attr;
        this.context = context;
    }

    public void draw(Canvas canvas) {
        for (int row = 0; row < Const.TOTAL_ROW; row++) {
			if (weeks[row] != null) {
                for (int col = 0; col < Const.TOTAL_COL; col ++) {
                    if (weeks[row].days[col] != null) {
                        dayRenderer.drawDay(canvas , weeks[row].days[col]);
                    }
                }
            }
		}
    }

    public void onClickDate(int col, int row) {
        if (col >= Const.TOTAL_COL || row >= Const.TOTAL_ROW)
            return;
        if (weeks[row] != null) {
            if(attr.getCalendarType() == CalendarAttr.CalendayType.MONTH) {
                if(weeks[row].days[col].getCalendarState() == CalendarState.CURRENT_MONTH){
                    weeks[row].days[col].setCalendarState(CalendarState.SELECT);
                    selectedDate = weeks[row].days[col].getDate();
                    CalendarViewAdapter.saveDate(selectedDate);
                    onSelectDateListener.onSelectDate(selectedDate);
                    seedDate = selectedDate;
                } else if (weeks[row].days[col].getCalendarState() == CalendarState.PAST_MONTH){
                    selectedDate = weeks[row].days[col].getDate();
                    CalendarViewAdapter.saveDate(selectedDate);
                    onSelectDateListener.onSelectOtherMonth(-1);
                    onSelectDateListener.onSelectDate(selectedDate);
                } else if (weeks[row].days[col].getCalendarState() == CalendarState.NEXT_MONTH){
                    selectedDate = weeks[row].days[col].getDate();
                    CalendarViewAdapter.saveDate(selectedDate);
                    onSelectDateListener.onSelectOtherMonth(1);
                    onSelectDateListener.onSelectDate(selectedDate);
                }
            } else {
                weeks[row].days[col].setCalendarState(CalendarState.SELECT);
                selectedDate = weeks[row].days[col].getDate();
                CalendarViewAdapter.saveDate(selectedDate);
                onSelectDateListener.onSelectDate(selectedDate);
                seedDate = selectedDate;
            }
        }
    }

    public void updateWeek(int rowIndex) {
        CalendarDate currentWeekLastDay;
        if(CalendarViewAdapter.weekArrayType == 1) {
            currentWeekLastDay = CalendarUtil.getSaturday(seedDate);
        } else {
            currentWeekLastDay = CalendarUtil.getSunday(seedDate);
        }
        int day = currentWeekLastDay.day;
        for (int i = Const.TOTAL_COL - 1; i >= 0 ; i --) {
            CalendarDate date = currentWeekLastDay.modifyDay(day);
            if(weeks[rowIndex] == null) {
                weeks[rowIndex] = new Week(rowIndex);
            }
            if(weeks[rowIndex].days[i] != null) {
                if (date.equals(CalendarViewAdapter.loadDate())) {
                    weeks[rowIndex].days[i].setCalendarState(CalendarState.SELECT);
                    weeks[rowIndex].days[i].setDate(date);
                } else {
                    weeks[rowIndex].days[i].setCalendarState(CalendarState.CURRENT_MONTH);
                    weeks[rowIndex].days[i].setDate(date);
                }
            } else {
                if (date.equals(CalendarViewAdapter.loadDate())) {
                    weeks[rowIndex].days[i] = new Day(CalendarState.SELECT , date , rowIndex , i);
                } else {
                    weeks[rowIndex].days[i] = new Day(CalendarState.CURRENT_MONTH , date , rowIndex , i);
                }
            }
            day -- ;
        }
    }

    private void instantiateMonth() {
        int lastMonthDays = CalendarUtil.getMonthDays(seedDate.year, seedDate.month - 1);	// 上个月的天数
        int currentMonthDays = CalendarUtil.getMonthDays(seedDate.year, seedDate.month);	// 当前月的天数
        int firstDayPosition = CalendarUtil.getFirstDayWeekPosition(seedDate.year, seedDate.month , CalendarViewAdapter.weekArrayType);

        int day = 0;
        for (int row = 0; row < Const.TOTAL_ROW; row++) {
            day = fillWeek(lastMonthDays, currentMonthDays, firstDayPosition, day, row);
        }
    }

    private int fillWeek(int lastMonthDays, int currentMonthDays, int firstDayWeek, int day, int row) {
        for (int col = 0; col < Const.TOTAL_COL; col++) {
            int position = col + row * Const.TOTAL_COL;	// 单元格位置
            if (position >= firstDayWeek && position < firstDayWeek + currentMonthDays) {	// 本月的
                day ++;
                fillCurrentMonthDate(day, row, col);
            } else if (position < firstDayWeek) { //last month
                instantiateLastMonth(lastMonthDays, firstDayWeek, row, col, position);
            } else if (position >= firstDayWeek + currentMonthDays) {//next month
                instantiateNextMonth(currentMonthDays, firstDayWeek, row, col, position);
            }
        }
        return day;
    }

    private void fillCurrentMonthDate(int day, int row, int col) {
        CalendarDate date = seedDate.modifyDay(day);
        if(weeks[row] == null) {
            weeks[row] = new Week(row);
        }
        if(weeks[row].days[col] != null) {
            if(date.equals(CalendarViewAdapter.loadDate())) {
                weeks[row].days[col].setDate(date);
                weeks[row].days[col].setCalendarState(CalendarState.SELECT);
            } else {
                weeks[row].days[col].setDate(date);
                weeks[row].days[col].setCalendarState(CalendarState.CURRENT_MONTH);
            }
        } else {
            if(date.equals(CalendarViewAdapter.loadDate())) {
                weeks[row].days[col] = new Day(CalendarState.SELECT , date , row , col);
            } else {
                weeks[row].days[col] = new Day(CalendarState.CURRENT_MONTH , date , row , col);
            }
        }
        if(date.equals(seedDate)){
            selectedRowIndex = row;
        }
    }

    private void instantiateNextMonth(int currentMonthDays, int firstDayWeek, int row, int col, int position) {
        CalendarDate date = new CalendarDate(
                seedDate.year,
                seedDate.month + 1,
                position - firstDayWeek - currentMonthDays + 1);
        if(weeks[row] == null) {
            weeks[row] = new Week(row);
        }
        if(weeks[row].days[col] != null) {
            weeks[row].days[col].setDate(date);
            weeks[row].days[col].setCalendarState(CalendarState.NEXT_MONTH);
        } else {
            weeks[row].days[col] = new Day(CalendarState.NEXT_MONTH , date , row , col);
        }
        // TODO: 17/6/27  当下一个月的天数大于七时，说明该月有六周
//        if(position - firstDayWeek - currentMonthDays + 1 >= 7) { //当下一个月的天数大于七时，说明该月有六周
//        }
    }

    private void instantiateLastMonth(int lastMonthDays, int firstDayWeek, int row, int col, int position) {
        CalendarDate date = new CalendarDate(
                seedDate.year,
                seedDate.month - 1,
                lastMonthDays - (firstDayWeek- position - 1));
        if(weeks[row] == null) {
            weeks[row] = new Week(row);
        }
        if(weeks[row].days[col] != null) {
            weeks[row].days[col].setDate(date);
            weeks[row].days[col].setCalendarState(CalendarState.PAST_MONTH);
        } else {
            weeks[row].days[col] = new Day(CalendarState.PAST_MONTH , date , row , col);
        }
    }

    public void showDate(CalendarDate seedDate) {
        if(seedDate != null){
            this.seedDate = seedDate;
        } else {
            this.seedDate = new CalendarDate();
        }
        update();
    }

    public void update() {
        instantiateMonth();
        calendarView.invalidate();
    }

    public CalendarDate getSeedDate() {
        return this.seedDate;
    }

    public void cancelSelectState(){
        for (int i = 0; i < Const.TOTAL_ROW; i++) {
            if (weeks[i] != null){
                for (int j = 0; j < Const.TOTAL_COL; j++){
                    if(weeks[i].days[j].getCalendarState() == CalendarState.SELECT){
                        weeks[i].days[j].setCalendarState(CalendarState.CURRENT_MONTH);
                        resetSelectedRowIndex();
                        break;
                    }
                }
            }
        }
    }

    public void resetSelectedRowIndex(){
        selectedRowIndex = 0;
    }

    public int getSelectedRowIndex() {
        return selectedRowIndex;
    }

    public void setSelectedRowIndex(int selectedRowIndex) {
        this.selectedRowIndex = selectedRowIndex;
    }

    public CalendarView getCalendarView() {
        return calendarView;
    }

    public void setCalendarView(CalendarView calendarView) {
        this.calendarView = calendarView;
    }

    public CalendarAttr getAttr() {
        return attr;
    }

    public void setAttr(CalendarAttr attr) {
        this.attr = attr;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setOnSelectDateListener(OnSelectDateListener onSelectDateListener) {
        this.onSelectDateListener = onSelectDateListener;
    }

    public void setDayRenderer(IDayRenderer dayRenderer) {
        this.dayRenderer = dayRenderer;
    }
}
