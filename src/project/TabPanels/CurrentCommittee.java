package project.TabPanels;


import project.*;
import project.TabPanels.CreateTable.DialogTableTester;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by s000191354 on 4/11/17.
 */
public class CurrentCommittee {

    //Initialize Variables
    private String[] CommitteeList;
    private JComboBox committeeDropDown = new JComboBox();
    private JButton btnFindCommitteeMembers = new JButton("Find Current Members");
    private int count = 0;
    private JPanel PanelDropDown;
    private JPanel PanelTable;
    private JPanel PanelCurrentCommittee;
    private String selectedCommittee = "";



    //Create our FileManipulator
    FileManipulator rf = new FileManipulator();


    /**
     * Test our pannel
     * @param args
     */
    public static void main(String[] args) {
        run();

    }

    /**
     * Test method
     */
    public static void run() {
        project.TabPanels.CurrentCommittee c = new CurrentCommittee();
        c.getPanel();
        JFrame frame = new JFrame();
        frame.setMinimumSize(new Dimension(500,500));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(c.PanelCurrentCommittee);
        frame.setVisible(true);
    }

    /**
     * Command called that will return our panel
     * @return
     */
    public JPanel getPanel() {
        PanelCurrentCommittee = new JPanel();

        PanelCurrentCommittee.setLayout(new GridLayout(1, 1));

        //create our drop down box to select a committee
        createDropDown();
        createTable();

        return PanelCurrentCommittee;


    }

//START: Drop Down Menu
    /**
     * Create our drop down box that is loaded with all of our committees
     */
    public void createDropDown(){
        //Initialize variables
        PanelDropDown = new JPanel();
        //Grab all commitees and load them into a String array
        CommitteeList = rf.getCommittees();

        //Add Committees to ComboBox
        for (int i = 0; !(CommitteeList[i].isEmpty()); i++)
            committeeDropDown.addItem(CommitteeList[count++]);

        //Set selectedCommittee
        selectedCommittee = CommitteeList[0];

        //Create our search button
        btnFindCommitteeMembers.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (count < CommitteeList.length)
                    committeeDropDown.addItem(CommitteeList[count++]);
            }
        });

        //Put stuff in this actionlister if you want something to happen when selecting a committee
        committeeDropDown.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectedCommittee = committeeDropDown.getSelectedItem().toString();
                System.out.println(selectedCommittee);
            }
        });

        //add components to panel. Maybe move this somewhere else.
        PanelDropDown.add(committeeDropDown);
        PanelDropDown.add(btnFindCommitteeMembers);

        PanelCurrentCommittee.add(PanelDropDown);
    }
//END: DROP DOWN MENU

//START: Table
    public void createTable(){
        PanelTable = DialogTableTester.getPanel();
        PanelCurrentCommittee.add(PanelTable, BorderLayout.CENTER);
    }



//END: Table



}

/**
 * Load Drop down box.
 */
