package com.utcl.ccnf.m1service.pdf.pdfBoxTable.drawing;

import java.awt.Color;

import com.utcl.ccnf.m1service.pdf.pdfBoxTable.settings.BorderStyleInterface;

import lombok.Builder;
import lombok.Getter;

@Builder(toBuilder = true)
@Getter
public class PositionedLine {

    private float width;
    private float startX;
    private float startY;
    private float endX;
    private float endY;
    private Color color;
    private Color resetColor;
    private BorderStyleInterface borderStyle;

}
