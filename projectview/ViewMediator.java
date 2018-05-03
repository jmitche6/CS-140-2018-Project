package projectview;
import project.MachineModel;
import java.util.Observable;

import javax.swing.JFrame;

public class ViewMediator implements Observable {
    private MachineModel model;
    private JFrame frame;
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

    }
    public void makeReady(String s){

    }

}
