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
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import org.apache.xerces.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;

import xeadModeler.Modeler;
import xeadModeler.view.DialogAbout;
import xeadModeler.model.json.*;

public class DialogImportXEAF extends JDialog {
	private static final long serialVersionUID = 1L;
	private static ResourceBundle res = ResourceBundle.getBundle("xeadModeler.Res");

	private JPanel panelMain = new JPanel();
	private JLabel jLabel1 = new JLabel();
	private JLabel jLabel2 = new JLabel();
	private JProgressBar jProgressBar = new JProgressBar();
	private JButton jButtonStart = new JButton();
	private JButton jButtonCancel = new JButton();
	private SortableSubsystemComboBoxModel comboBoxModelBlockFrom = new SortableSubsystemComboBoxModel();
	private SortableSubsystemComboBoxModel comboBoxModelBlockInto = new SortableSubsystemComboBoxModel();
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
	private XeadSystem xeadSystemFrom;
	private org.w3c.dom.Element blockElementFrom;
	private Subsystem blockSubsystemInto;
	private String blockIDFrom, blockIDInto;
	private FileWriter fileWriter = null;
	private BufferedWriter bufferedWriter = null;

	private int createTableCounter, cancelTableCounter;
	private int createFunctionCounter, cancelFunctionCounter;
	private int missingTableCounter, missingFunctionCounter;
	private String defaultTableTypeID = "";
	private HashMap<String, String> functionTypeMap = new HashMap<String, String>();
	
	private static String[] typeIDArray = {"XF000","XF100","XF110","XF200","XF290","XF300","XF310","XF390"};
	private static String[] typeNameArray = {res.getString("XF000Name"),res.getString("XF100Name"),res.getString("XF110Name"),res.getString("XF200Name"),
			res.getString("XF290Name"),res.getString("XF300Name"),res.getString("XF310Name"),res.getString("XF390Name")};
	private static String[] typeDescArray = {res.getString("XF000Desc"),res.getString("XF100Desc"),res.getString("XF110Desc"),res.getString("XF200Desc"),
			res.getString("XF290Desc"),res.getString("XF300Desc"),res.getString("XF310Desc"),res.getString("XF390Desc")};


