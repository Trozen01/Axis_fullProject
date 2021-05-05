package com.OneView.utils; 

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

	public static String getRandomDateOfBirth() throws ParseException {
		String Dob = null;

		Dob = getRandomNumberInRange(1, 28) + "/" + getRandomNumberInRange(1, 12) + "/"
				+ getRandomNumberInRange(1990, 2010);

		return formatDate(Dob);
	}

	public static String formatDate(String dateFormat) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date date = sdf.parse(dateFormat);
		String st = sdf.format(date);
		return st;
	}

	public static String getRandomMobileNo() {
		String mobile;
		mobile = getRandomNumberInRange(11111, 99999) + "" + getRandomNumberInRange(11111, 99999);
		return mobile;
	}

	public static String getRandomText(int length) {
		return RandomStringUtils.randomAlphabetic(length);
	}

	public static String getCurrentDate() {
		Date date = new Date();
		SimpleDateFormat dt1 = new SimpleDateFormat("ddMMyy");
		//System.out.println(dt1.format(date));
		return dt1.format(date);
		
	}
	
	public static String getTimestamp() {
		//Date date = new Date();
		///SimpleDateFormat dt1 = new SimpleDateFormat("ddMMyy");
		//System.out.println(dt1.format(date));
		String a = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String timeStamp =a.replaceAll("\\.","");
		return timeStamp;
		
	}

	public static String getEmail() {
		//Date date = new Date();
		///SimpleDateFormat dt1 = new SimpleDateFormat("ddMMyy");
		//System.out.println(dt1.format(date));
		String generatedstring=RandomStringUtils.randomAlphabetic(10);
	    return(generatedstring+"@gmail.com");
		
	}

}
