import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class Rs2TableModel {
    @SuppressWarnings("serial")
	public static TableModel resultSetToTableModel(ResultSet rs, boolean editibility) {
	try {
	    ResultSetMetaData metaData = rs.getMetaData();
	    int numberOfColumns = metaData.getColumnCount();
	    Vector<String> columnNames = new Vector<String>();

	    // Get the column names
	    for (int column = 0; column < numberOfColumns; column++) {
		columnNames.addElement(metaData.getColumnLabel(column + 1));
	    }

	    // Get all rows.
	    Vector<Vector<Object>> rows = new Vector<Vector<Object>>();

	    while (rs.next()) {
		Vector<Object> newRow = new Vector<Object>();

		for (int i = 1; i <= numberOfColumns; i++) {
		    newRow.addElement(rs.getObject(i));
		}

		rows.addElement(newRow);
	    }
	    boolean[] columnEditables = new boolean[numberOfColumns];
	    for(int i=0;i<numberOfColumns;i++){
	    	columnEditables[i] = editibility;
	    }

	    return (new DefaultTableModel(rows, columnNames){
				
				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}});
	    } catch (Exception e) {
	    e.printStackTrace();

	    return null;
	}
    }
}