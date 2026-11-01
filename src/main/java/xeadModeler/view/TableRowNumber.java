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
}
