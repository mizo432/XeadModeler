package xeadModeler.view;

import xeadModeler.Modeler;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

/**
 * XEADページペインタークラス
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class XeadPagePainter implements Printable {

    private Modeler modeler;

    public XeadPagePainter(Modeler modeler) {
        this.modeler = modeler;
    }

    public int print(Graphics g, PageFormat fmt, int pageindex) {
        Graphics2D g2 = (Graphics2D) g;
        int returnValue = Printable.NO_SUCH_PAGE;

        //Print first page for each node//
        if (pageindex == 0) {

            if (modeler.currentMainTreeNode.getType().equals("SubjectArea")) {
                printPanel(g2, fmt, modeler.jPanelSubjectAreaDataflowEditor1, "");
                returnValue = Printable.PAGE_EXISTS;
            }

            if (modeler.currentMainTreeNode.getType().equals("TableList")) {
                if (modeler.jTabbedPaneTableList.getSelectedIndex() == 2) {
                    if (modeler.jDialogDatamodelSlideShow.isVisible()) {
                        printPanel(g2, fmt, modeler.jPanelDatamodelSlideShow2, modeler.res.getString("S466"));
                    } else {
                        printPanel(g2, fmt, modeler.jPanelDatamodel, modeler.res.getString("S466"));
                    }
                }
                returnValue = Printable.PAGE_EXISTS;
            }

            if (modeler.currentMainTreeNode.getType().equals("IOPanel")) {
                if (modeler.imageFunctionIO != null) {
                    printPanel(g2, fmt, modeler.jLabelIOImageIcon, modeler.res.getString("S3305"));
                } else {
                    if (modeler.jTextPaneIOPanelImage.getBackground().equals(Color.BLACK)) {
                        modeler.currentMainTreeNode.updateFields();
                        modeler.jTextPaneIOPanelImage.setBackground(Color.WHITE);
                        modeler.jTextPaneIOPanelImage.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                        modeler.jTextPaneIOPanelImage.updateUI();
                        StyledDocument styledDocument = modeler.jTextPaneIOPanelImage.getStyledDocument();
                        Style style = styledDocument.addStyle("style1", null);
                        StyleConstants.setForeground(style, Color.BLACK);
                        styledDocument.setCharacterAttributes(0, 9999, style, false);
                        printPanel(g2, fmt, modeler.jTextPaneIOPanelImage, modeler.res.getString("S3305"));
                        modeler.jTextPaneIOPanelImage.setBorder(BorderFactory.createRaisedBevelBorder());
                        modeler.currentMainTreeNode.activateContentsPane();
                    } else {
                        printPanel(g2, fmt, modeler.jTextPaneIOPanelImage, modeler.res.getString("S3305"));
                    }
                }
                returnValue = Printable.PAGE_EXISTS;
            }

            if (modeler.currentMainTreeNode.getType().equals("IOSpool")) {
                printPanel(g2, fmt, modeler.jTextPaneIOSpoolImage, modeler.res.getString("S3305"));
                returnValue = Printable.PAGE_EXISTS;
            }

            if (modeler.currentMainTreeNode.getType().equals("IOWebPage")) {
                printPanel(g2, fmt, modeler.jEditorPaneIOWebPageImage, modeler.res.getString("S3305"));
                returnValue = Printable.PAGE_EXISTS;
            }
        }

        return returnValue;
    }

    private void printPanel(Graphics2D g2, PageFormat fmt, JComponent jPanel, String name) {
        double panelWidth;
        double panelHeight;
        double scaleRateWidth;
        double scaleRateHeight;
        Border borderSaved;
        Color colorSaved;
        Font fontSaved;

        g2.translate(fmt.getImageableX(), fmt.getImageableY());
        borderSaved = modeler.jLabelSubtitle.getBorder();
        colorSaved = modeler.jLabelSubtitle.getBackground();
        fontSaved = modeler.jLabelSubtitle.getFont();
        modeler.jLabelSubtitle.setBorder(null);
        modeler.jLabelSubtitle.setBackground(Color.white);
        modeler.jLabelSubtitle.setFont(new java.awt.Font(modeler.mainFontName, 0, Modeler.MAIN_FONT_SIZE));
        modeler.jLabelSubtitle.print(g2);
        modeler.jLabelSubtitle.setBorder(borderSaved);
        modeler.jLabelSubtitle.setBackground(colorSaved);
        modeler.jLabelSubtitle.setFont(fontSaved);

        g2.translate(0, modeler.jLabelSubtitle.getHeight() + 3);

        panelWidth = jPanel.getWidth();
        scaleRateWidth = fmt.getImageableWidth() / panelWidth;
        panelHeight = jPanel.getHeight() + (modeler.jLabelSubtitle.getHeight() + 5);
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

        if (jPanel.equals(modeler.jPanelSubjectAreaDataflowEditor1)) {
            borderSaved = jPanel.getBorder();
            jPanel.setBorder(null);
            jPanel.print(g2);
            jPanel.setBorder(borderSaved);
        }

        if (jPanel.equals(modeler.jPanelDatamodel)) {
            borderSaved = jPanel.getBorder();
            jPanel.setBorder(null);
            jPanel.print(g2);
            jPanel.setBorder(borderSaved);
        }

        if (jPanel.equals(modeler.jPanelDatamodelSlideShow2)) {
            borderSaved = jPanel.getBorder();
            jPanel.setBorder(null);
            jPanel.print(g2);
            jPanel.setBorder(borderSaved);
        }

        if (!jPanel.equals(modeler.jPanelDatamodel) && !jPanel.equals(modeler.jPanelSubjectAreaDataflowEditor1)) {
            borderSaved = jPanel.getBorder();
            jPanel.setBorder(BorderFactory.createLineBorder(Color.black));
            jPanel.print(g2);
            jPanel.setBorder(borderSaved);
        }
    }
}
