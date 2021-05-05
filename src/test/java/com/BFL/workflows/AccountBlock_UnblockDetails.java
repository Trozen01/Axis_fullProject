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


public class AccountBlock_UnblockDetails extends ActionEngine {

	public String USERID;
	public String password;

	static Logger logger = Logger.getLogger(AccountBlock_UnblockDetails.class.getName());

	public static boolean AccountBlock(Hashtable <String,String > data) throws Throwable

	{
		boolean result = false;
		ImplicitWait();
		HtmlReportSupport.reportStep("Account Block Request");
		if (waitForElementPresent(MainpageLocator.OnlineRequest,"OnlineRequest Link Present")) {

			//click(MainpageLocator.CallRequest,"Call Request Clicked Successfully");
		}
		waitForPageLoaded();
		
		
		if (waitForElementPresent(OnlineOffLineCall_Locator.AccountBlockRequest,"AccountBlockRequest Link Present")) 
		{

			click(OnlineOffLineCall_Locator.AccountBlockRequest,"Account Block Request Clicked Successfully");
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
		
		Reporter.takescreenshot("Account_Blocked");
		waitForAjax(driver);
		
		String expectedresponse;
		List<WebElement> actualresponse;
		String actualresponse1,actualresponse2,actualresponse3,actualresponse4;
		String actText = null;
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
				Reporter.SuccessReport("Account Blocked successfully "," Account Blocked successfully  ");
				
				List<WebElement> btns=driver.findElements(By.id("ContentPlaceHolder1_btnHide2"));
				btns.get(1).click();
			} 
			else 
			{
				Reporter.reportStep("Message from the appliaction is :"+actText);
				Reporter.failureReport("Account Blocking ", "Account Blocking Failed");
				
			}

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Reporter.takescreenshot("Account_Blocked_failed");
		}
		
		result=AccountCancellationDB(data);
		
