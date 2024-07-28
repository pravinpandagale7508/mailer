package com.utcl.ccnf.m1service.pdf.pdfBoxTable.structure;

import static java.util.Comparator.naturalOrder;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import com.utcl.ccnf.m1service.pdf.pdfBoxTable.settings.BorderStyleInterface;
import com.utcl.ccnf.m1service.pdf.pdfBoxTable.settings.HorizontalAlignment;
import com.utcl.ccnf.m1service.pdf.pdfBoxTable.settings.Settings;
import com.utcl.ccnf.m1service.pdf.pdfBoxTable.settings.VerticalAlignment;
import com.utcl.ccnf.m1service.pdf.pdfBoxTable.structure.cell.AbstractCell;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter(AccessLevel.PACKAGE)
public class Row {

    private static final Float DEFAULT_HEIGHT = 10f;

    private Table table;

    private List<AbstractCell> cells;

    @Setter(AccessLevel.NONE)
    private Settings settings;

    @Setter(AccessLevel.PACKAGE)
    private Float height;

    private Row next;


    private Row(final List<AbstractCell> cells) {
        super();
        this.cells = cells;
    }

    public float getHeight() {
        if (table == null) {
            throw new TableNotYetBuiltException();
        }

        if (height == null) {
            this.height = getCells().stream()
                    .filter(cell -> cell.getRowSpan() == 1)
                    .map(AbstractCell::getHeight)
                    .max(naturalOrder())
                    .orElse(DEFAULT_HEIGHT);
        }

        return height;
    }

    void doRowSpanSizeAdaption(float heightOfHighestCell, float rowsHeight) {
        final float rowSpanSizeDifference = heightOfHighestCell - rowsHeight;
        this.height += (this.height / (heightOfHighestCell - rowSpanSizeDifference)) * rowSpanSizeDifference;
    }

    public Color getBorderColor() {
        // We could simply return any of the four border color values ...
        return settings.getBorderColorBottom();
    }

    public static class RowBuilder {

        private List<AbstractCell> cells = new ArrayList<>();
        private Settings settings = Settings.builder().build();

        private RowBuilder() {

        }

        public RowBuilder add(final AbstractCell cell) {
            cells.add(cell);
            return this;
        }

        public Row.RowBuilder font(final PDFont font) {
            settings.setFont(font);
            return this;
        }

        // Convenience setter for being halfway backwards compatible
        public Row.RowBuilder font(Standard14Fonts.FontName fontName) {
            settings.setFont(new PDType1Font(fontName));
            return this;
        }

        public Row.RowBuilder fontSize(final Integer fontSize) {
            settings.setFontSize(fontSize);
            return this;
        }

        public Row.RowBuilder textColor(final Color textColor) {
            settings.setTextColor(textColor);
            return this;
        }

        public Row.RowBuilder backgroundColor(final Color backgroundColor) {
            settings.setBackgroundColor(backgroundColor);
            return this;
        }

        public Row.RowBuilder padding(final float padding) {
            settings.setPaddingTop(padding);
            settings.setPaddingBottom(padding);
            settings.setPaddingLeft(padding);
            settings.setPaddingRight(padding);
            return this;
        }

        public Row.RowBuilder borderWidth(final float borderWidth) {
            settings.setBorderWidthTop(borderWidth);
            settings.setBorderWidthBottom(borderWidth);
            settings.setBorderWidthLeft(borderWidth);
            settings.setBorderWidthRight(borderWidth);
            return this;
        }

        public Row.RowBuilder borderStyle(final BorderStyleInterface borderStyle) {
            settings.setBorderStyleTop(borderStyle);
            settings.setBorderStyleBottom(borderStyle);
            settings.setBorderStyleLeft(borderStyle);
            settings.setBorderStyleRight(borderStyle);
            return this;
        }

        public Row.RowBuilder borderColor(final Color borderColor) {
            settings.setBorderColorBottom(borderColor);
            settings.setBorderColorTop(borderColor);
            settings.setBorderColorLeft(borderColor);
            settings.setBorderColorRight(borderColor);
            return this;
        }

        public Row.RowBuilder horizontalAlignment(HorizontalAlignment alignment) {
            settings.setHorizontalAlignment(alignment);
            return this;
        }

        public Row.RowBuilder verticalAlignment(VerticalAlignment alignment) {
            settings.setVerticalAlignment(alignment);
            return this;
        }

        public Row.RowBuilder wordBreak(Boolean wordBreak) {
            settings.setWordBreak(wordBreak);
            return this;
        }

        public Row build() {
            final Row row = new Row(cells);
            row.settings = settings;
            row.height = height;

            return row;
        }

    }

}
