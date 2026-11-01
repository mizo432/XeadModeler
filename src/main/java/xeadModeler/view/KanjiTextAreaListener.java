package xeadModeler.view;

import xeadModeler.Modeler;

import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Locale;
import javax.swing.text.JTextComponent;

/**
 * 日本語入力用TextAreaのFocusListener
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class KanjiTextAreaListener implements FocusListener {

    private Modeler modeler;

    public KanjiTextAreaListener(Modeler modeler) {
        this.modeler = modeler;
    }

    public void focusLost(FocusEvent event) {
        focusLostHandler(event.getComponent());
    }

    public void focusGained(FocusEvent event) {
        focusGainedHandler(event.getComponent());
    }

    private void focusGainedHandler(Component component) {
        if (component instanceof JTextComponent) {
            JTextComponent textComponent = (JTextComponent) component;
            textComponent.getInputContext().setCharacterSubsets(null);
            String lang = Locale.getDefault().getLanguage();
            if (lang.equals("ja")) {
                Character.Subset[] subsets = new Character.Subset[]{java.awt.im.InputSubset.KANJI};
                textComponent.getInputContext().setCharacterSubsets(subsets);
            }
            if (lang.equals("zh")) {
                Character.Subset[] subsets = new Character.Subset[]{
                    java.awt.im.InputSubset.TRADITIONAL_HANZI};
                textComponent.getInputContext().setCharacterSubsets(subsets);
            }
        }
    }

    private void focusLostHandler(Component component) {
        if (component instanceof JTextComponent) {
            JTextComponent textComponent = (JTextComponent) component;
            textComponent.getInputContext().setCharacterSubsets(null);
        }
    }
}
