package main.components;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.util.Date;

public class CalendarTableModel extends AbstractTableModel {
    private final String[] columnNames;
    private final Date[][] data;

    public CalendarTableModel(Date[][] data, String[] columnNames) {
        super();
        this.columnNames = columnNames;
        this.data = data;
    }

    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Date getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }

    public String getColumnName(int column) {
        return columnNames[column];
    }
}
