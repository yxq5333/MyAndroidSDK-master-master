package com.crazyhuskar.myandroidsdk.view.calendar.view;

import android.os.Parcel;
import android.os.Parcelable;

import com.crazyhuskar.myandroidsdk.view.calendar.component.CalendarState;
import com.crazyhuskar.myandroidsdk.view.calendar.model.CalendarDate;


/**
 * Created by ldf on 17/7/5.
 */

public class Day implements Parcelable {
    private CalendarState calendarState;
    private CalendarDate date;
    private int posRow;
    private int posCol;

    public Day(CalendarState calendarState, CalendarDate date , int posRow , int posCol) {
        this.calendarState = calendarState;
        this.date = date;
        this.posRow = posRow;
        this.posCol = posCol;
    }

    public CalendarState getCalendarState() {
        return calendarState;
    }

    public void setCalendarState(CalendarState calendarState) {
        this.calendarState = calendarState;
    }

    public CalendarDate getDate() {
        return date;
    }

    public void setDate(CalendarDate date) {
        this.date = date;
    }

    public int getPosRow() {
        return posRow;
    }

    public void setPosRow(int posRow) {
        this.posRow = posRow;
    }

    public int getPosCol() {
        return posCol;
    }

    public void setPosCol(int posCol) {
        this.posCol = posCol;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.calendarState == null ? -1 : this.calendarState.ordinal());
        dest.writeSerializable(this.date);
        dest.writeInt(this.posRow);
        dest.writeInt(this.posCol);
    }

    protected Day(Parcel in) {
        int tmpState = in.readInt();
        this.calendarState = tmpState == -1 ? null : CalendarState.values()[tmpState];
        this.date = (CalendarDate) in.readSerializable();
        this.posRow = in.readInt();
        this.posCol = in.readInt();
    }

    public static final Creator<Day> CREATOR = new Creator<Day>() {
        @Override
        public Day createFromParcel(Parcel source) {
            return new Day(source);
        }

        @Override
        public Day[] newArray(int size) {
            return new Day[size];
        }
    };
}
