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
import javax.swing.event.*;
import javax.swing.table.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import java.awt.event.*;
import java.util.ResourceBundle;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFFooter;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import java.io.*;
import java.net.URI;
import java.util.*;

import xeadModeler.Modeler;

public class DialogScan extends JDialog {
	private static final long serialVersionUID = 1L;
	private static ResourceBundle res = ResourceBundle.getBundle("xeadModeler.Res");
	private Modeler frame_;
	private BorderLayout borderLayoutMain = new BorderLayout();
	private JPanel panelMain = new JPanel();
	private JPanel jPanelNorth = new JPanel();
	private JPanel jPanelSouth = new JPanel();
	private JSplitPane jSplitPane = new JSplitPane();
	private JScrollPane jScrollPaneScanResult = new JScrollPane();
	private JScrollPane jScrollPaneScanDetail = new JScrollPane();
	private JLabel jLabel1 = new JLabel();
	private JTextField jTextFieldScan = new JTextField();
	private JLabel jLabel2 = new JLabel();
	private JTextField jTextFieldReplace = new JTextField();
	private JCheckBox jCheckBoxCaseSensitive = new JCheckBox();
	private JLabel jLabel3 = new JLabel();
	private JCheckBox jCheckBoxSystem = new JCheckBox();
	private JCheckBox jCheckBoxDepartment = new JCheckBox();
	private JCheckBox jCheckBoxTaskType = new JCheckBox();
	private JCheckBox jCheckBoxTableType = new JCheckBox();
	private JCheckBox jCheckBoxDataType = new JCheckBox();
	private JCheckBox jCheckBoxFunctionType = new JCheckBox();
	private JCheckBox jCheckBoxTerms = new JCheckBox();
	private JCheckBox jCheckBoxMaintenanceLog = new JCheckBox();
	private JCheckBox jCheckBoxSubjectArea = new JCheckBox();
	private JCheckBox jCheckBoxRole = new JCheckBox();
	private JCheckBox jCheckBoxTask = new JCheckBox();
	private JCheckBox jCheckBoxSubsystem = new JCheckBox();
	private JCheckBox jCheckBoxTable = new JCheckBox();
	private JCheckBox jCheckBoxFunction = new JCheckBox();
	private JCheckBox jCheckBoxAll = new JCheckBox();
	private SortableXeadNodeComboBoxModel comboBoxModelRoles = new SortableXeadNodeComboBoxModel();
	private JComboBox jComboBoxRoles = new JComboBox(comboBoxModelRoles);
	private SortableXeadNodeComboBoxModel comboBoxModelSubsystems = new SortableXeadNodeComboBoxModel();
	private JComboBox jComboBoxSubsystems = new JComboBox(comboBoxModelSubsystems);
	private TableModelScanResult tableModelScanResult = new TableModelScanResult();
	private JTable jTableScanResult = new JTable(tableModelScanResult);
	private TableColumn column0, column1, column2, column3, column4, column5, column6;
	private DefaultTableCellRenderer rendererTableHeader = null;
	private CheckBoxHeaderRenderer checkBoxHeaderRenderer = null;
	private DefaultTableCellRenderer rendererAlignmentCenter = new DefaultTableCellRenderer();
	private DefaultTableCellRenderer rendererAlignmentRight = new DefaultTableCellRenderer();
	private DefaultTableCellRenderer rendererAlignmentLeft = new DefaultTableCellRenderer();
	private JTextPane jTextPaneScanDetail = new JTextPane();
	private JButton jButtonStartScan = new JButton();
	private JProgressBar jProgressBar = new JProgressBar();
	private JButton jButtonCloseDialog = new JButton();
	private JButton jButtonReplaceAllSelected = new JButton();
	private JButton jButtonGenerateListData = new JButton();
	private boolean updated = false;
	private int countOfRows = 0;
	private int countOfStrings = 0;
	private int countOfRowsReplaced = 0;
	private int countOfStringsReplaced = 0;
	private String stringToBeScanned = "";
	private String stringToBeReplaced = "";
	private Desktop desktop = Desktop.getDesktop();
	private SimpleAttributeSet attrs = new SimpleAttributeSet();
	private StyledDocument doc = null;
	private Style style = null;

