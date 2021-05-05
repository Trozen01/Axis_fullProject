package com.BFL.smoke;

import java.util.Hashtable;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.BFL.workflows.AccountBlock_UnblockDetails;
import com.BFL.workflows.CallLogin;

import com.BFL.workflows.CustomerLimitEnhancement_Details;
import com.BFL.workflows.SignInClass;
import com.MainFrameWork.accelerators.TestEngine;
import com.MainFrameWork.support.HtmlReportSupport;
import com.MainFrameWork.utilities.Reporter;



public class CustomerLimitEnhancement  extends CustomerLimitEnhancement_Details{
	
	@Test(groups= {"smoke", "functional"}, dataProvider = "getData",enabled=true)
	public void CustomerLimitEnhancement_Test(Hashtable <String,String > data) throws Throwable {
		
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
			
			result =CallLogin.CallLoggin_Details(data);
			
			result3 = CustomerLimitEnhancement_Details.CustLimitEnhancement(data);
			
			result4= CustomerLimitEnhancement_Details.AccountLimitEnhancement(data);
			
			sign.logout();

			if (result3) 
			{
				Reporter.SuccessReport("BFL Customer Limit Enhancement","BFL Customer Limit Enhancement succesfully done." );
				System.out.println("BFL Customer Limit Enhancement pass.");
			} 

			else 
			{
				Reporter.failureReport("BFL Customer Limit Enhancement ", "BFL Customer Limit Enhancement failed.");
				Assert.assertTrue(result == true, "Test Failed");
				System.out.println("BFL Customer Limit Enhancement fail.");
			}
		}
			catch (Exception e){
			Reporter.failureReport("BFL Customer Limit Enhancement succesfully done.", "BFL Customer Limit Enhancement succesfully done.");
			e.printStackTrace();
			Assert.assertTrue(false,"Test Failed");
			System.out.println("BFL Customer Limit Enhancement fail.");
			Reporter.reportStep("Error is:"+e);
		}
		
		
	}

	

	

}
