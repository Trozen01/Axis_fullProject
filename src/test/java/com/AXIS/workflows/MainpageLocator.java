package com.AXIS.workflows;

import org.openqa.selenium.By;

public class MainpageLocator {
	
	// rohan data
//public static final By MerchantOnboarding = By.xpath("//a[contains(text(),'Merchant Onboarding')]");commented by rupali
public static final By MerchantOnboarding = By.xpath("//a[contains(text(),'Merchant Onboarding')]");
//public static final By AddMerchantProfile = By.xpath("//a[contains(text(),'Add Merchant Profile')]");
public static final By AddMerchantProfile = By.linkText("Add Merchant Profile");// Add Merchant Profile, commented by rupali
//public static final By AddMerchantProfile = By.xpath("//*[@id=6]/ul/li[15]/a");// Add Merchant Profile, 
public static final By MerchantDetailsLable = By.xpath(".//*[@id='ContentPlaceHolder1']");
public static final By ManageMerchant =By.linkText("Manage Merchant");
//public static final By Dashboard = By.linkText("DashBoard"); 
public static final By MakerDashboard = By.linkText("Dashboard Maker"); 
//public static final By ManageMCR =  By.xpath("(//a[contains(text(),'Manage MCR')])[2]");    
public static final By ManageMCR =  By.linkText("Manage MCR");     
public static final By ManageAggregator =  By.linkText("Manage Aggregator"); 
public static final By AddAggregator =  By.id("ContentPlaceHolder1_lnkAddAgr"); 









// new axis
public static final By MerchantId =  By.xpath(".//*[@id='ContentPlaceHolder1_drp_merchantIdentification']");
//public static final By AddMerchantProfile = By.xpath("//a[contains(text(),'Add Merchant Profile')]");
//new axis
public static final By OldMID =  By.xpath(".//*[@id='txt_oldMID']");



public static final By Reamrks =  By.id("ContentPlaceHolder1_txtsalesremark");
public static final By Submit =  By.id("ContentPlaceHolder1_btnsubmit");
public static final By MerchantCodeMsg =  By.id("ContentPlaceHolder1_lblmsgsuccess");


// checker screen
public static final By Dashboard =  By.linkText("Dashboard"); 
public static final By chk_Mid =  By.id("ContentPlaceHolder1_txtRequestId");  
public static final By chk_status =  By.id("ContentPlaceHolder1_drpStatus");
public static final By chk_search =  By.id("ContentPlaceHolder1_btn_search"); 
public static final By chk_remark =  By.id("ContentPlaceHolder1_txtremarks"); 
public static final By chk_selectstatus =  By.id("ContentPlaceHolder1_ddlStatus"); 

public static final By chk_assetmgmt =  By.linkText("Asset Management");
public static final By chk_merappr =  By.partialLinkText("INST MERCHANT APPROVAL");

public static final By inst_mecode =  By.id("txt_mid");
public static final By inst_institution =  By.id("ContentPlaceHolder1_drp_Institution");
public static final By inst_apprStatus =  By.id("ContentPlaceHolder1_drp_Approvalstatus");
public static final By inst_Search =  By.id("ContentPlaceHolder1_btn_search");

//old data	
public static final By AdminAndsetUp = By.xpath("//a[contains(text(),'Admin and Setup')]");
public static final By MasterSetUp = By.xpath("//a[contains(text(),'Master Setup')]");
public static final By CallLogging = By.xpath("//a[contains(text(),'Call Logging')]");
public static final By Reports = By.xpath("//a[contains(text(),'Reports')]");
public static final By BatchProcessing = By.xpath("//a[contains(text(),'Batch Processing')]");
public static final By ISOTransaction = By.xpath("//a[contains(text(),'ISOTransaction')]");
public static final By IssusanceActivities = By.xpath("//a[contains(text(),'Issuance Activities')]");
public static final By BulkActivity = By.xpath("//a[contains(text(),'Bulk Activity')]");
public static final By WlAdmin = By.xpath("//a[contains(text(),'WL admin')]");
public static final By ChangePassword = By.xpath(".//*[@id='lnkChangePwd']");
public static final By Logout = By.xpath(".//*[@id='lnkLogout']");
//==========================Admin setup======================================
public static final By Menuconfigration = By.xpath("//a[contains(text(),'Menu Configuration')]");
public static final By RoleMaster = By.xpath("//a[contains(text(),'Role Master')]");
public static final By CorporateBankSetup = By.xpath("//a[contains(text(),'Corporate Bank Setup')]");
public static final By EmailConfigration = By.xpath("//a[contains(text(),'Email Configurations')]");
public static final By UserSetup = By.xpath("//a[contains(text(),'User Setup')]");

//==========================Master setup======================================
public static final By Currency = By.xpath("//a[contains(text(),'Currency')]");
public static final By Country = By.xpath("//a[contains(text(),'Country')]");
public static final By State = By.xpath("//a[contains(text(),'State')]");
public static final By City = By.xpath("//a[contains(text(),'City')]");
public static final By Departments = By.xpath("//a[contains(text(),'Departments')]");
public static final By CancelReason = By.xpath("//a[contains(text(),'Cancel Reasons')]");
public static final By CallTypes = By.xpath("//a[contains(text(),'Call Types')]");
public static final By BankFeeMapping = By.xpath("//a[contains(text(),'Bank Fee Mapping')]");

public static final By CardTypeMaster = By.xpath("//a[contains(text(),'Card Type Master')]");
public static final By VariantMaster = By.xpath("//a[contains(text(),'Variant Master')]");
public static final By CardUpgradationMatrix = By.xpath("//a[contains(text(),'Card Upgradation Matrix')]");
public static final By ServiceTypes = By.xpath("//a[contains(text(),'Service Types')]");
public static final By ServiceErrorTypes = By.xpath("//a[contains(text(),'Service Error Types')]");
public static final By KeyConfigration = By.xpath("//a[contains(text(),'Key Configuration')]");
public static final By RequestResponceConfigration = By.xpath("//a[contains(text(),'Request Response Configuration')]");
public static final By SMSTextConfigration = By.xpath("//a[contains(text(),'SMS Text Configuration')]");
public static final By MailConfigration = By.xpath("//a[contains(text(),'Mail Configuration')]");
public static final By TxnMaster = By.xpath("//a[contains(text(),'TXN Master')]");
public static final By DynamicQuizzing = By.xpath("//a[contains(text(),'Dynamic Quizzing')]");
public static final By BankwiseDQMapping = By.xpath("//a[contains(text(),'Bank Wise DQ Mapping')]");
public static final By EmailParameter = By.xpath("//a[contains(text(),'Email Parameter')]");
public static final By ImageMasterUpload = By.xpath("//a[contains(text(),'Image Master Upload')]");
public static final By EmiParameter = By.xpath("//a[contains(text(),'EMI Parameters')]");
public static final By BonusPointRedumption = By.xpath("//a[contains(text(),'Bonus Point Redemption')]");
public static final By EmailAlertConfigration = By.xpath("//a[contains(text(),'Email Alert Configuration')]");

public static final By SSoKeysetting = By.xpath("//a[contains(text(),'SSO Key Setting')]");
public static final By PurgingParameterConfigration = By.xpath("//a[contains(text(),'Purging Parameter Configuration')]");
public static final By IVRKeyConfigration = By.xpath("//a[contains(text(),'IVR Key Configuration')]");

public static final By SecurityConfigration = By.xpath("//a[contains(text(),'Security configuration')]");
public static final By RoleAndCallTypeMapping = By.xpath("//a[contains(text(),'Role & Call Type Mapping')]");
public static final By CustomerSegmentMaster = By.xpath("//a[contains(text(),'Customer Segment Master')]");
public static final By CashBackConfigration = By.xpath("//a[contains(text(),'Cash Back Configuration')]");



public static final By CardValidationParam = By.xpath("//a[contains(text(),'Card Validation Param')]");
public static final By CallRequestAccess = By.xpath("//a[contains(text(),'Call Request Access Parameter')]");
public static final By MiscparameterConfig = By.xpath("//a[contains(text(),'Misc Parameters Config')]");
public static final By CarddegradationMatrix = By.xpath("//a[contains(text(),'Card Degradation Matrix')]");


//==========================Call Logging setup======================================
public static final By Inquiries = By.xpath("//a[contains(text(),'Inquiries')]");
public static final By CallComment = By.xpath("//a[contains(text(),'Call Comments')]");
public static final By CallCentreCheck = By.xpath("//a[contains(text(),'Call Center Check')]");
public static final By CPUCheckScreen = By.xpath("//a[contains(text(),'CPU Check Screens')]");
public static final By BranchCodeChange = By.xpath("//a[contains(text(),'BranchCodechnage']");

public static final By GeneralInquiries = By.xpath("//a[contains(text(),'General Inquiries')]");
public static final By Uploadimage = By.xpath("//a[contains(text(),'Upload Images')]");
public static final By CardInformation = By.xpath("//a[contains(text(),'card Information')]");
public static final By CardMasking = By.xpath("//a[contains(text(),'Card Masking')]");
public static final By CallsFeedBack = By.xpath("//a[contains(text(),'Calls Feedback')]");


public static final By CallBackManagement = By.xpath("//a[contains(text(),'Call Back Management')]");
public static final By UpdateNICNO = By.xpath("//a[contains(text(),'Update NIC Number')]");
public static final By CallCentreCheckOffice = By.xpath("//a[contains(text(),'Call Center Check Offline')]");

//==========================Report setup======================================
public static final By UserWiseCallReport = By.xpath("//a[contains(text(),'User Wise Call Reports')]");
public static final By TatReports = By.xpath("//a[contains(text(),'TAT Reports')]");
public static final By AuditTrailReport = By.xpath("//a[contains(text(),'Audit Trail Report')]");
public static final By TransactionStatementReports = By.xpath("//a[contains(text(),'Transaction Statement Report')]");
public static final By OnlineTxnRejectionRPT = By.xpath("//a[contains(text(),'Online Txn Rejection Rpt')]");

public static final By EODFileProcessReport = By.xpath("//a[contains(text(),'EOD FileProcessed Report')]");
public static final By TotalCardReport = By.xpath("//a[contains(text(),'Total Card Report')]");
public static final By IPGTransactionLog = By.xpath("//a[contains(text(),'IPG Transaction Log')]");
public static final By Auditreport = By.xpath("//a[contains(text(),'Audit Report - Services')]");
public static final By ServiceRequest = By.xpath("//a[contains(text(),'Service Request-Response')]");


public static final By DBCRAdjustmentReports = By.xpath("//a[contains(text(),'DB/CR Adjustment Reports')]");
public static final By Userwisereport = By.xpath("//a[contains(text(),'User Wise Call Reports')]");
public static final By WLCMSserviceAuditReports = By.xpath("//a[contains(text(),'WLCMS Service Audit Report')]");

public static final By SmsAuditreport = By.xpath("//a[contains(text(),'SMS Audit Report')]");
public static final By Labelreports = By.xpath("//a[contains(text(),'Lable Reports')]");
public static final By CallHistoryReport = By.xpath("//a[contains(text(),'Call History Report')]");
public static final By CombinedReport = By.xpath("//a[contains(text(),'Combined Report')]");
public static final By InactiveUserReport = By.xpath("//a[contains(text(),'Inactive User Report')]");



public static final By ManualReport = By.xpath("//a[contains(text(),'Manual Report')]");
public static final By UserReport = By.xpath("//a[contains(text(),'User Report')]");
public static final By AuthorazationReport = By.xpath("//a[contains(text(),'Authorization Report')]");


public static final By ServiceMonitor = By.xpath("//a[contains(text(),'Service Monitoring')]");
public static final By EmailAuditReport = By.xpath("//a[contains(text(),'Email Audit Report')]");
public static final By AmlReportDownload = By.xpath("//a[contains(text(),'AML Report Download')]");

public static final By PinPrintingReport = By.xpath("//a[contains(text(),'Pin Printing Report')]");
public static final By BillingReport = By.xpath("//a[contains(text(),'Billing Report')]");
public static final By CallBackReport = By.xpath("//a[contains(text(),'Call Back Report')]");
public static final By TransactionReport = By.xpath("//a[contains(text(),'Transaction Report')]");
public static final By AuthReport = By.xpath("//a[contains(text(),'Auth Report']");


public static final By InstantPingenerationReport = By.xpath("//a[contains(text(),'Instant Pin Generation Report')]");
public static final By CAFFileReconReport = By.xpath("//a[contains(text(),'CAF File Recon Report')]");
public static final By DiscountCashReport = By.xpath("//a[contains(text(),'Discount Cash Report')]");


public static final By PendingCardReport = By.xpath("//a[contains(text(),'Pending Card Report')]");
public static final By DispatchDailyReport = By.xpath("//a[contains(text(),'Dispatch Daily Report')]");

//======================Batch Processing=======================================

public static final By MagnusFileGenerations = By.xpath("//a[contains(text(),'Magnus file Generations')]");


//======================ISO Transaction =======================================

public static final By ISOTransactionSetUp = By.xpath("//a[contains(text(),'ISO Transaction Setup')]");
public static final By ISOResponceCodeSetUp = By.xpath("//a[contains(text(),'ISO Response Code Setup')]");


//======================Issuance Activities =======================================

public static final By CardOrderprocess = By.xpath("//a[contains(text(),'Card Order Process')]");
public static final By IncompleteAppleProcess = By.xpath("//a[contains(text(),'Incomplete Appl Process')]");

public static final By CardEnquiry = By.xpath("//a[contains(text(),'Card Enquiry')]");
public static final By CorpCardActivation = By.xpath("//a[contains(text(),'Corp Card Activation')]");
public static final By SendEmailStatement = By.xpath("//a[contains(text(),'Send eMail Statements')]");
public static final By BOIDuplicateStatement = By.xpath("//a[contains(text(),'BOI Duplicate Statement')]");

public static final By ServiceClient = By.xpath("//a[contains(text(),'Service Client')]");

public static final By PDF_Generation = By.xpath("//a[contains(text(),'PDF Generation')]");

public static final By UBI_DuplicateStatement = By.xpath("//a[contains(text(),'UBI Duplicate Statement')]");

public static final By EmbossaDispatched = By.xpath("//a[contains(text(),'Embossa Dispatched')]");

public static final By CardIssuance = By.xpath("//a[contains(text(),'Card Issuance')]");

public static final By RestServiceClient = By.xpath("//a[contains(text(),'Rest Service Client')]");


public static final By CallRequest = By.xpath("//b[contains(text(),'Call Request')]");

public static final By MessageValidation = By.xpath(".//*[@id='ContentPlaceHolder1_diverror']/div");


public static final By QuickLink = By.xpath(".//*[@id='ContentPlaceHolder1_uPnlCustomerInfo']/span[1]");


public static final By CallHistory = By.xpath(".//*[@id='ContentPlaceHolder1_lnkcallhistory']");

public static final By CardDetails=By.xpath(".//*[@id='ContentPlaceHolder1_lnkcarddetails']");




//====================================PCR==================
public static final By ManagePCR = By.linkText("Manage PCR");
public static final By AddParachangeRequest = By.linkText("Add Parachange Request");

public static final By checkerDashboard = By.linkText("Dashboard");

public static final By Midlink = By.id("ContentPlaceHolder1_gv_searchMEdetails_aTag_0");


//====================================AssetSwap=======================================
public static final By AssetManagement = By.xpath("//*[@id='30']/a");
public static final By Assetswap = By.linkText("Asset Swapping Request");
public static final By DashboardM = By.linkText("Dashboard");








}