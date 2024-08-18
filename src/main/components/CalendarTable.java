package main.components;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.Date;

public class CalendarTable extends JPanel {
    String[] daysOfWeek;    // JTable column names
    Date[][] daysOfMonth; // JTable row data
    CalendarModel calendarModel;

    public CalendarTable() {
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        this.calendarModel = new CalendarModel();
        this.daysOfWeek = calendarModel.getDaysOfWeek();
        this.daysOfMonth = calendarModel.getDaysOfMonth();

        JTable calendarTable = new JTable(new CalendarTableModel(daysOfMonth, daysOfWeek));
        calendarTable.setDefaultRenderer(Object.class, new DateRenderer());
        calendarTable.setCellSelectionEnabled(true);
        initCellSizes(calendarTable);

        this.add(calendarTable.getTableHeader(), BorderLayout.PAGE_START);
        this.add(calendarTable, BorderLayout.CENTER);
    }

    /**
     * Sets the initial cell width to fit number of day
     * @param table JTable component that holds the data of current month days
     */
    private void initCellSizes(JTable table) {
        TableModel model = table.getModel();
        JTableHeader header = table.getTableHeader();
        TableColumn column;
        for (int i = 0; i < model.getColumnCount(); i++) {
            column = table.getColumnModel().getColumn(i);
            column.setPreferredWidth(30);
        }
    }
}
