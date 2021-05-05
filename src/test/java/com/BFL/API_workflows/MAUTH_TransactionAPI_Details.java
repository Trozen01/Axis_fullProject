package com.BFL.API_workflows;

import java.io.FileReader;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
import com.BFL.workflows.CallLogin;
import com.MainFrameWork.accelerators.ActionEngine;
import com.MainFrameWork.support.HtmlReportSupport;
import com.MainFrameWork.utilities.Reporter;
import com.OneView.utils.RandomTextUtils;

public class MAUTH_TransactionAPI_Details extends ActionEngine {

	public String USERID;
	public String password;

	static Logger logger = Logger.getLogger(MAUTH_TransactionAPI_Details.class.getName());

	public static boolean MauthCustomer(Hashtable<String, String> data) throws Throwable

	{
		boolean result = false;
		ImplicitWait();
		HtmlReportSupport.reportStep("Enter Customer Mauth JSON");
		if (waitForElementPresent(MainpageLocator.EnterJSON, "OnlineRequest Link Present")) {

			// click(MainpageLocator.CallRequest,"Call Request Clicked Successfully");
		}
		waitForPageLoaded();

		//NavigateToServiceClient.CreateMerchantOnboardingJSON(data);

		Reporter.takescreenshot("Mauth_Onboarding");

		// result=AccountCancellationDB(data);

		return result;

	}

	
	  public static boolean MauthOnboardingDB(Hashtable <String,String > data)
	  throws Throwable
	  
