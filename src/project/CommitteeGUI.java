package project;

import project.TabPanels.CurrentCommittee;
import project.TabPanels.EligibleProfessors;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import static javax.swing.JOptionPane.YES_OPTION;

/**
 * Created by s000191354 on 4/11/17.
 */
public class CommitteeGUI {


    private boolean isFileSaved;

    private JFrame CommitteeFrame;
    private String title = "Committee on Committees";

    private JTabbedPane tabbedPane;
    private JComponent FindCurrentCommittee;


    FileManipulator rf = new FileManipulator();

    public static void main(String[] args) {
        new CommitteeGUI().go();
    }

    public void createFrame(){
        CommitteeFrame = new JFrame(title);
        //CommitteeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        //CommitteeFrame.setBounds(0, 0, screenSize.width, screenSize.height);

        CommitteeFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        CommitteeFrame.setMinimumSize(new Dimension(800,600));
    }
    /**
     * Creates the menu bar at the top of YahtzeeFrame
     * and adds exit and restart
     */
    public void createMenu(){
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");

        fileMenu.setMnemonic('f');

        menuBar.add(fileMenu);

        //create Item for File Menu
        JMenuItem itemExit = new JMenuItem("Exit");
        JMenuItem itemSave = new JMenuItem("Save");
        JMenuItem itemAbout = new JMenuItem("About");

        //create action listeners
        itemExit.addActionListener(new exitListener());
        itemSave.addActionListener(new save());
        itemAbout.addActionListener(new aboutListener());

        fileMenu.add(itemAbout);
        fileMenu.add(itemSave);
        fileMenu.add(itemExit);

        CommitteeFrame.setJMenuBar(menuBar);
    }


    /**
     * https://docs.oracle.com/javase/tutorial/uiswing/components/tabbedpane.html
     */
    public void createTabbedPane(){
        tabbedPane = new JTabbedPane();

        //START Create Find Current Committee
        //FindCurrentCommittee = makeTextPanel("See Committee Members");


        tabbedPane.addTab("Current Committee", new CurrentCommittee().getPanel());
        tabbedPane.addTab("Eligible Professor", new EligibleProfessors().getPanel());

        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
        //END Create Find Current Committee

        CommitteeFrame.add(tabbedPane);

        addWindowExitListener();
    }


    /**
     * https://docs.oracle.com/javase/tutorial/uiswing/components/tabbedpane.html
     *
     * @param text
     * @return
     */
    private JComponent makeTextPanel(String text){
        JPanel panel = new JPanel(false);
        JLabel filler = new JLabel(text);
        filler.setHorizontalAlignment(JLabel.CENTER);
        panel.setLayout(new GridLayout(1,1));
        panel.add(filler);
        return panel;
    }

    public void createFindCurrentCommittee(){


    }

    public void go(){
        createFrame();
        createMenu();
        createTabbedPane();

        CommitteeFrame.setVisible(true);
    }
//START ACTION LISTENERS
    /**
     * What to do when the x is clicked
     */
    private void addWindowExitListener(){
        //Make sure it does not close
        CommitteeFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);


        //Add actionListener that asks if user wants to exit
        CommitteeFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                //Ask if they want to quit
                if (JOptionPane.showConfirmDialog(CommitteeFrame,
                        "Are you sure you would like to exit? All information changed will be saved!!", "Really Closing?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == YES_OPTION){

                    FileManipulator.saveFile();
                    System.exit(0);
                }
            }
        });
    }

    /**
     * opens high scores when selected
     */
    class save implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            FileManipulator.saveFile();
        }
    }

    /**
     * Action Listener
     * exits the program
     * @author s000191354
     *
     */
    class exitListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int result = JOptionPane.showConfirmDialog(null,
                    "Are you sure you'd like to exit?",
                    "Yahtzee",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            if(result == YES_OPTION){
                System.exit(0);
            }
        }
    }

    class aboutListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(CommitteeFrame, "This was created by Ryan Martin.",
                    "About", JOptionPane.INFORMATION_MESSAGE );
        }
    }

//END ACTION LISTENERS
}
