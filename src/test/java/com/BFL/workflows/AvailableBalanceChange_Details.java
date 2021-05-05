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


public class AvailableBalanceChange_Details extends ActionEngine {

	public String USERID;
	public String password;

	static Logger logger = Logger.getLogger(AvailableBalanceChange_Details.class.getName());

	public static boolean BalanceChange(Hashtable <String,String > data) throws Throwable

	{
		boolean result = false;
		ImplicitWait();
		HtmlReportSupport.reportStep("Available Balance Change");
		if (waitForElementPresent(MainpageLocator.OnlineRequest,"OnlineRequest Link Present")) {

			//click(MainpageLocator.CallRequest,"Call Request Clicked Successfully");
		}
		waitForPageLoaded();
		
		scrolltoElement(driver.findElement(OnlineOffLineCall_Locator.AvailableBalanceChange));
		
		if (waitForElementPresent(OnlineOffLineCall_Locator.AvailableBalanceChange,"AvailableBalanceChange Link Present")) 
		{

			click(OnlineOffLineCall_Locator.AvailableBalanceChange,"AvailableBalanceChange Clicked Successfully");
			waitForPageLoaded();
		}
		
		///String OTBL= driver.findElement(By.id("txtCurrentLimit")).getAttribute("value");
		String OTBL= data.get("ACC_CREDIT_LIMIT");
		String OldAdjLimit= data.get("ACC_ADJ_LIMIT");
		
		int amt= Integer.parseInt(OldAdjLimit.trim());
		
	//	int newamt= (amt/100)-100;
		int newamt= 100;
		
		// adding fix amount of 1000 to the 
		int newexpaccadjlimit= amt+10000;
		
		String strnewamt= Integer.toString(newexpaccadjlimit);
		
		
		
		data.put("NewAdjAmt",strnewamt);
		data.put("OldAdjAmt",OldAdjLimit);
		
		System.out.println("old adj value is:"+OldAdjLimit);
		System.out.println("new adj value is:"+strnewamt);
		
		
		
		
			waitForElementPresent(CardCancellation_Locator.AccountId, "AccountId");
			selectByVisibleText(CardCancellation_Locator.AccountId,data.get("AccountId"),"AccountId");
			waitForPageLoaded();
		
		
		
			waitForElementPresent(CardCancellation_Locator.CardNumber_drp, "CardNumber");
			selectByVisibleText(CardCancellation_Locator.CardNumber_drp,data.get("CardNumber"),"CardNumber");
			waitForPageLoaded();
		
		
		
		waitForPageLoaded();
		selectByVisibleText(CardCancellation_Locator.ChannelType,data.get("ChannelType"),"ChannelType");

		waitForPageLoaded();
		type(CardCancellation_Locator.NewAdjAmt,"100","New Adjustment Amount");
		waitForPageLoaded();
		
		//type(CardCancellation_Locator.OffMobileNumber,OffMobileNo,"Office Mobile No");
		//waitForPageLoaded();
		//type(CardCancellation_Locator.OffEmail,OffEmail,"Office Email Id");
		waitForPageLoaded();
		selectByVisibleText(CardCancellation_Locator.ChannelType,data.get("ChannelType"),"ChannelType");
		
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        
        Date currentDate = new Date();
        System.out.println(dateFormat.format(currentDate));

        // convert date to calendar
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);

        // manipulate date
       
        c.add(Calendar.MONTH, 1);
         //same with c.add(Calendar.DAY_OF_MONTH, 1);
        

        // convert calendar to date
        Date currentDatePlusOne = c.getTime();

        System.out.println(dateFormat.format(currentDatePlusOne));
        
		String processDevEndDate=currentDatePlusOne.toString();
				
				 
        
        System.out.println("processDevEndDate is: " + processDevEndDate); 
        waitForPageLoaded();
        waitForElementPresent(CardCancellation_Locator.ProcessDeviationEnddate, "Process Deviation End Date");
        
        js_type(CardCancellation_Locator.ProcessDeviationEnddate, dateFormat.format(currentDatePlusOne), "Process Deviation End Date");
		
		
		ImplicitWait();
		JSClick(CardCancellation_Locator.SendRequestButton_new, "Send request button ");
		Thread.sleep(2000);
		
		Reporter.takescreenshot("Available_Balance_Change");
		
		waitForPageLoaded();
		//waitForAjax(driver);
		String expectedresponse;
		List<WebElement> actualresponse;
		String actualresponse1,actualresponse2,actualresponse3,actualresponse4;
		try {
		//	waitForElementPresent(By.id("ContentPlaceHolder1_pnlPopup2"), "Wait for Element Display");
			expectedresponse = "has been Enhanced on";
			
			// added wait to handle spinner
			//Thread.sleep(2000);
			
		//	Thread.sleep(5000);
			String actText = null;
			
			if(waitTillSpinnerDisable(driver,By.id("ContentPlaceHolder1_UpdateProgress1")))
			{
				actualresponse = driver.findElements(By.id("ContentPlaceHolder1_lblPopUp"));
				 actText=actualresponse.get(3).getText();
			}
			
			
			
			
			if (actText.trim().contains(expectedresponse.trim()))    

			{
				result= true;
				Reporter.reportStep("Message from the application is :"+actText);
				Reporter.SuccessReport("Available Balance change request done successfully "," Available Balance change request done successfully  ");
				
				List<WebElement> btns=driver.findElements(By.id("ContentPlaceHolder1_btnHide2"));
				btns.get(3).click();
			} 
			else 
			{
				Reporter.reportStep("Message from the application is :"+actText);
				Reporter.failureReport("Available Balance change", "Available Balance change Failed");
				List<WebElement> btns=driver.findElements(By.id("ContentPlaceHolder1_btnHide2"));
				btns.get(3).click();
				
			}

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Reporter.failureReport("Available_Balance_change_failed", "Available_Balance_change_failed");
			Reporter.takescreenshot("Available_Balance_change_failed");
		}
		
