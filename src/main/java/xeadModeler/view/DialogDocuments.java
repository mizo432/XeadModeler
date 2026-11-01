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
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Footer;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.*;

import xeadModeler.Modeler;
import xeadModeler.model.json.DataType;
import xeadModeler.model.json.Function;
import xeadModeler.model.json.FunctionType;
import xeadModeler.model.json.FunctionUsedByThis;
import xeadModeler.model.json.IOPanel;
import xeadModeler.model.json.IOPanelField;
import xeadModeler.model.json.IOSpool;
import xeadModeler.model.json.IOSpoolField;
import xeadModeler.model.json.IOTable;
import xeadModeler.model.json.IOTableField;
import xeadModeler.model.json.IOWebPage;
import xeadModeler.model.json.IOWebPageField;
import xeadModeler.model.json.Relationship;
import xeadModeler.model.json.Role;
import xeadModeler.model.json.Subsystem;
import xeadModeler.model.json.Table;
import xeadModeler.model.json.TableField;
import xeadModeler.model.json.TableKey;
import xeadModeler.model.json.TableKeyField;
import xeadModeler.model.json.TableType;
import xeadModeler.model.json.Task;
import xeadModeler.model.json.TaskAction;
import xeadModeler.model.json.TaskFunctionIO;
import xeadModeler.model.json.XeadSystem;

public class DialogDocuments extends JDialog {
	private static final long serialVersionUID = 1L;
	private static ResourceBundle res = ResourceBundle.getBundle("xeadModeler.Res");
	private Modeler frame_;
	private String fileName_;
	private String xlsFileName;
	private JPanel jPanelMain = new JPanel();
	private JPanel jPanelCenter = new JPanel();
	private JPanel jPanelSouth = new JPanel();
	private JLabel jLabelDocumentType = new JLabel();
	private JLabel jLabelSubsystem = new JLabel();
	private JPanel jPanel1 = new JPanel();
	private BorderLayout borderLayout1 = new BorderLayout();
	private GridLayout gridLayout1 = new GridLayout();
	private JRadioButton jRadioButtonTable = new JRadioButton();
	private JRadioButton jRadioButtonFunction = new JRadioButton();
	private ButtonGroup buttonGroup = new ButtonGroup();
	private SortableJsonNodeComboBoxModel comboBoxModelSubsystems = new SortableJsonNodeComboBoxModel();
	private JComboBox jComboBoxSubsystems = new JComboBox(comboBoxModelSubsystems);
	private SortableJsonNodeComboBoxModel comboBoxModelFunctions = new SortableJsonNodeComboBoxModel();
	private JComboBox jComboBoxFunctions = new JComboBox(comboBoxModelFunctions);
	private SortableJsonNodeComboBoxModel comboBoxModelTables = new SortableJsonNodeComboBoxModel();
	private JComboBox jComboBoxTables = new JComboBox(comboBoxModelTables);
	private JTextArea jTextArea1 = new JTextArea();
	private JButton jButtonStart = new JButton();
	private JProgressBar jProgressBar = new JProgressBar();
	private JButton jButtonCloseDialog = new JButton();
	private List<Task> taskList = null;
	private List<Role> roleList = null;
	private List<Subsystem> subsystemList = null;
	private List<Table> tableList = null;
	private List<IOTable> ioTableList = null;
	private List<Relationship> relationshipList = null;
	private List<TableType> tableTypeList = null;
	private List<DataType> dataTypeList = null;
	private List<Function> functionList = null;
	private List<FunctionType> functionTypeList = null;
	private Subsystem subsystemElement = null;
	private String subsystemID = "";
	private String tableID = "";
	private String functionID = "";
	private int currentRowNumber = 0;
	private int countOfErrors = 0;
	private JsonNode[] definitionArray = new JsonNode[200];
	private JsonNode[] elementArray1 = new JsonNode[1000];
	private int countOfElementArray1 = -1;
	private JsonNode[] elementArray2 = new JsonNode[1000];
	private int countOfElementArray2 = -1;

	private XSSFFont fontTitle = null;
	private XSSFFont fontHeader1 = null;
	private XSSFFont fontHeader2 = null;
	private XSSFFont fontValue = null;
	private XSSFFont fontValueStrong = null;
	private XSSFFont fontImage = null;
	private XSSFFont fontImageUL = null;
	private XSSFWorkbook workBook = null;
	private XSSFCellStyle styleTitle = null;
	private XSSFCellStyle styleHeader1 = null;
	private XSSFCellStyle styleHeader2 = null;
	private XSSFCellStyle styleHeader2Number = null;
	private XSSFCellStyle styleValue = null;
	private XSSFCellStyle styleValueStrong = null;
	private XSSFCellStyle styleValueNumber = null;
	private XSSFCellStyle styleImage = null;
	protected XSSFPicture picture = null;

	public DialogDocuments(Modeler frame, String title, boolean modal) {
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

	public DialogDocuments(Modeler frame) {
		this(frame, "", true);
	}

	public DialogDocuments(JFrame parentFrame) {
		super(parentFrame, "", true);
		try {
			frame_ = null;
			jbInit();
			pack();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	private void jbInit() throws Exception {
		jPanelMain.setLayout(borderLayout1);
		jPanelMain.setPreferredSize(new Dimension(600, 321));
		jPanelMain.setBorder(BorderFactory.createEtchedBorder());
		jPanelCenter.setBorder(BorderFactory.createEtchedBorder());
		jPanelCenter.setLayout(null);
		jPanelCenter.setPreferredSize(new Dimension(800, 134));
		jPanelSouth.setBorder(BorderFactory.createEtchedBorder());
		jPanelSouth.setLayout(null);
		jPanelSouth.setPreferredSize(new Dimension(800, 43));
		jPanel1.setLayout(gridLayout1);
		jLabelSubsystem.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jLabelSubsystem.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabelSubsystem.setText(res.getString("DialogDocuments01"));
		jLabelSubsystem.setBounds(new Rectangle(5, 12, 130, 20));
		jComboBoxSubsystems.setBounds(new Rectangle(140, 9, 250, 25));
		jComboBoxSubsystems.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jComboBoxSubsystems.addActionListener(new DialogDocuments_jComboBoxSubsystems_actionAdapter(this));
		jLabelDocumentType.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jLabelDocumentType.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabelDocumentType.setText(res.getString("DialogDocuments02"));
		jLabelDocumentType.setBounds(new Rectangle(5, 43, 130, 20));
		gridLayout1.setColumns(1);
		gridLayout1.setRows(2);
		jPanel1.setBorder(BorderFactory.createEtchedBorder());
		jPanel1.setBounds(new Rectangle(140, 40, 180, 60));
		jPanel1.setLayout(gridLayout1);
		jRadioButtonTable.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jRadioButtonTable.setText(res.getString("DialogDocuments03"));
		jRadioButtonTable.setSelected(true);
		jRadioButtonTable.addChangeListener(new DialogDocuments_jRadioButtonTable_changeAdapter(this));
		jRadioButtonFunction.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jRadioButtonFunction.setText(res.getString("DialogDocuments04"));
		jRadioButtonFunction.addChangeListener(new DialogDocuments_jRadioButtonFunction_changeAdapter(this));
		buttonGroup.add(jRadioButtonTable);
		buttonGroup.add(jRadioButtonFunction);
		jComboBoxTables.setBounds(new Rectangle(325, 40, 262, 25));
		jComboBoxTables.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jComboBoxTables.setEnabled(false);
		jComboBoxFunctions.setBounds(new Rectangle(325, 74, 262, 25));
		jComboBoxFunctions.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jComboBoxFunctions.setEnabled(false);
		jTextArea1.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jTextArea1.setForeground(Color.BLUE);
		jTextArea1.setEditable(false);
		jTextArea1.setBounds(new Rectangle(5, 106, 584, 160));
		jTextArea1.setLineWrap(true);
		jTextArea1.setBackground(SystemColor.control);
		jTextArea1.setBorder(BorderFactory.createLoweredBevelBorder());
		jTextArea1.setText(res.getString("DialogDocuments05") + "\n" + "\n" + res.getString("DialogDocuments06"));

		getContentPane().add(jPanelMain);
		jPanelMain.add(jPanelSouth, BorderLayout.SOUTH);
		jPanelMain.add(jPanelCenter, BorderLayout.CENTER);
		jPanel1.add(jRadioButtonTable, null);
		jPanel1.add(jRadioButtonFunction, null);
		jPanelCenter.add(jLabelSubsystem, null);
		jPanelCenter.add(jComboBoxSubsystems, null);
		jPanelCenter.add(jLabelDocumentType, null);
		jPanelCenter.add(jPanel1, null);
		jPanelCenter.add(jComboBoxTables, null);
		jPanelCenter.add(jComboBoxFunctions, null);
		jPanelCenter.add(jTextArea1, null);

		jButtonStart.setBounds(new Rectangle(30, 7, 200, 27));
		jButtonStart.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jButtonStart.setText(res.getString("DialogDocuments07"));
		jButtonStart.addActionListener(new DialogDocuments_jButtonStart_actionAdapter(this));
		jProgressBar.setBounds(new Rectangle(30, 7, 200, 27));
		jProgressBar.setVisible(false);
		jProgressBar.setStringPainted(true);
		jButtonCloseDialog.setBounds(new Rectangle(440, 7, 110, 27));
		jButtonCloseDialog.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jButtonCloseDialog.setText(res.getString("DialogDocuments08"));
		jButtonCloseDialog.addActionListener(new DialogDocuments_jButtonCloseDialog_actionAdapter(this));
		jPanelSouth.add(jButtonCloseDialog, null);
		jPanelSouth.add(jButtonStart, null);
		jPanelSouth.add(jProgressBar, null);

		this.setResizable(false);
		this.setTitle(res.getString("DialogDocuments09"));
		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension dlgSize = this.getPreferredSize();
		this.setLocation((scrSize.width - dlgSize.width)/2 , (scrSize.height - dlgSize.height)/2);
		this.pack();
	}

	public String request(String fileName) {
		JsonNode node;
		fileName_ = fileName;
		xlsFileName = "";

		comboBoxModelSubsystems.removeAllElements();
		XeadSystem xeadSystem = frame_.getSystemElement();
		if (xeadSystem == null) {
			JOptionPane.showMessageDialog(this, "No system data loaded.");
			return "";
		}
		subsystemList = xeadSystem.getSubsystems();
		for (int i = 0; i < subsystemList.size(); i++) {
			node = new JsonNode("Subsystem", subsystemList.get(i));
			comboBoxModelSubsystems.addElement((Object)node);
		}
		comboBoxModelSubsystems.sortElements();
		comboBoxModelSubsystems.insertElementAt(res.getString("DialogDocuments10"), 0);
		comboBoxModelSubsystems.setSelectedItem(comboBoxModelSubsystems.getElementAt(0));

		taskList = xeadSystem.getTasks();
		roleList = xeadSystem.getRoles();
		tableList = xeadSystem.getTables();
		relationshipList = xeadSystem.getRelationships();
		dataTypeList = xeadSystem.getDataTypes();
		tableTypeList = xeadSystem.getTableTypes();
		functionList = xeadSystem.getFunctions();
		functionTypeList = xeadSystem.getFunctionTypes();
		// Collect ioTableList from all functions
		ioTableList = new ArrayList<>();
		for (Function func : functionList) {
			if (func.getIOTables() != null) {
				ioTableList.addAll(func.getIOTables());
			}
		}

		super.setVisible(true);

		return xlsFileName;
	}

	void jButtonStart_actionPerformed(ActionEvent e) {
		Object workPojo = null;
		JsonNode workNode = null;
		int countOfDefinitions = 0;

		try{
			setCursor(new Cursor(Cursor.WAIT_CURSOR));
			jProgressBar.setVisible(true);
			jButtonStart.setVisible(false);
			subsystemID = "";
			countOfErrors = 0;

			if (jComboBoxSubsystems.getSelectedIndex() > 0) {
				workNode = (JsonNode)comboBoxModelSubsystems.getSelectedItem();
				subsystemElement = (Subsystem)workNode.getPojo();
				subsystemID = subsystemElement.getId();

				File file = new File(fileName_);

				if (jRadioButtonTable.isSelected()) {
					if (jComboBoxTables.getSelectedIndex() > 0) {
						workNode = (JsonNode)comboBoxModelTables.getSelectedItem();
						workPojo = workNode.getPojo();
						tableID = ((Table)workPojo).getId();
					}

					countOfDefinitions = 0;
					for (int m = 0; m < tableList.size(); m++) {
						Table table = tableList.get(m);
						if (table.getSubsystemId().equals(subsystemID)) {
							if (table.getId().equals(tableID) || tableID.equals("")) {
								workNode = new JsonNode("Table", table);
								definitionArray[countOfDefinitions] = workNode;
								countOfDefinitions++;
							}
						}
					}
					if (countOfDefinitions > 1) {
						Arrays.sort(definitionArray, 0, countOfDefinitions);
					}
					xlsFileName = file.getParent() + File.separator + "TableDoc" + getStringValueOfDateAndTime() + ".xlsx";
				}

				if (jRadioButtonFunction.isSelected()) {
					if (jComboBoxFunctions.getSelectedIndex() > 0) {
						workNode = (JsonNode)comboBoxModelFunctions.getSelectedItem();
						workPojo = workNode.getPojo();
						functionID = ((Function)workPojo).getId();
					}

					countOfDefinitions = 0;
					for (int m = 0; m < functionList.size(); m++) {
						Function func = functionList.get(m);
						if (func.getSubsystemId().equals(subsystemID)) {
							if (func.getId().equals(functionID) || functionID.equals("")) {
								workNode = new JsonNode("Function", func);
								definitionArray[countOfDefinitions] = workNode;
								countOfDefinitions++;
							}
						}
					}
					if (countOfDefinitions > 1) {
						Arrays.sort(definitionArray, 0, countOfDefinitions);
					}
					xlsFileName = file.getParent() + File.separator + "FunctionDoc" + getStringValueOfDateAndTime() + ".xlsx";
				}

				jProgressBar.setValue(0);
				jProgressBar.setMaximum(countOfDefinitions);

				FileOutputStream fileOut = new FileOutputStream(xlsFileName);
				createWorkBookAndStyles();

				if (jRadioButtonTable.isSelected()) {
					for (int m = 0; m < countOfDefinitions; m++) {
						Table table = (Table)definitionArray[m].getPojo();
						if (table.getSubsystemId().equals(subsystemID)) {
							if (table.getId().equals(tableID) || tableID.equals("")) {
								jProgressBar.setValue(jProgressBar.getValue() + 1);
								jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());
								generateTableDocument(table);
							}
						}
					}
				}

				if (jRadioButtonFunction.isSelected()) {
					for (int m = 0; m < countOfDefinitions; m++) {
						Function func = (Function)definitionArray[m].getPojo();
						if (func.getSubsystemId().equals(subsystemID)) {
							if (func.getId().equals(functionID) || functionID.equals("")) {
								jProgressBar.setValue(jProgressBar.getValue() + 1);
								jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());
								generateFunctionDocument(func);
							}
						}
					}
				}

				if (countOfDefinitions > 0) {
					workBook.write(fileOut);
				}
				fileOut.close();
			}
		} catch(Exception ex1) {
			ex1.printStackTrace();
		} finally {
			jProgressBar.setVisible(false);
			jButtonStart.setVisible(true);
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

			if (countOfErrors == 0 && countOfDefinitions > 0) {
				try {
					setCursor(new Cursor(Cursor.WAIT_CURSOR));
					File workXlsFile = new File(xlsFileName);
					frame_.desktop.open(workXlsFile);
				} catch (Exception ex) {
				} finally {
					setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}
			} else {
				if (countOfDefinitions > 0) {
					countOfDefinitions = countOfDefinitions - countOfErrors;
					JOptionPane.showMessageDialog(this.getContentPane(), countOfDefinitions + res.getString("DialogDocuments11") + "\n" + xlsFileName + "\n" + countOfErrors + res.getString("DialogDocuments12"));
				} else {
					JOptionPane.showMessageDialog(null, "No elements found to be processed.");
				}
			}
		}
	}

	void createWorkBookAndStyles() {
		workBook = new XSSFWorkbook();

		fontTitle = workBook.createFont();
		fontTitle.setFontName(res.getString("DialogDocuments13"));
		fontTitle.setFontHeightInPoints((short)14);
		fontTitle.setColor(new XSSFColor(new byte[]{(byte)0, (byte)0, (byte)0}));
 	fontTitle.setBold(true);

		fontHeader1 = workBook.createFont();
		fontHeader1.setFontName(res.getString("DialogDocuments13"));
		fontHeader1.setFontHeightInPoints((short)10);
		fontHeader1.setItalic(true);
		fontHeader1.setColor(new XSSFColor(new byte[]{(byte)0, (byte)0, (byte)0}));

		fontHeader2 = workBook.createFont();
		fontHeader2.setFontName(res.getString("DialogDocuments13"));
		fontHeader2.setFontHeightInPoints((short)10);

		fontValue = workBook.createFont();
		fontValue.setFontName(res.getString("DialogDocuments14"));
		fontValue.setFontHeightInPoints((short)10);

		fontValueStrong = workBook.createFont();
		fontValueStrong.setFontName(res.getString("DialogDocuments14"));
		fontValueStrong.setFontHeightInPoints((short)10);
 	fontValueStrong.setBold(true);

		fontImage = workBook.createFont();
		fontImage.setFontName(res.getString("DialogDocuments15"));
		fontImage.setFontHeightInPoints((short)6);

		fontImageUL = workBook.createFont();
		fontImageUL.setFontName(res.getString("DialogDocuments15"));
		fontImageUL.setFontHeightInPoints((short)6);
 	fontImageUL.setUnderline((byte)1);

		styleTitle = workBook.createCellStyle();
		styleTitle.setBorderBottom(BorderStyle.THIN);
		styleTitle.setBorderLeft(BorderStyle.THIN);
		styleTitle.setBorderRight(BorderStyle.THIN);
		styleTitle.setBorderTop(BorderStyle.THIN);
		styleTitle.setFillForegroundColor(new XSSFColor(new byte[]{(byte)64, (byte)64, (byte)64}));
		styleTitle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		styleTitle.setAlignment(HorizontalAlignment.CENTER);
		styleTitle.setVerticalAlignment(VerticalAlignment.CENTER);
		styleTitle.setFont(fontTitle);

		styleHeader1 = workBook.createCellStyle();
		styleHeader1.setBorderBottom(BorderStyle.THIN);
		styleHeader1.setBorderLeft(BorderStyle.THIN);
		styleHeader1.setBorderRight(BorderStyle.THIN);
		styleHeader1.setBorderTop(BorderStyle.THIN);
		styleHeader1.setFillForegroundColor(new XSSFColor(new byte[]{(byte)64, (byte)64, (byte)64}));
		styleHeader1.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		styleHeader1.setAlignment(HorizontalAlignment.LEFT);
		styleHeader1.setVerticalAlignment(VerticalAlignment.CENTER);
		styleHeader1.setFont(fontHeader1);

		styleHeader2 = workBook.createCellStyle();
		styleHeader2.setBorderBottom(BorderStyle.THIN);
		styleHeader2.setBorderLeft(BorderStyle.THIN);
		styleHeader2.setBorderRight(BorderStyle.THIN);
		styleHeader2.setBorderTop(BorderStyle.THIN);
		styleHeader2.setFillForegroundColor(new XSSFColor(new byte[]{(byte)192, (byte)192, (byte)192}));
		styleHeader2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		styleHeader2.setAlignment(HorizontalAlignment.LEFT);
		styleHeader2.setVerticalAlignment(VerticalAlignment.CENTER);
		styleHeader2.setFont(fontHeader2);

		styleHeader2Number = workBook.createCellStyle();
		styleHeader2Number.setBorderBottom(BorderStyle.THIN);
		styleHeader2Number.setBorderLeft(BorderStyle.THIN);
		styleHeader2Number.setBorderRight(BorderStyle.THIN);
		styleHeader2Number.setBorderTop(BorderStyle.THIN);
		styleHeader2Number.setFillForegroundColor(new XSSFColor(new byte[]{(byte)192, (byte)192, (byte)192}));
		styleHeader2Number.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		styleHeader2Number.setAlignment(HorizontalAlignment.RIGHT);
		styleHeader2Number.setVerticalAlignment(VerticalAlignment.CENTER);
		styleHeader2Number.setFont(fontHeader2);

		styleValue = workBook.createCellStyle();
		styleValue.setBorderBottom(BorderStyle.THIN);
		styleValue.setBorderLeft(BorderStyle.THIN);
		styleValue.setBorderRight(BorderStyle.THIN);
		styleValue.setBorderTop(BorderStyle.THIN);
		styleValue.setAlignment(HorizontalAlignment.LEFT);
		styleValue.setVerticalAlignment(VerticalAlignment.TOP);
		styleValue.setFont(fontValue);
		styleValue.setWrapText(true);

		styleValueStrong = workBook.createCellStyle();
		styleValueStrong.setBorderBottom(BorderStyle.THIN);
		styleValueStrong.setBorderLeft(BorderStyle.THIN);
		styleValueStrong.setBorderRight(BorderStyle.THIN);
		styleValueStrong.setBorderTop(BorderStyle.THIN);
		styleValueStrong.setAlignment(HorizontalAlignment.LEFT);
		styleValueStrong.setVerticalAlignment(VerticalAlignment.TOP);
		styleValueStrong.setFont(fontValueStrong);
		styleValueStrong.setWrapText(true);

		styleValueNumber = workBook.createCellStyle();
		styleValueNumber.setBorderBottom(BorderStyle.THIN);
		styleValueNumber.setBorderLeft(BorderStyle.THIN);
		styleValueNumber.setBorderRight(BorderStyle.THIN);
		styleValueNumber.setBorderTop(BorderStyle.THIN);
		styleValueNumber.setAlignment(HorizontalAlignment.RIGHT);
		styleValueNumber.setVerticalAlignment(VerticalAlignment.TOP);
		styleValueNumber.setFont(fontValue);

		styleImage = workBook.createCellStyle();
		styleImage.setBorderBottom(BorderStyle.THIN);
		styleImage.setBorderLeft(BorderStyle.THIN);
		styleImage.setBorderRight(BorderStyle.THIN);
		styleImage.setBorderTop(BorderStyle.THIN);
		styleImage.setAlignment(HorizontalAlignment.LEFT);
		styleImage.setVerticalAlignment(VerticalAlignment.TOP);
		styleImage.setFont(fontImage);
		styleImage.setWrapText(true);
	}

	void generateTableDocument(Table table) {
		try {
			XSSFSheet sheet = workBook.createSheet("(" + table.getSortKey() + ")" + table.getName());
			sheet.setDefaultRowHeight( (short) 300);
			sheet.setDefaultColumnWidth(9);
			sheet.setColumnWidth(0, 1100);
			sheet.setColumnWidth(1, 4100);
			sheet.setColumnWidth(3, 4100);
			Footer footer = sheet.getFooter();
			footer.setRight("(" + subsystemElement.getSortKey() + ")" + subsystemElement.getName() + " - (" + table.getSortKey() + ")" + table.getName() + "  Page &P / &N");

			setupTableSummary(sheet, table);
			setupTableFieldList(sheet, table);
			setupTableKeyList(sheet, table);
			setupTableUsageList(sheet, table);
			setupTableKeyDetails(sheet, table);
		}
		catch (Exception ex) {
			countOfErrors++;
		}
	}

	void generateFunctionDocument(Function func) {
		try {
			XSSFSheet sheet = workBook.createSheet("(" + func.getSortKey() + ")" + func.getName());
			sheet.setDefaultRowHeight( (short) 300);
			sheet.setDefaultColumnWidth(9);
			sheet.setColumnWidth(0, 1100);
			sheet.setColumnWidth(1, 4100);
			Footer footer = sheet.getFooter();
			footer.setRight("(" + subsystemElement.getSortKey() + ")" + subsystemElement.getName() + " - (" + func.getSortKey() + ")" + func.getName() + "  Page &P / &N");

			setupFunctionSummary(sheet, func);
			setupFunctionIOList(sheet, func);
			setupFunctionsCalledByThisFunction(sheet, func);
			setupFunctionsCallingThis(sheet, func);
			setupTasksUsingThisFunction(sheet, func);
			setupFunctionIODetails(sheet, func);
		}
		catch (Exception ex) {
			countOfErrors++;
		}
	}

	void setupFunctionSummary(XSSFSheet sheet, Function func) {
		String workString = "";

		XSSFRow row0 = sheet.createRow(0);
		XSSFRow row1 = sheet.createRow(1);
		XSSFRow row2 = sheet.createRow(2);
		XSSFRow row3 = sheet.createRow(3);
		XSSFRow row4 = sheet.createRow(4);
		XSSFRow row5 = sheet.createRow(5);
		XSSFRow row6 = sheet.createRow(6);
		XSSFRow row7 = sheet.createRow(7);
		XSSFRow row8 = sheet.createRow(8);
		XSSFRow row9 = sheet.createRow(9);

		//Title
		XSSFRichTextString title = new XSSFRichTextString(res.getString("DialogDocuments16"));
		XSSFCell cellA0 = row0.createCell(0);
		cellA0.setCellStyle(styleTitle);
		XSSFCell cellB0 = row0.createCell(1);
		cellB0.setCellStyle(styleTitle);
		XSSFCell cellA1 = row1.createCell(0);
		cellA1.setCellStyle(styleTitle);
		XSSFCell cellB1 = row1.createCell(1);
		cellB1.setCellStyle(styleTitle);
		cellA0.setCellValue(title);
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 1));

