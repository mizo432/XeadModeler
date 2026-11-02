package xeadModeler.view;

/*
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
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

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import xeadModeler.Modeler;
import xeadModeler.model.json.*;

public class DialogImportXEAD extends JDialog {
	private static final long serialVersionUID = 1L;
	private static ResourceBundle res = ResourceBundle.getBundle("xeadModeler.Res");

	private JPanel panelMain = new JPanel();
	private JLabel jLabel1 = new JLabel();
	private JLabel jLabel2 = new JLabel();
	private JPanel jPanel1 = new JPanel();
	private GridLayout gridLayout1 = new GridLayout();
	private JRadioButton jRadioButtonTablesAndFunctions = new JRadioButton();
	private JRadioButton jRadioButtonTables = new JRadioButton();
	private JRadioButton jRadioButtonFunctions = new JRadioButton();
	private JRadioButton jRadioButtonTasks = new JRadioButton();
	private JRadioButton jRadioButtonDataflow = new JRadioButton();
	private ButtonGroup buttonGroup1 = new ButtonGroup();
	private JLabel jLabel3 = new JLabel();
	private JProgressBar jProgressBar = new JProgressBar();
	private JButton jButtonStart = new JButton();
	private JButton jButtonCancel = new JButton();
	private SortableXeadNodeComboBoxModel comboBoxModelBlockFrom = new SortableXeadNodeComboBoxModel();
	private SortableXeadNodeComboBoxModel comboBoxModelBlockInto = new SortableXeadNodeComboBoxModel();
	private JComboBox jComboBoxBlockFrom = new JComboBox(comboBoxModelBlockFrom);
	private JComboBox jComboBoxBlockInto = new JComboBox(comboBoxModelBlockInto);
	private JTextArea jTextArea1 = new JTextArea();
	private JLabel jLabel4 = new JLabel();
	private JTextField jTextFieldImportFileName = new JTextField();
	private JLabel jLabel5 = new JLabel();
	private JTextField jTextFieldImportSystemName = new JTextField();

	private String importResult = "";
	private Modeler frame_;
	private XeadSystem xeadSystemFrom;
	private Subsystem blockSubsystemFrom;
	private Subsystem blockSubsystemInto;
	private Role blockRoleInto;
	private SubjectArea blockSubjectAreaFrom;
	private SubjectArea blockSubjectAreaInto;
	private String blockIDFrom, blockIDInto;
	private FileWriter fileWriter = null;
	private BufferedWriter bufferedWriter = null;

	private int updateTableCounter, createTableCounter, cancelTableCounter;
	private int updateFunctionCounter, createFunctionCounter, cancelFunctionCounter;
	private int updateTaskCounter, createTaskCounter, cancelTaskCounter, cancelRoleCounter;
	private int missingTableCounter, missingTableKeyCounter, missingFunctionCounter, missingFunctionIOCounter;

	public DialogImportXEAD(Modeler frame, String title, boolean modal) {
		super(frame, title, modal);
		try {
			frame_ = frame;
			jbInit();
			pack();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public DialogImportXEAD(Modeler frame) {
		this(frame, "", true);
	}

	private void jbInit() throws Exception {
		this.setResizable(false);
		this.setTitle(res.getString("DialogImportXEAD01"));

		jLabel4.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jLabel4.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel4.setText(res.getString("DialogImportXEAD02"));
		jLabel4.setBounds(new Rectangle(5, 12, 170, 20));
		jTextFieldImportFileName.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jTextFieldImportFileName.setBounds(new Rectangle(180, 9, 350, 25));
		jTextFieldImportFileName.setEditable(false);

		jLabel5.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jLabel5.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel5.setText(res.getString("DialogImportXEAD03"));
		jLabel5.setBounds(new Rectangle(5, 43, 170, 20));
		jTextFieldImportSystemName.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jTextFieldImportSystemName.setBounds(new Rectangle(180, 40, 350, 25));
		jTextFieldImportSystemName.setEditable(false);

		jLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel3.setText(res.getString("DialogImportXEAD04"));
		jLabel3.setBounds(new Rectangle(5, 74, 170, 20));
		jLabel3.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jPanel1.setBorder(BorderFactory.createEtchedBorder());
		jPanel1.setBounds(new Rectangle(180, 71, 350, 120));
		jPanel1.setLayout(gridLayout1);
		gridLayout1.setColumns(1);
		gridLayout1.setRows(5);
		jRadioButtonTablesAndFunctions.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jRadioButtonTablesAndFunctions.setText(res.getString("DialogImportXEAD05"));
		jRadioButtonTablesAndFunctions.addItemListener(new DialogImportXEAD_jRadioButton_itemAdapter(this));
		jRadioButtonTables.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jRadioButtonTables.setText(res.getString("DialogImportXEAD46"));
		jRadioButtonTables.addItemListener(new DialogImportXEAD_jRadioButton_itemAdapter(this));
		jRadioButtonFunctions.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jRadioButtonFunctions.setText(res.getString("DialogImportXEAD44"));
		jRadioButtonFunctions.addItemListener(new DialogImportXEAD_jRadioButton_itemAdapter(this));
		jRadioButtonTasks.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jRadioButtonTasks.setText(res.getString("DialogImportXEAD06"));
		jRadioButtonTasks.addItemListener(new DialogImportXEAD_jRadioButton_itemAdapter(this));
		jRadioButtonDataflow.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jRadioButtonDataflow.setText(res.getString("S3385"));
		jRadioButtonDataflow.addItemListener(new DialogImportXEAD_jRadioButton_itemAdapter(this));
		jPanel1.add(jRadioButtonFunctions, null);
		jPanel1.add(jRadioButtonTables, null);
		jPanel1.add(jRadioButtonTablesAndFunctions, null);
		jPanel1.add(jRadioButtonTasks, null);
		jPanel1.add(jRadioButtonDataflow, null);
		buttonGroup1.add(jRadioButtonFunctions);
		buttonGroup1.add(jRadioButtonTables);
		buttonGroup1.add(jRadioButtonTablesAndFunctions);
		buttonGroup1.add(jRadioButtonTasks);
		buttonGroup1.add(jRadioButtonDataflow);

		jLabel1.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel1.setBounds(new Rectangle(5, 194, 170, 20));
		jComboBoxBlockFrom.setBounds(new Rectangle(180, 191, 350, 25));
		jComboBoxBlockFrom.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jComboBoxBlockFrom.addActionListener(new DialogImportXEAD_jComboBoxBlockFrom_actionAdapter(this));

		jLabel2.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel2.setBounds(new Rectangle(5, 225, 170, 20));
		jComboBoxBlockInto.setBounds(new Rectangle(180, 222, 350, 25));
		jComboBoxBlockInto.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		//jComboBoxBlockInto.addActionListener(new DialogImportXEAD_jComboBoxBlockInto_actionAdapter(this));

		jTextArea1.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jTextArea1.setForeground(Color.BLUE);
		jTextArea1.setEditable(false);
		jTextArea1.setBounds(new Rectangle(9, 253, 522, 173));
		jTextArea1.setLineWrap(true);
		jTextArea1.setBackground(SystemColor.control);
		jTextArea1.setBorder(BorderFactory.createLoweredBevelBorder());

		jProgressBar.setBounds(new Rectangle(30, 440, 340, 27));
		jProgressBar.setStringPainted(true);
		jProgressBar.setVisible(false);

		jButtonStart.setBounds(new Rectangle(30, 440, 340, 27));
		jButtonStart.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jButtonStart.setText(res.getString("DialogImportXEAD07"));
		jButtonStart.addActionListener(new DialogImportXEAD_jButtonStart_actionAdapter(this));
		jButtonStart.setEnabled(false);

		jButtonCancel.setBounds(new Rectangle(400, 440, 110, 27));
		jButtonCancel.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jButtonCancel.setText(res.getString("DialogImportXEAD08"));
		jButtonCancel.addActionListener(new DialogImportXEAD_jButtonCancel_actionAdapter(this));

		panelMain.setLayout(null);
		panelMain.setPreferredSize(new Dimension(540, 480));
		panelMain.setBorder(BorderFactory.createEtchedBorder());
		panelMain.add(jLabel1, null);
		panelMain.add(jLabel2, null);
		panelMain.add(jLabel3, null);
		panelMain.add(jLabel4, null);
		panelMain.add(jLabel5, null);
		panelMain.add(jPanel1, null);
		panelMain.add(jTextFieldImportFileName, null);
		panelMain.add(jTextFieldImportSystemName, null);
		panelMain.add(jComboBoxBlockFrom, null);
		panelMain.add(jComboBoxBlockInto, null);
		panelMain.add(jTextArea1, null);
		panelMain.add(jProgressBar, null);
		panelMain.add(jButtonStart, null);
		panelMain.add(jButtonCancel, null);

		this.getContentPane().add(panelMain, BorderLayout.SOUTH);

		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension dlgSize = this.getPreferredSize();
		this.setLocation((scrSize.width - dlgSize.width)/2 , (scrSize.height - dlgSize.height)/2);
		this.pack();
	}

	public String request(String fileName) {
		importResult = "";

		xeadSystemFrom = null;
		jRadioButtonFunctions.setSelected(true);

		try {
			xeadSystemFrom = frame_.getXeadDocumentModel().getJsonService().loadSystem(fileName);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		if (xeadSystemFrom == null) {
			return importResult;
		}

		float importFileFormat = Float.parseFloat(xeadSystemFrom.getFormatVersion());
		float appliFormat = Float.parseFloat(DialogAbout.FORMAT_VERSION);
		if (importFileFormat > appliFormat) {
			JOptionPane.showMessageDialog(this, res.getString("S1") + xeadSystemFrom.getFormatVersion() + res.getString("S2") + DialogAbout.FORMAT_VERSION + res.getString("S3"));
		} else {
			jTextFieldImportFileName.setText(fileName);
			jTextFieldImportSystemName.setText(xeadSystemFrom.getName());
			jRadioButton_itemStateChanged();
			jProgressBar.setValue(0);
			super.setVisible(true);
		}

		return importResult;
	}

	void jRadioButton_itemStateChanged() {
		try {
			setCursor(new Cursor(Cursor.WAIT_CURSOR));
			if (xeadSystemFrom != null) {

				if (jRadioButtonTables.isSelected() || jRadioButtonFunctions.isSelected() || jRadioButtonTablesAndFunctions.isSelected()) {

					jLabel1.setText(res.getString("DialogImportXEAD09"));
					jLabel2.setText(res.getString("DialogImportXEAD10"));
					if (jRadioButtonTables.isSelected()) {
						jTextArea1.setText(res.getString("DialogImportXEAD47"));
					}
					if (jRadioButtonFunctions.isSelected()) {
						jTextArea1.setText(res.getString("DialogImportXEAD45"));
					}
					if (jRadioButtonTablesAndFunctions.isSelected()) {
						jTextArea1.setText(res.getString("DialogImportXEAD11"));
					}

					///////////////////
					//Setup ComboBox1//
					///////////////////
					comboBoxModelBlockFrom.removeAllElements();
					List<Subsystem> subsystemsFrom = xeadSystemFrom.getSubsystems();
					if (subsystemsFrom != null) {
						for (Subsystem subsystem : subsystemsFrom) {
							comboBoxModelBlockFrom.addElement(new XeadNodeFromSubsystem(subsystem));
						}
					}
					comboBoxModelBlockFrom.sortElements();
					comboBoxModelBlockFrom.insertElementAt(res.getString("DialogImportXEAD12"), 0);
					comboBoxModelBlockFrom.setSelectedItem(comboBoxModelBlockFrom.getElementAt(0));

				///////////////////
				//Setup ComboBox2//
				///////////////////
				comboBoxModelBlockInto.removeAllElements();
				XeadSystem xeadSystem = frame_.getXeadDocumentModel().getXeadSystem();
				List<Subsystem> subsystems = xeadSystem.getSubsystems();
				if (subsystems != null) {
					for (Subsystem subsystem : subsystems) {
						comboBoxModelBlockInto.addElement(new XeadNodeFromSubsystem(subsystem));
					}
				}
				comboBoxModelBlockInto.sortElements();
				//comboBoxModelBlockInto.insertElementAt(res.getString("DialogImportXEAD12"), 0);
				comboBoxModelBlockInto.insertElementAt("*New", 0);
				comboBoxModelBlockInto.setSelectedItem(comboBoxModelBlockInto.getElementAt(0));
			}

				if (jRadioButtonTasks.isSelected()) {

					jLabel1.setText(res.getString("DialogImportXEAD13"));
					jLabel2.setText(res.getString("DialogImportXEAD14"));
					jTextArea1.setText(res.getString("DialogImportXEAD15"));

					///////////////////
					//Setup ComboBox1//
					///////////////////
					comboBoxModelBlockFrom.removeAllElements();
					List<Role> rolesFrom = xeadSystemFrom.getRoles();
					if (rolesFrom != null) {
						for (Role role : rolesFrom) {
							comboBoxModelBlockFrom.addElement(new XeadNodeFromRole(role));
						}
					}
					comboBoxModelBlockFrom.sortElements();
					comboBoxModelBlockFrom.insertElementAt(res.getString("DialogImportXEAD12"), 0);
					comboBoxModelBlockFrom.setSelectedItem(comboBoxModelBlockFrom.getElementAt(0));

				///////////////////
				//Setup ComboBox2//
				///////////////////
				comboBoxModelBlockInto.removeAllElements();
				XeadSystem xeadSystem = frame_.getXeadDocumentModel().getXeadSystem();
				List<Role> roles = xeadSystem.getRoles();
				if (roles != null) {
					for (Role role : roles) {
						comboBoxModelBlockInto.addElement(new XeadNodeFromRole(role));
					}
				}
				comboBoxModelBlockInto.sortElements();
				//comboBoxModelBlockInto.insertElementAt(res.getString("DialogImportXEAD12"), 0);
				comboBoxModelBlockInto.insertElementAt("*New", 0);
				comboBoxModelBlockInto.setSelectedItem(comboBoxModelBlockInto.getElementAt(0));
			}

				if (jRadioButtonDataflow.isSelected()) {

					jLabel1.setText(res.getString("DialogImportXEAD52"));
					jLabel2.setText(res.getString("DialogImportXEAD53"));
					jTextArea1.setText(res.getString("DialogImportXEAD51"));

					///////////////////
					//Setup ComboBox1//
					///////////////////
					comboBoxModelBlockFrom.removeAllElements();
					List<SubjectArea> subjectAreasFrom = xeadSystemFrom.getSubjectAreas();
					if (subjectAreasFrom != null) {
						for (SubjectArea subjectArea : subjectAreasFrom) {
							comboBoxModelBlockFrom.addElement(new XeadNodeFromSubjectArea(subjectArea));
						}
					}
					comboBoxModelBlockFrom.sortElements();
					comboBoxModelBlockFrom.insertElementAt(res.getString("DialogImportXEAD12"), 0);
					comboBoxModelBlockFrom.setSelectedItem(comboBoxModelBlockFrom.getElementAt(0));

				///////////////////
				//Setup ComboBox2//
				///////////////////
				comboBoxModelBlockInto.removeAllElements();
				XeadSystem xeadSystem = frame_.getXeadDocumentModel().getXeadSystem();
				List<SubjectArea> subjectAreas = xeadSystem.getSubjectAreas();
				if (subjectAreas != null) {
					for (SubjectArea subjectArea : subjectAreas) {
						comboBoxModelBlockInto.addElement(new XeadNodeFromSubjectArea(subjectArea));
					}
				}
				comboBoxModelBlockInto.sortElements();
				//comboBoxModelBlockInto.insertElementAt(res.getString("DialogImportXEAD12"), 0);
				comboBoxModelBlockInto.insertElementAt("*New", 0);
				comboBoxModelBlockInto.setSelectedItem(comboBoxModelBlockInto.getElementAt(0));
			}
		}
	} finally {
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
	}
	
	void jComboBoxBlockFrom_actionPerformed(ActionEvent e) {
		if (jComboBoxBlockFrom.getSelectedIndex() > 0) {
			String value = jComboBoxBlockFrom.getItemAt(jComboBoxBlockFrom.getSelectedIndex()).toString();
			for (int i = 0; i < jComboBoxBlockInto.getItemCount(); i++) {
				if (jComboBoxBlockInto.getItemAt(i).toString().equals(value)) {
					jComboBoxBlockInto.setSelectedIndex(i);
					break;
				}
			}
			jButtonStart.setEnabled(true);
		} else {
			jButtonStart.setEnabled(false);
		}
	}
	
//	void jComboBoxBlockInto_actionPerformed(ActionEvent e) {
//		if (jComboBoxBlockFrom.getSelectedIndex() > 0 && jComboBoxBlockInto.getSelectedIndex() > 0) {
//			jButtonStart.setEnabled(true);
//		} else {
//			jButtonStart.setEnabled(false);
//		}
//	}

	void jButtonStart_actionPerformed(ActionEvent e) {
		String logFileName = "";
		int lastID = 0;
		XeadSystem xeadSystem = frame_.getXeadDocumentModel().getXeadSystem();

		try {
			setCursor(new Cursor(Cursor.WAIT_CURSOR));
			jProgressBar.setVisible(true);
			jButtonStart.setVisible(false);

			String dateTime = getStringValueOfDateAndTime();
			File file = new File(frame_.currentFileName);
			logFileName = file.getParent() + File.separator + "XeadImportLog" + dateTime + ".txt";
			fileWriter = new FileWriter(logFileName);
			bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(res.getString("DialogImportXEAD01") + "(" + dateTime + ")" + "\n");
			bufferedWriter.write("\n");
			bufferedWriter.write(res.getString("DialogImportXEAD02") + ":" + jTextFieldImportFileName.getText() + "\n");
			bufferedWriter.write(res.getString("DialogImportXEAD31") + ":" + frame_.currentFileName + res.getString("DialogImportXEAD41") + "\n");
			bufferedWriter.write("\n");
			if (jRadioButtonTables.isSelected()) {
				bufferedWriter.write(res.getString("DialogImportXEAD04") + ":" + res.getString("DialogImportXEAD46") + "\n");
			}
			if (jRadioButtonFunctions.isSelected()) {
				bufferedWriter.write(res.getString("DialogImportXEAD04") + ":" + res.getString("DialogImportXEAD44") + "\n");
			}
			if (jRadioButtonTablesAndFunctions.isSelected()) {
				bufferedWriter.write(res.getString("DialogImportXEAD04") + ":" + res.getString("DialogImportXEAD05") + "\n");
			}
			if (jRadioButtonTasks.isSelected()) {
				bufferedWriter.write(res.getString("DialogImportXEAD04") + ":" + res.getString("DialogImportXEAD06") + "\n");
			}
			if (jRadioButtonDataflow.isSelected()) {
				bufferedWriter.write(res.getString("DialogImportXEAD04") + ":" + res.getString("S3385") + "\n");
			}
			bufferedWriter.write(res.getString("DialogImportXEAD03") + ":" + jTextFieldImportSystemName.getText() + "\n");
			bufferedWriter.write(res.getString("DialogImportXEAD32") + ":" + frame_.systemName + "\n");
			bufferedWriter.write("\n");

			updateTableCounter = 0;
			createTableCounter = 0;
			cancelTableCounter = 0;
			updateFunctionCounter = 0;
			createFunctionCounter = 0;
			cancelFunctionCounter = 0;
			updateTaskCounter = 0;
			createTaskCounter = 0;
			cancelTaskCounter = 0;
			cancelRoleCounter = 0;
			missingTableCounter = 0;
			missingFunctionCounter = 0;
			missingFunctionIOCounter = 0;

			// Get source block from combo box (now using JSON wrappers)
			Object selectedFrom = comboBoxModelBlockFrom.getSelectedItem();
			String sourceName, sourceSortKey;
			if (selectedFrom instanceof XeadNodeFromSubsystem) {
				blockSubsystemFrom = ((XeadNodeFromSubsystem)selectedFrom).getSubsystem();
				blockIDFrom = blockSubsystemFrom.getId();
				sourceName = blockSubsystemFrom.getName();
				sourceSortKey = blockSubsystemFrom.getSortKey();
			} else if (selectedFrom instanceof XeadNodeFromRole) {
				Role blockRoleFrom = ((XeadNodeFromRole)selectedFrom).getRole();
				blockIDFrom = blockRoleFrom.getId();
				sourceName = blockRoleFrom.getName();
				sourceSortKey = blockRoleFrom.getSortKey();
			} else if (selectedFrom instanceof XeadNodeFromSubjectArea) {
				blockSubjectAreaFrom = ((XeadNodeFromSubjectArea)selectedFrom).getSubjectArea();
				blockIDFrom = blockSubjectAreaFrom.getId();
				sourceName = blockSubjectAreaFrom.getName();
				sourceSortKey = blockSubjectAreaFrom.getSortKey();
			} else {
				sourceName = "";
				sourceSortKey = "";
			}
			bufferedWriter.write(jLabel1.getText() + ":" + sourceName + "(" + sourceSortKey + ")" + "\n");

			//////////////////////////////
			// Setup the target element //
			//////////////////////////////
			if (jComboBoxBlockInto.getSelectedIndex() == 0) {
				if (jRadioButtonTables.isSelected()
						|| jRadioButtonFunctions.isSelected()
						|| jRadioButtonTablesAndFunctions.isSelected()) {
					blockSubsystemInto = new Subsystem();
					lastID = getLastIDOfTheType("Subsystem");
					blockSubsystemInto.setId(Integer.toString(lastID + 1));
					xeadSystem.getSubsystems().add(blockSubsystemInto);
				}
				if (jRadioButtonTasks.isSelected()) {
					blockRoleInto = new Role();
					lastID = getLastIDOfTheType("Role");
					blockRoleInto.setId(Integer.toString(lastID + 1));
					xeadSystem.getRoles().add(blockRoleInto);
				}
				if (jRadioButtonDataflow.isSelected()) {
					blockSubjectAreaInto = new SubjectArea();
					lastID = getLastIDOfTheType("SubjectArea");
					blockSubjectAreaInto.setId(Integer.toString(lastID + 1));
					xeadSystem.getSubjectAreas().add(blockSubjectAreaInto);
				}
				bufferedWriter.write(jLabel2.getText() + ":*New\n");
			} else {
				Object selectedItem = comboBoxModelBlockInto.getSelectedItem();
				if (selectedItem instanceof XeadNodeFromSubsystem) {
					blockSubsystemInto = ((XeadNodeFromSubsystem)selectedItem).getSubsystem();
				} else if (selectedItem instanceof XeadNodeFromRole) {
					blockRoleInto = ((XeadNodeFromRole)selectedItem).getRole();
				} else if (selectedItem instanceof XeadNodeFromSubjectArea) {
					blockSubjectAreaInto = ((XeadNodeFromSubjectArea)selectedItem).getSubjectArea();
				}
				String targetName = blockSubsystemInto != null ? blockSubsystemInto.getName() :
					blockRoleInto != null ? blockRoleInto.getName() : blockSubjectAreaInto.getName();
				String targetSortKey = blockSubsystemInto != null ? blockSubsystemInto.getSortKey() :
					blockRoleInto != null ? blockRoleInto.getSortKey() : blockSubjectAreaInto.getSortKey();
				bufferedWriter.write(jLabel2.getText() + ":" + targetName + "(" + targetSortKey + ")" + "\n");
			}
			if (blockSubsystemInto != null) {
				blockIDInto = blockSubsystemInto.getId();
			} else if (blockRoleInto != null) {
				blockIDInto = blockRoleInto.getId();
			} else {
				blockIDInto = blockSubjectAreaInto.getId();
			}

			//////////////////////
			// Importing tables //
			//////////////////////
			if (jRadioButtonTables.isSelected()) {
				importSubsystem();
				importTables();
				importSubsystemAttributesOfTable();
				importResult = res.getString("DialogImportXEAD16") +
				updateTableCounter + res.getString("DialogImportXEAD17") +
				createTableCounter + res.getString("DialogImportXEAD18") +
				cancelTableCounter + res.getString("DialogImportXEAD50") + "\n" +
				res.getString("DialogImportXEAD27") + missingTableKeyCounter + res.getString("DialogImportXEAD23") + "\n" +
				res.getString("DialogImportXEAD29") + logFileName + res.getString("DialogImportXEAD30") + "\n" + "\n";
			}

			/////////////////////////
			// Importing functions //
			/////////////////////////
			if (jRadioButtonFunctions.isSelected()) {
				importSubsystem();
				importFunctions();
				importResult = res.getString("DialogImportXEAD19") +
				updateFunctionCounter + res.getString("DialogImportXEAD17") +
				createFunctionCounter + res.getString("DialogImportXEAD18") +
				cancelFunctionCounter + res.getString("DialogImportXEAD50") + "\n" +
				res.getString("DialogImportXEAD20") + missingTableCounter + res.getString("DialogImportXEAD21") + "\n" + res.getString("DialogImportXEAD22") + missingFunctionCounter + res.getString("DialogImportXEAD23") + "\n" +
				res.getString("DialogImportXEAD29") + logFileName + res.getString("DialogImportXEAD30") + "\n" + "\n";
			}

			////////////////////////////////////
			// Importing tables and functions //
			////////////////////////////////////
			if (jRadioButtonTablesAndFunctions.isSelected()) {
				importSubsystem();
				importTables();
				importSubsystemAttributesOfTable();
				importFunctions();
				importResult = res.getString("DialogImportXEAD16") +
				updateTableCounter + res.getString("DialogImportXEAD17") +
				createTableCounter + res.getString("DialogImportXEAD18") +
				cancelTableCounter + res.getString("DialogImportXEAD50") + "\n" +
				res.getString("DialogImportXEAD27") +
				missingTableKeyCounter + res.getString("DialogImportXEAD23") + "\n" +
				res.getString("DialogImportXEAD19") +
				updateFunctionCounter + res.getString("DialogImportXEAD17") +
				createFunctionCounter + res.getString("DialogImportXEAD18") +
				cancelFunctionCounter + res.getString("DialogImportXEAD50") + "\n" +
				res.getString("DialogImportXEAD20") +
				missingTableCounter + res.getString("DialogImportXEAD21") + "\n" +
				res.getString("DialogImportXEAD22") +
				missingFunctionCounter + res.getString("DialogImportXEAD23") + "\n" +
				res.getString("DialogImportXEAD29") + logFileName + res.getString("DialogImportXEAD30") + "\n" + "\n";
			}

			/////////////////////
			// Importing tasks //
			/////////////////////
			if (jRadioButtonTasks.isSelected()) {
				importRole();
				importTasks();
				importResult = res.getString("DialogImportXEAD24") +
				updateTaskCounter + res.getString("DialogImportXEAD17") +
				createTaskCounter + res.getString("DialogImportXEAD18") +
				cancelTaskCounter + res.getString("DialogImportXEAD50") + "\n" +
				res.getString("DialogImportXEAD25") +
				missingFunctionIOCounter + res.getString("DialogImportXEAD26") + "\n" +
				res.getString("DialogImportXEAD29") + logFileName + res.getString("DialogImportXEAD30") + "\n" + "\n";
			}

			///////////////////////////
			// Importing a data-flow //
			///////////////////////////
			if (jRadioButtonDataflow.isSelected()) {
				importDataflow();
				if (cancelTaskCounter == 0 && cancelRoleCounter == 0) {
					importResult = res.getString("DialogImportXEAD55") + "\n" +
					res.getString("DialogImportXEAD29") + logFileName + res.getString("DialogImportXEAD30") + "\n" + "\n";
				} else {
					importResult = res.getString("DialogImportXEAD56") + "\n" +
					res.getString("DialogImportXEAD29") + logFileName + res.getString("DialogImportXEAD30") + "\n" + "\n";
				}
			}
		}
		catch (Exception ex1) {
			try {
				bufferedWriter.close();
			} catch (Exception ex2) {}
		} finally {
			jProgressBar.setVisible(false);
			jButtonStart.setVisible(true);
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			this.setVisible(false);
		}
		try {
			bufferedWriter.flush();
			bufferedWriter.close();
		} catch (Exception ex3) {
			try {
				bufferedWriter.close();
			} catch (Exception ex4) {}
		}
	}

	void importTables() {
		List<Table> elementListFrom = new ArrayList<>();
		int checkCounter = 0;
		String sortKeyOfDefinition;

		//////////////////////////
		//Import Table/Data Type//
		//////////////////////////
		importTypeDefinitionWithTagName("TableType");
		importTypeDefinitionWithTagName("DataType");

		///////////////////////////////////////
		//Setup List of Tables to be imported//
		///////////////////////////////////////
		List<Table> tablesFrom = xeadSystemFrom.getTables();
		if (tablesFrom != null) {
			for (Table table : tablesFrom) {
				if (!table.getSortKey().equals("") && table.getSubsystemId().equals(blockIDFrom)) {
					elementListFrom.add(table);
				}
			}
		}
		int itemsOfElementListFrom = elementListFrom.size();

		jProgressBar.setValue(0);
		jProgressBar.setMaximum(itemsOfElementListFrom);

		try {
			bufferedWriter.write("\n");
		} catch (IOException ex) {}

 	/////////////////////////////////////////////////
		//Import Table definitions into target document//
		/////////////////////////////////////////////////
		XeadSystem xeadSystem = frame_.getXeadDocumentModel().getXeadSystem();
		List<Table> targetTableList = xeadSystem.getTables();
		for (int i = 0; i < itemsOfElementListFrom; i++) {
			Table sourceTable = elementListFrom.get(i);
			jProgressBar.setValue(jProgressBar.getValue() + 1);
			jProgressBar.setString((i+1) + "/" + itemsOfElementListFrom);
			jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());

			checkCounter = 0;
			sortKeyOfDefinition = sourceTable.getSortKey();
			Table targetTable = null;
			for (int n = 0; n < targetTableList.size(); n++) {
				targetTable = targetTableList.get(n);
				if (targetTable.getSortKey().equals(sortKeyOfDefinition)) {
					checkCounter++;
					if (targetTable.getSubsystemId().equals(blockIDInto)) {
						try {
							bufferedWriter.write(sourceTable.getName() +
								"(" + sourceTable.getSortKey() +
								"):" + res.getString("DialogImportXEAD33") + "\n");
						} catch (IOException ex1) {}
						updateTableDefinition(sourceTable, targetTable);
						updateTableCounter++;
					} else {
						try {
							bufferedWriter.write(sourceTable.getName() +
								"(" + sourceTable.getSortKey() +
								"):" + res.getString("DialogImportXEAD48") + "\n");
						} catch (IOException ex1) {}
						cancelTableCounter++;
					}
					break;
				}
			}

			if (checkCounter == 0) {
				createTableDefinition(sourceTable, blockIDInto);
				createTableCounter++;
				try {
					bufferedWriter.write(sourceTable.getName() + "(" +
							sourceTable.getSortKey() +
						"):" + res.getString("DialogImportXEAD34") + "\n");
				} catch (IOException ex2) {}
			}
		}

		try {
			bufferedWriter.write("\n");
		} catch (IOException ex3) {}

		//////////////////////////////////////////////////
		//Import Table Relationship into target document//
		//////////////////////////////////////////////////
		String table1ID, table2ID, tableID;

		List<Relationship> relationshipListFrom = xeadSystemFrom.getRelationships();
		jProgressBar.setValue(0);
		jProgressBar.setString("Importing Table Relationships");
		jProgressBar.setMaximum(relationshipListFrom != null ? relationshipListFrom.size() : 0);

		if (relationshipListFrom != null) {
			for (Relationship relationship : relationshipListFrom) {
				jProgressBar.setValue(jProgressBar.getValue() + 1);
				jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());

				table1ID = relationship.getTable1Id();
				table2ID = relationship.getTable2Id();
				checkCounter = 0;
				for (int m = 0; m < itemsOfElementListFrom; m++) {
					tableID = elementListFrom.get(m).getId();
					if (tableID.equals(table1ID)) {
						checkCounter++;
					}
					if (tableID.equals(table2ID)) {
						checkCounter++;
					}
				}
				if (checkCounter >= 1) {
					adjustTableRelationship(relationship);
				}
			}
		}
	}

	void importFunctions() {
		List<Function> elementListFrom = new ArrayList<>();
		int checkCounter = 0;
		String sortKeyOfDefinition;

		////////////////////////
		//Import Function Type//
		////////////////////////
		importTypeDefinitionWithTagName("FunctionType");

		//////////////////////////////////////////
		//Setup List of Functions to be imported//
		/////////////////////////////////////////
		List<Function> functionsFrom = xeadSystemFrom.getFunctions();
		if (functionsFrom != null) {
			for (Function func : functionsFrom) {
				if (!func.getSortKey().equals("") && func.getSubsystemId().equals(blockIDFrom)) {
					elementListFrom.add(func);
				}
			}
		}
		int itemsOfElementListFrom = elementListFrom.size();

		try {
			bufferedWriter.write("\n");
		} catch (IOException ex) {}

		////////////////////////////////////////////////////
		//Import Function definitions into target document//
		////////////////////////////////////////////////////
		jProgressBar.setValue(0);
		jProgressBar.setMaximum(itemsOfElementListFrom);

		ArrayList<Function> targetFunctionArray = new ArrayList<Function>();
		XeadSystem xeadSystem = frame_.getXeadDocumentModel().getXeadSystem();
		List<Function> allFunctions = xeadSystem.getFunctions();
		if (allFunctions != null) {
			for (Function func : allFunctions) {
				if (func.getSubsystemId().equals(blockIDInto)) {
					targetFunctionArray.add(func);
				}
			}
		}
		
		for (int i = 0; i < itemsOfElementListFrom; i++) {
			Function sourceFunction = elementListFrom.get(i);
			jProgressBar.setValue(jProgressBar.getValue() + 1);
			jProgressBar.setString((i+1) + "/" + itemsOfElementListFrom);
			jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());

			checkCounter = 0;
			sortKeyOfDefinition = sourceFunction.getSortKey();

			Function targetFunction = null;
			for (int n = 0; n < targetFunctionArray.size(); n++) {
				targetFunction = targetFunctionArray.get(n);
				if (targetFunction.getSortKey().equals(sortKeyOfDefinition)) {
					checkCounter++;
					try {
						bufferedWriter.write(sourceFunction.getName() +
								"(" + sortKeyOfDefinition +
								"):" + res.getString("DialogImportXEAD33") + "\n");
					} catch (IOException ex1) {}

					updateFunctionDefinition(sourceFunction, targetFunction);
					updateFunctionCounter++;
					break;
				}
			}

			if (checkCounter == 0) {
				try {
					bufferedWriter.write(sourceFunction.getName() + "(" +
							sourceFunction.getSortKey() + "):" +
							res.getString("DialogImportXEAD34") + "\n");
				} catch (IOException ex2) {}

				createFunctionDefinition(sourceFunction, blockIDInto);
				createFunctionCounter++;
			}
		}
		try {
			bufferedWriter.write("\n");
		} catch (IOException ex3) {}

		////////////////////////////////////////////////
		//Import Functions-called into target document//
		////////////////////////////////////////////////
		jProgressBar.setString("Checking functions called");
		jProgressBar.setValue(0);
		jProgressBar.setMaximum(itemsOfElementListFrom);

		for (int i = 0; i < itemsOfElementListFrom; i++) {
			Function sourceFunction = elementListFrom.get(i);
			jProgressBar.setValue(jProgressBar.getValue() + 1);
			jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());

 		sortKeyOfDefinition = sourceFunction.getSortKey();
			Function targetFunction = null;
			for (int n = 0; n < targetFunctionArray.size(); n++) {
				targetFunction = targetFunctionArray.get(n);
				if (targetFunction.getSortKey().equals(sortKeyOfDefinition)) {
					adjustFunctionsCalled(sourceFunction, targetFunction);
					break;
				}
			}
		}
	}

	void importRole() {
		//////////////////////
		//Import Departments//
		//////////////////////
		importTypeDefinitionWithTagName("Department");

		////////////////////////////////////
		//Update attributes of target Role//
		////////////////////////////////////
		// blockSubsystemFrom is used for Role imports; find the actual Role from xeadSystemFrom
		Role blockRoleFrom = null;
		List<Role> roleListFrom = xeadSystemFrom.getRoles();
		if (roleListFrom != null) {
			for (Role role : roleListFrom) {
				if (role.getId().equals(blockIDFrom)) {
					blockRoleFrom = role;
					break;
				}
			}
		}
		if (blockRoleFrom != null) {
			blockRoleInto.setName(blockRoleFrom.getName());
			blockRoleInto.setSortKey(blockRoleFrom.getSortKey());
			blockRoleInto.setDescriptions(blockRoleFrom.getDescriptions());
			blockRoleInto.setDepartmentId(convertInternalIDOfTheTypeTag(blockRoleFrom.getDepartmentId(), "Department"));
		}
	}

	void importTasks() {
		List<Task> elementListFrom = new ArrayList<>();
		int checkCounter = 0;
		String sortKeyOfDefinition;

		///////////////////
		//Import TaskType//
		///////////////////
		importTypeDefinitionWithTagName("TaskType");

		//////////////////////////////////////
		//Setup List of Tasks to be imported//
		//////////////////////////////////////
		List<Task> tasksFrom = xeadSystemFrom.getTasks();
		if (tasksFrom != null) {
			for (Task task : tasksFrom) {
				if (!task.getSortKey().equals("") && task.getRoleId().equals(blockIDFrom)) {
					elementListFrom.add(task);
				}
			}
		}
		int itemsOfElementListFrom = elementListFrom.size();

		try {
			bufferedWriter.write("\n");
		} catch (IOException ex) {}

		////////////////////////////////////////////////
		//Import Task definitions into target document//
		////////////////////////////////////////////////
		jProgressBar.setValue(0);
		jProgressBar.setMaximum(itemsOfElementListFrom);

		XeadSystem xeadSystem = frame_.getXeadDocumentModel().getXeadSystem();
		List<Task> targetTaskList = xeadSystem.getTasks();
		for (int i = 0; i < itemsOfElementListFrom; i++) {
			Task sourceTask = elementListFrom.get(i);
			jProgressBar.setValue(jProgressBar.getValue() + 1);
			jProgressBar.setString((i+1) + "/" + itemsOfElementListFrom);
			jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());

			try {
				bufferedWriter.write(sourceTask.getName() + "(" +
						sourceTask.getSortKey() + "):");
			} catch (IOException ex1) {}

			checkCounter = 0;
			sortKeyOfDefinition = sourceTask.getSortKey();
			Task targetTask = null;
			for (int n = 0; n < targetTaskList.size(); n++) {
				targetTask = targetTaskList.get(n);
				if (targetTask.getSortKey().equals(sortKeyOfDefinition)) {
					checkCounter++;
					if (targetTask.getRoleId().equals(blockIDInto)) {
						try {
							bufferedWriter.write(res.getString("DialogImportXEAD33") + "\n");
						} catch (IOException ex2) {}

						updateTaskDefinition(sourceTask, targetTask);
						updateTaskCounter++;
					} else {
						try {
							bufferedWriter.write(res.getString("DialogImportXEAD49") + "\n");
						} catch (IOException ex2) {}

						cancelTaskCounter++;
					}
					break;
				}
			}

			if (checkCounter == 0) {
				try {
					bufferedWriter.write(res.getString("DialogImportXEAD34") + "\n");
				} catch (IOException ex3) {}

				createTaskDefinition(sourceTask, blockIDInto);
				createTaskCounter++;
			}
		}
	}
	
	String checkTaskDefinition(Task nodeTaskFrom) {
		String targetTaskID = "";
		String taskSortKey = "";
		List<Task> taskListFrom = xeadSystemFrom.getTasks();
		if (taskListFrom != null) {
			for (Task task : taskListFrom) {
				if (task.getId().equals(nodeTaskFrom.getId())) {
					taskSortKey = task.getSortKey();
				}
			}
		}
		XeadSystem xeadSystem = frame_.getXeadDocumentModel().getXeadSystem();
		List<Task> targetTaskList = xeadSystem.getTasks();
		for (int i = 0; i < targetTaskList.size(); i++) {
			Task targetTask = targetTaskList.get(i);
			if (targetTask.getSortKey().equals(taskSortKey)) {
				targetTaskID = targetTask.getId();
				break;
			}
		}
		return targetTaskID;
	}
	
 String checkRoleDefinition(Role nodeRoleFrom) {
		String targetRoleID = "";
		String roleSortKey = "";
		List<Role> roleListFrom = xeadSystemFrom.getRoles();
		if (roleListFrom != null) {
			for (Role role : roleListFrom) {
				if (role.getId().equals(nodeRoleFrom.getId())) {
					roleSortKey = role.getSortKey();
				}
			}
		}
		XeadSystem xeadSystem = frame_.getXeadDocumentModel().getXeadSystem();
		List<Role> targetRoleList = xeadSystem.getRoles();
		for (int i = 0; i < targetRoleList.size(); i++) {
			Role targetRole = targetRoleList.get(i);
			if (targetRole.getSortKey().equals(roleSortKey)) {
				targetRoleID = targetRole.getId();
				break;
			}
		}
		return targetRoleID;
	}

	void importDataflow() {
		int lastID;
		String taskIDInto, roleIDInto;
		ArrayList<String> nodeIDListFrom = new ArrayList<String>();
		ArrayList<String> nodeIDListInto = new ArrayList<String>();
		
		List<DataflowNode> dataflowNodeListFrom = blockSubjectAreaFrom.getDataflowNodes();
		List<DataflowLine> dataflowLineListFrom = blockSubjectAreaFrom.getDataflowLines();
		int countOfElements = 0;
		if (dataflowNodeListFrom != null) countOfElements = dataflowNodeListFrom.size() * 2;
		if (dataflowLineListFrom != null) countOfElements = countOfElements + dataflowLineListFrom.size();
		jProgressBar.setValue(0);
		jProgressBar.setMaximum(countOfElements);
		
		///////////////////////////////////////////////////////////////
		// Check if task(process) definitions exist in target system //
		///////////////////////////////////////////////////////////////
		if (dataflowNodeListFrom != null) {
			for (DataflowNode nodeFrom : dataflowNodeListFrom) {
				jProgressBar.setValue(jProgressBar.getValue() + 1);
				jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());

				if (nodeFrom.getType().equals("Process")) {
					taskIDInto = checkTaskDefinitionForDataflow(nodeFrom.getTaskID());
					if (taskIDInto.equals("")) {
						cancelTaskCounter++;
						try {
							List<Task> taskListFrom = xeadSystemFrom.getTasks();
							if (taskListFrom != null) {
								for (Task task : taskListFrom) {
									if (task.getId().equals(nodeFrom.getTaskID())) {
										bufferedWriter.write(task.getName() + "(" +
												task.getSortKey() + 
											"):" + res.getString("DialogImportXEAD54") + "\n");
									}
								}
							}
						}
						catch (IOException ex1) {}
						break;
					}
				}
				if (nodeFrom.getType().equals("Subject")
						&& !nodeFrom.getRoleID().equals("")) {
					roleIDInto = checkRoleDefinitionForDataflow(nodeFrom.getRoleID());
					if (roleIDInto.equals("")) {
						cancelRoleCounter++;
						try {
							List<Role> roleListFrom = xeadSystemFrom.getRoles();
							if (roleListFrom != null) {
								for (Role role : roleListFrom) {
									if (role.getId().equals(nodeFrom.getRoleID())) {
										bufferedWriter.write(role.getName() + "(" +
												role.getSortKey() + 
											"):" + res.getString("DialogImportXEAD57") + "\n");
									}
								}
							}
						}
						catch (IOException ex1) {}
						break;
					}
				}
			}
		}
		
 	if (cancelTaskCounter == 0 && cancelRoleCounter == 0) {
		
		////////////////////////////////////////
		//Update attributes of target DataFlow//
		////////////////////////////////////////
		blockSubjectAreaInto.setName(blockSubjectAreaFrom.getName());
		blockSubjectAreaInto.setDescriptions(blockSubjectAreaFrom.getDescriptions());
		blockSubjectAreaInto.setBoundaryPosition(blockSubjectAreaFrom.getBoundaryPosition());
		blockSubjectAreaInto.setBoundarySize(blockSubjectAreaFrom.getBoundarySize());
		blockSubjectAreaInto.setSortKey(blockSubjectAreaFrom.getSortKey());

		// Clear existing dataflow nodes and lines
		blockSubjectAreaInto.getDataflowNodes().clear();
		blockSubjectAreaInto.getDataflowLines().clear();

		if (dataflowNodeListFrom != null) {
			for (DataflowNode nodeFrom : dataflowNodeListFrom) {
				jProgressBar.setValue(jProgressBar.getValue() + 1);
				jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());

				DataflowNode dataflowNode = new DataflowNode();
				lastID = getLastIDOfTheType("DataflowNode");
				dataflowNode.setId(Integer.toString(lastID + 1));
				dataflowNode.setName(nodeFrom.getName());
				dataflowNode.setPosition(nodeFrom.getPosition());
				dataflowNode.setSlideNumber(nodeFrom.getSlideNumber());
				if (nodeFrom.getType().equals("Process")) {
					taskIDInto = checkTaskDefinitionForDataflow(nodeFrom.getTaskID());
					dataflowNode.setTaskID(taskIDInto);
					dataflowNode.setEventPos(nodeFrom.getEventPos());
				} else {
					if (nodeFrom.getType().equals("Subject")
							&& !nodeFrom.getRoleID().equals("")) {
						roleIDInto = checkRoleDefinitionForDataflow(nodeFrom.getRoleID());
						dataflowNode.setRoleID(roleIDInto);
					} else {
						dataflowNode.setNameExt(nodeFrom.getNameExt());
					}
				}
				dataflowNode.setType(nodeFrom.getType());
				dataflowNode.setDescriptions(nodeFrom.getDescriptions());
				blockSubjectAreaInto.getDataflowNodes().add(dataflowNode);

				nodeIDListFrom.add(nodeFrom.getId());
				nodeIDListInto.add(dataflowNode.getId());
			}
		}

		if (dataflowLineListFrom != null) {
			for (DataflowLine lineFrom : dataflowLineListFrom) {
				jProgressBar.setValue(jProgressBar.getValue() + 1);
				jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());

				DataflowLine dataflowLine = new DataflowLine();
				lastID = getLastIDOfTheType("DataflowLine");
				dataflowLine.setId(Integer.toString(lastID + 1));
				dataflowLine.setName(lineFrom.getName());
				dataflowLine.setNameExt(lineFrom.getNameExt());
				dataflowLine.setImageType(lineFrom.getImageType());
				dataflowLine.setNodeID1(nodeIDListInto.get(nodeIDListFrom.indexOf(lineFrom.getNodeID1())));
				dataflowLine.setNodeID2(nodeIDListInto.get(nodeIDListFrom.indexOf(lineFrom.getNodeID2())));
				dataflowLine.setShowArrow1(lineFrom.getShowArrow1());
				dataflowLine.setShowArrow2(lineFrom.getShowArrow2());
				dataflowLine.setTerminalPosIndex1(lineFrom.getTerminalPosIndex1());
				dataflowLine.setTerminalPosIndex2(lineFrom.getTerminalPosIndex2());
				dataflowLine.setSlideNumber(lineFrom.getSlideNumber());
				blockSubjectAreaInto.getDataflowLines().add(dataflowLine);
			}
		}
			try {
				bufferedWriter.write(res.getString("DialogImportXEAD33") + "\n");
			} catch (IOException ex3) {
			}
		}
	}
	
	String checkTaskDefinitionForDataflow(String taskIdFrom) {
		String targetTaskID = "";
		String taskSortKey = "";
		List<Task> taskListFrom = xeadSystemFrom.getTasks();
		if (taskListFrom != null) {
			for (Task task : taskListFrom) {
				if (task.getId().equals(taskIdFrom)) {
					taskSortKey = task.getSortKey();
				}
			}
		}
		XeadSystem xeadSystem = frame_.getXeadDocumentModel().getXeadSystem();
		List<Task> targetTaskList = xeadSystem.getTasks();
		for (int i = 0; i < targetTaskList.size(); i++) {
			Task targetTask = targetTaskList.get(i);
			if (targetTask.getSortKey().equals(taskSortKey)) {
				targetTaskID = targetTask.getId();
				break;
			}
		}
		return targetTaskID;
	}
	
	String checkRoleDefinitionForDataflow(String roleIdFrom) {
		String targetRoleID = "";
		String roleSortKey = "";
		List<Role> roleListFrom = xeadSystemFrom.getRoles();
		if (roleListFrom != null) {
			for (Role role : roleListFrom) {
				if (role.getId().equals(roleIdFrom)) {
					roleSortKey = role.getSortKey();
				}
			}
		}
		XeadSystem xeadSystem = frame_.getXeadDocumentModel().getXeadSystem();
		List<Role> targetRoleList = xeadSystem.getRoles();
		for (int i = 0; i < targetRoleList.size(); i++) {
			Role targetRole = targetRoleList.get(i);
			if (targetRole.getSortKey().equals(roleSortKey)) {
				targetRoleID = targetRole.getId();
				break;
			}
		}
		return targetRoleID;
	}

	String getStringValueOfDateAndTime() {
		String returnValue = "";
		GregorianCalendar calendar = new GregorianCalendar();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		String monthStr = "";
		if (month < 10) {
			monthStr = "0" + Integer.toString(month);
		} else {
			monthStr = Integer.toString(month);
		}
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		String dayStr = "";
		if (day < 10) {
			dayStr = "0" + Integer.toString(day);
		} else {
			dayStr = Integer.toString(day);
		}
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		String hourStr = "";
		if (hour < 10) {
			hourStr = "0" + Integer.toString(hour);
		} else {
			hourStr = Integer.toString(hour);
		}
		int minute = calendar.get(Calendar.MINUTE);
		String minStr = "";
		if (minute < 10) {
			minStr = "0" + Integer.toString(minute);
		} else {
			minStr = Integer.toString(minute);
		}
		int second = calendar.get(Calendar.SECOND);
		String secStr = "";
		if (second < 10) {
			secStr = "0" + Integer.toString(second);
		} else {
			secStr = Integer.toString(second);
		}
		returnValue = Integer.toString(year) + monthStr + dayStr + hourStr + minStr + secStr;
		return returnValue;
	}

	void updateFunctionDefinition(Function elementFrom, Function functionInto) {
		String internalID;

		functionInto.setName(elementFrom.getName());
		functionInto.setSummary(elementFrom.getSummary());
		functionInto.setParameters(elementFrom.getParameters());
		functionInto.setReturn(elementFrom.getReturn());
		functionInto.setDescriptions(elementFrom.getDescriptions());
		internalID = convertInternalIDOfTheTypeTag(elementFrom.getFunctionTypeId(), "FunctionType");
		functionInto.setFunctionTypeId(internalID);

		adjustIOPanelDefinition(elementFrom, functionInto);
		adjustIOWebPageDefinition(elementFrom, functionInto);
		adjustIOSpoolDefinition(elementFrom, functionInto);
		adjustIOTableDefinition(elementFrom, functionInto);
	}

	void adjustIOPanelDefinition(Function elementFrom, Function functionInto) {
		// Clear existing IOPanels
		functionInto.getIOPanels().clear();

		List<IOPanel> panelListFrom = elementFrom.getIOPanels();
		if (panelListFrom != null) {
			for (IOPanel panelFrom : panelListFrom) {
				IOPanel ioPanel = new IOPanel();
				ioPanel.setId(Integer.toString(getNextIDOfFunctionIOs()));
				ioPanel.setName(panelFrom.getName());
				ioPanel.setSortKey(panelFrom.getSortKey());
				ioPanel.setDescriptions(panelFrom.getDescriptions());
				ioPanel.setImageText(panelFrom.getImageText());
				ioPanel.setBackground(panelFrom.getBackground());
				ioPanel.setSize(panelFrom.getSize());

				if (panelFrom.getIopanelStyle() != null) {
					IOPanelStyle style = new IOPanelStyle();
					style.setValue(panelFrom.getIopanelStyle().getValue());
					ioPanel.setIopanelStyle(style);
				}

				List<IOPanelField> fieldListFrom = panelFrom.getIopanelFields();
				if (fieldListFrom != null) {
					for (IOPanelField fieldFrom : fieldListFrom) {
						IOPanelField field = new IOPanelField();
						field.setName(fieldFrom.getName());
						field.setLabel(fieldFrom.getLabel());
						field.setIoType(fieldFrom.getIoType());
						field.setDescriptions(fieldFrom.getDescriptions());
						field.setSortKey(fieldFrom.getSortKey());
						ioPanel.getIopanelFields().add(field);
					}
				}

				functionInto.getIOPanels().add(ioPanel);
			}
		}
	}

	void adjustIOWebPageDefinition(Function elementFrom, Function functionInto) {
		// Clear existing IOWebPages
		functionInto.getIOWebPages().clear();

		List<IOWebPage> pageListFrom = elementFrom.getIOWebPages();
		if (pageListFrom != null) {
			for (IOWebPage pageFrom : pageListFrom) {
				IOWebPage ioWebPage = new IOWebPage();
				ioWebPage.setId(Integer.toString(getNextIDOfFunctionIOs()));
				ioWebPage.setName(pageFrom.getName());
				ioWebPage.setSortKey(pageFrom.getSortKey());
				ioWebPage.setDescriptions(pageFrom.getDescriptions());
				ioWebPage.setFileName(pageFrom.getFileName());

				List<IOWebPageField> fieldListFrom = pageFrom.getIowebpageFields();
				if (fieldListFrom != null) {
					for (IOWebPageField fieldFrom : fieldListFrom) {
						IOWebPageField field = new IOWebPageField();
						field.setName(fieldFrom.getName());
						field.setLabel(fieldFrom.getLabel());
						field.setIoType(fieldFrom.getIoType());
						field.setDescriptions(fieldFrom.getDescriptions());
						field.setSortKey(fieldFrom.getSortKey());
						ioWebPage.getIowebpageFields().add(field);
					}
				}

				functionInto.getIOWebPages().add(ioWebPage);
			}
		}
	}

	void adjustIOSpoolDefinition(Function elementFrom, Function functionInto) {
		// Clear existing IOSpools
		functionInto.getIOSpools().clear();

		List<IOSpool> spoolListFrom = elementFrom.getIOSpools();
		if (spoolListFrom != null) {
			for (IOSpool spoolFrom : spoolListFrom) {
				IOSpool ioSpool = new IOSpool();
				ioSpool.setId(Integer.toString(getNextIDOfFunctionIOs()));
				ioSpool.setName(spoolFrom.getName());
				ioSpool.setSortKey(spoolFrom.getSortKey());
				ioSpool.setDescriptions(spoolFrom.getDescriptions());
				ioSpool.setImageText(spoolFrom.getImageText());
				ioSpool.setBackground(spoolFrom.getBackground());
				ioSpool.setSize(spoolFrom.getSize());

				if (spoolFrom.getIospoolStyle() != null) {
					IOSpoolStyle style = new IOSpoolStyle();
					style.setValue(spoolFrom.getIospoolStyle().getValue());
					ioSpool.setIospoolStyle(style);
				}

				List<IOSpoolField> fieldListFrom = spoolFrom.getIospoolFields();
				if (fieldListFrom != null) {
					for (IOSpoolField fieldFrom : fieldListFrom) {
						IOSpoolField field = new IOSpoolField();
						field.setName(fieldFrom.getName());
						field.setLabel(fieldFrom.getLabel());
						field.setDescriptions(fieldFrom.getDescriptions());
						field.setSortKey(fieldFrom.getSortKey());
						ioSpool.getIospoolFields().add(field);
					}
				}

				functionInto.getIOSpools().add(ioSpool);
			}
		}
	}

	void adjustIOTableDefinition(Function elementFrom, Function functionInto) {
		String internalTableIDFrom = "";
		String internalTableIDInto = "";
		String internalFieldIDInto = "";
		String tableName = "";
		String tableSortKey = "";

		List<Table> tableListFrom = xeadSystemFrom.getTables();
		XeadSystem xeadSystem = frame_.getXeadDocumentModel().getXeadSystem();
		List<Table> tableListInto = xeadSystem.getTables();

		List<IOTable> ioTableListFrom = elementFrom.getIOTables();

		// Clear existing IOTables
		functionInto.getIOTables().clear();

		if (ioTableListFrom != null) {
			for (IOTable ioTableFrom : ioTableListFrom) {
				internalTableIDFrom = ioTableFrom.getTableId();
				internalTableIDInto = convertInternalIDOfTheTag(internalTableIDFrom, "Table");

				Table targetTable = null;
				if (!internalTableIDInto.equals("")) {
					for (int j = 0; j < tableListInto.size(); j++) {
						targetTable = tableListInto.get(j);
						if (internalTableIDInto.equals(targetTable.getId())) {
							break;
						}
					}
				}

				//////////////////
				//Create IOTable//
				//////////////////
				if (targetTable == null) {
					missingTableCounter++;
					if (tableListFrom != null) {
						for (Table table : tableListFrom) {
							if (internalTableIDFrom.equals(table.getId())) {
								tableName = table.getName();
								tableSortKey = table.getSortKey();
								break;
							}
						}
					}
					try {
						bufferedWriter.write("  " + tableName + "(" + tableSortKey + ")" + res.getString("DialogImportXEAD37") + "\n");
					} catch (IOException ex) {}
				} else {
					IOTable ioTable = new IOTable();
					ioTable.setId(Integer.toString(getNextIDOfFunctionIOs()));
					ioTable.setPosition(ioTableFrom.getPosition());
					ioTable.setNameExtension(ioTableFrom.getNameExtension());
					ioTable.setSortKey(ioTableFrom.getSortKey());
					ioTable.setDescriptions(ioTableFrom.getDescriptions());
					ioTable.setTableId(internalTableIDInto);
					ioTable.setOpC(ioTableFrom.getOpC());
					ioTable.setOpR(ioTableFrom.getOpR());
					ioTable.setOpU(ioTableFrom.getOpU());
					ioTable.setOpD(ioTableFrom.getOpD());

					///////////////////////
					//Create IOTableField//
					///////////////////////
					List<IOTableField> fieldListFrom = ioTableFrom.getIotableFields();
					if (fieldListFrom != null) {
						for (IOTableField fieldFrom : fieldListFrom) {
							internalFieldIDInto = convertInternalIDOfTableField(internalTableIDFrom, fieldFrom.getFieldId(), xeadSystem);
							if (!fieldFrom.getDescriptions().equals("") && !internalFieldIDInto.equals("")) {
								IOTableField field = new IOTableField();
								field.setFieldId(internalFieldIDInto);
								field.setDescriptions(fieldFrom.getDescriptions());
								ioTable.getIotableFields().add(field);
							}
						}
					}
					functionInto.getIOTables().add(ioTable);
				}

				setupSubsystemAttributeOfTable(internalTableIDInto, blockSubsystemInto);
			}
		}
	}

	void setupSubsystemAttributeOfTable(String internalTableIDInto, Subsystem subsystemInto) {
		if (subsystemInto == null) return;

		int checkCounter = 0;
		List<SubsystemTable> subsystemTableList = subsystemInto.getSubsystemTables();
		for (SubsystemTable st : subsystemTableList) {
			if (st.getTableId().equals(internalTableIDInto)) {
				checkCounter++;
			}
		}
		if (checkCounter == 0) {
			SubsystemTable subTable = new SubsystemTable();
			subTable.setTableId(internalTableIDInto);
			subTable.setBoxPosition("50,50");
			subTable.setExtDivLoc("600");
			subTable.setIntDivLoc("300");
			subTable.setShowOnModel("false");
			subTable.setShowInstance("false");
			subTable.setInstance("");
			subsystemInto.getSubsystemTables().add(subTable);
		}
	}

	void updateTaskDefinition(Task elementFrom, Task taskInto) {
		//////////////////////////
		//Update Task Definition//
		//////////////////////////
		taskInto.setName(elementFrom.getName());
		taskInto.setEvent(elementFrom.getEvent());
		taskInto.setTaskTypeId(convertInternalIDOfTheTypeTag(elementFrom.getTaskTypeId(), "TaskType"));
		taskInto.setDescriptions(elementFrom.getDescriptions());

		/////////////////////////////////
		//Purge and create Task Actions//
		/////////////////////////////////
		taskInto.getTaskActions().clear();
		createTaskActions(elementFrom, taskInto);
	}

	void updateTableDefinition(Table elementFrom, Table tableInto) {
		int lastID = 0;
		int checkCounter = 0;
		String workStr = "";
		String fieldID = "";

		String tableIDFrom = elementFrom.getId();
		List<TableField> fieldListFrom = elementFrom.getTableFields();
		List<TableKey> keyListFrom = elementFrom.getTableKeys();
		XeadSystem xeadSystem = frame_.getXeadDocumentModel().getXeadSystem();

		///////////////////////////
		//Update Table Attributes//
		///////////////////////////
		tableInto.setName(elementFrom.getName());
		tableInto.setDescriptions(elementFrom.getDescriptions());
		tableInto.setTableTypeId(convertInternalIDOfTheTypeTag(elementFrom.getTableTypeId(), "TableType"));

		/////////////////////
		//Adjust TableField//
		/////////////////////
		if (fieldListFrom != null) {
			for (TableField fieldFrom : fieldListFrom) {
				workStr = convertInternalIDOfTableField(tableIDFrom, fieldFrom.getId(), xeadSystem);

				checkCounter = 0;
				for (TableField tf : tableInto.getTableFields()) {
					if (workStr.equals(tf.getId())) {
						tf.setName(fieldFrom.getName());
						tf.setSortKey(fieldFrom.getSortKey());
						tf.setDescriptions(fieldFrom.getDescriptions());
						tf.setAlias(fieldFrom.getAlias());
						tf.setAttributeType(fieldFrom.getAttributeType());
						tf.setDataTypeId(convertInternalIDOfTheTypeTag(fieldFrom.getDataTypeId(), "DataType"));
						tf.setShowOnModel(fieldFrom.getShowOnModel());
						tf.setNotNull(fieldFrom.getNotNull());
						tf.setNoUpdate(fieldFrom.getNoUpdate());
						tf.setDefault(fieldFrom.getDefault());
						checkCounter++;
						break;
					}
				}
				if (checkCounter == 0) {
					lastID = getLastIDOfTheType("TableField");
					fieldID = Integer.toString(lastID + 1);
					TableField newField = new TableField();
					newField.setId(fieldID);
					newField.setName(fieldFrom.getName());
					newField.setSortKey(fieldFrom.getSortKey());
					newField.setDescriptions(fieldFrom.getDescriptions());
					newField.setAlias(fieldFrom.getAlias());
					newField.setAttributeType(fieldFrom.getAttributeType());
					newField.setDataTypeId(convertInternalIDOfTheTypeTag(fieldFrom.getDataTypeId(), "DataType"));
					newField.setShowOnModel(fieldFrom.getShowOnModel());
					newField.setNotNull(fieldFrom.getNotNull());
					newField.setNoUpdate(fieldFrom.getNoUpdate());
					newField.setDefault(fieldFrom.getDefault());
					tableInto.getTableFields().add(newField);
				}
			}
		}

		///////////////////
		//Adjust TableKey//
		///////////////////
		if (keyListFrom != null) {
			for (TableKey keyFrom : keyListFrom) {
				checkCounter = 0;
				TableKey lastKey = null;
				for (TableKey tk : tableInto.getTableKeys()) {
					if (theseAreIdenticalKeyDefinitions(keyFrom, tk, tableIDFrom, xeadSystem)) {
						tk.setSortKey(keyFrom.getSortKey());
						checkCounter++;
						break;
					}
					if (lastKey == null || Integer.parseInt(tk.getId()) > Integer.parseInt(lastKey.getId())) {
						lastKey = tk;
					}
				}
				if (checkCounter == 0 && lastKey != null) {
					lastID = Integer.parseInt(lastKey.getId());
					TableKey newKey = new TableKey();
					newKey.setId(Integer.toString(lastID + 1));
					newKey.setType(keyFrom.getType());
					newKey.setSortKey(keyFrom.getSortKey());
					List<TableKeyField> keyFieldListFrom = keyFrom.getTableKeyFields();
					if (keyFieldListFrom != null) {
						for (TableKeyField tkfFrom : keyFieldListFrom) {
							TableKeyField tkf = new TableKeyField();
							tkf.setFieldId(convertInternalIDOfTableField(elementFrom.getId(), tkfFrom.getFieldId(), xeadSystem));
							tkf.setSortKey(tkfFrom.getSortKey());
							newKey.getTableKeyFields().add(tkf);
						}
					}
					tableInto.getTableKeys().add(newKey);
				}
			}
		}
	}

	void createTableDefinition(Table elementFrom, String subsystemID) {
		int lastID = 0;

		XeadSystem xeadSystem = frame_.getXeadDocumentModel().getXeadSystem();

		///////////////////////////
		//Create Table Definition//
		///////////////////////////
		Table newTable = new Table();
		lastID = getLastIDOfTheType("Table");
		newTable.setId(Integer.toString(lastID + 1));
		newTable.setName(elementFrom.getName());
		newTable.setSortKey(elementFrom.getSortKey());
		newTable.setSubsystemId(subsystemID);
		newTable.setDescriptions(elementFrom.getDescriptions());
		newTable.setTableTypeId(convertInternalIDOfTheTypeTag(elementFrom.getTableTypeId(), "TableType"));
		xeadSystem.getTables().add(newTable);

		/////////////////////
		//Create TableField//
		/////////////////////
		List<TableField> fieldListFrom = elementFrom.getTableFields();
		if (fieldListFrom != null) {
			for (TableField fieldFrom : fieldListFrom) {
				TableField newField = new TableField();
				lastID = getLastIDOfTheType("TableField");
				newField.setId(Integer.toString(lastID + 1));
				newField.setName(fieldFrom.getName());
				newField.setSortKey(fieldFrom.getSortKey());
				newField.setDescriptions(fieldFrom.getDescriptions());
				newField.setAlias(fieldFrom.getAlias());
				newField.setAttributeType(fieldFrom.getAttributeType());
				newField.setDataTypeId(convertInternalIDOfTheTypeTag(fieldFrom.getDataTypeId(), "DataType"));
				newField.setShowOnModel(fieldFrom.getShowOnModel());
				newField.setNotNull(fieldFrom.getNotNull());
				newField.setNoUpdate(fieldFrom.getNoUpdate());
				newField.setDefault(fieldFrom.getDefault());
				newTable.getTableFields().add(newField);
			}
		}

		///////////////////
		//Create TableKey//
		///////////////////
		List<TableKey> keyListFrom = elementFrom.getTableKeys();
		if (keyListFrom != null) {
			for (TableKey keyFrom : keyListFrom) {
				TableKey newKey = new TableKey();
				newKey.setId(keyFrom.getId());
				newKey.setType(keyFrom.getType());
				newKey.setSortKey(keyFrom.getSortKey());
				List<TableKeyField> keyFieldListFrom = keyFrom.getTableKeyFields();
				if (keyFieldListFrom != null) {
					for (TableKeyField tkfFrom : keyFieldListFrom) {
						TableKeyField tkf = new TableKeyField();
						tkf.setFieldId(convertInternalIDOfTableField(elementFrom.getId(), tkfFrom.getFieldId(), xeadSystem));
						tkf.setAscDesc(tkfFrom.getAscDesc());
						tkf.setSortKey(tkfFrom.getSortKey());
						newKey.getTableKeyFields().add(tkf);
					}
				}
				newTable.getTableKeys().add(newKey);
			}
		}
	}

	void adjustTableRelationship(Relationship elementFrom) {
		int lastID = 0;

		String tableID1From = elementFrom.getTable1Id();
		String tableID2From = elementFrom.getTable2Id();
		String tableKey1From = elementFrom.getTableKey1Id();
		String tableKey2From = elementFrom.getTableKey2Id();
		String relationshipType = elementFrom.getType();
		List<Table> tableListFrom = xeadSystemFrom.getTables();
		XeadSystem xeadSystem = frame_.getXeadDocumentModel().getXeadSystem();
		List<Relationship> relationshipListInto = xeadSystem.getRelationships();

		String tableName1From = "";
		String tableName2From = "";
		String tableSortKey1From = "";
		String tableSortKey2From = "";
		if (tableListFrom != null) {
			for (Table table : tableListFrom) {
				if (table.getId().equals(tableID1From)) {
					tableName1From = table.getName();
					tableSortKey1From = table.getSortKey();
				}
				if (table.getId().equals(tableID2From)) {
					tableName2From = table.getName();
					tableSortKey2From = table.getSortKey();
				}
			}
		}

		String relationshipIDInto = convertInternalIDOfTableRelationship(elementFrom.getId(), xeadSystem);
		if (relationshipIDInto.equals("")) {
			String tableID1Into = convertInternalIDOfTheTag(tableID1From, "Table");
			String tableID2Into = convertInternalIDOfTheTag(tableID2From, "Table");
			String convertedKey1ID = convertTableKeyID(tableID1From, tableKey1From, tableID1Into, xeadSystem);
			String convertedKey2ID = convertTableKeyID(tableID2From, tableKey2From, tableID2Into, xeadSystem);
			if (convertedKey1ID.equals("") || convertedKey2ID.equals("")) {
				missingTableKeyCounter++;
				try {
					bufferedWriter.write(tableName1From + "(" + tableSortKey1From + ")," + tableName2From + "(" + tableSortKey2From + ")" + res.getString("DialogImportXEAD35") + "\n");
				} catch (IOException ex) {}
			} else {
				lastID = getLastIDOfTheType("Relationship");
				Relationship newRel = new Relationship();
				newRel.setId(Integer.toString(lastID + 1));
				newRel.setTable1Id(tableID1Into);
				newRel.setTable2Id(tableID2Into);
				newRel.setTableKey1Id(convertedKey1ID);
				newRel.setTableKey2Id(convertedKey2ID);
				newRel.setType(relationshipType);
				newRel.setExistWhen1(elementFrom.getExistWhen1());
				newRel.setExistWhen2(elementFrom.getExistWhen2());
				xeadSystem.getRelationships().add(newRel);

				try {
					bufferedWriter.write(tableName1From + "(" + tableSortKey1From + ")," + tableName2From + "(" + tableSortKey2From + ")" + res.getString("DialogImportXEAD36") + "\n");
				} catch (IOException ex) {}
			}
		} else {
			for (Relationship rel : relationshipListInto) {
				if (rel.getId().equals(relationshipIDInto)) {
					rel.setExistWhen1(elementFrom.getExistWhen1());
					rel.setExistWhen2(elementFrom.getExistWhen2());
					break;
				}
			}
		}
	}
	
	void importSubsystemAttributesOfTable() {
		String tableID1 = "";
		String relationshipIDFrom = "";
		String relationshipIDInto = "";
		int checkCounter = 0;

		if (blockSubsystemInto == null) return;

		/////////////////////////
		//Adjust SubsystemTable//
		/////////////////////////
		List<Table> tableListFrom = xeadSystemFrom.getTables();
		List<SubsystemTable> subTableListFrom = blockSubsystemFrom.getSubsystemTables();

		jProgressBar.setString("Importing Subsystem Attribute...");
		jProgressBar.setValue(0);
		jProgressBar.setMaximum(subTableListFrom != null ? subTableListFrom.size() : 0);

		if (subTableListFrom != null) {
			for (SubsystemTable subTableFrom : subTableListFrom) {
				jProgressBar.setValue(jProgressBar.getValue() + 1);
				jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());

				tableID1 = convertInternalIDOfTheTag(subTableFrom.getTableId(), "Table");
				if (tableID1.equals("")) {
					try {
						if (tableListFrom != null) {
							for (Table table : tableListFrom) {
								if (table.getId().equals(subTableFrom.getTableId())) {
									bufferedWriter.write(table.getName() + "(" + table.getSortKey() + ")" + res.getString("DialogImportXEAD42") + "\n");
								}
							}
						}
					} catch (IOException ex) {}
				} else {
					checkCounter = 0;
					SubsystemTable targetST = null;
					for (SubsystemTable st : blockSubsystemInto.getSubsystemTables()) {
						if (tableID1.equals(st.getTableId())) {
							targetST = st;
							checkCounter++;
							break;
						}
					}
					if (targetST != null) {
						targetST.setBoxPosition(subTableFrom.getBoxPosition());
						targetST.setExtDivLoc(subTableFrom.getExtDivLoc());
						targetST.setIntDivLoc(subTableFrom.getIntDivLoc());
						targetST.setShowOnModel(subTableFrom.getShowOnModel());
						targetST.setShowInstance(subTableFrom.getShowInstance());
						targetST.setInstance(subTableFrom.getInstance());
					}
				}
			}
		}

		////////////////////////////////
		//Adjust SubsystemRelationship//
		////////////////////////////////
		List<SubsystemRelationship> subRelListFrom = blockSubsystemFrom.getSubsystemRelationships();

		jProgressBar.setValue(0);
		jProgressBar.setMaximum(subRelListFrom != null ? subRelListFrom.size() : 0);

		if (subRelListFrom != null) {
			for (SubsystemRelationship subRelFrom : subRelListFrom) {
				jProgressBar.setValue(jProgressBar.getValue() + 1);
				jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());

				relationshipIDFrom = subRelFrom.getRelationshipId();
				relationshipIDInto = convertInternalIDOfTableRelationship(relationshipIDFrom);
				if (!relationshipIDInto.equals("")) {
					checkCounter = 0;
					SubsystemRelationship targetSR = null;
					for (SubsystemRelationship sr : blockSubsystemInto.getSubsystemRelationships()) {
						if (sr.getRelationshipId().equals(relationshipIDInto)) {
							targetSR = sr;
							checkCounter++;
							break;
						}
					}
					if (targetSR != null) {
						targetSR.setTerminalIndex1(subRelFrom.getTerminalIndex1());
						targetSR.setTerminalIndex2(subRelFrom.getTerminalIndex2());
						targetSR.setShowOnModel(subRelFrom.getShowOnModel());
					}
				}
			}
		}
	}

	void importSubsystem() {
		/////////////////////////////////////////
		//Update attributes of target subsystem//
		/////////////////////////////////////////
		if (blockSubsystemInto != null && blockSubsystemFrom != null) {
			blockSubsystemInto.setName(blockSubsystemFrom.getName());
			blockSubsystemInto.setSortKey(blockSubsystemFrom.getSortKey());
			blockSubsystemInto.setDescriptions(blockSubsystemFrom.getDescriptions());
		}
	}

	String convertInternalIDOfTableRelationship(String relationshipIDFrom) {
		return convertInternalIDOfTableRelationship(relationshipIDFrom, frame_.getXeadDocumentModel().getXeadSystem());
	}

	String convertInternalIDOfTableRelationship(String relationshipIDFrom, XeadSystem xeadSystemInto) {
		String table1IDFrom = "";
		String tableKey1IDFrom = "";
		String table2IDFrom = "";
		String tableKey2IDFrom = "";
		String relationshipTypeFrom = "";
		String table1IDInto = "";
		String tableKey1IDInto = "";
		String table2IDInto = "";
		String tableKey2IDInto = "";
		String relationshipTypeInto = "";
		String relationshipIDInto = "";
		String relationshipIDWork = "";

		TableKey tableKey1From = null;
		TableKey tableKey2From = null;

		List<Relationship> relationshipListFrom = xeadSystemFrom.getRelationships();
		if (relationshipListFrom != null) {
			for (Relationship rel : relationshipListFrom) {
				if (rel.getId().equals(relationshipIDFrom)) {
					table1IDFrom = rel.getTable1Id();
					tableKey1IDFrom = rel.getTableKey1Id();
					table2IDFrom = rel.getTable2Id();
					tableKey2IDFrom = rel.getTableKey2Id();
					relationshipTypeFrom = rel.getType();
				}
			}
		}

		List<Table> tableListFrom = xeadSystemFrom.getTables();
		if (tableListFrom != null) {
			for (Table table : tableListFrom) {
				if (table.getId().equals(table1IDFrom)) {
					List<TableKey> tableKeyList = table.getTableKeys();
					if (tableKeyList != null) {
						for (TableKey tk : tableKeyList) {
							if (tk.getId().equals(tableKey1IDFrom)) {
								tableKey1From = tk;
							}
						}
					}
				}
				if (table.getId().equals(table2IDFrom)) {
					List<TableKey> tableKeyList = table.getTableKeys();
					if (tableKeyList != null) {
						for (TableKey tk : tableKeyList) {
							if (tk.getId().equals(tableKey2IDFrom)) {
								tableKey2From = tk;
							}
						}
					}
				}
			}
		}

		List<Relationship> relationshipListInto = xeadSystemInto.getRelationships();
		List<Table> tableListInto = xeadSystemInto.getTables();

		for (int i = 0; i < relationshipListInto.size(); i++) {
			Relationship rel = relationshipListInto.get(i);
			relationshipIDWork = rel.getId();
			table1IDInto = rel.getTable1Id();
			tableKey1IDInto = rel.getTableKey1Id();
			table2IDInto = rel.getTable2Id();
			tableKey2IDInto = rel.getTableKey2Id();
			relationshipTypeInto = rel.getType();

			if (relationshipTypeFrom.equals(relationshipTypeInto)) {
				Table table1Into = null;
				Table table2Into = null;
				for (Table t : tableListInto) {
					if (t.getId().equals(table1IDInto)) {
						table1Into = t;
					}
					if (t.getId().equals(table2IDInto)) {
						table2Into = t;
					}
				}
				TableKey key1Into = null;
				TableKey key2Into = null;
				if (table1Into != null) {
					for (TableKey tk : table1Into.getTableKeys()) {
						if (tk.getId().equals(tableKey1IDInto)) {
							key1Into = tk;
						}
					}
				}
				if (table2Into != null) {
					for (TableKey tk : table2Into.getTableKeys()) {
						if (tk.getId().equals(tableKey2IDInto)) {
							key2Into = tk;
						}
					}
				}

				if (theseAreIdenticalKeyDefinitions(tableKey1From, key1Into, table1IDFrom, xeadSystemInto) &&
						theseAreIdenticalKeyDefinitions(tableKey2From, key2Into, table2IDFrom, xeadSystemInto)) {
					relationshipIDInto = relationshipIDWork;
					break;
				}
			}
		}
		return relationshipIDInto;
	}

	void createFunctionDefinition(Function elementFrom, String subsystemID) {
		String internalTableIDInto = "";
		String internalFieldIDInto = "";
		int lastID = 0;
		String tableName = "";
		String tableSortKey = "";
		List<Table> tableListFrom = xeadSystemFrom.getTables();
		XeadSystem xeadSystem = frame_.getXeadDocumentModel().getXeadSystem();

		Function newFunc = new Function();
		lastID = getLastIDOfTheType("Function");
		newFunc.setId(Integer.toString(lastID + 1));
		newFunc.setName(elementFrom.getName());
		newFunc.setSortKey(elementFrom.getSortKey());
		newFunc.setSubsystemId(subsystemID);
		newFunc.setSummary(elementFrom.getSummary());
		newFunc.setParameters(elementFrom.getParameters());
		newFunc.setReturn(elementFrom.getReturn());
		newFunc.setDescriptions(elementFrom.getDescriptions());
		newFunc.setFunctionTypeId(convertInternalIDOfTheTypeTag(elementFrom.getFunctionTypeId(), "FunctionType"));
		xeadSystem.getFunctions().add(newFunc);

		List<IOPanel> panelListFrom = elementFrom.getIOPanels();
		if (panelListFrom != null) {
			for (IOPanel panelFrom : panelListFrom) {
				IOPanel ioPanel = new IOPanel();
				ioPanel.setId(panelFrom.getId());
				ioPanel.setName(panelFrom.getName());
				ioPanel.setSortKey(panelFrom.getSortKey());
				ioPanel.setDescriptions(panelFrom.getDescriptions());
				ioPanel.setImageText(panelFrom.getImageText());
				ioPanel.setBackground(panelFrom.getBackground());
				ioPanel.setSize(panelFrom.getSize());
				if (panelFrom.getIopanelStyle() != null) {
					IOPanelStyle style = new IOPanelStyle();
					style.setValue(panelFrom.getIopanelStyle().getValue());
					ioPanel.setIopanelStyle(style);
				}
				List<IOPanelField> fieldListFrom = panelFrom.getIopanelFields();
				if (fieldListFrom != null) {
					for (IOPanelField fieldFrom : fieldListFrom) {
						IOPanelField field = new IOPanelField();
						field.setName(fieldFrom.getName());
						field.setLabel(fieldFrom.getLabel());
						field.setIoType(fieldFrom.getIoType());
						field.setDescriptions(fieldFrom.getDescriptions());
						field.setSortKey(fieldFrom.getSortKey());
						ioPanel.getIopanelFields().add(field);
					}
				}
				newFunc.getIOPanels().add(ioPanel);
			}
		}

		List<IOWebPage> pageListFrom = elementFrom.getIOWebPages();
		if (pageListFrom != null) {
			for (IOWebPage pageFrom : pageListFrom) {
				IOWebPage ioWebPage = new IOWebPage();
				ioWebPage.setId(pageFrom.getId());
				ioWebPage.setName(pageFrom.getName());
				ioWebPage.setSortKey(pageFrom.getSortKey());
				ioWebPage.setDescriptions(pageFrom.getDescriptions());
				ioWebPage.setFileName(pageFrom.getFileName());
				List<IOWebPageField> fieldListFrom = pageFrom.getIowebpageFields();
				if (fieldListFrom != null) {
					for (IOWebPageField fieldFrom : fieldListFrom) {
						IOWebPageField field = new IOWebPageField();
						field.setName(fieldFrom.getName());
						field.setLabel(fieldFrom.getLabel());
						field.setIoType(fieldFrom.getIoType());
						field.setDescriptions(fieldFrom.getDescriptions());
						field.setSortKey(fieldFrom.getSortKey());
						ioWebPage.getIowebpageFields().add(field);
					}
				}
				newFunc.getIOWebPages().add(ioWebPage);
			}
		}

		List<IOSpool> spoolListFrom = elementFrom.getIOSpools();
		if (spoolListFrom != null) {
			for (IOSpool spoolFrom : spoolListFrom) {
				IOSpool ioSpool = new IOSpool();
				ioSpool.setId(spoolFrom.getId());
				ioSpool.setName(spoolFrom.getName());
				ioSpool.setSortKey(spoolFrom.getSortKey());
				ioSpool.setDescriptions(spoolFrom.getDescriptions());
				ioSpool.setImageText(spoolFrom.getImageText());
				ioSpool.setBackground(spoolFrom.getBackground());
				ioSpool.setSize(spoolFrom.getSize());
				if (spoolFrom.getIospoolStyle() != null) {
					IOSpoolStyle style = new IOSpoolStyle();
					style.setValue(spoolFrom.getIospoolStyle().getValue());
					ioSpool.setIospoolStyle(style);
				}
				List<IOSpoolField> fieldListFrom = spoolFrom.getIospoolFields();
				if (fieldListFrom != null) {
					for (IOSpoolField fieldFrom : fieldListFrom) {
						IOSpoolField field = new IOSpoolField();
						field.setName(fieldFrom.getName());
						field.setLabel(fieldFrom.getLabel());
						field.setDescriptions(fieldFrom.getDescriptions());
						field.setSortKey(fieldFrom.getSortKey());
						ioSpool.getIospoolFields().add(field);
					}
				}
				newFunc.getIOSpools().add(ioSpool);
			}
		}

		List<IOTable> tableIOListFrom = elementFrom.getIOTables();
		if (tableIOListFrom != null) {
			for (IOTable tableIOFrom : tableIOListFrom) {
				internalTableIDInto = convertInternalIDOfTheTag(tableIOFrom.getTableId(), "Table");
				if (internalTableIDInto.equals("")) {
					missingTableCounter++;
					if (tableListFrom != null) {
						for (Table table : tableListFrom) {
							if (tableIOFrom.getTableId().equals(table.getId())) {
								tableName = table.getName();
								tableSortKey = table.getSortKey();
							}
						}
					}
					try {
						bufferedWriter.write("  " + tableName + "(" + tableSortKey + ")" + res.getString("DialogImportXEAD37") + "\n");
					} catch (IOException ex) {}
				} else {
					IOTable ioTable = new IOTable();
					ioTable.setId(tableIOFrom.getId());
					ioTable.setNameExtension(tableIOFrom.getNameExtension());
					ioTable.setSortKey(tableIOFrom.getSortKey());
					ioTable.setDescriptions(tableIOFrom.getDescriptions());
					ioTable.setTableId(internalTableIDInto);
					ioTable.setOpC(tableIOFrom.getOpC());
					ioTable.setOpR(tableIOFrom.getOpR());
					ioTable.setOpU(tableIOFrom.getOpU());
					ioTable.setOpD(tableIOFrom.getOpD());
					List<IOTableField> fieldListFrom = tableIOFrom.getIotableFields();
					if (fieldListFrom != null) {
						for (IOTableField fieldFrom : fieldListFrom) {
							internalFieldIDInto = convertInternalIDOfTableField(tableIOFrom.getTableId(), fieldFrom.getFieldId(), xeadSystem);
							if (!fieldFrom.getDescriptions().equals("") && !internalFieldIDInto.equals("")) {
								IOTableField field = new IOTableField();
								field.setFieldId(internalFieldIDInto);
								field.setDescriptions(fieldFrom.getDescriptions());
								ioTable.getIotableFields().add(field);
							}
						}
					}
					newFunc.getIOTables().add(ioTable);
					setupSubsystemAttributeOfTable(internalTableIDInto, blockSubsystemInto);
				}
			}
		}
	}

	String createTaskDefinition(Task elementFrom, String roleID) {
		int lastID = 0;
		String newID = "";
		XeadSystem xeadSystem = frame_.getXeadDocumentModel().getXeadSystem();

		Task newTask = new Task();
		lastID = getLastIDOfTheType("Task");
		newID = Integer.toString(lastID + 1);
		newTask.setId(newID);
		newTask.setName(elementFrom.getName());
		newTask.setEvent(elementFrom.getEvent());
		newTask.setSortKey(elementFrom.getSortKey());
		newTask.setRoleId(roleID);
		newTask.setTaskTypeId(convertInternalIDOfTheTypeTag(elementFrom.getTaskTypeId(), "TaskType"));
		newTask.setDescriptions(elementFrom.getDescriptions());
		xeadSystem.getTasks().add(newTask);

		createTaskActions(elementFrom, newTask);

		return newID;
	}

	void createTaskActions(Task elementFrom, Task taskInto) {
		String functionSortKey = "";
		String functionName = "";
		String ioSortKey = "";
		String ioName = "";
		String targetFunctionID = "";
		String targetFunctionIOID = "";

		List<Function> functionListFrom = xeadSystemFrom.getFunctions();
		XeadSystem xeadSystem = frame_.getXeadDocumentModel().getXeadSystem();
		List<Function> functionListInto = xeadSystem.getFunctions();
		List<Table> tableListFrom = xeadSystemFrom.getTables();

		List<TaskAction> taskActionListFrom = elementFrom.getTaskActions();
		if (taskActionListFrom != null) {
			for (TaskAction actionFrom : taskActionListFrom) {
				TaskAction taskAction = new TaskAction();
				taskAction.setExecuteIf(actionFrom.getExecuteIf());
				taskAction.setLabel(actionFrom.getLabel());
				taskAction.setSortKey(actionFrom.getSortKey());
				taskAction.setDescriptions(actionFrom.getDescriptions());
				taskAction.setIndentLevel(actionFrom.getIndentLevel());

				List<TaskFunctionIO> tfioListFrom = actionFrom.getTaskFunctionIOs();
				if (tfioListFrom != null) {
					for (TaskFunctionIO tfioFrom : tfioListFrom) {
						ioSortKey = "";
						ioName = "";
						targetFunctionID = "";
						targetFunctionIOID = "";

						if (functionListFrom != null) {
							for (Function funcFrom : functionListFrom) {
								if (funcFrom.getId().equals(tfioFrom.getFunctionId())) {
									functionName = funcFrom.getName();
									functionSortKey = funcFrom.getSortKey();
									if (!tfioFrom.getIoId().equals("0")) {
										List<IOPanel> panelList = funcFrom.getIOPanels();
										if (panelList != null) {
											for (IOPanel panel : panelList) {
												if (panel.getId().equals(tfioFrom.getIoId())) {
													ioSortKey = panel.getSortKey();
													ioName = panel.getName();
													break;
												}
											}
										}
										if (ioSortKey.equals("") && ioName.equals("")) {
											List<IOSpool> spoolList = funcFrom.getIOSpools();
											if (spoolList != null) {
												for (IOSpool spool : spoolList) {
													if (spool.getId().equals(tfioFrom.getIoId())) {
														ioSortKey = spool.getSortKey();
														ioName = spool.getName();
														break;
													}
												}
											}
										}
										if (ioSortKey.equals("") && ioName.equals("")) {
											List<IOTable> tableIOList = funcFrom.getIOTables();
											if (tableIOList != null) {
												for (IOTable tableIO : tableIOList) {
													if (tableIO.getId().equals(tfioFrom.getIoId())) {
														if (tableListFrom != null) {
															for (Table table : tableListFrom) {
																if (table.getId().equals(tableIO.getTableId())) {
																	ioSortKey = table.getSortKey();
																	ioName = table.getName();
																	break;
																}
															}
														}
														break;
													}
												}
											}
										}
										if (ioSortKey.equals("") && ioName.equals("")) {
											List<IOWebPage> pageList = funcFrom.getIOWebPages();
											if (pageList != null) {
												for (IOWebPage page : pageList) {
													if (page.getId().equals(tfioFrom.getIoId())) {
														ioSortKey = page.getSortKey();
														ioName = page.getName();
														break;
													}
												}
											}
										}
									}
									break;
								}
							}
						}

						for (Function func : functionListInto) {
							if (func.getSortKey().equals(functionSortKey)) {
								targetFunctionID = func.getId();
								if (tfioFrom.getIoId().equals("0")) {
									targetFunctionIOID = "0";
								} else {
									for (IOPanel panel : func.getIOPanels()) {
										if (panel.getSortKey().equals(ioSortKey)) {
											targetFunctionIOID = panel.getId();
											break;
										}
									}
									if (targetFunctionIOID.equals("")) {
										for (IOSpool spool : func.getIOSpools()) {
											if (spool.getSortKey().equals(ioSortKey)) {
												targetFunctionIOID = spool.getId();
												break;
											}
										}
									}
									if (targetFunctionIOID.equals("")) {
										for (IOTable table : func.getIOTables()) {
											if (table.getSortKey().equals(ioSortKey)) {
												targetFunctionIOID = table.getId();
												break;
											}
										}
									}
									if (targetFunctionIOID.equals("")) {
										for (IOWebPage page : func.getIOWebPages()) {
											if (page.getSortKey().equals(ioSortKey)) {
												targetFunctionIOID = page.getId();
												break;
											}
										}
									}
								}
								break;
							}
						}

						if (targetFunctionID.equals("") || targetFunctionIOID.equals("")) {
							missingFunctionIOCounter++;
							try {
								bufferedWriter.write("  " + functionName + "(" + functionSortKey + ")/" + ioName + "(" + ioSortKey + ")" + res.getString("DialogImportXEAD40") + "\n");
							} catch (IOException ex) {}
						} else {
							TaskFunctionIO tfio = new TaskFunctionIO();
							tfio.setFunctionId(targetFunctionID);
							tfio.setIoId(targetFunctionIOID);
							tfio.setIoIdSeq(tfioFrom.getIoIdSeq());
							tfio.setOperations(tfioFrom.getOperations());
							tfio.setSortKey(tfioFrom.getSortKey());
							taskAction.getTaskFunctionIOs().add(tfio);
						}
					}
				}

				taskInto.getTaskActions().add(taskAction);
			}
		}
	}

	void adjustFunctionsCalled(Function elementFrom, Function functionInto) {
		String internalID1, internalID2;
		int countEquals = 0;
		String functionName = "";
		String functionSortKey = "";
		String functionNameCalling = "";
		String functionSortKeyCalling = "";

		List<Function> functionList = xeadSystemFrom.getFunctions();
		List<FunctionUsedByThis> fubListFrom = elementFrom.getFunctionUsedByThisList();

		if (fubListFrom != null) {
			for (FunctionUsedByThis fubFrom : fubListFrom) {
				internalID1 = fubFrom.getFunctionId();
				internalID2 = convertInternalIDOfTheTag(internalID1, "Function");
				if (internalID2.equals("")) {
					missingFunctionCounter++;
					if (functionList != null) {
						for (Function func : functionList) {
							if (internalID1.equals(func.getId())) {
								functionName = func.getName();
								functionSortKey = func.getSortKey();
							}
							if (elementFrom.getId().equals(func.getId())) {
								functionNameCalling = func.getName();
								functionSortKeyCalling = func.getSortKey();
							}
						}
					}
					try {
						bufferedWriter.write(functionName + "(" + functionSortKey + ")" + res.getString("DialogImportXEAD38") + functionNameCalling + "(" + functionSortKeyCalling + ")" + res.getString("DialogImportXEAD39") +"\n");
					} catch (IOException ex) {}
				} else {
					countEquals = 0;
					FunctionUsedByThis targetFUB = null;
					for (FunctionUsedByThis fub : functionInto.getFunctionUsedByThisList()) {
						if (internalID2.equals(fub.getFunctionId())) {
							targetFUB = fub;
							countEquals++;
							break;
						}
					}

					if (countEquals == 0) {
						FunctionUsedByThis newFUB = new FunctionUsedByThis();
						newFUB.setFunctionId(internalID2);
						newFUB.setLaunchEvent(fubFrom.getLaunchEvent());
						newFUB.setSortKey(fubFrom.getSortKey());
						functionInto.getFunctionUsedByThisList().add(newFUB);
					}

					if (countEquals == 1) {
						targetFUB.setLaunchEvent(fubFrom.getLaunchEvent());
						targetFUB.setSortKey(fubFrom.getSortKey());
					}
				}
			}
		}
	}

	void importTypeDefinitionWithTagName(String tagName) {
		int checkCount = 0;
		int lastID = 0;

		XeadSystem xeadSystem = frame_.getXeadDocumentModel().getXeadSystem();

		if (tagName.equals("Department")) {
			List<Department> deptListFrom = xeadSystemFrom.getDepartments();
			if (deptListFrom != null) {
				jProgressBar.setString("Importing Type List...");
				jProgressBar.setValue(0);
				jProgressBar.setMaximum(deptListFrom.size());

				for (Department deptFrom : deptListFrom) {
					jProgressBar.setValue(jProgressBar.getValue() + 1);
					jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());

					checkCount = 0;
					for (Department dept : xeadSystem.getDepartments()) {
						if (dept.getSortKey().equals(deptFrom.getSortKey())) {
							checkCount++;
							dept.setName(deptFrom.getName());
							dept.setDescriptions(deptFrom.getDescriptions());
							break;
						}
					}
					if (checkCount == 0) {
						lastID = getLastIDOfTheType("Department");
						Department newDept = new Department();
						newDept.setId(Integer.toString(lastID + 1));
						newDept.setSortKey(deptFrom.getSortKey());
						newDept.setName(deptFrom.getName());
						newDept.setDescriptions(deptFrom.getDescriptions());
						xeadSystem.getDepartments().add(newDept);
					}
				}
			}
		} else if (tagName.equals("TaskType")) {
			List<TaskType> ttListFrom = xeadSystemFrom.getTaskTypes();
			if (ttListFrom != null) {
				jProgressBar.setString("Importing Type List...");
				jProgressBar.setValue(0);
				jProgressBar.setMaximum(ttListFrom.size());

				for (TaskType ttFrom : ttListFrom) {
					jProgressBar.setValue(jProgressBar.getValue() + 1);
					jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());

					checkCount = 0;
					for (TaskType tt : xeadSystem.getTaskTypes()) {
						if (tt.getSortKey().equals(ttFrom.getSortKey())) {
							checkCount++;
							tt.setName(ttFrom.getName());
							tt.setDescriptions(ttFrom.getDescriptions());
							break;
						}
					}
					if (checkCount == 0) {
						lastID = getLastIDOfTheType("TaskType");
						TaskType newTT = new TaskType();
						newTT.setId(Integer.toString(lastID + 1));
						newTT.setSortKey(ttFrom.getSortKey());
						newTT.setName(ttFrom.getName());
						newTT.setDescriptions(ttFrom.getDescriptions());
						xeadSystem.getTaskTypes().add(newTT);
					}
				}
			}
		} else if (tagName.equals("TableType")) {
			List<TableType> ttListFrom = xeadSystemFrom.getTableTypes();
			if (ttListFrom != null) {
				jProgressBar.setString("Importing Type List...");
				jProgressBar.setValue(0);
				jProgressBar.setMaximum(ttListFrom.size());

				for (TableType ttFrom : ttListFrom) {
					jProgressBar.setValue(jProgressBar.getValue() + 1);
					jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());

					checkCount = 0;
					for (TableType tt : xeadSystem.getTableTypes()) {
						if (tt.getSortKey().equals(ttFrom.getSortKey())) {
							checkCount++;
							tt.setName(ttFrom.getName());
							tt.setDescriptions(ttFrom.getDescriptions());
							break;
						}
					}
					if (checkCount == 0) {
						lastID = getLastIDOfTheType("TableType");
						TableType newTT = new TableType();
						newTT.setId(Integer.toString(lastID + 1));
						newTT.setSortKey(ttFrom.getSortKey());
						newTT.setName(ttFrom.getName());
						newTT.setDescriptions(ttFrom.getDescriptions());
						xeadSystem.getTableTypes().add(newTT);
					}
				}
			}
		} else if (tagName.equals("FunctionType")) {
			List<FunctionType> ftListFrom = xeadSystemFrom.getFunctionTypes();
			if (ftListFrom != null) {
				jProgressBar.setString("Importing Type List...");
				jProgressBar.setValue(0);
				jProgressBar.setMaximum(ftListFrom.size());

				for (FunctionType ftFrom : ftListFrom) {
					jProgressBar.setValue(jProgressBar.getValue() + 1);
					jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());

					checkCount = 0;
					for (FunctionType ft : xeadSystem.getFunctionTypes()) {
						if (ft.getSortKey().equals(ftFrom.getSortKey())) {
							checkCount++;
							ft.setName(ftFrom.getName());
							ft.setDescriptions(ftFrom.getDescriptions());
							break;
						}
					}
					if (checkCount == 0) {
						lastID = getLastIDOfTheType("FunctionType");
						FunctionType newFT = new FunctionType();
						newFT.setId(Integer.toString(lastID + 1));
						newFT.setSortKey(ftFrom.getSortKey());
						newFT.setName(ftFrom.getName());
						newFT.setDescriptions(ftFrom.getDescriptions());
						xeadSystem.getFunctionTypes().add(newFT);
					}
				}
			}
		} else if (tagName.equals("DataType")) {
			List<DataType> dtListFrom = xeadSystemFrom.getDataTypes();
			if (dtListFrom != null) {
				jProgressBar.setString("Importing Type List...");
				jProgressBar.setValue(0);
				jProgressBar.setMaximum(dtListFrom.size());

				for (DataType dtFrom : dtListFrom) {
					jProgressBar.setValue(jProgressBar.getValue() + 1);
					jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());

					checkCount = 0;
					for (DataType dt : xeadSystem.getDataTypes()) {
						if (dt.getSortKey().equals(dtFrom.getSortKey())) {
							checkCount++;
							dt.setName(dtFrom.getName());
							dt.setBasicType(dtFrom.getBasicType());
							dt.setLength(dtFrom.getLength());
							dt.setDecimal(dtFrom.getDecimal());
							dt.setSqlExpression(dtFrom.getSqlExpression());
							break;
						}
					}
					if (checkCount == 0) {
						lastID = getLastIDOfTheType("DataType");
						DataType newDT = new DataType();
						newDT.setId(Integer.toString(lastID + 1));
						newDT.setSortKey(dtFrom.getSortKey());
						newDT.setName(dtFrom.getName());
						newDT.setBasicType(dtFrom.getBasicType());
						newDT.setLength(dtFrom.getLength());
						newDT.setDecimal(dtFrom.getDecimal());
						newDT.setSqlExpression(dtFrom.getSqlExpression());
						xeadSystem.getDataTypes().add(newDT);
					}
				}
			}
		}
	}

	String convertInternalIDOfTheTypeTag(String originalInternalID, String tagName) {
		String convertedInternalID = "";
		String defaultID = "";
		String defaultSortKey = "";
		String sortKey = "";

		if (tagName.equals("Department")) {
			List<Department> deptListFrom = xeadSystemFrom.getDepartments();
			if (deptListFrom != null) {
				for (Department dept : deptListFrom) {
					if (dept.getId().equals(originalInternalID)) {
						sortKey = dept.getSortKey();
						break;
					}
				}
			}
		} else if (tagName.equals("TaskType")) {
			List<TaskType> ttListFrom = xeadSystemFrom.getTaskTypes();
			if (ttListFrom != null) {
				for (TaskType tt : ttListFrom) {
					if (tt.getId().equals(originalInternalID)) {
						sortKey = tt.getSortKey();
						break;
					}
				}
			}
		} else if (tagName.equals("TableType")) {
			List<TableType> ttListFrom = xeadSystemFrom.getTableTypes();
			if (ttListFrom != null) {
				for (TableType tt : ttListFrom) {
					if (tt.getId().equals(originalInternalID)) {
						sortKey = tt.getSortKey();
						break;
					}
				}
			}
		} else if (tagName.equals("FunctionType")) {
			List<FunctionType> ftListFrom = xeadSystemFrom.getFunctionTypes();
			if (ftListFrom != null) {
				for (FunctionType ft : ftListFrom) {
					if (ft.getId().equals(originalInternalID)) {
						sortKey = ft.getSortKey();
						break;
					}
				}
			}
		} else if (tagName.equals("DataType")) {
			List<DataType> dtListFrom = xeadSystemFrom.getDataTypes();
			if (dtListFrom != null) {
				for (DataType dt : dtListFrom) {
					if (dt.getId().equals(originalInternalID)) {
						sortKey = dt.getSortKey();
						break;
					}
				}
			}
		}

		XeadSystem xeadSystem = frame_.getXeadDocumentModel().getXeadSystem();

		if (tagName.equals("Department")) {
			for (Department dept : xeadSystem.getDepartments()) {
				if (defaultID.equals("") || defaultSortKey.compareTo(dept.getSortKey()) > 0) {
					defaultID = dept.getId();
					defaultSortKey = dept.getSortKey();
				}
				if (dept.getSortKey().equals(sortKey)) {
					convertedInternalID = dept.getId();
				}
			}
		} else if (tagName.equals("TaskType")) {
			for (TaskType tt : xeadSystem.getTaskTypes()) {
				if (defaultID.equals("") || defaultSortKey.compareTo(tt.getSortKey()) > 0) {
					defaultID = tt.getId();
					defaultSortKey = tt.getSortKey();
				}
				if (tt.getSortKey().equals(sortKey)) {
					convertedInternalID = tt.getId();
				}
			}
		} else if (tagName.equals("TableType")) {
			for (TableType tt : xeadSystem.getTableTypes()) {
				if (defaultID.equals("") || defaultSortKey.compareTo(tt.getSortKey()) > 0) {
					defaultID = tt.getId();
					defaultSortKey = tt.getSortKey();
				}
				if (tt.getSortKey().equals(sortKey)) {
					convertedInternalID = tt.getId();
				}
			}
		} else if (tagName.equals("FunctionType")) {
			for (FunctionType ft : xeadSystem.getFunctionTypes()) {
				if (defaultID.equals("") || defaultSortKey.compareTo(ft.getSortKey()) > 0) {
					defaultID = ft.getId();
					defaultSortKey = ft.getSortKey();
				}
				if (ft.getSortKey().equals(sortKey)) {
					convertedInternalID = ft.getId();
				}
			}
		} else if (tagName.equals("DataType")) {
			for (DataType dt : xeadSystem.getDataTypes()) {
				if (defaultID.equals("") || defaultSortKey.compareTo(dt.getSortKey()) > 0) {
					defaultID = dt.getId();
					defaultSortKey = dt.getSortKey();
				}
				if (dt.getSortKey().equals(sortKey)) {
					convertedInternalID = dt.getId();
				}
			}
		}

		if (convertedInternalID.equals("")) {
			convertedInternalID = defaultID;
		}

		return convertedInternalID;
	}

	String convertInternalIDOfTheTag(String originalInternalID, String tagName) {
		String convertedInternalID = "";
		String sortKey = "";
		String name = "";

		if (tagName.equals("Table")) {
			List<Table> tableListFrom = xeadSystemFrom.getTables();
			if (tableListFrom != null) {
				for (Table t : tableListFrom) {
					if (t.getId().equals(originalInternalID)) {
						sortKey = t.getSortKey();
						name = t.getName();
						break;
					}
				}
			}
		} else if (tagName.equals("Function")) {
			List<Function> funcListFrom = xeadSystemFrom.getFunctions();
			if (funcListFrom != null) {
				for (Function f : funcListFrom) {
					if (f.getId().equals(originalInternalID)) {
						sortKey = f.getSortKey();
						name = f.getName();
						break;
					}
				}
			}
		} else if (tagName.equals("Task")) {
			List<Task> taskListFrom = xeadSystemFrom.getTasks();
			if (taskListFrom != null) {
				for (Task t : taskListFrom) {
					if (t.getId().equals(originalInternalID)) {
						sortKey = t.getSortKey();
						name = t.getName();
						break;
					}
				}
			}
		} else if (tagName.equals("Subsystem")) {
			List<Subsystem> subListFrom = xeadSystemFrom.getSubsystems();
			if (subListFrom != null) {
				for (Subsystem s : subListFrom) {
					if (s.getId().equals(originalInternalID)) {
						sortKey = s.getSortKey();
						name = s.getName();
						break;
					}
				}
			}
		} else if (tagName.equals("Role")) {
			List<Role> roleListFrom = xeadSystemFrom.getRoles();
			if (roleListFrom != null) {
				for (Role r : roleListFrom) {
					if (r.getId().equals(originalInternalID)) {
						sortKey = r.getSortKey();
						name = r.getName();
						break;
					}
				}
			}
		} else if (tagName.equals("SubjectArea")) {
			List<SubjectArea> saListFrom = xeadSystemFrom.getSubjectAreas();
			if (saListFrom != null) {
				for (SubjectArea sa : saListFrom) {
					if (sa.getId().equals(originalInternalID)) {
						sortKey = sa.getSortKey();
						name = sa.getName();
						break;
					}
				}
			}
		}

		if (!sortKey.equals("")) {
			XeadSystem xeadSystem = frame_.getXeadDocumentModel().getXeadSystem();
			if (tagName.equals("Table")) {
				for (Table t : xeadSystem.getTables()) {
					if (t.getSortKey().equals(sortKey) && t.getName().equals(name)) {
						convertedInternalID = t.getId();
						break;
					}
				}
			} else if (tagName.equals("Function")) {
				for (Function f : xeadSystem.getFunctions()) {
					if (f.getSortKey().equals(sortKey) && f.getName().equals(name)) {
						convertedInternalID = f.getId();
						break;
					}
				}
			} else if (tagName.equals("Task")) {
				for (Task t : xeadSystem.getTasks()) {
					if (t.getSortKey().equals(sortKey) && t.getName().equals(name)) {
						convertedInternalID = t.getId();
						break;
					}
				}
			} else if (tagName.equals("Subsystem")) {
				for (Subsystem s : xeadSystem.getSubsystems()) {
					if (s.getSortKey().equals(sortKey) && s.getName().equals(name)) {
						convertedInternalID = s.getId();
						break;
					}
				}
			} else if (tagName.equals("Role")) {
				for (Role r : xeadSystem.getRoles()) {
					if (r.getSortKey().equals(sortKey) && r.getName().equals(name)) {
						convertedInternalID = r.getId();
						break;
					}
				}
			} else if (tagName.equals("SubjectArea")) {
				for (SubjectArea sa : xeadSystem.getSubjectAreas()) {
					if (sa.getSortKey().equals(sortKey) && sa.getName().equals(name)) {
						convertedInternalID = sa.getId();
						break;
					}
				}
			}
		}

		return convertedInternalID;
	}

	String convertInternalIDOfTableField(String originalInternalTableID, String originalInternalFieldID, XeadSystem xeadSystemInto) {
		String convertedInternalFieldID = "";
		String externalIDOfTable = "";
		String externalIDOfField = "";
		String nameOfField = "";

		List<Table> tableListFrom = xeadSystemFrom.getTables();
		Table sourceTable = null;
		if (tableListFrom != null) {
			for (Table t : tableListFrom) {
				if (t.getId().equals(originalInternalTableID)) {
					externalIDOfTable = t.getSortKey();
					sourceTable = t;
					break;
				}
			}
		}

		if (sourceTable != null) {
			List<TableField> fieldList = sourceTable.getTableFields();
			if (fieldList != null) {
				for (TableField tf : fieldList) {
					if (tf.getId().equals(originalInternalFieldID)) {
						externalIDOfField = tf.getAlias();
						nameOfField = tf.getName();
						break;
					}
				}
			}

			if (!externalIDOfTable.equals("")  && !nameOfField.equals("")) {
				Table targetTable = null;
				for (Table t : xeadSystemInto.getTables()) {
					if (t.getSortKey().equals(externalIDOfTable)) {
						targetTable = t;
						break;
					}
				}

				if (targetTable != null) {
					for (TableField tf : targetTable.getTableFields()) {
						if (tf.getAlias().equals(externalIDOfField) && tf.getName().equals(nameOfField)) {
							convertedInternalFieldID = tf.getId();
							break;
						}
						if (tf.getAlias().equals(externalIDOfField) && !tf.getAlias().equals("")) {
							convertedInternalFieldID = tf.getId();
							break;
						}
					}
				}
			}
		}

		return convertedInternalFieldID;
	}

	String convertTableKeyID(String originalInternalTableID, String originalInternalKeyID, String targetTableID) {
		return convertTableKeyID(originalInternalTableID, originalInternalKeyID, targetTableID, frame_.getXeadDocumentModel().getXeadSystem());
	}

	String convertTableKeyID(String originalInternalTableID, String originalInternalKeyID, String targetTableID, XeadSystem xeadSystemInto) {
		String convertedInternalKeyID = "";
		String keyType = "";
		TableKey sourceKey = null;

		List<Table> tableListFrom = xeadSystemFrom.getTables();
		Table sourceTable = null;
		if (tableListFrom != null) {
			for (Table t : tableListFrom) {
				if (t.getId().equals(originalInternalTableID)) {
					sourceTable = t;
					break;
				}
			}
		}

		if (sourceTable != null) {
			List<TableKey> keyList = sourceTable.getTableKeys();
			if (keyList != null) {
				for (TableKey tk : keyList) {
					if (tk.getId().equals(originalInternalKeyID)) {
						keyType = tk.getType();
						sourceKey = tk;
						break;
					}
				}
			}
		}

		Table targetTable = null;
		for (Table t : xeadSystemInto.getTables()) {
			if (t.getId().equals(targetTableID)) {
				targetTable = t;
				break;
			}
		}

		if (targetTable != null && sourceKey != null) {
			for (TableKey tk : targetTable.getTableKeys()) {
				if (tk.getType().equals(keyType)) {
					if (theseAreIdenticalKeyDefinitions(sourceKey, tk, originalInternalTableID, xeadSystemInto)) {
						convertedInternalKeyID = tk.getId();
					}
				}
			}
		}
		return convertedInternalKeyID;
	}

	int getLastIDOfTheType(String tagName) {
		XeadSystem xeadSystem = frame_.getXeadDocumentModel().getXeadSystem();
		int lastID = 0;
		if (tagName.equals("Subsystem")) {
			for (Subsystem sub : xeadSystem.getSubsystems()) {
				int id = Integer.parseInt(sub.getId());
				if (id > lastID) lastID = id;
			}
		} else if (tagName.equals("Role")) {
			for (Role role : xeadSystem.getRoles()) {
				int id = Integer.parseInt(role.getId());
				if (id > lastID) lastID = id;
			}
		} else if (tagName.equals("SubjectArea")) {
			for (SubjectArea sa : xeadSystem.getSubjectAreas()) {
				int id = Integer.parseInt(sa.getId());
				if (id > lastID) lastID = id;
			}
		} else if (tagName.equals("DataflowNode")) {
			for (SubjectArea sa : xeadSystem.getSubjectAreas()) {
				if (sa.getDataflowNodes() != null) {
					for (DataflowNode node : sa.getDataflowNodes()) {
						int id = Integer.parseInt(node.getId());
						if (id > lastID) lastID = id;
					}
				}
			}
		} else if (tagName.equals("DataflowLine")) {
			for (SubjectArea sa : xeadSystem.getSubjectAreas()) {
				if (sa.getDataflowLines() != null) {
					for (DataflowLine line : sa.getDataflowLines()) {
						int id = Integer.parseInt(line.getId());
						if (id > lastID) lastID = id;
					}
				}
			}
		} else if (tagName.equals("Table")) {
			for (Table t : xeadSystem.getTables()) {
				int id = Integer.parseInt(t.getId());
				if (id > lastID) lastID = id;
			}
		} else if (tagName.equals("TableField")) {
			for (Table t : xeadSystem.getTables()) {
				for (TableField tf : t.getTableFields()) {
					int id = Integer.parseInt(tf.getId());
					if (id > lastID) lastID = id;
				}
			}
		} else if (tagName.equals("TableKey")) {
			for (Table t : xeadSystem.getTables()) {
				for (TableKey tk : t.getTableKeys()) {
					int id = Integer.parseInt(tk.getId());
					if (id > lastID) lastID = id;
				}
			}
		} else if (tagName.equals("Function")) {
			for (Function f : xeadSystem.getFunctions()) {
				int id = Integer.parseInt(f.getId());
				if (id > lastID) lastID = id;
			}
		} else if (tagName.equals("Task")) {
			for (Task t : xeadSystem.getTasks()) {
				int id = Integer.parseInt(t.getId());
				if (id > lastID) lastID = id;
			}
		} else if (tagName.equals("TableType")) {
			for (TableType tt : xeadSystem.getTableTypes()) {
				int id = Integer.parseInt(tt.getId());
				if (id > lastID) lastID = id;
			}
		} else if (tagName.equals("DataType")) {
			for (DataType dt : xeadSystem.getDataTypes()) {
				int id = Integer.parseInt(dt.getId());
				if (id > lastID) lastID = id;
			}
		} else if (tagName.equals("FunctionType")) {
			for (FunctionType ft : xeadSystem.getFunctionTypes()) {
				int id = Integer.parseInt(ft.getId());
				if (id > lastID) lastID = id;
			}
		} else if (tagName.equals("TaskType")) {
			for (TaskType tt : xeadSystem.getTaskTypes()) {
				int id = Integer.parseInt(tt.getId());
				if (id > lastID) lastID = id;
			}
		} else if (tagName.equals("Department")) {
			for (Department d : xeadSystem.getDepartments()) {
				int id = Integer.parseInt(d.getId());
				if (id > lastID) lastID = id;
			}
		}
		return lastID;
	}

	/**
	 * Check if two TableKey POJOs have identical definitions
	 */
	boolean theseAreIdenticalKeyDefinitions(TableKey keyFrom, TableKey keyInto,
			String tableIDFrom, XeadSystem xeadSystem) {
		if (keyFrom == null || keyInto == null) return false;
		if (!keyFrom.getType().equals(keyInto.getType())) return false;

		List<TableKeyField> keyFieldListFrom = keyFrom.getTableKeyFields();
		if (keyFieldListFrom == null || keyFieldListFrom.size() != keyInto.getTableKeyFields().size()) return false;

		String[] keyFieldIDFrom = new String[30];
		int countOfKeyFieldFrom = 0;
		for (TableKeyField tkf : keyFieldListFrom) {
			keyFieldIDFrom[countOfKeyFieldFrom] = tkf.getFieldId();
			countOfKeyFieldFrom++;
		}

		String[] keyFieldIDInto = new String[30];
		int countOfKeyFieldInto = 0;
		for (TableKeyField tkf : keyInto.getTableKeyFields()) {
			keyFieldIDInto[countOfKeyFieldInto] = tkf.getFieldId();
			countOfKeyFieldInto++;
		}

		if (countOfKeyFieldInto != countOfKeyFieldFrom) return false;

		int checkCount = 0;
		for (int i = 0; i < countOfKeyFieldFrom; i++) {
			String workStr = convertInternalIDOfTableField(tableIDFrom, keyFieldIDFrom[i], xeadSystem);
			for (int j = 0; j < countOfKeyFieldInto; j++) {
				if (workStr.equals(keyFieldIDInto[j])) {
					keyFieldIDInto[j] = "";
					checkCount++;
				}
			}
		}
		return checkCount == countOfKeyFieldInto;
	}

	int getNextIDOfFunctionIOs() {
		int lastID = 0;
		XeadSystem xeadSystem = frame_.getXeadDocumentModel().getXeadSystem();
		List<Function> allFunctions = xeadSystem.getFunctions();
		if (allFunctions != null) {
			for (Function func : allFunctions) {
				if (func.getIOPanels() != null) {
					for (IOPanel panel : func.getIOPanels()) {
						int id = Integer.parseInt(panel.getId());
						if (id > lastID) lastID = id;
					}
				}
				if (func.getIOWebPages() != null) {
					for (IOWebPage page : func.getIOWebPages()) {
						int id = Integer.parseInt(page.getId());
						if (id > lastID) lastID = id;
					}
				}
				if (func.getIOSpools() != null) {
					for (IOSpool spool : func.getIOSpools()) {
						int id = Integer.parseInt(spool.getId());
						if (id > lastID) lastID = id;
					}
				}
				if (func.getIOTables() != null) {
					for (IOTable table : func.getIOTables()) {
						int id = Integer.parseInt(table.getId());
						if (id > lastID) lastID = id;
					}
				}
			}
		}
		lastID++;
		return lastID;
	}

	void jButtonCancel_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}

	class SortableXeadNodeComboBoxModel extends DefaultComboBoxModel {
		private static final long serialVersionUID = 1L;
		public void sortElements() {
			ArrayList<Comparable> list = new ArrayList<Comparable>();
			for (int i = 0; i < this.getSize(); i++) {
				list.add((Comparable)this.getElementAt(i));
			}
			this.removeAllElements();
			Collections.sort(list);
			Iterator<Comparable> it = list.iterator();
			while(it.hasNext()){
				this.addElement(it.next());
			}
		}
	}

	/**
	 * Wrapper for Subsystem JSON object
	 */
	class XeadNodeFromSubsystem implements Comparable<XeadNodeFromSubsystem> {
		private Subsystem subsystem;
		
		public XeadNodeFromSubsystem(Subsystem subsystem) {
			this.subsystem = subsystem;
		}
		
		public String toString() {
			String sortKey = subsystem.getSortKey();
			if (sortKey == null || sortKey.equals("")) {
				return subsystem.getId() + " " + subsystem.getName();
			} else {
				return sortKey + " " + subsystem.getName();
			}
		}
		
		public Subsystem getSubsystem() {
			return subsystem;
		}
		
		public String getSortKey() {
			return subsystem.getSortKey();
		}
		
		public int compareTo(XeadNodeFromSubsystem other) {
			return this.getSortKey().compareTo(other.getSortKey());
		}
	}

	/**
	 * Wrapper for Role JSON object
	 */
	class XeadNodeFromRole implements Comparable<XeadNodeFromRole> {
		private Role role;
		
		public XeadNodeFromRole(Role role) {
			this.role = role;
		}
		
		public String toString() {
			String sortKey = role.getSortKey();
			if (sortKey == null || sortKey.equals("")) {
				return role.getId() + " " + role.getName();
			} else {
				return sortKey + " " + role.getName();
			}
		}
		
		public Role getRole() {
			return role;
		}
		
		public String getSortKey() {
			return role.getSortKey();
		}
		
		public int compareTo(XeadNodeFromRole other) {
			return this.getSortKey().compareTo(other.getSortKey());
		}
	}

	/**
	 * Wrapper for SubjectArea JSON object
	 */
	class XeadNodeFromSubjectArea implements Comparable<XeadNodeFromSubjectArea> {
		private SubjectArea subjectArea;
		
		public XeadNodeFromSubjectArea(SubjectArea subjectArea) {
			this.subjectArea = subjectArea;
		}
		
		public String toString() {
			String sortKey = subjectArea.getSortKey();
			if (sortKey == null || sortKey.equals("")) {
				return subjectArea.getId() + " " + subjectArea.getName();
			} else {
				return sortKey + " " + subjectArea.getName();
			}
		}
		
		public SubjectArea getSubjectArea() {
			return subjectArea;
		}
		
		public String getSortKey() {
			return subjectArea.getSortKey();
		}
		
		public int compareTo(XeadNodeFromSubjectArea other) {
			return this.getSortKey().compareTo(other.getSortKey());
		}
	}
}

