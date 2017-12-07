package otrack.util;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRMapArrayDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import otrack.core.AppException;
import otrack.core.DBConnection;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Chunk;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;

/**
 * PDFReportUtil.java
 *
 * @author nehas
 * @Jun 3, 2005
 * @screen Name:
 *
 * Utility object for Reports
 */
public class ReportUtil 
{
    
	public static final String _9_DIGIT_FORMAT = "000000000";
	public static final String _3_DIGIT_FORMAT = "000";
	public static final String _4_DIGIT_FORMAT = "0000";
	public static final String _10_DIGIT_FORMAT = "00000000.00";
	public static final float MARGIN_LEFT = 20;
	public static final float MARGIN_RIGHT = 20;
	public static final float MARGIN_TOP = 0;
	public static final float MARGIN_BOTTOM = 10;	
	public static final int CELL_HEIGHT = 16;
	public static final int MAX_LINE_COUNT_LANDSCAPE = 27;
	public static final int MAX_LINE_COUNT_PORTRAIT = 37;
	public static final int DATA_TABLE_CELL_HEIGHT_LANDSCAPE = 450;
	public static final int DATA_TABLE_CELL_HEIGHT_PORTRAIT = 630;
	public static final String LOGO_NAME = "Idaho.jpg";
    /**
     * Added by smcafee
     * Method used to return a right aligned cell
     * @param string
     * @param fontSize
     * @param isBold
     * @param Border
     * @return
     */
	public static synchronized PdfPCell getFormattedCellRight(String string, int fontSize, boolean isBold, int border, int iPad) 
	{
		PdfPCell ppcWork = new PdfPCell(getFormattedCell(string,fontSize,isBold, border));
		ppcWork.setHorizontalAlignment(2);
		if (iPad != 0)
		{ppcWork.setPaddingRight(iPad);}
		return ppcWork;
	}
   /**
    * Method used to return a center aligned cell
    * Added by smcafee
    * @param string
    * @param fontSize
    * @param isBold
    * @param Border
    * @return
    */
	public static synchronized PdfPCell getFormattedCellCenter(String string, 
			int fontSize, boolean isBold, int border) {
		
		PdfPCell ppcWork = new PdfPCell(getFormattedCell(string,fontSize,isBold, border));
		ppcWork.setHorizontalAlignment(1);
		return ppcWork;
	}
   
    /**
      * Method used to set the font of the string parameter
      * @param string
      * @param fontSize
      * @param isBold
      * @param Border
      * @return
      */
    public static synchronized PdfPCell getFormattedCell (String string,int fontSize, boolean isBold,int border) {
       
    	if (fontSize == 0) 
        {
            fontSize = 10;
        }

        Phrase phrase = null;

        if (string != null) 
        {
            if (isBold) 
            {
                phrase = new Phrase(string,
                        FontFactory.getFont(FontFactory.TIMES_ROMAN, fontSize,
                            Font.BOLD, new Color(0, 0, 0)));
            } 
            else 
            {
                phrase = new Phrase(string,
                        FontFactory.getFont(FontFactory.TIMES_ROMAN, fontSize,
                            Font.NORMAL, new Color(0, 0, 0)));
            }
        } 
        else 
        {
            phrase = new Phrase("",
                    FontFactory.getFont(FontFactory.TIMES_ROMAN, fontSize,
                        Font.NORMAL, new Color(0, 0, 0)));
        }

        //return phrase;
        PdfPCell newCell = new PdfPCell(phrase);
        newCell.setBorder(border);

        return newCell;
    }

    /**
     * Added by smcafee
     * Method used to determine the cell alignment
     * @param String	The alignment string
     * @param String	The cell value string
     * @return PdfPCell	The properly formatted cell
     */
	public static PdfPCell getAlignedCell(String sAlignmentValue, String sCellValue) {
		
		PdfPCell ppcReturn;
		if (sAlignmentValue.startsWith("R"))
		{
	        int iPad = 0;
	        if (sAlignmentValue.length() > 1)	
	        {iPad = Integer.valueOf(sAlignmentValue.substring(1));}
			ppcReturn = ReportUtil.getFormattedCellRight(sCellValue, 10, false, 0, iPad);
		}
		else if (sAlignmentValue == "C")
		{ppcReturn = ReportUtil.getFormattedCellCenter(sCellValue, 10, false, 0);}
		else
		{ppcReturn = ReportUtil.getFormattedCell(sCellValue, 10, false, 0);}
		return ppcReturn;
	}

