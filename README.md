# Reimbursement-Calculation-Application
## Requirements to test the application in Windows.
* Having tomcat server installed;
* Having maven installed;
* Having a MySQL database installed with configuration :

username: "reimbursement",
password : "reimbursement",
connection: "localhost:3306/Reimbursement",
Schema: "reimbursement"
* The .war file with changed name ROOT.war needs to be copied to the folder "webapp" in tomcat, in place of original folder ROOT.
* And now we can start our Application 
## During the initialization of the application, new data will be written to the database:
* ADMIN account: username = "admin", password = "admin";
* Two USER 's account: username = "test1", password = "test1"; username = "test2", password = "test2";
* List of receipts;
* Rates;
* Limits;
## We will start with web address: http://localhost:8080/login
