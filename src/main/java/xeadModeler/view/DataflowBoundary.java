package xeadModeler.view;

import xeadModeler.Modeler;
import xeadModeler.Modeler.XeadTreeNode;

import javax.swing.*;
import java.awt.*;
import java.util.StringTokenizer;

/**
 * データフロー境界の表示コンポーネント
 *
 * Copyright (c) 2015 WATANABE kozo <qyf05466@nifty.com>,
 * All rights reserved.
 */
public class DataflowBoundary extends JPanel {

    private static final long serialVersionUID = 1L;
    private org.w3c.dom.Element dataflowBoundaryElement_;
    private JLabel jLabelName = new JLabel();
    public int boundaryPosX, boundaryPosY, boundaryWidth, boundaryHeight;
    private Modeler modeler;

    public DataflowBoundary(Modeler modeler, org.w3c.dom.Element element) {
        super();
        this.modeler = modeler;
        dataflowBoundaryElement_ = element;
        StringTokenizer workTokenizer = new StringTokenizer(
            dataflowBoundaryElement_.getAttribute("BoundaryPosition"), ",");
        boundaryPosX = Integer.parseInt(workTokenizer.nextToken());
        boundaryPosY = Integer.parseInt(workTokenizer.nextToken());
        workTokenizer = new StringTokenizer(dataflowBoundaryElement_.getAttribute("BoundarySize"),
            ",");
        boundaryWidth = Integer.parseInt(workTokenizer.nextToken());
        boundaryHeight = Integer.parseInt(workTokenizer.nextToken());
        this.setLayout(null);
        this.setBounds(
            new Rectangle(boundaryPosX, boundaryPosY, boundaryWidth + 1, boundaryHeight + 1));
        this.setBorder(null);
        this.setOpaque(false);
        jLabelName.setFont(new java.awt.Font(modeler.mainFontName, 0, Modeler.MAIN_FONT_SIZE - 2));
        jLabelName.setText(modeler.currentMainTreeNode.getRoot().toString().replace("*", ""));
        setSystemName();
        updateColors();
        if (!dataflowBoundaryElement_.getAttribute("BoundarySetting").equals("0")) {
            this.add(jLabelName, null);
        }
    }

    public DataflowBoundary(Modeler modeler) {
        super();
        this.modeler = modeler;
    }

    private void setSystemName() {
        if (dataflowBoundaryElement_.getAttribute("BoundarySetting").equals("1")) {
            jLabelName.setBounds(new Rectangle(15, 12, 300, 20));
        }
        if (dataflowBoundaryElement_.getAttribute("BoundarySetting").equals("2")) {
            jLabelName.setBounds(new Rectangle(boundaryWidth - 315, 12, 300, 20));
            jLabelName.setHorizontalAlignment(SwingConstants.RIGHT);
            jLabelName.setHorizontalTextPosition(SwingConstants.LEADING);
        }
        if (dataflowBoundaryElement_.getAttribute("BoundarySetting").equals("")
            || dataflowBoundaryElement_.getAttribute("BoundarySetting").equals("3")) {
            jLabelName.setBounds(new Rectangle(15, boundaryHeight - 32, 300, 20));
        }
        if (dataflowBoundaryElement_.getAttribute("BoundarySetting").equals("4")) {
            jLabelName.setBounds(new Rectangle(boundaryWidth - 315, boundaryHeight - 32, 300, 20));
            jLabelName.setHorizontalAlignment(SwingConstants.RIGHT);
            jLabelName.setHorizontalTextPosition(SwingConstants.LEADING);
        }
    }

    public void setBounds(int x, int y, int w, int h) {
        if (x > 0) {
            boundaryPosX = x;
        } else {
            boundaryPosX = 0;
        }
        if (y > 0) {
            boundaryPosY = y;
        } else {
            boundaryPosY = 0;
        }
        if (w > 51) {
            boundaryWidth = w - 1;
        } else {
            boundaryWidth = 50;
        }
        if (h > 51) {
            boundaryHeight = h - 1;
        } else {
            boundaryHeight = 50;
        }
        jLabelName.setBounds(new Rectangle(15, boundaryHeight - 30, 300, 20));
        dataflowBoundaryElement_.setAttribute("BoundaryPosition",
            Integer.toString(boundaryPosX) + "," + Integer.toString(boundaryPosY));
        dataflowBoundaryElement_.setAttribute("BoundarySize",
            Integer.toString(boundaryWidth) + "," + Integer.toString(boundaryHeight));
        super.setBounds(boundaryPosX, boundaryPosY, boundaryWidth + 1, boundaryHeight + 1);
    }

