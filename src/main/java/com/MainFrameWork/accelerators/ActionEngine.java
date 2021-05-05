/**
 * com.ctaf is a group of Selenium accelerators  
 */
package com.MainFrameWork.accelerators;

import java.awt.AWTException;
import java.awt.HeadlessException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;


import com.MainFrameWork.utilities.Reporter;
import com.google.common.base.Predicate;

/**
 * ActionEngine is a wrapper class of Selenium actions
 */
@SuppressWarnings("deprecation")
public class ActionEngine extends TestEngine {
	public static WebDriverWait wait;

	static String bool = configProps.getProperty("OnSuccessReports");

	static boolean b = true; // /Boolean.parseBoolean(bool);

	// public static boolean flag=false;

	/**
	 * @param locator
	 *            : Action to be performed on element (Get it from Object
	 *            repository)
	 * @param locatorName
	 *            : Meaningful name to the element (Ex:Login Button, SignIn Link
	 *            etc..)
	 * @return --boolean (true or false)
	 * @throws Throwable
	 */

	public static boolean click(By locator, String locatorName)
			throws Throwable {
		boolean flag = false;
		try {
			driver.findElement(locator).click();

			flag = true;
		} catch (Exception e) {
			Assert.assertTrue(flag, "Unable to click on " + locatorName);
			e.printStackTrace();
		} finally {
			if (!flag) {
				Reporter.failureReport("Click", "Unable to click on "
						+ locatorName);
				Assert.assertTrue(flag, "Unable to click on " + locatorName);
				return flag;
			} else if (b && flag) {
				Reporter.SuccessReport("Click", locatorName);

			}
		}
		return flag;
	}

	/**
	 * This method returns check existence of element
	 * 
	 * @param locator
	 *            : Action to be performed on element (Get it from Object
	 *            repository)
	 * @param locatorName
	 *            : Meaningful name to the element (Ex:Textbox, checkbox etc)
	 * @return: Boolean value(True or False)
	 * @throws NoSuchElementException
	 */
	public static boolean isElementPresent(By by, String locatorName)
			throws Throwable {
		boolean flag = false;
		try {
			driver.findElement(by);
			flag = true;
			return true;
		} catch (Exception e) {
			Assert.assertTrue(flag, locatorName
					+ " Element is not present on the page ");
			e.printStackTrace();
			return false;
		} finally {
			if (!flag) {
				Reporter.failureReport("Check IsElementPresent ", locatorName
						+ " Element is not present on the page");

			} else if (b && flag) {
				Reporter.SuccessReport("Is Element Present ",
						"Able to locate element " + locatorName
								+ "in this page");
			}

		}
	}

	public static void waitForJSandJQueryToLoad() {

		Boolean isJqueryUsed = (Boolean) ((JavascriptExecutor) driver)
				.executeScript("return (typeof(jQuery) != 'undefined')");
		if (isJqueryUsed) {
			while (true) {
				// JavaScript test to verify jQuery is active or not
				Boolean ajaxIsComplete = (Boolean) (((JavascriptExecutor) driver)
						.executeScript("return jQuery.active == 0"));
				if (ajaxIsComplete)
					break;
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
			}
		}
	}

	public static boolean isPopUpElementPresent(By by, String locatorName)
			throws Throwable {
		boolean flag = false;
		try {
			if (driver.findElement(by).isDisplayed())
				flag = true;
			else
				flag = false;
			return flag;
		} catch (Exception e) {

			System.out.println(e.getMessage());
			return false;
		} finally {
			if (!flag) {
				// Reporter.failureReport("check IsElementPresent", locatorName
				// + " Element is not present on the page");
			} else if (b && flag) {
				Reporter.SuccessReport("IsElementPresent ",
						"Able to locate element " + locatorName);
			}

		}
	}

	public static boolean js_type(By by, String Text, String LocatorName)
			throws Throwable {
		boolean flag = true;
		try {
			WebElement location = driver.findElement(by);
			// ((JavascriptExecutor)
			// driver).executeScript("arguments[0].click();", elementToClick);
			((JavascriptExecutor) driver).executeScript("arguments[0].value='"
					+ Text + "'", location);
			return true;
		} catch (Exception e) {
			flag = false;
			return false;
		} finally {
			if (flag) {
				Reporter.SuccessReport("LOCATOR DETAILS-Type : " + LocatorName,
						"Value provided as: " + Text);
				
			} else {
				Reporter.failureReport(
						"LOCATOR DETAILS-Type  : " + LocatorName,
						"Value provided as: " + Text);
			}
		}
	}

