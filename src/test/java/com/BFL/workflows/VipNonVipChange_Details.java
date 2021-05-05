package com.BFL.workflows;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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


public class VipNonVipChange_Details extends ActionEngine {

	public String USERID;
	public String password;

	static Logger logger = Logger.getLogger(VipNonVipChange_Details.class.getName());

	public static boolean VIPto_NonVip_SMT_VIPChange(Hashtable <String,String > data) throws Throwable

	{
		boolean result = false;
		ImplicitWait();
		
		RaiseVIPNonVIPRequest(data);
		
		result= selectNonVIP(data);
		
		RaiseVIPNonVIPRequest(data);
		
		result= SelectSMTUpdate(data);
		
		RaiseVIPNonVIPRequest(data);
		
		result= SelectVIPUpdate(data);
		
		return result;
		
	}
	
	public static boolean NonVipto_VIP_SMT_NonVIPChange(Hashtable <String,String > data) throws Throwable

	{
		boolean result = false;
		ImplicitWait();
		
		RaiseVIPNonVIPRequest(data);
		
		result= SelectVIPUpdate(data);
		
		RaiseVIPNonVIPRequest(data);
		
		result= SelectSMTUpdate(data);
		
		RaiseVIPNonVIPRequest(data);
		
		result= selectNonVIP(data);
		
		return result;
		
	}
	
	
	public static boolean SMTto_VIP_NonVIP_SMTChange(Hashtable <String,String > data) throws Throwable

	{
		boolean result = false;
		ImplicitWait();
		
		RaiseVIPNonVIPRequest(data);
		
		result= SelectVIPUpdate(data);
		
		RaiseVIPNonVIPRequest(data);
		
		result= selectNonVIP(data);
		
		RaiseVIPNonVIPRequest(data);
		
		result= SelectSMTUpdate(data);
		
		return result;
		
	}
	
	
	
	
	
	public static void RaiseVIPNonVIPRequest(Hashtable <String,String > data) throws Throwable{
		
		HtmlReportSupport.reportStep("VIP NONVIP Change");
		waitForPageLoaded();
		driver.findElement(By.linkText("Profile View")).click();
		waitForPageLoaded();
		
		if (waitForElementPresent(MainpageLocator.OnlineRequest,"OnlineRequest Link Present")) {

			//click(MainpageLocator.CallRequest,"Call Request Clicked Successfully");
		}
		waitForPageLoaded();
		
		scrolltoElement(driver.findElement(OnlineOffLineCall_Locator.VipNopVIPChange));
		
		if (waitForElementPresent(OnlineOffLineCall_Locator.VipNopVIPChange,"VipNopVIP request Link Present")) 
		{

			click(OnlineOffLineCall_Locator.VipNopVIPChange,"VipNopVIP request Link Clicked Successfully");
			waitForPageLoaded();
		}
		
	
		
		// choose account 
		
			waitForElementPresent(CardCancellation_Locator.VipLevel, "Level");
			selectByVisibleText(CardCancellation_Locator.VipLevel,"Account","Level");
			waitForPageLoaded();
		
		
		
			waitForElementPresent(CardCancellation_Locator.AccountId, "AccountId");
			Thread.sleep(2000);
			selectByVisibleText(CardCancellation_Locator.AccountId,data.get("AccountId"),"AccountId");
			waitForPageLoaded();
		
		
		
			waitForElementPresent(CardCancellation_Locator.CardNumber_drp, "CardNumber");
			selectByVisibleText(CardCancellation_Locator.CardNumber_drp,data.get("CardNumber"),"CardNumber");
			waitForPageLoaded();
		
		
		
		
	}

	public static boolean CheckAccntNonVipDB(Hashtable <String,String > data) throws Throwable

