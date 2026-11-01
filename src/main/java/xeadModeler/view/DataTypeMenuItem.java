package xeadModeler.view;

import xeadModeler.Modeler;
import xeadModeler.Modeler.XeadTreeNode;
import xeadModeler.model.json.XeadSystem;
import xeadModeler.model.json.Relationship;
import xeadModeler.model.json.Table;
import xeadModeler.model.json.TableField;
import xeadModeler.model.json.TableKey;
import xeadModeler.model.json.TableKeyField;
import xeadModeler.model.json.DataType;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

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
    DataType dataTypeObj;
    private Modeler modeler;

    public DataTypeMenuItem(Modeler modeler, DataType dt) {
        super();
        this.modeler = modeler;
        String wrkStr1 = "";
        String wrkStr2 = "";
        String zenkaku = "";
        dataTypeObj = dt;
        String basicType = dt.getBasicType() != null ? dt.getBasicType() : "";
        if (basicType.equals("SignedNumber")
            || basicType.equals("UnsignedNumber")) {
            wrkStr1 =
                (dt.getLength() != null ? dt.getLength() : 0) + "." + (dt.getDecimal() != null ? dt.getDecimal() : 0);
        } else {
            wrkStr1 = String.valueOf(dt.getLength() != null ? dt.getLength() : 0);
        }
        zenkaku = getZenkakuOfHankaku(wrkStr1);
        String name = dt.getName() != null ? dt.getName() : "";
        String sortKey = dt.getSortKey() != null ? dt.getSortKey() : "";
        if (name.contains(wrkStr1)
            || sortKey.contains(wrkStr1)
            || name.contains(zenkaku)) {
            wrkStr2 = name;
        } else {
            wrkStr2 = name + "(" + wrkStr1 + ")";
        }
        if (wrkStr2.startsWith(sortKey)) {
            setText(wrkStr2);
        } else {
            setText(sortKey + " " + wrkStr2);
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
            XeadSystem xeadSystem = modeler.getXeadDocumentModel().getXeadSystem();
            List<Relationship> relationshipList = xeadSystem != null ? xeadSystem.getRelationships() : null;
            Table table = (Table) tableNode.getPojo();
            TableField tableField = (TableField) modeler.currentMainTreeNode.getPojo();
            boolean relationshipAlreadyExist, thisIsComposedOfKey;
            String tableID = table.getId();
            String tableFieldID = tableField.getId();
            //
            if (!dataTypeObj.getId().equals(modeler.tableFieldDataTypeID)) {
                modeler.tableFieldDataTypeID = dataTypeObj.getId();
                modeler.jTextFieldTableFieldDataType.setText(getText());
                if (modeler.jTextFieldTableFieldAlias.getText().equals("")) {
                    List<TableField> allFields = new java.util.ArrayList<>();
                    if (xeadSystem != null) {
                        for (Table t : xeadSystem.getTables()) {
                            if (t.getTableFields() != null) {
                                allFields.addAll(t.getTableFields());
                            }
                        }
                    }
                    String fieldAlias = "";
                    boolean isAllTheSameAlias = true;
                    for (TableField tf : allFields) {
                        if (tf.getDataTypeId() != null && tf.getDataTypeId().equals(modeler.tableFieldDataTypeID)) {
                            String alias = tf.getAlias() != null ? tf.getAlias() : "";
                            if (!alias.equals("")
                                && !fieldAlias.equals("")
                                && !alias.equals(fieldAlias)) {
                                isAllTheSameAlias = false;
                            }
                            if (!alias.equals("")) {
                                fieldAlias = alias;
                            }
                        }
                    }
                    if (isAllTheSameAlias) {
                        modeler.jTextFieldTableFieldAlias.setText(fieldAlias);
                    }
                }
                //
                if (table.getTableKeys() != null) {
                    for (TableKey tableKey : table.getTableKeys()) {
                        thisIsComposedOfKey = false;
                        if (tableKey.getTableKeyFields() != null) {
                            for (TableKeyField tkf : tableKey.getTableKeyFields()) {
                                if (tkf.getFieldId() != null && tkf.getFieldId().equals(tableFieldID)) {
                                    thisIsComposedOfKey = true;
                                    break;
                                }
                            }
                        }
                        if (thisIsComposedOfKey) {
                            relationshipAlreadyExist = false;
                            String tableKeyID = tableKey.getId();
                            if (relationshipList != null) {
                                for (Relationship rel : relationshipList) {
                                    if (rel.getTable1Id() != null && rel.getTable1Id().equals(tableID) && rel.getTableKey1Id() != null && rel.getTableKey1Id().equals(tableKeyID)) {
                                        relationshipAlreadyExist = true;
                                        break;
                                    }
                                    if (rel.getTable2Id() != null && rel.getTable2Id().equals(tableID) && rel.getTableKey2Id() != null && rel.getTableKey2Id().equals(tableKeyID)) {
                                        relationshipAlreadyExist = true;
                                        break;
                                    }
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
}
