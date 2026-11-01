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

import java.awt.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import xeadModeler.Modeler;
import xeadModeler.Modeler_FocusListener;
import xeadModeler.model.json.DataType;
import xeadModeler.model.json.Subsystem;
import xeadModeler.model.json.Table;
import xeadModeler.model.json.TableField;

public class DialogReorganizeDataTypes extends JDialog {
	private static final long serialVersionUID = 1L;
	private static ResourceBundle res = ResourceBundle.getBundle("xeadModeler.Res");
	private Modeler frame_;
	private BorderLayout borderLayoutMain = new BorderLayout();
	private JPanel panelMain = new JPanel();
	private JSplitPane jSplitPane = new JSplitPane();
	private JPanel jPanelFilters = new JPanel();
	private JPanel jPanelFieldList = new JPanel();
	private JPanel jPanelSouth = new JPanel();
	private JPanel jPanelButtons = new JPanel();
	private JLabel jLabelFilter = new JLabel();
	private JButton jButtonListFields = new JButton();
	private JTextField jTextFieldFilter = new JTextField();
	private JLabel jLabel1 = new JLabel();
	private JTextField jTextFieldDataTypeFrom = new JTextField();
	private JLabel jLabel2 = new JLabel();
	private JTextField jTextFieldDataTypeInto = new JTextField();
	private String targetDataTypeID = "";
	private String targetDataTypeCommonAlias = "";
	private TableModelReadOnlyList tableModelDataTypeList = new TableModelReadOnlyList();
	private JTable jTableDataTypeList = new JTable(tableModelDataTypeList);
	private JScrollPane jScrollPaneDataTypeList = new JScrollPane();
	private TableModelFieldList tableModelFieldList = new TableModelFieldList();
	private JTable jTableFieldList = new JTable(tableModelFieldList);
	private JScrollPane jScrollPaneFieldList = new JScrollPane();
	private TableColumn column0, column1, column2, column3, column4, column5, column6;
	private DefaultTableCellRenderer rendererTableHeader = null;
	private CheckBoxHeaderRenderer checkBoxHeaderRenderer = null;
	private DefaultTableCellRenderer rendererAlignmentCenter = new DefaultTableCellRenderer();
	private DefaultTableCellRenderer rendererAlignmentRight = new DefaultTableCellRenderer();
	private DefaultTableCellRenderer rendererAlignmentLeft = new DefaultTableCellRenderer();
	private JButton jButtonListDataTypes = new JButton();
	private JButton jButtonTransfer = new JButton();
	private JButton jButtonCloseDialog = new JButton();
	private JButton jButtonDeleteDataType = new JButton();
	private SortableTableFieldListModel sortableTableFieldListModel = new SortableTableFieldListModel();
	private DataTypePopupMenu dataTypePopupMenu = new DataTypePopupMenu();
	private JMenuItem jMenuItemAddDataType = new JMenuItem();
	private boolean updated = false;

