package xeadModeler.service;

import xeadModeler.model.XeadDocumentModel;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * XMLデータ管理のサービス実装クラス
 * DOMドキュメントの操作を担当します。
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class XmlServiceImpl implements XmlService {
    
    private final XeadDocumentModel documentModel;
    
    public XmlServiceImpl(XeadDocumentModel documentModel) {
        this.documentModel = documentModel;
    }
    
    @Override
    public void arrangeOrderOfDomElement() {
        Element topElement = null;
        NodeList nodeList;
        
        // Function要素がある場合
        nodeList = documentModel.getDomDocument().getElementsByTagName("Function");
        if (nodeList.getLength() > 0) {
            topElement = (Element) nodeList.item(0);
            topElement = sortElementsByType("Relationship", topElement);
            topElement = sortElementsByType("Table", topElement);
            topElement = sortElementsByType("Subsystem", topElement);
            topElement = sortElementsByType("Task", topElement);
            topElement = sortElementsByType("Role", topElement);
            topElement = sortElementsByType("SubjectArea", topElement);
            topElement = sortElementsByType("MaintenanceLog", topElement);
        } else {
            // Relationship要素がある場合
            nodeList = documentModel.getDomDocument().getElementsByTagName("Relationship");
            if (nodeList.getLength() > 0) {
                topElement = (Element) nodeList.item(0);
                topElement = sortElementsByType("Table", topElement);
                topElement = sortElementsByType("Subsystem", topElement);
                topElement = sortElementsByType("Task", topElement);
                topElement = sortElementsByType("Role", topElement);
                topElement = sortElementsByType("SubjectArea", topElement);
                topElement = sortElementsByType("MaintenanceLog", topElement);
            } else {
                // Table要素がある場合
                nodeList = documentModel.getDomDocument().getElementsByTagName("Table");
                if (nodeList.getLength() > 0) {
                    topElement = (Element) nodeList.item(0);
                    topElement = sortElementsByType("Subsystem", topElement);
                    topElement = sortElementsByType("Task", topElement);
                    topElement = sortElementsByType("Role", topElement);
                    topElement = sortElementsByType("SubjectArea", topElement);
                    topElement = sortElementsByType("MaintenanceLog", topElement);
                } else {
                    // Subsystem要素がある場合
                    nodeList = documentModel.getDomDocument().getElementsByTagName("Subsystem");
                    if (nodeList.getLength() > 0) {
                        topElement = (Element) nodeList.item(0);
                        topElement = sortElementsByType("Task", topElement);
                        topElement = sortElementsByType("Role", topElement);
                        topElement = sortElementsByType("SubjectArea", topElement);
                        topElement = sortElementsByType("MaintenanceLog", topElement);
                    } else {
                        // Task要素がある場合
                        nodeList = documentModel.getDomDocument().getElementsByTagName("Task");
                        if (nodeList.getLength() > 0) {
                            topElement = (Element) nodeList.item(0);
                            topElement = sortElementsByType("Role", topElement);
                            topElement = sortElementsByType("SubjectArea", topElement);
                            topElement = sortElementsByType("MaintenanceLog", topElement);
                        } else {
                            // Role要素がある場合
                            nodeList = documentModel.getDomDocument().getElementsByTagName("Role");
                            if (nodeList.getLength() > 0) {
                                topElement = (Element) nodeList.item(0);
                                topElement = sortElementsByType("SubjectArea", topElement);
                                topElement = sortElementsByType("MaintenanceLog", topElement);
                            } else {
                                // SubjectArea要素がある場合
                                nodeList = documentModel.getDomDocument().getElementsByTagName("SubjectArea");
                                if (nodeList.getLength() > 0) {
                                    topElement = (Element) nodeList.item(0);
                                    topElement = sortElementsByType("MaintenanceLog", topElement);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    @Override
    public Element sortElementsByType(String type, Element topElement) {
        Element element;
        NodeList nodeList = documentModel.getDomDocument().getElementsByTagName(type);
        Element systemElement = getSystemElement();
        
        for (int i = nodeList.getLength() - 1; i >= 0; i--) {
            element = (Element) nodeList.item(i);
            systemElement.insertBefore(element, topElement);
            topElement = element;
        }
        return topElement;
    }
    
    @Override
    public void purgeInvalidSubsystemTable() {
        Element element;
        Element[] elementArray1 = new Element[1000];
        int arrayIndex1 = -1;
        Node parentDomNode;
        
        // 無効なSubsystemTableを配列に保存
        NodeList nodeList = documentModel.getDomDocument().getElementsByTagName("SubsystemTable");
        for (int i = 0; i < nodeList.getLength(); i++) {
            element = (Element) nodeList.item(i);
            String tableId = element.getAttribute("TableID");
            if (getSpecificElement("Table", tableId) == null) {
                arrayIndex1++;
                elementArray1[arrayIndex1] = element;
            }
        }
        
        // SubsystemTableを削除
        for (int i = 0; i <= arrayIndex1; i++) {
            parentDomNode = elementArray1[i].getParentNode();
            parentDomNode.removeChild(elementArray1[i]);
        }
    }
    
    @Override
    public void purgeInvalidSubsystemRelationship() {
        Element subsystemRelationshipElement, relationshipElement;
        NodeList subsystemRelationshipList, relationshipList;
        Element[] elementArray1 = new Element[1000];
        int arrayIndex1 = -1;
        Node parentDomNode;
        
        // 無効なSubsystemRelationshipを配列に保存
        subsystemRelationshipList = documentModel.getDomDocument().getElementsByTagName("SubsystemRelationship");
        for (int i = 0; i < subsystemRelationshipList.getLength(); i++) {
            subsystemRelationshipElement = (Element) subsystemRelationshipList.item(i);
            String relationshipId = subsystemRelationshipElement.getAttribute("RelationshipID");
            if (getSpecificElement("Relationship", relationshipId) == null) {
                arrayIndex1++;
                elementArray1[arrayIndex1] = subsystemRelationshipElement;
            }
        }
        
        // SubsystemRelationshipを削除
        for (int i = 0; i <= arrayIndex1; i++) {
            parentDomNode = elementArray1[i].getParentNode();
            parentDomNode.removeChild(elementArray1[i]);
        }
    }
    
    @Override
    public Element getSpecificElement(String type, String id) {
        NodeList nodeList = documentModel.getDomDocument().getElementsByTagName(type);
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);
            if (element.getAttribute("ID").equals(id)) {
                return element;
            }
        }
        return null;
    }
    
    @Override
    public NodeList getElementsByType(String type) {
        return documentModel.getDomDocument().getElementsByTagName(type);
    }
    
    @Override
    public Element getSystemElement() {
        return documentModel.getSystemElement();
    }
}
