package com.OneView.utils; 

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.MainFrameWork.accelerators.TestEngine;

public class QuickFlixUtils  extends TestEngine{

	
	/*Read t*/
	public static String getCookieFromFile(String cookieName, String fileName) {
	    BufferedReader br = null;
	    String cookieValue = null;
	    try {
	    	br = new BufferedReader(new FileReader(fileName));
	        String line = br.readLine();
	        while (line != null) {
	        	if(line.indexOf(cookieName) > 0){
	        		String []temp = line.split("[;]");
	        		for (String data : temp) {
						if(data.indexOf(cookieName) > 0){
							cookieValue =  data.split("[=]")[1];
							//System.out.println(cookieValue);
						}
					}
	        		break;
	            }
	            line = br.readLine();
	        }
	        return cookieValue;
	    } catch (Exception e) {
			e.printStackTrace();
		}finally {
	        try {
				br.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
	    }
		return cookieValue;
	}

	public static boolean isVideoPlaying(String fileName) {
	    BufferedReader br = null;
	    boolean isPlaying = false;
	    try {
	    	br = new BufferedReader(new FileReader(fileName));
	        List<Long> frames =new ArrayList<Long>();
	    	String line = br.readLine();
	        
	        while (line != null) {
	        	if(line.indexOf("video=") > 0){
	        		String frameCount = line.split("(video=)")[1].split("[)]")[0];
	        		frames.add(Long.valueOf(frameCount));
	        	}
	            line = br.readLine();
	        }
	        if(frames.size() > 0){
	        	int size = frames.size();

	        	if(frames.get(size-1) > frames.get(0)){
	        		return true;
	        	}else{
	        		return false;	        		
	        	}
	        	
	        }
	        return isPlaying;
	        
	    } catch(Exception e){
	    	e.printStackTrace();
	    } finally {
	        try {
				br.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
		return isPlaying;
	}	
	
	
	public static void createcounter(int rowNum, int counterVal,String counterName) throws Throwable {
		
		 
		String count1 = null;		
		count1 = Integer.toString(counterVal);
		counterProp.clean();
		counterProp.setProperty(counterName, count1);
		System.out.println("updating counter : " + counterName + " to "+counterVal);
		

		 }
	
}

