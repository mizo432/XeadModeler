package xeadModeler.model.json;

import java.util.ArrayList;
import java.util.List;

/**
 * XEADシステムのルートJSONモデルクラス
 * システム全体の設計データを表現します。
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class XeadSystem {
    
    private String descriptions;
    private String name;
    private String version;
    private String formatVersion;
    
    // DDL設定
    private String ddlCommentMark = "--";
    private String ddlSectionMark = ";";
    private String ddlAdditionalParms = "";
    private String ddlFieldCommentType = "1";
    private boolean ignoreForeignKeyConstraints = false;
    private boolean isToConvertCamel = true;
    private boolean useTableNameAsTableNameInCreateTableStatement = true;
    
    // 子要素
    private List<Department> departments = new ArrayList<>();
    private List<TableType> tableTypes = new ArrayList<>();
    private List<DataType> dataTypes = new ArrayList<>();
    private List<FunctionType> functionTypes = new ArrayList<>();
    private List<MaintenanceLog> maintenanceLogs = new ArrayList<>();
    private List<Table> tables = new ArrayList<>();
    private List<Relationship> relationships = new ArrayList<>();
    private List<Function> functions = new ArrayList<>();
    private List<Subsystem> subsystems = new ArrayList<>();
    private List<Task> tasks = new ArrayList<>();
    private List<Role> roles = new ArrayList<>();
    private List<SubjectArea> subjectAreas = new ArrayList<>();
    private List<Terms> terms = new ArrayList<>();
    private List<TaskType> taskTypes = new ArrayList<>();
    private List<IOTable> iotables = new ArrayList<>();
    
    // Getters and Setters
    
    public String getDescriptions() {
        return descriptions;
    }
    
    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getVersion() {
        return version;
    }
    
    public void setVersion(String version) {
        this.version = version;
    }
    
    public String getFormatVersion() {
        return formatVersion;
    }
    
    public void setFormatVersion(String formatVersion) {
        this.formatVersion = formatVersion;
    }
    
    public String getDdlCommentMark() {
        return ddlCommentMark;
    }
    
    public void setDdlCommentMark(String ddlCommentMark) {
        this.ddlCommentMark = ddlCommentMark;
    }
    
    public String getDdlSectionMark() {
        return ddlSectionMark;
    }
    
    public void setDdlSectionMark(String ddlSectionMark) {
        this.ddlSectionMark = ddlSectionMark;
    }
    
    public String getDdlAdditionalParms() {
        return ddlAdditionalParms;
    }
    
    public void setDdlAdditionalParms(String ddlAdditionalParms) {
        this.ddlAdditionalParms = ddlAdditionalParms;
    }
    
    public String getDdlFieldCommentType() {
        return ddlFieldCommentType;
    }
    
    public void setDdlFieldCommentType(String ddlFieldCommentType) {
        this.ddlFieldCommentType = ddlFieldCommentType;
    }
    
    public boolean isIgnoreForeignKeyConstraints() {
        return ignoreForeignKeyConstraints;
    }
    
    public void setIgnoreForeignKeyConstraints(boolean ignoreForeignKeyConstraints) {
        this.ignoreForeignKeyConstraints = ignoreForeignKeyConstraints;
    }
    
    public boolean isIsToConvertCamel() {
        return isToConvertCamel;
    }
    
    public void setIsToConvertCamel(boolean isToConvertCamel) {
        this.isToConvertCamel = isToConvertCamel;
    }
    
    public boolean isUseTableNameAsTableNameInCreateTableStatement() {
        return useTableNameAsTableNameInCreateTableStatement;
    }
    
    public void setUseTableNameAsTableNameInCreateTableStatement(boolean useTableNameAsTableNameInCreateTableStatement) {
        this.useTableNameAsTableNameInCreateTableStatement = useTableNameAsTableNameInCreateTableStatement;
    }
    
    public List<Department> getDepartments() {
        return departments;
    }
    
    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }
    
    public List<TableType> getTableTypes() {
        return tableTypes;
    }
    
    public void setTableTypes(List<TableType> tableTypes) {
        this.tableTypes = tableTypes;
    }
    
    public List<DataType> getDataTypes() {
        return dataTypes;
    }
    
    public void setDataTypes(List<DataType> dataTypes) {
        this.dataTypes = dataTypes;
    }
    
    public List<FunctionType> getFunctionTypes() {
        return functionTypes;
    }
    
    public void setFunctionTypes(List<FunctionType> functionTypes) {
        this.functionTypes = functionTypes;
    }
    
    public List<MaintenanceLog> getMaintenanceLogs() {
        return maintenanceLogs;
    }
    
    public void setMaintenanceLogs(List<MaintenanceLog> maintenanceLogs) {
        this.maintenanceLogs = maintenanceLogs;
    }
    
    public List<Table> getTables() {
        return tables;
    }
    
    public void setTables(List<Table> tables) {
        this.tables = tables;
    }
    
    public List<Relationship> getRelationships() {
        return relationships;
    }
    
    public void setRelationships(List<Relationship> relationships) {
        this.relationships = relationships;
    }
    
    public List<Function> getFunctions() {
        return functions;
    }
    
    public void setFunctions(List<Function> functions) {
        this.functions = functions;
    }
    
    public List<Subsystem> getSubsystems() {
        return subsystems;
    }
    
    public void setSubsystems(List<Subsystem> subsystems) {
        this.subsystems = subsystems;
    }
    
    public List<Task> getTasks() {
        return tasks;
    }
    
    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
    
    public List<Role> getRoles() {
        return roles;
    }
    
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
    
    public List<SubjectArea> getSubjectAreas() {
        return subjectAreas;
    }
    
    public void setSubjectAreas(List<SubjectArea> subjectAreas) {
        this.subjectAreas = subjectAreas;
    }
    
    public List<Terms> getTerms() {
        return terms;
    }
    
    public void setTerms(List<Terms> terms) {
        this.terms = terms;
    }
    
    public List<TaskType> getTaskTypes() {
        return taskTypes;
    }
    
    public void setTaskTypes(List<TaskType> taskTypes) {
        this.taskTypes = taskTypes;
    }
    
    public List<IOTable> getIotables() {
        return iotables;
    }
    
    public void setIotables(List<IOTable> iotables) {
        this.iotables = iotables;
    }
}
