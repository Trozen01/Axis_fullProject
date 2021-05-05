package com.OneView.utils;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
 
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import com.MainFrameWork.accelerators.TestEngine;
import com.MainFrameWork.utilities.Reporter;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Hashtable;
import java.util.Iterator;


public class XLSX2CSV {


    /**
     * The type of the data value is indicated by an attribute on the cell.
     * The value is usually in a "v" element within the cell.
     */
    enum xssfDataType {
        BOOL,
        ERROR,
        FORMULA,
        INLINESTR,
        SSTINDEX,
        NUMBER,
    }
int countrows =0;
String op = null;
String lead1=null;
String lead2=null;


 
    /**
     * Derived from http://poi.apache.org/spreadsheet/how-to.html#xssf_sax_api
     * <p/>
     * Also see Standard ECMA-376, 1st edition, part 4, pages 1928ff, at
     * http://www.ecma-international.org/publications/standards/Ecma-376.htm
     * <p/>
     * A web-friendly version is http://openiso.org/Ecma/376/Part4
     */
    class MyXSSFSheetHandler extends DefaultHandler {
 
        /**
         * Table with styles
         */
        private StylesTable stylesTable;
 
        /**
         * Table with unique strings
         */
        private ReadOnlySharedStringsTable sharedStringsTable;
 
        /**
         * Destination for data
         */
        public final PrintStream output;
 
        /**
         * Number of columns to read starting with leftmost
         */
        private final int minColumnCount;
 
        // Set when V start element is seen
        private boolean vIsOpen;
 
        // Set when cell start element is seen;
        // used when cell close element is seen.
        private xssfDataType nextDataType;
 
        // Used to format numeric cell values.
        private short formatIndex;
        private String formatString;
        private final DataFormatter formatter;
 
        private int thisColumn = -1;
        // The last column printed to the output stream
        private int lastColumnNumber = -1;
 
        // Gathers characters as they are seen.
        private StringBuffer value;
        
        public String op=null;
 
        /**
         * Accepts objects needed while parsing.
         *
         * @param styles  Table of styles
         * @param strings Table of shared strings
         * @param cols    Minimum number of columns to show
         * @param target  Sink for output
         */
        public MyXSSFSheetHandler(
                StylesTable styles,
                ReadOnlySharedStringsTable strings,
                int cols,
                PrintStream target) {
            this.stylesTable = styles;
            this.sharedStringsTable = strings;
            this.minColumnCount = cols;
            this.output = target;
            this.value = new StringBuffer();
            this.nextDataType = xssfDataType.NUMBER;
            this.formatter = new DataFormatter();
        }
 
        /*
           * (non-Javadoc)
           * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
           */
        public void startElement(String uri, String localName, String name,
                                 Attributes attributes) throws SAXException {
 
            if ("inlineStr".equals(name) || "v".equals(name)) {
                vIsOpen = true;
                // Clear contents cache
                value.setLength(0);
            }
            // c => cell
            else if ("c".equals(name)) {
                // Get the cell reference
                String r = attributes.getValue("r");
                int firstDigit = -1;
                for (int c = 0; c < r.length(); ++c) {
                    if (Character.isDigit(r.charAt(c))) {
                        firstDigit = c;
                        break;
                    }
                }
                thisColumn = nameToColumn(r.substring(0, firstDigit));
 
                // Set up defaults.
                this.nextDataType = xssfDataType.NUMBER;
                this.formatIndex = -1;
                this.formatString = null;
                String cellType = attributes.getValue("t");
                String cellStyleStr = attributes.getValue("s");
                if ("b".equals(cellType))
                    nextDataType = xssfDataType.BOOL;
                else if ("e".equals(cellType))
                    nextDataType = xssfDataType.ERROR;
                else if ("inlineStr".equals(cellType))
                    nextDataType = xssfDataType.INLINESTR;
                else if ("s".equals(cellType))
                    nextDataType = xssfDataType.SSTINDEX;
                else if ("str".equals(cellType))
                    nextDataType = xssfDataType.FORMULA;
                else if (cellStyleStr != null) {
                    // It's a number, but almost certainly one
                    //  with a special style or format 
                    int styleIndex = Integer.parseInt(cellStyleStr);
                    XSSFCellStyle style = stylesTable.getStyleAt(styleIndex);
                    this.formatIndex = style.getDataFormat();
                    this.formatString = style.getDataFormatString();
                    if (this.formatString == null)
                        this.formatString = BuiltinFormats.getBuiltinFormat(this.formatIndex);
                }
            }
 
        }
 
