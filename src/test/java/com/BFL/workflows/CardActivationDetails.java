package com.BFL.workflows;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;

import org.apache.log4j.Logger;

import com.MainFrameWork.accelerators.ActionEngine;



public class CardActivationDetails extends ActionEngine{
	
	Logger logger = Logger.getLogger(SignInClass.class.getName());
	
	public boolean CardBlock_Unblock(Hashtable<String, String> data) throws SQLException {
		
		
			
			ResultSet rs,rs1;
			boolean result = false;
			String username;
			String password ;
			
			String url = configProps.getProperty("DBURL");
			//String mid= configProps.getProperty("MID");
			String custID= data.get("CustomerID");
			
			// select * from fins_cardmaster where CRD_NO='2030409955489000';
			// select * from fins_cardmaster where CRD_CUST_ID='435';
			
			//String query = "select * from MAOPSAUDITLOG where MID ='"+ mercode + "'";
			String query = "SELECT * FROM fins_cardmaster WHERE CRD_CUST_ID ='"+custID+ "'"; 
			System.out.println("query is"+query);
					
			username =configProps.getProperty("DBUsername");
			password = configProps.getProperty("DBPassword");
			
			System.out.println("username is"+username);
			System.out.println("password is"+password);
			
			 String CRD_MANUAL_BLOCK_CODE = null;
			 String CRD_MANUAL_BLOCK_DATE= null;
			 String CRD_SYSTEM_BLOCK_CODE= null;
			 String CRD_SYSTEM_BLOCK_DATE= null;
			 
			rs =CallLogin.connectDB(url,username,password,query);
			
			
			 while(rs.next()){
				 
		      CRD_MANUAL_BLOCK_CODE = rs.getString("CRD_MANUAL_BLOCK_CODE");
		      CRD_MANUAL_BLOCK_DATE = rs.getString("CRD_MANUAL_BLOCK_DATE");
		      CRD_SYSTEM_BLOCK_CODE = rs.getString("CRD_SYSTEM_BLOCK_CODE");
		      CRD_SYSTEM_BLOCK_DATE = rs.getString("CRD_SYSTEM_BLOCK_DATE");
		    
		 
			//data.put("CRD_SYSTEM_BLOCK_CODE",CRD_SYSTEM_BLOCK_CODE);
			//data.put("CRD_SYSTEM_BLOCK_DATE",CRD_SYSTEM_BLOCK_DATE);
			
			 }
			 
			 if(CRD_MANUAL_BLOCK_CODE != null && CRD_MANUAL_BLOCK_CODE.length() > 0){ 
				 System.out.println("CRD_MANUAL_BLOCK_CODE is not null and not empty"); 
				 
			 }

			 
		
		// TODO Auto-generated method stub
		return false;
	}
	
	

}
