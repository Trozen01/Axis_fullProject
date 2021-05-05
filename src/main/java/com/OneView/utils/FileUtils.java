package com.OneView.utils; 

import java.text.ParseException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtils {
	
	public static boolean writeToFile(String fileNamePath, String content) {
		boolean result = false;
		try {

			File file = new File(fileNamePath);

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			//bw.write(content+"\r\n");
			bw.write(content);
			bw.close();
			result = true;
			System.out.println("Done");

		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	

}
