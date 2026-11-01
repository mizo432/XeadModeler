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
import java.util.HashMap;
import java.util.List;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import org.apache.xerces.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;

import xeadModeler.Modeler;
import xeadModeler.model.json.*;

public class DialogToListChangesOfFiles extends JDialog {
	private static final long serialVersionUID = 1L;
	private static ResourceBundle res = ResourceBundle.getBundle("xeadModeler.Res");
	private JPanel panelMain = new JPanel();
	private JScrollPane jScrollPane = new JScrollPane();
	private JProgressBar jProgressBar = new JProgressBar();
	private JButton jButtonStart = new JButton();
	private JCheckBox jCheckBoxListDiffDDL = new JCheckBox();
	private JButton jButtonCancel = new JButton();
	private JTextArea jTextArea = new JTextArea();
	private JLabel jLabel1 = new JLabel();
	private JTextField jTextFieldImportFileName = new JTextField();
	private JLabel jLabel2 = new JLabel();
	private JTextField jTextFieldImportSystemName = new JTextField();
	private Modeler frame_;
	private org.w3c.dom.Document domDocumentOld;
	private org.w3c.dom.Element systemElementOld;
	private XeadSystem xeadSystemNew;
	private int countOfChanges = 0;
	private boolean containsValueToBeCorrected = false;
	private StringBuffer buffer;
	private HashMap<String, DataType> newDataTypeMap = new HashMap<String, DataType>();
	private HashMap<String, org.w3c.dom.Element> oldDataTypeElementMap = new HashMap<String, org.w3c.dom.Element>();
	
