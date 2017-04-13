package project;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by s000191354 on 4/6/17.
 * Each Department must check to see if Tenured, Number of years specified, and
 * Minimum Rank allowed(usually associate).
 * hss=humanities and social, fa=fine arts, l=at large, mns=math&natrural sience, pp=prof programs
 * L=atLarge
 */
public class Requirements {
    //project.Constants:
    //ROW COMMITTEES_STARTS is the row in the excel file that the committee starts on


    /*
    private int      fineArtsNum;
    private boolean  fineArtsMustHaveTenure;
    private int      fineArtsMinNumYears;
    private boolean      fineArtsMustBeAssociate;


    private int      hssNum;
    private boolean  hssMustHaveTenure;
    private int      hssMinNumYears;
    private boolean      hssArtsMustBeAssociate;



    private int      mnsNum;
    private boolean  mnsMustHaveTenure;
    private int      mnsMinNumYears;
    private boolean      mnsArtsMustBeAssociate;

    private int      ppNum;
    private boolean  ppMustHaveTenure;
    private int      ppMinNumYears;
    private boolean      ppArtsMustBeAssociate;

    private int      atLargeNum;
    private boolean  atLargeMustHaveTenure;
    private int      atLargeMinNumYears;
    private boolean      atLargeMustBeAssociate;
    */

    //This is the row that our committees start on
    public static final int ROW_COMMITTEES_STARTS = 4;

    private boolean isElected;
    private double term_years;

    /**
     * This array stand for
     * [0] = # of people from this department are required
     * [1] = 1 if tenure is required, 0 if not
     * [2] = 1 if they must me Associate, 0 if not
     * [3] = # of years of service they must have at Mars Hill
     */
    private int[] fa_specs = new int[] {0,0,0,0};
    private int[] HSS_specs = new int[] {0,0,0,0};
    private int[] MNS_specs = new int[] {0,0,0,0};
    private int[] PP_specs = new int[] {0,0,0,0};
    private int[] L_specs = new int[] {0,0,0,0};

    private static final int FA_COLUMN = 1;
    private static final int HSS_COLUMN = 2;
    private static final int MNS_COLUMN = 3;
    private static final int PP_COLUMN = 4;
    private static final int L_COLUMN = 5;
    private static final int ELECTED_COLUMN = 6;
    private static final int TERM_COLUMN = 7;

    private static final String TENURE = "(T)";
    private static final String ASSOCIATE = "(A)";
    private static final String ELECTED = "Elected";

    FileManipulator rf = new FileManipulator("./Committee_on_Committes/CoC.xlsx");


    public static void main(String[] args) {
        //project.Requirements FacultyPersonel = new project.Requirements("Faculty Personel");
        Requirements PandP = new Requirements("P&P");

        //System.out.println(FacultyPersonel.isElected);
        //System.out.println(FacultyPersonel.term_years);

    }


    /**
     * This Opens our workbook and reads the File in. Looks at our committee professorSheet
     * and then calls getCommitteeRequirements
     * @param Committee
     */
    public Requirements(String Committee){
        //project.FileManipulator rf = new project.FileManipulator("./Committee_on_Committes/CoC.xlsx");

        Workbook wb = rf.readExcelFile(rf.getPath());

        //Sheet one is the committee requirements
        rf.professorSheet = wb.getSheetAt(1);


        int CommitteeSpecsRow = findCommittee(Committee);

        getCommitteeRequirements(CommitteeSpecsRow);



    }


    /**
     * Finds what row our committee is on in the Excel professorSheet
     * @param Committee
     * @return
     */
    private int findCommittee(String Committee){
        Cell cell;
        for(int i = ROW_COMMITTEES_STARTS; ; i++){
            cell = rf.getCellFromProfessorSheet(0,i);
            if(cell.getStringCellValue().isEmpty()){
                return -1;
            }
            if(cell.getStringCellValue().equals(Committee)){
                return i;
            }
        }
    }


    /**
     * Find all rules for department
     * @param CommitteeRow
     */
    private void getCommitteeRequirements(int CommitteeRow){
        getDepartmentSpecs(CommitteeRow, FA_COLUMN, fa_specs);
        getDepartmentSpecs(CommitteeRow, HSS_COLUMN, HSS_specs);
        getDepartmentSpecs(CommitteeRow, MNS_COLUMN, MNS_specs);
        getDepartmentSpecs(CommitteeRow, PP_COLUMN, PP_specs);
        getDepartmentSpecs(CommitteeRow, L_COLUMN, L_specs);

        getIsElected(CommitteeRow);
        getTermYears(CommitteeRow);
    }

    /**
     * Find out if Committee is elected or Appointed
     * @param CommitteeRow
     */
    private void getIsElected(int CommitteeRow){
        Cell cell = rf.getCellFromProfessorSheet(ELECTED_COLUMN,CommitteeRow);

        if(cell.getStringCellValue().equals(ELECTED)){
            isElected = true;
        }else{
            isElected = false;
        }

    }


    /**
     * Get the specs for a department. Looks at the Column given
     * And checks if they must have # of professors, if Tenure, and if Associate
     * This array stand for
     * [0] = # of people from this department are required
     * [1] = 1 if tenure is required, 0 if not
     * [2] = 1 if they must me Associate, 0 if not
     * [3] = # of years of service they must have at Mars Hill
     * @param CommitteeRow
     * @param Column
     * @param Department
     */
    private void getDepartmentSpecs(int CommitteeRow, int Column, int[] Department){
        Cell cell = rf.getCellFromProfessorSheet(Column,CommitteeRow);

        System.out.println(cell.toString());

        CellType cellType = cell.getCellTypeEnum();

        //If the cell contains only numbers
        if(cellType.equals(CellType.NUMERIC)){
            Department[0] = (int)cell.getNumericCellValue();

        }else {
            String specs = cell.getStringCellValue();

            //set # of people from department required
            if (Character.isDigit(specs.charAt(0)))
                Department[0] = Integer.parseInt(specs.charAt(0) + "");

            //set whether tenure is required
            if (specs.contains(TENURE))
                Department[1] = 1;

            //Set rank
            if (specs.contains(ASSOCIATE))
                Department[2] = 1;

            //Set # of years of service they must have at Mars Hill
            Matcher m = Pattern.compile("\\(([0-9])\\)").matcher(specs);
            if (m.find())
                Department[3] = Integer.parseInt(m.group(1));
        }
    }


    /**
     * Length of position on Committee
     * @param CommitteeRow
     */
    public void getTermYears(int CommitteeRow) {
        Cell cell = rf.getCellFromProfessorSheet(TERM_COLUMN,CommitteeRow);

        String termOfYears = cell.toString();
        System.out.println(termOfYears);

        if(termOfYears.isEmpty() || termOfYears.matches("[0-9]+")){
            term_years = -1;
        }else{
            term_years = Double.parseDouble(termOfYears);
        }

    }
}
/**
 * what does hss=humanities and social, fa=fine arts, l=at large, mns=mathnatrural s, pp=prof programs stand for.
 * sem S=spring semester
 * create a class for each that contains all that aplies to each of these for quicker searching.
 * Find out a way to check that the cell is a numeric value
 */









