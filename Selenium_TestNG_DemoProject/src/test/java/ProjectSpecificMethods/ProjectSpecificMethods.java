package ProjectSpecificMethods;

import java.io.IOException;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;

import Selenium_Project_TestBase.BaseActions;
import Utils.DBDataLibrary;
import Utils.DataLibrary;


public class ProjectSpecificMethods extends BaseActions {
	protected RemoteWebDriver driver;
	public String dataSheetName;
	public String browserName;
	public String portal;
	public String location;
	public String deviceType;

	@DataProvider(name = "fetch_Excel_Data")
	public Object[][] fetchData() throws IOException {
		return DataLibrary.readExcelData(dataSheetName);
	}

	@DataProvider(name="fetch_DB_Data")
	public String[][] feedDP()
	{
		return DBDataLibrary.readDB(deviceType);
	}

	@BeforeMethod
	public void beforeMethod() throws InterruptedException {
		if (portal.equalsIgnoreCase("JobSeeker")) {
			driver = startApp(browserName, "https://ihp.co/");
		}else if (portal.equalsIgnoreCase("JobProvider")) {
			driver = startApp(browserName, "https://ihp.co/");
		}else if (portal.equalsIgnoreCase("Admin")) {
			driver = startApp(browserName, "https://ihp.co/");
		}
		node = test.createNode(testCaseName);
	}

	@AfterMethod
	public void afterMethod() {
		close();
	}

}
