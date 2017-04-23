package project;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;


/**
 * @author s000191354
 *
 */
public final class FileManipulator {
    //Initialize variables
    public static Sheet professorSheet;
    public static Sheet committeeSheet;
    public static Workbook wb;

    //Our path that we use to view excel sheet
    public static String PATH = "./Committee_on_Committes/CoC.xlsx";

    /**
     * Constructor for FileManipulator.
     * Initializes our sheets
     */
    public FileManipulator(){
        wb = readExcelFile(getPath());

        professorSheet = wb.getSheetAt(0);
        committeeSheet = wb.getSheetAt(1);

    }

    /**
     * This unction reads from a file when given a path
     * @param args
    project.FileManipulator rf = new project.FileManipulator("/home/s000191354/Committee_on_Committes/CocProfessors1.ods");
     */
    public static void main(String[] args) {
        /*
        FileManipulator rf = new FileManipulator();

        Workbook wb = rf.readExcelFile(rf.getPath());

        Sheet sheet = wb.getSheetAt(0);
        //Sheet professorSheet = wb.getSheetAt(0);


        // Cell cell = rf.getCellFromProfessorSheet(1,3, professorSheet);

        int totalRows = sheet.getPhysicalNumberOfRows();
        Row row = sheet.getRow(0);
        Cell cell = rf.getCellFromProfessorSheet(Constants.CoC.YEAR_APPOINTED.getID(),3);

        if (cell == null)
            cell = row.createCell(3);

        int [] professors = rf.getAllEligible(Constants.CoC.DEPARTMENT.getID(), "Math & CS");
        professors = rf.getAllEligible(Constants.CoC.LAST_NAME.getID(), "Nash", professors);

        for(int i=0; i < professors.length; i++){
            if(professors[i]==0)break;
        }
        cell.setCellType(CellType.STRING);
        //cell.setCellValue("TESTING THIS NOW");

        //rf.saveFile(wb, rf);
        */
    }






    /**
     * write a workbook
     */
    public static void saveFile(){
        try {
            System.out.println("Saving File");
            FileOutputStream fileOut = new FileOutputStream(getPath());
            wb.write(fileOut);
            fileOut.close();
            //User friendly error
        }catch (IOException ioe){
            System.err.println("An Input or output operation has failed.");
        }
    }

    /**
     * Reading exel file and creating a new workbook
     * @param path is the location of the file you want to open.
     * @return a workbook
     */
    public static Workbook readExcelFile(String path){
        try {
            //Initialize variables
            InputStream inp = new FileInputStream(path);
            wb = WorkbookFactory.create(inp);
           // professorSheet = wb.getSheetAt(0);
           // committeeSheet = wb.getSheetAt(1);

            return wb;
            //Catch errors and give user friendly exceptions
        }catch (FileNotFoundException fnfe) {
            System.err.println("The file you tried to open does not exist.");
        }catch(InvalidFormatException ife){
            System.err.println("The file you tried to open has an INVALID FORM.");
        }catch (IOException ioe){
            System.err.println("An Input or output operation has failed.");
        }

        //This might create problems
        Workbook wb = new XSSFWorkbook();
        return wb;
    }

    /**
     * Retrieves a cell in a professorSheet at the location given
     * @param cellNum x-Axis in cell
     * @param rowNum Y-axis of grid
     * @return
     */
    public static Cell getCellFromProfessorSheet(int cellNum, int rowNum){
        //Professor Sheet
        Row row = wb.getSheetAt(0).getRow(rowNum);
        Cell cell = row.getCell(cellNum);

        return cell;
    }


    /**
     * Retrieves a cell in a CommitteeSheet at the location given
     * @param cellNum x-Axis in cell
     * @param rowNum Y-axis of grid
     * @return
     */
    public static Cell getCellFromCommitteeSheet(int cellNum, int rowNum){
        Row row = committeeSheet.getRow(rowNum);
        Cell cell = row.getCell(cellNum);

        return cell;
    }

    /**
     * Finds the cell in a column that matches the condition
     * @param column the column we are looking trough
     * @param condition content in the cell that we want to find
     * @return int row that our cell is in.
     */
    public static int getMatchedCellFromProfessorSheet(int column, String condition){
        //Initialize variables
        Cell cell;
        int row = 0;

        //Search through professor sheet for condition
        for(int i = 0; i < wb.getSheetAt(0).getPhysicalNumberOfRows(); i++){
            cell = getCellFromProfessorSheet(column, i);
            //If cell is equal to condition return row
            if (cell.toString().equals(condition)){
                row = i;
                break;
            }
        }
        return row;
    }


