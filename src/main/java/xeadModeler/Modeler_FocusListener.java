package xeadModeler;

import javax.swing.JTable;
import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * Focus listener for highlighting table selection
 */
public class Modeler_FocusListener implements FocusListener {

  String className;
  Color ACTIVE_COLOR = new Color(49, 106, 197);
  Color INACTIVE_COLOR = new Color(186, 186, 186);

  public void focusLost(FocusEvent e) {
    className = e.getComponent().getClass().getName();
    if (className.equals("javax.swing.JTable")) {
      JTable table = (JTable) e.getComponent();
      table.setSelectionBackground(INACTIVE_COLOR);
      table.setSelectionForeground(Color.black);
    }
  }

  public void focusGained(FocusEvent e) {
    className = e.getComponent().getClass().getName();
    if (className.equals("javax.swing.JTable")) {
      JTable table = (JTable) e.getComponent();
      table.setSelectionBackground(ACTIVE_COLOR);
      table.setSelectionForeground(Color.white);
    }
  }
}