    /** border int BOTTOM 2
     border int BOX 15
     border int LEFT 4
     border int NO_BORDER 0
     border int RIGHT 8
     border int TOP 1
     border int UNDEFINED -1

     Method used wen the label and value of the field are to be inserted in the same Cell.
     Label is to be of the Normal style while Value has to be in Bold
     */
    public static synchronized PdfPCell getMergedDataPdfPCell(String text,
        String value, int style, int fontSize, int border)
        throws BadElementException {
        if ((text == null) || "null".equalsIgnoreCase(text)) {
            text = " ";
        }

        if ((value == null) || "null".equalsIgnoreCase(value)) {
            value = " ";
        }

        Chunk chunk1 = new Chunk(text,
                FontFactory.getFont(FontFactory.TIMES_ROMAN, fontSize, style,
                    new Color(0, 0, 0)));

        Chunk chunk = new Chunk(value,
                FontFactory.getFont(FontFactory.TIMES_ROMAN, fontSize, 0,
                    new Color(0, 0, 0)));

        Paragraph paragraph = new Paragraph(chunk1);
        paragraph.add(chunk);

        PdfPCell cell = new PdfPCell(paragraph);
        cell.setBorder(border);

        return cell;
    }

    /**
     * This method returns a formatted PdfPCell object with the given text, font size, fontType, border.
     * @author Ravi Reddy
     * @param string
     * @param fontSize
     * @param fontType
     * @param isBold
     * @param border
     * @return
     *
     */
    public static synchronized PdfPCell getFormattedCell(String string,
        int fontSize, String fontType, boolean isBold, int border) {
        if (fontSize == 0) {
            fontSize = 10;
        }

        Phrase phrase = null;

        if (string != null) {
            if (isBold) {
                phrase = new Phrase(string,
                        FontFactory.getFont(fontType, fontSize, Font.BOLD,
                            new Color(0, 0, 0)));
            } else {
                phrase = new Phrase(string,
                        FontFactory.getFont(fontType, fontSize, Font.NORMAL,
                            new Color(0, 0, 0)));
            }
        } else {
            phrase = new Phrase("",
                    FontFactory.getFont(fontType, fontSize, Font.NORMAL,
                        new Color(0, 0, 0)));
        }

        //return phrase;
        PdfPCell newCell = new PdfPCell(phrase);
        newCell.setBorder(border);

        return newCell;
    }

    /**
     * @param value
     * @return
     * @screen: <JSP Name>
     *
     * This method returns the value of double passed in as parameter in '##,###,###.##' format.
     * It returns the full string with lenght size as 13.For example if you pass 123 you will get
     * #######123.00 where '#' means space character.
     */
    public static String getNumberInFormat(double value) {
        DecimalFormat format = new DecimalFormat("00,000,000.00");

        String text = format.format(value);
        boolean isNegative = text.startsWith("-");
        int i = isNegative ? 1 : 0;

        char[] array = text.toCharArray();
        boolean checkDecimal = false;

        for (; i < array.length; i++) {
            if ((array[i] == '0') || (array[i] == ',')) {
                array[i] = ' ';
            } else {
                if (array[i] != '.') {
                    break;
                } else {
                    checkDecimal = true;
                }
            }
        }

        if (checkDecimal) {
            int a = text.indexOf('.') + 1;
            int b = text.indexOf('.') + 2;
            int c = text.indexOf('.');

            if ((array[a] == ' ') && (array[b] == ' ')) {
                array[c] = ' ';
            } else if ((array[a] == ' ') && (array[b] != ' ')) {
                array[a] = '0';
            }
        }

        if (isNegative) {
            if (i == 1) {
                array[0] = '-';
            } else {
                array[0] = ' ';

                if (array[i - 1] == ' ') {
                    array[i - 1] = '-';
                } else if (array[i - 2] == ' ') {
                    array[i - 2] = '-';
                } else if (array[i - 3] == ' ') {
                    array[i - 3] = '-';
                }
            }
        }

        return new String(array);
    }

