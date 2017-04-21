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
import java.util.Calendar;

/**
 * Created by s000191354 on 4/18/17.
 */
public class EligibleProfessors {

    private String[] CommitteeList;
    private JComboBox committeeDropDown = new JComboBox();
    private JButton btnFindCommitteeMembers = new JButton("Find Eligible Members");
    private JRadioButton ThisSpring;
    private JRadioButton NextSpring;
    private JRadioButton ThisFall;
    private ButtonGroup bG;

    private int count = 0;
    private String thisYear = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));

    private String selectedCommittee = "";
    private String[] tableColumns = getTableColumns();

    private JPanel PanelDropDown;
    private JPanel PanelTable;
    private JPanel EligibleProfessorPanel;

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

        //If beyond Feb put next year in the Spring. Radio buttons for
        //Fall 2017, Spring 2017, Spring 2018. Based on current year

        EligibleProfessorPanel.add(PanelDropDown);
        CreateRadioButtons();
    }


    /**
     * Create our radio buttons for the year we are in
     */
    public void CreateRadioButtons(){
        JPanel RadioButtonPanel = new JPanel();
        int nextYear = Integer.parseInt(thisYear) + 1;
        ThisFall = new JRadioButton(thisYear +" Fall");
        ThisFall.setSelected(true);
        ThisSpring = new JRadioButton(thisYear +" Spring");
        NextSpring = new JRadioButton(nextYear + " Spring");

        bG = new ButtonGroup();
        bG.add(ThisFall);
        bG.add(ThisSpring);
        bG.add(NextSpring);

        //Add to Panel
        RadioButtonPanel.add(ThisFall);
        RadioButtonPanel.add(ThisSpring);
        RadioButtonPanel.add(NextSpring);

        EligibleProfessorPanel.add(RadioButtonPanel);
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

        int thisMonth = Calendar.getInstance().get(Calendar.MONTH);

        //Find requirements
        Requirements required = new Requirements(selectedCommittee);

        //Initialize variables
        ArrayList<Integer> EligibleProfessors;


        //Check to see if they are currently serving on a committee
        ArrayList<Integer> withAssignment = rf.getAllEligibleNotCondition(Professor_Constants.CURRENT_ASSIGNMENT, "");

        //If serving on a committee and the term is ending or term has ended
        if(ThisSpring.isSelected()) {

            withAssignment = rf.getAllEligibleNotCondition(Professor_Constants.UNTIL, thisYear, withAssignment);

        //If this Fall Find all professors eligible in the spring of this year
        } else if(ThisFall.isSelected()){

            //Get all professors This year
            ArrayList<Integer> ThisYear = rf.getAllEligible(Professor_Constants.UNTIL, thisYear);

            //All professors eligible in the spring
            ArrayList<Integer> NotFallUntil = rf.getAllEligible(Professor_Constants.SEM, "S");

            //merge all the lists
            ThisYear = rf.mergeLists(ThisYear, NotFallUntil);
            withAssignment = rf.mergeLists(withAssignment, ThisYear);

        //If Next Spring Find all professors eligible in the spring of this year
        } else if(NextSpring.isSelected()){
            //Get all professors This year
            ArrayList<Integer> ThisYear = rf.getAllEligible(Professor_Constants.UNTIL, thisYear);

            //merge all the lists
            withAssignment = rf.mergeLists(withAssignment, ThisYear);

        }

        //Check box that asks Spring or Fall. If Spring do not show Until
        //that ends in spring. If Fall show folks that ended in spring.
        ArrayList<Integer> withOutAssignment = rf.getAllEligible(Professor_Constants.CURRENT_ASSIGNMENT, "");

        //Need to highlight married couples. Should add this to table.

        //Merge With and Without
        EligibleProfessors = rf.mergeLists(withOutAssignment, withAssignment);

        //Check if Next Assignment is empty
        EligibleProfessors = rf.getAllEligible(Professor_Constants.NEXT_ASSIGNMENT, "");


        //Check year appointed. Highlight if they were added in fall. SHOULD ADD THIS TO TABLE
        //special  election for current
        //or highlight all folks from previous year. Add key
        EligibleProfessors = rf.getAllEligibleNotCondition(Professor_Constants.CURRENT_ASSIGNMENT, thisYear, EligibleProfessors);

        //How to check if they are on sabbatical
        EligibleProfessors = rf.getAllEligibleNotCondition(Professor_Constants.CURRENT_ASSIGNMENT, "Sabbatical", EligibleProfessors);



        //athRep
        //Check if they are athletic representative
        EligibleProfessors = rf.getAllEligibleNotCondition(Professor_Constants.CURRENT_ASSIGNMENT, "AthRep", EligibleProfessors);


        //TenureStatus DT-15 cannot but DT can. Also V is visiting
        //Check if they are DTA-15? and if they are Visiting?
        EligibleProfessors = rf.getAllEligibleNotCondition(Professor_Constants.TENURE_STATUS, "V", EligibleProfessors);
        EligibleProfessors = rf.getAllEligibleNotCondition(Professor_Constants.TENURE_STATUS, "DT-15", EligibleProfessors);


        //TenureStatus DT-AT cannot but DT can.
        //Check if Athletic Trainer (DT-AT)?
        EligibleProfessors = rf.getAllEligibleNotCondition(Professor_Constants.TENURE_STATUS, "DT-AT", EligibleProfessors);

        //Check if a dean *works
        EligibleProfessors = rf.getAllEligibleNotContains(Professor_Constants.CURRENT_ASSIGNMENT, "Dean", EligibleProfessors);


        //Check if FacChair *works
        EligibleProfessors = rf.getAllEligibleNotContains(Professor_Constants.CURRENT_ASSIGNMENT, "Fac", EligibleProfessors);





        //Loop through each Professor
        for (int i = 0; i < EligibleProfessors.size(); i++){
            //Get the row of our professor in excel sheet
            ProfessorRow = rf.professorSheet.getRow(EligibleProfessors.get(i));

            //Set the content wanted for each professor
            professorInfo = getProfessorInfo(ProfessorRow);

            //Add professorInfo(Our new Row) to ArrayList<Object[]> data
            data.add(professorInfo);
        }

        //Return information about the professors that meet given specs
        return data;
    }

    /**
     * Gets the professors information and stores it in an Object array.
     * @param ProfessorRow the row the professors information lives
     * @return data. This has all the wanted info for our table.
     */
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

/**
 * Add Something that shows the specs of the committee selected.
 * Add a feature that will allow the user to Select prospective committee members.
 * If they have selected a person in that division than the spec is checked.
 *
 */
