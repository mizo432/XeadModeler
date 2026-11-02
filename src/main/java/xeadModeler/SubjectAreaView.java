package xeadModeler;

import xeadModeler.Modeler;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.util.ResourceBundle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseAdapter;

/**
 * 主題領域ノードのビュー
 * データフローエディタとスライドショーダイアログを含みます。
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class SubjectAreaView {
    private static final ResourceBundle res = ResourceBundle.getBundle("xeadModeler.Res");
    private static final String MAIN_FONT_NAME = "SansSerif";

    // Components
    private final JPanel jPanelSubjectArea = new JPanel();
    private final JSplitPane jSplitPaneSubjectArea = new JSplitPane();
    private final JScrollPane jScrollPaneSubjectArea = new JScrollPane();
    private final JPanel jPanelSubjectArea1 = new JPanel();
    private final JPanel jPanelSubjectArea2 = new JPanel();
    private final JPanel jPanelSubjectArea3 = new JPanel();
    private final JPanel jPanelSubjectArea4 = new JPanel();
    private final JPanel jPanelSubjectAreaDataflowEditor1 = new JPanel();
    private final JPanel jPanelSubjectAreaDataflowEditor2 = new JPanel();
    private final JScrollPane jScrollPaneSubjectAreaDescriptions = new JScrollPane();
    
    // Labels
    private final JLabel jLabelSubjectAreaName = new JLabel();
    private final JLabel jLabelSubjectAreaDescriptions = new JLabel();
    private final JLabel jLabelSubjectAreaSortKey = new JLabel();
    private final JLabel jLabelSubjectAreaBoundarySetting = new JLabel();
    
    // Fields
    private final JTextField jTextFieldSubjectAreaName = new JTextField();
    private final JTextField jTextFieldSubjectAreaSortKey = new JTextField();
    private final JTextArea jTextAreaSubjectAreaDescriptions = new JTextArea();
    private final JComboBox<String> jComboBoxSubjectAreaBoundarySetting = new JComboBox<>();
    
    // Boundary guides
    private final JPanel jPanelBoundaryResizeGuide = new JPanel();
    private final JPanel jPanelBoundaryMoveGuide = new JPanel();
    
    // Slide show dialog
    private final JDialog jDialogDataflowSlideShow = new JDialog();
    private final JPanel jPanelSubjectAreaDataflowSlideShow = new JPanel();
    private final JPanel jPanelSubjectAreaDataflowSlideShow1 = new JPanel();
    private final JPanel jPanelSubjectAreaDataflowSlideShow2 = new JPanel();
    private final JLabel jLabelDataflowSlideShowPageGuide = new JLabel();

    public SubjectAreaView() {
    }

    /**
     * コンポーネントを初期化します。
     */
    
    /**
     * スライドショーダイアログを初期化します。
     */

    public JPanel getPanel() {
        return jPanelSubjectArea;
    }

    public JSplitPane getSplitPane() {
        return jSplitPaneSubjectArea;
    }

    public JScrollPane getScrollPane() {
        return jScrollPaneSubjectArea;
    }

    public JPanel getDataflowEditorPanel1() {
        return jPanelSubjectAreaDataflowEditor1;
    }

    public JPanel getDataflowEditorPanel2() {
        return jPanelSubjectAreaDataflowEditor2;
    }

    public JTextField getNameField() {
        return jTextFieldSubjectAreaName;
    }

    public JTextField getSortKeyField() {
        return jTextFieldSubjectAreaSortKey;
    }

    public JTextArea getDescriptionsArea() {
        return jTextAreaSubjectAreaDescriptions;
    }

    public JComboBox<String> getBoundarySettingCombo() {
        return jComboBoxSubjectAreaBoundarySetting;
    }

    public JPanel getBoundaryResizeGuide() {
        return jPanelBoundaryResizeGuide;
    }

    public JPanel getBoundaryMoveGuide() {
        return jPanelBoundaryMoveGuide;
    }

    public JDialog getSlideShowDialog() {
        return jDialogDataflowSlideShow;
    }

    public JPanel getSlideShowPanel() {
        return jPanelSubjectAreaDataflowSlideShow;
    }

    public JPanel getSlideShowPanel1() {
        return jPanelSubjectAreaDataflowSlideShow1;
    }

    public JPanel getSlideShowPanel2() {
        return jPanelSubjectAreaDataflowSlideShow2;
    }

    public JLabel getSlideShowPageGuide() {
        return jLabelDataflowSlideShowPageGuide;
    }

    /**
     * ModelerのSubjectAreaコンポーネントを初期化します。
     */
    public void initComponents(Modeler m) {
        jSplitPaneSubjectArea.setOrientation(JSplitPane.VERTICAL_SPLIT);
        jSplitPaneSubjectArea.setBorder(null);
        jSplitPaneSubjectArea.setDividerLocation(90);
        jPanelSubjectArea.setLayout(new BorderLayout());
        jPanelSubjectArea.setBackground(SystemColor.control);
        jPanelSubjectArea1.setBorder(BorderFactory.createEtchedBorder());
        jPanelSubjectArea1.setPreferredSize(new Dimension(10, 100));
        jPanelSubjectArea1.setMinimumSize(new Dimension(128, 0));
        jPanelSubjectArea1.setLayout(new BorderLayout());
        jScrollPaneSubjectArea.setBorder(null);
        jPanelSubjectArea4.setMinimumSize(new Dimension(100, 10));
        jPanelSubjectArea4.setPreferredSize(new Dimension(140, 90));
        jPanelSubjectArea4.setLayout(null);
        jLabelSubjectAreaName.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jLabelSubjectAreaName.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelSubjectAreaName.setText(res.getString("S324"));
        jLabelSubjectAreaName.setBounds(new Rectangle(5, 6, 130, 20));
        jLabelSubjectAreaDescriptions.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jLabelSubjectAreaDescriptions.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelSubjectAreaDescriptions.setText(res.getString("S204"));
        jLabelSubjectAreaDescriptions.setBounds(new Rectangle(5, 37, 130, 20));
        jPanelSubjectArea2.setLayout(new BorderLayout());
        jPanelSubjectArea3.setPreferredSize(new Dimension(10, 38));
        jPanelSubjectArea3.setLayout(null);
        jPanelSubjectAreaDataflowEditor1.setBorder(null);
        jPanelSubjectAreaDataflowEditor1.setLayout(null);
        jPanelSubjectAreaDataflowEditor1.setBackground(m.DESKTOP_COLOR);
        jPanelSubjectAreaDataflowEditor2.setLayout(null);
        jPanelSubjectAreaDataflowEditor2.setOpaque(false);
        jPanelSubjectAreaDataflowEditor2.setBounds(new Rectangle(0, 0, 2000, 1500));
        jPanelSubjectAreaDataflowEditor2.addMouseListener(
            new MouseAdapter() { public void mousePressed(MouseEvent e) { m.jPanelSubjectAreaDataflowEditor2_mousePressed(e); } public void mouseReleased(MouseEvent e) { m.jPanelSubjectAreaDataflowEditor2_mouseReleased(e); } public void mouseClicked(MouseEvent e) { m.jPanelSubjectAreaDataflowEditor2_mouseClicked(e); } });
        jPanelSubjectAreaDataflowEditor2.addMouseMotionListener(
            new MouseMotionAdapter() { public void mouseMoved(MouseEvent e) { m.jPanelSubjectAreaDataflowEditor2_mouseMoved(e); } public void mouseDragged(MouseEvent e) { m.jPanelSubjectAreaDataflowEditor2_mouseDragged(e); } });
        jTextFieldSubjectAreaName.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldSubjectAreaName.setBounds(new Rectangle(0, 6, 300, 25));
        jLabelSubjectAreaSortKey.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jLabelSubjectAreaSortKey.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelSubjectAreaSortKey.setText(res.getString("S201"));
        jLabelSubjectAreaSortKey.setBounds(new Rectangle(310, 10, 130, 20));
        jTextFieldSubjectAreaSortKey.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldSubjectAreaSortKey.setBounds(new Rectangle(445, 6, 100, 25));
        jLabelSubjectAreaBoundarySetting.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jLabelSubjectAreaBoundarySetting.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelSubjectAreaBoundarySetting.setText(res.getString("S381"));
        jLabelSubjectAreaBoundarySetting.setBounds(new Rectangle(555, 10, 160, 20));
        jComboBoxSubjectAreaBoundarySetting.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jComboBoxSubjectAreaBoundarySetting.setBounds(new Rectangle(720, 6, 170, 25));
        jComboBoxSubjectAreaBoundarySetting.addItem(res.getString("S382")); //0:Hidden//
        jComboBoxSubjectAreaBoundarySetting.addItem(res.getString("S383")); //1:Top-Left//
        jComboBoxSubjectAreaBoundarySetting.addItem(res.getString("S384")); //2:Top-Right//
        jComboBoxSubjectAreaBoundarySetting.addItem(res.getString("S385")); //3:Bottom-Left//
        jComboBoxSubjectAreaBoundarySetting.addItem(res.getString("S386")); //4:Bottom-Right//
        jComboBoxSubjectAreaBoundarySetting.addItem(res.getString("S387")); //H:Border-Hidden//
        jComboBoxSubjectAreaBoundarySetting.addItemListener(
            e -> m.jComboBoxSubjectAreaBoundarySetting_itemChanged(e));
        jTextAreaSubjectAreaDescriptions.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        jTextAreaSubjectAreaDescriptions.setLineWrap(true);
        jPanelSubjectArea.add(m.jSplitPaneSubjectArea, BorderLayout.CENTER);
        jSplitPaneSubjectArea.add(m.jScrollPaneSubjectArea, JSplitPane.BOTTOM);
        jSplitPaneSubjectArea.add(m.jPanelSubjectArea1, JSplitPane.TOP);
        jPanelSubjectArea1.add(m.jPanelSubjectArea2, BorderLayout.CENTER);
        jPanelSubjectArea1.add(m.jPanelSubjectArea4, BorderLayout.WEST);
        jPanelSubjectArea2.add(m.jScrollPaneSubjectAreaDescriptions, BorderLayout.CENTER);
        jPanelSubjectArea2.add(m.jPanelSubjectArea3, BorderLayout.NORTH);
        jPanelSubjectArea3.add(m.jTextFieldSubjectAreaName, null);
        jPanelSubjectArea3.add(m.jLabelSubjectAreaSortKey, null);
        jPanelSubjectArea3.add(m.jTextFieldSubjectAreaSortKey, null);
        jPanelSubjectArea3.add(m.jLabelSubjectAreaBoundarySetting, null);
        jPanelSubjectArea3.add(m.jComboBoxSubjectAreaBoundarySetting, null);
        jScrollPaneSubjectAreaDescriptions.getViewport().add(m.jTextAreaSubjectAreaDescriptions, null);
        jScrollPaneSubjectArea.getViewport().add(m.jPanelSubjectAreaDataflowEditor1, null);
        jScrollPaneSubjectArea.getVerticalScrollBar().setUnitIncrement(25);
        jPanelSubjectAreaDataflowEditor1.add(m.jPanelSubjectAreaDataflowEditor2, null);
        jPanelSubjectArea4.add(m.jLabelSubjectAreaName, null);
        jPanelSubjectArea4.add(m.jLabelSubjectAreaDescriptions, null);
        jPanelBoundaryResizeGuide.setLayout(null);
        jPanelBoundaryResizeGuide.setBackground(Color.black);
        jPanelBoundaryMoveGuide.setLayout(null);
        jPanelBoundaryMoveGuide.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        jPanelBoundaryMoveGuide.setOpaque(false);
        //(Slide Show Screen Dialog)//
        jDialogDataflowSlideShow.setResizable(false);
        jDialogDataflowSlideShow.setFocusable(true);
        jDialogDataflowSlideShow.setUndecorated(true);
        jDialogDataflowSlideShow.addKeyListener(new KeyAdapter() { public void keyPressed(KeyEvent e) { m.jDialogDataflowSlideShow_keyPressed(e); } });
        jPanelSubjectAreaDataflowSlideShow.setLayout(null);
        jPanelSubjectAreaDataflowSlideShow.setBounds(new Rectangle(0, 0, 2000, 1500));
        jPanelSubjectAreaDataflowSlideShow.setBackground(m.DESKTOP_COLOR);
        jPanelSubjectAreaDataflowSlideShow.addMouseListener(
            new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jPanelSubjectAreaDataflowSlideShow2_mouseClicked(e); } });
        jPanelSubjectAreaDataflowSlideShow1.setBackground(m.DESKTOP_COLOR);
        jPanelSubjectAreaDataflowSlideShow1.setBounds(new Rectangle(10, 50, 2000, 1500));
        jPanelSubjectAreaDataflowSlideShow1.setLayout(null);
        jPanelSubjectAreaDataflowSlideShow2.setOpaque(false);
        jPanelSubjectAreaDataflowSlideShow2.setBounds(new Rectangle(0, 0, 2000, 1500));
        jPanelSubjectAreaDataflowSlideShow2.setLayout(null);
        jPanelSubjectAreaDataflowSlideShow2.addMouseListener(
            new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jPanelSubjectAreaDataflowSlideShow2_mouseClicked(e); } });
        jLabelDataflowSlideShowPageGuide.setFont(new java.awt.Font(m.mainFontName, 0, 17));
        jLabelDataflowSlideShowPageGuide.setForeground(Color.lightGray);
        jLabelDataflowSlideShowPageGuide.setBounds(new Rectangle(15, 15, 600, 20));
        jDialogDataflowSlideShow.getContentPane().add(m.jPanelSubjectAreaDataflowSlideShow, null);
        jPanelSubjectAreaDataflowSlideShow.add(m.jLabelDataflowSlideShowPageGuide, null);
        jPanelSubjectAreaDataflowSlideShow.add(m.jPanelSubjectAreaDataflowSlideShow1, null);
        jPanelSubjectAreaDataflowSlideShow1.add(m.jPanelSubjectAreaDataflowSlideShow2, null);
    }
}
