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
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.*;

import xeadModeler.Modeler;
import xeadModeler.model.json.*;

public class DialogMatrixList extends JDialog {
	private static final long serialVersionUID = 1L;
	private static ResourceBundle res = ResourceBundle.getBundle("xeadModeler.Res");
	private Modeler frame_;
	private String fileName_;
	private String xlsFileName;
	private JPanel jPanelMain = new JPanel();
	private JPanel jPanelSouth = new JPanel();
	private JPanel jPanelCenter = new JPanel();
	private BorderLayout borderLayout1 = new BorderLayout();
	private GridLayout gridLayout1 = new GridLayout();
	private JCheckBox jCheckBoxSubjectAreaAndTask = new JCheckBox();
	private JCheckBox jCheckBoxSubjectAreaAndFunction = new JCheckBox();
	private JCheckBox jCheckBoxTaskAndFunction = new JCheckBox();
	private JCheckBox jCheckBoxTaskAndTable = new JCheckBox();
	private JCheckBox jCheckBoxTableAndFunction = new JCheckBox();
	private JButton jButtonStart = new JButton();
	private JProgressBar jProgressBar = new JProgressBar();
	private JButton jButtonCloseDialog = new JButton();
	private java.util.List<SubjectArea> subjectAreaList = null;
	private java.util.List<Task> taskList = null;
	private java.util.List<TaskType> taskTypeList = null;
	private java.util.List<Role> roleList = null;
	private java.util.List<Subsystem> subsystemList = null;
	private java.util.List<Table> tableList = null;
	private java.util.List<TableType> tableTypeList = null;
	private java.util.List<Function> functionList = null;
	private java.util.List<FunctionType> functionTypeList = null;
	private int currentRowNumber = 0;
	private int countOfErrors = 0;
	private ArrayList<String> keyList = new ArrayList<String>();
	private HashMap<String, String> hashKeyList = new HashMap<String, String>();

	private XSSFFont fontTitle = null;
	private XSSFFont fontHeader1 = null;
	private XSSFFont fontHeader2 = null;
	private XSSFFont fontValue = null;
	private XSSFWorkbook workBook = null;
	private XSSFCellStyle styleTitle = null;
	private XSSFCellStyle styleHeaderRotated = null;
	private XSSFCellStyle styleHeaderNormal = null;
	private XSSFCellStyle styleHeaderNumber = null;
	private XSSFCellStyle styleValue = null;
	private XSSFCellStyle styleValueNumber = null;
	private XSSFCellStyle styleCheck = null;

	public DialogMatrixList(Modeler frame, String title, boolean modal) {
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

	public DialogMatrixList(Modeler frame) {
		this(frame, "", true);
	}

	private void jbInit() throws Exception {
		jPanelMain.setLayout(borderLayout1);
		jPanelMain.setPreferredSize(new Dimension(300, 180));
		jPanelMain.setBorder(null);
		jPanelSouth.setBorder(BorderFactory.createEtchedBorder());
		jPanelSouth.setLayout(null);
		jPanelSouth.setPreferredSize(new Dimension(800, 45));
		jPanelCenter.setLayout(gridLayout1);
		gridLayout1.setColumns(1);
		gridLayout1.setRows(5);
		jPanelCenter.setLayout(gridLayout1);
		jCheckBoxSubjectAreaAndTask.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jCheckBoxSubjectAreaAndTask.setText(res.getString("S75"));
		jCheckBoxSubjectAreaAndTask.addChangeListener(new DialogMatrixList_jCheckBox_changeAdapter(this));
		jCheckBoxSubjectAreaAndFunction.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jCheckBoxSubjectAreaAndFunction.setText(res.getString("S76"));
		jCheckBoxSubjectAreaAndFunction.addChangeListener(new DialogMatrixList_jCheckBox_changeAdapter(this));
		jCheckBoxTaskAndFunction.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jCheckBoxTaskAndFunction.setText(res.getString("S77"));
		jCheckBoxTaskAndFunction.addChangeListener(new DialogMatrixList_jCheckBox_changeAdapter(this));
		jCheckBoxTaskAndTable.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jCheckBoxTaskAndTable.setText(res.getString("S78"));
		jCheckBoxTaskAndTable.addChangeListener(new DialogMatrixList_jCheckBox_changeAdapter(this));
		jCheckBoxTableAndFunction.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jCheckBoxTableAndFunction.setText(res.getString("S79"));
		jCheckBoxTableAndFunction.addChangeListener(new DialogMatrixList_jCheckBox_changeAdapter(this));

		getContentPane().add(jPanelMain);
		jPanelMain.add(jPanelSouth, BorderLayout.SOUTH);
		jPanelMain.add(jPanelCenter, BorderLayout.CENTER);
		jPanelCenter.add(jCheckBoxSubjectAreaAndTask, null);
		jPanelCenter.add(jCheckBoxSubjectAreaAndFunction, null);
		jPanelCenter.add(jCheckBoxTaskAndFunction, null);
		jPanelCenter.add(jCheckBoxTaskAndTable, null);
		jPanelCenter.add(jCheckBoxTableAndFunction, null);

		jButtonCloseDialog.setBounds(new Rectangle(13, 8, 110, 27));
		jButtonCloseDialog.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jButtonCloseDialog.setText(res.getString("DialogDocuments08"));
		jButtonCloseDialog.addActionListener(new DialogMatrixList_jButtonCloseDialog_actionAdapter(this));
		jButtonStart.setBounds(new Rectangle(170, 8, 120, 27));
		jButtonStart.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jButtonStart.setText(res.getString("DialogDocuments07"));
		jButtonStart.addActionListener(new DialogMatrixList_jButtonStart_actionAdapter(this));
		jProgressBar.setBounds(new Rectangle(170, 8, 120, 27));
		jProgressBar.setVisible(false);
		jProgressBar.setStringPainted(true);
		jPanelSouth.add(jButtonCloseDialog, null);
		jPanelSouth.add(jButtonStart, null);
		jPanelSouth.add(jProgressBar, null);

		this.setResizable(false);
		this.setTitle(res.getString("S74"));
		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension dlgSize = this.getPreferredSize();
		this.setLocation((scrSize.width - dlgSize.width)/2 , (scrSize.height - dlgSize.height)/2);
		this.pack();
	}

	public String request(String fileName) {
		fileName_ = fileName;
		xlsFileName = "";

		XeadSystem system = frame_.getXeadDocumentModel().getXeadSystem();
		subsystemList = system.getSubsystems();
		subjectAreaList = system.getSubjectAreas();
		taskTypeList = system.getTaskTypes();
		taskList = system.getTasks();
		roleList = system.getRoles();
		tableList = system.getTables();
		tableTypeList = system.getTableTypes();
		functionList = system.getFunctions();
		functionTypeList = system.getFunctionTypes();

		jCheckBoxSubjectAreaAndTask.setSelected(false);
		jCheckBoxSubjectAreaAndFunction.setSelected(false);
		jCheckBoxTaskAndFunction.setSelected(false);
		jCheckBoxTaskAndTable.setSelected(false);
		jCheckBoxTableAndFunction.setSelected(false);
		jButtonStart.setEnabled(false);
		super.setVisible(true);

		return xlsFileName;
	}

	void jButtonStart_actionPerformed(ActionEvent e) {
		int countOfDefinitions = 0;

		try{
			setCursor(new Cursor(Cursor.WAIT_CURSOR));
			jProgressBar.setVisible(true);
			jButtonStart.setVisible(false);
			countOfErrors = 0;

			File file = new File(fileName_);
			xlsFileName = file.getParent() + File.separator + "MatrixList" + getStringValueOfDateAndTime() + ".xlsx";

			if (jCheckBoxSubjectAreaAndTask.isSelected()) {
				countOfDefinitions = countOfDefinitions + (taskList != null ? taskList.size() : 0);
			}
			if (jCheckBoxSubjectAreaAndFunction.isSelected()) {
				countOfDefinitions = countOfDefinitions + (functionList != null ? functionList.size() : 0) + (subjectAreaList != null ? subjectAreaList.size() : 0);
			}
			if (jCheckBoxTaskAndFunction.isSelected()) {
				countOfDefinitions = countOfDefinitions + (functionList != null ? functionList.size() : 0) + (taskList != null ? taskList.size() : 0);
			}
			if (jCheckBoxTaskAndTable.isSelected()) {
				countOfDefinitions = countOfDefinitions + (tableList != null ? tableList.size() : 0) + (taskList != null ? taskList.size() : 0);
			}
			if (jCheckBoxTableAndFunction.isSelected()) {
				countOfDefinitions = countOfDefinitions + (tableList != null ? tableList.size() : 0) + (functionList != null ? functionList.size() : 0);
			}

			jProgressBar.setValue(0);
			jProgressBar.setMaximum(countOfDefinitions);
			jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());
			FileOutputStream fileOut = new FileOutputStream(xlsFileName);
			createWorkBookAndStyles();

			if (jCheckBoxSubjectAreaAndTask.isSelected()) {
				generateSheetForSubjectAreaAndTask();
			}
			if (jCheckBoxSubjectAreaAndFunction.isSelected()) {
				generateSheetForSubjectAreaAndFunction();
			}
			if (jCheckBoxTaskAndFunction.isSelected()) {
				generateSheetForTaskAndFunction();
			}
			if (jCheckBoxTaskAndTable.isSelected()) {
				generateSheetForTaskAndTable();
			}
			if (jCheckBoxTableAndFunction.isSelected()) {
				generateSheetForTableAndFunction();
			}

			workBook.write(fileOut);
			fileOut.close();

		} catch(Exception ex1) {
			ex1.printStackTrace();
		} finally {
			jProgressBar.setVisible(false);
			jButtonStart.setVisible(true);
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

			if (countOfErrors == 0) {
				File workXlsFile = new File(xlsFileName);
				try {
					setCursor(new Cursor(Cursor.WAIT_CURSOR));
					frame_.desktop.open(workXlsFile);
				} catch (Exception ex) {
				} finally {
					setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}
			} else {
				JOptionPane.showMessageDialog(this.getContentPane(), "Generating Matrix List has failed possibly because that too many columns for a Excel sheet has been demanded.");
			}
			super.setVisible(false);
		}
	}
	
