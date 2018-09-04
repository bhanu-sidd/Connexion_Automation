package operation;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.Reporter;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.*;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.offset.PointOption;


public class UIOperation {

	WebDriver driver;
	DesiredCapabilities capabilities;
	WebDriverWait wait;
	AndroidDriver<WebElement> Androiddriver;
//	AppiumDriver<WebElement> Appiumdriver;
//	DesiredCapabilities capabilities;
	
//	public UIOperation(WebDriver driver,DesiredCapabilities capabilities){
//		
//		
//	}
	public UIOperation(WebDriver driver,AndroidDriver<WebElement> Androiddriver) {
		// TODO Auto-generated constructor stub
		this.driver = driver;
		this.Androiddriver=Androiddriver;
//		this.Appiumdriver=Appiumdriver;
		capabilities = new DesiredCapabilities();
		
	}
	public String capture(WebDriver driver,String screenShotName) throws IOException
    {	this.driver = driver;
    String fileSeperator = System.getProperty("file.separator");
    String FolderScreenshot = new SimpleDateFormat("dd-MMM-yy-HH").format(new Date());
    File createfile = new File("ErrorScreenshots" + fileSeperator + FolderScreenshot);
	if (!createfile.exists()) {
		System.out.println("File created " + createfile);
		createfile.mkdir();
	}
        TakesScreenshot ts = (TakesScreenshot)driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String dest = System.getProperty("user.dir") +"\\" +"ErrorScreenshots" +"\\"+ FolderScreenshot +"\\" + screenShotName+".png";
        File destination = new File(dest);
        FileUtils.copyFile(source, destination);        
                     
        return dest;
    }
	
