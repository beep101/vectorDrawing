package ModifyInterface;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ValuePicker extends JPanel{
	private JTextField txtValue;
	private int defValue;
	public ValuePicker(String name,int value) {
		super();
		
		defValue=value;
		
		JLabel lblName = new JLabel(name);
		add(lblName);
		
		txtValue = new JTextField();
		if(value<0)
			txtValue.setText("");
		else
			txtValue.setText(Integer.toString(value));
		add(txtValue);
		txtValue.setColumns(10);
	}
	
	public int getValue() {
		try {
			return Integer.parseInt(txtValue.getText());
		}
		catch(NumberFormatException e) {
			return defValue;
		}
	}

}
