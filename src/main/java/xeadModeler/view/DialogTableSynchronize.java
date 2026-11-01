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
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.*;

import xeadModeler.Modeler;
import xeadModeler.Modeler.XeadTreeNode;
import xeadModeler.model.json.*;

public class DialogTableSynchronize extends JDialog {
	private static final long serialVersionUID = 1L;
	private static ResourceBundle res = ResourceBundle.getBundle("xeadModeler.Res");
	private JButton jButtonClose = new JButton();
	private JButton jButtonImport = new JButton();
	private JScrollPane jScrollPaneMessage = new JScrollPane();
	private JTextArea jTextAreaMessage = new JTextArea();
	private JPanel jPanelButtons = new JPanel();
	private boolean isSynchError_ = false;
	private boolean isImported_ = false;
	private String errorMessage_ = "";
	private XeadSystem synchFileXeadSystem_ = null;
	private Table synchFileTable_ = null;
	private Table targetTable_ = null;
	private int countOfErrors = 0;
	private Modeler frame_;

	public DialogTableSynchronize(Modeler frame, String title, boolean modal) {
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

	public DialogTableSynchronize(Modeler frame) {
		this(frame, "", true);
	}

	private void jbInit() throws Exception {
		jScrollPaneMessage.setBorder(BorderFactory.createEtchedBorder());
		this.getContentPane().setLayout(new BorderLayout());

		jTextAreaMessage.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jTextAreaMessage.setEditable(false);
		jTextAreaMessage.setOpaque(false);
		jTextAreaMessage.setLineWrap(true);
		jTextAreaMessage.setTabSize(4);
		jScrollPaneMessage.getViewport().add(jTextAreaMessage);

		jButtonClose.setText(res.getString("DialogTableSynchronize02"));
		jButtonClose.setBounds(new Rectangle(20, 7, 150, 29));
		jButtonClose.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jButtonClose.addActionListener(e -> jButtonClose_actionPerformed(e));
		jButtonImport.setText(res.getString("DialogTableSynchronize03"));
		jButtonImport.setBounds(new Rectangle(455, 7, 220, 29));
		jButtonImport.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jButtonImport.addActionListener(e -> jButtonImport_actionPerformed(e));
		jPanelButtons.setBorder(BorderFactory.createEtchedBorder());
		jPanelButtons.setPreferredSize(new Dimension(100, 43));
		jPanelButtons.setLayout(null);
		jPanelButtons.add(jButtonClose, null);
		jPanelButtons.add(jButtonImport, null);

		this.setTitle(res.getString("DialogTableSynchronize01"));
		this.getContentPane().add(jPanelButtons,  BorderLayout.SOUTH);
		this.setResizable(false);
		this.setPreferredSize(new Dimension(700, 400));
		this.getContentPane().add(jScrollPaneMessage,  BorderLayout.CENTER);
	}

	public boolean request(XeadTreeNode tableNode) {
		isSynchError_ = tableNode.isError();
		isImported_ = false;

		if (tableNode.getType().equals("Table")) {
			try {
				setCursor(new Cursor(Cursor.WAIT_CURSOR));

				isSynchError_ = isSynchError(tableNode, null);
				if (isSynchError_) {
					jTextAreaMessage.setText(errorMessage_);
				} else {
					jTextAreaMessage.setText(res.getString("DialogTableSynchronize05"));
				}
				jTextAreaMessage.setCaretPosition(0);
				jPanelButtons.getRootPane().setDefaultButton(jButtonClose);
				Dimension dlgSize = this.getPreferredSize();
				Dimension frmSize = frame_.getSize();
				Point loc = frame_.getLocation();
				this.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
			} finally {
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			super.setVisible(true);
		}

		return isSynchError_;
	}

	public boolean isSynchError(XeadTreeNode tableNode, XeadSystem synchFileComparedTo) {
		isSynchError_ = false;
		errorMessage_ = "";

		jButtonImport.setEnabled(false);

		synchFileTable_ = null;
		targetTable_ = (Table) tableNode.getJsonNode();
		if (targetTable_ == null) {
			return false;
		}

		String synchFile = targetTable_.getSynchFile();
		if (synchFile == null || synchFile.equals("")) {
			return false;
		}

		if (targetTable_.getSortKey() == null || targetTable_.getSortKey().equals("")) {
			isSynchError_ = true;
			errorMessage_ = res.getString("DialogTableSynchronize06");
		} else {
			if (synchFileComparedTo == null) {
				synchFileXeadSystem_ = frame_.getSynchFileDocument(synchFile);
			} else {
				synchFileXeadSystem_ = synchFileComparedTo;
			}
			if (synchFileXeadSystem_ == null) {
				isSynchError_ = true;
				errorMessage_ = res.getString("DialogTableSynchronize07");
			} else {
				boolean isFound = false;
				List<Table> tableList = synchFileXeadSystem_.getTables();
				if (tableList != null) {
					for (Table table : tableList) {
						if (targetTable_.getSortKey().equals(table.getSortKey())) {
							isFound = true;
							synchFileTable_ = table;
							break;
						}
					}
				}
				if (isFound) {
					String synchFileOfSynchTable = synchFileTable_.getSynchFile();
					if (synchFileOfSynchTable == null || synchFileOfSynchTable.equals("")) {
						jButtonImport.setEnabled(true);
						isSynchError_ = compareDefinitions(targetTable_, synchFileTable_);
						errorMessage_ = errorMessage_ + "\n\n" + res.getString("DialogTableSynchronize04");
					} else {
						isSynchError_ = true;
						errorMessage_ = res.getString("DialogTableSynchronize25");
					}
				} else {
					isSynchError_ = true;
					errorMessage_ = res.getString("DialogTableSynchronize08");
				}
			}
		}

		return isSynchError_;
	}

	boolean compareDefinitions(Table targetTable, Table synchFileTable) {
		countOfErrors = 0;
		StringBuffer bf = new StringBuffer();

		if (!targetTable.getName().equals(synchFileTable.getName())) {
			countOfErrors++;
			bf.append("\n" + countOfErrors+ res.getString("DialogTableSynchronize09"));
		}
		if (!targetTable.getAlias().equals(synchFileTable.getAlias())) {
			countOfErrors++;
			bf.append("\n" + countOfErrors+ res.getString("DialogTableSynchronize30"));
		}
		if (isDifferentTableType(targetTable, synchFileTable)) {
			countOfErrors++;
			bf.append("\n" + countOfErrors+ res.getString("DialogTableSynchronize31"));
		}
		if (!targetTable.getDescriptions().equals(synchFileTable.getDescriptions())) {
			countOfErrors++;
			bf.append("\n" + countOfErrors+ res.getString("DialogTableSynchronize10"));
		}

		List<TableField> targetFieldList = targetTable.getTableFields();
		List<TableField> synchFileFieldList = synchFileTable.getTableFields();

		for (TableField targetField : targetFieldList) {
			boolean isFound = false;
			TableField synchFileField = null;
			for (TableField sf : synchFileFieldList) {
				if (targetField.getAlias().equals(sf.getAlias())) {
					isFound = true;
					synchFileField = sf;
					break;
				}
			}
			if (isFound) {
				if (!targetField.getName().equals(synchFileField.getName())) {
					countOfErrors++;
					bf.append("\n" + countOfErrors+ res.getString("DialogTableSynchronize11") + targetField.getAlias() + res.getString("DialogTableSynchronize12"));
				}
				if (isDifferentDataType(targetField, synchFileField)) {
					countOfErrors++;
					bf.append("\n" + countOfErrors+ res.getString("DialogTableSynchronize11") + targetField.getAlias() + res.getString("DialogTableSynchronize13"));
				}
				if (!targetField.getDescriptions().equals(synchFileField.getDescriptions())) {
					countOfErrors++;
					bf.append("\n" + countOfErrors+ res.getString("DialogTableSynchronize11") + targetField.getAlias() + res.getString("DialogTableSynchronize14"));
				}
				if (!targetField.getAttributeType().equals(synchFileField.getAttributeType())) {
					countOfErrors++;
					bf.append("\n" + countOfErrors+ res.getString("DialogTableSynchronize11") + targetField.getAlias() + res.getString("DialogTableSynchronize26"));
				}
				if (!targetField.getNotNull().equals(synchFileField.getNotNull())) {
					countOfErrors++;
					bf.append("\n" + countOfErrors+ res.getString("DialogTableSynchronize11") + targetField.getAlias() + res.getString("DialogTableSynchronize27"));
				}
				if (!targetField.getDefault().equals(synchFileField.getDefault())) {
					countOfErrors++;
					bf.append("\n" + countOfErrors+ res.getString("DialogTableSynchronize11") + targetField.getAlias() + res.getString("DialogTableSynchronize28"));
				}
				String targetNoUpdate = targetField.getNoUpdate();
				String synchNoUpdate = synchFileField.getNoUpdate();
				if (targetNoUpdate != null && !targetNoUpdate.equals("") && synchNoUpdate != null && !synchNoUpdate.equals("")
						&& !targetNoUpdate.equals(synchNoUpdate)) {
					countOfErrors++;
					bf.append("\n" + countOfErrors+ res.getString("DialogTableSynchronize11") + targetField.getAlias() + res.getString("DialogTableSynchronize29"));
				}
			} else {
				countOfErrors++;
				bf.append("\n" + countOfErrors+ res.getString("DialogTableSynchronize11") + targetField.getAlias() + res.getString("DialogTableSynchronize15"));
			}
		}

		for (TableField synchFileField : synchFileFieldList) {
			boolean isFound = false;
			for (TableField targetField : targetFieldList) {
				if (targetField.getAlias().equals(synchFileField.getAlias())) {
					isFound = true;
					break;
				}
			}
			if (!isFound) {
				countOfErrors++;
				bf.append("\n" + countOfErrors+ res.getString("DialogTableSynchronize16") + synchFileField.getAlias() + res.getString("DialogTableSynchronize17"));
			}
		}

		List<TableKey> targetKeyList = targetTable.getTableKeys();
		List<TableKey> synchFileKeyList = synchFileTable.getTableKeys();

		for (TableKey targetKey : targetKeyList) {
			if (targetKey.getType().equals("PK")) {
				for (TableKey synchFileKey : synchFileKeyList) {
					if (synchFileKey.getType().equals(targetKey.getType())) {
						if (!isEquivalentKey(targetKey, synchFileKey, targetFieldList, synchFileFieldList)) {
							countOfErrors++;
							bf.append("\n" + countOfErrors+ res.getString("DialogTableSynchronize18"));
						}
						break;
					}
				}
			} else {
				boolean isFound = false;
				for (TableKey synchFileKey : synchFileKeyList) {
					if (synchFileKey.getType().equals(targetKey.getType())) {
						if (isEquivalentKey(targetKey, synchFileKey, targetFieldList, synchFileFieldList)) {
							isFound = true;
							break;
						}
					}
				}
				if (!isFound) {
					countOfErrors++;
					bf.append("\n" + countOfErrors+ res.getString("DialogTableSynchronize20"));
				}
			}
		}

		for (TableKey synchFileKey : synchFileKeyList) {
			boolean isFound = false;
			if (!synchFileKey.getType().equals("PK")) {
				for (TableKey targetKey : targetKeyList) {
					if (targetKey.getType().equals(synchFileKey.getType())) {
						if (isEquivalentKey(targetKey, synchFileKey, targetFieldList, synchFileFieldList)) {
							isFound = true;
							break;
						}
					}
				}
				if (!isFound) {
					countOfErrors++;
					bf.append("\n" + countOfErrors+ res.getString("DialogTableSynchronize22"));
				}
			}
		}
		
		if (countOfErrors == 0) {
			return false;
		} else {
			errorMessage_ = res.getString("DialogTableSynchronize23") + countOfErrors + res.getString("DialogTableSynchronize24") + bf.toString();
			return true;
		}
	}

	boolean isDifferentTableType(Table targetTable, Table synchFileTable) {
		boolean isDifferent = false;

		TableType targetTableType = null;
		List<TableType> targetTableTypeList = frame_.getXeadDocumentModel().getXeadSystem().getTableTypes();
		for (TableType tt : targetTableTypeList) {
			if (tt.getId().equals(targetTable.getTableTypeId())) {
				targetTableType = tt;
			}
		}

		TableType synchFileTableType = null;
		List<TableType> synchFileTableTypeList = synchFileXeadSystem_.getTableTypes();
		for (TableType tt : synchFileTableTypeList) {
			if (tt.getId().equals(synchFileTable.getTableTypeId())) {
				synchFileTableType = tt;
			}
		}
		
		if (targetTableType != null && synchFileTableType != null) {
			if (!targetTableType.getSortKey().equals(synchFileTableType.getSortKey())
					|| !targetTableType.getName().equals(synchFileTableType.getName())) {
				isDifferent = true;
			}
		} else {
			isDifferent = true;
		}
		
		return isDifferent;
	}

	boolean isDifferentDataType(TableField targetField, TableField synchFileField) {
		boolean isDifferent = false;

		DataType targetDataType = null;
		List<DataType> targetDataTypeList = frame_.getXeadDocumentModel().getXeadSystem().getDataTypes();
		for (DataType dt : targetDataTypeList) {
			if (dt.getId().equals(targetField.getDataTypeId())) {
				targetDataType = dt;
			}
		}

		DataType synchFileDataType = null;
		List<DataType> synchFileDataTypeList = synchFileXeadSystem_.getDataTypes();
		for (DataType dt : synchFileDataTypeList) {
			if (dt.getId().equals(synchFileField.getDataTypeId())) {
				synchFileDataType = dt;
			}
		}
		
		if (targetDataType != null && synchFileDataType != null) {
			if (!targetDataType.getBasicType().equals(synchFileDataType.getBasicType())
					|| !targetDataType.getName().equals(synchFileDataType.getName())
					|| !targetDataType.getSqlExpression().equals(synchFileDataType.getSqlExpression())
					|| !targetDataType.getLength().equals(synchFileDataType.getLength())
					|| !targetDataType.getDecimal().equals(synchFileDataType.getDecimal())) {
				isDifferent = true;
			}
		} else {
			isDifferent = true;
		}
		
		return isDifferent;
	}

	boolean isEquivalentKey(TableKey targetKey, TableKey synchFileKey, List<TableField> targetFieldList, List<TableField> synchFileFieldList) {
		boolean isEquivalent = true;
		String targetKeyFieldAlias, synchFileKeyFieldAlias;
		boolean isFound;

		List<TableKeyField> targetKeyFieldList = targetKey.getTableKeyFields();
		List<TableKeyField> synchFileKeyFieldList = synchFileKey.getTableKeyFields();

		if (targetKeyFieldList.size() == synchFileKeyFieldList.size()) {
			for (TableKeyField targetKeyField : targetKeyFieldList) {
				isFound = false;

				targetKeyFieldAlias = "";
				for (TableField tf : targetFieldList) {
					if (targetKeyField.getFieldId().equals(tf.getId())) {
						targetKeyFieldAlias = tf.getAlias();
						break;
					}
				}

				synchFileKeyFieldAlias = "";
				for (TableKeyField synchFileKeyField : synchFileKeyFieldList) {
					for (TableField sf : synchFileFieldList) {
						if (synchFileKeyField.getFieldId().equals(sf.getId())) {
							synchFileKeyFieldAlias = sf.getAlias();
							break;
						}
					}
					if (targetKeyFieldAlias.equals(synchFileKeyFieldAlias)) {
						if (targetKeyField.getAscDesc().equals(synchFileKeyField.getAscDesc())) {
							isFound = true;
						}
						break;
					}
				}

				if (!isFound) {
					isEquivalent = false;
				}
			}
		} else {
			isEquivalent = false;
		}
		
		return isEquivalent;
	}

	void jButtonClose_actionPerformed(ActionEvent e) {
		super.setVisible(false);
	}

	void jButtonImport_actionPerformed(ActionEvent e) {
		try {
			setCursor(new Cursor(Cursor.WAIT_CURSOR));

			isImported_ = true;
			importIntoTarget(targetTable_, synchFileTable_);

			isSynchError_ = compareDefinitions(targetTable_, synchFileTable_);
			if (isSynchError_) {
				jTextAreaMessage.setText(errorMessage_ + "\n\n" + res.getString("DialogTableSynchronize04"));
			} else {
				jTextAreaMessage.setText(res.getString("DialogTableSynchronize05"));
			}
			jTextAreaMessage.setCaretPosition(0);
			jButtonImport.setEnabled(false);

		} finally {
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
	}

	void importIntoTarget(Table targetTable, Table synchFileTable) {
		targetTable.setName(synchFileTable.getName());
		targetTable.setAlias(synchFileTable.getAlias());
		targetTable.setDescriptions(synchFileTable.getDescriptions());
		if (isDifferentTableType(targetTable, synchFileTable)) {
			String tableTypeID = getEquivalentTableTypeIDFromTargetDocument(targetTable, synchFileTable.getTableTypeId());
			targetTable.setTableTypeId(tableTypeID);
		}

		String dataTypeID;
		List<TableField> targetFieldList = targetTable.getTableFields();
		List<TableField> synchFileFieldList = synchFileTable.getTableFields();
		for (TableField synchFileField : synchFileFieldList) {
			boolean isFound = false;
			TableField targetField = null;
			for (TableField tf : targetFieldList) {
				if (tf.getAlias().equals(synchFileField.getAlias())) {
					isFound = true;
					targetField = tf;
					break;
				}
			}
			if (isFound) {
				targetField.setName(synchFileField.getName());
				if (isDifferentDataType(targetField, synchFileField)) {
					dataTypeID = getEquivalentDataTypeIDFromTargetDocument(synchFileField.getDataTypeId());
					targetField.setDataTypeId(dataTypeID);
				}
				targetField.setDescriptions(synchFileField.getDescriptions());
				targetField.setAttributeType(synchFileField.getAttributeType());
				targetField.setNotNull(synchFileField.getNotNull());
				targetField.setDefault(synchFileField.getDefault());
				targetField.setNoUpdate(synchFileField.getNoUpdate());
				targetField.setSortKey(synchFileField.getSortKey());
			} else {
				TableField newField = new TableField();
				newField.setId(getNextIDOfNodeType("Table"));
				newField.setName(synchFileField.getName());
				newField.setAlias(synchFileField.getAlias());
				dataTypeID = getEquivalentDataTypeIDFromTargetDocument(synchFileField.getDataTypeId());
				newField.setDataTypeId(dataTypeID);
				newField.setDescriptions(synchFileField.getDescriptions());
				newField.setAttributeType(synchFileField.getAttributeType());
				newField.setNotNull(synchFileField.getNotNull());
				newField.setDefault(synchFileField.getDefault());
				newField.setNoUpdate(synchFileField.getNoUpdate());
				newField.setShowOnModel(synchFileField.getShowOnModel());
				newField.setSortKey(synchFileField.getSortKey());
				targetTable.getTableFields().add(newField);
			}
		}

		int biggestKeyID = 0;
		List<TableKey> targetKeyList = targetTable.getTableKeys();
		for (TableKey tk : targetKeyList) {
			int wrkInt = Integer.parseInt(tk.getId());
			if (wrkInt > biggestKeyID) {
				biggestKeyID = wrkInt;
			}
		}

		List<Relationship> targetRelationshipList = frame_.getXeadDocumentModel().getXeadSystem().getRelationships();
		List<TableKey> synchFileKeyList = synchFileTable.getTableKeys();
		for (TableKey synchFileKey : synchFileKeyList) {

			if (synchFileKey.getType().equals("PK")) {
				for (TableKey targetKey : targetKeyList) {
					if (targetKey.getType().equals(synchFileKey.getType())) {
						if (!isEquivalentKey(targetKey, synchFileKey, targetFieldList, synchFileFieldList)) {
							boolean hasRelationships = false;
							for (Relationship rel : targetRelationshipList) {
								if ((rel.getTable1Id().equals(targetTable.getId()) && rel.getTableKey1Id().equals(targetKey.getId()))
									|| (rel.getTable2Id().equals(targetTable.getId()) && rel.getTableKey2Id().equals(targetKey.getId()))) {
									hasRelationships = true;
									break;
								}
							}
							if (hasRelationships) {
								JOptionPane.showMessageDialog(this, res.getString("DialogTableSynchronize19"));
							} else {
								// Clear existing key fields
								targetKey.getTableKeyFields().clear();
								// Add new key fields from synch file
								for (TableKeyField synchFileKeyField : synchFileKey.getTableKeyFields()) {
									TableKeyField newKeyField = new TableKeyField();
									newKeyField.setFieldId(
											getEquivalentFieldIDFromTargetTable(synchFileKeyField.getFieldId(), synchFileTable, targetTable));
									newKeyField.setAscDesc(synchFileKeyField.getAscDesc());
									newKeyField.setSortKey(synchFileKeyField.getSortKey());
									targetKey.getTableKeyFields().add(newKeyField);
								}
							}
						}
						break;
					}
				}
			} else {
				boolean isFound = false;
				for (TableKey targetKey : targetKeyList) {
					if (targetKey.getType().equals(synchFileKey.getType())) {
						if (isEquivalentKey(targetKey, synchFileKey, targetFieldList, synchFileFieldList)) {
							isFound = true;
							break;
						}
					}
				}
				if (!isFound) {
					biggestKeyID++;
					TableKey newKey = new TableKey();
					newKey.setId(Integer.toString(biggestKeyID));
					newKey.setType(synchFileKey.getType());
					newKey.setSortKey(synchFileKey.getSortKey());
					targetTable.getTableKeys().add(newKey);
					for (TableKeyField synchFileKeyField : synchFileKey.getTableKeyFields()) {
						TableKeyField newKeyField = new TableKeyField();
						newKeyField.setFieldId(
								getEquivalentFieldIDFromTargetTable(synchFileKeyField.getFieldId(), synchFileTable, targetTable));
						newKeyField.setAscDesc(synchFileKeyField.getAscDesc());
						newKeyField.setSortKey(synchFileKeyField.getSortKey());
						newKey.getTableKeyFields().add(newKeyField);
					}
				}
			}
		}
	}

	private String getEquivalentTableTypeIDFromTargetDocument(Table targetTable, String synchFileTableTypeID) {
		TableType synchFileTableType = null;
		List<TableType> synchFileTableTypeList = synchFileXeadSystem_.getTableTypes();
		for (TableType tt : synchFileTableTypeList) {
			if (tt.getId().equals(synchFileTableTypeID)) {
				synchFileTableType = tt;
				break;
			}
		}

		String targetTableTypeID = "";
		List<TableType> targetTableTypeList = frame_.getXeadDocumentModel().getXeadSystem().getTableTypes();
		for (TableType tt : targetTableTypeList) {
			if (tt.getSortKey().equals(synchFileTableType.getSortKey())
					&& tt.getName().equals(synchFileTableType.getName())) {
				targetTableTypeID = tt.getId();
				break;
			}
		}
		if (targetTableTypeID.equals("")) {
			TableType newTableType = new TableType();
			targetTableTypeID = getNextIDOfNodeType("TableType");
			newTableType.setId(targetTableTypeID);
			newTableType.setName(synchFileTableType.getName());
			newTableType.setSortKey(synchFileTableType.getSortKey());
			newTableType.setDescriptions(synchFileTableType.getDescriptions());
			frame_.getXeadDocumentModel().getXeadSystem().getTableTypes().add(newTableType);
		}

		return targetTableTypeID;
	}

	private String getEquivalentDataTypeIDFromTargetDocument(String synchFileDataTypeID) {
		DataType synchFileDataType = null;
		List<DataType> synchFileDataTypeList = synchFileXeadSystem_.getDataTypes();
		for (DataType dt : synchFileDataTypeList) {
			if (dt.getId().equals(synchFileDataTypeID)) {
				synchFileDataType = dt;
				break;
			}
		}

		String targetDataTypeID = "";
		List<DataType> targetDataTypeList = frame_.getXeadDocumentModel().getXeadSystem().getDataTypes();
		for (DataType dt : targetDataTypeList) {
			if (dt.getBasicType().equals(synchFileDataType.getBasicType())
					&& dt.getName().equals(synchFileDataType.getName())
					&& dt.getSqlExpression().equals(synchFileDataType.getSqlExpression())
					&& dt.getLength().equals(synchFileDataType.getLength())
					&& dt.getDecimal().equals(synchFileDataType.getDecimal())) {
				targetDataTypeID = dt.getId();
				break;
			}
		}
		if (targetDataTypeID.equals("")) {
			DataType newDataType = new DataType();
			targetDataTypeID = getNextIDOfNodeType("DataType");
			newDataType.setId(targetDataTypeID);
			newDataType.setName(synchFileDataType.getName());
			newDataType.setSortKey(synchFileDataType.getSortKey());
			newDataType.setBasicType(synchFileDataType.getBasicType());
			newDataType.setSqlExpression(synchFileDataType.getSqlExpression());
			newDataType.setLength(synchFileDataType.getLength());
			newDataType.setDecimal(synchFileDataType.getDecimal());
			frame_.getXeadDocumentModel().getXeadSystem().getDataTypes().add(newDataType);
		}

		return targetDataTypeID;
	}

	private String getEquivalentFieldIDFromTargetTable(String synchFileFieldID, Table synchFileTable, Table targetTable) {
		TableField synchFileField = null;
		List<TableField> synchFileFieldList = synchFileTable.getTableFields();
		for (TableField tf : synchFileFieldList) {
			if (tf.getId().equals(synchFileFieldID)) {
				synchFileField = tf;
				break;
			}
		}

		String targetFieldID = "";
		List<TableField> targetFieldList = targetTable.getTableFields();
		for (TableField tf : targetFieldList) {
			if (tf.getAlias().equals(synchFileField.getAlias())) {
				targetFieldID = tf.getId();
				break;
			}
		}
		return targetFieldID;
	}

	private String getNextIDOfNodeType(String tagName) {
		String nextID = "1";
		XeadSystem xeadSystem = frame_.getXeadDocumentModel().getXeadSystem();
		
		int lastID = 0;
		switch (tagName) {
			case "Table":
				for (Table t : xeadSystem.getTables()) {
					int id = Integer.parseInt(t.getId());
					if (id > lastID) lastID = id;
				}
				break;
			case "TableType":
				for (TableType tt : xeadSystem.getTableTypes()) {
					int id = Integer.parseInt(tt.getId());
					if (id > lastID) lastID = id;
				}
				break;
			case "DataType":
				for (DataType dt : xeadSystem.getDataTypes()) {
					int id = Integer.parseInt(dt.getId());
					if (id > lastID) lastID = id;
				}
				break;
			default:
				break;
		}
		
		nextID = Integer.toString(lastID + 1);
		return nextID;
	}

	public boolean isImported() {
		return isImported_;
	}
}
