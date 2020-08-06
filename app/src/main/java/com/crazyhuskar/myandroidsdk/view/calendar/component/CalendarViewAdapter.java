package com.crazyhuskar.myandroidsdk.view.calendar.component;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.crazyhuskar.myandroidsdk.view.calendar.CalendarUtil;
import com.crazyhuskar.myandroidsdk.view.calendar.interf.IDayRenderer;
import com.crazyhuskar.myandroidsdk.view.calendar.interf.OnAdapterSelectListener;
import com.crazyhuskar.myandroidsdk.view.calendar.interf.OnSelectDateListener;
import com.crazyhuskar.myandroidsdk.view.calendar.model.CalendarDate;
import com.crazyhuskar.myandroidsdk.view.calendar.view.CalendarView;
import com.crazyhuskar.myandroidsdk.view.calendar.view.MonthPager;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.viewpager.widget.PagerAdapter;

public class CalendarViewAdapter extends PagerAdapter {
    public static int weekArrayType = 0;//周排列方式 1 express Sunday as the first day of week

    private static CalendarDate date = new CalendarDate();
    private ArrayList<CalendarView> calendarViews = new ArrayList<>();
    private int currentPosition;
    private CalendarAttr.CalendayType calendarType = CalendarAttr.CalendayType.MONTH;
    private int rowCount = 0;
    private CalendarDate seedDate;

    public CalendarViewAdapter(Context context,
                               OnSelectDateListener onSelectDateListener,
                               CalendarAttr.CalendayType calendarType,
                               IDayRenderer dayView) {
        super();
        this.calendarType = calendarType;
        init(context, onSelectDateListener);
        setCustomDayRenderer(dayView);
    }

