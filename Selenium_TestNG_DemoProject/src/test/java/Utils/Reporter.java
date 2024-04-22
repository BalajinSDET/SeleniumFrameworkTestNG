package Utils;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
//import com.aventstack.extentreports.MediaEntityModelProvider;
import com.aventstack.extentreports.model.Media;
//import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

//import io.cucumber.testng.AbstractTestNGCucumberTests;

public abstract class Reporter {
	
	public static ExtentSparkReporter reporter;
	public static ExtentReports extent;
	public ExtentTest test, node;
	public String testCaseName, testDescription, nodes, authors,category;
	public String excelFileName;
	String repName;
	@BeforeSuite
	public void startReport() throws IOException {
	extent = new ExtentReports();
		
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());// time stamp
		repName = "Test-Report-"+ timeStamp + ".html";
		
		reporter = new ExtentSparkReporter("./reports/"+repName);	
		reporter.loadXMLConfig("./extent-config.xml");	
		extent.attachReporter(reporter);
		
		reporter.config().setDocumentTitle("The Chennai Silks portal Automation Report"); 
		reporter.config().setReportName("TCS-PORTAL Functional Testing"); 
		reporter.config().setTheme(Theme.DARK);
	//	reporter.config().setTheme(Theme.STANDARD);
		extent.setSystemInfo("Operating System", System.getProperty("os.name"));
		extent.setSystemInfo("User Name", System.getProperty("user.name"));
		extent.setSystemInfo("Environemnt", "QA");
	}
	
    @BeforeClass
	public void report() throws IOException {
    	// Creating a new Test inside the report for each test case
		test = extent.createTest(testCaseName, testDescription);
		test.assignAuthor(authors);
		test.assignCategory(category);  
	}
    public abstract long takeSnap();
    public void reportStep(String dec, String status, boolean bSnap ) {
    	Media img = null;
		if(bSnap && !status.equalsIgnoreCase("INFO")){

			long snapNumber = 100000L;
			snapNumber = takeSnap();
			try {
				img = MediaEntityBuilder.createScreenCaptureFromPath
						("./../reports/images/"+snapNumber+".jpg").build();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
    	if(status.equalsIgnoreCase("pass")) {
    		node.pass(dec, img);
    	} else if(status.equalsIgnoreCase("fail")) {
    		node.fail(dec, img); 
    	}
    }
    
    public void reportStep(String desc, String status) {
		reportStep(desc, status, true);
	}

    @AfterSuite
    public void stopReport() {
    	extent.flush();
    }
}