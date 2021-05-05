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


public class CustomerBlock_UnblockDetails extends ActionEngine {

	public String USERID;
	public String password;

	static Logger logger = Logger.getLogger(CustomerBlock_UnblockDetails.class.getName());

	public static boolean CustBlock(Hashtable <String,String > data) throws Throwable

	{
		boolean result = false;
		ImplicitWait();
		HtmlReportSupport.reportStep("Customer Block Request");
		if (waitForElementPresent(MainpageLocator.OnlineRequest,"OnlineRequest Link Present")) {

			//click(MainpageLocator.CallRequest,"Call Request Clicked Successfully");
		}
		waitForPageLoaded();
		
		
		if (waitForElementPresent(OnlineOffLineCall_Locator.CustomerBlockRequest,"CustomerBlockRequest Link Present")) 
		{

			click(OnlineOffLineCall_Locator.CustomerBlockRequest,"CardBlockRequest Clicked Successfully");
			waitForPageLoaded();
		}
		

		waitForPageLoaded();
		selectByVisibleText(CardCancellation_Locator.BlockCategory,data.get("BlockCategory"),"BlockCategory");
		waitForPageLoaded();
		selectByVisibleText(CardCancellation_Locator.BlockCode,data.get("BlockCode"),"BlockCode");
		waitForPageLoaded();
		selectByVisibleText(CardCancellation_Locator.ChannelType,data.get("ChannelType"),"ChannelType");
		
		
		ImplicitWait();
		JSClick(CardCancellation_Locator.SendRequestButton, "Send Request button ");
		Thread.sleep(3000);
		
		Reporter.takescreenshot("Customer_Blocked");
		waitForAjax(driver);
		String expectedresponse;
		List<WebElement> actualresponse;
		String actualresponse1,actualresponse2,actualresponse3,actualresponse4;

		String actText=null;
		
		try {
		//	waitForElementPresent(By.id("ContentPlaceHolder1_pnlPopup2"), "Wait for Element Display");
			expectedresponse = "successfully Blocked";
			
			
			//Thread.sleep(2000);
			//Thread.sleep(5000);
			
			if(waitTillSpinnerDisable(driver,By.id("ContentPlaceHolder1_UpdateProgress1")))
			{
				actualresponse = driver.findElements(By.id("ContentPlaceHolder1_lblPopUp"));
				 actText=actualresponse.get(1).getText();
			}
			
			
			
			if (actText.trim().contains(expectedresponse.trim()))    

			{
				Reporter.reportStep("Message from the application is :"+actText);
				Reporter.SuccessReport("Customer Blocked successfully "," Customer Blocked successfully  ");
				
				List<WebElement> btns=driver.findElements(By.id("ContentPlaceHolder1_btnHide2"));
				btns.get(1).click();
			} 
			else 
			{
				Reporter.reportStep("Message from the appliaction is :"+actText);
				Reporter.failureReport("Customer Blocking ", "Customer Blocking Failed");
				
			}

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Reporter.takescreenshot("Customer_Blocked_failed");
		}
		
		result=CustomerCancellationDB(data);
		
		return result;

	}
	
	public static boolean CustUnblock(Hashtable <String,String > data) throws Throwable