	public DialogToListChangesOfFiles(Modeler frame, String title, boolean modal) {
		super(frame, title, modal);
		try {
			frame_ = frame;
			jbInit();
			pack();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public DialogToListChangesOfFiles(Modeler frame) {
		this(frame, "", true);
	}

	private void jbInit() throws Exception {
		this.setResizable(false);
		this.setTitle(res.getString("DialogToListChangesOfFiles1"));

		jLabel1.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel1.setText(res.getString("DialogToListChangesOfFiles3"));
		jLabel1.setBounds(new Rectangle(5, 12, 170, 20));
		jTextFieldImportFileName.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jTextFieldImportFileName.setBounds(new Rectangle(180, 9, 650, 25));
		jTextFieldImportFileName.setEditable(false);

		jLabel2.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel2.setText(res.getString("DialogToListChangesOfFiles4"));
		jLabel2.setBounds(new Rectangle(5, 43, 170, 20));
		jTextFieldImportSystemName.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jTextFieldImportSystemName.setBounds(new Rectangle(180, 40, 650, 25));
		jTextFieldImportSystemName.setEditable(false);

		jScrollPane.setBounds(new Rectangle(9, 73, 822, 458));
		jScrollPane.setBorder(BorderFactory.createLoweredBevelBorder());
		jScrollPane.getViewport().add(jTextArea);
		jTextArea.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jTextArea.setEditable(false);
		jTextArea.setLineWrap(true);
		jTextArea.setBackground(SystemColor.control);
		jTextArea.setBorder(null);

		jProgressBar.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jProgressBar.setBounds(new Rectangle(30, 540, 420, 30));
		jProgressBar.setStringPainted(true);
		jProgressBar.setVisible(false);

		jButtonStart.setBounds(new Rectangle(30, 540, 420, 30));
		jButtonStart.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jButtonStart.setText(res.getString("DialogToListChangesOfFiles5"));
		jButtonStart.addActionListener(new DialogToListChangesOfFiles_jButtonStart_actionAdapter(this));

		jCheckBoxListDiffDDL.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jCheckBoxListDiffDDL.setText(res.getString("DialogToListChangesOfFiles36"));
		jCheckBoxListDiffDDL.setBounds(new Rectangle(460, 545, 235, 20));

		jButtonCancel.setBounds(new Rectangle(700, 540, 110, 30));
		jButtonCancel.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jButtonCancel.setText(res.getString("DialogToListChangesOfFiles6"));
		jButtonCancel.addActionListener(new DialogToListChangesOfFiles_jButtonCancel_actionAdapter(this));

		panelMain.setLayout(null);
		panelMain.setPreferredSize(new Dimension(840, 583));
		panelMain.setBorder(BorderFactory.createEtchedBorder());
		panelMain.add(jLabel1, null);
		panelMain.add(jLabel2, null);
		panelMain.add(jTextFieldImportFileName, null);
		panelMain.add(jTextFieldImportSystemName, null);
		panelMain.add(jScrollPane, null);
		panelMain.add(jProgressBar, null);
		panelMain.add(jButtonStart, null);
		panelMain.add(jCheckBoxListDiffDDL, null);
		panelMain.add(jButtonCancel, null);

		this.getContentPane().add(panelMain, BorderLayout.SOUTH);
		this.getRootPane().setDefaultButton(jButtonStart);

		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension dlgSize = this.getPreferredSize();
		this.setLocation((scrSize.width - dlgSize.width)/2 , (scrSize.height - dlgSize.height)/2);
		this.pack();
	}

	public void request(String fileName) {
		try {
			DOMParser parser = new DOMParser();
			parser.parse(new InputSource(new FileInputStream(fileName)));
			domDocumentOld = parser.getDocument();

			NodeList elementList = domDocumentOld.getElementsByTagName("System");
			systemElementOld = (org.w3c.dom.Element)elementList.item(0);
			xeadSystemNew = frame_.getXeadDocumentModel().getXeadSystem();

			float importFileFormat = Float.parseFloat(systemElementOld.getAttribute("FormatVersion"));
			float appliFormat = Float.parseFloat(DialogAbout.FORMAT_VERSION);
			if (importFileFormat > appliFormat) {
				JOptionPane.showMessageDialog(this, res.getString("S1") + systemElementOld.getAttribute("FormatVersion") + res.getString("S2") + DialogAbout.FORMAT_VERSION + res.getString("S3"));
			} else {
				jTextFieldImportFileName.setText(fileName);
				jTextFieldImportSystemName.setText(systemElementOld.getAttribute("Name") + " V" + systemElementOld.getAttribute("Version"));
				jTextArea.setText(res.getString("DialogToListChangesOfFiles2"));
				jProgressBar.setValue(0);
				jProgressBar.setString(res.getString("DialogToListChangesOfFiles37"));
				jButtonStart.setEnabled(true);
				jCheckBoxListDiffDDL.setEnabled(true);
				this.getRootPane().setDefaultButton(jButtonStart);
				super.setVisible(true);
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "XML format of the file is invalid. Process is canceled.");
		}
	}

	void jButtonStart_actionPerformed(ActionEvent e) {
		try {
			setCursor(new Cursor(Cursor.WAIT_CURSOR));

			jButtonStart.setVisible(false);
			jProgressBar.setVisible(true);
			setupProgressMaxValue();
			buffer = new StringBuffer();
			countOfChanges = 0;

			listChangesOfBasicInfo();
			listChangesOfDataflow();
			listChangesOfRoleAndTask();
 		listChangesOfSubsystem();
			listChangesOfTable();
			listChangesOfFunction();

			////////////////////////////////////
			// Generate text file of Diff-DDL //
			////////////////////////////////////
			if (jCheckBoxListDiffDDL.isSelected()) {
				FileWriter fileWriter = null;
				NodeList oldTableElementList = systemElementOld.getElementsByTagName("Table");
				List<Table> newTableList = xeadSystemNew.getTables();
				BufferedWriter bufferedWriter = null;
				try {
					String diffDDL = getDiffDDL(newTableList, oldTableElementList);
					if (!diffDDL.equals("")) {
						File textFile = File.createTempFile("AlterTables" + frame_.getStringValueOfDateTime("withTime"), ".txt");
						String textFileName = textFile.getPath();
						fileWriter = new FileWriter(textFileName);
						bufferedWriter = new BufferedWriter(fileWriter);
						if (containsValueToBeCorrected) {
							diffDDL = frame_.ddlCommentMark + "Warning: It contains constraint id(s) indicated as '???' that should be checked and corrected before executing.\n\n" + diffDDL;
						}
						bufferedWriter.write(diffDDL);
						bufferedWriter.flush();
						frame_.desktop.open(textFile);
					}
				} catch (Exception ex1) {
					ex1.printStackTrace();
				} finally {
					try {
						bufferedWriter.close();
					} catch (Exception ex2) {}
				}
			}

		} finally {
			jTextArea.setText(countOfChanges + res.getString("DialogToListChangesOfFiles35") + buffer.toString());
			jTextArea.setCaretPosition(0);

			jProgressBar.setVisible(false);
			jButtonStart.setVisible(true);
			jButtonStart.setEnabled(false);
			jCheckBoxListDiffDDL.setEnabled(false);
			this.getRootPane().setDefaultButton(jButtonCancel);

			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
	}

	void setupProgressMaxValue() {
		NodeList workElementList;
		int countOfElementsProcessed = 0;

		workElementList = systemElementOld.getElementsByTagName("Department");
		countOfElementsProcessed = countOfElementsProcessed + workElementList.getLength();
		countOfElementsProcessed = countOfElementsProcessed + xeadSystemNew.getDepartments().size();
		workElementList = systemElementOld.getElementsByTagName("TaskType");
		countOfElementsProcessed = countOfElementsProcessed + workElementList.getLength();
		countOfElementsProcessed = countOfElementsProcessed + xeadSystemNew.getTaskTypes().size();
		workElementList = systemElementOld.getElementsByTagName("TableType");
		countOfElementsProcessed = countOfElementsProcessed + workElementList.getLength();
		countOfElementsProcessed = countOfElementsProcessed + xeadSystemNew.getTableTypes().size();
		workElementList = systemElementOld.getElementsByTagName("DataType");
		countOfElementsProcessed = countOfElementsProcessed + workElementList.getLength();
		countOfElementsProcessed = countOfElementsProcessed + xeadSystemNew.getDataTypes().size();
		workElementList = systemElementOld.getElementsByTagName("FunctionType");
		countOfElementsProcessed = countOfElementsProcessed + workElementList.getLength();
		countOfElementsProcessed = countOfElementsProcessed + xeadSystemNew.getFunctionTypes().size();
		workElementList = systemElementOld.getElementsByTagName("Terms");
		countOfElementsProcessed = countOfElementsProcessed + workElementList.getLength();
		countOfElementsProcessed = countOfElementsProcessed + xeadSystemNew.getTerms().size();
		workElementList = systemElementOld.getElementsByTagName("MaintenanceLog");
		countOfElementsProcessed = countOfElementsProcessed + workElementList.getLength();
		countOfElementsProcessed = countOfElementsProcessed + xeadSystemNew.getMaintenanceLogs().size();

		workElementList = systemElementOld.getElementsByTagName("SubjectArea");
		countOfElementsProcessed = countOfElementsProcessed + workElementList.getLength();
		countOfElementsProcessed = countOfElementsProcessed + xeadSystemNew.getSubjectAreas().size();

		workElementList = systemElementOld.getElementsByTagName("Role");
		countOfElementsProcessed = countOfElementsProcessed + workElementList.getLength();
		countOfElementsProcessed = countOfElementsProcessed + xeadSystemNew.getRoles().size();
		workElementList = systemElementOld.getElementsByTagName("Task");
		countOfElementsProcessed = countOfElementsProcessed + workElementList.getLength();
		countOfElementsProcessed = countOfElementsProcessed + xeadSystemNew.getTasks().size();

		workElementList = systemElementOld.getElementsByTagName("Subsystem");
		countOfElementsProcessed = countOfElementsProcessed + workElementList.getLength();
		countOfElementsProcessed = countOfElementsProcessed + xeadSystemNew.getSubsystems().size();
		workElementList = systemElementOld.getElementsByTagName("Table");
		countOfElementsProcessed = countOfElementsProcessed + workElementList.getLength();
		countOfElementsProcessed = countOfElementsProcessed + xeadSystemNew.getTables().size();
		workElementList = systemElementOld.getElementsByTagName("Function");
		countOfElementsProcessed = countOfElementsProcessed + workElementList.getLength();
		countOfElementsProcessed = countOfElementsProcessed + xeadSystemNew.getFunctions().size();

		jProgressBar.setMaximum(countOfElementsProcessed + 1); // System Info counts 1//
	}

	void listChangesOfBasicInfo() {
		NodeList oldElementList;
		String tagName;
		ArrayList<String> attrList = new ArrayList<String>();
		int workCount = countOfChanges;
		buffer.append("\n\n<" + res.getString("DialogToListChangesOfFiles8") + ">");

		/////////////////////////
		// System Descriptions //
		/////////////////////////
		compareNewAndOldSystem(xeadSystemNew, systemElementOld, "Name", res.getString("DialogToListChangesOfFiles9"));
		compareNewAndOldSystem(xeadSystemNew, systemElementOld, "Version", res.getString("DialogToListChangesOfFiles9"));
		compareNewAndOldSystem(xeadSystemNew, systemElementOld, "Descriptions", res.getString("DialogToListChangesOfFiles9"));

		/////////////////
		// Departments //
		/////////////////
		tagName = "Department";
		attrList.clear();
		attrList.add("Name");
		attrList.add("Descriptions");
		oldElementList = systemElementOld.getElementsByTagName(tagName);
		checkIfNewElementChangedOrAdded(xeadSystemNew.getDepartments(), oldElementList, attrList, tagName, "Name");
		checkIfNewElementDeleted(xeadSystemNew.getDepartments(), oldElementList, tagName, "Name");

		////////////////
		// Task Types //
		////////////////
		tagName = "TaskType";
		attrList.clear();
		attrList.add("Name");
		attrList.add("Descriptions");
		oldElementList = systemElementOld.getElementsByTagName(tagName);
		checkIfNewElementChangedOrAdded(xeadSystemNew.getTaskTypes(), oldElementList, attrList, tagName, "Name");
		checkIfNewElementDeleted(xeadSystemNew.getTaskTypes(), oldElementList, tagName, "Name");

		/////////////////
		// Table Types //
		/////////////////
		tagName = "TableType";
		attrList.clear();
		attrList.add("Name");
		attrList.add("Descriptions");
		oldElementList = systemElementOld.getElementsByTagName(tagName);
		checkIfNewElementChangedOrAdded(xeadSystemNew.getTableTypes(), oldElementList, attrList, tagName, "Name");
		checkIfNewElementDeleted(xeadSystemNew.getTableTypes(), oldElementList, tagName, "Name");

		////////////////
		// Data Types //
		////////////////
		tagName = "DataType";
		attrList.clear();
		attrList.add("Name");
		attrList.add("BasicType");
		attrList.add("Length");
		attrList.add("Decimal");
		attrList.add("SQLExpression");
		oldElementList = systemElementOld.getElementsByTagName(tagName);
		checkIfNewElementChangedOrAdded(xeadSystemNew.getDataTypes(), oldElementList, attrList, tagName, "Name");
		checkIfNewElementDeleted(xeadSystemNew.getDataTypes(), oldElementList, tagName, "Name");

		////////////////////
		// Function Types //
		////////////////////
		tagName = "FunctionType";
		attrList.clear();
		attrList.add("Name");
		attrList.add("Descriptions");
		oldElementList = systemElementOld.getElementsByTagName(tagName);
		checkIfNewElementChangedOrAdded(xeadSystemNew.getFunctionTypes(), oldElementList, attrList, tagName, "Name");
		checkIfNewElementDeleted(xeadSystemNew.getFunctionTypes(), oldElementList, tagName, "Name");

		///////////
		// Terms //
		///////////
		tagName = "Terms";
		attrList.clear();
		attrList.add("Header");
		attrList.add("HtmlFileName");
		attrList.add("Descriptions");
		oldElementList = systemElementOld.getElementsByTagName(tagName);
		checkIfNewElementChangedOrAdded(xeadSystemNew.getTerms(), oldElementList, attrList, tagName, "Header");
		checkIfNewElementDeleted(xeadSystemNew.getTerms(), oldElementList, tagName, "Header");

		/////////////////////
		// Maintenance Log //
		/////////////////////
		tagName = "MaintenanceLog";
		attrList.clear();
		attrList.add("Headder");
		attrList.add("Descriptions");
		oldElementList = systemElementOld.getElementsByTagName(tagName);
		checkIfNewElementChangedOrAdded(xeadSystemNew.getMaintenanceLogs(), oldElementList, attrList, tagName, "Header");
		checkIfNewElementDeleted(xeadSystemNew.getMaintenanceLogs(), oldElementList, tagName, "Header");

		////////////////////////////////////////
		// Set message if with no differences //
		////////////////////////////////////////
		if (countOfChanges == workCount) {
			buffer.append("\n" + res.getString("DialogToListChangesOfFiles7"));
		}
	}

	void listChangesOfDataflow() {
		NodeList oldElementList;
		String tagName;
		ArrayList<String> attrList = new ArrayList<String>();
		int workCount = countOfChanges;
		buffer.append("\n\n<" + res.getString("S3385") + ">");

		///////////////////
		// Subject Areas //
		///////////////////
		tagName = "SubjectArea";
		attrList.clear();
		attrList.add("Name");
		attrList.add("Descriptions");
		oldElementList = systemElementOld.getElementsByTagName(tagName);
		checkIfNewElementChangedOrAdded(xeadSystemNew.getSubjectAreas(), oldElementList, attrList, tagName, "Name");
		checkIfNewElementDeleted(xeadSystemNew.getSubjectAreas(), oldElementList, tagName, "Name");

		////////////////////////////////////////
		// Set message if with no differences //
		////////////////////////////////////////
		if (countOfChanges == workCount) {
			buffer.append("\n" + res.getString("DialogToListChangesOfFiles7"));
		}
	}

	void listChangesOfRoleAndTask() {
		NodeList oldElementList;
		String tagName;
		ArrayList<String> attrList = new ArrayList<String>();
		int workCount = countOfChanges;
		buffer.append("\n\n<" + res.getString("S3391") + ">");

		///////////
		// Roles //
		///////////
		tagName = "Role";
		attrList.clear();
		attrList.add("Name");
		attrList.add("DepartmentID");
		attrList.add("Descriptions");
		oldElementList = systemElementOld.getElementsByTagName(tagName);
		checkIfNewElementChangedOrAdded(xeadSystemNew.getRoles(), oldElementList, attrList, tagName, "Name");
		checkIfNewElementDeleted(xeadSystemNew.getRoles(), oldElementList, tagName, "Name");

		//////////
		// Task //
		//////////
		tagName = "Task";
		attrList.clear();
		attrList.add("Name");
		attrList.add("Descriptions");
		attrList.add("Event");
		attrList.add("TaskTypeID");
		attrList.add("RoleID");
		oldElementList = systemElementOld.getElementsByTagName(tagName);
		checkIfNewElementChangedOrAdded(xeadSystemNew.getTasks(), oldElementList, attrList, tagName, "Name");
		checkIfNewElementDeleted(xeadSystemNew.getTasks(), oldElementList, tagName, "Name");

		////////////////////////////////////////
		// Set message if with no differences //
		////////////////////////////////////////
		if (countOfChanges == workCount) {
			buffer.append("\n" + res.getString("DialogToListChangesOfFiles7"));
		}
	}

	void listChangesOfSubsystem() {
		NodeList oldElementList;
		String tagName;
		ArrayList<String> attrList = new ArrayList<String>();
		int workCount = countOfChanges;
		buffer.append("\n\n<" + res.getString("S413") + ">");

		////////////////
		// Subsystems //
		////////////////
		tagName = "Subsystem";
		attrList.clear();
		attrList.add("Name");
		attrList.add("Descriptions");
		attrList.add("DatamodelDescriptions");
		oldElementList = systemElementOld.getElementsByTagName(tagName);
		checkIfNewElementChangedOrAdded(xeadSystemNew.getSubsystems(), oldElementList, attrList, tagName, "Name");
		checkIfNewElementDeleted(xeadSystemNew.getSubsystems(), oldElementList, tagName, "Name");

		////////////////////////////////////////
		// Set message if with no differences //
		////////////////////////////////////////
		if (countOfChanges == workCount) {
			buffer.append("\n" + res.getString("DialogToListChangesOfFiles7"));
		}
	}

	void listChangesOfTable() {
		NodeList oldElementList;
		String tagName;
		ArrayList<String> attrList = new ArrayList<String>();
		int workCount = countOfChanges;
		containsValueToBeCorrected = false;
		buffer.append("\n\n<" + res.getString("DialogToListChangesOfFiles10") + ">");

		////////////
		// Tables //
		////////////
		tagName = "Table";
		attrList.clear();
		attrList.add("Name");
		attrList.add("Descriptions");
		attrList.add("Alias");
		attrList.add("SynchFile");
		attrList.add("TableTypeID");
		attrList.add("SubsystemID");
		oldElementList = systemElementOld.getElementsByTagName(tagName);
		checkIfNewElementChangedOrAdded(xeadSystemNew.getTables(), oldElementList, attrList, tagName, "Name");
		checkIfNewElementDeleted(xeadSystemNew.getTables(), oldElementList, tagName, "Name");

		////////////////////////////////////////
		// Set message if with no differences //
		////////////////////////////////////////
		if (countOfChanges == workCount) {
			buffer.append("\n" + res.getString("DialogToListChangesOfFiles7"));
		}
	}

	void listChangesOfFunction() {
		NodeList oldElementList;
		String tagName;
		ArrayList<String> attrList = new ArrayList<String>();
		int workCount = countOfChanges;
		buffer.append("\n\n<" + res.getString("DialogToListChangesOfFiles14") + ">");

		///////////////
		// Functions //
		///////////////
		tagName = "Function";
		attrList.clear();
		attrList.add("Name");
		attrList.add("Descriptions");
		attrList.add("Summary");
		attrList.add("Parameters");
		attrList.add("Return");
		attrList.add("FunctionTypeID");
		attrList.add("SubsystemID");
		oldElementList = systemElementOld.getElementsByTagName(tagName);
		checkIfNewElementChangedOrAdded(xeadSystemNew.getFunctions(), oldElementList, attrList, tagName, "Name");
		checkIfNewElementDeleted(xeadSystemNew.getFunctions(), oldElementList, tagName, "Name");

		////////////////////////////////////////
		// Set message if with no differences //
		////////////////////////////////////////
		if (countOfChanges == workCount) {
			buffer.append("\n" + res.getString("DialogToListChangesOfFiles7"));
		}
	}

	// JSON-overloaded helper methods

	private String getKeyFieldIDsOfJson(Table table, TableKey tableKey) {
		String keyFieldIDs = "";
		List<TableKeyField> keyFieldList = tableKey.getTableKeyFields();
		if (keyFieldList == null) {
			return keyFieldIDs;
		}
		List<TableField> tableFieldList = table.getTableFields();
		if (tableFieldList == null) {
			return keyFieldIDs;
		}

		// Sort key fields by sortKey
		List<TableKeyField> sortedList = new ArrayList<TableKeyField>(keyFieldList);
		Collections.sort(sortedList, new Comparator<TableKeyField>() {
			public int compare(TableKeyField o1, TableKeyField o2) {
				return Integer.compare(Integer.parseInt(o1.getSortKey()), Integer.parseInt(o2.getSortKey()));
			}
		});

		for (int i = 0; i < sortedList.size(); i++) {
			TableKeyField keyField = sortedList.get(i);
			TableField field = null;
			for (TableField tf : tableFieldList) {
				if (tf.getId().equals(keyField.getFieldId())) {
					field = tf;
					break;
				}
			}
			if (field != null) {
				if (i > 0) {
					keyFieldIDs = keyFieldIDs + ", ";
				}
				if (field.getAlias() == null || field.getAlias().equals("")) {
					keyFieldIDs = keyFieldIDs + field.getName();
				} else {
					keyFieldIDs = keyFieldIDs + field.getAlias();
				}
			}
		}
		return keyFieldIDs;
	}

	private String getSortedKeyFieldNames(Table table, TableKey tableKey) {
		return getKeyFieldIDsOfJson(table, tableKey);
	}

	private boolean isLackingForeignKeyConstraintOfJson(Relationship rel, Table table, XeadSystem xeadSystem,
			org.w3c.dom.Element oldTableElement, org.w3c.dom.Element systemElementOld) {
		// Check if this foreign key constraint exists in the old document
		NodeList oldRelationshipList = systemElementOld.getElementsByTagName("Relationship");
		for (int i = 0; i < oldRelationshipList.getLength(); i++) {
			org.w3c.dom.Element oldRel = (org.w3c.dom.Element)oldRelationshipList.item(i);
			if (oldRel.getAttribute("Table1ID").equals(rel.getTable1Id())
					&& oldRel.getAttribute("TableKey1ID").equals(rel.getTableKey1Id())
					&& oldRel.getAttribute("Table2ID").equals(rel.getTable2Id())
					&& oldRel.getAttribute("TableKey2ID").equals(rel.getTableKey2Id())) {
				return false;
			}
		}
		return true;
	}

	private String getRefferencesOfJson(Relationship rel, XeadSystem xeadSystem) {
		String table1Name = "";
		String table1Alias = "";
		// Find table 1
		for (Table t : xeadSystem.getTables()) {
			if (t.getId().equals(rel.getTable1Id())) {
				table1Name = t.getName();
				table1Alias = t.getAlias() == null ? "" : t.getAlias();
				break;
			}
		}
		String tableName = table1Alias.equals("") ? table1Name : table1Alias;
		return tableName + "(" + getKeyFieldIDsOfJsonForTable(rel.getTable1Id(), rel.getTableKey1Id(), xeadSystem) + ")";
	}

	private String getKeyFieldIDsOfJsonForTable(String tableId, String tableKeyId, XeadSystem xeadSystem) {
		for (Table table : xeadSystem.getTables()) {
			if (table.getId().equals(tableId)) {
				List<TableKey> tableKeyList = table.getTableKeys();
				if (tableKeyList == null) continue;
				for (TableKey tableKey : tableKeyList) {
					if (tableKey.getId().equals(tableKeyId)) {
						return getKeyFieldIDsOfJson(table, tableKey);
					}
				}
			}
		}
		return "";
	}

	// JSON-overloaded compareNewAndOldSystem
	void compareNewAndOldSystem(XeadSystem xeadSystem, org.w3c.dom.Element elementOld, String attribute, String elementLabel) {
		String valueOld, valueNew;
		if (attribute.equals("Name")) {
			valueNew = xeadSystem.getName() == null ? "" : xeadSystem.getName();
		} else if (attribute.equals("Version")) {
			valueNew = xeadSystem.getVersion() == null ? "" : xeadSystem.getVersion();
		} else if (attribute.equals("Descriptions")) {
			valueNew = xeadSystem.getDescriptions() == null ? "" : xeadSystem.getDescriptions();
		} else {
			valueNew = "";
		}
		valueOld = elementOld.getAttribute(attribute);
		if (!valueNew.equals(valueOld)) {
			countOfChanges++;
			buffer.append("\n" + elementLabel + " " + attribute + ": " + valueOld + " -> " + valueNew);
		}
	}

	// JSON-overloaded checkIfNewElementChangedOrAdded
	void checkIfNewElementChangedOrAdded(List<?> newList, NodeList oldElementList, ArrayList<String> attrList, String tagName, String nameAttr) {
		boolean isNotFound;
		String name, attrValueOld, attrValueNew;
		Object newElement;

		for (int i = 0; i < newList.size(); i++) {
			newElement = newList.get(i);
			name = getJsonAttribute(newElement, nameAttr);
			isNotFound = true;
			for (int j = 0; j < oldElementList.getLength(); j++) {
				org.w3c.dom.Element oldElement = (org.w3c.dom.Element)oldElementList.item(j);
				if (oldElement.getAttribute(nameAttr).equals(name)) {
					isNotFound = false;
					for (int k = 0; k < attrList.size(); k++) {
						String attr = attrList.get(k);
						if (!attr.equals(nameAttr)) {
							attrValueNew = getJsonAttribute(newElement, attr);
							attrValueOld = oldElement.getAttribute(attr);
							if (!attrValueNew.equals(attrValueOld)) {
								countOfChanges++;
								buffer.append("\n" + getElementNameAccordingToTagName(tagName) + " " + name + " " + attr + ": " + attrValueOld + " -> " + attrValueNew);
							}
						}
					}
					break;
				}
			}
			if (isNotFound) {
				countOfChanges++;
				buffer.append("\n" + res.getString("DialogToListChangesOfFiles5") + " " + getElementNameAccordingToTagName(tagName) + " " + name);
			}
		}
	}

	// JSON-overloaded checkIfNewElementDeleted
	void checkIfNewElementDeleted(List<?> newList, NodeList oldElementList, String tagName, String nameAttr) {
		String name;
		boolean isNotFound;
		Object newElement;

		for (int i = 0; i < oldElementList.getLength(); i++) {
			org.w3c.dom.Element oldElement = (org.w3c.dom.Element)oldElementList.item(i);
			name = oldElement.getAttribute(nameAttr);
			isNotFound = true;
			for (int j = 0; j < newList.size(); j++) {
				newElement = newList.get(j);
				if (getJsonAttribute(newElement, nameAttr).equals(name)) {
					isNotFound = false;
					break;
				}
			}
			if (isNotFound) {
				countOfChanges++;
				buffer.append("\n" + res.getString("DialogToListChangesOfFiles6") + " " + getElementNameAccordingToTagName(tagName) + " " + name);
			}
		}
	}

	// Helper to get element name from tag name
	private String getElementNameAccordingToTagName(String tagName) {
		String elementName = "???";
		if (tagName.equals("Department")) elementName = res.getString("S341");
		if (tagName.equals("TaskType")) elementName = res.getString("S362");
		if (tagName.equals("TableType")) elementName = res.getString("S477");
		if (tagName.equals("DataType")) elementName = res.getString("S544");
		if (tagName.equals("FunctionType")) elementName = res.getString("S417");
		if (tagName.equals("Terms")) elementName = res.getString("S245");
		if (tagName.equals("MaintenanceLog")) elementName = res.getString("S252");
		if (tagName.equals("SubjectArea")) elementName = res.getString("S3385");
		if (tagName.equals("Role")) elementName = res.getString("S339");
		if (tagName.equals("Task")) elementName = res.getString("S359");
		if (tagName.equals("Subsystem")) elementName = res.getString("S413");
		if (tagName.equals("Table")) elementName = res.getString("DialogToListChangesOfFiles10");
		if (tagName.equals("Function")) elementName = res.getString("DialogToListChangesOfFiles14");
		return elementName;
	}

	// Helper to get attribute from JSON objects
	private String getJsonAttribute(Object obj, String attr) {
		if (obj instanceof DataType) {
			DataType dt = (DataType) obj;
			if (attr.equals("Name")) return dt.getName() == null ? "" : dt.getName();
			if (attr.equals("BasicType")) return dt.getBasicType() == null ? "" : dt.getBasicType();
			if (attr.equals("Length")) return dt.getLength() == null ? "" : dt.getLength();
			if (attr.equals("Decimal")) return dt.getDecimal() == null ? "" : dt.getDecimal();
			if (attr.equals("SQLExpression")) return dt.getSqlExpression() == null ? "" : dt.getSqlExpression();
		} else if (obj instanceof Department) {
			Department d = (Department) obj;
			if (attr.equals("Name")) return d.getName() == null ? "" : d.getName();
			if (attr.equals("Descriptions")) return d.getDescriptions() == null ? "" : d.getDescriptions();
		} else if (obj instanceof TaskType) {
			TaskType tt = (TaskType) obj;
			if (attr.equals("Name")) return tt.getName() == null ? "" : tt.getName();
			if (attr.equals("Descriptions")) return tt.getDescriptions() == null ? "" : tt.getDescriptions();
		} else if (obj instanceof TableType) {
			TableType tt = (TableType) obj;
			if (attr.equals("Name")) return tt.getName() == null ? "" : tt.getName();
			if (attr.equals("Descriptions")) return tt.getDescriptions() == null ? "" : tt.getDescriptions();
		} else if (obj instanceof FunctionType) {
			FunctionType ft = (FunctionType) obj;
			if (attr.equals("Name")) return ft.getName() == null ? "" : ft.getName();
			if (attr.equals("Descriptions")) return ft.getDescriptions() == null ? "" : ft.getDescriptions();
		} else if (obj instanceof Terms) {
			Terms t = (Terms) obj;
			if (attr.equals("Header")) return t.getHeader() == null ? "" : t.getHeader();
			if (attr.equals("HtmlFileName")) return ""; // Terms doesn't have HtmlFileName in JSON model
			if (attr.equals("Descriptions")) return t.getDescriptions() == null ? "" : t.getDescriptions();
		} else if (obj instanceof MaintenanceLog) {
			MaintenanceLog ml = (MaintenanceLog) obj;
			if (attr.equals("Header")) return ml.getHeader() == null ? "" : ml.getHeader();
			if (attr.equals("Descriptions")) return ml.getDescriptions() == null ? "" : ml.getDescriptions();
		} else if (obj instanceof SubjectArea) {
			SubjectArea sa = (SubjectArea) obj;
			if (attr.equals("Name")) return sa.getName() == null ? "" : sa.getName();
			if (attr.equals("Descriptions")) return sa.getDescriptions() == null ? "" : sa.getDescriptions();
		} else if (obj instanceof Role) {
			Role r = (Role) obj;
			if (attr.equals("Name")) return r.getName() == null ? "" : r.getName();
			if (attr.equals("DepartmentID")) return r.getDepartmentId() == null ? "" : r.getDepartmentId();
			if (attr.equals("Descriptions")) return r.getDescriptions() == null ? "" : r.getDescriptions();
		} else if (obj instanceof Task) {
			Task t = (Task) obj;
			if (attr.equals("Name")) return t.getName() == null ? "" : t.getName();
			if (attr.equals("Descriptions")) return t.getDescriptions() == null ? "" : t.getDescriptions();
			if (attr.equals("Event")) return t.getEvent() == null ? "" : t.getEvent();
			if (attr.equals("TaskTypeID")) return t.getTaskTypeId() == null ? "" : t.getTaskTypeId();
			if (attr.equals("RoleID")) return t.getRoleId() == null ? "" : t.getRoleId();
		} else if (obj instanceof Subsystem) {
			Subsystem s = (Subsystem) obj;
			if (attr.equals("Name")) return s.getName() == null ? "" : s.getName();
			if (attr.equals("Descriptions")) return s.getDescriptions() == null ? "" : s.getDescriptions();
			if (attr.equals("DatamodelDescriptions")) return ""; // Subsystem doesn't have this in JSON model
		} else if (obj instanceof Table) {
			Table t = (Table) obj;
			if (attr.equals("Name")) return t.getName() == null ? "" : t.getName();
			if (attr.equals("Descriptions")) return t.getDescriptions() == null ? "" : t.getDescriptions();
			if (attr.equals("Alias")) return t.getAlias() == null ? "" : t.getAlias();
			if (attr.equals("SynchFile")) return t.getSynchFile() == null ? "" : t.getSynchFile();
			if (attr.equals("TableTypeID")) return t.getTableTypeId() == null ? "" : t.getTableTypeId();
			if (attr.equals("SubsystemID")) return t.getSubsystemId() == null ? "" : t.getSubsystemId();
 	} else if (obj instanceof Function) {
 		Function f = (Function) obj;
 		if (attr.equals("Name")) return f.getName() == null ? "" : f.getName();
 		if (attr.equals("Descriptions")) return f.getDescriptions() == null ? "" : f.getDescriptions();
 		if (attr.equals("Summary")) return f.getSummary() == null ? "" : f.getSummary();
 		if (attr.equals("Parameters")) return f.getParameters() == null ? "" : f.getParameters();
 		if (attr.equals("Return")) return f.getReturn() == null ? "" : f.getReturn();
 		if (attr.equals("FunctionTypeID")) return f.getFunctionTypeId() == null ? "" : f.getFunctionTypeId();
 		if (attr.equals("SubsystemID")) return f.getSubsystemId() == null ? "" : f.getSubsystemId();
 	}
 	return "";
	}

	// DDL generation methods

	String getDiffDDL(List<Table> newTableList, NodeList oldTableElementList) {
		StringBuffer bf = new StringBuffer();
		String wrkStr, tableId = "";
		Table newTable;
		org.w3c.dom.Element oldTableElement, workElement;
		NodeList dataTypeList;
		NodeList oldSubsystemList = systemElementOld.getElementsByTagName("Subsystem");

		// Build relationship list from JSON model
		List<Relationship> jsonRelationshipList = xeadSystemNew.getRelationships();

		jProgressBar.setValue(0);
		jProgressBar.setString(res.getString("DialogToListChangesOfFiles38"));
		jProgressBar.setMaximum(newTableList.size() + oldTableElementList.getLength());

		////////////////////////
		// Setup DataType-Map //
		////////////////////////
		newDataTypeMap.clear();
		for (DataType dt : xeadSystemNew.getDataTypes()) {
			newDataTypeMap.put(dt.getId(), dt);
		}
		oldDataTypeElementMap.clear();
		dataTypeList = systemElementOld.getElementsByTagName("DataType");
		for (int i = 0; i < dataTypeList.getLength(); i++) {
			workElement = (org.w3c.dom.Element)dataTypeList.item(i);
			oldDataTypeElementMap.put(workElement.getAttribute("ID"), workElement);
		}

		///////////////////////////////////
		// Setup Create/Alter statements //
		///////////////////////////////////
		for (int i = 0; i < newTableList.size(); i++) {
			newTable = newTableList.get(i);

			jProgressBar.setValue(jProgressBar.getValue() + 1);
			jProgressBar.paintImmediately(0, 0, jProgressBar.getWidth(), jProgressBar.getHeight());

			oldTableElement = findOldTableElement(newTable, oldTableElementList);
			if (oldTableElement == null) {
				bf.append(getCreateStatementByTableJson(newTable, jsonRelationshipList));
			} else {
				wrkStr = getAlterStatementsByTableJson(newTable, oldTableElement, jsonRelationshipList);
				if (!wrkStr.equals("")) {
					bf.append(wrkStr + "\n");
				}
			}
		}

		///////////////////////////
		// Setup Drop statements //
		///////////////////////////
		for (int i = 0; i < oldTableElementList.getLength(); i++) {

			jProgressBar.setValue(jProgressBar.getValue() + 1);
			jProgressBar.paintImmediately(0, 0, jProgressBar.getWidth(), jProgressBar.getHeight());

			oldTableElement = (org.w3c.dom.Element)oldTableElementList.item(i);
			newTable = null;
			for (int j = 0; j < newTableList.size(); j++) {
				workElement = (org.w3c.dom.Element)oldTableElementList.item(j);
				if (frame_.useTableNameAsTableNameInCreateTableStatement) {
					tableId = oldTableElement.getAttribute("Name");
					if (newTableList.get(j).getName().equals(tableId)) {
						newTable = newTableList.get(j);
						break;
					}
				} else {
					String oldAlias = oldTableElement.getAttribute("Alias");
					String oldSortKey = oldTableElement.getAttribute("SortKey");
					String newAlias = newTableList.get(j).getAlias() == null ? "" : newTableList.get(j).getAlias();
					String newSortKey = newTableList.get(j).getSortKey() == null ? "" : newTableList.get(j).getSortKey();
					if (oldAlias.equals("")) {
						if (newAlias.equals("")) {
							if (oldSortKey.equals(newSortKey)) {
								newTable = newTableList.get(j);
								break;
							}
						} else {
							if (oldSortKey.equals(newAlias)) {
								newTable = newTableList.get(j);
								break;
							}
						}
					} else {
						if (newAlias.equals("")) {
							if (oldAlias.equals(newSortKey)) {
								newTable = newTableList.get(j);
								break;
							}
						} else {
							if (oldAlias.equals(newAlias)) {
								newTable = newTableList.get(j);
								break;
							}
						}
					}
				}
			}
			if (newTable == null) {
				for (int j = 0; j < oldSubsystemList.getLength(); j++) {
					workElement = (org.w3c.dom.Element)oldSubsystemList.item(j);
					if (oldTableElement.getAttribute("SubsystemID").equals(workElement.getAttribute("ID"))) {
						String tableName = getTableNameForDDL(oldTableElement);
						bf.append(frame_.ddlCommentMark + workElement.getAttribute("Name")
								+ "(" + workElement.getAttribute("SortKey") + ")"
								+ " - " + oldTableElement.getAttribute("Name")
								+ (oldTableElement.getAttribute("Alias").equals("") ? "" : "(" + oldTableElement.getAttribute("Alias") + ")")
								+ "\nDROP TABLE " + tableName + frame_.ddlSectionMark + "\n\n");
						break;
					}
				}
			}
		}
		return bf.toString();
	}

	// Find the corresponding old table element for a new JSON table
	private org.w3c.dom.Element findOldTableElement(Table newTable, NodeList oldTableElementList) {
		for (int k = 0; k < oldTableElementList.getLength(); k++) {
			org.w3c.dom.Element workElement = (org.w3c.dom.Element)oldTableElementList.item(k);
			if (frame_.useTableNameAsTableNameInCreateTableStatement) {
				if (workElement.getAttribute("Name").equals(newTable.getName())) {
					return workElement;
				}
			} else {
				String oldAlias = workElement.getAttribute("Alias");
				String oldSortKey = workElement.getAttribute("SortKey");
				String newAlias = newTable.getAlias() == null ? "" : newTable.getAlias();
				String newSortKey = newTable.getSortKey() == null ? "" : newTable.getSortKey();
				if (oldAlias.equals("")) {
					if (newAlias.equals("")) {
						if (oldSortKey.equals(newSortKey)) {
							return workElement;
						}
					} else {
						if (oldSortKey.equals(newAlias)) {
							return workElement;
						}
					}
				} else {
					if (newAlias.equals("")) {
						if (oldAlias.equals(newSortKey)) {
							return workElement;
						}
					} else {
						if (oldAlias.equals(newAlias)) {
							return workElement;
						}
					}
				}
			}
		}
		return null;
	}

	// Get table name for DDL statements
	private String getTableNameForDDL(org.w3c.dom.Element tableElement) {
		if (frame_.useTableNameAsTableNameInCreateTableStatement) {
			return tableElement.getAttribute("Name");
		} else {
			if (tableElement.getAttribute("Alias").equals("")) {
				if (tableElement.getAttribute("SortKey").equals("")) {
					return tableElement.getAttribute("Name");
				} else {
					return tableElement.getAttribute("SortKey");
				}
			} else {
				return tableElement.getAttribute("Alias");
			}
		}
	}

	// Get table name for DDL from JSON Table
	private String getTableNameForDDL(Table table) {
		if (frame_.useTableNameAsTableNameInCreateTableStatement) {
			return table.getName();
		} else {
			String alias = table.getAlias() == null ? "" : table.getAlias();
			String sortKey = table.getSortKey() == null ? "" : table.getSortKey();
			if (alias.equals("")) {
				if (sortKey.equals("")) {
					return table.getName();
				} else {
					return sortKey;
				}
			} else {
				return alias;
			}
		}
	}

	// Generate CREATE TABLE statement from JSON Table
	private String getCreateStatementByTableJson(Table table, List<Relationship> relationshipList) {
		StringBuffer statement = new StringBuffer();
		String tableName = getTableNameForDDL(table);
		String subsystemName = "";
		String subsystemSortKey = "";

		// Find subsystem name
		if (table.getSubsystemId() != null) {
			for (Subsystem s : xeadSystemNew.getSubsystems()) {
				if (s.getId().equals(table.getSubsystemId())) {
					subsystemName = s.getName();
					subsystemSortKey = s.getSortKey() == null ? "" : s.getSortKey();
					break;
				}
			}
		}

		// Header comment
		statement.append(frame_.ddlCommentMark).append(subsystemName).append("(").append(subsystemSortKey).append(") - ")
				.append(table.getName());
		if (!table.getAlias().equals("")) {
			statement.append("(").append(table.getAlias()).append(")");
		} else if (!table.getSortKey().equals("")) {
			statement.append("(").append(table.getSortKey()).append(")");
		}
		statement.append("\n");

		// CREATE TABLE statement
		statement.append("CREATE TABLE ").append(tableName).append(" (\n");

		// Fields
		List<TableField> fieldList = table.getTableFields();
		if (fieldList != null) {
			for (int i = 0; i < fieldList.size(); i++) {
				TableField field = fieldList.get(i);
				String fieldName = field.getAlias() == null || field.getAlias().equals("") ? field.getName() : field.getAlias();
				statement.append("\t").append(fieldName);

				// Data type
				DataType dt = newDataTypeMap.get(field.getDataTypeId());
				if (dt != null && dt.getSqlExpression() != null && !dt.getSqlExpression().equals("")) {
					statement.append(" ").append(dt.getSqlExpression());
				} else {
					statement.append(" Invalid-data-type");
				}

				// NOT NULL
				if ("true".equals(field.getNotNull())) {
					statement.append(" NOT NULL");
				}

				// DEFAULT
				if (field.getDefault() != null && !field.getDefault().equals("")) {
					statement.append(" DEFAULT ").append(field.getDefault());
				}

				// Comment
				if (field.getAlias() != null && !field.getAlias().equals("") && !field.getAlias().equals(field.getName())
						&& frame_.ddlFieldCommentType.equals("1")) {
					statement.append(" COMMENT '").append(field.getName()).append("'");
				}

				if (i < fieldList.size() - 1) {
					statement.append(",");
				}
				statement.append("\n");
			}
		}

		// Primary Key
		List<TableKey> keyList = table.getTableKeys();
		if (keyList != null) {
			for (TableKey key : keyList) {
				if ("PK".equals(key.getType())) {
					String keyFields = getKeyFieldIDsOfJson(table, key);
					if (!keyFields.equals("")) {
						statement.append(",\n\tPRIMARY KEY (").append(keyFields).append(")");
					}
				}
			}
		}

		statement.append(")").append(frame_.ddlSectionMark).append("\n");

		// Unique constraints
		if (keyList != null) {
			int skIndex = 0;
			for (TableKey key : keyList) {
				if ("SK".equals(key.getType())) {
					skIndex++;
					String keyFields = getKeyFieldIDsOfJson(table, key);
					if (!keyFields.equals("")) {
						statement.append("ALTER TABLE ").append(tableName)
								.append(" ADD CONSTRAINT ").append(tableName).append("_SK").append(skIndex)
								.append(" UNIQUE (").append(keyFields).append(")")
								.append(frame_.ddlSectionMark).append("\n");
					}
				}
			}
		}

		// Index constraints
		if (keyList != null) {
			for (TableKey key : keyList) {
				if ("XK".equals(key.getType())) {
					String keyFields = getKeyFieldIDsOfJson(table, key);
					if (!keyFields.equals("")) {
						statement.append("ALTER TABLE ").append(tableName)
								.append(" ADD INDEX (").append(keyFields).append(")")
								.append(frame_.ddlSectionMark).append("\n");
					}
				}
			}
		}

		// Foreign Keys
		if (!frame_.ignoreForeignKeyConstraints && relationshipList != null) {
			int fkIndex = 0;
			for (Relationship rel : relationshipList) {
				if (rel.getTable2Id() != null && rel.getTable2Id().equals(table.getId())
						&& (rel.getExistWhen1() == null || rel.getExistWhen1().equals(""))) {
					fkIndex++;
					String keyFields = getKeyFieldIDsOfJsonForTable(rel.getTable2Id(), rel.getTableKey2Id(), xeadSystemNew);
					String references = getRefferencesOfJson(rel, xeadSystemNew);
					if (!keyFields.equals("") && !references.equals("")) {
						statement.append("ALTER TABLE ").append(tableName)
								.append(" ADD CONSTRAINT ").append(tableName).append("_FK").append(fkIndex)
								.append(" FOREIGN KEY (").append(keyFields).append(") REFERENCES ")
								.append(references).append(frame_.ddlSectionMark).append("\n");
					}
				}
			}
		}

		return statement.toString();
	}

	// Generate ALTER TABLE statements from JSON Table vs old XML element
	private String getAlterStatementsByTableJson(Table newTable, org.w3c.dom.Element oldTableElement,
			List<Relationship> relationshipList) {
		StringBuffer statement = new StringBuffer();
		String tableName = getTableNameForDDL(newTable);
		String subsystemName = "";
		String subsystemSortKey = "";

		// Find subsystem name
		if (newTable.getSubsystemId() != null) {
			for (Subsystem s : xeadSystemNew.getSubsystems()) {
				if (s.getId().equals(newTable.getSubsystemId())) {
					subsystemName = s.getName();
					subsystemSortKey = s.getSortKey() == null ? "" : s.getSortKey();
					break;
				}
			}
		}

		// Lists for tracking changes
		ArrayList<String> addFieldIDList = new ArrayList<String>();
		ArrayList<String> addFieldTypeList = new ArrayList<String>();
		ArrayList<String> addFieldDefaultList = new ArrayList<String>();
		ArrayList<Boolean> addFieldNotNullList = new ArrayList<Boolean>();
		ArrayList<String> addFieldCommentList = new ArrayList<String>();
		ArrayList<String> modifyFieldIDList = new ArrayList<String>();
		ArrayList<String> modifyFieldTypeList = new ArrayList<String>();
		ArrayList<String> modifyFieldDefaultList = new ArrayList<String>();
		ArrayList<Boolean> modifyFieldNotNullList = new ArrayList<Boolean>();
		ArrayList<String> dropFieldIDList = new ArrayList<String>();

		// Key variants
		boolean isToDropOldPrimaryKey = false;
		String addPrimaryKeyFieldIDs = "";
		ArrayList<String> dropUniqueKeyList = new ArrayList<String>();
		ArrayList<String> addUniqueKeyList = new ArrayList<String>();
		ArrayList<String> addUniqueKeyConstraintIDList = new ArrayList<String>();
		ArrayList<String> dropIndexKeyList = new ArrayList<String>();
		ArrayList<String> addIndexKeyList = new ArrayList<String>();

		// Foreign key variants
		ArrayList<String> addForeignKeyConstraintIDList = new ArrayList<String>();
		ArrayList<String> addForeignKeyFieldIDsList = new ArrayList<String>();
		ArrayList<String> addForeignKeyRefferencesList = new ArrayList<String>();
		ArrayList<String> dropForeignKeyFieldIDsList = new ArrayList<String>();
		ArrayList<String> dropForeignKeyRefferencesList = new ArrayList<String>();

		// Old elements
		NodeList oldFieldList = oldTableElement.getElementsByTagName("TableField");
		NodeList oldKeyList = oldTableElement.getElementsByTagName("TableKey");
		NodeList oldRelationshipList = systemElementOld.getElementsByTagName("Relationship");

		/////////////////////////////////////////
		// Scan fields to be added or modified //
		/////////////////////////////////////////
		List<TableField> newFieldList = newTable.getTableFields();
		if (newFieldList != null) {
			for (TableField newField : newFieldList) {
				String fieldName = newField.getAlias() == null || newField.getAlias().equals("") 
						? newField.getName() : newField.getAlias();
				String fieldComment = newField.getAlias() == null || newField.getAlias().equals("") 
						? "" : newField.getName();

				// Get data type
				String dataType = "Invalid-data-type";
				DataType dt = newDataTypeMap.get(newField.getDataTypeId());
				if (dt != null && dt.getSqlExpression() != null && !dt.getSqlExpression().equals("")) {
					dataType = dt.getSqlExpression();
				}

				String defaultValue = newField.getDefault() == null ? "" : newField.getDefault();
				boolean isNotNull = "true".equals(newField.getNotNull());

				// Find corresponding old field
				org.w3c.dom.Element correspondingFieldElement = null;
				for (int j = 0; j < oldFieldList.getLength(); j++) {
					org.w3c.dom.Element workElement = (org.w3c.dom.Element)oldFieldList.item(j);
					String oldFieldName = workElement.getAttribute("Alias").equals("") 
							? workElement.getAttribute("Name") : workElement.getAttribute("Alias");
					if (oldFieldName.equals(fieldName)) {
						correspondingFieldElement = workElement;
						break;
					}
				}

				if (correspondingFieldElement == null) {
					// Field to be added
					addFieldIDList.add(fieldName);
					addFieldTypeList.add(dataType);
					addFieldDefaultList.add(defaultValue);
					addFieldNotNullList.add(isNotNull);
					addFieldCommentList.add(fieldComment);
				} else {
					// Check if field is modified
					String dataTypeOld = "Invalid-data-type";
					org.w3c.dom.Element oldDt = oldDataTypeElementMap.get(correspondingFieldElement.getAttribute("DataTypeID"));
					if (oldDt != null && oldDt.getAttribute("SQLExpression") != null && !oldDt.getAttribute("SQLExpression").equals("")) {
						dataTypeOld = oldDt.getAttribute("SQLExpression");
					}
					String defaultValueOld = correspondingFieldElement.getAttribute("Default");
					boolean isNotNullOld = "true".equals(correspondingFieldElement.getAttribute("NotNull"));

					if (!dataType.equals(dataTypeOld) || !defaultValue.equals(defaultValueOld) || isNotNull != isNotNullOld) {
						modifyFieldIDList.add(fieldName);
						modifyFieldTypeList.add(dataType.equals(dataTypeOld) ? null : dataType);
						modifyFieldDefaultList.add(defaultValue.equals(defaultValueOld) ? null : defaultValue);
						modifyFieldNotNullList.add(isNotNull == isNotNullOld ? null : isNotNull);
					}
				}
			}
		}

		///////////////////////////////
		// Scan fields to be dropped //
		///////////////////////////////
		for (int i = 0; i < oldFieldList.getLength(); i++) {
			org.w3c.dom.Element fieldElement = (org.w3c.dom.Element)oldFieldList.item(i);
			String fieldName = fieldElement.getAttribute("Alias").equals("") 
					? fieldElement.getAttribute("Name") : fieldElement.getAttribute("Alias");
			boolean found = false;
			if (newFieldList != null) {
				for (TableField newField : newFieldList) {
					String newFieldName = newField.getAlias() == null || newField.getAlias().equals("") 
							? newField.getName() : newField.getAlias();
					if (newFieldName.equals(fieldName)) {
						found = true;
						break;
					}
				}
			}
			if (!found) {
				dropFieldIDList.add(fieldName);
			}
		}

		///////////////////////////
		// Scan keys to be added //
		///////////////////////////
		int indexOfSK = 0;
		List<TableKey> newKeyList = newTable.getTableKeys();
		if (newKeyList != null) {
			for (TableKey newKey : newKeyList) {
				if ("SK".equals(newKey.getType())) {
					indexOfSK++;
				}
				if ("PK".equals(newKey.getType()) || "SK".equals(newKey.getType()) || "XK".equals(newKey.getType())) {
					String keyFields = getKeyFieldIDsOfJson(newTable, newKey);
					boolean isNotFound = true;
					for (int j = 0; j < oldKeyList.getLength(); j++) {
						org.w3c.dom.Element workElement = (org.w3c.dom.Element)oldKeyList.item(j);
						if (workElement.getAttribute("Type").equals(newKey.getType())) {
							String keyFieldsOld = getKeyFieldIDsOfOldTable(oldTableElement, workElement);
							if (keyFieldsOld.equals(keyFields)) {
								isNotFound = false;
								break;
							}
						}
					}
					if (isNotFound) {
						if ("PK".equals(newKey.getType())) {
							// Check if old has primary key
							for (int j = 0; j < oldKeyList.getLength(); j++) {
								org.w3c.dom.Element workElement = (org.w3c.dom.Element)oldKeyList.item(j);
								if ("PK".equals(workElement.getAttribute("Type"))) {
									isToDropOldPrimaryKey = true;
									break;
								}
							}
							addPrimaryKeyFieldIDs = keyFields;
						} else if ("SK".equals(newKey.getType())) {
							addUniqueKeyList.add(keyFields);
							addUniqueKeyConstraintIDList.add(Integer.toString(indexOfSK));
						} else if ("XK".equals(newKey.getType())) {
							addIndexKeyList.add(keyFields);
						}
					}
				}
			}
		}

		/////////////////////////////
		// Scan keys to be dropped //
		/////////////////////////////
		for (int i = 0; i < oldKeyList.getLength(); i++) {
			org.w3c.dom.Element workElement = (org.w3c.dom.Element)oldKeyList.item(i);
			if ("PK".equals(workElement.getAttribute("Type")) || "SK".equals(workElement.getAttribute("Type"))
					|| "XK".equals(workElement.getAttribute("Type"))) {
				String keyFieldsOld = getKeyFieldIDsOfOldTable(oldTableElement, workElement);
				boolean isNotFound = true;
				if (newKeyList != null) {
					for (TableKey newKey : newKeyList) {
						if (workElement.getAttribute("Type").equals(newKey.getType())) {
							String keyFields = getKeyFieldIDsOfJson(newTable, newKey);
							if (keyFields.equals(keyFieldsOld)) {
								isNotFound = false;
								break;
							}
						}
					}
				}
				if (isNotFound && !keyFieldsOld.equals("")) {
					if ("PK".equals(workElement.getAttribute("Type")) || "SK".equals(workElement.getAttribute("Type"))) {
						dropUniqueKeyList.add(keyFieldsOld);
						containsValueToBeCorrected = true;
					}
					if ("XK".equals(workElement.getAttribute("Type"))) {
						dropIndexKeyList.add(keyFieldsOld);
					}
				}
			}
		}

		//////////////////////////////////////////////
		// Scan foreign-key constraints to be added //
		//////////////////////////////////////////////
		if (!frame_.ignoreForeignKeyConstraints && relationshipList != null && newKeyList != null) {
			int indexOfFK = 0;
			for (TableKey newKey : newKeyList) {
				if ("PK".equals(newKey.getType()) || "SK".equals(newKey.getType()) || "FK".equals(newKey.getType())) {
					indexOfFK++;
					for (Relationship rel : relationshipList) {
						if (rel.getTable2Id() != null && rel.getTable2Id().equals(newTable.getId())
								&& rel.getTableKey2Id() != null && rel.getTableKey2Id().equals(newKey.getId())
								&& (rel.getExistWhen1() == null || rel.getExistWhen1().equals(""))) {
							if (isLackingForeignKeyConstraintOfJson(rel, newTable, xeadSystemNew, oldTableElement, systemElementOld)) {
								addForeignKeyConstraintIDList.add(Integer.toString(indexOfFK));
								addForeignKeyFieldIDsList.add(getKeyFieldIDsOfJson(newTable, newKey));
								addForeignKeyRefferencesList.add(getRefferencesOfJson(rel, xeadSystemNew));
							}
							break;
						}
					}
				}
			}
		}

		////////////////////////////////////////////////
		// Scan foreign-key constraints to be dropped //
		////////////////////////////////////////////////
		if (!frame_.ignoreForeignKeyConstraints) {
			for (int i = 0; i < oldKeyList.getLength(); i++) {
				org.w3c.dom.Element keyElement = (org.w3c.dom.Element)oldKeyList.item(i);
				if ("PK".equals(keyElement.getAttribute("Type")) || "SK".equals(keyElement.getAttribute("Type"))
						|| "FK".equals(keyElement.getAttribute("Type"))) {
					for (int j = 0; j < oldRelationshipList.getLength(); j++) {
						org.w3c.dom.Element workElement = (org.w3c.dom.Element)oldRelationshipList.item(j);
						if (workElement.getAttribute("Table2ID").equals(oldTableElement.getAttribute("ID"))
								&& workElement.getAttribute("TableKey2ID").equals(keyElement.getAttribute("ID"))
								&& workElement.getAttribute("ExistWhen1").equals("")) {
							// Check if this relationship exists in the new model
							boolean foundInNew = false;
							if (relationshipList != null) {
								for (Relationship rel : relationshipList) {
									if (rel.getTable1Id() != null && rel.getTable1Id().equals(workElement.getAttribute("Table1ID"))
											&& rel.getTableKey1Id() != null && rel.getTableKey1Id().equals(workElement.getAttribute("TableKey1ID"))
											&& rel.getTable2Id() != null && rel.getTable2Id().equals(workElement.getAttribute("Table2ID"))
											&& rel.getTableKey2Id() != null && rel.getTableKey2Id().equals(workElement.getAttribute("TableKey2ID"))) {
										foundInNew = true;
										break;
									}
								}
							}
							if (!foundInNew) {
								dropForeignKeyFieldIDsList.add(getKeyFieldIDsOfOldTable(oldTableElement, keyElement));
								dropForeignKeyRefferencesList.add(getRefferencesOfOldRelationship(workElement, systemElementOld));
								containsValueToBeCorrected = true;
							}
							break;
						}
					}
				}
			}
		}

		////////////////////////////////////////
		// Generate ALTER-DROP-KEY statements //
		////////////////////////////////////////
		for (int i = 0; i < dropUniqueKeyList.size(); i++) {
			statement.append(frame_.ddlCommentMark).append("Following is the statement to drop unique-constraint with keys(")
					.append(dropUniqueKeyList.get(i)).append("). Check value of constraint id and correct '???' before executing.\n");
			statement.append("ALTER TABLE ").append(tableName).append(" DROP CONSTRAINT ???")
					.append(frame_.ddlSectionMark).append("\n");
		}
		for (int i = 0; i < dropIndexKeyList.size(); i++) {
			statement.append("ALTER TABLE ").append(tableName).append(" DROP INDEX (")
					.append(dropIndexKeyList.get(i)).append(")").append(frame_.ddlSectionMark).append("\n");
		}

		////////////////////////////////////////////////
		// Generate ALTER-DROP-FOREIGN-KEY statements //
		////////////////////////////////////////////////
		for (int i = 0; i < dropForeignKeyFieldIDsList.size(); i++) {
			statement.append(frame_.ddlCommentMark).append("Following is the statement to drop refference-constraint with keys(")
					.append(dropForeignKeyFieldIDsList.get(i)).append(") to ")
					.append(dropForeignKeyRefferencesList.get(i))
					.append(". Check value of constraint id and correct '???' before executing.\n");
			statement.append("ALTER TABLE ").append(tableName).append(" DROP CONSTRAINT ???")
					.append(frame_.ddlSectionMark).append("\n");
		}

		////////////////////////////////////
		// Generate DROP-COLUMN statement //
		////////////////////////////////////
		boolean firstFieldStatement = true;
		for (int i = 0; i < dropFieldIDList.size(); i++) {
			if (firstFieldStatement) {
				firstFieldStatement = false;
				statement.append("ALTER TABLE ").append(tableName).append(" DROP (\n");
			} else {
				statement.append(",\n");
			}
			statement.append("\t").append(dropFieldIDList.get(i));
		}
		if (dropFieldIDList.size() > 0) {
			statement.append(")").append(frame_.ddlSectionMark).append("\n");
		}

		///////////////////////////////////
		// Generate ADD-COLUMN statement //
		///////////////////////////////////
		firstFieldStatement = true;
		for (int i = 0; i < addFieldIDList.size(); i++) {
			if (firstFieldStatement) {
				firstFieldStatement = false;
				statement.append("ALTER TABLE ").append(tableName).append(" ADD (\n");
			} else {
				statement.append(",\n");
			}
			statement.append("\t").append(addFieldIDList.get(i));
			statement.append(" ").append(addFieldTypeList.get(i));
			if (addFieldNotNullList.get(i)) {
				statement.append(" NOT NULL");
			}
			statement.append(" DEFAULT ").append(addFieldDefaultList.get(i));
			if (!addFieldCommentList.get(i).equals("") && frame_.ddlFieldCommentType.equals("1")) {
				statement.append(" COMMENT '").append(addFieldCommentList.get(i)).append("'");
			}
		}
		if (addFieldIDList.size() > 0) {
			statement.append(")").append(frame_.ddlSectionMark).append("\n");
		}

		//////////////////////////////////////
		// Generate MODIFY-COLUMN statement //
		//////////////////////////////////////
		firstFieldStatement = true;
		for (int i = 0; i < modifyFieldIDList.size(); i++) {
			if (firstFieldStatement) {
				firstFieldStatement = false;
				statement.append("ALTER TABLE ").append(tableName).append(" MODIFY (\n");
			} else {
				statement.append(",\n");
			}
			statement.append("\t").append(modifyFieldIDList.get(i));
			if (modifyFieldTypeList.get(i) != null) {
				statement.append(" ").append(modifyFieldTypeList.get(i));
			}
			if (modifyFieldNotNullList.get(i) != null) {
				if (modifyFieldNotNullList.get(i)) {
					statement.append(" NOT NULL");
				} else {
					statement.append(" NULL");
				}
			}
			if (modifyFieldDefaultList.get(i) != null) {
				statement.append(" DEFAULT ").append(modifyFieldDefaultList.get(i));
			}
		}
		if (modifyFieldIDList.size() > 0) {
			statement.append(")").append(frame_.ddlSectionMark).append("\n");
		}

		///////////////////////////////////////
		// Generate ALTER-ADD-KEY statements //
		///////////////////////////////////////
		if (!addPrimaryKeyFieldIDs.equals("")) {
			if (isToDropOldPrimaryKey) {
				statement.append("ALTER TABLE ").append(tableName).append(" DROP CONSTRAINT ")
						.append(tableName).append("_PK PRIMARY KEY").append(frame_.ddlSectionMark).append("\n");
			}
			statement.append("ALTER TABLE ").append(tableName).append(" ADD CONSTRAINT ")
					.append(tableName).append("_PK PRIMARY KEY (").append(addPrimaryKeyFieldIDs).append(")")
					.append(frame_.ddlSectionMark).append("\n");
		}
		for (int i = 0; i < addUniqueKeyList.size(); i++) {
			statement.append("ALTER TABLE ").append(tableName).append(" ADD CONSTRAINT ")
					.append(tableName).append("_SK").append(addUniqueKeyConstraintIDList.get(i))
					.append(" UNIQUE (").append(addUniqueKeyList.get(i)).append(")")
					.append(frame_.ddlSectionMark).append("\n");
		}
		for (int i = 0; i < addIndexKeyList.size(); i++) {
			statement.append("ALTER TABLE ").append(tableName).append(" ADD INDEX (")
					.append(addIndexKeyList.get(i)).append(")").append(frame_.ddlSectionMark).append("\n");
		}

		///////////////////////////////////////////////
		// Generate ALTER-ADD-FOREIGN-KEY statements //
		///////////////////////////////////////////////
		for (int i = 0; i < addForeignKeyConstraintIDList.size(); i++) {
			statement.append("ALTER TABLE ").append(tableName).append(" ADD CONSTRAINT ")
					.append(tableName).append("_FK").append(addForeignKeyConstraintIDList.get(i))
					.append(" FOREIGN KEY (").append(addForeignKeyFieldIDsList.get(i)).append(") REFFERENCES ")
					.append(addForeignKeyRefferencesList.get(i)).append(frame_.ddlSectionMark).append("\n");
		}

		/////////////////////
		// Header Comments //
		/////////////////////
		if (statement.length() > 0) {
			StringBuffer header = new StringBuffer();
			header.append(frame_.ddlCommentMark).append(subsystemName).append("(").append(subsystemSortKey)
					.append(") - ").append(newTable.getName());
			if (newTable.getAlias() != null && !newTable.getAlias().equals("")) {
				header.append("(").append(newTable.getAlias()).append(")");
			} else if (newTable.getSortKey() != null && !newTable.getSortKey().equals("")) {
				header.append("(").append(newTable.getSortKey()).append(")");
			}
			header.append("\n");
			header.append(statement);
			statement = header;
		}

		return statement.toString();
	}

	// Get key field IDs from old XML table element
	private String getKeyFieldIDsOfOldTable(org.w3c.dom.Element tableElement, org.w3c.dom.Element keyElement) {
		String keyFieldIDs = "";
		NodeList keyFieldList = keyElement.getElementsByTagName("TableKeyField");
		frame_.sortableDomElementListModel.removeAllElements();
		for (int m = 0; m < keyFieldList.getLength(); m++) {
			frame_.sortableDomElementListModel.addElementFromPojo(keyFieldList.item(m));
		}
		if (frame_.sortableDomElementListModel.getSize() > 0) {
			frame_.sortableDomElementListModel.sortElements();
			for (int m = 0; m < frame_.sortableDomElementListModel.getSize(); m++) {
				xeadModeler.model.XeadElement xeadElement = frame_.sortableDomElementListModel.getElementAt(m);
				org.w3c.dom.Element keyFieldElement = (org.w3c.dom.Element)xeadElement.getPojo();
				org.w3c.dom.Element fieldElement = getFieldElementAccordingToID(tableElement, keyFieldElement.getAttribute("FieldID"));
				if (m > 0) {
					keyFieldIDs = keyFieldIDs + ", ";
				}
				if (fieldElement.getAttribute("Alias").equals("")) {
					keyFieldIDs = keyFieldIDs + fieldElement.getAttribute("Name");
				} else {
					keyFieldIDs = keyFieldIDs + fieldElement.getAttribute("Alias");
				}
			}
		}
		return keyFieldIDs;
	}

	// Find field element by ID in old XML table element
	private org.w3c.dom.Element getFieldElementAccordingToID(org.w3c.dom.Element tableElement, String fieldId) {
		NodeList fieldList = tableElement.getElementsByTagName("TableField");
		for (int i = 0; i < fieldList.getLength(); i++) {
			org.w3c.dom.Element fieldElement = (org.w3c.dom.Element)fieldList.item(i);
			if (fieldElement.getAttribute("ID").equals(fieldId)) {
				return fieldElement;
			}
		}
		return null;
	}

	// Get references from old XML relationship element
	private String getRefferencesOfOldRelationship(org.w3c.dom.Element relationshipElement, org.w3c.dom.Element systemElement) {
		String tableId = relationshipElement.getAttribute("Table1ID");
		String tableKeyId = relationshipElement.getAttribute("TableKey1ID");
		org.w3c.dom.Element tableElement = null;
		org.w3c.dom.Element tableKeyElement = null;

		NodeList tableElementList = systemElement.getElementsByTagName("Table");
		for (int i = 0; i < tableElementList.getLength(); i++) {
			org.w3c.dom.Element workElement = (org.w3c.dom.Element)tableElementList.item(i);
			if (workElement.getAttribute("ID").equals(tableId)) {
				tableElement = workElement;
				break;
			}
		}
		if (tableElement != null) {
			String tableName = getTableNameForDDL(tableElement);
			NodeList tableKeyElementList = tableElement.getElementsByTagName("TableKey");
			for (int i = 0; i < tableKeyElementList.getLength(); i++) {
				org.w3c.dom.Element workElement = (org.w3c.dom.Element)tableKeyElementList.item(i);
				if (workElement.getAttribute("ID").equals(tableKeyId)) {
					tableKeyElement = workElement;
					break;
				}
			}
			if (tableKeyElement != null) {
				String fieldNames = getKeyFieldIDsOfOldTable(tableElement, tableKeyElement);
				return tableName + "(" + fieldNames + ")";
			}
		}
		return "";
	}

	void jButtonCancel_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}

class DialogToListChangesOfFiles_jButtonStart_actionAdapter implements java.awt.event.ActionListener {
	DialogToListChangesOfFiles adaptee;

	DialogToListChangesOfFiles_jButtonStart_actionAdapter(DialogToListChangesOfFiles adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.jButtonStart_actionPerformed(e);
	}
}

class DialogToListChangesOfFiles_jButtonCancel_actionAdapter implements java.awt.event.ActionListener {
	DialogToListChangesOfFiles adaptee;

	DialogToListChangesOfFiles_jButtonCancel_actionAdapter(DialogToListChangesOfFiles adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.jButtonCancel_actionPerformed(e);
	}
}