	public DialogImportXEAF(Modeler frame, String title, boolean modal) {
		super(frame, title, modal);
		try {
			frame_ = frame;
			jbInit();
			pack();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public DialogImportXEAF(Modeler frame) {
		this(frame, "", true);
	}

	private void jbInit() throws Exception {
		this.setResizable(false);
		this.setTitle(res.getString("DialogImportXEAF01"));

		jLabel4.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jLabel4.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel4.setText(res.getString("DialogImportXEAF02"));
		jLabel4.setBounds(new Rectangle(5, 12, 170, 20));
		jTextFieldImportFileName.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jTextFieldImportFileName.setBounds(new Rectangle(180, 9, 350, 25));
		jTextFieldImportFileName.setEditable(false);

		jLabel5.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jLabel5.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel5.setText(res.getString("DialogImportXEAF03"));
		jLabel5.setBounds(new Rectangle(5, 43, 170, 20));
		jTextFieldImportSystemName.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jTextFieldImportSystemName.setBounds(new Rectangle(180, 40, 350, 25));
		jTextFieldImportSystemName.setEditable(false);

		jLabel1.setText(res.getString("DialogImportXEAF09"));
		jLabel1.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel1.setBounds(new Rectangle(5, 74, 170, 20));
		jComboBoxBlockFrom.setBounds(new Rectangle(180, 71, 350, 25));
		jComboBoxBlockFrom.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jComboBoxBlockFrom.addActionListener(new DialogImportXEAF_jComboBoxBlockFrom_actionAdapter(this));

		jLabel2.setText(res.getString("DialogImportXEAF10"));
		jLabel2.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel2.setBounds(new Rectangle(5, 105, 170, 20));
		jComboBoxBlockInto.setBounds(new Rectangle(180, 102, 350, 25));
		jComboBoxBlockInto.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jComboBoxBlockInto.addActionListener(new DialogImportXEAF_jComboBoxBlockInto_actionAdapter(this));

		jTextArea1.setText(res.getString("DialogImportXEAF11"));
		jTextArea1.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jTextArea1.setForeground(Color.BLUE);
		jTextArea1.setEditable(false);
		jTextArea1.setBounds(new Rectangle(9, 133, 522, 120));
		jTextArea1.setLineWrap(true);
		jTextArea1.setBackground(SystemColor.control);
		jTextArea1.setBorder(BorderFactory.createLoweredBevelBorder());

		jProgressBar.setBounds(new Rectangle(30, 264, 340, 27));
		jProgressBar.setStringPainted(true);
		jProgressBar.setVisible(false);

		jButtonStart.setBounds(new Rectangle(30, 264, 340, 27));
		jButtonStart.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jButtonStart.setText(res.getString("DialogImportXEAF07"));
		jButtonStart.addActionListener(new DialogImportXEAF_jButtonStart_actionAdapter(this));
		jButtonStart.setEnabled(false);

		jButtonCancel.setBounds(new Rectangle(400, 264, 110, 27));
		jButtonCancel.setFont(new java.awt.Font(frame_.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
		jButtonCancel.setText(res.getString("DialogImportXEAF08"));
		jButtonCancel.addActionListener(new DialogImportXEAF_jButtonCancel_actionAdapter(this));

		panelMain.setLayout(null);
		panelMain.setPreferredSize(new Dimension(540, 303));
		panelMain.setBorder(BorderFactory.createEtchedBorder());
		panelMain.add(jLabel1, null);
		panelMain.add(jLabel2, null);
		panelMain.add(jLabel4, null);
		panelMain.add(jLabel5, null);
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

			///////////////////////
			//Setup ComboBox1 & 2//
			///////////////////////
 		comboBoxModelBlockFrom.removeAllElements();
			elementList = domDocumentFrom.getElementsByTagName("Subsystem");
			for (int i = 0; i < elementList.getLength(); i++) {
				comboBoxModelBlockFrom.addElement(new XeadNodeFromElement((org.w3c.dom.Element)elementList.item(i)));
			}
			comboBoxModelBlockFrom.sortElements();
			comboBoxModelBlockFrom.insertElementAt(res.getString("DialogImportXEAF12"), 0);
			comboBoxModelBlockFrom.setSelectedItem(comboBoxModelBlockFrom.getElementAt(0));
			comboBoxModelBlockInto.removeAllElements();
			XeadSystem xeadSystem = frame_.getXeadDocumentModel().getXeadSystem();
			java.util.List<Subsystem> subsystems = xeadSystem.getSubsystems();
			if (subsystems != null) {
				for (Subsystem subsystem : subsystems) {
					comboBoxModelBlockInto.addElement(new XeadNodeFromSubsystem(subsystem));
				}
			}
			comboBoxModelBlockInto.sortElements();
			comboBoxModelBlockInto.insertElementAt(res.getString("DialogImportXEAF12"), 0);
			comboBoxModelBlockInto.setSelectedItem(comboBoxModelBlockInto.getElementAt(0));

			jProgressBar.setValue(0);
			super.setVisible(true);
		}

		return importResult;
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
		}
	}
	
	void jComboBoxBlockInto_actionPerformed(ActionEvent e) {
		if (jComboBoxBlockFrom.getSelectedIndex() > 0 && jComboBoxBlockInto.getSelectedIndex() > 0) {
			jButtonStart.setEnabled(true);
		} else {
			jButtonStart.setEnabled(false);
		}
	}

	void jButtonStart_actionPerformed(ActionEvent e) {
		String logFileName = "";

		try {
			setCursor(new Cursor(Cursor.WAIT_CURSOR));
			jProgressBar.setVisible(true);
			jButtonStart.setVisible(false);

			String dateTime = getStringValueOfDateAndTime();
			File file = new File(frame_.currentFileName);
			logFileName = file.getParent() + File.separator + "XeadImportLog" + dateTime + ".txt";
			fileWriter = new FileWriter(logFileName);
			bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(res.getString("DialogImportXEAF01") + "(" + dateTime + ")" + "\n");
			bufferedWriter.write("\n");
			bufferedWriter.write(res.getString("DialogImportXEAF02") + ":" + jTextFieldImportFileName.getText() + "\n");
			bufferedWriter.write(res.getString("DialogImportXEAF31") + ":" + frame_.currentFileName + res.getString("DialogImportXEAF41") + "\n");
			bufferedWriter.write("\n");
			bufferedWriter.write(res.getString("DialogImportXEAF03") + ":" + jTextFieldImportSystemName.getText() + "\n");
			bufferedWriter.write(res.getString("DialogImportXEAF32") + ":" + frame_.systemName + "\n");
			bufferedWriter.write("\n");

			createTableCounter = 0;
			cancelTableCounter = 0;
			createFunctionCounter = 0;
			cancelFunctionCounter = 0;
			missingTableCounter = 0;
			missingFunctionCounter = 0;

			XeadNodeFromElement fromNode = (XeadNodeFromElement)comboBoxModelBlockFrom.getSelectedItem();
			blockElementFrom = fromNode.getElement();
			blockIDFrom = blockElementFrom.getAttribute("ID");
			bufferedWriter.write(jLabel1.getText() + ":" + blockElementFrom.getAttribute("Name") + "(" + blockElementFrom.getAttribute("ID") + ")" + "\n");

			XeadNodeFromSubsystem intoNode = (XeadNodeFromSubsystem)comboBoxModelBlockInto.getSelectedItem();
			blockSubsystemInto = intoNode.getSubsystem();
			blockIDInto = blockSubsystemInto.getId();
			bufferedWriter.write(jLabel2.getText() + ":" + blockSubsystemInto.getName() + "(" + blockSubsystemInto.getSortKey() + ")" + "\n");
			
			importSubsystem();
			importTables();
			importFunctions();

			importResult = res.getString("DialogImportXEAF16") +
					createTableCounter + res.getString("DialogImportXEAF18") +
					cancelTableCounter + res.getString("DialogImportXEAF50") + "\n" +
					res.getString("DialogImportXEAF19") +
					createFunctionCounter + res.getString("DialogImportXEAF18") +
					cancelFunctionCounter + res.getString("DialogImportXEAF50") + "\n" +
					res.getString("DialogImportXEAF20") +
					missingTableCounter + res.getString("DialogImportXEAF21") + "\n" +
					res.getString("DialogImportXEAF22") +
					missingFunctionCounter + res.getString("DialogImportXEAF23") + "\n" +
					res.getString("DialogImportXEAF29") + logFileName + res.getString("DialogImportXEAF30") + "\n" + "\n";
		} catch (Exception ex1) {
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
		NodeList workElementList;
		ArrayList<org.w3c.dom.Element> tableElementListFrom = new ArrayList<org.w3c.dom.Element>(); 
		boolean isAlreadyExisting;
		String idOfDefinition;
		XeadSystem xeadSystem = frame_.getXeadDocumentModel().getXeadSystem();

		////////////////////////////
		//Setup Default Table Type//
		////////////////////////////
		java.util.List<TableType> tableTypes = xeadSystem.getTableTypes();
		if (tableTypes != null && !tableTypes.isEmpty()) {
			defaultTableTypeID = tableTypes.get(0).getId();
		}

		///////////////////////////////////////
		//Setup List of Tables to be imported//
		///////////////////////////////////////
		workElementList = domDocumentFrom.getElementsByTagName("Table");
		for (int i = 0; i < workElementList.getLength(); i++) {
			workElement = (org.w3c.dom.Element)workElementList.item(i);
			if (workElement.getAttribute("SubsystemID").equals(blockIDFrom)) {
				tableElementListFrom.add(workElement);
			}
		}

		jProgressBar.setValue(0);
		jProgressBar.setMaximum(tableElementListFrom.size());

		try {
			bufferedWriter.write("\n");
		} catch (IOException ex) {}

		/////////////////////////////////////////////////
		//Import Table definitions into target document//
		/////////////////////////////////////////////////
		java.util.List<Table> targetTables = xeadSystem.getTables();
		for (int i = 0; i < tableElementListFrom.size(); i++) {
			jProgressBar.setValue(jProgressBar.getValue() + 1);
			jProgressBar.setString("Importing tables(" + (i+1) + "/" + tableElementListFrom.size() + ")");
			jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());

			isAlreadyExisting = false;
			idOfDefinition = tableElementListFrom.get(i).getAttribute("ID");

			if (targetTables != null) {
				for (Table table : targetTables) {
					if (table.getSortKey().equals(idOfDefinition)) {
						isAlreadyExisting = true;
						try {
							bufferedWriter.write(tableElementListFrom.get(i).getAttribute("Name") +
									"(" + tableElementListFrom.get(i).getAttribute("ID") +
									"):" + res.getString("DialogImportXEAF48") + "\n");
						} catch (IOException ex1) {}
						cancelTableCounter++;
						break;
					}
				}
			}

			if (!isAlreadyExisting) {
				String tableID = createTableDefinition(tableElementListFrom.get(i), blockIDInto);
				createTableCounter++;

				/////////////////////////////////////////
				//Create Subsystem attributes for Table//
				/////////////////////////////////////////
				SubsystemTable subsystemTable = new SubsystemTable();
				subsystemTable.setTableId(tableID);
				int pos = 50 + 10 * i;
				subsystemTable.setBoxPosition(Integer.toString(pos) + "," + Integer.toString(pos));
				subsystemTable.setExtDivLoc("500");
				subsystemTable.setIntDivLoc("0");
				subsystemTable.setShowOnModel("true");
				subsystemTable.setShowInstance("false");
				blockSubsystemInto.getSubsystemTables().add(subsystemTable);

				try {
					bufferedWriter.write(tableElementListFrom.get(i).getAttribute("Name") + "(" +
							tableElementListFrom.get(i).getAttribute("ID") +
							"):" + res.getString("DialogImportXEAF34") + "\n");
				} catch (IOException ex2) {}
			}
		}

		try {
			bufferedWriter.write("\n");
		} catch (IOException ex3) {}
	}

	void importFunctions() {
		org.w3c.dom.Element workElement, fromFunctionElement;
		XeadSystem xeadSystem = frame_.getXeadDocumentModel().getXeadSystem();
		ArrayList<org.w3c.dom.Element> functionElementListFrom = new ArrayList<org.w3c.dom.Element>(); 
		boolean isAlreadyExisting;
		String idOfDefinition;

		///////////////////////////
		//Setup Function Type Map//
		///////////////////////////
		setupFunctionTypeMap();

		//////////////////////////////////////////
		//Setup List of Functions to be imported//
		/////////////////////////////////////////
		NodeList functionElementListInto = domDocumentFrom.getElementsByTagName("Function");
		for (int i = 0; i < functionElementListInto.getLength(); i++) {
			workElement = (org.w3c.dom.Element)functionElementListInto.item(i);
			if (workElement.getAttribute("SubsystemID").equals(blockIDFrom)) {
				functionElementListFrom.add(workElement);
			}
		}

		jProgressBar.setValue(0);
		jProgressBar.setMaximum(functionElementListFrom.size());
		
		ArrayList<org.w3c.dom.Element> fromFunctionArray = new ArrayList<org.w3c.dom.Element>();
		ArrayList<Function> newFunctionArray = new ArrayList<Function>();

		java.util.List<Function> targetFunctions = xeadSystem.getFunctions();
		ArrayList<Function> targetFunctionArray = new ArrayList<Function>();
		if (targetFunctions != null) {
			for (Function func : targetFunctions) {
				if (func.getSubsystemId().equals(blockIDInto)) {
					targetFunctionArray.add(func);
				}
			}
		}
		
		for (int i = 0; i < functionElementListFrom.size(); i++) {
			jProgressBar.setValue(jProgressBar.getValue() + 1);
			jProgressBar.setString("Importing functions(" + (i+1) + "/" + functionElementListFrom.size() + ")");
			jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());

			isAlreadyExisting = false;
			idOfDefinition = functionElementListFrom.get(i).getAttribute("ID");

			for (int j = 0; j < targetFunctionArray.size(); j++) {
				if (targetFunctionArray.get(j).getSortKey().equals(idOfDefinition)) {
					isAlreadyExisting = true;
					try {
						bufferedWriter.write(functionElementListFrom.get(i).getAttribute("Name") +
								"(" + functionElementListFrom.get(i).getAttribute("ID") +
								"):" + res.getString("DialogImportXEAF48") + "\n");
					} catch (IOException ex1) {}
					cancelFunctionCounter++;
					break;
				}
			}

			if (!isAlreadyExisting) {
				try {
					bufferedWriter.write(functionElementListFrom.get(i).getAttribute("Name") + "(" +
							functionElementListFrom.get(i).getAttribute("ID") + "):" +
							res.getString("DialogImportXEAF34") + "\n");
				} catch (IOException ex2) {}

				Function newFunction = createFunctionDefinition(functionElementListFrom.get(i), blockIDInto);
				fromFunctionArray.add(functionElementListFrom.get(i));
				newFunctionArray.add(newFunction);
				
				createFunctionCounter++;
			}
		}
		try {
			bufferedWriter.write("\n");
		} catch (IOException ex3) {}
		

		////////////////////////////////////////////////////////////
		//Update Function-called-information by Functions imported//
		////////////////////////////////////////////////////////////
		jProgressBar.setValue(0);
		jProgressBar.setMaximum(fromFunctionArray.size());

		for (int i = 0; i < fromFunctionArray.size(); i++) {
			jProgressBar.setValue(jProgressBar.getValue() + 1);
			jProgressBar.setString("Checking functions called...");
			jProgressBar.paintImmediately(0,0,jProgressBar.getWidth(),jProgressBar.getHeight());

			fromFunctionElement = fromFunctionArray.get(i);
			Function newFunction = newFunctionArray.get(i);
			updateFunctionsCalledOfNewFunction(fromFunctionElement, newFunction, targetFunctions);
		}
	}

	void updateFunctionsCalledOfNewFunction(org.w3c.dom.Element elementFrom, Function functionNew, java.util.List<Function> functionListInto) {
		org.w3c.dom.Element workElement;
		String action;
		int pos1, pos2;
		String convertedID;

		if (elementFrom.getAttribute("Type").equals("XF100")) {
			if (!elementFrom.getAttribute("DetailFunction").equals("")) {
				convertedID = convertFunctionID(elementFrom.getAttribute("DetailFunction"), functionListInto);
				if (convertedID.equals("")) {
					missingFunctionCounter++;
					try {
						bufferedWriter.write(elementFrom.getAttribute("DetailFunction")
								+ res.getString("DialogImportXEAF38") + "\n");
					} catch (IOException ex1) {}
				} else {
					FunctionUsedByThis funcUsed = new FunctionUsedByThis();
					funcUsed.setFunctionId(convertedID);
					funcUsed.setLaunchEvent("");
					funcUsed.setSortKey("0");
					functionNew.getFunctionUsedByThisList().add(funcUsed);
				}
			}
		}

		if (elementFrom.getAttribute("Type").equals("XF110")) {
			if (!elementFrom.getAttribute("BatchRecordFunction").equals("")) {
				convertedID = convertFunctionID(elementFrom.getAttribute("BatchRecordFunction"), functionListInto);
				if (convertedID.equals("")) {
					missingFunctionCounter++;
					try {
						bufferedWriter.write(elementFrom.getAttribute("BatchRecordFunction")
								+ res.getString("DialogImportXEAF38") + "\n");
					} catch (IOException ex1) {}
				} else {
					FunctionUsedByThis funcUsed = new FunctionUsedByThis();
					funcUsed.setFunctionId(convertedID);
					funcUsed.setLaunchEvent("");
					funcUsed.setSortKey("0");
					functionNew.getFunctionUsedByThisList().add(funcUsed);
				}
			}
		}

		if (elementFrom.getAttribute("Type").equals("XF200")) {
			if (!elementFrom.getAttribute("FunctionAfterInsert").equals("")) {
				convertedID = convertFunctionID(elementFrom.getAttribute("FunctionAfterInsert"), functionListInto);
				if (convertedID.equals("")) {
					missingFunctionCounter++;
					try {
						bufferedWriter.write(elementFrom.getAttribute("FunctionAfterInsert")
								+ res.getString("DialogImportXEAF38") + "\n");
					} catch (IOException ex1) {}
				} else {
					FunctionUsedByThis funcUsed = new FunctionUsedByThis();
					funcUsed.setFunctionId(convertedID);
					funcUsed.setLaunchEvent("");
					funcUsed.setSortKey("0");
					functionNew.getFunctionUsedByThisList().add(funcUsed);
				}
			}
		}

		if (elementFrom.getAttribute("Type").equals("XF300")) {
			if (!elementFrom.getAttribute("HeaderFunction").equals("")) {
				convertedID = convertFunctionID(elementFrom.getAttribute("HeaderFunction"), functionListInto);
				if (convertedID.equals("")) {
					missingFunctionCounter++;
					try {
						bufferedWriter.write(elementFrom.getAttribute("HeaderFunction")
								+ res.getString("DialogImportXEAF38") + "\n");
					} catch (IOException ex1) {}
				} else {
					FunctionUsedByThis funcUsed = new FunctionUsedByThis();
					funcUsed.setFunctionId(convertedID);
					funcUsed.setLaunchEvent("");
					funcUsed.setSortKey("0");
					functionNew.getFunctionUsedByThisList().add(funcUsed);
				}
			}
		}

		if (elementFrom.getAttribute("Type").equals("XF100")
				|| elementFrom.getAttribute("Type").equals("XF110")
				|| elementFrom.getAttribute("Type").equals("XF200")
				|| elementFrom.getAttribute("Type").equals("XF300")
				|| elementFrom.getAttribute("Type").equals("XF310")) {
			NodeList buttonList = elementFrom.getElementsByTagName("Button");
			for (int i = 0; i < buttonList.getLength(); i++) {
				workElement = (org.w3c.dom.Element)buttonList.item(i);
				action = workElement.getAttribute("Action");
				pos1 = action.indexOf("CALL(");
				pos2 = action.indexOf(")");
				if (pos1 >= 0 && pos2 > pos1) {
					convertedID = convertFunctionID(action.substring(pos1+5, pos2), functionListInto);
					if (convertedID.equals("")) {
						missingFunctionCounter++;
						try {
							bufferedWriter.write(action.substring(pos1+5, pos2)
									+ res.getString("DialogImportXEAF38") + "\n");
						} catch (IOException ex1) {}
					} else {
						FunctionUsedByThis funcUsed = new FunctionUsedByThis();
						funcUsed.setFunctionId(convertedID);
						funcUsed.setLaunchEvent("");
						funcUsed.setSortKey("0");
						functionNew.getFunctionUsedByThisList().add(funcUsed);
					}
				}
			}
		}
	}
	
	String convertFunctionID(String originalID, java.util.List<Function> functionListInto) {
		String convertedID = "";
		if (!originalID.equals("")) {
			if (functionListInto != null) {
				for (Function func : functionListInto) {
					if (func.getSortKey().equals(originalID)) {
						convertedID = func.getId();
						break;
					}
				}
			}
		}
		return convertedID;
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

	String createTableDefinition(org.w3c.dom.Element elementFrom, String subsystemID) {
		org.w3c.dom.Element workElement1;
		NodeList elementList1;
		int lastID = 0;
		int length = 0;
		int decimal = 0;
		String tableID = "";
		XeadSystem xeadSystem = frame_.getXeadDocumentModel().getXeadSystem();

		///////////////////////////
		//Create Table Definition//
		///////////////////////////
		Table newTable = new Table();
		java.util.List<Table> tables = xeadSystem.getTables();
		if (tables != null && !tables.isEmpty()) {
			for (Table t : tables) {
				int id = Integer.parseInt(t.getId());
				if (id > lastID) lastID = id;
			}
		}
		tableID = Integer.toString(lastID + 1);
		newTable.setId(tableID);
		newTable.setName(elementFrom.getAttribute("Name"));
		newTable.setSortKey(elementFrom.getAttribute("ID"));
		newTable.setSubsystemId(subsystemID);
		newTable.setDescriptions(elementFrom.getAttribute("Remarks"));
		newTable.setTableTypeId(defaultTableTypeID);
		xeadSystem.getTables().add(newTable);

		/////////////////////
		//Create TableField//
		/////////////////////
		HashMap<String, String> fieldIDMap = new HashMap<String, String>();
		elementList1 = elementFrom.getElementsByTagName("Field");
		for (int i = 0; i < elementList1.getLength(); i++) {
			workElement1 = (org.w3c.dom.Element)elementList1.item(i);
			TableField field = new TableField();
			lastID = 0;
			java.util.List<TableField> allFields = new ArrayList<>();
			if (tables != null) {
				for (Table t : tables) {
					if (t.getTableFields() != null) {
						allFields.addAll(t.getTableFields());
					}
				}
			}
			if (!allFields.isEmpty()) {
				for (TableField f : allFields) {
					int id = Integer.parseInt(f.getId());
					if (id > lastID) lastID = id;
				}
			}
			field.setId(Integer.toString(lastID + 1));
			field.setName(workElement1.getAttribute("Name"));
			field.setSortKey(workElement1.getAttribute("Order"));
			field.setDescriptions(workElement1.getAttribute("Remarks"));
			field.setAlias(workElement1.getAttribute("ID"));
			if (workElement1.getAttribute("TypeOptions").contains("VIRTUAL")) {
				field.setAttributeType("DERIVABLE");
			} else {
				field.setAttributeType("NATIVE");
			}
			if (workElement1.getAttribute("Size").equals("")) {
				length = 0;
			} else {
				length = Integer.parseInt(workElement1.getAttribute("Size"));				
			}
			if (workElement1.getAttribute("Decimal").equals("")) {
				decimal = 0;
			} else {
				decimal = Integer.parseInt(workElement1.getAttribute("Decimal"));				
			}
			field.setDataTypeId(getIDOfDataType(workElement1.getAttribute("Type"), length, decimal));
			if (i < 20) {
				field.setShowOnModel("true");
			} else {
				field.setShowOnModel("false");
			}
			if (workElement1.getAttribute("Nullable").equals("T")) {
				field.setNotNull("false");
			} else {
				field.setNotNull("true");
			}
			if (workElement1.getAttribute("NoUpdate").equals("T")) {
				field.setNoUpdate("true");
			} else {
				field.setNoUpdate("false");
			}
			field.setDefault("");

			newTable.getTableFields().add(field);
			fieldIDMap.put(field.getAlias(), field.getId());
		}

		///////////////////
		//Create TableKey//
		///////////////////
		boolean hasPK = false;
		StringTokenizer workTokenizer;
		String fieldID;
		elementList1 = elementFrom.getElementsByTagName("Key");
		for (int i = 0; i < elementList1.getLength(); i++) {
			workElement1 = (org.w3c.dom.Element)elementList1.item(i);
			if (workElement1.getAttribute("Type").equals("PK")
					|| workElement1.getAttribute("Type").equals("SK")
					|| workElement1.getAttribute("Type").equals("XK")) {
				TableKey key = new TableKey();
				key.setId(Integer.toString(i));
				key.setType(workElement1.getAttribute("Type"));
				if (workElement1.getAttribute("Type").equals("PK")) {
					key.setSortKey("000");
				} else {
					key.setSortKey("010");
				}
				workTokenizer = new StringTokenizer(workElement1.getAttribute("Fields"), ";" );
				while (workTokenizer.hasMoreTokens()) {
					fieldID = workTokenizer.nextToken();
					TableKeyField keyField = new TableKeyField();
					if (workElement1.getAttribute("Type").equals("XK")) {
						if (fieldID.contains("(D)")) {
							fieldID = fieldID.replace("(D)", "");
							keyField.setAscDesc("D");
						} else {
							keyField.setAscDesc("A");
						}
					}
					keyField.setFieldId(fieldIDMap.get(fieldID));
					keyField.setSortKey(Modeler.getFormatted4ByteString(i * 10));
					key.getTableKeyFields().add(keyField);
				}
				newTable.getTableKeys().add(key);
			}
			if (workElement1.getAttribute("Type").equals("PK")) {
				hasPK = true;
			}
		}
		if (!hasPK) {
			TableKey key = new TableKey();
			key.setId(Integer.toString(0));
			key.setType("PK");
			key.setSortKey("000");
			newTable.getTableKeys().add(key);
		}
		
		return tableID;
	}

	void importSubsystem() {
		/////////////////////////////////////////
		//Update attributes of target subsystem//
		/////////////////////////////////////////
		blockSubsystemInto.setName(blockElementFrom.getAttribute("Name"));
		blockSubsystemInto.setDescriptions(blockElementFrom.getAttribute("Descriptions"));
	}

	void setupFunctionTypeMap() {
		XeadSystem xeadSystem = frame_.getXeadDocumentModel().getXeadSystem();
		int lastID;
		String idOfFunctionType;
		boolean isAlreadyExisting;
		java.util.List<FunctionType> functionTypes = xeadSystem.getFunctionTypes();

		functionTypeMap.clear();
		
		for (int i = 0; i < typeIDArray.length; i++) {
			isAlreadyExisting = false;
			if (functionTypes != null) {
				for (FunctionType ft : functionTypes) {
					if (ft.getSortKey().equals(typeIDArray[i])) {
						isAlreadyExisting = true;
						functionTypeMap.put(typeIDArray[i], ft.getId());
						break;
					}
				}
			}
			if (!isAlreadyExisting) {
				FunctionType newFunctionType = new FunctionType();
				lastID = 0;
				if (functionTypes != null && !functionTypes.isEmpty()) {
					for (FunctionType ft : functionTypes) {
						int id = Integer.parseInt(ft.getId());
						if (id > lastID) lastID = id;
					}
				}
				idOfFunctionType = Integer.toString(lastID + 1);
				newFunctionType.setId(idOfFunctionType);
				newFunctionType.setSortKey(typeIDArray[i]);
				newFunctionType.setName(typeNameArray[i]);
				newFunctionType.setDescriptions(typeDescArray[i]);
				xeadSystem.getFunctionTypes().add(newFunctionType);
				functionTypeMap.put(typeIDArray[i], idOfFunctionType);
			}
		}
		
	}

	Function createFunctionDefinition(org.w3c.dom.Element elementFrom, String subsystemID) {
		int lastID = 0;
		XeadSystem xeadSystem = frame_.getXeadDocumentModel().getXeadSystem();

		//////////////////////////////
		//Create Function Definition//
		//////////////////////////////
		Function newFunction = new Function();
		java.util.List<Function> functions = xeadSystem.getFunctions();
		if (functions != null && !functions.isEmpty()) {
			for (Function f : functions) {
				int id = Integer.parseInt(f.getId());
				if (id > lastID) lastID = id;
			}
		}
		newFunction.setId(Integer.toString(lastID + 1));
		newFunction.setName(elementFrom.getAttribute("Name"));
		newFunction.setSortKey(elementFrom.getAttribute("ID"));
		newFunction.setSubsystemId(subsystemID);
		newFunction.setReturn(res.getString("DialogImportXEAF04"));
		newFunction.setDescriptions("");
		newFunction.setFunctionTypeId(functionTypeMap.get(elementFrom.getAttribute("Type")));

		//////////////////////////////////
		//Make up for attributes by type//
		//////////////////////////////////
		if (elementFrom.getAttribute("Type").equals("XF000")) {
			newFunction.setSummary(typeDescArray[0]);
			newFunction.setParameters("*None");
			xeadSystem.getFunctions().add(newFunction);
		}
		if (elementFrom.getAttribute("Type").equals("XF100")) {
			newFunction.setSummary(typeDescArray[1]);
			newFunction.setParameters("*None");
			setupElementForFunctionXF100(elementFrom, newFunction);
			xeadSystem.getFunctions().add(newFunction);
		}
		if (elementFrom.getAttribute("Type").equals("XF110")) {
			newFunction.setSummary(typeDescArray[2]);
			newFunction.setParameters("*None");
			setupElementForFunctionXF110(elementFrom, newFunction);
			xeadSystem.getFunctions().add(newFunction);
		}
		if (elementFrom.getAttribute("Type").equals("XF200")) {
			newFunction.setSummary(typeDescArray[3]);
			setupElementForFunctionXF200(elementFrom, newFunction);
			xeadSystem.getFunctions().add(newFunction);
		}
		if (elementFrom.getAttribute("Type").equals("XF290")) {
			newFunction.setSummary(typeDescArray[4]);
			setupElementForFunctionXF290(elementFrom, newFunction);
			xeadSystem.getFunctions().add(newFunction);
		}
		if (elementFrom.getAttribute("Type").equals("XF300")) {
			newFunction.setSummary(typeDescArray[5]);
			setupElementForFunctionXF300(elementFrom, newFunction);
			xeadSystem.getFunctions().add(newFunction);
		}
		if (elementFrom.getAttribute("Type").equals("XF310")) {
			newFunction.setSummary(typeDescArray[6]);
			setupElementForFunctionXF310(elementFrom, newFunction);
			xeadSystem.getFunctions().add(newFunction);
		}
		if (elementFrom.getAttribute("Type").equals("XF390")) {
			newFunction.setSummary(typeDescArray[7]);
			setupElementForFunctionXF390(elementFrom, newFunction);
			xeadSystem.getFunctions().add(newFunction);
		}
		
		return newFunction;
	}

	void setupElementForFunctionXF100(org.w3c.dom.Element elementFrom, Function newFunction) {
		Table tableElementInto = convertTableID(elementFrom.getAttribute("PrimaryTable"));
		if (tableElementInto == null) {
			missingTableCounter++;
			try {
				bufferedWriter.write(elementFrom.getAttribute("PrimaryTable")
						+ res.getString("DialogImportXEAF37") + "\n");
			} catch (IOException ex1) {}
		} else {
			IOTable ioTable = new IOTable();
			ioTable.setId("1");
			ioTable.setNameExtension("");
			ioTable.setSortKey("020");
			ioTable.setDescriptions(res.getString("DialogImportXEAF13"));
			ioTable.setTableId(tableElementInto.getId());
			ioTable.setPosition("PRIMARY");
			ioTable.setOpC("");
			ioTable.setOpR("+");
			ioTable.setOpU("");
			ioTable.setOpD("");
			newFunction.getIOTables().add(ioTable);
		}
	}

	void setupElementForFunctionXF110(org.w3c.dom.Element elementFrom, Function newFunction) {
		Table tableElementInto = convertTableID(elementFrom.getAttribute("PrimaryTable"));
		if (tableElementInto == null) {
			missingTableCounter++;
			try {
				bufferedWriter.write(elementFrom.getAttribute("PrimaryTable")
						+ res.getString("DialogImportXEAF37") + "\n");
			} catch (IOException ex1) {}
		} else {
			IOTable ioTable = new IOTable();
			ioTable.setId("1");
			ioTable.setNameExtension("");
			ioTable.setSortKey("020");
			ioTable.setDescriptions(res.getString("DialogImportXEAF13"));
			ioTable.setTableId(tableElementInto.getId());
			ioTable.setPosition("PRIMARY");
			ioTable.setOpC("");
			ioTable.setOpR("+");
			ioTable.setOpU("+");
			ioTable.setOpD("");
			newFunction.getIOTables().add(ioTable);
		}
		if (!elementFrom.getAttribute("BatchTable").equals("")) {
			tableElementInto = convertTableID(elementFrom.getAttribute("BatchTable"));
			if (tableElementInto == null) {
				missingTableCounter++;
				try {
					bufferedWriter.write(elementFrom.getAttribute("BatchTable")
							+ res.getString("DialogImportXEAF37") + "\n");
				} catch (IOException ex1) {}
			} else {
				IOTable ioTable = new IOTable();
				ioTable.setId("2");
				ioTable.setNameExtension("");
				ioTable.setSortKey("020");
				ioTable.setDescriptions(res.getString("DialogImportXEAF24"));
				ioTable.setTableId(tableElementInto.getId());
				ioTable.setPosition("JOIN");
				ioTable.setOpC("+");
				ioTable.setOpR("");
				ioTable.setOpU("");
				ioTable.setOpD("");
				newFunction.getIOTables().add(ioTable);
			}
		}
	}

	void setupElementForFunctionXF200(org.w3c.dom.Element elementFrom, Function newFunction) {
		Table tableElementInto = convertTableID(elementFrom.getAttribute("PrimaryTable"));
		if (tableElementInto == null) {
			missingTableCounter++;
			try {
				bufferedWriter.write(elementFrom.getAttribute("PrimaryTable")
						+ res.getString("DialogImportXEAF37") + "\n");
			} catch (IOException ex1) {}
		} else {
			IOTable ioTable = new IOTable();
			ioTable.setId("1");
			ioTable.setNameExtension("");
			ioTable.setSortKey("020");
			ioTable.setDescriptions(res.getString("DialogImportXEAF13"));
			ioTable.setTableId(tableElementInto.getId());
			ioTable.setPosition("PRIMARY");
			ioTable.setOpC("+");
			ioTable.setOpR("+");
			ioTable.setOpU("+");
			ioTable.setOpD("+");
			newFunction.getIOTables().add(ioTable);
			newFunction.setParameters(getKeyFieldNameOfTable(tableElementInto));
		}
	}

	void setupElementForFunctionXF290(org.w3c.dom.Element elementFrom, Function newFunction) {
		Table tableElementInto = convertTableID(elementFrom.getAttribute("PrimaryTable"));
		if (tableElementInto == null) {
			missingTableCounter++;
			try {
				bufferedWriter.write(elementFrom.getAttribute("PrimaryTable")
						+ res.getString("DialogImportXEAF37") + "\n");
			} catch (IOException ex1) {}
		} else {
			IOTable ioTable = new IOTable();
			ioTable.setId("1");
			ioTable.setNameExtension("");
			ioTable.setSortKey("020");
			ioTable.setDescriptions(res.getString("DialogImportXEAF13"));
			ioTable.setTableId(tableElementInto.getId());
			ioTable.setPosition("PRIMARY");
			ioTable.setOpC("");
			ioTable.setOpR("+");
			ioTable.setOpU("");
			ioTable.setOpD("");
			newFunction.getIOTables().add(ioTable);
			newFunction.setParameters(getKeyFieldNameOfTable(tableElementInto));
		}
	}

	void setupElementForFunctionXF300(org.w3c.dom.Element elementFrom, Function newFunction) {
		Table tableElementInto = convertTableID(elementFrom.getAttribute("HeaderTable"));
		if (tableElementInto == null) {
			missingTableCounter++;
			try {
				bufferedWriter.write(elementFrom.getAttribute("HeaderTable")
						+ res.getString("DialogImportXEAF37") + "\n");
			} catch (IOException ex1) {}
		} else {
			IOTable ioTable = new IOTable();
			ioTable.setId("1");
			ioTable.setNameExtension("");
			ioTable.setSortKey("020");
			ioTable.setDescriptions(res.getString("DialogImportXEAF13"));
			ioTable.setTableId(tableElementInto.getId());
			ioTable.setPosition("HEADER");
			ioTable.setOpC("");
			ioTable.setOpR("+");
			ioTable.setOpU("");
			ioTable.setOpD("");
			newFunction.getIOTables().add(ioTable);
			newFunction.setParameters(getKeyFieldNameOfTable(tableElementInto));
		}
		NodeList detailList = elementFrom.getElementsByTagName("Detail");
		for (int i = 0; i < detailList.getLength(); i++) {
			org.w3c.dom.Element workElement = (org.w3c.dom.Element)detailList.item(i);
			tableElementInto = convertTableID(workElement.getAttribute("Table"));
			if (tableElementInto == null) {
				missingTableCounter++;
				try {
					bufferedWriter.write(workElement.getAttribute("Table")
							+ res.getString("DialogImportXEAF37") + "\n");
				} catch (IOException ex1) {}
			} else {
				IOTable ioTable = new IOTable();
				ioTable.setId("2");
				ioTable.setNameExtension("");
				ioTable.setSortKey("020");
				ioTable.setDescriptions(res.getString("DialogImportXEAF15"));
				ioTable.setTableId(tableElementInto.getId());
				ioTable.setPosition("DETAIL");
				ioTable.setOpC("");
				ioTable.setOpR("+");
				ioTable.setOpU("");
				ioTable.setOpD("");
				newFunction.getIOTables().add(ioTable);
			}
		}
	}

	void setupElementForFunctionXF310(org.w3c.dom.Element elementFrom, Function newFunction) {
		Table tableElementInto = convertTableID(elementFrom.getAttribute("HeaderTable"));
		if (tableElementInto == null) {
			missingTableCounter++;
			try {
				bufferedWriter.write(elementFrom.getAttribute("HeaderTable")
						+ res.getString("DialogImportXEAF37") + "\n");
			} catch (IOException ex1) {}
		} else {
			IOTable ioTable = new IOTable();
			ioTable.setId("1");
			ioTable.setNameExtension("");
			ioTable.setSortKey("020");
			ioTable.setDescriptions(res.getString("DialogImportXEAF14"));
			ioTable.setTableId(tableElementInto.getId());
			ioTable.setPosition("HEADER");
			ioTable.setOpC("");
			ioTable.setOpR("+");
			ioTable.setOpU("+");
			ioTable.setOpD("");
			newFunction.getIOTables().add(ioTable);
			newFunction.setParameters(getKeyFieldNameOfTable(tableElementInto));
		}
		tableElementInto = convertTableID(elementFrom.getAttribute("DetailTable"));
		if (tableElementInto == null) {
			missingTableCounter++;
			try {
				bufferedWriter.write(elementFrom.getAttribute("DetailTable")
						+ res.getString("DialogImportXEAF37") + "\n");
			} catch (IOException ex1) {}
		} else {
			IOTable ioTable = new IOTable();
			ioTable.setId("2");
			ioTable.setNameExtension("");
			ioTable.setSortKey("020");
			ioTable.setDescriptions(res.getString("DialogImportXEAF15"));
			ioTable.setTableId(tableElementInto.getId());
			ioTable.setPosition("DETAIL");
			ioTable.setOpC("+");
			ioTable.setOpR("+");
			ioTable.setOpU("+");
			ioTable.setOpD("+");
			newFunction.getIOTables().add(ioTable);
		}
	}

	void setupElementForFunctionXF390(org.w3c.dom.Element elementFrom, Function newFunction) {
		Table tableElementInto = convertTableID(elementFrom.getAttribute("HeaderTable"));
		if (tableElementInto == null) {
			missingTableCounter++;
			try {
				bufferedWriter.write(elementFrom.getAttribute("HeaderTable")
						+ res.getString("DialogImportXEAF37") + "\n");
			} catch (IOException ex1) {}
		} else {
			IOTable ioTable = new IOTable();
			ioTable.setId("1");
			ioTable.setNameExtension("");
			ioTable.setSortKey("020");
			ioTable.setDescriptions(res.getString("DialogImportXEAF14"));
			ioTable.setTableId(tableElementInto.getId());
			ioTable.setPosition("HEADER");
			ioTable.setOpC("");
			ioTable.setOpR("+");
			ioTable.setOpU("");
			ioTable.setOpD("");
			newFunction.getIOTables().add(ioTable);
			newFunction.setParameters(getKeyFieldNameOfTable(tableElementInto));
		}
		tableElementInto = convertTableID(elementFrom.getAttribute("DetailTable"));
		if (tableElementInto == null) {
			missingTableCounter++;
			try {
				bufferedWriter.write(elementFrom.getAttribute("DetailTable")
						+ res.getString("DialogImportXEAF37") + "\n");
			} catch (IOException ex1) {}
		} else {
			IOTable ioTable = new IOTable();
			ioTable.setId("2");
			ioTable.setNameExtension("");
			ioTable.setSortKey("020");
			ioTable.setDescriptions(res.getString("DialogImportXEAF15"));
			ioTable.setTableId(tableElementInto.getId());
			ioTable.setPosition("DETAIL");
			ioTable.setOpC("");
			ioTable.setOpR("+");
			ioTable.setOpU("");
			ioTable.setOpD("");
			newFunction.getIOTables().add(ioTable);
		}
	}
	
	String getKeyFieldNameOfTable(Table table) {
		String keyFieldName = "";

		java.util.List<TableKey> keyList = table.getTableKeys();
		if (keyList != null) {
			for (TableKey key : keyList) {
				if (key.getType().equals("PK")) {
					java.util.List<TableKeyField> keyFieldList = key.getTableKeyFields();
					java.util.List<String> keyFieldIDList = new ArrayList<String>();
					if (keyFieldList != null) {
						for (TableKeyField ktf : keyFieldList) {
							keyFieldIDList.add(ktf.getFieldId());
						}
					}
					StringBuffer bf = new StringBuffer();
					java.util.List<TableField> fieldList = table.getTableFields();
					if (fieldList != null) {
						for (int j = 0; j < keyFieldIDList.size(); j++) {
							for (TableField field : fieldList) {
								if (field.getId().equals(keyFieldIDList.get(j))) {
									if (!bf.toString().equals("")) {
										bf.append(", ");
									}
									bf.append(field.getName());
									break;
								}
							}
						}
					}
					keyFieldName = bf.toString();
					break;
				}
			}
		}
		return keyFieldName;
	}
	
	String getIDOfDataType(String dataType, int length, int decimal) {
		String idOfDataType = "";
		boolean isAlreadyExisting = false;
		int lastID = 0;
		XeadSystem xeadSystem = frame_.getXeadDocumentModel().getXeadSystem();
		java.util.List<DataType> dataTypes = xeadSystem.getDataTypes();

		if (dataTypes != null) {
			for (DataType dt : dataTypes) {
				if (dt.getSortKey().equals(dataType + "_" + length)) {
					idOfDataType = dt.getId();
					isAlreadyExisting = true;
					break;
				}
			}
		}
		if (!isAlreadyExisting) {
			DataType newDataType = new DataType();
			if (dataTypes != null && !dataTypes.isEmpty()) {
				for (DataType dt : dataTypes) {
					int id = Integer.parseInt(dt.getId());
					if (id > lastID) lastID = id;
				}
			}
			idOfDataType = Integer.toString(lastID + 1);
			newDataType.setId(idOfDataType);
			newDataType.setSortKey(dataType + "_" + length);
			newDataType.setName(dataType + "_" + length);
			newDataType.setBasicType("String");
			if (dataType.contains("INT")
					|| dataType.contains("DOUBLE")
					|| dataType.equals("DECIMAL")
					|| dataType.equals("NUMERIC")
					|| dataType.equals("REAL")) {
				newDataType.setBasicType("SignedNumber");
			}
			if (dataType.contains("DATE")) {
				newDataType.setBasicType("Date");
			}
			if (dataType.contains("BINARY")
					|| dataType.contains("LOB")) {
				newDataType.setBasicType("Object");
			}
			newDataType.setLength(Integer.toString(length));
			newDataType.setDecimal(Integer.toString(decimal));
			if (dataType.contains("CHAR")) {
				newDataType.setSqlExpression(dataType + "(" + length + ")");
			} else {
				newDataType.setSqlExpression(dataType);
			}
			xeadSystem.getDataTypes().add(newDataType);
		}
		
		return idOfDataType;
	}

	Table convertTableID(String tableIDFrom) {
		Table convertedTable = null;

		if (!tableIDFrom.equals("")) {
			XeadSystem xeadSystem = frame_.getXeadDocumentModel().getXeadSystem();
			java.util.List<Table> tables = xeadSystem.getTables();
			if (tables != null) {
				for (Table table : tables) {
					if (table.getSortKey().equals(tableIDFrom)) {
						convertedTable = table;
						break;
					}
				}
			}
		}

		return convertedTable;
	}

	void jButtonCancel_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}

	/**
	 * ComboBox model for the "From" combo box - wraps DOM Elements from the import file
	 */
	class SortableSubsystemComboBoxModel extends DefaultComboBoxModel {
		private static final long serialVersionUID = 1L;
		
		public void sortElements() {
			ArrayList<Object> list = new ArrayList<Object>();
			for (int i = 0; i < this.getSize(); i++) {
				Object obj = this.getElementAt(i);
				// Items are already wrapped, just add them directly
				list.add(obj);
			}
			this.removeAllElements();
			Collections.sort(list, new XeadNodeComparator());
			for (Object obj : list) {
				this.addElement(obj);
			}
		}
	}

	/**
	 * Wrapper for DOM Element from import file
	 */
	class XeadNodeFromElement implements Comparable<XeadNodeFromElement> {
		private org.w3c.dom.Element domElement;
		
		public XeadNodeFromElement(org.w3c.dom.Element element) {
			this.domElement = element;
		}
		
		public String toString() {
			String sortKey = domElement.getAttribute("SortKey");
			if (sortKey.equals("")) {
				return domElement.getAttribute("ID") + " " + domElement.getAttribute("Name");
			} else {
				return sortKey + " " + domElement.getAttribute("Name");
			}
		}
		
		public org.w3c.dom.Element getElement() {
			return domElement;
		}
		
		public String getSortKey() {
			return domElement.getAttribute("SortKey");
		}
		
		public int compareTo(XeadNodeFromElement other) {
			return this.getSortKey().compareTo(other.getSortKey());
		}
	}

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
	 * Comparator for mixed XeadNode types
	 */
	class XeadNodeComparator implements Comparator<Object> {
		public int compare(Object o1, Object o2) {
			String s1, s2;
			if (o1 instanceof XeadNodeFromElement) {
				s1 = ((XeadNodeFromElement)o1).getSortKey();
			} else if (o1 instanceof XeadNodeFromSubsystem) {
				s1 = ((XeadNodeFromSubsystem)o1).getSortKey();
			} else {
				s1 = o1.toString();
			}
			if (o2 instanceof XeadNodeFromElement) {
				s2 = ((XeadNodeFromElement)o2).getSortKey();
			} else if (o2 instanceof XeadNodeFromSubsystem) {
				s2 = ((XeadNodeFromSubsystem)o2).getSortKey();
			} else {
				s2 = o2.toString();
			}
			return s1.compareTo(s2);
		}
	}
}

class DialogImportXEAF_jComboBoxBlockFrom_actionAdapter implements java.awt.event.ActionListener {
	DialogImportXEAF adaptee;

	DialogImportXEAF_jComboBoxBlockFrom_actionAdapter(DialogImportXEAF adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.jComboBoxBlockFrom_actionPerformed(e);
	}
}

class DialogImportXEAF_jComboBoxBlockInto_actionAdapter implements java.awt.event.ActionListener {
	DialogImportXEAF adaptee;

	DialogImportXEAF_jComboBoxBlockInto_actionAdapter(DialogImportXEAF adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.jComboBoxBlockInto_actionPerformed(e);
	}
}

class DialogImportXEAF_jButtonStart_actionAdapter implements java.awt.event.ActionListener {
	DialogImportXEAF adaptee;

	DialogImportXEAF_jButtonStart_actionAdapter(DialogImportXEAF adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.jButtonStart_actionPerformed(e);
	}
}

class DialogImportXEAF_jButtonCancel_actionAdapter implements java.awt.event.ActionListener {
	DialogImportXEAF adaptee;

	DialogImportXEAF_jButtonCancel_actionAdapter(DialogImportXEAF adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.jButtonCancel_actionPerformed(e);
	}
}
