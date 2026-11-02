package xeadModeler;

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
    }

    /**
     * コンポーネントを初期化します。
     */

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

    /**
     * ModelerのSubsystemコンポーネントを初期化します。
     */
    public static void initComponents(Modeler m) {
        m.jPanelSubsystem.setLayout(new BorderLayout());
        m.jPanelSubsystem3.setLayout(null);
        m.jTextFieldSubsystemName.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jTextFieldSubsystemName.setBounds(new Rectangle(150, 6, 350, 25));
        m.jLabelSubsystemDescriptions1.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jLabelSubsystemDescriptions1.setMaximumSize(new Dimension(70, 15));
        m.jLabelSubsystemDescriptions1.setMinimumSize(new Dimension(70, 15));
        m.jLabelSubsystemDescriptions1.setPreferredSize(new Dimension(70, 15));
        m.jLabelSubsystemDescriptions1.setHorizontalAlignment(SwingConstants.LEFT);
        m.jLabelSubsystemDescriptions1.setText(res.getString("S204"));
        m.jLabelSubsystemDescriptions1.setBounds(new Rectangle(7, 0, 130, 22));
        m.jLabelSubsystemName.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jLabelSubsystemName.setHorizontalAlignment(SwingConstants.RIGHT);
        m.jLabelSubsystemName.setText(res.getString("S436"));
        m.jLabelSubsystemName.setBounds(new Rectangle(5, 9, 140, 20));
        m.jPanelSubsystem3.setPreferredSize(new Dimension(10, 37));
        m.jPanelSubsystem1.setBorder(BorderFactory.createEtchedBorder());
        m.jPanelSubsystem1.setPreferredSize(new Dimension(10, 80));
        m.jPanelSubsystem1.setLayout(new BorderLayout());
        m.jPanelSubsystem2.setLayout(null);
        m.jPanelSubsystem2.setPreferredSize(new Dimension(10, 22));
        m.jPanelSubsystem2.setMinimumSize(new Dimension(100, 10));
        m.jPanelSubsystem.add(m.jPanelSubsystem3, BorderLayout.NORTH);
        m.jPanelSubsystem3.add(m.jTextFieldSubsystemName, null);
        m.jPanelSubsystem3.add(m.jLabelSubsystemName, null);
        m.jPanelSubsystem3.add(m.jTextFieldSubsystemSortKey, null);
        m.jPanelSubsystem3.add(m.jLabelSubsysytemSortKey, null);
        m.jPanelSubsystem1.add(m.jPanelSubsystem2, BorderLayout.NORTH);
        m.jPanelSubsystem2.add(m.jLabelSubsystemDescriptions1, null);
        m.jPanelSubsystem.add(m.jPanelSubsystem1, BorderLayout.CENTER);
        m.jLabelSubsysytemSortKey.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jLabelSubsysytemSortKey.setHorizontalAlignment(SwingConstants.RIGHT);
        m.jLabelSubsysytemSortKey.setText(res.getString("S201"));
        m.jLabelSubsysytemSortKey.setBounds(new Rectangle(510, 9, 130, 20));
        m.jTextFieldSubsystemSortKey.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jTextFieldSubsystemSortKey.setBounds(new Rectangle(645, 6, 100, 25));
        m.jTextAreaSubsystemDescriptions.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jTextAreaSubsystemDescriptions.setLineWrap(true);
        m.jPanelSubsystem1.add(m.jScrollPaneSubsystemDescriptions, BorderLayout.CENTER);
        m.jScrollPaneSubsystemDescriptions.getViewport().add(m.jTextAreaSubsystemDescriptions, null);
    }
}
