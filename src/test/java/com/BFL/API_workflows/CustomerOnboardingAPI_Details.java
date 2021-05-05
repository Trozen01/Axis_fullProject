package com.BFL.API_workflows;

import java.io.FileReader;
import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.BFL.testObjects.CallLogin_Locator;
import com.BFL.testObjects.CardCancellation_Locator;
import com.BFL.testObjects.MainpageLocator;
import com.BFL.testObjects.OnlineOffLineCall_Locator;
import com.MainFrameWork.accelerators.ActionEngine;
import com.MainFrameWork.support.HtmlReportSupport;
import com.MainFrameWork.utilities.Reporter;
import com.OneView.utils.RandomTextUtils;


public class CustomerOnboardingAPI_Details extends ActionEngine {

	public String USERID;
	public String password;

	static Logger logger = Logger.getLogger(CustomerOnboardingAPI_Details.class.getName());

	public static boolean OnBoardCustomer(Hashtable <String,String > data) throws Throwable

	{
		boolean result = false;
		ImplicitWait();
		HtmlReportSupport.reportStep("Enter Customer Onboarding JSON");
		if (waitForElementPresent(MainpageLocator.EnterJSON,"OnlineRequest Link Present")) {

			//click(MainpageLocator.CallRequest,"Call Request Clicked Successfully");
		}
		waitForPageLoaded();
		
		
		NavigateToServiceClient.CreateMerchantOnboardingJSON(data);
		
		
		Reporter.takescreenshot("Customer_Onboarding");
		
	
		//result=AccountCancellationDB(data);
		
		return result;

	}
	
	

	
	
	
	public static boolean CustOnboardingDB(Hashtable <String,String > data) throws Throwable

	{
		
		
		ResultSet rs,rs1,rs2;
		boolean result = false;
		String username;
		String password ;
		
		String url = configProps.getProperty("DBURL");
		//String mid= configProps.getProperty("MID");
		
		String emicard= data.get("EMIcardno");
		String nameoncard= data.get("NAME_ON_CARD");
		
		
		String custid= data.get("CUSTOMER_ID");
		String accountid= data.get("ACCOUNT_ID");
		
		String firstname= data.get("FIRST_NAME");
		String accntCredLimit= data.get("ACCOUNT_CREDIT_LIMIT");
		
		
	//select * from fins_accountmaster where ACC_CUST_ID='435';
		
		//select * from Fins_cardmaster WHERE CRD_NO='2030403330000009'; 
		String query = "SELECT * FROM Fins_cardmaster WHERE CRD_NO ='"+emicard+ "'"; 
		System.out.println("query is"+query);
				
		username =configProps.getProperty("DBUsername");
		password = configProps.getProperty("DBPassword");
		
		System.out.println("username is"+username);
		System.out.println("password is"+password);
		
		 String CRD_NO = null;
		 String CRD_CUST_ID= null;
		 String CRD_ACTIVATION_DT = null;
		 String CRD_NAME_ON_CARD = null;  
		 String CLI_CUST_ID = null;
		 
		 
		
		 
		rs =NavigateToServiceClient.connectDB(url,username,password,query);
		
		
		 while(rs.next()){
			 
			 CRD_NO = rs.getString("CRD_NO");
			 CRD_CUST_ID = rs.getString("CRD_CUST_ID");
			 CRD_ACTIVATION_DT  = rs.getString("CRD_ACTIVATION_DT");  
			 CRD_NAME_ON_CARD  = rs.getString("CRD_NAME_ON_CARD"); 				
	      // test
	     
		 }
		 
		 
		
		 if(CRD_NO!= null && CRD_NO.contains(emicard) && CRD_CUST_ID.contains(custid)
				 && CRD_NAME_ON_CARD.contains(nameoncard)) {
			 
			 
			 Reporter.reportStep("CRD_ACTIVATION_DT value is :"+CRD_ACTIVATION_DT);
			 Reporter.reportStep("CRD_NO value from Fins_cardmaster table is : "+CRD_NO);
			 Reporter.reportStep("CRD_CUST_ID value from Fins_cardmaster table is : "+CRD_CUST_ID);
			 Reporter.reportStep("CRD_NAME_ON_CARD value from Fins_cardmaster table is : "+CRD_NAME_ON_CARD);
			 Reporter.SuccessReport("Customer Onboarding Fins_cardmaster table validations","Customer Onboarding Fins_cardmaster table validation pass.");
			 result=true;
		 }else {
			 Reporter.reportStep("CRD_ACTIVATION_DT value is :"+CRD_ACTIVATION_DT);
			 Reporter.reportStep("CRD_NO value from Fins_cardmaster table is : "+CRD_NO);
			 Reporter.reportStep("CRD_CUST_ID value from Fins_cardmaster table is : "+CRD_CUST_ID);
			 Reporter.reportStep("CRD_NAME_ON_CARD value from Fins_cardmaster table is : "+CRD_NAME_ON_CARD);
			 Reporter.failureReport("Customer Onboarding validations","Customer Onboarding Database validation fail.");
			 result=false;
		 }
		 
		 
		 
		 
		 //db new pending
		 //select * from fins_clientmaster where CLI_CUST_ID='5002';
		 String query1 = "SELECT * FROM fins_clientmaster WHERE CLI_CUST_ID ='"+custid+ "'"; 
		 System.out.println("query is"+query1);
					
			
			
			 String CLI_FIRST_NAME= null;
			// String CRD_ACTIVATION_DT = null;
			
			 
			rs1 =NavigateToServiceClient.connectDB(url,username,password,query1);
			
			
			 while(rs1.next()){
				 
				 CLI_CUST_ID = rs1.getString("CLI_CUST_ID");
				 CLI_FIRST_NAME = rs1.getString("CLI_FIRST_NAME");
				// CRD_ACTIVATION_DT  = rs.getString("CRD_ACTIVATION_DT");
		      // test
		     
			 }
			 
			 
			
			 if(CLI_CUST_ID!= null && CLI_CUST_ID.contains(custid)) {
				 
				 
				 Reporter.reportStep("CLI_CUST_ID value from fins_clientmaster table is : "+CLI_CUST_ID);
				 //Reporter.reportStep("CLI_FIRST_NAME value from fins_clientmaster table is : "+CLI_FIRST_NAME);
				 Reporter.SuccessReport("Customer Onboarding fins_clientmaster table validations","Customer Onboarding fins_clientmaster table validation pass.");
				 result=true;
			 }else {
				 Reporter.reportStep("CLI_CUST_ID value from fins_clientmaster table is : "+CLI_CUST_ID);
				// Reporter.reportStep("CLI_FIRST_NAME value from fins_clientmaster table is : "+CLI_FIRST_NAME);
				 Reporter.failureReport("Customer Onboarding fins_clientmaster table validations","Customer Onboarding fins_clientmaster table validation fail.");
				 result=false;
			 }
		 
		 
		 
		 
			 
			 //
			 //select * from  fins_accountmaster where ACC_CUST_ID='5002';
			 String query2 = "SELECT * FROM fins_accountmaster WHERE ACC_ID ='"+accountid+ "'"; 
				System.out.println("query is"+query2);
						
				
				
				 String ACC_ID = null;
				 String ACC_CREDIT_LIMIT= null;
				 String ACC_LAST_MODIFY_DT_WL = null;
				
				 
				rs2 =NavigateToServiceClient.connectDB(url,username,password,query2);
				
				
				 while(rs2.next()){
					 
					 ACC_ID = rs2.getString("ACC_ID");
					 ACC_CREDIT_LIMIT = rs2.getString("ACC_CREDIT_LIMIT");
					 ACC_LAST_MODIFY_DT_WL  = rs2.getString("ACC_LAST_MODIFY_DT_WL");
			      // test
			     
				 }
				
				 if(ACC_ID!= null && ACC_ID.contains(accountid)) {
					
					 Reporter.reportStep("ACC_ID value from fins_accountmaster table is : "+ACC_ID);
					// Reporter.reportStep("ACC_CREDIT_LIMIT value from fins_accountmaster table is : "+ACC_CREDIT_LIMIT);
					// Reporter.reportStep("ACC_LAST_MODIFY_DT_WL value from fins_accountmaster table is : "+ACC_LAST_MODIFY_DT_WL);
					 Reporter.SuccessReport("Customer Onboarding fins_accountmaster table validations","Customer Onboarding fins_accountmaster table validation pass.");
					 result=true;
				 }else {
					 Reporter.reportStep("ACC_ID value from fins_accountmaster table is : "+ACC_ID);
					// Reporter.reportStep("ACC_CREDIT_LIMIT value from fins_accountmaster table is : "+ACC_CREDIT_LIMIT);
					// Reporter.reportStep("ACC_LAST_MODIFY_DT_WL value from fins_accountmaster table is : "+ACC_LAST_MODIFY_DT_WL);
					 Reporter.failureReport("Customer Onboarding fins_accountmaster table validations","Customer Onboarding fins_accountmaster table validation fail.");
					 result=false;
				 }
			 
		 
		
		return result;
		
	}
	
