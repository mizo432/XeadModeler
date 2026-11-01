package xeadModeler.view;

import org.w3c.dom.Element;

/**
 * テーブル行番号のデータ保持クラス
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class TableRowNumber {

    private Element element_;
    private Element element_ext1;
    private Element element_ext2;
    private int number_;

    public TableRowNumber(int num, Element elm) {
        number_ = num;
        element_ = elm;
        element_ext1 = null;
        element_ext2 = null;
    }

    public TableRowNumber(int num, Element elm, Element elmExt1, Element elmExt2) {
        number_ = num;
        element_ = elm;
        element_ext1 = elmExt1;
        element_ext2 = elmExt2;
    }

    public String toString() {
        return Integer.toString(number_);
    }

    public Element getElement() {
        return element_;
    }

    public Element getElementExt1() {
        return element_ext1;
    }

    public Element getElementExt2() {
        return element_ext2;
    }

    /**
     * 指定された属性名で属性値を取得します
     * @param name 属性名
     * @return 属性値
     */
    public String getAttribute(String name) {
        if (element_ != null) {
            return element_.getAttribute(name);
        }
        return "";
    }

    /**
     * 指定された属性名と値で属性を設定します
     * @param name 属性名
     * @param value 属性値
     */
    public void setAttribute(String name, String value) {
        if (element_ != null) {
            element_.setAttribute(name, value);
        }
    }

    /**
     * 拡張要素1の属性値を取得します
     * @param name 属性名
     * @return 属性値
     */
    public String getAttributeExt1(String name) {
        if (element_ext1 != null) {
            return element_ext1.getAttribute(name);
        }
        return "";
    }

    /**
     * 拡張要素2の属性値を取得します
     * @param name 属性名
     * @return 属性値
     */
    public String getAttributeExt2(String name) {
        if (element_ext2 != null) {
            return element_ext2.getAttribute(name);
        }
        return "";
    }
}
