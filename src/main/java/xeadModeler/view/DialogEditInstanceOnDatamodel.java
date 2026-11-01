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

import java.awt.event.*;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.List;
import java.util.stream.Collectors;

import xeadModeler.Modeler;
import xeadModeler.Modeler.*;
import xeadModeler.model.json.SubsystemTable;
import xeadModeler.model.json.Instance;

public class DialogEditInstanceOnDatamodel extends JDialog {
	private static final long serialVersionUID = 1L;
	private static ResourceBundle res = ResourceBundle.getBundle("xeadModeler.Res");
	private JTabbedPane jTabbedPane = new JTabbedPane(JTabbedPane.BOTTOM);
	private SpinnerNumberModel sModel = new SpinnerNumberModel(0, 0, 30, 1);
	private JSpinner spinner = new JSpinner(sModel);
	private Modeler frame_;
	private SubsystemTable subsystemTable_;
	private JPopupMenu jPopupMenu = new JPopupMenu();
	private JMenuItem jMenuItemSetSequence = new JMenuItem(res.getString("DialogEditInstanceOnDatamodel2"));
	private JMenuItem jMenuItemAddSlide = new JMenuItem(res.getString("DialogEditInstanceOnDatamodel3"));
	private JMenuItem jMenuItemRemoveSlide = new JMenuItem(res.getString("DialogEditInstanceOnDatamodel4"));
	private ArrayList<JTextArea> textAreaList = new ArrayList<JTextArea>(); 
	private ArrayList<String> sortKeyList = new ArrayList<String>();
	private boolean isEdited = false;

	public DialogEditInstanceOnDatamodel(Modeler frame) {
		super(frame, "", true);
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
		jPopupMenu.add(jMenuItemSetSequence);
		jPopupMenu.add(jMenuItemAddSlide);
		jPopupMenu.add(jMenuItemRemoveSlide);
		jMenuItemSetSequence.addActionListener(new DialogEditInstanceOnDatamodel_jMenuItemSetSequenceActionAdapter(this));
		jMenuItemAddSlide.addActionListener(new DialogEditInstanceOnDatamodel_jMenuItemAddSlideActionAdapter(this));
		jMenuItemRemoveSlide.addActionListener(new DialogEditInstanceOnDatamodel_jMenuItemRemoveSlideActionAdapter(this));
		this.setResizable(true);
		this.getContentPane().add(jTabbedPane);
	}

	public boolean request(DatamodelEntityBox entityBox) {
		subsystemTable_ = entityBox.getSubsystemTable();
		jTabbedPane.removeAll();
		textAreaList.clear();
		sortKeyList.clear();
		isEdited = false;

		int maxRows = 0;
		List<Instance> instances = null;
		if (subsystemTable_ != null) {
			instances = subsystemTable_.getInstances();
		}
		
		if (instances == null || instances.isEmpty()) {
			// インスタンスがない場合は、後方互換性のためにinstanceフィールドを使用
			String instanceText = "";
			if (subsystemTable_ != null) {
				instanceText = subsystemTable_.getInstance();
			}
			maxRows = setupTextAreaForInstance(instanceText, "0");
		} else {
			// SortKeyでソート
			List<Instance> sortedInstances = instances.stream()
				.sorted((a, b) -> {
					String sortKeyA = a.getSortKey() != null ? a.getSortKey() : "";
					String sortKeyB = b.getSortKey() != null ? b.getSortKey() : "";
					return sortKeyA.compareTo(sortKeyB);
				})
				.collect(Collectors.toList());
			
			for (Instance instance : sortedInstances) {
				int rows = setupTextAreaForInstance(instance.getText(), instance.getSortKey());
				if (rows > maxRows) {
					maxRows = rows;
				}
			}
		}

		textAreaList.get(0).requestFocus();
		this.setTitle("Instance - " + entityBox.getTableNode().getName());
		if (frame_.modelSize.equals("S")) {
			this.setPreferredSize(new Dimension(entityBox.getWidth()-58, 150));
			this.setLocation((int)entityBox.getLocationOnScreen().getX()+83, (int)(entityBox.getLocationOnScreen().getY() + entityBox.getHeight() - 4));
		}
		if (frame_.modelSize.equals("M")) {
			this.setPreferredSize(new Dimension(entityBox.getWidth()-150, 200));
			this.setLocation((int)entityBox.getLocationOnScreen().getX()+175, (int)(entityBox.getLocationOnScreen().getY() + entityBox.getHeight() - 8));
		}
		if (frame_.modelSize.equals("L")) {
			this.setPreferredSize(new Dimension(entityBox.getWidth()-242, 300));
			this.setLocation((int)entityBox.getLocationOnScreen().getX()+265, (int)(entityBox.getLocationOnScreen().getY() + entityBox.getHeight() - 12));
		}
		this.pack();
		this.setVisible(true);
		updateAndClose();

		return isEdited;
	}
	