	public static boolean CreateMerchantOnboardingJSON(Hashtable <String,String > data) throws Throwable{
		
		boolean result= false;
		
		String reqType= data.get("TransactionType");
		
		String path= System.getProperty("user.dir");
		
		String from2= path+"\\src\\test\\java\\com\\BFL\\data\\CustomerOnboarding.json";
		
	        // generate card number & RRN number and check if not present in db
	        String EMIcardno= NavigateToServiceClient.getEMINumber();
	        
	        data.put("EMIcardno", EMIcardno);
	        
	        // check card number not in db
	        Boolean status= NavigateToServiceClient.GetEmiCardNuminDB(data);
	        
	        if(status) {
	        	
	        	EMIcardno= NavigateToServiceClient.getEMINumber();
	        	data.put("EMIcardno", EMIcardno);
	        }
	        
	      //  String temprrn=configProps.getProperty("APICounter");
	        String temprrn=configProps.getPropertyNew("APICounter",NavigateToServiceClient.strFilePath);
	        
	        String RRN=NavigateToServiceClient.countRRNnumber(temprrn);
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
	   	
	   	
//	   	JSONObject obj= new JSONObject(JSON_STRING);
	   	JSONObject obj= new JSONObject(tmp);
	   	
	   	JSONArray arr = obj.getJSONArray("CARDDATA");
	   	JSONArray arr1 = obj.getJSONArray("ACCOUNTDATA");
	   	JSONArray arr2 = obj.getJSONArray("CUSTDATA");
	   	
	   
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
	        waitForElementPresent(CallLogin_Locator.SubmitButton,"Clear JSON");
	        click(CallLogin_Locator.SubmitButton, "Submit button");
	        
	        Thread.sleep(2000);
	        waitForPageLoaded();
		
	        String posturl= data.get("PostURL");
	        
	        waitForElementPresent(CallLogin_Locator.PostUrl,"Post URL");
	        type(CallLogin_Locator.PostUrl, posturl, "PostUrl");
	        
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
	

	
}