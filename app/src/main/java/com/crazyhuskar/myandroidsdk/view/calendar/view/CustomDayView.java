package com.crazyhuskar.myandroidsdk.view.calendar.view;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.crazyhuskar.myandroidsdk.R;
import com.crazyhuskar.myandroidsdk.view.calendar.CalendarUtil;
import com.crazyhuskar.myandroidsdk.view.calendar.component.CalendarState;
import com.crazyhuskar.myandroidsdk.view.calendar.interf.IDayRenderer;
import com.crazyhuskar.myandroidsdk.view.calendar.model.CalendarDate;


/**
 * Created by ldf on 17/6/26.
 */

public class CustomDayView extends DayView {

    private TextView dateTv;
    private ImageView marker;
    private View selectedBackground;
    private View todayBackground;
    private final CalendarDate today = new CalendarDate();
    private int width = 0;

    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     *
     * @param context
     * @param layoutResource the layout resource to use for the MarkerView
     */
    public CustomDayView(Context context, int layoutResource, int width) {
        super(context, layoutResource, width);
        dateTv = (TextView) findViewById(R.id.date);
        marker = (ImageView) findViewById(R.id.maker);
        selectedBackground = findViewById(R.id.selected_background);
        todayBackground = findViewById(R.id.today_background);
        this.width = width;
    }

    @Override
    public void refreshContent() {
        renderToday(day.getDate());
        renderSelect(day.getCalendarState());
        renderMarker(day.getDate(), day.getCalendarState());
        super.refreshContent();
    }

    private void renderMarker(CalendarDate date, CalendarState calendarState) {
        if (CalendarUtil.loadMarkData().containsKey(date.toString())) {
            if (calendarState == CalendarState.SELECT || date.toString().equals(today.toString())) {
                marker.setVisibility(GONE);
            } else {
                marker.setVisibility(VISIBLE);
                if (CalendarUtil.loadMarkData().get(date.toString()).equals("0")) {
                    marker.setEnabled(true);
                } else {
                    marker.setEnabled(false);
                }
            }
        } else {
            marker.setVisibility(GONE);
        }
    }

    private void renderSelect(CalendarState calendarState) {
        if (calendarState == CalendarState.SELECT) {
            selectedBackground.setVisibility(VISIBLE);
            dateTv.setTextColor(Color.WHITE);
        } else if (calendarState == CalendarState.NEXT_MONTH || calendarState == CalendarState.PAST_MONTH) {
            selectedBackground.setVisibility(GONE);
            dateTv.setTextColor(Color.parseColor("#d5d5d5"));
        } else {
            selectedBackground.setVisibility(GONE);
            dateTv.setTextColor(Color.parseColor("#111111"));
        }
    }

    private void renderToday(CalendarDate date) {
        if (date != null) {
            if (date.equals(today)) {
                dateTv.setText("今");
                todayBackground.setVisibility(VISIBLE);
            } else {
                dateTv.setText(date.day + "");
                todayBackground.setVisibility(GONE);
            }
        }
    }

    @Override
    public IDayRenderer copy() {
        return new CustomDayView(context, layoutResource, width);
    }
}
