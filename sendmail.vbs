Email_List = "rohan.baraskar@worldline.com;deepali.kumbhar@worldline.com;deepali.sawant@worldline.com;ramesh.pole@worldline.com;yogesh.bhavsar@worldline.com;sheela.selvam@worldline.com"

Set App = CreateObject("Outlook.Application")
Set Mail = App.CreateItem(0)

With Mail
    .To = Email_List
    .CC = "sanjay.dalvi@worldline.com;ranjit.talreja@worldline.com"
    .BCC = "rohan.baraskar@worldline.com"
    .Subject = "BFL LRP: Automation Execution Report"
    .HTMLBody = "BFL LRP Automation Batch Run TestResults"
    '.Body = strbody
    'You can add a file like this 
    .Attachments.Add ("C:\demo\BFL\BFLLRP\target\surefire-reports\Extentreport.html")

    'use .Send (to send) or .Display (to display the email and edit before sending)
    .Display
    .send
End With

Set Mail = Nothing
Set App = Nothing