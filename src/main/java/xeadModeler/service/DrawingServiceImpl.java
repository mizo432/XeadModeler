package xeadModeler.service;

import xeadModeler.view.XeadPagePainter;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.print.PageFormat;
import java.awt.print.Printable;

/**
 * 描画サービスの実装クラス
 * 印刷機能などの描画操作を担当します。
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class DrawingServiceImpl implements DrawingService {
    
    private JLabel jLabelSubtitle;
    private JPanel jPanelSubjectAreaDataflowEditor1;
    private JPanel jPanelDatamodel;
    private JPanel jPanelDatamodelSlideShow2;
    private JDialog jDialogDatamodelSlideShow;
    private JLabel jLabelIOImageIcon;
    private JTextPane jTextPaneIOPanelImage;
    private JTextPane jTextPaneIOSpoolImage;
    private JEditorPane jEditorPaneIOWebPageImage;
    private Image imageFunctionIO;
    private String mainFontName;
    private int MAIN_FONT_SIZE;
    
    // 現在のノード情報
    private String currentNodeType;
    private int selectedTabIndex;
    
    // リソースバンドル
    private java.util.ResourceBundle res;
    
    public DrawingServiceImpl() {
        this.res = java.util.ResourceBundle.getBundle("xeadModeler.Res");
    }
    
    public void setJLabelSubtitle(JLabel jLabelSubtitle) {
        this.jLabelSubtitle = jLabelSubtitle;
    }
    
    public void setJPanelSubjectAreaDataflowEditor1(JPanel jPanelSubjectAreaDataflowEditor1) {
        this.jPanelSubjectAreaDataflowEditor1 = jPanelSubjectAreaDataflowEditor1;
    }
    
    public void setJPanelDatamodel(JPanel jPanelDatamodel) {
        this.jPanelDatamodel = jPanelDatamodel;
    }
    
    public void setJPanelDatamodelSlideShow2(JPanel jPanelDatamodelSlideShow2) {
        this.jPanelDatamodelSlideShow2 = jPanelDatamodelSlideShow2;
    }
    
    public void setJDialogDatamodelSlideShow(JDialog jDialogDatamodelSlideShow) {
        this.jDialogDatamodelSlideShow = jDialogDatamodelSlideShow;
    }
    
    public void setJLabelIOImageIcon(JLabel jLabelIOImageIcon) {
        this.jLabelIOImageIcon = jLabelIOImageIcon;
    }
    
    public void setJTextPaneIOPanelImage(JTextPane jTextPaneIOPanelImage) {
        this.jTextPaneIOPanelImage = jTextPaneIOPanelImage;
    }
    
    public void setJTextPaneIOSpoolImage(JTextPane jTextPaneIOSpoolImage) {
        this.jTextPaneIOSpoolImage = jTextPaneIOSpoolImage;
    }
    
    public void setJEditorPaneIOWebPageImage(JEditorPane jEditorPaneIOWebPageImage) {
        this.jEditorPaneIOWebPageImage = jEditorPaneIOWebPageImage;
    }
    
    public void setImageFunctionIO(Image imageFunctionIO) {
        this.imageFunctionIO = imageFunctionIO;
    }
    
    public void setMainFontName(String mainFontName) {
        this.mainFontName = mainFontName;
    }
    
    public void setMainFontSize(int mainFontSize) {
        this.MAIN_FONT_SIZE = mainFontSize;
    }
    
    public void setCurrentNodeType(String currentNodeType) {
        this.currentNodeType = currentNodeType;
    }
    
    public void setSelectedTabIndex(int selectedTabIndex) {
        this.selectedTabIndex = selectedTabIndex;
    }
    
    @Override
    public int print(Graphics g, PageFormat fmt, int pageindex) {
        Graphics2D g2 = (Graphics2D) g;
        int returnValue = Printable.NO_SUCH_PAGE;
        
        // 最初のページのみ印刷
        if (pageindex == 0) {
            if ("SubjectArea".equals(currentNodeType)) {
                printPanel(g2, fmt, jPanelSubjectAreaDataflowEditor1, "");
                returnValue = Printable.PAGE_EXISTS;
            }
            
            if ("TableList".equals(currentNodeType)) {
                if (selectedTabIndex == 2) {
                    if (jDialogDatamodelSlideShow != null && jDialogDatamodelSlideShow.isVisible()) {
                        printPanel(g2, fmt, jPanelDatamodelSlideShow2, res.getString("S466"));
                    } else {
                        printPanel(g2, fmt, jPanelDatamodel, res.getString("S466"));
                    }
                }
                returnValue = Printable.PAGE_EXISTS;
            }
            
            if ("IOPanel".equals(currentNodeType)) {
                if (imageFunctionIO != null) {
                    printPanel(g2, fmt, jLabelIOImageIcon, res.getString("S3305"));
                } else {
                    if (jTextPaneIOPanelImage.getBackground().equals(Color.BLACK)) {
                        jTextPaneIOPanelImage.setBackground(Color.WHITE);
                        jTextPaneIOPanelImage.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                        jTextPaneIOPanelImage.updateUI();
                        StyledDocument styledDocument = jTextPaneIOPanelImage.getStyledDocument();
                        Style style = styledDocument.addStyle("style1", null);
                        StyleConstants.setForeground(style, Color.BLACK);
                        styledDocument.setCharacterAttributes(0, 9999, style, false);
                        printPanel(g2, fmt, jTextPaneIOPanelImage, res.getString("S3305"));
                        jTextPaneIOPanelImage.setBorder(BorderFactory.createRaisedBevelBorder());
                    } else {
                        printPanel(g2, fmt, jTextPaneIOPanelImage, res.getString("S3305"));
                    }
                }
                returnValue = Printable.PAGE_EXISTS;
            }
            
            if ("IOSpool".equals(currentNodeType)) {
                printPanel(g2, fmt, jTextPaneIOSpoolImage, res.getString("S3305"));
                returnValue = Printable.PAGE_EXISTS;
            }
            
            if ("IOWebPage".equals(currentNodeType)) {
                printPanel(g2, fmt, jEditorPaneIOWebPageImage, res.getString("S3305"));
                returnValue = Printable.PAGE_EXISTS;
            }
        }
        
        return returnValue;
    }
    
    @Override
    public void printPanel(Graphics2D g2, PageFormat fmt, JComponent jPanel, String name) {
        double panelWidth;
        double panelHeight;
        double scaleRateWidth;
        double scaleRateHeight;
        Border borderSaved;
        Color colorSaved;
        Font fontSaved;
        
        g2.translate(fmt.getImageableX(), fmt.getImageableY());
        
        if (jLabelSubtitle != null) {
            borderSaved = jLabelSubtitle.getBorder();
            colorSaved = jLabelSubtitle.getBackground();
            fontSaved = jLabelSubtitle.getFont();
            jLabelSubtitle.setBorder(null);
            jLabelSubtitle.setBackground(Color.white);
            jLabelSubtitle.setFont(new java.awt.Font(mainFontName, 0, MAIN_FONT_SIZE));
            jLabelSubtitle.print(g2);
            jLabelSubtitle.setBorder(borderSaved);
            jLabelSubtitle.setBackground(colorSaved);
            jLabelSubtitle.setFont(fontSaved);
            
            g2.translate(0, jLabelSubtitle.getHeight() + 3);
        }
        
        panelWidth = jPanel.getWidth();
        scaleRateWidth = fmt.getImageableWidth() / panelWidth;
        panelHeight = jPanel.getHeight() + (jLabelSubtitle != null ? jLabelSubtitle.getHeight() + 5 : 0);
        scaleRateHeight = (fmt.getImageableHeight() - 18) / panelHeight;
        
        if (scaleRateWidth < scaleRateHeight && scaleRateWidth < 1.0) {
            AffineTransform at = new AffineTransform();
            at.setToScale(scaleRateWidth, scaleRateWidth);
            g2.transform(at);
        }
        if (scaleRateWidth > scaleRateHeight && scaleRateHeight < 1.0) {
            AffineTransform at = new AffineTransform();
            at.setToScale(scaleRateHeight, scaleRateHeight);
            g2.transform(at);
        }
        
        if (jPanel == jPanelSubjectAreaDataflowEditor1) {
            borderSaved = jPanel.getBorder();
            jPanel.setBorder(null);
            jPanel.print(g2);
            jPanel.setBorder(borderSaved);
        } else if (jPanel == jPanelDatamodel) {
            borderSaved = jPanel.getBorder();
            jPanel.setBorder(null);
            jPanel.print(g2);
            jPanel.setBorder(borderSaved);
        } else {
            jPanel.print(g2);
        }
    }
}
