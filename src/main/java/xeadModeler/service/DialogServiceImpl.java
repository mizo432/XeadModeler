package xeadModeler.service;

// TODO: DialogMatrixListをJSONベースに修正するまでコメントアウト
// import xeadModeler.view.DialogMatrixList;
import xeadModeler.view.DialogCreateTableStatement;
import xeadModeler.view.DialogDocuments;
// TODO: DialogReorganizeDataTypesをJSONベースに修正するまでコメントアウト
// import xeadModeler.view.DialogReorganizeDataTypes;

import org.springframework.stereotype.Service;

import javax.swing.*;
import java.awt.*;

/**
 * ダイアログ操作サービスの実装クラス
 * メッセージダイアログ、確認ダイアログなどの機能を担当します。
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
@Service
public class DialogServiceImpl implements DialogService {
    
    @Override
    public void showMessageDialog(Component parentComponent, Object message) {
        JOptionPane.showMessageDialog(parentComponent, message);
    }
    
    @Override
    public void showMessageDialog(Component parentComponent, Object message, String title, int messageType) {
        JOptionPane.showMessageDialog(parentComponent, message, title, messageType);
    }
    
    @Override
    public int showConfirmDialog(Component parentComponent, Object message) {
        return JOptionPane.showConfirmDialog(parentComponent, message);
    }
    
    @Override
    public int showConfirmDialog(Component parentComponent, Object message, String title, int optionType) {
        return JOptionPane.showConfirmDialog(parentComponent, message, title, optionType);
    }
    
    @Override
    public int showOptionDialog(Component parentComponent, Object message, String title, int messageType, Object[] options) {
        return JOptionPane.showOptionDialog(parentComponent, message, title, 
            JOptionPane.DEFAULT_OPTION, messageType, null, options, options[0]);
    }
    
    @Override
    public void showMatrixListDialog(JFrame parentFrame) {
        // TODO: DialogMatrixListのコンストラクタをJFrameに変更するまでコメントアウト
        // DialogMatrixList dialog = new DialogMatrixList(parentFrame);
        // dialog.setLocationRelativeTo(parentFrame);
        // dialog.setVisible(true);
    }
    
    @Override
    public void showCreateTableStatementDialog(JFrame parentFrame) {
        // TODO: DialogCreateTableStatementのコンストラクタをJFrameに変更するまでコメントアウト
        // DialogCreateTableStatement dialog = new DialogCreateTableStatement(parentFrame);
        // dialog.setLocationRelativeTo(parentFrame);
        // dialog.setVisible(true);
    }
    
    @Override
    public void showDocumentsDialog(JFrame parentFrame) {
        DialogDocuments dialog = new DialogDocuments(parentFrame);
        dialog.setLocationRelativeTo(parentFrame);
        dialog.setVisible(true);
    }
    
    @Override
    public void showReorganizeDataTypesDialog(JFrame parentFrame) {
        // TODO: DialogReorganizeDataTypesのコンストラクタをJFrameに変更するまでコメントアウト
        // DialogReorganizeDataTypes dialog = new DialogReorganizeDataTypes(parentFrame);
        // dialog.setLocationRelativeTo(parentFrame);
        // dialog.setVisible(true);
    }
}
