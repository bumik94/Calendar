package main.components;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Calendar;

public class CalendarTable extends JPanel {
    private final CalendarModel calendar;
    private final DefaultTableModel tableModel;
//    private final JLabel yearLabel;
//    private final JLabel monthLabel;
    private String borderString;
    private TitledBorder border;

    public CalendarTable() {
        // Set JPanel properties
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        // Initialize fields
        this.calendar = new CalendarModel();
        this.borderString = (calendar.getFieldName(Calendar.MONTH, Calendar.LONG_STANDALONE) + " " + calendar.currentYear);
        this.border = BorderFactory.createTitledBorder(null, borderString, TitledBorder.CENTER, TitledBorder.ABOVE_TOP);
        this.setBorder(border);
//        this.yearLabel = new JLabel(Integer.toString(calendar.getYear()));
//        this.monthLabel = new JLabel(calendar.getFieldName(Calendar.MONTH, Calendar.LONG_STANDALONE));
        this.tableModel = new DefaultTableModel(
                calendar.getDaysOfMonth(),
                calendar.getDaysOfWeek(Calendar.SHORT_STANDALONE));

        // Set JTable properties
        JTable calendarTable = new JTable(tableModel) {
            // Disable editing cells
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        calendarTable.setDefaultRenderer(Object.class, new DateRenderer());
        calendarTable.setCellSelectionEnabled(true);
        calendarTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        initCellSizes(calendarTable);

        // Set JTableHeader properties
        JTableHeader tableHeader = getTableHeader(calendarTable);
        calendarTable.setTableHeader(tableHeader);

        //TODO: Update table procedure
        // Refactor into a method
        // Add controls to manipulate calendar

        // change calendar fields
        calendar.setYear(2020);
        calendar.setMonth(Calendar.JUNE);
        // Update labels
//        yearLabel.setText(Integer.toString(calendar.getYear()));
//        monthLabel.setText(calendar.getFieldName(Calendar.MONTH, Calendar.LONG_STANDALONE));
        // Update table and resize again
        tableModel.setDataVector(calendar.getDaysOfMonth(), calendar.getDaysOfWeek(Calendar.SHORT_STANDALONE));
        tableModel.fireTableDataChanged();
        initCellSizes(calendarTable);
        borderString = (calendar.getFieldName(Calendar.MONTH, Calendar.LONG_STANDALONE) + " " + calendar.getYear());
        border.setTitle(borderString);

        // Add components to the content pane
        c.gridx = 0;
        c.gridy = 0;
//        this.add(yearLabel, c);

        c.gridx = 0;
        c.gridy = 1;
//        this.add(monthLabel, c);

        c.gridx = 0;
        c.gridy = 2;
        this.add(calendarTable.getTableHeader(), c);

        c.gridx = 0;
        c.gridy = 3;
        this.add(calendarTable, c);
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
        TableColumn column;
        for (int i = 0; i < model.getColumnCount(); i++) {
            column = table.getColumnModel().getColumn(i);
            column.setPreferredWidth(36);
        }
    }
}
