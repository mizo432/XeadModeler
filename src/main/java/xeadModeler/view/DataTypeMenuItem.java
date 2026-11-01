package xeadModeler.view;

import xeadModeler.Modeler;
import xeadModeler.Modeler.XeadTreeNode;

import javax.swing.*;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * データタイプメニュー項目クラス
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class DataTypeMenuItem extends JMenuItem {

    private static final long serialVersionUID = 1L;
    String dataTypeID;
    String dataTypeName;
    Element dataTypeElement;
    private Modeler modeler;

    public DataTypeMenuItem(Modeler modeler, Element element) {
        super();
        this.modeler = modeler;
        String wrkStr1 = "";
        String wrkStr2 = "";
        String zenkaku = "";
        dataTypeElement = element;
        if (dataTypeElement.getAttribute("BasicType").equals("SignedNumber")
            || dataTypeElement.getAttribute("BasicType").equals("UnsignedNumber")) {
            wrkStr1 =
                dataTypeElement.getAttribute("Length") + "." + dataTypeElement.getAttribute("Decimal");
        } else {
            wrkStr1 = dataTypeElement.getAttribute("Length");
        }
        zenkaku = getZenkakuOfHankaku(wrkStr1);
        if (dataTypeElement.getAttribute("Name").contains(wrkStr1)
            || dataTypeElement.getAttribute("SortKey").contains(wrkStr1)
            || dataTypeElement.getAttribute("Name").contains(zenkaku)) {
            wrkStr2 = dataTypeElement.getAttribute("Name");
        } else {
            wrkStr2 = dataTypeElement.getAttribute("Name") + "(" + wrkStr1 + ")";
        }
        if (wrkStr2.startsWith(dataTypeElement.getAttribute("SortKey"))) {
            setText(wrkStr2);
        } else {
            setText(dataTypeElement.getAttribute("SortKey") + " " + wrkStr2);
        }
        addActionListener(new dataTypeMenuItemActionListener());
    }

    private String getZenkakuOfHankaku(String hankaku) {
        String zenkaku = "";
        for (int i = 0; i < hankaku.length(); i++) {
            char c = hankaku.charAt(i);
            if (c >= '0' && c <= '9') {
                zenkaku += (char) (c + 0xFEE0);
            } else if (c >= 'A' && c <= 'Z') {
                zenkaku += (char) (c + 0xFEE0);
            } else if (c >= 'a' && c <= 'z') {
                zenkaku += (char) (c + 0xFEE0);
            } else {
                zenkaku += c;
            }
        }
        return zenkaku;
    }

    class dataTypeMenuItemActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            XeadTreeNode tableNode = (XeadTreeNode) modeler.currentMainTreeNode.getParent().getParent();
            NodeList nodeList1, nodeList2;
            NodeList nodeList3 = modeler.domDocument.getElementsByTagName("Relationship");
            Element element1, element2, element3;
            boolean relationshipAlreadyExist, thisIsComposedOfKey;
            String tableID = tableNode.getElement().getAttribute("ID");
            String tableFieldID = modeler.currentMainTreeNode.getElement().getAttribute("ID");
            //
            if (!dataTypeElement.getAttribute("ID").equals(modeler.tableFieldDataTypeID)) {
                modeler.tableFieldDataTypeID = dataTypeElement.getAttribute("ID");
                modeler.jTextFieldTableFieldDataType.setText(getText());
                if (modeler.jTextFieldTableFieldAlias.getText().equals("")) {
                    NodeList fieldList = modeler.domDocument.getElementsByTagName("TableField");
                    String fieldAlias = "";
                    boolean isAllTheSameAlias = true;
                    for (int i = 0; i < fieldList.getLength(); i++) {
                        element1 = (Element) fieldList.item(i);
                        if (element1.getAttribute("DataTypeID").equals(modeler.tableFieldDataTypeID)) {
                            if (!element1.getAttribute("Alias").equals("")
                                && !fieldAlias.equals("")
                                && !element1.getAttribute("Alias").equals(fieldAlias)) {
                                isAllTheSameAlias = false;
                            }
                            if (!element1.getAttribute("Alias").equals("")) {
                                fieldAlias = element1.getAttribute("Alias");
                            }
                        }
                    }
                    if (isAllTheSameAlias) {
                        modeler.jTextFieldTableFieldAlias.setText(fieldAlias);
                    }
                }
                //
                nodeList1 = tableNode.getElement().getElementsByTagName("TableKey");
                for (int i = 0; i < nodeList1.getLength(); i++) {
                    element1 = (Element) nodeList1.item(i);
                    thisIsComposedOfKey = false;
                    nodeList2 = element1.getElementsByTagName("TableKeyField");
                    for (int j = 0; j < nodeList2.getLength(); j++) {
                        element2 = (Element) nodeList2.item(j);
                        if (element2.getAttribute("FieldID").equals(tableFieldID)) {
                            thisIsComposedOfKey = true;
                            break;
                        }
                    }
                    if (thisIsComposedOfKey) {
                        relationshipAlreadyExist = false;
                        String tableKeyID = element1.getAttribute("ID");
                        for (int j = 0; j < nodeList3.getLength(); j++) {
                            element3 = (Element) nodeList3.item(j);
                            if (element3.getAttribute("Table1ID").equals(tableID) && element3.getAttribute(
                                "TableKey1ID").equals(tableKeyID)) {
                                relationshipAlreadyExist = true;
                                break;
                            }
                            if (element3.getAttribute("Table2ID").equals(tableID) && element3.getAttribute(
                                "TableKey2ID").equals(tableKeyID)) {
                                relationshipAlreadyExist = true;
                                break;
                            }
                        }
                        if (relationshipAlreadyExist) {
                            if (!modeler.isWarningAcceptedForDataTypeUnmatch) {
                                Object[] bts = {"OK", modeler.res.getString("S5763")};
                                int rtn = JOptionPane.showOptionDialog(modeler.jButtonTableFieldDataTypeChange,
                                    modeler.res.getString("S1920") + "\n"
                                        + modeler.res.getString("S1922") + "\n" + modeler.res.getString("S1924"),
                                    modeler.res.getString("S1925"), JOptionPane.DEFAULT_OPTION,
                                    JOptionPane.INFORMATION_MESSAGE, null, bts, bts[0]);
                                if (rtn == 1) {
                                    modeler.isWarningAcceptedForDataTypeUnmatch = true;
                                }
                            }
                            break;
                        }
                    }
                }
            }
        }
    }
}
