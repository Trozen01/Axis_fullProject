package com.BFL.smoke;

import java.util.Hashtable;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.BFL.workflows.AccountBlock_UnblockDetails;
import com.BFL.workflows.CallLogin;
import com.BFL.workflows.CardActivationDetails;
import com.BFL.workflows.CardCancellationDetails;
import com.BFL.workflows.CustomerBlock_UnblockDetails;
import com.BFL.workflows.SignInClass;
import com.MainFrameWork.accelerators.TestEngine;
import com.MainFrameWork.support.HtmlReportSupport;
import com.MainFrameWork.utilities.Reporter;



public class AccountBlock_UnBlock  extends AccountBlock_UnblockDetails{
	
	@Test(groups= {"smoke", "functional"}, dataProvider = "getData",enabled=true)
	public void AccountBlock_UnBlock_Test(Hashtable <String,String > data) throws Throwable {
		
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
			
			result3 = AccountBlock_UnblockDetails.AccountBlock(data);
			
			result4=  AccountBlock_UnblockDetails.AccountUnblock(data);
			
			sign.logout();

			if (result4) 
			{
				Reporter.SuccessReport("BFL Account Blocking/UnBlocking","BFL Account Blocking/UnBlocking succesfully done." );
				System.out.println("BFL Account Blocking/UnBlocking passed.");
			} 

			else 
			{
				Reporter.failureReport("BFL Account Blocking/UnBlocking ", "BFL Account Blocking/UnBlocking succesfully done.");
				Assert.assertTrue(result == true, "Test Failed");
				System.out.println("BFL Account Blocking/UnBlocking failed.");
			}
		}
			catch (Exception e){
			Reporter.failureReport("BFL Account Blocking/UnBlocking succesfully done.", "BFL Account Blocking/UnBlocking succesfully done.");
			e.printStackTrace();
			Assert.assertTrue(false,"Test Failed");
			System.out.println("BFL Account Blocking/UnBlocking failed.");
			Reporter.reportStep("Error is:"+e);
		}
		
		
	}

	

}
