package xeadModeler;

import xeadModeler.Modeler;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.text.JTextComponent;
import javax.swing.text.Keymap;
import java.awt.*;
import java.awt.event.*;
import java.util.ResourceBundle;
import javax.swing.event.TableColumnModelListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseAdapter;

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

    /**
     * ModelerのIOPanelコンポーネントを初期化します。
     */
    public static void initComponents(Modeler m) {
        m.jScrollPaneIOPanelFieldList.getViewport().setBackground(SystemColor.control);
        m.jSplitPaneIOPanel2.setOrientation(JSplitPane.VERTICAL_SPLIT);
        m.jSplitPaneIOPanel2.setBorder(null);
        m.jSplitPaneIOPanel2.add(m.jPanelIOPanel1, JSplitPane.TOP);
        m.jSplitPaneIOPanel2.add(m.jPanelIOPanel5, JSplitPane.BOTTOM);
        m.jSplitPaneIOPanel2.setDividerLocation(110);
        m.jPanelIOPanelImage.setBackground(m.DESKTOP_COLOR);
        m.jPanelIOPanelImage.setLayout(null);
        m.jPanelIOPanelImage.addMouseListener(new MouseAdapter() { public void mousePressed(MouseEvent e) { m.jPanelIOPanelImage_mousePressed(e); } public void mouseClicked(MouseEvent e) { m.jPanelIOPanelImage_mouseClicked(e); } public void mouseReleased(MouseEvent e) { m.jPanelIOPanelImage_mouseReleased(e); } });
        m.jPanelIOPanelImage.addMouseMotionListener(
            new MouseMotionAdapter() { public void mouseMoved(MouseEvent e) { m.jPanelIOPanelImage_mouseMoved(e); } public void mouseDragged(MouseEvent e) { m.jPanelIOPanelImage_mouseDragged(e); } });
        m.jScrollPaneIOPanelImage1.setBorder(null);
        m.jScrollPaneIOPanelImage1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        m.jScrollPaneIOPanelImage1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        InputMap inputMap = m.jScrollPaneIOPanelImage1.getInputMap(
            JSplitPane.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        inputMap.clear();
        ActionMap actionMap = m.jScrollPaneIOPanelImage1.getActionMap();
        actionMap.clear();
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK), "UNDO");
        actionMap.put("UNDO", m.actionUndoIOPanelImage);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_Y, KeyEvent.CTRL_DOWN_MASK), "REDO");
        actionMap.put("REDO", m.actionRedoIOPanelImage);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_U, KeyEvent.CTRL_DOWN_MASK), "UNDERLINE");
        actionMap.put("UNDERLINE", m.actionUnderlineIOPanelImage);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_DOWN_MASK), "RESET");
        actionMap.put("RESET", m.actionResetAttrIOPanelImage);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, KeyEvent.CTRL_DOWN_MASK),
            "BACKGROUND_WHITE");
        actionMap.put("BACKGROUND_WHITE", m.actionChangeBackgroundToWhite);
        m.jTextPaneIOPanelImage.setBorder(BorderFactory.createRaisedBevelBorder());
        m.jTextPaneIOPanelImage.setBackground(SystemColor.control);
        m.jTextPaneIOPanelImage.addKeyListener(new KeyAdapter() { public void keyPressed(KeyEvent e) { m.jTextPaneIOPanelImage_keyPressed(e); } public void keyTyped(KeyEvent e) { m.jTextPaneIOPanelImage_keyTyped(e); } public void keyReleased(KeyEvent e) { m.jTextPaneIOPanelImage_keyReleased(e); } });
        m.jTextPaneIOPanelImage.addMouseListener(new MouseAdapter() { public void mousePressed(MouseEvent e) { m.jTextPaneIOPanelImage_mousePressed(e); } public void mouseClicked(MouseEvent e) { m.jTextPaneIOPanelImage_mouseClicked(e); } public void mouseReleased(MouseEvent e) { m.jTextPaneIOPanelImage_mouseReleased(e); } });
        m.jTextPaneIOPanelImage.addMouseMotionListener(
            new MouseMotionAdapter() { public void mouseDragged(MouseEvent e) { m.jTextPaneIOPanelImage_mouseDragged(e); } });
        m.jTextPaneIOPanelImage.addCaretListener(e -> m.jTextPaneIOPanelImage_caretUpdate(e));
        m.jTextPaneIOPanelImage.setFont(new java.awt.Font(m.ioImageFontName, 0, 12));
        Keymap keymapIOPanelImage = m.jTextPaneIOPanelImage.getKeymap();
        JTextComponent.loadKeymap(keymapIOPanelImage, m.customBindings,
            m.jTextPaneIOPanelImage.getActions());
        m.jPanelIOPanel5.setBorder(BorderFactory.createEtchedBorder());
        m.jPanelIOPanel5.setLayout(new BorderLayout());
        m.jScrollPaneIOPanelImage2.setBorder(BorderFactory.createLoweredBevelBorder());
        m.jPanelIOPanel6.setMinimumSize(new Dimension(10, 14));
        m.jPanelIOPanel6.setPreferredSize(new Dimension(10, 21));
        m.jPanelIOPanel6.setLayout(new BorderLayout());
        m.jSplitPaneIOPanel1.setDividerLocation(750);
        m.jPanelIOPanel.setLayout(new BorderLayout());
        m.jLabelIOPanelName.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jLabelIOPanelName.setHorizontalAlignment(SwingConstants.RIGHT);
        m.jLabelIOPanelName.setText(res.getString("S703"));
        m.jLabelIOPanelName.setBounds(new Rectangle(5, 8, 130, 20));
        m.jTextFieldIOPanelName.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jTextFieldIOPanelName.setBounds(new Rectangle(0, 5, 250, 25));
        //jLabelIOPanelSortKey.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        //jLabelIOPanelSortKey.setHorizontalAlignment(SwingConstants.RIGHT);
        //jLabelIOPanelSortKey.setText(res.getString("S201"));
        //jLabelIOPanelSortKey.setBounds(new Rectangle(260, 8, 130, 20));
        //jTextFieldIOPanelSortKey.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        //jTextFieldIOPanelSortKey.setBounds(new Rectangle(395, 5, 100, 25));
        m.jPanelIOPanel1.setBorder(BorderFactory.createEtchedBorder());
        m.jPanelIOPanel1.setPreferredSize(new Dimension(10, 100));
        m.jPanelIOPanel1.setLayout(new BorderLayout());
        m.jPanelIOPanel1.setMinimumSize(new Dimension(100, 34));
        m.jPanelIOPanel3.setLayout(new BorderLayout());
        m.jPanelIOPanel3.setPreferredSize(new Dimension(1000, 60));
        m.jPanelIOPanel2.setLayout(null);
        m.jPanelIOPanel2.setPreferredSize(new Dimension(140, 80));
        m.jPanelIOPanel2.setMinimumSize(new Dimension(100, 10));
        m.jPanelIOPanel4.setLayout(null);
        m.jPanelIOPanel4.setPreferredSize(new Dimension(10, 37));
        m.jLabelIOPanelDescriptions.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jLabelIOPanelDescriptions.setHorizontalAlignment(SwingConstants.RIGHT);
        m.jLabelIOPanelDescriptions.setText(res.getString("S204"));
        m.jLabelIOPanelDescriptions.setBounds(new Rectangle(5, 39, 130, 20));
        m.jTextAreaIOPanelDescriptions.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jTextAreaIOPanelDescriptions.setLineWrap(true);
        m.jScrollPaneIOPanelFieldList.setBorder(BorderFactory.createLoweredBevelBorder());
        m.jSplitPaneIOPanel1.setOrientation(JSplitPane.VERTICAL_SPLIT);
        m.jSplitPaneIOPanel1.setBorder(null);
        m.jPanelIOPanel.add(m.jSplitPaneIOPanel1, BorderLayout.CENTER);
        m.jPanelIOPanel3.add(m.jScrollPaneIOPanelDescriptions, BorderLayout.CENTER);
        m.jPanelIOPanel3.add(m.jPanelIOPanel4, BorderLayout.NORTH);
        m.jScrollPaneIOPanelDescriptions.getViewport().add(m.jTextAreaIOPanelDescriptions, null);
        m.jPanelIOPanel4.add(m.jTextFieldIOPanelName, null);
        //jPanelIOPanel4.add(jTextFieldIOPanelSortKey, null);
        //jPanelIOPanel4.add(jLabelIOPanelSortKey, null);
        m.jPanelIOPanel1.add(m.jPanelIOPanel2, BorderLayout.WEST);
        m.jPanelIOPanel1.add(m.jPanelIOPanel3, BorderLayout.CENTER);
        m.jPanelIOPanel2.add(m.jLabelIOPanelName, null);
        m.jPanelIOPanel2.add(m.jLabelIOPanelDescriptions, null);
        m.jSplitPaneIOPanel1.add(m.jSplitPaneIOPanel2, JSplitPane.TOP);
        m.jSplitPaneIOPanel1.add(m.jScrollPaneIOPanelFieldList, JSplitPane.BOTTOM);
        m.jScrollPaneIOPanelFieldList.getViewport().add(m.jTableIOPanelFieldList, null);
        m.jScrollPaneIOPanelFieldList.addMouseListener(
            new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jScrollPaneIOPanelFieldList_mouseClicked(e); } });
        m.jTableIOPanelFieldList.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jTableIOPanelFieldList_mouseClicked(e); } });
        m.jTableIOPanelFieldList.addFocusListener(new Modeler_FocusListener());
        m.jPanelIOPanel5.add(m.jScrollPaneIOPanelImage2, BorderLayout.CENTER);
        m.jScrollPaneIOPanelImage1.getViewport().add(m.jTextPaneIOPanelImage, null);
        m.jScrollPaneIOPanelImage2.getViewport().add(m.jPanelIOPanelImage, null);
        m.jPanelIOPanel5.add(m.jPanelIOPanel6, BorderLayout.NORTH);
        m.jPanelIOPanel6.add(m.jLabelIOPanelImage, BorderLayout.CENTER);
        m.jPanelIOPanel6.add(m.jLabelIOPanelCaretPosition, BorderLayout.EAST);
        m.jLabelIOPanelImage.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jLabelIOPanelImage.setText(res.getString("S701"));
        m.jLabelIOPanelCaretPosition.setBorder(
            BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.white,
                UIManager.getColor("control"), Color.gray, UIManager.getColor("control")));
        m.jLabelIOPanelCaretPosition.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jLabelIOPanelCaretPosition.setPreferredSize(new Dimension(100, 18));
        m.jLabelIOPanelCaretPosition.setHorizontalAlignment(SwingConstants.CENTER);
        m.jLabelIOPanelCaretPosition.setText("0 - 0");
        //(jTableIOPanelFieldList)//
        m.jTableIOPanelFieldList.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jTableIOPanelFieldList.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        m.jTableIOPanelFieldList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        m.jTableIOPanelFieldList.setRowHeight(m.TABLE_ROW_HEIGHT_EDITABLE);
        m.jTableIOPanelFieldList.getSelectionModel()
            .addListSelectionListener(e -> m.jTableIOPanelFieldList_valueChanged(e));
        m.jTableIOPanelFieldList.getColumnModel()
            .addColumnModelListener(new TableColumnModelListener() { public void columnSelectionChanged(javax.swing.event.ListSelectionEvent e) { m.jTableIOPanelFieldList_columnSelectionChanged(e); } public void columnMarginChanged(javax.swing.event.ChangeEvent e) {} public void columnMoved(javax.swing.event.TableColumnModelEvent e) {} public void columnRemoved(javax.swing.event.TableColumnModelEvent e) {} public void columnAdded(javax.swing.event.TableColumnModelEvent e) {} });
    }
}