    public void setBounds(Rectangle rect) {
        if (rect.x > 0) {
            boundaryPosX = rect.x;
        } else {
            boundaryPosX = 0;
        }
        if (rect.y > 0) {
            boundaryPosY = rect.y;
        } else {
            boundaryPosY = 0;
        }
        if (rect.width > 51) {
            boundaryWidth = rect.width - 1;
        } else {
            boundaryWidth = 50;
        }
        if (rect.height > 51) {
            boundaryHeight = rect.height - 1;
        } else {
            boundaryHeight = 50;
        }
        jLabelName.setBounds(new Rectangle(15, boundaryHeight - 30, 300, 20));
        dataflowBoundaryElement_.setAttribute("BoundaryPosition",
            Integer.toString(boundaryPosX) + "," + Integer.toString(boundaryPosY));
        dataflowBoundaryElement_.setAttribute("BoundarySize",
            Integer.toString(boundaryWidth) + "," + Integer.toString(boundaryHeight));
        super.setBounds(boundaryPosX, boundaryPosY, boundaryWidth + 1, boundaryHeight + 1);
    }

    public Dimension getPreferredSizeOfParentPanel() {
        return new Dimension(boundaryPosX + boundaryWidth + 5, boundaryPosY + boundaryHeight + 5);
    }

