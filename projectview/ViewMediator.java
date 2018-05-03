package projectview;
import project.MachineModel;
<<<<<<< HEAD
import java.util.Observable;
=======
import project.Memory;
>>>>>>> 9e545dde46d8af40bda079934533e322f7596b8c

import java.awt.*;
import java.util.Observable;

import javax.swing.*;

public class ViewMediator extends Observable {
    private MachineModel model;
    private CodeViewPanel codeViewPanel;
    private MemoryViewPanel memoryViewPanel1;
    private MemoryViewPanel memoryViewPanel2;
    private MemoryViewPanel memoryViewPanel3;
    //private ControlPanel controlPanel;
//private ProcessorViewPanel processorPanel;
//private MenuBarBuilder menuBuilder;
    private JFrame frame;
    private FilesManager filesManager;
    private Animator animator;
    private void createAndShowGUI(){
        animator = new Animator(this);
        filesManager = new FilesManager(this);
        codeViewPanel = new CodeViewPanel(this, model);
        memoryViewPanel1 = new MemoryViewPanel(this, model, 0, 240);
        memoryViewPanel2 = new MemoryViewPanel(this, model, 240, Memory.DATA_SIZE/2);
        memoryViewPanel3 = new MemoryViewPanel(this, model, Memory.DATA_SIZE/2, Memory.DATA_SIZE);
        //controlPanel = new ControlPanel(this);
        //processorPanel = new ProcessorPanel(this,model);
        //menuBuilder = new MenuBarBuilder(this);
        frame = new JFrame("Simulator");
        Container content = frame.getContentPane();
        content.setLayout(new BorderLayout(1,1));
        content.setBackground(Color.BLACK);
        frame.setSize(1200, 600);
        JPanel center = new JPanel();
        center.setLayout(new GridLayout(1, 3));
        frame.add(codeViewPanel.createCodeDisplay(),BorderLayout.LINE_START);
        center.add(memoryViewPanel1.createMemoryDisplay(), memoryViewPanel2.createMemoryDisplay(), memoryViewPanel3.createMemoryDisplay());
        frame.add(center);
        //TODO return HERE for the other GUI components.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //TODO return HERE for other setup details
        frame.setVisible(true);
    }
    public States getCurrentState(){
        return model.getCurrentState;
    }
    public void setCurrentState(States currentState){
        if(currentState == States.PROGRAM_HALTED){
            animator.setAutoStepOn(false);
        }
        model.setCurrentState(currentState);
        model.getCurrentState().enter();
        setChanged();
        notifyObservers();
    }
    public void exit() { // method executed when user exits the program
        int decision = JOptionPane.showConfirmDialog(
                frame, "Do you really wish to exit?",
                "Confirmation", JOptionPane.YES_NO_OPTION);
        if (decision == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
    public void step() {

    }

    public JFrame getFrame() {
        return frame;
    }

    public MachineModel getModel() {
        return model;
    }

    public void setModel(MachineModel model) {
        this.model = model;
    }
    public void clearJob(){
        model.clearJob();
        model.setCurrentState(States.NOTHING_LOADED);
        model.getCurrentState().enter();
        setChanged();
    }
    public void makeReady(String s){

    }
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ViewMediator mediator = new ViewMediator();
                MachineModel model = new MachineModel(
                        //true,
                        //() -> mediator.setCurrentState(States.PROGRAM_HALTED)
                );
                mediator.setModel(model);
                mediator.createAndShowGUI();
            }
        });
    }


}
