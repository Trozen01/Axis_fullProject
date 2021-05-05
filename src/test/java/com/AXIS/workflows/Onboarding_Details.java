package com.AXIS.workflows;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.Select;

import com.AXIS.testObjects.SignInClass;
import com.BFL.workflows.CallLogin;
import com.MainFrameWork.accelerators.ActionEngine;
import com.MainFrameWork.support.HtmlReportSupport;
import com.MainFrameWork.utilities.Reporter;

import bsh.org.objectweb.asm.Type;







public class Onboarding_Details extends ActionEngine{
	
	Logger logger = Logger.getLogger(SignInClass.class.getName());
	
	public static boolean E2EFlowNewMerchantAdditionNewFlow(Hashtable <String,String > data) throws Throwable

	{
		boolean result = false;
		boolean Result2 = false;
		boolean Result3 = false;
		boolean Result4 = false;
	
		ImplicitWait();
		Reporter.reportStep("Merchant onboarding");
		
		
		if (waitForElementPresent(MainpageLocator.MerchantOnboarding,"Merchant Onboarding Link Present")) {

			Reporter.SuccessReport("Merchant Onboarding Link Present. "," Passed");

			Reporter.SuccessReport("TestCases Covered:", "TC_0001N");
			Reporter.takescreenshot("Merchant_Onboarding_Link_Present");
			
			
			// hover over menu and click functionality present, automated using click as manual test case mentioned
			click(MainpageLocator.MerchantOnboarding,"Merchant Onboarding Clicked Successfully");
			Thread.sleep(3000);

			waitForPageLoaded();
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(2000);
			//ImplicitWait();
			//test
			if (waitForElementPresent(MainpageLocator.AddMerchantProfile,"Add new Merchant Profile Link Present"))
			{
				waitForPageLoaded();
				waitForVisibilityOfElement(MainpageLocator.AddMerchantProfile,"Add new Merchant Profile Link Present");
				click(MainpageLocator.AddMerchantProfile,"Add new Merchant Profile Link clicked successfully");
				
			}
			
			waitForJSandJQueryToLoad();
				//waitForElementPresent(MainpageLocator.MerchantDetailsLable,"Merchant Details Lable Present");
			if (assertTextPresent("Add Merchant Profile")){
				result = true;
				Reporter.SuccessReport("TC_001 & TC_0002:ADD MERCHANT PROFILE text present on page, Click on Add New Merchant Profile successful. "," Passed  ");
				Reporter.takescreenshot("ADD_MERCHANT_PROFILE_text_present");
				Reporter.SuccessReport("TestCases Covered:", "TC_0003N,TC_0004N");
			}else{
				Reporter.SuccessReport("TestCases Failed:", "TC_0003N,TC_0004N");
				Reporter.failureReport("TC_001 & TC_0002:ADD MERCHANT PROFILE text not present on page, Click on Add New Merchant Profile unsuccessful. "," Failed  ");
			}
			
			
			waitForPageLoaded();
			waitForAjax(driver);
			
			// switch to iframe
			switchToFrame();
			
			//passing driver
			AddMerchentDetails_Existing(driver,data);
			// coming back to main page from Iframe
			driver.switchTo().defaultContent();
				
			Result2= Checkerapproval(driver, data);
			
			result = InstitutionApproval(driver,data);
			
			logout(driver);	
			
			// maker checker logs validation
		//	Result3= makerchekerflagDBvalidation(driver,data);
			
			}
		
		return result;

	}
	
