package xeadModeler.service;

import xeadModeler.model.XeadDocumentModel;
import xeadModeler.model.json.*;

import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.List;

/**
 * テーブル操作サービスの実装クラス
 * テーブルデータの編集、更新などの機能を担当します。
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
@Service
public class TableServiceImpl implements TableService {
    
    private XeadDocumentModel documentModel;
    
    public TableServiceImpl(XeadDocumentModel documentModel) {
        this.documentModel = documentModel;
    }
    
    @Override
    public void updateCell(Object object, String propertyName, String value) {
        // リフレクションを使用してプロパティを更新
        try {
            String setter = "set" + Character.toUpperCase(propertyName.charAt(0)) + propertyName.substring(1);
            Method method = object.getClass().getMethod(setter, String.class);
            method.invoke(object, value);
        } catch (Exception e) {
            // プロパティが見つからない場合は無視
        }
    }
    
    @Override
    public Object addRow(String parentTypeId, String parentType, String rowType) {
        XeadSystem system = documentModel.getXeadSystem();
        if (system == null) {
            return null;
        }
        
        // 親オブジェクトを検索
        Object parent = findElementByAttribute(parentType, "id", parentTypeId);
        if (parent == null) {
            return null;
        }
        
        String id = generateId(rowType);
        switch (rowType) {
            case "TableField":
                if (parent instanceof Table) {
                    TableField field = new TableField();
                    field.setId(id);
                    ((Table) parent).getTableFields().add(field);
                    return field;
                }
                break;
            case "TableKey":
                if (parent instanceof Table) {
                    TableKey key = new TableKey();
                    key.setId(id);
                    ((Table) parent).getTableKeys().add(key);
                    return key;
                }
                break;
        }
        return null;
    }
    
    @Override
    public void removeRow(String type, String id) {
        XeadSystem system = documentModel.getXeadSystem();
        if (system == null) {
            return;
        }
        
        // 全テーブルから該当する行を削除
        for (Table table : system.getTables()) {
            if ("TableField".equals(type)) {
                table.getTableFields().removeIf(f -> f.getId().equals(id));
            } else if ("TableKey".equals(type)) {
                table.getTableKeys().removeIf(k -> k.getId().equals(id));
            }
        }
    }
    
    @Override
    public void refreshTable(XeadDocumentModel documentModel) {
        // テーブルの再読み込みロジック
        // 現在の実装では何もしません
    }
    
    @Override
    public Object findElementByAttribute(String elementType, String propertyName, String propertyValue) {
        XeadSystem system = documentModel.getXeadSystem();
        if (system == null) {
            return null;
        }
        
        // 要素タイプに応じて検索
        switch (elementType) {
            case "Table":
                for (Table table : system.getTables()) {
                    if (getPropertyValue(table, propertyName).equals(propertyValue)) {
                        return table;
                    }
                }
                break;
            case "TableField":
                for (Table table : system.getTables()) {
                    for (TableField field : table.getTableFields()) {
                        if (getPropertyValue(field, propertyName).equals(propertyValue)) {
                            return field;
                        }
                    }
                }
                break;
            case "TableKey":
                for (Table table : system.getTables()) {
                    for (TableKey key : table.getTableKeys()) {
                        if (getPropertyValue(key, propertyName).equals(propertyValue)) {
                            return key;
                        }
                    }
                }
                break;
        }
        return null;
    }
    
    /**
     * オブジェクトのプロパティ値を取得します。
     */
    private String getPropertyValue(Object object, String propertyName) {
        try {
            String getter = "get" + Character.toUpperCase(propertyName.charAt(0)) + propertyName.substring(1);
            Method method = object.getClass().getMethod(getter);
            Object value = method.invoke(object);
            return value != null ? value.toString() : null;
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * 要素IDを生成します。
     * @param elementType 要素タイプ
     * @return 生成されたID
     */
    private String generateId(String elementType) {
        XeadSystem system = documentModel.getXeadSystem();
        if (system == null) {
            return elementType + "1";
        }
        
        int maxId = 0;
        if ("TableField".equals(elementType)) {
            for (Table table : system.getTables()) {
                for (TableField field : table.getTableFields()) {
                    String id = field.getId();
                    if (id != null && !id.isEmpty()) {
                        try {
                            int numericId = Integer.parseInt(id.replace(elementType, ""));
                            if (numericId > maxId) {
                                maxId = numericId;
                            }
                        } catch (NumberFormatException e) {
                            // スキップ
                        }
                    }
                }
            }
        } else if ("TableKey".equals(elementType)) {
            for (Table table : system.getTables()) {
                for (TableKey key : table.getTableKeys()) {
                    String id = key.getId();
                    if (id != null && !id.isEmpty()) {
                        try {
                            int numericId = Integer.parseInt(id.replace(elementType, ""));
                            if (numericId > maxId) {
                                maxId = numericId;
                            }
                        } catch (NumberFormatException e) {
                            // スキップ
                        }
                    }
                }
            }
        }
        return elementType + (maxId + 1);
    }
}
