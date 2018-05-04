package projectview;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;
import project.MachineModel;
import project.Memory;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

@SuppressWarnings("deprecation")
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

	@Override
	public void update(Observable arg0, Object arg1) {
		if(arg1 != null && arg1.equals("Load Code")) {
			int offset = model.getCurrentJob().getStartcodeIndex();
			System.out.println("CODE SIZE " +  model.getCurrentJob().getCodeSize());
			for(int i = offset; 
					i < offset + model.getCurrentJob().getCodeSize(); i++) {
				System.out.println("CODE SIZE " +  model.getCurrentJob().getCodeSize());
				codeHex[i].setText(model.getHex(i));
				codeDecimal[i].setText(model.getDecimal(i));
			}	
			previousColor = model.getInstructionPointer();			
			codeHex[previousColor].setBackground(Color.YELLOW);
			codeDecimal[previousColor].setBackground(Color.YELLOW);
		} else if(arg1 != null && arg1.equals("Clear")) {
			int offset = model.getCurrentJob().getStartcodeIndex();
			for(int i = offset; 
					i < offset + model.getCurrentJob().getCodeSize(); i++) {
				if(model == null) {
					codeHex[i].setText("");
					codeDecimal[i].setText("");
				}
				else {
					codeHex[i].setText(model.getHex(i));
					codeDecimal[i].setText(model.getDecimal(i));
				}
			}	
			if(previousColor >= 0 && previousColor < Memory.CODE_MAX/2) {
				codeHex[previousColor].setBackground(Color.WHITE);
				codeDecimal[previousColor].setBackground(Color.WHITE);
			}
			previousColor = -1;
		}		
		if(this.previousColor >= 0 && previousColor < Memory.CODE_MAX/2) {
			codeHex[previousColor].setBackground(Color.WHITE);
			codeDecimal[previousColor].setBackground(Color.WHITE);
		}
		previousColor = model.getInstructionPointer();
		if(this.previousColor >= 0 && previousColor < Memory.CODE_MAX/2) {
			codeHex[previousColor].setBackground(Color.YELLOW);
			codeDecimal[previousColor].setBackground(Color.YELLOW);
		} 

		if(scroller != null && model != null && model!= null) {
			JScrollBar bar= scroller.getVerticalScrollBar();
			int pc = model.getInstructionPointer();
			//CHANGE
			if(pc > 0 && pc < Memory.CODE_MAX && codeHex[pc] != null) {
				Rectangle bounds = codeHex[pc].getBounds();
				bar.setValue(Math.max(0, bounds.y - 15*bounds.height));
			}
		}
	}

}
