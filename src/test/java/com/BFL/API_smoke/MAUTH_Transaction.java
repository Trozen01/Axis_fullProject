package com.BFL.API_smoke;

import java.util.Hashtable;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.BFL.API_workflows.CustomerOnboardingAPI_Details;
import com.BFL.API_workflows.MAUTH_TransactionAPI_Details;
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



public class MAUTH_Transaction  extends MAUTH_TransactionAPI_Details{
	
	@Test(groups= {"smoke", "functional"}, dataProvider = "getData",enabled=true)
	public void MAUTH_Transaction_Test(Hashtable <String,String > data) throws Throwable {
		
		
		try
		{
			boolean result = false;
			boolean result1 = false;
			boolean result3 = false;
			boolean result4 = false;
			boolean result5 = false;
			boolean result6 = false;
			
			TestEngine.testDescription.put(HtmlReportSupport.tc_name,"");
			System.out.println("Usertype - "+ data.get("Usertype"));
				 
			SignInClass sign = new SignInClass();
			HtmlReportSupport.reportStep(data.get("TestCoverage"));
		
			SignInClass.specificlogin("makerusername","makerpassword");
			
			NavigateToServiceClient.ServiceClient(data);
			
			GetPrvMauthDB(data);
			
			result3 = CreateMauthJSON(data);
			
			result4= MauthOnboardingDB(data);
			
			result5= CancelMauthJSON(data);
			
			result6= CancelMauthTransactionDB(data);
			
			//GETTotalAmountMasterDB();
			
			sign.logout();

			if (result6) 
			{
				Reporter.SuccessReport("MAUTH API Customer Onboarding","MAUTH API Customer Onboarding succesfully done." );
				System.out.println("MAUTH API Customer Onboarding passed.");
			} 

			else 
			{
				Reporter.failureReport("MAUTH API Customer Onboarding ", "MAUTH API Customer Onboarding failed..");
				Assert.assertTrue(result == true, "Test Failed");
				System.out.println("MAUTH API Customer Onboarding failed.");
			}
		}
			catch (Exception e){
			Reporter.failureReport("MAUTH API Customer Onboarding succesfully done.", "MAUTH API Customer Onboarding Failed.");
			e.printStackTrace();
			Assert.assertTrue(false,"Test Failed");
			System.out.println("MAUTH API Customer Onboarding failed.");
			Reporter.reportStep("Error is:"+e);
		}
		
		
	}

	

	

}
