package view;

import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import controller.Controller;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.JLabel;

public class Frame extends JFrame{
	private Controller controller;
	
	private View view;
	private JPanel panelButtons,panelLogger,panelLoad;
	private ButtonGroup modeButtons;
	private JButton btnLoad,
			btnSave,
			btnBorderColor,
			btnSurfaceColor,
			btnUndo,
			btnRedo,
			btnModify,
			btnDelete,
			btnForward,
			btnBackward,
			btnBringToFront,
			btnSendToBack,
			btnLoadNext,
			btnLoadPrevious,
			btnLoadAll,
			btnLoadNone,
			btnLoadDone;
	private JToggleButton btnSelect,
			btnPoint,
			btnLine,
			btnSquare,
			btnRectangle,
			btnCircle,
			btnHexagon;
	private JTextArea txtLogArea;
	
	public Frame(View viewRef) {
		//setting frame parameters
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("IT 27-2016 Pavlovic Rajko");
		getContentPane().setLayout(new BorderLayout(0, 0));
		setMinimumSize(new Dimension(1000,800));
		
		//setting view panel
		this.view=viewRef;
		getContentPane().add(view, BorderLayout.CENTER);
		
		//setting load panel
		panelLoad=new JPanel();
		panelLoad.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		btnLoadNone=new JButton("Load none"); 
		panelLoad.add(btnLoadNone);
		btnLoadPrevious=new JButton("Load previous");
		panelLoad.add(btnLoadPrevious);
		btnLoadNext=new JButton("Load next");
		panelLoad.add(btnLoadNext);
		btnLoadAll=new JButton("Load all");
		panelLoad.add(btnLoadAll);
		btnLoadDone=new JButton("Done");
		panelLoad.add(btnLoadDone);
		
		//setting buttons panel
		panelButtons = new JPanel();
		panelButtons.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		getContentPane().add(panelButtons, BorderLayout.NORTH);
		//load and save buttons
		btnLoad=new JButton("Load");
		panelButtons.add(btnLoad);
		btnSave=new JButton("Save");
		panelButtons.add(btnSave);
		panelButtons.add(new JSeparator());
		//mode selection buttons
		modeButtons=new ButtonGroup(); 
		btnSelect = new JToggleButton("Select");
		modeButtons.add(btnSelect);
		panelButtons.add(btnSelect);
		panelButtons.add(new JSeparator());	
		btnPoint = new JToggleButton("Point");
		modeButtons.add(btnPoint);
		panelButtons.add(btnPoint);
		btnLine = new JToggleButton("Line");
		modeButtons.add(btnLine);
		panelButtons.add(btnLine);
		panelButtons.add(new JSeparator());
		btnSquare = new JToggleButton("Square");
		modeButtons.add(btnSquare);
		panelButtons.add(btnSquare);
		btnRectangle = new JToggleButton("Rectangle");
		modeButtons.add(btnRectangle);
		panelButtons.add(btnRectangle);
		btnCircle = new JToggleButton("Circle");
		modeButtons.add(btnCircle);
		panelButtons.add(btnCircle);
		btnHexagon = new JToggleButton("Hexagon");
		modeButtons.add(btnHexagon);
		panelButtons.add(btnHexagon);
		panelButtons.add(new JSeparator());
		//color choosing buttons
		JLabel lblBorder = new JLabel("Border");
		panelButtons.add(lblBorder);
		btnBorderColor = new JButton("");
		panelButtons.add(btnBorderColor);
		JLabel lblSurface = new JLabel("Surface");
		panelButtons.add(lblSurface);
		btnSurfaceColor = new JButton("");
		panelButtons.add(btnSurfaceColor);
		panelButtons.add(new JSeparator());
		//undo redo buttons
		btnUndo = new JButton("Undo");
		panelButtons.add(btnUndo);
		btnRedo = new JButton("Redo");
		panelButtons.add(btnRedo);
		panelButtons.add(new JSeparator());
		//modify and delete buttons
		btnModify = new JButton("Modify");
		panelButtons.add(btnModify);
		btnDelete = new JButton("Delete");
		panelButtons.add(btnDelete);
		panelButtons.add(new JSeparator());
		//movment by z-axis
		btnSendToBack=new JButton("To back");
		panelButtons.add(btnSendToBack);
		btnBackward=new JButton("Backward");
		panelButtons.add(btnBackward);
		btnForward=new JButton("Forward");
		panelButtons.add(btnForward);
		btnBringToFront=new JButton("To front");
		panelButtons.add(btnBringToFront);
		
		//logging panel
		txtLogArea = new JTextArea(10,120);
		txtLogArea.setEditable(false);
		JScrollPane scroll=new JScrollPane(txtLogArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);		
		txtLogArea.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		panelLogger = new JPanel();
		panelLogger.add(scroll);
		getContentPane().add(panelLogger, BorderLayout.SOUTH);
		
		//setting listeners
		addListeners();
	}
	