	{
		
		
		ResultSet rs,rs1;
		boolean result = false;
		String username;
		String password ;
		
		String url = configProps.getProperty("DBURL");
		//String mid= configProps.getProperty("MID");
		//String custID= data.get("CustomerID");
		String customerid= data.get("CustomerID");
		
		String NewAdjAmt= data.get("NewTempAmt");
		
		String fromdate= data.get("start_date");
		String todate= data.get("end_date");
		
	
		String query = "SELECT * FROM fins_accountmaster WHERE ACC_CUST_ID ='"+customerid+ "'"; 
		System.out.println("query is"+query);
				
		username =configProps.getProperty("DBUsername");
		password = configProps.getProperty("DBPassword");
		
		System.out.println("username is"+username);
		System.out.println("password is"+password);
		
		 String ACC_VIP_NONVIP_FLAG = null;
		 String ACC_CUST_ID= null;
		 
		
		rs =CallLogin.connectDB(url,username,password,query);
		
		
		 while(rs.next()){
			 
			 ACC_VIP_NONVIP_FLAG = rs.getString("ACC_VIP_NONVIP_FLAG");
			 ACC_CUST_ID = rs.getString("ACC_CUST_ID");
			
		 }
		 
		 
		// temp limit logic needs to be validated
		   
		 if( ACC_VIP_NONVIP_FLAG.contains("N")) {
			 Reporter.reportStep("ACC_VIP_NONVIP_FLAG value from fins_accountmaster table is : "+ACC_VIP_NONVIP_FLAG);
			 Reporter.reportStep("ACC_CUST_ID value from fins_accountmaster table is : "+ACC_CUST_ID);
			
			 Reporter.SuccessReport("VIP_NonVIP validations", "NON VIP type database validations pass.");
			 result=true;
			
		 }else {
			 Reporter.reportStep("ACC_VIP_NONVIP_FLAG value from fins_accountmaster table is : "+ACC_VIP_NONVIP_FLAG);
			 Reporter.reportStep("ACC_CUST_ID value from fins_accountmaster table is : "+ACC_CUST_ID);
			
			 
			 Reporter.failureReport("VIP_NonVIP validations", "NON VIP type database validations failed.");
			 
		 }
		 
		 
		return result;
		
	}
	
	public static boolean CheckSMTDB(Hashtable <String,String > data) throws Throwable

	{
		
		
		ResultSet rs,rs1;
		boolean result = false;
		String username;
		String password ;
		
		String url = configProps.getProperty("DBURL");
		//String mid= configProps.getProperty("MID");
		//String custID= data.get("CustomerID");
		String customerid= data.get("CustomerID");
		
		String NewAdjAmt= data.get("NewTempAmt");
		
		String fromdate= data.get("start_date");
		String todate= data.get("end_date");
		
	
		String query = "SELECT * FROM fins_accountmaster WHERE ACC_CUST_ID ='"+customerid+ "'"; 
		System.out.println("query is"+query);
				
		username =configProps.getProperty("DBUsername");
		password = configProps.getProperty("DBPassword");
		
		System.out.println("username is"+username);
		System.out.println("password is"+password);
		
		 String ACC_VIP_NONVIP_FLAG = null;
		 String ACC_CUST_ID= null;
		 
		
		rs =CallLogin.connectDB(url,username,password,query);
		
		
		 while(rs.next()){
			 
			 ACC_VIP_NONVIP_FLAG = rs.getString("ACC_VIP_NONVIP_FLAG");
			 ACC_CUST_ID = rs.getString("ACC_CUST_ID");
			
		 }
		 
		 
		// temp limit logic needs to be validated
		   
		 if( ACC_VIP_NONVIP_FLAG.contains("S")) {
			 Reporter.reportStep("ACC_VIP_NONVIP_FLAG value from fins_accountmaster table is : "+ACC_VIP_NONVIP_FLAG);
			 Reporter.reportStep("ACC_CUST_ID value from fins_accountmaster table is : "+ACC_CUST_ID);
			
			 Reporter.SuccessReport("SMT update validations", "SMT update database validations pass.");
			 result=true;
			
		 }else {
			 Reporter.reportStep("ACC_VIP_NONVIP_FLAG value from fins_accountmaster table is : "+ACC_VIP_NONVIP_FLAG);
			 Reporter.reportStep("ACC_CUST_ID value from fins_accountmaster table is : "+ACC_CUST_ID);
			
			 
			 Reporter.failureReport("SMT update validations", "SMT update database validations failed.");
			 
		 }
		 
		 
		return result;
		
	}
	
	public static boolean CheckVIPDB(Hashtable <String,String > data) throws Throwable

