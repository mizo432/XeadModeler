package xeadModeler;

import xeadModeler.Modeler;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
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

    /**
     * ModelerのIOSpoolコンポーネントを初期化します。
     */
    public static void initComponents(Modeler m) {
        m.jSplitPaneIOSpool2.setOrientation(JSplitPane.VERTICAL_SPLIT);
        m.jSplitPaneIOSpool2.setBorder(null);
        m.jSplitPaneIOSpool2.add(m.jPanelIOSpool1, JSplitPane.TOP);
        m.jSplitPaneIOSpool2.add(m.jPanelIOSpool5, JSplitPane.BOTTOM);
        m.jSplitPaneIOSpool2.setDividerLocation(110);
        m.jPanelIOSpoolImage.setBackground(m.DESKTOP_COLOR);
        m.jPanelIOSpoolImage.setLayout(null);
        m.jPanelIOSpoolImage.addMouseListener(new MouseAdapter() { public void mousePressed(MouseEvent e) { m.jPanelIOSpoolImage_mousePressed(e); } public void mouseClicked(MouseEvent e) { m.jPanelIOSpoolImage_mouseClicked(e); } public void mouseReleased(MouseEvent e) { m.jPanelIOSpoolImage_mouseReleased(e); } });
        m.jPanelIOSpoolImage.addMouseMotionListener(
            new MouseMotionAdapter() { public void mouseMoved(MouseEvent e) { m.jPanelIOSpoolImage_mouseMoved(e); } public void mouseDragged(MouseEvent e) { m.jPanelIOSpoolImage_mouseDragged(e); } });
        m.jTextPaneIOSpoolImage.setBackground(SystemColor.control);
        m.jTextPaneIOSpoolImage.setBorder(null);
        m.jTextPaneIOSpoolImage.setFont(new java.awt.Font(m.ioImageFontName, 0, 12));
        m.jTextPaneIOSpoolImage.addKeyListener(new KeyAdapter() { public void keyPressed(KeyEvent e) { m.jTextPaneIOSpoolImage_keyPressed(e); } public void keyTyped(KeyEvent e) { m.jTextPaneIOSpoolImage_keyTyped(e); } public void keyReleased(KeyEvent e) { m.jTextPaneIOSpoolImage_keyReleased(e); } });
        m.jTextPaneIOSpoolImage.addMouseListener(new MouseAdapter() { public void mousePressed(MouseEvent e) { m.jTextPaneIOSpoolImage_mousePressed(e); } public void mouseClicked(MouseEvent e) { m.jTextPaneIOSpoolImage_mouseClicked(e); } public void mouseReleased(MouseEvent e) { m.jTextPaneIOSpoolImage_mouseReleased(e); } });
        m.jTextPaneIOSpoolImage.addMouseMotionListener(
            new MouseMotionAdapter() { public void mouseDragged(MouseEvent e) { m.jTextPaneIOSpoolImage_mouseDragged(e); } });
        m.jTextPaneIOSpoolImage.addCaretListener(e -> m.jTextPaneIOSpoolImage_caretUpdate(e));
        Keymap keymapIOSpoolImage = m.jTextPaneIOSpoolImage.getKeymap();
        JTextComponent.loadKeymap(keymapIOSpoolImage, m.customBindings,
            m.jTextPaneIOSpoolImage.getActions());
        m.jPanelIOSpool.setLayout(new BorderLayout());
        m.jLabelIOSpoolName.setBounds(new Rectangle(5, 8, 130, 25));
        m.jLabelIOSpoolName.setText(res.getString("S717"));
        m.jLabelIOSpoolName.setHorizontalAlignment(SwingConstants.RIGHT);
        m.jLabelIOSpoolName.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jTextFieldIOSpoolName.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jTextFieldIOSpoolName.setBounds(new Rectangle(0, 5, 250, 25));
        m.jPanelIOSpool1.setLayout(new BorderLayout());
        m.jPanelIOSpool1.setPreferredSize(new Dimension(10, 100));
        m.jPanelIOSpool1.setBorder(BorderFactory.createEtchedBorder());
        m.jPanelIOSpool1.setMinimumSize(new Dimension(100, 34));
        m.jPanelIOSpool3.setLayout(new BorderLayout());
        m.jPanelIOSpool3.setPreferredSize(new Dimension(1000, 60));
        m.jPanelIOSpool2.setLayout(null);
        m.jPanelIOSpool2.setPreferredSize(new Dimension(140, 80));
        m.jPanelIOSpool2.setMinimumSize(new Dimension(100, 10));
        m.jPanelIOSpool4.setLayout(null);
        m.jPanelIOSpool4.setPreferredSize(new Dimension(10, 37));
        m.jLabelIOSpoolDescriptions.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jLabelIOSpoolDescriptions.setHorizontalAlignment(SwingConstants.RIGHT);
        m.jLabelIOSpoolDescriptions.setText(res.getString("S204"));
        m.jLabelIOSpoolDescriptions.setBounds(new Rectangle(5, 39, 130, 20));
        m.jTextAreaIOSpoolDescriptions.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jTextAreaIOSpoolDescriptions.setLineWrap(true);
        m.jScrollPaneIOSpoolFieldList.setBorder(BorderFactory.createLoweredBevelBorder());
        m.jSplitPaneIOSpool1.setOrientation(JSplitPane.VERTICAL_SPLIT);
        m.jSplitPaneIOSpool1.setBorder(null);
        m.jPanelIOSpool6.setMinimumSize(new Dimension(10, 14));
        m.jPanelIOSpool6.setPreferredSize(new Dimension(10, 21));
        m.jPanelIOSpool6.setLayout(new BorderLayout());
        m.jScrollPaneIOSpoolImage1.setBorder(null);
        m.jScrollPaneIOSpoolImage1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        m.jScrollPaneIOSpoolImage1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        InputMap inputMap = m.jScrollPaneIOSpoolImage1.getInputMap(
            JSplitPane.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        inputMap.clear();
        ActionMap actionMap = m.jScrollPaneIOSpoolImage1.getActionMap();
        actionMap.clear();
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK), "UNDO");
        actionMap.put("UNDO", m.actionUndoIOSpoolImage);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_Y, KeyEvent.CTRL_DOWN_MASK), "REDO");
        actionMap.put("REDO", m.actionRedoIOSpoolImage);
        m.jScrollPaneIOSpoolImage2.setBorder(BorderFactory.createLoweredBevelBorder());
        m.jLabelIOSpoolImage.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jLabelIOSpoolImage.setText(res.getString("S727"));
        m.jPanelIOSpool5.setBorder(BorderFactory.createEtchedBorder());
        m.jPanelIOSpool5.setLayout(new BorderLayout());
        m.jPanelIOSpool2.add(m.jLabelIOSpoolName, null);
        m.jPanelIOSpool2.add(m.jLabelIOSpoolDescriptions, null);
        m.jPanelIOSpool1.add(m.jPanelIOSpool3, BorderLayout.CENTER);
        m.jPanelIOSpool1.add(m.jPanelIOSpool2, BorderLayout.WEST);
        m.jPanelIOSpool3.add(m.jScrollPaneIOSpoolDescriptions, BorderLayout.CENTER);
        m.jPanelIOSpool3.add(m.jPanelIOSpool4, BorderLayout.NORTH);
        m.jScrollPaneIOSpoolDescriptions.getViewport().add(m.jTextAreaIOSpoolDescriptions, null);
        m.jPanelIOSpool4.add(m.jTextFieldIOSpoolName, null);
        m.jPanelIOSpool.add(m.jSplitPaneIOSpool1, BorderLayout.CENTER);
        m.jSplitPaneIOSpool1.add(m.jSplitPaneIOSpool2, JSplitPane.TOP);
        m.jSplitPaneIOSpool1.add(m.jScrollPaneIOSpoolFieldList, JSplitPane.BOTTOM);
        m.jScrollPaneIOSpoolFieldList.getViewport().add(m.jTableIOSpoolFieldList, null);
        m.jScrollPaneIOSpoolFieldList.addMouseListener(
            new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jScrollPaneIOSpoolFieldList_mouseClicked(e); } });
        m.jTableIOSpoolFieldList.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent e) { m.jTableIOSpoolFieldList_mouseClicked(e); } });
        m.jTableIOSpoolFieldList.addFocusListener(new Modeler_FocusListener());
        m.jPanelIOSpool5.add(m.jScrollPaneIOSpoolImage2, BorderLayout.CENTER);
        m.jScrollPaneIOSpoolImage1.getViewport().add(m.jTextPaneIOSpoolImage, null);
        m.jScrollPaneIOSpoolImage2.getViewport().add(m.jPanelIOSpoolImage, null);
        m.jPanelIOSpool5.add(m.jPanelIOSpool6, BorderLayout.NORTH);
        m.jPanelIOSpool6.add(m.jLabelIOSpoolImage, BorderLayout.CENTER);
        m.jPanelIOSpool6.add(m.jLabelIOSpoolCaretPosition, BorderLayout.EAST);
        m.jSplitPaneIOSpool1.setDividerLocation(750);
        Border borderOriginal4 = BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.white,
            UIManager.getColor("control"), Color.gray, UIManager.getColor("control"));
        m.jLabelIOSpoolCaretPosition.setBorder(borderOriginal4);
        m.jLabelIOSpoolCaretPosition.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jLabelIOSpoolCaretPosition.setPreferredSize(new Dimension(100, 19));
        m.jLabelIOSpoolCaretPosition.setHorizontalAlignment(SwingConstants.CENTER);
        m.jLabelIOSpoolCaretPosition.setText("0 - 0");
        m.jTableIOSpoolFieldList.setFont(new java.awt.Font(m.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        m.jTableIOSpoolFieldList.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        m.jTableIOSpoolFieldList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        m.jTableIOSpoolFieldList.setRowHeight(m.TABLE_ROW_HEIGHT_EDITABLE);
        m.jTableIOSpoolFieldList.getSelectionModel()
            .addListSelectionListener(e -> m.jTableIOSpoolFieldList_valueChanged(e));
        m.jTableIOSpoolFieldList.getColumnModel()
            .addColumnModelListener(new TableColumnModelListener() { public void columnSelectionChanged(javax.swing.event.ListSelectionEvent e) { m.jTableIOSpoolFieldList_columnSelectionChanged(e); } public void columnMarginChanged(javax.swing.event.ChangeEvent e) {} public void columnMoved(javax.swing.event.TableColumnModelEvent e) {} public void columnRemoved(javax.swing.event.TableColumnModelEvent e) {} public void columnAdded(javax.swing.event.TableColumnModelEvent e) {} });
    }
}
