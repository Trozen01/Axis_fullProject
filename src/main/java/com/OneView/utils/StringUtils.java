package com.OneView.utils; 

import java.text.ParseException;
import java.util.regex.Pattern;

public class StringUtils {
	
	public static boolean validateStringWithNumbers(String inputString){
		boolean result = false;
		String regex = "\\d+";
		Pattern pattern = Pattern.compile(regex);
		result = pattern.matcher(inputString).matches();
		return result;
	}
	
	public static boolean validateStringWithSpaces(String inputString){
		boolean result = false;
		String regex = "\\s+";
		Pattern pattern = Pattern.compile(regex);
		result = pattern.matcher(inputString).matches();
		return result;
	}
	
	public static boolean validateStringWithLettersAndSpaces(String inputString){
		boolean result = false;
		String regex = "^[a-zA-Z\\s]*$";
		Pattern pattern = Pattern.compile(regex);
		result = pattern.matcher(inputString).matches();
		return result;
	}
	
	public static boolean validateStringWithNumbersAndSpaces(String inputString){
		boolean result = false;
		String regex = "^[\\d ]*$";
		Pattern pattern = Pattern.compile(regex);
		result = pattern.matcher(inputString).matches();
		return result;
	}
	public static boolean validateStringWithLettersNumbersAndSpaces(String inputString){
		boolean result = false;
		String regex = "^[\\w ]*$";
		Pattern pattern = Pattern.compile(regex);
		result = pattern.matcher(inputString).matches();
		return result;
	}
	
	
	public static void main(String[] args) throws ParseException, InterruptedException {
	 //System.out.println(validateStringWithLettersNumbersAndSpaces("34 A "));   
		double expectedMastComm = 35234.596;
		//expectedMastComm = new BigDecimal(expectedMastComm).setScale(3, RoundingMode.HALF_UP).doubleValue();
		/*
		DecimalFormat df = new DecimalFormat("#.##");
		df.setRoundingMode(RoundingMode.CEILING);
		*/
		expectedMastComm = (double)Math.round(expectedMastComm * 100d) / 100d;
		
		System.out.println(expectedMastComm);
	// System.out.println(df.format(expectedMastComm));
	 

	}

}