	private void addListeners() {
		btnLoadNone.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.loadNone();
			}
		});
		btnLoadPrevious.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.loadPrevious();
			}
		});
		btnLoadNext.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.loadNext();
			}
		});
		btnLoadAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.loadAll();
			}
		});
		btnLoadDone.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.loadDone();
			}
		});

		btnLoad.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.load();
			}
		});
		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				controller.save();
			}
		});
		
		btnSelect.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.select();
			}
		});
		btnPoint.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.point();
			}
		});
		btnLine.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.line();
			}
		});
		btnSquare.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.square();
			}
		});
		btnRectangle.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.rectangle();
			}
		});
		btnCircle.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.circle();
			}
		});
		btnHexagon.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.hexagon();				
			}
		});
		
		
		btnBorderColor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.borderColor();
			}
		});
		btnSurfaceColor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.surfaceColor();
			}
		});
		
		
		btnUndo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.undo();
			}
		});
		btnRedo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.redo();
			}
		});
		
		
		btnModify.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.modify();
			}
		});
		btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.delete();
			}
		});
		
		
		btnSendToBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.sendToBack();
			}
		});
		btnBackward.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.backward();
			}
		});
		btnForward.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.forward();
			}
		});
		btnBringToFront.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.bringToFront();
			}
		});
		
				
		view.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				controller.clicked(e.getX(),e.getY());
			}
			@Override
			public void mousePressed(MouseEvent e) {}
			
			@Override
			public void mouseExited(MouseEvent e) {}
			
			@Override
			public void mouseEntered(MouseEvent e) {}
			
			@Override
			public void mouseClicked(MouseEvent e) {}
		});
	}
	
	public void start() {
		setVisible(true);
		pack();
	}
	
	public void showLoadButtons() {
		getContentPane().remove(panelButtons);
		getContentPane().add(panelLoad, BorderLayout.NORTH);
		setVisible(true);
		getContentPane().repaint();
	}
	
	public void hideLoadButtons() {
		getContentPane().remove(panelLoad);
		getContentPane().add(panelButtons, BorderLayout.NORTH);
		getContentPane().repaint();
	}
	
	public void enableUndo(boolean enable) {
		btnUndo.setEnabled(enable);
	}
	
	public void enableRedo(boolean enable) {
		btnRedo.setEnabled(enable);
	}
	
	public void enableModify(boolean enable) {
		btnModify.setEnabled(enable);
	}
	
	public void enableDelete(boolean enable) {
		btnDelete.setEnabled(enable);
	}
	
	public void setBorderColor(Color color) {
		btnBorderColor.setBackground(color);
	}
	
	public void setSurfaceColor(Color color) {
		btnSurfaceColor.setBackground(color);
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	public void disableMoveToButtons() {
		this.btnSendToBack.setEnabled(false);
		this.btnBackward.setEnabled(false);
		this.btnForward.setEnabled(false);
		this.btnBringToFront.setEnabled(false);
	}
	
	public void enableMoveForwardButtons() {
		this.btnForward.setEnabled(true);
		this.btnBringToFront.setEnabled(true);	
	}

	public void enableMoveBackwardButtons() {
		this.btnSendToBack.setEnabled(true);
		this.btnBackward.setEnabled(true);
	}
	
	public void adjustLoadButtons(boolean set) {
		this.btnLoadNext.setEnabled(set);
		this.btnLoadAll.setEnabled(set);
	}
	
	public void adjustUnloadButtons(boolean set) {
		this.btnLoadPrevious.setEnabled(set);
		this.btnLoadNone.setEnabled(set);
	}
	
	public void setLogText(String text){
		txtLogArea.setText(text);
	}
	
	public void btnSelectSetSelected(boolean check) {
		btnSelect.setSelected(check);
	}
}
