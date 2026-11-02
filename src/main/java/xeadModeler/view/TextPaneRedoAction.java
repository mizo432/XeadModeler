package xeadModeler.view;

import javax.swing.*;
import javax.swing.undo.CannotRedoException;
import java.awt.event.ActionEvent;

/**
 * TextPaneのRedoアクション
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class TextPaneRedoAction extends AbstractAction {

    private static final long serialVersionUID = 1L;
    private javax.swing.undo.UndoManager textPaneUndoManager;
    private TextPaneUndoAction undoAction;

    public TextPaneRedoAction(javax.swing.undo.UndoManager textPaneUndoManager, TextPaneUndoAction undoAction) {
        super("Redo");
        this.textPaneUndoManager = textPaneUndoManager;
        this.undoAction = undoAction;
        setEnabled(false);
    }

    public void actionPerformed(ActionEvent e) {
        try {
            textPaneUndoManager.redo();
        } catch (CannotRedoException ex) {
        }
        update();
        undoAction.update();
    }

    public void update() {
        if (textPaneUndoManager.canRedo()) {
            setEnabled(true);
            putValue(Action.NAME, textPaneUndoManager.getRedoPresentationName());
        } else {
            setEnabled(false);
            putValue(Action.NAME, "Redo");
        }
    }
}
