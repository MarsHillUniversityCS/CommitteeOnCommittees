package project.TabPanels;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import project.FileManipulator;
import project.Professor_Constants;
import project.Requirements;
import project.TabPanels.CreateTable.DialogTableTester;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by s000191354 on 4/18/17.
 */
public class EligibleProfessors {

    private String[] CommitteeList;
    private JComboBox committeeDropDown = new JComboBox();
    private JButton btnFindCommitteeMembers = new JButton("Find Current Members");
    private int count = 0;
    private JPanel PanelDropDown;
    private JPanel PanelTable;
    private JPanel EligibleProfessorPanel;
    private String selectedCommittee = "";
    private String[] tableColumns = getTableColumns();

    //Create our FileManipulator
    FileManipulator rf = new FileManipulator();


    public static void main(String[] args) {

    }

    public JPanel getPanel(){
        //Our main panel
        EligibleProfessorPanel = new JPanel();


        EligibleProfessorPanel.setLayout(new GridLayout(1, 1));

        //create our drop down box to select a committee
        createDropDown();

        //Create our table that displays minor information
        createTable();


        return EligibleProfessorPanel;
    }

//START: Drop Down Menu
    /**
     * Create our drop down box that is loaded with all of our committees
     */
    public void createDropDown(){
        //Initialize variables
        PanelDropDown = new JPanel();

        //Grab all committees and load them into a String array
        CommitteeList = rf.getCommittees();

        //Add Committees to ComboBox
        for (int i = 0; !(CommitteeList[i].isEmpty()); i++)
            committeeDropDown.addItem(CommitteeList[count++]);

        //Set selectedCommittee
        selectedCommittee = CommitteeList[0];

        //Create our search button
        btnFindCommitteeMembers.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                EligibleProfessorPanel.remove(PanelTable);
                EligibleProfessorPanel.revalidate();
                createTable();
                EligibleProfessorPanel.add(PanelTable);
                EligibleProfessorPanel.revalidate();
                /*)
                if (count < CommitteeList.length)
                    committeeDropDown.addItem(CommitteeList[count++]);
                */
            }
        });

        //Put stuff in this actionlister if you want something to happen when selecting a committee
        committeeDropDown.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectedCommittee = committeeDropDown.getSelectedItem().toString();
                System.out.println(selectedCommittee);
            }
        });

        //add components to panel.
        PanelDropDown.add(committeeDropDown);
        PanelDropDown.add(btnFindCommitteeMembers);

        EligibleProfessorPanel.add(PanelDropDown);
    }
//END: DROP DOWN MENU


//START: Table


    /**
     * Program that runs our methods that create our table
     */
    public void createTable(){
        //Get the columns for our table. These are created in a method
        tableColumns = getTableColumns();

        //Get the table data. That is the Professors in the selected committee
        ArrayList<Object[]> tableData = getTableData();

        //Get the Table inside of a Panel using a GitHub Class from Oliver Watkins
        PanelTable = DialogTableTester.getPanel(tableColumns, tableData);

        //Add the table to our main Panel
        EligibleProfessorPanel.add(PanelTable, BorderLayout.CENTER);
    }

    /**
     * returns the names to our columns. The top row
     * @return string of column names
     */
    public String[] getTableColumns(){

        String [] columns = new String[] {"ID","First Name", "Last Name", "Pref 1", "Pref 2", "Pref 3", "Pref 4", "Pref 5"};

        return columns;
    }


    /**
     * Gets the data that will be loaded into our table
     * @return Array list of object[]. Each Object[] is information about the professor
     */
    public ArrayList<Object[]> getTableData(){
        //Initialize ArrayList data
        ArrayList<Object[]> data = new ArrayList<Object[]>();
        Object[] professorInfo;
        Row ProfessorRow;

        //Find requirements
        Requirements required = new Requirements(selectedCommittee);


        //Check all specs from our Requirement
        ArrayList<Integer> EligibleProfessors = rf.getAllEligible(Professor_Constants.CURRENT_ASSIGNMENT, "");



        //Loop through each Professor
        for (int i = 0; i < EligibleProfessors.size(); i++){
            //Get the row of our professor in excel sheet
            ProfessorRow = rf.professorSheet.getRow(EligibleProfessors.get(i));

            professorInfo = getProfessorInfo(ProfessorRow);

            //Initialize our professorInfo
            /*
            for(int j =0; j < tableColumns.length; j++) {
                Cell cell = ProfessorRow.getCell(Professor_Constants.FIRST_NAME);
                professorInfo[j] = cell.toString();
                //data[i][j] = cell.toString();
                //CHECK WHEN WE ARE FINISHED
            }
            */

            //Add professorInfo(Our new Row) to ArrayList<Object[]> data
            data.add(professorInfo);
        }

        return data;
    }

    private Object[] getProfessorInfo(Row ProfessorRow){
        Object[] Info = new Object[tableColumns.length];

        //Load Info into professorInfo
        Cell cell = ProfessorRow.getCell(Professor_Constants.ID);
        Info[0] = cell.toString();

        cell = ProfessorRow.getCell(Professor_Constants.FIRST_NAME);
        Info[1] = cell.toString();

        cell = ProfessorRow.getCell(Professor_Constants.LAST_NAME);
        Info[2] = cell.toString();

        cell = ProfessorRow.getCell(Professor_Constants.PREFERENCE_1);
        Info[3] = cell.toString();

        cell = ProfessorRow.getCell(Professor_Constants.PREFERENCE_2);
        Info[4] = cell.toString();

        cell = ProfessorRow.getCell(Professor_Constants.PREFERENCE_3);
        Info[5] = cell.toString();

        cell = ProfessorRow.getCell(Professor_Constants.PREFERENCE_4);
        Info[6] = cell.toString();

        cell = ProfessorRow.getCell(Professor_Constants.PREFERENCE_5);
        Info[7] = cell.toString();
        return Info;
    }

//END: Table

}
