package xeadModeler.view;

import xeadModeler.Modeler;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

/**
 * TabbedPaneの変更リスナー
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class TabbedPaneChangeListener implements ChangeListener {

    private Modeler modeler;

    public TabbedPaneChangeListener(Modeler modeler) {
        this.modeler = modeler;
    }

    public void stateChanged(ChangeEvent event) {
        try {
            modeler.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            if (modeler.currentMainTreeNode != null) {
                modeler.currentMainTreeNode.updateFields();
                modeler.currentMainTreeNode.activateContentsPane();
            }
        } finally {
            modeler.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }
}