	{
		
		
		ResultSet rs,rs1;
		boolean result = false;
		String username;
		String password ;
		
		String url = configProps.getProperty("DBURL");
		//String mid= configProps.getProperty("MID");
		//String custID= data.get("CustomerID");
		String customerid= data.get("CustomerID");
		
		String NewAdjAmt= data.get("NewTempAmt");
		
		
		String query = "SELECT * FROM fins_accountmaster WHERE ACC_CUST_ID ='"+customerid+ "'"; 
		System.out.println("query is"+query);
				
		username =configProps.getProperty("DBUsername");
		password = configProps.getProperty("DBPassword");
		
		System.out.println("username is"+username);
		System.out.println("password is"+password);
		
		 String ACC_VIP_NONVIP_FLAG = null;
		 String ACC_CUST_ID= null;
		 
		
		rs =CallLogin.connectDB(url,username,password,query);
		
		
		 while(rs.next()){
			 
			 ACC_VIP_NONVIP_FLAG = rs.getString("ACC_VIP_NONVIP_FLAG");
			 ACC_CUST_ID = rs.getString("ACC_CUST_ID");
			
		 }
		 
		 
		// temp limit logic needs to be validated
		   
		 if( ACC_VIP_NONVIP_FLAG.contains("V")) {
			 Reporter.reportStep("ACC_VIP_NONVIP_FLAG value from fins_accountmaster table is : "+ACC_VIP_NONVIP_FLAG);
			 Reporter.reportStep("ACC_CUST_ID value from fins_accountmaster table is : "+ACC_CUST_ID);
			
			 Reporter.SuccessReport("VIP update validations", "VIP update database validations pass.");
			 result=true;
			
		 }else {
			 Reporter.reportStep("ACC_VIP_NONVIP_FLAG value from fins_accountmaster table is : "+ACC_VIP_NONVIP_FLAG);
			 Reporter.reportStep("ACC_CUST_ID value from fins_accountmaster table is : "+ACC_CUST_ID);
			
			 
			 Reporter.failureReport("VIP update validations", "VIP update database validations failed.");
			 
		 }
		 
		 
		return result;
		
	}
	
	public static  void GetVipORNonVipDB(Hashtable <String,String > data) throws Throwable

	{
		
		
		ResultSet rs,rs1;
		boolean result = false;
		String username;
		String password ;
		
		String url = configProps.getProperty("DBURL");
		//String mid= configProps.getProperty("MID");
		//String custID= data.get("CustomerID");
		String customerid= data.get("CustomerID");
		
		
		
		
	
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
		 
		 
		
		
	}
	