    public void updateColors() {
        if (modeler.isNormalColorConfigOnSubjectArea) {
            jLabelName.setForeground(Color.LIGHT_GRAY);
        } else {
            jLabelName.setForeground(Color.DARK_GRAY);
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (modeler.isNormalColorConfigOnSubjectArea) {
            g2.setPaint(new GradientPaint(5, 5, Color.WHITE, 10, 10, Color.LIGHT_GRAY));
            g2.fillArc(0, 1, 40, 40, 90, 90);
            g2.setPaint(new GradientPaint(5, boundaryHeight - 5, Color.WHITE, 10, boundaryHeight - 10,
                Color.LIGHT_GRAY));
            g2.fillArc(0, boundaryHeight - 40, 40, 40, 180, 90);
            g2.setPaint(new GradientPaint(boundaryWidth - 5, 5, Color.WHITE, boundaryWidth - 10, 10,
                Color.LIGHT_GRAY));
            g2.fillArc(boundaryWidth - 40, 1, 40, 40, 0, 90);
            g2.setPaint(new GradientPaint(boundaryWidth - 5, boundaryHeight - 5, Color.WHITE,
                boundaryWidth - 10, boundaryHeight - 10, Color.LIGHT_GRAY));
            g2.fillArc(boundaryWidth - 40, boundaryHeight - 40, 40, 40, 270, 90);

            g2.setPaint(new GradientPaint(10, 10, Color.LIGHT_GRAY, 20, 20, Modeler.DESKTOP_COLOR));
            int[] x1 = {0, 20, 20};
            int[] y1 = {20, 20, 0};
            g2.fillPolygon(x1, y1, 3);
            g2.setPaint(
                new GradientPaint(0, boundaryHeight / 2, Color.LIGHT_GRAY, 20, boundaryHeight / 2,
                    Modeler.DESKTOP_COLOR));
            g2.fillRect(0, 20, 20, boundaryHeight - 40);

            g2.setPaint(new GradientPaint(boundaryWidth / 2, 0, Color.LIGHT_GRAY, boundaryWidth / 2, 20,
                Modeler.DESKTOP_COLOR));
            g2.fillRect(20, 0, boundaryWidth - 40, 20);

            g2.setPaint(
                new GradientPaint(10, boundaryHeight - 10, Color.LIGHT_GRAY, 20, boundaryHeight - 20,
                    Modeler.DESKTOP_COLOR));
            int[] x2 = {0, 20, 20};
            int[] y2 = {boundaryHeight - 20, boundaryHeight, boundaryHeight - 20};
            g2.fillPolygon(x2, y2, 3);

            g2.setPaint(new GradientPaint(boundaryWidth / 2, boundaryHeight, Color.LIGHT_GRAY,
                boundaryWidth / 2, boundaryHeight - 20, Modeler.DESKTOP_COLOR));
            g2.fillRect(20, boundaryHeight - 20, boundaryWidth - 40, 20);

            g2.setPaint(
                new GradientPaint(boundaryWidth - 10, 10, Color.LIGHT_GRAY, boundaryWidth - 20, 20,
                    Modeler.DESKTOP_COLOR));
            int[] x3 = {boundaryWidth - 20, boundaryWidth, boundaryWidth - 20};
            int[] y3 = {0, 20, 20};
            g2.fillPolygon(x3, y3, 3);

            g2.setPaint(new GradientPaint(boundaryWidth, boundaryHeight / 2, Color.LIGHT_GRAY,
                boundaryWidth - 20, boundaryHeight / 2, Modeler.DESKTOP_COLOR));
            g2.fillRect(boundaryWidth - 20, 20, 20, boundaryHeight - 40);

            g2.setPaint(new GradientPaint(boundaryWidth - 10, boundaryHeight - 10, Color.LIGHT_GRAY,
                boundaryWidth - 20, boundaryHeight - 20, Modeler.DESKTOP_COLOR));
            int[] x4 = {boundaryWidth - 20, boundaryWidth - 20, boundaryWidth};
            int[] y4 = {boundaryHeight - 20, boundaryHeight, boundaryHeight - 20};
            g2.fillPolygon(x4, y4, 3);

            g2.setColor(Color.darkGray);
            g2.drawArc(0, 0, 40, 40, 90, 90);
            g2.drawArc(0, boundaryHeight - 40, 40, 40, 180, 90);
            g2.drawArc(boundaryWidth - 40, 0, 40, 40, 0, 90);
            g2.drawArc(boundaryWidth - 40, boundaryHeight - 40, 40, 40, 270, 90);
            g2.drawLine(20, 0, boundaryWidth - 20, 0);
            g2.drawLine(20, boundaryHeight, boundaryWidth - 20, boundaryHeight);
            g2.drawLine(0, 20, 0, boundaryHeight - 20);
            g2.drawLine(boundaryWidth, 20, boundaryWidth, boundaryHeight - 20);

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);

        } else {
            g2.setPaint(new GradientPaint(5, 5, Color.GRAY, 10, 10, Color.LIGHT_GRAY));
            g2.fillArc(0, 1, 40, 40, 90, 90);
            g2.setPaint(new GradientPaint(5, boundaryHeight - 5, Color.GRAY, 10, boundaryHeight - 10,
                Color.LIGHT_GRAY));
            g2.fillArc(0, boundaryHeight - 40, 40, 40, 180, 90);
            g2.setPaint(new GradientPaint(boundaryWidth - 5, 5, Color.GRAY, boundaryWidth - 10, 10,
                Color.LIGHT_GRAY));
            g2.fillArc(boundaryWidth - 40, 1, 40, 40, 0, 90);
            g2.setPaint(
                new GradientPaint(boundaryWidth - 5, boundaryHeight - 5, Color.GRAY, boundaryWidth - 10,
                    boundaryHeight - 10, Color.LIGHT_GRAY));
            g2.fillArc(boundaryWidth - 40, boundaryHeight - 40, 40, 40, 270, 90);

            g2.setPaint(new GradientPaint(10, 10, Color.LIGHT_GRAY, 20, 20, Color.WHITE));
            int[] x1 = {0, 20, 20};
            int[] y1 = {20, 20, 0};
            g2.fillPolygon(x1, y1, 3);
            g2.setPaint(
                new GradientPaint(0, boundaryHeight / 2, Color.LIGHT_GRAY, 20, boundaryHeight / 2,
                    Color.WHITE));
            g2.fillRect(0, 20, 20, boundaryHeight - 40);

            g2.setPaint(new GradientPaint(boundaryWidth / 2, 0, Color.LIGHT_GRAY, boundaryWidth / 2, 20,
                Color.WHITE));
            g2.fillRect(20, 0, boundaryWidth - 40, 20);

            g2.setPaint(
                new GradientPaint(10, boundaryHeight - 10, Color.LIGHT_GRAY, 20, boundaryHeight - 20,
                    Color.WHITE));
            int[] x2 = {0, 20, 20};
            int[] y2 = {boundaryHeight - 20, boundaryHeight, boundaryHeight - 20};
            g2.fillPolygon(x2, y2, 3);

            g2.setPaint(new GradientPaint(boundaryWidth / 2, boundaryHeight, Color.LIGHT_GRAY,
                boundaryWidth / 2, boundaryHeight - 20, Color.WHITE));
            g2.fillRect(20, boundaryHeight - 20, boundaryWidth - 40, 20);

            g2.setPaint(
                new GradientPaint(boundaryWidth - 10, 10, Color.LIGHT_GRAY, boundaryWidth - 20, 20,
                    Color.WHITE));
            int[] x3 = {boundaryWidth - 20, boundaryWidth, boundaryWidth - 20};
            int[] y3 = {0, 20, 20};
            g2.fillPolygon(x3, y3, 3);

            g2.setPaint(new GradientPaint(boundaryWidth, boundaryHeight / 2, Color.LIGHT_GRAY,
                boundaryWidth - 20, boundaryHeight / 2, Color.WHITE));
            g2.fillRect(boundaryWidth - 20, 20, 20, boundaryHeight - 40);

            g2.setPaint(new GradientPaint(boundaryWidth - 10, boundaryHeight - 10, Color.LIGHT_GRAY,
                boundaryWidth - 20, boundaryHeight - 20, Color.WHITE));
            int[] x4 = {boundaryWidth - 20, boundaryWidth - 20, boundaryWidth};
            int[] y4 = {boundaryHeight - 20, boundaryHeight, boundaryHeight - 20};
            g2.fillPolygon(x4, y4, 3);
        }
        setSystemName();
    }
}
