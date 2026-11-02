package xeadModeler.view;

/*
 * Copyright (c) 2014 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 *
 * This file is part of XEAD Modeler.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the XEAD Project nor the names of its contributors
 *       may be used to endorse or promote products derived from this software
 *       without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

import java.awt.*;

import javax.swing.*;

import xeadModeler.Modeler;
import xeadModeler.Modeler.DataflowLine;
import xeadModeler.Modeler.DataflowNode;
import xeadModeler.KanjiTextField;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

public class DialogDataflowNode extends JDialog {
	private static final long serialVersionUID = 1L;
	private static ResourceBundle res = ResourceBundle.getBundle("xeadModeler.Res");
	private JPanel panelMain = new JPanel();
	private JLabel jLabel1 = new JLabel();
	private KanjiTextField jTextFieldLabel = new KanjiTextField();
	private JLabel jLabel1Ext = new JLabel();
	private KanjiTextField jTextFieldLabelExt = new KanjiTextField();
	private JLabel jLabel2 = new JLabel();
	private JComboBox jComboBoxType = new JComboBox();
	private JTextField jTextFieldType = new JTextField();
	private JComboBox jComboBoxEventPos = new JComboBox();
	private JLabel jLabel4 = new JLabel();
	private JLabel jLabel5 = new JLabel();
	private JLabel jLabel6 = new JLabel();
	private JButton jButtonOK = new JButton();
	private JButton jButtonCancel = new JButton();
	private JSpinner jSpinnerSlideNumber = new JSpinner();
	private JScrollPane jScrollPaneDescriptions = new JScrollPane();
	private JTextArea jTextAreaDescriptions = new JTextArea();
	private boolean buttonOKIsPressed;
	private Modeler frame_;

	public DialogDataflowNode(Modeler frame, String title, boolean modal) {
		super(frame, title, modal);
		try {
			frame_ = frame;
			jbInit();
			pack();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	public DialogDataflowNode(Modeler frame) {
		this(frame, "", true);
	}

	private void jbInit() throws Exception {
		this.setModal(true);
		this.setResizable(false);
		panelMain.setLayout(null);
		panelMain.setPreferredSize(new Dimension(322, 303));
		panelMain.setBorder(BorderFactory.createEtchedBorder());
		jLabel1.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel1.setText(res.getString("DialogDataflowNode01"));
		jLabel1.setBounds(new Rectangle(5, 12, 130, 20));
		jTextFieldLabel.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jTextFieldLabel.setBounds(new Rectangle(140, 9, 130, 25));
		jLabel1Ext.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jLabel1Ext.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel1Ext.setText(res.getString("DialogDataflowNode24"));
		jLabel1Ext.setBounds(new Rectangle(5, 43, 130, 20));
		jTextFieldLabelExt.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jTextFieldLabelExt.setBounds(new Rectangle(140, 40, 130, 25));
		jLabel2.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel2.setText(res.getString("DialogDataflowNode02"));
		jLabel2.setBounds(new Rectangle(5, 74, 130, 20));
		jComboBoxType.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jComboBoxType.setBounds(new Rectangle(140, 71, 130, 25));
		jComboBoxType.addItem(res.getString("DialogDataflowNode03")); //:0
		jComboBoxType.addItem(res.getString("DialogDataflowNode19")); //:1
		jComboBoxType.addItem(res.getString("DialogDataflowNode04")); //:2
		jComboBoxType.addItem(res.getString("DialogDataflowNode05")); //:3
		jComboBoxType.addItem(res.getString("DialogDataflowNode06")); //:4
		jComboBoxType.addItem(res.getString("DialogDataflowNode07")); //:5
		jComboBoxType.addItem(res.getString("DialogDataflowNode08")); //:6
		jComboBoxType.addItem(res.getString("DialogDataflowNode09")); //:7
		jComboBoxType.addItem(res.getString("DialogDataflowNode10")); //:8
		jComboBoxType.addItem(res.getString("DialogDataflowNode11")); //:9
		jComboBoxType.addItem(res.getString("DialogDataflowNode18")); //:10
		jComboBoxType.setMaximumRowCount(20);
		jTextFieldType.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jTextFieldType.setBounds(new Rectangle(140, 71, 130, 25));
		jTextFieldType.setEditable(false);
		jLabel4.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jLabel4.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel4.setText(res.getString("DialogDataflowNode12"));
		jLabel4.setBounds(new Rectangle(5, 105, 130, 20));
		jLabel6.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jLabel6.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel6.setText(res.getString("DialogDataflowNode21"));
		jLabel6.setBounds(new Rectangle(5, 136, 130, 20));
		jComboBoxEventPos.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jComboBoxEventPos.setBounds(new Rectangle(140, 133, 70, 25));
		jLabel5.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jLabel5.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel5.setText(res.getString("DialogDataflowNode20"));
		jLabel5.setBounds(new Rectangle(5, 167, 130, 20));
		jTextAreaDescriptions.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jTextAreaDescriptions.setLineWrap(true);
		jScrollPaneDescriptions.getViewport().add(jTextAreaDescriptions, null);
		jScrollPaneDescriptions.setBounds(new Rectangle(140, 164, 170, 90));
		jButtonOK.setBounds(new Rectangle(40, 265, 110, 27));
		jButtonOK.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jButtonOK.setText("OK");
		jButtonOK.addActionListener(new DialogDataflowNode_jButtonOK_actionAdapter(this));
		jButtonCancel.setBounds(new Rectangle(179, 265, 110, 27));
		jButtonCancel.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jButtonCancel.setText(res.getString("DialogDataflowNode13"));
		jButtonCancel.addActionListener(new DialogDataflowNode_jButtonCancel_actionAdapter(this));
		getContentPane().add(panelMain);
	}

	public boolean request(String action, xeadModeler.model.json.DataflowNode dataflowNode, ArrayList<DataflowNode> nodeArray, ArrayList<DataflowLine> lineArray) {
		buttonOKIsPressed = false;
		//
		jComboBoxEventPos.setEnabled(false);
		jComboBoxEventPos.removeAllItems();
		//
		if (dataflowNode.getType().equals("Process")) {
			jTextFieldType.setText(res.getString("DialogDataflowNode14"));
			jLabel6.setEnabled(true);
			jComboBoxEventPos.setEnabled(true);
			jComboBoxEventPos.addItem(res.getString("DialogDataflowNode22")); //:0
			jComboBoxEventPos.addItem(res.getString("DialogDataflowNode23")); //:0
		} else {
			jLabel6.setEnabled(false);
		}
		if (dataflowNode.getType().equals("Ledger")) {
			jTextFieldType.setText(res.getString("DialogDataflowNode03"));
		}
		if (dataflowNode.getType().equals("Drum")) {
			jTextFieldType.setText(res.getString("DialogDataflowNode19"));
		}
		if (dataflowNode.getType().equals("Store1")) {
			jTextFieldType.setText(res.getString("DialogDataflowNode04"));
		}
		if (dataflowNode.getType().equals("Store2")) {
			jTextFieldType.setText(res.getString("DialogDataflowNode05"));
		}
		if (dataflowNode.getType().equals("Tray")) {
			jTextFieldType.setText(res.getString("DialogDataflowNode06"));
		}
		if (dataflowNode.getType().equals("Folder")) {
			jTextFieldType.setText(res.getString("DialogDataflowNode07"));
		}
		if (dataflowNode.getType().equals("Subject")) {
			jTextFieldType.setText(res.getString("DialogDataflowNode08"));
		}
		if (dataflowNode.getType().equals("Casher")) {
			jTextFieldType.setText(res.getString("DialogDataflowNode09"));
		}
		if (dataflowNode.getType().equals("Safe")) {
			jTextFieldType.setText(res.getString("DialogDataflowNode10"));
		}
		if (dataflowNode.getType().equals("Sofa")) {
			jTextFieldType.setText(res.getString("DialogDataflowNode11"));
		}
		if (dataflowNode.getType().equals("Basket")) {
			jTextFieldType.setText(res.getString("DialogDataflowNode18"));
		}
		//
		panelMain.removeAll();
		panelMain.add(jLabel2, null);
		panelMain.add(jTextFieldLabel, null);
		panelMain.add(jLabel1, null);
		panelMain.add(jTextFieldLabelExt, null);
		panelMain.add(jLabel1Ext, null);
		panelMain.add(jLabel4, null);
		panelMain.add(jLabel6, null);
		panelMain.add(jComboBoxEventPos, null);
		panelMain.add(jLabel5, null);
		panelMain.add(jScrollPaneDescriptions, null);
		panelMain.add(jButtonOK, null);
		panelMain.add(jButtonCancel, null);
		panelMain.getRootPane().setDefaultButton(jButtonOK);
		//
		//Setup components according to action and type//
		if (action.equals("Add")) {
			this.setTitle(res.getString("DialogDataflowNode15"));
			jTextFieldLabel.setText(dataflowNode.getName());
			jTextFieldLabel.setBackground(Color.white);
			jTextFieldLabel.setEditable(true);
			jLabel1Ext.setEnabled(true);
			jTextFieldLabelExt.setEditable(true);
			jTextFieldLabelExt.setText("");
			jTextAreaDescriptions.setText("");
			jTextAreaDescriptions.setBackground(Color.white);
			jTextAreaDescriptions.setEditable(true);
			jComboBoxType.setSelectedIndex(0);
			panelMain.add(jComboBoxType, null);
		} else {
			this.setTitle(res.getString("DialogDataflowNode16"));
			panelMain.add(jTextFieldType, null);
			if (dataflowNode.getType().equals("Process")) {
				this.setTitle(res.getString("DialogDataflowNode17"));
				Modeler.XeadTreeNode node = frame_.getSpecificXeadTreeNode("Task", dataflowNode.getTaskID(), null);
				xeadModeler.model.json.Task task = (xeadModeler.model.json.Task) node.getPojo();
				jTextFieldLabel.setText(task.getName());
				jTextFieldLabel.setBackground(SystemColor.control);
				jTextFieldLabel.setEditable(false);
				jLabel1Ext.setEnabled(false);
				jTextFieldLabelExt.setText("");
				jTextFieldLabelExt.setBackground(SystemColor.control);
				jTextFieldLabelExt.setEditable(false);
				jTextAreaDescriptions.setText(Modeler.substringLinesWithTokenOfEOL(task.getDescriptions(), "\n"));
				jTextAreaDescriptions.setBackground(SystemColor.control);
				jTextAreaDescriptions.setEditable(false);
				String eventPos = dataflowNode.getEventPos();
				if (eventPos == null || eventPos.equals("")) {
					jComboBoxEventPos.setSelectedIndex(0);
				} else {
					jComboBoxEventPos.setSelectedIndex(1);
				}
			} else {
				String roleID = dataflowNode.getRoleID();
				if (roleID == null || roleID.equals("")) {
					jTextFieldLabel.setText(dataflowNode.getName());
					jTextFieldLabel.setBackground(Color.white);
					jTextFieldLabel.setEditable(true);
					jLabel1Ext.setEnabled(true);
					jTextFieldLabelExt.setText(dataflowNode.getNameExt());
					jTextFieldLabelExt.setBackground(Color.white);
					jTextFieldLabelExt.setEditable(true);
					jTextAreaDescriptions.setText(Modeler.substringLinesWithTokenOfEOL(dataflowNode.getDescriptions(), "\n"));
					jTextAreaDescriptions.setBackground(Color.white);
					jTextAreaDescriptions.setEditable(true);
				} else {
					Modeler.XeadTreeNode node = frame_.getSpecificXeadTreeNode("Role", roleID, null);
					xeadModeler.model.json.Role role = (xeadModeler.model.json.Role) node.getPojo();
					jTextFieldLabel.setText(role.getName());
					jTextFieldLabel.setBackground(SystemColor.control);
					jTextFieldLabel.setEditable(false);
					jLabel1Ext.setEnabled(false);
					jTextFieldLabelExt.setBackground(SystemColor.control);
					jTextFieldLabelExt.setEditable(false);
					jTextAreaDescriptions.setText(Modeler.substringLinesWithTokenOfEOL(role.getDescriptions(), "\n"));
					jTextAreaDescriptions.setBackground(SystemColor.control);
					jTextAreaDescriptions.setEditable(false);
				}
			}
		}
		jTextAreaDescriptions.setCaretPosition(0);
		//
		//Create spinner with specified default//
		int defaultValue = dataflowNode.getSlideNumber();
		SpinnerNumberModel spinnerNumberModel = new SpinnerNumberModel(defaultValue, 1, 30, 1);
		jSpinnerSlideNumber.setModel(spinnerNumberModel);
		JSpinner.NumberEditor spinnerEditor = new JSpinner.NumberEditor(jSpinnerSlideNumber, "00");
		jSpinnerSlideNumber.setEditor(spinnerEditor);
		jSpinnerSlideNumber.setBounds(new Rectangle(140, 102, 50, 25));
		jSpinnerSlideNumber.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		panelMain.add(jSpinnerSlideNumber, null);
		//
		//Setup dialog and show//
		Dimension dlgSize = this.getPreferredSize();
		Dimension frmSize = frame_.getSize();
		Point loc = frame_.getLocation();
		this.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
		this.pack();
		this.setVisible(true);
		//
		//Update and return parameter value//
		if (buttonOKIsPressed) {
			if (dataflowNode.getType().equals("Process")) {
				if (jComboBoxEventPos.getSelectedIndex() == 0) {
					dataflowNode.setEventPos("L");
				}
				if (jComboBoxEventPos.getSelectedIndex() == 1) {
					dataflowNode.setEventPos("R");
				}
			} else {
				//
				//Update JSON node//
				dataflowNode.setName(jTextFieldLabel.getText());
				dataflowNode.setNameExt(jTextFieldLabelExt.getText());
				dataflowNode.setDescriptions(Modeler.concatLinesWithTokenOfEOL(jTextAreaDescriptions.getText()));
				//
				if (action.equals("Add")) {
					if (jComboBoxType.getSelectedIndex() == 0) {
						dataflowNode.setType("Ledger");
					}
					if (jComboBoxType.getSelectedIndex() == 1) {
						dataflowNode.setType("Drum");
					}
					if (jComboBoxType.getSelectedIndex() == 2) {
						dataflowNode.setType("Store1");
					}
					if (jComboBoxType.getSelectedIndex() == 3) {
						dataflowNode.setType("Store2");
					}
					if (jComboBoxType.getSelectedIndex() == 4) {
						dataflowNode.setType("Tray");
					}
					if (jComboBoxType.getSelectedIndex() == 5) {
						dataflowNode.setType("Folder");
					}
					if (jComboBoxType.getSelectedIndex() == 6) {
						dataflowNode.setType("Subject");
					}
					if (jComboBoxType.getSelectedIndex() == 7) {
						dataflowNode.setType("Casher");
					}
					if (jComboBoxType.getSelectedIndex() == 8) {
						dataflowNode.setType("Safe");
					}
					if (jComboBoxType.getSelectedIndex() == 9) {
						dataflowNode.setType("Sofa");
					}
					if (jComboBoxType.getSelectedIndex() == 10) {
						dataflowNode.setType("Basket");
					}
				}
			}
			//
			int newSlideNumber = Integer.parseInt(jSpinnerSlideNumber.getValue().toString());
			int oldSlideNumber = dataflowNode.getSlideNumber();
			if (newSlideNumber != oldSlideNumber) {
				int diff = newSlideNumber - oldSlideNumber;
				int wrkInt;
				boolean isToCheck = false;
				for (int i = 0; i < nodeArray.size(); i++) {
					xeadModeler.model.json.DataflowNode pojo = nodeArray.get(i).getPojo();
					if (pojo != null) {
						wrkInt = pojo.getSlideNumber();
						if (wrkInt > oldSlideNumber) {
							isToCheck = true;
							break;
						}
					}
				}
				if (!isToCheck) {
					for (int i = 0; i < lineArray.size(); i++) {
						xeadModeler.model.json.DataflowLine pojoLine = lineArray.get(i).getPojo();
						if (pojoLine != null) {
							wrkInt = pojoLine.getSlideNumber();
							if (wrkInt > oldSlideNumber) {
								isToCheck = true;
								break;
							}
						}
					}
				}
				if (isToCheck) {
					int rtn = JOptionPane.showOptionDialog(this, res.getString("DialogDataflowLine34"),
							res.getString("DialogDataflowLine35"), JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, JOptionPane.YES_OPTION);
					if (rtn == JOptionPane.YES_OPTION) {
						for (int i = 0; i < nodeArray.size(); i++) {
							xeadModeler.model.json.DataflowNode pojo = nodeArray.get(i).getPojo();
							if (pojo != null) {
								wrkInt = pojo.getSlideNumber();
								if (wrkInt > oldSlideNumber) {
									wrkInt = wrkInt + diff;
									pojo.setSlideNumber(wrkInt);
								}
							}
						}
						for (int i = 0; i < lineArray.size(); i++) {
							xeadModeler.model.json.DataflowLine pojoLine = lineArray.get(i).getPojo();
							if (pojoLine != null) {
								wrkInt = pojoLine.getSlideNumber();
								if (wrkInt > oldSlideNumber) {
									wrkInt = wrkInt + diff;
									pojoLine.setSlideNumber(wrkInt);
								}
							}
						}
					}
				}
				dataflowNode.setSlideNumber(newSlideNumber);
			}
			//
			if (action.equals("Add")) {
				// Add to the current subject area's dataflow nodes list
				xeadModeler.model.json.SubjectArea subjectArea = (xeadModeler.model.json.SubjectArea) frame_.currentMainTreeNode.getPojo();
				subjectArea.getDataflowNodes().add(dataflowNode);
			}
		}
		return buttonOKIsPressed;
	}

	void jButtonOK_actionPerformed(ActionEvent e) {
		buttonOKIsPressed = true;
		this.setVisible(false);
	}

	void jButtonCancel_actionPerformed(ActionEvent e) {
		buttonOKIsPressed = false;
		this.setVisible(false);
	}
}

class DialogDataflowNode_jButtonOK_actionAdapter implements java.awt.event.ActionListener {
	DialogDataflowNode adaptee;

	DialogDataflowNode_jButtonOK_actionAdapter(DialogDataflowNode adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.jButtonOK_actionPerformed(e);
	}
}

class DialogDataflowNode_jButtonCancel_actionAdapter implements java.awt.event.ActionListener {
	DialogDataflowNode adaptee;

	DialogDataflowNode_jButtonCancel_actionAdapter(DialogDataflowNode adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.jButtonCancel_actionPerformed(e);
	}
}
