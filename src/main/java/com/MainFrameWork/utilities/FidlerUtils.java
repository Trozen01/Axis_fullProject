package com.MainFrameWork.utilities;  

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinUser;

public class FidlerUtils {
private static boolean FiddlerRunning=false;

	 static boolean isProcessRunning(String process) {
		boolean found = false;
		try {
			File file = File.createTempFile("realhowto",".vbs");
			file.deleteOnExit();
			FileWriter fw = new java.io.FileWriter(file);

			String vbs = "Set WshShell = WScript.CreateObject(\"WScript.Shell\")\n"
					+ "Set locator = CreateObject(\"WbemScripting.SWbemLocator\")\n"
					+ "Set service = locator.ConnectServer()\n"
					+ "Set processes = service.ExecQuery _\n"
					+ " (\"select * from Win32_Process where name='" + process +"'\")\n"
					+ "For Each process in processes\n"
					+ "wscript.echo process.Name \n"
					+ "Next\n"
					+ "Set WSHShell = Nothing\n";

			fw.write(vbs);
			fw.close();
			Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
			BufferedReader input =
					new BufferedReader
					(new InputStreamReader(p.getInputStream()));
			String line;
			line = input.readLine();
			if (line != null) {
				if (line.equals(process)) {
					found = true;
				}
			}
			input.close();

		}
		catch(Exception e){
			e.printStackTrace();
		}
		return found;
	}
	public static void initFiddler(){
		StartFiddler();

		//check if fiddler is running
		boolean result = FidlerUtils.isProcessRunning("Fiddler.exe");
		if(result){
			//System.out.println("fiddler is running trying to close..");
	
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
			//System.out.println("fiddler closed without any exception");

		}
		//clean the previous captures
		deleteFile(Constants.FiddlerCapturePath,Constants.ext);
		//copy customized rules file
		try {
			copyCustomRulesFilem(new File("CustomRules.js"),new File( Constants.custumRulesfile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		


}
	public static void main(String[] args){
		StartFiddler();
		
		
		//check if fiddler is running
		boolean result = FidlerUtils.isProcessRunning("Fiddler.exe");
		if(result){
			//System.out.println("fiddler is running trying to close..");
			try {
				//close it if its running
				Runtime.getRuntime().exec("taskkill /F /IM Fiddler.exe");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//System.out.println("problem in closing fiddler " +e);

				e.printStackTrace();
			}
			//System.out.println("fiddler closed without any exception");

		}
		//clean the previous captures
		deleteFile(Constants.FiddlerCapturePath,Constants.ext);
		//copy customized rules file
		try {
			copyCustomRulesFilem(new File("CustomRules.js"),new File( Constants.custumRulesfile));
			//System.out.println("loading CustomRules.js");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		StartFiddler();

	}

	public static void deleteFile(String folder, String ext){

		GenericExtFilter filter = new GenericExtFilter(ext);
		File dir = new File(folder);

		//list out all the file name with .txt extension
		String[] list = dir.list(filter);

		if (list==null || list.length == 0)  
			return;

		File fileDelete;

		for (String file : list){
			String temp = new StringBuffer(Constants.FiddlerCapturePath)
			.append(File.separator)
			.append(file).toString();
			fileDelete = new File(temp);
			boolean isdeleted = fileDelete.delete();
			//System.out.println("file : " + temp + " is deleted : " + isdeleted);
		}
	}


	private static void copyCustomRulesFilem(File source, File dest) throws IOException {
		//System.out.println("copying custom rules file");
		InputStream is = null;
		OutputStream os = null;
		try {
			is = new FileInputStream(source);
			os = new FileOutputStream(dest);
			byte[] buffer = new byte[1024];
			int length;
			while ((length = is.read(buffer)) > 0) {
				os.write(buffer, 0, length);
			}
		} finally {
			is.close();
			os.close();
		}
		//System.out.println("copyied  rules file");

	}

	public static void StartFiddler(){
		//System.out.println("starting fiddler");
		Process myProcess = null;
		try {
			//myProcess = Runtime.getRuntime().exec(Constants.fiddlerExePath);
			myProcess = new ProcessBuilder(Constants.fiddlerExePath).start();
			Thread.sleep(6000);
			HWND hwnd = User32.INSTANCE.FindWindow(null, "Fiddler Web Debugger");
			User32.INSTANCE.ShowWindow(hwnd, WinUser.SW_MINIMIZE);
			FiddlerRunning=true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static void closeWindowGracefully(){
		
		//ActionEngine.screenShot("file name");
		//System.out.println("closing fiddler");	
		int WM_CLOSE = 0x10;
		List<String> winNameList=EnumAllWindowNames.getAllWindowNames();
		for (String winName : winNameList) {
			//System.out.println(winName);
			HWND hwnd = User32.INSTANCE.FindWindow(null, winName);
			User32.INSTANCE.PostMessage(hwnd, WM_CLOSE, new WinDef.WPARAM(), new WinDef.LPARAM());
		}
		
		//System.out.println("closed fiddler");	
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		try {
			Runtime.getRuntime().exec("taskkill /F /IM Fiddler.exe");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Constants.FiddlerCapturePath+"//_stored.saz";


	}
	public static boolean isFiddlerRunning(){
		return FiddlerRunning;
	}
}

class GenericExtFilter implements FilenameFilter {

    private String ext;

    public GenericExtFilter(String ext) {
      this.ext = ext;             
    }

    public boolean accept(File dir, String name) {
      return (name.endsWith(ext));
    }
 }