package xeadModeler.service;

import javax.swing.JComponent;
import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;

/**
 * 描画サービスのインターフェース
 * 印刷機能などの描画操作を担当します。
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public interface DrawingService extends Printable {
    
    /**
     * パネルを印刷します
     * @param g2 Graphics2Dコンテキスト
     * @param fmt ページフォーマット
     * @param jPanel 印刷するパネル
     * @param name ページ名
     */
    void printPanel(Graphics2D g2, PageFormat fmt, JComponent jPanel, String name);
    
    /**
     * 現在のノードに応じてページを印刷します
     * @param g Graphicsコンテキスト
     * @param fmt ページフォーマット
     * @param pageindex ページインデックス
     * @return Printable.PAGE_EXISTS または Printable.NO_SUCH_PAGE
     */
    int print(Graphics g, PageFormat fmt, int pageindex);
}
