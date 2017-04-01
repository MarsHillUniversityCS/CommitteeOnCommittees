import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;


/**
 * @author s000191354
 *
 */
public class FileManipulator {
    private Sheet sheet;


    private String path = "";
    //public static ArrayList<String[]> myList = new ArrayList<String[]>();

    private FileManipulator(String path){
        this.path = path;

    }

    /**
     * This unction reads from a file when given a path
     * @param args
    FileManipulator rf = new FileManipulator("/home/s000191354/Committee_on_Committes/CocProfessors1.ods");
     */
    public static void main(String[] args) {
        FileManipulator rf = new FileManipulator("./Committee_on_Committes/CoC.xlsx");

        Workbook wb = rf.readExcelFile(rf.getPath());

        Sheet sheet = wb.getSheetAt(0);


       // Cell cell = rf.getCell(1,3, sheet);

        int totalRows = sheet.getPhysicalNumberOfRows();
        Row row = sheet.getRow(0);
            Cell cell = rf.getCell(Constants.CoC.YEAR_APPOINTED.getID(),3);
            System.out.println(cell.toString());

        if (cell == null)
            cell = row.createCell(3);

        int [] professors = rf.getAllElidgable(Constants.CoC.DEPARTMENT.getID(), "Math & CS");
        professors = rf.getAllElidgable(Constants.CoC.LAST_NAME.getID(), "Nash", professors);
        System.out.println(professors.length);

        for(int i=0; i < professors.length; i++){
            if(professors[i]==0)break;
            System.out.println("professor department math  num = " + professors[i]);
        }
        cell.setCellType(CellType.STRING);
        //System.out.println("Editing Excel sheet now");
        //cell.setCellValue("TESTING THIS NOW");

        //rf.saveFile(wb, rf);
    }






    /**
     * write a workbook
     * @param wb workbook to be saved
     * @param rf FileManipulator with path to be saved
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
            Workbook wb = WorkbookFactory.create(inp);

            sheet = wb.getSheetAt(0);

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
     * Retrieves a cell in a sheet at the location given
     * @param cellNum x-Axis in cell
     * @param rowNum Y-axis of grid
     * @return
     */
    public Cell getCell(int cellNum, int rowNum){
        Row row = sheet.getRow(rowNum);

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
    public int[] getAllElidgable(int Column, String Condition, int [] professors){
        int spotInArray = 0;
        Cell cell;
        int [] eligibleProfessors = new int[professors.length];

        for(int i = 0; i < eligibleProfessors.length; i++){
            cell = getCell(Column, professors[i]);
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
    public int[] getAllElidgable(int Column, String Condition){
       int [] eligibleProfessors = new int[sheet.getPhysicalNumberOfRows()];
       int spotInArray = 0;
       Cell cell;

       for(int i = 1; i < sheet.getPhysicalNumberOfRows(); i++){
           cell = getCell(Column, i);
           if(cell.toString().equals(Condition)){
               eligibleProfessors[spotInArray++] = i;
           }
       }
       return eligibleProfessors;
    }



    /**
     * Getter for FileManipulator.
     * @return path of read file
     */
    public String getPath() {
        return path;
    }
	/*
	 * reads from file
	 *http://stackoverflow.com/questions/4716503/reading-a-plain-text-file-in-java
	 */


}
