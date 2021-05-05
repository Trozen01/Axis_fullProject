package com.MainFrameWork.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;



public class RandomTextUtils {
	
	public static int getRandomNumberInRange(int min, int max) {

		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}
	
	
	public static String getRandomDateOfBirth() throws ParseException{
		String Dob = null;
		
		Dob = getRandomNumberInRange(1,28) + "/" 
				+ getRandomNumberInRange(1,12) + "/" 
				+ getRandomNumberInRange(1970,1999);
		
		return formatDate(Dob);
	}
	
	
	public static String formatDate(String dateFormat) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date date = sdf.parse(dateFormat);
		String st = sdf.format(date);
		return st;
	}
	
	
	
	public static String getRandomMobileNo(){
		String mobile;
		mobile = getRandomNumberInRange(88888,99999) + "" + getRandomNumberInRange(88888,99991);
		return mobile;
	}
	
	
	
	public static String getLOCRandomMobileNo(){
		String mobile;
		mobile = "7"+ getRandomNumberInRange(100,999) + "" + getRandomNumberInRange(70199,99991);
		return mobile;
	}
	
	public static String getCDBRandomMobileNo()
	{
		String mobile;
		mobile = "7"+ getRandomNumberInRange(100,999) + "" + getRandomNumberInRange(70199,99991);
		return mobile;
	}
	
	
	public static String getLOCRandomNIC(){
		String NIC;
		NIC = getRandomNumberInRange(1111,9999) + "" + getRandomNumberInRange(0111,9991)+ getRandomText(1); ;
		return NIC;
		
		
	}
	
	
	public static String getCustID(){
		String CustID;
		CustID = getRandomNumberInRange(888,999) + "" + getRandomNumberInRange(888,991);
		return CustID;
	}
	
	
	public static String getCustomerID(){
		String CustID;
		CustID = getRandomNumberInRange(8888,9999) + "" + getRandomNumberInRange(8888,9999);
		return CustID;
	}
	
	public static String getCDBCustomerID()
	{
		String CustID;
		CustID = getRandomNumberInRange(8888,9999) + "" + getRandomNumberInRange(8888,9999);
		return CustID;
	}
	
	public static String getRandomText(int length){
		return RandomStringUtils.randomAlphabetic(length);
	}
	
	public static String getRandomPanNo(){
		String pan;
		pan = getRandomText(5) + getRandomNumberInRange(1111, 9999) + getRandomText(1);
		return pan;
	}
	
	
	public static String getRandomCustomerID(){
		
		String CustomerID;		
		CustomerID = getRandomNumberInRange(888,999) + "" + getRandomNumberInRange(888,999);		
		return CustomerID;
	}
	
	public static String getaddress(){
		String address;
		address = getRandomText(5)  + getRandomText(1);
		return address;
	}
	
	
	
	
	public static String getRandomNICNo(){
		String NIC;
		NIC = getRandomText(4) + getRandomNumberInRange(111111, 999999) ;
		return NIC;
	}
	
	public static String getRandomNICNo1(){
		String NIC;
		NIC =  getRandomNumberInRange(111111,999999)+""+getRandomNumberInRange(111111,999999);
		return NIC;
	}
	
	public static void main(String[] args) throws ParseException {
		int c;
	 String dd;
	    for (c = 1; c <= 3; c++) {
	    	dd = getRandomNICNo();
	 	      System.out.println(dd);
	    }

	}
	
	public static String getCitizonshipID(){
		String CitizonshipID;
		CitizonshipID = getRandomNumberInRange(888888,999999) + "" + getRandomNumberInRange(88888,99991);
		return CitizonshipID;
	}
	
	public static String getEmail() {
		//Date date = new Date();
		///SimpleDateFormat dt1 = new SimpleDateFormat("ddMMyy");
		//System.out.println(dt1.format(date));
		String generatedstring=RandomStringUtils.randomAlphabetic(10);
	    return(generatedstring+"@gmail.com");
		
	}


}