	public DialogScan(Modeler frame, String title, boolean modal) {
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

	public DialogScan(Modeler frame) {
		this(frame, "", true);
	}

	private void jbInit() throws Exception {
		//
		//panelMain
		panelMain.setLayout(borderLayoutMain);
		panelMain.setPreferredSize(new Dimension(1000, 700));
		panelMain.setBorder(BorderFactory.createEtchedBorder());
		panelMain.add(jPanelNorth, BorderLayout.NORTH);
		panelMain.add(jPanelSouth, BorderLayout.SOUTH);
		panelMain.add(jSplitPane, BorderLayout.CENTER);
		//
		//jPanelNorth and objects on it
		jPanelNorth.setBorder(null);
		jPanelNorth.setLayout(null);
		jPanelNorth.setPreferredSize(new Dimension(1000, 170));
		jLabel1.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel1.setText(res.getString("DialogScan02"));
		jLabel1.setBounds(new Rectangle(5, 13, 130, 20));
		jTextFieldScan.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jTextFieldScan.setText("");
		jTextFieldScan.setBounds(new Rectangle(140, 10, 240, 25));
		jTextFieldScan.addKeyListener(new DialogScan_jTextFieldScan_keyAdapter(this));
		jTextFieldScan.addFocusListener(new DialogScan_jTextField_focusListener());
		jLabel2.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel2.setText(res.getString("DialogScan03"));
		jLabel2.setBounds(new Rectangle(390, 13, 130, 20));
		jTextFieldReplace.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jTextFieldReplace.setText("");
		jTextFieldReplace.setBounds(new Rectangle(525, 10, 240, 25));
		jTextFieldReplace.addKeyListener(new DialogScan_jTextFieldReplace_keyAdapter(this));
		jTextFieldReplace.addFocusListener(new DialogScan_jTextField_focusListener());
		jCheckBoxCaseSensitive.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jCheckBoxCaseSensitive.setText(res.getString("DialogScan04"));
		jCheckBoxCaseSensitive.setBounds(new Rectangle(770, 10, 220, 25));
		jLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel3.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jLabel3.setText(res.getString("DialogScan05"));
		jLabel3.setBounds(new Rectangle(5, 44, 130, 20));
		jCheckBoxSystem.setBounds(new Rectangle(140, 41, 150, 25));
		jCheckBoxSystem.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jCheckBoxSystem.setText(res.getString("DialogScan06"));
		jCheckBoxSystem.setSelected(true);
		jCheckBoxDepartment.setBounds(new Rectangle(290, 41, 150, 25));
		jCheckBoxDepartment.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jCheckBoxDepartment.setText(res.getString("DialogScan07"));
		jCheckBoxDepartment.setSelected(true);
		jCheckBoxTaskType.setBounds(new Rectangle(440, 41, 150, 25));
		jCheckBoxTaskType.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jCheckBoxTaskType.setText(res.getString("DialogScan26"));
		jCheckBoxTaskType.setSelected(true);
		jCheckBoxTableType.setBounds(new Rectangle(590, 41, 150, 25));
		jCheckBoxTableType.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jCheckBoxTableType.setText(res.getString("DialogScan08"));
		jCheckBoxTableType.setSelected(true);
		jCheckBoxDataType.setBounds(new Rectangle(140, 72, 150, 25));
		jCheckBoxDataType.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jCheckBoxDataType.setText(res.getString("DialogScan09"));
		jCheckBoxDataType.setSelected(true);
		jCheckBoxFunctionType.setBounds(new Rectangle(290, 72, 150, 25));
		jCheckBoxFunctionType.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jCheckBoxFunctionType.setText(res.getString("DialogScan10"));
		jCheckBoxFunctionType.setSelected(true);
		jCheckBoxTerms.setBounds(new Rectangle(440, 72, 150, 25));
		jCheckBoxTerms.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jCheckBoxTerms.setText(res.getString("DialogScan84"));
		jCheckBoxTerms.setSelected(true);
		jCheckBoxMaintenanceLog.setBounds(new Rectangle(590, 72, 150, 25));
		jCheckBoxMaintenanceLog.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jCheckBoxMaintenanceLog.setText(res.getString("DialogScan11"));
		jCheckBoxMaintenanceLog.setSelected(true);
		jCheckBoxSubjectArea.setBounds(new Rectangle(140, 103, 150, 25));
		jCheckBoxSubjectArea.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jCheckBoxSubjectArea.setText(res.getString("DialogScan12"));
		jCheckBoxSubjectArea.setSelected(true);
		jCheckBoxRole.setBounds(new Rectangle(290, 103, 150, 25));
		jCheckBoxRole.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jCheckBoxRole.setText(res.getString("DialogScan13"));
		jCheckBoxRole.addChangeListener(new DialogScan_jCheckBoxRole_changeAdapter(this));
		jCheckBoxRole.setSelected(true);
		jCheckBoxTask.setBounds(new Rectangle(440, 103, 150, 25));
		jCheckBoxTask.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jCheckBoxTask.setText(res.getString("DialogScan14"));
		jCheckBoxTask.addChangeListener(new DialogScan_jCheckBoxTask_changeAdapter(this));
		jCheckBoxTask.setSelected(true);
		jComboBoxRoles.setBounds(new Rectangle(594, 103, 220, 25));
		jComboBoxRoles.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jCheckBoxSubsystem.setBounds(new Rectangle(140, 134, 150, 25));
		jCheckBoxSubsystem.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jCheckBoxSubsystem.setText(res.getString("DialogScan15"));
		jCheckBoxSubsystem.addChangeListener(new DialogScan_jCheckBoxSubsystem_changeAdapter(this));
		jCheckBoxSubsystem.setSelected(true);
		jCheckBoxTable.setBounds(new Rectangle(290, 134, 150, 25));
		jCheckBoxTable.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jCheckBoxTable.setText(res.getString("DialogScan16"));
		jCheckBoxTable.addChangeListener(new DialogScan_jCheckBoxTable_changeAdapter(this));
		jCheckBoxTable.setSelected(true);
		jCheckBoxFunction.setBounds(new Rectangle(440, 134, 150, 25));
		jCheckBoxFunction.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jCheckBoxFunction.setText(res.getString("DialogScan17"));
		jCheckBoxFunction.addChangeListener(new DialogScan_jCheckBoxFunction_changeAdapter(this));
		jCheckBoxFunction.setSelected(true);
		jComboBoxSubsystems.setBounds(new Rectangle(594, 134, 220, 25));
		jComboBoxSubsystems.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jCheckBoxAll.setBounds(new Rectangle(840, 103, 140, 25));
		jCheckBoxAll.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jCheckBoxAll.setText(res.getString("DialogScan85"));
		jCheckBoxAll.setSelected(true);
		jCheckBoxAll.addActionListener(new DialogScan_jCheckBoxAll_actionAdapter(this));
		jButtonStartScan.setBounds(new Rectangle(840, 130, 140, 32));
		jButtonStartScan.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jButtonStartScan.setText(res.getString("DialogScan18"));
		jButtonStartScan.addActionListener(new DialogScan_jButtonStartScan_actionAdapter(this));
		jButtonStartScan.setEnabled(false);
		jButtonStartScan.setFocusCycleRoot(true);
		jProgressBar.setBounds(new Rectangle(840, 130, 140, 32));
		jProgressBar.setVisible(false);
		jPanelNorth.add(jTextFieldScan, null);
		jPanelNorth.add(jTextFieldReplace, null);
		jPanelNorth.add(jLabel2, null);
		jPanelNorth.add(jCheckBoxCaseSensitive, null);
		jPanelNorth.add(jCheckBoxDepartment, null);
		jPanelNorth.add(jCheckBoxTaskType, null);
		jPanelNorth.add(jCheckBoxTableType, null);
		jPanelNorth.add(jCheckBoxDataType, null);
		jPanelNorth.add(jCheckBoxFunctionType, null);
		jPanelNorth.add(jCheckBoxTerms, null);
		jPanelNorth.add(jCheckBoxMaintenanceLog, null);
		jPanelNorth.add(jCheckBoxRole, null);
		jPanelNorth.add(jCheckBoxTask, null);
		jPanelNorth.add(jCheckBoxSubsystem, null);
		jPanelNorth.add(jCheckBoxFunction, null);
		jPanelNorth.add(jCheckBoxTable, null);
		jPanelNorth.add(jLabel1, null);
		jPanelNorth.add(jCheckBoxSystem, null);
		jPanelNorth.add(jCheckBoxSubjectArea, null);
		jPanelNorth.add(jLabel3, null);
		jPanelNorth.add(jComboBoxSubsystems, null);
		jPanelNorth.add(jComboBoxRoles, null);
		jPanelNorth.add(jCheckBoxAll, null);
		jPanelNorth.add(jButtonStartScan, null);
		jPanelNorth.add(jProgressBar, null);
		//
		//jSplitPane and objects on it
		jSplitPane.setBorder(null);
		jSplitPane.setBounds(new Rectangle(11, 244, 380, 58));
		jSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		jSplitPane.setDividerLocation(300);
		jSplitPane.setPreferredSize(new Dimension(750, 300));
		jScrollPaneScanDetail.setBounds(new Rectangle(132, 144, 282, 51));
		jSplitPane.add(jScrollPaneScanResult, JSplitPane.TOP);
		jSplitPane.add(jScrollPaneScanDetail, JSplitPane.BOTTOM);
		jTableScanResult.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jTableScanResult.setBackground(SystemColor.control);
		jTableScanResult.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		jTableScanResult.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jTableScanResult.setRowSelectionAllowed(true);
		jTableScanResult.getSelectionModel().addListSelectionListener(new DialogScan_jTableScanResult_listSelectionAdapter(this));
		jTableScanResult.setRowHeight(Modeler.TABLE_ROW_HEIGHT);
		jTableScanResult.addMouseListener(new DialogScan_jTableScanResult_mouseAdapter(this));
		jTableScanResult.getTableHeader().setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		tableModelScanResult.addColumn("No.");
		tableModelScanResult.addColumn("");
		tableModelScanResult.addColumn(res.getString("DialogScan20"));
		tableModelScanResult.addColumn(res.getString("DialogScan21"));
		tableModelScanResult.addColumn(res.getString("DialogScan22"));
		tableModelScanResult.addColumn(res.getString("DialogScan23"));
		tableModelScanResult.addColumn(res.getString("DialogScan24"));
		column0 = jTableScanResult.getColumnModel().getColumn(0);
		column1 = jTableScanResult.getColumnModel().getColumn(1);
		column2 = jTableScanResult.getColumnModel().getColumn(2);
		column3 = jTableScanResult.getColumnModel().getColumn(3);
		column4 = jTableScanResult.getColumnModel().getColumn(4);
		column5 = jTableScanResult.getColumnModel().getColumn(5);
		column6 = jTableScanResult.getColumnModel().getColumn(6);
		column0.setPreferredWidth(40);
		column1.setPreferredWidth(30);
		column2.setPreferredWidth(190);
		column3.setPreferredWidth(400);
		column4.setPreferredWidth(125);
		column5.setPreferredWidth(145);
		column6.setPreferredWidth(35);
		rendererAlignmentCenter.setHorizontalAlignment(0); //CENTER//
		rendererAlignmentRight.setHorizontalAlignment(4); //RIGHT//
		rendererAlignmentLeft.setHorizontalAlignment(2); //LEFT//
		column0.setCellRenderer(rendererAlignmentCenter);
		CheckBoxRenderer checkBoxRenderer = new CheckBoxRenderer();
		column1.setCellRenderer(checkBoxRenderer);
		DefaultCellEditor editorWithCheckBox = new DefaultCellEditor(new JCheckBox());
		column1.setCellEditor(editorWithCheckBox);
		checkBoxHeaderRenderer = new CheckBoxHeaderRenderer(new CheckBoxHeaderListener());
		column1.setHeaderRenderer(checkBoxHeaderRenderer);
		column2.setCellRenderer(rendererAlignmentLeft);
		column3.setCellRenderer(rendererAlignmentLeft);
		column4.setCellRenderer(rendererAlignmentLeft);
		column5.setCellRenderer(rendererAlignmentLeft);
		column6.setCellRenderer(rendererAlignmentRight);
		rendererTableHeader = (DefaultTableCellRenderer)jTableScanResult.getTableHeader().getDefaultRenderer();
		rendererTableHeader.setHorizontalAlignment(2); //LEFT//
		jScrollPaneScanResult.getViewport().add(jTableScanResult, null);
		jTextPaneScanDetail.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jTextPaneScanDetail.setEditable(false);
		jTextPaneScanDetail.setBackground(SystemColor.control);
		jScrollPaneScanDetail.getViewport().add(jTextPaneScanDetail, null);
		doc = jTextPaneScanDetail.getStyledDocument();
		style = doc.addStyle("style1",null);
		StyleConstants.setBackground(style, Color.cyan);
		//
		//jPanelSouth and objects on it
		jPanelSouth.setBorder(BorderFactory.createEtchedBorder());
		jPanelSouth.setBorder(null);
		jPanelSouth.setLayout(null);
		jPanelSouth.setPreferredSize(new Dimension(800, 43));
		jButtonCloseDialog.setBounds(new Rectangle(30, 8, 120, 27));
		jButtonCloseDialog.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jButtonCloseDialog.setText(res.getString("DialogScan25"));
		jButtonCloseDialog.addActionListener(new DialogScan_jButtonCloseDialog_actionAdapter(this));
		jButtonReplaceAllSelected.setBounds(new Rectangle(380, 8, 230, 27));
		jButtonReplaceAllSelected.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jButtonReplaceAllSelected.setText(res.getString("DialogScan28"));
		jButtonReplaceAllSelected.addActionListener(new DialogScan_jButtonReplaceAllSelected_actionAdapter(this));
		jButtonGenerateListData.setBounds(new Rectangle(845, 8, 120, 27));
		jButtonGenerateListData.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jButtonGenerateListData.setText(res.getString("DialogScan29"));
		jButtonGenerateListData.setEnabled(false);
		jButtonGenerateListData.addActionListener(new DialogScan_jButtonGenerateListData_actionAdapter(this));
		jPanelSouth.add(jButtonCloseDialog, null);
		jPanelSouth.add(jButtonReplaceAllSelected, null);
		jPanelSouth.add(jButtonGenerateListData, null);
		//
		//DialogScan
		this.setTitle(res.getString("DialogScan01"));
		this.getContentPane().add(panelMain);
		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension dlgSize = this.getPreferredSize();
		this.setLocation((scrSize.width - dlgSize.width)/2 , (scrSize.height - dlgSize.height)/2);
		this.pack();
	}

	public boolean request() {
		XeadNode node;
		java.util.List<?> elementList;
		updated = false;
		//
		comboBoxModelRoles.removeAllElements();
		elementList = frame_.getXeadDocumentModel().getXeadSystem().getRoles();
		for (int i = 0; i < elementList.size(); i++) {
			node = new XeadNode("Role", elementList.get(i));
			comboBoxModelRoles.addElement((Object)node);
		}
		comboBoxModelRoles.sortElements();
		comboBoxModelRoles.insertElementAt(res.getString("DialogScan30"), 0);
		comboBoxModelRoles.setSelectedItem(comboBoxModelRoles.getElementAt(0));
		//
		comboBoxModelSubsystems.removeAllElements();
		elementList = frame_.getXeadDocumentModel().getXeadSystem().getSubsystems();
		for (int i = 0; i < elementList.size(); i++) {
			node = new XeadNode("Subsystem", elementList.get(i));
			comboBoxModelSubsystems.addElement((Object)node);
		}
		comboBoxModelSubsystems.sortElements();
		comboBoxModelSubsystems.insertElementAt(res.getString("DialogScan31"), 0);
		comboBoxModelSubsystems.setSelectedItem(comboBoxModelSubsystems.getElementAt(0));
		//
		if (tableModelScanResult.getRowCount() > 0) {
			int rowCount = tableModelScanResult.getRowCount();
			for (int i = 0; i < rowCount; i++) {tableModelScanResult.removeRow(0);}
		}
		jTextPaneScanDetail.setText("");
		//
		jButtonReplaceAllSelected.setEnabled(false);
		jButtonGenerateListData.setEnabled(false);
		jTextFieldScan.requestFocusInWindow();
		//
		super.setVisible(true);
		//
		return updated;
	}

	void jCheckBoxTaskAndRole_stateChanged(ChangeEvent e) {
		if (jCheckBoxTask.isSelected() || jCheckBoxRole.isSelected()) {
			jComboBoxRoles.setEnabled(true);
		} else {
			jComboBoxRoles.setEnabled(false);
		}
	}

	void jCheckBoxSubsystem_stateChanged(ChangeEvent e) {
		if (jCheckBoxSubsystem.isSelected() || jCheckBoxTable.isSelected() || jCheckBoxFunction.isSelected()) {
			jComboBoxSubsystems.setEnabled(true);
		} else {
			jComboBoxSubsystems.setEnabled(false);
		}
	}

	void jCheckBoxAll_actionPerformed(ActionEvent e) {
		jCheckBoxSystem.setSelected((jCheckBoxAll.isSelected()));
		jCheckBoxDepartment.setSelected((jCheckBoxAll.isSelected()));
		jCheckBoxTaskType.setSelected((jCheckBoxAll.isSelected()));
		jCheckBoxTableType.setSelected((jCheckBoxAll.isSelected()));
		jCheckBoxDataType.setSelected((jCheckBoxAll.isSelected()));
		jCheckBoxFunctionType.setSelected((jCheckBoxAll.isSelected()));
		jCheckBoxTerms.setSelected((jCheckBoxAll.isSelected()));
		jCheckBoxMaintenanceLog.setSelected((jCheckBoxAll.isSelected()));
		jCheckBoxSubjectArea.setSelected((jCheckBoxAll.isSelected()));
		jCheckBoxRole.setSelected((jCheckBoxAll.isSelected()));
		jCheckBoxTask.setSelected((jCheckBoxAll.isSelected()));
		jCheckBoxSubsystem.setSelected((jCheckBoxAll.isSelected()));
		jCheckBoxTable.setSelected((jCheckBoxAll.isSelected()));
		jCheckBoxFunction.setSelected((jCheckBoxAll.isSelected()));
	}

	void jButtonStartScan_actionPerformed(ActionEvent e) {
		XeadNode workNode;
		Object workElement;
		String subsystemID = "";
		String roleID = "";
		java.util.List<?> systemList = null;
		java.util.List<?> departmentList = null;
		java.util.List<?> taskTypeList = null;
		java.util.List<?> tableTypeList = null;
		java.util.List<?> dataTypeList = null;
		java.util.List<?> functionTypeList = null;
		java.util.List<?> termsList = null;
		java.util.List<?> maintenanceLogList = null;
		java.util.List<?> subjectAreaList = null;
		java.util.List<?> roleList = null;
		java.util.List<?> taskList = null;
		java.util.List<?> subsystemList = null;
		java.util.List<?> tableList = null;
		java.util.List<?> relationshipList = null;
		java.util.List<?> functionList = null;
		java.util.List<?> workList1 = null;
		java.util.List<?> workList2 = null;
		int numberOfTableElementsToBeScanned = -1;
		Object[] tableElementArray = new Object[2000];
		//
		try {
			setCursor(new Cursor(Cursor.WAIT_CURSOR));
			//
			if (jCheckBoxCaseSensitive.isSelected()) {
				stringToBeScanned = jTextFieldScan.getText();
			} else {
				stringToBeScanned = jTextFieldScan.getText().toUpperCase();
			}
			//
			//Clear previous scan result//
			countOfRows = 0;
			countOfStrings = 0;
			jTextPaneScanDetail.setText("");
			if (tableModelScanResult.getRowCount() > 0) {
				int rowCount = tableModelScanResult.getRowCount();
				for (int i = 0; i < rowCount; i++) {tableModelScanResult.removeRow(0);}
			}
			jButtonReplaceAllSelected.setEnabled(false);
			checkBoxHeaderRenderer.setSelected(false);
			jTableScanResult.getTableHeader().repaint();   
			//
			//Get ID of Role and Subsystem if they are specified to be scanned;
			if (jComboBoxSubsystems.getSelectedIndex() != 0) {
				workNode = (XeadNode)comboBoxModelSubsystems.getSelectedItem();
				workElement = workNode.getElement();
				subsystemID = getAttribute(workElement, "ID");
			}
			if (jComboBoxRoles.getSelectedIndex() != 0) {
				workNode = (XeadNode)comboBoxModelRoles.getSelectedItem();
				workElement = workNode.getElement();
				roleID = getAttribute(workElement, "ID");
			}
			//
			//Count number of elements to be scanned//
			int countOfElementsToBeScanned = 0;
			xeadModeler.model.json.XeadSystem xeadSystem = frame_.getXeadDocumentModel().getXeadSystem();
			if (jCheckBoxSystem.isSelected()) {
				countOfElementsToBeScanned += 1;
			}
			if (jCheckBoxDepartment.isSelected()) {
				departmentList = xeadSystem.getDepartments();
				countOfElementsToBeScanned += departmentList.size();
			}
			if (jCheckBoxTaskType.isSelected()) {
				taskTypeList = xeadSystem.getTaskTypes();
				countOfElementsToBeScanned += taskTypeList.size();
			}
			if (jCheckBoxTableType.isSelected()) {
				tableTypeList = xeadSystem.getTableTypes();
				countOfElementsToBeScanned += tableTypeList.size();
			}
			if (jCheckBoxDataType.isSelected()) {
				dataTypeList = xeadSystem.getDataTypes();
				countOfElementsToBeScanned += dataTypeList.size();
			}
			if (jCheckBoxFunctionType.isSelected()) {
				functionTypeList = xeadSystem.getFunctionTypes();
				countOfElementsToBeScanned += functionTypeList.size();
			}
			if (jCheckBoxTerms.isSelected()) {
				termsList = xeadSystem.getTerms();
				countOfElementsToBeScanned += termsList.size();
			}
			if (jCheckBoxMaintenanceLog.isSelected()) {
				maintenanceLogList = xeadSystem.getMaintenanceLogs();
				countOfElementsToBeScanned += maintenanceLogList.size();
			}
			if (jCheckBoxSubjectArea.isSelected()) {
				subjectAreaList = xeadSystem.getSubjectAreas();
				countOfElementsToBeScanned += subjectAreaList.size();
			}
			if (jCheckBoxRole.isSelected()) {
				roleList = xeadSystem.getRoles();
				if (roleID.equals("")) {
					countOfElementsToBeScanned += roleList.size();
				} else {
					countOfElementsToBeScanned++;
				}
			}
			if (jCheckBoxTask.isSelected()) {
				taskList = xeadSystem.getTasks();
				if (roleID.equals("")) {
					countOfElementsToBeScanned += taskList.size();
				} else {
					for (int i = 0; i < taskList.size(); i++) {
						workElement = taskList.get(i);
						if (roleID.equals(getAttribute(workElement, "RoleID"))) {
							countOfElementsToBeScanned++;
						}
					}
				}
			}
			if (jCheckBoxSubsystem.isSelected()) {
				subsystemList = xeadSystem.getSubsystems();
				if (subsystemID.equals("")) {
					countOfElementsToBeScanned += subsystemList.size();
				} else {
					countOfElementsToBeScanned++;
				}
			}
			if (jCheckBoxTable.isSelected()) {
				tableList = xeadSystem.getTables();
				if (subsystemID.equals("")) {
					countOfElementsToBeScanned += tableList.size();
				} else {
					for (int i = 0; i < tableList.size(); i++) {
						Object element = tableList.get(i);
						if (subsystemID.equals(getAttribute(element, "SubsystemID"))) {
							countOfElementsToBeScanned++;
							numberOfTableElementsToBeScanned++;
							tableElementArray[numberOfTableElementsToBeScanned] = element;
						}
					}
				}
				relationshipList = xeadSystem.getRelationships();
				if (subsystemID.equals("")) {
					countOfElementsToBeScanned += relationshipList.size();
				} else {
					for (int i = 0; i < relationshipList.size(); i++) {
						workElement = relationshipList.get(i);
						for (int j = 0; j < numberOfTableElementsToBeScanned; j++) {
							if (getAttribute(tableElementArray[j], "ID").equals(getAttribute(workElement, "Table1ID")) || getAttribute(tableElementArray[j], "ID").equals(getAttribute(workElement, "Table2ID"))) {
								countOfElementsToBeScanned++;
							}
						}
					}
				}
			}
			if (jCheckBoxFunction.isSelected()) {
				functionList = xeadSystem.getFunctions();
				if (subsystemID.equals("")) {
					countOfElementsToBeScanned += functionList.size();
				} else {
					for (int i = 0; i < functionList.size(); i++) {
						workElement = functionList.get(i);
						if (subsystemID.equals(getAttribute(workElement, "SubsystemID"))) {
							countOfElementsToBeScanned++;
						}
					}
				}
			}
			//
			jProgressBar.setValue(0);
			jProgressBar.setMaximum(countOfElementsToBeScanned);
			jProgressBar.setVisible(true);
			jButtonStartScan.setVisible(false);
			//
			//Scan elements//
			if (jCheckBoxSystem.isSelected()) {
				jProgressBar.setValue(jProgressBar.getValue() + 1);
				jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());
				//
				scanAttribute(xeadSystem, "System", "Name");
				scanAttribute(xeadSystem, "System", "Descriptions");
			}
			if (jCheckBoxDepartment.isSelected()) {
				for (int i = 0; i < departmentList.size(); i++) {
					jProgressBar.setValue(jProgressBar.getValue() + 1);
					jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());
					//
					Object element = departmentList.get(i);
					scanAttribute(element, "Department", "SortKey");
					scanAttribute(element, "Department", "Name");
					scanAttribute(element, "Department", "Descriptions");
				}
			}
			if (jCheckBoxTaskType.isSelected()) {
				for (int i = 0; i < taskTypeList.size(); i++) {
					jProgressBar.setValue(jProgressBar.getValue() + 1);
					jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());
					//
					Object element = taskTypeList.get(i);
					scanAttribute(element, "TaskType", "SortKey");
					scanAttribute(element, "TaskType", "Name");
					scanAttribute(element, "TaskType", "Descriptions");
				}
			}
			if (jCheckBoxTableType.isSelected()) {
				for (int i = 0; i < tableTypeList.size(); i++) {
					jProgressBar.setValue(jProgressBar.getValue() + 1);
					jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());
					//
					Object element = tableTypeList.get(i);
					scanAttribute(element, "TableType", "SortKey");
					scanAttribute(element, "TableType", "Name");
					scanAttribute(element, "TableType", "Descriptions");
				}
			}
			if (jCheckBoxDataType.isSelected()) {
				for (int i = 0; i < dataTypeList.size(); i++) {
					jProgressBar.setValue(jProgressBar.getValue() + 1);
					jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());
					//
					Object element = dataTypeList.get(i);
					scanAttribute(element, "DataType", "SortKey");
					scanAttribute(element, "DataType", "Name");
					scanAttribute(element, "DataType", "Descriptions");
				}
			}
			if (jCheckBoxFunctionType.isSelected()) {
				for (int i = 0; i < functionTypeList.size(); i++) {
					jProgressBar.setValue(jProgressBar.getValue() + 1);
					jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());
					//
					Object element = functionTypeList.get(i);
					scanAttribute(element, "FunctionType", "SortKey");
					scanAttribute(element, "FunctionType", "Name");
					scanAttribute(element, "FunctionType", "Descriptions");
				}
			}
			if (jCheckBoxTerms.isSelected()) {
				for (int i = 0; i < termsList.size(); i++) {
					jProgressBar.setValue(jProgressBar.getValue() + 1);
					jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());
					//
					Object element = termsList.get(i);
					scanAttribute(element, "Terms", "SortKey");
					scanAttribute(element, "Terms", "Header");
					scanAttribute(element, "Terms", "Descriptions");
				}
			}
			if (jCheckBoxMaintenanceLog.isSelected()) {
				for (int i = 0; i < maintenanceLogList.size(); i++) {
					jProgressBar.setValue(jProgressBar.getValue() + 1);
					jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());
					//
					Object element = maintenanceLogList.get(i);
					scanAttribute(element, "MaintenanceLog", "SortKey");
					scanAttribute(element, "MaintenanceLog", "Headder");
					scanAttribute(element, "MaintenanceLog", "Descriptions");
				}
			}
 		if (jCheckBoxSubjectArea.isSelected()) {
 			for (int i = 0; i < subjectAreaList.size(); i++) {
 				jProgressBar.setValue(jProgressBar.getValue() + 1);
 				jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());
 				//
 				Object element = subjectAreaList.get(i);
 				scanAttribute(element, "SubjectArea", "SortKey");
 				scanAttribute(element, "SubjectArea", "Name");
 				scanAttribute(element, "SubjectArea", "Descriptions");
 				//
 				xeadModeler.model.json.SubjectArea subjectArea = (xeadModeler.model.json.SubjectArea)element;
 				workList1 = subjectArea.getDataflowNodes();
 				for (int j = 0; j < workList1.size(); j++) {
 					Object dataflowNodeElement = workList1.get(j);
 					if (!getAttribute(dataflowNodeElement, "Type").equals("Process")) {
 						scanAttribute(dataflowNodeElement, "DataflowNode", "Name");
 						scanAttribute(dataflowNodeElement, "DataflowNode", "NameExt");
 					}
 					scanAttribute(dataflowNodeElement, "DataflowNode", "Descriptions");
 				}
 				//
 				workList1 = subjectArea.getDataflowLines();
 				for (int j = 0; j < workList1.size(); j++) {
 					Object dataflowLineElement = workList1.get(j);
 					scanAttribute(dataflowLineElement, "DataflowLine", "Name");
 					scanAttribute(dataflowLineElement, "DataflowLine", "NameExt");
 				}
 			}
 		}
 		if (jCheckBoxRole.isSelected()) {
 			for (int i = 0; i < roleList.size(); i++) {
 				Object element = roleList.get(i);
 				if (roleID.equals("") || roleID.equals(getAttribute(element, "ID"))) {
 					jProgressBar.setValue(jProgressBar.getValue() + 1);
 					jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());
 					//
 					scanAttribute(element, "Role", "SortKey");
 					scanAttribute(element, "Role", "Name");
 					scanAttribute(element, "Role", "Descriptions");
 					//
 					if (!roleID.equals("") && roleID.equals(getAttribute(element, "ID"))) {
 						break;
 					}
 				}
 			}
 		}
 		if (jCheckBoxTask.isSelected()) {
 			for (int i = 0; i < taskList.size(); i++) {
 				Object element = taskList.get(i);
 				if (roleID.equals("") || roleID.equals(getAttribute(element, "RoleID"))) {
 					jProgressBar.setValue(jProgressBar.getValue() + 1);
 					jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());
 					//
 					scanAttribute(element, "Task", "SortKey");
 					scanAttribute(element, "Task", "Name");
 					scanAttribute(element, "Task", "Event");
 					scanAttribute(element, "Task", "Descriptions");
 					//
 					xeadModeler.model.json.Task task = (xeadModeler.model.json.Task)element;
 					workList1 = task.getTaskActions();
 					for (int j = 0; j < workList1.size(); j++) {
 						Object taskActionElement = workList1.get(j);
 						scanAttribute(taskActionElement, "TaskAction", "ExecuteIf");
 						scanAttribute(taskActionElement, "TaskAction", "Label");
 						scanAttribute(taskActionElement, "TaskAction", "Descriptions");
 						//
 						xeadModeler.model.json.TaskAction taskAction = (xeadModeler.model.json.TaskAction)taskActionElement;
 						workList2 = taskAction.getTaskFunctionIOs();
 						for (int k = 0; k < workList2.size(); k++) {
 							Object taskFunctionIOElement = workList2.get(k);
 							scanAttribute(taskFunctionIOElement, "TaskFunctionIO", "Operations");
 						}
 					}
 				}
 			}
 		}
 		if (jCheckBoxSubsystem.isSelected()) {
 			for (int i = 0; i < subsystemList.size(); i++) {
 				Object element = subsystemList.get(i);
 				if (subsystemID.equals("") || subsystemID.equals(getAttribute(element, "ID"))) {
 					jProgressBar.setValue(jProgressBar.getValue() + 1);
 					jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());
 					//
 					scanAttribute(element, "Subsystem", "Name");
 					scanAttribute(element, "Subsystem", "Descriptions");
 					scanAttribute(element, "Subsystem", "DatamodelDescriptions");
 					//
 					if (jCheckBoxTable.isSelected()) {
 						xeadModeler.model.json.Subsystem subsystem = (xeadModeler.model.json.Subsystem)element;
 						workList1 = subsystem.getSubsystemTables();
 						for (int j = 0; j < workList1.size(); j++) {
 							Object subsystemTableElement = workList1.get(j);
 							scanAttribute(subsystemTableElement, "SubsystemTable", "Instance");
 						}
 					}
 					//
 					if (!subsystemID.equals("") && subsystemID.equals(getAttribute(element, "ID"))) {
 						break;
 					}
 				}
 			}
 		}
 		if (jCheckBoxTable.isSelected()) {
 			for (int i = 0; i < tableList.size(); i++) {
 				Object element = tableList.get(i);
 				if (subsystemID.equals("") || subsystemID.equals(getAttribute(element, "SubsystemID"))) {
 					jProgressBar.setValue(jProgressBar.getValue() + 1);
 					jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());
 					//
 					scanAttribute(element, "Table", "Name");
 					scanAttribute(element, "Table", "SortKey");
 					scanAttribute(element, "Table", "Alias");
 					scanAttribute(element, "Table", "SynchFile");
 					scanAttribute(element, "Table", "Descriptions");
 					//
 					xeadModeler.model.json.Table table = (xeadModeler.model.json.Table)element;
 					workList1 = table.getTableFields();
 					for (int j = 0; j < workList1.size(); j++) {
 						Object tableFieldElement = workList1.get(j);
 						scanAttribute(tableFieldElement, "TableField", "Name");
 						scanAttribute(tableFieldElement, "TableField", "Alias");
 						scanAttribute(tableFieldElement, "TableField", "Descriptions");
 						scanAttribute(tableFieldElement, "TableField", "Default");
 					}
 				}
 			}
 			for (int i = 0; i < relationshipList.size(); i++) {
 				Object element = relationshipList.get(i);
 				if (subsystemID.equals("")) {
 					jProgressBar.setValue(jProgressBar.getValue() + 1);
 					jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());
 					//
 					scanAttribute(element, "Relationship", "ExistWhen1");
 					scanAttribute(element, "Relationship", "ExistWhen2");
 				} else {
 					for (int j = 0; j < numberOfTableElementsToBeScanned; j++) {
 						if (getAttribute(tableElementArray[j], "ID").equals(getAttribute(element, "Table1ID")) || getAttribute(tableElementArray[j], "ID").equals(getAttribute(element, "Table2ID"))) {
 							jProgressBar.setValue(jProgressBar.getValue() + 1);
 							jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());
 							//
 							scanAttribute(element, "Relationship", "ExistWhen1");
 							scanAttribute(element, "Relationship", "ExistWhen2");
 						}
 					}
 				}
 			}
 		}
 		if (jCheckBoxFunction.isSelected()) {
 			for (int i = 0; i < functionList.size(); i++) {
 				Object element = functionList.get(i);
 				if (subsystemID.equals("") || subsystemID.equals(getAttribute(element, "SubsystemID"))) {
 					jProgressBar.setValue(jProgressBar.getValue() + 1);
 					jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());
 					//
 					scanAttribute(element, "Function", "SortKey");
 					scanAttribute(element, "Function", "Name");
 					scanAttribute(element, "Function", "Summary");
 					scanAttribute(element, "Function", "Parameters");
 					scanAttribute(element, "Function", "Return");
 					scanAttribute(element, "Function", "DocFile");
 					scanAttribute(element, "Function", "Descriptions");
 					//
 					xeadModeler.model.json.Function function = (xeadModeler.model.json.Function)element;
 					workList1 = function.getFunctionUsedByThisList();
 					for (int j = 0; j < workList1.size(); j++) {
 						Object functionUsedByThisElement = workList1.get(j);
 						scanAttribute(functionUsedByThisElement, "FunctionUsedByThis", "LaunchEvent");
 					}
 					//
 					workList1 = function.getIOPanels();
 					for (int j = 0; j < workList1.size(); j++) {
 						Object iOPanelElement = workList1.get(j);
 						scanAttribute(iOPanelElement, "IOPanel", "SortKey");
 						scanAttribute(iOPanelElement, "IOPanel", "Name");
 						scanAttribute(iOPanelElement, "IOPanel", "Descriptions");
 						scanAttribute(iOPanelElement, "IOPanel", "ImageText");
 						//
 						xeadModeler.model.json.IOPanel ioPanel = (xeadModeler.model.json.IOPanel)iOPanelElement;
 						workList2 = ioPanel.getIopanelFields();
 						for (int k = 0; k < workList2.size(); k++) {
 							Object iOPanelFieldElement = workList2.get(k);
 							scanAttribute(iOPanelFieldElement, "IOPanelField", "Name");
 							scanAttribute(iOPanelFieldElement, "IOPanelField", "Label");
 							scanAttribute(iOPanelFieldElement, "IOPanelField", "Descriptions");
 						}
 					}
 					//
 					workList1 = function.getIOWebPages();
 					for (int j = 0; j < workList1.size(); j++) {
 						Object iOWebPageElement = workList1.get(j);
 						scanAttribute(iOWebPageElement, "IOWebPage", "SortKey");
 						scanAttribute(iOWebPageElement, "IOWebPage", "Name");
 						scanAttribute(iOWebPageElement, "IOWebPage", "Descriptions");
 						scanAttribute(iOWebPageElement, "IOWebPage", "FileName");
 						//
 						xeadModeler.model.json.IOWebPage ioWebPage = (xeadModeler.model.json.IOWebPage)iOWebPageElement;
 						workList2 = ioWebPage.getIowebpageFields();
 						for (int k = 0; k < workList2.size(); k++) {
 							Object iOWebPageFieldElement = workList2.get(k);
 							scanAttribute(iOWebPageFieldElement, "IOWebPageField", "Name");
 							scanAttribute(iOWebPageFieldElement, "IOWebPageField", "Label");
 							scanAttribute(iOWebPageFieldElement, "IOWebPageField", "Descriptions");
 						}
 					}
 					//
 					workList1 = function.getIOSpools();
 					for (int j = 0; j < workList1.size(); j++) {
 						Object iOSpoolElement = workList1.get(j);
 						scanAttribute(iOSpoolElement, "IOSpool", "SortKey");
 						scanAttribute(iOSpoolElement, "IOSpool", "Name");
 						scanAttribute(iOSpoolElement, "IOSpool", "Descriptions");
 						scanAttribute(iOSpoolElement, "IOSpool", "ImageText");
 						//
 						xeadModeler.model.json.IOSpool ioSpool = (xeadModeler.model.json.IOSpool)iOSpoolElement;
 						workList2 = ioSpool.getIospoolFields();
 						for (int k = 0; k < workList2.size(); k++) {
 							Object iOSpoolFieldElement = workList2.get(k);
 							scanAttribute(iOSpoolFieldElement, "IOSpoolField", "Name");
 							scanAttribute(iOSpoolFieldElement, "IOSpoolField", "Label");
 							scanAttribute(iOSpoolFieldElement, "IOSpoolField", "Descriptions");
 						}
 					}
 					//
 					workList1 = function.getIOTables();
 					for (int j = 0; j < workList1.size(); j++) {
 						Object iOTableElement = workList1.get(j);
 						scanAttribute(iOTableElement, "IOTable", "SortKey");
 						scanAttribute(iOTableElement, "IOTable", "NameExtension");
 						scanAttribute(iOTableElement, "IOTable", "Descriptions");
 						//
 						xeadModeler.model.json.IOTable ioTable = (xeadModeler.model.json.IOTable)iOTableElement;
 						workList2 = ioTable.getIotableFields();
 						for (int k = 0; k < workList2.size(); k++) {
 							Object iOTableFieldElement = workList2.get(k);
 							scanAttribute(iOTableFieldElement, "IOTableField", "Descriptions");
 						}
 					}
 				}
 			}
 		}
			//
			if (countOfRows > 0) {
				jTextPaneScanDetail.setText(countOfRows + res.getString("DialogScan32") + countOfStrings + res.getString("DialogScan33"));
				jButtonGenerateListData.setEnabled(true);
			} else {
				jTextPaneScanDetail.setText(res.getString("DialogScan34"));
				jButtonGenerateListData.setEnabled(false);
			}
		} finally {
			jProgressBar.setVisible(false);
			jButtonStartScan.setVisible(true);
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			jTextFieldScan.requestFocusInWindow();
		}
	}

	void scanAttribute(Object element, String elementType, String attributeType) {
		String targetString = "";
		int scanningPosFrom = 0;
		int countOfScanned = 0;
		boolean processingEOLRequired = false;
		//
		if (attributeType.equals("Descriptions")
				|| attributeType.equals("Operations")
				|| attributeType.equals("ImageText")
				|| attributeType.equals("DatamodelDescriptions")) {
			processingEOLRequired = true;
			targetString = substringLinesWithTokenOfEOL(getAttribute(element, attributeType), "\n");
		} else {
			processingEOLRequired = false;
			targetString = getAttribute(element, attributeType);
		}
		if (!jCheckBoxCaseSensitive.isSelected()) {
			targetString = targetString.toUpperCase();
		}
		//
		do {
			scanningPosFrom = targetString.indexOf(stringToBeScanned, scanningPosFrom);
			if (scanningPosFrom != -1) {
				scanningPosFrom++;
				countOfScanned++;
			}
		} while(scanningPosFrom != -1);
		//
		if (countOfScanned > 0) {
			Object[] Cell = new Object[7];
			countOfRows++;
			countOfStrings = countOfStrings + countOfScanned;
			Cell[0] = new TableRowNumber(countOfRows, element, attributeType);
			Cell[1] = Boolean.FALSE;
			Cell[2] = translateElementType(elementType);
			Cell[3] = getItemName(element, elementType);
			Cell[4] = translateAttributeType(attributeType);
			if (processingEOLRequired) {
				Cell[5] = getFirstSentence(getAttribute(element, attributeType));
			} else {
				Cell[5] = getAttribute(element, attributeType);
			}
			Cell[6] = Integer.toString(countOfScanned);
			tableModelScanResult.addRow(Cell);
		}
	}

	String translateElementType(String elementType) {
		String result = "";
		//
		if (elementType.equals("System")) {
			result = res.getString("DialogScan35");
		}
		if (elementType.equals("Department")) {
			result = res.getString("DialogScan36");
		}
		if (elementType.equals("TaskType")) {
			result = res.getString("DialogScan26");
		}
		if (elementType.equals("TableType")) {
			result = res.getString("DialogScan37");
		}
		if (elementType.equals("DataType")) {
			result = res.getString("DialogScan38");
		}
		if (elementType.equals("FunctionType")) {
			result = res.getString("DialogScan39");
		}
		if (elementType.equals("Terms")) {
			result = res.getString("DialogScan84");
		}
		if (elementType.equals("MaintenanceLog")) {
			result = res.getString("DialogScan40");
		}
		if (elementType.equals("SubjectArea")) {
			result = res.getString("DialogScan41");
		}
		if (elementType.equals("DataflowNode")) {
			result = res.getString("DialogScan42");
		}
		if (elementType.equals("DataflowLine")) {
			result = res.getString("DialogScan43");
		}
		if (elementType.equals("Role")) {
			result = res.getString("DialogScan44");
		}
		if (elementType.equals("Task")) {
			result = res.getString("DialogScan45");
		}
		if (elementType.equals("TaskAction")) {
			result = res.getString("DialogScan46");
		}
		if (elementType.equals("TaskFunctionIO")) {
			result = res.getString("DialogScan47");
		}
		if (elementType.equals("Subsystem")) {
			result = res.getString("DialogScan48");
		}
		if (elementType.equals("SubsystemTable")) {
			result = res.getString("DialogScan49");
		}
		if (elementType.equals("Table")) {
			result = res.getString("DialogScan50");
		}
		if (elementType.equals("TableField")) {
			result = res.getString("DialogScan51");
		}
		if (elementType.equals("Relationship")) {
			result = res.getString("DialogScan80");
		}
		if (elementType.equals("Function")) {
			result = res.getString("DialogScan52");
		}
		if (elementType.equals("FunctionUsedByThis")) {
			result = res.getString("DialogScan53");
		}
		if (elementType.equals("IOPanel")) {
			result = res.getString("DialogScan54");
		}
		if (elementType.equals("IOPanelField")) {
			result = res.getString("DialogScan55");
		}
		if (elementType.equals("IOWebPage")) {
			result = res.getString("DialogScan56");
		}
		if (elementType.equals("IOWebPageField")) {
			result = res.getString("DialogScan57");
		}
		if (elementType.equals("IOSpool")) {
			result = res.getString("DialogScan58");
		}
		if (elementType.equals("IOSpoolField")) {
			result = res.getString("DialogScan59");
		}
		if (elementType.equals("IOTable")) {
			result = res.getString("DialogScan60");
		}
		if (elementType.equals("IOTableField")) {
			result = res.getString("DialogScan61");
		}
		//
		return result;
	}

	String translateAttributeType(String attributeType) {
		String result = "";
		//
		if (attributeType.equals("SortKey")) {
			result = res.getString("DialogScan62");
		}
		if (attributeType.equals("Alias")) {
			result = res.getString("DialogScan62");
		}
		if (attributeType.equals("Name")) {
			result = res.getString("DialogScan63");
		}
		if (attributeType.equals("NameExt")) {
			result = res.getString("DialogScan64");
		}
		if (attributeType.equals("ExecuteIf")) {
			result = res.getString("DialogScan65");
		}
		if (attributeType.equals("Label")) {
			result = res.getString("DialogScan66");
		}
		if (attributeType.equals("Instance")) {
			result = res.getString("DialogScan67");
		}
		if (attributeType.equals("Header")) {
			result = res.getString("DialogScan68");
		}
		if (attributeType.equals("Descriptions")) {
			result = res.getString("DialogScan69");
		}
		if (attributeType.equals("DatamodelDescriptions")) {
			result = res.getString("DialogScan83");
		}
		if (attributeType.equals("Event")) {
			result = res.getString("DialogScan70");
		}
		if (attributeType.equals("Alias")) {
			result = res.getString("DialogScan71");
		}
		if (attributeType.equals("Default")) {
			result = res.getString("DialogScan72");
		}
		if (attributeType.equals("ExistWhen1") || attributeType.equals("ExistWhen2")) {
			result = res.getString("DialogScan73");
		}
		if (attributeType.equals("LaunchEvent")) {
			result = res.getString("DialogScan74");
		}
		if (attributeType.equals("Operations")) {
			result = res.getString("DialogScan75");
		}
		if (attributeType.equals("Summary")) {
			result = res.getString("DialogScan76");
		}
		if (attributeType.equals("Parameters")) {
			result = res.getString("DialogScan77");
		}
		if (attributeType.equals("Return")) {
			result = res.getString("DialogScan78");
		}
		if (attributeType.equals("DocFile")) {
			result = res.getString("DialogScan87");
		}
		if (attributeType.equals("ImageText")) {
			result = res.getString("DialogScan79");
		}
		if (attributeType.equals("SynchFile")) {
			result = res.getString("DialogScan86");
		}
		//
		return result;
	}

	String getItemName(Object element, String elementType) {
		java.util.List<?> workList = null;
		String workString = "";
		Object workElement1, workElement2, workElement3;
		String itemName =  getAttribute(element, "Name") + "(" + getAttribute(element, "SortKey") + ")";
		//
		if (elementType.equals("Terms")) {
			itemName = getAttribute(element, "Header");
		}
		//
		if (elementType.equals("MaintenanceLog")) {
			itemName = getAttribute(element, "Headder");
		}
		//
		if (elementType.equals("DataflowNode")) {
			workElement1 = getParentElement(element);
			if (getAttribute(element, "NameExt").equals("")) {
				itemName = getAttribute(workElement1, "Name") +
					"(" + getAttribute(workElement1, "SortKey") + ")" +
					" + " + getAttribute(element, "Name");
			} else {
				itemName = getAttribute(workElement1, "Name") +
				"(" + getAttribute(workElement1, "SortKey") + ")" +
				" + " + getAttribute(element, "Name") +
				"&" + getAttribute(element, "NameExt");
			}
		}
		//
		if (elementType.equals("DataflowLine")) {
			workElement1 = getParentElement(element);
			if (getAttribute(element, "NameExt").equals("")) {
				itemName = getAttribute(workElement1, "Name") +
				"(" + getAttribute(workElement1, "SortKey") + ")" +
				" + " + getAttribute(element, "Name");
			} else {
				itemName = getAttribute(workElement1, "Name") +
				"(" + getAttribute(workElement1, "SortKey") + ")" +
				" + " + getAttribute(element, "Name") +
				"&" + getAttribute(element, "NameExt");
			}
		}
		//
		if (elementType.equals("Task")) {
			workList = frame_.getXeadDocumentModel().getXeadSystem().getRoles();
			for (int m = 0; m < workList.size(); m++) {
				workElement1 = workList.get(m);
				if (getAttribute(element, "RoleID").equals(getAttribute(workElement1, "ID"))) {
					itemName = getAttribute(workElement1, "Name") +
					"(" + getAttribute(workElement1, "SortKey") + ")" +
					" + " + getAttribute(element, "Name") +
					"(" + getAttribute(element, "SortKey") + ")";
					break;
				}
			}
		}
		//
		if (elementType.equals("TaskAction")) {
			workElement1 = getParentElement(element);
			workList = frame_.getXeadDocumentModel().getXeadSystem().getRoles();
			for (int m = 0; m < workList.size(); m++) {
				workElement2 = workList.get(m);
				if (getAttribute(workElement1, "RoleID").equals(getAttribute(workElement2, "ID"))) {
					itemName = getAttribute(workElement2, "Name") +
					"(" + getAttribute(workElement2, "SortKey") + ")" +
					" + " + getAttribute(workElement1, "Name") +
					"(" + getAttribute(workElement1, "SortKey") + ")" +
					" + " + getAttribute(element, "Label");
					break;
				}
			}
		}
		//
		if (elementType.equals("TaskFunctionIO")) {
			workElement1 = getParentElement(element);
			workElement2 = getParentElement(workElement1);
			//
			workList = frame_.getXeadDocumentModel().getXeadSystem().getRoles();
			for (int m = 0; m < workList.size(); m++) {
				workElement3 = workList.get(m);
				if (getAttribute(workElement2, "RoleID").equals(getAttribute(workElement3, "ID"))) {
					itemName = getAttribute(workElement3, "Name") +
					"(" + getAttribute(workElement3, "SortKey") + ")" +
					" + " + getAttribute(workElement2, "Name") +
					"(" + getAttribute(workElement2, "SortKey") + ")" +
					" + " + getAttribute(workElement1, "Label");
					break;
				}
			}
			//
			workList = frame_.getXeadDocumentModel().getXeadSystem().getFunctions();
			for (int m = 0; m < workList.size(); m++) {
				workElement3 = workList.get(m);
				if (getAttribute(element, "FunctionID").equals(getAttribute(workElement3, "ID"))) {
					itemName = itemName + " + " +
					getAttribute(workElement3, "Name") +
					"(" + getAttribute(workElement3, "SortKey") + ")";
					break;
				}
			}
		}
		//
		if (elementType.equals("SubsystemTable")) {
			workElement1 = getParentElement(element);
			workList = frame_.getXeadDocumentModel().getXeadSystem().getTables();
			for (int m = 0; m < workList.size(); m++) {
				workElement2 = workList.get(m);
				if (getAttribute(element, "TableID").equals(getAttribute(workElement2, "ID"))) {
					itemName = getAttribute(workElement1, "Name") +
					"(" + getAttribute(workElement1, "SortKey") + ")" +
					" + " + getAttribute(workElement2, "Name") +
					"(" + getAttribute(workElement2, "SortKey") + ")";
					break;
				}
			}
		}
		//
		if (elementType.equals("Table")) {
			workList = frame_.getXeadDocumentModel().getXeadSystem().getSubsystems();
			for (int m = 0; m < workList.size(); m++) {
				workElement1 = workList.get(m);
				if (getAttribute(element, "SubsystemID").equals(getAttribute(workElement1, "ID"))) {
					itemName = getAttribute(workElement1, "Name") +
					"(" + getAttribute(workElement1, "SortKey") + ")" +
					" + " + getAttribute(element, "Name") +
					"(" + getAttribute(element, "SortKey") + ")";
					break;
				}
			}
		}
		//
		if (elementType.equals("TableField")) {
			workElement1 = getParentElement(element);
			workList = frame_.getXeadDocumentModel().getXeadSystem().getSubsystems();
			for (int m = 0; m < workList.size(); m++) {
				workElement2 = workList.get(m);
				if (getAttribute(workElement1, "SubsystemID").equals(getAttribute(workElement2, "ID"))) {
					itemName = getAttribute(workElement2, "Name") +
					"(" + getAttribute(workElement2, "SortKey") + ")" +
					" + " + getAttribute(workElement1, "Name") +
					"(" + getAttribute(workElement1, "SortKey") + ")" +
					" + " + getAttribute(element, "Name") +
					"(" + getAttribute(element, "Alias") + ")";
					break;
				}
			}
		}
		//
		if (elementType.equals("Relationship")) {
			workList = frame_.getXeadDocumentModel().getXeadSystem().getTables();
			workString = "";
			for (int m = 0; m < workList.size(); m++) {
				workElement1 = workList.get(m);
				if (getAttribute(element, "Table1ID").equals(getAttribute(workElement1, "ID"))) {
					if (workString.equals("")) {
						workString = getAttribute(workElement1, "Name") + "(" + getAttribute(workElement1, "SortKey") + ")";
					} else {
						workString = workString + " + " + getAttribute(workElement1, "Name") + "(" + getAttribute(workElement1, "SortKey") + ")";
						break;
					}
				}
				if (getAttribute(element, "Table2ID").equals(getAttribute(workElement1, "ID"))) {
					if (workString.equals("")) {
						workString = getAttribute(workElement1, "Name") + "(" + getAttribute(workElement1, "SortKey") + ")";
					} else {
						workString = workString + " + " + getAttribute(workElement1, "Name") + "(" + getAttribute(workElement1, "SortKey") + ")";
						break;
					}
				}
			}
			itemName = workString;
		}
		//
		if (elementType.equals("Function")) {
			workList = frame_.getXeadDocumentModel().getXeadSystem().getSubsystems();
			for (int m = 0; m < workList.size(); m++) {
				workElement1 = workList.get(m);
				if (getAttribute(element, "SubsystemID").equals(getAttribute(workElement1, "ID"))) {
					itemName = getAttribute(workElement1, "Name") +
					"(" + getAttribute(workElement1, "SortKey") + ")" +
					" + " + getAttribute(element, "Name") +
					"(" + getAttribute(element, "SortKey") + ")";
					break;
				}
			}
		}
		//
		if (elementType.equals("FunctionUsedByThis")) {
			workElement1 = getParentElement(element);
			//
			workList = frame_.getXeadDocumentModel().getXeadSystem().getSubsystems();
			for (int m = 0; m < workList.size(); m++) {
				workElement2 = workList.get(m);
				if (getAttribute(workElement1, "SubsystemID").equals(getAttribute(workElement2, "ID"))) {
					itemName = getAttribute(workElement2, "Name") +
					"(" + getAttribute(workElement2, "SortKey") + ")" +
					" + " + getAttribute(workElement1, "Name") +
					"(" + getAttribute(workElement1, "SortKey") + ")";
					break;
				}
			}
			//
			workList = frame_.getXeadDocumentModel().getXeadSystem().getFunctions();
			for (int m = 0; m < workList.size(); m++) {
				workElement3 = workList.get(m);
				if (getAttribute(element, "FunctionID").equals(getAttribute(workElement3, "ID"))) {
					itemName = itemName + " + " +
					getAttribute(workElement3, "Name") +
					"(" + getAttribute(workElement3, "SortKey") + ")";
					break;
				}
			}
		}
		//
		if (elementType.equals("IOPanel") || elementType.equals("IOSpool") || elementType.equals("IOWebPage")) {
			workElement1 = getParentElement(element);
			//
			workList = frame_.getXeadDocumentModel().getXeadSystem().getSubsystems();
			for (int m = 0; m < workList.size(); m++) {
				workElement2 = workList.get(m);
				if (getAttribute(workElement1, "SubsystemID").equals(getAttribute(workElement2, "ID"))) {
					itemName = getAttribute(workElement2, "Name") +
					"(" + getAttribute(workElement2, "SortKey") + ")" +
					" + " + getAttribute(workElement1, "Name") +
					"(" + getAttribute(workElement1, "SortKey") + ")" +
					" + " + getAttribute(element, "Name") +
					"(" + getAttribute(element, "SortKey") + ")";
					break;
				}
			}
		}
		//
		if (elementType.equals("IOPanelField") || elementType.equals("IOSpoolField") || elementType.equals("IOWebPageField")) {
			workElement1 = getParentElement(element);
			workElement2 = getParentElement(workElement1);
			//
			workList = frame_.getXeadDocumentModel().getXeadSystem().getSubsystems();
			for (int m = 0; m < workList.size(); m++) {
				workElement3 = workList.get(m);
				if (getAttribute(workElement2, "SubsystemID").equals(getAttribute(workElement3, "ID"))) {
					itemName = getAttribute(workElement3, "Name") +
					"(" + getAttribute(workElement3, "SortKey") + ")" +
					" + " + getAttribute(workElement2, "Name") +
					"(" + getAttribute(workElement2, "SortKey") + ")" +
					" + " + getAttribute(workElement1, "Name") +
					"(" + getAttribute(workElement1, "SortKey") + ")" +
					" + " + getAttribute(element, "Name") +
					"(" + getAttribute(element, "SortKey") + ")";
					break;
				}
			}
		}
		//
		if (elementType.equals("IOTable")) {
			workElement1 = getParentElement(element);
			//
			workList = frame_.getXeadDocumentModel().getXeadSystem().getSubsystems();
			for (int m = 0; m < workList.size(); m++) {
				workElement2 = workList.get(m);
				if (getAttribute(workElement1, "SubsystemID").equals(getAttribute(workElement2, "ID"))) {
					itemName = getAttribute(workElement2, "Name") +
					"(" + getAttribute(workElement2, "SortKey") + ")" +
					" + " + getAttribute(workElement1, "Name") +
					"(" + getAttribute(workElement1, "SortKey") + ")";
					break;
				}
			}
			//
			workList = frame_.getXeadDocumentModel().getXeadSystem().getTables();
			for (int m = 0; m < workList.size(); m++) {
				workElement2 = workList.get(m);
				if (getAttribute(element, "TableID").equals(getAttribute(workElement2, "ID"))) {
					itemName = itemName + " + " +
					getAttribute(workElement2, "Name") +
					"(" + getAttribute(workElement2, "SortKey") + ")";
					break;
				}
			}
		}
		//
		if (elementType.equals("IOTableField")) {
			workElement1 = getParentElement(element);
			workElement2 = getParentElement(workElement1);
			//
			workList = frame_.getXeadDocumentModel().getXeadSystem().getSubsystems();
			for (int m = 0; m < workList.size(); m++) {
				workElement3 = workList.get(m);
				if (getAttribute(workElement2, "SubsystemID").equals(getAttribute(workElement3, "ID"))) {
					itemName = getAttribute(workElement3, "Name") +
					"(" + getAttribute(workElement3, "SortKey") + ")" +
					" + " + getAttribute(workElement2, "Name") + 
					"(" + getAttribute(workElement2, "SortKey") + ")";
					break;
				}
			}
			//
			workElement3 = null;
			workList = frame_.getXeadDocumentModel().getXeadSystem().getTables();
			for (int m = 0; m < workList.size(); m++) {
				workElement3 = workList.get(m);
				if (getAttribute(workElement1, "TableID").equals(getAttribute(workElement3, "ID"))) {
					itemName = itemName + " + " +
					getAttribute(workElement3, "Name") +
					"(" + getAttribute(workElement3, "SortKey") + ")";
					break;
				}
			}
			//
			if (workElement3 != null) {
				xeadModeler.model.json.Table table = (xeadModeler.model.json.Table)workElement3;
				workList = table.getTableFields();
				for (int m = 0; m < workList.size(); m++) {
					workElement3 = workList.get(m);
					if (getAttribute(element, "FieldID").equals(getAttribute(workElement3, "ID"))) {
						itemName = itemName + " + " +
						getAttribute(workElement3, "Name") +
						"(" + getAttribute(workElement3, "Alias") + ")";
						break;
					}
				}
			}
		}
		//
		return itemName;
	}

	// Helper methods for POJO attribute access
	String getAttribute(Object element, String attributeType) {
		if (element == null) return "";
		try {
			java.lang.reflect.Method method = element.getClass().getMethod("get" + attributeType);
			Object result = method.invoke(element);
			return result != null ? result.toString() : "";
		} catch (Exception e) {
			return "";
		}
	}

	void setAttribute(Object element, String attributeType, String value) {
		if (element == null) return;
		try {
			java.lang.reflect.Method method = element.getClass().getMethod("set" + attributeType, String.class);
			method.invoke(element, value);
		} catch (Exception e) {
			// Ignore if setter not found
		}
	}

	Object getParentElement(Object element) {
		// For POJO-based model, parent relationships are maintained through references
		// This method returns null as parent navigation is not needed for JSON model
		return null;
	}

	String getSortKey(Object element) {
		return getAttribute(element, "SortKey");
	}

	String getFirstSentence(String originalString) {
		String sentence = "";
		originalString = substringLinesWithTokenOfEOL(originalString, "  ");
		if (originalString.length() >= 2) {
			for (int i = 0; i < originalString.length(); i++) {
				if (i+2 <= originalString.length()) {
					if (originalString.substring(i,i+1).equals("�B")) {
						sentence = originalString.substring(0, i) + "...";
						break;
					}
					if (originalString.substring(i,i+2).equals(". ")) {
						sentence = originalString.substring(0, i+1) + "...";
						break;
					}
					if (originalString.substring(i,i+2).equals("  ")) {
						sentence = originalString.substring(0, i+1) + "...";
						break;
					}
				} else if (originalString.substring(originalString.length() - 1, originalString.length())
            .equals("�B")) {
          sentence = originalString.substring(0, originalString.length() - 1);
          break;
        } else {
          sentence = originalString;
          break;
        }
			}
		} else {
			sentence = originalString;
		}
		return sentence;
	}

	static String substringLinesWithTokenOfEOL(String originalString, String stringToBeInserted) {
		StringBuffer processedString = new StringBuffer();
		int lastEnd = 0;
		for (int i = 0; i <= originalString.length(); i++) {
			if (i+5 <= originalString.length()) {
				if (originalString.substring(i,i+5).equals("#EOL#")) {
					processedString.append(originalString.substring(lastEnd, i));
					processedString.append(stringToBeInserted);
					lastEnd = i+5;
				}
			} else {
				if (i == originalString.length()) {
					processedString.append(originalString.substring(lastEnd, i));
				}
			}
		}
		return processedString.toString();
	}

	static String concatLinesWithTokenOfEOL(String originalString) {
		StringBuffer processedString = new StringBuffer();
		int lastEnd = 0;
		for (int i = 0; i <= originalString.length(); i++) {
			if (i < originalString.length()) {
				if (originalString.substring(i,i+1).equals("\n")) {
					processedString.append(originalString.substring(lastEnd, i));
					processedString.append("#EOL#");
					lastEnd = i+1;
				}
			} else {
				processedString.append(originalString.substring(lastEnd, i));
			}
		}
		return processedString.toString();
	}

	void jTableScanResult_valueChanged(ListSelectionEvent e) {
		if (jTableScanResult.getRowCount() > 0) {
			if (jTableScanResult.getSelectedRow() > -1) {
				setSelectedRowValueToTextPane();
			} else {
				jTextPaneScanDetail.setText("");
			}
		}
	}

	void jButtonReplaceAllSelected_actionPerformed(ActionEvent e) {
		int countOfHits = 0;
		String attributeType = "";
		String targetString = "";
		boolean processingEOLRequired = false;
		//
		countOfRowsReplaced = 0;
		countOfStringsReplaced = 0;
		stringToBeReplaced = jTextFieldReplace.getText();
		//
		for (int i = 0; i < tableModelScanResult.getRowCount(); i++) {
			if (((Boolean)tableModelScanResult.getValueAt(i, 1)).booleanValue()) {
				//
				countOfHits = Integer.parseInt((String)tableModelScanResult.getValueAt(i, 6));
				if (countOfHits > 0) {
					//
					TableRowNumber tableRowNumber = (TableRowNumber)tableModelScanResult.getValueAt(i, 0);
					//
					Object element = tableRowNumber.getElement();
					attributeType = tableRowNumber.getAttributeType();
					//
					if (attributeType.equals("Descriptions") || attributeType.equals("Operations") || attributeType.equals("ImageText")) {
						processingEOLRequired = true;
						targetString = substringLinesWithTokenOfEOL(getAttribute(element, attributeType), "\n");
					} else {
						processingEOLRequired = false;
						targetString = getAttribute(element, attributeType);
					}
					if (!jCheckBoxCaseSensitive.isSelected()) {
						targetString = targetString.toUpperCase();
					}
					//
					countOfRowsReplaced++;
					countOfStringsReplaced = countOfStringsReplaced + countOfHits;
					targetString = targetString.replaceAll(stringToBeScanned, stringToBeReplaced);
					if (processingEOLRequired) {
						targetString = concatLinesWithTokenOfEOL(targetString);
					}
					setAttribute(element, attributeType, targetString);
					//
					if (processingEOLRequired) {
						tableModelScanResult.setValueAt(getFirstSentence(getAttribute(element, attributeType)), i, 5);
					} else {
						tableModelScanResult.setValueAt(getAttribute(element, attributeType), i, 5);
					}
				}
			}
		}
		if (countOfRowsReplaced > 0) {
			updated = true;
			setSelectedRowValueToTextPane();
		}
		JOptionPane.showMessageDialog(this, countOfRowsReplaced + res.getString("DialogScan81") + countOfStringsReplaced + res.getString("DialogScan82"));
	}

	void setSelectedRowValueToTextPane() {
		if (jTableScanResult.getSelectedRow() > -1) {
			TableRowNumber tableRowNumber = (TableRowNumber)tableModelScanResult.getValueAt(jTableScanResult.getSelectedRow(), 0);
			Object element = tableRowNumber.getElement();
			//
			String attrType = tableRowNumber.getAttributeType();
			String imageText = "";
			if (attrType.equals("Descriptions")
					|| attrType.equals("Operations")
					|| attrType.equals("ImageText")) {
				imageText = substringLinesWithTokenOfEOL(getAttribute(element, attrType), "\n");
			} else {
				imageText = getAttribute(element, attrType);
			}
			String workString = imageText;
			if (!jCheckBoxCaseSensitive.isSelected()) {
				workString = imageText.toUpperCase();
			}
			jTextPaneScanDetail.removeStyle("style1");
			jTextPaneScanDetail.setText(imageText);
			StyledDocument styledDocument = jTextPaneScanDetail.getStyledDocument();
			Style style1 = styledDocument.addStyle("style1", null);
			StyleConstants.setForeground(style1, Color.BLACK);
			styledDocument.setCharacterAttributes(0, 9999, style1, false);
			int scanningPosFrom = 0;
			int workInt = 0;
			do {
				workInt = workString.indexOf(stringToBeScanned, scanningPosFrom);
				if (workInt == -1) {
					scanningPosFrom = -1;
				} else {
					doc.setCharacterAttributes(workInt, stringToBeScanned.length(), style, false);
					scanningPosFrom = workInt + stringToBeScanned.length();
				}
			} while(scanningPosFrom != -1);
			doc.setParagraphAttributes(0, jTextPaneScanDetail.getDocument().getLength(), attrs, false); 
			jTextPaneScanDetail.setCaretPosition(0);
		}
	}

