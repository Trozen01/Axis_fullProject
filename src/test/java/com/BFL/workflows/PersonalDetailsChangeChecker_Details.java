package com.BFL.workflows;

import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.BFL.testObjects.CardCancellation_Locator;
import com.BFL.testObjects.MainpageLocator;
import com.BFL.testObjects.OnlineOffLineCall_Locator;
import com.MainFrameWork.accelerators.ActionEngine;
import com.MainFrameWork.support.HtmlReportSupport;
import com.MainFrameWork.utilities.Reporter;
import com.MainFrameWork.utils.RandomTextUtils;


public class PersonalDetailsChangeChecker_Details extends ActionEngine {

	public String USERID;
	public String password;

	static Logger logger = Logger.getLogger(PersonalDetailsChangeChecker_Details.class.getName());

	public static boolean ChangeDetails(Hashtable <String,String > data) throws Throwable

	{
		boolean result = false;
		ImplicitWait();
		HtmlReportSupport.reportStep("Personal Details Change");
		if (waitForElementPresent(MainpageLocator.OnlineRequest,"OnlineRequest Link Present")) {

			//click(MainpageLocator.CallRequest,"Call Request Clicked Successfully");
		}
		waitForPageLoaded();
		
		scrolltoElement(driver.findElement(OnlineOffLineCall_Locator.PDChangeRequest));
		
		if (waitForElementPresent(OnlineOffLineCall_Locator.PDChangeRequest,"PDChangeRequest Link Present")) 
		{

			click(OnlineOffLineCall_Locator.PDChangeRequest,"PDChangeRequest Clicked Successfully");
			waitForPageLoaded();
		}
		
		String ResMobileNo= RandomTextUtils.getRandomMobileNo();
		String ResEmail= RandomTextUtils.getEmail();
		
		//String OffMobileNo= RandomTextUtils.getRandomMobileNo();
		//String OffEmail= RandomTextUtils.getEmail();
		
		
		data.put("ResMob", ResMobileNo);
		data.put("ResEmail", ResEmail);
		//data.put("OffMob", OffMobileNo);
		//data.put("OffEmail", OffEmail);

		waitForPageLoaded();
		type(CardCancellation_Locator.ResMobileNumber,ResMobileNo,"Resident Mobile No");
		waitForPageLoaded();
		type(CardCancellation_Locator.ResEmail,ResEmail,"Resident Email Id");
		waitForPageLoaded();
		//type(CardCancellation_Locator.OffMobileNumber,OffMobileNo,"Office Mobile No");
		//waitForPageLoaded();
		//type(CardCancellation_Locator.OffEmail,OffEmail,"Office Email Id");
		waitForPageLoaded();
		selectByVisibleText(CardCancellation_Locator.ChannelType,data.get("ChannelType"),"ChannelType");
		
		
		ImplicitWait();
		JSClick(CardCancellation_Locator.Submit, "Submit button ");
		Thread.sleep(3000);
		
		Reporter.takescreenshot("Personal_Detail_Change");
		waitForAjax(driver);
		String expectedresponse;
		List<WebElement> actualresponse;
		String actualresponse1,actualresponse2,actualresponse3,actualresponse4;
		String actText=null;
		try {
		//	waitForElementPresent(By.id("ContentPlaceHolder1_pnlPopup2"), "Wait for Element Display");
			expectedresponse = "request successfully";
			
			
			//Thread.sleep(2000);
			//Thread.sleep(5000);
			if(waitTillSpinnerDisable(driver,By.id("ContentPlaceHolder1_UpdateProgress1")))
			{
				actualresponse = driver.findElements(By.id("ContentPlaceHolder1_lblPopUp"));
				actText=actualresponse.get(1).getText();
			}
			
			if (actText.trim().contains(expectedresponse.trim()))    

			{
				result= true;
				Reporter.reportStep("Message from the application is :"+actText);
				Reporter.SuccessReport("Personal details change request done successfully "," Passed  ");
				
				List<WebElement> btns=driver.findElements(By.id("ContentPlaceHolder1_btnHide2"));
				btns.get(1).click();
			} 
			else 
			{
				Reporter.reportStep("Message from the application is :"+actText);
				Reporter.failureReport("Personal details change", "Personal details change Failed");
				
			}

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Reporter.takescreenshot("Personal_details_change_failed");
		}
		
		//result=CustomerCancellationDB(data);
		
		return result;

	}
	
