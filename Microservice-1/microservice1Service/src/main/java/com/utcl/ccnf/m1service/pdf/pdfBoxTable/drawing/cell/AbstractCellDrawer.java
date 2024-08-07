package com.utcl.ccnf.m1service.pdf.pdfBoxTable.drawing.cell;

import static com.utcl.ccnf.m1service.pdf.pdfBoxTable.settings.VerticalAlignment.BOTTOM;
import static com.utcl.ccnf.m1service.pdf.pdfBoxTable.settings.VerticalAlignment.MIDDLE;

import java.awt.Color;
import java.awt.geom.Point2D;

import org.apache.pdfbox.pdmodel.PDPageContentStream;

import com.utcl.ccnf.m1service.pdf.pdfBoxTable.drawing.Drawer;
import com.utcl.ccnf.m1service.pdf.pdfBoxTable.drawing.DrawingContext;
import com.utcl.ccnf.m1service.pdf.pdfBoxTable.drawing.DrawingUtil;
import com.utcl.ccnf.m1service.pdf.pdfBoxTable.drawing.PositionedLine;
import com.utcl.ccnf.m1service.pdf.pdfBoxTable.drawing.PositionedRectangle;
import com.utcl.ccnf.m1service.pdf.pdfBoxTable.structure.cell.AbstractCell;
import com.utcl.ccnf.m1service.pdf.pdfBoxTable.util.FloatUtil;

import lombok.SneakyThrows;

public abstract class AbstractCellDrawer<T extends AbstractCell> implements Drawer {

    protected T cell;

    public AbstractCellDrawer<T> withCell(T cell) {
        this.cell = cell;
        return this;
    }

    @Override
    @SneakyThrows
    public void drawBackground(DrawingContext drawingContext) {
        if (cell.hasBackgroundColor()) {
            final PDPageContentStream contentStream = drawingContext.getContentStream();
            final Point2D.Float start = drawingContext.getStartingPoint();

            final float rowHeight = cell.getRow().getHeight();
            final float height = Math.max(cell.getHeight(), rowHeight);
            final float y = rowHeight < cell.getHeight()
                    ? start.y + rowHeight - cell.getHeight()
                    : start.y;

            DrawingUtil.drawRectangle(contentStream,
                    PositionedRectangle.builder()
                            .x(start.x)
                            .y(y)
                            .width(cell.getWidth())
                            .height(height)
                            .color(cell.getBackgroundColor())
                            .build()
            );
        }
    }

    @Override
    public abstract void drawContent(DrawingContext drawingContext);

    @Override
    @SneakyThrows
    public void drawBorders(DrawingContext drawingContext) {
        final Point2D.Float start = drawingContext.getStartingPoint();
        final PDPageContentStream contentStream = drawingContext.getContentStream();

        final float cellWidth = cell.getWidth();

        final float rowHeight = cell.getRow().getHeight();
        final float height = Math.max(cell.getHeight(), rowHeight);
        final float sY = rowHeight < cell.getHeight()
                ? start.y + rowHeight - cell.getHeight()
                : start.y;

        // Handle the cell's borders
        final Color cellBorderColorTop = cell.getBorderColorTop();
        final Color cellBorderColorBottom = cell.getBorderColorBottom();
        final Color cellBorderColorLeft = cell.getBorderColorLeft();
        final Color cellBorderColorRight = cell.getBorderColorRight();

        final Color rowBorderColor = cell.getRow().getBorderColor();

        if (cell.hasBorderTop() || cell.hasBorderBottom()) {
            final float correctionLeft = cell.getBorderWidthLeft() / 2;
            final float correctionRight = cell.getBorderWidthRight() / 2;

            if (cell.hasBorderTop()) {
                DrawingUtil.drawLine(contentStream, PositionedLine.builder()
                        .startX(start.x - correctionLeft)
                        .startY(start.y + rowHeight)
                        .endX(start.x + cellWidth + correctionRight)
                        .endY(start.y + rowHeight)
                        .width(cell.getBorderWidthTop())
                        .color(cellBorderColorTop)
                        .resetColor(rowBorderColor)
                        .borderStyle(cell.getBorderStyleTop())
                        .build()
                );
            }

            if (cell.hasBorderBottom()) {
                DrawingUtil.drawLine(contentStream, PositionedLine.builder()
                        .startX(start.x - correctionLeft)
                        .startY(sY)
                        .endX(start.x + cellWidth + correctionRight)
                        .endY(sY)
                        .width(cell.getBorderWidthBottom())
                        .color(cellBorderColorBottom)
                        .resetColor(rowBorderColor)
                        .borderStyle(cell.getBorderStyleBottom())
                        .build()
                );
            }
        }

        if (cell.hasBorderLeft() || cell.hasBorderRight()) {
            final float correctionTop = cell.getBorderWidthTop() / 2;
            final float correctionBottom = cell.getBorderWidthBottom() / 2;

            if (cell.hasBorderLeft()) {
                DrawingUtil.drawLine(contentStream, PositionedLine.builder()
                        .startX(start.x)
                        .startY(sY - correctionBottom)
                        .endX(start.x)
                        .endY(sY + height + correctionTop)
                        .width(cell.getBorderWidthLeft())
                        .color(cellBorderColorLeft)
                        .resetColor(rowBorderColor)
                        .borderStyle(cell.getBorderStyleLeft())
                        .build()
                );
            }

            if (cell.hasBorderRight()) {
                DrawingUtil.drawLine(contentStream, PositionedLine.builder()
                        .startX(start.x + cellWidth)
                        .startY(sY - correctionBottom)
                        .endX(start.x + cellWidth)
                        .endY(sY + height + correctionTop)
                        .width(cell.getBorderWidthRight())
                        .color(cellBorderColorRight)
                        .resetColor(rowBorderColor)
                        .borderStyle(cell.getBorderStyleRight())
                        .build()
                );
            }
        }
    }

    protected boolean rowHeightIsBiggerThanOrEqualToCellHeight() {
        return cell.getRow().getHeight() > cell.getHeight()
                || FloatUtil.isEqualInEpsilon(cell.getRow().getHeight(), cell.getHeight());
    }

    protected float getRowSpanAdaption() {
        return cell.getRowSpan() > 1
                ? cell.calculateHeightForRowSpan() - cell.getRow().getHeight()
                : 0;
    }

    protected float calculateOuterHeight() {
        return cell.getRowSpan() > 1 ? cell.getHeight() : cell.getRow().getHeight();
    }

    protected float getAdaptionForVerticalAlignment() {
        if (rowHeightIsBiggerThanOrEqualToCellHeight() || cell.getRowSpan() > 1) {

            if (cell.isVerticallyAligned(MIDDLE)) {
                return (calculateOuterHeight() / 2 + calculateInnerHeight() / 2) - getRowSpanAdaption();

            } else if (cell.isVerticallyAligned(BOTTOM)) {
                return (calculateInnerHeight() + cell.getPaddingBottom()) - getRowSpanAdaption();
            }
        }

        // top alignment (default case)
        return cell.getRow().getHeight() - cell.getPaddingTop(); // top position
    }

    protected abstract float calculateInnerHeight();
}
