package xeadModeler.view;

import xeadModeler.Modeler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ResourceBundle;

/**
 * IOウェブページノードのビュー
 * IOウェブページ情報、フィールドリスト、イメージプレビューなどを含みます。
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class IOWebPageView {
    private static final ResourceBundle res = ResourceBundle.getBundle("xeadModeler.Res");
    private static final String MAIN_FONT_NAME = "SansSerif";

    // Components
    private final JPanel jPanelIOWebPage = new JPanel();
    private final JPanel jPanelIOWebPage1 = new JPanel();
    private final JPanel jPanelIOWebPage2 = new JPanel();
    private final JPanel jPanelIOWebPage3 = new JPanel();
    private final JPanel jPanelIOWebPage4 = new JPanel();
    private final JPanel jPanelIOWebPage5 = new JPanel();
    private final JPanel jPanelIOWebPage6 = new JPanel();
    private final JSplitPane jSplitPaneIOWebPage1 = new JSplitPane();
    private final JSplitPane jSplitPaneIOWebPage2 = new JSplitPane();
    
    // Labels
    private final JLabel jLabelIOWebPageName = new JLabel();
    private final JLabel jLabelIOWebPageFileName = new JLabel();
    private final JLabel jLabelIOWebPageDescriptions = new JLabel();
    private final JLabel jLabelIOWebPageImage = new JLabel();
    
    // Fields
    private final JTextField jTextFieldIOWebPageName = new JTextField();
    private final JTextField jTextFieldIOWebPageFileName = new JTextField();
    
    // Descriptions
    private final JScrollPane jScrollPaneIOWebPageDescriptions = new JScrollPane();
    private final JTextArea jTextAreaIOWebPageDescriptions = new JTextArea();
    
    // Field list
    private final JScrollPane jScrollPaneIOWebPageFieldList = new JScrollPane();
    private final JTable jTableIOWebPageFieldList;
    
    // Image preview
    private final JScrollPane jScrollPaneIOWebPageImage = new JScrollPane();
    private final JEditorPane jEditorPaneIOWebPageImage = new JEditorPane();
    
    // State
    private int selectedRow_jTableIOWebPageFieldList;
    
    private Modeler modeler;
    
    /**
     * IOウェブページ操作リスナーインターフェース
     */
    public interface IOWebPageListener {
        void onIOWebPageChanged();
    }
    
    private IOWebPageListener ioWebPageListener;
    
    public IOWebPageView(Modeler modeler, IOWebPageListener listener) {
        this.modeler = modeler;
        this.ioWebPageListener = listener;
        jTableIOWebPageFieldList = modeler.jTableIOWebPageFieldList;
        initComponents();
    }
    
    public JPanel getPanel() {
        return jPanelIOWebPage;
    }
    
    public JTable getFieldList() {
        return jTableIOWebPageFieldList;
    }
    
    public JEditorPane getImagePreview() {
        return jEditorPaneIOWebPageImage;
    }
    
    /**
     * コンポーネントを初期化します。
     */
    private void initComponents() {
        jScrollPaneIOWebPageFieldList.getViewport().setBackground(SystemColor.control);
        jSplitPaneIOWebPage2.setOrientation(JSplitPane.VERTICAL_SPLIT);
        jSplitPaneIOWebPage2.setBorder(null);
        jSplitPaneIOWebPage2.add(jPanelIOWebPage1, JSplitPane.TOP);
        jSplitPaneIOWebPage2.add(jPanelIOWebPage5, JSplitPane.BOTTOM);
        jSplitPaneIOWebPage2.setDividerLocation(110);
        jEditorPaneIOWebPageImage.setBorder(BorderFactory.createRaisedBevelBorder());
        jEditorPaneIOWebPageImage.setEditable(false);
        jEditorPaneIOWebPageImage.setContentType("text/html");
        jPanelIOWebPage5.setBorder(BorderFactory.createEtchedBorder());
        jPanelIOWebPage5.setLayout(new BorderLayout());
        jScrollPaneIOWebPageImage.setBorder(BorderFactory.createLoweredBevelBorder());
        jPanelIOWebPage6.setMinimumSize(new Dimension(10, 14));
        jPanelIOWebPage6.setPreferredSize(new Dimension(10, 19));
        jPanelIOWebPage6.setLayout(new BorderLayout());
        jLabelIOWebPageImage.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jLabelIOWebPageImage.setText(res.getString("S766"));
        jSplitPaneIOWebPage1.setDividerLocation(750);
        jPanelIOWebPage.setLayout(new BorderLayout());
        jLabelIOWebPageName.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jLabelIOWebPageName.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelIOWebPageName.setText(res.getString("S768"));
        jLabelIOWebPageName.setBounds(new Rectangle(5, 8, 130, 25));
        jTextFieldIOWebPageName.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldIOWebPageName.setBounds(new Rectangle(0, 5, 250, 25));
        jLabelIOWebPageFileName.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jLabelIOWebPageFileName.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelIOWebPageFileName.setText(res.getString("S774"));
        jLabelIOWebPageFileName.setBounds(new Rectangle(505, 8, 130, 20));
        jTextFieldIOWebPageFileName.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldIOWebPageFileName.setBounds(new Rectangle(640, 5, 250, 25));
        jPanelIOWebPage1.setBorder(BorderFactory.createEtchedBorder());
        jPanelIOWebPage1.setPreferredSize(new Dimension(10, 100));
        jPanelIOWebPage1.setLayout(new BorderLayout());
        jPanelIOWebPage1.setMinimumSize(new Dimension(100, 34));
        jPanelIOWebPage3.setLayout(new BorderLayout());
        jPanelIOWebPage3.setPreferredSize(new Dimension(1000, 60));
        jPanelIOWebPage2.setLayout(null);
        jPanelIOWebPage2.setPreferredSize(new Dimension(140, 80));
        jPanelIOWebPage2.setMinimumSize(new Dimension(100, 10));
        jPanelIOWebPage4.setLayout(null);
        jPanelIOWebPage4.setPreferredSize(new Dimension(10, 37));
        jLabelIOWebPageDescriptions.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jLabelIOWebPageDescriptions.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelIOWebPageDescriptions.setText(res.getString("S204"));
        jLabelIOWebPageDescriptions.setBounds(new Rectangle(5, 39, 130, 20));
        jTextAreaIOWebPageDescriptions.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTextAreaIOWebPageDescriptions.setLineWrap(true);
        jScrollPaneIOWebPageFieldList.setBorder(BorderFactory.createLoweredBevelBorder());
        jSplitPaneIOWebPage1.setOrientation(JSplitPane.VERTICAL_SPLIT);
        jSplitPaneIOWebPage1.setBorder(null);
        jPanelIOWebPage.add(jSplitPaneIOWebPage1, BorderLayout.CENTER);
        jPanelIOWebPage3.add(jScrollPaneIOWebPageDescriptions, BorderLayout.CENTER);
        jPanelIOWebPage3.add(jPanelIOWebPage4, BorderLayout.NORTH);
        jScrollPaneIOWebPageDescriptions.getViewport().add(jTextAreaIOWebPageDescriptions, null);
        jPanelIOWebPage4.add(jTextFieldIOWebPageName, null);
        jPanelIOWebPage4.add(jTextFieldIOWebPageFileName, null);
        jPanelIOWebPage4.add(jLabelIOWebPageFileName, null);
        jPanelIOWebPage1.add(jPanelIOWebPage2, BorderLayout.WEST);
        jPanelIOWebPage1.add(jPanelIOWebPage3, BorderLayout.CENTER);
        jPanelIOWebPage2.add(jLabelIOWebPageName, null);
        jPanelIOWebPage2.add(jLabelIOWebPageDescriptions, null);
        jSplitPaneIOWebPage1.add(jSplitPaneIOWebPage2, JSplitPane.TOP);
        jSplitPaneIOWebPage1.add(jScrollPaneIOWebPageFieldList, JSplitPane.BOTTOM);
        jScrollPaneIOWebPageFieldList.getViewport().add(jTableIOWebPageFieldList, null);
        jPanelIOWebPage5.add(jScrollPaneIOWebPageImage, BorderLayout.CENTER);
        jScrollPaneIOWebPageImage.getViewport().add(jEditorPaneIOWebPageImage, null);
        jPanelIOWebPage5.add(jPanelIOWebPage6, BorderLayout.NORTH);
        jPanelIOWebPage6.add(jLabelIOWebPageImage, BorderLayout.CENTER);
        //(jTableIOWebPageFieldList)//
        jTableIOWebPageFieldList.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTableIOWebPageFieldList.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTableIOWebPageFieldList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTableIOWebPageFieldList.setRowHeight(Modeler.TABLE_ROW_HEIGHT_EDITABLE);
    }
}
