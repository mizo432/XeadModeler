package xeadModeler.model;

import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Method;

/**
 * JSON POJOをElementのように扱えるラッパークラス
 * 属性の取得・設定をリフレクションで行います。
 */
public class PojoElement {
    private final Object pojo;

    public PojoElement(Object pojo) {
        this.pojo = pojo;
    }

    public Object getPojo() {
        return pojo;
    }

    public String getAttribute(String name) {
        if (pojo == null) return "";
        String getter = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
        try {
            Method method = pojo.getClass().getMethod(getter);
            Object result = method.invoke(pojo);
            return result != null ? result.toString() : "";
        } catch (Exception e) {
            return "";
        }
    }

    public void setAttribute(String name, String value) {
        if (pojo == null) return;
        String setterName = "set" + name.substring(0, 1).toLowerCase() + name.substring(1);
        if (name.endsWith("ID")) {
            setterName = "set" + name.substring(0, name.length() - 2) + "Id";
        }
        try {
            Method method = pojo.getClass().getMethod(setterName, String.class);
            method.invoke(pojo, value);
        } catch (Exception e) {
            try {
                Method method = pojo.getClass().getMethod("set" + name, String.class);
                method.invoke(pojo, value);
            } catch (Exception e2) {
                // Ignore
            }
        }
    }

    public NodeList getElementsByTagName(String tagName) {
        if (pojo == null) return new NodeList(new ArrayList<>());
        try {
            // Try plural form first (e.g., "TableField" -> "getTableFields")
            String plural = tagName + "s";
            String getter = "get" + plural.substring(0, 1).toUpperCase() + plural.substring(1);
            Method method = pojo.getClass().getMethod(getter);
            Object result = method.invoke(pojo);
            if (result instanceof List) {
                return new NodeList((List<?>) result);
            }
        } catch (Exception e) {
            // Try singular form
        }
        try {
            String getter = "get" + tagName.substring(0, 1).toUpperCase() + tagName.substring(1);
            Method method = pojo.getClass().getMethod(getter);
            Object result = method.invoke(pojo);
            if (result instanceof List) {
                return new NodeList((List<?>) result);
            }
        } catch (Exception e) {
            // Ignore
        }
        return new NodeList(new ArrayList<>());
    }

    public void appendChild(Object child) {
        // This is handled by the caller using appendChildToJsonPojo
    }

    public void removeChild(Object child) {
        // This is handled by the caller using removeChildFromJsonPojo
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        PojoElement other = (PojoElement) obj;
        return pojo != null ? pojo.equals(other.pojo) : other.pojo == null;
    }

    @Override
    public int hashCode() {
        return pojo != null ? pojo.hashCode() : 0;
    }
}
