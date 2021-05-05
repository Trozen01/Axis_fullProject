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


public class CardCancellationDetails extends ActionEngine {

	public String USERID;
	public String password;

	static Logger logger = Logger.getLogger(CardCancellationDetails.class.getName());

	public static boolean CardCancellation(Hashtable <String,String > data) throws Throwable

	{
		boolean result = false;
		ImplicitWait();
		HtmlReportSupport.reportStep("Card Block Request");
		if (waitForElementPresent(MainpageLocator.OnlineRequest,"OnlineRequest Link Present")) {

			//click(MainpageLocator.CallRequest,"Call Request Clicked Successfully");
		}
		waitForPageLoaded();
	
		
		
		if (waitForElementPresent(OnlineOffLineCall_Locator.CardBlockRequest,"CardBlockRequest Link Present")) 
		{

			click(OnlineOffLineCall_Locator.CardBlockRequest,"CardBlockRequest Clicked Successfully");
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
		
		Reporter.takescreenshot("Card_Blocked");
		waitForAjax(driver);
		String expectedresponse;
		List<WebElement> actualresponse;
		String actualresponse1,actualresponse2,actualresponse3,actualresponse4;
		String actText=null;
		try {
		//	waitForElementPresent(By.id("ContentPlaceHolder1_pnlPopup2"), "Wait for Element Display");
			expectedresponse = "successfully Blocked";
			
			
		//	Thread.sleep(2000);
		//	Thread.sleep(5000);
			if(waitTillSpinnerDisable(driver,By.id("ContentPlaceHolder1_UpdateProgress1")))
			{
				actualresponse = driver.findElements(By.id("ContentPlaceHolder1_lblPopUp"));
				 actText=actualresponse.get(2).getText();
			}
			
			
			
			if (actText.trim().contains(expectedresponse.trim()))    

			{
				Reporter.reportStep("Message from the appliaction is :"+actText);
				Reporter.SuccessReport("Card Blocked successfully "," Card Blocked successfully  ");
				
				List<WebElement> btns=driver.findElements(By.id("ContentPlaceHolder1_btnHide2"));
				btns.get(2).click();
			} 
			else 
			{

				Reporter.failureReport("Card Blocking ", "Card Blocking Failed");

			}

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Reporter.takescreenshot("Card_Blocked_failed");
		}
		
		result=CardCancellationDB(data);
		
		return result;

	}
	
	public static boolean CardActivation(Hashtable <String,String > data) throws Throwable

	{
		boolean result = false;
		ImplicitWait();
		HtmlReportSupport.reportStep("Card Unblocking");
		
		
		
		if (waitForElementPresent(OnlineOffLineCall_Locator.CardUnBlockRequest,"CardUnBlockRequest Link Present")) 
		{

			click(OnlineOffLineCall_Locator.CardUnBlockRequest,"CardUnBlockRequest Clicked Successfully");
			waitForPageLoaded();
		}
		

		waitForPageLoaded();
		
			waitForElementPresent(CardCancellation_Locator.AccountId, "AccountId");
			selectByVisibleText(CardCancellation_Locator.AccountId,data.get("AccountId"),"AccountId");
			waitForPageLoaded();
		
		
		
			waitForElementPresent(CardCancellation_Locator.CardNumber_drp, "CardNumber");
			selectByVisibleText(CardCancellation_Locator.CardNumber_drp,data.get("CardNumber"),"CardNumber");
			waitForPageLoaded();
		
		
		
		
		selectByVisibleText(CardCancellation_Locator.UnblockReason,data.get("UnblockReason").trim(),"UnblockReason");
		waitForPageLoaded();
		
		selectByVisibleText(CardCancellation_Locator.ChannelType,data.get("ChannelType").trim(),"ChannelType");
		waitForPageLoaded();
		
		JSClick(CardCancellation_Locator.SendRequestButton, "Send Request button");
		Thread.sleep(3000);
		
		Reporter.takescreenshot("Card_UnBlocked");
		waitForAjax(driver);
		String expectedresponse;
		List<WebElement> actualresponse;
		String actualresponse1,actualresponse2,actualresponse3,actualresponse4;
		String actText=null;
		
		try {
		//	waitForElementPresent(By.id("ContentPlaceHolder1_pnlPopup2"), "Wait for Element Display");
			expectedresponse = "successfully Un-blocked";
			
			
			//Thread.sleep(2000);
			//Thread.sleep(5000);
			

			if(waitTillSpinnerDisable(driver,By.id("ContentPlaceHolder1_UpdateProgress1")))
			{
				actualresponse = driver.findElements(By.id("ContentPlaceHolder1_lblPopUp"));
				 actText=actualresponse.get(3).getText();
			}
			
			
			
			if (actText.trim().contains(expectedresponse.trim()))    

			{
				Reporter.reportStep("Message from the appliaction is :"+actText);
				Reporter.SuccessReport("Card UnBlocked successfully "," Card UnBlocked successfully  ");
				
				List<WebElement> btns=driver.findElements(By.id("ContentPlaceHolder1_btnHide2"));
				btns.get(3).click();
			} 
			else 
			{

				Reporter.failureReport("Card UnBlocking ", "Card UnBlocking Failed");

			}

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Reporter.takescreenshot("Card_Blocked_failed");
		}
		
		
		// code to validate DB setup

		// select CRD_NO,CRD_CUST_ID,CRD_ACCOUNT_ID,CRD_MANUAL_BLOCK_CODE,CRD_MANUAL_BLOCK_DATE,CRD_MANUAL_UNBLOCK_DATE from fins_cardmaster;
		
		// click on modal
		//JSClick(CardCancellation_Locator.CloseButton, "Close button ");
		
		
		
		//******RESULT******
		
		//CardCancellationDB(data);
		result=CardActivatioDB(data);
		
		
		return result;

	}

	public static boolean CardCancellationDB(Hashtable <String,String > data) throws Throwable

	{
		
		
		ResultSet rs,rs1;
		boolean result = false;
		String username;
		String password ;
		
		String url = configProps.getProperty("DBURL");
		//String mid= configProps.getProperty("MID");
		//String custID= data.get("CustomerID");
		String cardnumber= data.get("CardNumber");
		
		// select * from fins_cardmaster where CRD_NO='2030409955489000';
		// select * from fins_cardmaster where CRD_CUST_ID='435';
		
		//String query = "select * from MAOPSAUDITLOG where MID ='"+ mercode + "'";
		//String query = "SELECT * FROM fins_cardmaster WHERE CRD_CUST_ID ='"+custID+ "'"; 
		String query = "SELECT * FROM fins_cardmaster WHERE CRD_NO ='"+cardnumber+ "'"; 
		System.out.println("query is"+query);
				
		username =configProps.getProperty("DBUsername");
		password = configProps.getProperty("DBPassword");
		
		System.out.println("username is"+username);
		System.out.println("password is"+password);
		
		 String CRD_MANUAL_BLOCK_CODE = null;
		 String CRD_MANUAL_BLOCK_DATE= null;
		 String CRD_SYSTEM_BLOCK_CODE= null;
		 String CRD_SYSTEM_BLOCK_DATE= null;
		 
		rs =CallLogin.connectDB(url,username,password,query);
		
		
		 while(rs.next()){
			 
	      CRD_MANUAL_BLOCK_CODE = rs.getString("CRD_MANUAL_BLOCK_CODE");
	      CRD_MANUAL_BLOCK_DATE = rs.getString("CRD_MANUAL_BLOCK_DATE");
	      CRD_SYSTEM_BLOCK_CODE = rs.getString("CRD_SYSTEM_BLOCK_CODE");
	      CRD_SYSTEM_BLOCK_DATE = rs.getString("CRD_SYSTEM_BLOCK_DATE");
	    
	 
		
		 }
		 
		 
		
		 if( CRD_MANUAL_BLOCK_CODE!= null) {
			 Reporter.reportStep("CRD_MANUAL_BLOCK_CODE value from fins_cardmaster table is : "+CRD_MANUAL_BLOCK_CODE);
			 Reporter.reportStep("CRD_MANUAL_BLOCK_DATE value from fins_cardmaster table is : "+CRD_MANUAL_BLOCK_DATE);
			 Reporter.SuccessReport("Card Unblock database validation", "Card Unblock database validation succesful");
			 result=true;
			
		 }else {
			 Reporter.failureReport("Card block database validation", "Card block database validation failed.");
			 Reporter.reportStep("CRD_MANUAL_BLOCK_CODE value from fins_cardmaster table is : "+CRD_MANUAL_BLOCK_CODE);
			 Reporter.reportStep("CRD_MANUAL_BLOCK_DATE value from fins_cardmaster table is : "+CRD_MANUAL_BLOCK_DATE);
		 }
		 
		 
		return result;
		
	}
	
	
	public static boolean CardActivatioDB(Hashtable <String,String > data) throws Throwable

	{
		
		
		ResultSet rs,rs1;
		boolean result = false;
		String username;
		String password ;
		
		String url = configProps.getProperty("DBURL");
		//String mid= configProps.getProperty("MID");
		String custID= data.get("CustomerID");
		
		String cardnumber= data.get("CardNumber");
		
		// select * from fins_cardmaster where CRD_NO='2030409955489000';
		// select * from fins_cardmaster where CRD_CUST_ID='435';
		
		//String query = "select * from MAOPSAUDITLOG where MID ='"+ mercode + "'";
		//String query = "SELECT * FROM fins_cardmaster WHERE CRD_CUST_ID ='"+custID+ "'"; 
		String query = "SELECT * FROM fins_cardmaster WHERE CRD_NO ='"+cardnumber+ "'"; 
		
		System.out.println("query is"+query);
				
		username =configProps.getProperty("DBUsername");
		password = configProps.getProperty("DBPassword");
		
		System.out.println("username is"+username);
		System.out.println("password is"+password);
		
		 String CRD_MANUAL_BLOCK_CODE = null;
		 String CRD_MANUAL_BLOCK_DATE= null;
		 String CRD_SYSTEM_BLOCK_CODE= null;
		 String CRD_SYSTEM_BLOCK_DATE= null;
		 String CRD_MANUAL_UNBLOCK_DATE= null;
		 
		rs =CallLogin.connectDB(url,username,password,query);
		
		
		 while(rs.next()){
			 
	      CRD_MANUAL_BLOCK_CODE = rs.getString("CRD_MANUAL_BLOCK_CODE");
	      CRD_MANUAL_BLOCK_DATE = rs.getString("CRD_MANUAL_BLOCK_DATE");
	      CRD_SYSTEM_BLOCK_CODE = rs.getString("CRD_SYSTEM_BLOCK_CODE");
	      CRD_SYSTEM_BLOCK_DATE = rs.getString("CRD_SYSTEM_BLOCK_DATE"); 
	      CRD_MANUAL_UNBLOCK_DATE = rs.getString("CRD_MANUAL_UNBLOCK_DATE");
	     
		 }
		 
		 
		
		 if(CRD_MANUAL_UNBLOCK_DATE!= null) {
			 
			 Reporter.reportStep("CRD_MANUAL_BLOCK_CODE value from fins_cardmaster table is : "+CRD_MANUAL_BLOCK_CODE);
			 Reporter.reportStep("CRD_MANUAL_BLOCK_DATE value from fins_cardmaster table is : "+CRD_MANUAL_BLOCK_DATE);
			 Reporter.reportStep("CRD_MANUAL_UNBLOCK_DATE value from fins_cardmaster table is : "+CRD_MANUAL_UNBLOCK_DATE);
			
			 Reporter.SuccessReport("Card Unblocking Database validations","Card Unblocking Database validation pass");
			 result=true; 
		 }else {
			 Reporter.reportStep("CRD_MANUAL_BLOCK_CODE value from fins_cardmaster table is : "+CRD_MANUAL_BLOCK_CODE);
			 Reporter.reportStep("CRD_MANUAL_BLOCK_DATE value from fins_cardmaster table is : "+CRD_MANUAL_BLOCK_DATE);
			 Reporter.reportStep("CRD_MANUAL_UNBLOCK_DATE value from fins_cardmaster table is : "+CRD_MANUAL_UNBLOCK_DATE);
			 Reporter.failureReport("Card block database validation", "Card block database validation failed");
			
		 }
		 
		
		return result;
		
	}
	

	
}