	{
		boolean result = false;
		ImplicitWait();
		HtmlReportSupport.reportStep("Customer Unblocking");
		
		
		
		if (waitForElementPresent(OnlineOffLineCall_Locator.CustomerUnBlockRequest,"CustomerUnBlockRequest Link Present")) 
		{

			click(OnlineOffLineCall_Locator.CustomerUnBlockRequest,"CustomerUnBlockRequest Clicked Successfully");
			waitForPageLoaded();
		}
		

		waitForPageLoaded();
		
		
		selectByVisibleText(CardCancellation_Locator.CustUnblockReason,data.get("UnblockReason").trim(),"UnblockReason");
		waitForPageLoaded();
		
		selectByVisibleText(CardCancellation_Locator.ChannelType,data.get("ChannelType").trim(),"ChannelType");
		waitForPageLoaded();
		
		JSClick(CardCancellation_Locator.SendRequestButton, "Send Request button");
		Thread.sleep(3000);
		
		Reporter.takescreenshot("Customer_UnBlocked");
		waitForAjax(driver);
		String expectedresponse;
		List<WebElement> actualresponse;
		String actualresponse1,actualresponse2,actualresponse3,actualresponse4;
		String actText=null;
		
		try {
		//	waitForElementPresent(By.id("ContentPlaceHolder1_pnlPopup2"), "Wait for Element Display");
			expectedresponse = "successfully Unblocked";
			
			
		//	Thread.sleep(2000);
		//	Thread.sleep(5000);
			
			if(waitTillSpinnerDisable(driver,By.id("ContentPlaceHolder1_UpdateProgress1")))
			{
				actualresponse = driver.findElements(By.id("ContentPlaceHolder1_lblPopUp"));
				 actText=actualresponse.get(1).getText();
			}
			
			
			
			
			if (actText.trim().contains(expectedresponse.trim()))    

			{
				Reporter.reportStep("Message from the application is :"+actText);
				Reporter.SuccessReport("Customer UnBlocked successfully "," Customer UnBlocked successfully  ");
				
				List<WebElement> btns=driver.findElements(By.id("ContentPlaceHolder1_btnHide2"));
				btns.get(1).click();
			} 
			else 
			{
				Reporter.reportStep("Message from the appliaction is :"+actText);
				Reporter.failureReport("Customer UnBlocking ", "Customer UnBlocking Failed");
				

			}

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Reporter.takescreenshot("Customer_UnBlocked_failed");
		}
		
		
		// code to validate DB setup

		// select CRD_NO,CRD_CUST_ID,CRD_ACCOUNT_ID,CRD_MANUAL_BLOCK_CODE,CRD_MANUAL_BLOCK_DATE,CRD_MANUAL_UNBLOCK_DATE from fins_cardmaster;
		
		// click on modal
		//JSClick(CardCancellation_Locator.CloseButton, "Close button ");
		
		
		
		//******RESULT******
		
		//CardCancellationDB(data);
		result=CustomerActivatioDB(data);
		
		
		return result;

	}

	public static boolean CustomerCancellationDB(Hashtable <String,String > data) throws Throwable

	{
		
		
		ResultSet rs,rs1;
		boolean result = false;
		String username;
		String password ;
		
		String url = configProps.getProperty("DBURL");
		//String mid= configProps.getProperty("MID");
		//String custID= data.get("CustomerID");
		String customerid= data.get("CustomerID");
		
	
		String query = "SELECT * FROM fins_clientmaster WHERE CLI_CUST_ID ='"+customerid+ "'"; 
		System.out.println("query is"+query);
				
		username =configProps.getProperty("DBUsername");
		password = configProps.getProperty("DBPassword");
		
		System.out.println("username is"+username);
		System.out.println("password is"+password);
		
		 String CLI_MANUAL_BLOCK_CODE = null;
		 String CLI_MANUAL_BLOCK_DATE= null;
		
		 
		rs =CallLogin.connectDB(url,username,password,query);
		
		
		 while(rs.next()){
			 
			 CLI_MANUAL_BLOCK_CODE = rs.getString("CLI_MANUAL_BLOCK_CODE");
			 CLI_MANUAL_BLOCK_DATE = rs.getString("CLI_MANUAL_BLOCK_DATE");
	     
		 }
		 
		 
		
		 if( CLI_MANUAL_BLOCK_CODE!= null) {
			 Reporter.reportStep("CLI_MANUAL_BLOCK_CODE value from fins_clientmaster table is : "+CLI_MANUAL_BLOCK_CODE);
			 Reporter.reportStep("CLI_MANUAL_BLOCK_DATE value from fins_clientmaster table is : "+CLI_MANUAL_BLOCK_DATE);
			 Reporter.SuccessReport("Customer block database validation", "Customer block database validation succesful");
			 result=true;
			
		 }else {
			 Reporter.reportStep("CLI_MANUAL_BLOCK_CODE value from fins_clientmaster table is : "+CLI_MANUAL_BLOCK_CODE);
			 Reporter.reportStep("CLI_MANUAL_BLOCK_DATE value from fins_clientmaster table is : "+CLI_MANUAL_BLOCK_DATE);
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