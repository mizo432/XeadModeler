package xeadModeler.service;

import xeadModeler.model.XeadDocumentModel;
import xeadModeler.model.json.*;

import java.util.ArrayList;
import java.util.List;

/**
 * データ管理サービスの実装クラス
 * XeadSystemの操作を担当します。
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class DataServiceImpl implements DataService {
    
    private final XeadDocumentModel documentModel;
    
    public DataServiceImpl(XeadDocumentModel documentModel) {
        this.documentModel = documentModel;
    }
    
    @Override
    public void arrangeOrderOfDomElement() {
        // JSONベースではリストの順序を変更することで要素の順序を整列します
        XeadSystem system = documentModel.getXeadSystem();
        if (system == null) {
            return;
        }
        
        // sortKeyに基づいて各リストをソート
        List<Function> functions = system.getFunctions();
        List<Relationship> relationships = system.getRelationships();
        List<Table> tables = system.getTables();
        List<Subsystem> subsystems = system.getSubsystems();
        List<Task> tasks = system.getTasks();
        List<Role> roles = system.getRoles();
        List<SubjectArea> subjectAreas = system.getSubjectAreas();
        List<MaintenanceLog> maintenanceLogs = system.getMaintenanceLogs();
        
        // sortKeyでソート（実装は必要に応じて追加）
    }
    
    @Override
    public void purgeInvalidSubsystemTable() {
        XeadSystem system = documentModel.getXeadSystem();
        if (system == null) {
            return;
        }
        
        // 無効なSubsystemTableを削除
        for (Subsystem subsystem : system.getSubsystems()) {
            if (subsystem.getSubsystemTables() != null) {
                List<SubsystemTable> toRemove = new ArrayList<>();
                for (SubsystemTable st : subsystem.getSubsystemTables()) {
                    if (getSpecificElement("Table", st.getTableId()) == null) {
                        toRemove.add(st);
                    }
                }
                subsystem.getSubsystemTables().removeAll(toRemove);
            }
        }
    }
    
    @Override
    public void purgeInvalidSubsystemRelationship() {
        XeadSystem system = documentModel.getXeadSystem();
        if (system == null) {
            return;
        }
        
        // 無効なSubsystemRelationshipを削除
        for (Subsystem subsystem : system.getSubsystems()) {
            if (subsystem.getSubsystemRelationships() != null) {
                List<SubsystemRelationship> toRemove = new ArrayList<>();
                for (SubsystemRelationship sr : subsystem.getSubsystemRelationships()) {
                    if (getSpecificElement("Relationship", sr.getRelationshipId()) == null) {
                        toRemove.add(sr);
                    }
                }
                subsystem.getSubsystemRelationships().removeAll(toRemove);
            }
        }
    }
    
    @Override
    public Object getSpecificElement(String type, String id) {
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
            case "Department":
                for (Department department : system.getDepartments()) {
                    if (department.getId().equals(id)) {
                        return department;
                    }
                }
                break;
            case "TableType":
                for (TableType tableType : system.getTableTypes()) {
                    if (tableType.getId().equals(id)) {
                        return tableType;
                    }
                }
                break;
            case "DataType":
                for (DataType dataType : system.getDataTypes()) {
                    if (dataType.getId().equals(id)) {
                        return dataType;
                    }
                }
                break;
            case "FunctionType":
                for (FunctionType functionType : system.getFunctionTypes()) {
                    if (functionType.getId().equals(id)) {
                        return functionType;
                    }
                }
                break;
            case "TaskType":
                for (TaskType taskType : system.getTaskTypes()) {
                    if (taskType.getId().equals(id)) {
                        return taskType;
                    }
                }
                break;
            case "Terms":
                for (Terms terms : system.getTerms()) {
                    if (terms.getId().equals(id)) {
                        return terms;
                    }
                }
                break;
        }
        return null;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public List<?> getElementsByType(String type) {
        XeadSystem system = documentModel.getXeadSystem();
        if (system == null) {
            return new ArrayList<>();
        }
        
        switch (type) {
            case "Table":
                return system.getTables();
            case "Relationship":
                return system.getRelationships();
            case "Subsystem":
                return system.getSubsystems();
            case "Function":
                return system.getFunctions();
            case "Task":
                return system.getTasks();
            case "Role":
                return system.getRoles();
            case "SubjectArea":
                return system.getSubjectAreas();
            case "MaintenanceLog":
                return system.getMaintenanceLogs();
            case "Department":
                return system.getDepartments();
            case "TableType":
                return system.getTableTypes();
            case "DataType":
                return system.getDataTypes();
            case "FunctionType":
                return system.getFunctionTypes();
            case "TaskType":
                return system.getTaskTypes();
            case "Terms":
                return system.getTerms();
            default:
                return new ArrayList<>();
        }
    }
    
    @Override
    public XeadSystem getSystemElement() {
        return documentModel.getXeadSystem();
    }
}
