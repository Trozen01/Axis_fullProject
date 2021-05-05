package com.AXIS.testObjects;

import org.openqa.selenium.By;

public class SignInClass {
	
	
	public static final By Username = By.id("txtUserID");
	public static final By Password = By.id("txtPassword");
	public static final By Logout = By.linkText("Logout");
	
	public static final By loginButton = By.id("btnLogin");
	public static final By UserIcon = By.id("imgLogo");
}