	void generateSheetForSubjectAreaAndTask() {
		try {
			currentRowNumber = 0;
			countOfErrors = 0;

			XSSFSheet sheet = workBook.createSheet(res.getString("S75"));
			sheet.setDefaultRowHeight((short)300);
			sheet.setDefaultColumnWidth(9);
			sheet.setColumnWidth(0, 1100);
			sheet.setColumnWidth(1, 6000); // Task Type //
			sheet.setColumnWidth(2, 6000); // Role //
			sheet.setColumnWidth(3, 8000); // Task //

			XSSFRow topRow = sheet.createRow(currentRowNumber);
			topRow.setHeight((short)2500);
			XSSFCell cellSequence = topRow.createCell(0);
			cellSequence.setCellStyle(styleHeaderNumber);
			cellSequence.setCellValue(new XSSFRichTextString("No"));
			XSSFCell cellTaskType = topRow.createCell(1);
			cellTaskType.setCellStyle(styleHeaderNormal);
			cellTaskType.setCellValue(new XSSFRichTextString(res.getString("S362")));
			XSSFCell cellRoleName = topRow.createCell(2);
			cellRoleName.setCellStyle(styleHeaderNormal);
			cellRoleName.setCellValue(new XSSFRichTextString(res.getString("S348")));
			XSSFCell cellTaskName = topRow.createCell(3);
			cellTaskName.setCellStyle(styleHeaderNormal);
			cellTaskName.setCellValue(new XSSFRichTextString(res.getString("S360")));

			setupSubjectAreaColumnsForTask(sheet, topRow);
			setupTaskRowsForSubjectAreas(sheet);

		} catch (Exception ex) {
			countOfErrors++;
		}
	}
	
	void generateSheetForSubjectAreaAndFunction() {
		try {
			currentRowNumber = 0;
			countOfErrors = 0;

			XSSFSheet sheet = workBook.createSheet(res.getString("S76"));
			sheet.setDefaultRowHeight((short)300);
			sheet.setDefaultColumnWidth(9);
			sheet.setColumnWidth(0, 1100);
			sheet.setColumnWidth(1, 6000); // Subsystem //
			sheet.setColumnWidth(2, 12000); // Function //
			sheet.setColumnWidth(3, 6000); // Function Type //

			XSSFRow topRow = sheet.createRow(currentRowNumber);
			topRow.setHeight((short)2500);
			XSSFCell cellSequence = topRow.createCell(0);
			cellSequence.setCellStyle(styleHeaderNumber);
			cellSequence.setCellValue(new XSSFRichTextString("No"));
			XSSFCell cellTaskType = topRow.createCell(1);
			cellTaskType.setCellStyle(styleHeaderNormal);
			cellTaskType.setCellValue(new XSSFRichTextString(res.getString("S413")));
			XSSFCell cellRoleName = topRow.createCell(2);
			cellRoleName.setCellStyle(styleHeaderNormal);
			cellRoleName.setCellValue(new XSSFRichTextString(res.getString("S415")));
			XSSFCell cellTaskName = topRow.createCell(3);
			cellTaskName.setCellStyle(styleHeaderNormal);
			cellTaskName.setCellValue(new XSSFRichTextString(res.getString("S417")));

			setupSubjectAreaColumnsForFunction(sheet, topRow);
			setupFunctionRowsForSubjectAreas(sheet);

		} catch (Exception ex) {
			countOfErrors++;
		}
	}
	
