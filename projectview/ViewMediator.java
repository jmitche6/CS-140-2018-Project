package projectview;
import project.MachineModel;
import javafx.beans.Observable;

import javax.swing.JFrame;

public class ViewMediator extends Observable {
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
}
