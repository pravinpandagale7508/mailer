//package com.utcl.m1service.pdf.pdfBoxTable.excel;
//
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.math.BigDecimal;
//import java.util.Iterator;
//
//import org.apache.poi.ss.usermodel.CellStyle;
//import org.apache.poi.ss.usermodel.HorizontalAlignment;
//import org.apache.poi.ss.usermodel.IndexedColors;
//import org.apache.poi.ss.usermodel.VerticalAlignment;
//import org.apache.poi.wp.usermodel.Paragraph;
//import org.apache.poi.xssf.usermodel.XSSFColor;
//import org.apache.poi.xssf.usermodel.XSSFFont;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//
//import com.utcl.m1service.pdf.pdfBoxTable.structure.Table;
//import com.utcl.m1service.pdf.pdfBoxTable.structure.Table.TableBuilder;
//import com.utcl.m1service.pdf.pdfBoxTable.structure.cell.TextCell;
//
//import org.apache.poi.ss.usermodel.CellType;
//import org.apache.poi.ss.usermodel.Font;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Cell;
//
//
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.apache.pdfbox.Loader;
//import org.apache.pdfbox.pdmodel.PDDocument;
//import org.apache.pdfbox.pdmodel.PDDocumentInformation;
//
//public class ExcelToPDFConverter {
//
//    private static final Logger logger = LogManager.getLogger(ExcelToPDFConverter.class);
//
//    public static XSSFWorkbook readExcelFile(String excelFilePath) throws IOException {
//        FileInputStream inputStream = new FileInputStream(excelFilePath);
//        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
//        inputStream.close();
//        return workbook;
//    }
//
//    private static PDDocument createPDFDocument(String pdfFilePath) throws IOException {
//    	
//    	File newFile = new File(pdfFilePath);
//    	InputStream inputStream = new FileInputStream(newFile);
//    	PDDocument document = Loader.loadPDF(Utility.readAllBytes(inputStream));
//        //document.open();
//        return document;
//    }
//
//    public static void convertExcelToPDF(String excelFilePath, String pdfFilePath) throws IOException {
//        XSSFWorkbook workbook = readExcelFile(excelFilePath);
//        PDDocument document = createPDFDocument(pdfFilePath);
//
//        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
//            XSSFSheet worksheet = workbook.getSheetAt(i);
//            
//            PDDocumentInformation pdd = document.getDocumentInformation();  
//            pdd.setTitle(worksheet.getSheetName());   
//            // Add header with sheet name as title
////            Paragraph title = new Paragraph(worksheet.getSheetName(), new Font);
////            title.setSpacingAfter(20f);
////            title.setAlignment(Element.ALIGN_CENTER);
////            document.add(title);
//            createAndAddTable(worksheet, document);
//            // Add a new page for each sheet (except the last one)
//            if (i < workbook.getNumberOfSheets() - 1) {
//                document.newPage();
//            }
//        }
//
//        document.close();
//        workbook.close();
//    }
//
//    private static void createAndAddTable(XSSFSheet worksheet, PDDocument document) throws DocumentException, IOException {
//    	final TableBuilder tableBuilder = Table.builder();
//    	
//    	Table table = tableBuilder.build(); ;//new PdfPTable(worksheet.getRow(0).getPhysicalNumberOfCells());
//        //table.setWidthPercentage(100);
//        addTableHeader(worksheet, table);
//        addTableData(worksheet, table);
//        document.add(table);
//    }
//
//    private static void addTableHeader(XSSFSheet worksheet, Table table) throws DocumentException, IOException {
//        Row headerRow = worksheet.getRow(0);
//        for (int i = 0; i < headerRow.getPhysicalNumberOfCells(); i++) {
//            Cell cell = headerRow.getCell(i);
//            String headerText = getCellText(cell);
//            //TextCell.builder().text("Product").horizontalAlignment(HorizontalAlignment.LEFT).borderWidth(1).build()
//            TextCell headerCell = new PdfPCell(new Phrase(headerText, getCellStyle(cell)));
//            setBackgroundColor(cell, headerCell);
//            setCellAlignment(cell, headerCell);
//            table.addCell(headerCell);
//        }
//    }
//
//    public static String getCellText(Cell cell) {
//        String cellValue;
//        switch (cell.getCellType()) {
//        case STRING:
//            cellValue = cell.getStringCellValue();
//            break;
//        case NUMERIC:
//            cellValue = String.valueOf(BigDecimal.valueOf(cell.getNumericCellValue()));
//            break;
//        case BLANK:
//        default:
//            cellValue = "";
//            break;
//        }
//        return cellValue;
//    }
//
//    private static void addTableData(XSSFSheet worksheet, PdfPTable table) throws DocumentException, IOException {
//        Iterator<Row> rowIterator = worksheet.iterator();
//        while (rowIterator.hasNext()) {
//            Row row = rowIterator.next();
//            if (row.getRowNum() == 0) {
//                continue;
//            }
//            for (int i = 0; i < row.getPhysicalNumberOfCells(); i++) {
//                Cell cell = row.getCell(i);
//                String cellValue = getCellText(cell);
//                PdfPCell cellPdf = new PdfPCell(new Phrase(cellValue, getCellStyle(cell)));
//                setBackgroundColor(cell, cellPdf);
//                setCellAlignment(cell, cellPdf);
//                table.addCell(cellPdf);
//            }
//        }
//    }
//
//    private static void setBackgroundColor(Cell cell, PdfPCell cellPdf) {
//        // Set background color
//        short bgColorIndex = cell.getCellStyle()
//            .getFillForegroundColor();
//        if (bgColorIndex != IndexedColors.AUTOMATIC.getIndex()) {
//            XSSFColor bgColor = (XSSFColor) cell.getCellStyle()
//                .getFillForegroundColorColor();
//            if (bgColor != null) {
//                byte[] rgb = bgColor.getRGB();
//                if (rgb != null && rgb.length == 3) {
//                    cellPdf.setBackgroundColor(new BaseColor(rgb[0] & 0xFF, rgb[1] & 0xFF, rgb[2] & 0xFF));
//                }
//            }
//        }
//    }
//
//    private static void setCellAlignment(Cell cell, PdfPCell cellPdf) {
//        CellStyle cellStyle = cell.getCellStyle();
//
//        HorizontalAlignment horizontalAlignment = cellStyle.getAlignment();
//        VerticalAlignment verticalAlignment = cellStyle.getVerticalAlignment();
//
//        switch (horizontalAlignment) {
//        case LEFT:
//            cellPdf.setHorizontalAlignment(Element.ALIGN_LEFT);
//            break;
//        case CENTER:
//            cellPdf.setHorizontalAlignment(Element.ALIGN_CENTER);
//            break;
//        case JUSTIFY:
//        case FILL:
//            cellPdf.setVerticalAlignment(Element.ALIGN_JUSTIFIED);
//            break;
//        case RIGHT:
//            cellPdf.setHorizontalAlignment(Element.ALIGN_RIGHT);
//            break;
//        }
//
//        switch (verticalAlignment) {
//        case TOP:
//            cellPdf.setVerticalAlignment(Element.ALIGN_TOP);
//            break;
//        case CENTER:
//            cellPdf.setVerticalAlignment(Element.ALIGN_MIDDLE);
//            break;
//        case JUSTIFY:
//            cellPdf.setVerticalAlignment(Element.ALIGN_JUSTIFIED);
//            break;
//        case BOTTOM:
//            cellPdf.setVerticalAlignment(Element.ALIGN_BOTTOM);
//            break;
//        }
//    }
//
//    private static Font getCellStyle(Cell cell) throws DocumentException, IOException {
//        Font font = new Font();
//        CellStyle cellStyle = cell.getCellStyle();
//        org.apache.poi.ss.usermodel.Font cellFont = cell.getSheet()
//            .getWorkbook()
//            .getFontAt(cellStyle.getFontIndexAsInt());
//
//        short fontColorIndex = cellFont.getColor();
//        if (fontColorIndex != IndexedColors.AUTOMATIC.getIndex() && cellFont instanceof XSSFFont) {
//            XSSFColor fontColor = ((XSSFFont) cellFont).getXSSFColor();
//            if (fontColor != null) {
//                byte[] rgb = fontColor.getRGB();
//                if (rgb != null && rgb.length == 3) {
//                    font.setColor(new BaseColor(rgb[0] & 0xFF, rgb[1] & 0xFF, rgb[2] & 0xFF));
//                }
//            }
//        }
//
//        if (cellFont.getItalic()) {
//            font.setStyle(Font.ITALIC);
//        }
//
//        if (cellFont.getStrikeout()) {
//            font.setStyle(Font.STRIKETHRU);
//        }
//
//        if (cellFont.getUnderline() == 1) {
//            font.setStyle(Font.UNDERLINE);
//        }
//
//        short fontSize = cellFont.getFontHeightInPoints();
//        font.setSize(fontSize);
//
//        if (cellFont.getBold()) {
//            font.setStyle(Font.BOLD);
//        }
//
//        String fontName = cellFont.getFontName();
//        if (FontFactory.isRegistered(fontName)) {
//            font.setFamily(fontName); // Use extracted font family if supported by iText
//        } else {
//            logger.warn("Unsupported font type: {}", fontName);
//            // - Use a fallback font (e.g., Helvetica)
//            font.setFamily("Helvetica");
//        }
//
//        return font;
//    }
//
//    public static void main(String[] args) throws DocumentException, IOException {
//        String excelFilePath = "src/main/resources/excelsample.xlsx";
//        String pdfFilePath = "src/main/resources/pdfsample.pdf";
//        convertExcelToPDF(excelFilePath, pdfFilePath);
//    }
//}
package com.utcl.ccnf.m1service.pdf.pdfBoxTable.excel;



