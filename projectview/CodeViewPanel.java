package projectview;

import java.awt.*;
import java.util.Observer;
import project.MachineModel;
import project.Memory;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public class CodeViewPanel implements Observer {
    private MachineModel model;
    private JScrollPane scroller;
    private JTextField[] codeHex = new JTextField[Memory.CODE_MAX/2];
    private JTextField[] codeDecimal = new JTextField[Memory.CODE_MAX/2];
    private int previousColor = -1;


    public CodeViewPanel(ViewMediator gui, MachineModel mdl){
        gui.addObserver(this);
        this.model = mdl;


    }
    public JComponent createCodeDisplay(){
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        Border border = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                "Code Memory View",
                TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION);
        panel.setBorder(border);
        JPanel innerPanel = new JPanel();
        innerPanel.setLayout(new BorderLayout());
        JPanel numPanel = new JPanel();
        JPanel decimalPanel = new JPanel();
        JPanel hexPanel = new JPanel();
        numPanel.setLayout(new GridLayout(0,1));
        decimalPanel.setLayout(new GridLayout(0,1));
        hexPanel.setLayout(new GridLayout(0,1));
        innerPanel.add(numPanel, BorderLayout.LINE_START);
        innerPanel.add(decimalPanel, BorderLayout.CENTER);
        innerPanel.add(hexPanel, BorderLayout.LINE_END);
        for(int i = 0; i <Memory.CODE_MAX/2; i++){
            numPanel.add(new JLabel(i+": ", JLabel.RIGHT));
            codeDecimal[i] = new JTextField(10);
            codeHex[i] = new JTextField(10);
            decimalPanel.add(codeDecimal[i]);
            hexPanel.add(codeHex[i]);
        }
        scroller = new JScrollPane(innerPanel);
        panel.add(scroller);
        return panel;
    }



}
