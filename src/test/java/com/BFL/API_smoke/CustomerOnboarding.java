package com.BFL.API_smoke;

import java.util.Hashtable;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.BFL.API_workflows.CustomerOnboardingAPI_Details;
import com.BFL.API_workflows.NavigateToServiceClient;
import com.BFL.workflows.AccountBlock_UnblockDetails;
import com.BFL.workflows.CallLogin;
import com.BFL.workflows.CardActivationDetails;
import com.BFL.workflows.CardCancellationDetails;
import com.BFL.workflows.CustomerBlock_UnblockDetails;
import com.BFL.workflows.SignInClass;
import com.MainFrameWork.accelerators.TestEngine;
import com.MainFrameWork.support.HtmlReportSupport;
import com.MainFrameWork.utilities.Reporter;



public class CustomerOnboarding  extends CustomerOnboardingAPI_Details{
	
	@Test(groups= {"smoke", "functional"}, dataProvider = "getData",enabled=true)
	public void CustomerOnboardingAPI_Test(Hashtable <String,String > data) throws Throwable {
		
		try
		{
			boolean result = false;
			boolean result1 = false;
			boolean result3 = false;
			boolean result4 = false;
			
			TestEngine.testDescription.put(HtmlReportSupport.tc_name,"");
			System.out.println("Usertype - "+ data.get("Usertype"));
				 
			SignInClass sign = new SignInClass();
			HtmlReportSupport.reportStep(data.get("TestCoverage"));
		
			SignInClass.specificlogin("makerusername","makerpassword");
			
			NavigateToServiceClient.ServiceClient(data);
			
			//result3 = NavigateToServiceClient.CreateMerchantOnboardingJSON(data);
			result3 = CreateMerchantOnboardingJSON(data);
			
			result4= CustOnboardingDB(data);
			
			sign.logout();

			if (result4) 
			{
				Reporter.SuccessReport("BFL API Customer Onboarding","BFL API Customer Onboarding succesfully done." );
				System.out.println("BFL API Customer Onboarding,BFL API Customer Onboarding succesfully done.");
			} 

			else 
			{
				Reporter.failureReport("BFL API Customer Onboarding ", "BFL API Customer Onboarding failed..");
				Assert.assertTrue(result == true, "Test Failed");
				System.out.println("BFL API Customer Onboarding failed.");
			}
		}
			catch (Exception e){
			Reporter.failureReport("BFL API Customer Onboarding succesfully done.", "BFL API Customer Onboarding Failed.");
			e.printStackTrace();
			Assert.assertTrue(false,"Test Failed");
			System.out.println("BFL API Customer Onboarding failed.");
			
			Reporter.reportStep("Error is:"+e);
		}
		
		
	}

	

	

}