	public static void AddMerchentDetails_Existing(EventFiringWebDriver driver, Hashtable<String, String> data) throws Throwable {
		
		String OldMid = data.get("OldMID");
		
		
		
		Robot robot = new Robot(); 
		// temp code remove later if incompatible
		// wait for page to load
		//Thread.sleep(3000);
		//waitforElementDisplayed(webelement,5);
		
		waitForSpinner(driver, 10);
		waitForElementPresent(MainpageLocator.MerchantId, "Merchant Identification");
		selectByVisibleText(MainpageLocator.MerchantId, "Existing", "Merchant Identification");
		Thread.sleep(3000);
		
		waitForSpinner(driver, 10);
		waitForElementPresent(MainpageLocator.OldMID, "Old MID");
		type(MainpageLocator.OldMID, OldMid, "Old MID");
	
		
		
		Thread.sleep(3000);
		
		robot.keyPress(KeyEvent.VK_TAB);	
		
		waitForSpinner(driver, 10);
		JavascriptExecutor js = ((JavascriptExecutor) driver);
	    js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
	    
	    waitForElementPresent(MainpageLocator.Reamrks, "Remarks");
		type(MainpageLocator.Reamrks, "Existing Mid used comment", "Remarks");
		waitForElementPresent(MainpageLocator.Submit, "Submit button");
		click(MainpageLocator.Submit, "Submit button");
		
		Thread.sleep(3000);
		waitForAjax(driver);
		waitForSpinner(driver, 5);
		String msg=null;
		String actMerchantCode=null;
		
		try {
			if(driver.findElements(By.id("ContentPlaceHolder1_lblmsgsuccess")).size()>0) {
				waitForElementPresent(MainpageLocator.MerchantCodeMsg, "Merchant Code");
				msg=driver.findElement(By.id("ContentPlaceHolder1_lblmsgsuccess")).getText();
				Reporter.reportStep("Message from the application is :"+msg);
				String[] splited = msg.split(":");
			    actMerchantCode= splited[1];
			    data.put("MID", actMerchantCode);
			    Reporter.takescreenshot("Existing_Merchant_code_generated");
			    driver.findElement(By.xpath("//*[@id='ContentPlaceHolder1_divSuccess']/div/div/div[3]/button")).click();
			    Reporter.SuccessReport("Merchant code Generated", "Merchant code generated");
			}else {
				Reporter.failureReport("Step Fail", "Merchant Code not generated");
				Reporter.takescreenshot("Existing_Merchant_code_makerFail");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			Reporter.failureReport("Step Fail", "Merchant Code not generated");
		}
		
		logout(driver);	
	}

	public static void logout(EventFiringWebDriver driver) {
		driver.switchTo().defaultContent();
		driver.findElement(By.id("logout")).click();
		Reporter.reportStep("LogOut done");
	}
	public static void AddMerchentDetailsNewFlow(EventFiringWebDriver driver, Hashtable<String, String> data) throws Throwable {
		// TODO Auto-generated method stub
		
		
		fillMerchantDetails(driver,data);
		
		/*
		
		fillInstallationAddress(driver,data);
		
		fillRegisteredAddress(driver,data);
		
		fillOwnerDetails1SameAsAbove(driver,data);
		
		fillOwnerDetails2SameAsAbove(driver,data);
		
		fillGSTLORstate(driver,data);
			
		fillPaymentDetails(driver,data);
		// new change
		fillMerchantReimbersement(driver,data);
		
		fillEDCInstallationDetails(driver,data);
		
		fillMerchantMSFDetails(driver,data);
		
		fillOfficeUseDetails(driver,data);
		
		enterRemarks(driver, data);
		
		//clickSubmit(driver, data);
		clickSave(driver, data);
		*/
		
		
	}
	
public static boolean Checkerapproval(EventFiringWebDriver driver, Hashtable<String, String> data) throws Throwable {
		
	boolean result = false;
	String OldMid = data.get("OldMID");
		
		SignInClass_Axis.specificlogin("CheckerUsername","CheckerPassword");
		
		Robot robot = new Robot(); 
		
		waitForPageLoaded();
		waitForElementPresent(MainpageLocator.MerchantOnboarding, "MerchantOnboarding");
		click(MainpageLocator.MerchantOnboarding, "MerchantOnboarding link");
		waitForPageLoaded();
		waitForElementPresent(MainpageLocator.Dashboard, "Dashboard");
		click(MainpageLocator.Dashboard, "Dashboard");
		waitForPageLoaded();
		Thread.sleep(2000);
		switchToFrame();
		waitForAjax(driver);
		waitForElementPresent(MainpageLocator.chk_Mid, "MID");
		type(MainpageLocator.chk_Mid, data.get("MID"), "MID");
		waitForElementPresent(MainpageLocator.chk_status, "status");
		selectByVisibleText(MainpageLocator.chk_status, "Pending for Approval", "Status");
		Thread.sleep(2000);
		waitForPageLoaded(); 
		waitForElementPresent(MainpageLocator.chk_search, "search");
		click(MainpageLocator.chk_search, "search");
		Thread.sleep(2000);
		waitForPageLoaded(); 
		selectByVisibleText(MainpageLocator.chk_selectstatus, "Approve", "Status selected");
		
		
		try {
			if(driver.findElements(By.xpath("//*[@id='ContentPlaceHolder1_gv_searchMEdetails']/tbody/tr[2]/td[3]")).size()>0){
				//driver.findElement(By.name("ctl00$ContentPlaceHolder1$gv_searchMEdetails$ctl02$chkValid")).click();
				Thread.sleep(2000);
				driver.findElement(By.id("ContentPlaceHolder1_txtremarks")).sendKeys("Checker automation comments");
				driver.findElement(By.id("ContentPlaceHolder1_btnSubmit")).click();
				Thread.sleep(3000);
				String msg= driver.findElement(By.xpath("//*[@id='msgbox']/div/table/tbody/tr[2]/td[2]")).getText();
				if(msg.toLowerCase().contains("approved")) {
					Reporter.takescreenshot("Checker_Approval_pass");
					Reporter.SuccessReport("Checker approval", "Checker approval done succesfully.");
				}else {
					Reporter.failureReport("Checker module", "Error in chekar module");
					Reporter.takescreenshot("Checker_Approval_fail");
				}
				driver.findElement(By.xpath("//*[@id='msgbox']/div/table/tbody/tr[3]/td/input")).click();
				result= true;
			}
		
		}catch(Exception e) {
			Reporter.failureReport("Checker module", "Error in chekar module");
		}
		// temp code remove later if incompatible
		// wait for page to load
		//Thread.sleep(3000);
		//waitforElementDisplayed(webelement,5);
		//logout(driver);	
	//	waitForSpinner(driver, 10);
		driver.switchTo().defaultContent();
		return result;
}
	
	
		public static boolean InstitutionApproval(EventFiringWebDriver driver, Hashtable<String, String> data) throws Throwable {
			
			boolean result = false;
			String MID = data.get("MID");
				
				
				waitForPageLoaded();
				waitForElementPresent(MainpageLocator.chk_assetmgmt, "AssetManagement");
				click(MainpageLocator.chk_assetmgmt, "AssetManagement link");
				waitForPageLoaded();
				waitForElementPresent(MainpageLocator.chk_merappr, "Institution approval");
				click(MainpageLocator.chk_merappr, "Institution approval");
				waitForPageLoaded();
				Thread.sleep(2000);
				switchToFrame();
				waitForAjax(driver);
				
				waitForPageLoaded();
				waitForElementPresent(MainpageLocator.inst_mecode, "ME Code");
				type(MainpageLocator.inst_mecode, MID, "ME CODE");
				waitForElementPresent(MainpageLocator.inst_institution, "Institution Name");
				selectByVisibleText(MainpageLocator.inst_institution, "AXIS Bank", "Institution Name");
				Thread.sleep(2000);
				waitForElementPresent(MainpageLocator.inst_apprStatus, "Approval Status");
				selectByVisibleText(MainpageLocator.inst_apprStatus, "Pending for Approval", "Approval Status");
				Thread.sleep(2000);
				waitForElementPresent(MainpageLocator.inst_Search, "Search");
				click(MainpageLocator.inst_Search, "Search");
				Thread.sleep(5000);
				
				if(driver.findElements(By.id("ContentPlaceHolder1_gv_searchMEdetails_chkmercode_0")).size()>0) {
					driver.findElement(By.id("ContentPlaceHolder1_gv_searchMEdetails_chkmercode_0")).click();
					Thread.sleep(2000);
					Select sel= new Select(driver.findElement(By.id("ContentPlaceHolder1_gv_searchMEdetails_drp_terminal_0")));
					sel.selectByVisibleText("ICT-220");
					Thread.sleep(2000);
					driver.findElement(By.id("ContentPlaceHolder1_btn_Submit")).click();
					
					Thread.sleep(4000);
					
					if(driver.findElements(By.xpath("//*[@id='msgbox']/div/table/tbody/tr[3]/td/input")).size()>0){
						Reporter.takescreenshot("Institutional_Approval_pass");
						result=true;
						Reporter.SuccessReport("Institution Approval", "Institution Approval done succesfully.");
						driver.findElement(By.xpath("//*[@id='msgbox']/div/table/tbody/tr[3]/td/input")).click();
					}
					
				}else {
					Reporter.failureReport("Institution Approval Failed", "Institution Approval Failed");
					Reporter.takescreenshot("Institutional_Approval_Fail");
				}
				
				
				return result;
		}
		
		public static  ResultSet connectDB(String url, String username, String password, String query) throws SQLException{
			
			ResultSet rs = null ;
			 boolean result = false;
			 boolean status = false;
			
			
			 try {

		            Class.forName("oracle.jdbc.driver.OracleDriver");

		         } 	catch (ClassNotFoundException e) {

		            System.out.println("Where is your Oracle JDBC Driver?");
		            e.printStackTrace();
		           

		            }

		        System.out.println("Oracle JDBC Driver Registered!");

		        Connection connection = null;
		        
		        connection = null;
		        try {

		            connection = DriverManager.getConnection(url,username, password);
		            
		            
		            	if (connection != null) {
		                System.out.println("You made it, take control your database now!");
		            	} else {
		                System.out.println("Failed to make connection!");
		            	}
		       
		            	Statement stmt=connection.createStatement();
		            

		            	rs= stmt.executeQuery(query); 
		            	
		            	
		        	} catch (SQLException e) {

		                System.out.println("Connection Failed! Check output console");
		                e.printStackTrace();
		                

		            }
		        // close connection
		        //connection.close();
		        System.out.println("Op is:"+rs);
		       
			
			return rs;
		}
		
		public static boolean dbValidationExisting(EventFiringWebDriver driver, Hashtable<String, String> data) throws Throwable {

			// Object of Statement. It is used to create a Statement to execute the query
			
			ResultSet rs,rs1;
			boolean result = false;
			String username;
			String password ;
			
			String url = configProps.getProperty("DBURL");
			//String mid= configProps.getProperty("MID");
			//String custID= data.get("CustomerID");
			String customerid= data.get("CustomerID");
			
			
			//select * from merchantprofile where merchantcode='037222000040014';
			
		
			String query = "SELECT * FROM fins_accountmaster WHERE ACC_CUST_ID ='"+customerid+ "'"; 
			System.out.println("query is"+query);
					
			username =configProps.getProperty("DBUsername");
			password = configProps.getProperty("DBPassword");
			
			System.out.println("username is"+username);
			System.out.println("password is"+password);
			
			 String ACC_VIP_NONVIP_FLAG = null;
			 
			
			rs =CallLogin.connectDB(url,username,password,query);
			
			
			 while(rs.next()){
				 
				 ACC_VIP_NONVIP_FLAG = rs.getString("ACC_VIP_NONVIP_FLAG");
				
				System.out.println("vip flagis:"+ACC_VIP_NONVIP_FLAG);
				
				 
			 }
			 
			 if(ACC_VIP_NONVIP_FLAG!= null) {
				 data.put("ACC_VIP_NONVIP_FLAG", ACC_VIP_NONVIP_FLAG);
			 }else {
				 data.put("ACC_VIP_NONVIP_FLAG", "null");
			 }
			 
			 return result;
	}
		
		
		
	
	
	public static void fillMerchantDetails(EventFiringWebDriver driver, Hashtable<String, String> data) throws Throwable {
		// TODO Auto-generated method stub
				// enter merchant identification
	//	AxisAddMerchantProfile mp = new AxisAddMerchantProfile(driver);
		
		String MerchantIdentification = data.get("Merchant Identification");
		String MerchantType = data.get("Merchant Type");
		String MerchantPaymentCurrency = data.get("Merchant Payment Currency");
		String MerchantSegment = data.get("Merchant Segment");
		String Aggregator = data.get("Aggregator"); 
		String MerchantGroupProfiles = data.get("Merchant Group Profiles");
		String MCC = data.get("MCC"); 
		String TCC = data.get("TCC"); 
		String AadharNo = data.get("Aadhar No");
		String MerchantLegalName = data.get("Merchant Legal Name"); 
		String MerchantDBAName = data.get("Merchant DBA Name");
		String ZoneCode = data.get("Zone Code");
		String InstallationBranchCode = data.get("Installation Branch Code"); 
		String DOBIncorporationDate = data.get("DOB/Incorporation Date");
		String BusinessEntity = data.get("Business Entity"); 		
		String CashWithdrawal = data.get("Cash Withdrawal (Cash@pos)"); 	
		String InstaBharatQRPan = data.get("Insta Bharat QR Pan");
		String Fuelassociation = data.get("Fuel Association");
		String FuelDealerID = data.get("Fuel Dealer ID");
		String FuelRemarks = data.get("Fuel Remarks");
		
		
		/*
				//select Merchant Identification
				if(MerchantIdentification.length()>0){
					//Thread.sleep(5000);
					
					
					
					AxisAddMerchantProfile.waitforElementToBeClickable(mp.getmerchantIDEle(driver),8);
					mp.getmerchantID(driver,MerchantIdentification);
					Reporter.reportStep("Merchant Identification selcted as  " +MerchantIdentification);
					Reporter.SuccessReport("TestCases Covered:", "TC_0006N,TC_1515");
					robot.keyPress(KeyEvent.VK_TAB);	
					
			    }
			
				// type application number
				//Thread.sleep(3000);
				AxisAddMerchantProfile.waitForSpinner(driver, 5);
				AxisAddMerchantProfile.waitforElementToBeClickable(mp.gettxt_ApplicationNo(),8);
			    mp.gettxt_ApplicationNo().clear();
			    //AxisAddMerchantProfile.waitForSpinner(driver, 5);
				
			    //Thread.sleep(3000);
				// application number is fetched from properties file as field "AppNumber"
			    AxisAddMerchantProfile.waitForSpinner(driver, 5);
			    AxisAddMerchantProfile.waitforElementToBeClickable(mp.gettxt_ApplicationNo(),8);
				mp.gettxt_ApplicationNo().sendKeys(configProps.getProperty("AppNumber"));
				Reporter.SuccessReport("TestCases Covered:", "TC_0008N,TC_1517");
			    Reporter.reportStep("Application Number entered as : " +configProps.getProperty("AppNumber"));
			   
			    //select merchant type
			    if(MerchantType.length()>0){
//			    	robot.keyPress(KeyEvent.VK_TAB);
//			    	Thread.sleep(3000);
			    	AxisAddMerchantProfile.waitForSpinner(driver, 5);
			    	AxisAddMerchantProfile.waitforElementToBeClickable(mp.getmerchantTypeEle(driver),8);
			    	mp.getmerchantType(driver,MerchantType);
			    	Reporter.SuccessReport("TestCases Covered:", "TC_0012N,TC_1521");
			    	
			    	Reporter.reportStep("Merchant Type selected as :" +MerchantType);
			    }
			   // new validations for USD
			    
			    if(MerchantType.equalsIgnoreCase("USD")){
			    	// print no of dropdowns
			    	AxisAddMerchantProfile.waitForSpinner(driver, 5);
			    	AxisAddMerchantProfile.waitForElementVisible(mp.getmerchantPayCurEle(driver),5);
			    	Select dropdown = new Select(driver.findElement(By.id("ContentPlaceHolder1_ucMerchantDetails_drp_MEPaymentCurrency")));

			        List<WebElement> dd = dropdown.getOptions();

			        // Loop to print one by one
			        for (int j = 0; j < dd.size(); j++) {
			            System.out.println(dd.get(j).getText());
			            String opt= dd.get(j).getText();
			            Reporter.reportStep("Options available in Merchant payment currency dropdown are:"+opt);
			            if(opt.contains("INR") || opt.contains("USD")){
			            	 Reporter.SuccessReport("INR & USD options are availble in dropdown:", "TC009,TC_0020/TC_0021/TC_0022");
			            	 Reporter.reportStep("Merchant payment currency field is enabled for merchant type USD");
			            }
			        }
			        
			        AxisAddMerchantProfile.waitForSpinner(driver, 5);
			    	AxisAddMerchantProfile.waitforElementToBeClickable(mp.getmerchantPayCurEle(driver),8);
			    	mp.getmerchantPayCur(driver,MerchantPaymentCurrency);
			    	
			    	Reporter.SuccessReport("TestCases Covered:", "TC_1839");
			    	
			    	Reporter.reportStep("Merchant pay currency selected as :" +MerchantPaymentCurrency);
			        
			    }
			    
			    
			   
			    //select merchant segment
			    if(MerchantSegment.length()>0){
//			    	robot.keyPress(KeyEvent.VK_TAB);
//			    	Thread.sleep(3000);
			    	AxisAddMerchantProfile.waitForSpinner(driver, 5);
			    	AxisAddMerchantProfile.waitforElementToBeClickable(mp.getmerchantSegmentEle(driver),8);
			    	mp.getmerchantSegment(driver, MerchantSegment); 
			    	Reporter.reportStep("Merchant Segment selected as :" +MerchantSegment);
			    	Reporter.SuccessReport("TestCases Covered:", "TC_0013N,TC_0014N,TC_1522,TC_1523");
			    	
			    }
			    //Thread.sleep(2000);
			    
			    // select merchant aggregator
			    if(Aggregator.length()>0){
			    	AxisAddMerchantProfile.waitForSpinner(driver, 8);
			    	AxisAddMerchantProfile.waitforElementToBeVisible(mp.getaggregatorEle(driver),8);
//			    	robot.keyPress(KeyEvent.VK_TAB);
//			    	Thread.sleep(3000);
			    	mp.getaggregator(driver, Aggregator);
			    	Reporter.reportStep("Aggregator selected as :" +Aggregator);
			    	Reporter.SuccessReport("TestCases Covered:", "TC_0015N/TC_1524");
			    }
			    Thread.sleep(2000);
	
		
		*/
	}
	
	
	
	
	
	
	
	
	

}
