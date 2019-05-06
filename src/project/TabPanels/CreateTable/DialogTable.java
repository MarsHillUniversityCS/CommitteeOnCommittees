//github.com/oliverwatkins/swing_library
package project.TabPanels.CreateTable;

import org.apache.poi.ss.usermodel.Cell;
import project.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

public class DialogTable extends JTable {

	public DialogTable(TableModel model) {

		super(model);

		ArrayList<JTextArea> ProfessorInfo = new ArrayList<>();

		CreateDB db = new CreateDB();

		this.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {

					int row = rowAtPoint(e.getPoint());

					final JDialog dialog = new JDialog();

					// m.setMaximumSize(new Dimension(400,200));
					// dialog.setPreferredSize(new
					// Dimension(400,dialog.getPreferredSize().height));
					dialog.setLayout(new GridBagLayout());

					GridBagConstraints gbc = new GridBagConstraints();
					gbc.insets = new Insets(2, 2, 2, 2);


					int professorID = ((Integer) getValueAt(row, 0)).intValue();

					CreateDB db = new CreateDB();
					Professor p =  db.getProfessorInformationWithID(professorID);

					ArrayList<String> labels = p.getPopupLabels();
					ArrayList<String> values = p.getProfessorInformation();

					for(int i = 0; i < values.size(); i++){
						String val = values.get(i);
						String lbl = labels.get(i);



						gbc.gridx = 0;
						gbc.gridy = i;
						gbc.anchor = GridBagConstraints.WEST;
						gbc.fill = GridBagConstraints.HORIZONTAL;


						dialog.add(new JLabel(lbl), gbc);
						gbc.gridx = 1;

						JTextArea ta = new JTextArea(val);

						dialog.add(ta, gbc);


					}
					gbc.gridx = 1;
					gbc.gridy = ++gbc.gridy;
					gbc.fill = GridBagConstraints.NONE;

					JButton saveButton = new JButton("Save");
					JButton cancelButton = new JButton("Cancel");


					saveButton.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {

							/*
							int result = JOptionPane.showConfirmDialog((Component) null, "Would you like to update this info? \n**It will not be saved**",
									"alert", JOptionPane.YES_NO_CANCEL_OPTION);
							if(result == JOptionPane.YES_OPTION) {
								FileManipulator.editProfessorRow(ProfessorInfo);
								dialog.setVisible(false);
							} else if (result == JOptionPane.YES_NO_OPTION){
								dialog.setVisible(false);
							}

							*/



						}
					});

					cancelButton.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							/*
							int result = JOptionPane.showConfirmDialog((Component) null, "Would you like to update this info? \n**It will not be saved**",
									"alert", JOptionPane.YES_NO_CANCEL_OPTION);
							if(result == JOptionPane.YES_OPTION) {
								FileManipulator.editProfessorRow(ProfessorInfo);
								dialog.setVisible(false);
							} else if (result == JOptionPane.YES_NO_OPTION){
								dialog.setVisible(false);
							}

							*/



						}
					});


					gbc.anchor = GridBagConstraints.EAST;
					dialog.add(saveButton, gbc);

					gbc.gridx = 0;
					dialog.add(cancelButton, gbc);

//					dialog.setLocationRelativeTo(mainFrame);
					dialog.setModal(true);
					dialog.pack();
					dialog.setVisible(true);
				}
			}
		});
	}

	private JTable getThisTable() {
		return this;
	}
}
