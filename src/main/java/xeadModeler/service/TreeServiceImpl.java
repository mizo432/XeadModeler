package xeadModeler.service;

import xeadModeler.model.XeadDocumentModel;
import xeadModeler.model.json.*;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ツリー操作サービスの実装クラス
 * ツリーモデルの初期化、ノードの操作などの機能を担当します。
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
@Service
public class TreeServiceImpl implements TreeService {
    
    private XeadDocumentModel documentModel;
    
    public TreeServiceImpl(XeadDocumentModel documentModel) {
        this.documentModel = documentModel;
    }
    
    @Override
    public void initializeTreeModel(XeadDocumentModel documentModel) throws Exception {
        // ツリーモデルの初期化ロジック
        // Modeler.javaのsetupMainTreeModelWithCurrentFileNameメソッドから抽出
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public Object addChildNode(String nodeType, String name, String alias) {
        XeadSystem system = documentModel.getXeadSystem();
        if (system == null) {
            return null;
        }
        
        String id = generateId(nodeType);
        switch (nodeType) {
            case "Table":
                Table table = new Table();
                table.setId(id);
                table.setName(name);
                table.setDescriptions(alias);
                system.getTables().add(table);
                return table;
            case "Relationship":
                Relationship relationship = new Relationship();
                relationship.setId(id);
                relationship.setName(name);
                relationship.setDescriptions(alias);
                system.getRelationships().add(relationship);
                return relationship;
            case "Subsystem":
                Subsystem subsystem = new Subsystem();
                subsystem.setId(id);
                subsystem.setName(name);
                subsystem.setDescriptions(alias);
                system.getSubsystems().add(subsystem);
                return subsystem;
            case "Function":
                Function function = new Function();
                function.setId(id);
                function.setName(name);
                function.setDescriptions(alias);
                system.getFunctions().add(function);
                return function;
            case "Task":
                Task task = new Task();
                task.setId(id);
                task.setName(name);
                task.setDescriptions(alias);
                system.getTasks().add(task);
                return task;
            case "Role":
                Role role = new Role();
                role.setId(id);
                role.setName(name);
                role.setDescriptions(alias);
                system.getRoles().add(role);
                return role;
            case "SubjectArea":
                SubjectArea subjectArea = new SubjectArea();
                subjectArea.setId(id);
                subjectArea.setName(name);
                subjectArea.setDescriptions(alias);
                system.getSubjectAreas().add(subjectArea);
                return subjectArea;
            case "MaintenanceLog":
                MaintenanceLog maintenanceLog = new MaintenanceLog();
                maintenanceLog.setId(id);
                maintenanceLog.setHeader(name);
                maintenanceLog.setDescriptions(alias);
                system.getMaintenanceLogs().add(maintenanceLog);
                return maintenanceLog;
            default:
                return null;
        }
    }
    
    @Override
    public void removeNode(String type, String id) {
        XeadSystem system = documentModel.getXeadSystem();
        if (system == null) {
            return;
        }
        
        switch (type) {
            case "Table":
                system.getTables().removeIf(t -> t.getId().equals(id));
                break;
            case "Relationship":
                system.getRelationships().removeIf(r -> r.getId().equals(id));
                break;
            case "Subsystem":
                system.getSubsystems().removeIf(s -> s.getId().equals(id));
                break;
            case "Function":
                system.getFunctions().removeIf(f -> f.getId().equals(id));
                break;
            case "Task":
                system.getTasks().removeIf(t -> t.getId().equals(id));
                break;
            case "Role":
                system.getRoles().removeIf(r -> r.getId().equals(id));
                break;
            case "SubjectArea":
                system.getSubjectAreas().removeIf(s -> s.getId().equals(id));
                break;
            case "MaintenanceLog":
                system.getMaintenanceLogs().removeIf(m -> m.getId().equals(id));
                break;
        }
    }
    
    @Override
    public void updateNode(String type, String id, String name, String alias) {
        Object node = findNode(type, id);
        if (node == null) {
            return;
        }
        
        switch (type) {
            case "Table":
                ((Table) node).setName(name);
                ((Table) node).setDescriptions(alias);
                break;
            case "Relationship":
                ((Relationship) node).setName(name);
                ((Relationship) node).setDescriptions(alias);
                break;
            case "Subsystem":
                ((Subsystem) node).setName(name);
                ((Subsystem) node).setDescriptions(alias);
                break;
            case "Function":
                ((Function) node).setName(name);
                ((Function) node).setDescriptions(alias);
                break;
            case "Task":
                ((Task) node).setName(name);
                ((Task) node).setDescriptions(alias);
                break;
            case "Role":
                ((Role) node).setName(name);
                ((Role) node).setDescriptions(alias);
                break;
            case "SubjectArea":
                ((SubjectArea) node).setName(name);
                ((SubjectArea) node).setDescriptions(alias);
                break;
            case "MaintenanceLog":
                ((MaintenanceLog) node).setHeader(name);
                ((MaintenanceLog) node).setDescriptions(alias);
                break;
        }
    }
    
    @Override
    public Object findNode(String type, String id) {
        XeadSystem system = documentModel.getXeadSystem();
        if (system == null) {
            return null;
        }
        
        switch (type) {
            case "Table":
                for (Table table : system.getTables()) {
                    if (table.getId().equals(id)) {
                        return table;
                    }
                }
                break;
            case "Relationship":
                for (Relationship relationship : system.getRelationships()) {
                    if (relationship.getId().equals(id)) {
                        return relationship;
                    }
                }
                break;
            case "Subsystem":
                for (Subsystem subsystem : system.getSubsystems()) {
                    if (subsystem.getId().equals(id)) {
                        return subsystem;
                    }
                }
                break;
            case "Function":
                for (Function function : system.getFunctions()) {
                    if (function.getId().equals(id)) {
                        return function;
                    }
                }
                break;
            case "Task":
                for (Task task : system.getTasks()) {
                    if (task.getId().equals(id)) {
                        return task;
                    }
                }
                break;
            case "Role":
                for (Role role : system.getRoles()) {
                    if (role.getId().equals(id)) {
                        return role;
                    }
                }
                break;
            case "SubjectArea":
                for (SubjectArea subjectArea : system.getSubjectAreas()) {
                    if (subjectArea.getId().equals(id)) {
                        return subjectArea;
                    }
                }
                break;
            case "MaintenanceLog":
                for (MaintenanceLog maintenanceLog : system.getMaintenanceLogs()) {
                    if (maintenanceLog.getId().equals(id)) {
                        return maintenanceLog;
                    }
                }
                break;
        }
        return null;
    }
    
    /**
     * ノードIDを生成します。
     * @param nodeType ノードタイプ
     * @return 生成されたID
     */
    private String generateId(String nodeType) {
        XeadSystem system = documentModel.getXeadSystem();
        if (system == null) {
            return nodeType + "1";
        }
        
        List<?> nodeList;
        switch (nodeType) {
            case "Table":
                nodeList = system.getTables();
                break;
            case "Relationship":
                nodeList = system.getRelationships();
                break;
            case "Subsystem":
                nodeList = system.getSubsystems();
                break;
            case "Function":
                nodeList = system.getFunctions();
                break;
            case "Task":
                nodeList = system.getTasks();
                break;
            case "Role":
                nodeList = system.getRoles();
                break;
            case "SubjectArea":
                nodeList = system.getSubjectAreas();
                break;
            case "MaintenanceLog":
                nodeList = system.getMaintenanceLogs();
                break;
            default:
                return nodeType + "1";
        }
        
        int maxId = 0;
        for (Object obj : nodeList) {
            String id = null;
            if (obj instanceof Table) id = ((Table) obj).getId();
            else if (obj instanceof Relationship) id = ((Relationship) obj).getId();
            else if (obj instanceof Subsystem) id = ((Subsystem) obj).getId();
            else if (obj instanceof Function) id = ((Function) obj).getId();
            else if (obj instanceof Task) id = ((Task) obj).getId();
            else if (obj instanceof Role) id = ((Role) obj).getId();
            else if (obj instanceof SubjectArea) id = ((SubjectArea) obj).getId();
            else if (obj instanceof MaintenanceLog) id = ((MaintenanceLog) obj).getId();
            
            if (id != null && !id.isEmpty()) {
                try {
                    int numericId = Integer.parseInt(id.replace(nodeType, ""));
                    if (numericId > maxId) {
                        maxId = numericId;
                    }
                } catch (NumberFormatException e) {
                    // 数値として解析できない場合はスキップ
                }
            }
        }
        return nodeType + (maxId + 1);
    }
}
