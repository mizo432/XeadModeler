package xeadModeler.view;

import xeadModeler.Modeler;
import xeadModeler.Modeler.XeadTreeNode;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

/**
 * ノードジャンプボタン
 * ツリーノードへのジャンプボタンとして機能します。
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class NodeJumpButton extends JButton {

    private static final long serialVersionUID = 1L;
    private final XeadTreeNode node_;
    private final Modeler modeler;
    private final ResourceBundle res;

    /**
     * コンストラクタ
     *
     * @param modeler Modelerインスタンス
     * @param res リソースバンドル
     * @param nd ツリーノード
     */
    public NodeJumpButton(Modeler modeler, ResourceBundle res, XeadTreeNode nd) {
        super();
        this.modeler = modeler;
        this.res = res;
        node_ = nd;
        this.setFont(new java.awt.Font(modeler.mainFontName, 0, Modeler.MAIN_FONT_SIZE - 2));
        String nodeType = ((XeadTreeNode) node_).getType();
        this.addActionListener(new NodeJumpButtonActionAdapter(this));
        if (nodeType == "System") {
            this.setIcon(modeler.imageIconSystem);
            this.setText(node_.getName());
        }
        if (nodeType == "SubjectAreaList") {
            this.setIcon(modeler.imageIconSubjectAreaList);
            this.setText(node_.getName());
        }
        if (nodeType == "SubjectArea") {
            this.setIcon(modeler.imageIconSubjectArea);
            this.setText(node_.getName());
        }
        if (nodeType == "RoleList") {
            this.setIcon(modeler.imageIconRoleList);
            this.setText(node_.getName());
        }
        if (nodeType == "Role") {
            this.setIcon(modeler.imageIconRole);
            this.setText(node_.getName());
        }
        if (nodeType == "Task") {
            this.setIcon(modeler.imageIconTask);
            XeadTreeNode parentNode = (XeadTreeNode) node_.getParent();
            this.setText(node_.getName() + "(" + parentNode.getName() + ")");
        }
        if (nodeType == "SubsystemList") {
            this.setIcon(modeler.imageIconSubsystemList);
            this.setText(node_.getName());
        }
        if (nodeType == "Subsystem") {
            this.setIcon(modeler.imageIconSubsystem);
            this.setText(node_.getName());
        }
        if (nodeType == "TableList") {
            this.setIcon(modeler.imageIconTableList);
            XeadTreeNode parentNode = (XeadTreeNode) node_.getParent();
            this.setText(parentNode.getName() + " " + res.getString("S3407"));
        }
        if (nodeType == "Table") {
            this.setIcon(modeler.imageIconTable);
            this.setText(node_.getName());
        }
        if (nodeType == "TableFieldList") {
            this.setIcon(modeler.imageIconFieldList);
            XeadTreeNode parentNode = (XeadTreeNode) node_.getParent();
            this.setText(parentNode.getName() + " " + res.getString("S613"));
        }
        if (nodeType == "TableField") {
            this.setIcon(modeler.imageIconField);
            XeadTreeNode parentNode = (XeadTreeNode) node_.getParent().getParent();
            this.setText(node_.getName() + "(" + parentNode.getName() + ")");
        }
        if (nodeType == "TableKeyList") {
            this.setIcon(modeler.imageIconKeyList);
            XeadTreeNode parentNode = (XeadTreeNode) node_.getParent();
            this.setText(parentNode.getName() + " " + res.getString("S3427"));
        }
        if (nodeType == "TableKey") {
            this.setIcon(modeler.imageIconKey);
            XeadTreeNode parentNode = (XeadTreeNode) node_.getParent().getParent();
            this.setText(node_.getName() + "(" + parentNode.getName() + ")");
        }
        if (nodeType == "FunctionList") {
            this.setIcon(modeler.imageIconFunctionList);
            XeadTreeNode parentNode = (XeadTreeNode) node_.getParent();
            this.setText(parentNode.getName() + " " + res.getString("S3433"));
        }
        if (nodeType == "Function") {
            this.setIcon(modeler.imageIconFunction);
            this.setText(node_.getName());
        }
        if (nodeType == "IOPanel") {
            this.setIcon(modeler.imageIconIOPanel);
            XeadTreeNode parentNode = (XeadTreeNode) node_.getParent();
            this.setText(node_.getName() + "(" + parentNode.getName() + ")");
        }
        if (nodeType == "IOTable") {
            this.setIcon(modeler.imageIconIOTable);
            XeadTreeNode parentNode = (XeadTreeNode) node_.getParent();
            this.setText(node_.getName() + "(" + parentNode.getName() + ")");
        }
        if (nodeType == "IOSpool") {
            this.setIcon(modeler.imageIconIOSpool);
            XeadTreeNode parentNode = (XeadTreeNode) node_.getParent();
            this.setText(node_.getName() + "(" + parentNode.getName() + ")");
        }
        if (nodeType == "IOWebPage") {
            this.setIcon(modeler.imageIconIOWebPage);
            XeadTreeNode parentNode = (XeadTreeNode) node_.getParent();
            this.setText(node_.getName() + "(" + parentNode.getName() + ")");
        }
        this.setToolTipText(res.getString("S2864") + this.getText() + res.getString("S2865"));
    }

    /**
     * ノードを取得する
     *
     * @return ツリーノード
     */
    public XeadTreeNode getNode() {
        return node_;
    }

    /**
     * アクションリスナー
     */
    private class NodeJumpButtonActionAdapter implements ActionListener {
        private final NodeJumpButton adaptee;

        NodeJumpButtonActionAdapter(NodeJumpButton adaptee) {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e) {
            adaptee.setBorder(BorderFactory.createLoweredBevelBorder());
            TreePath tp = new TreePath(node_.getPath());
            modeler.jTreeMain.setSelectionPath(tp);
            Rectangle nodePos = modeler.jTreeMain.getPathBounds(tp);
            if (nodePos != null) {
                modeler.jScrollPaneTreeView.getViewport().setViewPosition(new Point(0, nodePos.y));
            }
            modeler.setupContentsPaneForTreeNodeSelected(node_, false);
        }
    }
}
