package xeadModeler.view;

import xeadModeler.Modeler;

import javax.swing.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.im.InputSubset;
import java.util.Locale;

/**
 * 日本語入力用のTextAreaコンポーネント
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class KanjiTextArea extends JTextArea {

    private static final long serialVersionUID = 1L;
    private Modeler modeler;

    public KanjiTextArea(Modeler modeler) {
        super();
        this.modeler = modeler;
        addFocusListener(new KanjiTextAreaListener());
    }

    class KanjiTextAreaListener implements FocusListener {

        public void focusLost(FocusEvent event) {
            focusLostHandler();
        }

        public void focusGained(FocusEvent event) {
            focusGainedHandler();
        }
    }

    private void focusGainedHandler() {
        getInputContext().setCharacterSubsets(null);
        String lang = Locale.getDefault().getLanguage();
        if (lang.equals("ja")) {
            Character.Subset[] subsets = new Character.Subset[]{InputSubset.KANJI};
            getInputContext().setCharacterSubsets(subsets);
        }
        if (lang.equals("zh")) {
            Character.Subset[] subsets = new Character.Subset[]{
                InputSubset.TRADITIONAL_HANZI};
            getInputContext().setCharacterSubsets(subsets);
        }
    }

    private void focusLostHandler() {
        getInputContext().setCharacterSubsets(null);
    }
}