	public void perform(Properties p,String operation,String objectName,String objectType,String value) throws Exception,MalformedURLException, InterruptedException{
//		System.out.println("");
		
//		WebDriverWait wait = new WebDriverWait(driver, 60);
//		DesiredCapabilities capabilities;
		switch (operation.toUpperCase()) {
		
		case "CLICK":
			//Perform click
			driver.findElement(this.getObject(p,objectName,objectType)).click();
			Thread.sleep(2000);
			break;
		case "SET TEXT":
			//Set text on control
			driver.findElement(this.getObject(p,objectName,objectType)).sendKeys(value);
			Thread.sleep(4000);
			break;
			
		case "GO TO URL":
			//Get url of application
			driver.get(p.getProperty(value));
//			System.out.println(p.getProperty(value));
			break;
		case "GET TEXT":
			//Get text of an element
			driver.findElement(this.getObject(p,objectName,objectType)).getText();
			break;
		case "WINDOW MAXIMIZE":
			//Maximize the browser window
			driver.manage().window().maximize();
			break;
		case "IMPLICIT WAIT":
			//Implicitly wait for some time
			value =value.substring(0, value.length()-2);
			System.out.println(value);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			break;
		case "ASSERT TITLE":
			//Implicitly wait for some time
			Assert.assertEquals(driver.getTitle(), value);
			break;
		case "WAIT FOR ELEMENT":
			wait = new WebDriverWait(driver, 120);
			//Wait till element is displayed
			WebElement objUN = wait.until(ExpectedConditions.presenceOfElementLocated(this.getObject(p,objectName,objectType)));
			AssertJUnit.assertTrue(objUN.isDisplayed());
			System.out.println(value);
			break;
		case "SCROLL TO ELEMENT":
			//Scroll the element into the view
			WebElement objScrollelement = driver.findElement(this.getObject(p,objectName,objectType));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", objScrollelement);
			Thread.sleep(3000);
			System.out.println("Page scrolled to" + objectName);
			break;
		case "REPORTER LOG":
			//Reporter log for reports
			Reporter.log(value);
			Thread.sleep(4000);
			break;
		case "ELEMENT ESC ACTION":
			//click on a drop down and send esc to close it
			WebElement objCatalogToSelect = driver.findElement(this.getObject(p,objectName,objectType));
			Thread.sleep(3000);
			Actions action2 = new Actions(driver);
			Action seriesOfActions2 = action2.moveToElement(objCatalogToSelect).click().sendKeys(objCatalogToSelect, Keys.ESCAPE).build();
			seriesOfActions2.perform();
			break;
		case "CLOSE BROWSER":
			// close the browser window
			driver.close();
			break;
		case "DISMISS ALERT":
			//Cancel a window alert
			driver.switchTo().alert().dismiss();
			break;
		case "ACCEPT ALERT":
			//Cancel a window alert
			driver.switchTo().alert().accept();
			break;
		case "IF CHKBOX NOT SELECTED":
			//If Checkbox is not selected
			WebElement checkbox = driver.findElement(this.getObject(p,objectName,objectType));
			if (!checkbox.isSelected()) {
				checkbox.click();
				// Enter sequence
				driver.findElement(this.getObject(p,value,objectType))
						.sendKeys("4");
			}else{
				checkbox.click();
			}
			break;
		case "IF CHKBOX SELECTED":
			WebElement checkbox1 = driver.findElement(this.getObject(p,objectName,objectType));
			//If check is selected
			if (checkbox1.isSelected()) {
				checkbox1.click();
			}
			break;
		case "CLEAR FIELD":
			WebElement textField = driver.findElement(this.getObject(p,objectName,objectType));
			textField.clear();
			break;
		case "GET ROLE TEXT":
			// Get the selected dropdown option Admin
			WebElement roleselected = driver.findElement(this.getObject(p,objectName,objectType));
			String defaultItem = roleselected.getText();
			System.out.println(defaultItem );
			roleselected.click();
			if(defaultItem.contains("Admin")){
				driver.findElement(By.xpath("//span[@class='mat-option-text' and contains(text(),'User')]")).click();
			}
			else if(defaultItem.contains("Publisher")){
				driver.findElement(By.xpath("//span[@class='mat-option-text' and contains(text(),'Admin')]")).click();
			}else{
				driver.findElement(By.xpath("//span[@class='mat-option-text' and contains(text(),'Publisher')]")).click();
			}
			break;
		case "GET STATUS TEXT":
			// Get the selected dropdown option
			WebElement statusselected = driver.findElement(this.getObject(p,objectName,objectType));
			String defaultItem1 = statusselected.getText();
			System.out.println(defaultItem1 );
			statusselected.click();
			if(defaultItem1.contains("Enable")){
				driver.findElement(By.xpath("//span[@class='mat-option-text' and contains(text(),'Disable')]")).click();
			}else{
				driver.findElement(By.xpath("//span[@class='mat-option-text' and contains(text(),'Enable')]")).click();
			}
			break;
		case "GET UNITS TEXT":
			// Get the selected dropdown option
			WebElement unitsselected = driver.findElement(this.getObject(p,objectName,objectType));
			String unitItem = unitsselected.getText();
			System.out.println(unitItem);
			unitsselected.click();
			if(unitItem.contains("Standard")){
			driver.findElement(By.xpath("//span[@class='mat-option-text' and contains(text(),' Metric(Cm/Kg) ')]")).click();
			Thread.sleep(2000);
			driver.findElement(By.xpath("//input[@placeholder='Height' and @type='number']")).clear();
			Thread.sleep(2000);
			driver.findElement(By.xpath("//input[@placeholder='Height' and @type='number']")).sendKeys("90");
			Thread.sleep(2000);
			driver.findElement(By.xpath("//input[@placeholder='Height' and @type='number']")).clear();
			Thread.sleep(2000);
			driver.findElement(By.xpath("//input[@placeholder='Height' and @type='number']")).sendKeys("172");
			Thread.sleep(2000);
			driver.findElement(By.xpath("//input[@placeholder='Weight' and @type='number']")).clear();
			Thread.sleep(2000);
			driver.findElement(By.xpath("//input[@placeholder='Weight' and @type='number']")).sendKeys("31");
			Thread.sleep(2000);
			driver.findElement(By.xpath("//input[@placeholder='Weight' and @type='number']")).clear();
			Thread.sleep(2000);
			driver.findElement(By.xpath("//input[@placeholder='Weight' and @type='number']")).sendKeys("80");
			}
			else{
			driver.findElement(By.xpath("//span[@class='mat-option-text' and contains(text(),' Standard(Ft/Lb) ')]")).click();
			Thread.sleep(2000);
			driver.findElement(By.xpath("//input[@placeholder='Feet' and @type='number']")).clear();
			Thread.sleep(2000);
			driver.findElement(By.xpath("//input[@placeholder='Feet' and @type='number']")).sendKeys("2");
			Thread.sleep(2000);
			driver.findElement(By.xpath("//input[@placeholder='Feet' and @type='number']")).clear();
			Thread.sleep(2000);
			driver.findElement(By.xpath("//input[@placeholder='Feet' and @type='number']")).sendKeys("6");
			Thread.sleep(2000);
			driver.findElement(By.xpath("//input[@formcontrolname='inch' and @type='number']")).clear();
			Thread.sleep(2000);
			driver.findElement(By.xpath("//input[@formcontrolname='inch' and @type='number']")).sendKeys("2");
			Thread.sleep(2000);
			driver.findElement(By.xpath("//input[@placeholder='Weight' and @type='number']")).clear();
			Thread.sleep(2000);
			driver.findElement(By.xpath("//input[@placeholder='Weight' and @type='number']")).sendKeys("20");
			Thread.sleep(2000);
			driver.findElement(By.xpath("//input[@placeholder='Weight' and @type='number']")).clear();
			Thread.sleep(2000);
			driver.findElement(By.xpath("//input[@placeholder='Weight' and @type='number']")).sendKeys("180");
			}
			break;
			
		case "SET BROWSER":
			// Optional
			
			capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
			break;
		case "SET DEVICE":
			// Specify the device name (any name)
			capabilities.setCapability("deviceName", value);
			break;
		case "SET PLATFORM VERSION":
			// Platform version ZY223HQSXS  5802ba6a
			capabilities.setCapability("platformVersion", value);
			break;
		case "SET PLATFORM":
			// platform name
			capabilities.setCapability("platformName", value);
			break;
		case "SET PERMISSIONS":
			capabilities.setCapability("autoGrantPermissions", "true");
			break;
		case "SET APP PACKAGE":
			// specify the application package that we copied from appium
			capabilities.setCapability("appPackage", value);
			break;
		case "SET APP ACTIVITY":
			// specify the application activity that we copied from appium
			capabilities.setCapability("appActivity", value);
			break;
		case "START DRIVER":
			// Start android driver using default port 4723
			Androiddriver = new AndroidDriver<WebElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
			break;
		case "EXPLICIT WAIT":
			//Explicitly wait for given time
			value =value.substring(0, value.length()-2);
			System.out.println(value);
			Thread.sleep(Integer.parseInt(value));
			break;
		case "APP CLICK":
			//Perform click
			Androiddriver.findElement(this.getObject(p,objectName,objectType)).click();
			Thread.sleep(2000);
			break;
		case "APP SET TEXT":
			//Set text on control
			System.out.println(value);
			Androiddriver.findElement(this.getObject(p,objectName,objectType)).sendKeys(value);
			Thread.sleep(4000);
			break;
		case "APP WAIT FOR ELEMENT":
			wait = new WebDriverWait(Androiddriver, 120);
			//Wait till element is displayed
			WebElement app_element = wait.until(ExpectedConditions.presenceOfElementLocated(this.getObject(p,objectName,objectType)));
			AssertJUnit.assertTrue(app_element.isDisplayed());
			System.out.println(value);
			break;
		case "APP IF FOUND CLICK":
			List<WebElement> IF_ELEMENT = Androiddriver.findElements(this.getObject(p,objectName,objectType));

			if(IF_ELEMENT.size()>0) {
			System.out.println("Element Found");
			IF_ELEMENT.get(1).click();
			}else {
				System.out.println("Element not Found");	
			}
			break;
		case "SET NATIVE VIEW":
//			Switching back to NATIVE_APP
			Androiddriver.context("NATIVE_APP");
			Thread.sleep(2000);
			break;
		case "GET CONTEXT WEB":
			// Switching to webview
			Set<String> contextNames = Androiddriver.getContextHandles();
			for (String contextName : contextNames) {
				System.out.println(contextNames); //prints out something like [NATIVE_APP, WEBVIEW_<APP_PKG_NAME>]
			}
//			Androiddriver.context((String) contextNames.toArray()[1]);
			break;
		case "APP SCROLL ELEMENT":
			// Scroll to element
//			TouchAction actions = new TouchAction(Androiddriver);
//			WebElement startElement = driver.findElement(this.getObject(p,objectName,objectType));
//			WebElement endElement = driver.findElement(this.getObject(p,value,objectType));
//			actions.press((PointOption) startElement).waitAction(Duration.ofSeconds(2)).moveTo(endElement).release().perform();
//			WebElement element = Androiddriver.findElement(MobileBy.AndroidUIAutomator(
//					"new UiScrollable(new UiSelector().resourceId(\"custom setting-icon Settings\")).getChildByText("
//					+ "new UiSelector().className(\"android.widget.Spinner\"), \"Display Units\")"));
//			
//	Perform the action on the element
//	element.click();
//			TouchAction().press(startElement).moveTo(endElement).release();
//			JavascriptExecutor js = (JavascriptExecutor) Androiddriver;
//			HashMap<String, String> scrollObject = new HashMap<String, String>();
//			scrollObject.put("direction", "down");
//			js.executeScript("mobile: scroll", scrollObject);
			WebElement objScrollele = Androiddriver.findElement(this.getObject(p,objectName,objectType));
			((JavascriptExecutor) Androiddriver).executeScript("arguments[0].scrollIntoView();", objScrollele);
			Thread.sleep(3000);
			System.out.println("Page scrolled to" + objectName);
			
			break;
		case "APP UNITS TEXT":
			// Get the selected dropdown option
			WebElement appunits = Androiddriver.findElement(this.getObject(p,objectName,objectType));
			String appunitstext = appunits.getText();
			System.out.println(appunitstext);
			appunits.click();
			if(appunitstext.contains("Standard")){
			Androiddriver.findElement(By.xpath("//div[@class='alert-radio-label' and contains(text(),'Metric')]")).click();
			Thread.sleep(2000);
			Androiddriver.findElement(By.xpath("//span[@class='button-inner' and contains(text(),'Ok')]")).click();
			Thread.sleep(2000);
			Androiddriver.findElement(By.xpath("//input[@ng-reflect-placeholder='Centimeters']")).clear();
			Thread.sleep(2000);
			Androiddriver.findElement(By.xpath("//input[@ng-reflect-placeholder='Centimeters']")).sendKeys("200");
			Thread.sleep(2000);
			Androiddriver.findElement(By.xpath("//input[@formcontrolname='weight']")).clear();
			Thread.sleep(2000);
			Androiddriver.findElement(By.xpath("//input[@formcontrolname='weight']")).sendKeys("90");
			Thread.sleep(2000);
			}////span[contains(text(),'SAVE')]
			//div[@class='send_button']
			else{
			Androiddriver.findElement(By.xpath("//div[@class='alert-radio-label' and contains(text(),'Standard')]")).click();
			Thread.sleep(2000);
			Androiddriver.findElement(By.xpath("//span[@class='button-inner' and contains(text(),'Ok')]")).click();
			Thread.sleep(2000);
			Androiddriver.findElement(By.xpath("//input[@placeholder='Feet']")).clear();
			Thread.sleep(2000);
			Androiddriver.findElement(By.xpath("//input[@placeholder='Feet']")).sendKeys("4");
			Thread.sleep(2000);
			Androiddriver.findElement(By.xpath("//input[@formcontrolname='inch']")).clear();
			Thread.sleep(2000);
			Androiddriver.findElement(By.xpath("//input[@formcontrolname='inch']")).sendKeys("5");
			Thread.sleep(2000);
			Androiddriver.findElement(By.xpath("//input[@formcontrolname='weight']")).clear();
			Thread.sleep(2000);
			Androiddriver.findElement(By.xpath("//input[@formcontrolname='weight']")).sendKeys("530");
			Thread.sleep(2000);
			}
			break;
		case "DEVICE BACK":
			//Tap on device back button
			Androiddriver.pressKey(new KeyEvent(AndroidKey.BACK));
			break;
			
		case "CLOSE APP":
			//Close the native app
			Androiddriver.quit();
			break;
			
		default:
			break;
		}
	}
	
	/**
	 * Find element BY using object type and value
	 * @param p
	 * @param objectName
	 * @param objectType
	 * @return
	 * @throws Exception
	 */
	private By getObject(Properties p,String objectName,String objectType) throws Exception{
		//Find by xpath
		if(objectType.equalsIgnoreCase("XPATH")){
			
			return By.xpath(p.getProperty(objectName));
		}
		//find by class
		else if(objectType.equalsIgnoreCase("CLASSNAME")){
			
			return By.className(p.getProperty(objectName));
			
		}
		//find by name
		else if(objectType.equalsIgnoreCase("NAME")){
			
			return By.name(p.getProperty(objectName));
		}
		//Find by css
		else if(objectType.equalsIgnoreCase("CSS")){
			
			return By.cssSelector(p.getProperty(objectName));
			
		}
		//find by link
		else if(objectType.equalsIgnoreCase("LINK")){
			
			return By.linkText(p.getProperty(objectName));
			
		}
		//find by partial link
		else if(objectType.equalsIgnoreCase("PARTIALLINK")){
			
			return By.partialLinkText(p.getProperty(objectName));
			
		}
		else
		{
//			return null;
			throw new Exception("Wrong object type");
		}
	}
}