	public static boolean CheckerApproval(Hashtable <String,String > data) throws Throwable

	{
		boolean result = false;
		ImplicitWait();
		HtmlReportSupport.reportStep("Personal Detail Checker");
		
		String custid= data.get("CustomerID");
		waitForPageLoaded();
		
		scrolltoElement(driver.findElement(OnlineOffLineCall_Locator.PDChangeChecker));
		if (waitForElementPresent(OnlineOffLineCall_Locator.PDChangeChecker,"Personal detail checker Link Present")) 
		{

			click(OnlineOffLineCall_Locator.PDChangeChecker,"Personal detail checker link Clicked Successfully");
			waitForPageLoaded();
		}
		waitForPageLoaded(); 
		if(driver.findElement(By.id("ContentPlaceHolder1_gvmaker_lblcustid_0")).getText().contains(custid)) {
			driver.findElement(By.id("ContentPlaceHolder1_gvmaker_CheckBox1_0")).click();
			click(OnlineOffLineCall_Locator.PDChangeSendApprove,"personal detail checker send approve button.");
			
			
		}

		waitForPageLoaded();
		waitForAjax(driver);
		
		String messageTest= driver.findElement(By.id("ContentPlaceHolder1_gvstatus_lblErr_Desc_0")).getText();
		Reporter.takescreenshot("Personal_Detail_Checker_popup");
		JSClick(CardCancellation_Locator.Closepopup, "Popup Close button");
		Reporter.reportStep("Message from checker popup is:"+messageTest);
		Thread.sleep(3000);
		
		Reporter.takescreenshot("Personal_Detail_Checker");
		
		
		result=PersonalDeailsCheckDB(data);
		
		
		return result;

	}

	public static boolean PersonalDeailsCheckDB(Hashtable <String,String > data) throws Throwable

	{
		
		
		ResultSet rs,rs1;
		boolean result = false;
		String username;
		String password ;
		
		String url = configProps.getProperty("DBURL");
		//String mid= configProps.getProperty("MID");
		//String custID= data.get("CustomerID");
		String customerid= data.get("CustomerID");
		
		String ResMob= data.get("ResMob");
		String ResEmail= data.get("ResEmail");
		String OffMob= data.get("OffMob");
		String OffEmail= data.get("OffEmail");
		
	
		String query = "SELECT * FROM fins_clientmaster WHERE CLI_CUST_ID ='"+customerid+ "'"; 
		System.out.println("query is"+query);
				
		username =configProps.getProperty("DBUsername");
		password = configProps.getProperty("DBPassword");
		
		System.out.println("username is"+username);
		System.out.println("password is"+password);
		
		 String CLI_RES_MOBILE = null;
		 String CLI_RES_EMAIL= null;
		 
		 String CLI_OFC_MOBILE = null;
		 String CLI_OFC_EMAIL= null;  
		
		 String CLI_RES_MOBILE_CHG_DATE= null;  
		 String CLI_RES_EMAIL_DATE= null;  
		 
		 String CLI_OFC_MOBILE_CHG_DATE= null;  
		 String CLI_OFC_EMAIL_DATE = null;  
		 
		rs =CallLogin.connectDB(url,username,password,query);
		
		
		 while(rs.next()){
			 
			 CLI_RES_MOBILE = rs.getString("CLI_RES_MOBILE");
			 CLI_RES_EMAIL = rs.getString("CLI_RES_EMAIL");
			 CLI_OFC_MOBILE = rs.getString("CLI_OFC_MOBILE");
			 CLI_OFC_EMAIL = rs.getString("CLI_OFC_EMAIL");
			 
			 CLI_RES_MOBILE_CHG_DATE = rs.getString("CLI_RES_MOBILE_CHG_DATE");
			 CLI_RES_EMAIL_DATE = rs.getString("CLI_RES_EMAIL_DATE");
			 CLI_OFC_MOBILE_CHG_DATE = rs.getString("CLI_OFC_MOBILE_CHG_DATE");
			 CLI_OFC_EMAIL_DATE = rs.getString("CLI_OFC_EMAIL_DATE");
	     
		 }
		 
		 
		
		 if( CLI_RES_MOBILE.contains(ResMob) && CLI_RES_EMAIL.contains(ResEmail) 
				 ) {
			 Reporter.reportStep("CLI_RES_MOBILE value from fins_clientmaster table is : "+CLI_RES_MOBILE);
			 Reporter.reportStep("CLI_RES_EMAIL value from fins_clientmaster table is : "+CLI_RES_EMAIL);
			
			 Reporter.reportStep("CLI_RES_MOBILE_CHG_DATE value from fins_clientmaster table is : "+CLI_RES_MOBILE_CHG_DATE);
			 Reporter.reportStep("CLI_RES_EMAIL_DATE value from fins_clientmaster table is : "+CLI_RES_EMAIL_DATE);
			
			 Reporter.SuccessReport("Personal details change validations", "Personal details change database validation succesful");
			 result=true;
			
		 }else {
			
			 Reporter.reportStep("CLI_RES_MOBILE value from fins_clientmaster table is : "+CLI_RES_MOBILE);
			 Reporter.reportStep("CLI_RES_EMAIL value from fins_clientmaster table is : "+CLI_RES_EMAIL);
			
			 Reporter.reportStep("CLI_RES_MOBILE_CHG_DATE value from fins_clientmaster table is : "+CLI_RES_MOBILE_CHG_DATE);
			 Reporter.reportStep("CLI_RES_EMAIL_DATE value from fins_clientmaster table is : "+CLI_RES_EMAIL_DATE);
			 Reporter.failureReport("Customer block database validation", "Customer block database validation failed.");
		 }
		 
		 
		return result;
		
	}
	
	
	public static boolean CustomerActivatioDB(Hashtable <String,String > data) throws Throwable

