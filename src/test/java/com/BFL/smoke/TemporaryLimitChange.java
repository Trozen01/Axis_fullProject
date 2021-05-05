package com.BFL.smoke;

import java.util.Hashtable;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.BFL.workflows.AvailableBalanceChange_Details;
import com.BFL.workflows.CallLogin;
import com.BFL.workflows.CardActivationDetails;
import com.BFL.workflows.CardCancellationDetails;
import com.BFL.workflows.CustomerBlock_UnblockDetails;
import com.BFL.workflows.PersonalDetailsChangeChecker_Details;
import com.BFL.workflows.SignInClass;
import com.BFL.workflows.TemoraryLimitChange_Details;
import com.MainFrameWork.accelerators.TestEngine;
import com.MainFrameWork.support.HtmlReportSupport;
import com.MainFrameWork.utilities.Reporter;



public class TemporaryLimitChange  extends TemoraryLimitChange_Details{
	
	@Test(groups= {"smoke", "functional"}, dataProvider = "getData",enabled=true)
	public void TemporaryLimitChange_Test(Hashtable <String,String > data) throws Throwable {
		
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
			
			GetTempLimitDB(data);
			
			result3 = TemoraryLimitChange_Details.TempLimitChange(data);
			
			sign.logout();

			if (result3) 
			{
				Reporter.SuccessReport("Temporary Limit Change request validations","Temporary Limit Change request succesfully done." );
				System.out.println("Temporary Limit Change pass.");
			} 

			else 
			{
				Reporter.failureReport("Temporary Limit Change request validations", "Temporary Limit Change request failed.");
				Assert.assertTrue(result == true, "Test Failed");
				System.out.println("Temporary Limit Change fail.");
			}
		}
			catch (Exception e){
			Reporter.failureReport("Temporary Limit Change request validations.", "Temporary Limit Change request validations failed.");
			e.printStackTrace();
			Assert.assertTrue(false,"Test Failed");
			System.out.println("Temporary Limit Change fail.");
			Reporter.reportStep("Error is:"+e);
		}
		
		
	}

	

	

}
