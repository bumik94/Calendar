package test.components.models;

import java.util.*;

public class CalendarModel {
    private static final int MAX_DAYS_OF_WEEK   = 7;
    private static final int MAX_WEEKS_OF_MONTH = 6;

    Calendar calendar;
    int currentYear;
    int currentMonth;

    public CalendarModel() {
        this.calendar = Calendar.getInstance();
        this.calendar.setMinimalDaysInFirstWeek(1);
        this.currentYear = calendar.get(Calendar.YEAR);
        this.currentMonth = calendar.get(Calendar.MONTH);
    }

    private static String capitalize(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    public int getCurrentYear() {
        return currentYear;
    }

    public void setCurrentYear(int currentYear) {
        this.currentYear = currentYear;
    }

    public int getCurrentMonth() {
        return currentMonth;
    }

    public void setCurrentMonth(int currentMonth) {
        this.currentMonth = currentMonth;
    }

    public int getYear() {
        return calendar.get(Calendar.YEAR);
    }

    public void setYear(int year) {
        calendar.set(Calendar.YEAR, year);
        currentYear = getYear();
    }

    public int getMonth() {
        return calendar.get(Calendar.MONTH);
    }

    public void setMonth(int month) {
        calendar.set(Calendar.MONTH, month);
        currentMonth = getMonth();
    }

    /**
     * Parses a field name in default locale with capital first letter.
     *
     * @param field Calendar instance field
     * @param style Calendar class style constant
     * @return capitalized string representing calendar field
     */
    public String getFieldName(int field, int style) {
        String name = calendar.getDisplayName(field, style, Locale.getDefault());

        return capitalize(name);
    }

    /**
     * Parses ordered days of week in short form into an array according to current locale's first day.
     *
     * @return A String array of short form days of week in a default locale
     */
    public String[] getDaysOfWeek(int style) {
        Map<String, Integer> daysOfWeekMap = calendar.getDisplayNames(
                Calendar.DAY_OF_WEEK,
                style,
                Locale.getDefault());
        String[] array = new String[7];

        if (calendar.getFirstDayOfWeek() == Calendar.MONDAY) {
            daysOfWeekMap.forEach((day, idx) -> {
                // Capitalize first letter of a day
                day = day.substring(0, 1).toUpperCase() + day.substring(1);
                // Put Sunday at the end of the array
                if (idx == Calendar.SUNDAY) {
                    array[array.length - 1] = day;
                    // Shift all other days to the start of the array
                } else {
                    array[idx - 2] = day;
                }
            });
        }
        else { daysOfWeekMap.forEach((day, idx) -> array[idx - 1] = day); }

        return array;
    }

    /**
     * Parses list of dates from start of the first week to the end of the last week of current month.
     * May change data type according to capabilities of TableRenderer.
     *
     * @return Array of parsed days of month
     */
    public Date[][] getDaysOfMonth() {
        Date[][] array = new Date[MAX_WEEKS_OF_MONTH][MAX_DAYS_OF_WEEK];
        List<Date> dateList = getDateList();
        ListIterator<Date> iterator = dateList.listIterator();

        for (int week = 0; week < MAX_WEEKS_OF_MONTH; week++) {
            for (int day = 0; day < MAX_DAYS_OF_WEEK; day++) {
                if (iterator.hasNext()){
                    array[week][day] = iterator.next();
                }
            }
        }
        return array;
    }

    private List<Date> getDateList() {
        ArrayList<Date> list = new ArrayList<>();
        calendar.set(Calendar.WEEK_OF_MONTH, 1);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        // Condition checks if the calendar has reached next month at the beginning of a new week
        do {
            // Extract the date string for the current day and add it to the list
            list.add(calendar.getTime());
            // Increment day of the calendar
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        } while (! (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY &&
                calendar.get(Calendar.MONTH) != currentMonth));
        // Set calendar back to current month and year in case of overlap to the next month/year
        calendar.set(Calendar.YEAR, currentYear);
        calendar.set(Calendar.MONTH, currentMonth);
        return list;
    }

    public String[] getMonths() {
        Map<String, Integer> map = calendar.getDisplayNames
                (Calendar.MONTH, Calendar.LONG_STANDALONE, Locale.getDefault());
        String[] array = new String[map.size()];
        map.forEach((k, v) -> array[v] = capitalize(k));

        return array;
    }
}
