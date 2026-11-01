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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import xeadModeler.Modeler;
import xeadModeler.Modeler.*;
import xeadModeler.KanjiTextField;
import xeadModeler.model.json.TableKey;
import xeadModeler.model.json.TableField;
import xeadModeler.model.json.TableKeyField;

public class DialogAddRelationshipOnDatamodel extends JDialog {
	private static final long serialVersionUID = 1L;
	private static ResourceBundle res = ResourceBundle.getBundle("xeadModeler.Res");
	private JPanel jPanelMain = new JPanel();
	private JPanel jPanelRadioButtons = new JPanel();
	private JLabel jLabelName = new JLabel();
	private KanjiTextField jTextFieldName = new KanjiTextField();
	private JLabel jLabelAlias = new JLabel();
	private JTextField jTextFieldAlias = new JTextField();
	private JRadioButton jRadioButtonPasteFields = new JRadioButton();
	private JRadioButton jRadioButtonReuseFields = new JRadioButton();
	private JButton jButtonOK = new JButton();
	private JButton jButtonCancel = new JButton();
	private JScrollPane jScrollPaneDescriptions = new JScrollPane();
	private JTextArea jTextAreaDescriptions = new JTextArea();
	private boolean buttonOKIsPressed;
	private Modeler frame_;
	private XeadTreeNode sourceTableNode_;
	private TableKey sourceKey_;
	private XeadTreeNode targetTableNode_;
	private ArrayList<String> keyFieldNameList = new ArrayList<String>();
	private ArrayList<String> keyFieldAliasList = new ArrayList<String>();
	private ArrayList<String> keyFieldDataTypeIDList = new ArrayList<String>();
	private ArrayList<String> keyFieldNameListChanged = new ArrayList<String>();
	private ArrayList<String> keyFieldAliasListChanged = new ArrayList<String>();