	public DialogReorganizeDataTypes(Modeler frame, String title, boolean modal) {
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

	public DialogReorganizeDataTypes(Modeler frame) {
		this(frame, "", true);
	}

	private void jbInit() throws Exception {

		//panelMain//
		panelMain.setLayout(borderLayoutMain);
		panelMain.setPreferredSize(new Dimension(950, 700));
		panelMain.setBorder(BorderFactory.createEtchedBorder());
		panelMain.add(jSplitPane, BorderLayout.CENTER);
		panelMain.add(jPanelSouth, BorderLayout.SOUTH);

		//Data Type List//
		jTableDataTypeList.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jTableDataTypeList.setBackground(SystemColor.control);
		jTableDataTypeList.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		jTableDataTypeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jTableDataTypeList.getSelectionModel().addListSelectionListener(new DialogReorganizeDataTypes_jTableDataTypeList_listSelectionAdapter(this));
		jTableDataTypeList.addFocusListener(new Modeler_FocusListener());
		jTableDataTypeList.setRowHeight(Modeler.TABLE_ROW_HEIGHT);
		tableModelDataTypeList.addColumn("No.");
		tableModelDataTypeList.addColumn(res.getString("S201"));
		tableModelDataTypeList.addColumn(res.getString("S289"));
		tableModelDataTypeList.addColumn(res.getString("S290"));
		tableModelDataTypeList.addColumn(res.getString("S291"));
		tableModelDataTypeList.addColumn(res.getString("S292"));
		tableModelDataTypeList.addColumn(res.getString("S293"));
		column0 = jTableDataTypeList.getColumnModel().getColumn(0);
		column1 = jTableDataTypeList.getColumnModel().getColumn(1);
		column2 = jTableDataTypeList.getColumnModel().getColumn(2);
		column3 = jTableDataTypeList.getColumnModel().getColumn(3);
		column4 = jTableDataTypeList.getColumnModel().getColumn(4);
		column5 = jTableDataTypeList.getColumnModel().getColumn(5);
		column6 = jTableDataTypeList.getColumnModel().getColumn(6);
		column0.setPreferredWidth(40);
		column1.setPreferredWidth(160);
		column2.setPreferredWidth(230);
		column3.setPreferredWidth(150);
		column4.setPreferredWidth(65);
		column5.setPreferredWidth(75);
		column6.setPreferredWidth(150);
		column0.setCellRenderer(rendererAlignmentCenter);
		column1.setCellRenderer(rendererAlignmentLeft);
		column2.setCellRenderer(rendererAlignmentLeft);
		column3.setCellRenderer(rendererAlignmentLeft);
		column4.setCellRenderer(rendererAlignmentRight);
		column5.setCellRenderer(rendererAlignmentRight);
		column6.setCellRenderer(rendererAlignmentLeft);
		jTableDataTypeList.getTableHeader().setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		rendererTableHeader = (DefaultTableCellRenderer)jTableDataTypeList.getTableHeader().getDefaultRenderer();
		rendererTableHeader.setHorizontalAlignment(2); //LEFT//
		jScrollPaneDataTypeList.getViewport().add(jTableDataTypeList, null);

		//jTableFieldList//
		jTableFieldList.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jTableFieldList.setBackground(SystemColor.control);
		jTableFieldList.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		jTableFieldList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jTableFieldList.setRowSelectionAllowed(true);
		jTableFieldList.setRowHeight(Modeler.TABLE_ROW_HEIGHT);
		jTableFieldList.addMouseListener(new DialogReorganizeDataTypes_jTableFieldList_mouseAdapter(this));
		jTableFieldList.getTableHeader().setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		tableModelFieldList.addColumn("No.");
		tableModelFieldList.addColumn("");
		tableModelFieldList.addColumn(res.getString("S297"));
		tableModelFieldList.addColumn(res.getString("S298"));
		tableModelFieldList.addColumn(res.getString("S299"));
		tableModelFieldList.addColumn(res.getString("S300"));
		column0 = jTableFieldList.getColumnModel().getColumn(0);
		column1 = jTableFieldList.getColumnModel().getColumn(1);
		column2 = jTableFieldList.getColumnModel().getColumn(2);
		column3 = jTableFieldList.getColumnModel().getColumn(3);
		column4 = jTableFieldList.getColumnModel().getColumn(4);
		column5 = jTableFieldList.getColumnModel().getColumn(5);
		column0.setPreferredWidth(40);
		column1.setPreferredWidth(30);
		column2.setPreferredWidth(130);
		column3.setPreferredWidth(150);
		column4.setPreferredWidth(300);
		column5.setPreferredWidth(250);
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
		rendererTableHeader = (DefaultTableCellRenderer)jTableFieldList.getTableHeader().getDefaultRenderer();
		rendererTableHeader.setHorizontalAlignment(2); //LEFT//
		jScrollPaneFieldList.getViewport().add(jTableFieldList, null);

		//jSplitPane and objects on it//
		jSplitPane.setBorder(null);
		jSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		jSplitPane.setDividerLocation(300);
		jSplitPane.add(jScrollPaneDataTypeList, JSplitPane.TOP);
		//jSplitPane.add(jScrollPaneFieldList, JSplitPane.BOTTOM);
		jSplitPane.add(jPanelFieldList, JSplitPane.BOTTOM);

		//jPanelFieldList and objects on it//
		jPanelFieldList.setBorder(BorderFactory.createEtchedBorder());
		jPanelFieldList.setBorder(null);
		jPanelFieldList.setLayout(new BorderLayout());
		//jPanelFieldList.setPreferredSize(new Dimension(800, 90));
		jPanelFilters.setBorder(BorderFactory.createEtchedBorder());
		jPanelFilters.setBorder(null);
		jPanelFilters.setLayout(null);
		jPanelFilters.setPreferredSize(new Dimension(800, 45));
		jLabelFilter.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jLabelFilter.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabelFilter.setText(res.getString("DialogReorganizeDataTypes07"));
		jLabelFilter.setBounds(new Rectangle(5, 13, 200, 20));
		jTextFieldFilter.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jTextFieldFilter.setText("");
		jTextFieldFilter.setBounds(new Rectangle(210, 10, 300, 25));
		jButtonListFields.setBounds(new Rectangle(510, 9, 75, 27));
		jButtonListFields.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jButtonListFields.setText("Scan");
		jButtonListFields.addActionListener(new DialogReorganizeDataTypes_jButtonListFields_actionAdapter(this));
		jPanelFilters.add(jLabelFilter);
		jPanelFilters.add(jTextFieldFilter);
		jPanelFilters.add(jButtonListFields);
		jPanelFieldList.add(jPanelFilters, BorderLayout.NORTH);
		jPanelFieldList.add(jScrollPaneFieldList, BorderLayout.CENTER);

		//jPanelSouth and objects on it//
		jPanelSouth.setBorder(BorderFactory.createEtchedBorder());
		jPanelSouth.setBorder(null);
		jPanelSouth.setLayout(null);
		jPanelSouth.setPreferredSize(new Dimension(800, 90));
		jLabel1.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel1.setText(res.getString("DialogReorganizeDataTypes02"));
		jLabel1.setBounds(new Rectangle(5, 13, 150, 20));
		jTextFieldDataTypeFrom.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jTextFieldDataTypeFrom.setText("");
		jTextFieldDataTypeFrom.setBounds(new Rectangle(160, 10, 240, 25));
		jTextFieldDataTypeFrom.setEditable(false);
		jLabel2.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel2.setText(res.getString("DialogReorganizeDataTypes03"));
		jLabel2.setBounds(new Rectangle(450, 13, 150, 20));
		jTextFieldDataTypeInto.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jTextFieldDataTypeInto.setText("");
		jTextFieldDataTypeInto.setBounds(new Rectangle(605, 10, 240, 25));
		jTextFieldDataTypeInto.setEditable(false);
		jButtonListDataTypes.setBounds(new Rectangle(845, 9, 70, 27));
		jButtonListDataTypes.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jButtonListDataTypes.setText("List");
		jButtonListDataTypes.addActionListener(new DialogReorganizeDataTypes_jButtonListDataTypes_actionAdapter(this));
		jButtonCloseDialog.setBounds(new Rectangle(30, 10, 120, 27));
		jButtonCloseDialog.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jButtonCloseDialog.setText(res.getString("DialogReorganizeDataTypes04"));
		jButtonCloseDialog.addActionListener(new DialogReorganizeDataTypes_jButtonCloseDialog_actionAdapter(this));
		jButtonTransfer.setBounds(new Rectangle(250, 10, 350, 27));
		jButtonTransfer.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jButtonTransfer.setText(res.getString("DialogReorganizeDataTypes05"));
		jButtonTransfer.addActionListener(new DialogReorganizeDataTypes_jButtonTransfer_actionAdapter(this));
		jButtonDeleteDataType.setBounds(new Rectangle(680, 10, 235, 27));
		jButtonDeleteDataType.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jButtonDeleteDataType.setText(res.getString("DialogReorganizeDataTypes06"));
		jButtonDeleteDataType.setEnabled(false);
		jButtonDeleteDataType.addActionListener(new DialogReorganizeDataTypes_jButtonDeleteDataType_actionAdapter(this));
		jPanelButtons.setBounds(new Rectangle(0, 45, 950, 47));
		jPanelButtons.setBorder(BorderFactory.createEtchedBorder());
		jPanelButtons.setLayout(null);
		jPanelButtons.add(jButtonCloseDialog, null);
		jPanelButtons.add(jButtonTransfer, null);
		jPanelButtons.add(jButtonDeleteDataType, null);
		jPanelSouth.add(jLabel1, null);
		jPanelSouth.add(jTextFieldDataTypeFrom, null);
		jPanelSouth.add(jLabel2, null);
		jPanelSouth.add(jTextFieldDataTypeInto, null);
		jPanelSouth.add(jPanelButtons, null);
		jPanelSouth.add(jButtonListDataTypes, null);

		//DialogReorganizeDataTypes//
		this.setTitle(res.getString("DialogReorganizeDataTypes01"));
		this.getContentPane().add(panelMain);
		this.getRootPane().setDefaultButton(jButtonListFields);
		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension dlgSize = this.getPreferredSize();
		this.setLocation((scrSize.width - dlgSize.width)/2 , (scrSize.height - dlgSize.height)/2);
		this.pack();
	}

	public boolean request() {
		try {
			setCursor(new Cursor(Cursor.WAIT_CURSOR));
			updated = false;
			jButtonTransfer.setEnabled(false);
			jButtonDeleteDataType.setEnabled(false);
			jTextFieldDataTypeInto.setText("");
			targetDataTypeID = "";
			targetDataTypeCommonAlias = "";

			// Collect all TableFields from all Tables
			sortableTableFieldListModel.removeAllElements();
			List<Table> tables = frame_.getXeadDocumentModel().getXeadSystem().getTables();
			if (tables != null) {
				for (Table table : tables) {
					List<TableField> fields = table.getTableFields();
					if (fields != null) {
						for (TableField field : fields) {
							sortableTableFieldListModel.addElement(field);
						}
					}
				}
			}
			sortableTableFieldListModel.sortElements();

			//Clear list rows//
			if (tableModelDataTypeList.getRowCount() > 0) {
				int rowCount = tableModelDataTypeList.getRowCount();
				for (int i = 0; i < rowCount; i++) {tableModelDataTypeList.removeRow(0);}
			}

			//Add rows to list//
			List<DataType> dataTypeList = frame_.getXeadDocumentModel().getXeadSystem().getDataTypes();
			dataTypePopupMenu.initializeMenuItem(dataTypeList);
			frame_.sortableDomElementListModel.removeAllElements();
			if (dataTypeList != null) {
				for (DataType dt : dataTypeList) {
					frame_.sortableDomElementListModel.addElementFromPojo(dt);
				}
			}
			frame_.sortableDomElementListModel.sortElements();
			for (int i = 0; i < frame_.sortableDomElementListModel.getSize(); i++) {
				DataType dataType = (DataType) frame_.sortableDomElementListModel.getElementAt(i).getPojo();
				Object[] Cell = new Object[7];
				Cell[0] =  new TableRowNumber(i+1, dataType);
				Cell[1] = dataType.getSortKey();
				Cell[2] = dataType.getName();
				Cell[3] = dataType.getBasicType();
				Integer length = new Integer(dataType.getLength());
				Cell[4] = length;
				Integer decimal = new Integer(dataType.getDecimal());
				Cell[5] = decimal;
				Cell[6] = dataType.getSqlExpression();
				tableModelDataTypeList.addRow(Cell);
			}
			if (jTableDataTypeList.getRowCount() > 0) {
				jTableDataTypeList.setRowSelectionInterval(0, 0);
			}
		} finally {
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		super.setVisible(true);
		return updated;
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

	void jTableFieldList_mouseClicked(MouseEvent e) {
		jButtonTransfer.setEnabled(false);
		if (!jTextFieldDataTypeInto.getText().equals("")) {
			for (int i = 0; i < tableModelFieldList.getRowCount(); i++) {
				if (((Boolean)tableModelFieldList.getValueAt(i, 1)).booleanValue()) {
					jButtonTransfer.setEnabled(true);
					break;
				}
			}
		}
	}

	void jButtonCloseDialog_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
	
	void jTableDataTypeList_valueChanged() {
		String wrkStr1, wrkStr2 = "";

		jButtonTransfer.setEnabled(false);
		jButtonDeleteDataType.setEnabled(false);

		if (jTableDataTypeList.getSelectedRow() >= 0) {
			TableRowNumber tableRowNumber = (TableRowNumber)tableModelDataTypeList.getValueAt(jTableDataTypeList.getSelectedRow(), 0);
			DataType dataType = tableRowNumber.getDataType();
			if (dataType.getDecimal().equals("") || dataType.getDecimal().equals("0")) {
				wrkStr1 = dataType.getLength();
			} else {
				wrkStr1 = dataType.getLength() + "." + dataType.getDecimal();
			}
			String zenkaku = Modeler.getZenkakuOfHankaku(wrkStr1);
			if (dataType.getName().contains(wrkStr1)
					|| dataType.getSortKey().contains(wrkStr1)
					|| dataType.getName().contains(zenkaku)) {
				wrkStr2 = dataType.getName();
			} else {
				wrkStr2 = dataType.getName() + "(" + wrkStr1 + ")";
			}
			if (wrkStr2.startsWith(dataType.getSortKey())) {
				jTextFieldDataTypeFrom.setText(wrkStr2);
			} else {
				jTextFieldDataTypeFrom.setText(dataType.getSortKey() + " " + wrkStr2);
			}

			if (tableModelFieldList.getRowCount() > 0) {
				int rowCount = tableModelFieldList.getRowCount();
				for (int j = 0; j < rowCount; j++) {tableModelFieldList.removeRow(0);}
			}

			Table table;
			TableField field;
			Subsystem subsystem;
			int m = 0;
			for (int j = 0; j < sortableTableFieldListModel.getSize(); j++) {
				field = sortableTableFieldListModel.getElementAt(j);
				if (field.getDataTypeId().equals(tableRowNumber.getDataType().getId())) {
					if (jTextFieldFilter.getText().equals("")
							|| field.getAlias().contains(jTextFieldFilter.getText())
							|| field.getName().contains(jTextFieldFilter.getText())) {
						Object[] Cell = new Object[6];
						m = m + 1;
						Cell[0] =  new TableRowNumber(m, field);
						Cell[1] = Boolean.FALSE;
						Cell[2] = field.getAlias();
						Cell[3] = field.getName();
						// Find the table containing this field
						table = findTableForField(field);
						if (table != null) {
							Cell[4] = table.getSortKey() + " " + table.getName();
							// Find the subsystem for this table
							subsystem = findSubsystemById(table.getSubsystemID());
							if (subsystem != null) {
								Cell[5] = subsystem.getName();
							} else {
								Cell[5] = "";
							}
						} else {
							Cell[4] = "";
							Cell[5] = "";
						}
						tableModelFieldList.addRow(Cell);
					}
				}
			}
			if (tableModelFieldList.getRowCount() == 0 && jTextFieldFilter.getText().equals("")) {
				jButtonDeleteDataType.setEnabled(true);
			}
		}
	}

	// Find the table that contains the given field
	private Table findTableForField(TableField field) {
		List<Table> tables = frame_.getXeadDocumentModel().getXeadSystem().getTables();
		if (tables != null) {
			for (Table table : tables) {
				List<TableField> fields = table.getTableFields();
				if (fields != null && fields.contains(field)) {
					return table;
				}
			}
		}
		return null;
	}

	// Find subsystem by ID
	private Subsystem findSubsystemById(String subsystemId) {
		List<Subsystem> subsystems = frame_.getXeadDocumentModel().getXeadSystem().getSubsystems();
		if (subsystems != null) {
			for (Subsystem subsystem : subsystems) {
				if (subsystem.getId().equals(subsystemId)) {
					return subsystem;
				}
			}
		}
		return null;
	}

	void jButtonTransfer_actionPerformed(ActionEvent e) {
		if (!targetDataTypeID.equals("")) {
			for (int i = 0; i < tableModelFieldList.getRowCount(); i++) {
				if (((Boolean)tableModelFieldList.getValueAt(i, 1)).booleanValue()) {
					TableRowNumber tableRowNumber = (TableRowNumber)tableModelFieldList.getValueAt(i, 0);
					TableField field = tableRowNumber.getTableField();
					field.setDataTypeId(targetDataTypeID);
					if (field.getAlias().equals("") && !targetDataTypeCommonAlias.equals("")) {
						field.setAlias(targetDataTypeCommonAlias);
					}
					updated = true;
				}
			}
			jTableDataTypeList_valueChanged();
		}
	}

	void jButtonListDataTypes_actionPerformed(ActionEvent e) {
		Component com = (Component)e.getSource();
		dataTypePopupMenu.show(com, 0, 0);
	}

	void jButtonDeleteDataType_actionPerformed(ActionEvent e) {
		int workInt = jTableDataTypeList.getSelectedRow();

		TableRowNumber tableRowNumber = (TableRowNumber)tableModelDataTypeList.getValueAt(workInt, 0);
		DataType dataType = tableRowNumber.getDataType();
		
		// Remove the DataType from the system
		List<DataType> dataTypes = frame_.getXeadDocumentModel().getXeadSystem().getDataTypes();
		if (dataTypes != null) {
			dataTypes.remove(dataType);
		}

		tableModelDataTypeList.removeRow(jTableDataTypeList.getSelectedRow());
		if (tableModelDataTypeList.getRowCount() > 0) {
			if (workInt > (tableModelDataTypeList.getRowCount()-1)) {
				jTableDataTypeList.setRowSelectionInterval(tableModelDataTypeList.getRowCount()-1, tableModelDataTypeList.getRowCount()-1);
			} else {
				jTableDataTypeList.setRowSelectionInterval(workInt, workInt);
			}
		} else {
			jTextFieldDataTypeFrom.setText("");
			jButtonTransfer.setEnabled(false);
			jButtonDeleteDataType.setEnabled(false);
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
					if (!jTextFieldDataTypeInto.getText().equals("")) {
						for (int i = 0; i < tableModelFieldList.getRowCount(); i++) {
							tableModelFieldList.setValueAt(Boolean.TRUE, i, 1);
						}
						jButtonTransfer.setEnabled(true);
					}
				} else {
					for (int i = 0; i < tableModelFieldList.getRowCount(); i++) {
						tableModelFieldList.setValueAt(Boolean.FALSE, i, 1);
					}
					jButtonTransfer.setEnabled(false);
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

	class TableModelReadOnlyList extends DefaultTableModel {
		private static final long serialVersionUID = 1L;
		public boolean isCellEditable(int row, int col) {return false;}
	}

	class TableModelFieldList extends DefaultTableModel {
		private static final long serialVersionUID = 1L;
		public boolean isCellEditable( int row, int col) {
			if (col != 1) {return false;} else {return true;}
		}
	}

	// TableRowNumber now wraps either DataType or TableField
	class TableRowNumber extends Object {
		private DataType dataType_;
		private TableField tableField_;
		private int number_;
		
		public TableRowNumber(int num, DataType dt) {
			number_ = num;
			dataType_ = dt;
			tableField_ = null;
		}
		
		public TableRowNumber(int num, TableField tf) {
			number_ = num;
			dataType_ = null;
			tableField_ = tf;
		}
		
		public String toString() {
			return Integer.toString(number_);
		}
		
		public DataType getDataType() {
			return dataType_;
		}
		
		public TableField getTableField() {
			return tableField_;
		}
	}

	class SortableXeadNodeComboBoxModel extends DefaultComboBoxModel {
		private static final long serialVersionUID = 1L;
		public void sortElements() {
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

	class XeadNode implements Comparable {
		private String nodeType_;
		private Subsystem subsystem_;
		public XeadNode(String type, Subsystem subsystem) {
			super();
			nodeType_ = type;
			subsystem_ = subsystem;
		}
		public String toString() {
			String str = "";
			str = this.getName();
			return str;
		}
		public String getName() {
			String str = "";
			if (nodeType_.equals("Subsystem")) {
				str = subsystem_.getSortKey() + " " + subsystem_.getName();
			}
			return str;
		}
		public Subsystem getSubsystem() {
			return subsystem_;
		}
        public int compareTo(Object other) {
            XeadNode otherNode = (XeadNode)other;
            return subsystem_.getSortKey().compareTo(otherNode.getSubsystem().getSortKey());
        }
	}

	// SortableTableFieldListModel replaces SortableDomElementFieldListModel
	class SortableTableFieldListModel extends DefaultListModel<TableField> {
		private static final long serialVersionUID = 1L;
		public void addElement(TableField field) {
			super.addElement(field);
		}
		public void sortElements() {
			ArrayList<TableField> list = new ArrayList<TableField>();
			for (int i = 0; i < this.getSize(); i++) {
				list.add((TableField)super.getElementAt(i));
			}
			this.removeAllElements();
			Collections.sort(list, (f1, f2) -> f1.getAlias().compareTo(f2.getAlias()));
			Iterator<TableField> it = list.iterator();
			while(it.hasNext()){
				super.addElement((TableField)it.next());
			}
		}
		public TableField getElementAt(int index) {
			return super.getElementAt(index);
		}
	}

	class DataTypePopupMenu extends JPopupMenu {
		private static final long serialVersionUID = 1L;
		public DataTypePopupMenu(){
			super();
		}
		public void initializeMenuItem(List<DataType> dataTypeList){
			DataType dataType;
			String sortKeyOfDataType = "";
			String first2BytesOfsortKey = "";
			JMenu currentMenu = null;
			this.removeAll();
			frame_.sortableDomElementListModel.removeAllElements();
			if (dataTypeList != null) {
				for (DataType dt : dataTypeList) {
					frame_.sortableDomElementListModel.addElementFromPojo(dt);
				}
			}
			frame_.sortableDomElementListModel.sortElements();
			for (int i = 0; i < frame_.sortableDomElementListModel.getSize(); i++) {
				dataType = (DataType) frame_.sortableDomElementListModel.getElementAt(i).getPojo();
				sortKeyOfDataType = dataType.getSortKey() + "  ";
				if (!first2BytesOfsortKey.equals(sortKeyOfDataType.substring(0,2)) || i==0) {
					first2BytesOfsortKey = sortKeyOfDataType.substring(0,2);
					currentMenu = new JMenu(first2BytesOfsortKey);
					this.add(currentMenu);
				}
				currentMenu.add(new DataTypeMenuItem(dataType));
			}
			this.add(jMenuItemAddDataType);
		}
	}

	class DataTypeMenuItem extends JMenuItem {
		private static final long serialVersionUID = 1L;
		String dataTypeID;
		DataType dataType;
		public DataTypeMenuItem(DataType dt){
			super();
			String wrkStr1 = "";
			String wrkStr2 = "";
			String zenkaku = "";
			dataType = dt;
			if (dataType.getDecimal().equals("") || dataType.getDecimal().equals("0")) {
				wrkStr1 = dataType.getLength();
			} else {
				wrkStr1 = dataType.getLength() + "." + dataType.getDecimal();
			}
			zenkaku = Modeler.getZenkakuOfHankaku(wrkStr1);
			if (dataType.getName().contains(wrkStr1)
					|| dataType.getSortKey().contains(wrkStr1)
					|| dataType.getName().contains(zenkaku)) {
				wrkStr2 = dataType.getName();
			} else {
				wrkStr2 = dataType.getName() + "(" + wrkStr1 + ")";
			}
			if (wrkStr2.startsWith(dataType.getSortKey())) {
				setText(wrkStr2);
			} else {
				setText(dataType.getSortKey() + " " + wrkStr2);
			}
			dataTypeID = dataType.getId();
			addActionListener(new dataTypeMenuItemActionListener());
		}

		class dataTypeMenuItemActionListener implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				jTextFieldDataTypeInto.setText(getText());
				targetDataTypeID = dataTypeID;

				targetDataTypeCommonAlias = "";
				// Collect all TableFields from all Tables
				List<TableField> allFields = new ArrayList<>();
				List<Table> tables = frame_.getXeadDocumentModel().getXeadSystem().getTables();
				if (tables != null) {
					for (Table table : tables) {
						List<TableField> fields = table.getTableFields();
						if (fields != null) {
							allFields.addAll(fields);
						}
					}
				}
				
				String fieldAlias = "";
				TableField field;
				boolean isAllTheSameAlias = true;
				for (int i = 0; i < allFields.size(); i++) {
					field = allFields.get(i);
					if (field.getDataTypeId().equals(targetDataTypeID)) {
						if (!field.getAlias().equals("")
								&& !fieldAlias.equals("")
								&& !field.getAlias().equals(fieldAlias)) {
							isAllTheSameAlias = false;
						}
						if (!field.getAlias().equals("")) {
							fieldAlias = field.getAlias();
						}
					}
				}
				if (isAllTheSameAlias) {
					targetDataTypeCommonAlias = fieldAlias;
				}
				
				jTableFieldList_mouseClicked(null);
			}
		}
	}
}

class DialogReorganizeDataTypes_jTableDataTypeList_listSelectionAdapter  implements ListSelectionListener {
	DialogReorganizeDataTypes adaptee;
	DialogReorganizeDataTypes_jTableDataTypeList_listSelectionAdapter(DialogReorganizeDataTypes adaptee) {
		this.adaptee = adaptee;
	}
	public void valueChanged(ListSelectionEvent e) {
		adaptee.jTableDataTypeList_valueChanged();
	}
}

class DialogReorganizeDataTypes_jButtonListFields_actionAdapter implements java.awt.event.ActionListener {
	DialogReorganizeDataTypes adaptee;
	DialogReorganizeDataTypes_jButtonListFields_actionAdapter(DialogReorganizeDataTypes adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.jTableDataTypeList_valueChanged();
	}
}

class DialogReorganizeDataTypes_jButtonTransfer_actionAdapter implements java.awt.event.ActionListener {
	DialogReorganizeDataTypes adaptee;
	DialogReorganizeDataTypes_jButtonTransfer_actionAdapter(DialogReorganizeDataTypes adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.jButtonTransfer_actionPerformed(e);
	}
}

class DialogReorganizeDataTypes_jButtonCloseDialog_actionAdapter implements java.awt.event.ActionListener {
	DialogReorganizeDataTypes adaptee;

	DialogReorganizeDataTypes_jButtonCloseDialog_actionAdapter(DialogReorganizeDataTypes adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.jButtonCloseDialog_actionPerformed(e);
	}
}

class DialogReorganizeDataTypes_jButtonDeleteDataType_actionAdapter implements java.awt.event.ActionListener {
	DialogReorganizeDataTypes adaptee;

	DialogReorganizeDataTypes_jButtonDeleteDataType_actionAdapter(DialogReorganizeDataTypes adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.jButtonDeleteDataType_actionPerformed(e);
	}
}

class DialogReorganizeDataTypes_jButtonListDataTypes_actionAdapter implements java.awt.event.ActionListener {
	DialogReorganizeDataTypes adaptee;

	DialogReorganizeDataTypes_jButtonListDataTypes_actionAdapter(DialogReorganizeDataTypes adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.jButtonListDataTypes_actionPerformed(e);
	}
}

class DialogReorganizeDataTypes_jTableFieldList_mouseAdapter extends java.awt.event.MouseAdapter {
	DialogReorganizeDataTypes adaptee;
	DialogReorganizeDataTypes_jTableFieldList_mouseAdapter(DialogReorganizeDataTypes adaptee) {
		this.adaptee = adaptee;
	}
	public void mouseClicked(MouseEvent e) {
		adaptee.jTableFieldList_mouseClicked(e);
	}
}
