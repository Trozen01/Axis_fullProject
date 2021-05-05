package com.MainFrameWork.utilities;
  
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;

import com.MainFrameWork.accelerators.ActionEngine;
import com.MainFrameWork.accelerators.TestEngine;
import com.MainFrameWork.support.ConfiguratorSupport;
import com.MainFrameWork.support.HtmlReportSupport;
import com.MainFrameWork.support.ReportStampSupport;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Reporter extends TestEngine {
	public static ConfiguratorSupport configProps = new ConfiguratorSupport(
			"config.properties");
	static String timeStamp = ReportStampSupport.timeStamp().replace(":", "_")
			.replace(".", "_");

	public static void reportCreater() throws Throwable {
		int intReporterType = Integer.parseInt(configProps
				.getProperty("reportsType"));

		switch (intReporterType) {
		case 1:

			break;
		case 2:

			HtmlReportSupport.htmlCreateReport();
			HtmlReportSupport.createDetailedReport();

			break;
		default:

			HtmlReportSupport.htmlCreateReport();
			break;
		}
	}

	public static void SuccessReport(String strStepName, String strStepDes)
			throws Throwable {
		int intReporterType = Integer.parseInt(configProps
				.getProperty("reportsType"));
		TestEngine.test.log(LogStatus.PASS, strStepDes);
		switch (intReporterType) {
		case 1:

			break;
		case 2:
			if (configProps.getProperty("OnSuccessScreenshot")
					.equalsIgnoreCase("True")) {
				ActionEngine.screenShot(TestEngine.filePath()
						+ strStepDes.replace(" ", "_") + "_"
						+ TestEngine.timeStamp + ".jpeg");
			}
			HtmlReportSupport.onSuccess(strStepName, strStepDes);

			break;

		default:
			if (configProps.getProperty("OnSuccessScreenshot")
					.equalsIgnoreCase("True")) {
				ActionEngine.screenShot(TestEngine.filePath()
						+ strStepDes.replace(" ", "_") + "_"
						+ TestEngine.timeStamp + ".jpeg");
			}
			HtmlReportSupport.onSuccess(strStepName, strStepDes);
			break;
		}
	}

	public static void failureReport(String strStepName, String strStepDes)
			throws Throwable {
		String strStepDes1 = strStepDes.replace(":", "_");
		strStepDes1 = strStepDes1.replace("\\", "_");
		strStepDes1 = strStepDes1.replace("/", "_");
		strStepDes1 = strStepDes1.replace("?", "_");
		strStepDes1 = strStepDes1.replace("*", "_");
		strStepDes1 = strStepDes1.replace("<", "_");
		strStepDes1 = strStepDes1.replace(">", "_");
		System.out.println(strStepDes1);
		TestEngine.test.log(LogStatus.FAIL, strStepDes);
		int intReporterType = Integer.parseInt(configProps
				.getProperty("reportsType"));
		switch (intReporterType) {
		case 1:
			flag = true;
			break;
		case 2:
			ActionEngine.screenShot(TestEngine.filePath()+"/"+"Screenshots"+"/"
					+ strStepDes1.replace(" ", "_") + "_"
					+ TestEngine.timeStamp + ".jpeg");
			flag = true;
			HtmlReportSupport.onFailure(strStepName, strStepDes);
			break;

		default:
			flag = true;
			ActionEngine.screenShot(TestEngine.filePath()+"/"+"Screenshots"+"/"
					+ strStepDes.replace(" ", "_") + "_" + TestEngine.timeStamp
					+ ".jpeg");
			System.out.println(TestEngine.filePath()+"/"+"Screenshots"+"/"
					+ strStepDes.replace(" ", "_") + "_" + TestEngine.timeStamp
					+ ".jpeg");
			HtmlReportSupport.onFailure(strStepName, strStepDes);
			break;
		}

	}
	public static void warningReport(String strStepName, String strStepDes)
			throws Throwable {
		int intReporterType = Integer.parseInt(configProps
				.getProperty("reportsType"));
		switch (intReporterType) {
		case 1:
			flag = true;
			break;
		case 2:
			ActionEngine.screenShot(TestEngine.filePath()+"/"+"Screenshots"+"/"
					+ strStepDes.replace(" ", "_") + "_"
					+ TestEngine.timeStamp + ".jpeg");
			flag = true;
			HtmlReportSupport.onWarning(strStepName, strStepDes);
			break;

		default:
			flag = true;
			ActionEngine.screenShot(TestEngine.filePath()+"/"+"Screenshots"+"/"
					+ strStepDes.replace(" ", "_") + "_" + TestEngine.timeStamp
					+ ".jpeg");
			HtmlReportSupport.onWarning(strStepName, strStepDes);
			break;
		}

	}
	
	public static void takescreenshot(String ssname) throws Throwable
	{
		String timeStamp;
		File screenShotName;
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		
		timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime()); 
		
		String projectPath = System.getProperty("user.dir");
	    String PathTofile = "\\Results\\Screenshots\\";
	    String path = projectPath + PathTofile;
		
	   
	    
		
		screenShotName = new File(path+ssname+timeStamp+".png");
		//String filePath = screenShotName.toString();
		
		FileUtils.copyFile(scrFile, screenShotName);
		 
		//Convert web driver object to TakeScreenshot

       
		
		
		
	}
	
	public static void takescreenshotOnFailure() throws Throwable
	{
		String timeStamp;
		File screenShotName;
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		
		timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime()); 
		
		String projectPath = System.getProperty("user.dir");
	    String PathTofile = "\\Results\\Screenshots\\";
	    String path = projectPath + PathTofile;
		
		screenShotName = new File(path+timeStamp+"Failure_"+".png");
		//String filePath = screenShotName.toString();
		
		FileUtils.copyFile(scrFile, screenShotName);
		 
		
	}
	
}
