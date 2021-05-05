package com.BFL.workflows;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.UnhandledAlertException;
import org.testng.Assert;

import com.BFL.testObjects.CallLogin_Locator;
import com.BFL.testObjects.MainpageLocator;
import com.MainFrameWork.accelerators.ActionEngine;
import com.MainFrameWork.support.HtmlReportSupport;
import com.MainFrameWork.utilities.Reporter;
import com.OneView.utils.DataUtil;

import com.thoughtworks.selenium.webdriven.commands.WaitForPageToLoad;

public class CallLogin extends ActionEngine {

	public String USERID;
	public String password;

	
	static Logger logger = Logger.getLogger(CallLogin.class.getName());

	public static boolean CallLoggin_Details(Hashtable <String,String > data) throws Throwable

	{
		boolean result = false;
		HtmlReportSupport.reportStep("Call logging Details");
		ImplicitWait();

		waitForPageLoaded();
		if (waitForElementPresent(MainpageLocator.CallLogging,"Call Login Link Present")) {
         
			click(MainpageLocator.CallLogging,"Call Logging Clicked Successfully");
		}
		
		waitForPageLoaded();
		waitForAjax(driver);
		if (waitForElementPresent(MainpageLocator.Inquiries,"Inquries Link Present")) {

			click(MainpageLocator.Inquiries,"Inquries Clicked Successfully");
		}
		
		
		
		waitForPageLoaded();
		//Thread.sleep(5000);
		// temp code 
		
		if (data.get("CardNumber") != "" && data.get("CardNumber")!= null) 
		{
			waitForElementPresent(CallLogin_Locator.CardNumber,"Card Number field present");
			 String  num= data.get("CardNumber");
			
			//type(CallLogin_Locator.CardNumber, data.get("CardNumber"),"Card Number Entered Sucessfully");
			js_type(CallLogin_Locator.CardNumber, num,"Card Number Entered Sucessfully");
//			driver.findElement(By.id("txtCardNumber")).clear();
//			driver.findElement(By.id("txtCardNumber")).sendKeys(data.get("CardNumber"));
			//ImplicitWait();
		}
		
		
		if (data.get("CardHolderName") != "" && data.get("CardHolderName")!= null) {
		type(CallLogin_Locator.CardHolderName ,data.get("CardHolderName"),"Card Holder Name Entered Sucessfully");
		}
		
		if (data.get("FirstName") != "" && data.get("FirstName")!= null) {
			type(CallLogin_Locator.FirstName ,data.get("FirstName"),"Card Holder First Name Entered Sucessfully");
			}
		
		if (data.get("LastName") != "" && data.get("LastName")!= null) {
			type(CallLogin_Locator.LastName ,data.get("LastName"),"Card Holder Last Name Entered Sucessfully");
			}
		
		
		if (data.get("MotherMaidenName") != "" && data.get("MotherMaidenName")!= null) {
		type(CallLogin_Locator.MotherMaidenName ,data.get("MotherMaidenName"),"Mother Maiden Name Entered Sucessfully");
		}
		
		
		if (data.get("AccountID") != "" && data.get("AccountID")!= null) {
        type(CallLogin_Locator.AccountID ,data.get("AccountNo"),"Account No Entered Sucessfully");
		}
		
		if (data.get("ReferenceNO") != "" && data.get("ReferenceNO")!= null) {
		type(CallLogin_Locator.ReferenceNo ,data.get("ReferenceNO"),"Reference No Entered Sucessfully");
		
		}
		
		if (data.get("ApplyreferenceNo") != "" && data.get("ApplyreferenceNo")!= null) {
		type(CallLogin_Locator.ApplyreferenceNo ,data.get("ApplyreferenceNo"),"Apply Reference No Entered Sucessfully");
		
		}
		
		if (data.get("CustomerID") != "" && data.get("CustomerID")!= null) {
		type(CallLogin_Locator.CustomerID ,data.get("CustomerID"),"CustomerID No Entered Sucessfully");
		}
		
		if (data.get("NationalID") != "" && data.get("NationalID")!= null) {
		type(CallLogin_Locator.NationalID,data.get("NationalID"),"NationalID Entered Sucessfully");
		}
		
		if (data.get("DateofBirth") != "" && data.get("DateofBirth")!= null) {
		type(CallLogin_Locator.DateOfBirth ,data.get("DateofBirth"),"Date Of Birth Entered Sucessfully");
		}
		
		if (data.get("MobileNumber") != "" && data.get("MobileNumber")!= null) {
		type(CallLogin_Locator.MobileNo,data.get("MobileNumber"),"Mobile Number  Entered Sucessfully");
		}
		waitForPageLoaded();
		/*
		if (waitForElementPresent(CallLogin_Locator.InBound_RadioButton,"Inbound Radiobutton Link Present")) {

			//click(CallLogin_Locator.InBound_RadioButton,"Inbound Radio button Clicked Successfully");
		}
			*/
		//temp code
		
		if (waitForElementPresent(CallLogin_Locator.SearchButton,"Search button")) {

			//click(CallLogin_Locator.InBound_RadioButton,"Inbound Radio button Clicked Successfully");
			JSClick(CallLogin_Locator.SearchButton,"Search button Clicked Successfully");
		}
		//JSClick(CallLogin_Locator.SearchButton,"Search button Clicked Successfully");
		
		
		// ====================Card Order Radio Button And TEXT Selection===============================================
		
		waitForPageLoaded();
					
					if (isElementDisplayed(CallLogin_Locator.CardNumber_RadioButton,
							"Radio button ")) {
						
						JSClick(CallLogin_Locator.CardNumber_RadioButton,
								" Card number Radio button");
						JSClick(CallLogin_Locator.ProceedButton,
								" Proceed Radio button");
						
						//Thread.sleep(2000);
						
					}
					
					
					
				try {
					driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
					
					if(driver.findElements(By.id("ContentPlaceHolder1_btnHide")).size()>0) {
						String errmsg= driver.findElement(By.id("Divmsg")).getText();
						if(errmsg.length()>0) {
							Reporter.reportStep("Message from the application is "+errmsg);
							Reporter.takescreenshot("Call_Logging_failed");
							Reporter.failureReport("Call logging", "No Data");
							driver.findElement(By.id("ContentPlaceHolder1_btnHide")).click();
						}
						
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

					// ===== for PMC
					// Bank=========================================================
					waitForPageLoaded();
					driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
					if (isElementDisplayed(CallLogin_Locator.CardNumber_Checkbox,
							"Radio button ")) {
						String fileName1 = driver.findElement(
								CallLogin_Locator.CardNumber_Text).getText();
						JSClick(CallLogin_Locator.CardNumber_Checkbox,
								" Card number Radio button");
						//Thread.sleep(2000);
					}

						if (isElementDisplayed(
								CallLogin_Locator.CustomerID_Checkbox,
								"Checkbox button ")) {
							String fileName5 = driver.findElement(
									CallLogin_Locator.CUstomerID_Text).getText();
							JSClick(CallLogin_Locator.CustomerID_Checkbox,
									"Customer ID checkBox");
							//Thread.sleep(2000);
						

						if (isElementDisplayed(CallLogin_Locator.Cardexpiry_Checkbox,
								"Checkbox button ")) {
							String fileName3 = driver.findElement(
									CallLogin_Locator.CardExpiryDate).getText();
							JSClick(CallLogin_Locator.Cardexpiry_Checkbox,
									"Cardexpiry checkBox");
							Thread.sleep(2000);

						}

						if (isElementDisplayed(CallLogin_Locator.MobileNo_Checkbox,
								"Checkbox button ")) {
							String fileName6= driver.findElement(
									CallLogin_Locator.MobileNo_Text).getText();
							JSClick(CallLogin_Locator.MobileNo_Checkbox,
									"MobileNo_Checkbox");
							//Thread.sleep(2000);
						}
						
						
						if (isElementDisplayed(CallLogin_Locator.Email_Checkbox,
								"Checkbox button")) {
							String fileName = driver.findElement(
									CallLogin_Locator.CUstomerID_Text).getText();
							JSClick(CallLogin_Locator.Email_Text,
									"Email_Checkbox");
							Thread.sleep(2000);
						}
					}
						
					//JSClick(CallLogin_Locator.SubmitButton, "submit button ");
					JSClick(CallLogin_Locator.CallLogin_Submit, "submit button ");
					waitForPageLoaded();
					waitForAjax(driver);
		
			result = true;
		
		return result;

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
}