		return result;

	}
	
	public static boolean AccountUnblock(Hashtable <String,String > data) throws Throwable

	{
		boolean result = false;
		ImplicitWait();
		HtmlReportSupport.reportStep("Account Unblocking");
		
		
		
		if (waitForElementPresent(OnlineOffLineCall_Locator.AccountUnBlockRequest,"Account UnBlockRequest Link Present")) 
		{

			click(OnlineOffLineCall_Locator.AccountUnBlockRequest,"Account UnBlockRequest Clicked Successfully");
			waitForPageLoaded();
		}
		

		waitForPageLoaded();
		
		
			waitForElementPresent(CardCancellation_Locator.AccountId, "AccountId");
			selectByVisibleText(CardCancellation_Locator.AccountId,data.get("AccountId"),"AccountId");
			waitForPageLoaded();
		
		
		
			waitForElementPresent(CardCancellation_Locator.CardNumber_drp, "CardNumber");
			selectByVisibleText(CardCancellation_Locator.CardNumber_drp,data.get("CardNumber"),"CardNumber");
			waitForPageLoaded();
		
		
		
		Thread.sleep(2000);
		selectByVisibleText(CardCancellation_Locator.AccountUnblockReason,data.get("UnblockReason").trim(),"UnblockReason");
		waitForPageLoaded();
		
		selectByVisibleText(CardCancellation_Locator.ChannelType,data.get("ChannelType").trim(),"ChannelType");
		waitForPageLoaded();
		
		JSClick(CardCancellation_Locator.SendRequestButton, "Send Request button");
		Thread.sleep(3000);
		
		Reporter.takescreenshot("Account_UnBlocked");
		waitForAjax(driver);
		String expectedresponse;
		List<WebElement> actualresponse;
		String actualresponse1,actualresponse2,actualresponse3,actualresponse4;
		String actText =null;
		try {
		//	waitForElementPresent(By.id("ContentPlaceHolder1_pnlPopup2"), "Wait for Element Display");
			expectedresponse = "successfully Un-Blocked";
			
			
			//Thread.sleep(2000);
			//Thread.sleep(5000);
			if(waitTillSpinnerDisable(driver,By.id("ContentPlaceHolder1_UpdateProgress1")))
			{
				actualresponse = driver.findElements(By.id("ContentPlaceHolder1_lblPopUp"));
				actText=actualresponse.get(3).getText();
			}
			
			
			
			
			if (actText.trim().contains(expectedresponse.trim()))    

			{
				Reporter.reportStep("Message from the application is :"+actText);
				Reporter.SuccessReport("Account UnBlocked successfully "," Account UnBlocked successfully  ");
				
				List<WebElement> btns=driver.findElements(By.id("ContentPlaceHolder1_btnHide2"));
				btns.get(3).click();
			} 
			else 
			{
				Reporter.reportStep("Message from the appliaction is :"+actText);
				Reporter.failureReport("Account UnBlocking ", "Account UnBlocking Failed");
				

			}

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Reporter.takescreenshot("Account_UnBlocked_failed");
		}
		
		
		// code to validate DB setup

		// select CRD_NO,CRD_CUST_ID,CRD_ACCOUNT_ID,CRD_MANUAL_BLOCK_CODE,CRD_MANUAL_BLOCK_DATE,CRD_MANUAL_UNBLOCK_DATE from fins_cardmaster;
		
		// click on modal
		//JSClick(CardCancellation_Locator.CloseButton, "Close button ");
		
		
		
		//******RESULT******
		
		//CardCancellationDB(data);
		result=AccountActivatioDB(data);
		
		
		return result;

	}

	public static boolean AccountCancellationDB(Hashtable <String,String > data) throws Throwable

	{
		
		
		ResultSet rs,rs1;
		boolean result = false;
		String username;
		String password ;
		
		String url = configProps.getProperty("DBURL");
		//String mid= configProps.getProperty("MID");
		//String custID= data.get("CustomerID");
		String customerid= data.get("CustomerID");
		
	
		String query = "SELECT * FROM fins_accountmaster WHERE ACC_CUST_ID ='"+customerid+"'"; 
		System.out.println("query is"+query);
				
		username =configProps.getProperty("DBUsername");
		password = configProps.getProperty("DBPassword");
		
		System.out.println("username is"+username);
		System.out.println("password is"+password);
		
		 String ACC_MANUAL_BLOCK_CODE = null;
		 String ACC_MANUAL_BLOCK_DATE= null;
		
		 
		rs =CallLogin.connectDB(url,username,password,query);
		
		
		 while(rs.next()){
			 
			 ACC_MANUAL_BLOCK_CODE = rs.getString("ACC_MANUAL_BLOCK_CODE");
			 ACC_MANUAL_BLOCK_DATE = rs.getString("ACC_MANUAL_BLOCK_DATE");
	     
		 }
		 
		 
		
		 if( ACC_MANUAL_BLOCK_CODE!= null) {
			 Reporter.reportStep("ACC_MANUAL_BLOCK_CODE value from fins_accountmaster table is : "+ACC_MANUAL_BLOCK_CODE);
			 Reporter.reportStep("ACC_MANUAL_BLOCK_DATE value from fins_accountmaster table is : "+ACC_MANUAL_BLOCK_DATE);
			 Reporter.SuccessReport("Account block database validation", "Account block database validation succesful");
			 result=true;
			
		 }else {
			 Reporter.failureReport("Account block database validation", "Account block database validation failed.");
			 Reporter.reportStep("ACC_MANUAL_BLOCK_CODE value from fins_accountmaster table is : "+ACC_MANUAL_BLOCK_CODE);
			 Reporter.reportStep("ACC_MANUAL_BLOCK_DATE value from fins_accountmaster table is : "+ACC_MANUAL_BLOCK_DATE);
		 }
		 
		 
		return result;
		
	}
	
	
	public static boolean AccountActivatioDB(Hashtable <String,String > data) throws Throwable

	{
		
		
		ResultSet rs,rs1;
		boolean result = false;
		String username;
		String password ;
		
		String url = configProps.getProperty("DBURL");
		//String mid= configProps.getProperty("MID");
		String custID= data.get("CustomerID");
		
	//select * from fins_accountmaster where ACC_CUST_ID='435';
		
		
		String query = "SELECT * FROM fins_accountmaster WHERE ACC_CUST_ID ='"+custID+ "'"; 
		System.out.println("query is"+query);
				
		username =configProps.getProperty("DBUsername");
		password = configProps.getProperty("DBPassword");
		
		System.out.println("username is"+username);
		System.out.println("password is"+password);
		
		 String ACC_MANUAL_BLOCK_CODE = null;
		 String ACC_MANUAL_BLOCK_DATE= null;
		 String ACC_MANUAL_UNBLOCK_DATE   = null;
		
		 
		rs =CallLogin.connectDB(url,username,password,query);
		
		
		 while(rs.next()){
			 
			 ACC_MANUAL_BLOCK_CODE = rs.getString("ACC_MANUAL_BLOCK_CODE");
			 ACC_MANUAL_BLOCK_DATE = rs.getString("ACC_MANUAL_BLOCK_DATE");
			 ACC_MANUAL_UNBLOCK_DATE  = rs.getString("ACC_MANUAL_UNBLOCK_DATE");
	      // test
	     
		 }
		 
		 
		
		 if(ACC_MANUAL_UNBLOCK_DATE!= null) {
			 
			 
			 Reporter.reportStep("ACC_MANUAL_UNBLOCK_DATE value is :"+ACC_MANUAL_UNBLOCK_DATE);
			 Reporter.reportStep("ACC_MANUAL_BLOCK_CODE value from fins_accountmaster table is : "+ACC_MANUAL_BLOCK_CODE);
			 Reporter.reportStep("ACC_MANUAL_BLOCK_DATE value from fins_accountmaster table is : "+ACC_MANUAL_BLOCK_DATE);
			 Reporter.SuccessReport("Account Unblocking Database validations","Account Unblocking Database validation pass.");
			 result=true;
		 }else {
			 Reporter.reportStep("ACC_MANUAL_UNBLOCK_DATE value is :"+ACC_MANUAL_UNBLOCK_DATE);
			 Reporter.reportStep("ACC_MANUAL_BLOCK_CODE value from fins_accountmaster table is : "+ACC_MANUAL_BLOCK_CODE);
			 Reporter.reportStep("ACC_MANUAL_BLOCK_DATE value from fins_accountmaster table is : "+ACC_MANUAL_BLOCK_DATE);
			 Reporter.failureReport("Account Unblock database validation", "Account Unblock database validation fail.");
			 
		 }
		 
		
		return result;
		
	}
	

	
}