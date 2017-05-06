package project.TabPanels;

import org.apache.poi.ss.usermodel.Cell;
import project.FileManipulator;
import project.Professor_Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by s000191354 on 5/6/17.
 */
public class QuickChanges {

    private JPanel QuickChangesPanel;
    private JPanel ButtonPanel;
    /**
     * Command called that will return our panel
     * @return
     */
    public JPanel getPanel() {
        //Our main panel
        QuickChangesPanel = new JPanel();

        QuickChangesPanel.setLayout(new GridLayout(1, 1));

        createButtons();


        return QuickChangesPanel;
    }


    private void createButtons(){
        ButtonPanel = new JPanel();
        JButton updateCommittees = new JButton("->");
        updateCommittees.addActionListener(new updateCommitteeListener());

        JLabel updateCommitteesLabel = new JLabel("Update Current Assignment with Next Assignment");

        ButtonPanel.add(updateCommitteesLabel);
        ButtonPanel.add(updateCommittees);

        QuickChangesPanel.add(ButtonPanel);
    }

    //START: ActionListeners
    /**
     * opens high scores when selected
     */
    class updateCommitteeListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event){
            int result = JOptionPane.showConfirmDialog((Component) null, "This will replace all Current Assignments with Next Assignments. Are you sure you want to do this?",
                    "alert", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION){
                ArrayList<Integer> professorList = FileManipulator.getAllEligibleNotCondition(Professor_Constants.NEXT_ASSIGNMENT,"");
                for (Integer professor : professorList) {

                    Cell currentName = FileManipulator.getCellFromProfessorSheet(Professor_Constants.CURRENT_ASSIGNMENT, professor);
                    Cell currentNum = FileManipulator.getCellFromProfessorSheet(Professor_Constants.COMMITTEE_NUMBER, professor);

                    Cell nextName = FileManipulator.getCellFromProfessorSheet(Professor_Constants.NEXT_ASSIGNMENT, professor);
                    Cell nextNum = FileManipulator.getCellFromProfessorSheet(Professor_Constants.NEXT_ASSIGNMENT_COMMITTEE_NUMBER, professor);

                    if(nextName == null || nextNum == null) break;

                    if(currentName == null)
                        currentName = FileManipulator.fixNullCell(Professor_Constants.CURRENT_ASSIGNMENT, professor);
                    if(currentNum == null)
                        currentNum = FileManipulator.fixNullCell(Professor_Constants.COMMITTEE_NUMBER, professor);

                    currentName.setCellValue(FileManipulator.getCellString(nextName));
                    currentNum.setCellValue(FileManipulator.getCellString(nextNum));



                    nextName.setCellValue("");
                    nextNum.setCellValue("");
                }
            }
        }
    }

//END: ActionListeners

}
