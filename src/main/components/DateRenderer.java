package main.components;


import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateRenderer extends DefaultTableCellRenderer
        implements TableCellRenderer {

    public DateRenderer() {
        super();
        this.setHorizontalAlignment( SwingConstants.CENTER );
    }

    @Override
    protected void setValue(Object value) {
        String text = "";

        if (value != null) {
            Date date = (Date) value;
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            text = Integer.toString(c.get(Calendar.DAY_OF_MONTH));
        }
        // TODO: read date and set background if the day of month != current month or if day of month == current day
//        setBackground();
        setText(text);
    }
}
