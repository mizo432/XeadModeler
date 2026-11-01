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
 * IOプールノードのビュー
 * IOプール情報、フィールドリスト、イメージエディタなどを含みます。
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class IOSpoolView {
    private static final ResourceBundle res = ResourceBundle.getBundle("xeadModeler.Res");
    private static final String MAIN_FONT_NAME = "SansSerif";

    // Components
    private final JPanel jPanelIOSpool = new JPanel();
    private final JPanel jPanelIOSpool1 = new JPanel();
    private final JPanel jPanelIOSpool2 = new JPanel();
    private final JPanel jPanelIOSpool3 = new JPanel();
    private final JPanel jPanelIOSpool4 = new JPanel();
    private final JPanel jPanelIOSpool5 = new JPanel();
    private final JPanel jPanelIOSpool6 = new JPanel();
    private final JPanel jPanelIOSpoolImage = new JPanel();
    private final JSplitPane jSplitPaneIOSpool1 = new JSplitPane();
    private final JSplitPane jSplitPaneIOSpool2 = new JSplitPane();
    
    // Labels
    private final JLabel jLabelIOSpoolName = new JLabel();
    private final JLabel jLabelIOSpoolDescriptions = new JLabel();
    private final JLabel jLabelIOSpoolImage = new JLabel();
    private final JLabel jLabelIOSpoolCaretPosition = new JLabel();
    
    // Fields
    private final JTextField jTextFieldIOSpoolName = new JTextField();
    
    // Descriptions
    private final JScrollPane jScrollPaneIOSpoolDescriptions = new JScrollPane();
    private final JTextArea jTextAreaIOSpoolDescriptions = new JTextArea();
    
    // Field list
    private final JScrollPane jScrollPaneIOSpoolFieldList = new JScrollPane();
    private final JTable jTableIOSpoolFieldList;
    
    // Image editor
    private final JScrollPane jScrollPaneIOSpoolImage1 = new JScrollPane();
    private final JScrollPane jScrollPaneIOSpoolImage2 = new JScrollPane();
    private final JTextPane jTextPaneIOSpoolImage = new JTextPane();
    
    // State
    private int selectedRow_jTableIOSpoolFieldList;
    
    private Modeler modeler;
    
    /**
     * IOプール操作リスナーインターフェース
     */
    public interface IOSpoolListener {
        void onIOSpoolChanged();
    }
    
    private IOSpoolListener ioSpoolListener;
    
    public IOSpoolView(Modeler modeler, IOSpoolListener listener) {
        this.modeler = modeler;
        this.ioSpoolListener = listener;
        jTableIOSpoolFieldList = modeler.jTableIOSpoolFieldList;
        initComponents();
    }
    
    public JPanel getPanel() {
        return jPanelIOSpool;
    }
    
    public JTable getFieldList() {
        return jTableIOSpoolFieldList;
    }
    
    public JTextPane getImageEditor() {
        return jTextPaneIOSpoolImage;
    }
    
    /**
     * コンポーネントを初期化します。
     */
    private void initComponents() {
        jSplitPaneIOSpool2.setOrientation(JSplitPane.VERTICAL_SPLIT);
        jSplitPaneIOSpool2.setBorder(null);
        jSplitPaneIOSpool2.add(jPanelIOSpool1, JSplitPane.TOP);
        jSplitPaneIOSpool2.add(jPanelIOSpool5, JSplitPane.BOTTOM);
        jSplitPaneIOSpool2.setDividerLocation(110);
        jPanelIOSpoolImage.setBackground(Modeler.DESKTOP_COLOR);
        jPanelIOSpoolImage.setLayout(null);
        jTextPaneIOSpoolImage.setBackground(SystemColor.control);
        jTextPaneIOSpoolImage.setBorder(null);
        jTextPaneIOSpoolImage.setFont(new java.awt.Font("SansSerif", 0, 12));
        Keymap keymapIOSpoolImage = jTextPaneIOSpoolImage.getKeymap();
        javax.swing.ActionMap actionMap = jTextPaneIOSpoolImage.getActionMap();
        javax.swing.InputMap inputMap = jTextPaneIOSpoolImage.getInputMap();
        JTextComponent.loadKeymap(keymapIOSpoolImage, modeler.customBindings,
            jTextPaneIOSpoolImage.getActions());
        jPanelIOSpool.setLayout(new BorderLayout());
        jLabelIOSpoolName.setBounds(new Rectangle(5, 8, 130, 25));
        jLabelIOSpoolName.setText(res.getString("S717"));
        jLabelIOSpoolName.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelIOSpoolName.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldIOSpoolName.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldIOSpoolName.setBounds(new Rectangle(0, 5, 250, 25));
        jPanelIOSpool1.setLayout(new BorderLayout());
        jPanelIOSpool1.setPreferredSize(new Dimension(10, 100));
        jPanelIOSpool1.setBorder(BorderFactory.createEtchedBorder());
        jPanelIOSpool1.setMinimumSize(new Dimension(100, 34));
        jPanelIOSpool3.setLayout(new BorderLayout());
        jPanelIOSpool3.setPreferredSize(new Dimension(1000, 60));
        jPanelIOSpool2.setLayout(null);
        jPanelIOSpool2.setPreferredSize(new Dimension(140, 80));
        jPanelIOSpool2.setMinimumSize(new Dimension(100, 10));
        jPanelIOSpool4.setLayout(null);
        jPanelIOSpool4.setPreferredSize(new Dimension(10, 37));
        jLabelIOSpoolDescriptions.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jLabelIOSpoolDescriptions.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelIOSpoolDescriptions.setText(res.getString("S204"));
        jLabelIOSpoolDescriptions.setBounds(new Rectangle(5, 39, 130, 20));
        jTextAreaIOSpoolDescriptions.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTextAreaIOSpoolDescriptions.setLineWrap(true);
        jScrollPaneIOSpoolFieldList.setBorder(BorderFactory.createLoweredBevelBorder());
        jSplitPaneIOSpool1.setOrientation(JSplitPane.VERTICAL_SPLIT);
        jSplitPaneIOSpool1.setBorder(null);
        jPanelIOSpool6.setMinimumSize(new Dimension(10, 14));
        jPanelIOSpool6.setPreferredSize(new Dimension(10, 21));
        jPanelIOSpool6.setLayout(new BorderLayout());
        jScrollPaneIOSpoolImage1.setBorder(null);
        jScrollPaneIOSpoolImage1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPaneIOSpoolImage1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        jScrollPaneIOSpoolImage2.setBorder(BorderFactory.createLoweredBevelBorder());
        jLabelIOSpoolImage.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jLabelIOSpoolImage.setText(res.getString("S727"));
        jPanelIOSpool5.setBorder(BorderFactory.createEtchedBorder());
        jPanelIOSpool5.setLayout(new BorderLayout());
        jPanelIOSpool2.add(jLabelIOSpoolName, null);
        jPanelIOSpool2.add(jLabelIOSpoolDescriptions, null);
        jPanelIOSpool1.add(jPanelIOSpool3, BorderLayout.CENTER);
        jPanelIOSpool1.add(jPanelIOSpool2, BorderLayout.WEST);
        jPanelIOSpool3.add(jScrollPaneIOSpoolDescriptions, BorderLayout.CENTER);
        jPanelIOSpool3.add(jPanelIOSpool4, BorderLayout.NORTH);
        jScrollPaneIOSpoolDescriptions.getViewport().add(jTextAreaIOSpoolDescriptions, null);
        jPanelIOSpool4.add(jTextFieldIOSpoolName, null);
        jPanelIOSpool.add(jSplitPaneIOSpool1, BorderLayout.CENTER);
        jSplitPaneIOSpool1.add(jSplitPaneIOSpool2, JSplitPane.TOP);
        jSplitPaneIOSpool1.add(jScrollPaneIOSpoolFieldList, JSplitPane.BOTTOM);
        jScrollPaneIOSpoolFieldList.getViewport().add(jTableIOSpoolFieldList, null);
        jPanelIOSpool5.add(jScrollPaneIOSpoolImage2, BorderLayout.CENTER);
        jScrollPaneIOSpoolImage1.getViewport().add(jTextPaneIOSpoolImage, null);
        jScrollPaneIOSpoolImage2.getViewport().add(jPanelIOSpoolImage, null);
        jPanelIOSpool5.add(jPanelIOSpool6, BorderLayout.NORTH);
        jPanelIOSpool6.add(jLabelIOSpoolImage, BorderLayout.CENTER);
        jPanelIOSpool6.add(jLabelIOSpoolCaretPosition, BorderLayout.EAST);
        jSplitPaneIOSpool1.setDividerLocation(750);
        jLabelIOSpoolCaretPosition.setBorder(
            BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.white,
                UIManager.getColor("control"), Color.gray, UIManager.getColor("control")));
        jLabelIOSpoolCaretPosition.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jLabelIOSpoolCaretPosition.setPreferredSize(new Dimension(100, 19));
        jLabelIOSpoolCaretPosition.setHorizontalAlignment(SwingConstants.CENTER);
        jLabelIOSpoolCaretPosition.setText("0 - 0");
        //(jTableIOSpoolFieldList)//
        jTableIOSpoolFieldList.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTableIOSpoolFieldList.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTableIOSpoolFieldList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTableIOSpoolFieldList.setRowHeight(Modeler.TABLE_ROW_HEIGHT_EDITABLE);
    }
}
