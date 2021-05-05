package com.AXIS.smoke;

import java.util.Hashtable;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.AXIS.testObjects.SignInClass;
import com.AXIS.workflows.Onboarding_Details;
import com.AXIS.workflows.SignInClass_Axis;
import com.MainFrameWork.accelerators.TestEngine;
import com.MainFrameWork.support.HtmlReportSupport;
import com.MainFrameWork.utilities.Reporter;






public class MerchantOnboarding  extends Onboarding_Details{
	
	@Test(groups= {"smoke", "functional"}, dataProvider = "getData",enabled=true)
	public void MerchantOnboarding_Test(Hashtable <String,String > data) throws Throwable {
		
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
		
			SignInClass_Axis.specificlogin("makerusername","makerpassword");
			
			result= E2EFlowNewMerchantAdditionNewFlow(data);
			
			
			
		//	sign.logout();

			if (result) 
			{
				Reporter.SuccessReport("Axis Merchant OnBoarding","Axis Merchant OnBoarding(Existing) succesfully done." );
				System.out.println("Axis Merchant OnBoarding Pass.");
			} 

			else 
			{
				Reporter.failureReport("Axis Merchant OnBoarding","Axis Merchant OnBoarding(Existing) fail." );
				Assert.assertTrue(result == true, "Test Failed");
				System.out.println("Axis Merchant OnBoarding fail.");
			}
		}
			catch (Exception e){
			Reporter.failureReport("Axis Merchant OnBoarding(Existing) fail.", "Axis Merchant OnBoarding(Existing) fail.");
			e.printStackTrace();
			Assert.assertTrue(false,"Test Failed");
			System.out.println("BFL Card Blocking/UnBlocking fail.");
			Reporter.reportStep("Error is:"+e);
		}
	
		
		
	}


	

}
