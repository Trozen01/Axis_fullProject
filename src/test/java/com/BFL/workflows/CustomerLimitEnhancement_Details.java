package com.BFL.workflows;

import java.sql.ResultSet;
import java.text.DecimalFormat;
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


public class CustomerLimitEnhancement_Details extends ActionEngine {

	public String USERID;
	public String password;

	static Logger logger = Logger.getLogger(CustomerLimitEnhancement_Details.class.getName());

	public static boolean CustLimitEnhancement(Hashtable <String,String > data) throws Throwable

	{
		boolean result = false;
		ImplicitWait();
		HtmlReportSupport.reportStep("Customer Limit Enhancemenet Request");
		if (waitForElementPresent(MainpageLocator.OnlineRequest,"OnlineRequest Link Present")) {

			//click(MainpageLocator.CallRequest,"Call Request Clicked Successfully");
		}
		waitForPageLoaded();
		
		
		if (waitForElementPresent(OnlineOffLineCall_Locator.CustomerlimitEnhancement,"CustomerlimitEnhancement Link Present")) 
		{

			click(OnlineOffLineCall_Locator.CustomerlimitEnhancement,"CustomerlimitEnhancement Clicked Successfully");
			waitForPageLoaded();
		}
		
		String currentlimit= driver.findElement(By.id("txtCurrentLimit")).getAttribute("value");
		
		String expnum= currentlimit.replace(".00", "");
		
		Reporter.reportStep("Current Limit is:"+expnum);
		
		int newnum= Integer.parseInt(expnum.trim());
		
		int newlimit=newnum+1;
		String formatted;
		try {
			
			formatted=String.valueOf(newlimit);
		
			waitForPageLoaded(); 
			js_type(CardCancellation_Locator.NewLimit,formatted , "New limit");
			
			data.put("NewLimit",formatted);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		
//NewLimit
		
		ImplicitWait();
		JSClick(CardCancellation_Locator.SendRequestButton_new, "Send Request button ");
		Thread.sleep(3000);
		waitForPageLoaded();
		Reporter.takescreenshot("Customer_LimitEnhancement");
		waitForAjax(driver);
		String expectedresponse,actualresponse = null;
		
		String actualresponse1,actualresponse2,actualresponse3,actualresponse4;
		String actText=null;
		try {
		//	waitForElementPresent(By.id("ContentPlaceHolder1_pnlPopup2"), "Wait for Element Display");
			expectedresponse = "successfully Changed";
			Thread.sleep(3000);
			
			actualresponse = driver.findElement(By.id("ContentPlaceHolder1_lblDisplayStatus")).getText();
			System.out.println("message from app is"+actualresponse);
			
			Thread.sleep(2000);
			
			
			if (actualresponse.trim().contains(expectedresponse.trim()))    

			{
				Reporter.reportStep("Message from the application is :"+actualresponse);
				Reporter.SuccessReport("Customer Limit Enhancement done successfully "," Passed  ");
				
				driver.findElement(By.id("ContentPlaceHolder1_btnHide")).click();
				
			} 
			else 
			{
				Reporter.reportStep("Message from the appliaction is :"+actualresponse);
				Reporter.failureReport("Customer Limit Enhancement ", "Customer Limit Enhancement Failed");
				driver.findElement(By.id("ContentPlaceHolder1_btnHide")).click();
				
			}

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Reporter.takescreenshot("Customer_Limit_Enhancement_failed");
		}
		
		result=CustomerLimitEnhancementDB(data);
		
		return result;

	}
	
	public static boolean AccountLimitEnhancement(Hashtable <String,String > data) throws Throwable

	{
		boolean result = false;
		ImplicitWait();
		HtmlReportSupport.reportStep("Account Limit Enhancemenet Request");
		if (waitForElementPresent(MainpageLocator.OnlineRequest,"OnlineRequest Link Present")) {

			//click(MainpageLocator.CallRequest,"Call Request Clicked Successfully");
		}
		waitForPageLoaded();
		
		
		if (waitForElementPresent(OnlineOffLineCall_Locator.AccountlimitEnhancement,"AccountlimitEnhancement Link")) 
		{

			click(OnlineOffLineCall_Locator.AccountlimitEnhancement,"AccountlimitEnhancement link");
			waitForPageLoaded();
		}
		
		String currentlimit= driver.findElement(By.id("txtCurrentLimit")).getAttribute("value");
		
		String expnum= currentlimit.replace(".00", "");
		
		Reporter.reportStep("Current limit is:"+expnum);
		
		int newnum= Integer.parseInt(expnum.trim());
		
		int newlimit=newnum+1;
		String formatted;
		try {
			
			formatted=String.valueOf(newlimit);
		
			waitForPageLoaded(); 
			js_type(CardCancellation_Locator.NewLimit,formatted , "New limit");
			
			data.put("NewLimit",formatted);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		
//NewLimit
		
		ImplicitWait();
		JSClick(CardCancellation_Locator.SendRequestButton_new, "Send Request button ");
		Thread.sleep(3000);
		waitForPageLoaded();
		Reporter.takescreenshot("Account_LimitEnhancement");
		waitForAjax(driver);
		String expectedresponse,actualresponse = null;
		
		String actualresponse1,actualresponse2,actualresponse3,actualresponse4;
		try {
		//	waitForElementPresent(By.id("ContentPlaceHolder1_pnlPopup2"), "Wait for Element Display");
			expectedresponse = "successfully Changed";
			Thread.sleep(3000);
			
			
			
			actualresponse = driver.findElement(By.id("ContentPlaceHolder1_lblDisplayStatus")).getText();
			Thread.sleep(2000);
			
			
			if (actualresponse.trim().contains(expectedresponse.trim()))    

			{
				Reporter.reportStep("Message from the application is :"+actualresponse);
				Reporter.SuccessReport("Account Limit Enhancement done successfully "," Passed  ");
				
				driver.findElement(By.id("ContentPlaceHolder1_btnHide")).click();
				
			} 
			else 
			{
				Reporter.reportStep("Message from the appliaction is :"+actualresponse);
				Reporter.failureReport("Account Limit Enhancement ", "Failed");
				
			}

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Reporter.takescreenshot("Account_Limit_Enhancement_failed");
		}
		
		result=AccountLimitEnhancementDB(data);
		
		return result;

	}
	
	

	public static boolean CustomerLimitEnhancementDB(Hashtable <String,String > data) throws Throwable

	{
		
		
		ResultSet rs,rs1;
		boolean result = false;
		String username;
		String password ;
		
		String url = configProps.getProperty("DBURL");
		//String mid= configProps.getProperty("MID");
		//String custID= data.get("CustomerID");
		String customerid= data.get("CustomerID");
		
	//SELECT * FROM fins_clientmaster WHERE CLI_CUST_ID ='3515'; 
		String query = "SELECT * FROM fins_clientmaster WHERE CLI_CUST_ID ='"+customerid+ "'"; 
		System.out.println("query is"+query);
				
		username =configProps.getProperty("DBUsername");
		password = configProps.getProperty("DBPassword");
		
		System.out.println("username is"+username);
		System.out.println("password is"+password);
		
		 String CLI_CREDIT_LIMIT  = null;
		 String CLI_CUST_ID= null;
		
		 
		rs =CallLogin.connectDB(url,username,password,query);
		
		
		 while(rs.next()){
			 
			 CLI_CREDIT_LIMIT = rs.getString("CLI_CREDIT_LIMIT");
			 CLI_CUST_ID = rs.getString("CLI_CUST_ID");
	     
		 }
		 
		 String limit= data.get("NewLimit");
		 
		 Reporter.reportStep("Customer Limit Enhancement database validations.");
		
		 if( CLI_CREDIT_LIMIT.contains(limit) ) {
			 Reporter.reportStep("CLI_CREDIT_LIMIT value from fins_clientmaster table is : "+CLI_CREDIT_LIMIT);
			 Reporter.reportStep("CLI_CUST_ID value from fins_clientmaster table is : "+CLI_CUST_ID);
			 Reporter.SuccessReport("Customer limit enhancement database validation", "Customer limit enhancement database validation succesful");
			 result=true;
			
		 }else {
			
			 Reporter.reportStep("CLI_CREDIT_LIMIT value from fins_clientmaster table is : "+CLI_CREDIT_LIMIT);
			 Reporter.reportStep("CLI_CUST_ID value from fins_clientmaster table is : "+CLI_CUST_ID);
			 Reporter.failureReport("Customer limit enhancement database validation", "Customer limit enhancement database validation failed.");
		 }
		 
		 
		return result;
		
	}
	
	
	public static boolean AccountLimitEnhancementDB(Hashtable <String,String > data) throws Throwable

	{
		
		
		ResultSet rs,rs1;
		boolean result = false;
		String username;
		String password ;
		
		String url = configProps.getProperty("DBURL");
		//String mid= configProps.getProperty("MID");
		//String custID= data.get("CustomerID");
		String AccountId= data.get("AccountId");
		
	//SELECT * FROM fins_clientmaster WHERE CLI_CUST_ID ='3515'; 
		String query = "SELECT * FROM fins_accountmaster WHERE ACC_ID ='"+AccountId+ "'"; 
		System.out.println("query is"+query);
				
		username =configProps.getProperty("DBUsername");
		password = configProps.getProperty("DBPassword");
		
		System.out.println("username is"+username);
		System.out.println("password is"+password);
		
		 String ACC_CREDIT_LIMIT   = null;
		 String ACC_ID= null;
		
		 
		rs =CallLogin.connectDB(url,username,password,query);
		
		
		 while(rs.next()){
			 
			 ACC_CREDIT_LIMIT  = rs.getString("ACC_CREDIT_LIMIT");
			 ACC_ID = rs.getString("ACC_ID");
	     
		 }
		 
		 String limit= data.get("NewLimit");
		 
		 Reporter.reportStep("Account Limit Enhancement database validations.");
		
		 if( ACC_CREDIT_LIMIT.contains(limit) ) {
			 Reporter.reportStep("ACC_CREDIT_LIMIT value from fins_accountmaster table is : "+ACC_CREDIT_LIMIT);
			 Reporter.reportStep("ACC_ID value from fins_accountmaster table is : "+ACC_ID);
			 Reporter.SuccessReport("Account limit enhancement database validation", "Account limit enhancement database validation succesful");
			 result=true;
			
		 }else {
			
			 Reporter.reportStep("ACC_ID value from fins_accountmaster table is : "+ACC_ID);
			 Reporter.reportStep("ACC_CREDIT_LIMIT value from fins_accountmaster table is : "+ACC_CREDIT_LIMIT);
			 Reporter.failureReport("Account limit enhancement database validation", "Account limit enhancement database validation failed.");
		 }
		 
		 
		return result;
		
	}
	
	
	

	
}