    /**
     * @author Ravi Reddy
     * @param text
     * @param value
     * @param fontType
     * @param style
     * @param fontSize
     * @param border
     * @return
     * @throws BadElementException
     * @screen Name:
     *
     * <The detailed purpose of this method>
     * This method returns merged cell of both Label and value.
     */
    public static synchronized PdfPCell getMergedDataPdfPCell(String text,
        String value, String fontType, int style, int fontSize, int border)
        throws BadElementException {
        if ((text == null) || "null".equalsIgnoreCase(text)) {
            text = " ";
        }

        if ((value == null) || "null".equalsIgnoreCase(value)) {
            value = " ";
        }

        Chunk chunk1 = new Chunk(text,
                FontFactory.getFont(fontType, fontSize, style,
                    new Color(0, 0, 0)));

        Chunk chunk = new Chunk(value,
                FontFactory.getFont(fontType, fontSize, 0, new Color(0, 0, 0)));

        Paragraph paragraph = new Paragraph(chunk1);
        paragraph.add(chunk);

        PdfPCell cell = new PdfPCell(paragraph);
        cell.setBorder(border);

        return cell;
    }

    /**
         * @param columnNos
         * @param border
         * @param widthsInFloat
         * @param widthsInInt
         * @param widthPercent
         * @param alignment
         * @return
         * @throws DocumentException
         * @screen: <JSP Name>
         *
         * This method returns the Table for a Document.
         */
    public static PdfPTable getTable(int columnNos, int border,
        float[] widthsInFloat, int[] widthsInInt, float widthPercent,
        int alignment) throws DocumentException {
        
    	PdfPTable table = new PdfPTable(columnNos);
        table.getDefaultCell().setBorder(border);

        if (widthsInFloat != null) {
            table.setWidths(widthsInFloat);
        } else if (widthsInInt != null) {
            table.setWidths(widthsInInt);
        }

        table.setWidthPercentage(widthPercent);
        table.setHorizontalAlignment(alignment);

        return table;
    }

    /**
         * @param table
         * @param cells
         * @screen: <JSP Name>
         *
         * This method adds cells(PdfPCell) contained in List to PdfPTable
         */
    public static synchronized void addCellsToTable(PdfPTable table, List cells) {
        if ((table != null) && (cells != null)) {
            for (Iterator iter = cells.iterator(); iter.hasNext();) {
                table.addCell((PdfPCell) iter.next());
            }
        }
    }
    
	/**
	 * @param value
	 * @param digitFormat
	 * @return
	 * 
	 * <The detailed purpose of this method>
	 * This method formats a number to the given number of digits and returns the formatted number as String.
	 * For example. If you pass a number 100 and the format as ReportUtil._4_DIGIT_FORMAT, it will return you
	 * #100 number as String.
	 */
	public static String getNumberInFormat(long value, String digitFormat) {
		DecimalFormat format = new DecimalFormat(digitFormat);

		String text = format.format(value);
	
		char[] array = text.toCharArray();
	
		for (int i = 0; i < array.length; i++) 
		{
			if (array[i] == '0') 
			{
				array[i] = ' ';
			}
			else
			{
				break; 
			}
		}

		return new String(array);
	}

	/**
	 * @param value
	 * @param digitFormat
	 * @return
	 * @author Ashish Arya
	 * @date 5-sep-2005
	 * <The detailed purpose of this method>
	 * This method formats a number to the given number of digits and returns the formatted number as String.
	 * For example. If you pass a number 100 and the format as ReportUtil._10_DIGIT_FORMAT, it will return you
	 * #100 number as String.
	 */
	public static String getNumberInFormat(double value, String digitFormat) {
		DecimalFormat format = new DecimalFormat(digitFormat);

		String text = format.format(value);
	
		char[] array = text.toCharArray();
	
		for (int i = 0; i < array.length; i++) 
		{
			if (i == 0 && array[i] == '-'){
				continue;
			}
			if (array[i] == '0') 
			{
				array[i] = ' ';
			}
			else
			{
				break; 
			}
		}

		return new String(array);
	}