		//Label Subsystem
		XSSFCell cellC0 = row0.createCell(2);
		cellC0.setCellStyle(styleHeader2);
		XSSFCell cellD0 = row0.createCell(3);
		cellD0.setCellStyle(styleHeader2);
		XSSFCell cellE0 = row0.createCell(4);
		cellE0.setCellStyle(styleHeader2);
		XSSFCell cellF0 = row0.createCell(5);
		cellF0.setCellStyle(styleHeader2);
		XSSFCell cellG0 = row0.createCell(6);
		cellG0.setCellStyle(styleHeader2);
		XSSFCell cellH0 = row0.createCell(7);
		cellH0.setCellStyle(styleHeader2);
		XSSFCell cellI0 = row0.createCell(8);
		cellI0.setCellStyle(styleHeader2);
		cellC0.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments17")));
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 2, 8));

		//Value Subsystem
		XSSFCell cellC1 = row1.createCell(2);
		cellC1.setCellStyle(styleValue);
		XSSFCell cellD1 = row1.createCell(3);
		cellD1.setCellStyle(styleValue);
		XSSFCell cellE1 = row1.createCell(4);
		cellE1.setCellStyle(styleValue);
		XSSFCell cellF1 = row1.createCell(5);
		cellF1.setCellStyle(styleValue);
		XSSFCell cellG1 = row1.createCell(6);
		cellG1.setCellStyle(styleValue);
		XSSFCell cellH1 = row1.createCell(7);
		cellH1.setCellStyle(styleValue);
		XSSFCell cellI1 = row1.createCell(8);
		cellI1.setCellStyle(styleValue);
		cellC1.setCellValue(new XSSFRichTextString(subsystemElement.getSortKey() + " / " + subsystemElement.getName()));
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 2, 8));

		//Label Function
		XSSFCell cellA2 = row2.createCell(0);
		cellA2.setCellStyle(styleHeader2);
		XSSFCell cellB2 = row2.createCell(1);
		cellB2.setCellStyle(styleHeader2);
		XSSFCell cellC2 = row2.createCell(2);
		cellC2.setCellStyle(styleHeader2);
		XSSFCell cellD2 = row2.createCell(3);
		cellD2.setCellStyle(styleHeader2);
		XSSFCell cellE2 = row2.createCell(4);
		cellE2.setCellStyle(styleHeader2);
		cellA2.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments18")));
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 4));

		//Value Function
		XSSFCell cellA3 = row3.createCell(0);
		cellA3.setCellStyle(styleValue);
		XSSFCell cellB3 = row3.createCell(1);
		cellB3.setCellStyle(styleValue);
		XSSFCell cellC3 = row3.createCell(2);
		cellC3.setCellStyle(styleValue);
		XSSFCell cellD3 = row3.createCell(3);
		cellD3.setCellStyle(styleValue);
		XSSFCell cellE3 = row3.createCell(4);
		cellE3.setCellStyle(styleValue);
		cellA3.setCellValue(new XSSFRichTextString(func.getSortKey() + " / " + func.getName()));
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 4));

		//Label FunctionType
		XSSFCell cellF2 = row2.createCell(5);
		cellF2.setCellStyle(styleHeader2);
		XSSFCell cellG2 = row2.createCell(6);
		cellG2.setCellStyle(styleHeader2);
		XSSFCell cellH2 = row2.createCell(7);
		cellH2.setCellStyle(styleHeader2);
		XSSFCell cellI2 = row2.createCell(8);
		cellI2.setCellStyle(styleHeader2);
		cellF2.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments19")));
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 5, 8));

		//Value FunctionType
		XSSFCell cellF3 = row3.createCell(5);
		cellF3.setCellStyle(styleValue);
		XSSFCell cellG3 = row3.createCell(6);
		cellG3.setCellStyle(styleValue);
		XSSFCell cellH3 = row3.createCell(7);
		cellH3.setCellStyle(styleValue);
		XSSFCell cellI3 = row3.createCell(8);
		cellI3.setCellStyle(styleValue);
		for (FunctionType ft : functionTypeList) {
			if (ft.getId().equals(func.getFunctionTypeId())) {
				workString = ft.getSortKey() + " / " + ft.getName();
				break;
			}
		}
		cellF3.setCellValue(new XSSFRichTextString(workString));
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 5, 8));

		//Label Summary
		XSSFCell cellA4 = row4.createCell(0);
		cellA4.setCellStyle(styleHeader2);
		XSSFCell cellB4 = row4.createCell(1);
		cellB4.setCellStyle(styleHeader2);
		XSSFCell cellC4 = row4.createCell(2);
		cellC4.setCellStyle(styleHeader2);
		XSSFCell cellD4 = row4.createCell(3);
		cellD4.setCellStyle(styleHeader2);
		XSSFCell cellE4 = row4.createCell(4);
		cellE4.setCellStyle(styleHeader2);
		XSSFCell cellF4 = row4.createCell(5);
		cellF4.setCellStyle(styleHeader2);
		XSSFCell cellG4 = row4.createCell(6);
		cellG4.setCellStyle(styleHeader2);
		XSSFCell cellH4 = row4.createCell(7);
		cellH4.setCellStyle(styleHeader2);
		XSSFCell cellI4 = row4.createCell(8);
		cellI4.setCellStyle(styleHeader2);
		cellA4.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments20")));
		sheet.addMergedRegion(new CellRangeAddress(4, 4, 0, 8));

		//Value Summary
		XSSFCell cellA5 = row5.createCell(0);
		cellA5.setCellStyle(styleValue);
		XSSFCell cellB5 = row5.createCell(1);
		cellB5.setCellStyle(styleValue);
		XSSFCell cellC5 = row5.createCell(2);
		cellC5.setCellStyle(styleValue);
		XSSFCell cellD5 = row5.createCell(3);
		cellD5.setCellStyle(styleValue);
		XSSFCell cellE5 = row5.createCell(4);
		cellE5.setCellStyle(styleValue);
		XSSFCell cellF5 = row5.createCell(5);
		cellF5.setCellStyle(styleValue);
		XSSFCell cellG5 = row5.createCell(6);
		cellG5.setCellStyle(styleValue);
		XSSFCell cellH5 = row5.createCell(7);
		cellH5.setCellStyle(styleValue);
		XSSFCell cellI5 = row5.createCell(8);
		cellI5.setCellStyle(styleValue);
		cellA5.setCellValue(new XSSFRichTextString(func.getSummary() != null ? func.getSummary() : ""));
		sheet.addMergedRegion(new CellRangeAddress(5, 5, 0, 8));

		//Label Parameters
		XSSFCell cellA6 = row6.createCell(0);
		cellA6.setCellStyle(styleHeader2);
		XSSFCell cellB6 = row6.createCell(1);
		cellB6.setCellStyle(styleHeader2);
		XSSFCell cellC6 = row6.createCell(2);
		cellC6.setCellStyle(styleHeader2);
		XSSFCell cellD6 = row6.createCell(3);
		cellD6.setCellStyle(styleHeader2);
		XSSFCell cellE6 = row6.createCell(4);
		cellE6.setCellStyle(styleHeader2);
		XSSFCell cellF6 = row6.createCell(5);
		cellF6.setCellStyle(styleHeader2);
		cellA6.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments21")));
		sheet.addMergedRegion(new CellRangeAddress(6, 6, 0, 5));

		//Value Parameters
		XSSFCell cellA7 = row7.createCell(0);
		cellA7.setCellStyle(styleValue);
		XSSFCell cellB7 = row7.createCell(1);
		cellB7.setCellStyle(styleValue);
		XSSFCell cellC7 = row7.createCell(2);
		cellC7.setCellStyle(styleValue);
		XSSFCell cellD7 = row7.createCell(3);
		cellD7.setCellStyle(styleValue);
		XSSFCell cellE7 = row7.createCell(4);
		cellE7.setCellStyle(styleValue);
		XSSFCell cellF7 = row7.createCell(5);
		cellF7.setCellStyle(styleValue);
		cellA7.setCellValue(new XSSFRichTextString(func.getParameters() != null ? func.getParameters() : ""));
		sheet.addMergedRegion(new CellRangeAddress(7, 7, 0, 5));

		//Label Return
		XSSFCell cellG6 = row6.createCell(6);
		cellG6.setCellStyle(styleHeader2);
		XSSFCell cellH6 = row6.createCell(7);
		cellH6.setCellStyle(styleHeader2);
		XSSFCell cellI6 = row6.createCell(8);
		cellI6.setCellStyle(styleHeader2);
		cellG6.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments22")));
		sheet.addMergedRegion(new CellRangeAddress(6, 6, 6, 8));

		//Value Return
		XSSFCell cellG7 = row7.createCell(6);
		cellG7.setCellStyle(styleValue);
		XSSFCell cellH7 = row7.createCell(7);
		cellH7.setCellStyle(styleValue);
		XSSFCell cellI7 = row7.createCell(8);
		cellI7.setCellStyle(styleValue);
		cellG7.setCellValue(new XSSFRichTextString(func.getReturn() != null ? func.getReturn() : ""));
		sheet.addMergedRegion(new CellRangeAddress(7, 7, 6, 8));

		//Label Descriptions
		XSSFCell cellA8 = row8.createCell(0);
		cellA8.setCellStyle(styleHeader2);
		XSSFCell cellB8 = row8.createCell(1);
		cellB8.setCellStyle(styleHeader2);
		XSSFCell cellC8 = row8.createCell(2);
		cellC8.setCellStyle(styleHeader2);
		XSSFCell cellD8 = row8.createCell(3);
		cellD8.setCellStyle(styleHeader2);
		XSSFCell cellE8 = row8.createCell(4);
		cellE8.setCellStyle(styleHeader2);
		XSSFCell cellF8 = row8.createCell(5);
		cellF8.setCellStyle(styleHeader2);
		XSSFCell cellG8 = row8.createCell(6);
		cellG8.setCellStyle(styleHeader2);
		XSSFCell cellH8 = row8.createCell(7);
		cellH8.setCellStyle(styleHeader2);
		XSSFCell cellI8 = row8.createCell(8);
		cellI8.setCellStyle(styleHeader2);
		cellA8.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments23")));
		sheet.addMergedRegion(new CellRangeAddress(8, 8, 0, 8));

		//Value Descriptions
		XSSFCell cellA9 = row9.createCell(0);
		cellA9.setCellStyle(styleValue);
		XSSFCell cellB9 = row9.createCell(1);
		cellB9.setCellStyle(styleValue);
		XSSFCell cellC9 = row9.createCell(2);
		cellC9.setCellStyle(styleValue);
		XSSFCell cellD9 = row9.createCell(3);
		cellD9.setCellStyle(styleValue);
		XSSFCell cellE9 = row9.createCell(4);
		cellE9.setCellStyle(styleValue);
		XSSFCell cellF9 = row9.createCell(5);
		cellF9.setCellStyle(styleValue);
		XSSFCell cellG9 = row9.createCell(6);
		cellG9.setCellStyle(styleValue);
		XSSFCell cellH9 = row9.createCell(7);
		cellH9.setCellStyle(styleValue);
		XSSFCell cellI9 = row9.createCell(8);
		cellI9.setCellStyle(styleValue);
		workString = substringLinesWithTokenOfEOL(func.getDescriptions() != null ? func.getDescriptions() : "", "\n");
		cellA9.setCellValue(new XSSFRichTextString(workString));
		sheet.addMergedRegion(new CellRangeAddress(9, 9, 0, 8));
		row9.setHeight((short)1000);

		currentRowNumber = 9;
	}

	void setupFunctionIOList(XSSFSheet sheet, Function func) {
		String workString = "";
		int rowSequence = 0;
		JsonNode node = null;

		//Label IOList
		currentRowNumber++;
		XSSFRow row0 = sheet.createRow(currentRowNumber);
		XSSFCell cellA0 = row0.createCell(0);
		cellA0.setCellStyle(styleHeader1);
		XSSFCell cellB0 = row0.createCell(1);
		cellB0.setCellStyle(styleHeader1);
		XSSFCell cellC0 = row0.createCell(2);
		cellC0.setCellStyle(styleHeader1);
		XSSFCell cellD0 = row0.createCell(3);
		cellD0.setCellStyle(styleHeader1);
		XSSFCell cellE0 = row0.createCell(4);
		cellE0.setCellStyle(styleHeader1);
		XSSFCell cellF0 = row0.createCell(5);
		cellF0.setCellStyle(styleHeader1);
		XSSFCell cellG0 = row0.createCell(6);
		cellG0.setCellStyle(styleHeader1);
		XSSFCell cellH0 = row0.createCell(7);
		cellH0.setCellStyle(styleHeader1);
		XSSFCell cellI0 = row0.createCell(8);
		cellI0.setCellStyle(styleHeader1);
		cellA0.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments24")));
		sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 0, 8));

		//Header IOList
		currentRowNumber++;
		XSSFRow row1 = sheet.createRow(currentRowNumber);
		XSSFCell cellA1 = row1.createCell(0);
		cellA1.setCellStyle(styleHeader2Number);
		cellA1.setCellValue(new XSSFRichTextString("No."));
		XSSFCell cellB1 = row1.createCell(1);
		cellB1.setCellStyle(styleHeader2);
		XSSFCell cellC1 = row1.createCell(2);
		cellC1.setCellStyle(styleHeader2);
		XSSFCell cellD1 = row1.createCell(3);
		cellD1.setCellStyle(styleHeader2);
		cellB1.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments25")));
		sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 1, 3));
		XSSFCell cellE1 = row1.createCell(4);
		cellE1.setCellStyle(styleHeader2);
		cellE1.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments26")));
		XSSFCell cellF1 = row1.createCell(5);
		cellF1.setCellStyle(styleHeader2);
		XSSFCell cellG1 = row1.createCell(6);
		cellG1.setCellStyle(styleHeader2);
		XSSFCell cellH1 = row1.createCell(7);
		cellH1.setCellStyle(styleHeader2);
		XSSFCell cellI1 = row1.createCell(8);
		cellI1.setCellStyle(styleHeader2);
		cellF1.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments23")));
		sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 5, 8));

		countOfElementArray1 = -1;
		if (func.getIOPanels() != null) {
			for (IOPanel ioPanel : func.getIOPanels()) {
				countOfElementArray1++;
				node = new JsonNode("IOPanel", ioPanel);
				elementArray1[countOfElementArray1] = node;
			}
		}
		if (func.getIOSpools() != null) {
			for (IOSpool ioSpool : func.getIOSpools()) {
				countOfElementArray1++;
				node = new JsonNode("IOSpool", ioSpool);
				elementArray1[countOfElementArray1] = node;
			}
		}
		if (func.getIOWebPages() != null) {
			for (IOWebPage ioWebPage : func.getIOWebPages()) {
				countOfElementArray1++;
				node = new JsonNode("IOWebPage", ioWebPage);
				elementArray1[countOfElementArray1] = node;
			}
		}
		if (func.getIOTables() != null) {
			for (IOTable ioTable : func.getIOTables()) {
				countOfElementArray1++;
				node = new JsonNode("IOTable", ioTable);
				elementArray1[countOfElementArray1] = node;
			}
		}
		if (countOfElementArray1 > 0) {
			Arrays.sort(elementArray1, 0, countOfElementArray1 + 1);
		}

		rowSequence = 0;
		for (int i = 0; i <= countOfElementArray1; i++) {
			currentRowNumber++;
			XSSFRow nextRow = sheet.createRow(currentRowNumber);
			Object ioPojo = elementArray1[i].getPojo();
			rowSequence++;
			XSSFCell cellA = nextRow.createCell(0);
			cellA.setCellStyle(styleValueNumber);
			cellA.setCellValue(rowSequence);
			XSSFCell cellB = nextRow.createCell(1);
			cellB.setCellStyle(styleValue);
			XSSFCell cellC = nextRow.createCell(2);
			cellC.setCellStyle(styleValue);
			XSSFCell cellD = nextRow.createCell(3);
			cellD.setCellStyle(styleValue);
			workString = "";
			if (elementArray1[i].getType().equals("IOTable")) {
				IOTable ioTable = (IOTable)ioPojo;
				for (Table table : tableList) {
					if (table.getId().equals(ioTable.getTableId())) {
						if (ioTable.getNameExtension() == null || ioTable.getNameExtension().equals("")) {
							workString = table.getSortKey() + " " + table.getName();
						} else {
							workString = table.getSortKey() + ":" + ioTable.getNameExtension() + " " + table.getName();
						}
						break;
					}
				}
			} else {
				workString = elementArray1[i].getName();
			}
			cellB.setCellValue(new XSSFRichTextString(workString));
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 1, 3));
			XSSFCell cellE = nextRow.createCell(4);
			cellE.setCellStyle(styleValue);
			workString = "";
			if (elementArray1[i].getType().equals("IOPanel")) {
				workString = res.getString("DialogDocuments27");
			}
			if (elementArray1[i].getType().equals("IOSpool")) {
				workString = res.getString("DialogDocuments28");
			}
			if (elementArray1[i].getType().equals("IOWebPage")) {
				workString = res.getString("DialogDocuments29");
			}
			if (elementArray1[i].getType().equals("IOTable")) {
				IOTable ioTable = (IOTable)ioPojo;
				workString = "[";
				if ("+".equals(ioTable.getOpC())) workString = workString + "C";
				if ("+".equals(ioTable.getOpR())) workString = workString + "R";
				if ("+".equals(ioTable.getOpU())) workString = workString + "U";
				if ("+".equals(ioTable.getOpD())) workString = workString + "D";
				workString = workString + "]";
			}
			cellE.setCellValue(new XSSFRichTextString(workString));
			XSSFCell cellF = nextRow.createCell(5);
			cellF.setCellStyle(styleValue);
			XSSFCell cellG = nextRow.createCell(6);
			cellG.setCellStyle(styleValue);
			XSSFCell cellH = nextRow.createCell(7);
			cellH.setCellStyle(styleValue);
			XSSFCell cellI = nextRow.createCell(8);
			cellI.setCellStyle(styleValue);
			String descriptions = "";
			if (ioPojo instanceof IOPanel) descriptions = ((IOPanel)ioPojo).getDescriptions() != null ? ((IOPanel)ioPojo).getDescriptions() : "";
			if (ioPojo instanceof IOSpool) descriptions = ((IOSpool)ioPojo).getDescriptions() != null ? ((IOSpool)ioPojo).getDescriptions() : "";
			if (ioPojo instanceof IOWebPage) descriptions = ((IOWebPage)ioPojo).getDescriptions() != null ? ((IOWebPage)ioPojo).getDescriptions() : "";
			if (ioPojo instanceof IOTable) descriptions = ((IOTable)ioPojo).getDescriptions() != null ? ((IOTable)ioPojo).getDescriptions() : "";
			workString = substringLinesWithTokenOfEOL(descriptions, "\n");
			cellF.setCellValue(new XSSFRichTextString(workString));
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 5, 8));
		}

		if (rowSequence == 0) {
			currentRowNumber++;
			XSSFRow nextRow = sheet.createRow(currentRowNumber);
			XSSFCell cellA = nextRow.createCell(0);
			cellA.setCellStyle(styleValueNumber);
			XSSFCell cellB = nextRow.createCell(1);
			cellB.setCellStyle(styleValue);
			XSSFCell cellC = nextRow.createCell(2);
			cellC.setCellStyle(styleValue);
			XSSFCell cellD = nextRow.createCell(3);
			cellD.setCellStyle(styleValue);
			cellB.setCellValue(new XSSFRichTextString("(None)"));
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 1, 3));
			XSSFCell cellE = nextRow.createCell(4);
			cellE.setCellStyle(styleValue);
			XSSFCell cellF = nextRow.createCell(5);
			cellF.setCellStyle(styleValue);
			XSSFCell cellG = nextRow.createCell(6);
			cellG.setCellStyle(styleValue);
			XSSFCell cellH = nextRow.createCell(7);
			cellH.setCellStyle(styleValue);
			XSSFCell cellI = nextRow.createCell(8);
			cellI.setCellStyle(styleValue);
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 5, 8));
		}
	}

	void setupFunctionsCalledByThisFunction(XSSFSheet sheet, Function func) {
		String workString1 = "";
		String workString2 = "";
		int rowSequence = 0;

		//Label FunctionsCalledByThis
		currentRowNumber++;
		XSSFRow rowLabel = sheet.createRow(currentRowNumber);
		XSSFCell cellA0 = rowLabel.createCell(0);
		cellA0.setCellStyle(styleHeader1);
		XSSFCell cellB0 = rowLabel.createCell(1);
		cellB0.setCellStyle(styleHeader1);
		XSSFCell cellC0 = rowLabel.createCell(2);
		cellC0.setCellStyle(styleHeader1);
		XSSFCell cellD0 = rowLabel.createCell(3);
		cellD0.setCellStyle(styleHeader1);
		XSSFCell cellE0 = rowLabel.createCell(4);
		cellE0.setCellStyle(styleHeader1);
		XSSFCell cellF0 = rowLabel.createCell(5);
		cellF0.setCellStyle(styleHeader1);
		XSSFCell cellG0 = rowLabel.createCell(6);
		cellG0.setCellStyle(styleHeader1);
		XSSFCell cellH0 = rowLabel.createCell(7);
		cellH0.setCellStyle(styleHeader1);
		XSSFCell cellI0 = rowLabel.createCell(8);
		cellI0.setCellStyle(styleHeader1);
		cellA0.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments30")));
		sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 0, 8));

		//Header FunctionsCalledByThis
		currentRowNumber++;
		XSSFRow rowColumnHeading = sheet.createRow(currentRowNumber);
		XSSFCell cellA1 = rowColumnHeading.createCell(0);
		cellA1.setCellStyle(styleHeader2Number);
		cellA1.setCellValue(new XSSFRichTextString("No."));
		XSSFCell cellB1 = rowColumnHeading.createCell(1);
		cellB1.setCellStyle(styleHeader2);
		XSSFCell cellC1 = rowColumnHeading.createCell(2);
		cellC1.setCellStyle(styleHeader2);
		XSSFCell cellD1 = rowColumnHeading.createCell(3);
		cellD1.setCellStyle(styleHeader2);
		cellB1.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments18")));
		sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 1, 3));
		XSSFCell cellE1 = rowColumnHeading.createCell(4);
		cellE1.setCellStyle(styleHeader2);
		XSSFCell cellF1 = rowColumnHeading.createCell(5);
		cellF1.setCellStyle(styleHeader2);
		cellE1.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments21")));
		sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 4, 5));
		XSSFCell cellG1 = rowColumnHeading.createCell(6);
		cellG1.setCellStyle(styleHeader2);
		XSSFCell cellH1 = rowColumnHeading.createCell(7);
		cellH1.setCellStyle(styleHeader2);
		XSSFCell cellI1 = rowColumnHeading.createCell(8);
		cellI1.setCellStyle(styleHeader2);
		cellG1.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments31")));
		sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 6, 8));

		//Rows of FunctionsCalledByThis
		List<FunctionUsedByThis> futList = func.getFunctionUsedByThisList();
		if (futList != null) {
			for (FunctionUsedByThis fut : futList) {
				currentRowNumber++;
				XSSFRow nextRow = sheet.createRow(currentRowNumber);
				XSSFCell cellA = nextRow.createCell(0);
				cellA.setCellStyle(styleValueNumber);
				rowSequence++;
				cellA.setCellValue(rowSequence);
				XSSFCell cellB = nextRow.createCell(1);
				cellB.setCellStyle(styleValue);
				XSSFCell cellC = nextRow.createCell(2);
				cellC.setCellStyle(styleValue);
				XSSFCell cellD = nextRow.createCell(3);
				cellD.setCellStyle(styleValue);
				workString1 = "";
				workString2 = "";
				for (Function calledFunc : functionList) {
					if (calledFunc.getId().equals(fut.getFunctionId())) {
						workString1 = calledFunc.getSortKey() + " " + calledFunc.getName();
						workString2 = calledFunc.getParameters() != null ? calledFunc.getParameters() : "";
						break;
					}
				}
				cellB.setCellValue(new XSSFRichTextString(workString1));
				sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 1, 3));
				XSSFCell cellE = nextRow.createCell(4);
				cellE.setCellStyle(styleValue);
				XSSFCell cellF = nextRow.createCell(5);
				cellF.setCellStyle(styleValue);
				cellE.setCellValue(new XSSFRichTextString(workString2));
				sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 4, 5));
				XSSFCell cellG = nextRow.createCell(6);
				cellG.setCellStyle(styleValue);
				XSSFCell cellH = nextRow.createCell(7);
				cellH.setCellStyle(styleValue);
				XSSFCell cellI = nextRow.createCell(8);
				cellI.setCellStyle(styleValue);
				cellG.setCellValue(new XSSFRichTextString(fut.getLaunchEvent() != null ? fut.getLaunchEvent() : ""));
				sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 6, 8));
			}
		}

		if (rowSequence == 0) {
			currentRowNumber++;
			XSSFRow nextRow = sheet.createRow(currentRowNumber);
			XSSFCell cellA2 = nextRow.createCell(0);
			cellA2.setCellStyle(styleValueNumber);
			XSSFCell cellB2 = nextRow.createCell(1);
			cellB2.setCellStyle(styleValue);
			XSSFCell cellC2 = nextRow.createCell(2);
			cellC2.setCellStyle(styleValue);
			XSSFCell cellD2 = nextRow.createCell(3);
			cellD2.setCellStyle(styleValue);
			cellB2.setCellValue(new XSSFRichTextString("(None)"));
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 1, 3));
			XSSFCell cellE2 = nextRow.createCell(4);
			cellE2.setCellStyle(styleValue);
			XSSFCell cellF2 = nextRow.createCell(5);
			cellF2.setCellStyle(styleValue);
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 4, 5));
			XSSFCell cellG2 = nextRow.createCell(6);
			cellG2.setCellStyle(styleValue);
			XSSFCell cellH2 = nextRow.createCell(7);
			cellH2.setCellStyle(styleValue);
			XSSFCell cellI2 = nextRow.createCell(8);
			cellI2.setCellStyle(styleValue);
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 6, 8));
		}
	}

	void setupFunctionsCallingThis(XSSFSheet sheet, Function func) {
		int rowSequence = 0;

		//Label FunctionsCallingThis
		currentRowNumber++;
		XSSFRow rowLabel = sheet.createRow(currentRowNumber);
		XSSFCell cellA0 = rowLabel.createCell(0);
		cellA0.setCellStyle(styleHeader1);
		XSSFCell cellB0 = rowLabel.createCell(1);
		cellB0.setCellStyle(styleHeader1);
		XSSFCell cellC0 = rowLabel.createCell(2);
		cellC0.setCellStyle(styleHeader1);
		XSSFCell cellD0 = rowLabel.createCell(3);
		cellD0.setCellStyle(styleHeader1);
		XSSFCell cellE0 = rowLabel.createCell(4);
		cellE0.setCellStyle(styleHeader1);
		XSSFCell cellF0 = rowLabel.createCell(5);
		cellF0.setCellStyle(styleHeader1);
		XSSFCell cellG0 = rowLabel.createCell(6);
		cellG0.setCellStyle(styleHeader1);
		XSSFCell cellH0 = rowLabel.createCell(7);
		cellH0.setCellStyle(styleHeader1);
		XSSFCell cellI0 = rowLabel.createCell(8);
		cellI0.setCellStyle(styleHeader1);
		cellA0.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments32")));
		sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 0, 8));

		//Header FunctionsCallingThis
		currentRowNumber++;
		XSSFRow rowColumnHeading = sheet.createRow(currentRowNumber);
		XSSFCell cellA1 = rowColumnHeading.createCell(0);
		cellA1.setCellStyle(styleHeader2Number);
		cellA1.setCellValue(new XSSFRichTextString("No."));
		XSSFCell cellB1 = rowColumnHeading.createCell(1);
		cellB1.setCellStyle(styleHeader2);
		XSSFCell cellC1 = rowColumnHeading.createCell(2);
		cellC1.setCellStyle(styleHeader2);
		XSSFCell cellD1 = rowColumnHeading.createCell(3);
		cellD1.setCellStyle(styleHeader2);
		cellB1.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments18")));
		sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 1, 3));
		XSSFCell cellE1 = rowColumnHeading.createCell(4);
		cellE1.setCellStyle(styleHeader2);
		XSSFCell cellF1 = rowColumnHeading.createCell(5);
		cellF1.setCellStyle(styleHeader2);
		XSSFCell cellG1 = rowColumnHeading.createCell(6);
		cellG1.setCellStyle(styleHeader2);
		XSSFCell cellH1 = rowColumnHeading.createCell(7);
		cellH1.setCellStyle(styleHeader2);
		XSSFCell cellI1 = rowColumnHeading.createCell(8);
		cellI1.setCellStyle(styleHeader2);
		cellE1.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments31")));
		sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 4, 8));

		for (Function otherFunc : functionList) {
			List<FunctionUsedByThis> futList = otherFunc.getFunctionUsedByThisList();
			if (futList != null) {
				for (FunctionUsedByThis fut : futList) {
					if (fut.getFunctionId().equals(func.getId())) {
						currentRowNumber++;
						XSSFRow nextRow = sheet.createRow(currentRowNumber);
						XSSFCell cellA = nextRow.createCell(0);
						cellA.setCellStyle(styleValueNumber);
						rowSequence++;
						cellA.setCellValue(rowSequence);
						XSSFCell cellB = nextRow.createCell(1);
						cellB.setCellStyle(styleValue);
						XSSFCell cellC = nextRow.createCell(2);
						cellC.setCellStyle(styleValue);
						XSSFCell cellD = nextRow.createCell(3);
						cellD.setCellStyle(styleValue);
						cellB.setCellValue(new XSSFRichTextString(otherFunc.getSortKey() + " " + otherFunc.getName()));
						sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 1, 3));
						XSSFCell cellE = nextRow.createCell(4);
						cellE.setCellStyle(styleValue);
						XSSFCell cellF = nextRow.createCell(5);
						cellF.setCellStyle(styleValue);
						XSSFCell cellG = nextRow.createCell(6);
						cellG.setCellStyle(styleValue);
						XSSFCell cellH = nextRow.createCell(7);
						cellH.setCellStyle(styleValue);
						XSSFCell cellI = nextRow.createCell(8);
						cellI.setCellStyle(styleValue);
						cellE.setCellValue(new XSSFRichTextString(fut.getLaunchEvent() != null ? fut.getLaunchEvent() : ""));
						sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 4, 8));
					}
				}
			}
		}

		if (rowSequence == 0) {
			currentRowNumber++;
			XSSFRow nextRow = sheet.createRow(currentRowNumber);
			XSSFCell cellA2 = nextRow.createCell(0);
			cellA2.setCellStyle(styleValueNumber);
			XSSFCell cellB2 = nextRow.createCell(1);
			cellB2.setCellStyle(styleValue);
			XSSFCell cellC2 = nextRow.createCell(2);
			cellC2.setCellStyle(styleValue);
			XSSFCell cellD2 = nextRow.createCell(3);
			cellD2.setCellStyle(styleValue);
			cellB2.setCellValue(new XSSFRichTextString("(None)"));
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 1, 3));
			XSSFCell cellE2 = nextRow.createCell(4);
			cellE2.setCellStyle(styleValue);
			XSSFCell cellF2 = nextRow.createCell(5);
			cellF2.setCellStyle(styleValue);
			XSSFCell cellG2 = nextRow.createCell(6);
			cellG2.setCellStyle(styleValue);
			XSSFCell cellH2 = nextRow.createCell(7);
			cellH2.setCellStyle(styleValue);
			XSSFCell cellI2 = nextRow.createCell(8);
			cellI2.setCellStyle(styleValue);
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 4, 8));
		}
	}

	void setupTasksUsingThisFunction(XSSFSheet sheet, Function func) {
		String workString1 = "";
		int rowSequence = 0;

		//Label TasksUsingThisFunction
		currentRowNumber++;
		XSSFRow rowLabel = sheet.createRow(currentRowNumber);
		XSSFCell cellA0 = rowLabel.createCell(0);
		cellA0.setCellStyle(styleHeader1);
		XSSFCell cellB0 = rowLabel.createCell(1);
		cellB0.setCellStyle(styleHeader1);
		XSSFCell cellC0 = rowLabel.createCell(2);
		cellC0.setCellStyle(styleHeader1);
		XSSFCell cellD0 = rowLabel.createCell(3);
		cellD0.setCellStyle(styleHeader1);
		XSSFCell cellE0 = rowLabel.createCell(4);
		cellE0.setCellStyle(styleHeader1);
		XSSFCell cellF0 = rowLabel.createCell(5);
		cellF0.setCellStyle(styleHeader1);
		XSSFCell cellG0 = rowLabel.createCell(6);
		cellG0.setCellStyle(styleHeader1);
		XSSFCell cellH0 = rowLabel.createCell(7);
		cellH0.setCellStyle(styleHeader1);
		XSSFCell cellI0 = rowLabel.createCell(8);
		cellI0.setCellStyle(styleHeader1);
		cellA0.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments33")));
		sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 0, 8));

		//Header TasksUsingThisFunction
		currentRowNumber++;
		XSSFRow rowColumnHeading = sheet.createRow(currentRowNumber);
		XSSFCell cellA1 = rowColumnHeading.createCell(0);
		cellA1.setCellStyle(styleHeader2Number);
		cellA1.setCellValue(new XSSFRichTextString("No."));
		XSSFCell cellB1 = rowColumnHeading.createCell(1);
		cellB1.setCellStyle(styleHeader2);
		XSSFCell cellC1 = rowColumnHeading.createCell(2);
		cellC1.setCellStyle(styleHeader2);
		cellB1.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments34")));
		sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 1, 2));
		XSSFCell cellD1 = rowColumnHeading.createCell(3);
		cellD1.setCellStyle(styleHeader2);
		XSSFCell cellE1 = rowColumnHeading.createCell(4);
		cellE1.setCellStyle(styleHeader2);
		cellD1.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments35")));
		sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 3, 4));
		XSSFCell cellF1 = rowColumnHeading.createCell(5);
		cellF1.setCellStyle(styleHeader2);
		XSSFCell cellG1 = rowColumnHeading.createCell(6);
		cellG1.setCellStyle(styleHeader2);
		XSSFCell cellH1 = rowColumnHeading.createCell(7);
		cellH1.setCellStyle(styleHeader2);
		XSSFCell cellI1 = rowColumnHeading.createCell(8);
		cellI1.setCellStyle(styleHeader2);
		cellF1.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments36")));
		sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 5, 8));

		for (Task task : taskList) {
			List<TaskAction> taskActions = task.getTaskActions();
			if (taskActions == null) continue;
			for (TaskAction taskAction : taskActions) {
				List<TaskFunctionIO> taskFunctionIOs = taskAction.getTaskFunctionIOs();
				if (taskFunctionIOs == null) continue;
				for (TaskFunctionIO tfio : taskFunctionIOs) {
					if (tfio.getFunctionId().equals(func.getId())) {
						currentRowNumber++;
						XSSFRow nextRow = sheet.createRow(currentRowNumber);
						XSSFCell cellA = nextRow.createCell(0);
						cellA.setCellStyle(styleValueNumber);
						rowSequence++;
						cellA.setCellValue(rowSequence);
						XSSFCell cellB = nextRow.createCell(1);
						cellB.setCellStyle(styleValue);
						XSSFCell cellC = nextRow.createCell(2);
						cellC.setCellStyle(styleValue);
						workString1 = "";
						for (Role role : roleList) {
							if (role.getId().equals(task.getRoleId())) {
								workString1 = role.getName();
								break;
							}
						}
						cellB.setCellValue(new XSSFRichTextString(workString1));
						sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 1, 2));
						XSSFCell cellD = nextRow.createCell(3);
						cellD.setCellStyle(styleValue);
						XSSFCell cellE = nextRow.createCell(4);
						cellE.setCellStyle(styleValue);
						cellD.setCellValue(new XSSFRichTextString(task.getName()));
						sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 3, 4));
						XSSFCell cellF = nextRow.createCell(5);
						cellF.setCellStyle(styleValue);
						XSSFCell cellG = nextRow.createCell(6);
						cellG.setCellStyle(styleValue);
						XSSFCell cellH = nextRow.createCell(7);
						cellH.setCellStyle(styleValue);
						XSSFCell cellI = nextRow.createCell(8);
						cellI.setCellStyle(styleValue);
						cellF.setCellValue(new XSSFRichTextString(taskAction.getLabel() != null ? taskAction.getLabel() : ""));
						sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 5, 8));
					}
				}
			}
		}

		if (rowSequence == 0) {
			currentRowNumber++;
			XSSFRow nextRow = sheet.createRow(currentRowNumber);
			XSSFCell cellA2 = nextRow.createCell(0);
			cellA2.setCellStyle(styleValueNumber);
			XSSFCell cellB2 = nextRow.createCell(1);
			cellB2.setCellStyle(styleValue);
			XSSFCell cellC2 = nextRow.createCell(2);
			cellC2.setCellStyle(styleValue);
			cellB2.setCellValue(new XSSFRichTextString("(None)"));
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 1, 2));
			XSSFCell cellD2 = nextRow.createCell(3);
			cellD2.setCellStyle(styleValue);
			XSSFCell cellE2 = nextRow.createCell(4);
			cellE2.setCellStyle(styleValue);
			XSSFCell cellF2 = nextRow.createCell(5);
			cellF2.setCellStyle(styleValue);
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 3, 5));
			XSSFCell cellG2 = nextRow.createCell(6);
			cellG2.setCellStyle(styleValue);
			XSSFCell cellH2 = nextRow.createCell(7);
			cellH2.setCellStyle(styleValue);
			XSSFCell cellI2 = nextRow.createCell(8);
			cellI2.setCellStyle(styleValue);
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 6, 8));
		}
	}

	void setupFunctionIODetails(XSSFSheet sheet, Function func) {
		String workString1 = "";
		String workString2 = "";
		String workString3 = "";
		int rowSequence = 0;
		int sectionNumber = 0;
		int totalSectionNumber = countOfElementArray1 + 1;
		int start = 0;
		int end = 0;

		for (int i = 0; i <= countOfElementArray1; i++) {
			Object ioPojo = elementArray1[i].getPojo();
			sectionNumber++;

			currentRowNumber++;
			XSSFRow rowLabel = sheet.createRow(currentRowNumber);
			XSSFCell cellA0 = rowLabel.createCell(0);
			cellA0.setCellStyle(styleHeader1);
			XSSFCell cellB0 = rowLabel.createCell(1);
			cellB0.setCellStyle(styleHeader1);
			XSSFCell cellC0 = rowLabel.createCell(2);
			cellC0.setCellStyle(styleHeader1);
			XSSFCell cellD0 = rowLabel.createCell(3);
			cellD0.setCellStyle(styleHeader1);
			XSSFCell cellE0 = rowLabel.createCell(4);
			cellE0.setCellStyle(styleHeader1);
			XSSFCell cellF0 = rowLabel.createCell(5);
			cellF0.setCellStyle(styleHeader1);
			XSSFCell cellG0 = rowLabel.createCell(6);
			cellG0.setCellStyle(styleHeader1);
			XSSFCell cellH0 = rowLabel.createCell(7);
			cellH0.setCellStyle(styleHeader1);
			XSSFCell cellI0 = rowLabel.createCell(8);
			cellI0.setCellStyle(styleHeader1);
			cellA0.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments37") + "(" + sectionNumber + "/" + totalSectionNumber + ")"));
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 0, 8));

			currentRowNumber++;
			XSSFRow rowHeading1 = sheet.createRow(currentRowNumber);
			XSSFCell cellA1 = rowHeading1.createCell(0);
			cellA1.setCellStyle(styleHeader2);
			XSSFCell cellB1 = rowHeading1.createCell(1);
			cellB1.setCellStyle(styleHeader2);
			XSSFCell cellC1 = rowHeading1.createCell(2);
			cellC1.setCellStyle(styleHeader2);
			XSSFCell cellD1 = rowHeading1.createCell(3);
			cellD1.setCellStyle(styleHeader2);
			XSSFCell cellE1 = rowHeading1.createCell(4);
			cellE1.setCellStyle(styleHeader2);
			XSSFCell cellF1 = rowHeading1.createCell(5);
			cellF1.setCellStyle(styleHeader2);
			XSSFCell cellG1 = rowHeading1.createCell(6);
			cellG1.setCellStyle(styleHeader2);
			cellA1.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments25")));
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 0, 6));
			XSSFCell cellH1 = rowHeading1.createCell(7);
			cellH1.setCellStyle(styleHeader2);
			XSSFCell cellI1 = rowHeading1.createCell(8);
			cellI1.setCellStyle(styleHeader2);
			cellH1.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments38")));
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 7, 8));

			currentRowNumber++;
			XSSFRow rowValue1 = sheet.createRow(currentRowNumber);
			XSSFCell cellA2 = rowValue1.createCell(0);
			cellA2.setCellStyle(styleValue);
			XSSFCell cellB2 = rowValue1.createCell(1);
			cellB2.setCellStyle(styleValue);
			XSSFCell cellC2 = rowValue1.createCell(2);
			cellC2.setCellStyle(styleValue);
			XSSFCell cellD2 = rowValue1.createCell(3);
			cellD2.setCellStyle(styleValue);
			XSSFCell cellE2 = rowValue1.createCell(4);
			cellE2.setCellStyle(styleValue);
			XSSFCell cellF2 = rowValue1.createCell(5);
			cellF2.setCellStyle(styleValue);
			XSSFCell cellG2 = rowValue1.createCell(6);
			cellG2.setCellStyle(styleValue);
			workString1 = "";
			if (elementArray1[i].getType().equals("IOTable")) {
				IOTable ioTable = (IOTable)ioPojo;
				for (Table table : tableList) {
					if (table.getId().equals(ioTable.getTableId())) {
						workString1 = table.getSortKey() + ioTable.getNameExtension() + " / " + table.getName();
						break;
					}
				}
			} else {
				workString1 = elementArray1[i].getName();
			}
			cellA2.setCellValue(new XSSFRichTextString(workString1));
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 0, 6));
			XSSFCell cellH2 = rowValue1.createCell(7);
			cellH2.setCellStyle(styleValue);
			XSSFCell cellI2 = rowValue1.createCell(8);
			cellI2.setCellStyle(styleValue);
			workString1 = "";
			if (elementArray1[i].getType().equals("IOPanel")) {
				workString1 = res.getString("DialogDocuments27");
			}
			if (elementArray1[i].getType().equals("IOSpool")) {
				workString1 = res.getString("DialogDocuments28");
			}
			if (elementArray1[i].getType().equals("IOWebPage")) {
				workString1 = res.getString("DialogDocuments29");
			}
			if (elementArray1[i].getType().equals("IOTable")) {
				IOTable ioTable = (IOTable)ioPojo;
				workString1 = "Table[";
				if ("+".equals(ioTable.getOpC())) workString1 = workString1 + "C";
				if ("+".equals(ioTable.getOpR())) workString1 = workString1 + "R";
				if ("+".equals(ioTable.getOpU())) workString1 = workString1 + "U";
				if ("+".equals(ioTable.getOpD())) workString1 = workString1 + "D";
				workString1 = workString1 + "]";
			}
			cellH2.setCellValue(new XSSFRichTextString(workString1));
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 7, 8));

			currentRowNumber++;
			XSSFRow rowLabelHeading2 = sheet.createRow(currentRowNumber);
			XSSFCell cellA3 = rowLabelHeading2.createCell(0);
			cellA3.setCellStyle(styleHeader2);
			XSSFCell cellB3 = rowLabelHeading2.createCell(1);
			cellB3.setCellStyle(styleHeader2);
			XSSFCell cellC3 = rowLabelHeading2.createCell(2);
			cellC3.setCellStyle(styleHeader2);
			XSSFCell cellD3 = rowLabelHeading2.createCell(3);
			cellD3.setCellStyle(styleHeader2);
			XSSFCell cellE3 = rowLabelHeading2.createCell(4);
			cellE3.setCellStyle(styleHeader2);
			XSSFCell cellF3 = rowLabelHeading2.createCell(5);
			cellF3.setCellStyle(styleHeader2);
			XSSFCell cellG3 = rowLabelHeading2.createCell(6);
			cellG3.setCellStyle(styleHeader2);
			XSSFCell cellH3 = rowLabelHeading2.createCell(7);
			cellH3.setCellStyle(styleHeader2);
			XSSFCell cellI3 = rowLabelHeading2.createCell(8);
			cellI3.setCellStyle(styleHeader2);
			cellA3.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments23")));
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 0, 8));

			currentRowNumber++;
			XSSFRow rowValueDescriptions = sheet.createRow(currentRowNumber);
			XSSFCell cellA4 = rowValueDescriptions.createCell(0);
			cellA4.setCellStyle(styleValue);
			XSSFCell cellB4 = rowValueDescriptions.createCell(1);
			cellB4.setCellStyle(styleValue);
			XSSFCell cellC4 = rowValueDescriptions.createCell(2);
			cellC4.setCellStyle(styleValue);
			XSSFCell cellD4 = rowValueDescriptions.createCell(3);
			cellD4.setCellStyle(styleValue);
			XSSFCell cellE4 = rowValueDescriptions.createCell(4);
			cellE4.setCellStyle(styleValue);
			XSSFCell cellF4 = rowValueDescriptions.createCell(5);
			cellF4.setCellStyle(styleValue);
			XSSFCell cellG4 = rowValueDescriptions.createCell(6);
			cellG4.setCellStyle(styleValue);
			XSSFCell cellH4 = rowValueDescriptions.createCell(7);
			cellH4.setCellStyle(styleValue);
			XSSFCell cellI4 = rowValueDescriptions.createCell(8);
			cellI4.setCellStyle(styleValue);
			String descriptions = "";
			if (ioPojo instanceof IOPanel) descriptions = ((IOPanel)ioPojo).getDescriptions() != null ? ((IOPanel)ioPojo).getDescriptions() : "";
			if (ioPojo instanceof IOSpool) descriptions = ((IOSpool)ioPojo).getDescriptions() != null ? ((IOSpool)ioPojo).getDescriptions() : "";
			if (ioPojo instanceof IOWebPage) descriptions = ((IOWebPage)ioPojo).getDescriptions() != null ? ((IOWebPage)ioPojo).getDescriptions() : "";
			if (ioPojo instanceof IOTable) descriptions = ((IOTable)ioPojo).getDescriptions() != null ? ((IOTable)ioPojo).getDescriptions() : "";
			workString1 = substringLinesWithTokenOfEOL(descriptions, "\n");
			cellA4.setCellValue(new XSSFRichTextString(workString1));
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 0, 8));
			rowValueDescriptions.setHeight((short)1000);

			if (elementArray1[i].getType().equals("IOPanel") || elementArray1[i].getType().equals("IOSpool") || elementArray1[i].getType().equals("IOWebPage")) {
				currentRowNumber++;
				XSSFRow rowLabelImage = sheet.createRow(currentRowNumber);
				XSSFCell cellA5 = rowLabelImage.createCell(0);
				cellA5.setCellStyle(styleHeader2);
				XSSFCell cellB5 = rowLabelImage.createCell(1);
				cellB5.setCellStyle(styleHeader2);
				XSSFCell cellC5 = rowLabelImage.createCell(2);
				cellC5.setCellStyle(styleHeader2);
				XSSFCell cellD5 = rowLabelImage.createCell(3);
				cellD5.setCellStyle(styleHeader2);
				XSSFCell cellE5 = rowLabelImage.createCell(4);
				cellE5.setCellStyle(styleHeader2);
				XSSFCell cellF5 = rowLabelImage.createCell(5);
				cellF5.setCellStyle(styleHeader2);
				XSSFCell cellG5 = rowLabelImage.createCell(6);
				cellG5.setCellStyle(styleHeader2);
				XSSFCell cellH5 = rowLabelImage.createCell(7);
				cellH5.setCellStyle(styleHeader2);
				XSSFCell cellI5 = rowLabelImage.createCell(8);
				cellI5.setCellStyle(styleHeader2);
				cellA5.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments39")));
				sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 0, 8));

				currentRowNumber++;
				XSSFRow rowValueImage = sheet.createRow(currentRowNumber);
				XSSFCell cellA6 = rowValueImage.createCell(0);
				cellA6.setCellStyle(styleImage);
				XSSFCell cellB6 = rowValueImage.createCell(1);
				cellB6.setCellStyle(styleImage);
				XSSFCell cellC6 = rowValueImage.createCell(2);
				cellC6.setCellStyle(styleImage);
				XSSFCell cellD6 = rowValueImage.createCell(3);
				cellD6.setCellStyle(styleImage);
				XSSFCell cellE6 = rowValueImage.createCell(4);
				cellE6.setCellStyle(styleImage);
				XSSFCell cellF6 = rowValueImage.createCell(5);
				cellF6.setCellStyle(styleImage);
				XSSFCell cellG6 = rowValueImage.createCell(6);
				cellG6.setCellStyle(styleImage);
				XSSFCell cellH6 = rowValueImage.createCell(7);
				cellH6.setCellStyle(styleImage);
				XSSFCell cellI6 = rowValueImage.createCell(8);
				cellI6.setCellStyle(styleImage);
				XSSFRichTextString valueImage = null;
				if (elementArray1[i].getType().equals("IOWebPage")) {
					IOWebPage ioWebPage = (IOWebPage)ioPojo;
					workString1 = res.getString("DialogDocuments40a") + (ioWebPage.getFileName() != null ? ioWebPage.getFileName() : "") + res.getString("DialogDocuments40b");
					valueImage = new XSSFRichTextString(workString1);
					cellA6.setCellValue(valueImage);
				}
				if (elementArray1[i].getType().equals("IOPanel") || elementArray1[i].getType().equals("IOSpool")) {
					int imageType = -1;
					File file = new File(fileName_);
					String sortKey = func.getSortKey();
					String ioSortKey = "";
					if (ioPojo instanceof IOPanel) ioSortKey = ((IOPanel)ioPojo).getSortKey();
					if (ioPojo instanceof IOSpool) ioSortKey = ((IOSpool)ioPojo).getSortKey();
					String imageFileName = "";
					if (frame_.ioImageFolder.equals("") || frame_.ioImageFolder.equals("<CURRENT>")) {
						imageFileName = file.getParent() + File.separator + sortKey + "_" + ioSortKey;
					} else {
						if (frame_.ioImageFolder.contains("<CURRENT>")) {
							imageFileName = frame_.ioImageFolder.replace("<CURRENT>", file.getParent());
							imageFileName = imageFileName + File.separator + sortKey + "_" + ioSortKey;
						} else {
							imageFileName = frame_.ioImageFolder + File.separator + sortKey + "_" + ioSortKey;
						}
					}
					File imageFile = new File(imageFileName + ".png");
					if (imageFile.exists()) {
						imageType = XSSFWorkbook.PICTURE_TYPE_PNG;
					} else {
						imageFile = new File(imageFileName + ".PNG");
						if (imageFile.exists()) {
							imageType = XSSFWorkbook.PICTURE_TYPE_PNG;
						} else {
							imageFile = new File(imageFileName + ".jpg");
							if (imageFile.exists()) {
								imageType = XSSFWorkbook.PICTURE_TYPE_JPEG;
							} else {
								imageFile = new File(imageFileName + ".JPG");
								if (imageFile.exists()) {
									imageType = XSSFWorkbook.PICTURE_TYPE_JPEG;
								} else {
									imageFile = new File(imageFileName + ".jpeg");
									if (imageFile.exists()) {
										imageType = XSSFWorkbook.PICTURE_TYPE_JPEG;
									} else {
										imageFile = new File(imageFileName + ".JPEG");
										if (imageFile.exists()) {
											imageType = XSSFWorkbook.PICTURE_TYPE_JPEG;
										}
									}
								}
							}
						}
					}

					if (imageFile.exists()) {
						FileInputStream fis = null;
						try {
							fis = new FileInputStream(imageFile);
							byte[] bytes = IOUtils.toByteArray(fis);
							int pictureIndex = workBook.addPicture(bytes, imageType);

							CreationHelper helper = workBook.getCreationHelper();
							Drawing drawing = sheet.createDrawingPatriarch();
							ClientAnchor anchor = helper.createClientAnchor();
							anchor.setCol1((short)0);
							anchor.setRow1(cellA6.getRowIndex());
							anchor.setCol2((short)9);
							anchor.setRow2(cellA6.getRowIndex()+1);
							anchor.setDx1(0);
							anchor.setDy1(0);
							anchor.setDx2(0);
							anchor.setDy2(0);
							drawing.createPicture(anchor, pictureIndex);
						} catch(Exception ex) {
							ex.printStackTrace();
						} finally {
							try {
								if (fis != null) {
									fis.close();
								}
							} catch (IOException ex1) {
							}
						}
					} else {
						String imageText = "";
						if (ioPojo instanceof IOPanel) imageText = ((IOPanel)ioPojo).getImageText() != null ? ((IOPanel)ioPojo).getImageText() : "";
						if (ioPojo instanceof IOSpool) imageText = ((IOSpool)ioPojo).getImageText() != null ? ((IOSpool)ioPojo).getImageText() : "";
						workString1 = substringLinesWithTokenOfEOL(imageText, "\n");
						valueImage = new XSSFRichTextString(workString1);
						valueImage.applyFont(0, workString1.length(), fontImage);
						cellA6.setCellValue(valueImage);
					}
				}
				sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 0, 8));
				rowValueImage.setHeight((short)3500);

				if (elementArray1[i].getType().equals("IOPanel") || elementArray1[i].getType().equals("IOWebPage")) {
					currentRowNumber++;
					XSSFRow rowColumnHeading = sheet.createRow(currentRowNumber);
					XSSFCell cellA7 = rowColumnHeading.createCell(0);
					cellA7.setCellStyle(styleHeader2Number);
					cellA7.setCellValue(new XSSFRichTextString("No."));
					XSSFCell cellB7 = rowColumnHeading.createCell(1);
					cellB7.setCellStyle(styleHeader2);
					cellB7.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments41")));
					XSSFCell cellC7 = rowColumnHeading.createCell(2);
					cellC7.setCellStyle(styleHeader2);
					XSSFCell cellD7 = rowColumnHeading.createCell(3);
					cellD7.setCellStyle(styleHeader2);
					cellC7.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments42")));
					sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 2, 3));
					XSSFCell cellE7 = rowColumnHeading.createCell(4);
					cellE7.setCellStyle(styleHeader2);
					XSSFCell cellF7 = rowColumnHeading.createCell(5);
					cellF7.setCellStyle(styleHeader2);
					cellE7.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments43")));
					sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 4, 5));
					XSSFCell cellG7 = rowColumnHeading.createCell(6);
					cellG7.setCellStyle(styleHeader2);
					XSSFCell cellH7 = rowColumnHeading.createCell(7);
					cellH7.setCellStyle(styleHeader2);
					XSSFCell cellI7 = rowColumnHeading.createCell(8);
					cellI7.setCellStyle(styleHeader2);
					cellG7.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments23")));
					sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 6, 8));

					//Rows of Field
					rowSequence = 0;
					countOfElementArray2 = -1;
					JsonNode node = null;
					if (elementArray1[i].getType().equals("IOPanel")) {
						IOPanel ioPanel = (IOPanel)ioPojo;
						if (ioPanel.getIopanelFields() != null) {
							for (IOPanelField field : ioPanel.getIopanelFields()) {
								countOfElementArray2++;
								node = new JsonNode("IOPanelField", field);
								elementArray2[countOfElementArray2] = node;
							}
						}
					}
					if (elementArray1[i].getType().equals("IOWebPage")) {
						IOWebPage ioWebPage = (IOWebPage)ioPojo;
						if (ioWebPage.getIowebpageFields() != null) {
							for (IOWebPageField field : ioWebPage.getIowebpageFields()) {
								countOfElementArray2++;
								node = new JsonNode("IOWebPageField", field);
								elementArray2[countOfElementArray2] = node;
							}
						}
					}
					if (countOfElementArray2 > 0) {
						Arrays.sort(elementArray2, 0, countOfElementArray2 + 1);
					}
					for (int j = 0; j <= countOfElementArray2; j++) {
						Object fieldPojo = elementArray2[j].getPojo();
						currentRowNumber++;
						XSSFRow rowValue2 = sheet.createRow(currentRowNumber);
						XSSFCell cellA = rowValue2.createCell(0);
						cellA.setCellStyle(styleValueNumber);
						rowSequence++;
						cellA.setCellValue(rowSequence);
						XSSFCell cellB = rowValue2.createCell(1);
						cellB.setCellStyle(styleValue);
						String label = "";
						if (fieldPojo instanceof IOPanelField) label = ((IOPanelField)fieldPojo).getLabel() != null ? ((IOPanelField)fieldPojo).getLabel() : "";
						if (fieldPojo instanceof IOWebPageField) label = ((IOWebPageField)fieldPojo).getLabel() != null ? ((IOWebPageField)fieldPojo).getLabel() : "";
						cellB.setCellValue(new XSSFRichTextString(label));
						XSSFCell cellC = rowValue2.createCell(2);
						cellC.setCellStyle(styleValue);
						XSSFCell cellD = rowValue2.createCell(3);
						cellD.setCellStyle(styleValue);
						String name = "";
						if (fieldPojo instanceof IOPanelField) name = ((IOPanelField)fieldPojo).getName() != null ? ((IOPanelField)fieldPojo).getName() : "";
						if (fieldPojo instanceof IOWebPageField) name = ((IOWebPageField)fieldPojo).getName() != null ? ((IOWebPageField)fieldPojo).getName() : "";
						cellC.setCellValue(new XSSFRichTextString(name));
						sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 2, 3));
						XSSFCell cellE = rowValue2.createCell(4);
						cellE.setCellStyle(styleValue);
						XSSFCell cellF = rowValue2.createCell(5);
						cellF.setCellStyle(styleValue);
						workString1 = "";
						String ioType = "";
						if (fieldPojo instanceof IOPanelField) ioType = ((IOPanelField)fieldPojo).getIoType() != null ? ((IOPanelField)fieldPojo).getIoType() : "";
						if (fieldPojo instanceof IOWebPageField) ioType = ((IOWebPageField)fieldPojo).getIoType() != null ? ((IOWebPageField)fieldPojo).getIoType() : "";
						if ("INPUT".equals(ioType)) workString1 = res.getString("DialogDocuments44");
						if ("REQUIRED".equals(ioType)) workString1 = res.getString("DialogDocuments45");
						if ("OUTPUT".equals(ioType)) workString1 = res.getString("DialogDocuments46");
						if ("CONTROL".equals(ioType)) workString1 = res.getString("DialogDocuments47");
						cellE.setCellValue(new XSSFRichTextString(workString1));
						sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 4, 5));
						XSSFCell cellG = rowValue2.createCell(6);
						cellG.setCellStyle(styleValue);
						XSSFCell cellH = rowValue2.createCell(7);
						cellH.setCellStyle(styleValue);
						XSSFCell cellI = rowValue2.createCell(8);
						cellI.setCellStyle(styleValue);
						String fieldDesc = "";
						if (fieldPojo instanceof IOPanelField) fieldDesc = ((IOPanelField)fieldPojo).getDescriptions() != null ? ((IOPanelField)fieldPojo).getDescriptions() : "";
						if (fieldPojo instanceof IOWebPageField) fieldDesc = ((IOWebPageField)fieldPojo).getDescriptions() != null ? ((IOWebPageField)fieldPojo).getDescriptions() : "";
						cellG.setCellValue(new XSSFRichTextString(fieldDesc));
						sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 6, 8));
					}

					if (rowSequence == 0) {
						currentRowNumber++;
						XSSFRow rowBlank = sheet.createRow(currentRowNumber);
						XSSFCell cellA8 = rowBlank.createCell(0);
						cellA8.setCellStyle(styleValueNumber);
						XSSFCell cellB8 = rowBlank.createCell(1);
						cellB8.setCellStyle(styleValue);
						cellB8.setCellValue(new XSSFRichTextString("(None)"));
						XSSFCell cellC8 = rowBlank.createCell(2);
						cellC8.setCellStyle(styleValue);
						XSSFCell cellD8 = rowBlank.createCell(3);
						cellD8.setCellStyle(styleValue);
						sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 2, 3));
						XSSFCell cellE8 = rowBlank.createCell(4);
						cellE8.setCellStyle(styleValue);
						XSSFCell cellF8 = rowBlank.createCell(5);
						cellF8.setCellStyle(styleValue);
						sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 4, 5));
						XSSFCell cellG8 = rowBlank.createCell(6);
						cellG8.setCellStyle(styleValue);
						XSSFCell cellH8 = rowBlank.createCell(7);
						cellH8.setCellStyle(styleValue);
						XSSFCell cellI8 = rowBlank.createCell(8);
						cellI8.setCellStyle(styleValue);
						sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 6, 8));
					}
				}
				if (elementArray1[i].getType().equals("IOSpool")) {
					currentRowNumber++;
					XSSFRow rowColumnHeading = sheet.createRow(currentRowNumber);
					XSSFCell cellA7 = rowColumnHeading.createCell(0);
					cellA7.setCellStyle(styleHeader2Number);
					cellA7.setCellValue(new XSSFRichTextString("No."));
					XSSFCell cellB7 = rowColumnHeading.createCell(1);
					cellB7.setCellStyle(styleHeader2);
					cellB7.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments41")));
					XSSFCell cellC7 = rowColumnHeading.createCell(2);
					cellC7.setCellStyle(styleHeader2);
					XSSFCell cellD7 = rowColumnHeading.createCell(3);
					cellD7.setCellStyle(styleHeader2);
					cellC7.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments42")));
					sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 2, 3));
					XSSFCell cellE7 = rowColumnHeading.createCell(4);
					cellE7.setCellStyle(styleHeader2);
					XSSFCell cellF7 = rowColumnHeading.createCell(5);
					cellF7.setCellStyle(styleHeader2);
					XSSFCell cellG7 = rowColumnHeading.createCell(6);
					cellG7.setCellStyle(styleHeader2);
					XSSFCell cellH7 = rowColumnHeading.createCell(7);
					cellH7.setCellStyle(styleHeader2);
					XSSFCell cellI7 = rowColumnHeading.createCell(8);
					cellI7.setCellStyle(styleHeader2);
					cellE7.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments23")));
					sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 4, 8));

					//Rows of Field
					rowSequence = 0;
					countOfElementArray2 = -1;
					JsonNode node = null;
					IOSpool ioSpool = (IOSpool)ioPojo;
					if (ioSpool.getIospoolFields() != null) {
						for (IOSpoolField field : ioSpool.getIospoolFields()) {
							countOfElementArray2++;
							node = new JsonNode("IOSpoolField", field);
							elementArray2[countOfElementArray2] = node;
						}
					}
					if (countOfElementArray2 > 0) {
						Arrays.sort(elementArray2, 0, countOfElementArray2 + 1);
					}
					for (int j = 0; j <= countOfElementArray2; j++) {
						IOSpoolField field = (IOSpoolField)elementArray2[j].getPojo();
						currentRowNumber++;
						XSSFRow rowValue2 = sheet.createRow(currentRowNumber);
						XSSFCell cellA = rowValue2.createCell(0);
						cellA.setCellStyle(styleValueNumber);
						rowSequence++;
						cellA.setCellValue(rowSequence);
						XSSFCell cellB = rowValue2.createCell(1);
						cellB.setCellStyle(styleValue);
						cellB.setCellValue(new XSSFRichTextString(field.getLabel() != null ? field.getLabel() : ""));
						XSSFCell cellC = rowValue2.createCell(2);
						cellC.setCellStyle(styleValue);
						XSSFCell cellD = rowValue2.createCell(3);
						cellD.setCellStyle(styleValue);
						cellC.setCellValue(new XSSFRichTextString(field.getName() != null ? field.getName() : ""));
						sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 2, 3));
						XSSFCell cellE = rowValue2.createCell(4);
						cellE.setCellStyle(styleValue);
						XSSFCell cellF = rowValue2.createCell(5);
						cellF.setCellStyle(styleValue);
						XSSFCell cellG = rowValue2.createCell(6);
						cellG.setCellStyle(styleValue);
						XSSFCell cellH = rowValue2.createCell(7);
						cellH.setCellStyle(styleValue);
						XSSFCell cellI = rowValue2.createCell(8);
						cellI.setCellStyle(styleValue);
						cellE.setCellValue(new XSSFRichTextString(field.getDescriptions() != null ? field.getDescriptions() : ""));
						sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 4, 8));
					}

					if (rowSequence == 0) {
						currentRowNumber++;
						XSSFRow rowBlank = sheet.createRow(currentRowNumber);
						XSSFCell cellA8 = rowBlank.createCell(0);
						cellA8.setCellStyle(styleValueNumber);
						XSSFCell cellB8 = rowBlank.createCell(1);
						cellB8.setCellStyle(styleValue);
						cellB8.setCellValue(new XSSFRichTextString("(None)"));
						XSSFCell cellC8 = rowBlank.createCell(2);
						cellC8.setCellStyle(styleValue);
						sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 2, 3));
						XSSFCell cellD8 = rowBlank.createCell(3);
						cellD8.setCellStyle(styleValue);
						XSSFCell cellE8 = rowBlank.createCell(4);
						cellE8.setCellStyle(styleValue);
						XSSFCell cellF8 = rowBlank.createCell(5);
						cellF8.setCellStyle(styleValue);
						XSSFCell cellG8 = rowBlank.createCell(6);
						cellG8.setCellStyle(styleValue);
						XSSFCell cellH8 = rowBlank.createCell(7);
						cellH8.setCellStyle(styleValue);
						XSSFCell cellI8 = rowBlank.createCell(8);
						cellI8.setCellStyle(styleValue);
						sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 4, 8));
					}
				}
			}
			if (elementArray1[i].getType().equals("IOTable")) {
				IOTable ioTable = (IOTable)ioPojo;
				for (Table table : tableList) {
					if (table.getId().equals(ioTable.getTableId())) {
						List<IOTableField> ioFieldList = ioTable.getIotableFields();

						currentRowNumber++;
						XSSFRow rowColumnHeading = sheet.createRow(currentRowNumber);
						XSSFCell cellA7 = rowColumnHeading.createCell(0);
						cellA7.setCellStyle(styleHeader2Number);
						cellA7.setCellValue(new XSSFRichTextString("No."));
						XSSFCell cellB7 = rowColumnHeading.createCell(1);
						cellB7.setCellStyle(styleHeader2);
						XSSFCell cellC7 = rowColumnHeading.createCell(2);
						cellC7.setCellStyle(styleHeader2);
						cellB7.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments48")));
						sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 1, 2));
						XSSFCell cellD7 = rowColumnHeading.createCell(3);
						cellD7.setCellStyle(styleHeader2);
						XSSFCell cellE7 = rowColumnHeading.createCell(4);
						cellE7.setCellStyle(styleHeader2);
						cellD7.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments49")));
						sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 3, 4));
						XSSFCell cellF7 = rowColumnHeading.createCell(5);
						cellF7.setCellStyle(styleHeader2);
						XSSFCell cellG7 = rowColumnHeading.createCell(6);
						cellG7.setCellStyle(styleHeader2);
						XSSFCell cellH7 = rowColumnHeading.createCell(7);
						cellH7.setCellStyle(styleHeader2);
						XSSFCell cellI7 = rowColumnHeading.createCell(8);
						cellI7.setCellStyle(styleHeader2);
						cellF7.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments50")));
						sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 5, 8));

						List<TableKeyField> primaryKeyFieldList = new ArrayList<>();
						List<TableKey> tableKeys = table.getTableKeys();
						if (tableKeys != null) {
							for (TableKey tk : tableKeys) {
								if ("PK".equals(tk.getType())) {
									if (tk.getTableKeyFields() != null) {
										primaryKeyFieldList = tk.getTableKeyFields();
									}
									break;
								}
							}
						}

						rowSequence = 0;
						countOfElementArray2 = -1;
						JsonNode node = null;
						List<TableField> tableFields = table.getTableFields();
						if (tableFields != null) {
							for (TableField tf : tableFields) {
								countOfElementArray2++;
								node = new JsonNode("TableField", tf);
								elementArray2[countOfElementArray2] = node;
							}
						}
						if (countOfElementArray2 > 0) {
							Arrays.sort(elementArray2, 0, countOfElementArray2 + 1);
						}
						for (int k = 0; k <= countOfElementArray2; k++) {
							TableField tf = (TableField)elementArray2[k].getPojo();
							currentRowNumber++;
							XSSFRow rowValue2 = sheet.createRow(currentRowNumber);
							XSSFCell cellA = rowValue2.createCell(0);
							cellA.setCellStyle(styleValueNumber);
							rowSequence++;
							cellA.setCellValue(rowSequence);
							XSSFCell cellB = rowValue2.createCell(1);
							cellB.setCellStyle(styleValue);
							XSSFCell cellC = rowValue2.createCell(2);
							cellC.setCellStyle(styleValue);
							workString1 = "";
							boolean primaryKeyField = false;
							for (TableKeyField tkf : primaryKeyFieldList) {
								if (tkf.getFieldId().equals(tf.getId())) {
									primaryKeyField = true;
									break;
								}
							}
							String alias = tf.getAlias() != null ? tf.getAlias() : "";
							String tfName = tf.getName() != null ? tf.getName() : "";
							if (alias.equals("")) {
								if (primaryKeyField) {
									workString1 = "*" + tfName;
								} else {
									workString1 = tfName;
								}
							} else {
								if (primaryKeyField) {
									workString1 = "*" + alias + " / " + tfName;
								} else {
									workString1 = alias + " / " + tfName;
								}
							}
							cellB.setCellValue(new XSSFRichTextString(workString1));
							sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 1, 2));
							XSSFCell cellD = rowValue2.createCell(3);
							cellD.setCellStyle(styleValue);
							XSSFCell cellE = rowValue2.createCell(4);
							cellE.setCellStyle(styleValue);
							workString1 = "";
							for (DataType dt : dataTypeList) {
								if (dt.getId().equals(tf.getDataTypeId())) {
									String decimal = dt.getDecimal() != null ? dt.getDecimal() : "";
									String length = dt.getLength() != null ? dt.getLength() : "";
									if (decimal.equals("") || decimal.equals("0")) {
										workString1 = dt.getName() + "(" + length + ")";
									} else {
										workString1 = dt.getName() + "(" + length + "." + decimal + ")";
									}
									break;
								}
							}
							cellD.setCellValue(new XSSFRichTextString(workString1));
							sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 3, 4));
							XSSFCell cellF = rowValue2.createCell(5);
							cellF.setCellStyle(styleValue);
							XSSFCell cellG = rowValue2.createCell(6);
							cellG.setCellStyle(styleValue);
							XSSFCell cellH = rowValue2.createCell(7);
							cellH.setCellStyle(styleValue);
							XSSFCell cellI = rowValue2.createCell(8);
							cellI.setCellStyle(styleValue);
							workString1 = "";
							if (ioFieldList != null) {
								for (IOTableField iotf : ioFieldList) {
									if (iotf.getFieldId().equals(tf.getId())) {
										workString1 = iotf.getDescriptions() != null ? iotf.getDescriptions() : "";
										break;
									}
								}
							}
							cellF.setCellValue(new XSSFRichTextString(workString1));
							sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 5, 8));
						}
						break;
					}
				}
			}
		}
	}

	void setupTableSummary(XSSFSheet sheet, Table table) {
		String workString = "";

		XSSFRow row0 = sheet.createRow(0);
		XSSFRow row1 = sheet.createRow(1);
		XSSFRow row2 = sheet.createRow(2);
		XSSFRow row3 = sheet.createRow(3);
		XSSFRow row4 = sheet.createRow(4);
		XSSFRow row5 = sheet.createRow(5);

		//Title
		XSSFRichTextString title = new XSSFRichTextString(res.getString("DialogDocuments51"));
		XSSFCell cellA0 = row0.createCell(0);
		cellA0.setCellStyle(styleTitle);
		XSSFCell cellB0 = row0.createCell(1);
		cellB0.setCellStyle(styleTitle);
		XSSFCell cellA1 = row1.createCell(0);
		cellA1.setCellStyle(styleTitle);
		XSSFCell cellB1 = row1.createCell(1);
		cellB1.setCellStyle(styleTitle);
		cellA0.setCellValue(title);
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 1));

		//Label Subsystem
		XSSFCell cellC0 = row0.createCell(2);
		cellC0.setCellStyle(styleHeader2);
		XSSFCell cellD0 = row0.createCell(3);
		cellD0.setCellStyle(styleHeader2);
		XSSFCell cellE0 = row0.createCell(4);
		cellE0.setCellStyle(styleHeader2);
		XSSFCell cellF0 = row0.createCell(5);
		cellF0.setCellStyle(styleHeader2);
		XSSFCell cellG0 = row0.createCell(6);
		cellG0.setCellStyle(styleHeader2);
		XSSFCell cellH0 = row0.createCell(7);
		cellH0.setCellStyle(styleHeader2);
		XSSFCell cellI0 = row0.createCell(8);
		cellI0.setCellStyle(styleHeader2);
		cellC0.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments17")));
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 2, 8));

		//Value Subsystem
		XSSFCell cellC1 = row1.createCell(2);
		cellC1.setCellStyle(styleValue);
		XSSFCell cellD1 = row1.createCell(3);
		cellD1.setCellStyle(styleValue);
		XSSFCell cellE1 = row1.createCell(4);
		cellE1.setCellStyle(styleValue);
		XSSFCell cellF1 = row1.createCell(5);
		cellF1.setCellStyle(styleValue);
		XSSFCell cellG1 = row1.createCell(6);
		cellG1.setCellStyle(styleValue);
		XSSFCell cellH1 = row1.createCell(7);
		cellH1.setCellStyle(styleValue);
		XSSFCell cellI1 = row1.createCell(8);
		cellI1.setCellStyle(styleValue);
		cellC1.setCellValue(new XSSFRichTextString(subsystemElement.getSortKey() + " / " + subsystemElement.getName()));
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 2, 8));

		//Label Table Name
		XSSFCell cellA2 = row2.createCell(0);
		cellA2.setCellStyle(styleHeader2);
		XSSFCell cellB2 = row2.createCell(1);
		cellB2.setCellStyle(styleHeader2);
		XSSFCell cellC2 = row2.createCell(2);
		cellC2.setCellStyle(styleHeader2);
		XSSFCell cellD2 = row2.createCell(3);
		cellD2.setCellStyle(styleHeader2);
		XSSFCell cellE2 = row2.createCell(4);
		cellE2.setCellStyle(styleHeader2);
		cellA2.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments52")));
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 4));

		//Value Table Name
		XSSFCell cellA3 = row3.createCell(0);
		cellA3.setCellStyle(styleValue);
		XSSFCell cellB3 = row3.createCell(1);
		cellB3.setCellStyle(styleValue);
		XSSFCell cellC3 = row3.createCell(2);
		cellC3.setCellStyle(styleValue);
		XSSFCell cellD3 = row3.createCell(3);
		cellD3.setCellStyle(styleValue);
		XSSFCell cellE3 = row3.createCell(4);
		cellE3.setCellStyle(styleValue);
		String sortKey = table.getSortKey() != null ? table.getSortKey() : "";
		String alias = table.getAlias() != null ? table.getAlias() : "";
		String tableName = table.getName() != null ? table.getName() : "";
		if (sortKey.equals("")) {
			cellA3.setCellValue(new XSSFRichTextString(tableName));
		} else {
			if (alias.equals("")) {
				cellA3.setCellValue(new XSSFRichTextString(sortKey + " / " + tableName));
			} else {
				cellA3.setCellValue(new XSSFRichTextString(sortKey + " / " + tableName + "(" + alias + ")"));
			}
		}
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 4));

		//Label TableType
		XSSFCell cellF2 = row2.createCell(5);
		cellF2.setCellStyle(styleHeader2);
		XSSFCell cellG2 = row2.createCell(6);
		cellG2.setCellStyle(styleHeader2);
		XSSFCell cellH2 = row2.createCell(7);
		cellH2.setCellStyle(styleHeader2);
		XSSFCell cellI2 = row2.createCell(8);
		cellI2.setCellStyle(styleHeader2);
		cellF2.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments53")));
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 5, 8));

		//Value TableType
		XSSFCell cellF3 = row3.createCell(5);
		cellF3.setCellStyle(styleValue);
		XSSFCell cellG3 = row3.createCell(6);
		cellG3.setCellStyle(styleValue);
		XSSFCell cellH3 = row3.createCell(7);
		cellH3.setCellStyle(styleValue);
		XSSFCell cellI3 = row3.createCell(8);
		cellI3.setCellStyle(styleValue);
		for (TableType tt : tableTypeList) {
			if (tt.getId().equals(table.getTableTypeId())) {
				workString = tt.getName();
				break;
			}
		}
		cellF3.setCellValue(new XSSFRichTextString(workString));
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 5, 8));

		//Label Descriptions
		XSSFCell cellA4 = row4.createCell(0);
		cellA4.setCellStyle(styleHeader2);
		XSSFCell cellB4 = row4.createCell(1);
		cellB4.setCellStyle(styleHeader2);
		XSSFCell cellC4 = row4.createCell(2);
		cellC4.setCellStyle(styleHeader2);
		XSSFCell cellD4 = row4.createCell(3);
		cellD4.setCellStyle(styleHeader2);
		XSSFCell cellE4 = row4.createCell(4);
		cellE4.setCellStyle(styleHeader2);
		XSSFCell cellF4 = row4.createCell(5);
		cellF4.setCellStyle(styleHeader2);
		XSSFCell cellG4 = row4.createCell(6);
		cellG4.setCellStyle(styleHeader2);
		XSSFCell cellH4 = row4.createCell(7);
		cellH4.setCellStyle(styleHeader2);
		XSSFCell cellI4 = row4.createCell(8);
		cellI4.setCellStyle(styleHeader2);
		cellA4.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments23")));
		sheet.addMergedRegion(new CellRangeAddress(4, 4, 0, 8));

		//Value Descriptions
		XSSFCell cellA5 = row5.createCell(0);
		cellA5.setCellStyle(styleValue);
		XSSFCell cellB5 = row5.createCell(1);
		cellB5.setCellStyle(styleValue);
		XSSFCell cellC5 = row5.createCell(2);
		cellC5.setCellStyle(styleValue);
		XSSFCell cellD5 = row5.createCell(3);
		cellD5.setCellStyle(styleValue);
		XSSFCell cellE5 = row5.createCell(4);
		cellE5.setCellStyle(styleValue);
		XSSFCell cellF5 = row5.createCell(5);
		cellF5.setCellStyle(styleValue);
		XSSFCell cellG5 = row5.createCell(6);
		cellG5.setCellStyle(styleValue);
		XSSFCell cellH5 = row5.createCell(7);
		cellH5.setCellStyle(styleValue);
		XSSFCell cellI5 = row5.createCell(8);
		cellI5.setCellStyle(styleValue);
		workString = substringLinesWithTokenOfEOL(table.getDescriptions() != null ? table.getDescriptions() : "", "\n");
		cellA5.setCellValue(new XSSFRichTextString(workString));
		sheet.addMergedRegion(new CellRangeAddress(5, 5, 0, 8));
		row5.setHeight((short)1000);

		currentRowNumber = 5;
	}

	void setupTableFieldList(XSSFSheet sheet, Table table) {
		String workString1 = "";
		int rowSequence = 0;
		JsonNode node = null;

		//Label FieldList
		currentRowNumber++;
		XSSFRow row0 = sheet.createRow(currentRowNumber);
		XSSFCell cellA0 = row0.createCell(0);
		cellA0.setCellStyle(styleHeader1);
		XSSFCell cellB0 = row0.createCell(1);
		cellB0.setCellStyle(styleHeader1);
		XSSFCell cellC0 = row0.createCell(2);
		cellC0.setCellStyle(styleHeader1);
		XSSFCell cellD0 = row0.createCell(3);
		cellD0.setCellStyle(styleHeader1);
		XSSFCell cellE0 = row0.createCell(4);
		cellE0.setCellStyle(styleHeader1);
		XSSFCell cellF0 = row0.createCell(5);
		cellF0.setCellStyle(styleHeader1);
		XSSFCell cellG0 = row0.createCell(6);
		cellG0.setCellStyle(styleHeader1);
		XSSFCell cellH0 = row0.createCell(7);
		cellH0.setCellStyle(styleHeader1);
		XSSFCell cellI0 = row0.createCell(8);
		cellI0.setCellStyle(styleHeader1);
		cellA0.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments54")));
		sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 0, 8));

		//Header FieldList
		currentRowNumber++;
		XSSFRow row1 = sheet.createRow(currentRowNumber);
		XSSFCell cellA1 = row1.createCell(0);
		cellA1.setCellStyle(styleHeader2Number);
		cellA1.setCellValue(new XSSFRichTextString("No."));
		XSSFCell cellB1 = row1.createCell(1);
		cellB1.setCellStyle(styleHeader2);
		XSSFCell cellC1 = row1.createCell(2);
		cellC1.setCellStyle(styleHeader2);
		cellB1.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments55")));
		sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 1, 2));
		XSSFCell cellD1 = row1.createCell(3);
		cellD1.setCellStyle(styleHeader2);
		cellD1.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments56")));
		XSSFCell cellE1 = row1.createCell(4);
		cellE1.setCellStyle(styleHeader2);
		cellE1.setCellValue(new XSSFRichTextString("Null"));
		XSSFCell cellF1 = row1.createCell(5);
		cellF1.setCellStyle(styleHeader2);
		cellF1.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments57")));
		XSSFCell cellG1 = row1.createCell(6);
		cellG1.setCellStyle(styleHeader2);
		XSSFCell cellH1 = row1.createCell(7);
		cellH1.setCellStyle(styleHeader2);
		XSSFCell cellI1 = row1.createCell(8);
		cellI1.setCellStyle(styleHeader2);
		cellG1.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments23")));
		sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 6, 8));

		//Rows of TableField
		countOfElementArray1 = -1;
		List<TableField> tableFields = table.getTableFields();
		if (tableFields != null) {
			for (TableField tf : tableFields) {
				countOfElementArray1++;
				node = new JsonNode("TableField", tf);
				elementArray1[countOfElementArray1] = node;
			}
		}
		if (countOfElementArray1 > 0) {
			Arrays.sort(elementArray1, 0, countOfElementArray1 + 1);
		}
		for (int i = 0; i <= countOfElementArray1; i++) {
			TableField tf = (TableField)elementArray1[i].getPojo();
			currentRowNumber++;
			XSSFRow nextRow = sheet.createRow(currentRowNumber);
			XSSFCell cellA = nextRow.createCell(0);
			cellA.setCellStyle(styleValueNumber);
			rowSequence++;
			cellA.setCellValue(rowSequence);
			XSSFCell cellB = nextRow.createCell(1);
			if (isPrimaryKeyField(table, tf)) {
				cellB.setCellStyle(styleValueStrong);
			} else {
				cellB.setCellStyle(styleValue);
			}
			String alias = tf.getAlias() != null ? tf.getAlias() : "";
			String tfName = tf.getName() != null ? tf.getName() : "";
			String attrType = tf.getAttributeType() != null ? tf.getAttributeType() : "";
			if (alias.equals("")) {
				if ("NATIVE".equals(attrType)) {
					workString1 = tfName;
				} else {
					workString1 = "(" + tfName + ")";
				}
			} else {
				if ("NATIVE".equals(attrType)) {
					workString1 = alias + " / " + tfName;
				} else {
					workString1 = "(" + alias + " / " + tfName + ")";
				}
			}
			cellB.setCellValue(new XSSFRichTextString(workString1));
			XSSFCell cellC = nextRow.createCell(2);
			cellC.setCellStyle(styleValue);
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 1, 2));
			XSSFCell cellD = nextRow.createCell(3);
			cellD.setCellStyle(styleValue);
			workString1 = "";
			for (DataType dt : dataTypeList) {
				if (dt.getId().equals(tf.getDataTypeId())) {
					workString1 = dt.getName() + " " + (dt.getSqlExpression() != null ? dt.getSqlExpression() : "");
					break;
				}
			}
			cellD.setCellValue(new XSSFRichTextString(workString1));
			XSSFCell cellE = nextRow.createCell(4);
			cellE.setCellStyle(styleValue);
			String notNull = tf.getNotNull() != null ? tf.getNotNull() : "";
			if ("true".equals(notNull)) {
				workString1 = res.getString("DialogDocuments58");
			} else {
				workString1 = res.getString("DialogDocuments59");
			}
			cellE.setCellValue(new XSSFRichTextString(workString1));
			XSSFCell cellF = nextRow.createCell(5);
			cellF.setCellStyle(styleValue);
			cellF.setCellValue(new XSSFRichTextString(tf.getDefault() != null ? tf.getDefault() : ""));
			XSSFCell cellG = nextRow.createCell(6);
			cellG.setCellStyle(styleValue);
			XSSFCell cellH = nextRow.createCell(7);
			cellH.setCellStyle(styleValue);
			XSSFCell cellI = nextRow.createCell(8);
			cellI.setCellStyle(styleValue);
			workString1 = substringLinesWithTokenOfEOL(tf.getDescriptions() != null ? tf.getDescriptions() : "", "\n");
			cellG.setCellValue(new XSSFRichTextString(workString1));
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 6, 8));
		}

		if (rowSequence == 0) {
			currentRowNumber++;
			XSSFRow nextRow = sheet.createRow(currentRowNumber);
			XSSFCell cellA2 = nextRow.createCell(0);
			cellA2.setCellStyle(styleValueNumber);
			XSSFCell cellB2 = nextRow.createCell(1);
			cellB2.setCellStyle(styleValue);
			XSSFCell cellC2 = nextRow.createCell(2);
			cellC2.setCellStyle(styleValue);
			cellB2.setCellValue(new XSSFRichTextString("(None)"));
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 1, 2));
			XSSFCell cellD2 = nextRow.createCell(3);
			cellD2.setCellStyle(styleValue);
			XSSFCell cellE2 = nextRow.createCell(4);
			cellE2.setCellStyle(styleValue);
			XSSFCell cellF2 = nextRow.createCell(5);
			cellF2.setCellStyle(styleValue);
			XSSFCell cellG2 = nextRow.createCell(6);
			cellG2.setCellStyle(styleValue);
			XSSFCell cellH2 = nextRow.createCell(7);
			cellH2.setCellStyle(styleValue);
			XSSFCell cellI2 = nextRow.createCell(8);
			cellI2.setCellStyle(styleValue);
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 6, 8));
		}
	}

	boolean isPrimaryKeyField(Table table, TableField field) {
		boolean isKey = false;
		List<TableKey> tableKeys = table.getTableKeys();
		if (tableKeys == null) return false;
		for (TableKey tk : tableKeys) {
			if ("PK".equals(tk.getType())) {
				List<TableKeyField> tkfList = tk.getTableKeyFields();
				if (tkfList != null) {
					for (TableKeyField tkf : tkfList) {
						if (tkf.getFieldId().equals(field.getId())) {
							isKey = true;
							break;
						}
					}
				}
				break;
			}
		}
		return isKey;
	}

	void setupTableKeyList(XSSFSheet sheet, Table table) {
		String workString1 = "";
		int rowSequence = 0;
		JsonNode node = null;

		//Label KeyList
		currentRowNumber++;
		XSSFRow row0 = sheet.createRow(currentRowNumber);
		XSSFCell cellA0 = row0.createCell(0);
		cellA0.setCellStyle(styleHeader1);
		XSSFCell cellB0 = row0.createCell(1);
		cellB0.setCellStyle(styleHeader1);
		XSSFCell cellC0 = row0.createCell(2);
		cellC0.setCellStyle(styleHeader1);
		XSSFCell cellD0 = row0.createCell(3);
		cellD0.setCellStyle(styleHeader1);
		XSSFCell cellE0 = row0.createCell(4);
		cellE0.setCellStyle(styleHeader1);
		XSSFCell cellF0 = row0.createCell(5);
		cellF0.setCellStyle(styleHeader1);
		XSSFCell cellG0 = row0.createCell(6);
		cellG0.setCellStyle(styleHeader1);
		XSSFCell cellH0 = row0.createCell(7);
		cellH0.setCellStyle(styleHeader1);
		XSSFCell cellI0 = row0.createCell(8);
		cellI0.setCellStyle(styleHeader1);
		cellA0.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments60")));
		sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 0, 8));

		//Header KeyList
		currentRowNumber++;
		XSSFRow row1 = sheet.createRow(currentRowNumber);
		XSSFCell cellA1 = row1.createCell(0);
		cellA1.setCellStyle(styleHeader2Number);
		cellA1.setCellValue(new XSSFRichTextString("No."));
		XSSFCell cellB1 = row1.createCell(1);
		cellB1.setCellStyle(styleHeader2);
		cellB1.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments61")));
		XSSFCell cellC1 = row1.createCell(2);
		cellC1.setCellStyle(styleHeader2);
		XSSFCell cellD1 = row1.createCell(3);
		cellD1.setCellStyle(styleHeader2);
		XSSFCell cellE1 = row1.createCell(4);
		cellE1.setCellStyle(styleHeader2);
		XSSFCell cellF1 = row1.createCell(5);
		cellF1.setCellStyle(styleHeader2);
		XSSFCell cellG1 = row1.createCell(6);
		cellG1.setCellStyle(styleHeader2);
		XSSFCell cellH1 = row1.createCell(7);
		cellH1.setCellStyle(styleHeader2);
		XSSFCell cellI1 = row1.createCell(8);
		cellI1.setCellStyle(styleHeader2);
		cellC1.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments62")));
		sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 2, 8));

		//Rows of Key
		List<TableField> fieldList = table.getTableFields();
		List<TableKey> tableKeys = table.getTableKeys();
		if (tableKeys != null) {
			for (TableKey tk : tableKeys) {
				currentRowNumber++;
				XSSFRow nextRow = sheet.createRow(currentRowNumber);
				XSSFCell cellA = nextRow.createCell(0);
				cellA.setCellStyle(styleValueNumber);
				rowSequence++;
				cellA.setCellValue(rowSequence);
				XSSFCell cellB = nextRow.createCell(1);
				cellB.setCellStyle(styleValue);
				workString1 = "";
				if ("PK".equals(tk.getType())) workString1 = res.getString("DialogDocuments63");
				if ("FK".equals(tk.getType())) workString1 = res.getString("DialogDocuments64");
				if ("SK".equals(tk.getType())) workString1 = res.getString("DialogDocuments65");
				if ("XK".equals(tk.getType())) workString1 = res.getString("DialogDocuments74");
				cellB.setCellValue(new XSSFRichTextString(workString1));
				XSSFCell cellC = nextRow.createCell(2);
				cellC.setCellStyle(styleValue);
				XSSFCell cellD = nextRow.createCell(3);
				cellD.setCellStyle(styleValue);
				XSSFCell cellE = nextRow.createCell(4);
				cellE.setCellStyle(styleValue);
				XSSFCell cellF = nextRow.createCell(5);
				cellF.setCellStyle(styleValue);
				XSSFCell cellG = nextRow.createCell(6);
				cellG.setCellStyle(styleValue);
				XSSFCell cellH = nextRow.createCell(7);
				cellH.setCellStyle(styleValue);
				XSSFCell cellI = nextRow.createCell(8);
				cellI.setCellStyle(styleValue);
				workString1 = "";
				countOfElementArray1 = -1;
				List<TableKeyField> tkfList = tk.getTableKeyFields();
				if (tkfList != null) {
					for (TableKeyField tkf : tkfList) {
						countOfElementArray1++;
						node = new JsonNode("TableKeyField", tkf);
						elementArray1[countOfElementArray1] = node;
					}
				}
				if (countOfElementArray1 > 0) {
					Arrays.sort(elementArray1, 0, countOfElementArray1 + 1);
				}
				for (int j = 0; j <= countOfElementArray1; j++) {
					TableKeyField tkf = (TableKeyField)elementArray1[j].getPojo();
					if (fieldList != null) {
						for (TableField tf : fieldList) {
							if (tkf.getFieldId().equals(tf.getId())) {
								if (workString1.equals("")) {
									workString1 = tf.getName();
								} else {
									if ("XK".equals(tk.getType())) {
										workString1 = workString1 + " > " + tf.getName();
										if ("D".equals(tkf.getAscDesc())) {
											workString1 = workString1 + "(D)";
										}
									} else {
										workString1 = workString1 + " + " + tf.getName();
									}
								}
								break;
							}
						}
					}
				}
				if (workString1.equals("")) {
					workString1 = "*None";
				}
				cellC.setCellValue(new XSSFRichTextString(workString1));
				sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 2, 8));
			}
		}
	}

	void setupTableUsageList(XSSFSheet sheet, Table table) {
		String workString1 = "";
		int rowSequence = 0;
		JsonNode node = null;

		//Label FieldList
		currentRowNumber++;
		XSSFRow row0 = sheet.createRow(currentRowNumber);
		XSSFCell cellA0 = row0.createCell(0);
		cellA0.setCellStyle(styleHeader1);
		XSSFCell cellB0 = row0.createCell(1);
		cellB0.setCellStyle(styleHeader1);
		XSSFCell cellC0 = row0.createCell(2);
		cellC0.setCellStyle(styleHeader1);
		XSSFCell cellD0 = row0.createCell(3);
		cellD0.setCellStyle(styleHeader1);
		XSSFCell cellE0 = row0.createCell(4);
		cellE0.setCellStyle(styleHeader1);
		XSSFCell cellF0 = row0.createCell(5);
		cellF0.setCellStyle(styleHeader1);
		XSSFCell cellG0 = row0.createCell(6);
		cellG0.setCellStyle(styleHeader1);
		XSSFCell cellH0 = row0.createCell(7);
		cellH0.setCellStyle(styleHeader1);
		XSSFCell cellI0 = row0.createCell(8);
		cellI0.setCellStyle(styleHeader1);
		cellA0.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments66")));
		sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 0, 8));

		//Header FunctionList
		currentRowNumber++;
		XSSFRow row1 = sheet.createRow(currentRowNumber);
		XSSFCell cellA1 = row1.createCell(0);
		cellA1.setCellStyle(styleHeader2Number);
		cellA1.setCellValue(new XSSFRichTextString("No."));
		XSSFCell cellB1 = row1.createCell(1);
		cellB1.setCellStyle(styleHeader2);
		XSSFCell cellC1 = row1.createCell(2);
		cellC1.setCellStyle(styleHeader2);
		cellB1.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments17")));
		sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 1, 2));
		XSSFCell cellD1 = row1.createCell(3);
		cellD1.setCellStyle(styleHeader2);
		XSSFCell cellE1 = row1.createCell(4);
		cellE1.setCellStyle(styleHeader2);
		XSSFCell cellF1 = row1.createCell(5);
		cellF1.setCellStyle(styleHeader2);
		XSSFCell cellG1 = row1.createCell(6);
		cellG1.setCellStyle(styleHeader2);
		XSSFCell cellH1 = row1.createCell(7);
		cellH1.setCellStyle(styleHeader2);
		cellD1.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments18")));
		sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 3, 7));
		XSSFCell cellI1 = row1.createCell(8);
		cellI1.setCellStyle(styleHeader2);
		cellI1.setCellValue(new XSSFRichTextString("CRUD"));

		//Rows of Function
		for (Function func : functionList) {
			if (func.getSubsystemId().equals(subsystemID)) {
				List<IOTable> funcIOTables = func.getIOTables();
				if (funcIOTables != null) {
					for (IOTable iot : funcIOTables) {
						if (iot.getTableId().equals(table.getId())) {
							currentRowNumber++;
							XSSFRow nextRow = sheet.createRow(currentRowNumber);
							XSSFCell cellA = nextRow.createCell(0);
							cellA.setCellStyle(styleValueNumber);
							rowSequence++;
							cellA.setCellValue(rowSequence);
							XSSFCell cellB = nextRow.createCell(1);
							cellB.setCellStyle(styleValue);
							XSSFCell cellC = nextRow.createCell(2);
							cellC.setCellStyle(styleValue);
							for (Subsystem ss : subsystemList) {
								if (ss.getId().equals(func.getSubsystemId())) {
									workString1 = ss.getName();
									break;
								}
							}
							cellB.setCellValue(new XSSFRichTextString(workString1));
							sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 1, 2));
							XSSFCell cellD = nextRow.createCell(3);
							cellD.setCellStyle(styleValue);
							XSSFCell cellE = nextRow.createCell(4);
							cellE.setCellStyle(styleValue);
							XSSFCell cellF = nextRow.createCell(5);
							cellF.setCellStyle(styleValue);
							XSSFCell cellG = nextRow.createCell(6);
							cellG.setCellStyle(styleValue);
							XSSFCell cellH = nextRow.createCell(7);
							cellH.setCellStyle(styleValue);
							cellD.setCellValue(new XSSFRichTextString(func.getSortKey() + " " + func.getName()));
							sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 3, 7));
							XSSFCell cellI = nextRow.createCell(8);
							cellI.setCellStyle(styleValue);
							workString1 = "[";
							if ("+".equals(iot.getOpC())) workString1 = workString1 + "C";
							if ("+".equals(iot.getOpR())) workString1 = workString1 + "R";
							if ("+".equals(iot.getOpU())) workString1 = workString1 + "U";
							if ("+".equals(iot.getOpD())) workString1 = workString1 + "D";
							workString1 = workString1 + "]";
							cellI.setCellValue(new XSSFRichTextString(workString1));
						}
					}
				}
			}
		}

		if (rowSequence == 0) {
			currentRowNumber++;
			XSSFRow nextRow = sheet.createRow(currentRowNumber);
			XSSFCell cellA2 = nextRow.createCell(0);
			cellA2.setCellStyle(styleValueNumber);
			XSSFCell cellB2 = nextRow.createCell(1);
			cellB2.setCellStyle(styleValue);
			XSSFCell cellC2 = nextRow.createCell(2);
			cellC2.setCellStyle(styleValue);
			cellB2.setCellValue(new XSSFRichTextString("(None)"));
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 1, 2));
			XSSFCell cellD2 = nextRow.createCell(3);
			cellD2.setCellStyle(styleValue);
			XSSFCell cellE2 = nextRow.createCell(4);
			cellE2.setCellStyle(styleValue);
			XSSFCell cellF2 = nextRow.createCell(5);
			cellF2.setCellStyle(styleValue);
			XSSFCell cellG2 = nextRow.createCell(6);
			cellG2.setCellStyle(styleValue);
			XSSFCell cellH2 = nextRow.createCell(7);
			cellH2.setCellStyle(styleValue);
			sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 3, 7));
			XSSFCell cellI2 = nextRow.createCell(8);
			cellI2.setCellStyle(styleValue);
		}
	}

	void setupTableKeyDetails(XSSFSheet sheet, Table table) {
		String workString1 = "";
		String workString2 = "";
		int rowSequence = 0;
		int sectionNumber = 0;
		int totalSectionNumber = 0;
		JsonNode node = null;

		//Count TableKey
		List<TableKey> tableKeys = table.getTableKeys();
		if (tableKeys != null) {
			totalSectionNumber = tableKeys.size();
		}

		if (tableKeys != null) {
			for (TableKey tk : tableKeys) {
				sectionNumber++;
				currentRowNumber++;
				XSSFRow rowLabel = sheet.createRow(currentRowNumber);
				XSSFCell cellA0 = rowLabel.createCell(0);
				cellA0.setCellStyle(styleHeader1);
				XSSFCell cellB0 = rowLabel.createCell(1);
				cellB0.setCellStyle(styleHeader1);
				XSSFCell cellC0 = rowLabel.createCell(2);
				cellC0.setCellStyle(styleHeader1);
				XSSFCell cellD0 = rowLabel.createCell(3);
				cellD0.setCellStyle(styleHeader1);
				XSSFCell cellE0 = rowLabel.createCell(4);
				cellE0.setCellStyle(styleHeader1);
				XSSFCell cellF0 = rowLabel.createCell(5);
				cellF0.setCellStyle(styleHeader1);
				XSSFCell cellG0 = rowLabel.createCell(6);
				cellG0.setCellStyle(styleHeader1);
				XSSFCell cellH0 = rowLabel.createCell(7);
				cellH0.setCellStyle(styleHeader1);
				XSSFCell cellI0 = rowLabel.createCell(8);
				cellI0.setCellStyle(styleHeader1);
				workString1 = "";
				if ("PK".equals(tk.getType())) workString1 = res.getString("DialogDocuments63");
				if ("FK".equals(tk.getType())) workString1 = res.getString("DialogDocuments64");
				if ("SK".equals(tk.getType())) workString1 = res.getString("DialogDocuments65");
				if ("XK".equals(tk.getType())) workString1 = res.getString("DialogDocuments74");
				cellA0.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments67") + "(" + sectionNumber + "/" + totalSectionNumber + ") " + workString1));
				sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 0, 8));

				currentRowNumber++;
				XSSFRow rowColumnHeading = sheet.createRow(currentRowNumber);
				XSSFCell cellA1 = rowColumnHeading.createCell(0);
				cellA1.setCellStyle(styleHeader2Number);
				cellA1.setCellValue(new XSSFRichTextString("No."));
				XSSFCell cellB1 = rowColumnHeading.createCell(1);
				cellB1.setCellStyle(styleHeader2);
				XSSFCell cellC1 = rowColumnHeading.createCell(2);
				cellC1.setCellStyle(styleHeader2);
				cellB1.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments55")));
				sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 1, 2));
				XSSFCell cellD1 = rowColumnHeading.createCell(3);
				cellD1.setCellStyle(styleHeader2);
				cellD1.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments56")));
				XSSFCell cellE1 = rowColumnHeading.createCell(4);
				cellE1.setCellStyle(styleHeader2);
				cellE1.setCellValue(new XSSFRichTextString("Null"));
				XSSFCell cellF1 = rowColumnHeading.createCell(5);
				cellF1.setCellStyle(styleHeader2);
				cellF1.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments57")));
				XSSFCell cellG1 = rowColumnHeading.createCell(6);
				cellG1.setCellStyle(styleHeader2);
				XSSFCell cellH1 = rowColumnHeading.createCell(7);
				cellH1.setCellStyle(styleHeader2);
				XSSFCell cellI1 = rowColumnHeading.createCell(8);
				cellI1.setCellStyle(styleHeader2);
				cellG1.setCellValue(new XSSFRichTextString(res.getString("DialogDocuments23")));
				sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 6, 8));

				rowSequence = 0;
				countOfElementArray1 = -1;
				List<TableKeyField> tkfList = tk.getTableKeyFields();
				if (tkfList != null) {
					for (TableKeyField tkf : tkfList) {
						countOfElementArray1++;
						node = new JsonNode("TableKeyField", tkf);
						elementArray1[countOfElementArray1] = node;
					}
				}
				if (countOfElementArray1 > 0) {
					Arrays.sort(elementArray1, 0, countOfElementArray1 + 1);
				}
				for (int i = 0; i <= countOfElementArray1; i++) {
					TableKeyField tkf = (TableKeyField)elementArray1[i].getPojo();
					currentRowNumber++;
					XSSFRow nextRow = sheet.createRow(currentRowNumber);
					XSSFCell cellA = nextRow.createCell(0);
					cellA.setCellStyle(styleValueNumber);
					rowSequence++;
					cellA.setCellValue(rowSequence);
					XSSFCell cellB = nextRow.createCell(1);
					cellB.setCellStyle(styleValue);
					XSSFCell cellC = nextRow.createCell(2);
					cellC.setCellStyle(styleValue);
					workString1 = "";
					List<TableField> tableFields = table.getTableFields();
					if (tableFields != null) {
						for (TableField tf : tableFields) {
							if (tkf.getFieldId().equals(tf.getId())) {
								String alias = tf.getAlias() != null ? tf.getAlias() : "";
								String tfName = tf.getName() != null ? tf.getName() : "";
								if (alias.equals("")) {
									workString1 = tfName;
								} else {
									workString1 = alias + " / " + tfName;
								}
								break;
							}
						}
					}
					cellB.setCellValue(new XSSFRichTextString(workString1));
					sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 1, 2));
					XSSFCell cellD = nextRow.createCell(3);
					cellD.setCellStyle(styleValue);
					workString1 = "";
					if (tableFields != null) {
						for (TableField tf : tableFields) {
							if (tkf.getFieldId().equals(tf.getId())) {
								for (DataType dt : dataTypeList) {
									if (dt.getId().equals(tf.getDataTypeId())) {
										workString1 = dt.getName() + " " + (dt.getSqlExpression() != null ? dt.getSqlExpression() : "");
										break;
									}
								}
								break;
							}
						}
					}
					cellD.setCellValue(new XSSFRichTextString(workString1));
					XSSFCell cellE = nextRow.createCell(4);
					cellE.setCellStyle(styleValue);
					workString1 = "";
					if (tableFields != null) {
						for (TableField tf : tableFields) {
							if (tkf.getFieldId().equals(tf.getId())) {
								String notNull = tf.getNotNull() != null ? tf.getNotNull() : "";
								if ("true".equals(notNull)) {
									workString1 = res.getString("DialogDocuments58");
								} else {
									workString1 = res.getString("DialogDocuments59");
								}
								break;
							}
						}
					}
					cellE.setCellValue(new XSSFRichTextString(workString1));
					XSSFCell cellF = nextRow.createCell(5);
					cellF.setCellStyle(styleValue);
					workString1 = "";
					if (tableFields != null) {
						for (TableField tf : tableFields) {
							if (tkf.getFieldId().equals(tf.getId())) {
								workString1 = tf.getDefault() != null ? tf.getDefault() : "";
								break;
							}
						}
					}
					cellF.setCellValue(new XSSFRichTextString(workString1));
					XSSFCell cellG = nextRow.createCell(6);
					cellG.setCellStyle(styleValue);
					XSSFCell cellH = nextRow.createCell(7);
					cellH.setCellStyle(styleValue);
					XSSFCell cellI = nextRow.createCell(8);
					cellI.setCellStyle(styleValue);
					workString1 = "";
					if (tableFields != null) {
						for (TableField tf : tableFields) {
							if (tkf.getFieldId().equals(tf.getId())) {
								workString1 = substringLinesWithTokenOfEOL(tf.getDescriptions() != null ? tf.getDescriptions() : "", "\n");
								break;
							}
						}
					}
					cellG.setCellValue(new XSSFRichTextString(workString1));
					sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 6, 8));
				}

				if (rowSequence == 0) {
					currentRowNumber++;
					XSSFRow nextRow = sheet.createRow(currentRowNumber);
					XSSFCell cellA2 = nextRow.createCell(0);
					cellA2.setCellStyle(styleValueNumber);
					XSSFCell cellB2 = nextRow.createCell(1);
					cellB2.setCellStyle(styleValue);
					XSSFCell cellC2 = nextRow.createCell(2);
					cellC2.setCellStyle(styleValue);
					cellB2.setCellValue(new XSSFRichTextString("(None)"));
					sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 1, 2));
					XSSFCell cellD2 = nextRow.createCell(3);
					cellD2.setCellStyle(styleValue);
					XSSFCell cellE2 = nextRow.createCell(4);
					cellE2.setCellStyle(styleValue);
					XSSFCell cellF2 = nextRow.createCell(5);
					cellF2.setCellStyle(styleValue);
					XSSFCell cellG2 = nextRow.createCell(6);
					cellG2.setCellStyle(styleValue);
					XSSFCell cellH2 = nextRow.createCell(7);
					cellH2.setCellStyle(styleValue);
					XSSFCell cellI2 = nextRow.createCell(8);
					cellI2.setCellStyle(styleValue);
					sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 6, 8));
				}
			}
		}
	}

	String getStringValueOfDateAndTime() {
		Calendar cal = Calendar.getInstance();
		String workString = "";
		workString = workString + String.format("%04d", cal.get(Calendar.YEAR));
		workString = workString + String.format("%02d", cal.get(Calendar.MONTH) + 1);
		workString = workString + String.format("%02d", cal.get(Calendar.DAY_OF_MONTH));
		workString = workString + String.format("%02d", cal.get(Calendar.HOUR_OF_DAY));
		workString = workString + String.format("%02d", cal.get(Calendar.MINUTE));
		workString = workString + String.format("%02d", cal.get(Calendar.SECOND));
		return workString;
	}

	String substringLinesWithTokenOfEOL(String text, String token) {
		String workString = "";
		if (text == null || text.equals("")) {
			return "";
		}
		StringTokenizer st = new StringTokenizer(text, token);
		while (st.hasMoreTokens()) {
			workString = workString + st.nextToken() + "\n";
		}
		return workString;
	}

	void jComboBoxSubsystems_actionPerformed(ActionEvent e) {
		comboBoxModelTables.removeAllElements();
		comboBoxModelFunctions.removeAllElements();

		if (jComboBoxSubsystems.getSelectedIndex() > 0) {
			JsonNode workNode = (JsonNode)comboBoxModelSubsystems.getSelectedItem();
			Subsystem ss = (Subsystem)workNode.getPojo();
			subsystemID = ss.getId();

			comboBoxModelTables.insertElementAt(res.getString("DialogDocuments68"), 0);
			for (Table table : tableList) {
				if (table.getSubsystemId().equals(subsystemID)) {
					comboBoxModelTables.addElement(new JsonNode("Table", table));
				}
			}
			comboBoxModelTables.sortElements();
			comboBoxModelTables.setSelectedItem(comboBoxModelTables.getElementAt(0));

			comboBoxModelFunctions.insertElementAt(res.getString("DialogDocuments69"), 0);
			for (Function func : functionList) {
				if (func.getSubsystemId().equals(subsystemID)) {
					comboBoxModelFunctions.addElement(new JsonNode("Function", func));
				}
			}
			comboBoxModelFunctions.sortElements();
			comboBoxModelFunctions.setSelectedItem(comboBoxModelFunctions.getElementAt(0));
		}
	}

	void jRadioButtonTable_changeStateChanged(ChangeEvent e) {
		jComboBoxTables.setEnabled(jRadioButtonTable.isSelected());
		jComboBoxFunctions.setEnabled(!jRadioButtonTable.isSelected());
	}

	void jRadioButtonFunction_changeStateChanged(ChangeEvent e) {
		jComboBoxTables.setEnabled(!jRadioButtonFunction.isSelected());
		jComboBoxFunctions.setEnabled(jRadioButtonFunction.isSelected());
	}

	void jButtonCloseDialog_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}

