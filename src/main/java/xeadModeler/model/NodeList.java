package xeadModeler.model;

import java.util.List;

/**
 * JSON POJOリストのラッパークラス
 * XMLのNodeListに代わるインターフェースを提供します。
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class NodeList {
    private final List<?> list;

    public NodeList(List<?> list) {
        this.list = list;
    }

    public int getLength() {
        return list.size();
    }

    public Object item(int index) {
        if (index >= 0 && index < list.size()) {
            return list.get(index);
        }
        return null;
    }

    public List<?> getList() {
        return list;
    }
}
