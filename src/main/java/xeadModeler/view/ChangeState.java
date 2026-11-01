package xeadModeler.view;

import xeadModeler.Modeler;

import javax.swing.*;
import java.util.ResourceBundle;

/**
 * 変更状態の管理クラス
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class ChangeState {

    boolean changed;
    private Modeler modeler;

    public ChangeState(Modeler modeler) {
        this.modeler = modeler;
        changed = false;
        modeler.jLabelChangeState.setText("");
    }

    public void setChanged(boolean state) {
        changed = state;
        if (changed) {
            modeler.jLabelChangeState.setText(Modeler.res.getString("S6450"));
        } else {
            modeler.jLabelChangeState.setText("");
        }
    }

    public boolean isChanged() {
        return changed;
    }
}
