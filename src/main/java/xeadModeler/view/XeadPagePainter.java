package xeadModeler.view;

import javax.swing.JComponent;
import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;

/**
 * XEADページの描画インターフェース
 * 印刷機能を担当します。
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public interface XeadPagePainter extends Printable {
    
    /**
     * パネルを印刷します
     * @param g2 Graphics2Dコンテキスト
     * @param fmt ページフォーマット
     * @param jPanel 印刷するパネル
     * @param name ページ名
     */
    void printPanel(Graphics2D g2, PageFormat fmt, JComponent jPanel, String name);
}
