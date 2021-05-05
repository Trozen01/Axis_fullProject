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


public class TemoraryLimitChange_Details extends ActionEngine {

	public String USERID;
	public String password;

	static Logger logger = Logger.getLogger(TemoraryLimitChange_Details.class.getName());

	public static boolean TempLimitChange(Hashtable <String,String > data) throws Throwable

	{
		boolean result = false;
		ImplicitWait();
		HtmlReportSupport.reportStep("Temporary Limit Change");
		if (waitForElementPresent(MainpageLocator.OnlineRequest,"OnlineRequest Link Present")) {

			//click(MainpageLocator.CallRequest,"Call Request Clicked Successfully");
		}
		waitForPageLoaded();
		
		scrolltoElement(driver.findElement(OnlineOffLineCall_Locator.TempLimitChange));
		
		if (waitForElementPresent(OnlineOffLineCall_Locator.TempLimitChange,"Temporary Limit Change Link Present")) 
		{

			click(OnlineOffLineCall_Locator.TempLimitChange,"Temporary Limit Change Link Clicked Successfully");
			waitForPageLoaded();
		}
		
		///String OTBL= driver.findElement(By.id("txtCurrentLimit")).getAttribute("value");
		String oldTempLimit= data.get("ACC_TEMP_LIMIT");
		
		
		int amt= Integer.parseInt(oldTempLimit.trim());
		
		//int randlimittemp= RandomTextUtils.getRandomNumberInRange(100, 300);
		int randlimittemp= 100;
		
		//String newTempLimit= Integer.toString(newexpaccadjlimit);
		
	//	int newamt= (amt/100)-100;
		int newamt= randlimittemp*100;
		
		// adding fix amount of 1000 to the 
		int newexpaccadjlimit= amt+newamt;
		
		String strnewamt= Integer.toString(newexpaccadjlimit);
		
		
		data.put("OldTempAmt",oldTempLimit);
		data.put("NewTempAmt",strnewamt);
		
		
		System.out.println("old temp limt is:"+oldTempLimit);
		System.out.println("new temp limit is:"+strnewamt);
		
		
		
		
			waitForElementPresent(CardCancellation_Locator.AccountId, "AccountId");
			selectByVisibleText(CardCancellation_Locator.AccountId,data.get("AccountId"),"AccountId");
			waitForPageLoaded();
		
			waitForPageLoaded();
			
			waitForElementPresent(CardCancellation_Locator.CardNumber_drp, "CardNumber");
			selectByVisibleText(CardCancellation_Locator.CardNumber_drp,data.get("CardNumber"),"CardNumber");
			waitForPageLoaded();
		
		
		
		waitForPageLoaded();
		selectByVisibleText(CardCancellation_Locator.ChannelType,data.get("ChannelType"),"ChannelType");

		waitForPageLoaded();
		String strTempLimit=strnewamt.substring(0, strnewamt.length() - 2);
		type(CardCancellation_Locator.NewAdjAmt,"100","New Temporary Limit");
		waitForPageLoaded();
		
		//type(CardCancellation_Locator.OffMobileNumber,OffMobileNo,"Office Mobile No");
		//waitForPageLoaded();
		//type(CardCancellation_Locator.OffEmail,OffEmail,"Office Email Id");
		waitForPageLoaded();
		selectByVisibleText(CardCancellation_Locator.ChannelType,data.get("ChannelType"),"ChannelType");
		
		//DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        
        Date currentDate = new Date();
        System.out.println(dateFormat.format(currentDate));
        String fromdate = dateFormat.format(currentDate);

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
        
        data.put("start_date", dateFormat.format(currentDate));
        data.put("end_date", dateFormat.format(currentDatePlusOne));
        
        
        js_type(CardCancellation_Locator.TempLimitStartDate, dateFormat.format(currentDate), "Temporary Limit Start Date");
        
        js_type(CardCancellation_Locator.TempLimitEndDate, dateFormat.format(currentDatePlusOne), "Temporary Limit End Date");
		
		
		ImplicitWait();
		JSClick(CardCancellation_Locator.SendRequestButton_new, "Send request button ");
		Thread.sleep(3000);
		
		Reporter.takescreenshot("Temporary_Limit_Change");
		waitForPageLoaded();
		waitForAjax(driver);
		String expectedresponse;
		List<WebElement> actualresponse;
		String actualresponse1,actualresponse2,actualresponse3,actualresponse4;
		
		String actText=null;
		
		try {
		//	waitForElementPresent(By.id("ContentPlaceHolder1_pnlPopup2"), "Wait for Element Display");
			expectedresponse = "Temporary limit has been changed";
			
			
		//	Thread.sleep(2000);
			//Thread.sleep(5000);
			if(waitTillSpinnerDisable(driver,By.id("ContentPlaceHolder1_UpdateProgress1")))
			{
				actualresponse = driver.findElements(By.id("ContentPlaceHolder1_lblPopUp"));
				actText=actualresponse.get(3).getText();
			}
			
			
			if (actText.trim().contains(expectedresponse.trim()))    

			{
				result= true;
				
				Reporter.reportStep("Message from the application is :"+actText);
				Reporter.SuccessReport("Temporary limit changed successfully "," Passed  ");
				List<WebElement> btns=driver.findElements(By.id("ContentPlaceHolder1_btnHide2"));
				btns.get(3).click();
			} 
			else 
			{
				Reporter.reportStep("Message from the application is :"+actText);
				Reporter.failureReport("Temporary Limit change", "Temporary Limit change Failed");
				
			}

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Reporter.failureReport("Temporary_Limit_change_failed", "Temporary_Limit_change_failed");
			Reporter.takescreenshot("Temporary_Limit_change_failed");
		}
		
		result=TempLimitCheckDB(data);
		
		return result;

	}
	
	