	int setupTextAreaForInstance(String text, String sortKey) {
		JTextArea jTextArea = new JTextArea();
		if (frame_.modelSize.equals("S")) {
			jTextArea.setFont(new java.awt.Font(frame_.ioImageFontName, 0, 7));
		}
		if (frame_.modelSize.equals("M")) {
			jTextArea.setFont(new java.awt.Font(frame_.ioImageFontName, 0, 14));
		}
		if (frame_.modelSize.equals("L")) {
			jTextArea.setFont(new java.awt.Font(frame_.ioImageFontName, 0, 21));
		}
		jTextArea.setLineWrap(false);
		jTextArea.setText(Modeler.substringLinesWithTokenOfEOL(text, "\n"));
		jTextArea.setCaretPosition(0);
		jTextArea.setEditable(true);
		jTextArea.addMouseListener(new DialogEditInstanceOnDatamodel_mouseAdapter(this));
		jTextArea.addKeyListener(new DialogEditInstanceOnDatamodel_keyAdapter(this));
		JScrollPane jScrollPane = new JScrollPane();
		jScrollPane.getViewport().add(jTextArea);
		jTabbedPane.add(jScrollPane, "Slide "+sortKey);
		textAreaList.add(jTextArea);
		sortKeyList.add(sortKey);
		if (frame_.isNormalColorConfigOnDataModel) {
			jTextArea.setCaretColor(Color.WHITE);
			jTextArea.setBackground(Modeler.DESKTOP_COLOR);
			jTextArea.setForeground(Color.WHITE);
		} else {
			jTextArea.setCaretColor(Color.BLACK);
			jTextArea.setBackground(Color.WHITE);
			jTextArea.setForeground(Color.BLACK);
		}
		return jTextArea.getRows();
	}

	void jTextArea_mousePressed(MouseEvent e) {
		if ((e.getModifiers() & InputEvent.BUTTON1_MASK) != InputEvent.BUTTON1_MASK) {
			jPopupMenu.show(e.getComponent(), e.getX(), e.getY());
		}
	}

	void jTextArea_keyPressed(KeyEvent e) {
		isEdited = true;
	}

	void jMenuItemAddSlide_actionPerformed(ActionEvent e) {
		String text = textAreaList.get(jTabbedPane.getSelectedIndex()).getText();
		int sortKey = Integer.parseInt(sortKeyList.get(sortKeyList.size()-1)) + 1;
		setupTextAreaForInstance(text, Integer.toString(sortKey));
		isEdited = true;
	}

	void jMenuItemRemoveSlide_actionPerformed(ActionEvent e) {
		textAreaList.remove(jTabbedPane.getSelectedIndex());
		sortKeyList.remove(jTabbedPane.getSelectedIndex());
		jTabbedPane.remove(jTabbedPane.getSelectedIndex());
		isEdited = true;
		if (jTabbedPane.getTabCount() == 0) {
			frame_.informationOnThisPageChanged = updateAndClose();
		}
	}

	void jMenuItemSetSequence_actionPerformed(ActionEvent e) {
		sModel.setValue(Integer.parseInt(sortKeyList.get(jTabbedPane.getSelectedIndex())));
		int option = JOptionPane.showOptionDialog(this, spinner, res.getString("DialogEditInstanceOnDatamodel2"), JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
		if (option == JOptionPane.OK_OPTION) {
			sortKeyList.set(jTabbedPane.getSelectedIndex(), sModel.getValue().toString());
			jTabbedPane.setTitleAt(jTabbedPane.getSelectedIndex(), "Slide " + sModel.getValue().toString());
			isEdited = true;
		}
	}
	
	public boolean updateAndClose() {
		if (isEdited && subsystemTable_ != null) {
			// 既存のインスタンスリストをクリア
			subsystemTable_.getInstances().clear();
			
			// 新しいインスタンスを追加
			for (int i = 0; i < textAreaList.size(); i++) {
				Instance newInstance = new Instance();
				newInstance.setText(Modeler.concatLinesWithTokenOfEOL(textAreaList.get(i).getText()));
				newInstance.setSortKey(sortKeyList.get(i));
				subsystemTable_.getInstances().add(newInstance);
			}
		}
		this.setVisible(false);

		return isEdited;
	}
}

class DialogEditInstanceOnDatamodel_mouseAdapter extends java.awt.event.MouseAdapter {
	DialogEditInstanceOnDatamodel adaptee;
	DialogEditInstanceOnDatamodel_mouseAdapter(DialogEditInstanceOnDatamodel adaptee) {
		this.adaptee = adaptee;
	}
	public void mousePressed(MouseEvent e) {
		adaptee.jTextArea_mousePressed(e);
	}
	public void mouseClicked(MouseEvent e) {
	}
	public void mouseReleased(MouseEvent e) {
	}
}

class DialogEditInstanceOnDatamodel_keyAdapter extends java.awt.event.KeyAdapter {
	DialogEditInstanceOnDatamodel adaptee;
	DialogEditInstanceOnDatamodel_keyAdapter(DialogEditInstanceOnDatamodel adaptee) {
		this.adaptee = adaptee;
	}
	public void keyPressed(KeyEvent e) {
		adaptee.jTextArea_keyPressed(e);
	}
}

class DialogEditInstanceOnDatamodel_jMenuItemAddSlideActionAdapter implements java.awt.event.ActionListener {
	DialogEditInstanceOnDatamodel adaptee;
	DialogEditInstanceOnDatamodel_jMenuItemAddSlideActionAdapter(DialogEditInstanceOnDatamodel adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.jMenuItemAddSlide_actionPerformed(e);
	}
}

class DialogEditInstanceOnDatamodel_jMenuItemRemoveSlideActionAdapter implements java.awt.event.ActionListener {
	DialogEditInstanceOnDatamodel adaptee;
	DialogEditInstanceOnDatamodel_jMenuItemRemoveSlideActionAdapter(DialogEditInstanceOnDatamodel adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.jMenuItemRemoveSlide_actionPerformed(e);
	}
}

class DialogEditInstanceOnDatamodel_jMenuItemSetSequenceActionAdapter implements java.awt.event.ActionListener {
	DialogEditInstanceOnDatamodel adaptee;
	DialogEditInstanceOnDatamodel_jMenuItemSetSequenceActionAdapter(DialogEditInstanceOnDatamodel adaptee) {
		this.adaptee = adaptee;
	}
	public void actionPerformed(ActionEvent e) {
		adaptee.jMenuItemSetSequence_actionPerformed(e);
	}
}
