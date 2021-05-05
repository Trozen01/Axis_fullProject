package com.BFL.testObjects;

import org.openqa.selenium.By;

public class CardCancellation_Locator {	
	
	
	public static final By MessageText = By.xpath(".//*[@id='ContentPlaceHolder1_diverror']/div");
	public static final By RequestDate = By.xpath(".//*[@id='ContentPlaceHolder1_txtReqDate']");
	public static final By Time = By.xpath(".//*[@id='ContentPlaceHolder1_txtReqTime']");	
	public static final By CardNumber = By.xpath(".//*[@id='ContentPlaceHolder1_txtCardNumber']");	
	public static final By CardHolderName = By.xpath(".//*[@id='ContentPlaceHolder1_txtCardHolderName']");
	public static final By BlockCategory=By.xpath("//*[@id='ddlBlockCategory']");
	public static final By Instruction = By.xpath(".//*[@id='txtInstructions']");	
	public static final By MobileNo = By.xpath(".//*[@id='txtTelephone']"); 
	public static final By SendRequestButton = By.xpath(".//*[@id='ContentPlaceHolder1_btnSave']");
	public static final By SendRequestButton_new = By.xpath(".//*[@id='ContentPlaceHolder1_btnSendRequest']");
	
	public static final By ReturnButton = By.xpath(".//*[@id='ContentPlaceHolder1_btnCancel']");
	public static final By BlockCode=By.xpath("//*[@id='ddlblocktype']");
	public static final By ChannelType=By.xpath("//*[@id='ddlChannelType']");
	
	public static final By AccountId=By.xpath("//*[@id='ContentPlaceHolder1_ddlAccountID']");
	public static final By CardNumber_drp=By.xpath("//*[@id='ContentPlaceHolder1_ddlcardnumbers']");
	public static final By UnblockReason=By.xpath("//*[@id='ddlReason']"); 
	public static final By CustUnblockReason=By.xpath("//*[@id='ddlReasonUnblock']");
	public static final By AccountUnblockReason=By.xpath("//*[@id='ddlReason']");  
	
	
	
    public static final By CallResult = By.xpath(".//*[@id='ContentPlaceHolder1_lblCallHistory']");
	public static final By CardMessageText = By.xpath(".//*[@id='ContentPlaceHolder1_divErrorModal']/div");
	public static final By CallReferenceNO = By.xpath(".//*[@id='ContentPlaceHolder1_txtRefNo']");
	public static final By RESPONSE = By.xpath(".//*[@id='ContentPlaceHolder1_txtDispText']");	
	public static final By ApprovalCode = By.xpath(".//*[@id='ContentPlaceHolder1_txtApprovalCode']");
	public static final By RRNumber = By.xpath(".//*[@id='ContentPlaceHolder1_txtRRNo']");
	public static final By CloseButton = By.xpath(".//*[@id='btnClose']");
	
	public static final By CardStatus=By.xpath("//*[@id='ContentPlaceHolder1_lblCardStatus']");
	public static final By NewLimit=By.xpath("//*[@id='txtNewLimit']");
	public static final By CurrentLimit=By.xpath("//*[@id='txtCurrentLimit']");
	public static final By ResMobileNumber = By.xpath(".//*[@id='ContentPlaceHolder1_txtResidenceMobileNumber']");
	public static final By ResEmail = By.xpath(".//*[@id='ContentPlaceHolder1_txtResidenceEmailId']");
	public static final By OffMobileNumber = By.xpath(".//*[@id='ContentPlaceHolder1_txtOfficeMobileNumber']");
	public static final By OffEmail = By.xpath(".//*[@id='ContentPlaceHolder1_txtOfficeEmailId']");  
	public static final By Submit = By.xpath(".//*[@id='ContentPlaceHolder1_btn_submit']");  
	public static final By Closepopup = By.xpath(".//*[@id='ContentPlaceHolder1_btnHide']");  
	
	public static final By NewAdjAmt = By.xpath(".//*[@id='txtNewLimit']"); 
	public static final By ProcessDeviationEnddate = By.xpath(".//*[@id='ContentPlaceHolder1_txtDevEndDate']");
	public static final By TempLimitStartDate = By.xpath(".//*[@id='ContentPlaceHolder1_txtTempStartDate']");
	public static final By TempLimitEndDate = By.xpath(".//*[@id='ContentPlaceHolder1_txtTempEndDate']");
	public static final By VipLevel = By.xpath(".//*[@id='ContentPlaceHolder1_ddlLevel']");
	public static final By VipFlag = By.xpath(".//*[@id='ddlVIPFlag']");
	public static final By EndDate = By.xpath(".//*[@id='txtEndDate']");
	
	public static final By PopUp = By.xpath(".//*[@id='ContentPlaceHolder1_lblPopUp']");
	
	

}
