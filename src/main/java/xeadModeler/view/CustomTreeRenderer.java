package xeadModeler.view;

import xeadModeler.Modeler;
import xeadModeler.Modeler.XeadTreeNode;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

/**
 * カスタムツリーレンダラー
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class CustomTreeRenderer extends DefaultTreeCellRenderer {

    private static final long serialVersionUID = 1L;
    private Modeler modeler;

    public CustomTreeRenderer(Modeler modeler) {
        this.modeler = modeler;
    }

    public Component getTreeCellRendererComponent(JTree tree, Object treeNode, boolean selected,
        boolean expanded, boolean leaf, int row, boolean hasFocus) {
      //
      Component renderer = super.getTreeCellRendererComponent(tree, treeNode, selected, expanded,
          leaf, row, hasFocus);
      //
      if (hasFocus) {
        this.setBackgroundSelectionColor(Modeler.ACTIVE_COLOR);
      } else {
        this.setBackgroundSelectionColor(Modeler.INACTIVE_COLOR);
        this.setForeground(Color.black);
      }
      //
      //Set text expression of node//
      this.setText(((XeadTreeNode) treeNode).toString());
      //
      //Set graphic expression of node//
      String nodeType = ((XeadTreeNode) treeNode).getType();
      boolean isError = ((XeadTreeNode) treeNode).isError();
      boolean isExtRefered = ((XeadTreeNode) treeNode).isExtRefered();
      if (nodeType == "System") {
        this.setIcon(modeler.imageIconSystem);
      }
      if (nodeType == "SubjectAreaList") {
        this.setIcon(modeler.imageIconSubjectAreaList);
      }
      if (nodeType == "SubjectArea") {
        this.setIcon(modeler.imageIconSubjectArea);
      }
      if (nodeType == "RoleList") {
        this.setIcon(modeler.imageIconRoleList);
      }
      if (nodeType == "Role") {
        this.setIcon(modeler.imageIconRole);
      }
      if (nodeType == "Task") {
        this.setIcon(modeler.imageIconTask);
      }
      if (nodeType == "SubsystemList") {
        if (isError) {
          this.setIcon(modeler.imageIconSubsystemListErr);
        } else {
          this.setIcon(modeler.imageIconSubsystemList);
        }
      }
      if (nodeType == "Subsystem") {
        if (isError) {
          this.setIcon(modeler.imageIconSubsystemErr);
        } else {
          this.setIcon(modeler.imageIconSubsystem);
        }
      }
      if (nodeType == "TableList") {
        if (isError) {
          this.setIcon(modeler.imageIconTableListErr);
        } else {
          this.setIcon(modeler.imageIconTableList);
        }
      }
      if (nodeType == "Table") {
        if (isError) {
          this.setIcon(modeler.imageIconTableErr);
        } else {
          if (isExtRefered) {
            this.setIcon(modeler.imageIconTableSyn);
          } else {
            this.setIcon(modeler.imageIconTable);
          }
        }
      }
      if (nodeType == "TableFieldList") {
        this.setIcon(modeler.imageIconFieldList);
      }
      if (nodeType == "TableField") {
        this.setIcon(modeler.imageIconField);
      }
      if (nodeType == "TableKeyList") {
        this.setIcon(modeler.imageIconKeyList);
      }
      if (nodeType == "TableKey") {
        this.setIcon(modeler.imageIconKey);
      }
      if (nodeType == "FunctionList") {
        this.setIcon(modeler.imageIconFunctionList);
      }
      if (nodeType == "Function") {
        this.setIcon(modeler.imageIconFunction);
      }
      if (nodeType == "ReccursiveFunction") {
        this.setIcon(modeler.imageIconFunction);
      }
      if (nodeType == "IOPanel") {
        this.setIcon(modeler.imageIconIOPanel);
      }
      if (nodeType == "IOTable") {
        this.setIcon(modeler.imageIconIOTable);
      }
      if (nodeType == "IOSpool") {
        this.setIcon(modeler.imageIconIOSpool);
      }
      if (nodeType == "IOWebPage") {
        this.setIcon(modeler.imageIconIOWebPage);
      }
      if (nodeType == "TaskActionFirst") {
        this.setIcon(modeler.imageIconActionFirst);
      }
      if (nodeType == "TaskAction") {
        Object jsonNode = ((XeadTreeNode) treeNode).getJsonNode();
        String executeIf = "";
        if (jsonNode != null) {
          try {
            java.lang.reflect.Method method = jsonNode.getClass().getMethod("getExecuteIf");
            Object result = method.invoke(jsonNode);
            executeIf = result != null ? result.toString() : "";
          } catch (Exception e) {
            // Ignore if method not found
          }
        }
        if (executeIf.equals("")) {
          this.setIcon(modeler.imageIconAction);
        } else {
          this.setIcon(modeler.imageIconActionOptional);
        }
      }
      //
      return renderer;
    }
}
