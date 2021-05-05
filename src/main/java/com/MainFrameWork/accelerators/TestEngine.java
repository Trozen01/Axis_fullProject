//original

package com.MainFrameWork.accelerators;       

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Function;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.asserts.SoftAssert;

import com.MainFrameWork.support.ActionEngineSupport;
import com.MainFrameWork.support.ConfiguratorSupport;
import com.MainFrameWork.support.HtmlReportSupport;
import com.MainFrameWork.support.ReportStampSupport;
import com.MainFrameWork.utilities.Reporter;
import com.OneView.utils.DataUtil;
import com.OneView.utils.Xls_Reader;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class TestEngine extends HtmlReportSupport {
	public static Logger logger = Logger.getLogger(TestEngine.class.getName());
	public static ConfiguratorSupport configProps = new ConfiguratorSupport(
			"config.properties");
	public static ConfiguratorSupport counterProp = new ConfiguratorSupport(
			configProps.getProperty("counterPath"));
	public static String currentSuite = "";
	public static String method = "";
	public static String timeStamp = ReportStampSupport.timeStamp()
			.replace(" ", "_").replace(":", "_").replace(".", "_");
	public static boolean flag = false;
	public static WebDriver webDriver = null;
	public static EventFiringWebDriver driver=null;
	public static int stepNum = 0;
	public static int PassNum =0;
	public static int FailNum =0;
	public static int passCounter =0;
	public static int failCounter =0;
	public static String testName = "";
	public static String TestsheetName = null;
	public static String testCaseExecutionTime = "";
	public static StringBuffer x=new StringBuffer();
	public static String finalTime = "";
	public static boolean isSuiteRunning=false;
	public static Map<String, String> testDescription = new LinkedHashMap<String, String>();
	public static Map<String, String> testResults = new LinkedHashMap<String, String>();
	public static String url=null;
	//static ExcelReader xlsrdr = new ExcelReader(configProps.getProperty("TestData"),configProps.getProperty("sheetName0"));
	public static Xls_Reader xls = new Xls_Reader(configProps.getProperty("TestData"));
	
	public static SoftAssert soft;
	
	/*
	 * public static Screen s; public static String url =
	 * "jdbc:mysql://172.16.6.121/"; public static String dbName = "test";
	 * public static String userName = "root"; public static Connection conn =
	 * null; public static Statement stmt = null; public static
	 * PreparedStatement pstmt = null; public static ResultSet rs = null;
	 */
	public static int countcompare = 0;
	public static String browser = null;
	static int len = 0;
	static int i = 0;
	public static ITestContext itc;
	public static String groupNames = null;
	public static String ScreenshotLocation = null;
	
	public static ExtentTest test;
	public static ExtentReports report;

	/**
	 * Initializing browser requirements, Test Results file path and Database
	 * requirements from the configuration file
	 * 
	 * @Date 19/02/2013
	 * @Revision History
	 * 
	 */
	@BeforeSuite(alwaysRun = true)
	public static void setupSuite(ITestContext ctx) throws Throwable {
		try {
			itc = ctx;
			groupNames = ctx.getCurrentXmlTest().getIncludedGroups().toString();
			
			//report = new ExtentReports(System.getProperty("user.dir")+"\\ExtentReportResults.html");
			report = new ExtentReports (System.getProperty("user.dir")+"\\target\\surefire-reports\\Extentreport.html");
			
			report.loadConfig(new File(System.getProperty("user.dir")+"\\extent-config.xml"));
			
			
			String strBrowserType[];

			ReportStampSupport.calculateSuiteStartTime();
			try{
					logger.info("Starting browser : "
							+ configProps.getProperty("browserType"));

					browser = configProps.getProperty("browserType");
			
			} catch (Exception e1) {
				e1.printStackTrace();
				System.out.println(e1);
			}
			
			// browser = configProps.getProperty("browserType");
			if (browser.toString().contains(",")) {
				strBrowserType = browser.split("\\,");

			} else {
				strBrowserType = new String[] { browser };
			}
			if (browser.toString().contains(",")) {
				strBrowserType = browser.split("\\,");
			} else {
				strBrowserType = new String[] { browser };
			}
			len = strBrowserType.length;
			while (i < len) {

				if (strBrowserType[i].toString().equalsIgnoreCase("firefox")) {
					 //WebDriver driver = new FirefoxDriver();
				//	ProfilesIni profile = new ProfilesIni();
				//	FirefoxProfile ffprofile = profile.getProfile("Quickflix");
					//ffprofile.setEnableNativeEvents(true);

					webDriver = new FirefoxDriver();
					i = i + 1;
					break;

				} else if (strBrowserType[i].toString().equalsIgnoreCase("ie")) {
					File file = new File("Drivers\\IEDriverServer.exe");
					System.setProperty("webdriver.ie.driver",
							file.getAbsolutePath());

					webDriver = new InternetExplorerDriver();
					i = i + 1;
					if (configProps.getProperty("browserType").equals("ie")) {
						Process p = Runtime
								.getRuntime()
								.exec("RunDll32.exe InetCpl.cpl,ClearMyTracksByProcess 255");
						p.waitFor();
						Thread.sleep(1000);
					}
					break;

				} else if (strBrowserType[i].toString().equalsIgnoreCase("chrome")) {
					System.setProperty("webdriver.chrome.driver",
							"Drivers\\chromedriver.exe");
					DesiredCapabilities capabilities = new DesiredCapabilities();
					ChromeOptions options = new ChromeOptions();
					options.addArguments("test-type");
			    	 capabilities.setCapability(ChromeOptions.CAPABILITY, options);
			    	 // 28 feb 2020 rohan cert issues
			    	 options.addArguments("ignore-certificate-errors");
			    	 
			    	 // added by rohan  16/7
			    	 capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
					
			    	 // security certificate issue handling
			    	    
			    	 capabilities.setCapability (CapabilityType.ACCEPT_SSL_CERTS, true);
			    		
			    			// webDriver = new ChromeDriver(); 
					webDriver = new ChromeDriver(capabilities);
					
					i = i + 1;
					break;

				}

			}
			driver = new EventFiringWebDriver(webDriver);

			ActionEngineSupport myListener = new ActionEngineSupport();
			driver.register(myListener);
			//nz_region url
					try {
					
							url = (configProps.getProperty("URL"));
					

			} catch (Exception e1) {
				e1.printStackTrace();
				System.out.println(e1);
			}
			driver.manage().window().maximize();
			driver.get(url);
			
			//driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			
			Reporter.reportCreater();
			HtmlReportSupport.currentSuit = ctx.getCurrentXmlTest().getSuite()
					.getName();
			
			//TestsheetName = ctx.getName().replace(" ", "");
			TestsheetName = configProps.getProperty("TestDataSheet") ;
			
			logger.info("URL launched: "+driver.getCurrentUrl());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("@BeforeSuite failed.");
		}
		
		
	}

	

	/**
	 * De-Initializing and closing all the connections
	 * 
	 * @throws Throwable
	 */
	

	@AfterSuite(alwaysRun = true)
	public void tearDown(ITestContext ctx) throws Throwable {
		try {
			System.out.println("Entered after suite");
			ReportStampSupport.calculateSuiteExecutionTime();
			
			HtmlReportSupport.createHtmlSummaryReport(browser, url);

			//driver.quit();
			closeSummaryReport();
			report.flush();
			// added to check unknown flag
			//report.close();
			//sendwebmail();
			driver.quit();
			/*
			try {
				//String script = "E:\\MMS\\MMS\\sendmail.vbs";
				String script = System.getProperty("user.dir")+"\\sendmail.vbs";
				// search for real path:
				String executable = "C:\\windows\\System32\\wscript.exe"; 
				String cmdArr [] = {executable, script};
				Runtime.getRuntime ().exec (cmdArr);
				
				
			} catch (Exception ex) {
			    ex.printStackTrace();
			} 
			*/
			
			
			try {
				copyFile();
				killChromeDirver();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("close chromedriver issue.");
				e.printStackTrace();
			}
			// rohan softassert
			
			// latest code to send email  C:\demo\NewMMS  C:\Windows\System32
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(" @AfterSuite failed.");
		}
		
		
	}
	

	/**
	 * Write results to Browser specific path
	 * 
	 * @Date 19/02/2013
	 * @Revision History
	 * 
	 */
	// @Parameters({"browserType"})
	public static String filePath() {
		String strDirectoy = "";
		
			if (browser.equalsIgnoreCase("ie")) {
				strDirectoy = "IE\\IE";

			} else if (browser.equalsIgnoreCase("firefox")) {
				strDirectoy = "Firefox\\Firefox";

			} else {
				strDirectoy = "Chrome\\Chrome";
				
			}

		if (strDirectoy != "") {
			new File(configProps.getProperty("screenShotPath") + strDirectoy
					+ "_" + timeStamp).mkdirs();
		}
	
		File results = new File(configProps.getProperty("screenShotPath") + strDirectoy
			+ "_" + timeStamp+"\\"+"Screenshots");
		if(!results.exists())
		{
			results.mkdir();
			HtmlReportSupport.copyLogos();
		}

		return configProps.getProperty("screenShotPath") + strDirectoy + "_"
				+ timeStamp + "\\";

	}

	/**
	 * Browser type prefix for Run ID
	 * 
	 * @Date 19/02/2013
	 * @Revision History
	 * 
	 */
	public static String result_browser() {
		if (groupNames.equals("[]")) {
			if (configProps.getProperty("browserType").equals("ie")) {
				return "IE";
			} else if (configProps.getProperty("browserType").equals("firefox")) {
				return "Firefox";
			} else {
				return "Chrome";
			}
		} else {
			if (browser.equalsIgnoreCase("ie")) {
				return "IE";

			} else if (browser.equalsIgnoreCase("firefox")) {
				return "Firefox";

			} else {
				return "Chrome";

			}
		}
	}

	/**
	 * Related to Xpath
	 * 
	 * @Date 19/02/2013
	 * @Revision History
	 * 
	 */
	public static String methodName() {
		
			if (browser.equals("ie")) {
				return "post";
			} else {
				return "POST";
			}
		}
	@BeforeMethod(alwaysRun = true)
	public void reportHeader(Method method, ITestContext ctx) throws Throwable {
		
		try {
			System.out.println("@BeforeMethod");
			
			itc = ctx;
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("dd_MMM_yyyy hh mm ss SSS");
			String formattedDate = sdf.format(date);
			ReportStampSupport.calculateTestCaseStartTime();
			test = report.startTest(method.getName().toString());

			flag = false;

			HtmlReportSupport.tc_name = method.getName().toString() + "-"
					+ formattedDate;
			String[] ts_Name = this.getClass().getName().toString().split("\\.");
			HtmlReportSupport.packageName = ts_Name[0] + "." + ts_Name[1] + "."
					+ ts_Name[2];


				HtmlReportSupport.testHeader(HtmlReportSupport.tc_name, browser);

			stepNum = 0;
			PassNum = 0;
			FailNum = 0;
			testName = method.getName();

			driver.manage().window().maximize();
			driver.get(url);
			driver.get(url);
			System.out.println("@BeforeMethod -- End");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("@BeforeMethod -- failed");
		}
	
	}
	
		
	
	@AfterMethod(alwaysRun = true)
	public static void tearDown(ITestResult result) throws InterruptedException
	{
		try {
			System.out.println("entering after method");
			ReportStampSupport.calculateTestCaseExecutionTime();
			closeDetailedReport();
			
			if(FailNum!=0 && result.getStatus()==ITestResult.FAILURE)
			{
				failCounter=failCounter+1;
				testResults.put(HtmlReportSupport.tc_name, "FAIL");
				test.log(LogStatus.FAIL, result.getThrowable());
				
			}
			else if(result.getStatus()==ITestResult.SUCCESS)
			{
				testResults.put(HtmlReportSupport.tc_name, "PASS");
				passCounter=passCounter+1;
				test.log(LogStatus.PASS,"Test Case PASSED IS " + result.getName());
				
			}else if(result.getStatus()==ITestResult.SKIP) {
				test.log(LogStatus.SKIP,"Test Case SKIPPED IS " + result.getName());
			}
			
			
			report.endTest(test);
			
			//added new to check unknown issue
			//report.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("@AfterMethod failed.");
		}
	}
	
	public static void killChromeDirver() throws InterruptedException, IOException{
		try {

		//	Thread.sleep(minutes % 60 * 60L * 1000L);
			Runtime.getRuntime().exec("taskkill /f /im chromedriver.exe");
			//System.exit(0);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void screenshot(ITestResult result) throws Throwable
	{
		String timeStamp;
		File screenShotName;
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		//The below method will save the screen shot in d drive with name "screenshot.png"
		timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime()); 
		//screenShotName = new File("D:\\TestingDen\\Screenshots\\"+timeStamp+".png");
		//D:\all automation\AxisRevamp\Results\Screenshots
		screenShotName = new File("D:\\all automation\\AxisRevamp\\Results\\Screenshots\\"+result.getMethod().getMethodName()+timeStamp+".png");
		FileUtils.copyFile(scrFile, screenShotName);
		 
		String filePath = screenShotName.toString();
		
		String path = "<a href=\"file://" + filePath + "</a>";
		
		//<a href="c:\picture\picture.jpg">Variable Web Link</a> 
		
//		//Reporter.log(path);
//		Reporter.reportStep("Link to screenshot is"+path);
	Reporter.SuccessReport("<a href=\"file://" + filePath + "</a>", "Link to screenshot is");
	//Reporter.reportStep("Link to screenshot is"+filePath);
		
		//Reporter.log("<a href=/screenshots/" + destFile + "><img src=/screenshots/" + destFile + " style=width:100px;height:100px;/>" + destFile + "</a><br/>");
		
		// <a href=/screenshots/" + destFile + "><img src=/screenshots/" + destFile + " style=width:100px;height:100px;/>" + destFile + "</a><br/>
	}
	
	
	
	
	
	@DataProvider
	public Object[][] getData(Method method, ITestContext ctx) {
		System.out.println("@DataProvider -- Start");

		testName = method.getName();
		System.out.println("testName --" + testName);
		return DataUtil.getData(xls, testName);
	}
	
	
	
		
		
		

	
	
	public static void sendwebmail() throws InterruptedException, IOException, AWTException{
		try {

		//	Thread.sleep(minutes % 60 * 60L * 1000L);
			driver.get("https://mail.ventureinfotek.com/owa/auth/logon.aspx?replaceCurrent=1&url=https%3a%2f%2fmail.ventureinfotek.com%2fowa%2f");
		    driver.findElement(By.id("username")).clear();
		    String user=configProps.getProperty("webmailusername");
		    String password= configProps.getProperty("webmailpassword");
		    configProps.getProperty("URL");
		    driver.findElement(By.id("username")).sendKeys("VENTUREINFOTEK/rohan.baraskar");
		    driver.findElement(By.id("password")).clear();
		    driver.findElement(By.id("password")).sendKeys("Fiti4u@i21");
		    driver.findElement(By.cssSelector("span.signinTxt")).click();
		    driver.findElement(By.id("_ariaId_39")).click();
		    
		    Thread.sleep(4000);
		    
		    try {
		    	driver.findElement(By.xpath(" html/body/div[12]/div/div/div/div/div/div/div[3]/div[1]/button[1]")).click();
		    	
		    }catch(Exception e) {
		    	
		    	
		    }
		    
		   
		    
		    driver.findElement(By.xpath("/html/body/div[2]/div/div[3]/div[3]/div/div[1]/div[2]/div[7]/div/div/div[3]/div[2]/div[1]/div[3]/div[1]/div[2]/div/div/span/div[1]/form/input")).clear();
		    Thread.sleep(1000);
		    driver.findElement(By.xpath("/html/body/div[2]/div/div[3]/div[3]/div/div[1]/div[2]/div[7]/div/div/div[3]/div[2]/div[1]/div[3]/div[1]/div[2]/div/div/span/div[1]/form/input")).sendKeys("rohan.baraskar@worldline.com;sagar.lanke@worldline.com;arvind.bangar@worldline.com;shuyab-ahmad.syed@worldline.com");
		    Thread.sleep(1000);
		    driver.findElement(By.xpath("/html/body/div[2]/div/div[3]/div[3]/div/div[1]/div[2]/div[7]/div/div/div[3]/div[2]/div[1]/div[4]/div[1]/div[2]/div/div/span/div[1]/form/input")).clear();
		    Thread.sleep(1000);
		   // driver.findElement(By.xpath("/html/body/div[2]/div/div[3]/div[3]/div/div[1]/div[2]/div[7]/div/div/div[3]/div[2]/div[1]/div[4]/div[1]/div[2]/div/div/span/div[1]/form/input")).sendKeys("rohan.baraskar@worldline.com;dattatraya.agnihotri@worldline.com;gaurav.patil@worldline.com;s.venkatesh@worldline.com;aniket.deshmukh@worldline.com;sanjeev.bhasker@worldline.com;lavanya.richard@worldline.com");
		    driver.findElement(By.xpath("/html/body/div[2]/div/div[3]/div[3]/div/div[1]/div[2]/div[7]/div/div/div[3]/div[2]/div[1]/div[4]/div[1]/div[2]/div/div/span/div[1]/form/input")).sendKeys("rohan.baraskar@worldline.com");
		    Thread.sleep(1000);
		    
		    
		   
		    driver.findElement(By.xpath("/html/body/div[2]/div/div[3]/div[3]/div/div[1]/div[2]/div[7]/div/div/div[3]/div[2]/div[1]/div[7]/div/div/input")).clear();
		    driver.findElement(By.xpath("/html/body/div[2]/div/div[3]/div[3]/div/div[1]/div[2]/div[7]/div/div/div[3]/div[2]/div[1]/div[7]/div/div/input")).sendKeys("MMS Execution Report");
		    
		    
		    driver.findElement(By.xpath("/html/body/div[2]/div/div[3]/div[3]/div/div[1]/div[2]/div[7]/div/div/div[2]/div[1]/span[1]/div[3]/button/span[2]")).click();
		    Thread.sleep(1000);
		   
		    //driver.findElement(By.xpath("//*[text()='Attachments']")).click();
		    
		    
		    String projectPath = System.getProperty("user.dir");
		    String PathTofile = "\\target\\surefire-reports\\Extentreport.html";
		    
		    String path = projectPath + PathTofile;
		 /*
		    StringSelection ss = new StringSelection(path);
		    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
		    
		    Robot robot = new Robot();

		    robot.keyPress(KeyEvent.VK_CONTROL);
		    robot.keyPress(KeyEvent.VK_V);
		    robot.keyRelease(KeyEvent.VK_V);
		    robot.keyRelease(KeyEvent.VK_CONTROL);
		    robot.keyPress(KeyEvent.VK_ENTER);
		    robot.keyRelease(KeyEvent.VK_ENTER);
		    */
		    
		    
		    
		    driver.findElement(By.xpath("//*[text()='Attachments']")).click();
		    
		    
	           
	           			StringSelection ss = new StringSelection(path);
	           			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
	  		    
	  		   
	  		    
	           			Robot robot = new Robot();
	           			robot.delay(2050);

	           			robot.keyPress(KeyEvent.VK_ENTER);
	           			robot.delay(2050);
	           			robot.keyRelease(KeyEvent.VK_ENTER);
	           			robot.delay(2050);
	           			robot.keyPress(KeyEvent.VK_CONTROL);
	           			robot.delay(2050);
	           			robot.keyPress(KeyEvent.VK_V);
	           			robot.delay(2050);
	           			robot.keyRelease(KeyEvent.VK_V);
	           			robot.delay(2050);
	           			robot.keyRelease(KeyEvent.VK_CONTROL);
	           			robot.delay(2050);
	           			robot.keyPress(KeyEvent.VK_ENTER);
	           			robot.delay(2050);
	           			robot.delay(1000);
	           			robot.keyRelease(KeyEvent.VK_ENTER);
	           			robot.delay(2050);
	  		    
	           			Thread.sleep(3000);
	             
	           //cntrl to parent window
	           		
		    
		   
		  
		   
		    Thread.sleep(3000);
		    // ERROR: Caught exception [Error: locator strategy either id or name must be specified explicitly.]
		    driver.findElement(By.xpath("/html/body/div[2]/div/div[3]/div[3]/div/div[1]/div[2]/div[7]/div/div/div[2]/div[1]/span[1]/div[1]/button[1]/span[2]")).click();
		    //driver.close();
		    System.out.println("WebMail succesfully sent.");
		    Thread.sleep(4000);
		    

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void copyFile() throws IOException{ 
		File from1 = new File("programming.txt"); 
		File to1 = new File("java6");
		
		String token= new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());;
		
		String path= System.getProperty("user.dir");
		
		String from2= path+"\\target\\surefire-reports\\Extentreport.html";
		String to2=path+"\\Results\\ExtentReports\\"+token+"TestResult.html";
		
		File actfrom= new File(from2);
		File actto= new File(to2);
		
		

		System.out.println("Copying file to another directory using Apache commons IO"); 
		 

		 FileUtils.copyFile(actfrom, actto);
		
		}
	
	public static void logout() throws Throwable {
		
		Thread.sleep(1000);
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		try{
			
		
				if(driver.findElements(By.linkText("Logout")).size()>0){
					Thread.sleep(1000);
					driver.findElement(By.linkText("Logout")).click();
					
			}else{
				
			}
			
			
		}catch(Exception e){
			
		}
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	/***
	 * Wait till spinner gets disappear or timeout occurs
	 * @param driver Browser instance
	 * @param by Locator to locate spinner
	 * @return true if spinner disappear else false
	 */
	public static boolean waitTillSpinnerDisable(WebDriver driver, By by)
	{		
		
		
		FluentWait<WebDriver> fWait = new FluentWait<WebDriver>(driver);
		fWait.withTimeout(120, TimeUnit.SECONDS);
		fWait.pollingEvery(100, TimeUnit.MILLISECONDS);
		fWait.ignoring(NoSuchElementException.class);
		
		
		
		Function<WebDriver, Boolean> func = new Function<WebDriver, Boolean>() 
		{
			
			@Override
			public Boolean apply(WebDriver driver) {
				WebElement element = driver.findElement(by);
			
				System.out.println(element.getCssValue("display"));			
				
				if(element.getCssValue("display").equalsIgnoreCase("none")){
					return true;}
				
				return false;
		}
			};
			
		
		return fWait.until(func);
		
	}
	
	
}
