package project.TabPanels;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import project.Committee_Req_Constants;
import project.FileManipulator;
import project.Professor_Constants;
import project.Requirements;
import project.TabPanels.CreateTable.DialogTableTester;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
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
    private Requirements required;
    private int count = 0;
    private int thisYear = (Calendar.getInstance().get(Calendar.YEAR));

    private String selectedCommittee = "";
    private String[] tableColumns = getTableColumns();

    private JPanel PanelDropDown;
    private JPanel PanelTable;
    private JPanel EligibleProfessorPanel;
    private JPanel RadioButtonPanel;
    private JPanel SearchPanel;
    //Create our FileManipulator



    public JPanel getPanel(){
        //Our main panel
        EligibleProfessorPanel = new JPanel(new BorderLayout());
        SearchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        //SearchPanel.setLayout(new BoxLayout(SearchPanel, BoxLayout.X_AXIS));


        //EligibleProfessorPanel.setLayout(new GridLayout(1, 1));

        //create our drop down box to select a committee
        createDropDown();

        SearchPanel.add(PanelDropDown);
        SearchPanel.add(RadioButtonPanel);

        EligibleProfessorPanel.add(SearchPanel,BorderLayout.NORTH);

        //Create our table that displays minor information
        createTable();

        createRequirements();

        EligibleProfessorPanel.setSize(1000, 1000);


        return EligibleProfessorPanel;
    }

    public void createRequirements(){
        JPanel reqPanel = new JPanel(new GridBagLayout());
        JTextPane jtp = new JTextPane();
        jtp.setEditable(false);
        jtp.setContentType("text/html");

        StringBuilder requirements = new StringBuilder();
        requirements.append("<html><<body><h3>Overall Requirements</h3><ul>");
        requirements.append("<li>1. May serve on only one standing committee at a time and can succeed self only once.</li>");
        requirements.append("<li>No married couples may serve on same governing committee.</li>");
        requirements.append("<li>First Year Faculty cannot serve on committees</li>");
        requirements.append("<li>Faculty on Sabbatical cannot serve on committees. They must be replaced for the duration of their leave.</li>");
        requirements.append("<li>Faculty Athletic Representative does not serve on other committees</li>");
        requirements.append("<li>DTA-15 and Visiting (V) faculty do not serve on committees</li>");
        requirements.append("<li>Athletic Trainers (DT-AT) do not serve on committees</li>");
        requirements.append("<li>Deans do not serve on committees</li>");
        requirements.append("<li>FacChair is not eligible for election or appointment to a committee</li></ul>");



        requirements.append("</body></html>");
        jtp.setPreferredSize(new Dimension(750,120));
        //jtp.setBounds(0,0,200,200);
        jtp.setText(requirements.toString());
        JScrollPane pane = new JScrollPane(jtp);
        pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        reqPanel.add(pane);
        EligibleProfessorPanel.add(reqPanel, BorderLayout.SOUTH);

    }

