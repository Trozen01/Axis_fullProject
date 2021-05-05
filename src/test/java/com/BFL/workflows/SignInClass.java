package com.BFL.workflows;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;

import com.BFL.testObjects.BFL_SignIn;
import com.MainFrameWork.accelerators.ActionEngine;
import com.MainFrameWork.support.HtmlReportSupport;
import com.MainFrameWork.utilities.Reporter;

public class SignInClass extends ActionEngine {

	public String USERID;
	public String password;

	static Logger logger = Logger.getLogger(SignInClass.class.getName());
	
	
	
	
	public static boolean specificlogin(String propname, String proppass) throws Throwable

	{
		boolean result = false;
		

		String UserName, Password;
		url = configProps.getProperty("URL");
		UserName = configProps.getProperty(propname);
		Password = configProps.getProperty(proppass);
		driver.get(url);
		//
		waitForPageLoaded();
		String txturl= driver.getCurrentUrl();
		HtmlReportSupport.reportStep("URL launched is:"+txturl);
		
		System.out.println(UserName);
		
		System.out.println(Password);
		HtmlReportSupport.reportStep("Login Credentials_Entry");
		

		if (waitForElementPresent(BFL_SignIn.Username,"Login Field")) {

			logger.info("Login Page loaded succesfully");
             System.out.println("here");
			Reporter.SuccessReport("BFL Login Page","Login Page loaded succesfully");
			
			Reporter.takescreenshot("BFL_LoginPage");
			waitForElementPresent(BFL_SignIn.Username, "UserID");
			type(BFL_SignIn.Username, UserName,"UserID Entered Sucessfully");
			waitForElementPresent(BFL_SignIn.Password, "Password");
			type(BFL_SignIn.Password, Password,"Password Entered Sucessfully");

			click(BFL_SignIn.loginButton,"Login Button Clicked Successfully");
			
			
			Thread.sleep(2000);
			try {
				// try 2nd login if 
				waitForAjax(driver);
				Thread.sleep(2000);
				driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
				if(driver.findElements(By.id("txtUser")).size()>0){
					waitForElementPresent(BFL_SignIn.Username, "UserID");
					type(BFL_SignIn.Username, UserName,"UserID Entered Sucessfully");
					waitForElementPresent(BFL_SignIn.Password, "Password");
					type(BFL_SignIn.Password, Password,"Password Entered Sucessfully");

					click(BFL_SignIn.loginButton,"Login Button Clicked Successfully");
					driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			waitForPageLoaded();
			waitForAjax(driver);
			if (waitForElementPresent(BFL_SignIn.Logout,"Logout Link")) {

				logger.info("LoggedIn  succesfully");
				Reporter.reportStep("User looged in succesfully.");
				result = true;
			}

			
		}
		return result;

	}
	
	public static boolean specificvendorlogin(String propname, String proppass) throws Throwable

	{
		boolean result = false;
		ImplicitWait();

		String UserName, Password;
		
		UserName = configProps.getProperty(propname);
		Password = configProps.getProperty(proppass);
		String txturl= driver.getCurrentUrl();
		HtmlReportSupport.reportStep("URL launched is:"+txturl);
		
		System.out.println(UserName);
		
		System.out.println(Password);
		HtmlReportSupport.reportStep("Login Credentials_Entry");

		if (waitForElementPresent(BFL_SignIn.Username,"Login Field in Login Page")) {

			logger.info("Login Page loaded succesfully");
             System.out.println("here");
			Reporter.SuccessReport("MMS Login Page","Login Page loaded succesfully");
			
			Reporter.takescreenshot("MMS_LoginPage");

			type(BFL_SignIn.Username, UserName,"UserID Entered Sucessfully");
			waitForJSandJQueryToLoad();
			

			click(BFL_SignIn.loginButton,"Login Button Clicked Successfully");
			waitForJSandJQueryToLoad();
			waitForAjax(driver);
			result = true;
		}
		return result;

	}
	
	public static void logout() throws Throwable {
		//Thread.sleep(3000);  right logout
		HtmlReportSupport.reportStep("LOGOUT_DETAILS");
		//driver.navigate().refresh();
		//Thread.sleep(2000);
		
		
		//driver.navigate().refresh();
		// click logout
		Thread.sleep(1000);
		try{
			//if(driver.findElements(By.id("imgLogOut")).size()>0){
		
				if(driver.findElement(By.linkText("Logout")).isDisplayed()){
					Thread.sleep(1000);
					driver.findElement(By.linkText("Logout")).click();
					   
					Reporter.reportStep("Clicked logout link");
			}else{
				
			}
			
			
		}catch(Exception e){
			
		}
	}
	
	
	

}