    private void init(Context context, OnSelectDateListener onSelectDateListener) {
        saveDate(new CalendarDate());
        seedDate = new CalendarDate().modifyDay(1);//初始化的种子日期为今天
        for (int i = 0; i < 3; i++) {
            CalendarView calendarView = new CalendarView(context, onSelectDateListener);
            calendarView.setOnAdapterSelectListener(new OnAdapterSelectListener() {
                @Override
                public void cancelSelectState() {
                    cancelOtherSelectState();
                }

                @Override
                public void updateSelectState() {
                    invalidateCurrentCalendar();
                }
            });
            calendarViews.add(calendarView);
        }
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        this.currentPosition = position;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        if (position < 2) {
            return null;
        }
        CalendarView calendarView = calendarViews.get(position % calendarViews.size());
        if (calendarType == CalendarAttr.CalendayType.MONTH) {
            CalendarDate current = seedDate.modifyMonth(position - MonthPager.CURRENT_DAY_INDEX);
            current.setDay(1);//每月的种子日期都是1号
            calendarView.showDate(current);
        } else {
            CalendarDate current = seedDate.modifyWeek(position - MonthPager.CURRENT_DAY_INDEX);
            if (weekArrayType == 1) {
                calendarView.showDate(CalendarUtil.getSaturday(current));
            } else {
                calendarView.showDate(CalendarUtil.getSunday(current));
            }//每周的种子日期为这一周的最后一天
            calendarView.updateWeek(rowCount);
        }
        if (container.getChildCount() == calendarViews.size()) {
            container.removeView(calendarViews.get(position % 3));
        }
        if (container.getChildCount() < calendarViews.size()) {
            container.addView(calendarView, 0);
        } else {
            container.addView(calendarView, position % 3);
        }
        return calendarView;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((View) object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(container);
    }

    public ArrayList<CalendarView> getPagers() {
        return calendarViews;
    }

    public void cancelOtherSelectState() {
        for (int i = 0; i < calendarViews.size(); i++) {
            CalendarView calendarView = calendarViews.get(i);
            calendarView.cancelSelectState();
        }
    }

    public void invalidateCurrentCalendar() {
        for (int i = 0; i < calendarViews.size(); i++) {
            CalendarView calendarView = calendarViews.get(i);
            calendarView.update();
            if (calendarView.getCalendarType() == CalendarAttr.CalendayType.WEEK) {
                calendarView.updateWeek(rowCount);
            }
        }
    }

    public void setMarkData(HashMap<String, String> markData) {
        CalendarUtil.setMarkData(markData);
    }

    public void switchToMonth() {
        if (calendarViews != null && calendarViews.size() > 0 && calendarType != CalendarAttr.CalendayType.MONTH) {
            calendarType = CalendarAttr.CalendayType.MONTH;
            MonthPager.CURRENT_DAY_INDEX = currentPosition;
            CalendarView v = calendarViews.get(currentPosition % 3);//0
            seedDate = v.getSeedDate();

            CalendarView v1 = calendarViews.get(currentPosition % 3);//0
            v1.switchCalendarType(CalendarAttr.CalendayType.MONTH);
            v1.showDate(seedDate);

            CalendarView v2 = calendarViews.get((currentPosition - 1) % 3);//2
            v2.switchCalendarType(CalendarAttr.CalendayType.MONTH);
            CalendarDate last = seedDate.modifyMonth(-1);
            last.setDay(1);
            v2.showDate(last);

            CalendarView v3 = calendarViews.get((currentPosition + 1) % 3);//1
            v3.switchCalendarType(CalendarAttr.CalendayType.MONTH);
            CalendarDate next = seedDate.modifyMonth(1);
            next.setDay(1);
            v3.showDate(next);
        }
    }

    public void switchToWeek(int rowIndex) {
        rowCount = rowIndex;
        if (calendarViews != null && calendarViews.size() > 0 && calendarType != CalendarAttr.CalendayType.WEEK) {
            calendarType = CalendarAttr.CalendayType.WEEK;
            MonthPager.CURRENT_DAY_INDEX = currentPosition;
            CalendarView v = calendarViews.get(currentPosition % 3);
            seedDate = v.getSeedDate();

            rowCount = v.getSelectedRowIndex();

            CalendarView v1 = calendarViews.get(currentPosition % 3);
            v1.switchCalendarType(CalendarAttr.CalendayType.WEEK);
            v1.showDate(seedDate);
            v1.updateWeek(rowIndex);

            CalendarView v2 = calendarViews.get((currentPosition - 1) % 3);
            v2.switchCalendarType(CalendarAttr.CalendayType.WEEK);
            CalendarDate last = seedDate.modifyWeek(-1);
            if (weekArrayType == 1) {
                v2.showDate(CalendarUtil.getSaturday(last));
            } else {
                v2.showDate(CalendarUtil.getSunday(last));
            }//每周的种子日期为这一周的最后一天
            v2.updateWeek(rowIndex);

            CalendarView v3 = calendarViews.get((currentPosition + 1) % 3);
            v3.switchCalendarType(CalendarAttr.CalendayType.WEEK);
            CalendarDate next = seedDate.modifyWeek(1);
            if (weekArrayType == 1) {
                v3.showDate(CalendarUtil.getSaturday(next));
            } else {
                v3.showDate(CalendarUtil.getSunday(next));
            }//每周的种子日期为这一周的最后一天
            v3.updateWeek(rowIndex);
        }
    }

    public void notifyDataChanged(CalendarDate date) {
        seedDate = date;
        saveDate(date);
        if (calendarType == CalendarAttr.CalendayType.WEEK) {
            MonthPager.CURRENT_DAY_INDEX = currentPosition;
            CalendarView v1 = calendarViews.get(currentPosition % 3);
            v1.showDate(seedDate);
            v1.updateWeek(rowCount);

            CalendarView v2 = calendarViews.get((currentPosition - 1) % 3);
            CalendarDate last = seedDate.modifyWeek(-1);
            if (weekArrayType == 1) {
                v2.showDate(CalendarUtil.getSaturday(last));
            } else {
                v2.showDate(CalendarUtil.getSunday(last));
            }//每周的种子日期为这一周的最后一天
            v2.updateWeek(rowCount);

            CalendarView v3 = calendarViews.get((currentPosition + 1) % 3);
            CalendarDate next = seedDate.modifyWeek(1);
            if (weekArrayType == 1) {
                v3.showDate(CalendarUtil.getSaturday(next));
            } else {
                v3.showDate(CalendarUtil.getSunday(next));
            }//每周的种子日期为这一周的最后一天
            v3.updateWeek(rowCount);
        } else {
            MonthPager.CURRENT_DAY_INDEX = currentPosition;

            CalendarView v1 = calendarViews.get(currentPosition % 3);//0
            v1.showDate(seedDate);

            CalendarView v2 = calendarViews.get((currentPosition - 1) % 3);//2
            CalendarDate last = seedDate.modifyMonth(-1);
            last.setDay(1);
            v2.showDate(last);

            CalendarView v3 = calendarViews.get((currentPosition + 1) % 3);//1
            CalendarDate next = seedDate.modifyMonth(1);
            next.setDay(1);
            v3.showDate(next);
        }
    }

    public static void saveDate(CalendarDate calendarDate) {
        date = calendarDate;
    }

    public static CalendarDate loadDate() {
        return date;
    }

    public CalendarAttr.CalendayType getCalendarType() {
        return calendarType;
    }

    public void setCustomDayRenderer(IDayRenderer dayRenderer) {
        CalendarView c0 = calendarViews.get(0);
        c0.setDayRenderer(dayRenderer);

        CalendarView c1 = calendarViews.get(1);
        c1.setDayRenderer(dayRenderer.copy());

        CalendarView c2 = calendarViews.get(2);
        c2.setDayRenderer(dayRenderer.copy());
    }
}