    /**
     * Remove all duplicates from list and add the second list. Not efficient at all...
     * @param list1
     * @param list2
     * @return
     */
    public static ArrayList<Integer> mergeLists(ArrayList<Integer> list1, ArrayList<Integer> list2){

        //Remove all duplicates
        list1.removeAll(list2);

        //Add all of list two
        list1.addAll(list2);

        return list1;

    }

    /**
     * sorts through a list of professors to find the ones that do not meet a condition
     * @param Column
     * @param Condition
     * @param professors
     * @return
     */
    public static ArrayList<Integer> getAllEligibleNotContains(int Column, String Condition, ArrayList<Integer> professors){
        //Initialize variables
        Cell cell;
        ArrayList<Integer> eligibleProfessors = new ArrayList<Integer>();

        //Search through array
        for(int i = 0; i < professors.size(); i++){
            cell = getCellFromProfessorSheet(Column, professors.get(i));
            //Check to make sure it does not contain expression
            if(!(cell.toString().toLowerCase().contains(Condition.toLowerCase()))){
                eligibleProfessors.add(professors.get(i));
            }
            //Check if we are on the Header row
            if(professors.get(i) == 0)break;
        }

        return eligibleProfessors;
    }

    /**
     * sorts through a list of professors to find the ones that do not meet a condition
     * @param Column
     * @param Condition
     * @param professors
     * @return
     */
    public static ArrayList<Integer> getAllEligibleNotCondition(int Column, String Condition, ArrayList<Integer> professors){
        //Initialize variables
        Cell cell;
        ArrayList<Integer> eligibleProfessors = new ArrayList<Integer>();

        //Search through array
        for(int i = 0; i < professors.size(); i++){
            cell = getCellFromProfessorSheet(Column, professors.get(i));
            if(cell==null)break;
            //Check to make sure it is not the condition
            if(!(cell.toString().equals(Condition))){
                eligibleProfessors.add(professors.get(i));
            }
            //Check if we are on the Header row
            if(professors.get(i) == 0)break;
        }

        return eligibleProfessors;
    }

    /**
     * sorts through a list of professors to find the ones that do not finish this year or after
     * @param Column
     * @param thisYear
     * @param professors
     * @return
     */
    public static ArrayList<Integer> getAllEligibleNotCondition(int Column, int thisYear, ArrayList<Integer> professors){
        //Initialize variables
        Cell cell;
        ArrayList<Integer> eligibleProfessors = new ArrayList<Integer>();

        //Search through array
        for(int i = 0; i < professors.size(); i++){
            cell = getCellFromProfessorSheet(Column, professors.get(i));
            if(cell==null)break;
            //Check to make sure it is not the condition
            if(!(cell.toString().equals(thisYear)) && !cell.toString().isEmpty()){
                if((cell.getNumericCellValue()) < thisYear)
                    eligibleProfessors.add(professors.get(i));
            }
        }

        return eligibleProfessors;
    }

    /**
     * Overload gets a list of professors to find the ones that do not meet a condition
     * @param Column
     * @param Condition
     * @return
     */
    public static ArrayList<Integer> getAllEligibleNotCondition(int Column, String Condition){

        //Initialize variables
        ArrayList<Integer> eligibleProfessors = new ArrayList<Integer>();//int[professorSheet.getPhysicalNumberOfRows()];
        Cell cell;

        //Search through professor sheet
        for(int i = 1; i < wb.getSheetAt(0).getPhysicalNumberOfRows(); i++){
            cell = getCellFromProfessorSheet(Column, i);
            //if cell is not equal to condition
            if(!(cell.toString().equals(Condition))){
                eligibleProfessors.add(i);
            }
        }
        return eligibleProfessors;
    }
    /**
     * Overloaded function that sorts through a list of professors to find if they meet a condition
     * @param Column
     * @param Condition
     * @param professors
     * @return
     */
    public static ArrayList<Integer> getAllEligible(int Column, String Condition, ArrayList<Integer> professors){
        //Initialize variables
        Cell cell;
        ArrayList<Integer> eligibleProfessors = new ArrayList<Integer>();

        //Search sheet
        for(int i = 0; i < professors.size(); i++){
            cell = getCellFromProfessorSheet(Column, professors.get(i));
            //If we have a match add it to our list of eligible professors
            if(cell.toString().equals(Condition)){
                eligibleProfessors.add(professors.get(i));
            }

            //If we are on the header row inside sheet
            if(professors.get(i) == 0)break;
        }

        return eligibleProfessors;
    }


