package xeadModeler.view;

import xeadModeler.Modeler;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * チェックボックスヘッダーのリスナー
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class CheckBoxHeaderListener implements ItemListener {

    private CheckBoxHeaderRenderer rendererComponent_ = null;
    private Modeler modeler;

    public CheckBoxHeaderListener(Modeler modeler) {
        this.modeler = modeler;
    }

    public void setRenderer(CheckBoxHeaderRenderer rendererComponent) {
        rendererComponent_ = rendererComponent;
    }

    public void itemStateChanged(ItemEvent e) {
        if (rendererComponent_ != null) {
            for (int i = 0; i < modeler.jTableTableFieldList.getRowCount(); i++) {
                if (rendererComponent_.isSelected()) {
                    modeler.jTableTableFieldList.setValueAt(Boolean.TRUE, i, 3);
                } else {
                    modeler.jTableTableFieldList.setValueAt(Boolean.FALSE, i, 3);
                }
            }
        }
    }
}
