package TabPanels;

import javax.swing.*;
import java.awt.*;

/**
 * Created by s000191354 on 4/11/17.
 */
public class CurrentCommittee {
    public static void main(String[] args) {

    }


    public static JPanel getPanel(){
        JPanel CurrentCommittee = new JPanel();
        JLabel filler = new JLabel("TEST");
        filler.setHorizontalAlignment(JLabel.CENTER);
        CurrentCommittee.setLayout(new GridLayout(1,1));
        CurrentCommittee.add(filler);
        return CurrentCommittee;
    }
}