class JsonNode implements Comparable<JsonNode> {
	private String nodeType_;
	private Object pojo_;

	public JsonNode(String type, Object pojo) {
		nodeType_ = type;
		pojo_ = pojo;
	}

	public String toString() {
		return getName();
	}

	public String getName() {
		if (pojo_ instanceof Subsystem) {
			Subsystem s = (Subsystem) pojo_;
			return s.getSortKey() + " " + s.getName();
		} else if (pojo_ instanceof Table) {
			Table t = (Table) pojo_;
			return t.getSortKey() + " " + t.getName();
		} else if (pojo_ instanceof Function) {
			Function f = (Function) pojo_;
			return f.getSortKey() + " " + f.getName();
		} else if (pojo_ instanceof IOPanel) {
			return ((IOPanel)pojo_).getName();
		} else if (pojo_ instanceof IOSpool) {
			return ((IOSpool)pojo_).getName();
		} else if (pojo_ instanceof IOWebPage) {
			return ((IOWebPage)pojo_).getName();
		} else if (pojo_ instanceof IOTable) {
			return ((IOTable)pojo_).getName();
		} else if (pojo_ instanceof TableField) {
			TableField tf = (TableField) pojo_;
			return tf.getSortKey() + " " + tf.getName();
		} else if (pojo_ instanceof TableKeyField) {
			TableKeyField tkf = (TableKeyField) pojo_;
			return tkf.getSortKey() + " " + tkf.getFieldId();
		} else if (pojo_ instanceof IOPanelField) {
			return ((IOPanelField)pojo_).getName();
		} else if (pojo_ instanceof IOSpoolField) {
			return ((IOSpoolField)pojo_).getName();
		} else if (pojo_ instanceof IOWebPageField) {
			return ((IOWebPageField)pojo_).getName();
		} else if (pojo_ instanceof IOTableField) {
			return ((IOTableField)pojo_).getFieldId();
		}
		return "Unknown";
	}

