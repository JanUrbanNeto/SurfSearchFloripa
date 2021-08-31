package controller;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import model.Search;
import model.User;

public class PdfFileController {
	
	private static PdfFileController pdfFileController;
	private Document report;
	
	private final String windConditions = "\n0 - No Waves\n" + 
			 							  "1 - North East Waves\n" + 
			 							  "2 - South Waves\n" + 
			 							  "3 - South East Waves\n" + 
			 							  "4 - East Waves\n" + 
			 							  "5 - All Waves\n";
	
	private final String waveConditions = "\n0 - No Wind\n" + 
			 							  "1 - North Wind\n" +
			 							  "2 - North East Wind\n" +
			 							  "3 - North West Wind\n" +
			 							  "4 - South Wind\n" +
			 							  "5 - South East Wind\n" +
			 							  "6 - South West Wind\n" +
			 							  "7 - West Wind\n" +
			 							  "8 - East Wind\n" +
			 							  "9 - All Winds\n\n";
	
	public PdfFileController() {
		
	}
	
	public static PdfFileController getInstance() {
		if(pdfFileController == null) {
			pdfFileController = new PdfFileController();
		}
		return pdfFileController;
	}
	
	public int createPdfReport(String fileName, ArrayList<Search> list, User user) {
		
		int i = 0;	
		Paragraph p;
		
		try {
			String path = fileName + ".pdf";
			report = new Document(PageSize.A4, 20, 20, 30, 20);
			PdfWriter.getInstance(report, new FileOutputStream(path));
			report.open();
			
			Image img = Image.getInstance("src/images/icon.png");
			report.add(img);
			
			p = new Paragraph("SURF SEARCH FLORIPA - " + fileName + "\n\n");
			p.setAlignment(1);
			report.add(p);
			
			p = new Paragraph("Username: " + user.getName());
			p.setAlignment(0);
			report.add(p);
			
			p = new Paragraph("E-mail: " + user.getEmail() + "\n\n");
			p.setAlignment(0);
			report.add(p);
			
			PdfPTable tableLine = new PdfPTable(1);
			p = new Paragraph("Conditions");
			PdfPCell cel0 = new PdfPCell(p);
			cel0.setHorizontalAlignment(1);
			tableLine.addCell(cel0);
			report.add(tableLine);
			
			PdfPTable table = new PdfPTable(2);
			table.setHorizontalAlignment(1);
			PdfPCell cel11 = new PdfPCell(new Paragraph("Wind Direction"));
			PdfPCell cel12 = new PdfPCell(new Paragraph("Wave Direction"));
			PdfPCell cel21 = new PdfPCell(new Paragraph(waveConditions));
			PdfPCell cel22 = new PdfPCell(new Paragraph(windConditions));
			table.addCell(cel11);
			table.addCell(cel12);
			table.addCell(cel21);
			table.addCell(cel22);
			report.add(table);
			
			p = new Paragraph("\n\n");
			report.add(p);
			
			tableLine = new PdfPTable(1);
			p = new Paragraph("Searches");
			cel0 = new PdfPCell(p);
			cel0.setHorizontalAlignment(1);
			tableLine.addCell(cel0);
			report.add(tableLine);
			
			table = new PdfPTable(4);
			table.setHorizontalAlignment(1);
			cel11 = new PdfPCell(new Paragraph("ID"));
			cel12 = new PdfPCell(new Paragraph("Wind Direction"));
			PdfPCell cel13 = new PdfPCell(new Paragraph("Wave Direction"));
			PdfPCell cel14 = new PdfPCell(new Paragraph("Date - Time"));
			table.addCell(cel11);
			table.addCell(cel12);
			table.addCell(cel13);
			table.addCell(cel14);
			
			if(!list.isEmpty()) {
				for(Search s : list) {
					cel11 = new PdfPCell(new Paragraph(String.valueOf(s.getId())));
					cel12 = new PdfPCell(new Paragraph(String.valueOf(s.getWindDirection())));
					cel13 = new PdfPCell(new Paragraph(String.valueOf(s.getWaveDirection())));
					cel14 = new PdfPCell(new Paragraph(s.getTimestamp().toString()));
					
					table.addCell(cel11);
					table.addCell(cel12);
					table.addCell(cel13);
					table.addCell(cel14);

				}
			} else {
				cel11 = new PdfPCell(new Paragraph("No Search Result"));
				cel12 = new PdfPCell(new Paragraph(""));
				cel13 = new PdfPCell(new Paragraph(""));
				cel14 = new PdfPCell(new Paragraph(""));
				
				table.addCell(cel11);
				table.addCell(cel12);
				table.addCell(cel13);
				table.addCell(cel14);
			}
			
			report.add(table);
			
			
			p = new Paragraph("\n\n\nEND OF REPORT");
			p.setAlignment(1);
			report.add(p);
			
			report.close();
			
			
			i = 1;
			
			Desktop.getDesktop().open(new File(path));
			
		} catch (Exception e) {
			i = 0;
			e.printStackTrace();
		}
		
		
		return i;
	}

}