	{
		
		
		ResultSet rs,rs1;
		boolean result = false;
		String username;
		String password ;
		
		String url = configProps.getProperty("DBURL");
		//String mid= configProps.getProperty("MID");
		String custID= data.get("CustomerID");
		
	
		
		
		String query = "SELECT * FROM fins_clientmaster WHERE CLI_CUST_ID ='"+custID+ "'"; 
		System.out.println("query is"+query);
				
		username =configProps.getProperty("DBUsername");
		password = configProps.getProperty("DBPassword");
		
		System.out.println("username is"+username);
		System.out.println("password is"+password);
		
		 String CLI_MANUAL_BLOCK_CODE = null;
		 String CLI_MANUAL_BLOCK_DATE= null;
		 String CLI_MANUAL_UNBLOCK_DATE  = null;
		
		 
		rs =CallLogin.connectDB(url,username,password,query);
		
		
		 while(rs.next()){
			 
			 CLI_MANUAL_BLOCK_CODE = rs.getString("CLI_MANUAL_BLOCK_CODE");
			 CLI_MANUAL_BLOCK_DATE = rs.getString("CLI_MANUAL_BLOCK_DATE");
			 CLI_MANUAL_UNBLOCK_DATE = rs.getString("CLI_MANUAL_UNBLOCK_DATE");
	      
	     
		 }
		 
		 
		
		 if(CLI_MANUAL_UNBLOCK_DATE!= null) {
			 
			 
			 Reporter.reportStep("CLI_MANUAL_UNBLOCK_DATE value is :"+CLI_MANUAL_UNBLOCK_DATE);
			 Reporter.reportStep("CLI_MANUAL_BLOCK_CODE value from fins_clientmaster table is : "+CLI_MANUAL_BLOCK_CODE);
			 Reporter.reportStep("CLI_MANUAL_BLOCK_DATE value from fins_clientmaster table is : "+CLI_MANUAL_BLOCK_DATE);
			 Reporter.SuccessReport("Customer Unblocking Database validations","Customer Unblocking Database validation pass.");
			 result=true;
		 }else {
			 Reporter.reportStep("CLI_MANUAL_UNBLOCK_DATE value is :"+CLI_MANUAL_UNBLOCK_DATE);
			 Reporter.reportStep("CLI_MANUAL_BLOCK_CODE value from fins_clientmaster table is : "+CLI_MANUAL_BLOCK_CODE);
			 Reporter.reportStep("CLI_MANUAL_BLOCK_DATE value from fins_clientmaster table is : "+CLI_MANUAL_BLOCK_DATE);
			 Reporter.failureReport("Customer Unblock database validation", "Customer Unblock database validation fail.");
			 
		 }
		 
		
		return result;
		
	}
	

	
}