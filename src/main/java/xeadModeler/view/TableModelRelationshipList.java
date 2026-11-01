package xeadModeler.view;

import xeadModeler.Modeler;
import xeadModeler.Modeler.XeadTreeNode;
import xeadModeler.model.json.Table;
import xeadModeler.model.json.TableKey;
import xeadModeler.model.json.Relationship;

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
            Table table = (Table) tableNode.getPojo();
            TableKey tableKey = (TableKey) modeler.tableKeyNode.getPojo();
            String tableID = table.getId();
            String tableKeyID = tableKey.getId();
            TableRowNumber tableRowNumber = (TableRowNumber) getValueAt(row, 0);
            Relationship relationship = (Relationship) tableRowNumber.getPojo();
            if (relationship.getType() != null && relationship.getType().equals("SUBTYPE")) {
                editable = true;
                modeler.informationOnThisPageChanged = true;
            }
            if (relationship.getType() != null && relationship.getType().equals("REFFER")) {
                if (relationship.getTable1Id() != null && relationship.getTable1Id().equals(tableID)
                    && relationship.getTableKey1Id() != null && relationship.getTableKey1Id().equals(tableKeyID)) {
                    editable = false;
                }
                if (relationship.getTable2Id() != null && relationship.getTable2Id().equals(tableID)
                    && relationship.getTableKey2Id() != null && relationship.getTableKey2Id().equals(tableKeyID)) {
                    editable = true;
                    modeler.informationOnThisPageChanged = true;
                }
            }
            if (relationship.getType() != null && relationship.getType().equals("FAMILY")) {
                editable = false;
            }
        }
        return editable;
    }
}