	public static boolean TempLimitCheckDB(Hashtable <String,String > data) throws Throwable

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
		String OldAdjAmt= data.get("OldTempAmt");
		
		String fromdate= data.get("start_date");
		String todate= data.get("end_date");
		
	
		String query = "SELECT * FROM fins_accountmaster WHERE ACC_CUST_ID ='"+customerid+ "'"; 
		System.out.println("query is"+query);
				
		username =configProps.getProperty("DBUsername");
		password = configProps.getProperty("DBPassword");
		
		System.out.println("username is"+username);
		System.out.println("password is"+password);
		
		 String ACC_CREDIT_LIMIT = null;
		 String ACC_TEMP_LIMIT= null;
		 
		 String ACC_TEMP_LIMIT_START_DT = null;
		 String ACC_TEMP_LIMIT_END_DT = null;  
		
		
		 
		rs =CallLogin.connectDB(url,username,password,query);
		
		
		 while(rs.next()){
			 
			 ACC_CREDIT_LIMIT = rs.getString("ACC_CREDIT_LIMIT");
			 ACC_TEMP_LIMIT = rs.getString("ACC_TEMP_LIMIT");
			 ACC_TEMP_LIMIT_START_DT = rs.getString("ACC_TEMP_LIMIT_START_DT");
			 ACC_TEMP_LIMIT_END_DT = rs.getString("ACC_TEMP_LIMIT_END_DT");
			 
		 }
		 
		 
		 String sDate1=ACC_TEMP_LIMIT_START_DT;  
		 String sDate2 = ACC_TEMP_LIMIT_END_DT;  
		 
		 System.out.println("date1 is:"+sDate1);
		 System.out.println("date2 is:"+sDate2);
		 
		 String strTempLimit1=ACC_TEMP_LIMIT_START_DT.substring(0, ACC_TEMP_LIMIT_START_DT.length() - 2);
		 String strTempLimit2=ACC_TEMP_LIMIT_END_DT.substring(0, ACC_TEMP_LIMIT_END_DT.length() - 2);
		 