	void generateSheetForTaskAndFunction() {
		try {
			currentRowNumber = 0;
			countOfErrors = 0;

			XSSFSheet sheet = workBook.createSheet(res.getString("S77"));
			sheet.setDefaultRowHeight((short)300);
			sheet.setDefaultColumnWidth(9);
			sheet.setColumnWidth(0, 1100);
			sheet.setColumnWidth(1, 6000); // Subsystem //
			sheet.setColumnWidth(2, 12000); // Function //
			sheet.setColumnWidth(3, 6000); // Function Type //

			XSSFRow topRow = sheet.createRow(currentRowNumber);
			topRow.setHeight((short)2500);
			XSSFCell cellSequence = topRow.createCell(0);
			cellSequence.setCellStyle(styleHeaderNumber);
			cellSequence.setCellValue(new XSSFRichTextString("No"));
			XSSFCell cellTaskType = topRow.createCell(1);
			cellTaskType.setCellStyle(styleHeaderNormal);
			cellTaskType.setCellValue(new XSSFRichTextString(res.getString("S413")));
			XSSFCell cellRoleName = topRow.createCell(2);
			cellRoleName.setCellStyle(styleHeaderNormal);
			cellRoleName.setCellValue(new XSSFRichTextString(res.getString("S415")));
			XSSFCell cellTaskName = topRow.createCell(3);
			cellTaskName.setCellStyle(styleHeaderNormal);
			cellTaskName.setCellValue(new XSSFRichTextString(res.getString("S417")));

			setupTaskColumnsForFunction(sheet, topRow);
			setupFunctionRowsForTasks(sheet);

		} catch (Exception ex) {
			countOfErrors++;
		}
	}
	
	void generateSheetForTaskAndTable() {
		try {
			currentRowNumber = 0;
			countOfErrors = 0;

			XSSFSheet sheet = workBook.createSheet(res.getString("S78"));
			sheet.setDefaultRowHeight((short)300);
			sheet.setDefaultColumnWidth(9);
			sheet.setColumnWidth(0, 1100);
			sheet.setColumnWidth(1, 6000); // Subsystem //
			sheet.setColumnWidth(2, 8000); // Table //
			sheet.setColumnWidth(3, 4500); // Table Type //

			XSSFRow topRow = sheet.createRow(currentRowNumber);
			topRow.setHeight((short)2500);
			XSSFCell cellSequence = topRow.createCell(0);
			cellSequence.setCellStyle(styleHeaderNumber);
			cellSequence.setCellValue(new XSSFRichTextString("No"));
			XSSFCell cellTaskType = topRow.createCell(1);
			cellTaskType.setCellStyle(styleHeaderNormal);
			cellTaskType.setCellValue(new XSSFRichTextString(res.getString("S413")));
			XSSFCell cellRoleName = topRow.createCell(2);
			cellRoleName.setCellStyle(styleHeaderNormal);
			cellRoleName.setCellValue(new XSSFRichTextString(res.getString("S471")));
			XSSFCell cellTaskName = topRow.createCell(3);
			cellTaskName.setCellStyle(styleHeaderNormal);
			cellTaskName.setCellValue(new XSSFRichTextString(res.getString("S477")));

			setupTaskColumnsForTable(sheet, topRow);
			setupTableRowsForTask(sheet);

		} catch (Exception ex) {
			countOfErrors++;
		}
	}
	
	void generateSheetForTableAndFunction() {
		try {
			currentRowNumber = 0;
			countOfErrors = 0;

			XSSFSheet sheet = workBook.createSheet(res.getString("S79"));
			sheet.setDefaultRowHeight((short)300);
			sheet.setDefaultColumnWidth(9);
			sheet.setColumnWidth(0, 1100);
			sheet.setColumnWidth(1, 6000); // Subsystem //
			sheet.setColumnWidth(2, 12000); // Function //
			sheet.setColumnWidth(3, 6000); // Function Type //

			XSSFRow topRow = sheet.createRow(currentRowNumber);
			topRow.setHeight((short)2500);
			XSSFCell cellSequence = topRow.createCell(0);
			cellSequence.setCellStyle(styleHeaderNumber);
			cellSequence.setCellValue(new XSSFRichTextString("No"));
			XSSFCell cellTaskType = topRow.createCell(1);
			cellTaskType.setCellStyle(styleHeaderNormal);
			cellTaskType.setCellValue(new XSSFRichTextString(res.getString("S413")));
			XSSFCell cellRoleName = topRow.createCell(2);
			cellRoleName.setCellStyle(styleHeaderNormal);
			cellRoleName.setCellValue(new XSSFRichTextString(res.getString("S415")));
			XSSFCell cellTaskName = topRow.createCell(3);
			cellTaskName.setCellStyle(styleHeaderNormal);
			cellTaskName.setCellValue(new XSSFRichTextString(res.getString("S417")));

			setupTableColumnsForFunction(sheet, topRow);
			setupFunctionRowsForTable(sheet);

		} catch (Exception ex) {
			countOfErrors++;
		}
	}

	void setupSubjectAreaColumnsForTask(XSSFSheet sheet, XSSFRow topRow) {
		SubjectArea subjectArea;
		String workString = "";
		java.util.List<SubjectArea> sortedList = new ArrayList<>();

		if (subjectAreaList != null) {
			for (SubjectArea sa : subjectAreaList) {
				sortedList.add(sa);
			}
			Collections.sort(sortedList, (s1, s2) -> s1.getSortKey().compareTo(s2.getSortKey()));
		}
		
		for (int i = 0; i < sortedList.size(); i++) {
			subjectArea = sortedList.get(i);
			XSSFCell cell = topRow.createCell(i+4);
			cell.setCellStyle(styleHeaderRotated);
			workString = subjectArea.getSortKey() + " " + subjectArea.getName();
			cell.setCellValue(new XSSFRichTextString(workString));
			sheet.setColumnWidth(i+4, 1000);
		}
		if (sortedList.size() == 0) {
			XSSFCell cell = topRow.createCell(3);
			cell.setCellStyle(styleHeaderNormal);
			cell.setCellValue(new XSSFRichTextString("N/A"));
		}
	}

