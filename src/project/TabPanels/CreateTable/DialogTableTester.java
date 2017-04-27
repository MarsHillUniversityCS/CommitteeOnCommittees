//github.com/oliverwatkins/swing_library
package project.TabPanels.CreateTable;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

public class DialogTableTester extends JFrame {

	public DialogTableTester() {

	}

	public static void main(String[] args) {

		DialogTableTester t = new DialogTableTester();
		t.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		String[] columnNames = new String[] { "First Name", "Last Name", "Sport",
				"Balance", "Vegetarian", "Date of Birth", "Date Joined",
				"Notes" };

		ArrayList<Object[]> data = new ArrayList<Object[]>();
		//Object[][] data = new Object[][] {
		Object[] a =		{
						"Kathy",
						"Smith",
						"Snowboarding",
						"5",
						false,
						"16.04.1974",
						"",
						"Talented individual who possesses great skills on the slopers, and active and fun memeber" };
		Object[] b =	{ "John", "Doe", "Rowing", "3", true, "02.02.1972", "", "" };
		Object[] c =	{
						"Sue",
						"Black",
						"Knitting",
						"-2",
						false,
						"16.12.1988",
						"",
						"An excellent knitter who can knit several multicoloured jumpers in about 3 hours. Is ready to take her knitting to the next competitive level" };
			Object[] d =	{ "Jane", "White", "Speed reading", "20", true,
						"16.04.1942", "", "" };
				Object[] e = { "Joe", "Brown", "Pool", "-10", false, "16.04.1984", "",
						"" }; //};

		data.add(a);
		data.add(b);
		data.add(c);
		data.add(d);
		data.add(e);

		t.setSize(1800, 500);

		JPanel dataPanel = t.getPanel(columnNames, data);
		dataPanel.setPreferredSize(new Dimension(1800, 500));
		//dataPanel.setSize(new Dimension(100, 100));

		t.setLayout(new FlowLayout());
		//t.add(t.getPanel(columnNames, data));
		t.add(dataPanel);
		t.pack();
		t.setVisible(true);

	}

	public static JPanel getPanel(String[] columnNames, ArrayList<Object[]> data) {
		JPanel panel = new JPanel();

		MyTableModel m = new MyTableModel(columnNames, data);


		DialogTable t = new DialogTable(m);
		//MJG
		//t.getColumnModel().getColumn(0).setPreferredWidth(100);
		//t.getColumnModel().getColumn(1).setPreferredWidth(100);
		//t.getColumnModel().getColumn(2).setPreferredWidth(100);
		/*
		final TableColumnModel model = t.getColumnModel();
		for(int column = 0; column < model.getColumnCount(); column++){
			int width = 100;
			model.getColumn(column).setPreferredWidth(width);
		}
		*/
		//MJG

		panel.add(new JScrollPane(t));

		//t.getColumnModel().getColumn(3)
		//		.setCellRenderer(new MyTableCellRenderer());

		// mainFrame.getContentPane().add(panel);
		//
		// mainFrame.setVisible(true);
		// mainFrame.pack();

		return panel;

	}

	static class MyTableModel extends AbstractTableModel {

		String[] columnNames;
		ArrayList<Object[]> data;

		Object o = new Object();

		public MyTableModel(String[] columnNames, ArrayList<Object[]> data) {

			this.columnNames = columnNames;

			this.data = data;
		}


		public int getColumnCount() {
			return columnNames.length;
		}

		public int getRowCount() {
			return data.size();
		}

		public String getColumnName(int col) {
			return columnNames[col];
		}

		public Object getValueAt(int row, int col) {
			Object[] Row = data.get(row);//data[row][col];
            return Row[col];
		}

		public Class getColumnClass(int c) {
			return getValueAt(0, c).getClass();
		}

		/*
		 * Don't need to implement this method unless your table's editable.
		 */
		/*
		public boolean isCellEditable(int row, int col) {
			// Note that the data/cell address is constant,
			// no matter where the cell appears onscreen.
			if (col < 2) {
				return false;
			} else {
				return true;
			}
		}
		*/

		public void setValueAt(Object value, int row, int col) {
			//data[row][col] = value;
			data.get(row)[col] = value;
			fireTableCellUpdated(row, col);
		}

	}

	static class MyTableCellRenderer extends DefaultTableCellRenderer {

		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row2,
				int column) {

			int row = table.convertRowIndexToModel(row2); // this is needed if
															// we use inbuilt
															// sorting.

			JLabel label = (JLabel) super.getTableCellRendererComponent(table,
					value, isSelected, hasFocus, row, column);

			MyTableModel model = (MyTableModel) table.getModel();

			// if not selected colour row differently if booked.
			if (!isSelected) {

				Object o = model.getValueAt(row, 3);

				Integer i = new Integer("" + o);
				if (i > 0) {
					label.setBackground(Color.CYAN);
				} else {
					label.setBackground(Color.RED);
				}
			}
			return label;
		}
	}
}
