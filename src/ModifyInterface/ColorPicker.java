package ModifyInterface;

import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;

public class ColorPicker extends JPanel{
	private JButton btnColor;
	private String name;
	private Color color,defColor;
	
	public ColorPicker(String name,Color color) {
		super();
		this.color=color;
		this.defColor=color;
		this.name=name;
		JLabel lblName = new JLabel(name);
		add(lblName);
		
		btnColor = new JButton();
		btnColor.setBackground(color);
		add(btnColor);

		btnColor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				colorChooser();
			}
		});
	}
	
	public Color getColor() {
		return color;
	}
	
	public void colorChooser() {
		JColorChooser colorChooser = new JColorChooser();
		Color temp=colorChooser.showDialog(null, "Choose color for "+name, defColor);
		if(temp!=null) {
			color=temp;
			btnColor.setBackground(color);
		}
	}

}
