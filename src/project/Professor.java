package project;

import org.apache.poi.ss.usermodel.Table;

import java.util.ArrayList;

public class Professor {

    private boolean profIsActive;
    private int profID;
    private String profFirstName;
    private String profLastName;
    private String profCurrentAssignment;
    private int profMarried;
    private String profDivision;
    private String profDept;
    private String profProgram;
    private int profHired;
    private int profCurrentAssignmentID;
    private String profCurrentRepresenting;
    private int profRepresentingCurrentUntil;
    private String profCurrentSemester;
    private String profNextAssignment;
    private int profNextAssignmentID;
    private String profNextRepresenting;
    private int profRepresentingNextUntil;
    private String profNextSemester;
    private String profRank;
    private int profTenure;
    private String profYearEligibleTenure;
    private String profNextYearTenureStatus;
    private String profPreferenceOne;
    private String profPreferenceTwo;
    private String profPreferenceThree;
    private String profPreferenceFour;
    private String profPreferenceFive;



    ArrayList<String> TABLE_CONSTANTS = new ArrayList<String>();

    public Professor(int ID, String firstName, String lastName, String currentAssignment){
        profID = ID;
        profFirstName = firstName;
        profLastName = lastName;
        profCurrentAssignment = currentAssignment;
    }

    public Professor(){}

    public boolean getProfIsActive() {
        return profIsActive;
    }

    public void setProfIsActive(boolean profIsActive) {
        this.profIsActive = profIsActive;
    }

    public int getProfID() {
        return profID;
    }

    public void setProfID(int profID) {
        this.profID = profID;
    }

    public String getProfFirstName() {
        return profFirstName;
    }

    public void setProfFirstName(String profFirstName) {
        this.profFirstName = profFirstName;
    }

    public String getProfLastName() {
        return profLastName;
    }

    public void setProfLastName(String profLastName) {
        this.profLastName = profLastName;
    }

    public String getProfCurrentAssignment() {
        return profCurrentAssignment;
    }

    public void setProfCurrentAssignment(String profCurrentAssignment) {
        this.profCurrentAssignment = profCurrentAssignment;
    }

    public void setProfMarried(int profMarried){
        this.profMarried = profMarried;
    }

    public void setProfDivison(String profDivision){
        this.profDivision = profDivision;
    }

    public void setProfDept(String profDept){
        this.profDept = profDept;
    }

    public void setProfProgram(String profProgram){
        this.profProgram = profProgram;
    }

    public void setProfHired(int profHired){
        this.profHired = profHired;
    }

    public void setProfCurrentAssignmentID(int profCurrentAssignmentID){
        this.profCurrentAssignmentID = profCurrentAssignmentID;
    }

    public void setProfCurrentRepresenting(String profCurrentRepresenting){
        this.profCurrentRepresenting = profCurrentRepresenting;
    }

    public void setProfRepresentingCurrentUntil(int profRepresentingCurrentUntil){
        this.profRepresentingCurrentUntil = profRepresentingCurrentUntil;
    }

    public void setProfCurrentSemester(String profCurrentSemester){
        this.profCurrentSemester = profCurrentSemester;
    }

    public void setProfNextAssignment(String profNextAssignment){
        this.profNextAssignment = profNextAssignment;
    }

    public void setProfNextAssignmentID(int profNextAssignmentID){
        this.profNextAssignmentID = profNextAssignmentID;
    }

    public void setProfNextRepresenting(String profNextRepresenting){
        this.profNextRepresenting = profNextRepresenting;
    }

    public void setProfRepresentingNextUntil(int profRepresentingNextUntil){
        this.profRepresentingNextUntil = profRepresentingNextUntil;
    }

    public void setProfNextSemester(String profNextSemester){
        this.profNextSemester = profNextSemester;
    }

    public void setProfRank(String profRank){
        this.profRank = profRank;
    }

    public void setProfTenure(int profTenure){
        this.profTenure = profTenure;
    }

    public void setProfYearEligibleTenure(String profYearEligibleTenure){
        this.profYearEligibleTenure = profYearEligibleTenure;
    }

    public void setProfNextYearTenureStatus(String profNextYearTenureStatus){
        this.profNextYearTenureStatus = profNextYearTenureStatus;
    }

    public void setProfPreferenceOne(String profPreferenceOne){
        this.profPreferenceOne = profPreferenceOne;
    }

    public void setProfPreferenceTwo(String profPreferenceTwo){
        this.profPreferenceTwo = profPreferenceTwo;
    }

    public void setProfPreferenceThree(String profPreferenceThree){
        this.profPreferenceThree = profPreferenceThree;
    }

    public void setProfPreferenceFour(String profPreferenceFour){
        this.profPreferenceFour = profPreferenceFour;
    }

    public void setProfPreferenceFive(String profPreferenceFive){
        this.profPreferenceFive = profPreferenceFive;
    }

    public Object[] getTableInfo(){

        Object[] info = new Object[4];
        int i = 0;
        info[i++] = getProfID();
        info[i++] = getProfFirstName();
        info[i++] = getProfLastName();
        info[i++] = "2018";

        return info;

    }

    public void fillStringConstants(){
        TABLE_CONSTANTS.add("First Name");
        TABLE_CONSTANTS.add("Last Name");
        TABLE_CONSTANTS.add("ID");
        TABLE_CONSTANTS.add("Married To");
        TABLE_CONSTANTS.add("Division");
        TABLE_CONSTANTS.add("Dept");
        TABLE_CONSTANTS.add("Program");
        TABLE_CONSTANTS.add("Year of Appt.");
        TABLE_CONSTANTS.add("Current Assignment");
        TABLE_CONSTANTS.add(" #");
        TABLE_CONSTANTS.add("Representing");
        TABLE_CONSTANTS.add("until");
        TABLE_CONSTANTS.add("Sem");
        TABLE_CONSTANTS.add("Next Assignment");
        TABLE_CONSTANTS.add("#");
        TABLE_CONSTANTS.add("Representing");
        TABLE_CONSTANTS.add("until");
        TABLE_CONSTANTS.add("Sem");
        TABLE_CONSTANTS.add("Rank");
        TABLE_CONSTANTS.add("Tenure Status");
        TABLE_CONSTANTS.add("Year Eligible for Tenure");
        TABLE_CONSTANTS.add("Next Year Tenure Status (if different)");
        TABLE_CONSTANTS.add("Year Eligible (if applicable)");
        TABLE_CONSTANTS.add("Pref1");
        TABLE_CONSTANTS.add("Pref2");
        TABLE_CONSTANTS.add("Pref3");
        TABLE_CONSTANTS.add("Pref4");
        TABLE_CONSTANTS.add("Pref5");
    }
}