    /**
     * Sort through all row and find which professors meet the condition
     * @param Column is the Constant.CoC Column we are looking through
     * @param Condition is the string we are looking for in our column
     * @return
     */
    public static ArrayList<Integer> getAllEligible(int Column, String Condition){

        //Initialize variables
        ArrayList<Integer> eligibleProfessors = new ArrayList<Integer>();//int[professorSheet.getPhysicalNumberOfRows()];
        Cell cell;

        //Search through professor sheet
        for(int i = 1; i < wb.getSheetAt(0).getPhysicalNumberOfRows(); i++){
            cell = getCellFromProfessorSheet(Column, i);
            if(cell==null)break;
            //if cell is equal to condition
            if(cell.toString().equals(Condition)){
                eligibleProfessors.add(i);
            }
        }
        return eligibleProfessors;
    }


    /**
     * Sort through all row and find which professors end their term this year
     * @param Column is the Constant.CoC Column we are looking through
     * @param thisYear is the string we are looking for in our column
     * @return
     */
    public static ArrayList<Integer> getAllEligible(int Column, int thisYear){

        //Initialize variables
        ArrayList<Integer> eligibleProfessors = new ArrayList<Integer>();//int[professorSheet.getPhysicalNumberOfRows()];
        Cell cell;

        //Search through professor sheet
        for(int i = 1; i < wb.getSheetAt(0).getPhysicalNumberOfRows(); i++){
            cell = getCellFromProfessorSheet(Column, i);
            if(cell==null)break;
            //if cell is equal to this year or less than
            if(!cell.toString().isEmpty() && (cell.getNumericCellValue() <= thisYear)){

                eligibleProfessors.add(i);
            }
        }
        return eligibleProfessors;
    }


    /**
     * This returns all of the committees inside of CoC Committee Sheet
     * @return A string of all the committees
     */
    public static String[] getCommittees(){
        //Initialize variables
        String [] Committees = new String[20];
        int committeeCount = 0;
        Cell cell;

        //Look at each name starting with row ROW_COMMITTEES_STARTS
        for(int i = Requirements.ROW_COMMITTEES_STARTS;   ; i++){
            cell = getCellFromCommitteeSheet(0,i);
            //If we reach an empty cell we are finished
            if(cell.toString().isEmpty()){
                Committees[committeeCount] = "";
                break;
            }
            //else add cell to Committees
            Committees[committeeCount++] = cell.toString();
        }


        return Committees;
    }

    /**
     * Overloaded function that sorts through a list of professors to find if they meet a condition
     * @param Column
     * @param Condition
     * @param professors
     * @return
     */
    public static ArrayList<Integer> getAllMatches(int Column, String Condition, ArrayList<Integer> professors){

        //Initialize variables
        Cell cell;
        ArrayList<Integer> eligibleProfessors = new ArrayList<Integer>();

        //Search sheet
        for(int i = 0; i < eligibleProfessors.size(); i++){
            cell = getCellFromProfessorSheet(Column, professors.get(i));
            //If we have a match add it to our list of eligible professors
            if(cell.toString().matches(Condition)){
                eligibleProfessors.add(professors.get(i));
            }

            //If we are on the header row inside sheet
            if(professors.get(i) == 0)break;
        }

        return eligibleProfessors;
    }


    /**
     * Take information that was changed in edit professor sheet and update spreadsheet
     * @param ProfessorInfo
     */
    public static void editProfessorRow(ArrayList<JTextArea> ProfessorInfo){
        //Initialize variables
        JTextArea profID = ProfessorInfo.get(Professor_Constants.ID);
        int rowInSheet = (int)Double.parseDouble(profID.getText());
        int numericValue = 0;
        Cell cell;

        for(int i = 0; i < Professor_Constants.PREFERENCE_5; i++){
            String info = ProfessorInfo.get(i).getText();
            cell = getCellFromProfessorSheet(i,rowInSheet);

            //check if cell is a text cell
            if(cell.getCellType() == Cell.CELL_TYPE_STRING || cell.toString().isEmpty()) {

                cell.setCellValue(info);
            //check if cell is a number cell
            }else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
                if(info.isEmpty()){
                    cell.setCellValue("");
                }else {
                    numericValue = (int)Double.parseDouble(info);
                    cell.setCellValue(numericValue);
                }
            }

        }
        //update professor sheet for other pages
        professorSheet = wb.getSheetAt(0);
        //saveFile();
    }


    /**
     * Getter for project.FileManipulator.
     * @return path of read file
     */
    public static String getPath() {
        return PATH;
    }
	/*
	 * reads from file
	 *http://stackoverflow.com/questions/4716503/reading-a-plain-text-file-in-java
	 */

}
