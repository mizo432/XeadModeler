package xeadModeler.view;

import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;

/**
 * TextPaneのUndo/Redoリスナー
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class CustomUndoableEditListener implements UndoableEditListener {

    private TextPaneUndoAction undoAction;
    private TextPaneRedoAction redoAction;
    private javax.swing.undo.UndoManager textPaneUndoManager;

    public CustomUndoableEditListener(javax.swing.undo.UndoManager textPaneUndoManager,
                                       TextPaneUndoAction undoAction,
                                       TextPaneRedoAction redoAction) {
        this.textPaneUndoManager = textPaneUndoManager;
        this.undoAction = undoAction;
        this.redoAction = redoAction;
    }

    public void undoableEditHappened(UndoableEditEvent e) {
        textPaneUndoManager.addEdit(e.getEdit());
        undoAction.update();
        redoAction.update();
    }
}