	public Object getPojo() {
		return pojo_;
	}

	public String getType() {
		return nodeType_;
	}

	public int compareTo(JsonNode other) {
		try {
			if (pojo_ instanceof Subsystem && other.pojo_ instanceof Subsystem) {
				return ((Subsystem)pojo_).getSortKey().compareTo(((Subsystem)other.pojo_).getSortKey());
			}
			if (pojo_ instanceof Table && other.pojo_ instanceof Table) {
				return ((Table)pojo_).getSortKey().compareTo(((Table)other.pojo_).getSortKey());
			}
			if (pojo_ instanceof Function && other.pojo_ instanceof Function) {
				return ((Function)pojo_).getSortKey().compareTo(((Function)other.pojo_).getSortKey());
			}
			if (pojo_ instanceof TableField && other.pojo_ instanceof TableField) {
				return ((TableField)pojo_).getSortKey().compareTo(((TableField)other.pojo_).getSortKey());
			}
			if (pojo_ instanceof TableKeyField && other.pojo_ instanceof TableKeyField) {
				return ((TableKeyField)pojo_).getSortKey().compareTo(((TableKeyField)other.pojo_).getSortKey());
			}
			if (pojo_ instanceof IOPanelField && other.pojo_ instanceof IOPanelField) {
				return ((IOPanelField)pojo_).getSortKey().compareTo(((IOPanelField)other.pojo_).getSortKey());
			}
			if (pojo_ instanceof IOSpoolField && other.pojo_ instanceof IOSpoolField) {
				return ((IOSpoolField)pojo_).getSortKey().compareTo(((IOSpoolField)other.pojo_).getSortKey());
			}
			if (pojo_ instanceof IOWebPageField && other.pojo_ instanceof IOWebPageField) {
				return ((IOWebPageField)pojo_).getSortKey().compareTo(((IOWebPageField)other.pojo_).getSortKey());
			}
			if (pojo_ instanceof IOTableField && other.pojo_ instanceof IOTableField) {
				return ((IOTableField)pojo_).getSortKey().compareTo(((IOTableField)other.pojo_).getSortKey());
			}
			if (pojo_ instanceof IOPanel && other.pojo_ instanceof IOPanel) {
				return ((IOPanel)pojo_).getSortKey().compareTo(((IOPanel)other.pojo_).getSortKey());
			}
			if (pojo_ instanceof IOSpool && other.pojo_ instanceof IOSpool) {
				return ((IOSpool)pojo_).getSortKey().compareTo(((IOSpool)other.pojo_).getSortKey());
			}
			if (pojo_ instanceof IOWebPage && other.pojo_ instanceof IOWebPage) {
				return ((IOWebPage)pojo_).getSortKey().compareTo(((IOWebPage)other.pojo_).getSortKey());
			}
			if (pojo_ instanceof IOTable && other.pojo_ instanceof IOTable) {
				return ((IOTable)pojo_).getSortKey().compareTo(((IOTable)other.pojo_).getSortKey());
			}
		} catch(Exception e) {
			// ignore
		}
		return 0;
	}
}

