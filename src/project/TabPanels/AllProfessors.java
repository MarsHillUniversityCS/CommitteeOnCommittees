package project.TabPanels;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import project.CreateDB;
import project.FileManipulator;
import project.Professor_Constants;
import project.TabPanels.CreateTable.DialogTableTester;
import project.Professor;
import java.sql.Connection;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by s000191354 on 4/22/17.
 */
public class AllProfessors {

    private JPanel PanelAllProfessors;

    /**
     * Command called that will return our panel
     * @return
     */
    public JPanel getPanel() {

        //Get a connection to the database
        CreateDB db = new CreateDB();
        Connection conn = db.getConnection();

        //Our main panel
        PanelAllProfessors = new JPanel();

        PanelAllProfessors.setLayout(new GridLayout(1, 1));

        //Create our table that displays minor information
        createTable();

        return PanelAllProfessors;
    }


//START: Table

    String[] tableColumns;
    JPanel PanelTable;

    /**
     * Program that runs our methods that create our table
     */
    public void createTable(){
        //Get the columns for our table. These are created in a method
        tableColumns = getTableColumns();

        //Get the table data. That is the Professors in the selected committee
        ArrayList<Professor> tableData = getTableData();

        //Get the Table inside of a Panel using a GitHub Class from Oliver Watkins
        PanelTable = DialogTableTester.getPanel(tableColumns, tableData);

        //Add the table to our main Panel
        PanelAllProfessors.add(PanelTable, BorderLayout.CENTER);
    }

    /**
     * returns the names to our columns. The top row
     * @return string of column names
     */
    public String[] getTableColumns(){

        String [] columns = new String[] {"ID","First Name", "Last Name", "Term", "Active"};

        return columns;
    }


    /**
     * Gets the data that will be loaded into our table
     * @return Array list of object[]. Each Object[] is information about the professor
     */
    public ArrayList<Professor> getTableData(){
        //Initialize ArrayList data
        ArrayList<Professor> profList = new ArrayList<Professor>();

        CreateDB db = new CreateDB();

        profList = db.getAllProfessors();

        //Object[] professorInfo;
        //Row ProfessorRow;

        //Our ArrayList of Professors. Each Integer represents professor's row number
 /*       ArrayList<Integer> EligibleProfessors = FileManipulator.getAllProfessors();

        //Loop through each Professor
        for (int i = 0; i < EligibleProfessors.size(); i++){
            //Get the row of our professor in excel sheet
            ProfessorRow = FileManipulator.professorSheet.getRow(EligibleProfessors.get(i));
            //Initialize our professorInfo
            professorInfo = new Object[tableColumns.length];

            //Load Info into professorInfo
            Cell cell = ProfessorRow.getCell(Professor_Constants.ID);
            professorInfo[0] = FileManipulator.getCellString(cell);

            cell = ProfessorRow.getCell(Professor_Constants.FIRST_NAME);
            professorInfo[1] = FileManipulator.getCellString(cell);

            cell = ProfessorRow.getCell(Professor_Constants.LAST_NAME);
            professorInfo[2] = FileManipulator.getCellString(cell);

            cell = ProfessorRow.getCell(Professor_Constants.UNTIL);
            professorInfo[3] = FileManipulator.getCellString(cell);

            *//*
            for(int j =0; j < tableColumns.length; j++) {
                Cell cell = ProfessorRow.getCell(Professor_Constants.FIRST_NAME);
                professorInfo[j] = cell.toString();
                //data[i][j] = cell.toString();
                //CHECK WHEN WE ARE FINISHED
            }
            *//*

            //Add professorInfo(Our new Row) to ArrayList<Object[]> data
            //data.add(professorInfo);
        }*/

        return profList;
    }

//END: Table
}
