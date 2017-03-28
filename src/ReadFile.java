

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.util.ArrayList;

/**
 *
 *
 */

/**
 * @author s000191354
 *
 */
public class ReadFile {
    String path = "";
    public static ArrayList<String[]> myList = new ArrayList<String[]>();

    public ReadFile(String path){
        this.path = path;

    }

    /**
     * This unction reads from a file when given a path
     * @param args
    ReadFile rf = new ReadFile("/home/s000191354/Committee_on_Committes/CocProfessors1.ods");
     */
    public static void main(String[] args) {
        try {
        InputStream inp = new FileInputStream("/home/s000191354/Committee_on_Committes/CocProfessors1.xlsx");
        //InputStream inp = new FileInputStream("workbook.xlsx");

            Workbook wb = WorkbookFactory.create(inp);
            Sheet sheet = wb.getSheetAt(0);
            Row row = sheet.getRow(2);
            Cell cell = row.getCell(3);
            if (cell == null)
                cell = row.createCell(3);
            cell.setCellType(CellType.STRING);
            System.out.println("Editing Excel sheet now");
            cell.setCellValue("HELLOWORLD");

            // Write the output to a file
            FileOutputStream fileOut = new FileOutputStream("/home/s000191354/Committee_on_Committes/CocProfessors1.xlsx");
            wb.write(fileOut);
            fileOut.close();
        }catch (IOException ioe){
            ioe.printStackTrace();
        }catch(InvalidFormatException ife){
            ife.printStackTrace();
        }
    }

	/*
	 * reads from file
	 *http://stackoverflow.com/questions/4716503/reading-a-plain-text-file-in-java
	 */


}