class SortableJsonNodeComboBoxModel extends DefaultComboBoxModel {
	private static final long serialVersionUID = 1L;

	public void sortElements() {
		Object[] elements = new Object[getSize()];
		for (int i = 0; i < getSize(); i++) {
			elements[i] = getElementAt(i);
		}
		Arrays.sort(elements, 0, getSize(), new Comparator<Object>() {
			public int compare(Object o1, Object o2) {
				if (o1 instanceof JsonNode && o2 instanceof JsonNode) {
					return ((JsonNode)o1).compareTo((JsonNode)o2);
				}
				return 0;
			}
		});
		removeAllElements();
		for (Object element : elements) {
			addElement(element);
		}
	}
}

class DialogDocuments_jButtonStart_actionAdapter implements ActionListener {
	private DialogDocuments adaptee;
	DialogDocuments_jButtonStart_actionAdapter(DialogDocuments adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.jButtonStart_actionPerformed(e);
	}
}

class DialogDocuments_jButtonCloseDialog_actionAdapter implements ActionListener {
	private DialogDocuments adaptee;
	DialogDocuments_jButtonCloseDialog_actionAdapter(DialogDocuments adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.jButtonCloseDialog_actionPerformed(e);
	}
}

class DialogDocuments_jComboBoxSubsystems_actionAdapter implements ActionListener {
	private DialogDocuments adaptee;
	DialogDocuments_jComboBoxSubsystems_actionAdapter(DialogDocuments adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.jComboBoxSubsystems_actionPerformed(e);
	}
}

class DialogDocuments_jRadioButtonTable_changeAdapter implements ChangeListener {
	private DialogDocuments adaptee;
	DialogDocuments_jRadioButtonTable_changeAdapter(DialogDocuments adaptee) {
		this.adaptee = adaptee;
	}
	public void stateChanged(ChangeEvent e) {
		adaptee.jRadioButtonTable_changeStateChanged(e);
	}
}

class DialogDocuments_jRadioButtonFunction_changeAdapter implements ChangeListener {
	private DialogDocuments adaptee;
	DialogDocuments_jRadioButtonFunction_changeAdapter(DialogDocuments adaptee) {
		this.adaptee = adaptee;
	}
	public void stateChanged(ChangeEvent e) {
		adaptee.jRadioButtonFunction_changeStateChanged(e);
	}
}

	