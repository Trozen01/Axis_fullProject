package com.AXIS.testObjects;

import org.openqa.selenium.By;

public class CallLogin_Locator {
	
	public static final By BankName = By.xpath(".//*[@id='ddlBankName']");
	public static final By CardNumber = By.xpath(".//*[@id='txtCardNumberDISP']");
	public static final By CardHolderName= By.xpath(".//*[@id='txtCardHolderName']");
	public static final By FirstName= By.xpath(".//*[@id='txtFirstName']");
	public static final By LastName= By.xpath(".//*[@id='txtLastName']");
	public static final By MotherMaidenName = By.xpath(".//*[@id='txtMotherMaidenName']");	
	public static final By AccountID = By.xpath(".//*[@id='txtAccountNo']");	
	public static final By CustomerID = By.xpath(".//*[@id='txtcustomerid']");	
	public static final By ReferenceNo = By.xpath(".//*[@id='txtRefNo']");
	public static final By ApplyreferenceNo= By.xpath(".//*[@id='txtApplyReferenceNumber']");
	public static final By CIFNumber = By.xpath(".//*[@id='txtCIFNumber']");	
	public static final By NationalID = By.xpath(".//*[@id='txtnid']");
	public static final By DateOfBirth = By.xpath(".//*[@id='txtDOB']");
	public static final By MobileNo = By.xpath(".//*[@id='txtMobileNo']");
	public static final By InBound_RadioButton= By.xpath(".//*[@id='ContentPlaceHolder1_rBtnInbound']");
	public static final By OutBound_RadioButton= By.xpath(".//*[@id='ContentPlaceHolder1_rBtnOutbound']");	
	public static final By SearchButton= By.xpath(".//*[@id='btnSearch']");
	public static final By ResetButton= By.xpath(".//*[@id='ContentPlaceHolder1_btnCancel']");	
	public static final By SubmitButton= By.xpath(".//*[@id='btnSubmit']");
	public static final By CancelButton= By.xpath(".//*[@id='ContentPlaceHolder1_btnCancel']");	
	// public static By CardNumber_RadioButton = By.name("ctl00$ContentPlaceHolder1$gvBankDetails$ctl02$rdoSel");	
	 public static final By CardNumber_RadioButton = By.name("rdbauthid");	
	 public static final By ProceedButton = By.id("btnProceed");	
	 public static final By CallLogin_Submit = By.id("ContentPlaceHolder1_btnSave");	
	 
	 public static By Cardholder_Name_checkbox = By.name("ctl00$ContentPlaceHolder1$gvBankDetails$ctl02$ChkCardHolderName");
	 public static By CustomerID_Checkbox = By.id("ContentPlaceHolder1_chkcustomerid");	
	 public static By CardNumber_Checkbox = By.id("ContentPlaceHolder1_chkcardnumber");	
	 public static By Cardexpiry_Checkbox = By.id("ContentPlaceHolder1_chkCardExpiryDate");	 
	 public static By Dateofbirth_Checkbox = By.name("ctl00$ContentPlaceHolder1$gvBankDetails$ctl02$ChkDateOfBirth");
	 public static By ContactNo_checkbox = By.name("ctl00$ContentPlaceHolder1$gvBankDetails$ctl02$ChkContactNumber");
	 public static By Email_Checkbox = By.id("ContentPlaceHolder1_chkResiEmail");
	 public static By CorporateAddress_CheckBox = By.name("ctl00$ContentPlaceHolder1$gvBankDetails$ctl02$ChkCorporateAddress");	 
	 public static By SFLP_Dateofbirth = By.name("ctl00$ContentPlaceHolder1$gvBankDetails$ctl02$ChkDateOfBirth");
	 public static By SFLP_ContactNo = By.name("ctl00$ContentPlaceHolder1$gvBankDetails$ctl02$ChkContactNumber"); 	 
	 public static By Mothermaidenname_Checkbox = By.name("ctl00$ContentPlaceHolder1$gvBankDetails$ctl02$ChkMothersMaidenName");
	 public static By MobileNo_Checkbox = By.id("ContentPlaceHolder1_chkResMobileNo");	
	 public static By CardNumber_Text= By.xpath("//*[contains(@id,'txtcardnumber')]");	 
	 public static By CardHolderName_Text= By.xpath("//*[contains(@id,'ContentPlaceHolder1_gvBankDetails_lblCardHolderName_')]");
	 public static By Address_Text= By.xpath("//*[contains(@id,'.//*[@id='ContentPlaceHolder1_gvBankDetails_lblAddress_')]");
     public static By CUstomerID_Text= By.xpath("//*[contains(@id,'ContentPlaceHolder1_txtCustomerId')]");    
     public static By DateOFBirth_Text= By.xpath("//*[contains(@id,'ContentPlaceHolder1_gvBankDetails_lblDOB_')]");	     
     public static By DateOFBirth1_Text= By.xpath("//*[contains(@id,'ContentPlaceHolder1_gvBankDetails_lblDOB_')]");
     public static By ContactNo_Text= By.xpath("//*[contains(@id,'ContentPlaceHolder1_gvBankDetails_lblContactNumber_')]");
     public static By ContactNo1_Text= By.xpath("//*[contains(@id,'ContentPlaceHolder1_gvBankDetails_lblContactNumber_')]");     
     public static By Email_Text= By.xpath("//*[contains(@id,'txtResiEmail')]");
     public static By MobileNo_Text= By.xpath("//*[contains(@id,'txtResMobileNo')]");			 
     public static By CorporateAddress_Text= By.xpath("//*[contains(@id,'ContentPlaceHolder1_gvBankDetails_lblCorpAddress_')]");
     public static By CardExpiryDate= By.xpath("//*[contains(@id,'txtCardExpiryDate')]");
     // API Testing
     
     public static By Txntype= By.xpath("//*[contains(@id,'ddlTransactionType')]");
     public static By WithoutEncryption= By.xpath("//*[contains(@id,'ContentPlaceHolder1_chkEnryptionFlag')]");
     public static final By PostUrl= By.xpath(".//*[@id='ContentPlaceHolder1_txtPostURL']"); 
     public static final By ClearJSON= By.xpath(".//*[@id='ContentPlaceHolder1_txtClearJSON']"); 	
     public static final By ApiSubmit= By.xpath(".//*[@id='Button1']"); 
     public static final By Response= By.xpath(".//*[@id='ContentPlaceHolder1_txtResponse']");
     
     
     
     
     
		
}
