package xeadModeler.view;

import javax.swing.*;
import javax.swing.undo.CannotUndoException;
import java.awt.event.ActionEvent;

/**
 * TextPaneのUndoアクション
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class TextPaneUndoAction extends AbstractAction {

    private static final long serialVersionUID = 1L;
    private javax.swing.undo.UndoManager textPaneUndoManager;
    private TextPaneRedoAction redoAction;

    public TextPaneUndoAction(javax.swing.undo.UndoManager textPaneUndoManager, TextPaneRedoAction redoAction) {
        super("Undo");
        this.textPaneUndoManager = textPaneUndoManager;
        this.redoAction = redoAction;
        setEnabled(false);
    }

    public void actionPerformed(ActionEvent e) {
        try {
            textPaneUndoManager.undo();
        } catch (CannotUndoException ex) {
        }
        update();
        redoAction.update();
    }

    public void update() {
        if (textPaneUndoManager.canUndo()) {
            setEnabled(true);
            putValue(Action.NAME, textPaneUndoManager.getUndoPresentationName());
        } else {
            setEnabled(false);
            putValue(Action.NAME, "Undo");
        }
    }
}