//START: Drop Down Menu
    /**
     * Create our drop down box that is loaded with all of our committees
     */
    public void createDropDown(){
        //Initialize variables
        PanelDropDown = new JPanel();

        //Grab all committees and load them into a String array
        CommitteeList = FileManipulator.getCommittees();

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
                EligibleProfessorPanel.add(PanelTable, BorderLayout.CENTER);
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
                //selectedCommittee = committeeDropDown.getSelectedItem().toString();
                //Set selected committee from index in our committee list
                //selectedCommittee = Committee_Req_Constants.CommitteeNames[committeeDropDown.getSelectedIndex()];
                selectedCommittee = committeeDropDown.getSelectedItem().toString();
            }
        });

        //add components to panel.
        PanelDropDown.add(committeeDropDown);
        PanelDropDown.add(btnFindCommitteeMembers);

        //If beyond Feb put next year in the Spring. Radio buttons for
        //Fall 2017, Spring 2017, Spring 2018. Based on current year

        CreateRadioButtons();
    }


    /**
     * Create our radio buttons for the year we are in
     */
    public void CreateRadioButtons(){
        RadioButtonPanel = new JPanel();
        RadioButtonPanel.setLayout(new BoxLayout(RadioButtonPanel, BoxLayout.Y_AXIS));
        int nextYear = (thisYear) + 1;
        ThisFall = new JRadioButton(thisYear +" Fall");
        ThisFall.setSelected(true);
        ThisSpring = new JRadioButton(thisYear +" Spring");
        NextSpring = new JRadioButton(nextYear + " Spring");

        bG = new ButtonGroup();
        bG.add(ThisFall);
        bG.add(ThisSpring);
        bG.add(NextSpring);

        //Add to Panel
        RadioButtonPanel.add(ThisSpring);
        RadioButtonPanel.add(ThisFall);
        RadioButtonPanel.add(NextSpring);

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
        PanelTable.setSize(new Dimension(1000, 1000));

        //Add the table to our main Panel
        EligibleProfessorPanel.add(PanelTable, BorderLayout.CENTER);
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

        //Initialize variables
        ArrayList<Integer> EligibleProfessors = getAllEligible();

        //Loop through each Professor
        for (int i = 0; i < EligibleProfessors.size(); i++){
            //Get the row of our professor in excel sheet
            ProfessorRow = FileManipulator.professorSheet.getRow(EligibleProfessors.get(i));

            //Set the content wanted for each professor
            professorInfo = getProfessorInfo(ProfessorRow);

            //Add professorInfo(Our new Row) to ArrayList<Object[]> data
            data.add(professorInfo);
        }

        //Return information about the professors that meet given specs
        return data;
    }


    public ArrayList<Integer> getAllEligible(){
        //Find requirements
        required = new Requirements(selectedCommittee);

        //Check to see if they are currently serving on a committee
        ArrayList<Integer> withAssignment = FileManipulator.getAllEligibleNotCondition(Professor_Constants.CURRENT_ASSIGNMENT, "");

        //If serving on a committee and the term is ending or term has ended
        if(ThisSpring.isSelected()) {
            int lastYear = thisYear-1;
           // withAssignment = FileManipulator.getAllEligibleNotCondition(Professor_Constants.UNTIL, thisYear, withAssignment);
            ArrayList<Integer> ThisYear = FileManipulator.getAllEligible(Professor_Constants.UNTIL, (int)(lastYear));
            //ArrayList<Integer> ThisYear = FileManipulator.getAllEligibleNotCondition(Professor_Constants.UNTIL, (thisYear), withAssignment);

            //All professors eligible in the spring
            ArrayList<Integer> NotFallUntil = FileManipulator.getAllEligibleNotCondition(Professor_Constants.SEM, "F", ThisYear);

            NotFallUntil = FileManipulator.getAllEligibleNotCondition(Professor_Constants.UNTIL, (lastYear), NotFallUntil);

            ThisYear.removeAll(NotFallUntil);

            withAssignment = ThisYear;
            //If this Fall Find all professors eligible in the spring of this year
        } else if(ThisFall.isSelected()){

            //Get all professors This year
            ArrayList<Integer> ThisYear = FileManipulator.getAllEligible(Professor_Constants.UNTIL, (thisYear));

            //All professors eligible in the spring
            ArrayList<Integer> NotFallUntil = FileManipulator.getAllEligibleNotCondition(Professor_Constants.SEM, "S", ThisYear);

            ThisYear.removeAll(NotFallUntil);

            //merge all the lists
            withAssignment = ThisYear;
           // withAssignment = FileManipulator.mergeLists(withAssignment, ThisYear);

            //If Next Spring Find all professors eligible in the spring of this year
        } else if(NextSpring.isSelected()){
            //Get all professors This year
            //ArrayList<Integer> ThisYear = FileManipulator.getAllEligible(Professor_Constants.UNTIL, thisYear);
            withAssignment = FileManipulator.getAllEligible(Professor_Constants.UNTIL, thisYear);

            //merge all the lists
            //withAssignment = FileManipulator.mergeLists(withAssignment, ThisYear);

        }
        //Check box that asks Spring or Fall. If Spring do not show Until
        //that ends in spring. If Fall show folks that ended in spring.
        ArrayList<Integer> withOutAssignment = FileManipulator.getAllEligible(Professor_Constants.CURRENT_ASSIGNMENT, "");

        //Need to highlight married couples. Should add this to table.

        //Merge With and Without
        ArrayList<Integer> EligibleProfessors = FileManipulator.mergeLists(withOutAssignment, withAssignment);

        //Check if Next Assignment is empty
        //EligibleProfessors = rf.getAllEligible(Professor_Constants.NEXT_ASSIGNMENT, "", EligibleProfessors);
        //Check year appointed. Highlight if they were added in fall. SHOULD ADD THIS TO TABLE
        //special  election for current
        //or highlight all folks from previous year. Add key
        //EligibleProfessors = rf.getAllEligibleNotCondition(Professor_Constants.CURRENT_ASSIGNMENT, thisYear, EligibleProfessors);

        //not sabbatical
        EligibleProfessors = FileManipulator.getAllEligibleNotCondition(Professor_Constants.CURRENT_ASSIGNMENT, "Sabbatical", EligibleProfessors);

        //athRep
        //not athletic representative
        EligibleProfessors = FileManipulator.getAllEligibleNotCondition(Professor_Constants.CURRENT_ASSIGNMENT, "AthRep", EligibleProfessors);

        //TenureStatus DT-15 cannot but DT can. Also V is visiting
        //not Visiting
        EligibleProfessors = FileManipulator.getAllEligibleNotCondition(Professor_Constants.TENURE_STATUS, "V", EligibleProfessors);

        //not DTA-15
        EligibleProfessors = FileManipulator.getAllEligibleNotCondition(Professor_Constants.TENURE_STATUS, "DT-15", EligibleProfessors);

        //TenureStatus DT-AT cannot but DT can.
        //not Athletic Trainers (DT-AT)?
        EligibleProfessors = FileManipulator.getAllEligibleNotCondition(Professor_Constants.TENURE_STATUS, "DT-AT", EligibleProfessors);

        //not deans *works
        EligibleProfessors = FileManipulator.getAllEligibleNotContains(Professor_Constants.CURRENT_ASSIGNMENT, "Dean", EligibleProfessors);

        //not FacChairs *works
        EligibleProfessors = FileManipulator.getAllEligibleNotContains(Professor_Constants.CURRENT_ASSIGNMENT, "Fac", EligibleProfessors);

        //Get All Department Requirements
        EligibleProfessors = getDepartmentRequirements(required.fa_specs, EligibleProfessors);

        return EligibleProfessors;

    }

    /**
     * get set of all professors that meet requirements for EligibleProfessors
     * @param requirements reqs for the committee
     * @param EligibleProfessors professors that met all general specs
     * @return
     */
    public ArrayList<Integer> getDepartmentRequirements(int[] requirements, ArrayList<Integer> EligibleProfessors){

        ArrayList<ArrayList<Integer>> Divisions = new ArrayList<>();
        ArrayList<Integer> NewEligible = new ArrayList<>();
        ArrayList<Integer> FA = FileManipulator.getAllEligible(Professor_Constants.DIVISION,"FA", EligibleProfessors);
        ArrayList<Integer> HSS = FileManipulator.getAllEligible(Professor_Constants.DIVISION,"HSS", EligibleProfessors);
        ArrayList<Integer> MNS = FileManipulator.getAllEligible(Professor_Constants.DIVISION,"MNS", EligibleProfessors);
        ArrayList<Integer> PP = FileManipulator.getAllEligible(Professor_Constants.DIVISION,"PP", EligibleProfessors);
        ArrayList<Integer> L = FileManipulator.getAllEligible(Professor_Constants.DIVISION,"LL", EligibleProfessors);


        Divisions.add(FA);
        Divisions.add(HSS);
        Divisions.add(MNS);
        Divisions.add(PP);
        Divisions.add(L);

        /**
         * This requirements array stand for
         * [0] = # of people from this department are required
         * [1] = 1 if tenure is required, 0 if not
         * [2] = 1 if they must me Associate, 0 if not
         * [3] = # of years of service they must have at Mars Hill
         */
        int i = 0;
        int j = 0;
        for (ArrayList<Integer> Div : Divisions) {

            //req[1] is tenure. If 1 then sort through Tenure
            if(requirements[1] == 1 ){
                Div = FileManipulator.getAllMatches(Professor_Constants.TENURE_STATUS, ".*\\d+.*", Div);
            }
            if(requirements[2] == 1){
                Div = FileManipulator.getAllEligible(Professor_Constants.RANK, "Associate", Div);
            }
            if(requirements[3] == 1){
                Div = FileManipulator.getAllEligibleNotCondition(Professor_Constants.RANK, thisYear-5, Div);
            }
            NewEligible.addAll(Div);
        }

        return NewEligible;
    }


    /**
     * returns the names to our columns. The top row
     * @return string of column names
     */
    public String[] getTableColumns(){

        String [] columns = new String[] {"ID","First Name","Last Name", "Married To", "Div", "Current Pos", "Until", "Sem",
                "Pref 1", "Pref 2"};

        return columns;
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
        Info[0] = FileManipulator.getCellString(cell);

        cell = ProfessorRow.getCell(Professor_Constants.FIRST_NAME);
        Info[1] = FileManipulator.getCellString(cell);

        cell = ProfessorRow.getCell(Professor_Constants.LAST_NAME);
        Info[2] = FileManipulator.getCellString(cell);

        cell = ProfessorRow.getCell(Professor_Constants.Maried_To);
        Info[3] = FileManipulator.getCellString(cell);

        cell = ProfessorRow.getCell(Professor_Constants.DIVISION);
        Info[4] = FileManipulator.getCellString(cell);

        cell = ProfessorRow.getCell(Professor_Constants.CURRENT_ASSIGNMENT);
        Info[5] = FileManipulator.getCellString(cell);

        cell = ProfessorRow.getCell(Professor_Constants.UNTIL);
        Info[6] = FileManipulator.getCellString(cell);

        cell = ProfessorRow.getCell(Professor_Constants.SEM);
        Info[7] = FileManipulator.getCellString(cell);

        cell = ProfessorRow.getCell(Professor_Constants.PREFERENCE_1);
        Info[8] = FileManipulator.getCellString(cell);

        cell = ProfessorRow.getCell(Professor_Constants.PREFERENCE_2);
        Info[9] = FileManipulator.getCellString(cell);


        return Info;
    }

//END: Table

}

/**
 * work more on get department Requirements
 * Get rid of .0 at the end of Years and IDs
 *
 * Add Something that shows the specs of the committee selected.
 * Add a feature that will allow the user to Select prospective committee members.
 * If they have selected a person in that division than the spec is checked.
 * Add a way to switch Next Assignment-Representing to Current Assignment-Representing
 *
 */
