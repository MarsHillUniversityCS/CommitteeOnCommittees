import TabPanels.CurrentCommittee;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by s000191354 on 4/11/17.
 */
public class CommitteeGUI {



    private JFrame CommitteeFrame;
    private String title = "Committee on Committees";

    private JTabbedPane tabbedPane;
    private JComponent FindCurrentCommittee;



    public static void main(String[] args) {
        new CommitteeGUI().go();
    }

    public void createFrame(){
        CommitteeFrame = new JFrame(title);
        CommitteeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        //CommitteeFrame.setBounds(0, 0, screenSize.width, screenSize.height);

        CommitteeFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        CommitteeFrame.setMinimumSize(new Dimension(800,600));
    }

    /**
     * https://docs.oracle.com/javase/tutorial/uiswing/components/tabbedpane.html
     */
    public void createTabbedPane(){
        tabbedPane = new JTabbedPane();

        //START Create Find Current Committee
        //FindCurrentCommittee = makeTextPanel("See Committee Members");


        tabbedPane.addTab("Tab 1", CurrentCommittee.getPanel());

        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
        //END Create Find Current Committee

        CommitteeFrame.add(tabbedPane);

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
        createTabbedPane();


        CommitteeFrame.setVisible(true);
    }
}