		result=AvbBalanceCheckDB(data);
		
		return result;

	}
	
	

	public static boolean AvbBalanceCheckDB(Hashtable <String,String > data) throws Throwable

	{
		
		
		ResultSet rs,rs1;
		boolean result = false;
		String username;
		String password ;
		
		String url = configProps.getProperty("DBURL");
		//String mid= configProps.getProperty("MID");
		//String custID= data.get("CustomerID");
		String customerid= data.get("CustomerID");
		
		String NewAdjAmt= data.get("NewAdjAmt");
		String OldAdjAmt= data.get("OldAdjAmt");
		
		
	
		String query = "SELECT * FROM fins_accountmaster WHERE ACC_CUST_ID ='"+customerid+ "'"; 
		System.out.println("query is"+query);
				
		username =configProps.getProperty("DBUsername");
		password = configProps.getProperty("DBPassword");
		
		System.out.println("username is"+username);
		System.out.println("password is"+password);
		
		 String ACC_CREDIT_LIMIT = null;
		 String ACC_ADJ_LIMIT= null;
		 
		 String ACC_ADJ_LIMIT_DATE = null;
		 String ACC_PROC_DEV_END_DATE = null;  
		
		 
		 
		rs =CallLogin.connectDB(url,username,password,query);
		
		
		 while(rs.next()){
			 
			 ACC_CREDIT_LIMIT = rs.getString("ACC_CREDIT_LIMIT");
			 ACC_ADJ_LIMIT = rs.getString("ACC_ADJ_LIMIT");
			 ACC_ADJ_LIMIT_DATE = rs.getString("ACC_ADJ_LIMIT_DATE");
			 ACC_PROC_DEV_END_DATE = rs.getString("ACC_PROC_DEV_END_DATE");
			 
		 }
		 
		 // logic needs revision
		
		 if( ACC_ADJ_LIMIT.contains(NewAdjAmt)) {  
			 Reporter.reportStep("Old ACC_ADJ_LIMIT value from fins_accountmaster table before update was :"+OldAdjAmt);
			 Reporter.reportStep("ACC_ADJ_LIMIT value from fins_accountmaster table is : "+ACC_ADJ_LIMIT);
			 Reporter.reportStep("ACC_ADJ_LIMIT_DATE value from fins_accountmaster table is : "+ACC_ADJ_LIMIT_DATE);
			
			 Reporter.reportStep("ACC_PROC_DEV_END_DATE value from fins_accountmaster table is : "+ACC_PROC_DEV_END_DATE);
			 Reporter.reportStep("ACC_CREDIT_LIMIT value from fins_accountmaster table is : "+ACC_CREDIT_LIMIT);
			
			 Reporter.SuccessReport("Available balance change validations", "Available balance change  database validation succesful");
			 result=true;
			
		 }else {
			 Reporter.reportStep("Old ACC_ADJ_LIMIT value from fins_accountmaster table before update was :"+OldAdjAmt);
			
			 Reporter.reportStep("ACC_ADJ_LIMIT value from fins_accountmaster table is : "+ACC_ADJ_LIMIT);
			 Reporter.reportStep("ACC_ADJ_LIMIT_DATE value from fins_accountmaster table is : "+ACC_ADJ_LIMIT_DATE);
			
			 Reporter.reportStep("ACC_PROC_DEV_END_DATE value from fins_accountmaster table is : "+ACC_PROC_DEV_END_DATE);
			 Reporter.reportStep("ACC_CREDIT_LIMIT value from fins_accountmaster table is : "+ACC_CREDIT_LIMIT);
			 Reporter.failureReport("Available balance change validations", "Available balance change validations failed.");
			 
		 }
		 
		 
		return result;
		
	}
	
	
	public static  void GetLimitDB(Hashtable <String,String > data) throws Throwable

	{
		
		
		ResultSet rs,rs1;
		boolean result = false;
		String username;
		String password ;
		
		String url = configProps.getProperty("DBURL");
		//String mid= configProps.getProperty("MID");
		//String custID= data.get("CustomerID");
		String customerid= data.get("CustomerID");
		
		String NewAdjAmt= data.get("NewAdjAmt");
		
		
	
		String query = "SELECT * FROM fins_accountmaster WHERE ACC_CUST_ID ='"+customerid+ "'"; 
		System.out.println("query is"+query);
				
		username =configProps.getProperty("DBUsername");
		password = configProps.getProperty("DBPassword");
		
		System.out.println("username is"+username);
		System.out.println("password is"+password);
		
		 String ACC_CREDIT_LIMIT = null;
		 String ACC_ADJ_LIMIT= null;
		 
		 String ACC_ADJ_LIMIT_DATE = null;
		 String ACC_PROC_DEV_END_DATE = null;  
		
		 
		 
		rs =CallLogin.connectDB(url,username,password,query);
		
		
		 while(rs.next()){
			 
			 ACC_CREDIT_LIMIT = rs.getString("ACC_CREDIT_LIMIT");
			 ACC_ADJ_LIMIT = rs.getString("ACC_ADJ_LIMIT");
			 ACC_ADJ_LIMIT_DATE = rs.getString("ACC_ADJ_LIMIT_DATE");
			 ACC_PROC_DEV_END_DATE = rs.getString("ACC_PROC_DEV_END_DATE");
			 
		 }
		 
		 data.put("ACC_CREDIT_LIMIT", ACC_CREDIT_LIMIT);
		 data.put("ACC_ADJ_LIMIT", ACC_ADJ_LIMIT);
		
	}
	

	
}