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
import org.apache.xerces.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;

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
	private org.w3c.dom.Document domDocumentFrom;
	private org.w3c.dom.Element blockElementFrom;
	private Subsystem blockSubsystemInto;
	private Role blockRoleInto;
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
		NodeList elementList;
		org.w3c.dom.Element element;
		importResult = "";

		domDocumentFrom = null;
		jRadioButtonFunctions.setSelected(true);

		try {
			DOMParser parser = new DOMParser();
			parser.parse(new InputSource(new FileInputStream(fileName)));
			domDocumentFrom = parser.getDocument();
		} catch (IOException ex) {
		} catch (SAXException ex) {
		}

		elementList = domDocumentFrom.getElementsByTagName("System");
		element = (org.w3c.dom.Element)elementList.item(0);
		float importFileFormat = Float.parseFloat(element.getAttribute("FormatVersion"));
		float appliFormat = Float.parseFloat(DialogAbout.FORMAT_VERSION);
		if (importFileFormat > appliFormat) {
			JOptionPane.showMessageDialog(this, res.getString("S1") + element.getAttribute("FormatVersion") + res.getString("S2") + DialogAbout.FORMAT_VERSION + res.getString("S3"));
		} else {
			jTextFieldImportFileName.setText(fileName);
			jTextFieldImportSystemName.setText(element.getAttribute("Name"));
			jRadioButton_itemStateChanged();
			jProgressBar.setValue(0);
			super.setVisible(true);
		}

		return importResult;
	}

	void jRadioButton_itemStateChanged() {
		XeadNode node;
		NodeList elementList;

		try {
			setCursor(new Cursor(Cursor.WAIT_CURSOR));
			if (domDocumentFrom != null) {

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
					elementList = domDocumentFrom.getElementsByTagName("Subsystem");
					for (int i = 0; i < elementList.getLength(); i++) {
						node = new XeadNode("Subsystem",(org.w3c.dom.Element)elementList.item(i));
						comboBoxModelBlockFrom.addElement((Object)node);
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
					elementList = domDocumentFrom.getElementsByTagName("Role");
					for (int i = 0; i < elementList.getLength(); i++) {
						node = new XeadNode("Role",(org.w3c.dom.Element)elementList.item(i));
						comboBoxModelBlockFrom.addElement((Object)node);
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
					elementList = domDocumentFrom.getElementsByTagName("SubjectArea");
					for (int i = 0; i < elementList.getLength(); i++) {
						node = new XeadNode("SubjectArea",(org.w3c.dom.Element)elementList.item(i));
						comboBoxModelBlockFrom.addElement((Object)node);
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
		XeadNode workNode;
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

			workNode = (XeadNode)comboBoxModelBlockFrom.getSelectedItem();
			blockElementFrom = workNode.getElement();
			blockIDFrom = blockElementFrom.getAttribute("ID");
			bufferedWriter.write(jLabel1.getText() + ":" + blockElementFrom.getAttribute("Name") + "(" + blockElementFrom.getAttribute("SortKey") + ")" + "\n");

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
		org.w3c.dom.Element workElement;
		NodeList workElementList, targetElementList;
		org.w3c.dom.Element[] elementListFrom = new org.w3c.dom.Element[500];
		int itemsOfElementListFrom = 0;
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
		workElementList = domDocumentFrom.getElementsByTagName("Table");
		for (int i = 0; i < workElementList.getLength(); i++) {
			workElement = (org.w3c.dom.Element)workElementList.item(i);
			if (!workElement.getAttribute("SortKey").equals("") && workElement.getAttribute("SubsystemID").equals(blockIDFrom)) {
				elementListFrom[itemsOfElementListFrom] = workElement;
				itemsOfElementListFrom++;
			}
		}

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
			jProgressBar.setValue(jProgressBar.getValue() + 1);
			jProgressBar.setString((i+1) + "/" + itemsOfElementListFrom);
			jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());

			checkCounter = 0;
			sortKeyOfDefinition = elementListFrom[i].getAttribute("SortKey");
			Table targetTable = null;
			for (int n = 0; n < targetTableList.size(); n++) {
				targetTable = targetTableList.get(n);
				if (targetTable.getSortKey().equals(sortKeyOfDefinition)) {
					checkCounter++;
					if (targetTable.getSubsystemID().equals(blockIDInto)) {
						try {
							bufferedWriter.write(elementListFrom[i].getAttribute("Name") +
								"(" + elementListFrom[i].getAttribute("SortKey") +
								"):" + res.getString("DialogImportXEAD33") + "\n");
						} catch (IOException ex1) {}
						updateTableDefinition(elementListFrom[i], targetTable);
						updateTableCounter++;
					} else {
						try {
							bufferedWriter.write(elementListFrom[i].getAttribute("Name") +
								"(" + elementListFrom[i].getAttribute("SortKey") +
								"):" + res.getString("DialogImportXEAD48") + "\n");
						} catch (IOException ex1) {}
						cancelTableCounter++;
					}
					break;
				}
			}

			if (checkCounter == 0) {
				createTableDefinition(elementListFrom[i], blockIDInto);
				createTableCounter++;
				try {
					bufferedWriter.write(elementListFrom[i].getAttribute("Name") + "(" +
							elementListFrom[i].getAttribute("SortKey") +
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

		workElementList = domDocumentFrom.getElementsByTagName("Relationship");
		jProgressBar.setValue(0);
		jProgressBar.setString("Importing Table Relationships");
		jProgressBar.setMaximum(workElementList.getLength());

		for (int i = 0; i < workElementList.getLength(); i++) {
			jProgressBar.setValue(jProgressBar.getValue() + 1);
			jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());

			workElement = (org.w3c.dom.Element)workElementList.item(i);
			table1ID = workElement.getAttribute("Table1ID");
			table2ID = workElement.getAttribute("Table2ID");
			checkCounter = 0;
			for (int m = 0; m < itemsOfElementListFrom; m++) {
				tableID = elementListFrom[m].getAttribute("ID");
				if (tableID.equals(table1ID)) {
					checkCounter++;
				}
				if (tableID.equals(table2ID)) {
					checkCounter++;
				}
			}
			if (checkCounter >= 1) {
				adjustTableRelationship(workElement);
			}
		}
	}

	void importFunctions() {
		org.w3c.dom.Element workElement;
		NodeList workElementList;
		org.w3c.dom.Element[] elementListFrom = new org.w3c.dom.Element[500];
		int itemsOfElementListFrom = 0;
		int checkCounter = 0;
		String sortKeyOfDefinition;

		////////////////////////
		//Import Function Type//
		////////////////////////
		importTypeDefinitionWithTagName("FunctionType");

		//////////////////////////////////////////
		//Setup List of Functions to be imported//
		/////////////////////////////////////////
		workElementList = domDocumentFrom.getElementsByTagName("Function");
		for (int i = 0; i < workElementList.getLength(); i++) {
			workElement = (org.w3c.dom.Element)workElementList.item(i);
			if (!workElement.getAttribute("SortKey").equals("") && workElement.getAttribute("SubsystemID").equals(blockIDFrom)) {
				elementListFrom[itemsOfElementListFrom] = workElement;
				itemsOfElementListFrom++;
			}
		}

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
				if (func.getSubsystemID().equals(blockIDInto)) {
					targetFunctionArray.add(func);
				}
			}
		}
		
		for (int i = 0; i < itemsOfElementListFrom; i++) {
			jProgressBar.setValue(jProgressBar.getValue() + 1);
			jProgressBar.setString((i+1) + "/" + itemsOfElementListFrom);
			jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());

			checkCounter = 0;
			sortKeyOfDefinition = elementListFrom[i].getAttribute("SortKey");

			Function targetFunction = null;
			for (int n = 0; n < targetFunctionArray.size(); n++) {
				targetFunction = targetFunctionArray.get(n);
				if (targetFunction.getSortKey().equals(sortKeyOfDefinition)) {
					checkCounter++;
					try {
						bufferedWriter.write(elementListFrom[i].getAttribute("Name") +
								"(" + sortKeyOfDefinition +
								"):" + res.getString("DialogImportXEAD33") + "\n");
					} catch (IOException ex1) {}

					updateFunctionDefinition(elementListFrom[i], targetFunction);
					updateFunctionCounter++;
					break;
				}
			}

			if (checkCounter == 0) {
				try {
					bufferedWriter.write(elementListFrom[i].getAttribute("Name") + "(" +
							elementListFrom[i].getAttribute("SortKey") + "):" +
							res.getString("DialogImportXEAD34") + "\n");
				} catch (IOException ex2) {}

				createFunctionDefinition(elementListFrom[i], blockIDInto);
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
			jProgressBar.setValue(jProgressBar.getValue() + 1);
			jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());

 		sortKeyOfDefinition = elementListFrom[i].getAttribute("SortKey");
			Function targetFunction = null;
			for (int n = 0; n < targetFunctionArray.size(); n++) {
				targetFunction = targetFunctionArray.get(n);
				if (targetFunction.getSortKey().equals(sortKeyOfDefinition)) {
					adjustFunctionsCalled(elementListFrom[i], targetFunction);
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
		blockRoleInto.setName(blockElementFrom.getAttribute("Name"));
		blockRoleInto.setSortKey(blockElementFrom.getAttribute("SortKey"));
		blockRoleInto.setDescriptions(blockElementFrom.getAttribute("Descriptions"));
		blockRoleInto.setDepartmentId(convertInternalIDOfTheTypeTag(blockElementFrom.getAttribute("DepartmentID"), "Department"));
	}

	void importTasks() {
		org.w3c.dom.Element workElement;
		NodeList workElementList, targetElementList;
		org.w3c.dom.Element[] elementListFrom = new org.w3c.dom.Element[500];
		int itemsOfElementListFrom = 0;
		int checkCounter = 0;
		String sortKeyOfDefinition;

		///////////////////
		//Import TaskType//
		///////////////////
		importTypeDefinitionWithTagName("TaskType");

		//////////////////////////////////////
		//Setup List of Tasks to be imported//
		//////////////////////////////////////
		workElementList = domDocumentFrom.getElementsByTagName("Task");
		for (int i = 0; i < workElementList.getLength(); i++) {
			workElement = (org.w3c.dom.Element)workElementList.item(i);
			if (!workElement.getAttribute("SortKey").equals("") && workElement.getAttribute("RoleID").equals(blockIDFrom)) {
				elementListFrom[itemsOfElementListFrom] = workElement;
				itemsOfElementListFrom++;
			}
		}

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
			jProgressBar.setValue(jProgressBar.getValue() + 1);
			jProgressBar.setString((i+1) + "/" + workElementList.getLength());
			jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());

			try {
				bufferedWriter.write(elementListFrom[i].getAttribute("Name") + "(" +
						elementListFrom[i].getAttribute("SortKey") + "):");
			} catch (IOException ex1) {}

			checkCounter = 0;
			sortKeyOfDefinition = elementListFrom[i].getAttribute("SortKey");
			Task targetTask = null;
			for (int n = 0; n < targetTaskList.size(); n++) {
				targetTask = targetTaskList.get(n);
				if (targetTask.getSortKey().equals(sortKeyOfDefinition)) {
					checkCounter++;
					if (targetTask.getRoleID().equals(blockIDInto)) {
						try {
							bufferedWriter.write(res.getString("DialogImportXEAD33") + "\n");
						} catch (IOException ex2) {}

						updateTaskDefinition(elementListFrom[i], targetTask);
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

				createTaskDefinition(elementListFrom[i], blockIDInto);
				createTaskCounter++;
			}
		}
	}
	
	String checkTaskDefinition(org.w3c.dom.Element nodeElementFrom) {
		String targetTaskID = "";
		String taskSortKey = "";
		org.w3c.dom.Element workElement;
		NodeList taskListFrom = domDocumentFrom.getElementsByTagName("Task");
		for (int i = 0; i < taskListFrom.getLength(); i++) {
			workElement = (org.w3c.dom.Element)taskListFrom.item(i);
			if (workElement.getAttribute("ID").equals(nodeElementFrom.getAttribute("TaskID"))) {
				taskSortKey = workElement.getAttribute("SortKey");
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
	
	String checkRoleDefinition(org.w3c.dom.Element nodeElementFrom) {
		String targetRoleID = "";
		String roleSortKey = "";
		org.w3c.dom.Element workElement;
		NodeList roleListFrom = domDocumentFrom.getElementsByTagName("Role");
		for (int i = 0; i < roleListFrom.getLength(); i++) {
			workElement = (org.w3c.dom.Element)roleListFrom.item(i);
			if (workElement.getAttribute("ID").equals(nodeElementFrom.getAttribute("RoleID"))) {
				roleSortKey = workElement.getAttribute("SortKey");
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
		org.w3c.dom.Element workElement1, workElement2, newElement, lastElement;
		NodeList workElementList;
		int lastID;
		String taskIDInto, roleIDInto;
		ArrayList<String> nodeIDListFrom = new ArrayList<String>();
		ArrayList<String> nodeIDListInto = new ArrayList<String>();
		
		workElementList = blockElementFrom.getElementsByTagName("DataflowNode");
		int countOfElements = workElementList.getLength() * 2;
		workElementList = blockElementFrom.getElementsByTagName("DataflowLine");
		countOfElements = countOfElements + workElementList.getLength();
		jProgressBar.setValue(0);
		jProgressBar.setMaximum(countOfElements);
		
		///////////////////////////////////////////////////////////////
		// Check if task(process) definitions exist in target system //
		///////////////////////////////////////////////////////////////
		workElementList = blockElementFrom.getElementsByTagName("DataflowNode");
		for (int i = 0; i < workElementList.getLength(); i++) {
			jProgressBar.setValue(jProgressBar.getValue() + 1);
			jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());

			workElement1 = (org.w3c.dom.Element)workElementList.item(i);
			if (workElement1.getAttribute("Type").equals("Process")) {
				taskIDInto = checkTaskDefinition(workElement1);
				if (taskIDInto.equals("")) {
					cancelTaskCounter++;
					try {
						NodeList taskListFrom = domDocumentFrom.getElementsByTagName("Task");
						for (int j = 0; j < taskListFrom.getLength(); j++) {
							workElement2 = (org.w3c.dom.Element)taskListFrom.item(j);
							if (workElement2.getAttribute("ID").equals(workElement1.getAttribute("TaskID"))) {
								bufferedWriter.write(workElement2.getAttribute("Name") + "(" +
										workElement2.getAttribute("SortKey") + 
										"):" + res.getString("DialogImportXEAD54") + "\n");
							}
						}
					}
					catch (IOException ex1) {}
					break;
				}
			}
			if (workElement1.getAttribute("Type").equals("Subject")
					&& !workElement1.getAttribute("RoleID").equals("")) {
				roleIDInto = checkRoleDefinition(workElement1);
				if (roleIDInto.equals("")) {
					cancelRoleCounter++;
					try {
						NodeList roleListFrom = domDocumentFrom.getElementsByTagName("Role");
						for (int j = 0; j < roleListFrom.getLength(); j++) {
							workElement2 = (org.w3c.dom.Element)roleListFrom.item(j);
							if (workElement2.getAttribute("ID").equals(workElement1.getAttribute("RoleID"))) {
								bufferedWriter.write(workElement2.getAttribute("Name") + "(" +
										workElement2.getAttribute("SortKey") + 
										"):" + res.getString("DialogImportXEAD57") + "\n");
							}
						}
					}
					catch (IOException ex1) {}
					break;
				}
			}
		}
		
 	if (cancelTaskCounter == 0 && cancelRoleCounter == 0) {
		
 		////////////////////////////////////////
 		//Update attributes of target DataFlow//
 		////////////////////////////////////////
 		blockSubjectAreaInto.setName(blockElementFrom.getAttribute("Name"));
 		blockSubjectAreaInto.setDescriptions(blockElementFrom.getAttribute("Descriptions"));
 		blockSubjectAreaInto.setBoundaryPosition(blockElementFrom.getAttribute("BoundaryPosition"));
 		blockSubjectAreaInto.setBoundarySize(blockElementFrom.getAttribute("BoundarySize"));
 		blockSubjectAreaInto.setSortKey(blockElementFrom.getAttribute("SortKey"));

 		// Clear existing dataflow nodes and lines
 		blockSubjectAreaInto.getDataflowNodes().clear();
 		blockSubjectAreaInto.getDataflowLines().clear();

 		workElementList = blockElementFrom.getElementsByTagName("DataflowNode");
			for (int i = 0; i < workElementList.getLength(); i++) {
				jProgressBar.setValue(jProgressBar.getValue() + 1);
				jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());

				workElement1 = (org.w3c.dom.Element)workElementList.item(i);

				DataflowNode dataflowNode = new DataflowNode();
				lastID = getLastIDOfTheType("DataflowNode");
				dataflowNode.setId(Integer.toString(lastID + 1));
				dataflowNode.setName(workElement1.getAttribute("Name"));
				dataflowNode.setPosition(workElement1.getAttribute("Position"));
 			dataflowNode.setSlideNumber(Integer.parseInt(workElement1.getAttribute("SlideNumber")));
				if (workElement1.getAttribute("Type").equals("Process")) {
					taskIDInto = checkTaskDefinition(workElement1);
					dataflowNode.setTaskID(taskIDInto);
					dataflowNode.setEventPos(workElement1.getAttribute("EventPos"));
				} else {
					if (workElement1.getAttribute("Type").equals("Subject")
							&& !workElement1.getAttribute("RoleID").equals("")) {
						roleIDInto = checkRoleDefinition(workElement1);
						dataflowNode.setRoleID(roleIDInto);
					} else {
						dataflowNode.setNameExt(workElement1.getAttribute("NameExt"));
					}
				}
				dataflowNode.setType(workElement1.getAttribute("Type"));
				dataflowNode.setDescriptions(workElement1.getAttribute("Descriptions"));
				blockSubjectAreaInto.getDataflowNodes().add(dataflowNode);

				nodeIDListFrom.add(workElement1.getAttribute("ID"));
				nodeIDListInto.add(dataflowNode.getId());
			}

			workElementList = blockElementFrom.getElementsByTagName("DataflowLine");
			for (int i = 0; i < workElementList.getLength(); i++) {
				jProgressBar.setValue(jProgressBar.getValue() + 1);
				jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());

				workElement1 = (org.w3c.dom.Element)workElementList.item(i);

				DataflowLine dataflowLine = new DataflowLine();
				lastID = getLastIDOfTheType("DataflowLine");
				dataflowLine.setId(Integer.toString(lastID + 1));
				dataflowLine.setName(workElement1.getAttribute("Name"));
				dataflowLine.setNameExt(workElement1.getAttribute("NameExt"));
				dataflowLine.setImageType(workElement1.getAttribute("ImageType"));
 			dataflowLine.setNodeID1(nodeIDListInto.get(nodeIDListFrom.indexOf(workElement1.getAttribute("NodeID1"))));
				dataflowLine.setNodeID2(nodeIDListInto.get(nodeIDListFrom.indexOf(workElement1.getAttribute("NodeID2"))));
				dataflowLine.setShowArrow1(workElement1.getAttribute("ShowArrow1"));
				dataflowLine.setShowArrow2(workElement1.getAttribute("ShowArrow2"));
				dataflowLine.setTerminalPosIndex1(Integer.parseInt(workElement1.getAttribute("TerminalPosIndex1")));
				dataflowLine.setTerminalPosIndex2(Integer.parseInt(workElement1.getAttribute("TerminalPosIndex2")));
				dataflowLine.setSlideNumber(Integer.parseInt(workElement1.getAttribute("SlideNumber")));
				blockSubjectAreaInto.getDataflowLines().add(dataflowLine);
			}
			try {
				bufferedWriter.write(res.getString("DialogImportXEAD33") + "\n");
			} catch (IOException ex3) {
			}
		}
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

	void updateFunctionDefinition(org.w3c.dom.Element elementFrom, Function functionInto) {
		String internalID;

		functionInto.setName(elementFrom.getAttribute("Name"));
		functionInto.setSummary(elementFrom.getAttribute("Summary"));
		functionInto.setParameters(elementFrom.getAttribute("Parameters"));
		functionInto.setReturn(elementFrom.getAttribute("Return"));
		functionInto.setDescriptions(elementFrom.getAttribute("Descriptions"));
		internalID = convertInternalIDOfTheTypeTag(elementFrom.getAttribute("FunctionTypeID"), "FunctionType");
		functionInto.setFunctionTypeId(internalID);

		adjustIOPanelDefinition(elementFrom, functionInto);
		adjustIOWebPageDefinition(elementFrom, functionInto);
		adjustIOSpoolDefinition(elementFrom, functionInto);
		adjustIOTableDefinition(elementFrom, functionInto);
	}

	void adjustIOPanelDefinition(org.w3c.dom.Element elementFrom, Function functionInto) {
		org.w3c.dom.Element workElement1, workElement2;
		NodeList elementList1, elementList3;

		elementList1 = elementFrom.getElementsByTagName("IOPanel");
		// Clear existing IOPanels
		functionInto.getIOPanels().clear();

		for (int i = 0; i < elementList1.getLength(); i++) {
			workElement1 = (org.w3c.dom.Element)elementList1.item(i);

			IOPanel ioPanel = new IOPanel();
			ioPanel.setId(Integer.toString(getNextIDOfFunctionIOs()));
			ioPanel.setName(workElement1.getAttribute("Name"));
			ioPanel.setSortKey(workElement1.getAttribute("SortKey"));
			ioPanel.setDescriptions(workElement1.getAttribute("Descriptions"));
			ioPanel.setImageText(workElement1.getAttribute("ImageText"));
			ioPanel.setBackground(workElement1.getAttribute("Background"));
			ioPanel.setSize(workElement1.getAttribute("Size"));

			elementList3 = workElement1.getElementsByTagName("IOPanelStyle");
			if (elementList3.getLength() > 0) {
				workElement2 = (org.w3c.dom.Element)elementList3.item(0);
				IOPanelStyle style = new IOPanelStyle();
				style.setValue(workElement2.getAttribute("Value"));
				ioPanel.setIopanelStyle(style);
			}

			elementList3 = workElement1.getElementsByTagName("IOPanelField");
			for (int m = 0; m < elementList3.getLength(); m++) {
				workElement2 = (org.w3c.dom.Element)elementList3.item(m);
				IOPanelField field = new IOPanelField();
				field.setName(workElement2.getAttribute("Name"));
				field.setLabel(workElement2.getAttribute("Label"));
				field.setIoType(workElement2.getAttribute("IOType"));
				field.setDescriptions(workElement2.getAttribute("Descriptions"));
				field.setSortKey(workElement2.getAttribute("SortKey"));
				ioPanel.getIopanelFields().add(field);
			}

			functionInto.getIOPanels().add(ioPanel);
		}
	}

	void adjustIOWebPageDefinition(org.w3c.dom.Element elementFrom, Function functionInto) {
		org.w3c.dom.Element workElement1, workElement2;
		NodeList elementList1, elementList3;

		elementList1 = elementFrom.getElementsByTagName("IOWebPage");
		// Clear existing IOWebPages
		functionInto.getIOWebPages().clear();

		for (int i = 0; i < elementList1.getLength(); i++) {
			workElement1 = (org.w3c.dom.Element)elementList1.item(i);

			IOWebPage ioWebPage = new IOWebPage();
			ioWebPage.setId(Integer.toString(getNextIDOfFunctionIOs()));
			ioWebPage.setName(workElement1.getAttribute("Name"));
			ioWebPage.setSortKey(workElement1.getAttribute("SortKey"));
			ioWebPage.setDescriptions(workElement1.getAttribute("Descriptions"));
			ioWebPage.setFileName(workElement1.getAttribute("FileName"));

			elementList3 = workElement1.getElementsByTagName("IOWebPageField");
			for (int m = 0; m < elementList3.getLength(); m++) {
				workElement2 = (org.w3c.dom.Element)elementList3.item(m);
				IOWebPageField field = new IOWebPageField();
				field.setName(workElement2.getAttribute("Name"));
				field.setLabel(workElement2.getAttribute("Label"));
				field.setIoType(workElement2.getAttribute("IOType"));
				field.setDescriptions(workElement2.getAttribute("Descriptions"));
				field.setSortKey(workElement2.getAttribute("SortKey"));
				ioWebPage.getIowebpageFields().add(field);
			}

			functionInto.getIOWebPages().add(ioWebPage);
		}
	}

	void adjustIOSpoolDefinition(org.w3c.dom.Element elementFrom, Function functionInto) {
		org.w3c.dom.Element workElement1, workElement2;
		NodeList elementList1, elementList3;

		elementList1 = elementFrom.getElementsByTagName("IOSpool");
		// Clear existing IOSpools
		functionInto.getIOSpools().clear();

		for (int i = 0; i < elementList1.getLength(); i++) {
			workElement1 = (org.w3c.dom.Element)elementList1.item(i);

			IOSpool ioSpool = new IOSpool();
			ioSpool.setId(Integer.toString(getNextIDOfFunctionIOs()));
			ioSpool.setName(workElement1.getAttribute("Name"));
			ioSpool.setSortKey(workElement1.getAttribute("SortKey"));
			ioSpool.setDescriptions(workElement1.getAttribute("Descriptions"));
			ioSpool.setImageText(workElement1.getAttribute("ImageText"));
			ioSpool.setBackground(workElement1.getAttribute("Background"));
			ioSpool.setSize(workElement1.getAttribute("Size"));

			elementList3 = workElement1.getElementsByTagName("IOSpoolStyle");
			if (elementList3.getLength() > 0) {
				workElement2 = (org.w3c.dom.Element)elementList3.item(0);
				IOSpoolStyle style = new IOSpoolStyle();
				style.setValue(workElement2.getAttribute("Value"));
				ioSpool.setIospoolStyle(style);
			}

			elementList3 = workElement1.getElementsByTagName("IOSpoolField");
			for (int m = 0; m < elementList3.getLength(); m++) {
				workElement2 = (org.w3c.dom.Element)elementList3.item(m);
				IOSpoolField field = new IOSpoolField();
				field.setName(workElement2.getAttribute("Name"));
				field.setLabel(workElement2.getAttribute("Label"));
				field.setDescriptions(workElement2.getAttribute("Descriptions"));
				field.setSortKey(workElement2.getAttribute("SortKey"));
				ioSpool.getIospoolFields().add(field);
			}

			functionInto.getIOSpools().add(ioSpool);
		}
	}

	void adjustIOTableDefinition(org.w3c.dom.Element elementFrom, Function functionInto) {
		org.w3c.dom.Element ioTableElementFrom, workElement;
		NodeList elementListFrom, workElementList;
		org.w3c.dom.Element tableElementFrom;
		String internalTableIDFrom = "";
		String internalTableIDInto = "";
		String internalFieldIDInto = "";
		String tableName = "";
		String tableSortKey = "";

		NodeList tableListFrom = domDocumentFrom.getElementsByTagName("Table");
		XeadSystem xeadSystem = frame_.getXeadDocumentModel().getXeadSystem();
		List<Table> tableListInto = xeadSystem.getTables();

		elementListFrom = elementFrom.getElementsByTagName("IOTable");

		// Clear existing IOTables
		functionInto.getIOTables().clear();

		for (int i = 0; i < elementListFrom.getLength(); i++) {

			ioTableElementFrom = (org.w3c.dom.Element)elementListFrom.item(i);
			internalTableIDFrom = ioTableElementFrom.getAttribute("TableID");
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
				for (int m = 0; m < tableListFrom.getLength(); m++) {
					tableElementFrom = (org.w3c.dom.Element)tableListFrom.item(m);
					if (internalTableIDFrom.equals(tableElementFrom.getAttribute("ID"))) {
						tableName = tableElementFrom.getAttribute("Name");
						tableSortKey = tableElementFrom.getAttribute("SortKey");
						break;
					}
				}
				try {
					bufferedWriter.write("  " + tableName + "(" + tableSortKey + ")" + res.getString("DialogImportXEAD37") + "\n");
				} catch (IOException ex) {}
			} else {
				IOTable ioTable = new IOTable();
				ioTable.setId(Integer.toString(getNextIDOfFunctionIOs()));
				ioTable.setPosition(ioTableElementFrom.getAttribute("Position"));
				ioTable.setNameExtension(ioTableElementFrom.getAttribute("NameExtension"));
				ioTable.setSortKey(ioTableElementFrom.getAttribute("SortKey"));
				ioTable.setDescriptions(ioTableElementFrom.getAttribute("Descriptions"));
				ioTable.setTableId(internalTableIDInto);
				ioTable.setOpC(ioTableElementFrom.getAttribute("OpC"));
				ioTable.setOpR(ioTableElementFrom.getAttribute("OpR"));
				ioTable.setOpU(ioTableElementFrom.getAttribute("OpU"));
				ioTable.setOpD(ioTableElementFrom.getAttribute("OpD"));

				///////////////////////
				//Create IOTableField//
				///////////////////////
				workElementList = ioTableElementFrom.getElementsByTagName("IOTableField");
				for (int m = 0; m < workElementList.getLength(); m++) {
					workElement = (org.w3c.dom.Element)workElementList.item(m);
					internalFieldIDInto = convertInternalIDOfTableField(internalTableIDFrom, workElement.getAttribute("FieldID"), domDocumentFrom, xeadSystem);
					if (!workElement.getAttribute("Descriptions").equals("") && !internalFieldIDInto.equals("")) {
						IOTableField field = new IOTableField();
						field.setFieldId(internalFieldIDInto);
						field.setDescriptions(workElement.getAttribute("Descriptions"));
						ioTable.getIotableFields().add(field);
					}
				}
				functionInto.getIOTables().add(ioTable);
			}

			setupSubsystemAttributeOfTable(internalTableIDInto, blockSubsystemInto);
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

	void updateTaskDefinition(org.w3c.dom.Element elementFrom, Task taskInto) {
		//////////////////////////
		//Update Task Definition//
		//////////////////////////
		taskInto.setName(elementFrom.getAttribute("Name"));
		taskInto.setEvent(elementFrom.getAttribute("Event"));
		taskInto.setTaskTypeId(convertInternalIDOfTheTypeTag(elementFrom.getAttribute("TaskTypeID"), "TaskType"));
		taskInto.setDescriptions(elementFrom.getAttribute("Descriptions"));

		/////////////////////////////////
		//Purge and create Task Actions//
		/////////////////////////////////
		taskInto.getTaskActions().clear();
		createTaskActions(elementFrom, taskInto);
	}

	void updateTableDefinition(org.w3c.dom.Element elementFrom, Table tableInto) {
		org.w3c.dom.Element workElement1, workElement2, workElement3;
		NodeList elementList;
		int lastID = 0;
		int checkCounter = 0;
		String workStr = "";
		String fieldID = "";

		String tableIDFrom = elementFrom.getAttribute("ID");
		NodeList fieldListFrom = elementFrom.getElementsByTagName("TableField");
		NodeList keyListFrom = elementFrom.getElementsByTagName("TableKey");
		XeadSystem xeadSystem = frame_.getXeadDocumentModel().getXeadSystem();

		///////////////////////////
		//Update Table Attributes//
		///////////////////////////
		tableInto.setName(elementFrom.getAttribute("Name"));
		tableInto.setDescriptions(elementFrom.getAttribute("Descriptions"));
		tableInto.setTableTypeId(convertInternalIDOfTheTypeTag(elementFrom.getAttribute("TableTypeID"), "TableType"));

		/////////////////////
		//Adjust TableField//
		/////////////////////
		for (int i = 0; i < fieldListFrom.getLength(); i++) {
			workElement1 = (org.w3c.dom.Element)fieldListFrom.item(i);
			workStr = convertInternalIDOfTableField(tableIDFrom, workElement1.getAttribute("ID"), domDocumentFrom, xeadSystem);

			checkCounter = 0;
			for (TableField tf : tableInto.getTableFields()) {
				if (workStr.equals(tf.getId())) {
					tf.setName(workElement1.getAttribute("Name"));
					tf.setSortKey(workElement1.getAttribute("SortKey"));
					tf.setDescriptions(workElement1.getAttribute("Descriptions"));
					tf.setAlias(workElement1.getAttribute("Alias"));
					tf.setAttributeType(workElement1.getAttribute("AttributeType"));
					tf.setDataTypeId(convertInternalIDOfTheTypeTag(workElement1.getAttribute("DataTypeID"), "DataType"));
					tf.setShowOnModel(workElement1.getAttribute("ShowOnModel"));
					tf.setNotNull(workElement1.getAttribute("NotNull"));
					tf.setNoUpdate(workElement1.getAttribute("NoUpdate"));
					tf.setDefault(workElement1.getAttribute("Default"));
					checkCounter++;
					break;
				}
			}
			if (checkCounter == 0) {
				lastID = getLastIDOfTheType("TableField");
				fieldID = Integer.toString(lastID + 1);
				TableField newField = new TableField();
				newField.setId(fieldID);
				newField.setName(workElement1.getAttribute("Name"));
				newField.setSortKey(workElement1.getAttribute("SortKey"));
				newField.setDescriptions(workElement1.getAttribute("Descriptions"));
				newField.setAlias(workElement1.getAttribute("Alias"));
				newField.setAttributeType(workElement1.getAttribute("AttributeType"));
				newField.setDataTypeId(convertInternalIDOfTheTypeTag(workElement1.getAttribute("DataTypeID"), "DataType"));
				newField.setShowOnModel(workElement1.getAttribute("ShowOnModel"));
				newField.setNotNull(workElement1.getAttribute("NotNull"));
				newField.setNoUpdate(workElement1.getAttribute("NoUpdate"));
				newField.setDefault(workElement1.getAttribute("Default"));
				tableInto.getTableFields().add(newField);
			}
		}

		///////////////////
		//Adjust TableKey//
		///////////////////
		for (int i = 0; i < keyListFrom.getLength(); i++) {
			workElement1 = (org.w3c.dom.Element)keyListFrom.item(i);
			checkCounter = 0;
			TableKey lastKey = null;
			for (TableKey tk : tableInto.getTableKeys()) {
				if (theseAreIdenticalKeyDefinitionsWithElement(workElement1, tk, tableIDFrom, xeadSystem)) {
					tk.setSortKey(workElement1.getAttribute("SortKey"));
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
				newKey.setType(workElement1.getAttribute("Type"));
				newKey.setSortKey(workElement1.getAttribute("SortKey"));
				elementList = workElement1.getElementsByTagName("TableKeyField");
				for (int m = 0; m < elementList.getLength(); m++) {
					workElement2 = (org.w3c.dom.Element)elementList.item(m);
					TableKeyField tkf = new TableKeyField();
					tkf.setFieldId(convertInternalIDOfTableField(elementFrom.getAttribute("ID"), workElement2.getAttribute("FieldID"), domDocumentFrom, xeadSystem));
					tkf.setSortKey(workElement2.getAttribute("SortKey"));
					newKey.getTableKeyFields().add(tkf);
				}
				tableInto.getTableKeys().add(newKey);
			}
		}
	}

	void createTableDefinition(org.w3c.dom.Element elementFrom, String subsystemID) {
		org.w3c.dom.Element workElement1, workElement2;
		NodeList elementList1, elementList2;
		int lastID = 0;

		XeadSystem xeadSystem = frame_.getXeadDocumentModel().getXeadSystem();

		///////////////////////////
		//Create Table Definition//
		///////////////////////////
		Table newTable = new Table();
		lastID = getLastIDOfTheType("Table");
		newTable.setId(Integer.toString(lastID + 1));
		newTable.setName(elementFrom.getAttribute("Name"));
		newTable.setSortKey(elementFrom.getAttribute("SortKey"));
		newTable.setSubsystemId(subsystemID);
		newTable.setDescriptions(elementFrom.getAttribute("Descriptions"));
		newTable.setTableTypeId(convertInternalIDOfTheTypeTag(elementFrom.getAttribute("TableTypeID"), "TableType"));
		xeadSystem.getTables().add(newTable);

		/////////////////////
		//Create TableField//
		/////////////////////
		elementList1 = elementFrom.getElementsByTagName("TableField");
		for (int i = 0; i < elementList1.getLength(); i++) {
			workElement1 = (org.w3c.dom.Element)elementList1.item(i);
			TableField newField = new TableField();
			lastID = getLastIDOfTheType("TableField");
			newField.setId(Integer.toString(lastID + 1));
			newField.setName(workElement1.getAttribute("Name"));
			newField.setSortKey(workElement1.getAttribute("SortKey"));
			newField.setDescriptions(workElement1.getAttribute("Descriptions"));
			newField.setAlias(workElement1.getAttribute("Alias"));
			newField.setAttributeType(workElement1.getAttribute("AttributeType"));
			newField.setDataTypeId(convertInternalIDOfTheTypeTag(workElement1.getAttribute("DataTypeID"), "DataType"));
			newField.setShowOnModel(workElement1.getAttribute("ShowOnModel"));
			newField.setNotNull(workElement1.getAttribute("NotNull"));
			newField.setNoUpdate(workElement1.getAttribute("NoUpdate"));
			newField.setDefault(workElement1.getAttribute("Default"));
			newTable.getTableFields().add(newField);
		}

		///////////////////
		//Create TableKey//
		///////////////////
		elementList1 = elementFrom.getElementsByTagName("TableKey");
		for (int i = 0; i < elementList1.getLength(); i++) {
			workElement1 = (org.w3c.dom.Element)elementList1.item(i);
			TableKey newKey = new TableKey();
			newKey.setId(workElement1.getAttribute("ID"));
			newKey.setType(workElement1.getAttribute("Type"));
			newKey.setSortKey(workElement1.getAttribute("SortKey"));
			elementList2 = workElement1.getElementsByTagName("TableKeyField");
			for (int m = 0; m < elementList2.getLength(); m++) {
				workElement2 = (org.w3c.dom.Element)elementList2.item(m);
				TableKeyField tkf = new TableKeyField();
				tkf.setFieldId(convertInternalIDOfTableField(elementFrom.getAttribute("ID"), workElement2.getAttribute("FieldID"), domDocumentFrom, xeadSystem));
				tkf.setAscDesc(workElement2.getAttribute("AscDesc"));
				tkf.setSortKey(workElement2.getAttribute("SortKey"));
				newKey.getTableKeyFields().add(tkf);
			}
			newTable.getTableKeys().add(newKey);
		}
	}

	void adjustTableRelationship(org.w3c.dom.Element elementFrom) {
		org.w3c.dom.Element workElement;
		int lastID = 0;

		String tableID1From = elementFrom.getAttribute("Table1ID");
		String tableID2From = elementFrom.getAttribute("Table2ID");
		String tableKey1From = elementFrom.getAttribute("TableKey1ID");
		String tableKey2From = elementFrom.getAttribute("TableKey2ID");
		String relationshipType = elementFrom.getAttribute("Type");
		NodeList tableListFrom = domDocumentFrom.getElementsByTagName("Table");
		XeadSystem xeadSystem = frame_.getXeadDocumentModel().getXeadSystem();
		List<Relationship> relationshipListInto = xeadSystem.getRelationships();

		String tableName1From = "";
		String tableName2From = "";
		String tableSortKey1From = "";
		String tableSortKey2From = "";
		for (int i = 0; i < tableListFrom.getLength(); i++) {
			workElement = (org.w3c.dom.Element)tableListFrom.item(i);
			if (workElement.getAttribute("ID").equals(tableID1From)) {
				tableName1From = workElement.getAttribute("Name");
				tableSortKey1From = workElement.getAttribute("SortKey");
			}
			if (workElement.getAttribute("ID").equals(tableID2From)) {
				tableName2From = workElement.getAttribute("Name");
				tableSortKey2From = workElement.getAttribute("SortKey");
			}
		}

		String relationshipIDInto = convertInternalIDOfTableRelationship(elementFrom.getAttribute("ID"), xeadSystem);
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
				newRel.setExistWhen1(elementFrom.getAttribute("ExistWhen1"));
				newRel.setExistWhen2(elementFrom.getAttribute("ExistWhen2"));
				xeadSystem.getRelationships().add(newRel);

				try {
					bufferedWriter.write(tableName1From + "(" + tableSortKey1From + ")," + tableName2From + "(" + tableSortKey2From + ")" + res.getString("DialogImportXEAD36") + "\n");
				} catch (IOException ex) {}
			}
		} else {
			for (Relationship rel : relationshipListInto) {
				if (rel.getId().equals(relationshipIDInto)) {
					rel.setExistWhen1(elementFrom.getAttribute("ExistWhen1"));
					rel.setExistWhen2(elementFrom.getAttribute("ExistWhen2"));
					break;
				}
			}
		}
	}
	
	void importSubsystemAttributesOfTable() {
		org.w3c.dom.Element workElement1, workElement2;
		NodeList elementListFrom, tableListFrom;
		String tableID1 = "";
		String relationshipIDFrom = "";
		String relationshipIDInto = "";
		int checkCounter = 0;

		if (blockSubsystemInto == null) return;

		/////////////////////////
		//Adjust SubsystemTable//
		/////////////////////////
		tableListFrom = domDocumentFrom.getElementsByTagName("Table");
		elementListFrom = blockElementFrom.getElementsByTagName("SubsystemTable");

		jProgressBar.setString("Importing Subsystem Attribute...");
		jProgressBar.setValue(0);
		jProgressBar.setMaximum(elementListFrom.getLength());

		for (int i = 0; i < elementListFrom.getLength(); i++) {
			jProgressBar.setValue(jProgressBar.getValue() + 1);
			jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());

			workElement1 = (org.w3c.dom.Element)elementListFrom.item(i);
			tableID1 = convertInternalIDOfTheTag(workElement1.getAttribute("TableID"), "Table");
			if (tableID1.equals("")) {
				try {
					for (int j = 0; j < tableListFrom.getLength(); j++) {
						workElement2 = (org.w3c.dom.Element)tableListFrom.item(j);
						if (workElement2.getAttribute("ID").equals(workElement1.getAttribute("TableID"))) {
							bufferedWriter.write(workElement2.getAttribute("Name") + "(" + workElement2.getAttribute("SortKey") + ")" + res.getString("DialogImportXEAD42") + "\n");
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
					targetST.setBoxPosition(workElement1.getAttribute("BoxPosition"));
					targetST.setExtDivLoc(workElement1.getAttribute("ExtDivLoc"));
					targetST.setIntDivLoc(workElement1.getAttribute("IntDivLoc"));
					targetST.setShowOnModel(workElement1.getAttribute("ShowOnModel"));
					targetST.setShowInstance(workElement1.getAttribute("ShowInstance"));
					targetST.setInstance(workElement1.getAttribute("Instance"));
				}
			}
		}

		////////////////////////////////
		//Adjust SubsystemRelationship//
		////////////////////////////////
		elementListFrom = blockElementFrom.getElementsByTagName("SubsystemRelationship");

		jProgressBar.setValue(0);
		jProgressBar.setMaximum(elementListFrom.getLength());

		for (int i = 0; i < elementListFrom.getLength(); i++) {
			jProgressBar.setValue(jProgressBar.getValue() + 1);
			jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());

			workElement1 = (org.w3c.dom.Element)elementListFrom.item(i);
			relationshipIDFrom = workElement1.getAttribute("RelationshipID");
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
					targetSR.setTerminalIndex1(workElement1.getAttribute("TerminalIndex1"));
					targetSR.setTerminalIndex2(workElement1.getAttribute("TerminalIndex2"));
					targetSR.setShowOnModel(workElement1.getAttribute("ShowOnModel"));
				}
			}
		}
	}

	void importSubsystem() {
		/////////////////////////////////////////
		//Update attributes of target subsystem//
		/////////////////////////////////////////
		if (blockSubsystemInto != null) {
			blockSubsystemInto.setName(blockElementFrom.getAttribute("Name"));
			blockSubsystemInto.setSortKey(blockElementFrom.getAttribute("SortKey"));
			blockSubsystemInto.setDescriptions(blockElementFrom.getAttribute("Descriptions"));
		}
	}

	String convertInternalIDOfTableRelationship(String relationshipIDFrom) {
		return convertInternalIDOfTableRelationship(relationshipIDFrom, frame_.getXeadDocumentModel().getXeadSystem());
	}

	String convertInternalIDOfTableRelationship(String relationshipIDFrom, XeadSystem xeadSystemInto) {
		org.w3c.dom.Element workElement1, workElement2, workElement3;
		NodeList tableList, tableKeyList, relationshipList;
		org.w3c.dom.Element tableKey1ElementFrom = null;
		org.w3c.dom.Element tableKey2ElementFrom = null;
		org.w3c.dom.Element tableKey1ElementInto = null;
		org.w3c.dom.Element tableKey2ElementInto = null;
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

		relationshipList = domDocumentFrom.getElementsByTagName("Relationship");
		for (int i = 0; i < relationshipList.getLength(); i++) {
			workElement1 = (org.w3c.dom.Element)relationshipList.item(i);
			if (workElement1.getAttribute("ID").equals(relationshipIDFrom)) {
				table1IDFrom = workElement1.getAttribute("Table1ID");
				tableKey1IDFrom = workElement1.getAttribute("TableKey1ID");
				table2IDFrom = workElement1.getAttribute("Table2ID");
				tableKey2IDFrom = workElement1.getAttribute("TableKey2ID");
				relationshipTypeFrom = workElement1.getAttribute("Type");
			}
		}

		tableList = domDocumentFrom.getElementsByTagName("Table");
		for (int i = 0; i < tableList.getLength(); i++) {
			workElement1 = (org.w3c.dom.Element)tableList.item(i);
			if (workElement1.getAttribute("ID").equals(table1IDFrom)) {
				tableKeyList = workElement1.getElementsByTagName("TableKey");
				for (int j = 0; j < tableKeyList.getLength(); j++) {
					workElement2 = (org.w3c.dom.Element)tableKeyList.item(j);
					if (workElement2.getAttribute("ID").equals(tableKey1IDFrom)) {
						tableKey1ElementFrom = workElement2;
					}
				}
			}
			if (workElement1.getAttribute("ID").equals(table2IDFrom)) {
				tableKeyList = workElement1.getElementsByTagName("TableKey");
				for (int j = 0; j < tableKeyList.getLength(); j++) {
					workElement2 = (org.w3c.dom.Element)tableKeyList.item(j);
					if (workElement2.getAttribute("ID").equals(tableKey2IDFrom)) {
						tableKey2ElementFrom = workElement2;
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

				if (theseAreIdenticalKeyDefinitionsWithElementAndKey(tableKey1ElementFrom, key1Into, table1IDFrom, xeadSystemInto) &&
						theseAreIdenticalKeyDefinitionsWithElementAndKey(tableKey2ElementFrom, key2Into, table2IDFrom, xeadSystemInto)) {
					relationshipIDInto = relationshipIDWork;
					break;
				}
			}
		}
		return relationshipIDInto;
	}

	void createFunctionDefinition(org.w3c.dom.Element elementFrom, String subsystemID) {
		org.w3c.dom.Element workElement1, workElement2;
		NodeList elementList1, elementList2, elementList3;
		String internalTableIDInto = "";
		String internalFieldIDInto = "";
		int lastID = 0;
		org.w3c.dom.Element tableElement;
		String tableName = "";
		String tableSortKey = "";
		NodeList tableListFrom = domDocumentFrom.getElementsByTagName("Table");
		XeadSystem xeadSystem = frame_.getXeadDocumentModel().getXeadSystem();

		Function newFunc = new Function();
		lastID = getLastIDOfTheType("Function");
		newFunc.setId(Integer.toString(lastID + 1));
		newFunc.setName(elementFrom.getAttribute("Name"));
		newFunc.setSortKey(elementFrom.getAttribute("SortKey"));
		newFunc.setSubsystemId(subsystemID);
		newFunc.setSummary(elementFrom.getAttribute("Summary"));
		newFunc.setParameters(elementFrom.getAttribute("Parameters"));
		newFunc.setReturn(elementFrom.getAttribute("Return"));
		newFunc.setDescriptions(elementFrom.getAttribute("Descriptions"));
		newFunc.setFunctionTypeId(convertInternalIDOfTheTypeTag(elementFrom.getAttribute("FunctionTypeID"), "FunctionType"));
		xeadSystem.getFunctions().add(newFunc);

		elementList1 = elementFrom.getElementsByTagName("IOPanel");
		for (int i = 0; i < elementList1.getLength(); i++) {
			workElement1 = (org.w3c.dom.Element)elementList1.item(i);
			IOPanel ioPanel = new IOPanel();
			ioPanel.setId(workElement1.getAttribute("ID"));
			ioPanel.setName(workElement1.getAttribute("Name"));
			ioPanel.setSortKey(workElement1.getAttribute("SortKey"));
			ioPanel.setDescriptions(workElement1.getAttribute("Descriptions"));
			ioPanel.setImageText(workElement1.getAttribute("ImageText"));
			ioPanel.setBackground(workElement1.getAttribute("Background"));
			ioPanel.setSize(workElement1.getAttribute("Size"));
			elementList2 = workElement1.getElementsByTagName("IOPanelStyle");
			if (elementList2.getLength() > 0) {
				workElement2 = (org.w3c.dom.Element)elementList2.item(0);
				IOPanelStyle style = new IOPanelStyle();
				style.setValue(workElement2.getAttribute("Value"));
				ioPanel.setIopanelStyle(style);
			}
			elementList2 = workElement1.getElementsByTagName("IOPanelField");
			for (int m = 0; m < elementList2.getLength(); m++) {
				workElement2 = (org.w3c.dom.Element)elementList2.item(m);
				IOPanelField field = new IOPanelField();
				field.setName(workElement2.getAttribute("Name"));
				field.setLabel(workElement2.getAttribute("Label"));
				field.setIoType(workElement2.getAttribute("IOType"));
				field.setDescriptions(workElement2.getAttribute("Descriptions"));
				field.setSortKey(workElement2.getAttribute("SortKey"));
				ioPanel.getIopanelFields().add(field);
			}
			newFunc.getIOPanels().add(ioPanel);
		}

		elementList1 = elementFrom.getElementsByTagName("IOWebPage");
		for (int i = 0; i < elementList1.getLength(); i++) {
			workElement1 = (org.w3c.dom.Element)elementList1.item(i);
			IOWebPage ioWebPage = new IOWebPage();
			ioWebPage.setId(workElement1.getAttribute("ID"));
			ioWebPage.setName(workElement1.getAttribute("Name"));
			ioWebPage.setSortKey(workElement1.getAttribute("SortKey"));
			ioWebPage.setDescriptions(workElement1.getAttribute("Descriptions"));
			ioWebPage.setFileName(workElement1.getAttribute("FileName"));
			elementList2 = workElement1.getElementsByTagName("IOWebPageField");
			for (int m = 0; m < elementList2.getLength(); m++) {
				workElement2 = (org.w3c.dom.Element)elementList2.item(m);
				IOWebPageField field = new IOWebPageField();
				field.setName(workElement2.getAttribute("Name"));
				field.setLabel(workElement2.getAttribute("Label"));
				field.setIoType(workElement2.getAttribute("IOType"));
				field.setDescriptions(workElement2.getAttribute("Descriptions"));
				field.setSortKey(workElement2.getAttribute("SortKey"));
				ioWebPage.getIowebpageFields().add(field);
			}
			newFunc.getIOWebPages().add(ioWebPage);
		}

		elementList1 = elementFrom.getElementsByTagName("IOSpool");
		for (int i = 0; i < elementList1.getLength(); i++) {
			workElement1 = (org.w3c.dom.Element)elementList1.item(i);
			IOSpool ioSpool = new IOSpool();
			ioSpool.setId(workElement1.getAttribute("ID"));
			ioSpool.setName(workElement1.getAttribute("Name"));
			ioSpool.setSortKey(workElement1.getAttribute("SortKey"));
			ioSpool.setDescriptions(workElement1.getAttribute("Descriptions"));
			ioSpool.setImageText(workElement1.getAttribute("ImageText"));
			ioSpool.setBackground(workElement1.getAttribute("Background"));
			ioSpool.setSize(workElement1.getAttribute("Size"));
			elementList2 = workElement1.getElementsByTagName("IOSpoolStyle");
			if (elementList2.getLength() > 0) {
				workElement2 = (org.w3c.dom.Element)elementList2.item(0);
				IOSpoolStyle style = new IOSpoolStyle();
				style.setValue(workElement2.getAttribute("Value"));
				ioSpool.setIospoolStyle(style);
			}
			elementList2 = workElement1.getElementsByTagName("IOSpoolField");
			for (int m = 0; m < elementList2.getLength(); m++) {
				workElement2 = (org.w3c.dom.Element)elementList2.item(m);
				IOSpoolField field = new IOSpoolField();
				field.setName(workElement2.getAttribute("Name"));
				field.setLabel(workElement2.getAttribute("Label"));
				field.setDescriptions(workElement2.getAttribute("Descriptions"));
				field.setSortKey(workElement2.getAttribute("SortKey"));
				ioSpool.getIospoolFields().add(field);
			}
			newFunc.getIOSpools().add(ioSpool);
		}

		elementList1 = elementFrom.getElementsByTagName("IOTable");
		for (int i = 0; i < elementList1.getLength(); i++) {
			workElement1 = (org.w3c.dom.Element)elementList1.item(i);
			internalTableIDInto = convertInternalIDOfTheTag(workElement1.getAttribute("TableID"), "Table");
			if (internalTableIDInto.equals("")) {
				missingTableCounter++;
				for (int m = 0; m < tableListFrom.getLength(); m++) {
					tableElement = (org.w3c.dom.Element)tableListFrom.item(m);
					if (workElement1.getAttribute("TableID").equals(tableElement.getAttribute("ID"))) {
						tableName = tableElement.getAttribute("Name");
						tableSortKey = tableElement.getAttribute("SortKey");
					}
				}
				try {
					bufferedWriter.write("  " + tableName + "(" + tableSortKey + ")" + res.getString("DialogImportXEAD37") + "\n");
				} catch (IOException ex) {}
			} else {
				IOTable ioTable = new IOTable();
				ioTable.setId(workElement1.getAttribute("ID"));
				ioTable.setNameExtension(workElement1.getAttribute("NameExtension"));
				ioTable.setSortKey(workElement1.getAttribute("SortKey"));
				ioTable.setDescriptions(workElement1.getAttribute("Descriptions"));
				ioTable.setTableId(internalTableIDInto);
				ioTable.setOpC(workElement1.getAttribute("OpC"));
				ioTable.setOpR(workElement1.getAttribute("OpR"));
				ioTable.setOpU(workElement1.getAttribute("OpU"));
				ioTable.setOpD(workElement1.getAttribute("OpD"));
				elementList3 = workElement1.getElementsByTagName("IOTableField");
				for (int m = 0; m < elementList3.getLength(); m++) {
					workElement2 = (org.w3c.dom.Element)elementList3.item(m);
					internalFieldIDInto = convertInternalIDOfTableField(workElement1.getAttribute("TableID"), workElement2.getAttribute("FieldID"), domDocumentFrom, xeadSystem);
					if (!workElement2.getAttribute("Descriptions").equals("") && !internalFieldIDInto.equals("")) {
						IOTableField field = new IOTableField();
						field.setFieldId(internalFieldIDInto);
						field.setDescriptions(workElement2.getAttribute("Descriptions"));
						ioTable.getIotableFields().add(field);
					}
				}
				newFunc.getIOTables().add(ioTable);
				setupSubsystemAttributeOfTable(internalTableIDInto, blockSubsystemInto);
			}
		}
	}

	String createTaskDefinition(org.w3c.dom.Element elementFrom, String roleID) {
		int lastID = 0;
		String newID = "";
		XeadSystem xeadSystem = frame_.getXeadDocumentModel().getXeadSystem();

		Task newTask = new Task();
		lastID = getLastIDOfTheType("Task");
		newID = Integer.toString(lastID + 1);
		newTask.setId(newID);
		newTask.setName(elementFrom.getAttribute("Name"));
		newTask.setEvent(elementFrom.getAttribute("Event"));
		newTask.setSortKey(elementFrom.getAttribute("SortKey"));
		newTask.setRoleId(roleID);
		newTask.setTaskTypeId(convertInternalIDOfTheTypeTag(elementFrom.getAttribute("TaskTypeID"), "TaskType"));
		newTask.setDescriptions(elementFrom.getAttribute("Descriptions"));
		xeadSystem.getTasks().add(newTask);

		createTaskActions(elementFrom, newTask);

		return newID;
	}

	void createTaskActions(org.w3c.dom.Element elementFrom, Task taskInto) {
		org.w3c.dom.Element workElement1, workElement2, workElement3, workElement4, workElement5;
		NodeList elementList1, elementList2, elementList3;
		String functionSortKey = "";
		String functionName = "";
		String ioSortKey = "";
		String ioName = "";
		String targetFunctionID = "";
		String targetFunctionIOID = "";

		NodeList functionListFrom = domDocumentFrom.getElementsByTagName("Function");
		XeadSystem xeadSystem = frame_.getXeadDocumentModel().getXeadSystem();
		List<Function> functionListInto = xeadSystem.getFunctions();
		NodeList tableListFrom = domDocumentFrom.getElementsByTagName("Table");

		elementList1 = elementFrom.getElementsByTagName("TaskAction");
		for (int i = 0; i < elementList1.getLength(); i++) {
			workElement1 = (org.w3c.dom.Element)elementList1.item(i);
			TaskAction taskAction = new TaskAction();
			taskAction.setExecuteIf(workElement1.getAttribute("ExecuteIf"));
			taskAction.setLabel(workElement1.getAttribute("Label"));
			taskAction.setSortKey(workElement1.getAttribute("SortKey"));
			taskAction.setDescriptions(workElement1.getAttribute("Descriptions"));
			taskAction.setIndentLevel(workElement1.getAttribute("IndentLevel"));

			elementList2 = workElement1.getElementsByTagName("TaskFunctionIO");
			for (int m = 0; m < elementList2.getLength(); m++) {
				workElement2 = (org.w3c.dom.Element)elementList2.item(m);
				ioSortKey = "";
				ioName = "";
				targetFunctionID = "";
				targetFunctionIOID = "";

				for (int j = 0; j < functionListFrom.getLength(); j++) {
					workElement3 = (org.w3c.dom.Element)functionListFrom.item(j);
					if (workElement3.getAttribute("ID").equals(workElement2.getAttribute("FunctionID"))) {
						functionName = workElement3.getAttribute("Name");
						functionSortKey = workElement3.getAttribute("SortKey");
						if (!workElement2.getAttribute("IOID").equals("0")) {
							elementList3 = workElement3.getElementsByTagName("IOPanel");
							for (int n = 0; n < elementList3.getLength(); n++) {
								workElement4 = (org.w3c.dom.Element)elementList3.item(n);
								if (workElement4.getAttribute("ID").equals(workElement2.getAttribute("IOID"))) {
									ioSortKey = workElement4.getAttribute("SortKey");
									ioName = workElement4.getAttribute("Name");
									break;
								}
							}
							if (ioSortKey.equals("") && ioName.equals("")) {
								elementList3 = workElement3.getElementsByTagName("IOSpool");
								for (int n = 0; n < elementList3.getLength(); n++) {
									workElement4 = (org.w3c.dom.Element)elementList3.item(n);
									if (workElement4.getAttribute("ID").equals(workElement2.getAttribute("IOID"))) {
										ioSortKey = workElement4.getAttribute("SortKey");
										ioName = workElement4.getAttribute("Name");
										break;
									}
								}
							}
							if (ioSortKey.equals("") && ioName.equals("")) {
								elementList3 = workElement3.getElementsByTagName("IOTable");
								for (int n = 0; n < elementList3.getLength(); n++) {
									workElement4 = (org.w3c.dom.Element)elementList3.item(n);
									if (workElement4.getAttribute("ID").equals(workElement2.getAttribute("IOID"))) {
										for (int p = 0; p < tableListFrom.getLength(); p++) {
											workElement5 = (org.w3c.dom.Element)tableListFrom.item(p);
											if (workElement5.getAttribute("ID").equals(workElement4.getAttribute("TableID"))) {
												ioSortKey = workElement5.getAttribute("SortKey");
												ioName = workElement5.getAttribute("Name");
												break;
											}
										}
										break;
									}
								}
							}
							if (ioSortKey.equals("") && ioName.equals("")) {
								elementList3 = workElement3.getElementsByTagName("IOWebPage");
								for (int n = 0; n < elementList3.getLength(); n++) {
									workElement4 = (org.w3c.dom.Element)elementList3.item(n);
									if (workElement4.getAttribute("ID").equals(workElement2.getAttribute("IOID"))) {
										ioSortKey = workElement4.getAttribute("SortKey");
										ioName = workElement4.getAttribute("Name");
										break;
									}
								}
							}
						}
						break;
					}
				}

				for (Function func : functionListInto) {
					if (func.getSortKey().equals(functionSortKey)) {
						targetFunctionID = func.getId();
						if (workElement2.getAttribute("IOID").equals("0")) {
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
					tfio.setIoIdSeq(workElement2.getAttribute("IOIDSeq"));
					tfio.setOperations(workElement2.getAttribute("Operations"));
					tfio.setSortKey(workElement2.getAttribute("SortKey"));
					taskAction.getTaskFunctionIOs().add(tfio);
				}
			}

			taskInto.getTaskActions().add(taskAction);
		}
	}

	void adjustFunctionsCalled(org.w3c.dom.Element elementFrom, Function functionInto) {
		org.w3c.dom.Element workElement1;
		NodeList elementList1;
		String internalID1, internalID2;
		int countEquals = 0;
		org.w3c.dom.Element functionElement;
		String functionName = "";
		String functionSortKey = "";
		String functionNameCalling = "";
		String functionSortKeyCalling = "";

		NodeList functionList = domDocumentFrom.getElementsByTagName("Function");
		elementList1 = elementFrom.getElementsByTagName("FunctionUsedByThis");

		for (int i = 0; i < elementList1.getLength(); i++) {
			workElement1 = (org.w3c.dom.Element)elementList1.item(i);
			internalID1 = workElement1.getAttribute("FunctionID");
			internalID2 = convertInternalIDOfTheTag(internalID1, "Function");
			if (internalID2.equals("")) {
				missingFunctionCounter++;
				for (int m = 0; m < functionList.getLength(); m++) {
					functionElement = (org.w3c.dom.Element)functionList.item(m);
					if (internalID1.equals(functionElement.getAttribute("ID"))) {
						functionName = functionElement.getAttribute("Name");
						functionSortKey = functionElement.getAttribute("SortKey");
					}
					if (elementFrom.getAttribute("ID").equals(functionElement.getAttribute("ID"))) {
						functionNameCalling = functionElement.getAttribute("Name");
						functionSortKeyCalling = functionElement.getAttribute("SortKey");
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
					newFUB.setLaunchEvent(workElement1.getAttribute("LaunchEvent"));
					newFUB.setSortKey(workElement1.getAttribute("SortKey"));
					functionInto.getFunctionUsedByThisList().add(newFUB);
				}

				if (countEquals == 1) {
					targetFUB.setLaunchEvent(workElement1.getAttribute("LaunchEvent"));
					targetFUB.setSortKey(workElement1.getAttribute("SortKey"));
				}
			}
		}
	}

	void importTypeDefinitionWithTagName(String tagName) {
		int checkCount = 0;
		int lastID = 0;
		org.w3c.dom.Element elementFrom;

		NodeList typeListFrom = domDocumentFrom.getElementsByTagName(tagName);
		XeadSystem xeadSystem = frame_.getXeadDocumentModel().getXeadSystem();

		jProgressBar.setString("Importing Type List...");
		jProgressBar.setValue(0);
		jProgressBar.setMaximum(typeListFrom.getLength());

		for (int i = 0; i < typeListFrom.getLength(); i++) {
			jProgressBar.setValue(jProgressBar.getValue() + 1);
			jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());

			elementFrom = (org.w3c.dom.Element)typeListFrom.item(i);
			checkCount = 0;

			if (tagName.equals("Department")) {
				for (Department dept : xeadSystem.getDepartments()) {
					if (dept.getSortKey().equals(elementFrom.getAttribute("SortKey"))) {
						checkCount++;
						dept.setName(elementFrom.getAttribute("Name"));
						dept.setDescriptions(elementFrom.getAttribute("Descriptions"));
						break;
					}
				}
				if (checkCount == 0) {
					lastID = getLastIDOfTheType("Department");
					Department newDept = new Department();
					newDept.setId(Integer.toString(lastID + 1));
					newDept.setSortKey(elementFrom.getAttribute("SortKey"));
					newDept.setName(elementFrom.getAttribute("Name"));
					newDept.setDescriptions(elementFrom.getAttribute("Descriptions"));
					xeadSystem.getDepartments().add(newDept);
				}
			} else if (tagName.equals("TaskType")) {
				for (TaskType tt : xeadSystem.getTaskTypes()) {
					if (tt.getSortKey().equals(elementFrom.getAttribute("SortKey"))) {
						checkCount++;
						tt.setName(elementFrom.getAttribute("Name"));
						tt.setDescriptions(elementFrom.getAttribute("Descriptions"));
						break;
					}
				}
				if (checkCount == 0) {
					lastID = getLastIDOfTheType("TaskType");
					TaskType newTT = new TaskType();
					newTT.setId(Integer.toString(lastID + 1));
					newTT.setSortKey(elementFrom.getAttribute("SortKey"));
					newTT.setName(elementFrom.getAttribute("Name"));
					newTT.setDescriptions(elementFrom.getAttribute("Descriptions"));
					xeadSystem.getTaskTypes().add(newTT);
				}
			} else if (tagName.equals("TableType")) {
				for (TableType tt : xeadSystem.getTableTypes()) {
					if (tt.getSortKey().equals(elementFrom.getAttribute("SortKey"))) {
						checkCount++;
						tt.setName(elementFrom.getAttribute("Name"));
						tt.setDescriptions(elementFrom.getAttribute("Descriptions"));
						break;
					}
				}
				if (checkCount == 0) {
					lastID = getLastIDOfTheType("TableType");
					TableType newTT = new TableType();
					newTT.setId(Integer.toString(lastID + 1));
					newTT.setSortKey(elementFrom.getAttribute("SortKey"));
					newTT.setName(elementFrom.getAttribute("Name"));
					newTT.setDescriptions(elementFrom.getAttribute("Descriptions"));
					xeadSystem.getTableTypes().add(newTT);
				}
			} else if (tagName.equals("FunctionType")) {
				for (FunctionType ft : xeadSystem.getFunctionTypes()) {
					if (ft.getSortKey().equals(elementFrom.getAttribute("SortKey"))) {
						checkCount++;
						ft.setName(elementFrom.getAttribute("Name"));
						ft.setDescriptions(elementFrom.getAttribute("Descriptions"));
						break;
					}
				}
				if (checkCount == 0) {
					lastID = getLastIDOfTheType("FunctionType");
					FunctionType newFT = new FunctionType();
					newFT.setId(Integer.toString(lastID + 1));
					newFT.setSortKey(elementFrom.getAttribute("SortKey"));
					newFT.setName(elementFrom.getAttribute("Name"));
					newFT.setDescriptions(elementFrom.getAttribute("Descriptions"));
					xeadSystem.getFunctionTypes().add(newFT);
				}
			} else if (tagName.equals("DataType")) {
				for (DataType dt : xeadSystem.getDataTypes()) {
					if (dt.getSortKey().equals(elementFrom.getAttribute("SortKey"))) {
						checkCount++;
						dt.setName(elementFrom.getAttribute("Name"));
						dt.setBasicType(elementFrom.getAttribute("BasicType"));
						dt.setLength(elementFrom.getAttribute("Length"));
						dt.setDecimal(elementFrom.getAttribute("Decimal"));
						dt.setSqlExpression(elementFrom.getAttribute("SQLExpression"));
						break;
					}
				}
				if (checkCount == 0) {
					lastID = getLastIDOfTheType("DataType");
					DataType newDT = new DataType();
					newDT.setId(Integer.toString(lastID + 1));
					newDT.setSortKey(elementFrom.getAttribute("SortKey"));
					newDT.setName(elementFrom.getAttribute("Name"));
					newDT.setBasicType(elementFrom.getAttribute("BasicType"));
					newDT.setLength(elementFrom.getAttribute("Length"));
					newDT.setDecimal(elementFrom.getAttribute("Decimal"));
					newDT.setSqlExpression(elementFrom.getAttribute("SQLExpression"));
					xeadSystem.getDataTypes().add(newDT);
				}
			}
		}
	}

	String convertInternalIDOfTheTypeTag(String originalInternalID, String tagName) {
		String convertedInternalID = "";
		String defaultID = "";
		String defaultSortKey = "";
		String sortKey = "";
		org.w3c.dom.Element element = null;

		NodeList nodeList = domDocumentFrom.getElementsByTagName(tagName);
		for (int i = 0; i < nodeList.getLength(); i++) {
			element = (org.w3c.dom.Element)nodeList.item(i);
			if (element.getAttribute("ID").equals(originalInternalID)) {
				sortKey = element.getAttribute("SortKey");
				break;
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
		org.w3c.dom.Element element = null;

		NodeList nodeList = domDocumentFrom.getElementsByTagName(tagName);
		for (int i = 0; i < nodeList.getLength(); i++) {
			element = (org.w3c.dom.Element)nodeList.item(i);
			if (element.getAttribute("ID").equals(originalInternalID)) {
				sortKey = element.getAttribute("SortKey");
				name = element.getAttribute("Name");
				break;
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

	String convertInternalIDOfTableField(String originalInternalTableID, String originalInternalFieldID, org.w3c.dom.Document documentFrom, org.w3c.dom.Document documentInto) {
		return convertInternalIDOfTableField(originalInternalTableID, originalInternalFieldID, documentFrom, frame_.getXeadDocumentModel().getXeadSystem());
	}

	String convertInternalIDOfTableField(String originalInternalTableID, String originalInternalFieldID, org.w3c.dom.Document documentFrom, XeadSystem xeadSystemInto) {
		String convertedInternalFieldID = "";
		String externalIDOfTable = "";
		String externalIDOfField = "";
		String nameOfField = "";
		org.w3c.dom.Element tableElement = null;
		org.w3c.dom.Element element = null;

		NodeList nodeList = documentFrom.getElementsByTagName("Table");
		for (int i = 0; i < nodeList.getLength(); i++) {
			element = (org.w3c.dom.Element)nodeList.item(i);
			if (element.getAttribute("ID").equals(originalInternalTableID)) {
				externalIDOfTable = element.getAttribute("SortKey");
				tableElement = element;
				break;
			}
		}

		if (tableElement != null) {
			nodeList = tableElement.getElementsByTagName("TableField");
			for (int i = 0; i < nodeList.getLength(); i++) {
				element = (org.w3c.dom.Element)nodeList.item(i);
				if (element.getAttribute("ID").equals(originalInternalFieldID)) {
					externalIDOfField = element.getAttribute("Alias");
					nameOfField = element.getAttribute("Name");
					break;
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
		org.w3c.dom.Element element1 = null, element2 = null;
		NodeList nodeList1, nodeList2;

		nodeList1 = domDocumentFrom.getElementsByTagName("Table");
		for (int i = 0; i < nodeList1.getLength(); i++) {
			element1 = (org.w3c.dom.Element)nodeList1.item(i);
			if (element1.getAttribute("ID").equals(originalInternalTableID)) {
				nodeList2 = element1.getElementsByTagName("TableKey");
				for (int j = 0; j < nodeList2.getLength(); j++) {
					element2 = (org.w3c.dom.Element)nodeList2.item(j);
					if (element2.getAttribute("ID").equals(originalInternalKeyID)) {
						keyType = element2.getAttribute("Type");
						break;
					}
				}
				break;
			}
		}

		Table targetTable = null;
		for (Table t : xeadSystemInto.getTables()) {
			if (t.getId().equals(targetTableID)) {
				targetTable = t;
				break;
			}
		}

		if (targetTable != null) {
			for (TableKey tk : targetTable.getTableKeys()) {
				if (tk.getType().equals(keyType)) {
					if (theseAreIdenticalKeyDefinitionsWithElementAndKey(element2, tk, originalInternalTableID, xeadSystemInto)) {
						convertedInternalKeyID = tk.getId();
					}
				}
			}
		}
		return convertedInternalKeyID;
	}

	/**
	 * Check if an XML element's key definition matches a JSON TableKey object
	 */
	boolean theseAreIdenticalKeyDefinitionsWithElementAndKey(org.w3c.dom.Element keyElementFrom, TableKey keyInto,
			String tableIDFrom, XeadSystem xeadSystem) {
		if (keyInto == null || keyElementFrom == null) return false;
		String keyTypeFrom = keyElementFrom.getAttribute("Type");
		if (!keyTypeFrom.equals(keyInto.getType())) return false;

		NodeList nodeList1 = keyElementFrom.getElementsByTagName("TableKeyField");
		if (nodeList1.getLength() != keyInto.getTableKeyFields().size()) return false;

		String[] keyFieldIDFrom = new String[30];
		int countOfKeyFieldFrom = 0;
		for (int i = 0; i < nodeList1.getLength(); i++) {
			org.w3c.dom.Element element1 = (org.w3c.dom.Element)nodeList1.item(i);
			keyFieldIDFrom[countOfKeyFieldFrom] = element1.getAttribute("FieldID");
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
			String workStr = convertInternalIDOfTableField(tableIDFrom, keyFieldIDFrom[i], domDocumentFrom, xeadSystem);
			for (int j = 0; j < countOfKeyFieldInto; j++) {
				if (workStr.equals(keyFieldIDInto[j])) {
					keyFieldIDInto[j] = "";
					checkCount++;
				}
			}
		}
		return checkCount == countOfKeyFieldInto;
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
	 * Check if an XML element's key definition matches a JSON TableKey object
	 */
	boolean theseAreIdenticalKeyDefinitionsWithElement(org.w3c.dom.Element keyElementFrom, TableKey keyInto,
			String tableIDFrom, XeadSystem xeadSystem) {
		String keyTypeFrom = keyElementFrom.getAttribute("Type");
		if (!keyTypeFrom.equals(keyInto.getType())) return false;

		NodeList nodeList1 = keyElementFrom.getElementsByTagName("TableKeyField");
		if (nodeList1.getLength() != keyInto.getTableKeyFields().size()) return false;

		String[] keyFieldIDFrom = new String[30];
		int countOfKeyFieldFrom = 0;
		for (int i = 0; i < nodeList1.getLength(); i++) {
			org.w3c.dom.Element element1 = (org.w3c.dom.Element)nodeList1.item(i);
			keyFieldIDFrom[countOfKeyFieldFrom] = element1.getAttribute("FieldID");
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
			String workStr = convertInternalIDOfTableField(tableIDFrom, keyFieldIDFrom[i], domDocumentFrom, xeadSystem);
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

	class XeadNode implements Comparable {
		private String nodeType_;
		private org.w3c.dom.Element domNode_;
		public XeadNode(String type, org.w3c.dom.Element node) {
			super();
			nodeType_ = type;
			domNode_ = node;
		}
		public String toString() {
			String str = "";
			str = this.getName();
			return str;
		}
		public String getName() {
			String str = "";
			if (nodeType_.equals("Role")) {
				str = domNode_.getAttribute("SortKey") + " " + domNode_.getAttribute("Name");
			}
			if (nodeType_.equals("Subsystem")) {
				str = domNode_.getAttribute("SortKey") + " " + domNode_.getAttribute("Name");
			}
			if (nodeType_.equals("SubjectArea")) {
				str = domNode_.getAttribute("SortKey") + " " + domNode_.getAttribute("Name");
			}
			return str;
		}
		public org.w3c.dom.Element getElement() {
			return domNode_;
		}
        public int compareTo(Object other) {
            XeadNode otherNode = (XeadNode)other;
            return domNode_.getAttribute("SortKey").compareTo(otherNode.getElement().getAttribute("SortKey"));
        }
	}

	class SortableXeadNodeComboBoxModel extends DefaultComboBoxModel {
		private static final long serialVersionUID = 1L;
		public void sortElements() {
//			TreeSet<XeadNode> treeSet = new TreeSet<XeadNode>(new NodeComparator());
//			int elementCount = this.getSize();
//			XeadNode node;
//			for (int i = 0; i < elementCount; i++) {
//				node = (XeadNode)this.getElementAt(i);
//				treeSet.add(node);
//			}
//			this.removeAllElements();
//			Iterator<XeadNode> it = treeSet.iterator();
//			while( it.hasNext() ){
//				node = (XeadNode)it.next();
//				this.addElement(node);
//			}
			ArrayList<XeadNode> list = new ArrayList<XeadNode>();
			for (int i = 0; i < this.getSize(); i++) {
				list.add((XeadNode)this.getElementAt(i));
			}
			this.removeAllElements();
			Collections.sort(list);
			Iterator<XeadNode> it = list.iterator();
			while(it.hasNext()){
				this.addElement(it.next());
			}
		}
	}

//	class NodeComparator implements java.util.Comparator<XeadNode> {
//		public int compare(XeadNode node1, XeadNode node2) {
//			String value1, value2;
//			value1 = node1.getElement().getAttribute("SortKey");
//			value2 = node2.getElement().getAttribute("SortKey");
//			int compareResult = value1.compareTo(value2);
//			if (compareResult == 0) {
//				value1 = node1.getElement().getAttribute("ID");
//				value2 = node2.getElement().getAttribute("ID");
//				compareResult = value1.compareTo(value2);
//				if (compareResult == 0) {
//					compareResult = 1;
//				}
//			}
//			return(compareResult);
//		}
//	}

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
