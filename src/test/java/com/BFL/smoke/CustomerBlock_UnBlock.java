package com.BFL.smoke;

import java.util.Hashtable;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.BFL.workflows.CallLogin;
import com.BFL.workflows.CardActivationDetails;
import com.BFL.workflows.CardCancellationDetails;
import com.BFL.workflows.CustomerBlock_UnblockDetails;
import com.BFL.workflows.SignInClass;
import com.MainFrameWork.accelerators.TestEngine;
import com.MainFrameWork.support.HtmlReportSupport;
import com.MainFrameWork.utilities.Reporter;



public class CustomerBlock_UnBlock  extends CustomerBlock_UnblockDetails{
	
	@Test(groups= {"smoke", "functional"}, dataProvider = "getData",enabled=true)
	public void CustomerBlock_UnBlock_Test(Hashtable <String,String > data) throws Throwable {
		
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
			
			result3 = CustomerBlock_UnblockDetails.CustBlock(data);
			
			result4=  CustomerBlock_UnblockDetails.CustUnblock(data);
			//
			
			sign.logout();

			if (result4) 
			{
				Reporter.SuccessReport("BFL Customer Blocking/UnBlocking","BFL Customer Blocking/UnBlocking succesfully done." );
				System.out.println("BFL Customer Blocking/UnBlocking pass.");
			} 

			else 
			{
				Reporter.failureReport("BFL Customer Blocking/UnBlocking ", "BFL Customer Blocking/UnBlocking succesfully done.");
				Assert.assertTrue(result == true, "Test Failed");
				System.out.println("BFL Customer Blocking/UnBlocking fail.");
			}
		}
			catch (Exception e){
			Reporter.failureReport("BFL Customer Blocking/UnBlocking succesfully done.", "BFL Customer Blocking/UnBlocking succesfully done.");
			e.printStackTrace();
			Assert.assertTrue(false,"Test Failed");
			System.out.println("BFL Customer Blocking/UnBlocking fail.");
			Reporter.reportStep("Error is:"+e);
		}
		
		
	}

	

	

}
