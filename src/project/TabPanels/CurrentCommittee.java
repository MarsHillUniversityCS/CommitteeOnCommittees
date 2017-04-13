package project.TabPanels;


import project.*;
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
    private JPanel DropDownPanel;
    private JPanel CurrentCommittee;
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
     * Command called that will return our panel
     * @return
     */
    public JPanel getPanel(){
        CurrentCommittee = new JPanel();

        CurrentCommittee.setLayout(new GridLayout(1,1));

        //create our drop down box to select a committee
        createDropDown();

        return CurrentCommittee;


    }

    /**
     * Create our drop down box that is loaded with all of our committees
     */
    public void createDropDown(){
        //Initialize variables
        DropDownPanel = new JPanel();
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
        DropDownPanel.add(committeeDropDown);
        DropDownPanel.add(btnFindCommitteeMembers);

        CurrentCommittee.add(DropDownPanel);
    }

    public static void run() {
        project.TabPanels.CurrentCommittee c = new CurrentCommittee();
        c.getPanel();
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(c.DropDownPanel);
        frame.setVisible(true);
    }
}

/**
 * Load Drop down box.
 */
