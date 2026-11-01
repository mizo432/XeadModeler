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
			// TODO: listChangesOfFunction requires further migration
			// listChangesOfFunction();

			////////////////////////////////////
			// Generate text file of Diff-DDL //
			////////////////////////////////////
			// TODO: DDL generation requires further migration of helper methods
			// if (jCheckBoxListDiffDDL.isSelected()) {
			// 	FileWriter fileWriter = null;
			// 	NodeList oldTableElementList = systemElementOld.getElementsByTagName("Table");
			// 	List<Table> newTableList = xeadSystemNew.getTables();
			// 	BufferedWriter bufferedWriter = null;
			// 	try {
			// 		String diffDDL = getDiffDDL(newTableList, oldTableElementList);
			// 		if (!diffDDL.equals("")) {
			// 			File textFile = File.createTempFile("AlterTables" + frame_.getStringValueOfDateTime("withTime"), ".txt");
			// 			String textFileName = textFile.getPath();
			// 			fileWriter = new FileWriter(textFileName);
			// 			bufferedWriter = new BufferedWriter(fileWriter);
			// 			if (containsValueToBeCorrected) {
			// 				diffDDL = frame_.ddlCommentMark + "Warning: It contains constraint id(s) indicated as '???' that should be checked and corrected before executing.\n\n" + diffDDL;
			// 			}
			// 			bufferedWriter.write(diffDDL);
			// 			bufferedWriter.flush();
			// 			frame_.desktop.open(textFile);
			// 		}
			// 	} catch (Exception ex1) {
			// 		ex1.printStackTrace();
			// 	} finally {
			// 		try {
			// 			bufferedWriter.close();
			// 		} catch (Exception ex2) {}
			// 	}
			// }

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
			if (attr.equals("FunctionTypeID")) return f.getFunctionTypeId() == null ? "" : f.getFunctionTypeId();
			if (attr.equals("SubsystemID")) return f.getSubsystemId() == null ? "" : f.getSubsystemId();
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