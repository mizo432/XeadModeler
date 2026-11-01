package xeadModeler;

import javax.swing.JTextField;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Locale;

/**
 * Class of KanjiTextField
 */
public class KanjiTextField extends JTextField {

  private static final long serialVersionUID = 1L;

  public KanjiTextField() {
    super();
    addFocusListener(new KanjiTextFieldListener());
  }

  class KanjiTextFieldListener implements FocusListener {

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
      Character.Subset[] subsets = new Character.Subset[]{java.awt.im.InputSubset.KANJI};
      getInputContext().setCharacterSubsets(subsets);
    }
    if (lang.equals("zh")) {
      Character.Subset[] subsets = new Character.Subset[]{
          java.awt.im.InputSubset.TRADITIONAL_HANZI};
      getInputContext().setCharacterSubsets(subsets);
    }
  }

  private void focusLostHandler() {
    getInputContext().setCharacterSubsets(null);
  }
}
