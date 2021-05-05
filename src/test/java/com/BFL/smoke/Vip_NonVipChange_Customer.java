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
import com.BFL.workflows.VipNonVipChangeCust_Details;
import com.BFL.workflows.VipNonVipChange_Details;
import com.MainFrameWork.accelerators.TestEngine;
import com.MainFrameWork.support.HtmlReportSupport;
import com.MainFrameWork.utilities.Reporter;



public class Vip_NonVipChange_Customer  extends VipNonVipChangeCust_Details{
	
	@Test(groups= {"smoke", "functional"}, dataProvider = "getData",enabled=true)
	public void Vip_NonVipChange_Customer_Test(Hashtable <String,String > data) throws Throwable {
		
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
			
			GetVipORNonVipDB(data);
			
			String status=data.get("CLI_SMT_FLAG");
			
			if(status.contains("V")) {
				
				result3 = VipNonVipChangeCust_Details.VIPto_NonVip_SMT_VIPChange(data);
			}else if (status.contains("N")) {
				result3 = VipNonVipChangeCust_Details.NonVipto_VIP_SMT_NonVIPChange(data);
				
			}else if (status.contains("S")) {
				result3 = VipNonVipChangeCust_Details.SMTto_VIP_NonVIP_SMTChange(data);
			}
			
			
			
			
			
			sign.logout();

			if (result3) 
			{
				Reporter.SuccessReport("Vip_NonVipChange_Customer request validations","Vip_NonVipChange_Customer request succesfully done." );
				System.out.println("Vip_NonVipChange_Customer request pass.");
			} 

			else 
			{
				Reporter.failureReport("Vip_NonVipChange_Customer request validations", "Vip_NonVipChange_Customer Change request failed.");
				Assert.assertTrue(result == true, "Test Failed");
				System.out.println("Vip_NonVipChange_Customer request fail.");
			}
		}
			catch (Exception e){
			Reporter.failureReport("Vip_NonVipChange_Customer request validations.", "Vip_NonVipChange_Customer request validations failed.");
			e.printStackTrace();
			Assert.assertTrue(false,"Test Failed");
			System.out.println("Vip_NonVipChange_Customer request fail.");
			Reporter.reportStep("Error is:"+e);
		}
		
		
	}

	

	

}
