package Start;
import java.awt.Dimension;

import controller.Controller;
import model.Model;
import view.Frame;
import view.View;

public class Start {
	public static void main(String[] args) {
		Model model=new Model();
		View view=new View(model);
		Frame frame=new Frame(view);
		Controller controller=new Controller(view, model, frame);
		frame.setController(controller);
		frame.start();
	}
}
