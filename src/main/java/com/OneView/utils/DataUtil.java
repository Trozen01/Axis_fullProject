package com.OneView.utils; 

import java.util.ArrayList;
import java.util.Hashtable;

import com.MainFrameWork.accelerators.TestEngine;

public class DataUtil extends TestEngine {

	static Object[][] Testdata;
	
	public static Object[][] getData(Xls_Reader xls, String testName) {
		String sheetName = TestsheetName;
		// reads data for only testCaseName

		int testStartRowNum = 1;
		//System.out.println("Test starts from row - " + sheetName + " -- " + testName);
		while (!xls.getCellData(sheetName, 0, testStartRowNum).equals(testName)) {
			testStartRowNum++;
		}
		//System.out.println("Test starts from row - " + testStartRowNum);
		int colStartRowNum = testStartRowNum + 1;
		int dataStartRowNum = testStartRowNum + 2;

		// calculate rows of data
		int rows = 0;
		while (!xls.getCellData(sheetName, 0, dataStartRowNum + rows).equals("")) {
			rows++;
		}
		System.out.println("Total rows are  - " + rows);

		// calculate total cols
		int cols = 0;
		while (!xls.getCellData(sheetName, cols, colStartRowNum).equals("")) {
			cols++;
		}
		System.out.println("Total cols are  - " + cols);
		Object[][] data = new Object[rows][1];
		// read the data
		int dataRow = 0;
		Hashtable<String, String> table = null;
		for (int rNum = dataStartRowNum; rNum < dataStartRowNum + rows; rNum++) {
			table = new Hashtable<String, String>();
			for (int cNum = 0; cNum < cols; cNum++) {
				String key = xls.getCellData(sheetName, cNum, colStartRowNum);
				String value = xls.getCellData(sheetName, cNum, rNum);
				table.put(key, value);
				table.put("colStartRowNum", Integer.toString(colStartRowNum - 1));
				table.put("DataRowNum", Integer.toString(rNum));
				if (rNum == dataStartRowNum) {
					table.put("FirstdataRow", "Yes");

				} else {
					table.put("FirstdataRow", "No");
				}

				if (rows == 1) {
					table.put("LastdataRow", "Yes");

				} else if (rNum == dataStartRowNum + rows - 1) {
					table.put("LastdataRow", "Yes");
				}

				else {
					table.put("LastdataRow", "No");
				}

				// System.out.println("rNum --" + rNum + " dataStartRowNum --" +
				// dataStartRowNum + "dataRow --" + dataRow);
				// 0,0 0,1 0,2
				// 1,0 1,1
			}
			data[dataRow][0] = table;
			dataRow++;
		}
		return data;
	}

	// true - N
	// false - Y

	/*public static boolean isSkip(Xls_Reader xls, String testName) {
		int rows = xls.getRowCount(ProjectConstants.TESTCASES_SHEET);

		for (int rNum = 2; rNum <= rows; rNum++) {
			String tcid = xls.getCellData(ProjectConstants.TESTCASES_SHEET, ProjectConstants.TCID_COL, rNum);
			if (tcid.equals(testName)) {
				String runmode = xls.getCellData(ProjectConstants.TESTCASES_SHEET, ProjectConstants.RUNMODE_COL, rNum);
				if (runmode.equals("Y"))
					return false;
				else
					return true;
			}
		}

		return true;// default

	}
*/
	
	
	public static String GetTestData(String TestSheet,String TestCase, String ColName, String ConditionCol, String ConditionData) {
		// Get Test Data for Other Testcase
		String data = null;
		String OldSheet = TestsheetName;
		TestsheetName = TestSheet;
		Testdata = DataUtil.getData(xls, TestCase);
		Hashtable<String, String> Testdatatable;

		for (int i = 0; i < Testdata.length; i++) {

			Testdatatable = (Hashtable<String, String>) Testdata[i][0];
			String Concoldata = Testdatatable.get(ConditionCol);

			if (Concoldata.equals(ConditionData)) {

				data = Testdatatable.get(ColName);

			}

		}
		TestsheetName = OldSheet;
		return data;

	}
	
	
	public static ArrayList<String> getListData(Xls_Reader xls, String testName, String TestsheetName) {
		String sheetName = TestsheetName;
		// reads data for only testCaseName
		int testStartRowNum = 1;
		int dataStartRowNum = testStartRowNum + 2;

		// calculate rows of data
		int rows = 0;
		while (!xls.getCellData(sheetName, 0, dataStartRowNum + rows).equals("")) {
			rows++;
		}
		System.out.println("Total rows are  - " + rows);

		ArrayList<String> data = new ArrayList<String>();
		for (int rNum = dataStartRowNum; rNum < dataStartRowNum + rows; rNum++) {
			new Hashtable<String, String>();

			data.add(xls.getCellData(sheetName, 1, dataStartRowNum));
		}

		return data;
	}

}
