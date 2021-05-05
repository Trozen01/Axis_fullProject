package com.BFL.smoke;

import java.util.Hashtable;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.BFL.workflows.CallLogin;
import com.BFL.workflows.CardActivationDetails;
import com.BFL.workflows.CardCancellationDetails;
import com.BFL.workflows.SignInClass;
import com.MainFrameWork.accelerators.TestEngine;
import com.MainFrameWork.support.HtmlReportSupport;
import com.MainFrameWork.utilities.Reporter;



public class CardActivation  extends CardActivationDetails{
	
	@Test(groups= {"smoke", "functional"}, dataProvider = "getData",enabled=true)
	public void CardBlock_UnBlock_Test(Hashtable <String,String > data) throws Throwable {
		
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
			
			result3 = CardCancellationDetails.CardCancellation(data);
			
			result4=  CardCancellationDetails.CardActivation(data);
			
			sign.logout();

			if (result4) 
			{
				Reporter.SuccessReport("BFL Card Blocking/UnBlocking","BFL Card Blocking/UnBlocking succesfully done." );
				System.out.println("BFL Card Blocking/UnBlocking pass.");
			} 

			else 
			{
				Reporter.failureReport("BFL Card Blocking/UnBlocking ", "BFL Card Blocking/UnBlocking succesfully done.");
				Assert.assertTrue(result == true, "Test Failed");
				System.out.println("BFL Card Blocking/UnBlocking fail.");
			}
		}
			catch (Exception e){
			Reporter.failureReport("BFL Card Blocking/UnBlocking succesfully done.", "BFL Card Blocking/UnBlocking succesfully done.");
			e.printStackTrace();
			Assert.assertTrue(false,"Test Failed");
			System.out.println("BFL Card Blocking/UnBlocking fail.");
			Reporter.reportStep("Error is:"+e);
		}
		
		
	}

	

	

}
