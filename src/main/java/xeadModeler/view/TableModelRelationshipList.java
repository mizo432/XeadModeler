package xeadModeler.view;

import xeadModeler.Modeler;
import xeadModeler.Modeler.XeadTreeNode;

import javax.swing.table.DefaultTableModel;

/**
 * RelationshipList用のTableModel
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class TableModelRelationshipList extends DefaultTableModel {

    private static final long serialVersionUID = 1L;
    private Modeler modeler;

    public TableModelRelationshipList(Modeler modeler) {
        this.modeler = modeler;
    }

    public boolean isCellEditable(int row, int col) {
        boolean editable = false;
        if (col == 6) {
            XeadTreeNode tableNode = (XeadTreeNode) modeler.tableKeyNode.getParent().getParent();
            String tableID = tableNode.getElement().getAttribute("ID");
            String tableKeyID = modeler.tableKeyNode.getElement().getAttribute("ID");
            TableRowNumber tableRowNumber = (TableRowNumber) getValueAt(row, 0);
            if (tableRowNumber.getElement().getAttribute("Type").equals("SUBTYPE")) {
                editable = true;
                modeler.informationOnThisPageChanged = true;
            }
            if (tableRowNumber.getElement().getAttribute("Type").equals("REFFER")) {
                if (tableRowNumber.getElement().getAttribute("Table1ID").equals(tableID)
                    && tableRowNumber.getElement().getAttribute("TableKey1ID").equals(tableKeyID)) {
                    editable = false;
                }
                if (tableRowNumber.getElement().getAttribute("Table2ID").equals(tableID)
                    && tableRowNumber.getElement().getAttribute("TableKey2ID").equals(tableKeyID)) {
                    editable = true;
                    modeler.informationOnThisPageChanged = true;
                }
            }
            if (tableRowNumber.getElement().getAttribute("Type").equals("FAMILY")) {
                editable = false;
            }
        }
        return editable;
    }
}
