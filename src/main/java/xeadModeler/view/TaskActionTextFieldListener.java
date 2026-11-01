package xeadModeler.view;

import xeadModeler.Modeler;
import xeadModeler.Modeler.XeadTreeNode;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * タスクアクションテキストフィールドのリスナー
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class TaskActionTextFieldListener implements FocusListener {

    private Modeler modeler;

    public TaskActionTextFieldListener(Modeler modeler) {
        this.modeler = modeler;
    }

    public void focusLost(FocusEvent event) {
        XeadTreeNode currentTaskActionTreeNode = modeler.currentTaskActionTreeNode;
        if (currentTaskActionTreeNode != null) {
            currentTaskActionTreeNode.updateFieldsForTaskAction(modeler.currentMainTreeNode);
        }
    }

    public void focusGained(FocusEvent event) {
    }
}
