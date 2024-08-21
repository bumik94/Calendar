package main.components;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Calendar;

public class CalendarTable extends JPanel {
    CalendarModel calendar;

    public CalendarTable() {
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        this.calendar = new CalendarModel();

        // Set JTable properties
        JTable calendarTable = new JTable(new CalendarTableModel(
                calendar.getDaysOfMonth(), calendar.getDaysOfWeek(Calendar.SHORT_STANDALONE)));
        calendarTable.setDefaultRenderer(Object.class, new DateRenderer());
        calendarTable.setCellSelectionEnabled(true);
        initCellSizes(calendarTable);

        // Set JTableHeader properties
        JTableHeader tableHeader = getTableHeader(calendarTable);
        calendarTable.setTableHeader(tableHeader);

        // Add components to the content pane
        this.add(calendarTable.getTableHeader(), BorderLayout.PAGE_START);
        this.add(calendarTable, BorderLayout.CENTER);
    }

    /**
     * Creates new JTableHeader with ColumnModel of the table and tooltips showing full names of days.
     * Also disables reordering and resizing.
     *
     * @param table JTable whose column model will be used to create new JTableHeader
     * @return a new JTableHeader with tooltips and current ColumnModel
     */
    private JTableHeader getTableHeader(JTable table) {
        JTableHeader tableHeader = new JTableHeader(table.getColumnModel()) {
            // Array of names used for showing column tooltips
            final String[] columnToolTips = calendar.getDaysOfWeek(Calendar.LONG);

            public String getToolTipText(MouseEvent e) {
                String tip = null;
                Point p = e.getPoint();
                int index = columnModel.getColumnIndexAtX(p.x);
                int realIndex = columnModel.getColumn(index).getModelIndex();
                return columnToolTips[realIndex];
            }
        };
        tableHeader.setReorderingAllowed(false);
        tableHeader.setResizingAllowed(false);

        return tableHeader;
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
            column.setPreferredWidth(34);
        }
    }
}
