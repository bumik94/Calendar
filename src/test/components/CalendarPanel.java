package test.components;

import com.sun.source.doctree.SummaryTree;
import test.components.models.CalendarModel;
import test.components.models.DateRenderer;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.Year;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalendarPanel extends JPanel
        implements ActionListener, ChangeListener {

    private final CalendarModel cm;
    private final DefaultTableModel tableModel;
    private final JTable table;
    private final JLabel dateLabel;
    private final JComboBox<String> monthSelect;
    private final JSpinner yearSelect;

    private List<String> monthNames;

    //
    // Constructor
    //

    public CalendarPanel(CalendarModel calendarModel) {
        super(new GridBagLayout());
        cm = calendarModel;

        // Initialize components
        dateLabel = new JLabel(getDateLabelString());
        tableModel = new DefaultTableModel(
                cm.getDaysOfMonth(),
                cm.getDaysOfWeek(Calendar.SHORT_STANDALONE));
        table = getTable(tableModel);
        monthSelect = getMonthSelect();
        yearSelect = getYearSelect();

        GridBagConstraints c = new GridBagConstraints();

        c.gridwidth = 3;
        c.gridx = 0;    c.gridy = 0;    this.add(dateLabel, c);
        c.gridx = 0;    c.gridy = 1;    this.add(table.getTableHeader(), c);
        c.gridx = 0;    c.gridy = 2;    this.add(table, c);

        c.gridwidth = 1;
        c.gridx = 0;    c.gridy = 3;    this.add(monthSelect, c);

        c.anchor = GridBagConstraints.LINE_END;
        c.gridx = 2;    c.gridy = 3;    this.add(yearSelect, c);
    }

    //
    // Component methods
    //

    /**
     * Creates a JComboBox of month names in default locale
     * preconfigured to current month.
     * Changing selection updates the table and label components.
     *
     * @return JComboBox component
     */
    private JComboBox<String> getMonthSelect() {
        String[] monthsArray = cm.getMonths();
        JComboBox<String> comboBox = new JComboBox<>(monthsArray);

        comboBox.setSelectedIndex(cm.getMonth());
        comboBox.addActionListener(this);

        monthNames = Arrays.asList(monthsArray);

        return comboBox;
    }

    private JSpinner getYearSelect() {
        SpinnerDateModel spinnerModel = new SpinnerDateModel(cm.getTime(), null, null, Calendar.YEAR);
        JSpinner spinner = new JSpinner(spinnerModel);

        spinner.setEditor(new JSpinner.DateEditor(spinner, "yyyy"));
        spinner.setPreferredSize(monthSelect.getPreferredSize());
        spinner.addChangeListener(this);

        return spinner;
    }

    /**
     * Creates JTable containing the calendar and sets its properties.
     *
     * @param tableModel TableModel class representing the data for calendar
     * @return a table to display calendar
     */
    private JTable getTable(TableModel tableModel) {
        JTable table = new JTable(tableModel) {
            // Disable editing cells
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        // Default renderer to display a day digit in a table cell
        table.setDefaultRenderer(Object.class, new DateRenderer(cm));
        table.setCellSelectionEnabled(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        initCellSizes(table);

        // Set JTableHeader properties
        JTableHeader tableHeader = getTableHeader(table);
        table.setTableHeader(tableHeader);

        return table;
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
            final String[] columnToolTips = cm.getDaysOfWeek(Calendar.LONG);

            public String getToolTipText(MouseEvent e) {
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

    //
    // Utility methods
    //

    /**
     * Generates a border title from the CalendarModel class to the current date.
     *
     * @return String representing month and year of the current display table
     */
    private String getDateLabelString() {
        return cm.getFieldName(Calendar.MONTH, Calendar.LONG_STANDALONE) + " " + cm.getYear();
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

    /**
     * Updates the components of this panel to the current time of CalendarModel
     */
    private void updateCalendarPanel() {
        dateLabel.setText(getDateLabelString());
        tableModel.setDataVector(cm.getDaysOfMonth(), cm.getDaysOfWeek(Calendar.SHORT_STANDALONE));
        tableModel.fireTableDataChanged();
        initCellSizes(table);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(monthSelect)) {
            String month = (String) monthSelect.getSelectedItem();
            cm.setMonth(monthNames.indexOf(month));
        }

        updateCalendarPanel();
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        Calendar c;

        if (e.getSource().equals(yearSelect)) {
            c = Calendar.getInstance();
            c.setTime((Date) yearSelect.getModel().getValue());
            cm.setYear(c.get(Calendar.YEAR));
        }

        updateCalendarPanel();
    }
}
