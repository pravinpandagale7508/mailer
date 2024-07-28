package com.utcl.ccnf.m1service.pdf.pdfBoxTable.structure.cell;

import com.utcl.ccnf.m1service.pdf.pdfBoxTable.drawing.Drawer;
import com.utcl.ccnf.m1service.pdf.pdfBoxTable.drawing.cell.VerticalTextCellDrawer;

import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
public class VerticalTextCell extends AbstractTextCell {

    @NonNull
    private String text;

    protected Drawer createDefaultDrawer() {
        return new VerticalTextCellDrawer(this);
    }

}
