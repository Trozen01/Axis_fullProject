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
import com.MainFrameWork.accelerators.TestEngine;
import com.MainFrameWork.support.HtmlReportSupport;
import com.MainFrameWork.utilities.Reporter;



public class AvailableBalanceChange  extends AvailableBalanceChange_Details{
	
	@Test(groups= {"smoke", "functional"}, dataProvider = "getData",enabled=true)
	public void AvailableBalanceChange_Test(Hashtable <String,String > data) throws Throwable {
		
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
			
			GetLimitDB(data);
			
			result3 = AvailableBalanceChange_Details.BalanceChange(data);
			
			//result4=  PersonalDetailsChangeChecker_Details.CheckerApproval(data);
			
			sign.logout();

			if (result3) 
			{
				Reporter.SuccessReport("AvailableBalanceChange_ request validations","AvailableBalanceChange_ request succesfully done." );
				System.out.println("AvailableBalanceChange_ request passed.");
			} 

			else 
			{
				Reporter.failureReport("AvailableBalanceChange_ request ", "AvailableBalanceChange_ request failed.");
				Assert.assertTrue(result == true, "Test Failed");
				System.out.println("AvailableBalanceChange_ request failed.");
			}
		}
			catch (Exception e){
			Reporter.failureReport("AvailableBalanceChange_ request.", "AvailableBalanceChange_ request failed.");
			e.printStackTrace();
			Assert.assertTrue(false,"Test Failed");
			
			Reporter.reportStep("Error is:"+e);
			System.out.println("AvailableBalanceChange_ request failed.");
		}
		
		
	}

	

	

}
