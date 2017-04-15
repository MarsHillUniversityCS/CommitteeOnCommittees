package project;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;


/**
 * @author s000191354
 *
 */
public final class FileManipulator {
    public Sheet professorSheet;
    public Sheet committeeSheet;
    public Workbook wb;

    public String PATH = "./Committee_on_Committes/CoC.xlsx";
    //public static ArrayList<String[]> myList = new ArrayList<String[]>();

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
        System.out.println(cell.toString());

        if (cell == null)
            cell = row.createCell(3);

        int [] professors = rf.getAllEligible(Constants.CoC.DEPARTMENT.getID(), "Math & CS");
        professors = rf.getAllEligible(Constants.CoC.LAST_NAME.getID(), "Nash", professors);
        System.out.println(professors.length);

        for(int i=0; i < professors.length; i++){
            if(professors[i]==0)break;
            System.out.println("professor department math  num = " + professors[i]);
        }
        cell.setCellType(CellType.STRING);
        //System.out.println("Editing Excel professorSheet now");
        //cell.setCellValue("TESTING THIS NOW");

        //rf.saveFile(wb, rf);
        */
    }






    /**
     * write a workbook
     * @param wb workbook to be saved
     * @param rf project.FileManipulator with path to be saved
     */
    public void saveFile(Workbook wb, FileManipulator rf){
        // Write the output to a file
        try {
            FileOutputStream fileOut = new FileOutputStream(rf.getPath());
            wb.write(fileOut);
            fileOut.close();
        }catch (IOException ioe){
            System.err.println("An Input or output operation has failed.");
        }
    }

    /**
     * Reading exel file and creating a new workbook
     * @param path is the location of the file you want to open.
     * @return a workbook
     */
    public Workbook readExcelFile(String path){
        try {
            InputStream inp = new FileInputStream(path);
            //InputStream inp = new FileInputStream("workbook.xlsx");
            wb = WorkbookFactory.create(inp);

            professorSheet = wb.getSheetAt(0);
            committeeSheet = wb.getSheetAt(1);

            return wb;
        }catch (FileNotFoundException fnfe) {
            System.err.println("The file you tried to open does not exist.");
        }catch(InvalidFormatException ife){
            System.err.println("The file you tried to open has an INVALID FORM.");
        }catch (IOException ioe){
            System.err.println("An Input or output operation has failed.");
        }

        Workbook wb = new XSSFWorkbook();
        return wb;
    }

    /**
     * Retrieves a cell in a professorSheet at the location given
     * @param cellNum x-Axis in cell
     * @param rowNum Y-axis of grid
     * @return
     */
    public Cell getCellFromProfessorSheet(int cellNum, int rowNum){
        Row row = professorSheet.getRow(rowNum);

        Cell cell = row.getCell(cellNum);

        return cell;
    }


    /**
     * Retrieves a cell in a professorSheet at the location given
     * @param cellNum x-Axis in cell
     * @param rowNum Y-axis of grid
     * @return
     */
    public Cell getCellFromCommitteeSheet(int cellNum, int rowNum){
        Row row = committeeSheet.getRow(rowNum);

        Cell cell = row.getCell(cellNum);

        return cell;
    }
    /**
     * Overloaded function that sorts through a list of professors to find if they meet a condition
     * @param Column
     * @param Condition
     * @param professors
     * @return
     */
    public int[] getAllEligible(int Column, String Condition, int [] professors){
        int spotInArray = 0;
        Cell cell;
        int [] eligibleProfessors = new int[professors.length];

        for(int i = 0; i < eligibleProfessors.length; i++){
            cell = getCellFromProfessorSheet(Column, professors[i]);
            if(cell.toString().equals(Condition)){
                eligibleProfessors[spotInArray++] = professors[i];
            }
            if(professors[i] == 0)break;
        }

        return eligibleProfessors;
    }


    /**
     * Sort through all row and find which professors meet the condition
     * @param Column is the Constant.CoC Column we are looking through
     * @param Condition is the string we are looking for in our column
     * @return
     */
    public ArrayList<Integer> getAllEligible(int Column, String Condition){
        ArrayList<Integer> eligibleProfessors = new ArrayList<Integer>();//int[professorSheet.getPhysicalNumberOfRows()];
        int spotInArray = 0;
        Cell cell;

        for(int i = 1; i < professorSheet.getPhysicalNumberOfRows(); i++){
            cell = getCellFromProfessorSheet(Column, i);
            if(cell.toString().equals(Condition)){
                //eligibleProfessors.add(spotInArray++);
                eligibleProfessors.add(i);
                //eligibleProfessors[spotInArray++] = i;
            }
        }
        return eligibleProfessors;
    }


    public String[] getCommittees(){
        String [] Committees = new String[20];
        int committeeCount = 0;

        Cell cell;
        for(int i = Requirements.ROW_COMMITTEES_STARTS;   ; i++){

            cell = getCellFromCommitteeSheet(0,i);
            if(cell.toString().isEmpty()){
                Committees[committeeCount] = "";
                break;
            }
            Committees[committeeCount++] = cell.toString();
        }


        return Committees;
    }


    /**
     * Getter for project.FileManipulator.
     * @return path of read file
     */
    public String getPath() {
        return PATH;
    }
	/*
	 * reads from file
	 *http://stackoverflow.com/questions/4716503/reading-a-plain-text-file-in-java
	 */


}
