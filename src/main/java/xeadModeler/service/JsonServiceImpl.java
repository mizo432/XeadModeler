package xeadModeler.service;

import xeadModeler.model.json.*;

import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.json.JsonMapper;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * JSONデータ管理のサービス実装クラス
 * Jackson 3を使用してJSONファイルの読み込み・書き込みを担当します。
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
@Service
public class JsonServiceImpl implements JsonService {
    
    private final ObjectMapper objectMapper;
    
    public JsonServiceImpl() {
        this.objectMapper = JsonMapper.builder()
            .enable(SerializationFeature.INDENT_OUTPUT)
            .build();
    }
    
    @Override
    public XeadSystem loadSystem(String fileName) throws Exception {
        File file = new File(fileName);
        if (!file.exists()) {
            throw new IOException("File not found: " + fileName);
        }
        return objectMapper.readValue(file, XeadSystem.class);
    }
    
    @Override
    public void saveSystem(XeadSystem system, String fileName) throws Exception {
        objectMapper.writeValue(new File(fileName), system);
    }
    
    @Override
    public XeadSystem createNewSystem(String fileName, String formatVersion) throws Exception {
        XeadSystem system = new XeadSystem();
        
        String lang = Locale.getDefault().getLanguage();
        if (lang.equals("ja")) {
            system.setName("新規システム");
        } else {
            system.setName("New System");
        }
        
        system.setDescriptions("");
        system.setVersion("0.0.1");
        system.setFormatVersion(formatVersion);
        
        // Department
        List<Department> departments = new ArrayList<>();
        Department dept1 = new Department();
        dept1.setId("1");
        dept1.setSortKey("000");
        if (lang.equals("ja")) {
            dept1.setName("未指定");
        } else {
            dept1.setName("Unknown");
        }
        dept1.setDescriptions("");
        departments.add(dept1);
        
        Department dept2 = new Department();
        dept2.setId("2");
        dept2.setSortKey("001");
        if (lang.equals("ja")) {
            dept2.setName("部署１");
            dept2.setDescriptions("説明１");
        } else {
            dept2.setName("Section1");
            dept2.setDescriptions("");
        }
        departments.add(dept2);
        system.setDepartments(departments);
        
        // TableType
        List<TableType> tableTypes = new ArrayList<>();
        TableType tt1 = new TableType();
        tt1.setId("1");
        tt1.setSortKey("000");
        if (lang.equals("ja")) {
            tt1.setName("未指定");
        } else {
            tt1.setName("Unknown");
        }
        tt1.setDescriptions("");
        tableTypes.add(tt1);
        
        TableType tt2 = new TableType();
        tt2.setId("2");
        tt2.setSortKey("001");
        if (lang.equals("ja")) {
            tt2.setName("テーブルタイプ１");
            tt2.setDescriptions("説明１");
        } else {
            tt2.setName("TableType1");
            tt2.setDescriptions("");
        }
        tableTypes.add(tt2);
        system.setTableTypes(tableTypes);
        
        // DataType
        List<DataType> dataTypes = new ArrayList<>();
        DataType dt1 = new DataType();
        dt1.setId("1");
        dt1.setSortKey("000");
        dt1.setBasicType("String");
        dt1.setLength("0");
        dt1.setDecimal("0");
        if (lang.equals("ja")) {
            dt1.setName("未指定");
        } else {
            dt1.setName("Unknown");
        }
        dataTypes.add(dt1);
        
        DataType dt2 = new DataType();
        dt2.setId("2");
        dt2.setSortKey("NR09.0-");
        dt2.setBasicType("SignedNumber");
        dt2.setLength("9");
        dt2.setDecimal("0");
        dt2.setSqlExpression("NUMBER(9)");
        if (lang.equals("ja")) {
            dt2.setName("符号付９桁整数");
        } else {
            dt2.setName("Signed 9-digit Integer");
        }
        dataTypes.add(dt2);
        
        DataType dt3 = new DataType();
        dt3.setId("3");
        dt3.setSortKey("NR09.0");
        dt3.setBasicType("UnsignedNumber");
        dt3.setLength("9");
        dt3.setDecimal("0");
        dt3.setSqlExpression("NUMBER(9)");
        if (lang.equals("ja")) {
            dt3.setName("正の９桁整数");
        } else {
            dt3.setName("Unsigned 9-digit Integer");
        }
        dataTypes.add(dt3);
        
        DataType dt4 = new DataType();
        dt4.setId("4");
        dt4.setSortKey("TX050");
        dt4.setBasicType("String");
        dt4.setLength("50");
        dt4.setDecimal("0");
        dt4.setSqlExpression("VARCHAR(50)");
        if (lang.equals("ja")) {
            dt4.setName("５０桁文字");
        } else {
            dt4.setName("50-digit String");
        }
        dataTypes.add(dt4);
        
        DataType dt5 = new DataType();
        dt5.setId("5");
        dt5.setSortKey("KB1");
        dt5.setBasicType("String");
        dt5.setLength("1");
        dt5.setDecimal("0");
        dt5.setSqlExpression("CHAR(1)");
        if (lang.equals("ja")) {
            dt5.setName("区分");
        } else {
            dt5.setName("1-digit Type");
        }
        dataTypes.add(dt5);
        system.setDataTypes(dataTypes);
        
        // FunctionType
        List<FunctionType> functionTypes = new ArrayList<>();
        FunctionType ft1 = new FunctionType();
        ft1.setId("1");
        ft1.setSortKey("000");
        if (lang.equals("ja")) {
            ft1.setName("未指定");
        } else {
            ft1.setName("Unknown");
        }
        ft1.setDescriptions("");
        functionTypes.add(ft1);
        
        FunctionType ft2 = new FunctionType();
        ft2.setId("2");
        ft2.setSortKey("001");
        if (lang.equals("ja")) {
            ft2.setName("機能タイプ１");
            ft2.setDescriptions("説明１");
        } else {
            ft2.setName("FunctionType1");
            ft2.setDescriptions("");
        }
        functionTypes.add(ft2);
        system.setFunctionTypes(functionTypes);
        
        // MaintenanceLog
        List<MaintenanceLog> maintenanceLogs = new ArrayList<>();
        MaintenanceLog ml1 = new MaintenanceLog();
        ml1.setId("0001");
        ml1.setHeader("0.0.1");
        ml1.setSortKey("");
        if (lang.equals("ja")) {
            ml1.setDescriptions("新規追加");
        } else {
            ml1.setDescriptions("Created");
        }
        maintenanceLogs.add(ml1);
        system.setMaintenanceLogs(maintenanceLogs);
        
        // 空のリストを初期化
        system.setTables(new ArrayList<>());
        system.setRelationships(new ArrayList<>());
        system.setFunctions(new ArrayList<>());
        system.setSubsystems(new ArrayList<>());
        system.setTasks(new ArrayList<>());
        system.setRoles(new ArrayList<>());
        system.setSubjectAreas(new ArrayList<>());
        system.setTerms(new ArrayList<>());
        system.setTaskTypes(new ArrayList<>());
        
        // ファイルに保存
        saveSystem(system, fileName);
        
        return system;
    }
    
    @Override
    public Object cloneObject(Object obj) throws Exception {
        if (obj == null) {
            return null;
        }
        String json = objectMapper.writeValueAsString(obj);
        return objectMapper.readValue(json, obj.getClass());
    }
}
