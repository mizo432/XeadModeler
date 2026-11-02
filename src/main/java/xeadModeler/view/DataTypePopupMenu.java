package xeadModeler.view;

import xeadModeler.Modeler;
import xeadModeler.model.json.DataType;
import xeadModeler.model.XeadElement;
import xeadModeler.view.SortableDomElementListModel;

import javax.swing.*;
import java.util.List;

/**
 * Class of DataTypeMenu
 */
public class DataTypePopupMenu extends JPopupMenu {

    private static final long serialVersionUID = 1L;

    private Modeler modeler;
    private SortableDomElementListModel sortableDomElementListModel;

    public DataTypePopupMenu(Modeler modeler) {
        super();
        this.modeler = modeler;
        this.sortableDomElementListModel = modeler.sortableDomElementListModel;
    }

    public void initializeMenuItem(List<DataType> dataTypeList) {
        String sortKeyOfDataType = "";
        String first2BytesOfsortKey = "";
        JMenu currentMenu = null;
        //
        this.removeAll();
        sortableDomElementListModel.removeAllElements();
        for (DataType dt : dataTypeList) {
            sortableDomElementListModel.addElementFromPojo(dt);
        }
        sortableDomElementListModel.sortElements();
        for (int i = 0; i < sortableDomElementListModel.getSize(); i++) {
            DataType dt = (DataType) ((XeadElement) sortableDomElementListModel.getElementAt(i)).getPojo();
            sortKeyOfDataType = (dt.getSortKey() != null ? dt.getSortKey() : "") + "  ";
            if (!first2BytesOfsortKey.equals(sortKeyOfDataType.substring(0, Math.min(2, sortKeyOfDataType.length()))) || i == 0) {
                first2BytesOfsortKey = sortKeyOfDataType.substring(0, Math.min(2, sortKeyOfDataType.length()));
                currentMenu = new JMenu(first2BytesOfsortKey);
                this.add(currentMenu);
            }
            currentMenu.add(new DataTypeMenuItem(modeler, dt));
        }
        this.add(modeler.getJMenuItemAddDataType());
    }
}