        /*
           * (non-Javadoc)
           * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
           */
        public void endElement(String uri, String localName, String name)
                throws SAXException {
 
            String thisStr = null;
 
            // v => contents of a cell
            if ("v".equals(name)) {
                // Process the value contents as required.
                // Do now, as characters() may be called more than once
                switch (nextDataType) {
 
                    case BOOL:
                        char first = value.charAt(0);
                        thisStr = first == '0' ? "FALSE" : "TRUE";
                        break;
 
                    case ERROR:
                        thisStr = "\"ERROR:" + value.toString() + '"';
                        break;
 
                    case FORMULA:
                        // A formula could result in a string value,
                        // so always add double-quote characters.
                        thisStr = '"' + value.toString() + '"';
                        break;
 
                    case INLINESTR:
                        // TODO: have seen an example of this, so it's untested.
                        XSSFRichTextString rtsi = new XSSFRichTextString(value.toString());
                        thisStr = '"' + rtsi.toString() + '"';
                        break;
 
                    case SSTINDEX:
                        String sstIndex = value.toString();
                        try {
                            int idx = Integer.parseInt(sstIndex);
                            XSSFRichTextString rtss = new XSSFRichTextString(sharedStringsTable.getEntryAt(idx));
                            thisStr = '"' + rtss.toString() + '"';
                        }
                        catch (NumberFormatException ex) {
                            output.println("Failed to parse SST index '" + sstIndex + "': " + ex.toString());
                        }
                        break;
 
                    case NUMBER:
                        String n = value.toString();
                        if (this.formatString != null)
                            thisStr = formatter.formatRawCellContents(Double.parseDouble(n), this.formatIndex, this.formatString);
                        else
                            thisStr = n;
                        break;
 
                    default:
                        thisStr = "(TODO: Unexpected type: " + nextDataType + ")";
                        break;
                }
 
                // Output after we've seen the string contents
                // Emit commas for any fields that were missing on this row
                if (lastColumnNumber == -1) {
                    lastColumnNumber = 0;
                }
                for (int i = lastColumnNumber; i < thisColumn; ++i)
                    output.print(',');
 
                // Might be the empty string.
                output.print(thisStr);
 
                // Update column
                if (thisColumn > -1)
                    lastColumnNumber = thisColumn;
 
            } else if ("row".equals(name)) {
 
                // Print out any missing commas if needed
                if (minColumns > 0) {
                    // Columns are 0 based
                    if (lastColumnNumber == -1) {
                        lastColumnNumber = 0;
                    }
                    for (int i = lastColumnNumber; i < (this.minColumnCount); i++) {
                        output.print(',');
                    }
                }
 
                // We're onto a new row
                 
                output.println();
                output.println(countrows++);
                lastColumnNumber = -1;
                 
            }
 
        }
 
        /**
         * Captures characters only if a suitable element is open.
         * Originally was just "v"; extended for inlineStr also.
         */
        public void characters(char[] ch, int start, int length)
                throws SAXException {
            if (vIsOpen)
                value.append(ch, start, length);
        }
 
        /**
         * Converts an Excel column name like "C" to a zero-based index.
         *
         * @param name
         * @return Index corresponding to the specified name
         */
        private int nameToColumn(String name) {
            int column = -1;
            for (int i = 0; i < name.length(); ++i) {
                int c = name.charAt(i);
                column = (column + 1) * 26 + c - 'A';
            }
            return column;
        }
 
    }
 
    ///////////////////////////////////////
 
    private OPCPackage xlsxPackage;
    private int minColumns;
    public PrintStream output;
 
    /**
     * Creates a new XLSX -> CSV converter
     *
     * @param pkg        The XLSX package to process
     * @param output     The PrintStream to output the CSV to
     * @param minColumns The minimum number of columns to output, or -1 for no minimum
     */
    public XLSX2CSV(OPCPackage pkg, PrintStream output, int minColumns) {
        this.xlsxPackage = pkg;
        this.output = output;
        this.minColumns = minColumns;
    }
 
    /**
     * Parses and shows the content of one sheet
     * using the specified styles and shared-strings tables.
     *
     * @param styles
     * @param strings
     * @param sheetInputStream
     */
    public void processSheet(
            StylesTable styles,
            ReadOnlySharedStringsTable strings,
            InputStream sheetInputStream)
            throws IOException, ParserConfigurationException, SAXException {
 
        InputSource sheetSource = new InputSource(sheetInputStream);
        SAXParserFactory saxFactory = SAXParserFactory.newInstance();
        SAXParser saxParser = saxFactory.newSAXParser();
        XMLReader sheetParser = saxParser.getXMLReader();
        ContentHandler handler = new MyXSSFSheetHandler(styles, strings, this.minColumns, this.output);
        sheetParser.setContentHandler(handler);
        sheetParser.parse(sheetSource);
    }
 
