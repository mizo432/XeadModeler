package xeadModeler.view;

/**
 * テーブル行番号のデータ保持クラス
 * JSON POJOをサポートします。
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class TableRowNumber {

    private Object pojo_;
    private Object pojo_ext1;
    private Object pojo_ext2;
    private int number_;

    public TableRowNumber(int num, Object pojo) {
        number_ = num;
        pojo_ = pojo;
        pojo_ext1 = null;
        pojo_ext2 = null;
    }

    public TableRowNumber(int num, Object pojo, Object pojoExt1, Object pojoExt2) {
        number_ = num;
        pojo_ = pojo;
        pojo_ext1 = pojoExt1;
        pojo_ext2 = pojoExt2;
    }

    public String toString() {
        return Integer.toString(number_);
    }

    public Object getPojo() {
        return pojo_;
    }

    public Object getPojoExt1() {
        return pojo_ext1;
    }

    public Object getPojoExt2() {
        return pojo_ext2;
    }

    /**
     * 指定されたプロパティ名でプロパティ値を取得します（リフレクション使用）
     * @param name プロパティ名
     * @return プロパティ値
     */
    public String getAttribute(String name) {
        if (pojo_ != null) {
            try {
                String getter = "get" + Character.toUpperCase(name.charAt(0)) + name.substring(1);
                java.lang.reflect.Method method = pojo_.getClass().getMethod(getter);
                Object result = method.invoke(pojo_);
                return result != null ? result.toString() : "";
            } catch (Exception e) {
                return "";
            }
        }
        return "";
    }

    /**
     * 指定されたプロパティ名と値でプロパティを設定します（リフレクション使用）
     * @param name プロパティ名
     * @param value プロパティ値
     */
    public void setAttribute(String name, String value) {
        if (pojo_ != null) {
            try {
                String setter = "set" + Character.toUpperCase(name.charAt(0)) + name.substring(1);
                java.lang.reflect.Method method = pojo_.getClass().getMethod(setter, String.class);
                method.invoke(pojo_, value);
            } catch (Exception e) {
                // Ignore if setter not found
            }
        }
    }

    /**
     * 拡張POJO1のプロパティ値を取得します
     * @param name プロパティ名
     * @return プロパティ値
     */
    public String getAttributeExt1(String name) {
        if (pojo_ext1 != null) {
            try {
                String getter = "get" + Character.toUpperCase(name.charAt(0)) + name.substring(1);
                java.lang.reflect.Method method = pojo_ext1.getClass().getMethod(getter);
                Object result = method.invoke(pojo_ext1);
                return result != null ? result.toString() : "";
            } catch (Exception e) {
                return "";
            }
        }
        return "";
    }

    /**
     * 拡張POJO2のプロパティ値を取得します
     * @param name プロパティ名
     * @return プロパティ値
     */
    public String getAttributeExt2(String name) {
        if (pojo_ext2 != null) {
            try {
                String getter = "get" + Character.toUpperCase(name.charAt(0)) + name.substring(1);
                java.lang.reflect.Method method = pojo_ext2.getClass().getMethod(getter);
                Object result = method.invoke(pojo_ext2);
                return result != null ? result.toString() : "";
            } catch (Exception e) {
                return "";
            }
        }
        return "";
    }
}
