package ProjectPageObjects;

import org.openqa.selenium.remote.RemoteWebDriver;

import com.aventstack.extentreports.ExtentTest;

import ProjectSpecificMethods.ProjectSpecificMethods;

public class JobSeekerDashboardPage extends ProjectSpecificMethods {

	public JobSeekerDashboardPage(RemoteWebDriver driver, ExtentTest node) {
		this.driver=driver;
		this.node=node;
	}

	
}
