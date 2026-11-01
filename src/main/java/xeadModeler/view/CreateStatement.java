package xeadModeler.view;

/**
 * CreateTableステートメントのデータ保持クラス
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class CreateStatement {

    private String statement_;
    private boolean warningOccured_;

    public CreateStatement(String str, boolean warn) {
        statement_ = str;
        warningOccured_ = warn;
    }

    public String toString() {
        return statement_;
    }

    public boolean isWarned() {
        return warningOccured_;
    }
}
