package com.utcl.ccnf.m1service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.itextpdf.io.font.FontProgram;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.BaseDirection;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfNumber;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEvent;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.utcl.Utility;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PdfGenerator {

	public static PdfPTable headerTable;
	public static PdfPTable footerTable;
	@SuppressWarnings("deprecation")
	static BaseColor cellBackGround = WebColors.getRGBColor("#eeeeee");
	static Font normalBig = new Font(Font.FontFamily.TIMES_ROMAN, 13.0f, Font.NORMAL, BaseColor.BLACK);
	static Font boldBig = new Font(Font.FontFamily.TIMES_ROMAN, 13.0f, Font.BOLD, BaseColor.BLACK);
	static Font f = new Font(Font.FontFamily.TIMES_ROMAN, 10.0f, Font.BOLD, BaseColor.BLACK);
	static Font f2 = new Font(Font.FontFamily.TIMES_ROMAN, 5.0f, Font.BOLD, BaseColor.BLACK);
	static Font f3 = new Font(Font.FontFamily.TIMES_ROMAN, 5.0f, Font.NORMAL, BaseColor.BLACK);
	static Font f4 = new Font(Font.FontFamily.TIMES_ROMAN, 6.0f, Font.NORMAL, BaseColor.BLACK);

	public static byte[] goDownReciept(Map<String, Object> data1) {
		byte[] out = null;
		File file = null;
		try {
			String dir = System.getProperty("user.dir");
			file = new File(dir + "/test.pdf");
			PdfDocument pdfDoc = new PdfDocument(
					new com.itextpdf.kernel.pdf.PdfWriter(System.getProperty("user.dir") + "/test.pdf"));
			FontProgram boldProgram = FontProgramFactory.createFont(dir + "/arialuni.ttf");
			FontProgram normalProgram = FontProgramFactory.createFont(dir + "/arialuni.ttf");
			PdfFont boldFont = PdfFontFactory.createFont(boldProgram, PdfEncodings.IDENTITY_H);
			PdfFont normalFont = PdfFontFactory.createFont(normalProgram);
			com.itextpdf.kernel.geom.Rectangle r = new com.itextpdf.kernel.geom.Rectangle(20, 20, 612, 792.50f);

			com.itextpdf.layout.Document doc = new com.itextpdf.layout.Document(pdfDoc,
					new com.itextpdf.kernel.geom.PageSize(r));

			doc.setMargins(17, 30, 30, 30);
			// doc.setBorder(new SolidBorder(2));
			// doc.setRenderer(new CustomDocumentRenderer(doc));

			Cell c = new Cell();
			float f[] = { 55f, 45f };
			Table table = new Table(UnitValue.createPercentArray(f)).useAllAvailableWidth();
			table.setVerticalAlignment(VerticalAlignment.BOTTOM);
			table.setWidth(UnitValue.createPercentValue(100));
			table.setVerticalAlignment(VerticalAlignment.BOTTOM);
			// table.setMargin(20);
			// table.setPadding(20);
			// c.add(new Paragraph("-----"));
			// c.setBorder(Border.NO_BORDER);

			// table.addCell(c);
			c = createTextCell("Tax Invoice", boldFont, Border.NO_BORDER, Border.NO_BORDER, Border.NO_BORDER,
					Border.NO_BORDER, TextAlignment.RIGHT, VerticalAlignment.BOTTOM).setFontSize(10.0f).setBold();

			table.addCell(c);
			c = createTextCell("(ORIGINAL FOR RECIPIENT)", normalFont, Border.NO_BORDER, Border.NO_BORDER,
					Border.NO_BORDER, Border.NO_BORDER, TextAlignment.RIGHT, VerticalAlignment.BOTTOM).setFontSize(9.0f)
					.setItalic();

			table.addCell(c);

			Table firstHalf = new Table(UnitValue.createPercentArray(1)).useAllAvailableWidth();
			firstHalf.setVerticalAlignment(VerticalAlignment.BOTTOM);
			firstHalf.setTextAlignment(TextAlignment.JUSTIFIED);

			firstHalf.addCell(createTextCell("Nauratans Builders Pvt. Ltd.", boldFont, Border.NO_BORDER,
					Border.NO_BORDER, Border.NO_BORDER, Border.NO_BORDER, TextAlignment.LEFT, VerticalAlignment.BOTTOM)
					.setFontSize(9.0f).setBold());
			// State Name : West Bengal, Code : 19
			firstHalf.addCell(
					createTextCell("1, Ripon Street", normalFont, Border.NO_BORDER, Border.NO_BORDER, Border.NO_BORDER,
							Border.NO_BORDER, TextAlignment.LEFT, VerticalAlignment.BOTTOM).setFontSize(9.0f));

			firstHalf.addCell(createTextCell("Kolkata - 700 016", normalFont, Border.NO_BORDER, Border.NO_BORDER,
					Border.NO_BORDER, Border.NO_BORDER, TextAlignment.LEFT, VerticalAlignment.BOTTOM)
					.setFontSize(9.0f));

			firstHalf.addCell(createTextCell("GSTIN/UIN: 19AACCN2273R1ZU", normalFont, Border.NO_BORDER,
					Border.NO_BORDER, Border.NO_BORDER, Border.NO_BORDER, TextAlignment.LEFT, VerticalAlignment.BOTTOM)
					.setFontSize(9.0f));

			firstHalf.addCell(createTextCell("State Name : West Bengal, Code : 19", normalFont, new SolidBorder(0.5f),
					Border.NO_BORDER, Border.NO_BORDER, Border.NO_BORDER, TextAlignment.LEFT, VerticalAlignment.BOTTOM)
					.setFontSize(9.0f));

			// Buyer (Bill to)

			firstHalf.addCell(
					createTextCell("Buyer (Bill to)", normalFont, Border.NO_BORDER, Border.NO_BORDER, Border.NO_BORDER,
							Border.NO_BORDER, TextAlignment.LEFT, VerticalAlignment.BOTTOM).setFontSize(9.0f));

			firstHalf.addCell(createTextCell("Ultratech Cement Limited", boldFont, Border.NO_BORDER, Border.NO_BORDER,
					Border.NO_BORDER, Border.NO_BORDER, TextAlignment.LEFT, VerticalAlignment.BOTTOM).setFontSize(9.0f)
					.setBold());

			firstHalf.addCell(createTextCell("PS Arcadia Central, 5th Floor, 4A, Abanindra Nath", normalFont,
					Border.NO_BORDER, Border.NO_BORDER, Border.NO_BORDER, Border.NO_BORDER, TextAlignment.LEFT,
					VerticalAlignment.BOTTOM).setFontSize(9.0f));

			firstHalf.addCell(createTextCell("Thakur Sarani, Kolkata - 700 017", normalFont, Border.NO_BORDER,
					Border.NO_BORDER, Border.NO_BORDER, Border.NO_BORDER, TextAlignment.LEFT, VerticalAlignment.BOTTOM)
					.setFontSize(9.0f));

			firstHalf.addCell(createTextCell("GSTIN/UIN : 19AAACL6442L1Z7", normalFont, Border.NO_BORDER,
					Border.NO_BORDER, Border.NO_BORDER, Border.NO_BORDER, TextAlignment.LEFT, VerticalAlignment.BOTTOM)
					.setFontSize(9.0f));

			firstHalf.addCell(createTextCell("PAN/IT No : AAACL6442L", normalFont, Border.NO_BORDER, Border.NO_BORDER,
					Border.NO_BORDER, Border.NO_BORDER, TextAlignment.LEFT, VerticalAlignment.BOTTOM)
					.setFontSize(9.0f));

			firstHalf.addCell(createTextCell("State Name : West Bengal, Code : 19", normalFont, Border.NO_BORDER,
					Border.NO_BORDER, Border.NO_BORDER, Border.NO_BORDER, TextAlignment.LEFT, VerticalAlignment.BOTTOM)
					.setFontSize(9.0f));

			firstHalf.addCell(createTextCell("Place of Supply : West Bengal", normalFont, Border.NO_BORDER,
					Border.NO_BORDER, Border.NO_BORDER, Border.NO_BORDER, TextAlignment.LEFT, VerticalAlignment.BOTTOM)
					.setFontSize(9.0f));

			table.addCell(firstHalf);

			Table secondHalf = new Table(UnitValue.createPercentArray(1)).useAllAvailableWidth();
			secondHalf.setVerticalAlignment(VerticalAlignment.BOTTOM);
			secondHalf.setTextAlignment(TextAlignment.JUSTIFIED);
			secondHalf.setBorder(Border.NO_BORDER);

			float secondHalfCont[] = { 50f, 50f };
			Table secondHalfContTable = new Table(UnitValue.createPercentArray(secondHalfCont)).useAllAvailableWidth();
			secondHalfContTable.setBorder(Border.NO_BORDER);

			c = createTextCell("Invoice No.", boldFont, Border.NO_BORDER, Border.NO_BORDER, Border.NO_BORDER,
					new SolidBorder(0.5f), TextAlignment.LEFT, VerticalAlignment.BOTTOM).setFontSize(9.0f);
			// c.add();
			secondHalfContTable.addCell(c);

			c = createTextCell("Dated", boldFont, Border.NO_BORDER, Border.NO_BORDER, Border.NO_BORDER,
					Border.NO_BORDER, TextAlignment.LEFT, VerticalAlignment.BOTTOM).setFontSize(9.0f).setPaddingLeft(5);
			// c.add();
			secondHalfContTable.addCell(c);

			c = createTextCell("NBPL/0006/24", boldFont, new SolidBorder(0.5f), Border.NO_BORDER, Border.NO_BORDER,
					new SolidBorder(0.5f), TextAlignment.LEFT, VerticalAlignment.BOTTOM).setFontSize(9.0f).setBold();
			// c.add();
			secondHalfContTable.addCell(c);

			c = createTextCell("2-May-24", boldFont, new SolidBorder(0.5f), Border.NO_BORDER, Border.NO_BORDER,
					Border.NO_BORDER, TextAlignment.LEFT, VerticalAlignment.BOTTOM).setFontSize(9.0f).setPaddingLeft(5)
					.setBold();
			// c.add();
			secondHalfContTable.addCell(c);

			c = createTextCell("Reference No. & Date.", boldFont, Border.NO_BORDER, Border.NO_BORDER, Border.NO_BORDER,
					new SolidBorder(0.5f), TextAlignment.LEFT, VerticalAlignment.BOTTOM).setFontSize(9.0f);
			// c.add();
			secondHalfContTable.addCell(c);

			c = createTextCell("Other References", boldFont, Border.NO_BORDER, Border.NO_BORDER, Border.NO_BORDER,
					Border.NO_BORDER, TextAlignment.LEFT, VerticalAlignment.BOTTOM).setFontSize(9.0f).setPaddingLeft(5);
			// c.add();
			secondHalfContTable.addCell(c);

			c = createTextCell("04-12 dt. 2-May-24", boldFont, new SolidBorder(0.5f), Border.NO_BORDER,
					Border.NO_BORDER, new SolidBorder(0.5f), TextAlignment.LEFT, VerticalAlignment.BOTTOM)
					.setFontSize(9.0f).setBold();
			// c.add();
			secondHalfContTable.addCell(c);

			c = createTextCell("", boldFont, new SolidBorder(0.5f), Border.NO_BORDER, Border.NO_BORDER,
					Border.NO_BORDER, TextAlignment.LEFT, VerticalAlignment.BOTTOM).setFontSize(9.0f).setPaddingLeft(5)
					.setBold();
			// c.add();
			secondHalfContTable.addCell(c);

			secondHalfContTable.setBorder(Border.NO_BORDER);
			secondHalfContTable.setPadding(0);
			c = new Cell();
			c.setBorder(Border.NO_BORDER);
			c.setPadding(0);
			c.add(secondHalfContTable);
			secondHalf.addCell(c);

			table.addCell(secondHalf);


			float headerArr[] = { 66f, 8.8f, 8.2f, 4f, 13f };
			Table perticularsTable = new Table(UnitValue.createPercentArray(headerArr)).useAllAvailableWidth();
			perticularsTable.setMinHeight(300);
			perticularsTable.setBorder(new SolidBorder(0.5f));
			perticularsTable.setBorderTop(Border.NO_BORDER);
			perticularsTable.addHeaderCell(createTextCell("Particulars", boldFont, Border.NO_BORDER, Border.NO_BORDER,
					Border.NO_BORDER, new SolidBorder(0.5f), TextAlignment.CENTER, VerticalAlignment.TOP)
					.setFontSize(9.0f).setPaddingLeft(0).setBorder(Border.NO_BORDER).setHeight(18));
			perticularsTable.addHeaderCell(createTextCell("HSN/SAC", boldFont, Border.NO_BORDER, Border.NO_BORDER,
					Border.NO_BORDER, new SolidBorder(0.5f), TextAlignment.CENTER, VerticalAlignment.TOP)
					.setFontSize(9.0f).setPaddingLeft(0).setBorder(Border.NO_BORDER));
			perticularsTable.addHeaderCell(createTextCell("Rate", boldFont, Border.NO_BORDER, Border.NO_BORDER,
					Border.NO_BORDER, new SolidBorder(0.5f), TextAlignment.CENTER, VerticalAlignment.TOP)
					.setFontSize(9.0f).setPaddingLeft(0).setBorder(Border.NO_BORDER));
			perticularsTable.addHeaderCell(createTextCell("per", boldFont, Border.NO_BORDER, Border.NO_BORDER,
					Border.NO_BORDER, new SolidBorder(0.5f), TextAlignment.CENTER, VerticalAlignment.TOP)
					.setFontSize(9.0f).setPaddingLeft(0).setBorder(Border.NO_BORDER));
			perticularsTable.addHeaderCell(createTextCell("Amount", boldFont, Border.NO_BORDER, Border.NO_BORDER,
					Border.NO_BORDER, new SolidBorder(0.5f), TextAlignment.CENTER, VerticalAlignment.TOP)
					.setFontSize(9.0f).setPaddingLeft(0).setBorder(Border.NO_BORDER));

			
			
				float perticularInnarCellArr[] = { 40f, 60f };
				Table perticularInnarCol = new Table(UnitValue.createPercentArray(perticularInnarCellArr)).useAllAvailableWidth();
				perticularInnarCol.addCell(createTextCell("Lease Rent", boldFont, Border.NO_BORDER, Border.NO_BORDER,
					Border.NO_BORDER, Border.NO_BORDER, TextAlignment.LEFT, VerticalAlignment.TOP)
					.setFontSize(9.0f).setPaddingLeft(10).setBorder(Border.NO_BORDER).setBold());
				perticularInnarCol.addCell(createTextCell("", boldFont, Border.NO_BORDER, Border.NO_BORDER,
						Border.NO_BORDER, Border.NO_BORDER, TextAlignment.RIGHT, VerticalAlignment.TOP)
						.setFontSize(9.0f).setPaddingLeft(0).setBorder(Border.NO_BORDER).setBold());
				perticularInnarCol.addCell(createTextCell("", boldFont, Border.NO_BORDER, Border.NO_BORDER,
						Border.NO_BORDER, Border.NO_BORDER, TextAlignment.RIGHT, VerticalAlignment.TOP)
						.setFontSize(9.0f).setPaddingLeft(0).setBorder(Border.NO_BORDER).setBold());
				perticularInnarCol.addCell(createTextCell("Output CGST 9%", boldFont, Border.NO_BORDER, Border.NO_BORDER,
						Border.NO_BORDER, Border.NO_BORDER, TextAlignment.RIGHT, VerticalAlignment.TOP)
						.setFontSize(9.0f).setPaddingLeft(0).setBorder(Border.NO_BORDER).setBold());
				
				perticularInnarCol.addCell(createTextCell("", boldFont, Border.NO_BORDER, Border.NO_BORDER,
						Border.NO_BORDER, Border.NO_BORDER, TextAlignment.RIGHT, VerticalAlignment.TOP)
						.setFontSize(9.0f).setPaddingLeft(0).setBorder(Border.NO_BORDER).setBold());
				perticularInnarCol.addCell(createTextCell("Output SGST 9%", boldFont, Border.NO_BORDER, Border.NO_BORDER,
						Border.NO_BORDER, Border.NO_BORDER, TextAlignment.RIGHT, VerticalAlignment.TOP)
						.setFontSize(9.0f).setPaddingLeft(0).setBorder(Border.NO_BORDER).setBold());
				
			Cell cell = new Cell(1, 1).add(perticularInnarCol);
			//cell.setMinHeight(250);
			perticularsTable.addCell(cell);

			cell = createTextCell("997212", boldFont, new SolidBorder(0.5f), new SolidBorder(0.5f),
					Border.NO_BORDER, Border.NO_BORDER, TextAlignment.CENTER, VerticalAlignment.TOP)
					.setFontSize(9.0f).setPaddingLeft(0).setBorder(Border.NO_BORDER);
			//cell.setMinHeight(300);
			perticularsTable.addCell(cell);

			
			
			float perticularRateCellArr[] = { 100f};
			Table perticularRateCol = new Table(UnitValue.createPercentArray(perticularRateCellArr)).useAllAvailableWidth();
			perticularRateCol.addCell(createTextCell("-", boldFont, Border.NO_BORDER, Border.NO_BORDER,
				Border.NO_BORDER, Border.NO_BORDER, TextAlignment.LEFT, VerticalAlignment.TOP)
				.setFontSize(9.0f).setPaddingLeft(10).setBorder(Border.NO_BORDER).setOpacity(0f));
			
			perticularRateCol.addCell(createTextCell("9", boldFont, Border.NO_BORDER, Border.NO_BORDER,
					Border.NO_BORDER, Border.NO_BORDER, TextAlignment.RIGHT, VerticalAlignment.TOP)
					.setFontSize(9.0f).setPaddingLeft(0).setBorder(Border.NO_BORDER));
			perticularRateCol.addCell(createTextCell("9", boldFont, Border.NO_BORDER, Border.NO_BORDER,
					Border.NO_BORDER, Border.NO_BORDER, TextAlignment.RIGHT, VerticalAlignment.TOP)
					.setFontSize(9.0f).setPaddingLeft(0).setBorder(Border.NO_BORDER));
			
			cell = new Cell(1, 1).add(perticularRateCol);
			//cell = new Cell(1, 1).add(new Paragraph("asdf"));
			//cell.setMinHeight(300);
			perticularsTable.addCell(cell);

			float perticularPerCellArr[] = { 100f};
			Table perticularPerCol = new Table(UnitValue.createPercentArray(perticularPerCellArr)).useAllAvailableWidth();
			perticularPerCol.addCell(createTextCell("-", boldFont, Border.NO_BORDER, Border.NO_BORDER,
				Border.NO_BORDER, Border.NO_BORDER, TextAlignment.LEFT, VerticalAlignment.TOP)
				.setFontSize(9.0f).setPaddingLeft(10).setBorder(Border.NO_BORDER).setOpacity(0f));
			
			perticularPerCol.addCell(createTextCell("%", boldFont, Border.NO_BORDER, Border.NO_BORDER,
					Border.NO_BORDER, Border.NO_BORDER, TextAlignment.LEFT, VerticalAlignment.TOP)
					.setFontSize(9.0f).setPaddingLeft(0).setBorder(Border.NO_BORDER));
			perticularPerCol.addCell(createTextCell("%", boldFont, Border.NO_BORDER, Border.NO_BORDER,
					Border.NO_BORDER, Border.NO_BORDER, TextAlignment.LEFT, VerticalAlignment.TOP)
					.setFontSize(9.0f).setPaddingLeft(0).setBorder(Border.NO_BORDER));
			
			cell = new Cell(1, 1).add(perticularPerCol);
			//cell.setMinHeight(300);
			perticularsTable.addCell(cell);

			float perticularAmmountCellArr[] = { 100f};
			Table perticularAmountCol = new Table(UnitValue.createPercentArray(perticularAmmountCellArr)).useAllAvailableWidth();
			perticularAmountCol.addCell(createTextCell("11,07,002.00", boldFont, Border.NO_BORDER, Border.NO_BORDER,
				Border.NO_BORDER, Border.NO_BORDER, TextAlignment.LEFT, VerticalAlignment.TOP)
				.setFontSize(9.0f).setPaddingLeft(10).setBorder(Border.NO_BORDER).setBold());
			
			perticularAmountCol.addCell(createTextCell("99,630.18", boldFont, Border.NO_BORDER, Border.NO_BORDER,
					Border.NO_BORDER, Border.NO_BORDER, TextAlignment.RIGHT, VerticalAlignment.TOP)
					.setFontSize(9.0f).setPaddingLeft(0).setBorder(Border.NO_BORDER).setBold());
			perticularAmountCol.addCell(createTextCell("99,630.18", boldFont, Border.NO_BORDER, Border.NO_BORDER,
					Border.NO_BORDER, Border.NO_BORDER, TextAlignment.RIGHT, VerticalAlignment.TOP)
					.setFontSize(9.0f).setPaddingLeft(0).setBorder(Border.NO_BORDER).setBold());
			
			cell = new Cell(1, 1).add(perticularAmountCol);
			perticularsTable.addCell(cell);
			// perticularsTable.setMinHeight(300);

			perticularsTable.addFooterCell(createTextCell("Total", boldFont, Border.NO_BORDER, Border.NO_BORDER,
					Border.NO_BORDER, new SolidBorder(0.5f), TextAlignment.RIGHT, VerticalAlignment.TOP)
					.setFontSize(9.0f).setPaddingRight(5).setBorder(Border.NO_BORDER));
			perticularsTable.addFooterCell(createTextCell("", boldFont, Border.NO_BORDER, Border.NO_BORDER,
					Border.NO_BORDER, new SolidBorder(0.5f), TextAlignment.CENTER, VerticalAlignment.TOP)
					.setFontSize(9.0f).setPaddingLeft(0).setBorder(Border.NO_BORDER));
			perticularsTable.addFooterCell(createTextCell("", boldFont, Border.NO_BORDER, Border.NO_BORDER,
					Border.NO_BORDER, new SolidBorder(0.5f), TextAlignment.CENTER, VerticalAlignment.TOP)
					.setFontSize(9.0f).setPaddingLeft(0).setBorder(Border.NO_BORDER));
			perticularsTable.addFooterCell(createTextCell("", boldFont, Border.NO_BORDER, Border.NO_BORDER,
					Border.NO_BORDER, new SolidBorder(0.5f), TextAlignment.CENTER, VerticalAlignment.TOP)
					.setFontSize(9.0f).setPaddingLeft(0).setBorder(Border.NO_BORDER));
			perticularsTable
					.addFooterCell(createTextCell("Rs. 13,06,262.36", boldFont, Border.NO_BORDER, Border.NO_BORDER,
							Border.NO_BORDER, new SolidBorder(0.5f), TextAlignment.CENTER, VerticalAlignment.TOP)
							.setFontSize(9.0f).setPaddingLeft(0).setBorder(Border.NO_BORDER));

			
			
			float amtChTextArr[] = { 70f, 30 };
			Table amtChTextTbl = new Table(UnitValue.createPercentArray(amtChTextArr)).useAllAvailableWidth();
			amtChTextTbl.setMarginTop(-2.5f);
			amtChTextTbl.addCell(createTextCell("Amount Chargeable (in words)", boldFont, Border.NO_BORDER, Border.NO_BORDER,
					new SolidBorder(0.5f), Border.NO_BORDER, TextAlignment.LEFT, VerticalAlignment.TOP)
							.setFontSize(8.0f).setPaddingLeft(2).setBorder(Border.NO_BORDER));
			amtChTextTbl.addCell(createTextCell("E. & O.E", boldFont, Border.NO_BORDER, Border.NO_BORDER,
					Border.NO_BORDER, new SolidBorder(0.5f), TextAlignment.RIGHT, VerticalAlignment.TOP)
					.setFontSize(8.5f).setPaddingRight(1).setBorder(Border.NO_BORDER).setItalic());
			
			amtChTextTbl.addCell(createTextCell("INR Thirteen Lakh Six Thousand Two Hundred Sixty Two and Thirty Six paise Only", boldFont, Border.NO_BORDER, Border.NO_BORDER,
					new SolidBorder(0.5f), Border.NO_BORDER, TextAlignment.LEFT, VerticalAlignment.TOP)
							.setFontSize(9.0f).setPaddingLeft(2).setBorder(Border.NO_BORDER).setBold());
			amtChTextTbl.addCell(createTextCell("-", boldFont, Border.NO_BORDER, Border.NO_BORDER,
					Border.NO_BORDER, new SolidBorder(0.5f), TextAlignment.RIGHT, VerticalAlignment.TOP)
					.setFontSize(8.5f).setPaddingRight(1).setBorder(Border.NO_BORDER).setOpacity(0f));
			
			float taxableArr[] = { 48f, 16.6f, 16.6f, 16.6f};
			Table taxableTbl = new Table(UnitValue.createPercentArray(taxableArr)).useAllAvailableWidth();
			
				Table taxableTblCol1 = new Table(UnitValue.createPercentArray(1)).useAllAvailableWidth();
				taxableTblCol1.setBorder(Border.NO_BORDER);
				taxableTblCol1.setMarginLeft(-3);
				taxableTblCol1.setMarginRight(-3);
				//taxableTblCol1.setBorderBottom(new SolidBorder(0.5f));
				//taxableTblCol1.setBorderLeft(new SolidBorder(0.5f));
				taxableTblCol1.addCell(createTextCell("Taxable", boldFont, Border.NO_BORDER, Border.NO_BORDER,
						Border.NO_BORDER, Border.NO_BORDER, TextAlignment.RIGHT, VerticalAlignment.TOP)
								.setFontSize(9.0f).setPaddingRight(2).setBorder(Border.NO_BORDER));
				
				taxableTblCol1.addCell(createTextCell("Value", boldFont, new SolidBorder(0.5f), Border.NO_BORDER,
						Border.NO_BORDER, Border.NO_BORDER, TextAlignment.RIGHT, VerticalAlignment.TOP)
								.setFontSize(9.0f).setPaddingRight(2).setBorder(Border.NO_BORDER));
				taxableTblCol1.addCell(createTextCell("11,07,002.00", boldFont, new SolidBorder(0.5f), Border.NO_BORDER,
						Border.NO_BORDER, Border.NO_BORDER, TextAlignment.RIGHT, VerticalAlignment.TOP)
								.setFontSize(9.0f).setPaddingRight(2).setBorder(Border.NO_BORDER));
				
				taxableTblCol1.addCell(createTextCell("Total: 11,07,002.00", boldFont, Border.NO_BORDER, Border.NO_BORDER,
						Border.NO_BORDER, Border.NO_BORDER, TextAlignment.RIGHT, VerticalAlignment.TOP)
								.setFontSize(9.0f).setPaddingRight(2).setBorder(Border.NO_BORDER).setBold());
				
			taxableTbl.addCell(taxableTblCol1);
			
			float taxableTblCol2Arr[] = { 50f, 50f};
			Table taxableTblCol2 = new Table(UnitValue.createPercentArray(taxableTblCol2Arr)).useAllAvailableWidth();
			taxableTblCol2.setBorder(Border.NO_BORDER);
			taxableTblCol2.setMarginLeft(-3);
			taxableTblCol2.setMarginRight(-3);
			//taxableTblCol1.setBorderBottom(new SolidBorder(0.5f));
			//taxableTblCol1.setBorderLeft(new SolidBorder(0.5f));
			taxableTblCol2.addCell(createTextCellWithSpan("CGST", boldFont, new SolidBorder(0.5f), Border.NO_BORDER,
					Border.NO_BORDER, Border.NO_BORDER, TextAlignment.CENTER, VerticalAlignment.TOP,1,2)
					.setFontSize(9.0f).setPaddingLeft(2).setBorder(Border.NO_BORDER));
			
			
			taxableTblCol2.addCell(createTextCellWithSpan("Rate", boldFont, new SolidBorder(0.5f), Border.NO_BORDER,
					Border.NO_BORDER, new SolidBorder(0.5f), TextAlignment.CENTER, VerticalAlignment.TOP,1,1)
					.setFontSize(9.0f).setPaddingLeft(2).setBorder(Border.NO_BORDER));
			
			taxableTblCol2.addCell(createTextCellWithSpan("Amount", boldFont, new SolidBorder(0.5f), Border.NO_BORDER,
					Border.NO_BORDER, Border.NO_BORDER, TextAlignment.CENTER, VerticalAlignment.TOP,1,1)
					.setFontSize(9.0f).setPaddingLeft(2).setBorder(Border.NO_BORDER));
			
			taxableTblCol2.addCell(createTextCellWithSpan("9%", boldFont, new SolidBorder(0.5f), Border.NO_BORDER,
					Border.NO_BORDER, new SolidBorder(0.5f), TextAlignment.CENTER, VerticalAlignment.TOP,1,1)
					.setFontSize(9.0f).setPaddingLeft(2).setBorder(Border.NO_BORDER));
			
			taxableTblCol2.addCell(createTextCellWithSpan("99,630.18", boldFont, new SolidBorder(0.5f), Border.NO_BORDER,
					Border.NO_BORDER, Border.NO_BORDER, TextAlignment.CENTER, VerticalAlignment.TOP,1,1)
					.setFontSize(9.0f).setPaddingLeft(2).setBorder(Border.NO_BORDER));
			
			
			taxableTblCol2.addCell(createTextCellWithSpan("-", boldFont, Border.NO_BORDER, Border.NO_BORDER,
					Border.NO_BORDER, new SolidBorder(0.5f), TextAlignment.CENTER, VerticalAlignment.TOP,1,1)
					.setFontSize(9.0f).setPaddingLeft(2).setBorder(Border.NO_BORDER).setOpacity(0f));
			
			taxableTblCol2.addCell(createTextCellWithSpan("99,630.18", boldFont, Border.NO_BORDER, Border.NO_BORDER,
					Border.NO_BORDER, Border.NO_BORDER, TextAlignment.CENTER, VerticalAlignment.TOP,1,1)
					.setFontSize(9.0f).setPaddingLeft(2).setBorder(Border.NO_BORDER).setBold());
			
			taxableTbl.addCell(taxableTblCol2);
			
			
			
			
			float taxableTblCol3Arr[] = { 50f, 50f};
			Table taxableTblCol3 = new Table(UnitValue.createPercentArray(taxableTblCol3Arr)).useAllAvailableWidth();
			taxableTblCol3.setBorder(Border.NO_BORDER);
			taxableTblCol3.setMarginLeft(-3);
			taxableTblCol3.setMarginRight(-3);
			//taxableTblCol1.setBorderBottom(new SolidBorder(0.5f));
			//taxableTblCol1.setBorderLeft(new SolidBorder(0.5f));
			taxableTblCol3.addCell(createTextCellWithSpan("SGST/UTGST", boldFont, new SolidBorder(0.5f), Border.NO_BORDER,
					Border.NO_BORDER, Border.NO_BORDER, TextAlignment.CENTER, VerticalAlignment.TOP,1,2)
					.setFontSize(9.0f).setPaddingLeft(2).setBorder(Border.NO_BORDER));
			
			
			taxableTblCol3.addCell(createTextCellWithSpan("Rate", boldFont, new SolidBorder(0.5f), Border.NO_BORDER,
					Border.NO_BORDER, new SolidBorder(0.5f), TextAlignment.CENTER, VerticalAlignment.TOP,1,1)
					.setFontSize(9.0f).setPaddingLeft(2).setBorder(Border.NO_BORDER));
			
			taxableTblCol3.addCell(createTextCellWithSpan("Amount", boldFont, new SolidBorder(0.5f), Border.NO_BORDER,
					Border.NO_BORDER, Border.NO_BORDER, TextAlignment.CENTER, VerticalAlignment.TOP,1,1)
					.setFontSize(9.0f).setPaddingLeft(2).setBorder(Border.NO_BORDER));
			
			taxableTblCol3.addCell(createTextCellWithSpan("9%", boldFont, new SolidBorder(0.5f), Border.NO_BORDER,
					Border.NO_BORDER, new SolidBorder(0.5f), TextAlignment.CENTER, VerticalAlignment.TOP,1,1)
					.setFontSize(9.0f).setPaddingLeft(2).setBorder(Border.NO_BORDER));
			
			taxableTblCol3.addCell(createTextCellWithSpan("99,630.18", boldFont, new SolidBorder(0.5f), Border.NO_BORDER,
					Border.NO_BORDER, Border.NO_BORDER, TextAlignment.CENTER, VerticalAlignment.TOP,1,1)
					.setFontSize(9.0f).setPaddingLeft(2).setBorder(Border.NO_BORDER));
			
			
			taxableTblCol3.addCell(createTextCellWithSpan("-", boldFont, Border.NO_BORDER, Border.NO_BORDER,
					Border.NO_BORDER, new SolidBorder(0.5f), TextAlignment.CENTER, VerticalAlignment.TOP,1,1)
					.setFontSize(9.0f).setPaddingLeft(2).setBorder(Border.NO_BORDER).setOpacity(0f));
			
			taxableTblCol3.addCell(createTextCellWithSpan("99,630.18", boldFont, Border.NO_BORDER, Border.NO_BORDER,
					Border.NO_BORDER, Border.NO_BORDER, TextAlignment.CENTER, VerticalAlignment.TOP,1,1)
					.setFontSize(9.0f).setPaddingLeft(2).setBorder(Border.NO_BORDER).setBold());
			
			taxableTbl.addCell(taxableTblCol3);
			
			Table taxableTblCol4 = new Table(UnitValue.createPercentArray(1)).useAllAvailableWidth();
			taxableTblCol4.setBorder(Border.NO_BORDER);
			taxableTblCol4.setMarginLeft(-3);
			taxableTblCol4.setMarginRight(-3);
			//taxableTblCol1.setBorderBottom(new SolidBorder(0.5f));
			//taxableTblCol1.setBorderLeft(new SolidBorder(0.5f));
			taxableTblCol4.addCell(createTextCell("Total", boldFont, Border.NO_BORDER, Border.NO_BORDER,
					Border.NO_BORDER, Border.NO_BORDER, TextAlignment.CENTER, VerticalAlignment.TOP)
							.setFontSize(9.0f).setPaddingRight(2).setBorder(Border.NO_BORDER));
			
			taxableTblCol4.addCell(createTextCell("Tax Amount", boldFont, new SolidBorder(0.5f), Border.NO_BORDER,
					Border.NO_BORDER, Border.NO_BORDER, TextAlignment.CENTER, VerticalAlignment.TOP)
							.setFontSize(9.0f).setPaddingRight(2).setBorder(Border.NO_BORDER));
			taxableTblCol4.addCell(createTextCell("1,99,260.36", boldFont, new SolidBorder(0.5f), Border.NO_BORDER,
					Border.NO_BORDER, Border.NO_BORDER, TextAlignment.CENTER, VerticalAlignment.TOP)
							.setFontSize(9.0f).setPaddingRight(2).setBorder(Border.NO_BORDER));
			
			taxableTblCol4.addCell(createTextCell("1,99,260.36", boldFont, Border.NO_BORDER, Border.NO_BORDER,
					Border.NO_BORDER, Border.NO_BORDER, TextAlignment.CENTER, VerticalAlignment.TOP)
							.setFontSize(9.0f).setPaddingRight(2).setBorder(Border.NO_BORDER).setBold());
			
		taxableTbl.addCell(taxableTblCol4);
			//taxableTbl
			
			doc.add(table);
			doc.add(perticularsTable);
			doc.add(amtChTextTbl);
			 doc.add(taxableTbl);
			// doc.add(table2);
			doc.close();
			InputStream inputStream = new FileInputStream(file);
			out = Utility.readAllBytes(inputStream);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return out;
	}

	public static byte[] recieptWithBarCode(Map<String, Object> data1) {
		byte[] out = null;
		File file = null;
		try {
			String dir = System.getProperty("user.dir");
			file = new File(dir + "/test.pdf");
			PdfDocument pdfDoc = new PdfDocument(
					new com.itextpdf.kernel.pdf.PdfWriter(System.getProperty("user.dir") + "/test.pdf"));
			FontProgram boldProgram = FontProgramFactory.createFont(dir + "/arialuni.ttf");
			FontProgram normalProgram = FontProgramFactory.createFont(dir + "/arialuni.ttf");
			PdfFont boldFont = PdfFontFactory.createFont(boldProgram, PdfEncodings.IDENTITY_H);
			PdfFont normalFont = PdfFontFactory.createFont(normalProgram);
			com.itextpdf.kernel.geom.Rectangle r = new com.itextpdf.kernel.geom.Rectangle(50, 50, 350, 350);

			com.itextpdf.layout.Document doc = new com.itextpdf.layout.Document(pdfDoc,
					new com.itextpdf.kernel.geom.PageSize(r));

			doc.setMargins(0, 0, 0, 0);
			// doc.setBorder(new SolidBorder(2));
			// doc.setRenderer(new CustomDocumentRenderer(doc));

			Table container = new Table(UnitValue.createPercentArray(1)).useAllAvailableWidth();
			container.setVerticalAlignment(VerticalAlignment.BOTTOM);
			container.setTextAlignment(TextAlignment.JUSTIFIED);

			float f[] = { 30.10f, 70.3f };
			Table table = new Table(UnitValue.createPercentArray(f)).useAllAvailableWidth();
			table.setVerticalAlignment(VerticalAlignment.BOTTOM);
			table.setWidth(UnitValue.createPercentValue(100));
			table.setVerticalAlignment(VerticalAlignment.BOTTOM);
			SolidBorder b = new SolidBorder(0);

			Table dataAndImg = new Table(UnitValue.createPercentArray(1)).useAllAvailableWidth().setKeepTogether(true);

			Cell c = createImageCell(dir + "/logo.png", 25, b, Border.NO_BORDER, Border.NO_BORDER, Border.NO_BORDER);
			dataAndImg.setMinWidth(UnitValue.createPointValue(80));
			dataAndImg.setMarginLeft(-2);
			dataAndImg.addCell(c);

			Table data = new Table(UnitValue.createPercentArray(2)).useAllAvailableWidth().setKeepTogether(true);
			data.setVerticalAlignment(VerticalAlignment.BOTTOM);

			data.addCell(createTextCell("Date", boldFont, Border.NO_BORDER, Border.NO_BORDER, Border.NO_BORDER,
					Border.NO_BORDER, TextAlignment.CENTER, VerticalAlignment.BOTTOM).setFontSize(9.0f).setBold());

			data.addCell(createTextCell(data1.get("DATE").toString(), normalFont, Border.NO_BORDER, Border.NO_BORDER,
					Border.NO_BORDER, Border.NO_BORDER, TextAlignment.CENTER, VerticalAlignment.BOTTOM)
					.setFontSize(9.0f));
			// data.addCell(createTextCell("25.01.22",Border.NO_BORDER));
			c = new Cell();
			c.add(data);
			c.setBorder(Border.NO_BORDER);

			// b.setWidth(1);
			// c.setBorderTop(b);
			c.setVerticalAlignment(VerticalAlignment.BOTTOM);
			dataAndImg.addCell(c);

			table.addCell(dataAndImg);

			// c = createImageCell(dir + "/" + data1.get("HAWB").toString() + ".jpg", 45,
			// Border.NO_BORDER,
			// Border.NO_BORDER, Border.NO_BORDER, Border.NO_BORDER);
			Table bar = new Table(UnitValue.createPercentArray(1)).useAllAvailableWidth().setKeepTogether(true);
			// bar.addFooterCell(c);
			table.addCell(bar);

			c = new Cell();
			c.add(table);
			c.setBorder(Border.NO_BORDER);

			container.addCell(c);
			float row2Width[] = { 50f, 50.0f, 50.0f, 50.0f };
			Table table1 = new Table(new float[] { 1, 1, 1, 1 }).useAllAvailableWidth().setKeepTogether(true);
			// table1.setWidth(0);
			table1.addCell(createCell("Locker Id", 1, 1, TextAlignment.LEFT).setFontSize(10.0f).setBold());
			table1.addCell(createCell(data1.get("TOKEN").toString(), 1, 1, TextAlignment.LEFT).setFontSize(10.0f));
			table1.addCell(createCell("Terms", 1, 1, TextAlignment.LEFT).setFontSize(10.0f).setBold());
			table1.addCell(createCell("DDP", 1, 1, TextAlignment.LEFT).setFontSize(10.0f));
			table1.addCell(createCell("Storage Location: ", 1, 1, TextAlignment.LEFT).setFontSize(10.0f).setBold());
			table1.addCell(
					createCell(data1.get("STORAGE_LOCATION").toString(), 1, 1, TextAlignment.LEFT).setFontSize(10.0f));
			Locale l = new Locale("", data1.get("COUNTRY_CODE").toString());
			table1.addCell(createCell("Country", 1, 1, TextAlignment.LEFT).setFontSize(10.0f).setBold());
			table1.addCell(createCell(l.getDisplayCountry(), 1, 1, TextAlignment.LEFT).setFontSize(10.0f));
			c = new Cell();
			c.add(table1);
			c.setBorder(Border.NO_BORDER);
			Table table2 = new Table(new float[] { 1, 1 }).useAllAvailableWidth().setKeepTogether(true);

			Table table2ContentLeft = new Table(new float[] { 1, 1, 1 }).useAllAvailableWidth().setKeepTogether(true);
			table2ContentLeft.addCell(createCell("Sender : ", 1, 1, TextAlignment.LEFT).setFontSize(10.0f).setBold()
					.setBorder(Border.NO_BORDER));
			table2ContentLeft.addCell(
					createCell("", 1, 1, TextAlignment.LEFT).setFontSize(10.0f).setBold().setBorder(Border.NO_BORDER));
			table2ContentLeft.addCell(
					createCell("", 1, 1, TextAlignment.LEFT).setFontSize(10.0f).setBold().setBorder(Border.NO_BORDER));
			table2ContentLeft.addCell(createCell(data1.get("SENDER_NAME").toString(), 1, 1, TextAlignment.LEFT)
					.setFontSize(10.0f).setBorder(Border.NO_BORDER));
			c = new Cell();
			c.add(table2ContentLeft);
			// c.setBorder(Border.NO_BORDER);
			c.setMaxWidth(46.2f);
			c.setMinHeight(155);
			table2.addCell(c);

			// table2.addCell(createCell("Locker Id", 1, 1,
			// TextAlignment.LEFT).setFontSize(10.0f).setBold());

			Table table2ContentRight = new Table(new float[] { 1 }).useAllAvailableWidth().setKeepTogether(true);

			table2ContentRight
					.addCell(createCell("Deliver To:    ", 1, 1, TextAlignment.RIGHT).setPadding(5).setFontSize(10.0f)
							.setBold().setBorder(Border.NO_BORDER).setBorderBottom(b).setBorderRight(b))
					.setMaxWidth(70).setMaxHeight(50).setMarginLeft(-2.7f).setMarginTop(-1.54f);
			// table2ContentRight.addCell(createCell("", 1, 1,
			// TextAlignment.LEFT).setFontSize(10.0f).setBold().setBorder(Border.NO_BORDER));
			// table2ContentRight.addCell(createCell("", 1, 1,
			// TextAlignment.LEFT).setFontSize(10.0f).setBold().setBorder(Border.NO_BORDER));
			Cell nameCell = createCell(data1.get("NAME_USER").toString(), 1, 1, TextAlignment.LEFT).setFontSize(10.0f);
			if (Pattern.matches("[a-zA-Z]+", data1.get("NAME_USER").toString().replace(" ", ""))) {
				Style normal = new Style().setBaseDirection(BaseDirection.RIGHT_TO_LEFT)
						.setTextAlignment(TextAlignment.LEFT).setFont(boldFont);
				nameCell.addStyle(normal);
			} else {
				Style hebrewStyle = new Style().setBaseDirection(BaseDirection.RIGHT_TO_LEFT)
						.setTextAlignment(TextAlignment.RIGHT).setFont(boldFont);
				nameCell.setFontScript(Character.UnicodeScript.HEBREW).addStyle(hebrewStyle);
				// tableBodyCell.setPaddingRight(50);
			}
			table2ContentRight.addCell(nameCell);

			c = new Cell();
			c.add(table2ContentRight);
			table2.addCell(c);

			Table row3 = new Table(UnitValue.createPercentArray(2)).useAllAvailableWidth().setKeepTogether(true);
			row3.setMinHeight(70);
			row3.setWidth(UnitValue.createPercentValue(100));
			row3.setVerticalAlignment(VerticalAlignment.BOTTOM);

			Table row3FirstLeft = new Table(UnitValue.createPercentArray(1)).useAllAvailableWidth()
					.setKeepTogether(true);
			row3FirstLeft.addCell(createTextCell("Sender: ", boldFont, Border.NO_BORDER, Border.NO_BORDER,
					Border.NO_BORDER, Border.NO_BORDER, TextAlignment.CENTER, VerticalAlignment.TOP).setFontSize(10.0f)
					.setBold());
			row3FirstLeft.addCell(createTextCell(data1.get("SENDER_NAME").toString(), normalFont, Border.NO_BORDER,
					Border.NO_BORDER, Border.NO_BORDER, Border.NO_BORDER, TextAlignment.CENTER, VerticalAlignment.TOP)
					.setFontSize(10.0f));

			c = new Cell();
			c.add(row3FirstLeft);
			c.setBorder(Border.NO_BORDER);
			c.setBorderRight(new SolidBorder(0));
			row3.addCell(c);
			Table row3FirstRight = new Table(UnitValue.createPercentArray(1)).useAllAvailableWidth()
					.setKeepTogether(true);

			float del[] = { 50.0f, 50.0f };
			Table delT = new Table(UnitValue.createPercentArray(del)).useAllAvailableWidth().setKeepTogether(true);
			delT.addCell(createTextCell("Deliver To: ", boldFont, new SolidBorder(0), Border.NO_BORDER,
					Border.NO_BORDER, new SolidBorder(0), TextAlignment.CENTER, VerticalAlignment.TOP)
					.setFontSize(10.0f).setBold());
			delT.addCell(createTextCell("", boldFont, Border.NO_BORDER, Border.NO_BORDER, Border.NO_BORDER,
					Border.NO_BORDER, TextAlignment.CENTER, VerticalAlignment.TOP).setFontSize(10.0f).setBold());
			c = new Cell();
			c.add(delT);
			c.setBorder(Border.NO_BORDER);
			row3FirstRight.addCell(c);
			Cell nameCell1 = createTextCell(data1.get("NAME_USER").toString(), normalFont, Border.NO_BORDER,
					Border.NO_BORDER, Border.NO_BORDER, Border.NO_BORDER, TextAlignment.CENTER, VerticalAlignment.TOP)
					.setFontSize(10.0f);

			// row3FirstRight.addCell(createTextCell("",boldFont,new
			// SolidBorder(0),Border.NO_BORDER,Border.NO_BORDER,new
			// SolidBorder(0),TextAlignment.CENTER,VerticalAlignment.TOP).setFontSize(10.0f).setBold());
			row3FirstRight.addCell(nameCell);
			Cell ADDRESS = createTextCell(data1.get("ADDRESS").toString(), normalFont, Border.NO_BORDER,
					Border.NO_BORDER, Border.NO_BORDER, Border.NO_BORDER, TextAlignment.CENTER, VerticalAlignment.TOP)
					.setFontSize(10.0f);
			if (Pattern.matches("[a-zA-Z]+", data1.get("ADDRESS").toString().replace(" ", ""))) {
				Style normal = new Style().setBaseDirection(BaseDirection.RIGHT_TO_LEFT)
						.setTextAlignment(TextAlignment.LEFT).setFont(boldFont);
				ADDRESS.addStyle(normal);
			} else {
				Style hebrewStyle = new Style().setBaseDirection(BaseDirection.RIGHT_TO_LEFT)
						.setTextAlignment(TextAlignment.RIGHT).setFont(boldFont);
				ADDRESS.setFontScript(Character.UnicodeScript.HEBREW).addStyle(hebrewStyle);
				// tableBodyCell.setPaddingRight(50);
			}
			row3FirstRight.addCell(ADDRESS);
			row3FirstRight.addCell(createTextCell(data1.get("PHONE").toString(), normalFont, Border.NO_BORDER,
					Border.NO_BORDER, Border.NO_BORDER, Border.NO_BORDER, TextAlignment.CENTER, VerticalAlignment.TOP)
					.setFontSize(10.0f));
			row3FirstRight.addCell(createTextCell(data1.get("EMAIL").toString(), normalFont, Border.NO_BORDER,
					Border.NO_BORDER, Border.NO_BORDER, Border.NO_BORDER, TextAlignment.CENTER, VerticalAlignment.TOP)
					.setFontSize(10.0f));

			c = new Cell();
			c.add(row3FirstRight);
			c.setBorder(Border.NO_BORDER);
			row3.addCell(c);
			Table row3C = new Table(UnitValue.createPercentArray(1)).useAllAvailableWidth().setKeepTogether(true);

			row3C.addCell(row3);

			float descWidth[] = { 100.0f, 0.0f };
			Table descTcont = new Table(UnitValue.createPercentArray(1)).useAllAvailableWidth().setKeepTogether(true);
			float descTW[] = { 50.0f, 25.0f, 25.0f };
			Table descT = new Table(UnitValue.createPercentArray(descTW)).useAllAvailableWidth().setKeepTogether(true);
			descT.addCell(createTextCell("Description of content: ", normalFont, Border.NO_BORDER, Border.NO_BORDER,
					Border.NO_BORDER, new SolidBorder(0), TextAlignment.CENTER, VerticalAlignment.TOP)
					.setFontSize(10.0f).setBold());
			descT.addCell(createTextCell("QTY: ", normalFont, Border.NO_BORDER, Border.NO_BORDER, Border.NO_BORDER,
					new SolidBorder(0), TextAlignment.CENTER, VerticalAlignment.TOP).setFontSize(10.0f).setBold());
			descT.addCell(createTextCell("KG: ", normalFont, Border.NO_BORDER, Border.NO_BORDER, Border.NO_BORDER,
					Border.NO_BORDER, TextAlignment.CENTER, VerticalAlignment.TOP).setFontSize(10.0f).setBold());

			descT.addCell(createTextCell(data1.get("DESC").toString(), normalFont, Border.NO_BORDER, new SolidBorder(0),
					Border.NO_BORDER, new SolidBorder(0), TextAlignment.CENTER, VerticalAlignment.TOP)
					.setFontSize(10.0f));
			descT.addCell(
					createTextCell(data1.get("QUANTITY").toString(), normalFont, Border.NO_BORDER, new SolidBorder(0),
							Border.NO_BORDER, new SolidBorder(0), TextAlignment.CENTER, VerticalAlignment.TOP)
							.setFontSize(10.0f));
			descT.addCell(createTextCell(data1.get("WEIGHT").toString(), normalFont, Border.NO_BORDER,
					new SolidBorder(0), Border.NO_BORDER, Border.NO_BORDER, TextAlignment.CENTER, VerticalAlignment.TOP)
					.setFontSize(10.0f));

			descTcont.addCell(descT);
			descTcont.addCell("");

			Table declCont = new Table(UnitValue.createPercentArray(1)).useAllAvailableWidth().setKeepTogether(true);
			declCont.addCell(createTextCell(
					"I certify that the particulars given in this declaration are correct and this Item does not contain any dangerous articles prohibited by legislation or postal or customer regulations. as per receiver seclare Dated in the system.",
					normalFont, Border.NO_BORDER, Border.NO_BORDER, Border.NO_BORDER, Border.NO_BORDER,
					TextAlignment.CENTER, VerticalAlignment.BOTTOM).setFontSize(8.0f).setItalic());

			doc.add(table);
			doc.add(table1);
			doc.add(table2);
			doc.close();
			InputStream inputStream = new FileInputStream(file);
			out = Utility.readAllBytes(inputStream);
		} catch (IOException ex) {

			ex.printStackTrace();
		} finally {
			if (file != null) {
				if (file.delete()) {
					log.info("File deleted successfully");
				} else {
					log.info("Failed to delete the file");
				}
			}
		}

		return out;
	}

	private static Cell createImageCell(String path, float imagHeight, Border borderBottom, Border borderTop,
			Border borderLeft, Border borderRight) throws MalformedURLException {
		com.itextpdf.layout.element.Image img = new com.itextpdf.layout.element.Image(ImageDataFactory.create(path));
		img.setWidth(UnitValue.createPercentValue(100));
		img.setHeight(imagHeight);
		Cell cell = new Cell().add(img);
		cell.setBorderBottom(borderBottom);
		cell.setBorderTop(borderTop);
		cell.setBorderRight(borderRight);
		cell.setBorderLeft(borderLeft);

		return cell;
	}

	private static Cell createTextCell(String text, PdfFont boldFont, Border borderBottom, Border borderTop,
			Border borderLeft, Border borderRight, TextAlignment alignment, VerticalAlignment verticalAlignment)
			throws IOException {
		Cell cell = new Cell();
		Paragraph p = new Paragraph(text);

		p.setFont(boldFont);
		p.setTextAlignment(alignment);
		p.setPadding(0);
		p.setFontColor(new DeviceRgb(0, 0, 0));
		p.setMargin(0);
		// cell.setPadding(0);
		cell.add(p).setVerticalAlignment(verticalAlignment);
		cell.setTextAlignment(alignment);
		cell.setBorderBottom(borderBottom).setPaddings(0, 0, 0, 0);
		cell.setBorderTop(borderTop);
		cell.setBorderRight(borderRight);
		cell.setBorderLeft(borderLeft);
		cell.setMargin(0);
		return cell;
	}
	private static Cell createTextCellWithSpan(String text, PdfFont boldFont, Border borderBottom, Border borderTop,
			Border borderLeft, Border borderRight, TextAlignment alignment, VerticalAlignment verticalAlignment,int rowSpan,int colSpan)
			throws IOException {
		Cell cell =new Cell(rowSpan, colSpan);
		Paragraph p = new Paragraph(text);

		p.setFont(boldFont);
		p.setTextAlignment(alignment);
		p.setPadding(0);
		p.setFontColor(new DeviceRgb(0, 0, 0));
		p.setMargin(0);
		// cell.setPadding(0);
		cell.add(p).setVerticalAlignment(verticalAlignment);
		cell.setTextAlignment(alignment);
		cell.setBorderBottom(borderBottom).setPaddings(0, 0, 0, 0);
		cell.setBorderTop(borderTop);
		cell.setBorderRight(borderRight);
		cell.setBorderLeft(borderLeft);
		cell.setMargin(0);
		return cell;
	}
	public static Cell createCell(String content, float borderWidth, int colspan, TextAlignment alignment) {
		Cell cell = new Cell(1, colspan).add(new Paragraph(content));
		cell.setTextAlignment(alignment);
		cell.setBorder(new SolidBorder(borderWidth));
		cell.setVerticalAlignment(VerticalAlignment.BOTTOM);
		return cell;
	}
}