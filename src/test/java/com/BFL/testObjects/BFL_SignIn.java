package com.BFL.testObjects;

import org.openqa.selenium.By;

public class BFL_SignIn {
	
	
	public static final By Username = By.id("txtUser");
	public static final By Password = By.id("txtPassword");
	public static final By Logout = By.linkText("Logout");
	
	public static final By loginButton = By.id("btnSubmit");
	public static final By UserIcon = By.cssSelector("span.fa.fa-user");

}
