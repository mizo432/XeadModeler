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
import java.util.*;

import javax.swing.*;

import org.w3c.dom.*;

import xeadModeler.Modeler;
import xeadModeler.Modeler.*;

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
	private xeadModeler.model.json.XeadSystem synchFileXeadSystem_ = null;
	private xeadModeler.model.json.Table synchFileTable_ = null;
	private org.w3c.dom.Element targetTableElement_ = null;
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
		jButtonClose.addActionListener(new DialogTableSynchronize_jButtonClose_actionAdapter(this));
		jButtonImport.setText(res.getString("DialogTableSynchronize03"));
		jButtonImport.setBounds(new Rectangle(455, 7, 220, 29));
		jButtonImport.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jButtonImport.addActionListener(new DialogTableSynchronize_jButtonImport_actionAdapter(this));
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

				isSynchError_ = isSynchError(tableNode.getElement(), null);
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

	public boolean isSynchError(org.w3c.dom.Element tableElement, org.w3c.dom.Document domDocumentComparedTo) {
		isSynchError_ = false;
		errorMessage_ = "";
		org.w3c.dom.Element workElement = null;

		jButtonImport.setEnabled(false);

		synchFileTable_ = null;
		targetTableElement_ = tableElement;
		if (!targetTableElement_.getAttribute("SynchFile").equals("")) {
			if (targetTableElement_.getAttribute("SortKey").equals("")) {
				isSynchError_ = true;
				errorMessage_ = res.getString("DialogTableSynchronize06");
			} else {
				// JSON-based implementation
				if (domDocumentComparedTo == null) {
					synchFileXeadSystem_ = frame_.getSynchFileDocument(targetTableElement_.getAttribute("SynchFile"));
				} else {
					// If comparing to a DOM document, we need to handle it differently
					// For now, set to null as this path may need further refactoring
					synchFileXeadSystem_ = null;
				}
				if (synchFileXeadSystem_ == null) {
					isSynchError_ = true;
					errorMessage_ = res.getString("DialogTableSynchronize07");
				} else {
					boolean isFound = false;
					// JSON-based: iterate over tables in XeadSystem
					if (synchFileXeadSystem_.getTables() != null) {
						for (xeadModeler.model.json.Table table : synchFileXeadSystem_.getTables()) {
							if (table.getSortKey() != null && table.getSortKey().equals(targetTableElement_.getAttribute("SortKey"))) {
								isFound = true;
								synchFileTable_ = table;
								break;
							}
						}
					}
					if (isFound) {
						if (synchFileTable_.getSynchFile() == null || synchFileTable_.getSynchFile().equals("")) {
							jButtonImport.setEnabled(true);
							// JSON-based compareDefinitions implementation
							// Get the target table from the DOM element
							xeadModeler.model.json.Table targetTable = null;
							if (targetTableElement_ != null && frame_.getSystemElement().getTables() != null) {
								for (xeadModeler.model.json.Table table : frame_.getSystemElement().getTables()) {
									if (table.getId() != null && table.getId().equals(targetTableElement_.getAttribute("ID"))) {
										targetTable = table;
										break;
									}
								}
							}
							if (targetTable != null) {
								isSynchError_ = compareDefinitions(targetTable, synchFileTable_);
							} else {
								isSynchError_ = false;
							}
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
		}

		return isSynchError_;
	}



	/**
	 * JSON-based overload for isDifferentTableType
	 * @param targetTable the target JSON Table object
	 * @param synchFileTable the sync file JSON Table object
	 * @return true if table types are different, false otherwise
	 */
	boolean isDifferentTableType(xeadModeler.model.json.Table targetTable, xeadModeler.model.json.Table synchFileTable) {
		boolean isDifferent = false;

		if (targetTable.getTableTypeId() == null || synchFileTable.getTableTypeId() == null) {
			return true;
		}

		// Find TableType from target system
		xeadModeler.model.json.TableType targetTableType = null;
		if (frame_.getSystemElement().getTableTypes() != null) {
			for (xeadModeler.model.json.TableType tt : frame_.getSystemElement().getTableTypes()) {
				if (tt.getId() != null && tt.getId().equals(targetTable.getTableTypeId())) {
					targetTableType = tt;
					break;
				}
			}
		}

		// Find TableType from sync file system
		xeadModeler.model.json.TableType synchFileTableType = null;
		if (synchFileXeadSystem_.getTableTypes() != null) {
			for (xeadModeler.model.json.TableType tt : synchFileXeadSystem_.getTableTypes()) {
				if (tt.getId() != null && tt.getId().equals(synchFileTable.getTableTypeId())) {
					synchFileTableType = tt;
					break;
				}
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


	/**
	 * JSON-based overload for isDifferentDataType
	 * @param targetField the target JSON TableField object
	 * @param synchFileField the sync file JSON TableField object
	 * @return true if data types are different, false otherwise
	 */
	boolean isDifferentDataType(xeadModeler.model.json.TableField targetField, xeadModeler.model.json.TableField synchFileField) {
		boolean isDifferent = false;

		if (targetField.getDataTypeId() == null || synchFileField.getDataTypeId() == null) {
			return true;
		}

		// Find DataType from target system
		xeadModeler.model.json.DataType targetDataType = null;
		if (frame_.getSystemElement().getDataTypes() != null) {
			for (xeadModeler.model.json.DataType dt : frame_.getSystemElement().getDataTypes()) {
				if (dt.getId() != null && dt.getId().equals(targetField.getDataTypeId())) {
					targetDataType = dt;
					break;
				}
			}
		}

		// Find DataType from sync file system
		xeadModeler.model.json.DataType synchFileDataType = null;
		if (synchFileXeadSystem_.getDataTypes() != null) {
			for (xeadModeler.model.json.DataType dt : synchFileXeadSystem_.getDataTypes()) {
				if (dt.getId() != null && dt.getId().equals(synchFileField.getDataTypeId())) {
					synchFileDataType = dt;
					break;
				}
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

	boolean isEquivalentKey(org.w3c.dom.Element targetKeyElement, org.w3c.dom.Element synchFileKeyElement, NodeList targetFieldList, NodeList synchFileFieldList) {
		boolean isEquivalent = true;
		org.w3c.dom.Element targetKeyFieldElement = null;
		org.w3c.dom.Element synchFileKeyFieldElement = null;
		String targetKeyFieldAlias, synchFileKeyFieldAlias;
		boolean isFound;

		NodeList targetKeyFieldList = targetKeyElement.getElementsByTagName("TableKeyField");
		NodeList synchFileKeyFieldList = synchFileKeyElement.getElementsByTagName("TableKeyField");

		if (targetKeyFieldList.getLength() == synchFileKeyFieldList.getLength()) {
			for (int i = 0; i < targetKeyFieldList.getLength(); i++) {
				isFound = false;

				targetKeyFieldAlias = "";
				targetKeyFieldElement = (org.w3c.dom.Element)targetKeyFieldList.item(i);
				for (int j = 0; j < targetFieldList.getLength(); j++) {
					org.w3c.dom.Element element = (org.w3c.dom.Element)targetFieldList.item(j);
					if (targetKeyFieldElement.getAttribute("FieldID").equals(element.getAttribute("ID"))) {
						targetKeyFieldAlias = element.getAttribute("Alias");
						break;
					}
				}

				synchFileKeyFieldAlias = "";
				for (int j = 0; j < synchFileKeyFieldList.getLength(); j++) {
					synchFileKeyFieldElement = (org.w3c.dom.Element)synchFileKeyFieldList.item(j);
					for (int k = 0; k < synchFileFieldList.getLength(); k++) {
						org.w3c.dom.Element element = (org.w3c.dom.Element)synchFileFieldList.item(k);
						if (synchFileKeyFieldElement.getAttribute("FieldID").equals(element.getAttribute("ID"))) {
							synchFileKeyFieldAlias = element.getAttribute("Alias");
							break;
						}
					}
					if (targetKeyFieldAlias.equals(synchFileKeyFieldAlias)) {
						if (targetKeyFieldElement.getAttribute("AscDesc").equals(synchFileKeyFieldElement.getAttribute("AscDesc"))) {
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

	/**
	 * JSON-based overload for isEquivalentKey
	 * @param targetKey the target JSON TableKey object
	 * @param synchFileKey the sync file JSON TableKey object
	 * @param targetFields the list of target TableField objects
	 * @param synchFileFields the list of sync file TableField objects
	 * @return true if keys are equivalent, false otherwise
	 */
	boolean isEquivalentKey(xeadModeler.model.json.TableKey targetKey, xeadModeler.model.json.TableKey synchFileKey,
		java.util.List<xeadModeler.model.json.TableField> targetFields,
		java.util.List<xeadModeler.model.json.TableField> synchFileFields) {
		
		boolean isEquivalent = true;

		if (targetKey.getTableKeyFields() == null || synchFileKey.getTableKeyFields() == null
				|| targetKey.getTableKeyFields().size() != synchFileKey.getTableKeyFields().size()) {
			return targetKey.getTableKeyFields() != null && synchFileKey.getTableKeyFields() != null
					&& targetKey.getTableKeyFields().size() == synchFileKey.getTableKeyFields().size();
		}

		for (int i = 0; i < targetKey.getTableKeyFields().size(); i++) {
			boolean isFound = false;

			// Find the alias of the target key field
			String targetKeyFieldAlias = "";
			xeadModeler.model.json.TableKeyField targetKeyField = targetKey.getTableKeyFields().get(i);
			if (targetFields != null && targetKeyField.getFieldId() != null) {
				for (xeadModeler.model.json.TableField field : targetFields) {
					if (field.getId() != null && field.getId().equals(targetKeyField.getFieldId())) {
						targetKeyFieldAlias = field.getAlias();
						break;
					}
				}
			}

			// Find the alias of the synch file key field and compare
			String synchFileKeyFieldAlias = "";
			if (synchFileKey.getTableKeyFields() != null) {
				for (int j = 0; j < synchFileKey.getTableKeyFields().size(); j++) {
					xeadModeler.model.json.TableKeyField synchFileKeyField = synchFileKey.getTableKeyFields().get(j);
					if (synchFileFields != null && synchFileKeyField.getFieldId() != null) {
						for (xeadModeler.model.json.TableField field : synchFileFields) {
							if (field.getId() != null && field.getId().equals(synchFileKeyField.getFieldId())) {
								synchFileKeyFieldAlias = field.getAlias();
								break;
							}
						}
					}
					if (targetKeyFieldAlias.equals(synchFileKeyFieldAlias)) {
						if (targetKeyField.getAscDesc() != null && targetKeyField.getAscDesc().equals(synchFileKeyField.getAscDesc())) {
							isFound = true;
						}
						break;
					}
				}
			}

			if (!isFound) {
				isEquivalent = false;
			}
		}

		return isEquivalent;
	}

	void jButtonClose_actionPerformed(ActionEvent e) {
		super.setVisible(false);
	}

	/**
	 * JSON-based overload for compareDefinitions
	 * @param targetTable the target JSON Table object
	 * @param synchFileTable the sync file JSON Table object
	 * @return true if there are differences, false otherwise
	 */
	boolean compareDefinitions(xeadModeler.model.json.Table targetTable, xeadModeler.model.json.Table synchFileTable) {
		countOfErrors = 0;
		StringBuffer bf = new StringBuffer();

		// Compare table name
		if (targetTable.getName() == null || !targetTable.getName().equals(synchFileTable.getName())) {
			countOfErrors++;
			bf.append("\n" + countOfErrors+ res.getString("DialogTableSynchronize09"));
		}

		// Compare table alias
		String targetAlias = targetTable.getDescriptions() != null ? targetTable.getDescriptions() : "";
		String synchAlias = synchFileTable.getDescriptions() != null ? synchFileTable.getDescriptions() : "";
		// Note: Alias comparison may need adjustment based on actual field mapping

		// Compare table type
		if (isDifferentTableType(targetTable, synchFileTable)) {
			countOfErrors++;
			bf.append("\n" + countOfErrors+ res.getString("DialogTableSynchronize31"));
		}

		// Compare descriptions
		if (targetTable.getDescriptions() == null || !targetTable.getDescriptions().equals(synchFileTable.getDescriptions())) {
			countOfErrors++;
			bf.append("\n" + countOfErrors+ res.getString("DialogTableSynchronize10"));
		}

		// Compare fields
		java.util.List<xeadModeler.model.json.TableField> targetFieldList = targetTable.getTableFields();
		java.util.List<xeadModeler.model.json.TableField> synchFileFieldList = synchFileTable.getTableFields();

		if (targetFieldList != null) {
			for (xeadModeler.model.json.TableField targetField : targetFieldList) {
				boolean isFound = false;
				xeadModeler.model.json.TableField synchFileField = null;
				if (synchFileFieldList != null) {
					for (xeadModeler.model.json.TableField sf : synchFileFieldList) {
						if (targetField.getAlias() != null && targetField.getAlias().equals(sf.getAlias())) {
							isFound = true;
							synchFileField = sf;
							break;
						}
					}
				}
				if (isFound && synchFileField != null) {
					// Compare field name
					if (targetField.getName() == null || !targetField.getName().equals(synchFileField.getName())) {
						countOfErrors++;
						bf.append("\n" + countOfErrors+ res.getString("DialogTableSynchronize11") + targetField.getAlias() + res.getString("DialogTableSynchronize12"));
					}
					// Compare data type
					if (isDifferentDataType(targetField, synchFileField)) {
						countOfErrors++;
						bf.append("\n" + countOfErrors+ res.getString("DialogTableSynchronize11") + targetField.getAlias() + res.getString("DialogTableSynchronize13"));
					}
					// Compare descriptions
					if (targetField.getDescriptions() == null || !targetField.getDescriptions().equals(synchFileField.getDescriptions())) {
						countOfErrors++;
						bf.append("\n" + countOfErrors+ res.getString("DialogTableSynchronize11") + targetField.getAlias() + res.getString("DialogTableSynchronize14"));
					}
					// Compare attribute type
					if (targetField.getAttributeType() == null || !targetField.getAttributeType().equals(synchFileField.getAttributeType())) {
						countOfErrors++;
						bf.append("\n" + countOfErrors+ res.getString("DialogTableSynchronize11") + targetField.getAlias() + res.getString("DialogTableSynchronize26"));
					}
					// Compare not null
					if (targetField.getNotNull() == null || !targetField.getNotNull().equals(synchFileField.getNotNull())) {
						countOfErrors++;
						bf.append("\n" + countOfErrors+ res.getString("DialogTableSynchronize11") + targetField.getAlias() + res.getString("DialogTableSynchronize27"));
					}
					// Compare default
					String targetDefault = targetField.getDefault_() != null ? targetField.getDefault_() : "";
					String synchDefault = synchFileField.getDefault_() != null ? synchFileField.getDefault_() : "";
					if (!targetDefault.equals(synchDefault)) {
						countOfErrors++;
						bf.append("\n" + countOfErrors+ res.getString("DialogTableSynchronize11") + targetField.getAlias() + res.getString("DialogTableSynchronize28"));
					}
					// Compare no update
					String targetNoUpdate = targetField.getNoUpdate() != null ? targetField.getNoUpdate() : "";
					String synchNoUpdate = synchFileField.getNoUpdate() != null ? synchFileField.getNoUpdate() : "";
					if (!targetNoUpdate.equals("") && !synchNoUpdate.equals("") && !targetNoUpdate.equals(synchNoUpdate)) {
						countOfErrors++;
						bf.append("\n" + countOfErrors+ res.getString("DialogTableSynchronize11") + targetField.getAlias() + res.getString("DialogTableSynchronize29"));
					}
				} else {
					countOfErrors++;
					bf.append("\n" + countOfErrors+ res.getString("DialogTableSynchronize11") + targetField.getAlias() + res.getString("DialogTableSynchronize15"));
				}
			}
		}

		// Check for fields in synch file that are not in target
		if (synchFileFieldList != null) {
			for (xeadModeler.model.json.TableField synchFileField : synchFileFieldList) {
				boolean isFound = false;
				if (targetFieldList != null) {
					for (xeadModeler.model.json.TableField targetField : targetFieldList) {
						if (targetField.getAlias() != null && targetField.getAlias().equals(synchFileField.getAlias())) {
							isFound = true;
							break;
						}
					}
				}
				if (!isFound) {
					countOfErrors++;
					bf.append("\n" + countOfErrors+ res.getString("DialogTableSynchronize16") + synchFileField.getAlias() + res.getString("DialogTableSynchronize17"));
				}
			}
		}

		// Compare keys
		java.util.List<xeadModeler.model.json.TableKey> targetKeyList = targetTable.getTableKeys();
		java.util.List<xeadModeler.model.json.TableKey> synchFileKeyList = synchFileTable.getTableKeys();

		if (targetKeyList != null) {
			for (xeadModeler.model.json.TableKey targetKey : targetKeyList) {
				if ("PK".equals(targetKey.getType())) {
					if (synchFileKeyList != null) {
						for (xeadModeler.model.json.TableKey synchFileKey : synchFileKeyList) {
							if ("PK".equals(synchFileKey.getType())) {
								if (!isEquivalentKey(targetKey, synchFileKey, targetFieldList, synchFileFieldList)) {
									countOfErrors++;
									bf.append("\n" + countOfErrors+ res.getString("DialogTableSynchronize18"));
								}
								break;
							}
						}
					}
				} else {
					boolean isFound = false;
					if (synchFileKeyList != null) {
						for (xeadModeler.model.json.TableKey synchFileKey : synchFileKeyList) {
							if (synchFileKey.getType() != null && synchFileKey.getType().equals(targetKey.getType())) {
								if (isEquivalentKey(targetKey, synchFileKey, targetFieldList, synchFileFieldList)) {
									isFound = true;
									break;
								}
							}
						}
					}
					if (!isFound) {
						countOfErrors++;
						bf.append("\n" + countOfErrors+ res.getString("DialogTableSynchronize20"));
					}
				}
			}
		}

		// Check for keys in synch file that are not in target
		if (synchFileKeyList != null) {
			for (xeadModeler.model.json.TableKey synchFileKey : synchFileKeyList) {
				if (!"PK".equals(synchFileKey.getType())) {
					boolean isFound = false;
					if (targetKeyList != null) {
						for (xeadModeler.model.json.TableKey targetKey : targetKeyList) {
							if (targetKey.getType() != null && targetKey.getType().equals(synchFileKey.getType())) {
								if (isEquivalentKey(targetKey, synchFileKey, targetFieldList, synchFileFieldList)) {
									isFound = true;
									break;
								}
							}
						}
					}
					if (!isFound) {
						countOfErrors++;
						bf.append("\n" + countOfErrors+ res.getString("DialogTableSynchronize22"));
					}
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

	void jButtonImport_actionPerformed(ActionEvent e) {
		// JSON-based implementation
		try {
			setCursor(new Cursor(Cursor.WAIT_CURSOR));

			isImported_ = true;
			// Get the target table from the DOM element
			xeadModeler.model.json.Table targetTable = null;
			if (targetTableElement_ != null && frame_.getSystemElement().getTables() != null) {
				for (xeadModeler.model.json.Table table : frame_.getSystemElement().getTables()) {
					if (table.getId() != null && table.getId().equals(targetTableElement_.getAttribute("ID"))) {
						targetTable = table;
						break;
					}
				}
			}

			if (targetTable != null && synchFileTable_ != null) {
				importIntoTarget(targetTable, synchFileTable_);
				isSynchError_ = compareDefinitions(targetTable, synchFileTable_);
			} else {
				isSynchError_ = false;
			}

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

	/**
	 * JSON-based overload for importIntoTarget
	 * @param targetTable the target JSON Table object
	 * @param synchFileTable the sync file JSON Table object
	 */
	void importIntoTarget(xeadModeler.model.json.Table targetTable, xeadModeler.model.json.Table synchFileTable) {
		// Update table properties
		targetTable.setName(synchFileTable.getName());
		targetTable.setDescriptions(synchFileTable.getDescriptions());

		// Update table type if different
		if (isDifferentTableType(targetTable, synchFileTable)) {
			String tableTypeID = getEquivalentTableTypeIDFromTargetDocument(synchFileTable.getTableTypeId());
			targetTable.setTableTypeId(tableTypeID);
		}

		// Import fields
		String dataTypeID;
		java.util.List<xeadModeler.model.json.TableField> targetFieldList = targetTable.getTableFields();
		java.util.List<xeadModeler.model.json.TableField> synchFileFieldList = synchFileTable.getTableFields();

		if (synchFileFieldList != null) {
			for (xeadModeler.model.json.TableField synchFileField : synchFileFieldList) {
				boolean isFound = false;
				xeadModeler.model.json.TableField targetField = null;

				if (targetFieldList != null) {
					for (xeadModeler.model.json.TableField tf : targetFieldList) {
						if (tf.getAlias() != null && tf.getAlias().equals(synchFileField.getAlias())) {
							isFound = true;
							targetField = tf;
							break;
						}
					}
				}

				if (isFound && targetField != null) {
					// Update existing field
					targetField.setName(synchFileField.getName());
					if (isDifferentDataType(targetField, synchFileField)) {
						dataTypeID = getEquivalentDataTypeIDFromTargetDocument(synchFileField.getDataTypeId());
						targetField.setDataTypeId(dataTypeID);
					}
					targetField.setDescriptions(synchFileField.getDescriptions());
					targetField.setAttributeType(synchFileField.getAttributeType());
					targetField.setNotNull(synchFileField.getNotNull());
					targetField.setDefault_(synchFileField.getDefault_());
					targetField.setNoUpdate(synchFileField.getNoUpdate());
					targetField.setSortKey(synchFileField.getSortKey());
				} else {
					// Add new field
					xeadModeler.model.json.TableField newField = new xeadModeler.model.json.TableField();
					newField.setId(getNextIDOfNodeType("TableField"));
					newField.setName(synchFileField.getName());
					newField.setAlias(synchFileField.getAlias());
					dataTypeID = getEquivalentDataTypeIDFromTargetDocument(synchFileField.getDataTypeId());
					newField.setDataTypeId(dataTypeID);
					newField.setDescriptions(synchFileField.getDescriptions());
					newField.setAttributeType(synchFileField.getAttributeType());
					newField.setNotNull(synchFileField.getNotNull());
					newField.setDefault_(synchFileField.getDefault_());
					newField.setNoUpdate(synchFileField.getNoUpdate());
					newField.setShowOnModel(synchFileField.getShowOnModel());
					newField.setSortKey(synchFileField.getSortKey());

					if (targetTable.getTableFields() == null) {
						targetTable.setTableFields(new java.util.ArrayList<>());
					}
					targetTable.getTableFields().add(newField);
				}
			}
		}

		// Import keys
		int biggestKeyID = 0;
		java.util.List<xeadModeler.model.json.TableKey> targetKeyList = targetTable.getTableKeys();
		java.util.List<xeadModeler.model.json.TableKey> synchFileKeyList = synchFileTable.getTableKeys();

		// Find the biggest key ID
		if (targetKeyList != null) {
			for (xeadModeler.model.json.TableKey key : targetKeyList) {
				if (key.getId() != null) {
					try {
						int wrkInt = Integer.parseInt(key.getId());
						if (wrkInt > biggestKeyID) {
							biggestKeyID = wrkInt;
						}
					} catch (NumberFormatException e) {
						// Ignore non-numeric IDs
					}
				}
			}
		}

		if (synchFileKeyList != null) {
			for (xeadModeler.model.json.TableKey synchFileKey : synchFileKeyList) {
				if ("PK".equals(synchFileKey.getType())) {
					// Handle primary key
					if (targetKeyList != null) {
						for (xeadModeler.model.json.TableKey targetKey : targetKeyList) {
							if ("PK".equals(targetKey.getType())) {
								if (!isEquivalentKey(targetKey, synchFileKey, targetTable.getTableFields(), synchFileTable.getTableFields())) {
									// Check for relationships
									boolean hasRelationships = false;
									if (frame_.getSystemElement().getRelationships() != null) {
										for (xeadModeler.model.json.Relationship rel : frame_.getSystemElement().getRelationships()) {
											if ((rel.getTable1Id() != null && rel.getTable1Id().equals(targetTable.getId()) && rel.getTableKey1Id() != null && rel.getTableKey1Id().equals(targetKey.getId()))
												|| (rel.getTable2Id() != null && rel.getTable2Id().equals(targetTable.getId()) && rel.getTableKey2Id() != null && rel.getTableKey2Id().equals(targetKey.getId()))) {
												hasRelationships = true;
												break;
											}
										}
									}
									if (hasRelationships) {
										JOptionPane.showMessageDialog(this, res.getString("DialogTableSynchronize19"));
									} else {
										// Update key fields
										if (targetKey.getTableKeyFields() != null) {
											targetKey.getTableKeyFields().clear();
										} else {
											targetKey.setTableKeyFields(new java.util.ArrayList<>());
										}
										if (synchFileKey.getTableKeyFields() != null) {
											for (xeadModeler.model.json.TableKeyField synchFileKeyField : synchFileKey.getTableKeyFields()) {
												xeadModeler.model.json.TableKeyField newKeyField = new xeadModeler.model.json.TableKeyField();
												newKeyField.setFieldId(getEquivalentFieldIDFromTargetTable(synchFileKeyField.getFieldId(), synchFileTable, targetTable));
												newKeyField.setAscDesc(synchFileKeyField.getAscDesc());
												newKeyField.setSortKey(synchFileKeyField.getSortKey());
												targetKey.getTableKeyFields().add(newKeyField);
											}
										}
									}
								}
								break;
							}
						}
					}
				} else {
					// Handle non-primary keys
					boolean isFound = false;
					if (targetKeyList != null) {
						for (xeadModeler.model.json.TableKey targetKey : targetKeyList) {
							if (targetKey.getType() != null && targetKey.getType().equals(synchFileKey.getType())) {
								if (isEquivalentKey(targetKey, synchFileKey, targetTable.getTableFields(), synchFileTable.getTableFields())) {
									isFound = true;
									break;
								}
							}
						}
					}
					if (!isFound) {
						biggestKeyID++;
						xeadModeler.model.json.TableKey newKey = new xeadModeler.model.json.TableKey();
						newKey.setId(Integer.toString(biggestKeyID));
						newKey.setType(synchFileKey.getType());
						newKey.setSortKey(synchFileKey.getSortKey());

						// Add key fields
						if (synchFileKey.getTableKeyFields() != null) {
							for (xeadModeler.model.json.TableKeyField synchFileKeyField : synchFileKey.getTableKeyFields()) {
								xeadModeler.model.json.TableKeyField newKeyField = new xeadModeler.model.json.TableKeyField();
								newKeyField.setFieldId(getEquivalentFieldIDFromTargetTable(synchFileKeyField.getFieldId(), synchFileTable, targetTable));
								newKeyField.setAscDesc(synchFileKeyField.getAscDesc());
								newKeyField.setSortKey(synchFileKeyField.getSortKey());
								if (newKey.getTableKeyFields() == null) {
									newKey.setTableKeyFields(new java.util.ArrayList<>());
								}
								newKey.getTableKeyFields().add(newKeyField);
							}
						}

						if (targetTable.getTableKeys() == null) {
							targetTable.setTableKeys(new java.util.ArrayList<>());
						}
						targetTable.getTableKeys().add(newKey);
					}
				}
			}
		}
	}


	/**
	 * JSON-based overload for getEquivalentTableTypeIDFromTargetDocument
	 * @param synchFileTableTypeID the TableType ID from the sync file
	 * @return the equivalent TableType ID in the target document
	 */
	private String getEquivalentTableTypeIDFromTargetDocument(String synchFileTableTypeID) {
		// Find TableType from sync file system
		xeadModeler.model.json.TableType synchFileTableType = null;
		if (synchFileXeadSystem_.getTableTypes() != null) {
			for (xeadModeler.model.json.TableType tt : synchFileXeadSystem_.getTableTypes()) {
				if (tt.getId() != null && tt.getId().equals(synchFileTableTypeID)) {
					synchFileTableType = tt;
					break;
				}
			}
		}

		if (synchFileTableType == null) {
			return "";
		}

		// Find equivalent TableType in target system
		String targetTableTypeID = "";
		if (frame_.getSystemElement().getTableTypes() != null) {
			for (xeadModeler.model.json.TableType tt : frame_.getSystemElement().getTableTypes()) {
				if (tt.getSortKey() != null && tt.getSortKey().equals(synchFileTableType.getSortKey())
						&& tt.getName() != null && tt.getName().equals(synchFileTableType.getName())) {
					targetTableTypeID = tt.getId();
					break;
				}
			}
		}

		// If not found, create a new TableType
		if (targetTableTypeID.equals("")) {
			xeadModeler.model.json.TableType newTableType = new xeadModeler.model.json.TableType();
			targetTableTypeID = getNextIDOfNodeType("TableType");
			newTableType.setId(targetTableTypeID);
			newTableType.setName(synchFileTableType.getName());
			newTableType.setSortKey(synchFileTableType.getSortKey());
			newTableType.setDescriptions(synchFileTableType.getDescriptions());
			if (frame_.getSystemElement().getTableTypes() == null) {
				frame_.getSystemElement().setTableTypes(new java.util.ArrayList<>());
			}
			frame_.getSystemElement().getTableTypes().add(newTableType);
		}

		return targetTableTypeID;
	}

	/**
	 * JSON-based overload for getEquivalentDataTypeIDFromTargetDocument
	 * @param synchFileDataTypeID the DataType ID from the sync file
	 * @return the equivalent DataType ID in the target document
	 */
	private String getEquivalentDataTypeIDFromTargetDocument(String synchFileDataTypeID) {
		// Find DataType from sync file system
		xeadModeler.model.json.DataType synchFileDataType = null;
		if (synchFileXeadSystem_.getDataTypes() != null) {
			for (xeadModeler.model.json.DataType dt : synchFileXeadSystem_.getDataTypes()) {
				if (dt.getId() != null && dt.getId().equals(synchFileDataTypeID)) {
					synchFileDataType = dt;
					break;
				}
			}
		}

		if (synchFileDataType == null) {
			return "";
		}

		// Find equivalent DataType in target system
		String targetDataTypeID = "";
		if (frame_.getSystemElement().getDataTypes() != null) {
			for (xeadModeler.model.json.DataType dt : frame_.getSystemElement().getDataTypes()) {
				if (dt.getBasicType() != null && dt.getBasicType().equals(synchFileDataType.getBasicType())
						&& dt.getName() != null && dt.getName().equals(synchFileDataType.getName())
						&& dt.getSqlExpression() != null && dt.getSqlExpression().equals(synchFileDataType.getSqlExpression())
						&& dt.getLength() != null && dt.getLength().equals(synchFileDataType.getLength())
						&& dt.getDecimal() != null && dt.getDecimal().equals(synchFileDataType.getDecimal())) {
					targetDataTypeID = dt.getId();
					break;
				}
			}
		}

		// If not found, create a new DataType
		if (targetDataTypeID.equals("")) {
			xeadModeler.model.json.DataType newDataType = new xeadModeler.model.json.DataType();
			targetDataTypeID = getNextIDOfNodeType("DataType");
			newDataType.setId(targetDataTypeID);
			newDataType.setName(synchFileDataType.getName());
			newDataType.setSortKey(synchFileDataType.getSortKey());
			newDataType.setBasicType(synchFileDataType.getBasicType());
			newDataType.setSqlExpression(synchFileDataType.getSqlExpression());
			newDataType.setLength(synchFileDataType.getLength());
			newDataType.setDecimal(synchFileDataType.getDecimal());
			if (frame_.getSystemElement().getDataTypes() == null) {
				frame_.getSystemElement().setDataTypes(new java.util.ArrayList<>());
			}
			frame_.getSystemElement().getDataTypes().add(newDataType);
		}

		return targetDataTypeID;
	}

	private String getEquivalentFieldIDFromTargetTable(String synchFileFieldID, org.w3c.dom.Element synchFileTableElement, org.w3c.dom.Element targetTableElement) {
		org.w3c.dom.Element element;

		org.w3c.dom.Element synchFileFieldElement = null;
		NodeList synchFileFieldList = synchFileTableElement.getElementsByTagName("TableField");
		for (int i = 0; i < synchFileFieldList.getLength(); i++) {
			element = (org.w3c.dom.Element)synchFileFieldList.item(i);
			if (element.getAttribute("ID").equals(synchFileFieldID)) {
				synchFileFieldElement = element;
				break;
			}
		}

		String targetFieldID = "";
		NodeList targetFieldList = targetTableElement.getElementsByTagName("TableField");
		for (int i = 0; i < targetFieldList.getLength(); i++) {
			element = (org.w3c.dom.Element)targetFieldList.item(i);
			if (element.getAttribute("Alias").equals(synchFileFieldElement.getAttribute("Alias"))) {
				targetFieldID = element.getAttribute("ID");
				break;
			}
		}
		return targetFieldID;
	}

	/**
	 * JSON-based overload for getEquivalentFieldIDFromTargetTable
	 * @param synchFileFieldID the field ID from the sync file
	 * @param synchFileTable the sync file JSON Table object
	 * @param targetTable the target JSON Table object
	 * @return the equivalent field ID in the target table
	 */
	private String getEquivalentFieldIDFromTargetTable(String synchFileFieldID, xeadModeler.model.json.Table synchFileTable, xeadModeler.model.json.Table targetTable) {
		// Find the field from sync file
		xeadModeler.model.json.TableField synchFileField = null;
		if (synchFileTable.getTableFields() != null) {
			for (xeadModeler.model.json.TableField field : synchFileTable.getTableFields()) {
				if (field.getId() != null && field.getId().equals(synchFileFieldID)) {
					synchFileField = field;
					break;
				}
			}
		}

		if (synchFileField == null) {
			return "";
		}

		// Find the equivalent field in target table by alias
		String targetFieldID = "";
		if (targetTable.getTableFields() != null) {
			for (xeadModeler.model.json.TableField field : targetTable.getTableFields()) {
				if (field.getAlias() != null && field.getAlias().equals(synchFileField.getAlias())) {
					targetFieldID = field.getId();
					break;
				}
			}
		}

		return targetFieldID;
	}

	private String getNextIDOfNodeType(String tagName) {
		String nextID = "1";
		org.w3c.dom.Element element = null;
		org.w3c.dom.Element lastDomElement = null;
		int lastDomElementID = 0;
		int elementID = 0;
		NodeList nodeList = frame_.domDocument.getElementsByTagName(tagName);
		for (int i = 0; i < nodeList.getLength(); i++) {
			element = (org.w3c.dom.Element)nodeList.item(i);
			if (lastDomElement == null) {
				lastDomElement = element;
			} else {
				elementID = Integer.parseInt(element.getAttribute("ID"));
				lastDomElementID = Integer.parseInt(lastDomElement.getAttribute("ID"));
				if (elementID > lastDomElementID) {
					lastDomElement = element;
				}
			}
		}
		if (lastDomElement != null) {
			int lastID = Integer.parseInt(lastDomElement.getAttribute("ID"));
			nextID = Integer.toString(lastID + 1);
		}
		return nextID;
	}

	public boolean isImported() {
		return isImported_;
	}
}

class DialogTableSynchronize_jButtonClose_actionAdapter implements java.awt.event.ActionListener {
	DialogTableSynchronize adaptee;

	DialogTableSynchronize_jButtonClose_actionAdapter(DialogTableSynchronize adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.jButtonClose_actionPerformed(e);
	}
}

class DialogTableSynchronize_jButtonImport_actionAdapter implements java.awt.event.ActionListener {
	DialogTableSynchronize adaptee;

	DialogTableSynchronize_jButtonImport_actionAdapter(DialogTableSynchronize adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.jButtonImport_actionPerformed(e);
	}
}