	  {
	 
	  ResultSet rs,rs1,rs2,rs3,rs4; 
	  boolean result = false; 
	  String username; 
	  String password;
	

	String url = configProps.getProperty("DBURL");
	String mid= configProps.getProperty("MID");

	
	  String rrn= data.get("RRN"); 
	  String advancedemiamount= data.get("AEA");
	  String loanamount=data.get("LA"); 
	  String emiamount= data.get("EA"); 
	  String transactiondate= data.get("TRAN_DATE_TIME");
	  String cli_cust_id=data.get("Customer_Id");
	 
	 
	  
	  
	  //select * from FINS_AUTHORIZATIONMASTER where AUTH_RRNUMBER in ('5005052055'); 
	  
	  //select * from FINS_AUTHORIZATIONMASTER where AUTH_RRNUMBER='bflapi800012';
	
	 String query = "select * from FINS_AUTHORIZATIONMASTER where AUTH_RRNUMBER='"+rrn+"'"; 
	 System.out.println("query is"+query);  

	   username =configProps.getProperty("DBUsername"); 
	   password =configProps.getProperty("DBPassword");
	  
	  System.out.println("username is"+username);
	  System.out.println("password is"+password);
	  
	 
	  String RRN= null; 
	  String AUTH_REQ_DATE=null;
	  
	 
	  rs =NavigateToServiceClient.connectDB(url,username,password,query);
	  SimpleDateFormat dateWithTimeFormate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	  while(rs.next())
	  {
	  
	  RRN=rs.getString("AUTH_RRNUMBER");	 
	  // txn date  
	  AUTH_REQ_DATE=rs.getString("AUTH_REQ_DATE");
	  System.out.println(AUTH_REQ_DATE);
	  Date dd=dateWithTimeFormate.parse(AUTH_REQ_DATE);
	  
	  System.out.println(dd);
			
			  SimpleDateFormat format = new SimpleDateFormat("dd-MM-yy");
			  
			// Date date = format.parse("AUTH_REQ_DATE");
			   
			  String NEW_AUTH_REQ_DATE=format.format(dd);
			  
			  System.out.println(NEW_AUTH_REQ_DATE);
			 
		
		System.out.println("New MAUTH_REQ_DATE is=" + NEW_AUTH_REQ_DATE);
		
	  
	  if(RRN!=null && rrn.contains(RRN) && NEW_AUTH_REQ_DATE!= null && NEW_AUTH_REQ_DATE.contains(transactiondate)) {
			
		  Reporter.reportStep("AUTH_RRNumber value from FINS_AUTHORIZATIONMASTER table is : "+RRN); 
			 Reporter.reportStep("AUTH_REQ_DATE value from FINS_AUTHORIZATIONMASTER  table is : "+NEW_AUTH_REQ_DATE); //
			 //Reporter.reportStep("Transaction Date value from FINS_AUTHORIZATIONMASTER  table is : " +transactiondate); 
			 Reporter.SuccessReport("Transaction Date is fins_authorizationmaster table validations","fins_authorizationmaster table validation pass.");
			 result=true;
			 }
			 else 
			 {
			 Reporter.reportStep("AUTH_REQ_DATE value from FINS_AUTHORIZATIONMASTER table is : " +NEW_AUTH_REQ_DATE); 
			 Reporter.reportStep("Transaction Date value from FINS_AUTHORIZATIONMASTER  table is : " +transactiondate); 
			 //Reporter.failureReport("Transaction Date FINS_AUTHORIZATIONMASTER  table validations","Transaction Date FINS_AUTHORIZATIONMASTER  table validation fail.");
			 Reporter.failureReport("Transaction Date fins_authorizationmaster table validations"," fins_authorizationmaster table validation fail.");

			 result=false;
			 }
	  
	  
	  
	  } 
		 
	  String newExpected1=data.get("compCLI_TOTAL_AUTH_BLOCKED");
	  String newExpected2=data.get("compCLI_ECS_AUTH_BLOCKED");

	//select CLI_TOTAL_AUTH_BLOCKED, CLI_ECS_AUTH_BLOCKED from fins_clientmaster where CLI_CUST_ID='817485';     
	 String query1= "select * from fins_clientmaster where CLI_CUST_ID='"+cli_cust_id+"'";
	  System.out.println("query is"+query1);
	
	 String CLI_TOTAL_AUTH_BLOCKED = null;
	 String CLI_ECS_AUTH_BLOCKED= null; 
	
	 rs2 =NavigateToServiceClient.connectDB(url,username,password,query1);
	
	while(rs2.next()){
	 
		CLI_TOTAL_AUTH_BLOCKED = rs2.getString("CLI_TOTAL_AUTH_BLOCKED");
		CLI_ECS_AUTH_BLOCKED =rs2.getString("CLI_ECS_AUTH_BLOCKED"); 
	
	 }
	
	  if(CLI_TOTAL_AUTH_BLOCKED!= null && CLI_TOTAL_AUTH_BLOCKED.contains(newExpected1) && CLI_ECS_AUTH_BLOCKED!=null && CLI_ECS_AUTH_BLOCKED.contains(newExpected2)) {
	
	  Reporter.reportStep("CLI_TOTAL_AUTH_BLOCKED value from fins_clientmaster table is : "+CLI_TOTAL_AUTH_BLOCKED); 
	  Reporter.reportStep("CLI_ECS_AUTH_BLOCKED value from fins_clientmaster table is : "+CLI_ECS_AUTH_BLOCKED); 
	  
	  Reporter.SuccessReport("fins_clientmaster table validations","fins_clientmaster table validation pass.");
	 result=true; 
	 }else {
		 

		  Reporter.reportStep("CLI_TOTAL_AUTH_BLOCKED value from fins_clientmaster table is : "+CLI_TOTAL_AUTH_BLOCKED); 
		  Reporter.reportStep("CLI_ECS_AUTH_BLOCKED value from fins_clientmaster table is : "+CLI_ECS_AUTH_BLOCKED); 
		  Reporter.failureReport("fins_clientmaster table validations","fins_clientmaster table validation fail.");
		  result=false;
	 }
	 
	  
	  
	  String total_auth=data.get("compnew_Exp_ACC_TOTAL_AUTH_BLOCKED");
	 

	//select * from  fins_accountmaster where ACC_CUST_ID='817485';   
	 String query4= "select * from fins_accountmaster where ACC_CUST_ID='"+cli_cust_id+"'";
	  System.out.println("query is"+query4);
	  
	  
	
	 String ACC_TOTAL_AUTH_BLOCKED = null;
	 
	
	 rs4 =NavigateToServiceClient.connectDB(url,username,password,query4);
	
	while(rs4.next()){
	 
		ACC_TOTAL_AUTH_BLOCKED = rs4.getString("ACC_TOTAL_AUTH_BLOCKED");
		
	
	
	  if(ACC_TOTAL_AUTH_BLOCKED!= null && ACC_TOTAL_AUTH_BLOCKED.contains(total_auth)) {
	
	  Reporter.reportStep("ACC_TOTAL_AUTH_BLOCKED value from fins_accountmaster table is : "+ACC_TOTAL_AUTH_BLOCKED); 
	  Reporter.SuccessReport("fins_accountmaster table validations","fins_accountmaster table validation pass.");
	 result=true; 
	 }else {
		 

		  Reporter.reportStep("ACC_TOTAL_AUTH_BLOCKED value from fins_accountmaster table is : "+ACC_TOTAL_AUTH_BLOCKED);  
		  Reporter.failureReport("fins_accountmaster  table validations","fins_accountmaster table validation fail.");
		  result=false;
	 }

	  
	}
	 return result;
	  
	  
	 
	 
	 
	 
	  }
	  
		public static boolean CancelMauthTransactionDB(Hashtable<String, String> data) throws Throwable

