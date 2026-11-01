package xeadModeler.view;

import xeadModeler.Modeler;
import xeadModeler.KanjiTextField;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

/**
 * サブシステムノードのビュー
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class SubsystemView {
    private static final ResourceBundle res = ResourceBundle.getBundle("xeadModeler.Res");
    private static final String MAIN_FONT_NAME = "SansSerif";

    // Components
    private final JPanel jPanelSubsystem = new JPanel();
    private final JPanel jPanelSubsystem1 = new JPanel();
    private final JPanel jPanelSubsystem2 = new JPanel();
    private final JPanel jPanelSubsystem3 = new JPanel();
    
    // Fields
    private final KanjiTextField jTextFieldSubsystemName = new KanjiTextField();
    private final JLabel jLabelSubsystemDescriptions1 = new JLabel();
    private final JLabel jLabelSubsystemName = new JLabel();
    private final JLabel jLabelSubsysytemSortKey = new JLabel();
    private final JTextField jTextFieldSubsystemSortKey = new JTextField();
    private final JScrollPane jScrollPaneSubsystemDescriptions = new JScrollPane();
    private final JTextArea jTextAreaSubsystemDescriptions = new JTextArea();

    public SubsystemView() {
        initComponents();
    }

    /**
     * コンポーネントを初期化します。
     */
    private void initComponents() {
        jPanelSubsystem.setLayout(new BorderLayout());
        
        jPanelSubsystem3.setLayout(null);
        
        jTextFieldSubsystemName.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldSubsystemName.setBounds(new Rectangle(150, 6, 350, 25));
        
        jLabelSubsystemDescriptions1.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jLabelSubsystemDescriptions1.setMaximumSize(new Dimension(70, 15));
        jLabelSubsystemDescriptions1.setMinimumSize(new Dimension(70, 15));
        jLabelSubsystemDescriptions1.setPreferredSize(new Dimension(70, 15));
        jLabelSubsystemDescriptions1.setHorizontalAlignment(SwingConstants.LEFT);
        jLabelSubsystemDescriptions1.setText(res.getString("S204"));
        jLabelSubsystemDescriptions1.setBounds(new Rectangle(7, 0, 130, 22));
        
        jLabelSubsystemName.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jLabelSubsystemName.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelSubsystemName.setText(res.getString("S436"));
        jLabelSubsystemName.setBounds(new Rectangle(5, 9, 140, 20));
        
        jPanelSubsystem3.setPreferredSize(new Dimension(10, 37));
        
        jPanelSubsystem1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanelSubsystem1.setPreferredSize(new Dimension(10, 80));
        jPanelSubsystem1.setLayout(new BorderLayout());
        
        jPanelSubsystem2.setLayout(null);
        jPanelSubsystem2.setPreferredSize(new Dimension(10, 22));
        jPanelSubsystem2.setMinimumSize(new Dimension(100, 10));
        
        jLabelSubsysytemSortKey.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jLabelSubsysytemSortKey.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabelSubsysytemSortKey.setText(res.getString("S201"));
        jLabelSubsysytemSortKey.setBounds(new Rectangle(510, 9, 130, 20));
        
        jTextFieldSubsystemSortKey.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTextFieldSubsystemSortKey.setBounds(new Rectangle(645, 6, 100, 25));
        
        jTextAreaSubsystemDescriptions.setFont(new java.awt.Font(MAIN_FONT_NAME, 0, Modeler.MAIN_FONT_SIZE));
        jTextAreaSubsystemDescriptions.setLineWrap(true);
        
        // Assemble components
        jPanelSubsystem.add(jPanelSubsystem3, BorderLayout.NORTH);
        jPanelSubsystem3.add(jTextFieldSubsystemName, null);
        jPanelSubsystem3.add(jLabelSubsystemName, null);
        jPanelSubsystem3.add(jTextFieldSubsystemSortKey, null);
        jPanelSubsystem3.add(jLabelSubsysytemSortKey, null);
        
        jPanelSubsystem1.add(jPanelSubsystem2, BorderLayout.NORTH);
        jPanelSubsystem2.add(jLabelSubsystemDescriptions1, null);
        
        jPanelSubsystem.add(jPanelSubsystem1, BorderLayout.CENTER);
        jPanelSubsystem1.add(jScrollPaneSubsystemDescriptions, BorderLayout.CENTER);
        jScrollPaneSubsystemDescriptions.getViewport().add(jTextAreaSubsystemDescriptions, null);
    }

    public JPanel getPanel() {
        return jPanelSubsystem;
    }

    public JTextField getNameField() {
        return jTextFieldSubsystemName;
    }

    public JTextField getSortKeyField() {
        return jTextFieldSubsystemSortKey;
    }

    public JTextArea getDescriptionsArea() {
        return jTextAreaSubsystemDescriptions;
    }
}