	public DialogAddRelationshipOnDatamodel(Modeler frame) {
		super(frame, res.getString("DialogAddRelationshipOnDatamodel1"), true);
		try {
			frame_ = frame;
			jbInit();
			pack();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	private void jbInit() throws Exception {
		this.setResizable(false);
		jPanelMain.setLayout(null);
		jPanelMain.setPreferredSize(new Dimension(500, 290));
		jPanelMain.setBorder(BorderFactory.createEtchedBorder());
		jTextAreaDescriptions.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jTextAreaDescriptions.setLineWrap(true);
		jTextAreaDescriptions.setText(res.getString("DialogAddRelationshipOnDatamodel2"));
		jTextAreaDescriptions.setEditable(false);
		jTextAreaDescriptions.setBackground(SystemColor.control);
		jScrollPaneDescriptions.getViewport().add(jTextAreaDescriptions, null);
		jScrollPaneDescriptions.setBounds(new Rectangle(10, 10, 480, 95));
		jPanelRadioButtons.setLayout(null);
		jPanelRadioButtons.setBorder(BorderFactory.createEtchedBorder());
		jPanelRadioButtons.setBounds(new Rectangle(10, 110, 481, 140));
		jRadioButtonPasteFields.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jRadioButtonPasteFields.setText(res.getString("DialogAddRelationshipOnDatamodel3"));
		jRadioButtonPasteFields.setBounds(new Rectangle(5, 9, 470, 25));
		jRadioButtonPasteFields.addChangeListener(new DialogAddRelationshipOnDatamodel_jRadioButton_changeAdapter(this));
		jRadioButtonReuseFields.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jRadioButtonReuseFields.setText(res.getString("DialogAddRelationshipOnDatamodel4"));
		jRadioButtonReuseFields.setBounds(new Rectangle(5, 101, 470, 25));
		jRadioButtonReuseFields.addChangeListener(new DialogAddRelationshipOnDatamodel_jRadioButton_changeAdapter(this));
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(jRadioButtonPasteFields);
		buttonGroup.add(jRadioButtonReuseFields);
		jLabelName.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jLabelName.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabelName.setText(res.getString("DialogAddRelationshipOnDatamodel5"));
		jLabelName.setBounds(new Rectangle(5, 38, 130, 22));
		jTextFieldName.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jTextFieldName.setBounds(new Rectangle(140, 37, 320, 25));
		jLabelAlias.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jLabelAlias.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabelAlias.setText(res.getString("DialogAddRelationshipOnDatamodel6"));
		jLabelAlias.setBounds(new Rectangle(5, 69, 130, 22));
		jTextFieldAlias.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jTextFieldAlias.setBounds(new Rectangle(140, 68, 320, 25));
		jPanelRadioButtons.add(jRadioButtonPasteFields);
		jPanelRadioButtons.add(jRadioButtonReuseFields);
		jPanelRadioButtons.add(jLabelName);
		jPanelRadioButtons.add(jTextFieldName);
		jPanelRadioButtons.add(jLabelAlias);
		jPanelRadioButtons.add(jTextFieldAlias);
		jButtonOK.setBounds(new Rectangle(40, 255, 100, 27));
		jButtonOK.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jButtonOK.setText("OK");
		jButtonOK.addActionListener(new DialogAddRelationshipOnDatamodel_jButtonOK_actionAdapter(this));
		jButtonCancel.setBounds(new Rectangle(340, 255, 100, 27));
		jButtonCancel.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jButtonCancel.setText(res.getString("DialogAddRelationshipOnDatamodel7"));
		jButtonCancel.addActionListener(new DialogAddRelationshipOnDatamodel_jButtonCancel_actionAdapter(this));
		jPanelMain.add(jScrollPaneDescriptions, null);
		jPanelMain.add(jPanelRadioButtons, null);
		jPanelMain.add(jButtonOK, null);
		jPanelMain.add(jButtonCancel, null);
		this.getContentPane().add(jPanelMain);
	}

	public boolean request(XeadTreeNode sourceTableNode, TableKey sourceKey, XeadTreeNode targetTableNode) {
		sourceTableNode_ = sourceTableNode;
		sourceKey_ = sourceKey;
		targetTableNode_ = targetTableNode;
		//
		buttonOKIsPressed = false;
		keyFieldDataTypeIDList.clear();
		keyFieldNameList.clear();
		keyFieldAliasList.clear();
		//
		// Retrieve key fields information of source key //
		// Get source table directly from JSON node
		xeadModeler.model.json.Table sourceTable = (xeadModeler.model.json.Table) sourceTableNode_.getJsonNode();
		
		if (sourceTable != null && sourceKey_.getTableKeyFields() != null) {
			// Sort key fields by sortKey
			java.util.List<TableKeyField> sortedKeyFields = new java.util.ArrayList<>(sourceKey_.getTableKeyFields());
			sortedKeyFields.sort((a, b) -> {
				String sortA = a.getSortKey() != null ? a.getSortKey() : "";
				String sortB = b.getSortKey() != null ? b.getSortKey() : "";
				return sortA.compareTo(sortB);
			});
			
			StringBuffer bfName = new StringBuffer();
			StringBuffer bfAlias = new StringBuffer();
			StringBuffer bfWork = new StringBuffer();
			
			for (int i = 0; i < sortedKeyFields.size(); i++) {
				if (i > 0) {
					bfName.append(";");
					bfAlias.append(";");
					bfWork.append(";");
				}
				TableKeyField keyField = sortedKeyFields.get(i);
				// Find the field in the source table
				if (sourceTable.getTableFields() != null) {
					for (TableField field : sourceTable.getTableFields()) {
						if (field.getId() != null && field.getId().equals(keyField.getFieldId())) {
							bfName.append(field.getName());
							bfAlias.append(field.getAlias());
							bfWork.append("");
							keyFieldDataTypeIDList.add(field.getDataTypeId());
							keyFieldNameList.add(field.getName());
							keyFieldAliasList.add(field.getAlias());
							break;
						}
					}
				}
			}
			jTextFieldName.setText(bfName.toString());
			if (bfAlias.toString().equals(bfWork.toString())) {
				jTextFieldAlias.setText("");
			} else {
				jTextFieldAlias.setText(bfAlias.toString());
			}
		}
		//
 	////////////////////////////////////////////////////////////////////////////
 	// Check if target table key status                                       //
 	// status 0: PK of target table contains source key                       //
 	// status 1: target table contains fields equivalent to source key fields //
 	// status 2: other than 0 nor 1                                           //
 	////////////////////////////////////////////////////////////////////////////
 	// JSON-based implementation
 	int statusOfTarget = 0;
	
 	// Get target table directly from JSON node
 	xeadModeler.model.json.Table targetTable = (xeadModeler.model.json.Table) targetTableNode_.getJsonNode();
	
 	if (targetTable != null) {
 		if (targetTable.getId().equals(sourceTable.getId())) {
 			statusOfTarget = 2;
 		} else {
 			// Check if target table has PK fields equivalent to source key fields
 			boolean hasNone = true;
 			if (targetTable.getTableKeys() != null) {
 				// Find the PK key
 				for (TableKey pkKey : targetTable.getTableKeys()) {
 					if ("PK".equals(pkKey.getType()) && pkKey.getTableKeyFields() != null) {
 						for (int i = 0; i < keyFieldDataTypeIDList.size(); i++) {
 							hasNone = true;
 							for (TableKeyField pkField : pkKey.getTableKeyFields()) {
 								// Find the field in the target table
 								if (targetTable.getTableFields() != null) {
 									for (TableField field : targetTable.getTableFields()) {
 										if (field.getId() != null && field.getId().equals(pkField.getFieldId())) {
 											if (field.getDataTypeId() != null && field.getDataTypeId().equals(keyFieldDataTypeIDList.get(i))) {
 												hasNone = false;
 												break;
 											}
 										}
 									}
 								}
 								if (!hasNone) break;
 							}
 							if (!hasNone) break;
 						}
 						break;
 					}
 				}
 			}
 			if (hasNone) {
 				statusOfTarget = 1;
 			}
 			//
 			if (statusOfTarget == 1) {
 				// Check if target table has any fields equivalent to source key fields
 				hasNone = true;
 				if (targetTable.getTableFields() != null) {
 					for (int i = 0; i < keyFieldDataTypeIDList.size(); i++) {
 						hasNone = true;
 						for (TableField field : targetTable.getTableFields()) {
 							if (field.getDataTypeId() != null && field.getDataTypeId().equals(keyFieldDataTypeIDList.get(i))) {
 								hasNone = false;
 								break;
 							}
 						}
 						if (!hasNone) break;
 					}
 				}
 				if (hasNone) {
 					statusOfTarget = 2;
 				}
 			}
 		}
 	}
		//
		jRadioButtonPasteFields.setSelected(true);
		if (statusOfTarget == 0 || statusOfTarget == 1) {
			jRadioButtonReuseFields.setEnabled(true);
		}
		if (statusOfTarget == 2) {
			jRadioButtonReuseFields.setEnabled(false);
		}
		//
		jPanelMain.getRootPane().setDefaultButton(jButtonOK);
		Dimension dlgSize = this.getPreferredSize();
		Dimension frmSize = frame_.getSize();
		Point loc = frame_.getLocation();
		this.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
		this.pack();
		this.setVisible(true);
		//
		return buttonOKIsPressed;
	}
	
	void jRadioButton_stateChanged(ChangeEvent e) {
		if (jRadioButtonPasteFields.isSelected()) {
			jTextFieldName.setEnabled(true);
			jTextFieldAlias.setEnabled(true);
		} else {
			jTextFieldName.setEnabled(false);
			jTextFieldAlias.setEnabled(false);
		}
	}

	void jButtonOK_actionPerformed(ActionEvent e) {
		if (jRadioButtonPasteFields.isSelected()) {
			keyFieldNameListChanged.clear();
			keyFieldAliasListChanged.clear();
			//
			StringTokenizer tokenizerName = new StringTokenizer(jTextFieldName.getText(), ";");
			while (tokenizerName.hasMoreTokens()) {
				keyFieldNameListChanged.add(tokenizerName.nextToken());
				if (jTextFieldAlias.getText().equals("")) {
					keyFieldAliasListChanged.add("");
				}
			}
			if (!jTextFieldAlias.getText().equals("")) {
				StringTokenizer tokenizerAlias = new StringTokenizer(jTextFieldAlias.getText(), ";");
				while (tokenizerAlias.hasMoreTokens()) {
					keyFieldAliasListChanged.add(tokenizerAlias.nextToken());
				}
			}
			//
			if (keyFieldNameListChanged.size() == keyFieldAliasListChanged.size()
					&& keyFieldNameListChanged.size() == keyFieldDataTypeIDList.size()) {
				//
				// JSON-based implementation
				boolean noError = true;
				// Get target table directly from JSON node
				xeadModeler.model.json.Table targetTable = (xeadModeler.model.json.Table) targetTableNode_.getJsonNode();
				
				if (targetTable != null && targetTable.getTableFields() != null) {
					for (TableField field : targetTable.getTableFields()) {
						for (int j = 0; j < keyFieldNameListChanged.size(); j++) {
							if (keyFieldNameListChanged.get(j).equals(field.getName())
									|| (keyFieldAliasListChanged.get(j).equals(field.getAlias()))
										&& field.getAlias() != null && !field.getAlias().equals("")) {
								noError = false;
								JOptionPane.showMessageDialog(this, res.getString("DialogAddRelationshipOnDatamode20"));
								break;
							}
						}
						if (!noError) {
							break;
						}
					}
				}
				//
				if (noError) {
					pasteKeyFieldsIntoTargetTable();
					buttonOKIsPressed = true;
					this.setVisible(false);
				}
			} else {
				JOptionPane.showMessageDialog(this, res.getString("DialogAddRelationshipOnDatamodel8"));
			}
		}
		//
		if (jRadioButtonReuseFields.isSelected()) {
			reuseCurrentFieldsAsForeignKey();
			buttonOKIsPressed = true;
			this.setVisible(false);
		}
	}
	
	public void pasteKeyFieldsIntoTargetTable() {
 	// JSON-based implementation
 	frame_.getCurrentMainTreenode().updateFields();
	
 	// Get target table directly from JSON node
 	xeadModeler.model.json.Table targetTable = (xeadModeler.model.json.Table) targetTableNode_.getJsonNode();
	
 	if (targetTable == null) {
 		return;
 	}
		
		// Get the last field ID
		int lastID = 0;
		if (targetTable.getTableFields() != null) {
			for (TableField field : targetTable.getTableFields()) {
				if (field.getId() != null) {
					int fieldID = Integer.parseInt(field.getId());
					if (fieldID > lastID) {
						lastID = fieldID;
					}
				}
			}
		}
		
		// Add new fields
		ArrayList<String> keyFieldIDList = new ArrayList<String>();
		for (int k = 0; k < keyFieldNameListChanged.size(); k++) {
			lastID++;
			String newFieldId = Integer.toString(lastID);
			
			TableField newField = new TableField();
			newField.setId(newFieldId);
			newField.setName(keyFieldNameListChanged.get(k));
			newField.setDataTypeId(keyFieldDataTypeIDList.get(k));
			newField.setAlias(keyFieldAliasListChanged.get(k));
			newField.setAttributeType("NATIVE");
			newField.setNotNull("true");
			newField.setDefault_("");
			newField.setDescriptions("");
			newField.setShowOnModel("true");
			newField.setSortKey(((XeadTreeNode)targetTableNode_.getChildAt(0)).getNextSortKeyOfTableField(targetTableNode_));
			
			keyFieldIDList.add(newFieldId);
			
			// Add to target table's fields
			if (targetTable.getTableFields() == null) {
				targetTable.setTableFields(new java.util.ArrayList<>());
			}
			targetTable.getTableFields().add(newField);
			
			// Add TreeNode of TableField
			XeadTreeNode newFieldNode = frame_.new XeadTreeNode("TableField", newField);
			((XeadTreeNode)targetTableNode_.getChildAt(0)).add(newFieldNode);
			
			// Add as a IOTable Field
			if (frame_.getSystemElement().getIotables() != null) {
				for (xeadModeler.model.json.IOTable ioTable : frame_.getSystemElement().getIotables()) {
					if (ioTable.getTableId() != null && ioTable.getTableId().equals(targetTable.getId())) {
						xeadModeler.model.json.IOTableField ioTableField = new xeadModeler.model.json.IOTableField();
						ioTableField.setFieldId(newFieldId);
						ioTableField.setDescriptions("");
						if (ioTable.getIotableFields() == null) {
							ioTable.setIotableFields(new java.util.ArrayList<>());
						}
						ioTable.getIotableFields().add(ioTableField);
					}
				}
			}
			
			// Add Log of Add
			frame_.getXeadUndoManager().addLogOfAdd(newFieldNode);
		}
		
		// Add node of Foreign Key
		lastID = 0;
		int lastSortKey = 0;
		if (targetTable.getTableKeys() != null) {
			for (TableKey key : targetTable.getTableKeys()) {
				if (key.getId() != null) {
					int elementID = Integer.parseInt(key.getId());
					if (elementID > lastID) {
						lastID = elementID;
					}
				}
				if (key.getSortKey() != null && !key.getSortKey().equals("")) {
					try {
						if (lastSortKey < Integer.parseInt(key.getSortKey())) {
							lastSortKey = Integer.parseInt(key.getSortKey());
						}
					} catch (NumberFormatException e) {
						// Ignore invalid sort keys
					}
				}
			}
		}
		
		// Create new FK key
		TableKey newKey = new TableKey();
		String newKeyId = Integer.toString(lastID + 1);
		newKey.setId(newKeyId);
		newKey.setType("FK");
		newKey.setSortKey("010");
		
		// Add key fields
		for (int k = 0; k < keyFieldIDList.size(); k++) {
			TableKeyField keyField = new TableKeyField();
			keyField.setFieldId(keyFieldIDList.get(k));
			keyField.setSortKey(Modeler.getFormatted4ByteString(lastSortKey + 10));
			newKey.getTableKeyFields().add(keyField);
		}
		
		// Add to target table's keys
		if (targetTable.getTableKeys() == null) {
			targetTable.setTableKeys(new java.util.ArrayList<>());
		}
		targetTable.getTableKeys().add(newKey);
		
		// Add TreeNode
		XeadTreeNode newTableKeyNode = frame_.new XeadTreeNode("TableKey", newKey);
		((XeadTreeNode)targetTableNode_.getChildAt(1)).add(newTableKeyNode);
		frame_.getXeadUndoManager().addLogOfAdd(newTableKeyNode);
		
		// Add a relationship
		frame_.getXeadUndoManager().saveNodeBeforeModified(newTableKeyNode);
		xeadModeler.model.json.Relationship relationship = new xeadModeler.model.json.Relationship();
		relationship.setId(Integer.toString(frame_.incrementLastIDOfRelationship()));
		xeadModeler.model.json.Table sourceTable = (xeadModeler.model.json.Table) sourceTableNode_.getJsonNode();
		relationship.setTable1Id(sourceTable.getId());
		relationship.setTableKey1Id(sourceKey_.getId());
		relationship.setTable2Id(targetTable.getId());
		relationship.setTableKey2Id(newKeyId);
		relationship.setType("REFFER");
		relationship.setExistWhen1("");
		relationship.setExistWhen2("");
		frame_.getSystemElement().getRelationships().add(relationship);
		frame_.getXeadUndoManager().addLogAfterModified(newTableKeyNode);
		
		// Add Relationship attributes to each Subsystem if necessary
		if (frame_.getSystemElement().getSubsystems() != null) {
			for (xeadModeler.model.json.Subsystem subsystem : frame_.getSystemElement().getSubsystems()) {
				frame_.createSubsystemAttributesForRelationship(relationship, subsystem);
			}
		}
		
		frame_.getXeadUndoManager().saveNodeBeforeModified(frame_.getCurrentMainTreenode());
	}

	public void reuseCurrentFieldsAsForeignKey() {
		// JSON-based implementation
		int lastID = 0;
		int wrkCnt;
		boolean eachFieldSpecified;
		ArrayList<String> keyFieldIDList = new ArrayList<String>();
		XeadTreeNode availableTableKeyNode = null;
		
 	frame_.getCurrentMainTreenode().updateFields();
	
 	// Get target table directly from JSON node
 	xeadModeler.model.json.Table targetTable = (xeadModeler.model.json.Table) targetTableNode_.getJsonNode();
	
 	if (targetTable == null) {
 		JOptionPane.showMessageDialog(this, res.getString("DialogAddRelationshipOnDatamodel9"));
 		return;
 	}
		
		// Search and specify corresponding field
		keyFieldIDList.clear();
		eachFieldSpecified = true;
		if (targetTable.getTableFields() != null) {
			for (int i = 0; i < keyFieldDataTypeIDList.size(); i++) {
				wrkCnt = 0;
				for (TableField field : targetTable.getTableFields()) {
					if (field.getDataTypeId() != null && field.getDataTypeId().equals(keyFieldDataTypeIDList.get(i))) {
						wrkCnt++;
						keyFieldIDList.add(field.getId());
					}
				}
				if (wrkCnt != 1) {
					eachFieldSpecified = false;
				}
			}
		}
		//
		if (!eachFieldSpecified) {
			keyFieldIDList.clear();
			eachFieldSpecified = true;
			if (targetTable.getTableFields() != null) {
				for (int i = 0; i < keyFieldDataTypeIDList.size(); i++) {
					wrkCnt = 0;
					for (TableField field : targetTable.getTableFields()) {
						if (field.getDataTypeId() != null && field.getDataTypeId().equals(keyFieldDataTypeIDList.get(i))
								&& (field.getName() != null && field.getName().equals(keyFieldNameList.get(i))
									|| field.getAlias() != null && field.getAlias().equals(keyFieldAliasList.get(i)))) {
							wrkCnt++;
							keyFieldIDList.add(field.getId());
						}
					}
					if (wrkCnt != 1) {
						eachFieldSpecified = false;
					}
				}
			}
		}
		//
		if (!eachFieldSpecified) {
			keyFieldIDList.clear();
			eachFieldSpecified = true;
			if (targetTable.getTableFields() != null) {
				for (int i = 0; i < keyFieldDataTypeIDList.size(); i++) {
					wrkCnt = 0;
					for (TableField field : targetTable.getTableFields()) {
						if (field.getDataTypeId() != null && field.getDataTypeId().equals(keyFieldDataTypeIDList.get(i))
								&& field.getName() != null && field.getName().equals(keyFieldNameList.get(i))
								&& field.getAlias() != null && field.getAlias().equals(keyFieldAliasList.get(i))) {
							wrkCnt++;
							keyFieldIDList.add(field.getId());
						}
					}
					if (wrkCnt != 1) {
						eachFieldSpecified = false;
					}
				}
			}
		}
		//
		if (eachFieldSpecified) {
			//
			// Search if key definition is available
			availableTableKeyNode = null;
			String relationshipType = "";
			if (targetTable.getTableKeys() != null) {
				for (TableKey key : targetTable.getTableKeys()) {
					wrkCnt = 0;
					if (key.getTableKeyFields() != null && key.getTableKeyFields().size() >= keyFieldIDList.size()) {
						for (TableKeyField keyField : key.getTableKeyFields()) {
							if (keyFieldIDList.contains(keyField.getFieldId())) {
								wrkCnt++;
							}
						}
						if (wrkCnt == keyFieldIDList.size()) {
							if (key.getTableKeyFields().size() > keyFieldIDList.size()) {
								if ("PK".equals(key.getType()) || "SK".equals(key.getType())) {
									relationshipType = "FAMILY";
								}
							}
							if (key.getTableKeyFields().size() == keyFieldIDList.size()) {
								if ("FK".equals(key.getType())) {
									relationshipType = "REFFER";
								}
								if ("PK".equals(key.getType()) || "SK".equals(key.getType())) {
									relationshipType = "SUBTYPE";
								}
							}
 						if (!relationshipType.equals("")) {
 							availableTableKeyNode = frame_.getSpecificXeadTreeNode("TableKey", targetTable.getId(), key.getId());
 							break;
 						}
						}
					}
				}
			}
			//
			// Add node of Foreign Key if without available key
			if (availableTableKeyNode == null) {
				lastID = 0;
				int lastSortKey = 0;
				relationshipType = "REFFER";
				if (targetTable.getTableKeys() != null) {
					for (TableKey key : targetTable.getTableKeys()) {
						if (key.getId() != null) {
							int elementID = Integer.parseInt(key.getId());
							if (elementID > lastID) {
								lastID = elementID;
							}
						}
						if (key.getSortKey() != null && !key.getSortKey().equals("")) {
							try {
								if (lastSortKey < Integer.parseInt(key.getSortKey())) {
									lastSortKey = Integer.parseInt(key.getSortKey());
								}
							} catch (NumberFormatException e) {
								// Ignore invalid sort keys
							}
						}
					}
				}
				
				// Create new FK key
				TableKey newKey = new TableKey();
				String newKeyId = Integer.toString(lastID + 1);
				newKey.setId(newKeyId);
				newKey.setType("FK");
				newKey.setSortKey("010");
				
				// Add key fields
				for (int k = 0; k < keyFieldIDList.size(); k++) {
					TableKeyField keyField = new TableKeyField();
					keyField.setFieldId(keyFieldIDList.get(k));
					keyField.setSortKey(Modeler.getFormatted4ByteString(lastSortKey + 10));
					newKey.getTableKeyFields().add(keyField);
				}
				
				// Add to target table's keys
				if (targetTable.getTableKeys() == null) {
					targetTable.setTableKeys(new java.util.ArrayList<>());
				}
				targetTable.getTableKeys().add(newKey);
				
				availableTableKeyNode = frame_.new XeadTreeNode("TableKey", newKey);
				((XeadTreeNode)targetTableNode_.getChildAt(1)).add(availableTableKeyNode);
				frame_.getXeadUndoManager().addLogOfAdd(availableTableKeyNode);
			}
			//
			if (!relationshipType.equals("")) {
				//
 			// Add a relationship
				frame_.getXeadUndoManager().saveNodeBeforeModified(availableTableKeyNode);
				xeadModeler.model.json.Relationship relationship = new xeadModeler.model.json.Relationship();
				relationship.setId(Integer.toString(frame_.incrementLastIDOfRelationship()));
				xeadModeler.model.json.Table sourceTable = (xeadModeler.model.json.Table) sourceTableNode_.getJsonNode();
				relationship.setTable1Id(sourceTable.getId());
				relationship.setTableKey1Id(sourceKey_.getId());
				relationship.setTable2Id(targetTable.getId());
				xeadModeler.model.json.TableKey availableKey = (xeadModeler.model.json.TableKey) availableTableKeyNode.getJsonNode();
				relationship.setTableKey2Id(availableKey.getId());
				relationship.setType(relationshipType);
				relationship.setExistWhen1("");
				relationship.setExistWhen2("");
				frame_.getSystemElement().getRelationships().add(relationship);
				frame_.getXeadUndoManager().addLogAfterModified(availableTableKeyNode);
				//
				// Add Relationship attributes to each Subsystem if necessary
				if (frame_.getSystemElement().getSubsystems() != null) {
					for (xeadModeler.model.json.Subsystem subsystem : frame_.getSystemElement().getSubsystems()) {
						frame_.createSubsystemAttributesForRelationship(relationship, subsystem);
					}
				}
				//
				frame_.getXeadUndoManager().saveNodeBeforeModified(frame_.getCurrentMainTreenode());
			}
		} else {
			JOptionPane.showMessageDialog(this, res.getString("DialogAddRelationshipOnDatamodel9"));
		}
	}

	void jButtonCancel_actionPerformed(ActionEvent e) {
		keyFieldNameList.clear();
		keyFieldAliasList.clear();
		//
		buttonOKIsPressed = false;
		this.setVisible(false);
	}
}

class DialogAddRelationshipOnDatamodel_jRadioButton_changeAdapter implements ChangeListener {
	DialogAddRelationshipOnDatamodel adaptee;
	DialogAddRelationshipOnDatamodel_jRadioButton_changeAdapter(DialogAddRelationshipOnDatamodel adaptee) {
		this.adaptee = adaptee;
	}
	public void stateChanged(ChangeEvent e) {
		adaptee.jRadioButton_stateChanged(e);
	}
}

class DialogAddRelationshipOnDatamodel_jButtonOK_actionAdapter implements java.awt.event.ActionListener {
	DialogAddRelationshipOnDatamodel adaptee;
	DialogAddRelationshipOnDatamodel_jButtonOK_actionAdapter(DialogAddRelationshipOnDatamodel adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.jButtonOK_actionPerformed(e);
	}
}

class DialogAddRelationshipOnDatamodel_jButtonCancel_actionAdapter implements java.awt.event.ActionListener {
	DialogAddRelationshipOnDatamodel adaptee;
	DialogAddRelationshipOnDatamodel_jButtonCancel_actionAdapter(DialogAddRelationshipOnDatamodel adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.jButtonCancel_actionPerformed(e);
	}
}
