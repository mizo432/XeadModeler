package xeadModeler.view;

import xeadModeler.Modeler;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.text.JTextComponent;
import javax.swing.text.Keymap;
import java.awt.*;
import java.awt.event.*;
import java.util.ResourceBundle;

/**
 * IOパネルノードのビュー
 * IOパネル情報、フィールドリスト、イメージエディタなどを含みます。
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class IOPanelView {
    private static final ResourceBundle res = ResourceBundle.getBundle("xeadModeler.Res");
    private static final String MAIN_FONT_NAME = "SansSerif";

    // Components
    private final JPanel jPanelIOPanel = new JPanel();
    private final JPanel jPanelIOPanel1 = new JPanel();
    private final JPanel jPanelIOPanel2 = new JPanel();
    private final JPanel jPanelIOPanel3 = new JPanel();
    private final JPanel jPanelIOPanel4 = new JPanel();
    private final JPanel jPanelIOPanel5 = new JPanel();
    private final JPanel jPanelIOPanel6 = new JPanel();
    private final JPanel jPanelIOPanelImage = new JPanel();
    private final JSplitPane jSplitPaneIOPanel1 = new JSplitPane();
    private final JSplitPane jSplitPaneIOPanel2 = new JSplitPane();
    
    // Labels
    private final JLabel jLabelIOPanelName = new JLabel();
    private final JLabel jLabelIOPanelDescriptions = new JLabel();
    private final JLabel jLabelIOPanelImage = new JLabel();
    private final JLabel jLabelIOPanelCaretPosition = new JLabel();
    
    // Fields
    private final JTextField jTextFieldIOPanelName = new JTextField();
    
    // Descriptions
    private final JScrollPane jScrollPaneIOPanelDescriptions = new JScrollPane();
    private final JTextArea jTextAreaIOPanelDescriptions = new JTextArea();
    
    // Field list
    private final JScrollPane jScrollPaneIOPanelFieldList = new JScrollPane();
    private final JTable jTableIOPanelFieldList;
    
    // Image editor
    private final JScrollPane jScrollPaneIOPanelImage1 = new JScrollPane();
    private final JScrollPane jScrollPaneIOPanelImage2 = new JScrollPane();
    private final JTextPane jTextPaneIOPanelImage = new JTextPane();
    
    // State
    private int selectedRow_jTableIOPanelFieldList;
    
    private Modeler modeler;
    
    /**
     * IOパネル操作リスナーインターフェース
     */
    public interface IOPanelListener {
        void onIOPanelChanged();
    }
    
    private IOPanelListener ioPanelListener;
    
    public IOPanelView(Modeler modeler, IOPanelListener listener) {
        this.modeler = modeler;
        this.ioPanelListener = listener;
        jTableIOPanelFieldList = modeler.jTableIOPanelFieldList;
        initComponents();
    }
    
    public JPanel getPanel() {
        return jPanelIOPanel;
    }
    
    public JTable getFieldList() {
        return jTableIOPanelFieldList;
    }
    
    public JTextPane getImageEditor() {
        return jTextPaneIOPanelImage;
    }
    
    /**
     * コンポーネントを初期化します。
     */
    private void initComponents() {
        jScrollPaneIOPanelFieldList.getViewport().setBackground(SystemColor.control);
        jSplitPaneIOPanel2.setOrientation(JSplitPane.VERTICAL_SPLIT);
        jSplitPaneIOPanel2.setBorder(null);
        jSplitPaneIOPanel2.add(jPanelIOPanel1, JSplitPane.TOP);
        jSplitPaneIOPanel2.add(jPanelIOPanel5, JSplitPane.BOTTOM);
        jSplitPaneIOPanel2.setDividerLocation(110);
        jPanelIOPanelImage.setBackground(Modeler.DESKTOP_COLOR);
        jPanelIOPanelImage.setLayout(null);
        jScrollPaneIOPanelImage1.setBorder(null);
        jScrollPaneIOPanelImage1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPaneIOPanelImage1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        jTextPaneIOPanelImage.setBorder(BorderFactory.createRaisedBevelBorder());
        jTextPaneIOPanelImage.setBackground(SystemColor.control);
        jTextPaneIOPanelImage.setFont(new java.awt.Font("SansSerif", 0, 12));
        Keymap keymapIOPanelImage = jTextPaneIOPanelImage.getKeymap();
        JTextComponent.loadKeymap(keymapIOPanelImage, modeler.customBindings,
            jTextPaneIOPanelImage.getActions());
        jPanelIOPanel5.setBorder(BorderFactory.createEtchedBorder());
        jPanelIOPanel5.setLayout(new BorderLayout());
        jScrollPaneIOPanelImage2.setBorder(BorderFactory.createLoweredBevelBorder());
        jPanelIOPanel6.setMinimumSize(new Dimension(10, 14));
        jPanelIOPanel6.setPreferredSize(new Dimension(10, 21));
        jPanelIOPanel6.setLayout(new BorderLayout());
        jSplitPaneIOPanel1.setDividerLocation(750);
        jPanelIOPanel.setLayout(new BorderLayout());
        jLabelIOPanelName.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jLabelIOPanelName.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelIOPanelName.setText(res.getString("S703"));
        jLabelIOPanelName.setBounds(new Rectangle(5, 8, 130, 20));
        jTextFieldIOPanelName.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldIOPanelName.setBounds(new Rectangle(0, 5, 250, 25));
        jPanelIOPanel1.setBorder(BorderFactory.createEtchedBorder());
        jPanelIOPanel1.setPreferredSize(new Dimension(10, 100));
        jPanelIOPanel1.setLayout(new BorderLayout());
        jPanelIOPanel1.setMinimumSize(new Dimension(100, 34));
        jPanelIOPanel3.setLayout(new BorderLayout());
        jPanelIOPanel3.setPreferredSize(new Dimension(1000, 60));
        jPanelIOPanel2.setLayout(null);
        jPanelIOPanel2.setPreferredSize(new Dimension(140, 80));
        jPanelIOPanel2.setMinimumSize(new Dimension(100, 10));
        jPanelIOPanel4.setLayout(null);
        jPanelIOPanel4.setPreferredSize(new Dimension(10, 37));
        jLabelIOPanelDescriptions.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jLabelIOPanelDescriptions.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelIOPanelDescriptions.setText(res.getString("S204"));
        jLabelIOPanelDescriptions.setBounds(new Rectangle(5, 39, 130, 20));
        jTextAreaIOPanelDescriptions.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTextAreaIOPanelDescriptions.setLineWrap(true);
        jScrollPaneIOPanelFieldList.setBorder(BorderFactory.createLoweredBevelBorder());
        jSplitPaneIOPanel1.setOrientation(JSplitPane.VERTICAL_SPLIT);
        jSplitPaneIOPanel1.setBorder(null);
        jPanelIOPanel.add(jSplitPaneIOPanel1, BorderLayout.CENTER);
        jPanelIOPanel3.add(jScrollPaneIOPanelDescriptions, BorderLayout.CENTER);
        jPanelIOPanel3.add(jPanelIOPanel4, BorderLayout.NORTH);
        jScrollPaneIOPanelDescriptions.getViewport().add(jTextAreaIOPanelDescriptions, null);
        jPanelIOPanel4.add(jTextFieldIOPanelName, null);
        jPanelIOPanel1.add(jPanelIOPanel2, BorderLayout.WEST);
        jPanelIOPanel1.add(jPanelIOPanel3, BorderLayout.CENTER);
        jPanelIOPanel2.add(jLabelIOPanelName, null);
        jPanelIOPanel2.add(jLabelIOPanelDescriptions, null);
        jSplitPaneIOPanel1.add(jSplitPaneIOPanel2, JSplitPane.TOP);
        jSplitPaneIOPanel1.add(jScrollPaneIOPanelFieldList, JSplitPane.BOTTOM);
        jScrollPaneIOPanelFieldList.getViewport().add(jTableIOPanelFieldList, null);
        jPanelIOPanel5.add(jScrollPaneIOPanelImage2, BorderLayout.CENTER);
        jScrollPaneIOPanelImage1.getViewport().add(jTextPaneIOPanelImage, null);
        jScrollPaneIOPanelImage2.getViewport().add(jPanelIOPanelImage, null);
        jPanelIOPanel5.add(jPanelIOPanel6, BorderLayout.NORTH);
        jPanelIOPanel6.add(jLabelIOPanelImage, BorderLayout.CENTER);
        jPanelIOPanel6.add(jLabelIOPanelCaretPosition, BorderLayout.EAST);
        jLabelIOPanelImage.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jLabelIOPanelImage.setText(res.getString("S701"));
        jLabelIOPanelCaretPosition.setBorder(
            BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.white,
                UIManager.getColor("control"), Color.gray, UIManager.getColor("control")));
        jLabelIOPanelCaretPosition.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jLabelIOPanelCaretPosition.setPreferredSize(new Dimension(100, 18));
        jLabelIOPanelCaretPosition.setHorizontalAlignment(SwingConstants.CENTER);
        jLabelIOPanelCaretPosition.setText("0 - 0");
        //(jTableIOPanelFieldList)//
        jTableIOPanelFieldList.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTableIOPanelFieldList.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTableIOPanelFieldList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTableIOPanelFieldList.setRowHeight(Modeler.TABLE_ROW_HEIGHT_EDITABLE);
    }
}
