package test.components.models;


import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.util.Calendar;
import java.util.Date;

public class DateRenderer extends DefaultTableCellRenderer {
    private final Calendar calendar;
    {
        this.calendar = Calendar.getInstance();
    }

    public DateRenderer() {
        super();
        this.setHorizontalAlignment( SwingConstants.CENTER );
    }

    @Override
    protected void setValue(Object value) {
        String text = "";

        if (value != null) {
            Date date = (Date) value;
            calendar.setTime(date);
            text = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
        }
        // TODO: read date and set background if the day of month != current month or if day of month == current day
//        setBackground();
        setText(text);
    }
}