		{

			ResultSet rs, rs1, rs2, rs3, rs4, rs5;
			boolean result = false;
			String username;
			String password;

			String url = configProps.getProperty("DBURL");
			String mid = configProps.getProperty("MID");

			String rrn = data.get("RRN");
			String advancedemiamount = data.get("AEA");
			String loanamount = data.get("LA");
			String emiamount = data.get("EA");
			String transactiondate = data.get("TRAN_DATE_TIME");
			String cli_cust_id = data.get("Customer_Id");
			String source_reverse = data.get("SOURCE_REVERSE");


			// **Query
			// select * from FINS_AUTHORIZATIONMASTER where AUTH_RRNUMBER='bflapi800072' AND AUTH_SOURCE_REVERSE='VOID';
			String query = "select * from FINS_AUTHORIZATIONMASTER where AUTH_RRNUMBER='" + rrn + "'"+ " AND AUTH_SOURCE_REVERSE='VOID'";
			System.out.println("query is" + query);

			username = configProps.getProperty("DBUsername");
			password = configProps.getProperty("DBPassword");

			System.out.println("username is" + username);
			System.out.println("password is" + password);

			String RRN = null;
			String AUTH_REQ_DATE = null;
			String SOURCE_REVERSE = null;

			rs = NavigateToServiceClient.connectDB(url, username, password, query);
			//SimpleDateFormat dateWithTimeFormate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			while (rs.next()) {

				RRN = rs.getString("AUTH_RRNUMBER");
				// txn date
				AUTH_REQ_DATE = rs.getString("AUTH_REQ_DATE");
				System.out.println(AUTH_REQ_DATE);
				

				SOURCE_REVERSE = rs.getString("AUTH_SOURCE_REVERSE");

				

				if (RRN != null && rrn.contains(RRN) && 
						 SOURCE_REVERSE != null
						&& SOURCE_REVERSE.contains(source_reverse)) {

					Reporter.reportStep("AUTH_RRNumber value from FINS_AUTHORIZATIONMASTER table is : " + RRN);
					
					Reporter.reportStep("AUTH_SOURCE_REVERSE value from FINS_AUTHORIZATIONMASTER  table is : " + SOURCE_REVERSE);
	             
					Reporter.SuccessReport("Fins_authorizationmaster table validations","fins_authorizationmaster table validation pass.");
					result = true;
				} else {
					
					Reporter.reportStep("Transaction Date value from FINS_AUTHORIZATIONMASTER  table is : " + transactiondate);
					Reporter.reportStep("AUTH_SOURCE_REVERSE value from FINS_AUTHORIZATIONMASTER  table is : " + SOURCE_REVERSE);
					Reporter.failureReport("Transaction Date FINS_AUTHORIZATIONMASTER table validations","Transaction Date FINS_AUTHORIZATIONMASTER table validationfail.");
					Reporter.failureReport("Fins_authorizationmaster table validations"," fins_authorizationmaster table validation fail.");

					result = false;
				}

			}
			
	       //***************************************************************//
			

			String Expected1=data.get("CLI_TOTAL_AUTH_BLOCKED");
			String Expected2=data.get("CLI_ECS_AUTH_BLOCKED");

			String CLI_TOTAL_AUTH_BLOCKED= null;
			String CLI_ECS_AUTH_BLOCKED= null;
			

			// select CLI_TOTAL_AUTH_BLOCKED, CLI_ECS_AUTH_BLOCKED from fins_clientmaster where CLI_CUST_ID='817485';
			String query2 = "select * from fins_clientmaster where CLI_CUST_ID='" + cli_cust_id + "'";
			System.out.println("query is" + query2);

			
			rs2 = NavigateToServiceClient.connectDB(url, username, password, query2);

			while (rs2.next()) {

				CLI_TOTAL_AUTH_BLOCKED = rs2.getString("CLI_TOTAL_AUTH_BLOCKED");
				CLI_ECS_AUTH_BLOCKED = rs2.getString("CLI_ECS_AUTH_BLOCKED");

			if (CLI_TOTAL_AUTH_BLOCKED != null && CLI_TOTAL_AUTH_BLOCKED.contains(Expected1) && CLI_ECS_AUTH_BLOCKED != null && CLI_ECS_AUTH_BLOCKED.contains(Expected2)) {

				Reporter.reportStep("CLI_TOTAL_AUTH_BLOCKED value from fins_clientmaster table is : " + CLI_TOTAL_AUTH_BLOCKED);
				Reporter.reportStep("CLI_ECS_AUTH_BLOCKED value from fins_clientmaster table is : " + CLI_ECS_AUTH_BLOCKED);

				Reporter.SuccessReport("CanclMAuth fins_clientmaster table validations", "CancelMAuth fins_clientmaster table validation pass.");
				result = true;
			} else {

				Reporter.reportStep("CLI_TOTAL_AUTH_BLOCKED value from fins_clientmaster table is : " + CLI_TOTAL_AUTH_BLOCKED);
				Reporter.reportStep("CLI_ECS_AUTH_BLOCKED value from fins_clientmaster table is : " + CLI_ECS_AUTH_BLOCKED);
				Reporter.failureReport("CancelMAuth fins_clientmaster table validations", "CancelMAuth fins_clientmaster table validation fail.");
				result = false;
			}
		}
	    //*****************************************************************************//
			String total_auth = data.get("ACC_TOTAL_AUTH_BLOCKED");
			
			
			String ACC_TOTAL_AUTH_BLOCKED= null;
			// select * from fins_accountmaster where ACC_CUST_ID='817485';
			String query4 = "select * from fins_accountmaster where ACC_CUST_ID='" + cli_cust_id + "'";
			System.out.println("query is" + query4);

			
			rs4 = NavigateToServiceClient.connectDB(url, username, password, query4);

