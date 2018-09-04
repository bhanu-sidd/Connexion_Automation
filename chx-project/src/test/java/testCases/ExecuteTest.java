package testCases;

import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.Properties;
import operation.ReadObject;
import operation.UIOperation;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;
import org.testng.Reporter;
import excelExportAndFileIO.ReadExcelFile;
import io.appium.java_client.android.AndroidDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * THIS IS THE Connexion key Word Driven Automation framework
 *
 */
public class ExecuteTest {
	ExtentTest logger;
	// Declare for mobile automation
	WebDriver webdriver;
	AndroidDriver<WebElement> Anddriver;
//	WebDriver driver;
	
	
	@SuppressWarnings("deprecation")
	@Test
	public void testLogin() throws Exception, MalformedURLException, InterruptedException, IOException,org.openqa.selenium.NoSuchElementException,org.openqa.selenium.TimeoutException,java.lang.NullPointerException {
		// TODO Auto-generated method stub
		// System.out.println("launching firefox browser");
		// System.setProperty("webdriver.firefox.marionette","C:\\Users\\SBP\\Desktop\\geckodriver.exe");
		// WebDriver webdriver = new FirefoxDriver();
			
//		if(sheetappname == "Web_CHX") {
//		String web="WEB";
//		if(web == "WEB") {
		//to initialize chrome browser
		System.out.println("launching Chrome browser");
		System.setProperty("webdriver.chrome.driver", "D:\\chromedriver.exe");
		// WebDriver webdriver = new ChromeDriver();
		DesiredCapabilities capabilitiesChrome = DesiredCapabilities.chrome();
		capabilitiesChrome.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
		ChromeOptions opts = new ChromeOptions();
		opts.addArguments("start-maximized");
		capabilitiesChrome.setCapability(ChromeOptions.CAPABILITY, opts);
		WebDriver webdriver = new ChromeDriver(capabilitiesChrome);
		webdriver.manage().deleteAllCookies();
//				}
		//To initialize mobile driver
		
		//Extent Reports
		String fileSeperator1 = System.getProperty("file.separator");
		String fileStamp = new SimpleDateFormat("dd-MMM-yy-HH-mma").format(new Date());
		String reportfolder = new SimpleDateFormat("dd-MMM-yy").format(new Date());
		File createfolder = new File("Reports" + fileSeperator1 + reportfolder);
		if (!createfolder.exists()) {
			System.out.println("File created " + createfolder);
			createfolder.mkdir();
		}
		System.out.println("./Reports/"+ reportfolder+"/"+ "CHX_Web_Automation_Report_" + fileStamp +".html");
		ExtentHtmlReporter reporter = new ExtentHtmlReporter("./Reports/"+ reportfolder+"/"+ "CHX_Web_Automation_Report_" + fileStamp +".html");
		
		ExtentReports extent = new ExtentReports();
		extent.attachReporter(reporter);
		ReadExcelFile file = new ReadExcelFile();
		ReadObject object = new ReadObject();
		Properties allObjects = object.getObjectRepository();
		UIOperation operation = new UIOperation(webdriver,Anddriver);
		// Define your test here for Web APP QA = "Web_CHX_QA" , WEB APP DEV="Web_CHX_DEV"
		// Android app = "Android_CHX" IOS app = "IOS_CHX"
		String sheetappname = "Web_CHX_QA";
		Sheet CHXWorkSheet = file.readExcel(System.getProperty("user.dir") + "\\", "TestCase2.xlsx", sheetappname);
		// Find number of rows in excel file
		int rowCount = CHXWorkSheet.getLastRowNum() - CHXWorkSheet.getFirstRowNum();
		System.out.println(rowCount);

		
		
		// Create a loop over all the rows of excel file to read it
		for (int i = 1; i < rowCount + 1; i++) {
			// Loop over all the rows
			Row row = CHXWorkSheet.getRow(i);
//			Cell cell = null;
//			cell.setCellType(Cell.CELL_TYPE_STRING);
			// Check if the first cell contain a value, if yes, That means it is
			// the new testcase name
			if (row.getCell(0).toString().length() == 0) {
				// Call perform function to perform operation on UI
				try {
					operation.perform(allObjects, row.getCell(2).toString(), row.getCell(3).toString(),
							row.getCell(4).toString(), row.getCell(5).toString());
					logger.log(Status.PASS,
							row.getCell(1).toString() + "----" + row.getCell(2).toString() + "----"
									+ row.getCell(3).toString() + "----" + row.getCell(4).toString() + "----"
									+ row.getCell(5).toString() + "Passed");
					// Print testcase detail on console
					System.out.println(row.getCell(1).toString() + "----" + row.getCell(2).toString() + "----"
							+ row.getCell(3).toString() + "----" + row.getCell(4).toString() + "----"
							+ row.getCell(5).toString());
				} catch (Throwable  t) {
					t.printStackTrace();
					logger.log(Status.FAIL,
							row.getCell(1).toString() + "----" + row.getCell(2).toString() + "----"
									+ row.getCell(3).toString() + "----" + row.getCell(4).toString() + "----"
									+ row.getCell(5).toString() + "Failed");
					String testcasefailed = row.getCell(1).toString();
					String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
					String errorscreenshot=testcasefailed + "_" + timeStamp;
					String screenShotPath = operation.capture(webdriver, errorscreenshot);
//		            test.log(Status.FAIL, MarkupHelper.createLabel(result.getName()+" Test case FAILED due to below issues:", ExtentColor.RED));
//					logger.fail(result.getThrowable());
					logger.fail("Snapshot below: " + logger.addScreenCaptureFromPath(screenShotPath));
					// System.out.println(row.getCell(1).toString()+"- failed to
					// execute the test case");
				}
			} else if (row.getCell(0).toString() == "SKIP") {
				// SKip this test case execution and move to next test case
				// System.out.println("This
				// Testcase->"+row.getCell(2).toString() +" skipped");
				logger.log(Status.SKIP, row.getCell(1).toString() + "Skipped");
			} 
			else if (row.getCell(0).toString().length() == 0 && row.getCell(1).toString().length() == 0) {
				return;
			} else {
				logger = extent.createTest(row.getCell(0).toString());
				// Print the new testcase name when it started
				// logger.log(Status.INFO, row.getCell(0).toString());
				System.out.println("New Testcase->" + row.getCell(0).toString() + " Started");
			}
			extent.flush();
		}
	}

}
