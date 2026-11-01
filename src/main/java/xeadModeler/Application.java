package xeadModeler;

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

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import xeadModeler.controller.FileController;
import xeadModeler.model.XeadDocumentModel;
import xeadModeler.service.FileService;
import xeadModeler.service.ChangeStateService;
import xeadModeler.service.XeadUndoManager;
import xeadModeler.service.XmlService;
import xeadModeler.service.DrawingService;
import xeadModeler.service.TreeService;
import xeadModeler.service.TableService;
import xeadModeler.service.DialogService;
import xeadModeler.view.DialogAbout;

import javax.swing.UIManager;

import java.awt.*;

import javax.swing.*;

import java.util.ResourceBundle;

import mdlaf.MaterialLookAndFeel;
import mdlaf.themes.MaterialOceanicTheme;

@Component
public class Application implements ApplicationRunner {
	private static ResourceBundle res = ResourceBundle.getBundle("xeadModeler.Res");
	private boolean packFrame = false;
	private JWindow splashScreen;
	private JLabel  splashIcon;
	private JLabel  splashLabel;

	// MVCコンポーネント
	@Autowired
	private FileService fileService;
	@Autowired
	private ChangeStateService changeStateService;
	@Autowired
	private XeadUndoManager xeadUndoManager;
	@Autowired
	private XmlService xmlService;
	@Autowired
	private DrawingService drawingService;
	@Autowired
	private TreeService treeService;
	@Autowired
	private TableService tableService;
	@Autowired
	private DialogService dialogService;

	public Application() {
	}

	/**
	 * ファイル操作コントローラーを取得します
	 * @param parentFrame 親フレーム
	 * @return FileController
	 */
	public FileController getFileController(JFrame parentFrame) {
		return new FileController(fileService, changeStateService, parentFrame, DialogAbout.FORMAT_VERSION);
	}

	/**
	 * ドキュメントモデルを取得します
	 * @return XeadDocumentModel
	 */
	public XeadDocumentModel getDocumentModel() {
		return fileService.getDocumentModel();
	}

	/**
	 * 変更状態サービスを取得します
	 * @return ChangeStateService
	 */
	public ChangeStateService getChangeStateService() {
		return changeStateService;
	}

	/**
	 * アンドゥマネージャーを取得します
	 * @return XeadUndoManager
	 */
	public XeadUndoManager getXeadUndoManager() {
		return xeadUndoManager;
	}

	/**
	 * XMLデータ管理サービスを取得します
	 * @return XmlService
	 */
	public XmlService getXmlService() {
		return xmlService;
	}

	/**
	 * 描画サービスを取得します
	 * @return DrawingService
	 */
	public DrawingService getDrawingService() {
		return drawingService;
	}

	/**
	 * ツリー操作サービスを取得します
	 * @return TreeService
	 */
	public TreeService getTreeService() {
		return treeService;
	}

	/**
	 * テーブル操作サービスを取得します
	 * @return TableService
	 */
	public TableService getTableService() {
		return tableService;
	}

	/**
	 * ダイアログ操作サービスを取得します
	 * @return DialogService
	 */
	public DialogService getDialogService() {
		return dialogService;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// SwingはEvent Dispatch Threadで実行する必要がある
		EventQueue.invokeLater(() -> {
			try {
				// Material Design Look and Feel を適用
				UIManager.setLookAndFeel(new MaterialLookAndFeel());
				new MaterialOceanicTheme();
			}
			catch(Exception e) {
				// Silently ignore look and feel errors - application will use default L&F
			}
			initializeGUI(args.getSourceArgs());
		});
	}

	private void initializeGUI(String[] args) {
		ImageIcon image = new ImageIcon(xeadModeler.Application.class.getResource("splash.png"));
		splashIcon = new JLabel(image);
		splashIcon.setLayout(null);
		splashLabel = new JLabel();
		splashLabel.setFont(new java.awt.Font("Dialog", 0, 16));
		splashLabel.setOpaque(false);
		splashLabel.setBounds(280, 205, 220, 20);
		splashLabel.setText(res.getString("SplashMessage0"));
		splashIcon.add(splashLabel);
		splashScreen = new JWindow();
		splashScreen.getContentPane().add(splashIcon);
		splashScreen.pack();
		splashScreen.setLocationRelativeTo(null);
		showSplash();
		//
		Modeler frame = new Modeler(args, this);
		if (packFrame) {
			frame.pack();
		} else {
			frame.validate();
		}
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = frame.getSize();
		if (frameSize.height > screenSize.height) {
			frameSize.height = screenSize.height;
		}
		if (frameSize.width > screenSize.width) {
			frameSize.width = screenSize.width;
		}
		frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
	}
	
	public void showSplash() {
		splashScreen.setVisible(true);
	}
	
	public void setTextOnSplash(String text) {
		if (splashLabel != null) {
			splashLabel.setText(text);
		}
	}

	public void hideSplash() {
		if (splashScreen != null) {
			splashScreen.setVisible(false);
			splashScreen = null;
			splashLabel  = null;
		}
	}
}