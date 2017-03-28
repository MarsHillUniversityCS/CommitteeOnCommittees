import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

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
        try {
            InputStream inp = new FileInputStream(rf.getPath());
            //InputStream inp = new FileInputStream("workbook.xlsx");

            Workbook wb = WorkbookFactory.create(inp);
            Sheet sheet = wb.getSheetAt(0);
            Row row = sheet.getRow(2);
            Cell cell = row.getCell(3);
            if (cell == null)
                cell = row.createCell(3);
            cell.setCellType(CellType.STRING);
            System.out.println("Editing Excel sheet now");
            cell.setCellValue("abcdefghijklmnopqrstuvwxyz");

            rf.writeFile(wb, rf);
        }catch (IOException ioe){
            ioe.printStackTrace();
        }catch(InvalidFormatException ife){
            ife.printStackTrace();
        }
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
