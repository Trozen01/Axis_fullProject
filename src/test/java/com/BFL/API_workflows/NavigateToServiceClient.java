package com.BFL.API_workflows;

import java.io.FileInputStream;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.UnhandledAlertException;
import org.testng.Assert;

import com.BFL.testObjects.CallLogin_Locator;
import com.BFL.testObjects.MainpageLocator;
import com.BFL.testObjects.OnlineOffLineCall_Locator;
import com.BFL.workflows.CallLogin;
import com.MainFrameWork.accelerators.ActionEngine;
import com.MainFrameWork.support.HtmlReportSupport;
import com.MainFrameWork.utilities.Reporter;
import com.OneView.utils.DataUtil;
import com.OneView.utils.RandomTextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.thoughtworks.selenium.webdriven.commands.WaitForPageToLoad;



public class NavigateToServiceClient extends ActionEngine {

	public String USERID;
	public String password;

	
	static Logger logger = Logger.getLogger(NavigateToServiceClient.class.getName());

	public static void ServiceClient(Hashtable <String,String > data) throws Throwable

	{
		boolean result = false;
		HtmlReportSupport.reportStep("Navigate_To_Service_Client_Page");
		ImplicitWait();

		waitForPageLoaded();
		if (waitForElementPresent(MainpageLocator.MasterSetup,"MasterSetup Link Present")) {
         
			click(MainpageLocator.MasterSetup,"MasterSetup Clicked Successfully");
		}
		
		
		if (waitForElementPresent(MainpageLocator.BlockCategory,"MasterSetup Present")) {
			scrolltoElement(driver.findElement(OnlineOffLineCall_Locator.ServiceClient));
			Thread.sleep(2000);
		}
		
		if (waitForElementPresent(OnlineOffLineCall_Locator.ServiceClient,"ServiceClient Link Present")) 
		{

			click(OnlineOffLineCall_Locator.ServiceClient,"ServiceClient Link Clicked Successfully");
			waitForPageLoaded();
		}
		
		waitForPageLoaded();
		//Thread.sleep(5000);
		// temp code 
		if(waitTillSpinnerDisable(driver,By.id("ContentPlaceHolder1_UpdateProgress1")))
		{
			waitForElementPresent(CallLogin_Locator.SubmitButton,"Service client link present");
		}
		
		//put transaction type
		
		if (data.get("TransactionType") != "" && data.get("TransactionType")!= null) 
		{
			waitForElementPresent(CallLogin_Locator.Txntype,"Transaction type field present");
			 
			selectByVisibleText(CallLogin_Locator.Txntype, data.get("TransactionType"), "Transaction Type");
			waitForAjax(driver);
			waitForPageLoaded();
			
		}
		
		//without encryption   WithoutEncryption
		
		
		
		
		if (data.get("WithoutEncryption") != "" && data.get("WithoutEncryption")!= null) 
		{
			waitForAjax(driver);
			waitForElementPresent(CallLogin_Locator.WithoutEncryption,"WithoutEncryption field present");
			 String  num= data.get("WithoutEncryption");
			
			 if(num.equalsIgnoreCase("Yes")) {
				 click(CallLogin_Locator.WithoutEncryption, "WithoutEncryption");
				 
			 }
			
		}
		// AES or DES
		
		if (data.get("EncryptionType") != "" && data.get("EncryptionType")!= null) {
			String  EncryptionType= data.get("EncryptionType");
			waitForAjax(driver);
			waitForElementPresent(CallLogin_Locator.WithoutEncryption,"WithoutEncryption field present");
			if(EncryptionType.equalsIgnoreCase("AES")) {
				driver.findElement(By.id("ContentPlaceHolder1_rdoAES")).click();
			}else if (EncryptionType.equalsIgnoreCase("DES")) {
				driver.findElement(By.id("ContentPlaceHolder1_rdoDES")).click();
			}

		}
		
		
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

public static boolean CreateMerchantOnboardingJSON(Hashtable <String,String > data) throws Throwable{
	
	boolean result= false;
	
	String reqType= data.get("TransactionType");
	
	String path= System.getProperty("user.dir");
	
	String from2= path+"\\src\\test\\java\\com\\BFL\\data\\CustomerOnboarding.json";
	
	
        
        // generate card number & RRN number and check if not present in db
        String EMIcardno= getEMINumber();
        
        data.put("EMIcardno", EMIcardno);
        
        // check card number not in db
        Boolean status= GetEmiCardNuminDB(data);
        
        if(status) {
        	
        	EMIcardno= getEMINumber();
        	data.put("EMIcardno", EMIcardno);
        }
        
        String temprrn=configProps.getProperty("APICounter");
        
        String RRN=countRRNnumber(temprrn);
        System.out.println("New RRN is :"+RRN);
        Reporter.reportStep("New RRN is :"+RRN);
        
        String newemi=data.get("EMIcardno");
        
        
       // rohan code
        
        JSONParser jsonParser = new JSONParser();
   	 	String tmp=null;
   	 
   	
   	  try (FileReader reader = new FileReader(from2))
           {
               //Read JSON file
               Object obj = jsonParser.parse(reader);
               
               tmp= obj.toString();
               System.out.println(tmp);
               
           }catch(Exception e) {
           	System.out.println("JSON read error");
           }
   	
   	
//   	JSONObject obj= new JSONObject(JSON_STRING);
   	JSONObject obj= new JSONObject(tmp);
   	
   	JSONArray arr = obj.getJSONArray("CARDDATA");
   	JSONArray arr1 = obj.getJSONArray("ACCOUNTDATA");
   	JSONArray arr2 = obj.getJSONArray("CUSTDATA");
   	
   	
   	//System.out.println(arr);
   	//System.out.println("accnt data obj is:"+arr1);
   	
   	//System.out.println(arr.length());
   	
   	String rrn= (String) obj.get("RRN");
   	
   	
   		obj.put("RRN", RRN);
   	//	jsonObj.put("RRN", RRN);
   		data.put("RRN", RRN);
   		System.out.println("New rrn is :"+RRN);
   	
   	// CARDDATA
   		
   	JSONObject cardnum= arr.getJSONObject(0);
   	
   	String cardnumwa=cardnum.getString("EMI_CARD_NO");     
   	
   	String nameoncard=cardnum.getString("NAME_ON_CARD"); 
   	 
   	System.out.println("card num is "+cardnumwa);
   	System.out.println("original name on card is "+nameoncard);
   	
   	 nameoncard=RandomTextUtils.getRandomText(8);
   	
   	cardnum.put("EMI_CARD_NO", newemi);
   	cardnum.put("NAME_ON_CARD", nameoncard);
   	
   	data.put("EMI_CARD_NO", newemi);
   	data.put("NAME_ON_CARD", nameoncard);
   	
   	Reporter.reportStep("EMI_CARD_NO is :"+newemi);
	Reporter.reportStep("NAME_ON_CARD is :"+nameoncard);
	
   	System.out.println("EMI_CARD_NO is :"+newemi);
	System.out.println("NAME_ON_CARD is :"+nameoncard);
   	//CUSTDATA
   	
	JSONObject firstnamerow= arr2.getJSONObject(0);
   	
   	String firstname=firstnamerow.getString("FIRST_NAME");  
	String custId=firstnamerow.getString("CUSTOMER_ID");
   	
   	System.out.println("orig firstname is "+firstname);
   	System.out.println("orig custId is "+custId);
   	
   	// random name
   	String randomname=RandomTextUtils.getRandomText(5);
   	
   	firstnamerow.put("FIRST_NAME", randomname);
   	
    data.put("FIRST_NAME", randomname);
    data.put("CUSTOMER_ID", custId);
    
	
	Reporter.reportStep("CUSTOMER_ID is :"+custId);
	
   
	System.out.println("CUSTOMER_ID is :"+custId);
    
    // ACCOUNTDATA
    
    int limit= RandomTextUtils.getRandomNumberInRange(150000, 200000);
    
    String newlimit=  Integer.toString(limit) ;
    
    JSONObject acccreditLimit= arr1.getJSONObject(0);
   	
   	String creditlimit=acccreditLimit.getString("ACCOUNT_CREDIT_LIMIT");
   	
	String accountid=acccreditLimit.getString("ACCOUNT_ID");
   	
   	System.out.println("original ACCOUNT_CREDIT_LIMIT is "+creditlimit);
   	
   	acccreditLimit.put("ACCOUNT_CREDIT_LIMIT", newlimit);
   	
   //JSONObject rrnstr= arr1.getJSONObject(0);
    
   	data.put("ACCOUNT_CREDIT_LIMIT", newlimit);
   	data.put("ACCOUNT_ID", accountid);
   
   	
	Reporter.reportStep("ACCOUNT_ID is :"+accountid);
	
  
	System.out.println("ACCOUNT_ID is :"+accountid);
   	
    
   
   		String NewJson =obj.toString();
   		
   		System.out.println("New Json is:"+NewJson);
   
        data.put("NewJSON", NewJson);
        
        waitForPageLoaded();
        
        waitForElementPresent(CallLogin_Locator.ClearJSON,"Clear JSON");
        
        System.out.println("New Json is:"+NewJson);
        
       //ype(CallLogin_Locator.ClearJSON,NewJson,"Clear JSON");
        waitForPageLoaded();
        js_type(CallLogin_Locator.ClearJSON,NewJson,"Clear JSON");
        
        
        
        
        Thread.sleep(2000);
        waitForPageLoaded();
        waitForElementPresent(CallLogin_Locator.SubmitButton,"Clear JSON");
        click(CallLogin_Locator.SubmitButton, "Submit button");
        
        Thread.sleep(2000);
        waitForPageLoaded();
	
        String posturl= data.get("PostURL");
        waitForPageLoaded();
        waitForElementPresent(CallLogin_Locator.PostUrl,"Post URL");
        type(CallLogin_Locator.PostUrl, posturl, "PostUrl");
        waitForPageLoaded();
        waitForElementPresent(CallLogin_Locator.ApiSubmit,"Submit button");
        click(CallLogin_Locator.ApiSubmit,"Submit button");
        
        String resp=null;
        
        waitForPageLoaded();
        
        if(waitTillSpinnerDisable(driver,By.id("ContentPlaceHolder1_UpdateProgress1")))
		{
			waitForElementPresent(CallLogin_Locator.Response,"Response field present");
			
			 resp= driver.findElement(By.id("ContentPlaceHolder1_txtResponse")).getText();
		} 
        
        String array1[]= resp.split(",");
        
        String RESPCODE= array1[2];
        String RESPDESC=array1[3];
        String opRRN=array1[4];
        
        Reporter.reportStep("Message from the application is :"+resp);
        
        scrollTillPageEnd();
        
        Thread.sleep(2000);
        
        Reporter.takescreenshot("Customer_Onboarding_api");
        
        if(RESPCODE.contains("00") && RESPDESC.contains("SUCCESS")) {
        	Reporter.SuccessReport("Customer Onboarding", "Customer succesfully created.");
        	result= true;
        	
        }else {
        	Reporter.failureReport("Customer Onboarding", "Customer onboarding failed.");
        }
	
        
	
	return result;
	
}

static String strFilePath="D:\\Devops\\config.properties";

//static String strFilePath=configProps.getProperty("LocalConfig");

public static String getEMINumber() throws Throwable {
	
	String genNumber=null;
	
	//String tempnum=configProps.getProperty("APICardNumber");
	
	
	
	//
	String tempnum=configProps.getPropertyNew("APICardNumber", strFilePath);
	
	long temp = Long.parseLong(tempnum)+1;
	
	 genNumber= Long.toString(temp);
	 
	 configProps.setPropertyNew("APICardNumber", genNumber, strFilePath);
	 
	
	// TODO Auto-generated method stub
	return genNumber;
}


public static  Boolean GetEmiCardNuminDB(Hashtable <String,String > data) throws Throwable

{
	
	
	ResultSet rs,rs1;
	boolean result = false;
	String username;
	String password ;
	
	String url = configProps.getProperty("DBURL");
	//String mid= configProps.getProperty("MID");
	//String custID= data.get("CustomerID");
	String EMIcardno= data.get("EMIcardno");
	
	//SELECT * FROM Fins_cardmaster WHERE CRD_NO='2030403330000002';
	String query = "SELECT * FROM Fins_cardmaster WHERE CRD_NO ='"+EMIcardno+ "'"; 
	System.out.println("query is"+query);
			
	username =configProps.getProperty("DBUsername");
	password = configProps.getProperty("DBPassword");
	
	System.out.println("username is"+username);
	System.out.println("password is"+password);
	
	 String CRD_NO = null;
	 
	
	rs =CallLogin.connectDB(url,username,password,query);
	
	
	 try {
		while(rs.next()){
			 
			CRD_NO = rs.getString("CRD_NO");
			
			
			
			 
		 }
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 
	 if(CRD_NO!= null) {
		 System.out.println("Card already present in database, Card number is:"+CRD_NO);
		
		 result=true;
	 }else {
		 System.out.println("Card not present in database");
	 }
	 
	 
	return result;
	
}

public static String countRRNnumber(String APICounter) throws Throwable {
    
    int counter=Integer.parseInt(APICounter);
          
    int newvalue=counter +1;
         //  configProps.setProperty("APICounter", (Integer.toString(newvalue)));
           configProps.setPropertyNew("APICounter", (Integer.toString(newvalue)),strFilePath);
           System.out.println("automation"+(Integer.toString(counter)));
           //RRN Logic has changed, now accepting 12 digit number
           //String rrn= "automation"+(Integer.toString(counter));
           String rrn= "bflafl"+(Integer.toString(counter));
           
    return rrn;
    
    
}

}