class DialogImportXEAD_jComboBoxBlockFrom_actionAdapter implements java.awt.event.ActionListener {
	DialogImportXEAD adaptee;

	DialogImportXEAD_jComboBoxBlockFrom_actionAdapter(DialogImportXEAD adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.jComboBoxBlockFrom_actionPerformed(e);
	}
}

//class DialogImportXEAD_jComboBoxBlockInto_actionAdapter implements java.awt.event.ActionListener {
//	DialogImportXEAD adaptee;
//
//	DialogImportXEAD_jComboBoxBlockInto_actionAdapter(DialogImportXEAD adaptee) {
//		this.adaptee = adaptee;
//	}
//	public void actionPerformed(ActionEvent e) {
//		adaptee.jComboBoxBlockInto_actionPerformed(e);
//	}
//}

class DialogImportXEAD_jRadioButton_itemAdapter implements ItemListener {
	DialogImportXEAD adaptee;

	DialogImportXEAD_jRadioButton_itemAdapter(DialogImportXEAD adaptee) {
		this.adaptee = adaptee;
	}
	public void itemStateChanged(ItemEvent e) {
		adaptee.jRadioButton_itemStateChanged();
	}
}

class DialogImportXEAD_jButtonStart_actionAdapter implements java.awt.event.ActionListener {
	DialogImportXEAD adaptee;

	DialogImportXEAD_jButtonStart_actionAdapter(DialogImportXEAD adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.jButtonStart_actionPerformed(e);
	}
}

class DialogImportXEAD_jButtonCancel_actionAdapter implements java.awt.event.ActionListener {
	DialogImportXEAD adaptee;

	DialogImportXEAD_jButtonCancel_actionAdapter(DialogImportXEAD adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.jButtonCancel_actionPerformed(e);
	}
}
