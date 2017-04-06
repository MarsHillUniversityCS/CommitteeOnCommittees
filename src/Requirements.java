import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

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
    private static final int ROW_COMMITTEES_STARTS = 6 ;


    private int      fineArtsNum;
    private boolean  fineArtsMustHaveTenure;
    private int      fineArtsMinNumYears;
    private int      fineArtsMinRank;

    private int      hssNum;
    private boolean  hssMustHaveTenure;
    private int      hssMinNumYears;
    private int      hssArtsMinRank;

    private int      mnsNum;
    private boolean  mnsMustHaveTenure;
    private int      mnsMinNumYears;
    private int      mnsArtsMinRank;

    private int      ppNum;
    private boolean  ppMustHaveTenure;
    private int      ppMinNumYears;
    private int      ppArtsMinRank;

    private int      atLargeNum;
    private boolean  atLargeMustHaveTenure;
    private int      atLargeMinNumYears;
    private int      atLargeMinRank;

    private boolean  isElected;
    private int      term_years;



    FileManipulator rf = new FileManipulator("./Committee_on_Committes/CoC.xlsx");

    public Requirements(String Committee){
        //FileManipulator rf = new FileManipulator("./Committee_on_Committes/CoC.xlsx");

        Workbook wb = rf.readExcelFile(rf.getPath());

        //Sheet one is the committee requirements
        Sheet sheet = wb.getSheetAt(1);

        int CommitteeSpecsRow = findCommittee(Committee, sheet);

        getCommittteeSpecs(CommitteeSpecsRow);



    }



    private int findCommittee(String Committee, Sheet sheet){
        Cell cell;
        for(int i = 6; ; i++){
            cell = rf.getCell(ROW_COMMITTEES_STARTS,i);
            if(cell.getStringCellValue().isEmpty()){
                return 0;
            }
            if(cell.getStringCellValue().equals(Committee)){
                return i;
            }
        }
    }

    private void getCommittteeSpecs(int row){
        Cell cell = rf.getCell(1,row);
        String specs = cell.getStringCellValue();

        if(specs.contains("(T)"))
            fineArtsMustHaveTenure = true;

        for(int i=1; i <= 7; i++){


        }
    }
}
/**
 * what does hss=humanities and social, fa=fine arts, l=at large, mns=mathnatrural s, pp=prof programs stand for.
 * sem S=spring semester
 * create a class for each that contains all that aplies to each of these for quicker searching.
 *
 */