			while (rs4.next()) {

				ACC_TOTAL_AUTH_BLOCKED = rs4.getString("ACC_TOTAL_AUTH_BLOCKED");
			

				if (ACC_TOTAL_AUTH_BLOCKED != null && ACC_TOTAL_AUTH_BLOCKED.contains(total_auth)) 
				{

					Reporter.reportStep("ACC_TOTAL_AUTH_BLOCKED value from fins_accountmaster table is : " + ACC_TOTAL_AUTH_BLOCKED);
					Reporter.SuccessReport("CancelMAuth fins_accountmaster table validations","CancelMAuth fins_accountmaster table validation pass.");
					result = true;
				} else {

					Reporter.reportStep("ACC_TOTAL_AUTH_BLOCKED value from fins_accountmaster table is : " + ACC_TOTAL_AUTH_BLOCKED);
					Reporter.failureReport("CancelMAuth fins_accountmaster table validations","CancelMAuth fins_accountmaster table validation fail.");
					result = false;
				}
		
			}
			return result;
	}
		
	  
	  
	 

	public static boolean CreateMauthJSON(Hashtable<String, String> data) throws Throwable {

		boolean result = false;

		String reqType = data.get("TransactionType");

		String path = System.getProperty("user.dir");

		String from2 = path + "\\src\\test\\java\\com\\BFL\\data\\MAUTH_Transaction.json";


		//String temprrn = configProps.getProperty("APICounter");
		String temprrn = configProps.getPropertyNew("APICounter",NavigateToServiceClient.strFilePath);

		String RRN = NavigateToServiceClient.countRRNnumber(temprrn);
		System.out.println("New RRN is :" + RRN);
		Reporter.reportStep("New RRN is :" + RRN);

		JSONParser jsonParser = new JSONParser();
		String tmp = null;

		try (FileReader reader = new FileReader(from2)) {
			// Read JSON file
			Object obj = jsonParser.parse(reader);

			tmp = obj.toString();
			System.out.println(tmp);

		} catch (Exception e) {
			System.out.println("JSON read error");
		}

		JSONObject obj = new JSONObject(tmp);

		String rrn = (String) obj.get("RRN");
		
		obj.put("RRN", RRN);
		// jsonObj.put("RRN", RRN);
		data.put("RRN", RRN);
		System.out.println("New rrn is :" + RRN);


		Date date = new Date();
		SimpleDateFormat TRAN_DATE_TIME = new SimpleDateFormat("dd-MM-yy");
		String strDate = TRAN_DATE_TIME.format(date);

		System.out.println("New date is=" + strDate);
		obj.put("TRAN_DATE_TIME", strDate);
		data.put("TRAN_DATE_TIME", strDate);

		String NewJson = obj.toString();
		System.out.println("New Json is:" + NewJson);
		data.put("NewJSON", NewJson);
		
	     waitForPageLoaded();
		waitForElementPresent(CallLogin_Locator.ClearJSON, "Clear JSON");
		//System.out.println("New Json is:" + NewJson);

		// ype(CallLogin_Locator.ClearJSON,NewJson,"Clear JSON");

		js_type(CallLogin_Locator.ClearJSON, NewJson, "Clear JSON");

		Thread.sleep(2000);
	     waitForPageLoaded();
		waitForElementPresent(CallLogin_Locator.SubmitButton, "Clear JSON");
		click(CallLogin_Locator.SubmitButton, "Submit button");

		Thread.sleep(2000);
		waitForPageLoaded();

		String posturl = data.get("PostURL");

		waitForElementPresent(CallLogin_Locator.PostUrl, "Post URL");
		type(CallLogin_Locator.PostUrl, posturl, "PostUrl");
	     waitForPageLoaded();
		waitForElementPresent(CallLogin_Locator.ApiSubmit, "Submit button");
		click(CallLogin_Locator.ApiSubmit, "Submit button");

		String resp = null;

		waitForPageLoaded();

		if (waitTillSpinnerDisable(driver, By.id("ContentPlaceHolder1_UpdateProgress1"))) {
			waitForElementPresent(CallLogin_Locator.Response, "Response field present");

			resp = driver.findElement(By.id("ContentPlaceHolder1_txtResponse")).getText();
		}

		String array1[] = resp.split(",");

		String RESPCODE = array1[2];
		String RESPDESC = array1[3];
		String opRRN = array1[4];

		Reporter.reportStep("Message from the application is :" + resp);

		scrollTillPageEnd();

		Thread.sleep(2000);

		Reporter.takescreenshot("MAuth_Onboarding_api");

		if (RESPCODE.contains("00")) {
			Reporter.SuccessReport("MAuth Onboarding", "MAuth Customer succesfully created.");
			result = true;

		} else {
			Reporter.failureReport("MAuth Onboarding", "MAuth Customer onboarding failed.");
		}

		return result;

	}
	
	public static boolean CancelMauthJSON(Hashtable<String, String> data) throws Throwable {

		boolean result = false;
		Reporter.reportStep("CANCELLATION MAUTH API STARTED");

		String path = System.getProperty("user.dir");

		String from2 = path + "\\src\\test\\java\\com\\BFL\\data\\MAUTH_Transaction.json";


		JSONParser jsonParser = new JSONParser();
		String tmp = null;

		try (FileReader reader = new FileReader(from2)) {
			// Read JSON file
			Object obj = jsonParser.parse(reader);

			tmp = obj.toString();
			System.out.println(tmp);

		} catch (Exception e) {
			System.out.println("JSON read error");
		}

		JSONObject obj = new JSONObject(tmp);

		/*
		 * String rrn = (String) obj.get("RRN"); obj.put("RRN", RRN); //
		 * jsonObj.put("RRN", RRN); data.put("RRN", RRN);
		 * System.out.println("New rrn is :" + RRN);
		 */

		// Cancel transaction should refer to new RRN created in earlier stage
		String RRN = data.get("RRN");
		obj.put("RRN", RRN);

		// Updating SOURCE_REVERSE value to VOID - V
		obj.put("SOURCE_REVERSE", "V");
		data.put("SOURCE_REVERSE", "V");

		/*
		 * Date date = new Date(); SimpleDateFormat TRAN_DATE_TIME = new
		 * SimpleDateFormat("dd-MM-yy"); String strDate = TRAN_DATE_TIME.format(date);
		 * 
		 * System.out.println("New date is=" + strDate); obj.put("TRAN_DATE_TIME",
		 * strDate); data.put("TRAN_DATE_TIME", strDate);
		 */

		String date1 = data.get("TRAN_DATE_TIME");
		data.put("TRAN_DATE_TIME", date1);

		
						 

		String NewJson = obj.toString();
		System.out.println("New Json is:" + NewJson);
		data.put("NewJSON", NewJson);
		
	     waitForPageLoaded();
		waitForElementPresent(CallLogin_Locator.ClearJSON, "Clear JSON");
	

		// ype(CallLogin_Locator.ClearJSON,NewJson,"Clear JSON");

		js_type(CallLogin_Locator.ClearJSON, NewJson, "Clear JSON");

		Thread.sleep(2000);
		scrollUp();
	     waitForPageLoaded();
		waitForElementPresent(CallLogin_Locator.SubmitButton, "Clear JSON");
		
		click(CallLogin_Locator.SubmitButton, "Submit button");

		Thread.sleep(2000);
		waitForPageLoaded();

		String posturl = data.get("PostURL");

		waitForElementPresent(CallLogin_Locator.PostUrl, "Post URL");
		type(CallLogin_Locator.PostUrl, posturl, "PostUrl");

		scrollTillPageEnd();
	     waitForPageLoaded();
		waitForElementPresent(CallLogin_Locator.ApiSubmit, "Submit button");
		click(CallLogin_Locator.ApiSubmit, "Submit button");

		String resp = null;

		waitForPageLoaded();

		if (waitTillSpinnerDisable(driver, By.id("ContentPlaceHolder1_UpdateProgress1"))) {
			waitForElementPresent(CallLogin_Locator.Response, "Response field present");

			resp = driver.findElement(By.id("ContentPlaceHolder1_txtResponse")).getText();
		}

		String array1[] = resp.split(",");

		String RESPCODE = array1[2];
		String RESPDESC = array1[3];
		String opRRN = array1[4];

		Reporter.reportStep("Message from the application is :" + resp);

		scrollTillPageEnd();

		Thread.sleep(2000);

		Reporter.takescreenshot("Cancel_MAuth_Transaction_api");

		if (RESPCODE.contains("00")) {
			//Reporter.SuccessReport("MAuth Transaction", "MAuth Transaction cancelled succesfully.");
			Reporter.SuccessReport("MAuth Transaction", "MAuth Cancel Transaction created succesfully.");
			result = true;

		} else {
			//Reporter.failureReport("MAuth Transaction", "MAuth Cancel Transaction failed.");
			Reporter.failureReport("MAuth Transaction", "MAuth Cancel Transaction failed.");
		}

		return result;

	}

	/*
	 * public static String getEMINumber() throws Throwable {
	 * 
	 * String genNumber = null;
	 * 
	 * String tempnum = configProps.getProperty("APICardNumber");
	 * 
	 * long temp = Long.parseLong(tempnum) + 1;
	 * 
	 * genNumber = Long.toString(temp);
	 * 
	 * configProps.setProperty("APICardNumber", genNumber);
	 * 
	 * // TODO Auto-generated method stub return genNumber;
	 */
	
	
	public static  void GetPrvMauthDB(Hashtable <String,String > data) throws Throwable

	{
		
		
		String path= System.getProperty("user.dir");
		
		String from2= path+"\\src\\test\\java\\com\\BFL\\data\\MAUTH_Transaction.json";
		
		
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

    	JSONObject obj= new JSONObject(tmp);
   	  
   	String CARD_NUMBER= (String) obj.get("CARD_NUMBER");
   	String EMI_AMOUNT= (String) obj.get("EMI_AMOUNT");
   	data.put("EMI_AMOUNT", EMI_AMOUNT);
   	
	String LOAN_AMOUNT= (String) obj.get("LOAN_AMOUNT");
   	data.put("LOAN_AMOUNT", LOAN_AMOUNT);
   	
	String ADVANCE_EMI_AMOUNT= (String) obj.get("ADVANCE_EMI_AMOUNT");
   	data.put("ADVANCE_EMI_AMOUNT", ADVANCE_EMI_AMOUNT);
   	
   	String SOURCE_REVERSE = (String) obj.get("SOURCE_REVERSE");
    data.put("SOURCE_REVERSE", SOURCE_REVERSE);
   	
   	
		
		ResultSet rs,rs1,rs3,rs4;
		boolean result = false;
		String username;
		String password ;
		String Customer_Id=data.get("Customer_Id");
		
		
		String reqType= data.get("TransactionType");
		
		
		//String path= System.getProperty("user.dir");
		
		//String from2= path+"\\src\\test\\java\\com\\BFL\\data\\\\MAUTH_Transaction.json.json";
		
		
		/////   new code ends here
		/*
		FileInputStream inFile = new FileInputStream(from2);
	    byte[] str = new byte[inFile.available()];
	    inFile.read(str);
	    String jsonString = new String(str);
	 
	    String escapedString = StringEscapeUtils.unescapeJava(jsonString);
	    
	    JSONObject jsonObj = null;
	        try {
	            jsonObj = new JSONObject(escapedString);
	        } catch (JSONException e) {
	            e.printStackTrace();
	        }
	        
	        
	        */
	        
	        
		
		
		//
		//use card number and get custid
		
		String url = configProps.getProperty("DBURL");
// card number
		//SELECT * FROM Fins_cardmaster WHERE CRD_NO='2030401900000760';

		String query2 = "select * from Fins_cardmaster WHERE CRD_NO ='"+CARD_NUMBER+"'";
		//String query = "SELECT * FROM Fins_cardmaster WHERE CRD_NO ='"+emicard+ "'";


		System.out.println("query is" + query2);

		username = configProps.getProperty("DBUsername");
		password = configProps.getProperty("DBPassword");

		System.out.println("username is" + username);
		System.out.println("password is" + password);

		//String Customer_Id = null;

		rs = CallLogin.connectDB(url, username, password, query2);
	
		
		try {
			while (rs.next()) {

				Customer_Id = rs.getString("CRD_CUST_ID");
				data.put("Customer_Id", Customer_Id);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (Customer_Id != null) {
			System.out.println("Customer id is present in DB, customer number is:" + Customer_Id);

			result = true;
		} else {
			System.out.println("Customer id is not present in DB");
		}

		
		// 2nd query 
		//fins_clientmaster 
		//select CLI_TOTAL_AUTH_BLOCKED, CLI_ECS_AUTH_BLOCKED from fins_clientmaster where CLI_CUST_ID='817485';
		
		String query3 = "select * from fins_clientmaster WHERE CLI_CUST_ID ='"+Customer_Id+"'";
		//String query = "SELECT * FROM Fins_cardmaster WHERE CRD_NO ='"+emicard+ "'";


		System.out.println("query is" + query3);

		username = configProps.getProperty("DBUsername");
		password = configProps.getProperty("DBPassword");

		System.out.println("username is" + username);
		System.out.println("password is" + password);

		//String Customer_Id = null;

		rs3 = CallLogin.connectDB(url, username, password, query3);
	
		String CLI_TOTAL_AUTH_BLOCKED=null;
		String CLI_ECS_AUTH_BLOCKED=null;
		
		try {
			while (rs3.next()) {

				CLI_TOTAL_AUTH_BLOCKED = rs3.getString("CLI_TOTAL_AUTH_BLOCKED");
				data.put("CLI_TOTAL_AUTH_BLOCKED", CLI_TOTAL_AUTH_BLOCKED);
				System.out.println("Current CLI_TOTAL_AUTH_BLOCKED value from fins_clientmaster table is :"+ CLI_TOTAL_AUTH_BLOCKED);
				Reporter.reportStep("Current CLI_TOTAL_AUTH_BLOCKED value from fins_clientmaster table is :"+ CLI_TOTAL_AUTH_BLOCKED);
				
				CLI_ECS_AUTH_BLOCKED = rs3.getString("CLI_ECS_AUTH_BLOCKED");
				data.put("CLI_ECS_AUTH_BLOCKED", CLI_ECS_AUTH_BLOCKED);
				System.out.println("Current CLI_ECS_AUTH_BLOCKED value from fins_clientmaster table is :"+ CLI_ECS_AUTH_BLOCKED);
				Reporter.reportStep("Current CLI_ECS_AUTH_BLOCKED value from fins_clientmaster table is :"+ CLI_ECS_AUTH_BLOCKED);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (Customer_Id != null) {
			System.out.println("Customer id is present in DB, customer number is:" + Customer_Id);

			result = true;
		} else {
			System.out.println("Customer id is not present in DB");
		}

		       
		String s1 = CLI_TOTAL_AUTH_BLOCKED;
       // String MauthNewValue1=s1.substring(0, s1.length() - 2);
        int i1=Integer.parseInt(s1);  
        int Exp_CLI_TOTAL_AUTH_BLOCKED;
        
        int i2 =Integer.parseInt(EMI_AMOUNT);

        /**tmp**/
        
        int tmp4=i2*100;
        
        //Exp_CLI_TOTAL_AUTH_BLOCKED= i1+i2;
        
      Exp_CLI_TOTAL_AUTH_BLOCKED= i1+tmp4;
        
        String compCLI_TOTAL_AUTH_BLOCKED=String.valueOf(Exp_CLI_TOTAL_AUTH_BLOCKED);
        data.put("compCLI_TOTAL_AUTH_BLOCKED", compCLI_TOTAL_AUTH_BLOCKED);
        
        
        String s2 = CLI_ECS_AUTH_BLOCKED;
       // String MauthNewValue2=s2.substring(0, s2.length() - 2);
        int i3=Integer.parseInt(s2); 
        int Exp_CLI_ECS_AUTH_BLOCKED;
        
        //Exp_CLI_ECS_AUTH_BLOCKED=i2+i3;
        Exp_CLI_ECS_AUTH_BLOCKED=i3+tmp4;
        
        
        String compCLI_ECS_AUTH_BLOCKED=String.valueOf(Exp_CLI_ECS_AUTH_BLOCKED);
        data.put("compCLI_ECS_AUTH_BLOCKED", compCLI_ECS_AUTH_BLOCKED);
        
        /*******rs4********************************************/
        
        //select * from  fins_accountmaster where ACC_CUST_ID='817485';
        String query4 = "select * from  fins_accountmaster WHERE ACC_CUST_ID ='"+Customer_Id+"'";
		//String query = "SELECT * FROM Fins_cardmaster WHERE CRD_NO ='"+emicard+ "'";


		System.out.println("query is" + query4);

		username = configProps.getProperty("DBUsername");
		password = configProps.getProperty("DBPassword");

		System.out.println("username is" + username);
		System.out.println("password is" + password);

		//String Customer_Id = null;

		rs4 = CallLogin.connectDB(url, username, password, query4);
	
		String ACC_TOTAL_AUTH_BLOCKED=null;
		
		
		try {
			while (rs4.next()) {

				ACC_TOTAL_AUTH_BLOCKED = rs4.getString("ACC_TOTAL_AUTH_BLOCKED");
				data.put("ACC_TOTAL_AUTH_BLOCKED", ACC_TOTAL_AUTH_BLOCKED);

				System.out.println("Current ACC_TOTAL_AUTH_BLOCKED value from fins_accountmaster table is :"+ ACC_TOTAL_AUTH_BLOCKED);
				Reporter.reportStep("Current ACC_TOTAL_AUTH_BLOCKED value from fins_accountmaster table is :"+ ACC_TOTAL_AUTH_BLOCKED);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (ACC_TOTAL_AUTH_BLOCKED != null) {
			System.out.println("ACC_TOTAL_AUTH_BLOCKED is present in DB, customer number is:" + ACC_TOTAL_AUTH_BLOCKED);

			result = true;
		} else {
			System.out.println("ACC_TOTAL_AUTH_BLOCKED  is not present in DB");
		}

				
		//int preACC_TOTAL_AUTH_BLOCKED = 0;
		int newVal=Integer.parseInt(ACC_TOTAL_AUTH_BLOCKED);
		
		

	    //int preLoan_Amount;
	    int newLoanVal=Integer.parseInt(LOAN_AMOUNT);
				
		//int preAdv_EMI_Amount;
		int newAdv_EMI=Integer.parseInt(ADVANCE_EMI_AMOUNT);
		
		int temp3=newLoanVal-newAdv_EMI;
		
	//	int Exp_ACC_TOTAL_AUTH_BLOCKED= (newVal)+(newLoanVal-newAdv_EMI);
	int Exp_ACC_TOTAL_AUTH_BLOCKED= (newVal)+(temp3*100);
		
		//***tmp code need t o remove later*//
		
		//int tmp2=Exp_ACC_TOTAL_AUTH_BLOCKED*100;
		
		/*
		 * String
		 * compnew_Exp_ACC_TOTAL_AUTH_BLOCKED=String.valueOf(Exp_ACC_TOTAL_AUTH_BLOCKED)
		 * ; data.put("compnew_Exp_ACC_TOTAL_AUTH_BLOCKED",
		 * compnew_Exp_ACC_TOTAL_AUTH_BLOCKED);
		 */
		String compnew_Exp_ACC_TOTAL_AUTH_BLOCKED=String.valueOf(Exp_ACC_TOTAL_AUTH_BLOCKED);
		data.put("compnew_Exp_ACC_TOTAL_AUTH_BLOCKED",compnew_Exp_ACC_TOTAL_AUTH_BLOCKED);
	}


	
	public static Boolean ADD_EMI_AMOUNTDB(Hashtable<String, String> data) throws Throwable

	{

		ResultSet rs3;
		boolean result = false;
		String username;
		String password;

		String url = configProps.getProperty("DBURL");
		// String mid= configProps.getProperty("MID");
		// String custID= data.get("CustomerID");
		String customerid = data.get("CustomerID");
		

		// SELECT * FROM Fins_cardmaster WHERE CRD_NO='2030403330000002';
		String query3 = "select * from Fins_cardmaster where CRD_CUST_ID ='" + customerid + "'";
		System.out.println("query is" + query3);

		username = configProps.getProperty("DBUsername");
		password = configProps.getProperty("DBPassword");

		System.out.println("username is" + username);
		System.out.println("password is" + password);

		String CRD_CUST_ID = null;

		rs3 = CallLogin.connectDB(url, username, password, query3);

		try {
			while (rs3.next()) {

				CRD_CUST_ID = rs3.getString("CRD_CUST_ID");

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (CRD_CUST_ID != null) {
			System.out.println("Customer is present in database:" + CRD_CUST_ID);

			result = true;
		} else {
			System.out.println("Customer is not present in database");
		}

		return result;

	}

	
	public static  void GETTotalAmountMasterDB(Hashtable <String,String > data) throws Throwable

	{
		
		
		ResultSet rs4;
		boolean result = false;
		String username;
		String password ;
		String Total_account=data.get("ACC_TOTAL_AUTH_BLOCKED");

		
		String url = configProps.getProperty("DBURL");

		String query4 = "select * from fins_accountmaster  WHERE CRD_CUST ='" + Total_account + "'";
		System.out.println("query is" + query4);

		username = configProps.getProperty("DBUsername");
		password = configProps.getProperty("DBPassword");

		System.out.println("username is" + username);
		System.out.println("password is" + password);

		//String Customer_Id = null;

		rs4 = CallLogin.connectDB(url, username, password, query4);
	
		
		try {
			while (rs4.next()) {

				Total_account = rs4.getString("Total_account");

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (Total_account != null) {
			System.out.println("Customer id is present in DB, customer number is:" + Total_account);

			result = true;
		} else {
			System.out.println("Customer id is not present in DB");
		}

		

	}

	
	
	public static String countRRNnumber(String APICounter) throws Throwable {

		int counter = Integer.parseInt(APICounter);

		int newvalue = counter + 1;
		configProps.setProperty("APICounter", (Integer.toString(newvalue)));
		System.out.println("auto" + (Integer.toString(counter)));
		String rrn = "auto" + (Integer.toString(counter));

		return rrn;

	}
	
}