//	void jButtonGenerateListData_actionPerformed(ActionEvent e) {
//		FileWriter fileWriter = null;
//		BufferedWriter bufferedWriter = null;
//		Boolean selectFlag;
//		String csvFileName = "";
//		//
//		try {
//			File tempCsvFile = File.createTempFile("xeadTemp" + getStringValueOfDateTime("withTime"), ".csv");
//			csvFileName = tempCsvFile.getPath();
//			fileWriter = new FileWriter(csvFileName);
//			bufferedWriter = new BufferedWriter(fileWriter);
//			//
//			for (int i = 0; i < tableModelScanResult.getColumnCount(); i++) {
//				if (i > 0) {
//					bufferedWriter.write(",");
//				}
//				bufferedWriter.write(tableModelScanResult.getColumnName(i));
//			}
//			bufferedWriter.write("\n");
//			//
//			for (int i = 0; i < tableModelScanResult.getRowCount(); i++) {
//				for (int j = 0; j < tableModelScanResult.getColumnCount(); j++) {
//					if (j == 0) {
//						bufferedWriter.write(Integer.toString(i + 1));
//					} else {
//						bufferedWriter.write(",");
//						if (j == 1) {
//							selectFlag = (Boolean)tableModelScanResult.getValueAt(i,j);
//							if (selectFlag.booleanValue()) {
//								bufferedWriter.write("v");
//							} else {
//								bufferedWriter.write("");
//							}
//						} else {
//							bufferedWriter.write(tableModelScanResult.getValueAt(i,j).toString());
//						}
//					}
//				}
//				bufferedWriter.write("\n");
//			}
//		}
//		catch (Exception ex1) {
//			try {
//				bufferedWriter.close();
//			}
//			catch (Exception ex2) {
//			}
//		}
//		try {
//			bufferedWriter.flush();
//			bufferedWriter.close();
//			//
//			File workCsvFile = new File(csvFileName);
//			try {
//				desktop.open(workCsvFile);
//			} catch (Exception ex) {
//				JOptionPane.showMessageDialog(this.getContentPane(), "The aplication related to csv is unable to run.");
//			}
//		}
//		catch (Exception ex3) {
//			try {
//				bufferedWriter.close();
//			}
//			catch (Exception ex4) {
//			}
//		}
//	}

	void jButtonGenerateListData_actionPerformed(ActionEvent e) {
		try {
			desktop.browse(getExcellBookURI());
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(null, "IOException : "+ e1.getMessage());
		}
	}

	URI getExcellBookURI() {
		Boolean selectFlag;
		File xlsFile = null;
		String xlsFileName = "";
		FileOutputStream fileOutputStream = null;
		//
		HSSFWorkbook workBook = new HSSFWorkbook();
		HSSFSheet workSheet = workBook.createSheet("Sheet1");
		workSheet.setDefaultRowHeight( (short) 300);
		HSSFFooter workSheetFooter = workSheet.getFooter();
		workSheetFooter.setRight(jTextFieldScan.getText() + "  Page " + HSSFFooter.page() + " / " + HSSFFooter.numPages() );
		//
		HSSFFont fontHeader = workBook.createFont();
		fontHeader = workBook.createFont();
		fontHeader.setFontName(res.getString("XLSFontHDR"));
		fontHeader.setFontHeightInPoints((short)11);
		//
		HSSFFont fontDetail = workBook.createFont();
		fontDetail.setFontName(res.getString("XLSFontDTL"));
		fontDetail.setFontHeightInPoints((short)11);
		//
 	HSSFCellStyle styleHeader = workBook.createCellStyle();
		styleHeader.setBorderBottom(BorderStyle.THIN);
		styleHeader.setBorderLeft(BorderStyle.THIN);
		styleHeader.setBorderRight(BorderStyle.THIN);
		styleHeader.setBorderTop(BorderStyle.THIN);
 	styleHeader.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		styleHeader.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		styleHeader.setAlignment(HorizontalAlignment.LEFT);
		styleHeader.setFont(fontHeader);
		//
		HSSFCellStyle styleHeaderNumber = workBook.createCellStyle();
		styleHeaderNumber.setBorderBottom(BorderStyle.THIN);
		styleHeaderNumber.setBorderLeft(BorderStyle.THIN);
		styleHeaderNumber.setBorderRight(BorderStyle.THIN);
		styleHeaderNumber.setBorderTop(BorderStyle.THIN);
 	styleHeaderNumber.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		styleHeaderNumber.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		styleHeaderNumber.setAlignment(HorizontalAlignment.LEFT);
		styleHeaderNumber.setFont(fontHeader);
		//
		HSSFCellStyle styleDataInteger = workBook.createCellStyle();
		styleDataInteger.setBorderBottom(BorderStyle.THIN);
		styleDataInteger.setBorderLeft(BorderStyle.THIN);
		styleDataInteger.setBorderRight(BorderStyle.THIN);
		styleDataInteger.setBorderTop(BorderStyle.THIN);
		styleDataInteger.setAlignment(HorizontalAlignment.RIGHT);
		styleDataInteger.setVerticalAlignment(VerticalAlignment.TOP);
		styleDataInteger.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));
		//
		HSSFCellStyle styleDataString = workBook.createCellStyle();
		styleDataString.setBorderBottom(BorderStyle.THIN);
		styleDataString.setBorderLeft(BorderStyle.THIN);
		styleDataString.setBorderRight(BorderStyle.THIN);
		styleDataString.setBorderTop(BorderStyle.THIN);
		styleDataString.setAlignment(HorizontalAlignment.LEFT);
		styleDataString.setVerticalAlignment(VerticalAlignment.TOP);
		styleDataString.setWrapText(true);
		styleDataString.setDataFormat(HSSFDataFormat.getBuiltinFormat("text"));
		//
		int currentRowNumber = -1;
		//
		try {
			xlsFile = File.createTempFile("xeadTemp" + getStringValueOfDateTime("withTime"), ".xls");
			xlsFileName = xlsFile.getPath();
			fileOutputStream = new FileOutputStream(xlsFileName);
			//
			currentRowNumber++;
			HSSFRow rowCaption = workSheet.createRow(currentRowNumber);
			for (int i = 0; i < tableModelScanResult.getColumnCount(); i++) {
				HSSFCell cell = rowCaption.createCell(i);
				cell.setCellStyle(styleHeaderNumber);
				if (i == 1) {
					cell.setCellValue(new HSSFRichTextString(res.getString("DialogScan19")));
				} else {
					cell.setCellValue(new HSSFRichTextString(tableModelScanResult.getColumnName(i)));
				}
				Rectangle rect = jTableScanResult.getCellRect(0, i, true);
				workSheet.setColumnWidth(i, rect.width * 40);
			}
			//
			for (int i = 0; i < tableModelScanResult.getRowCount(); i++) {
				currentRowNumber++;
				HSSFRow rowData = workSheet.createRow(currentRowNumber);
				//
				HSSFCell cell0 = rowData.createCell(0); //Column of Sequence Number
				cell0.setCellType(CellType.NUMERIC);
				cell0.setCellStyle(styleDataInteger);
				cell0.setCellValue(i + 1);
				//
				for (int j = 1; j < 7; j++) {
					HSSFCell cell = rowData.createCell(j);
					cell.setCellType(CellType.STRING);
					cell.setCellStyle(styleDataString);
					if (j == 1) {
						selectFlag = (Boolean)tableModelScanResult.getValueAt(i,j);
						if (selectFlag.booleanValue()) {
							cell.setCellValue(new HSSFRichTextString("v"));
						} else {
							cell.setCellValue(new HSSFRichTextString(""));
						}
					} else {
						if (j == 6) {
							cell.setCellValue(Double.parseDouble(tableModelScanResult.getValueAt(i, j).toString()));
						} else {
							cell.setCellValue(new HSSFRichTextString(tableModelScanResult.getValueAt(i, j).toString()));
						}
					}
				}
			}
			//
			workBook.write(fileOutputStream);
			fileOutputStream.close();
			//
		} catch (Exception ex1) {
			JOptionPane.showMessageDialog(null, "Exception : "+ ex1.getMessage());
			try {
				fileOutputStream.close();
			} catch (Exception ex2) {
			}
		}
		//
		return xlsFile.toURI();
		
	}

	String getStringValueOfDateTime(String timeFormat) {
		String monthStr = "";
		String dayStr = "";
		String hourStr = "";
		String minStr = "";
		String secStr = "";
		String returnValue = "";
		GregorianCalendar calendar = new GregorianCalendar();
		//
		int year = calendar.get(Calendar.YEAR);
		//
		int month = calendar.get(Calendar.MONTH) + 1;
		if (month < 10) {
			monthStr = "0" + Integer.toString(month);
		} else {
			monthStr = Integer.toString(month);
		}
		//
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		if (day < 10) {
			dayStr = "0" + Integer.toString(day);
		} else {
			dayStr = Integer.toString(day);
		}
		//
		if (timeFormat.equals("withTime")) {
			int hour = calendar.get(Calendar.HOUR_OF_DAY);
			if (hour < 10) {
				hourStr = "0" + Integer.toString(hour);
			} else {
				hourStr = Integer.toString(hour);
			}
			//
			int minute = calendar.get(Calendar.MINUTE);
			if (minute < 10) {
				minStr = "0" + Integer.toString(minute);
			} else {
				minStr = Integer.toString(minute);
			}
			//
			int second = calendar.get(Calendar.SECOND);
			if (second < 10) {
				secStr = "0" + Integer.toString(second);
			} else {
				secStr = Integer.toString(second);
			}
		}
		//
		returnValue = Integer.toString(year) + monthStr + dayStr + hourStr + minStr + secStr;
		return returnValue;
	}

	void jButtonCloseDialog_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}

	void jTextFieldScan_keyReleased(KeyEvent e) {
		if (jTextFieldScan.getText().equals("")) {
			jButtonStartScan.setEnabled(false);
		} else {
			jButtonStartScan.setEnabled(true);
			panelMain.getRootPane().setDefaultButton(jButtonStartScan);
			jTextFieldReplace.setText(jTextFieldScan.getText());
		}
	}

	void jTextFieldReplace_keyReleased(KeyEvent e) {
		jButtonReplaceAllSelected.setEnabled(false);
		if (!stringToBeScanned.equals(jTextFieldReplace.getText())) {
			for (int i = 0; i < tableModelScanResult.getRowCount(); i++) {
				if (((Boolean)tableModelScanResult.getValueAt(i, 1)).booleanValue()) {
					jButtonReplaceAllSelected.setEnabled(true);
					break;
				}
			}
		}
	}

	void jTableScanResult_mouseClicked(MouseEvent e) {
		jButtonReplaceAllSelected.setEnabled(false);
		if (!stringToBeScanned.equals(jTextFieldReplace.getText())) {
			for (int i = 0; i < tableModelScanResult.getRowCount(); i++) {
				if (((Boolean)tableModelScanResult.getValueAt(i, 1)).booleanValue()) {
					jButtonReplaceAllSelected.setEnabled(true);
					break;
				}
			}
		}
	}

	class CheckBoxHeaderListener implements ItemListener {
		private CheckBoxHeaderRenderer rendererComponent_ = null;   
		public void setRenderer(CheckBoxHeaderRenderer rendererComponent) {
			rendererComponent_ = rendererComponent;
		}
		public void itemStateChanged(ItemEvent e) {   
			if (rendererComponent_ != null) {
				if (rendererComponent_.isSelected()) {
					for (int i = 0; i < tableModelScanResult.getRowCount(); i++) {
						tableModelScanResult.setValueAt(Boolean.TRUE, i, 1);
					}
					if (!stringToBeScanned.equals(jTextFieldReplace.getText().toUpperCase()) && !jCheckBoxCaseSensitive.isSelected()
							|| !stringToBeScanned.equals(jTextFieldReplace.getText()) && jCheckBoxCaseSensitive.isSelected()) {
						jButtonReplaceAllSelected.setEnabled(true);
					}
				} else {
					for (int i = 0; i < tableModelScanResult.getRowCount(); i++) {
						tableModelScanResult.setValueAt(Boolean.FALSE, i, 1);
					}
					jButtonReplaceAllSelected.setEnabled(false);
				}
			}
		}   
	}   

	class CheckBoxHeaderRenderer extends JCheckBox implements TableCellRenderer, MouseListener {   
		private static final long serialVersionUID = 1L;
		protected CheckBoxHeaderRenderer rendererComponent;   
		protected int column;   
		protected boolean mousePressed = false;   
		public CheckBoxHeaderRenderer(CheckBoxHeaderListener itemListener) {   
			rendererComponent = this;   
			rendererComponent.addItemListener(itemListener);  
			itemListener.setRenderer(this);
		}   
		public Component getTableCellRendererComponent(	JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {  
			if (table != null) {   
				JTableHeader header = table.getTableHeader();   
				if (header != null) {   
					header.addMouseListener(rendererComponent);   
				}   
			}   
			setColumn(column);   
			rendererComponent.setText("");   
			rendererComponent.setBackground(new Color(219,219,219));   
			setBorder(UIManager.getBorder("TableHeader.cellBorder"));   
			return rendererComponent;   
		}   
		protected void setColumn(int column) {   
			this.column = column;   
		}   
		public int getColumn() {   
			return column;   
		}   
		protected void handleClickEvent(MouseEvent e) {   
			if (mousePressed) {   
				mousePressed=false;   
				JTableHeader header = (JTableHeader)(e.getSource());   
				JTable tableView = header.getTable();   
				TableColumnModel columnModel = tableView.getColumnModel();   
				int viewColumn = columnModel.getColumnIndexAtX(e.getX());   
				int column = tableView.convertColumnIndexToModel(viewColumn);   
				if (viewColumn == this.column && e.getClickCount() == 1 && column != -1) {   
					doClick();   
				}   
			}   
		}   
		public void mouseClicked(MouseEvent e) {   
			handleClickEvent(e);   
			((JTableHeader)e.getSource()).repaint();   
		}   
		public void mousePressed(MouseEvent e) {   
			mousePressed = true;   
		}   
		public void mouseReleased(MouseEvent e) {   
		}   
		public void mouseEntered(MouseEvent e) {   
		}   
		public void mouseExited(MouseEvent e) {   
		}   
	}  

	class CheckBoxRenderer extends JCheckBox implements TableCellRenderer {
		private static final long serialVersionUID = 1L;
		CheckBoxRenderer() {
			setHorizontalAlignment(JLabel.CENTER);
		}
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			if (isSelected) {
				setForeground(table.getSelectionForeground());
				setBackground(table.getSelectionBackground());
			} else {
				setForeground(table.getForeground());
				setBackground(table.getBackground());
			}
			setSelected((value != null && ((Boolean)value).booleanValue()));
			return this;
		}
	}

	class TableModelScanResult extends DefaultTableModel {
		private static final long serialVersionUID = 1L;
		public boolean isCellEditable( int row, int col) {
			if (col != 1) {return false;} else {return true;}
		}
	}

	class TableRowNumber extends Object {
		private Object pojoElement_;
		private int number_;
		private String attributeType_;
		public TableRowNumber(int num, Object elm, String attr) {
			number_ = num;
			pojoElement_ = elm;
			attributeType_ = attr;
		}
		public String toString() {
			return Integer.toString(number_);
		}
		public Object getElement() {
			return pojoElement_;
		}
		public String getAttributeType() {
			return attributeType_;
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

	class XeadNode implements Comparable {
		private String nodeType_;
		private Object pojoNode_;
		public XeadNode(String type, Object node) {
			super();
			nodeType_ = type;
			pojoNode_ = node;
		}
		public String toString() {
			String str = "";
			str = this.getName();
			return str;
		}
		public String getName() {
			String str = "";
			if (nodeType_.equals("Role")) {
				xeadModeler.model.json.Role role = (xeadModeler.model.json.Role)pojoNode_;
				str = role.getSortKey() + " " + role.getName();
			}
			if (nodeType_.equals("Subsystem")) {
				xeadModeler.model.json.Subsystem subsystem = (xeadModeler.model.json.Subsystem)pojoNode_;
				str = subsystem.getSortKey() + " " + subsystem.getName();
			}
			return str;
		}
		public Object getElement() {
			return pojoNode_;
		}
        public int compareTo(Object other) {
            XeadNode otherNode = (XeadNode)other;
            String thisSortKey = getSortKey(pojoNode_);
            String otherSortKey = getSortKey(otherNode.getElement());
            return thisSortKey.compareTo(otherSortKey);
        }
	}
}

class DialogScan_jTextField_focusListener implements FocusListener{
	public void focusLost(FocusEvent e){
	}
	public void focusGained(FocusEvent e){
		JTextField field = (JTextField)e.getComponent();
		field.selectAll();
	}
}

class DialogScan_jCheckBoxAll_actionAdapter implements java.awt.event.ActionListener {
	DialogScan adaptee;

	DialogScan_jCheckBoxAll_actionAdapter(DialogScan adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.jCheckBoxAll_actionPerformed(e);
	}
}

class DialogScan_jButtonStartScan_actionAdapter implements java.awt.event.ActionListener {
	DialogScan adaptee;

	DialogScan_jButtonStartScan_actionAdapter(DialogScan adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.jButtonStartScan_actionPerformed(e);
	}
}

class DialogScan_jButtonCloseDialog_actionAdapter implements java.awt.event.ActionListener {
	DialogScan adaptee;

	DialogScan_jButtonCloseDialog_actionAdapter(DialogScan adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.jButtonCloseDialog_actionPerformed(e);
	}
}

class DialogScan_jButtonReplaceAllSelected_actionAdapter implements java.awt.event.ActionListener {
	DialogScan adaptee;

	DialogScan_jButtonReplaceAllSelected_actionAdapter(DialogScan adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.jButtonReplaceAllSelected_actionPerformed(e);
	}
}

class DialogScan_jButtonGenerateListData_actionAdapter implements java.awt.event.ActionListener {
	DialogScan adaptee;

	DialogScan_jButtonGenerateListData_actionAdapter(DialogScan adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.jButtonGenerateListData_actionPerformed(e);
	}
}

class DialogScan_jTextFieldScan_keyAdapter extends java.awt.event.KeyAdapter {
	DialogScan adaptee;

	DialogScan_jTextFieldScan_keyAdapter(DialogScan adaptee) {
		this.adaptee = adaptee;
	}
	public void keyReleased(KeyEvent e) {
		adaptee.jTextFieldScan_keyReleased(e);
	}
}

class DialogScan_jTextFieldReplace_keyAdapter extends java.awt.event.KeyAdapter {
	DialogScan adaptee;

	DialogScan_jTextFieldReplace_keyAdapter(DialogScan adaptee) {
		this.adaptee = adaptee;
	}
	public void keyReleased(KeyEvent e) {
		adaptee.jTextFieldReplace_keyReleased(e);
	}
}

class DialogScan_jCheckBoxRole_changeAdapter  implements ChangeListener {
	DialogScan adaptee;
	DialogScan_jCheckBoxRole_changeAdapter(DialogScan adaptee) {
		this.adaptee = adaptee;
	}
	public void stateChanged(ChangeEvent e) {
		adaptee.jCheckBoxTaskAndRole_stateChanged(e);
	}
}

class DialogScan_jCheckBoxTask_changeAdapter  implements ChangeListener {
	DialogScan adaptee;
	DialogScan_jCheckBoxTask_changeAdapter(DialogScan adaptee) {
		this.adaptee = adaptee;
	}
	public void stateChanged(ChangeEvent e) {
		adaptee.jCheckBoxTaskAndRole_stateChanged(e);
	}
}

class DialogScan_jCheckBoxSubsystem_changeAdapter  implements ChangeListener {
	DialogScan adaptee;
	DialogScan_jCheckBoxSubsystem_changeAdapter(DialogScan adaptee) {
		this.adaptee = adaptee;
	}
	public void stateChanged(ChangeEvent e) {
		adaptee.jCheckBoxSubsystem_stateChanged(e);
	}
}

class DialogScan_jCheckBoxTable_changeAdapter  implements ChangeListener {
	DialogScan adaptee;
	DialogScan_jCheckBoxTable_changeAdapter(DialogScan adaptee) {
		this.adaptee = adaptee;
	}
	public void stateChanged(ChangeEvent e) {
		adaptee.jCheckBoxSubsystem_stateChanged(e);
	}
}

class DialogScan_jCheckBoxFunction_changeAdapter  implements ChangeListener {
	DialogScan adaptee;
	DialogScan_jCheckBoxFunction_changeAdapter(DialogScan adaptee) {
		this.adaptee = adaptee;
	}
	public void stateChanged(ChangeEvent e) {
		adaptee.jCheckBoxSubsystem_stateChanged(e);
	}
}

class DialogScan_jTableScanResult_listSelectionAdapter  implements ListSelectionListener {
	DialogScan adaptee;
	DialogScan_jTableScanResult_listSelectionAdapter(DialogScan adaptee) {
		this.adaptee = adaptee;
	}
	public void valueChanged(ListSelectionEvent e) {
		adaptee.jTableScanResult_valueChanged(e);
	}
}

class DialogScan_jTableScanResult_mouseAdapter extends java.awt.event.MouseAdapter {
	DialogScan adaptee;
	DialogScan_jTableScanResult_mouseAdapter(DialogScan adaptee) {
		this.adaptee = adaptee;
	}
	public void mouseClicked(MouseEvent e) {
		adaptee.jTableScanResult_mouseClicked(e);
	}
}
