package test.components.models;


import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.Calendar;
import java.util.Date;

public class DateRenderer extends DefaultTableCellRenderer {
    private final CalendarModel cm;

    public DateRenderer(CalendarModel calendarModel) {
        super();
        super.setHorizontalAlignment(SwingConstants.CENTER);

        cm = calendarModel;
    }

    /**
     * This method gets value of the day of month from
     * the CalendarModel class and sets the appropriate cell text.
     * Field cacheDate is used to store the state of the
     * CalendarModel before extracting the day from @param value.
     *
     * @param value  the string value for this cell; if value is
     *          <code>null</code> it sets the text value to an empty string
     */
    @Override
    protected void setValue(Object value) {
        String text = "";
        Date date = cm.getTime();
        System.out.println(date);
        if (value != null) {
            cm.setTime((Date) value);
            System.out.println((Date) value);
            text = Integer.toString(cm.getDay());

            if (cm.getMonth() == cm.getCurrentMonth() && cm.getDay() == cm.getCurrentDay()) {
                setBackground(Color.WHITE);
                System.out.println("getMonth " + cm.getMonth() + " currentMonth " + cm.getCurrentMonth());
                System.out.println("getDay " + cm.getDay() + " currentDay " + cm.getCurrentDay());
            }
            else if (cm.getMonth() == cm.getCurrentMonth()) {
                setBackground(Color.LIGHT_GRAY);
            }
            else {
                setBackground(Color.GRAY);
            }
        }
        else {
            setBackground(Color.GRAY);
        }
        setText(text);
        cm.setTime(date);
    }
}
