package ModifyInterface;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;

public class PickerDialog extends JDialog{
	private Flag flag;
	public PickerDialog(Flag flag,String headline) {
		setModal(true);
		setTitle(headline);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		this.flag=flag;
	}
	
	public void add(JPanel pane) {
		getContentPane().add(pane);
	}
	
	public void start() {
		JPanel panel=new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		JButton btnApply=new JButton("Apply");
		btnApply.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				finish();
			}
		});
		JButton btnCancel=new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		panel.add(btnApply);
		panel.add(btnCancel);
		add(panel);
		pack();
		setVisible(true);
	}
	
	public void restart() {
		setVisible(true);
	}
	
	public void finish() {
		for(int i=0;i<getContentPane().getComponents().length;i++)
			if(getContentPane().getComponent(i) instanceof ValuePicker)
				if(((ValuePicker)getContentPane().getComponent(i)).getValue()<0) {
					JOptionPane.showMessageDialog(this, "Value must be positive integer number", "Warning",JOptionPane.WARNING_MESSAGE);
					return;
				}
		flag.set(true);
		super.dispose();
	}
	
	@Override
	public void dispose() {
		flag.set(false);
		super.dispose();
	}

}