		 String [] datefrom = strTempLimit1.split(" ");
		 String [] dateTo = strTempLimit2.split(" ");
		 
		 String tmp=datefrom[0].toString();
		 String tmp1=dateTo[0].toString();
		 
		 String [] mmddyy= tmp.split("-");
		 String [] mmddyy2= tmp1.split("-");
		 
		 String Startyear= mmddyy[0].toString();
		 String Startmonth= mmddyy[1].toString();
		 String Startdate= mmddyy[2].toString();
		 
		 
		 String Endyear= mmddyy2[0].toString();
		 String Endmonth= mmddyy2[1].toString();
		 String Enddate= mmddyy2[2].toString();
		 
		 String finaldate=Startdate+"/"+Startmonth+"/"+Startyear;
		 String finaltodate=Enddate+"/"+Endmonth+"/"+Endyear;
		 
		 
		// String [] splitdate=datefrom(0).        .split("-");
		
		
		// temp limit logic needs to be validated
		   
		 if( finaldate.contains(fromdate) && finaltodate.contains(todate)
				 && ACC_TEMP_LIMIT.equals(NewAdjAmt)) {
			 Reporter.reportStep("ACC_CREDIT_LIMIT value from fins_accountmaster table is : "+ACC_CREDIT_LIMIT);
			 Reporter.reportStep("Old ACC_TEMP_LIMIT value from fins_accountmaster table was : "+OldAdjAmt);
			 Reporter.reportStep("ACC_TEMP_LIMIT value from fins_accountmaster table is : "+ACC_TEMP_LIMIT);
			
			 Reporter.reportStep("ACC_TEMP_LIMIT_START_DT value from fins_accountmaster table is : "+ACC_TEMP_LIMIT_START_DT);
			 Reporter.reportStep("ACC_TEMP_LIMIT_END_DT value from fins_accountmaster table is : "+ACC_TEMP_LIMIT_END_DT);
			
			 Reporter.SuccessReport("Temporary Limit Change database validations", "Temporary Limit Change datbase validations succesful");
			 result=true;
			
		 }else {
			 
			 Reporter.reportStep("Old ACC_TEMP_LIMIT value from fins_accountmaster table was : "+OldAdjAmt);
			 Reporter.reportStep("ACC_CREDIT_LIMIT value from fins_accountmaster table is : "+ACC_CREDIT_LIMIT);
			 Reporter.reportStep("ACC_TEMP_LIMIT value from fins_accountmaster table is : "+ACC_TEMP_LIMIT);
			
			 Reporter.reportStep("ACC_TEMP_LIMIT_START_DT value from fins_accountmaster table is : "+ACC_TEMP_LIMIT_START_DT);
			 Reporter.reportStep("ACC_TEMP_LIMIT_END_DT value from fins_accountmaster table is : "+ACC_TEMP_LIMIT_END_DT);
			 Reporter.failureReport("Temporary Limit Change database validations", "Temporary Limit Change datbase validations failed.");
			 
		 }
		 
		 
		return result;
		
	}
	
	
	public static  void GetTempLimitDB(Hashtable <String,String > data) throws Throwable

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
		
		 String ACC_TEMP_LIMIT = null;
		 
		 String ACC_CREDIT_LIMIT=null;
		
		
		 
		 
		rs =CallLogin.connectDB(url,username,password,query);
		
		
		 while(rs.next()){
			 
			 ACC_TEMP_LIMIT = rs.getString("ACC_TEMP_LIMIT");
			 ACC_CREDIT_LIMIT = rs.getString("ACC_CREDIT_LIMIT");
			System.out.println("temp limitis:"+ACC_TEMP_LIMIT);
			System.out.println("credit limitis:"+ACC_CREDIT_LIMIT);
			 
		 }
		 
		 
		 try {
			data.put("ACC_TEMP_LIMIT", ACC_TEMP_LIMIT);
			data.put("ACC_CREDIT_LIMIT", ACC_CREDIT_LIMIT);
			 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

	
}