	public static boolean selectNonVIP(Hashtable <String,String > data) throws Throwable{
		
		boolean result= false;
		
		Reporter.reportStep("Select Non VIP / Non SMT update");

		selectByVisibleText(CardCancellation_Locator.VipFlag,"Non VIP / Non SMT update","VIP Flag");
		waitForPageLoaded();
	
	ImplicitWait();
	JSClick(CardCancellation_Locator.SendRequestButton, "Send request button ");
	Thread.sleep(3000);
	
	Reporter.takescreenshot("Select_NonVIP");
	waitForPageLoaded();
	js_type(CardCancellation_Locator.EndDate, "31-10-2020", "End date");
	waitForPageLoaded();
	waitForAjax(driver);
	waitForPageLoaded();
	String expectedresponse;
	List<WebElement> actualresponse;
	
	String actText=null;
	
	String actualresponse1,actualresponse2,actualresponse3,actualresponse4;
	try {
	//	waitForElementPresent(By.id("ContentPlaceHolder1_pnlPopup2"), "Wait for Element Display");
		expectedresponse = "is successfully ";
		
		
	//	Thread.sleep(2000);
	//	Thread.sleep(5000);
		
		if(waitTillSpinnerDisable(driver,By.id("ContentPlaceHolder1_UpdateProgress1")))
		{
			actualresponse = driver.findElements(By.id("ContentPlaceHolder1_lblDisplayStatus"));
			//actText=actualresponse.get(2).getText();
			actText=actualresponse.get(1).getText();
		}
		
		
		if (actText.trim().contains(expectedresponse.trim()))    

		{
			result= true;
			Reporter.reportStep("Message from the application is :"+actText);
			Reporter.SuccessReport("NONVIP request raised successfully "," NONVIP request raised successfully  ");
			
			List<WebElement> btns=driver.findElements(By.id("ContentPlaceHolder1_btnHide"));
			//btns.get(2).click();
			btns.get(1).click();
		} 
		else 
		{
			Reporter.takescreenshot("NONVIP_failed");
			Reporter.reportStep("Message from the application is :"+actText);
			Reporter.failureReport("NONVIP request", "NONVIP request Failed");
			
		}

		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		Reporter.failureReport("VIP NONVIP request failed","VIP NONVIP request failed");
		Reporter.takescreenshot("VIP_NONVIP_failed");
	}
	
	result=CheckAccntNonVipDB(data);
	
	return result;
		
		
		
	}
	
public static boolean SelectSMTUpdate(Hashtable <String,String > data) throws Throwable{
		//SMT Update
		boolean result= false;
		Reporter.reportStep("Select SMT Update");
		waitForElementPresent(CardCancellation_Locator.VipFlag, "VIP Flag");
		selectByVisibleText(CardCancellation_Locator.VipFlag,"SMT Update","VIP Flag");
		waitForPageLoaded();
		js_type(CardCancellation_Locator.EndDate, "31-10-2020", "End date");
	ImplicitWait();
	JSClick(CardCancellation_Locator.SendRequestButton, "Send request button ");
	Thread.sleep(3000);
	
	Reporter.takescreenshot("SMTUpdate_Request_Change");
	waitForPageLoaded();
	waitForAjax(driver);
	String expectedresponse;
	List<WebElement> actualresponse;
	String actualresponse1,actualresponse2,actualresponse3,actualresponse4;
	
	String actText=null;
	try {
	//	waitForElementPresent(By.id("ContentPlaceHolder1_pnlPopup2"), "Wait for Element Display");
		expectedresponse = "is successfully ";
		
		
		//Thread.sleep(2000);
		//Thread.sleep(5000);
		
		if(waitTillSpinnerDisable(driver,By.id("ContentPlaceHolder1_UpdateProgress1")))
		{
			actualresponse = driver.findElements(By.id("ContentPlaceHolder1_lblDisplayStatus"));
			//actText=actualresponse.get(2).getText();
			actText=actualresponse.get(1).getText();
		}
		
		if (actText.trim().contains(expectedresponse.trim()))    

		{
			result= true;
			Reporter.reportStep("Message from the application is :"+actText);
			Reporter.SuccessReport("SMT update request raised successfully "," SMT update request raised successfully  ");
			
			List<WebElement> btns=driver.findElements(By.id("ContentPlaceHolder1_btnHide"));
			//btns.get(2).click();
			btns.get(1).click();
		} 
		else 
		{
			Reporter.takescreenshot("SMT_UPDATE_failed");
			Reporter.reportStep("Message from the application is :"+actText);
			Reporter.failureReport("SMT_UPDATE request", "SMT_UPDATE request Failed");
			
		}

		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		Reporter.failureReport("SMT_UPDATE request failed","SMT_UPDATE request failed");
		Reporter.takescreenshot("SMT_UPDATE_failed");
	}
	
	result=CheckSMTDB(data);
	
	return result;
		
		
		
	}

public static boolean SelectVIPUpdate(Hashtable <String,String > data) throws Throwable{
	//SMT Update
	boolean result= false;

	Reporter.reportStep("Select VIP update");
	waitForElementPresent(CardCancellation_Locator.VipFlag, "VIP Flag");
	selectByVisibleText(CardCancellation_Locator.VipFlag,"VIP Update","VIP Flag selected");
	waitForPageLoaded();
	js_type(CardCancellation_Locator.EndDate, "31-10-2020", "End date");

	ImplicitWait();
	JSClick(CardCancellation_Locator.SendRequestButton, "Send request button ");
	Thread.sleep(3000);
	
	Reporter.takescreenshot("VIP_Update_Request");
	waitForPageLoaded();
	waitForAjax(driver);
	String expectedresponse;
	List<WebElement> actualresponse;
	String actualresponse1,actualresponse2,actualresponse3,actualresponse4;
	String actText=null;
	
try {
//	waitForElementPresent(By.id("ContentPlaceHolder1_pnlPopup2"), "Wait for Element Display");
	expectedresponse = "is successfully ";
	
	
	//Thread.sleep(2000);
	//
	
	//Thread.sleep(5000);
		if(waitTillSpinnerDisable(driver,By.id("ContentPlaceHolder1_UpdateProgress1")))
		{
			actualresponse = driver.findElements(By.id("ContentPlaceHolder1_lblDisplayStatus"));
		
			//actText=actualresponse.get(2).getText();
			actText=actualresponse.get(1).getText();
		}
	
	
		if (actText.trim().contains(expectedresponse.trim()))    

		{
		result= true;
		Reporter.reportStep("Message from the application is :"+actText);
		Reporter.SuccessReport("VIP update request raised successfully "," VIP update request raised successfully  ");
		
		List<WebElement> btns=driver.findElements(By.id("ContentPlaceHolder1_btnHide"));
		//btns.get(2).click();
		btns.get(1).click();
		} 
		else 
		{
		Reporter.takescreenshot("VIP_UPDATE_failed");
		Reporter.reportStep("Message from the application is :"+actText);
		Reporter.failureReport("VIP_UPDATE request", "VIP_UPDATE request Failed");
		
		}

	
	} catch (Exception e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
	Reporter.failureReport("VIP_UPDATE request failed","SMT_UPDATE request failed");
	Reporter.takescreenshot("VIP_UPDATE_failed");
	}

		result=CheckVIPDB(data);

		return result;
	
	
	
}
	
	

	
}