	void setupSubjectAreaColumnsForFunction(XSSFSheet sheet, XSSFRow topRow) {
		SubjectArea subjectArea;
		Function function;
		Task task;
		TaskFunctionIO taskFunctionIO;
		String workString = "";
		java.util.List<SubjectArea> sortedList = new ArrayList<>();

		keyList.clear();
		if (subjectAreaList != null) {
			for (SubjectArea sa : subjectAreaList) {
				sortedList.add(sa);
			}
			Collections.sort(sortedList, (s1, s2) -> s1.getSortKey().compareTo(s2.getSortKey()));
		}
		
		for (int i = 0; i < sortedList.size(); i++) {
			subjectArea = sortedList.get(i);
			XSSFCell cell = topRow.createCell(i+4);
			cell.setCellStyle(styleHeaderRotated);
			workString = subjectArea.getSortKey() + " " + subjectArea.getName();
			cell.setCellValue(new XSSFRichTextString(workString));
			sheet.setColumnWidth(i+4, 1000);
			
			java.util.List<DataflowNode> dataflowNodes = subjectArea.getDataflowNodes();
			if (dataflowNodes != null) {
				for (DataflowNode dataflowNode : dataflowNodes) {
					if (dataflowNode.getType().equals("Process")) {
						String taskID = dataflowNode.getTaskID();
						if (taskList != null) {
							for (Task t : taskList) {
								if (t.getId().equals(taskID)) {
									java.util.List<TaskFunctionIO> taskFunctionIOs = t.getTaskFunctionIOs();
									if (taskFunctionIOs != null) {
										for (TaskFunctionIO tfio : taskFunctionIOs) {
											workString = subjectArea.getId() + "," + tfio.getFunctionId(); 
											if (!keyList.contains(workString)) {
												keyList.add(workString);
											}
										}
									}
									break;
								}
							}
						}
					}
				}
			}

			jProgressBar.setValue(jProgressBar.getValue() + 1);
			jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());
		}
		if (sortedList.size() == 0) {
			XSSFCell cell = topRow.createCell(3);
			cell.setCellStyle(styleHeaderNormal);
			cell.setCellValue(new XSSFRichTextString("N/A"));
		}
	}

	void setupTaskColumnsForFunction(XSSFSheet sheet, XSSFRow topRow) {
		Task task;
		TaskFunctionIO taskFunctionIO;
		String workString = "";
		java.util.List<Task> sortedList = new ArrayList<>();

		keyList.clear();
		if (taskList != null) {
			for (Task t : taskList) {
				sortedList.add(t);
			}
			Collections.sort(sortedList, (t1, t2) -> t1.getSortKey().compareTo(t2.getSortKey()));
		}
		
		for (int i = 0; i < sortedList.size(); i++) {
			task = sortedList.get(i);
			XSSFCell cell = topRow.createCell(i+4);
			cell.setCellStyle(styleHeaderRotated);
			workString = task.getSortKey() + " " + task.getName();
			cell.setCellValue(new XSSFRichTextString(workString));
			sheet.setColumnWidth(i+4, 1000);
			
			java.util.List<TaskFunctionIO> taskFunctionIOs = task.getTaskFunctionIOs();
			if (taskFunctionIOs != null) {
				for (TaskFunctionIO tfio : taskFunctionIOs) {
					workString = task.getId() + "," + tfio.getFunctionId(); 
					if (!keyList.contains(workString)) {
						keyList.add(workString);
					}
				}
			}

			jProgressBar.setValue(jProgressBar.getValue() + 1);
			jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());
		}
		if (sortedList.size() == 0) {
			XSSFCell cell = topRow.createCell(3);
			cell.setCellStyle(styleHeaderNormal);
			cell.setCellValue(new XSSFRichTextString("N/A"));
		}
	}

	void setupTaskColumnsForTable(XSSFSheet sheet, XSSFRow topRow) {
		Task task;
		TaskFunctionIO taskFunctionIO;
		Function function;
		IOTable ioTable;
		String workString = "";
		String crudMark = "";
		java.util.List<Task> sortedList = new ArrayList<>();

		hashKeyList.clear();
		if (taskList != null) {
			for (Task t : taskList) {
				sortedList.add(t);
			}
			Collections.sort(sortedList, (t1, t2) -> t1.getSortKey().compareTo(t2.getSortKey()));
		}
		
		for (int i = 0; i < sortedList.size(); i++) {
			task = sortedList.get(i);
			XSSFCell cell = topRow.createCell(i+4);
			cell.setCellStyle(styleHeaderRotated);
			workString = task.getSortKey() + " " + task.getName();
			cell.setCellValue(new XSSFRichTextString(workString));
			sheet.setColumnWidth(i+4, 1400);
			
			java.util.List<TaskFunctionIO> taskFunctionIOs = task.getTaskFunctionIOs();
			if (taskFunctionIOs != null) {
				for (TaskFunctionIO tfio : taskFunctionIOs) {
					if (functionList != null) {
						for (Function f : functionList) {
							if (f.getId().equals(tfio.getFunctionId())) {
								java.util.List<IOTable> ioTables = f.getIOTables();
								if (ioTables != null) {
									for (IOTable iot : ioTables) {
										crudMark = "";
										workString = task.getId() + "," + iot.getTableId(); 
										if (hashKeyList.containsKey(workString)) {
											crudMark = hashKeyList.get(workString);
										}
										if (iot.getOpC().equals("+") && !crudMark.contains("C")) {
											crudMark = crudMark + "C";
										}
										if (iot.getOpR().equals("+") && !crudMark.contains("R")) {
											crudMark = crudMark + "R";
										}
										if (iot.getOpU().equals("+") && !crudMark.contains("U")) {
											crudMark = crudMark + "U";
										}
										if (iot.getOpD().equals("+") && !crudMark.contains("D")) {
											crudMark = crudMark + "D";
										}
										hashKeyList.put(workString, crudMark);
									}
								}
								break;
							}
						}
					}
				}
			}

			jProgressBar.setValue(jProgressBar.getValue() + 1);
			jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());
		}
		if (sortedList.size() == 0) {
			XSSFCell cell = topRow.createCell(3);
			cell.setCellStyle(styleHeaderNormal);
			cell.setCellValue(new XSSFRichTextString("N/A"));
		}
	}

	void setupTableColumnsForFunction(XSSFSheet sheet, XSSFRow topRow) {
		Table table;
		String workString = "";
		java.util.List<Table> sortedList = new ArrayList<>();

		hashKeyList.clear();
		if (tableList != null) {
			for (Table t : tableList) {
				sortedList.add(t);
			}
			Collections.sort(sortedList, (t1, t2) -> t1.getSortKey().compareTo(t2.getSortKey()));
		}
		
		for (int i = 0; i < sortedList.size(); i++) {
			table = sortedList.get(i);
			XSSFCell cell = topRow.createCell(i+4);
			cell.setCellStyle(styleHeaderRotated);
			workString = table.getSortKey() + " " + table.getName();
			cell.setCellValue(new XSSFRichTextString(workString));
			sheet.setColumnWidth(i+4, 1400);

			jProgressBar.setValue(jProgressBar.getValue() + 1);
			jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());
		}
		if (sortedList.size() == 0) {
			XSSFCell cell = topRow.createCell(3);
			cell.setCellStyle(styleHeaderNormal);
			cell.setCellValue(new XSSFRichTextString("N/A"));
		}
	}

	void setupTaskRowsForSubjectAreas(XSSFSheet sheet) {
		SubjectArea subjectArea;
		TaskType taskType;
		Role role;
		Task task;
		DataflowNode dataflowNode;
		String workString = "";
		boolean hasThisTask;
		java.util.List<TaskType> sortedTaskTypeList = new ArrayList<>();
		java.util.List<Role> sortedRoleList = new ArrayList<>();
		java.util.List<Task> sortedTaskList = new ArrayList<>();
		java.util.List<SubjectArea> sortedSubjectAreaList = new ArrayList<>();

		if (taskTypeList != null) sortedTaskTypeList.addAll(taskTypeList);
		if (roleList != null) sortedRoleList.addAll(roleList);
		if (taskList != null) sortedTaskList.addAll(taskList);
		if (subjectAreaList != null) sortedSubjectAreaList.addAll(subjectAreaList);
		
		Collections.sort(sortedTaskTypeList, (t1, t2) -> t1.getSortKey().compareTo(t2.getSortKey()));
		Collections.sort(sortedRoleList, (r1, r2) -> r1.getSortKey().compareTo(r2.getSortKey()));
		Collections.sort(sortedTaskList, (t1, t2) -> t1.getSortKey().compareTo(t2.getSortKey()));
		Collections.sort(sortedSubjectAreaList, (s1, s2) -> s1.getSortKey().compareTo(s2.getSortKey()));
		
		if (sortedTaskTypeList.size() > 1) {
			for (TaskType tt : sortedTaskTypeList) {
				for (Role r : sortedRoleList) {
					for (Task t : sortedTaskList) {
						if (t.getRoleId().equals(r.getId()) && t.getTaskTypeId().equals(tt.getId())) {

							currentRowNumber++;
							XSSFRow row = sheet.createRow(currentRowNumber);
							XSSFCell cellSequence = row.createCell(0);
							cellSequence.setCellStyle(styleValueNumber);
							cellSequence.setCellValue(currentRowNumber);

							XSSFCell cellTaskType = row.createCell(1);
							cellTaskType.setCellStyle(styleValue);
							workString = tt.getSortKey() + " " + tt.getName();
							cellTaskType.setCellValue(new XSSFRichTextString(workString));

							XSSFCell cellRole = row.createCell(2);
							cellRole.setCellStyle(styleValue);
							workString = r.getSortKey() + " " + r.getName();
							cellRole.setCellValue(new XSSFRichTextString(workString));

							XSSFCell cellTask = row.createCell(3);
							cellTask.setCellStyle(styleValue);
							workString = t.getSortKey() + " " + t.getName();
							cellTask.setCellValue(new XSSFRichTextString(workString));

							for (SubjectArea sa : sortedSubjectAreaList) {
								hasThisTask = false;
								java.util.List<DataflowNode> dataflowNodes = sa.getDataflowNodes();
								if (dataflowNodes != null) {
									for (DataflowNode dfn : dataflowNodes) {
										if (dfn.getTaskID() != null && dfn.getTaskID().equals(t.getId())) {
											hasThisTask = true;
											break;
										}
									}
								}
								XSSFCell cellMatrix = row.createCell(sortedSubjectAreaList.indexOf(sa)+4);
								cellMatrix.setCellStyle(styleCheck);
								if (hasThisTask) {
									cellMatrix.setCellValue(new XSSFRichTextString(res.getString("S80")));
								}
							}
							jProgressBar.setValue(jProgressBar.getValue() + 1);
							jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());
						}
					}
				}
			}
		} else {
			for (Role r : sortedRoleList) {
				for (Task t : sortedTaskList) {
					if (t.getRoleId().equals(r.getId())) {
						currentRowNumber++;
						XSSFRow row = sheet.createRow(currentRowNumber);
						XSSFCell cellSequence = row.createCell(0);
						cellSequence.setCellStyle(styleValueNumber);
						cellSequence.setCellValue(currentRowNumber);

						XSSFCell cellTaskType = row.createCell(1);
						cellTaskType.setCellStyle(styleValue);
						cellTaskType.setCellValue(new XSSFRichTextString("N/A"));

						XSSFCell cellRole = row.createCell(2);
						cellRole.setCellStyle(styleValue);
						workString = r.getSortKey() + " / " + r.getName();
						cellRole.setCellValue(new XSSFRichTextString(workString));

						XSSFCell cellTask = row.createCell(3);
						cellTask.setCellStyle(styleValue);
						workString = t.getSortKey() + " / " + t.getName();
						cellTask.setCellValue(new XSSFRichTextString(workString));

						for (int m = 0; m < sortedSubjectAreaList.size(); m++) {
							subjectArea = sortedSubjectAreaList.get(m);
							hasThisTask = false;
							java.util.List<DataflowNode> dataflowNodes = subjectArea.getDataflowNodes();
							if (dataflowNodes != null) {
								for (DataflowNode dfn : dataflowNodes) {
									if (dfn.getTaskID() != null && dfn.getTaskID().equals(t.getId())) {
										hasThisTask = true;
									}
								}
							}
							XSSFCell cellMatrix = row.createCell(m+4);
							cellMatrix.setCellStyle(styleCheck);
							if (hasThisTask) {
								cellMatrix.setCellValue(new XSSFRichTextString(res.getString("S80")));
							}
						}
						jProgressBar.setValue(jProgressBar.getValue() + 1);
						jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());
					}
				}
			}
		}
	}

	void setupFunctionRowsForSubjectAreas(XSSFSheet sheet) {
		SubjectArea subjectArea;
		Subsystem subsystem;
		Function function;
		FunctionType functionType;
		String workString = "";
		java.util.List<Subsystem> sortedSubsystemList = new ArrayList<>();
		java.util.List<Function> sortedFunctionList = new ArrayList<>();
		java.util.List<SubjectArea> sortedSubjectAreaList = new ArrayList<>();

		if (subsystemList != null) sortedSubsystemList.addAll(subsystemList);
		if (functionList != null) sortedFunctionList.addAll(functionList);
		if (subjectAreaList != null) sortedSubjectAreaList.addAll(subjectAreaList);
		
		Collections.sort(sortedSubsystemList, (s1, s2) -> s1.getSortKey().compareTo(s2.getSortKey()));
		Collections.sort(sortedFunctionList, (f1, f2) -> f1.getSortKey().compareTo(f2.getSortKey()));
		Collections.sort(sortedSubjectAreaList, (s1, s2) -> s1.getSortKey().compareTo(s2.getSortKey()));
		
		for (Subsystem ss : sortedSubsystemList) {
			for (Function f : sortedFunctionList) {
				if (f.getSubsystemId().equals(ss.getId())) {
					
					currentRowNumber++;
					XSSFRow row = sheet.createRow(currentRowNumber);
					XSSFCell cellSequence = row.createCell(0);
					cellSequence.setCellStyle(styleValueNumber);
					cellSequence.setCellValue(currentRowNumber);

					XSSFCell cellTaskType = row.createCell(1);
					cellTaskType.setCellStyle(styleValue);
					workString = ss.getSortKey() + " " + ss.getName();
					cellTaskType.setCellValue(new XSSFRichTextString(workString));

					XSSFCell cellRole = row.createCell(2);
					cellRole.setCellStyle(styleValue);
					workString = f.getSortKey() + " " + f.getName();
					cellRole.setCellValue(new XSSFRichTextString(workString));

					XSSFCell cellTask = row.createCell(3);
					cellTask.setCellStyle(styleValue);
					if (functionTypeList != null) {
						for (FunctionType ft : functionTypeList) {
							if (f.getFunctionTypeId().equals(ft.getId())) {
								workString = ft.getSortKey() + " " + ft.getName();
								break;
							}
						}
					}
					cellTask.setCellValue(new XSSFRichTextString(workString));

					for (int m = 0; m < sortedSubjectAreaList.size(); m++) {
						subjectArea = sortedSubjectAreaList.get(m);
						XSSFCell cellMatrix = row.createCell(m+4);
						cellMatrix.setCellStyle(styleCheck);
						workString = subjectArea.getId() + "," + f.getId();
						if (keyList.contains(workString)) {
							cellMatrix.setCellValue(new XSSFRichTextString(res.getString("S80")));
						}
					}

					jProgressBar.setValue(jProgressBar.getValue() + 1);
					jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());
				}
			}
		}
	}

	void setupFunctionRowsForTasks(XSSFSheet sheet) {
		Subsystem subsystem;
		Function function;
		FunctionType functionType;
		String workString = "";
		java.util.List<Subsystem> sortedSubsystemList = new ArrayList<>();
		java.util.List<Function> sortedFunctionList = new ArrayList<>();

		if (subsystemList != null) sortedSubsystemList.addAll(subsystemList);
		if (functionList != null) sortedFunctionList.addAll(functionList);
		
		Collections.sort(sortedSubsystemList, (s1, s2) -> s1.getSortKey().compareTo(s2.getSortKey()));
		Collections.sort(sortedFunctionList, (f1, f2) -> f1.getSortKey().compareTo(f2.getSortKey()));

		for (Subsystem ss : sortedSubsystemList) {
			for (Function f : sortedFunctionList) {
				if (f.getSubsystemId().equals(ss.getId())) {
					
					currentRowNumber++;
					XSSFRow row = sheet.createRow(currentRowNumber);
					XSSFCell cellSequence = row.createCell(0);
					cellSequence.setCellStyle(styleValueNumber);
					cellSequence.setCellValue(currentRowNumber);

					XSSFCell cellTaskType = row.createCell(1);
					cellTaskType.setCellStyle(styleValue);
					workString = ss.getSortKey() + " " + ss.getName();
					cellTaskType.setCellValue(new XSSFRichTextString(workString));

					XSSFCell cellRole = row.createCell(2);
					cellRole.setCellStyle(styleValue);
					workString = f.getSortKey() + " " + f.getName();
					cellRole.setCellValue(new XSSFRichTextString(workString));

					XSSFCell cellTask = row.createCell(3);
					cellTask.setCellStyle(styleValue);
					if (functionTypeList != null) {
						for (FunctionType ft : functionTypeList) {
							if (f.getFunctionTypeId().equals(ft.getId())) {
								workString = ft.getSortKey() + " " + ft.getName();
								break;
							}
						}
					}
					cellTask.setCellValue(new XSSFRichTextString(workString));

					for (int m = 0; m < keyList.size(); m++) {
						XSSFCell cellMatrix = row.createCell(m+4);
						cellMatrix.setCellStyle(styleCheck);
						workString = keyList.get(m);
						if (workString.startsWith(f.getId() + ",")) {
							cellMatrix.setCellValue(new XSSFRichTextString(res.getString("S80")));
						}
					}

					jProgressBar.setValue(jProgressBar.getValue() + 1);
					jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());
				}
			}
		}
	}

	void setupTableRowsForTask(XSSFSheet sheet) {
		Subsystem subsystem;
		Table table;
		TableType tableType;
		String workString = "";
		String crudMark = "";
		String crudCheck = "";
		java.util.List<Subsystem> sortedSubsystemList = new ArrayList<>();
		java.util.List<Table> sortedTableList = new ArrayList<>();

		if (subsystemList != null) sortedSubsystemList.addAll(subsystemList);
		if (tableList != null) sortedTableList.addAll(tableList);
		
		Collections.sort(sortedSubsystemList, (s1, s2) -> s1.getSortKey().compareTo(s2.getSortKey()));
		Collections.sort(sortedTableList, (t1, t2) -> t1.getSortKey().compareTo(t2.getSortKey()));

		for (Subsystem ss : sortedSubsystemList) {
			for (Table t : sortedTableList) {
				if (t.getSubsystemId().equals(ss.getId())) {
					
					currentRowNumber++;
					XSSFRow row = sheet.createRow(currentRowNumber);
					XSSFCell cellSequence = row.createCell(0);
					cellSequence.setCellStyle(styleValueNumber);
					cellSequence.setCellValue(currentRowNumber);

					XSSFCell cellTaskType = row.createCell(1);
					cellTaskType.setCellStyle(styleValue);
					workString = ss.getSortKey() + " " + ss.getName();
					cellTaskType.setCellValue(new XSSFRichTextString(workString));

					XSSFCell cellRole = row.createCell(2);
					cellRole.setCellStyle(styleValue);
					workString = t.getSortKey() + " " + t.getName();
					cellRole.setCellValue(new XSSFRichTextString(workString));

					XSSFCell cellTask = row.createCell(3);
					cellTask.setCellStyle(styleValue);
					if (tableTypeList != null) {
						for (TableType tt : tableTypeList) {
							if (t.getTableTypeId().equals(tt.getId())) {
								workString = tt.getSortKey() + " " + tt.getName();
								break;
							}
						}
					}
					cellTask.setCellValue(new XSSFRichTextString(workString));

					for (int m = 0; m < hashKeyList.size(); m++) {
						XSSFCell cellMatrix = row.createCell(m+4);
						cellMatrix.setCellStyle(styleCheck);
						workString = t.getId();
						if (hashKeyList.containsKey(workString + "," + t.getId())) {
							crudMark = hashKeyList.get(workString + "," + t.getId());
							crudCheck = "";
							if (crudMark.contains("C")) crudCheck = crudCheck + "C";
							if (crudMark.contains("R")) crudCheck = crudCheck + "R";
							if (crudMark.contains("U")) crudCheck = crudCheck + "U";
							if (crudMark.contains("D")) crudCheck = crudCheck + "D";
							cellMatrix.setCellValue(new XSSFRichTextString(crudCheck));
						}
					}

					jProgressBar.setValue(jProgressBar.getValue() + 1);
					jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());
				}
			}
		}
	}

	void setupFunctionRowsForTable(XSSFSheet sheet) {
		Subsystem subsystem;
		Function function;
		FunctionType functionType;
		IOTable ioTable;
		String workString = "";
		String crudMark = "";
		String crudCheck = "";
		java.util.List<Subsystem> sortedSubsystemList = new ArrayList<>();
		java.util.List<Function> sortedFunctionList = new ArrayList<>();
		java.util.List<Table> sortedTableList = new ArrayList<>();

		if (subsystemList != null) sortedSubsystemList.addAll(subsystemList);
		if (functionList != null) sortedFunctionList.addAll(functionList);
		if (tableList != null) sortedTableList.addAll(tableList);
		
		Collections.sort(sortedSubsystemList, (s1, s2) -> s1.getSortKey().compareTo(s2.getSortKey()));
		Collections.sort(sortedFunctionList, (f1, f2) -> f1.getSortKey().compareTo(f2.getSortKey()));
		Collections.sort(sortedTableList, (t1, t2) -> t1.getSortKey().compareTo(t2.getSortKey()));
		
		for (Subsystem ss : sortedSubsystemList) {
			for (Function f : sortedFunctionList) {
				if (f.getSubsystemId().equals(ss.getId())) {
					
					currentRowNumber++;
					XSSFRow row = sheet.createRow(currentRowNumber);
					XSSFCell cellSequence = row.createCell(0);
					cellSequence.setCellStyle(styleValueNumber);
					cellSequence.setCellValue(currentRowNumber);

					XSSFCell cellTaskType = row.createCell(1);
					cellTaskType.setCellStyle(styleValue);
					workString = ss.getSortKey() + " " + ss.getName();
					cellTaskType.setCellValue(new XSSFRichTextString(workString));

					XSSFCell cellRole = row.createCell(2);
					cellRole.setCellStyle(styleValue);
					workString = f.getSortKey() + " " + f.getName();
					cellRole.setCellValue(new XSSFRichTextString(workString));

					XSSFCell cellTask = row.createCell(3);
					cellTask.setCellStyle(styleValue);
					if (functionTypeList != null) {
						for (FunctionType ft : functionTypeList) {
							if (f.getFunctionTypeId().equals(ft.getId())) {
								workString = ft.getSortKey() + " " + ft.getName();
								break;
							}
						}
					}
					cellTask.setCellValue(new XSSFRichTextString(workString));

					java.util.List<IOTable> ioTables = f.getIOTables();
					for (int m = 0; m < sortedTableList.size(); m++) {
						Table table = sortedTableList.get(m);
						XSSFCell cellMatrix = row.createCell(m+4);
						cellMatrix.setCellStyle(styleCheck);
						crudMark = "";
						if (ioTables != null) {
							for (IOTable iot : ioTables) {
								if (iot.getTableId().equals(table.getId())) {
									if (iot.getOpC().equals("+") && !crudMark.contains("C")) crudMark = crudMark + "C";
									if (iot.getOpR().equals("+") && !crudMark.contains("R")) crudMark = crudMark + "R";
									if (iot.getOpU().equals("+") && !crudMark.contains("U")) crudMark = crudMark + "U";
									if (iot.getOpD().equals("+") && !crudMark.contains("D")) crudMark = crudMark + "D";
								}
							}
						}
						crudCheck = "";
						if (crudMark.contains("C")) crudCheck = crudCheck + "C";
						if (crudMark.contains("R")) crudCheck = crudCheck + "R";
						if (crudMark.contains("U")) crudCheck = crudCheck + "U";
						if (crudMark.contains("D")) crudCheck = crudCheck + "D";
						cellMatrix.setCellValue(new XSSFRichTextString(crudCheck));
					}

					jProgressBar.setValue(jProgressBar.getValue() + 1);
					jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());
				}
			}
		}
	}

	void createWorkBookAndStyles() {
		workBook = new XSSFWorkbook();
		fontTitle = workBook.createFont();
		fontTitle.setFontName(res.getString("DialogDocuments13"));
		fontTitle.setFontHeightInPoints((short)14);
		fontTitle.setColor(new XSSFColor(new byte[]{(byte)255, (byte)255, (byte)255}));
		fontTitle.setBold(true);
		fontHeader1 = workBook.createFont();
		fontHeader1.setFontName(res.getString("DialogDocuments13"));
		fontHeader1.setFontHeightInPoints((short)10);
		fontHeader1.setItalic(true);
		fontHeader1.setColor(new XSSFColor(new byte[]{(byte)255, (byte)255, (byte)255}));
		fontHeader2 = workBook.createFont();
		fontHeader2.setFontName(res.getString("DialogDocuments13"));
		fontHeader2.setFontHeightInPoints((short)10);
		fontValue = workBook.createFont();
		fontValue.setFontName(res.getString("DialogDocuments14"));
		fontValue.setFontHeightInPoints((short)10);
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
		styleHeaderRotated = workBook.createCellStyle();
		styleHeaderRotated.setBorderBottom(BorderStyle.THIN);
		styleHeaderRotated.setBorderLeft(BorderStyle.THIN);
		styleHeaderRotated.setBorderRight(BorderStyle.THIN);
		styleHeaderRotated.setBorderTop(BorderStyle.THIN);
		styleHeaderRotated.setAlignment(HorizontalAlignment.LEFT);
		styleHeaderRotated.setVerticalAlignment(VerticalAlignment.TOP);
		styleHeaderRotated.setAlignment(HorizontalAlignment.CENTER);
		styleHeaderRotated.setRotation((short)180);
		styleHeaderRotated.setFont(fontValue);
		styleHeaderNormal = workBook.createCellStyle();
		styleHeaderNormal.setBorderBottom(BorderStyle.THIN);
		styleHeaderNormal.setBorderLeft(BorderStyle.THIN);
		styleHeaderNormal.setBorderRight(BorderStyle.THIN);
		styleHeaderNormal.setBorderTop(BorderStyle.THIN);
		styleHeaderNormal.setAlignment(HorizontalAlignment.LEFT);
		styleHeaderNormal.setVerticalAlignment(VerticalAlignment.CENTER);
		styleHeaderNormal.setFont(fontValue);
		styleHeaderNumber = workBook.createCellStyle();
		styleHeaderNumber.setBorderBottom(BorderStyle.THIN);
		styleHeaderNumber.setBorderLeft(BorderStyle.THIN);
		styleHeaderNumber.setBorderRight(BorderStyle.THIN);
		styleHeaderNumber.setBorderTop(BorderStyle.THIN);
		styleHeaderNumber.setAlignment(HorizontalAlignment.RIGHT);
		styleHeaderNumber.setVerticalAlignment(VerticalAlignment.CENTER);
		styleHeaderNumber.setFont(fontValue);
		styleValue = workBook.createCellStyle();
		styleValue.setBorderBottom(BorderStyle.THIN);
		styleValue.setBorderLeft(BorderStyle.THIN);
		styleValue.setBorderRight(BorderStyle.THIN);
		styleValue.setBorderTop(BorderStyle.THIN);
		styleValue.setAlignment(HorizontalAlignment.LEFT);
		styleValue.setVerticalAlignment(VerticalAlignment.TOP);
		styleValue.setFont(fontValue);
		styleValue.setWrapText(true);
		styleCheck = workBook.createCellStyle();
		styleCheck.setBorderBottom(BorderStyle.THIN);
		styleCheck.setBorderLeft(BorderStyle.THIN);
		styleCheck.setBorderRight(BorderStyle.THIN);
		styleCheck.setBorderTop(BorderStyle.THIN);
		styleCheck.setAlignment(HorizontalAlignment.CENTER);
		styleCheck.setVerticalAlignment(VerticalAlignment.CENTER);
		styleCheck.setFont(fontValue);
		styleValueNumber = workBook.createCellStyle();
		styleValueNumber.setBorderBottom(BorderStyle.THIN);
		styleValueNumber.setBorderLeft(BorderStyle.THIN);
		styleValueNumber.setBorderRight(BorderStyle.THIN);
		styleValueNumber.setBorderTop(BorderStyle.THIN);
		styleValueNumber.setAlignment(HorizontalAlignment.RIGHT);
		styleValueNumber.setVerticalAlignment(VerticalAlignment.TOP);
		styleValueNumber.setFont(fontValue);
	}

	void jButtonCloseDialog_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}

	void jCheckBox_stateChanged(ChangeEvent e) {
		jButtonStart.setEnabled(false);
		if (jCheckBoxSubjectAreaAndTask.isSelected()
				|| jCheckBoxSubjectAreaAndFunction.isSelected()
				|| jCheckBoxTaskAndFunction.isSelected()
				|| jCheckBoxTaskAndTable.isSelected()
				|| jCheckBoxTableAndFunction.isSelected()) {
			jButtonStart.setEnabled(true);
			this.getRootPane().setDefaultButton(jButtonStart);
		}
	}

	String getStringValueOfDateAndTime() {
		String returnValue = "";
		GregorianCalendar calendar = new GregorianCalendar();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		String monthStr = (month < 10) ? "0" + Integer.toString(month) : Integer.toString(month);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		String dayStr = (day < 10) ? "0" + Integer.toString(day) : Integer.toString(day);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		String hourStr = (hour < 10) ? "0" + Integer.toString(hour) : Integer.toString(hour);
		int minute = calendar.get(Calendar.MINUTE);
		String minStr = (minute < 10) ? "0" + Integer.toString(minute) : Integer.toString(minute);
		int second = calendar.get(Calendar.SECOND);
		String secStr = (second < 10) ? "0" + Integer.toString(second) : Integer.toString(second);
		returnValue = Integer.toString(year) + monthStr + dayStr + hourStr + minStr + secStr;
		return returnValue;
	}

}

class DialogMatrixList_jCheckBox_changeAdapter  implements ChangeListener {
	DialogMatrixList adaptee;
	DialogMatrixList_jCheckBox_changeAdapter(DialogMatrixList adaptee) {
		this.adaptee = adaptee;
	}
	public void stateChanged(ChangeEvent e) {
		adaptee.jCheckBox_stateChanged(e);
	}
}

class DialogMatrixList_jButtonStart_actionAdapter implements java.awt.event.ActionListener {
	DialogMatrixList adaptee;

	DialogMatrixList_jButtonStart_actionAdapter(DialogMatrixList adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.jButtonStart_actionPerformed(e);
	}
}

class DialogMatrixList_jButtonCloseDialog_actionAdapter implements java.awt.event.ActionListener {
	DialogMatrixList adaptee;

	DialogMatrixList_jButtonCloseDialog_actionAdapter(DialogMatrixList adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.jButtonCloseDialog_actionPerformed(e);
	}
}
