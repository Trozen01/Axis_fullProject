package com.BFL.smoke;

import java.util.Hashtable;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.BFL.workflows.CallLogin;
import com.BFL.workflows.CardActivationDetails;
import com.BFL.workflows.CardCancellationDetails;
import com.BFL.workflows.CustomerBlock_UnblockDetails;
import com.BFL.workflows.PersonalDetailsChangeChecker_Details;
import com.BFL.workflows.SignInClass;
import com.MainFrameWork.accelerators.TestEngine;
import com.MainFrameWork.support.HtmlReportSupport;
import com.MainFrameWork.utilities.Reporter;



public class PersonalDetailsChange_Checker  extends PersonalDetailsChangeChecker_Details{
	
	@Test(groups= {"smoke", "functional"}, dataProvider = "getData",enabled=true)
	public void PersonalDetailsChange_Checker_Test(Hashtable <String,String > data) throws Throwable {
		
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
			
			result3 = PersonalDetailsChangeChecker_Details.ChangeDetails(data);
			
			result4=  PersonalDetailsChangeChecker_Details.CheckerApproval(data);
			
			sign.logout();

			if (result4) 
			{
				Reporter.SuccessReport("Personal details change/checker request validations","Personal details change/checker request succesfully done." );
				System.out.println("Personal details change/checker pass.");
			} 

			else 
			{
				Reporter.failureReport("Personal details change/checker request ", "Personal details change/checker request failed.");
				Assert.assertTrue(result == true, "Test Failed");
				System.out.println("Personal details change/checker fail.");
			}
		}
			catch (Exception e){
			Reporter.failureReport("Personal details change/checker request.", "Personal details change/checker request failed.");
			e.printStackTrace();
			Assert.assertTrue(false,"Test Failed");
			System.out.println("Personal details change/checker fail.");
			Reporter.reportStep("Error is:"+e);
		}
		
		
	}

	

	

}
