package xeadModeler.factory;

import xeadModeler.model.json.*;

import java.util.HashMap;
import java.util.Map;

/**
 * JSON POJOのファクトリクラス
 * XMLのDocument.createElement()に代わってJSONモデルオブジェクトを生成します。
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class PojoFactory {

    private static final Map<String, Class<?>> TYPE_MAP = new HashMap<>();

    static {
        // Top-level elements
        TYPE_MAP.put("System", xeadModeler.model.json.XeadSystem.class);
        TYPE_MAP.put("SubjectArea", SubjectArea.class);
        TYPE_MAP.put("Role", Role.class);
        TYPE_MAP.put("Task", Task.class);
        TYPE_MAP.put("Subsystem", Subsystem.class);
        TYPE_MAP.put("Table", Table.class);
        TYPE_MAP.put("Relationship", Relationship.class);
        TYPE_MAP.put("Function", Function.class);
        TYPE_MAP.put("IOPanel", IOPanel.class);
        TYPE_MAP.put("IOSpool", IOSpool.class);
        TYPE_MAP.put("IOTable", IOTable.class);
        TYPE_MAP.put("IOWebPage", IOWebPage.class);
        TYPE_MAP.put("DataflowNode", DataflowNode.class);
        TYPE_MAP.put("DataflowLine", DataflowLine.class);

        // Child elements
        TYPE_MAP.put("TableField", TableField.class);
        TYPE_MAP.put("TableKey", TableKey.class);
        TYPE_MAP.put("TableKeyField", TableKeyField.class);
        TYPE_MAP.put("TaskAction", TaskAction.class);
        TYPE_MAP.put("TaskFunctionIO", TaskFunctionIO.class);
        TYPE_MAP.put("FunctionUsedByThis", FunctionUsedByThis.class);
        TYPE_MAP.put("IOTableField", IOTableField.class);
        TYPE_MAP.put("IOPanelField", IOPanelField.class);
        TYPE_MAP.put("IOPanelStyle", IOPanelStyle.class);
        TYPE_MAP.put("IOSpoolField", IOSpoolField.class);
        TYPE_MAP.put("IOSpoolStyle", IOSpoolStyle.class);
        TYPE_MAP.put("IOWebPageField", IOWebPageField.class);
        TYPE_MAP.put("SubsystemTable", SubsystemTable.class);
        TYPE_MAP.put("SubsystemRelationship", SubsystemRelationship.class);

        // Type definitions
        TYPE_MAP.put("DataType", DataType.class);
        TYPE_MAP.put("TableType", TableType.class);
        TYPE_MAP.put("FunctionType", FunctionType.class);
        TYPE_MAP.put("TaskType", TaskType.class);
        TYPE_MAP.put("Department", Department.class);
        TYPE_MAP.put("Terms", Terms.class);
        TYPE_MAP.put("MaintenanceLog", MaintenanceLog.class);
    }

    /**
     * 要素名に対応するJSON POJOを生成します。
     * 対応する型が見つからない場合はnullを返します。
     *
     * @param elementName 要素名（XMLタグ名と同等）
     * @return 生成されたJSON POJOインスタンス、またはnull
     */
    public Object createElement(String elementName) {
        Class<?> clazz = TYPE_MAP.get(elementName);
        if (clazz == null) {
            // Unknown element type - return null
            return null;
        }
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create POJO for: " + elementName, e);
        }
    }

    /**
     * 指定された要素名に対応するクラスが存在するか確認します。
     *
     * @param elementName 要素名
     * @return 対応するクラスが存在する場合はtrue
     */
    public boolean hasType(String elementName) {
        return TYPE_MAP.containsKey(elementName);
    }

    /**
     * 要素名に対応するクラスを取得します。
     *
     * @param elementName 要素名
     * @return 対応するClassオブジェクト、またはnull
     */
    public Class<?> getClassForElement(String elementName) {
        return TYPE_MAP.get(elementName);
    }
}
