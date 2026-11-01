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
    
    /**
     * Alias for getIotables() for compatibility
     */
    public List<IOTable> getIOTables() {
        return iotables;
    }
    
    /**
     * 指定されたタイプの要素から最大のIDを持つ要素を取得します
     * @param type 要素タイプ ("Department", "TableType", "DataType", "FunctionType", "Table", "Relationship", "Function", "Subsystem", "Task", "Role", "SubjectArea", "Terms", "TaskType", "IOTable")
     * @return 最大のIDを持つ要素、存在しない場合はnull
     */
    public Object getLastElementOfTheType(String type) {
        switch (type) {
            case "Department":
                return getDepartmentWithMaxId();
            case "TableType":
                return getTableTypeWithMaxId();
            case "DataType":
                return getDataTypeWithMaxId();
            case "FunctionType":
                return getFunctionTypeWithMaxId();
            case "Table":
            case "SubsystemTable":
                return getTableWithMaxId();
            case "Relationship":
            case "SubsystemRelationship":
                return getRelationshipWithMaxId();
            case "Function":
                return getFunctionWithMaxId();
            case "Subsystem":
                return getSubsystemWithMaxId();
            case "Task":
                return getTaskWithMaxId();
            case "Role":
                return getRoleWithMaxId();
            case "SubjectArea":
                return getSubjectAreaWithMaxId();
            case "Terms":
                return getTermsWithMaxId();
            case "TaskType":
                return getTaskTypeWithMaxId();
            case "IOTable":
                return getIOTableWithMaxId();
            default:
                return null;
        }
    }
    
    /**
     * 最大のIDを持つTableTypeを取得します
     * @return 最大のIDを持つTableType、存在しない場合はnull
     */
    public TableType getTableTypeWithMaxId() {
        if (tableTypes == null || tableTypes.isEmpty()) {
            return null;
        }
        TableType max = null;
        int maxId = 0;
        for (TableType tt : tableTypes) {
            int id = Integer.parseInt(tt.getId());
            if (id > maxId) {
                maxId = id;
                max = tt;
            }
        }
        return max;
    }
    
    /**
     * 最大のIDを持つTableを取得します
     * @return 最大のIDを持つTable、存在しない場合はnull
     */
    public Table getTableWithMaxId() {
        if (tables == null || tables.isEmpty()) {
            return null;
        }
        Table max = null;
        int maxId = 0;
        for (Table t : tables) {
            int id = Integer.parseInt(t.getId());
            if (id > maxId) {
                maxId = id;
                max = t;
            }
        }
        return max;
    }
    
    /**
     * 最大のIDを持つDepartmentを取得します
     * @return 最大のIDを持つDepartment、存在しない場合はnull
     */
    public Department getDepartmentWithMaxId() {
        if (departments == null || departments.isEmpty()) {
            return null;
        }
        Department max = null;
        int maxId = 0;
        for (Department d : departments) {
            int id = Integer.parseInt(d.getId());
            if (id > maxId) {
                maxId = id;
                max = d;
            }
        }
        return max;
    }
    
    /**
     * 最大のIDを持つDataTypeを取得します
     * @return 最大のIDを持つDataType、存在しない場合はnull
     */
    public DataType getDataTypeWithMaxId() {
        if (dataTypes == null || dataTypes.isEmpty()) {
            return null;
        }
        DataType max = null;
        int maxId = 0;
        for (DataType dt : dataTypes) {
            int id = Integer.parseInt(dt.getId());
            if (id > maxId) {
                maxId = id;
                max = dt;
            }
        }
        return max;
    }
    
    /**
     * 最大のIDを持つFunctionTypeを取得します
     * @return 最大のIDを持つFunctionType、存在しない場合はnull
     */
    public FunctionType getFunctionTypeWithMaxId() {
        if (functionTypes == null || functionTypes.isEmpty()) {
            return null;
        }
        FunctionType max = null;
        int maxId = 0;
        for (FunctionType ft : functionTypes) {
            int id = Integer.parseInt(ft.getId());
            if (id > maxId) {
                maxId = id;
                max = ft;
            }
        }
        return max;
    }
    
    /**
     * 最大のIDを持つRelationshipを取得します
     * @return 最大のIDを持つRelationship、存在しない場合はnull
     */
    public Relationship getRelationshipWithMaxId() {
        if (relationships == null || relationships.isEmpty()) {
            return null;
        }
        Relationship max = null;
        int maxId = 0;
        for (Relationship r : relationships) {
            int id = Integer.parseInt(r.getId());
            if (id > maxId) {
                maxId = id;
                max = r;
            }
        }
        return max;
    }
    
    /**
     * 最大のIDを持つFunctionを取得します
     * @return 最大のIDを持つFunction、存在しない場合はnull
     */
    public Function getFunctionWithMaxId() {
        if (functions == null || functions.isEmpty()) {
            return null;
        }
        Function max = null;
        int maxId = 0;
        for (Function f : functions) {
            int id = Integer.parseInt(f.getId());
            if (id > maxId) {
                maxId = id;
                max = f;
            }
        }
        return max;
    }
    
    /**
     * 最大のIDを持つSubsystemを取得します
     * @return 最大のIDを持つSubsystem、存在しない場合はnull
     */
    public Subsystem getSubsystemWithMaxId() {
        if (subsystems == null || subsystems.isEmpty()) {
            return null;
        }
        Subsystem max = null;
        int maxId = 0;
        for (Subsystem s : subsystems) {
            int id = Integer.parseInt(s.getId());
            if (id > maxId) {
                maxId = id;
                max = s;
            }
        }
        return max;
    }
    
    /**
     * 最大のIDを持つTaskを取得します
     * @return 最大のIDを持つTask、存在しない場合はnull
     */
    public Task getTaskWithMaxId() {
        if (tasks == null || tasks.isEmpty()) {
            return null;
        }
        Task max = null;
        int maxId = 0;
        for (Task t : tasks) {
            int id = Integer.parseInt(t.getId());
            if (id > maxId) {
                maxId = id;
                max = t;
            }
        }
        return max;
    }
    
    /**
     * 最大のIDを持つRoleを取得します
     * @return 最大のIDを持つRole、存在しない場合はnull
     */
    public Role getRoleWithMaxId() {
        if (roles == null || roles.isEmpty()) {
            return null;
        }
        Role max = null;
        int maxId = 0;
        for (Role r : roles) {
            int id = Integer.parseInt(r.getId());
            if (id > maxId) {
                maxId = id;
                max = r;
            }
        }
        return max;
    }
    
    /**
     * 最大のIDを持つSubjectAreaを取得します
     * @return 最大のIDを持つSubjectArea、存在しない場合はnull
     */
    public SubjectArea getSubjectAreaWithMaxId() {
        if (subjectAreas == null || subjectAreas.isEmpty()) {
            return null;
        }
        SubjectArea max = null;
        int maxId = 0;
        for (SubjectArea sa : subjectAreas) {
            int id = Integer.parseInt(sa.getId());
            if (id > maxId) {
                maxId = id;
                max = sa;
            }
        }
        return max;
    }
    
    /**
     * 最大のIDを持つTermsを取得します
     * @return 最大のIDを持つTerms、存在しない場合はnull
     */
    public Terms getTermsWithMaxId() {
        if (terms == null || terms.isEmpty()) {
            return null;
        }
        Terms max = null;
        int maxId = 0;
        for (Terms t : terms) {
            int id = Integer.parseInt(t.getId());
            if (id > maxId) {
                maxId = id;
                max = t;
            }
        }
        return max;
    }
    
    /**
     * 最大のIDを持つTaskTypeを取得します
     * @return 最大のIDを持つTaskType、存在しない場合はnull
     */
    public TaskType getTaskTypeWithMaxId() {
        if (taskTypes == null || taskTypes.isEmpty()) {
            return null;
        }
        TaskType max = null;
        int maxId = 0;
        for (TaskType tt : taskTypes) {
            int id = Integer.parseInt(tt.getId());
            if (id > maxId) {
                maxId = id;
                max = tt;
            }
        }
        return max;
    }
    
    /**
     * 最大のIDを持つIOTableを取得します
     * @return 最大のIDを持つIOTable、存在しない場合はnull
     */
    public IOTable getIOTableWithMaxId() {
        if (iotables == null || iotables.isEmpty()) {
            return null;
        }
        IOTable max = null;
        int maxId = 0;
        for (IOTable it : iotables) {
            int id = Integer.parseInt(it.getId());
            if (id > maxId) {
                maxId = id;
                max = it;
            }
        }
        return max;
    }
    
    /**
     * 最初のTableTypeのIDを取得します
     * @return 最初のTableTypeのID、存在しない場合は"1"
     */
    public String getDefaultTableTypeId() {
        if (tableTypes == null || tableTypes.isEmpty()) {
            return "1";
        }
        return tableTypes.get(0).getId();
    }
}
