package project;

/**
 * Created by s000191354 on 4/1/17.
 */
public final class Constants {
    /*
    public static final int FIRST_NAME = 0;
    public static final int LAST_NAME = 1;
    public static final int DIVISION = 2;
    public static final int DEPARTMENT = 3;
    public static final int PROGRAM = 4;
    public static final int YEAR_APPOINTED = 5;
    public static final int CURRENT_ASSIGNMENT = 6;
    public static final int COMMITTEE_NUMBER = 7;
    public static final int REPRESENTING = 8;
    public static final int UNTIL = 9;
    public static final int SEM = 10;
    public static final int NEXT_ASSIGNMENT = 11;
    public static final int NEXT_ASSIGNMENT_COMMITTEE_NUMBER= 12;
    public static final int NEXT_ASSIGNMENT_REPRESENTING = 13;
    public static final int NEXT_ASSIGNMENT_UNTIL = 14;
    public static final int NEXT_ASSIGNMENT_SEM = 15;
    public static final int RANK = 16;
    public static final int TENURE_STATUS= 17;
    public static final int YEAR_ELIGIBLE_TENURE = 18;
    public static final int NEXT_YEAR_TENURE = 19;
    public static final int YEAR_ELEGIBLE = 20;
    public static final int PREFERENCE_1 = 21;
    public static final int PREFERENCE_2 = 22;
    public static final int PREFERENCE_3 = 23;
    public static final int PREFERENCE_4 = 24;
    public static final int PREFERENCE_5 = 25;
    */


    public static final String EMPTY = "-";

    /**
     * CoC - Committeee on Committee Excel Columns.
     * ID is the column number inside the excel sheet.
     */
    public enum CoC {
        FIRST_NAME(0), LAST_NAME(1), DIVISION(2), DEPARTMENT(3), PROGRAM(4), YEAR_APPOINTED(5),
        CURRENT_ASSIGNMENT(6), COMMITTEE_NUMBER(7), REPRESENTING(8), UNTIL(9), SEM(10),
        NEXT_ASSIGNMENT(11), NEXT_ASSIGNMENT_COMMITTEE_NUMBER(12), NEXT_ASSIGNMENT_REPRESENTING(13), NEXT_ASSIGNMENT_UNTIL(14),
        NEXT_ASSIGNMENT_SEM(15),
        RANK(16), TENURE_STATUS(17), YEAR_ELIGIBLE_TENURE(18), NEXT_YEAR_TENURE(19), YEAR_ELEGIBLE(20),
        PREFERENCE_1(21), PREFERENCE_2(22), PREFERENCE_3(23), PREFERENCE_4(24), PREFERENCE_5(25);

        private int id;

        CoC(int id){
            this.id = id;
        }

        public int getID(){return id;};
    }
}
/**
 * Tempted to create Constant Strings for every situation possible for each column...
 */