    /**
     * Initiates the processing of the XLS workbook file to CSV.
     * @return 
     *
     * @throws IOException
     * @throws OpenXML4JException
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    public String process()
            throws IOException, OpenXML4JException, ParserConfigurationException, SAXException {
 
        ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(this.xlsxPackage);
        XSSFReader xssfReader = new XSSFReader(this.xlsxPackage);
     
        StylesTable styles = xssfReader.getStylesTable();
        XSSFReader.SheetIterator iter = (XSSFReader.SheetIterator) xssfReader.getSheetsData();
        int index = 0;
        while (iter.hasNext()) {
            InputStream stream = iter.next();
            String sheetName = iter.getSheetName(); 
            this.output.println();
            
            this.output.println(sheetName + " [index=" + index + "]:");
           // processSheet(styles, strings, stream);
            
            
            // rohan strings
            System.out.println(strings.toString());
            
            String text= null;
           
            
            
           
            java.util.List<java.lang.String> abc=strings.getItems();
            
            int max=strings.getUniqueCount();
            System.out.println("max count in data is :"+max);
            
         //    lead1=strings.getItemAt(338).getString();
        //     lead2=strings.getItemAt(345).getString();
             
             lead1=strings.getEntryAt(338);
             lead2=strings.getEntryAt(345);
            
            
            try {
            	
				for (int i = 300; i < max; i++) {
					
					
					
					try {
						if((strings.getEntryAt(i).length()>0)) {
							text= text + strings.getEntryAt(i);
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				
				e.printStackTrace();
			}
            
            System.out.println("data in excel is :"+text);
            System.out.println("lead1 in excel is :"+lead1);
            System.out.println("lead2 in excel is :"+lead2);
            
            stream.close();
            ++index;
        }
        return lead1;
    }
 
    
    public String processTicket()
            throws IOException, OpenXML4JException, ParserConfigurationException, SAXException {
    	
    	ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(this.xlsxPackage);
        XSSFReader xssfReader = new XSSFReader(this.xlsxPackage);
     
        StylesTable styles = xssfReader.getStylesTable();
        XSSFReader.SheetIterator iter = (XSSFReader.SheetIterator) xssfReader.getSheetsData();
        int index = 0;
        while (iter.hasNext()) {
            InputStream stream = iter.next();
            String sheetName = iter.getSheetName(); 
            this.output.println();
            
            this.output.println(sheetName + " [index=" + index + "]:");
           // processSheet(styles, strings, stream);
            
            
            // rohan strings
            System.out.println(strings.toString());
            
            String text= null;
           
            
            
           
            java.util.List<java.lang.String> abc=strings.getItems();
            
            int max=strings.getUniqueCount();
            System.out.println("max count in data is :"+max);
            
         //    lead1=strings.getItemAt(338).getString();
        //     lead2=strings.getItemAt(345).getString();
             
             lead1=strings.getEntryAt(85);
             lead2=strings.getEntryAt(87);
            
            
            try {
            	
				for (int i = 300; i < max; i++) {
					
					
					
					try {
						if((strings.getEntryAt(i).length()>0)) {
							text= text + strings.getEntryAt(i);
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				
				e.printStackTrace();
			}
            
            System.out.println("data in excel is :"+text);
            System.out.println("lead1 in excel is :"+lead1);
            System.out.println("lead2 in excel is :"+lead2);
            
            stream.close();
            ++index;
        }
        return lead1;
    }
    
    public String processTicketAddStatus()
            throws IOException, OpenXML4JException, ParserConfigurationException, SAXException {
 
        ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(this.xlsxPackage);
        XSSFReader xssfReader = new XSSFReader(this.xlsxPackage);
     
        StylesTable styles = xssfReader.getStylesTable();
        XSSFReader.SheetIterator iter = (XSSFReader.SheetIterator) xssfReader.getSheetsData();
        int index = 0;
        while (iter.hasNext()) {
            InputStream stream = iter.next();
            String sheetName = iter.getSheetName(); 
            this.output.println();
            
            this.output.println(sheetName + " [index=" + index + "]:");
           // processSheet(styles, strings, stream);
            
            
            // rohan strings
            System.out.println(strings.toString());
            
            String text= null;
           
            
            
           
            java.util.List<java.lang.String> abc=strings.getItems();
            
            int max=strings.getUniqueCount();
            System.out.println("max count in data is :"+max);
            
         //    lead1=strings.getItemAt(338).getString();
        //     lead2=strings.getItemAt(345).getString();
             
          //   lead1=strings.getEntryAt(75);
            // lead2=strings.getEntryAt(87);
             //lead1=strings.getEntryAt(46);
             lead1=strings.getEntryAt(47);
            
            try {
            	
				for (int i = 300; i < max; i++) {
					
					
					
					try {
						if((strings.getEntryAt(i).length()>0)) {
							text= text + strings.getEntryAt(i);
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				
				e.printStackTrace();
			}
            
            System.out.println("data in excel is :"+text);
            System.out.println("lead1 in excel is :"+lead1);
            System.out.println("lead2 in excel is :"+lead2);
            
            stream.close();
            ++index;
        }
        return lead1;
    }
    
    // read excel file
    
    public static void readExcel(String fileName,String sheetName, String ticketno, Hashtable<String, String> data) throws Throwable{

        //Create an object of File class to open xlsx file

      //  File file =    new File(filePath);  
        
        String downloadfile = TestEngine.configProps.getProperty("CommonFileLoaction_Download");
        
        String newpath=downloadfile+"\\CheckerActions.xlsx";
        
        File file =    new File(newpath);  
        
        //File opfile =  new File("C:\\Users\\rohan.baraskar\\Downloads\\CheckerActionsOP.xlsx");
        String opfilenew= downloadfile+"\\CheckerActionsOP.xlsx";
       // File opfile =  new File("C:\\Users\\rohan.baraskar\\Downloads\\CheckerActionsOP.xlsx");
        File opfile =  new File(opfilenew);
        
       data.put("newMSFupload",opfilenew);
        //Create an object of FileInputStream class to read excel file

        FileInputStream inputStream = new FileInputStream(file);

        Workbook guru99Workbook = null;
        int rownum = 0;
        //Find the file extension by splitting file name in substring  and getting only extension name

        String fileExtensionName = fileName.substring(fileName.indexOf("."));

        //Check condition if the file is xlsx file

        if(fileExtensionName.equals(".xlsx")){

        //If it is xlsx file then create object of XSSFWorkbook class

        guru99Workbook = new XSSFWorkbook(inputStream);

        }

        //Check condition if the file is xls file

        else if(fileExtensionName.equals(".xls")){

            //If it is xls file then create object of XSSFWorkbook class

            guru99Workbook = new HSSFWorkbook(inputStream);

        }

        //Read sheet inside the workbook by its name

        Sheet guru99Sheet = guru99Workbook.getSheet(sheetName);

        //Find number of rows in excel file

        int rowCount = guru99Sheet.getLastRowNum()-guru99Sheet.getFirstRowNum();
        
        //Create a loop over all the rows of excel file to read it

        myloop:        for (int i = 0; i < rowCount+1; i++) {

            Row row = guru99Sheet.getRow(i);

            //Create a loop to print cell values in a row

            	for (int j = 0; j < row.getLastCellNum(); j++) {

                //Print Excel data in console
            	//rownum= row.getRowNum();
                try {
                	//rownum= row.getRowNum();
					System.out.print(row.getCell(j).getStringCellValue()+"|| ");
					if(row.getCell(j).getStringCellValue().contains(ticketno)) {
						rownum= row.getRowNum();
					//	data.put("rownumber", Integer.toString(rownum));
						
					       // Get the column of your desired cell in your selected row.
					       // Let's say that your desired cell is at column 2.
					      // Cell column = row.getCell(1);
						Cell cell = row.createCell(1);
					       //Cell column = row.getCell(1, Row.RETURN_BLANK_AS_NULL);
					       // If the cell is String type.If double or else you can change it.
					     
					       //New content for desired cell.
					      String updatename="Problem Solved";
					       //Print out the updated content.
					       System.out.println(updatename);
					       //Set the new content to your desired cell(column).
					       cell.setCellValue(updatename); 
					       //Close the excel file.
					       inputStream.close();
					       //Where you want to save the updated sheet.
					       FileOutputStream out = 
					           new FileOutputStream(opfile);
					       guru99Workbook.write(out);
					       out.close();
						
						//
						//cell = worksheet.getRow(2).getCell(2);
						break myloop;
						
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Reporter.failureReport("Checker Upload failed.", "Checker not able to change status to Problem solved.");
				}

            }
            	
            	
                
                
            	

            System.out.println();
            System.out.println("row number is :"+rownum);
        } 
        System.out.println("finalm row number is :"+rownum);
        String tempnum=Integer.toString(rownum);
        data.put("rownum", tempnum);
     // add logic to shift the row and delete all other rows
        
     
    	try {
			// add logic to shift the row and delete all other rows
			FileInputStream file1 = new FileInputStream(opfile);
			XSSFWorkbook workbook = new XSSFWorkbook(file1);
			XSSFSheet sheet = workbook.getSheetAt(0);
			String row1 =data.get("rownum");
			int newrow = Integer.parseInt(row1); 
			 int lastRowNum=sheet.getLastRowNum();
			
			for(int i=newrow+1;i<(lastRowNum-1);i++) {
				
				removeRow(sheet, i);
				
			}
			
//			
			//sheet.shiftRows(366, 2, 1);
			file1.close();
			FileOutputStream out = new FileOutputStream(opfile);
			workbook.write(out);
			out.close();
			} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("shifting rows failed.");
			
//			
		}
    	
    	// new delete starting rows
    	try {
			// add logic to shift the row and delete all other rows
			FileInputStream file1 = new FileInputStream(opfile);
			XSSFWorkbook workbook = new XSSFWorkbook(file1);
			XSSFSheet sheet = workbook.getSheetAt(0);
			String row1 =data.get("rownum");
			int newrow = Integer.parseInt(row1); 
			 int lastRowNum=sheet.getLastRowNum();
			
			for(int i=newrow+1;i<(lastRowNum-1);i++) {
				
				removeRow(sheet, i);
				
			}
			
//			
			//sheet.shiftRows(366, 2, 1);
			file1.close();
			FileOutputStream out = new FileOutputStream(opfile);
			workbook.write(out);
			out.close();
			} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("shifting rows failed.");
			
//			
		}
    	
    	
        }  
    
    
    /**
     * Remove a row by its index
     * @param sheet a Excel sheet
     * @param rowIndex a 0 based index of removing row
     */
    public static void removeRow(XSSFSheet sheet, int rowIndex) {
        int lastRowNum=sheet.getLastRowNum();
        if(rowIndex>=0&&rowIndex<lastRowNum){
            sheet.shiftRows(rowIndex+1,lastRowNum, -1);
        }
        if(rowIndex==lastRowNum){
            XSSFRow removingRow=sheet.getRow(rowIndex);
            if(removingRow!=null){
                sheet.removeRow(removingRow);
            }
        }
    }
    
    
    
    
    public static void main(String[] args) throws Exception {
       /* if (args.length < 1) {
            System.err.println("Use:");
            System.err.println("  XLSX2CSV <xlsx file> [min columns]");
            return;
        }*/
 
    	
    	String filePath = "C:\\Users\\rohan.baraskar\\Downloads\\CheckerActions.xlsx";

        //Call read file method of the class to read data

    //    readExcel("CheckerActions.xlsx","Worksheet","TT10589",data);
    	
    	
        //File xlsxFile = new File(args[0]);
        File xlsxFile = new File("C:\\Users\\rohan.baraskar\\Downloads\\CheckerActions.xlsx");
        if (!xlsxFile.exists()) {
            System.err.println("Not found or not a file: " + xlsxFile.getPath());
            return;
        }
 
        int minColumns = -1;
        //if (args.length >= 2)
            //minColumns = Integer.parseInt(args[1]);
 
         
        //File originFile = new File("c:\\excel\\file1.txt");
      //  File destinationFile = new File("C:\\Users\\rohan.baraskar\\Downloads\\file1.txt");
        File destinationFile = new File("C:\\Users\\rohan.baraskar\\Downloads\\file1.csv");
    
        try {
    
          //FileInputStream fis = new FileInputStream(originFile);
          FileOutputStream fos = new FileOutputStream(destinationFile);
 
          fos.close();
        } catch (IOException e) {
          System.out.println(e);
        }
         
 
            minColumns = 2;
        // The package open is instantaneous, as it should be.
        OPCPackage p = OPCPackage.open(xlsxFile.getPath(), PackageAccess.READ);
        
        XLSX2CSV xlsx2csv = new XLSX2CSV(p, System.out, minColumns);
        System.out.println("********************output of the file is ***************************");
        String abc= xlsx2csv.processTicketAddStatus();
        System.out.println("********************2nd of the file is ***************************");
      //  String output=  xlsx2csv.process();
       
        //System.out.println(output);
    }
}