	/**
	 * @param value
	 * @param digitFormat
	 * @return
	 * @author Ashish Arya
	 * @date 5-sep-2005
	 * <The detailed purpose of this method>
	 * This method formats a decimal number according to decimal format object passed in.This method 
	 * removes all the 0(zeros) which are not required in the number for example 0001000 will become
	 * 1000 and 00012345 will become 12345.
	 * 
	 * 
	 */
	public static String getNumberInFormat(double value, DecimalFormat format) {
		if (format == null){
			return String.valueOf(value);	
		}
	
		String text = format.format(value);
		
		char[] array = text.toCharArray();
	
		for (int i = 0; i < array.length; i++) 
		{
			if (i == 0 && array[i] == '-'){
				continue;
			}
			if (array[i] == '0') 
			{
				array[i] = ' ';
			}
			else
			{
				break; 
			}
		}
		String ret = new String(array);
		if(ret != null){
			ret = ret.replaceAll(" ", "");
			if(ret.equalsIgnoreCase(".00"))
				ret = "0.00";
		}
		return ret;
	}


	/**
	 * This method is used to print jasper reports
	 * modified with Artf 1198 - smcafee - 09/30/2008 -- rename variables
	 * 	//artf 1115, artf 1116, artf 1117, artf 1118, artf 1121, artf 1124, artf 1125, artf 1174
	 * Date : 9 May, 2008
	 * @param response
	 * @param reportPath
	 * @param reportParams
	 * @param ResultSet reportData
	 * @return boolean
	 */
	public static boolean writeJasperReport (HttpServletResponse response, String reportPath, 
			Map reportParams, ResultSet reportData) {
		boolean returnValue = false;
		try	{
			//Wrap the data in a Jasper object
		    //Create the PDF document
		    String contentType = "application/pdf";
		    byte[] myPdfBytes = null;
			myPdfBytes = JasperRunManager.runReportToPdf(reportPath, reportParams, new JRResultSetDataSource(reportData));
		    response.setHeader("Content-disposition", "inline; filename=report.pdf");
		    response.setContentType(contentType);
		    response.getOutputStream().write(myPdfBytes);
		    returnValue = true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return returnValue;
	}
	/**
	 * This method is used to print jasper reports
	 * It uses an existing map array as the data source

	 * Date : 7 Oct, 2008
	 * @param response
	 * @param reportPath
	 * @param reportParams
	 * @param JRMapArrayDataSource reportData
	 * @return boolean
	 */
	public static boolean writeJasperReport (HttpServletResponse response, String reportPath, 
			Map reportParams, JRMapArrayDataSource reportData) {
		boolean returnValue = false;
		try	{
			//Wrap the data in a Jasper object
		    //Create the PDF document
		    String contentType = "application/pdf";
		    byte[] myPdfBytes = null;
			myPdfBytes = JasperRunManager.runReportToPdf(reportPath, reportParams, reportData);
		    response.setHeader("Content-disposition", "inline; filename=report.pdf");
		    response.setContentType(contentType);
		    response.getOutputStream().write(myPdfBytes);
		    returnValue = true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return returnValue;
	}
	
	/**
	 * This method is used to print jasper reports
	 * It uses a DBConnection and expects the SPL to be imbedded in the report
	 * @author smcafee, artf 1327, artf 1345
	 * Date : 23 June, 2009
	 * @param response
	 * @param reportPath
	 * @param reportParams
	 * @param DBConnection
	 * @return boolean
	 */
	public static boolean writeJasperReport (HttpServletResponse response, String reportPath, 
			Map reportParams, DBConnection conn) throws AppException{
		boolean returnValue = false;
		try	{
			//Wrap the data in a Jasper object
		    //Create the PDF document
		    String contentType = "application/pdf";
		    byte[] myPdfBytes = null;
			myPdfBytes = JasperRunManager.runReportToPdf(reportPath, reportParams, conn);

		    response.setHeader("Content-disposition", "inline; filename=report.pdf");
		    response.setContentType(contentType);
		    response.getOutputStream().write(myPdfBytes);
		    
		    
		    returnValue = true;
		} catch(IllegalStateException ise) {
		    ise.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
            throw new AppException("error.general.sql.processing");
		}
		return returnValue;
	}
	
	/**
	 * This method is used to print jasper reports without data
	 * It can be used for simple forms

	 * Date : 4 Dec, 2008
	 * @param response
	 * @param reportPath
	 * @param reportParams
	 * @return boolean
	 */
	public static boolean writeJasperReport (HttpServletResponse response, String reportPath, Map reportParams) {
		return writeJasperReport (response, reportPath, reportParams, new JRMapArrayDataSource(new HashMap[1]));
	}
	/**
	 * This method is used to export data to Excel
	 * Created smcafee, 2/9/2010, artf 1367, atch 1829
	 * Date : 9 Feb, 2010
	 * @param response
	 * @param reportPath
	 * @param reportParams
	 * @param ResultSet reportData
	 * @return boolean
	 */
	public static boolean writeExcelReport (HttpServletResponse response, String reportPath,  Map reportParams, ResultSet reportData) {
		boolean returnValue = false;
		try	{
		    byte[] outputBytes = null;
			ByteArrayOutputStream byteArrayOutputStream  = new ByteArrayOutputStream();
			JasperPrint jasperPrint = JasperFillManager.fillReport(reportPath, reportParams, new JRResultSetDataSource(reportData));
			JRXlsExporter exporterXLS = setXlsExporter(byteArrayOutputStream, jasperPrint);
		    exporterXLS.exportReport();
		    outputBytes = byteArrayOutputStream.toByteArray();

			
			response.setHeader("Content-disposition", "inline; filename=report.xls");
		    response.setContentType("application/vnd.ms-excel");
		    response.getOutputStream().write(outputBytes);
		    returnValue = true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return returnValue;
	}
	/**
	 * This method is used to create the Jasper Report Excel Exporter
	 * Created smcafee, 2/9/2010, artf 1367, atch 1829
	 * @param ByteArrayOutputStream baos
	 * @param JasperPrint jp
	 * @return JRXlsExporter
	 */
	private static JRXlsExporter setXlsExporter(ByteArrayOutputStream baos, JasperPrint jp){
		JRXlsExporter exporterXLS = new JRXlsExporter();
		exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jp);
	    exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, baos);
	    exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
	    exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
	    exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
	    exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
	    exporterXLS.setParameter(JRXlsExporterParameter.IS_IGNORE_CELL_BORDER, Boolean.TRUE);
	    exporterXLS.setParameter(JRXlsExporterParameter.IS_COLLAPSE_ROW_SPAN, Boolean.TRUE);				
		return exporterXLS;
	}

 	/**
	 * @author smcafee
	 * @param HttpServletResponse response
	 * @param String errorMsg
	 * @param String fileName
	 * @return boolean
	 * @throws Exception
	 * Added smcafee, 04-09-2010, artf 1327, atch 1865, item 3
	 * This method generates a simple error msg screen.
	 */
	public static boolean writeEmptyReport 
		(HttpServletResponse response, String errorMsg, String fileName) 
		throws AppException{
		
		boolean returnValue = false;
		try	{
		    String contentType = "text/html";

		    response.setHeader("Content-disposition", "inline; filename=" + fileName);
		    response.setContentType(contentType);
		    response.getOutputStream().write(errorMsg.getBytes());
		    returnValue = true;
		} catch(Exception e) {
			e.printStackTrace();
            throw new AppException("error.general.processing");
		}
		return returnValue;
	}
	public static boolean writeEmptyReport (HttpServletResponse response, String errorMsg) 
		throws AppException{
		return writeEmptyReport(response,errorMsg,null);
	}

}