	/**
	 * This method used type value in to text box or text area
	 * 
	 * @param locator
	 *            : Action to be performed on element (Get it from Object
	 *            repository)
	 * 
	 * @param testdata
	 *            : Value wish to type in text box / text area
	 * 
	 * @param locatorName
	 *            : Meaningful name to the element (Ex:Textbox,Text Area etc..)
	 * 
	 * @throws NoSuchElementException
	 */
	public static void Enter(By locator , String locatorName){
		boolean flag = false;
		try {
			
			driver.findElement(locator).sendKeys(Keys.ENTER);
			flag = true;

		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	public static boolean type(By locator, String testdata, String locatorName)
			throws Throwable {
		boolean flag = false;
		try {
			driver.findElement(locator).click();
			driver.findElement(locator).clear();
			driver.findElement(locator).sendKeys(testdata);
			flag = true;

		} catch (Exception e) {
			Assert.assertEquals(false, true,
					" type : Data typing action is not performed on  "
							+ locatorName);

			e.printStackTrace();
		} finally {
			if (!flag) {
				Reporter.failureReport("LOCATOR DETAILS-Type", " "
						+ locatorName + " Value provided as :" + testdata);

				return true;
			} else if (b && flag) {

				Reporter.SuccessReport("LOCATOR DETAILS-Type", " "
						+ locatorName + "  Value provided as :" + testdata);

			}
		}
		return flag;
	}

	/**
	 * Moves the mouse to the middle of the element. The element is scrolled
	 * into view and its location is calculated using getBoundingClientRect.
	 * 
	 * @param locator
	 *            : Action to be performed on element (Get it from Object
	 *            repository)
	 * 
	 * @param locatorName
	 *            : Meaningful name to the element (Ex:link,menus etc..)
	 * 
	 */
	public static boolean mouseover(By locator, String locatorName)
			throws Throwable {
		boolean flag = false;
		try {
			WebElement mo = driver.findElement(locator);
			new Actions(driver).moveToElement(mo).build().perform();
			flag = true;
			return true;
		} catch (Exception e) {
			Assert.assertTrue(flag, "MouseOver action is not perform on "
					+ locatorName);
			return false;
		} finally {
			if (!flag) {
				Reporter.failureReport("MouseOver",
						"MouseOver action is not perform on " + locatorName);

			} else if (b && flag) {

				Reporter.SuccessReport("MouseOver ",
						"MouserOver Action is Done on " + locatorName);
			}
		}
	}

	/**
	 * A convenience method that performs click-and-hold at the location of the
	 * source element, moves by a given offset, then releases the mouse.
	 * 
	 * @param source
	 *            : Element to emulate button down at.
	 * 
	 * @param xOffset
	 *            : Horizontal move offset.
	 * 
	 * @param yOffset
	 *            : Vertical move offset.
	 * 
	 */
	public static boolean draggable(By source, int x, int y, String locatorName)
			throws Throwable {
		boolean flag = false;
		try {

			WebElement dragitem = driver.findElement(source);
			new Actions(driver).dragAndDropBy(dragitem, x, y).build().perform();
			Thread.sleep(5000);
			flag = true;
			return true;

		} catch (Exception e) {

			return false;
		} finally {
			if (!flag) {
				Reporter.failureReport("Draggable ",
						"Draggable action is not performed on " + locatorName);

			} else if (b && flag) {

				Reporter.SuccessReport("Draggable ",
						"Draggable Action is Done on " + locatorName);
			}
		}
	}

	/**
	 * A convenience method that performs click-and-hold at the location of the
	 * source element, moves to the location of the target element, then
	 * releases the mouse.
	 * 
	 * @param source
	 *            : Element to emulate button down at.
	 * 
	 * @param target
	 *            : Element to move to and release the mouse at.
	 * 
	 * @param locatorName
	 *            : Meaningful name to the element (Ex:Button,image etc..)
	 * 
	 */
	public static boolean draganddrop(By source, By target, String locatorName)
			throws Throwable {
		boolean flag = false;
		try {
			WebElement from = driver.findElement(source);
			WebElement to = driver.findElement(target);
			new Actions(driver).dragAndDrop(from, to).perform();
			flag = true;
			return true;
		} catch (Exception e) {

			return false;
		} finally {
			if (!flag) {
				Reporter.failureReport("DragAndDrop ",
						"DragAndDrop action is not perform on " + locatorName);

			} else if (b && flag) {

				Reporter.SuccessReport("DragAndDrop ",
						"DragAndDrop Action is Done on " + locatorName);
			}
		}
	}

	/**
	 * To slide an object to some distance
	 * 
	 * @param slider
	 *            : Action to be performed on element
	 * 
	 * @param locatorName
	 *            : Meaningful name to the element (Ex:Login Button, SignIn Link
	 *            etc..)
	 * 
	 */
	public static boolean slider(By slider, int x, int y, String locatorName)
			throws Throwable {

		boolean flag = false;
		try {
			WebElement dragitem = driver.findElement(slider);
			// new Actions(driver).dragAndDropBy(dragitem, 400, 1).build()
			// .perform();
			new Actions(driver).dragAndDropBy(dragitem, x, y).build().perform();// 150,0
			Thread.sleep(5000);
			flag = true;
			return true;
		} catch (Exception e) {

			return false;
		} finally {
			if (!flag) {
				Reporter.failureReport("Slider ",
						"Slider action is not perform on " + locatorName);
				// throw new ElementNotFoundException("", "", "");

			} else if (b && flag) {
				Reporter.SuccessReport("Slider ", "Slider Action is Done on "
						+ locatorName);
			}
		}
	}

	/**
	 * To right click on an element
	 * 
	 * @param by
	 *            : Action to be performed on element (Get it from Object
	 *            repository)
	 * 
	 * @param locatorName
	 *            : Meaningful name to the element (Ex:Login Button, SignIn Link
	 *            etc..)
	 * 
	 * @throws Throwable
	 */

	public static boolean rightclick(By by, String locatorName)
			throws Throwable {

		boolean flag = false;
		try {
			WebElement elementToRightClick = driver.findElement(by);
			Actions clicker = new Actions(driver);
			clicker.contextClick(elementToRightClick).perform();
			flag = true;
			return true;
			// driver.findElement(by1).sendKeys(Keys.DOWN);
		} catch (Exception e) {

			return false;
		} finally {
			if (!flag) {
				Reporter.failureReport("RightClick ",
						"RightClick action is not perform on " + locatorName);

			} else if (b && flag) {
				Reporter.SuccessReport("RightClick ",
						"RightClick Action is Done on " + locatorName);
			}
		}
	}

	/**
	 * Wait for an element
	 * 
	 * @param locator
	 *            : Action to be performed on element (Get it from Object
	 *            repository)
	 * 
	 */

	public static boolean waitForTitlePresent(By locator) throws Throwable {

		boolean flag = false;
		boolean bValue = false;

		try {
			for (int i = 0; i < 200; i++) {
				if (driver.findElements(locator).size() > 0) {
					flag = true;
					bValue = true;
					break;
				} else {
					driver.wait(50);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (!flag) {
				Reporter.failureReport("WaitForTitlePresent ", "Title is wrong");

			} else if (b && flag) {
				Reporter.SuccessReport("WaitForTitlePresent ",
						"Launched successfully expected Title ");
			}
		}
		return bValue;
	}

	/**
	 * Wait for an ElementPresent
	 * 
	 * @param locator
	 *            : Action to be performed on element (Get it from Object
	 *            repository)
	 * 
	 * @return Whether or not the element is displayed
	 */
	public static boolean waitForElementPresent(By by, String locator)
			throws Throwable {
		boolean flag = false;
		try {
			for (int i = 0; i < 5000; i++) {
				if (driver.findElement(by).isDisplayed()) {
					flag = true;
					return true;

				} else {
					Thread.sleep(100);

				}
			}

		} catch (Exception e) {

			Assert.assertTrue(flag,
					"Wait For requested Element Present : Falied to locate element ");

			e.printStackTrace();

			return false;
		} finally {
			if (!flag) {
				Reporter.failureReport("Wait For requested Element Present  ",
						"Falied to locate requested element ");
			} else if (b && flag) {
			//	Reporter.SuccessReport("Wait For requested Element Present ",
				//		"Successfully located requested element ");
				Reporter.SuccessReport("Wait For requested Element Present ",
						"Successfully located "+locator);
			}
		}

		return flag;

	}

	/**  wait.until(ExpectedConditions.presenceOfElementLocated(By.id("table")));
	 * This method Click on element and wait for an element
	 * 
	 * @param locator
	 *            : Action to be performed on element (Get it from Object
	 *            repository)
	 * 
	 * @param waitElement
	 *            : Element name wish to wait for that (Get it from Object
	 *            repository)
	 * 
	 * @param locatorName
	 *            : Meaningful name to the element (Ex:Login Button, SignIn Link
	 *            etc..)
	 */
	public static boolean clickAndWaitForElementPresent(By locator,
			By waitElement, String locatorName) throws Throwable {
		boolean flag = false;
		try {
			click(locator, locatorName);
			waitForElementPresent(waitElement, locatorName);
			flag = true;
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			if (!flag) {
				Reporter.failureReport("ClickAndWaitForElementPresent ",
						"Failed to perform clickAndWaitForElementPresent action");
			} else if (b && flag) {
				Reporter.SuccessReport("ClickAndWaitForElementPresent ",
						"successfully performed clickAndWaitForElementPresent action");
			}
		}
	}

	/**
	 * Select a value from Dropdown using send keys
	 * 
	 * @param locator
	 *            : Action to be performed on element (Get it from Object
	 *            repository)
	 * 
	 * @param value
	 *            : Value wish type in dropdown list
	 * 
	 * @param locatorName
	 *            : Meaningful name to the element (Ex:Year Dropdown, items
	 *            Listbox etc..)
	 * 
	 */
	public static boolean selectBySendkeys(By locator, String value,
			String locatorName) throws Throwable {

		boolean flag = false;
		try {
			driver.findElement(locator).sendKeys(value);
			flag = true;
			return true;
		} catch (Exception e) {

			return false;
		} finally {
			if (!flag) {
				Reporter.failureReport("Select ", value
						+ "is Not Select from the DropDown " + locatorName);
				// throw new ElementNotFoundException("", "", "");

			} else if (b && flag) {
				Reporter.SuccessReport("Select ", value
						+ " is Selected from the DropDown " + locatorName);
			}
		}
	}

	/**
	 * select value from DropDown by using selectByIndex
	 * 
	 * @param locator
	 *            : Action to be performed on element (Get it from Object
	 *            repository)
	 * 
	 * @param index
	 *            : Index of value wish to select from dropdown list.
	 * 
	 * @param locatorName
	 *            : Meaningful name to the element (Ex:Year Dropdown, items
	 *            Listbox etc..)
	 * 
	 */
	public static boolean selectByIndex(By locator, int index,
			String locatorName) throws Throwable {
		boolean flag = false;
		try {
			Select s = new Select(driver.findElement(locator));
			s.selectByIndex(index);
			flag = true;
			return true;
		} catch (Exception e) {
			Assert.assertTrue(flag, "Option at index " + index
					+ " is Not Selected from the DropDown" + locatorName);
			e.printStackTrace();
			return false;
		} finally {
			if (!flag) {
				Reporter.failureReport("Select ", "Option at index " + index
						+ " is Not Select from the DropDown" + locatorName);

			} else if (b && flag) {
				Reporter.SuccessReport("Select ", "Option at index " + index
						+ " is Selected from the DropDown" + locatorName);
			}
		}
	}

	/**
	 * select value from DD by using value
	 * 
	 * @param locator
	 *            : Action to be performed on element (Get it from Object
	 *            repository)
	 * 
	 * @param value
	 *            : Value wish to select from dropdown list.
	 * 
	 * @param locatorName
	 *            : Meaningful name to the element (Ex:Year Dropdown, items
	 *            Listbox etc..)
	 */

	public static boolean selectByValue(By locator, String value,
			String locatorName) throws Throwable {
		boolean flag = false;
		try {
			Select s = new Select(driver.findElement(locator));
			s.selectByValue(value);
			flag = true;
			return true;
		} catch (Exception e) {
			Assert.assertTrue(flag, "Option with value attribute " + value
					+ " is Not Selected from the DropDown " + locatorName);
			e.printStackTrace();
			return false;
		} finally {
			if (!flag) {
				Reporter.failureReport("Select", "Option with value attribute "
						+ value + " is Not Select from the DropDown "
						+ locatorName);

			} else if (b && flag) {
				Reporter.SuccessReport("Select ",
						"Option with value attribute " + value
								+ " is  Selected from the DropDown "
								+ locatorName);
			}
		}
	}

	/**
	 * select value from DropDown by using selectByVisibleText
	 * 
	 * @param locator
	 *            : Action to be performed on element (Get it from Object
	 *            repository)
	 * 
	 * @param visibletext
	 *            : VisibleText wish to select from dropdown list.
	 * 
	 * @param locatorName
	 *            : Meaningful name to the element (Ex:Year Dropdown, items
	 *            Listbox etc..)
	 */

	public static boolean selectByVisibleText(By locator, String visibletext,
			String locatorName) throws Throwable {
		boolean flag = false;
		try {
			Select s = new Select(driver.findElement(locator));
			s.selectByVisibleText(visibletext);
			flag = true;
			return true;
		} catch (Exception e) {

			return false;
		} finally {
			if (!flag) {
				Reporter.failureReport("Select ", visibletext
						+ " is Not Selected from DropDown List as"
						+ locatorName);

			} else if (b && flag) {
				Reporter.SuccessReport("Select ", visibletext
						+ "  is Selected from DropDown List as " + locatorName);
			}
		}
	}

	public static boolean selectByVisibleText1(By locator, String visibletext,
			String locatorName) throws Throwable {
		boolean flag = false;
		try {
			Select s = new Select(driver.findElement(locator));
			s.selectByVisibleText(visibletext);
			flag = true;
			return true;
		} catch (Exception e) {

			return false;
		} finally {
			if (!flag) {

			} else if (b && flag) {

			}
		}
	}

	public static boolean selectByContainsVisibleText(By locator,
			String visibletext, String locatorName) throws Throwable {
		boolean flag = false;
		String optionText = null;
		try {
			Select s = new Select(driver.findElement(locator));
			List<WebElement> options = s.getOptions();
			for (WebElement option : options) {
				optionText = option.getText();
				if (optionText.contains(visibletext)) {
					option.click();
					flag = true;
					break;
				}
			}
			// s.selectByVisibleText(visibletext);
			return flag;
		} catch (Exception e) {
			return false;
		} finally {
			if (!flag) {
				Reporter.failureReport("Select the visible text containing ",
						visibletext + " is Not Selected from DropDown List "
								+ locatorName);

			} else if (b && flag) {
				Reporter.SuccessReport("Select the visible text containing ",
						visibletext + "  is Selected from DropDown List "
								+ locatorName);
			}
		}
	}

	/**
	 * SWITCH TO WINDOW BY USING TITLE
	 * 
	 * @param windowTitle
	 *            : Title of window wish to switch
	 * 
	 * @param count
	 *            : Selenium launched Window id (integer no)
	 * 
	 * @return: Boolean value(true or false)
	 * 
	 */
	//
	public static boolean switchWindowByTitle(String windowTitle, int count)
			throws Throwable {
		boolean flag = false;
		try {
			// Set<String> windowList = driver.getWindowHandles();
			// int windowCount = windowList.size();
			// Calendar calendar = new GregorianCalendar();
			// int second = calendar.get(Calendar.SECOND); // /to get current
			// time
			// int timeout = second + 40;
			/*
			 * while (windowCount != count && second < timeout) {
			 * Thread.sleep(500); windowList = driver.getWindowHandles();
			 * windowCount = windowList.size();
			 * 
			 * }
			 */

			// String[] array = windowList.toArray(new String[0]);

			// for (int i = 0; i <= windowCount; i++) {
			//
			// driver.switchTo().window(array[count - 1]);
			//
			// // if (driver.getTitle().contains(windowTitle))
			// flag = true;
			// return true;
			// }
			return false;
		} catch (Exception e) {

			return false;
		} finally {
			if (!flag) {
				Reporter.failureReport("SelectWindow ",
						"The Window with title " + windowTitle
								+ " is not Selected");

			} else if (b && flag) {
				Reporter.SuccessReport("SelectWindow ",
						"Focus navigated to the window with title "
								+ windowTitle);
			}
		}
	}

	/**
	 * Function To get column count and print data in Columns
	 * 
	 * @param locator
	 *            : Action to be performed on element (Get it from Object
	 *            repository)
	 * 
	 * @return: Returns no of columns.
	 * 
	 */
	public static int getColumncount(By locator) throws Exception {

		WebElement tr = driver.findElement(locator);
		List<WebElement> columns = tr.findElements(By.tagName("td"));
		int a = columns.size();
		System.out.println(columns.size());
		for (WebElement column : columns) {
			System.out.print(column.getText());
			System.out.print("|");
		}
		return a;

	}

	/**
	 * Function To get row count and print data in rows
	 * 
	 * @param locator
	 *            : Action to be performed on element (Get it from Object
	 *            repository)
	 * 
	 * @return: returns no of rows.
	 */
	public static int getRowCount(By locator) throws Exception {

		WebElement table = driver.findElement(locator);
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		int a = rows.size() - 1;
		return a;
	}

	/**
	 * Verify alert present or not
	 * 
	 * @return: Boolean (True: If alert preset, False: If no alert)
	 * 
	 */
	public static boolean Alert() throws Throwable {

		boolean presentFlag = false;
		Alert alert = null;

		try {

			// Check the presence of alert
			alert = driver.switchTo().alert();
			// if present consume the alert
			alert.accept();
			presentFlag = true;
		} catch (NoAlertPresentException ex) {
			// Alert present; set the flag

			// Alert not present
			ex.printStackTrace();
		} finally {
			if (presentFlag) {
				Reporter.SuccessReport("Alert ", "There was no alert to handle");
			} else {
				//Reporter.failureReport("Alert ",
				//		"The Alert is handled successfully ");
			}
		}

		return presentFlag;
	}

	public static boolean isAlertPresent() throws Throwable {

		boolean presentFlag = false;
		Alert alert = null;

		try {

			// Check the presence of alert
			alert = driver.switchTo().alert();
			// if present consume the alert
			// alert.accept();
			presentFlag = true;
			driver.switchTo().defaultContent();
		} catch (NoAlertPresentException ex) {
			// Alert present; set the flag

			// Alert not present
			ex.printStackTrace();
		}

		return presentFlag;
	}

	/**
	 * To launch URL
	 * 
	 * @param url
	 *            : url value want to launch
	 * @throws Throwable
	 * 
	 */
	public static boolean launchUrl(String url) throws Throwable {
		boolean flag = false;
		try {

			driver.navigate().to(url);
			ImplicitWait();
			flag = true;
			return true;
		} catch (Exception e) {
			Assert.assertTrue(flag, "Failed to launch " + url);
			e.printStackTrace();
			return false;
		} finally {
			if (!flag) {
				Reporter.failureReport("Launching URL ", "Failed to launch "
						+ url);
			} else if (b && flag) {
				Reporter.SuccessReport("Launching URL ",
						"Successfully launched " + url);
			}
		}
	}

	/*
	 * public static int getResponseCode(String url) { try { return
	 * Request.Get(url).execute().returnResponse().getStatusLine()
	 * .getStatusCode(); } catch (Exception e) { throw new RuntimeException(e);
	 * } }
	 */
	/**
	 * This method verify check box is checked or not
	 * 
	 * @param locator
	 *            : Action to be performed on element (Get it from Object
	 *            repository)
	 * 
	 * @param locatorName
	 *            : Meaningful name to the element (Ex:sign in Checkbox etc..)
	 * 
	 * @return: boolean value(True: if it is checked, False: if not checked)
	 * 
	 */
	public static String isChecked(By locator, String locatorName)
			throws Throwable {
		boolean bvalue = false;
		boolean flag = false;
		String test = null;
		try {
			if (driver.findElement(locator).isSelected()) {
				flag = true;
				bvalue = true;
				return test = driver.findElement(locator).getText();

			} else {
				flag = false;
				Reporter.failureReport("IsChecked ", locatorName
						+ " is not Selected ");
			}

		} catch (NoSuchElementException e) {

			bvalue = false;
		} finally {
			if (flag) {
				Reporter.SuccessReport("IsChecked ", locatorName
						+ " is Selected ");

			} else if (b && flag) {
				Reporter.failureReport("IsChecked ", locatorName
						+ " is not Selected ");
			}
		}
		// return bvalue;
		return test;
	}

	/**
	 * Element is enable or not
	 * 
	 * @param locator
	 *            : Action to be performed on element (Get it from Object
	 *            repository)
	 * 
	 * @param locatorName
	 *            : Meaningful name to the element (Ex:Login Button, UserName
	 *            Textbox etc..)
	 * 
	 * @return: boolean value (True: if the element is enabled, false: if it not
	 *          enabled).
	 * 
	 */

	public static boolean isEnabled(By locator, String locatorName)
			throws Throwable {
		Boolean value = false;
		boolean flag = false;
		try {
			if (driver.findElement(locator).isEnabled()) {
				flag = true;
				value = true;
			}

		} catch (Exception e) {

			flag = false;

		} finally {
			if (!flag) {
				Reporter.failureReport("IsEnabled ", locatorName
						+ " is not Enabled");

			} else if (b && flag) {
				Reporter.SuccessReport("IsEnabled ", locatorName + " is Enable");
			}
		}
		return value;
	}

	/**
	 * Element visible or not
	 * 
	 * @param locator
	 *            : Action to be performed on element (Get it from Object
	 *            repository)
	 * 
	 * @param locatorName
	 *            : Meaningful name to the element (Ex:Login Button, SignIn Link
	 *            etc..)
	 * 
	 * @return: boolean value(True: if the element is visible, false: If element
	 *          not visible)
	 * 
	 */

	public static boolean isVisible(By locator, String locatorName)
			throws Throwable {
		Boolean value = false;
		boolean flag = false;
		try {

			value = driver.findElement(locator).isDisplayed();
			value = true;
			flag = true;
		} catch (Exception e) {
			flag = false;
			value = false;
			Assert.assertTrue(flag, locatorName
					+ " Element is Not available for selection");

		} finally {
			if (!flag) {
				Reporter.failureReport("IsVisible ", locatorName
						+ " Element is Not available for selection");
			} else if (b && flag) {
				Reporter.SuccessReport("IsVisible ", locatorName
						+ " Element is available for selection ");

			}
		}
		return value;
	}

	/**
	 * Get the CSS value of an element
	 * 
	 * @param locator
	 *            : Action to be performed on element (Get it from Object
	 *            repository)
	 * 
	 * @param locatorName
	 *            : Meaningful name to the element (Ex:Login Button, label color
	 *            etc..)
	 * 
	 * @param cssattribute
	 *            : CSS attribute name wish to verify the value (id, name,
	 *            etc..)
	 * 
	 * @return: String CSS value of the element
	 * 
	 */

	public static String getCssValue(By locator, String cssattribute,
			String locatorName) throws Throwable {
		String value = "";
		boolean flag = false;
		try {
			if (isElementPresent(locator, "locatorName")) {
				value = driver.findElement(locator).getCssValue(cssattribute);
				flag = true;
			}
		} catch (Exception e) {

		} finally {
			if (!flag) {
				Reporter.failureReport("GetCssValue ",
						" Was able to get Css value from " + locatorName);

			} else if (b & flag) {
				Reporter.SuccessReport("GetCssValue ",
						" Was not able to get Css value from " + locatorName);
			}
		}
		return value;
	}

	/**
	 * Check the expected value is available or not
	 * 
	 * @param expvalue
	 *            : Expected value of attribute
	 * 
	 * @param locator
	 *            : Action to be performed on element (Get it from Object
	 *            repository)
	 * 
	 * @param attribute
	 *            : Attribute name of element wish to assert
	 * 
	 * @param locatorName
	 *            : Meaningful name to the element (Ex:link text, label text
	 *            etc..)
	 * 
	 */
	public static boolean assertValue(String expvalue, By locator,
			String attribute, String locatorName) throws Throwable {

		boolean flag = false;
		try {
			Assert.assertEquals(expvalue,
					getAttribute(locator, attribute, locatorName));
			flag = true;
		} catch (Exception e) {

		} finally {
			if (!flag) {
				Reporter.failureReport("AssertValue ", locatorName
						+ " present in the page");
				return false;
			} else if (b & flag) {
				Reporter.SuccessReport("AssertValue ", locatorName
						+ " is not present in the page ");
			}
		}
		return flag;
	}

	/**
	 * Check the text is presnt or not
	 * 
	 * @param text
	 *            : Text wish to assert on the page.
	 * 
	 */
	public static boolean assertTextPresent(String text) throws Throwable {
		boolean flag = false;
		try {
			Assert.assertTrue(isTextPresent(text));
			flag = true;
		} catch (Exception e) {

		} finally {
			if (!flag) {
				Reporter.failureReport("AssertTextPresent ", text
						+ " not present in the page ");
				return false;
			} else if (b & flag) {
				Reporter.SuccessReport("AssertTextPresent ", text
						+ " is present in the page ");
			}
		}
		return flag;
	}

	/**
	 * Assert element present or not
	 * 
	 * @param by
	 *            : Action to be performed on element (Get it from Object
	 *            repository)
	 * 
	 * @param locatorName
	 *            : Meaningful name to the element (Ex:Login Button, SignIn Link
	 *            etc..)
	 * 
	 */
	public static boolean assertElementPresent(By by, String locatorName)
			throws Throwable {

		boolean flag = false;
		try {
			Assert.assertTrue(isElementPresent(by, locatorName));
			flag = true;
		} catch (Exception e) {
			Assert.assertTrue(flag, locatorName + " present in the page ");
			e.printStackTrace();
		} finally {
			if (!flag) {
				Reporter.failureReport("AssertElementPresent ", locatorName
						+ " present in the page ");
				return false;
			} else if (b & flag) {
				Reporter.SuccessReport("AssertElementPresent ", locatorName
						+ " is not present in the page ");
			}
		}
		return flag;

	}

	/**
	 * Assert text on element
	 * 
	 * @param by
	 *            : Action to be performed on element (Get it from Object
	 *            repository)
	 * 
	 * @param text
	 *            : expected text to assert on the element
	 * 
	 */

	public static boolean assertText(By by, String text) throws Throwable {
		boolean flag = false;
		try {
			Assert.assertEquals(getText(by, text).trim(), text.trim());
			flag = true;
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (!flag) {
				Reporter.failureReport("AssertText ", text
						+ " is not present in the element ");
				return false;

			} else if (b && flag) {
				Reporter.SuccessReport("AssertText ", text
						+ " is  present in the element ");
			}
		}

	}

	/**
	 * Assert text on element
	 * 
	 * @param by
	 *            : Action to be performed on element (Get it from Object
	 *            repository)
	 * 
	 * @param text
	 *            : expected text to assert on the element
	 * 
	 * @param locatorName
	 *            : Meaningful name to the element (Ex:link text, label text
	 *            etc..)
	 * 
	 */
	public static boolean verifyText(By by, String text, String locatorName)
			throws Throwable {
		boolean flag = false;

		try {

			String vtxt = getText(by, locatorName).trim();
			vtxt.equals(text.trim());
			flag = true;
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			if (!flag) {
				Reporter.failureReport("VerifyText ", text
						+ " is not present in the location " + locatorName);
				flag = true;
			} else if (b && flag) {
				Reporter.SuccessReport("VerifyText ", text
						+ " is present in the location " + locatorName);
				flag = false;
			}
		}
	}

	/**
	 * @return: return title of current page.
	 * 
	 * @throws Throwable
	 */

	public static String getTitle() throws Throwable {

		String text = driver.getTitle();
		if (b) {
			Reporter.SuccessReport("Title ", "Title of the page is " + text);
		}
		return text;
	}

	/**
	 * Assert Title of the page.
	 * 
	 * @param title
	 *            : Expected title of the page.
	 * 
	 */
	public static boolean asserTitle(String title) throws Throwable {
		boolean flag = false;

		try {
			By windowTitle = By.xpath("//title[contains(text(),'" + title
					+ "')]");
			if (waitForTitlePresent(windowTitle)) {
				Assert.assertEquals(getTitle(), title);
				flag = true;
				return true;
			} else {
				return false;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		} finally {

			if (!flag) {
				Reporter.failureReport("AsserTitle ",
						"Page title is not matched with " + title);
				return false;
			} else if (b && flag) {
				Reporter.SuccessReport("AsserTitle ",
						" Page title is verified with " + title);
			}
		}

	}

	/**
	 * @return: return URL of current page.
	 * 
	 * @throws Throwable
	 */

	public static String getUrl() throws Throwable {

		String text = driver.getCurrentUrl();
		if (b) {
			Reporter.SuccessReport("Title ", "URL of the page is " + text);
		}
		return text;
	}

	/**
	 * Verify Title of the page.
	 * 
	 * @param title
	 *            : Expected title of the page.
	 * 
	 */
	public static boolean verifyTitle(String title) throws Throwable {

		boolean flag = false;

		try {
			getTitle().equals(title);
			flag = true;
			return true;
		} catch (Exception e) {
			return false;
		}

		finally {
			if (!flag) {
				Reporter.failureReport("VerifyTitle ",
						"Page title is not matched with " + title);

			} else if (b && flag) {
				Reporter.SuccessReport("VerifyTitle ",
						" Page title is verified with " + title);

			}
		}
	}

	/**
	 * Verify text present or not
	 * 
	 * @param text
	 *            : Text wish to verify on the current page.
	 * 
	 */
	public static boolean verifyTextPresent(String text) throws Throwable {
		boolean flag = false;
		;
		if (!(driver.getPageSource()).contains(text)) {

			Reporter.failureReport("VerifyTextPresent ", text
					+ " is not present in the page ");
			flag = false;
		} else if (b && flag) {
			Reporter.SuccessReport("VerifyTextPresent ", text
					+ " is present in the page ");
			flag = true;

		}
		return flag;
	}

	/**
	 * Get the value of a the given attribute of the element.
	 * 
	 * @param by
	 *            : Action to be performed on element (Get it from Object
	 *            repository)
	 * 
	 * @param attribute
	 *            : Attribute name wish to assert the value.
	 * 
	 * @param locatorName
	 *            : Meaningful name to the element (Ex:label, SignIn Link etc..)
	 * 
	 * @return: String attribute value
	 * 
	 */

	public static String getAttribute(By by, String attribute,
			String locatorName) throws Throwable {
		String value = "";
		if (isElementPresent(by, locatorName)) {
			value = driver.findElement(by).getAttribute(attribute);
		}
		return value;
	}

	/**
	 * Text present or not
	 * 
	 * @param text
	 *            : Text wish to verify on current page
	 * 
	 * @return: boolean value(true: if Text present, false: if text not present)
	 */

	public static boolean isTextPresent(String text) throws Throwable {

		boolean value = false;
		if (driver.getPageSource().toLowerCase().contains(text.toLowerCase())) {
			value = true;
			flag = true;
		} else {
			System.out.println("is text " + text + " present  " + value);
			flag = false;
		}
		if (!value) {
			// Reporter.failureReport("IsTextPresent ", text
			// + " is  not presented in the page ");
			// Assert.assertTrue(value,text
			// + " is  not presented in the page ");
			return false;

		} else if (b && flag) {
			Reporter.SuccessReport("IsTextPresent ", " " + text
					+ "YES Required Element Present On the page Requested");

			return true;
		}
		return value;
	}

	/**
	 * The innerText of this element.
	 * 
	 * @param locator
	 *            : Action to be performed on element (Get it from Object
	 *            repository)
	 * 
	 * @param locatorName
	 *            : Meaningful name to the element (Ex:label text, SignIn Link
	 *            etc..)
	 * 
	 * @return: String return text on element
	 * 
	 */

	public static String getText(By locator, String locatorName)
			throws Throwable {
		String text = "";
		boolean flag = false;
		try {
			if (isElementPresent(locator, locatorName)) {
				text = driver.findElement(locator).getText();
				flag = true;
			}
		} catch (Exception e) {
			Assert.assertTrue(flag, " Unable to get Text from " + locatorName);
			e.printStackTrace();
		} finally {
			if (!flag) {
				Reporter.warningReport("GetText ", " Unable to get Text from "
						+ locatorName);
			} else if (b && flag) {
				Reporter.SuccessReport("GetText ", " Able to get Text from "
						+ locatorName);
			}
		}
		return text;
	}

	public static String getValue(String locator, String locatorName)
			throws Throwable {
		String text = "";
		boolean flag = false;
		try {
			if (driver.findElement(By.xpath(locator)).isDisplayed()) {
				text = driver.findElement(By.xpath(locator)).getAttribute(
						"value");
				flag = true;
			}
		} catch (Exception e) {
			Assert.assertTrue(flag, " Unable to get Text from " + locatorName);
			e.printStackTrace();
		} finally {
			if (!flag) {
				Reporter.failureReport("GetValue ", " Unable to get Text from "
						+ locatorName);
			} else if (b && flag) {
				Reporter.SuccessReport("GetValue ", " Able to get Text from "
						+ locatorName);
			}
		}
		return text;
	}

	public static int getElementsSize(By locator, String locatorName)
			throws Throwable {
		int text = 0;
		try {
			if (driver.findElement(locator).isDisplayed()) {
				text = driver.findElements(locator).size();
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return text;
	}

	/**
	 * Capture Screenshot
	 * 
	 * @param fileName
	 *            : FileName screenshot save in local directory
	 * 
	 */
	public static void screenShot(String fileName) {
		File scrFile = ((TakesScreenshot) driver)
				.getScreenshotAs(OutputType.FILE);
		try {
			// Now you can do whatever you need to do with it, for example copy
			// somewhere
			FileUtils.copyFile(scrFile, new File(fileName));
		} catch (IOException e) {
			// Assert.assertTrue(flag,"Unable to take Screenshot");
			e.printStackTrace();
		}
	}

	/**
	 * Click on the Link
	 * 
	 * @param locator
	 *            : Action to be performed on element (Get it from Object
	 *            repository)
	 * 
	 * @param locatorName
	 *            : Meaningful name to the element (Ex:SignIn Link, menu's
	 *            etc..)
	 */

	public static boolean mouseHoverByJavaScript(By locator, String locatorName)
			throws Throwable {
		boolean flag = false;
		try {
			WebElement mo = driver.findElement(locator);
			String javaScript = "var evObj = document.createEvent('MouseEvents');"
					+ "evObj.initMouseEvent(\"mouseover\",true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);"
					+ "arguments[0].dispatchEvent(evObj);";
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript(javaScript, mo);
			flag = true;
			return true;
		}

		catch (Exception e) {

			return false;
		} finally {
			if (!flag) {
				Reporter.failureReport("MouseOver ",
						" MouseOver action is not perform on " + locatorName);
			} else if (b && flag) {
				Reporter.SuccessReport("MouseOver ",
						" MouserOver Action is Done on " + locatorName);
			}
		}
	}

	public static boolean JSClick(By locator, String locatorName)
			throws Throwable {
		boolean flag = false;
		try {
			WebElement element = driver.findElement(locator);
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", element);
			// driver.executeAsyncScript("arguments[0].click();", element);

			flag = true;

		}

		catch (Exception e) {

		} finally {
			if (!flag) {
				Reporter.failureReport("Click",
						" Click action is not perform on " + locatorName);
				return flag;
			} else if (b && flag) {
				Reporter.SuccessReport("Click ", " Click Action is Done on  "
						+ locatorName);
				return flag;
			}
		}
		return flag;
	}

	/**
	 * This method switch the focus to selected frame using frame index
	 * 
	 * @param index
	 *            : Index of frame wish to switch
	 * 
	 */
	public static boolean switchToFrameByIndex(int index) throws Throwable {
		boolean flag = false;
		try {
			driver.switchTo().frame(index);
			flag = true;
			return true;
		} catch (Exception e) {

			return false;
		} finally {
			if (!flag) {
				Reporter.failureReport("SelectFrame ", " Frame with index "
						+ index + " is not selected");
			} else if (b && flag) {
				Reporter.SuccessReport("SelectFrame ", " Frame with index "
						+ index + " is selected");
			}
		}
	}

	/**
	 * This method switch the to frame using frame ID.
	 * 
	 * @param idValue
	 *            : Frame ID wish to switch
	 * 
	 */
	public static boolean switchToFrameById(String idValue) throws Throwable {
		boolean flag = false;
		try {
			driver.switchTo().frame(idValue);
			flag = true;
			return true;
		} catch (Exception e) {

			e.printStackTrace();
			return false;
		} finally {
			if (!flag) {
				Reporter.failureReport("SelectFrame ", " Frame with Id "
						+ idValue + " is not selected");
			} else if (b && flag) {
				Reporter.SuccessReport("SelectFrame ", " Frame with Id "
						+ idValue + " is selected");
			}
		}
	}

	/**
	 * This method switch the to frame using frame Name.
	 * 
	 * @param nameValue
	 *            : Frame Name wish to switch
	 * 
	 */
	public static boolean switchToFrameByName(String nameValue)
			throws Throwable {
		boolean flag = false;
		try {
			driver.switchTo().frame(nameValue);
			flag = true;
			return true;
		} catch (Exception e) {

			return false;
		} finally {
			if (!flag) {
				Reporter.failureReport("SelectFrame ", " Frame with Name "
						+ nameValue + " is not selected");
			} else if (b && flag) {
				Reporter.SuccessReport("SelectFrame ", " Frame with Name "
						+ nameValue + " is selected");
			}
		}
	}

	/**
	 * This method switch the to Default Frame.
	 * 
	 * @throws Throwable
	 */
	public static boolean switchToDefaultFrame() throws Throwable {
		boolean flag = false;
		try {
			driver.switchTo().defaultContent();
			flag = true;
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (!flag) {
				Reporter.failureReport("SelectFrame ",
						" The Frame is not selected");
			} else if (b && flag) {
				Reporter.SuccessReport("SelectFrame ",
						" Frame with Name is selected");
			}
		}
	}

	/**
	 * This method switch the to frame using frame Name.
	 * 
	 * @param nameValue
	 *            : Frame Name wish to switch
	 * 
	 * @param locatorName
	 *            : Meaningful name to the element (Ex:SignIn Link, login button
	 *            etc..)
	 * 
	 * 
	 */
	public static boolean switchToFrameByLocator(By lacator, String locatorName)
			throws Throwable {
		boolean flag = false;
		try {
			driver.switchTo().frame(driver.findElement(lacator));
			flag = true;
			return true;
		} catch (Exception e) {

			e.printStackTrace();
			return false;
		} finally {
			if (!flag) {
				Reporter.failureReport("SelectFrame ", " The Frame "
						+ locatorName + " is not selected");
			} else if (b && flag) {
				Reporter.SuccessReport("SelectFrame ", " Frame with Name "
						+ locatorName + " is selected");
			}
		}
	}

	/**
	 * This method wait selenium until element present on web page.
	 */
	public static void ImplicitWait() {

		//driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public static boolean waitUntilTextPresents(By by, String expectedText,
			String locator) throws Throwable {
		wait = new WebDriverWait(driver, 160);
		boolean flag = false;

		try {
			wait.until(ExpectedConditions.textToBePresentInElementLocated(by,
					expectedText));

			flag = true;
			return true;

		} catch (Exception e) {
			Assert.assertTrue(false, " Falied to locate element " + locator
					+ " with text " + expectedText);
			e.printStackTrace();
			return false;
		} finally {
			if (!flag) {
				Reporter.failureReport("WaitUntilTextPresent ",
						" Falied to locate element " + locator + " with text "
								+ expectedText);
			} else if (b && flag) {
				Reporter.SuccessReport(" WaitUntilTextPresent ",
						" Successfully located element " + locator
								+ " with text " + expectedText);
			}

		}

	}

	/**
	 * Click on Element
	 * 
	 * @param locator
	 *            : Action to be performed on element (Get it from Object
	 *            repository)
	 * 
	 * @param locatorName
	 *            : Meaningful name to the element (Ex:SignIn Link, login button
	 *            etc..)
	 * 
	 * @throws StaleElementReferenceException
	 *             - If the element no longer exists as initially defined
	 */

	/**
	 * 
	 * This method wait driver until given driver time.
	 * 
	 */
	public static WebDriverWait driverwait() {

		WebDriverWait wait = new WebDriverWait(driver, 30);
		return wait;
	}

	/**
	 * This method wait selenium until visibility of Elements on WebPage.
	 * 
	 * @param by
	 *            : Action to be performed on element (Get it from Object
	 *            repository)
	 * @throws Throwable
	 * 
	 */

	public static boolean waitForVisibilityOfElement(By by, String locator)
			throws Throwable {
		boolean flag = false;
		WebDriverWait wait = new WebDriverWait(driver, 60);
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			flag = true;
			return true;
		} catch (Exception e) {
			Assert.assertTrue(flag, " Element " + locator + " is not visible");
			return false;
		} finally {
			if (!flag) {
				Reporter.failureReport("WaitForVisibilityOfElement ",
						" Element " + locator + " is not visible");
			} else if (b && flag) {
				Reporter.SuccessReport("WaitForVisibilityOfElement ",
						" Element " + locator + "  is visible");
			}
		}
	}

	/**
	 * This method wait driver until Invisibility of Element's attribute on
	 * WebPage.
	 * 
	 * @param by
	 *            : Action to be performed on element (Get it from Object
	 *            repository)
	 */
	public static boolean waitUntilElementAttributeIsVisible(By by,
			String attributeName, String locator) throws Throwable {
		boolean flag = false;
		try {
			for (int i = 0; i < 200; i++) {
				WebElement element = driver.findElement(by);
				boolean visible = element.getAttribute(attributeName).length() > 0;
				if (visible) {
					flag = true;
					break;
				} else {
					driver.wait(50);
				}
			}
			flag = true;
			return flag;
		} catch (Exception e) {
			/*
			 * Assert.assertTrue(flag," "+locator +" Element's " + attributeName
			 * + " is not visible");
			 */
			return false;
		} /*
		 * finally { if (!flag) {
		 * Reporter.failureReport("waitUntilElementAttributeIsVisible ",locator
		 * +" Element's " + attributeName + " is not visible"); } else if (b &&
		 * flag) {
		 * Reporter.SuccessReport("waitUntilElementAttributeIsVisible ",locator
		 * +" Element's " + attributeName + "  is visible"); } }
		 */
	}

	/**
	 * This method wait driver until Invisibility of Elements on WebPage.
	 * 
	 * @param by
	 *            : Action to be performed on element (Get it from Object
	 *            repository)
	 * 
	 * @param by
	 *            : Action to be performed on element (Get it from Object
	 *            repository)
	 * 
	 */
	public static boolean waitForInVisibilityOfElement(By by, String locator)
			throws Throwable {
		boolean flag = false;
		WebDriverWait wait = new WebDriverWait(driver, 30);
		try {
			wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
			flag = true;
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			if (!flag) {
				Reporter.failureReport("Ajax Wait ", locator
						+ " not dissapearing on page");
			} else if (b && flag) {
				Reporter.SuccessReport("Ajax Wait ", "Wait Till " + locator
						+ " dissapears");
			}
		}

	}

	public static boolean waitUntilElementAttributeChanges(By by,
			String attributeName, String expectedAttrubuteValue, String locator)
			throws Throwable {
		boolean flag = false;
		try {
			for (int i = 0; i < 200; i++) {
				WebElement element = driver.findElement(by);
				boolean enabled = element.getAttribute(attributeName).contains(
						expectedAttrubuteValue);
				if (enabled) {
					flag = true;
					break;
				} else {
					driver.wait(50);
				}
			}

			return flag;

		} catch (Exception e) {
			Assert.assertTrue(flag, " Falied to locate element " + locator
					+ " 's " + attributeName + " attribute with value "
					+ expectedAttrubuteValue);
			e.printStackTrace();
			return false;
		} finally {
			if (!flag) {
				Reporter.failureReport("waitUntilElementAttributeChanges ",
						"Falied to locate element " + locator + " 's "
								+ attributeName + " attribute with value "
								+ expectedAttrubuteValue);
			} else if (b && flag) {
				Reporter.SuccessReport("waitUntilElementAttributeChanges ",
						" Successfully located element " + locator + " 's "
								+ attributeName + " attribute with value "
								+ expectedAttrubuteValue);
			}

		}

	}
	

	public static List<WebElement> getElements(By locator) {

		List<WebElement> ele = driver.findElements(locator);

		return ele;
	}

	public static boolean assertTextMatching(By by, String text,
			String locatorName) throws Throwable {
		boolean flag = false;
		try {
			String ActualText = getText(by, text).trim();
			if (ActualText.contains(text)) {
				flag = true;
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (!flag) {
				Reporter.failureReport("Verify " + locatorName, text
						+ " is not present in the element ");
				return false;

			} else if (b && flag) {
				Reporter.SuccessReport("Verify " + locatorName, text
						+ " is  present in the element ");
			}
		}

	}

	public static boolean isElementDisplayed(By by, String locatorName)
			throws Throwable {
		boolean flag = false;
		try {
			driver.findElements(by);
			flag = driver.findElements(by).size() > 0;

		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	// QuickFlix Funcations added

	public static boolean isElementDisplayed(WebElement element)
			throws Throwable {
		boolean flag = false;
		try {
			for (int i = 0; i < 200; i++) {
				if (element.isDisplayed()) {
					flag = true;
					break;
				} else {
					Thread.sleep(50);
				}
			}
		} catch (Exception e) {
			return false;
		}
		return flag;
	}

	public static void executeJavaScriptOnElement(String script) {
		((JavascriptExecutor) driver).executeScript(script);
	}

	public static void closeBrowser() {
		driver.close();
		driver.quit();
	}

	public static boolean hitKey(By locator, Keys keyStroke, String locatorName)
			throws Throwable {
		boolean flag = false;
		try {
			driver.findElement(locator).sendKeys(keyStroke);
			flag = true;
			return true;
		} catch (NoSuchElementException e) {
			return false;
		} finally {
			if (flag) {
				// Reporter.SuccessReport("Type ","Data typing action is performed on"
				// + locatorName+" with data is "+testdata);

			} else {
				Reporter.failureReport("Type ",
						" Data typing action is not perform on" + locatorName
								+ " with data is " + keyStroke);

			}
		}
	}

	public static Collection<WebElement> getWebElementsByTagInsideLocator(
			By locator, String tagName, String locatorName) throws Throwable {
		boolean flag = false;
		Collection<WebElement> elements;
		try {
			WebElement element = driver.findElement(locator);
			elements = element.findElements(By.tagName(tagName));
			flag = true;
		} catch (NoSuchElementException e) {
			throw e;
		} finally {
			if (!flag) {
				Reporter.failureReport("Type ",
						"Data typing action is not perform on " + locatorName
								+ " with data is " + tagName);
			}
		}
		return elements;
	}

	public static void mouseOverElement(WebElement element, String locatorName)
			throws Throwable {
		boolean flag = false;
		try {
			new Actions(driver).moveToElement(element).build().perform();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (!flag) {
				Reporter.failureReport("MouseOver ",
						" MouseOver action is not perform on" + locatorName);
				// throw new ElementNotFoundException("", "", "");

			} else {
				Reporter.SuccessReport("MouseOver ",
						" MouserOver Action is Done on " + locatorName);
			}
		}
	}

	public static boolean refreshPage() throws Throwable {
		boolean flag = false;
		try {
			driver.navigate().refresh();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (!flag) {
				Reporter.failureReport("RefreshPage ",
						" Failed to Refresh the page ");
			} else {
				Reporter.SuccessReport("RefreshPage ",
						" Refreshed page successfully ");
			}
		}
		return flag;
	}

	private static CookieStore seleniumCookiesToCookieStore() {
		Cookie seleniumCookies = driver.manage().getCookieNamed(".QFXAUTH");
		CookieStore cookieStore = new BasicCookieStore();
		System.out.println("Selenium Cookie name = "
				+ seleniumCookies.getName());
		BasicClientCookie basicClientCookie = new BasicClientCookie(
				seleniumCookies.getName(), seleniumCookies.getValue());
		basicClientCookie.setDomain(seleniumCookies.getDomain());
		basicClientCookie.setExpiryDate(seleniumCookies.getExpiry());
		basicClientCookie.setPath(seleniumCookies.getPath());
		cookieStore.addCookie(basicClientCookie);
		return cookieStore;
	}

	public static boolean isLinkSuccess(String URLName) throws Throwable {
		boolean flag = false;
		System.out.println(URLName);
		int respCode = 0;
		try {
			if (URLName.contains("http")) {
				@SuppressWarnings("resource")
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpParams params = new BasicHttpParams();
				params.setParameter("http.protocol.handle-redirects", true);
				CookieStore cookieStore = seleniumCookiesToCookieStore();
				((AbstractHttpClient) httpClient).setParams(params);
				/*
				 * httpClient.getCredentialsProvider().setCredentials( new
				 * AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT), new
				 * UsernamePasswordCredentials("cigniti1@dev.quickflix.com.au",
				 * "password"));
				 */
				((AbstractHttpClient) httpClient).setCookieStore(cookieStore);
				HttpGet httpget = new HttpGet(URLName);
				HttpResponse httpResp = httpClient.execute(httpget);
				respCode = httpResp.getStatusLine().getStatusCode();
				System.out.println("response  " + respCode);
				if ((respCode == 200) | (respCode == 302)) {
					flag = true;
				} else {
					flag = false;
				}
			} else {
				flag = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (!flag) {
				Reporter.failureReport("IsLinkSuccess ",
						" Failed to veirfy if Link " + URLName
								+ " response code " + respCode);
			} else {
				Reporter.SuccessReport("IsLinkSuccess ",
						"Successfully veirfied Link " + URLName
								+ " response code " + respCode);
			}
		}
		return flag;
	}

	public static boolean isLinkSuccessWithOutAuth(String URLName)
			throws Throwable {
		boolean flag = false;
		System.out.println(URLName);
		int respCode = 0;
		try {
			if (URLName.contains("http")) {

				@SuppressWarnings("resource")
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpParams params = new BasicHttpParams();
				params.setParameter("http.protocol.handle-redirects", true);
				((AbstractHttpClient) httpClient).setParams(params);
				HttpGet httpget = new HttpGet(URLName);
				HttpResponse httpResp = httpClient.execute(httpget);
				respCode = httpResp.getStatusLine().getStatusCode();
				System.out.println("response  " + respCode);

				if ((respCode == 200) | (respCode == 302)) {
					flag = true;
				} else {
					flag = false;
				}
			} else {
				flag = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (!flag) {
				Reporter.failureReport("IsLinkSuccess ",
						" Failed to veirfy if Link " + URLName
								+ " response code " + respCode);
			} else {
				Reporter.SuccessReport("IsLinkSuccess ",
						"Successfully veirfied Link " + URLName
								+ " response code " + respCode);
			}
		}
		return flag;
	}

	public static String parseCookie(String raw) {
		String c = raw;

		if (raw != null) {
			int endIndex = raw.indexOf(";");
			if (endIndex >= 0) {
				c = raw.substring(0, endIndex);
			}
		}
		return c;
	}

	public static String readFile(String filename) throws IOException {
		String content = null;
		File file = new File(filename); // for ex foo.txt
		FileReader reader = null;
		try {
			reader = new FileReader(file);
			char[] chars = new char[(int) file.length()];
			reader.read(chars);
			content = new String(chars);
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
		return content;
	}

	public static boolean isElementPresent2(By by) throws Throwable {
		boolean flag = false;
		try {
			driver.findElement(by);
			flag = true;

		} catch (Exception e) {

		}
		return flag;
	}

	public static boolean verifyAndType(By locator, String testdata,
			String locatorName) throws Throwable {
		boolean flag = false;
		if (isElementPresent2(locator)) {
			type(locator, testdata, locatorName);

		}

		return flag;
	}

	public static boolean VerifyselectBySendkeys(By locator, String value,
			String locatorName) throws Throwable {

		boolean flag = false;

		if (isElementPresent2(locator)) {

			selectBySendkeys(locator, value, locatorName);

		}

		return flag;

	}

	public static boolean VerifyObjectisEnabled(By locator, String locatorName)
			throws Throwable {
		Boolean value = false;
		boolean flag = false;
		try {
			if (driver.findElement(locator).isEnabled()) {
				flag = true;
				value = true;
			}

		} catch (Exception e) {
		}
		return flag;

	}

	public static boolean VerifyEnableselectBySendkeys(By locator,
			String value, String locatorName) throws Throwable {

		boolean flag = false;

		if (VerifyObjectisEnabled(locator, locatorName)) {

			selectBySendkeys(locator, value, locatorName);

		}

		return flag;

	}

	public static boolean verifyEnabledAndType(By locator, String testdata,
			String locatorName) throws Throwable {
		boolean flag = false;
		if (VerifyObjectisEnabled(locator, locatorName)) {
			type(locator, testdata, locatorName);

		}

		return flag;
	}

	public static String TodateDate(String dateFormat) throws ParseException {

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return sdf.format(date);
	}

	public static boolean isElementPresent1(By by, String locatorName)
			throws Throwable {
		boolean flag = false;
		try {
			driver.findElement(by);
			flag = true;
			// return true;
		} catch (Exception e) {
			Assert.assertTrue(flag, locatorName
					+ " Element is not present on the page ");
			e.printStackTrace();
			flag = false;
			// return false;
		} finally {
			if (!flag) {
				// Reporter.failureReport("Check IsElementPresent ", locatorName
				// + " Element is not present on the page");

			} else if (b && flag) {
				Reporter.SuccessReport("Is Element Present ",
						"Able to locate element " + locatorName
								+ "in this page");
			}

		}
		return flag;
	}

	public static void waitForPageLoaded() throws HeadlessException, AWTException,
			IOException {
		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver)
						.executeScript("return document.readyState").toString()
						.equals("complete");
			}
		};
		try {
			Thread.sleep(1000);
			WebDriverWait wait = new WebDriverWait(driver, 40);
			wait.until(expectation);
		} catch (Throwable error) {
			// takeScreenShotOnError(error.toString());
			//Assert.fail("Timeout waiting for Page Load Request to complete.");
		}
	}
	
//	public static void waitForAjax(WebDriver driver) {
//		WebDriverWait webDriverWait = new WebDriverWait(driver, 30);
//		webDriverWait.withTimeout(30, TimeUnit.SECONDS);
//		try{
//			webDriverWait.until(waitCondition);
//		}catch (Exception e){
//			System.out.println(e.getMessage());
//		}          
//	}
	
	public  static void waitForAjax(WebDriver driver) throws InterruptedException {
		 JavascriptExecutor executor = (JavascriptExecutor)driver;
		    if((Boolean) executor.executeScript("return window.jQuery != undefined")){
		        while(!(Boolean) executor.executeScript("return jQuery.active == 0")){
		            Thread.sleep(1000);
		        }
		    }
		    return;
	}

	public void waitForAlert() throws InterruptedException {
		int i = 0;
		while (i++ < 5) {
			try {
				driver.switchTo().alert();
				break;
			} catch (NoAlertPresentException e) {
				Thread.sleep(2000);
				continue;
			}
		}
	}
	
	public static void scrollDown() throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		//js.executeScript(javaScript, mo);
		//js.executeScript("arguments[0].scrollIntoView(true);",element);
		
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,300)");
		// 
	}
	
	public static void scrolltoElement(WebElement element) throws InterruptedException {
	JavascriptExecutor js = (JavascriptExecutor) driver;
		
		
		js.executeScript("arguments[0].scrollIntoView(true);", element);
		// 
	}
	
	public static void scrollToElement(By locator) throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		
		
		js.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(locator));
		
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-150)");
		// 
	}
	
	public static void moveToElement(By locator) throws InterruptedException {
	Actions actions = new Actions(driver);
	actions.moveToElement(driver.findElement(locator));
	actions.perform();
	
	}
	
	
	
	
	
	
	
	
	public static void switchToFrame(){
		driver.switchTo().frame(0);
		
	}
	
	public static void waitForElementVisible(WebElement element, int timeSeconds){
		try {

            WebDriverWait wait = new WebDriverWait(driver, timeSeconds);
            wait.until(ExpectedConditions.visibilityOf(element));

        } catch (TimeoutException te) {
            
        }
		
	}
	
//	public static void checkPageIsReady() {
//		  
//		  JavascriptExecutor js = (JavascriptExecutor)driver;
//		  
//		  
//		  //Initially bellow given if condition will check ready state of page.
//		  if (js.executeScript("return document.readyState").toString().equals("complete")){ 
//		   System.out.println("Page Is loaded.");
//		   return; 
//		  } 
//		  
//		  //This loop will rotate for 25 times to check If page Is ready after every 1 second.
//		  //You can replace your value with 25 If you wants to Increase or decrease wait time.
//		  for (int i=0; i<25; i++){ 
//		   try {
//		    Thread.sleep(1000);
//		    }catch (InterruptedException e) {} 
//		   //To check page ready state.
//		   if (js.executeScript("return document.readyState").toString().equals("complete")){ 
//		    break; 
//		   }   
//		  }
//	
//	}
	
//	public void waitForLoad(WebDriver driver) {
//        ExpectedCondition<Boolean> pageLoadCondition = new
//                ExpectedCondition<Boolean>() {
//                    public Boolean apply(WebDriver driver) {
//                        return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete") &&
//                        		 ((Long)executeJavaScript("return jQuery.active") == 0);
//                        		;
//                    }
//                };
//        WebDriverWait wait = new WebDriverWait(driver, 30);
//        wait.until(pageLoadCondition);
//    }
	
	public static void checkPageIsReady(){
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("return document.readyState").toString().equals("complete");
	}
	
//	
//	public static void waitForJQueryProcessing(final WebDriver webDriver, int timeOutInSeconds) {
//        new WebDriverWait(webDriver, timeOutInSeconds) {
//        }.until(new ExpectedCondition<Boolean>() {
//                @Override
//                public Boolean apply(WebDriver driver) {
//                        return (Boolean) ((JavascriptExecutor) driver)
//                                        .executeScript("return window.jQuery != undefined && jQuery.active == 0");
//                }
//        });
//}
//	public static void scrollUp() throws InterruptedException {
//		JavascriptExecutor js = (JavascriptExecutor) driver;
//		
//		
//		js.executeScript("window.scrollTo(0, -document.body.scrollHeight);");
//		// 
//	}
	
	public static void scrollUp() throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		
		
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-90)");
		// 
	}
	
	public static void scrollTillEnd(By locator) throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		
		
		((JavascriptExecutor) driver)
	     .executeScript("window.scrollTo(0, document.body.scrollHeight)");
		
	}
	
	
	public static void scrollTillPageEnd() throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		//js.executeScript(javaScript, mo);
		//js.executeScript("arguments[0].scrollIntoView(true);",element);
		
		((JavascriptExecutor) driver)
	     .executeScript("window.scrollTo(0, document.body.scrollHeight)");
		// 
	}
	
// switch to pop up windows, provide original window handle
	public static void switchToPopUpWindow(String master) throws InterruptedException{


		
		//Assigning the handles to a set
		Set<String> handles = driver.getWindowHandles();
		//Switching to the popup window.
		for ( String handle : handles )
		{
		    if(!handle.equals(master))
		    {
		         driver.switchTo().window(handle);
		         Thread.sleep(2000);
		    }
		}
		
		
	}
	
	
	public static void waitForSpinner(WebDriver driver, int timeout) throws Throwable {
        // added try catch
        try{
               
               WebDriverWait wait = new WebDriverWait(driver,10);

               wait.until(new ExpectedCondition<Boolean>() {
                   public Boolean apply(WebDriver driver) {
                       WebElement button = driver.findElement(By.xpath("//*[@id='divloader']"));
                       String enabled = button.getAttribute("style");
                       if(enabled.contains("none")) 
                           return true;
                       else
                           return false;
                   }
               });
               
        }catch(Exception e){
               
        }
        
        
        
  }

}
