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
        FileManipulator rf = new FileManipulator("/home/s000191354/Committee_on_Committes/CocProfessors1.xlsx");

        Workbook wb = rf.readExcelFile(rf.getPath());

        Sheet sheet = wb.getSheetAt(0);


        Cell cell = rf.getCell(1,3, sheet);

        int totalRows = sheet.getPhysicalNumberOfRows();
        Row row = sheet.getRow(0);
        for (int i = 0; i < totalRows; i++){
            //Cell cell
            System.out.println();
        }

        if (cell == null)
            cell = row.createCell(3);


        cell.setCellType(CellType.STRING);
        System.out.println("Editing Excel sheet now");
        cell.setCellValue("TESTING THIS NOW");

        rf.writeFile(wb, rf);
    }



    /**
     * write a workbook
     * @param wb workbook to be saved
     * @param rf FileManipulator with path to be saved
     */
    public void writeFile(Workbook wb, FileManipulator rf){
        // Write the output to a file
        try {
            FileOutputStream fileOut = new FileOutputStream(rf.getPath());
            wb.write(fileOut);
            fileOut.close();
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    public Workbook readExcelFile(String path){
        try {
            InputStream inp = new FileInputStream(path);
            //InputStream inp = new FileInputStream("workbook.xlsx");
            Workbook wb = WorkbookFactory.create(inp);

            return wb;
        }catch (Exception e) {
            e.printStackTrace();
        }
        Workbook wb = new XSSFWorkbook();
        return wb;
    }

    /**
     * Retrieves a cell in a sheet at the location given
     * @param cellNum x-Axis in cell
     * @param rowNum Y-axis of grid
     * @param sheet sheet we are looking at
     * @return
     */
    public Cell getCell(int cellNum, int rowNum, Sheet sheet){
        Row row = sheet.getRow(rowNum);

        Cell cell = row.getCell(cellNum);

        return cell;
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
