package project.TabPanels;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import project.*;
import project.TabPanels.CreateTable.DialogTableTester;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by s000191354 on 4/11/17.
 */
public class CurrentCommittee {

    //Initialize Variables
    private String[] CommitteeList;
    private JComboBox committeeDropDown = new JComboBox();
    private JButton btnFindCommitteeMembers = new JButton("Find Current Members");
    private int count = 0;
    private JPanel PanelDropDown;
    private JPanel PanelTable;
    private JPanel PanelCurrentCommittee;
    private String selectedCommittee = "";
    private String[] tableColumns = getTableColumns();



    //Create our FileManipulator
    FileManipulator rf = new FileManipulator();


    /**
     * Test our pannel
     * @param args
     */
    public static void main(String[] args) {
        test();

    }

    /**
     * Test method
     */
    public static void test() {
        project.TabPanels.CurrentCommittee c = new CurrentCommittee();
        c.getPanel();
        JFrame frame = new JFrame();
        frame.setMinimumSize(new Dimension(500,500));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(c.PanelCurrentCommittee);
        frame.setVisible(true);
    }

    /**
     * Command called that will return our panel
     * @return
     */
    public JPanel getPanel() {
        //Our main panel
        PanelCurrentCommittee = new JPanel();

        PanelCurrentCommittee.setLayout(new GridLayout(1, 1));

        //create our drop down box to select a committee
        createDropDown();

        //Create our table that displays minor information
        createTable();

        return PanelCurrentCommittee;


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
                PanelCurrentCommittee.remove(PanelTable);
                PanelCurrentCommittee.revalidate();
                createTable();
                PanelCurrentCommittee.add(PanelTable);
                PanelCurrentCommittee.revalidate();
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

        PanelCurrentCommittee.add(PanelDropDown);
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
        PanelCurrentCommittee.add(PanelTable, BorderLayout.CENTER);
    }

    /**
     * returns the names to our columns. The top row
     * @return string of column names
     */
    public String[] getTableColumns(){

        String [] columns = new String[] {"First Name", "Last Name", "Term", "Preferences"};

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

        //Our ArrayList of Professors. Each Integer represents professor's row number
        ArrayList<Integer> EligibleProfessors = rf.getAllEligible(Professor_Constants.CURRENT_ASSIGNMENT, selectedCommittee);
        //Loop through each Professor
        for (int i = 0; i < EligibleProfessors.size(); i++){
            //Get the row of our professor in excel sheet
            ProfessorRow = rf.professorSheet.getRow(EligibleProfessors.get(i));
            //Initialize our professorInfo
            professorInfo = new Object[EligibleProfessors.size()];

            Cell cell = ProfessorRow.getCell(Professor_Constants.ID);
            professorInfo[0] = cell.toString();

            cell = ProfessorRow.getCell(Professor_Constants.FIRST_NAME);
            professorInfo[1] = cell.toString();

            cell = ProfessorRow.getCell(Professor_Constants.LAST_NAME);
            professorInfo[2] = cell.toString();

            cell = ProfessorRow.getCell(Professor_Constants.UNTIL);
            professorInfo[3] = cell.toString();

            //Load Info into professorInfo
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

//END: Table



}

/**
 * Create way to read long named Committees.
 * Create way to read minor assignments
 * Create way when double clicked we can view all of information about user
 * and edit it. Then if closed we can ask to save changes or not.
 * Add a check that will see if it is a valid string
 */
