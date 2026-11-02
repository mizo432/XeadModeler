package xeadModeler.view;

import xeadModeler.Modeler;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.ResourceBundle;

/**
 * メインフレームビュー
 * Modelerのメインフレーム全体のUI構成を担当します。
 * - メニューバー (MenuBarView)
 * - ツリービュー (TreeView)
 * - コンテンツビュー (ContentView)
 * - ステータスバー
 * - ジャンプボタン
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class ModelerView {
    private static final ResourceBundle res = ResourceBundle.getBundle("xeadModeler.Res");

    private final Modeler modeler;

    // メインパネル
    private JPanel jPanelMain;
    private JPanel jPanelJumpButtons = new JPanel();
    private GridLayout gridLayoutJumpButtons = new GridLayout();
    private NodeJumpButton[] nodeJumpButton = new NodeJumpButton[8];

    // メインスプリットペイン
    private JSplitPane jSplitPaneMain = new JSplitPane();
    private JPanel jPanelTopMargin1 = new JPanel();
    private JPanel jPanelTopMargin2 = new JPanel();
    private JLabel jLabelSubtitle = new JLabel();
    private JLabel jLabelChangeState = new JLabel();
    private JProgressBar jProgressBar = new JProgressBar();
    private Border borderOriginal1;

    // 子ビュー
    private MenuBarView menuBarView;
    private TreeView treeView;
    private ContentView contentView;

    /**
     * コンストラクタ
     * @param modeler Modelerインスタンス
     * @param treeListener ツリー操作リスナー
     * @param contentListener コンテンツ操作リスナー
     * @param menuBarListener メニュー操作リスナー
     */
    public ModelerView(Modeler modeler,
                       TreeView.TreeListener treeListener,
                       ContentView.ContentListener contentListener,
                       MenuBarView.MenuBarListener menuBarListener) {
        this.modeler = modeler;
        this.treeView = new TreeView(modeler, treeListener);
        this.contentView = new ContentView(modeler, contentListener);
        this.menuBarView = new MenuBarView(menuBarListener);
        initComponents();
    }

    private void initComponents() {
        // メインパネル
        jPanelMain = (JPanel) modeler.getContentPane();
        jPanelMain.setLayout(new BorderLayout());

        // ジャンプボタン
        jPanelJumpButtons.setPreferredSize(new Dimension(10, 26));
        jPanelJumpButtons.setLayout(gridLayoutJumpButtons);
        gridLayoutJumpButtons.setColumns(8);
        gridLayoutJumpButtons.setRows(0);

        // ステータスバーのボーダー
        borderOriginal1 = BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.white,
            UIManager.getColor("control"), Color.gray, UIManager.getColor("control"));
        jProgressBar.setBorder(borderOriginal1);
        jLabelChangeState.setBorder(jProgressBar.getBorder());
        jLabelChangeState.setFont(new java.awt.Font(modeler.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jLabelChangeState.setHorizontalAlignment(SwingConstants.CENTER);

        // トップマージンパネル
        jPanelTopMargin1.setPreferredSize(new Dimension(10, 26));
        jPanelTopMargin1.setLayout(new BorderLayout());
        jPanelTopMargin1.setBorder(null);

        GridLayout gridLayoutTopMargin = new GridLayout();
        jPanelTopMargin2.setPreferredSize(new Dimension(160, 20));
        jPanelTopMargin2.setLayout(gridLayoutTopMargin);
        jPanelTopMargin2.setBorder(null);

        jLabelSubtitle.setBorder(jProgressBar.getBorder());
        jLabelSubtitle.setFont(new java.awt.Font(modeler.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jLabelSubtitle.setPreferredSize(new Dimension(800, 17));
        jLabelSubtitle.setText(res.getString("S49"));

        // メインレイアウト構成
        jPanelMain.add(jPanelJumpButtons, BorderLayout.SOUTH);
        jPanelMain.add(jSplitPaneMain, BorderLayout.CENTER);
        jPanelMain.add(jPanelTopMargin1, BorderLayout.NORTH);
        jPanelTopMargin1.add(jLabelSubtitle, BorderLayout.CENTER);
        jPanelTopMargin1.add(jPanelTopMargin2, BorderLayout.EAST);
        jPanelTopMargin2.add(jProgressBar, null);
        jPanelTopMargin2.add(jLabelChangeState, null);

        // スプリットペイン構成
        jSplitPaneMain.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        jSplitPaneMain.add(treeView.getJScrollPaneTreeView(), JSplitPane.LEFT);
        jSplitPaneMain.add(contentView.getJPanelContentsPane(), JSplitPane.RIGHT);
        jSplitPaneMain.setDividerLocation(350);

        // メニューバー設定
        modeler.setJMenuBar(menuBarView.getMenuBar());
    }

    /**
     * メニューバービューを取得します。
     */
    public MenuBarView getMenuBarView() {
        return menuBarView;
    }

    /**
     * ツリービューを取得します。
     */
    public TreeView getTreeView() {
        return treeView;
    }

    /**
     * コンテンツビューを取得します。
     */
    public ContentView getContentView() {
        return contentView;
    }

    /**
     * サブタイトルラベルを取得します。
     */
    public JLabel getJLabelSubtitle() {
        return jLabelSubtitle;
    }

    /**
     * 変更状態ラベルを取得します。
     */
    public JLabel getJLabelChangeState() {
        return jLabelChangeState;
    }

    /**
     * プログレスバーを取得します。
     */
    public JProgressBar getJProgressBar() {
        return jProgressBar;
    }

    /**
     * ジャンプボタンパネルを取得します。
     */
    public JPanel getJPanelJumpButtons() {
        return jPanelJumpButtons;
    }

    /**
     * ノードジャンプボタンを取得します。
     */
    public NodeJumpButton[] getNodeJumpButton() {
        return nodeJumpButton;
    }

    /**
     * メインスプリットペインを取得します。
     */
    public JSplitPane getJSplitPaneMain() {
        return jSplitPaneMain;
    }
}
