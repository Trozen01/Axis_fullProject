package com.BFL.testObjects;

import org.openqa.selenium.By;

public class OnlineOffLineCall_Locator {	
	
	
	public static final By AuthorizationBlocking = By.xpath("//a[contains(text(),'Authorization Blocking')]");
	public static final By AuthorizationCancellation = By.xpath("//a[contains(text(),'Authorization Cancellation')]");
	public static final By CardActivation = By.xpath("//a[contains(text(),'Card Activation')]");
	public static final By CardBlacklisting = By.xpath("//a[contains(text(),'Card Blacklisting')]");	
	public static final By CardBlockRequest = By.xpath("//a[contains(text(),'Card Block Request')]");
	public static final By CardUnBlockRequest = By.xpath("//a[contains(text(),'Card Unblock Request')]");
	
	public static final By CustomerBlockRequest = By.xpath("//a[contains(text(),'Customer Block Request')]");
	public static final By CustomerUnBlockRequest = By.xpath("//a[contains(text(),'Customer Unblock Request')]");
	public static final By CardCancellationWithRefundLodha = By.xpath("//a[contains(text(),'Card Cancellation With Refund Lodha')]");	
	public static final By CardCancellationWithFullRefund = By.xpath("//a[contains(text(),'Card Type Master')]");
	public static final By CardHotlisting = By.xpath("//a[contains(text(),'Card Hotlisting')]");	
	public static final By AccountBlockRequest = By.xpath("//a[contains(text(),'Account Block Request')]");
	public static final By AccountUnBlockRequest = By.xpath("//a[contains(text(),'Account Unblock Request')]");
	
	public static final By CardHotlistingWithCardReplacement = By.xpath("//a[contains(text(),'Authorization Blocking')]");
	public static final By Cardnotpresentunblocking = By.xpath("//a[contains(text(),'Card Type Master')]");
	public static final By CardReload = By.xpath("//a[contains(text(),'Card Type Master')]");
	public static final By CardReplacementWithFee = By.xpath("//a[contains(text(),'Card Type Master')]");	
	public static final By Cashlimitchange = By.xpath("//a[contains(text(),'Cash Limit Change')]");
	public static final By DailyLimit = By.xpath("//a[contains(text(),'Card Type Master')]");
	public static final By InterestHolidayOnline = By.xpath("//a[contains(text(),'Card Type Master')]");	
	public static final By InternationalAuthLimit = By.xpath("//a[contains(text(),'International Auth Limit')]");
	public static final By InternationalSmsActivationAndDeactivation = By.xpath("//a[contains(text(),'Card Type Master')]");	
	
	public static final By InstaReplacement = By.xpath("//a[contains(text(),'Authorization Blocking')]");
	public static final By KYCUpgradation_Online = By.xpath("//a[contains(text(),'Card Type Master')]");
	public static final By LICBulkPayment = By.xpath("//a[contains(text(),'Card Type Master')]");
	public static final By LanguagePreference = By.xpath("//a[contains(text(),'Language Preference'");	
	public static final By LosttoTemporartyCardTransfer = By.xpath("//a[contains(text(),'Card Type Master')]");
	public static final By PartialRefund = By.xpath("//a[contains(text(),'Card Type Master')]");
	public static final By CustomerlimitEnhancement = By.xpath("//a[contains(text(),'Customer Limit Enhancement')]");
	public static final By AccountlimitEnhancement = By.xpath("//a[contains(text(),'Account Limit Enhancement')]");	
	public static final By PermanentLimitReduction = By.xpath("//a[contains(text(),'Card Type Master')]");
	public static final By PinUnblockingequest = By.xpath("//a[contains(text(),'Card Type Master')]");	
	
	//public static final By TransactionChecker_online=By.xpath("//a[contains(text(),'Transaction Checker-Online Call]'");
	public static final By TransactionChecker_online=By.linkText("Transaction Checker-Online Call");
	public static final By AutoDebitDetailsChange=By.linkText("Auto debit details change");
	public static final By VIPNormalFlagDetails=By.xpath("//a[contains(text(),'VIP Normal Flag Updation')]");
	public static final By TemporaryLimitEnhancement=By.xpath("//a[contains(text(),'Temporary Limit Enhancement')]");
	//public static final By LanguagePreferenceChange=By.xpath("//a[contains(text(),'Language Preference']");
	public static final By BulkAuthorizationcancellation=By.xpath("//a[contains(text(),'Bulk Authorization Cancellation')]");
	//Re-Activate
	public static final By Reactivate=By.xpath("//a[contains(text(),'Re-Activate')]");
	
	public static final By PDChangeRequest = By.xpath("//a[contains(text(),'Personal Detail Change Request')]");
	public static final By PDChangeChecker = By.xpath("//a[contains(text(),'Personal Detail Checker')]");
	public static final By PDChangeSendApprove = By.id("ContentPlaceHolder1_btnSave");
	public static final By AvailableBalanceChange = By.xpath("//a[contains(text(),'Available Balance Change')]");	
	public static final By TempLimitChange = By.xpath("//a[contains(text(),'Temporary Limit Change')]");	
	public static final By VipNopVIPChange = By.xpath("//a[contains(text(),'VIP / Non VIP Request')]");	
	//public static final By ServiceClient = By.xpath("//a[contains(text(),'Service Client')]");
	public static final By ServiceClient = By.linkText("Service Client");
	
}  
