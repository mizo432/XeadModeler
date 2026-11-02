package xeadModeler;

import xeadModeler.Modeler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ResourceBundle;
import javax.swing.event.TableColumnModelListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

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

    /**
     * ModelerのIOWebPageコンポーネントを初期化します。
     */
    public static void initComponents(Modeler m) {
        m.jScrollPaneIOWebPageFieldList.getViewport().setBackground(SystemColor.control);
        m.jSplitPaneIOWebPage2.setOrientation(JSplitPane.VERTICAL_SPLIT);
        m.jSplitPaneIOWebPage2.setBorder(null);
        m.jSplitPaneIOWebPage2.add(m.jPanelIOWebPage1, JSplitPane.TOP);
        m.jSplitPaneIOWebPage2.add(m.jPanelIOWebPage5, JSplitPane.BOTTOM);
        m.jSplitPaneIOWebPage2.setDividerLocation(110);
        m.jEditorPaneIOWebPageImage.setBorder(BorderFactory.createRaisedBevelBorder());
        m.jEditorPaneIOWebPageImage.addMouseListener(
            new MouseAdapter() { public void mouseReleased(MouseEvent e) { m.jEditorPaneIOWebPageImage_mouseReleased(e); } });
        m.jEditorPaneIOWebPageImage.setEditable(false);
        m.jEditorPaneIOWebPageImage.setContentType("text/html");
        m.jPanelIOWebPage5.setBorder(BorderFactory.createEtchedBorder());
        m.jPanelIOWebPage5.setLayout(new BorderLayout());
        m.jScrollPaneIOWebPageImage.setBorder(BorderFactory.createLoweredBevelBorder());
        m.jPanelIOWebPage6.setMinimumSize(new Dimension(10, 14));
        m.jPanelIOWebPage6.setPreferredSize(new Dimension(10, 19));
        m.jPanelIOWebPage6.setLayout(new BorderLayout());
        m.jLabelIOWebPageImage.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jLabelIOWebPageImage.setText(res.getString("S766"));
        m.jSplitPaneIOWebPage1.setDividerLocation(750);
        m.jPanelIOWebPage.setLayout(new BorderLayout());
        m.jLabelIOWebPageName.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jLabelIOWebPageName.setHorizontalAlignment(SwingConstants.RIGHT);
        m.jLabelIOWebPageName.setText(res.getString("S768"));
        m.jLabelIOWebPageName.setBounds(new Rectangle(5, 8, 130, 25));
        m.jTextFieldIOWebPageName.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jTextFieldIOWebPageName.setBounds(new Rectangle(0, 5, 250, 25));
        //m.jLabelIOWebPageSortKey.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        //m.jLabelIOWebPageSortKey.setHorizontalAlignment(SwingConstants.RIGHT);
        //m.jLabelIOWebPageSortKey.setText(res.getString("S201"));
        //m.jLabelIOWebPageSortKey.setBounds(new Rectangle(260, 8, 130, 20));
        //m.jTextFieldIOWebPageSortKey.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        //m.jTextFieldIOWebPageSortKey.setBounds(new Rectangle(395, 5, 100, 25));
        m.jLabelIOWebPageFileName.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jLabelIOWebPageFileName.setHorizontalAlignment(SwingConstants.RIGHT);
        m.jLabelIOWebPageFileName.setText(res.getString("S774"));
        m.jLabelIOWebPageFileName.setBounds(new Rectangle(505, 8, 130, 20));
        m.jTextFieldIOWebPageFileName.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jTextFieldIOWebPageFileName.setBounds(new Rectangle(640, 5, 250, 25));
        m.jPanelIOWebPage1.setBorder(BorderFactory.createEtchedBorder());
        m.jPanelIOWebPage1.setPreferredSize(new Dimension(10, 100));
        m.jPanelIOWebPage1.setLayout(new BorderLayout());
        m.jPanelIOWebPage1.setMinimumSize(new Dimension(100, 34));
        m.jPanelIOWebPage3.setLayout(new BorderLayout());
        m.jPanelIOWebPage3.setPreferredSize(new Dimension(1000, 60));
        m.jPanelIOWebPage2.setLayout(null);
        m.jPanelIOWebPage2.setPreferredSize(new Dimension(140, 80));
        m.jPanelIOWebPage2.setMinimumSize(new Dimension(100, 10));
        m.jPanelIOWebPage4.setLayout(null);
        m.jPanelIOWebPage4.setPreferredSize(new Dimension(10, 37));
        m.jLabelIOWebPageDescriptions.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jLabelIOWebPageDescriptions.setHorizontalAlignment(SwingConstants.RIGHT);
        m.jLabelIOWebPageDescriptions.setText(res.getString("S204"));
        m.jLabelIOWebPageDescriptions.setBounds(new Rectangle(5, 39, 130, 20));
        m.jTextAreaIOWebPageDescriptions.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jTextAreaIOWebPageDescriptions.setLineWrap(true);
        m.jScrollPaneIOWebPageFieldList.setBorder(BorderFactory.createLoweredBevelBorder());
        m.jSplitPaneIOWebPage1.setOrientation(JSplitPane.VERTICAL_SPLIT);
        m.jSplitPaneIOWebPage1.setBorder(null);
        m.jPanelIOWebPage.add(m.jSplitPaneIOWebPage1, BorderLayout.CENTER);
        m.jPanelIOWebPage3.add(m.jScrollPaneIOWebPageDescriptions, BorderLayout.CENTER);
        m.jPanelIOWebPage3.add(m.jPanelIOWebPage4, BorderLayout.NORTH);
        m.jScrollPaneIOWebPageDescriptions.getViewport().add(m.jTextAreaIOWebPageDescriptions, null);
        m.jPanelIOWebPage4.add(m.jTextFieldIOWebPageName, null);
        //m.jPanelIOWebPage4.add(m.jTextFieldIOWebPageSortKey, null);
        //m.jPanelIOWebPage4.add(m.jLabelIOWebPageSortKey, null);
        m.jPanelIOWebPage4.add(m.jTextFieldIOWebPageFileName, null);
        m.jPanelIOWebPage4.add(m.jLabelIOWebPageFileName, null);
        m.jPanelIOWebPage1.add(m.jPanelIOWebPage2, BorderLayout.WEST);
        m.jPanelIOWebPage1.add(m.jPanelIOWebPage3, BorderLayout.CENTER);
        m.jPanelIOWebPage2.add(m.jLabelIOWebPageName, null);
        m.jPanelIOWebPage2.add(m.jLabelIOWebPageDescriptions, null);
        m.jSplitPaneIOWebPage1.add(m.jSplitPaneIOWebPage2, JSplitPane.TOP);
        m.jSplitPaneIOWebPage1.add(m.jScrollPaneIOWebPageFieldList, JSplitPane.BOTTOM);
        m.jScrollPaneIOWebPageFieldList.getViewport().add(m.jTableIOWebPageFieldList, null);
        m.jScrollPaneIOWebPageFieldList.addMouseListener(
            new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jScrollPaneIOWebPageFieldList_mouseClicked(e); } });
        m.jTableIOWebPageFieldList.addMouseListener(
            new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jTableIOWebPageFieldList_mouseClicked(e); } });
        m.jPanelIOWebPage5.add(m.jScrollPaneIOWebPageImage, BorderLayout.CENTER);
        m.jScrollPaneIOWebPageImage.getViewport().add(m.jEditorPaneIOWebPageImage, null);
        m.jPanelIOWebPage5.add(m.jPanelIOWebPage6, BorderLayout.NORTH);
        m.jPanelIOWebPage6.add(m.jLabelIOWebPageImage, BorderLayout.CENTER);
        //(m.jTableIOWebPageFieldList)//
        m.jTableIOWebPageFieldList.addFocusListener(new Modeler_FocusListener());
        m.jTableIOWebPageFieldList.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jTableIOWebPageFieldList.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        m.jTableIOWebPageFieldList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        m.jTableIOWebPageFieldList.setRowHeight(m.TABLE_ROW_HEIGHT_EDITABLE);
        m.jTableIOWebPageFieldList.getSelectionModel()
            .addListSelectionListener(e -> m.jTableIOWebPageFieldList_valueChanged(e));
        m.jTableIOWebPageFieldList.getColumnModel()
            .addColumnModelListener(new TableColumnModelListener() { public void columnSelectionChanged(javax.swing.event.ListSelectionEvent e) { m.jTableIOWebPageFieldList_columnSelectionChanged(e); } public void columnMarginChanged(javax.swing.event.ChangeEvent e) {} public void columnMoved(javax.swing.event.TableColumnModelEvent e) {} public void columnRemoved(javax.swing.event.TableColumnModelEvent e) {} public void columnAdded(javax.swing.event.TableColumnModelEvent e) {} });
    }
}
