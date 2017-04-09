import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
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
    //Constants:
    //ROW COMMITTEES_STARTS is the row in the excel file that the committee starts on
    private static final int ROW_COMMITTEES_STARTS = 4;


    private int      fineArtsNum;
    private boolean  fineArtsMustHaveTenure;
    private int      fineArtsMinNumYears;
    private boolean      fineArtsMustBeAssociate;

    private int[] fineArts = new int[] {0,0,0,0};

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

    private boolean  isElected;
    private int      term_years;



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
        Requirements fa = new Requirements("Faculty Personel");
        System.out.println(fa.fineArtsMustHaveTenure);

    }

    public Requirements(String Committee){
        //FileManipulator rf = new FileManipulator("./Committee_on_Committes/CoC.xlsx");

        Workbook wb = rf.readExcelFile(rf.getPath());

        //Sheet one is the committee requirements
        rf.sheet = wb.getSheetAt(1);


        int CommitteeSpecsRow = findCommittee(Committee);

        getCommitteeRequirements(CommitteeSpecsRow);



    }



    private int findCommittee(String Committee){
        Cell cell;
        for(int i = ROW_COMMITTEES_STARTS; ; i++){
            cell = rf.getCell(0,i);
            if(cell.getStringCellValue().isEmpty()){
                return -1;
            }
            if(cell.getStringCellValue().equals(Committee)){
                return i;
            }
        }
    }

    private void getCommitteeRequirements(int CommitteeRow){
        getDepartmentRequirements(CommitteeRow, fineArts);


    }

    /**
     * Find all rules for department
     * @param CommitteeRow
     */
    private void getDepartmentRequirements(int CommitteeRow, int[] Department){
        Cell cell = rf.getCell(1,CommitteeRow);
        String specs = cell.getStringCellValue();

        if(Character.isDigit(specs.charAt(0)))
            Department[0] = 0;

        if(specs.contains(TENURE))
            Department[1] = 1;

        if(specs.contains(ASSOCIATE))
           Department[2] = 1;

        Matcher m = Pattern.compile("\\(([0-9])\\)").matcher(specs);
        if(m.find())
            Department[3] = Integer.parseInt(m.group(1));
    }




}
/**
 * what does hss=humanities and social, fa=fine arts, l=at large, mns=mathnatrural s, pp=prof programs stand for.
 * sem S=spring semester
 * create a class for each that contains all that aplies to each of these for quicker searching.
 *
 */









