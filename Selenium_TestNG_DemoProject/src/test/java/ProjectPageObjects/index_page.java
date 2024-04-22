package ProjectPageObjects;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentTest;

import ProjectSpecificMethods.ProjectSpecificMethods;
import Selenium_Project_TestBase.BaseActions;

import java.util.List;
public class index_page extends ProjectSpecificMethods {
	
	public index_page(RemoteWebDriver driver, ExtentTest node){
		this.driver = driver;
		this.node = node;
	}
	BaseActions baseActions = new BaseActions();

	@FindBy(xpath = "//*[contains(@class, \"marginSearch80 \")]")
	WebElement indexPageTittlelabel;

	@FindBy(xpath = "//input[@placeholder=\"Job title, keywords..\"]")
	WebElement indexPageTittle;

	@FindBy(xpath = "//div[@class='forSameRow marginSearch80 col-12 col-sm-12 col-md-12 col-lg-12']//form//*[name()='svg']")
	WebElement searchIcorn;

	@FindBy(xpath = "//button[contains(text(),'Search')]")
	WebElement searchbutton;

	@FindBy(xpath = "//p[@class=\"forMobTxtCenter font12 white Cand10K\"]")
	List<WebElement> uploadYourCV;

	@FindBy(xpath = "//div[contains(text(),'Popular Job Categories')]")
	WebElement ThePopularJobCategories;

	@FindBy(xpath = "//div[@class=\"margLeft10 fontRes forHomeHover\"]")
	WebElement AccountingorFinancebutton;

	@FindBy(xpath = "//div[contains(text(),'Marketing')]")
	WebElement Marketingbutton;

	@FindBy(xpath = "//div[contains(text(),'Design')]")
	WebElement designbutton;

	@FindBy(xpath = "//div[contains(text(),'Development')]")
	WebElement Developmentbutton;

	@FindBy(xpath = "//div[contains(text(),'Customer Service')]")
	WebElement CustomerServicebutton;

	@FindBy(xpath = "//div[contains(text(),'Automative Jobs')]")
	WebElement AutomativeJobsbutton;

	@FindBy(xpath = "//div[contains(text(),'Register an account to start')]")
	WebElement registerAnAccountToStartCard;

	@FindBy(xpath = "//div[contains(text(),'Explore over thousand of resumes')]")
	WebElement ExploreOverThousandOfResumesCard;

	@FindBy(xpath = "//div[contains(text(),'Find the most suitable candidate')]")
	WebElement FindTheMostSuitableCandidateText;

	@FindBy(xpath = "//button[contains(text(),'Post Resume')]")
	WebElement postResumeButton;

	@FindBy(xpath = "//div[contains(@class,'pb-xl-11 pb-lg-11 pb-md-11 pb-sm-11 pb-0')]//p[@class='forMobTxtCenter colorPink marginTopMinus10'][normalize-space()='Browse All >']")
	WebElement browseAllbutton;

	@FindBy(xpath = "//div[contains(text(),'Latest Jobs')]")
	WebElement latestJobs;

	@FindBy(xpath = "//button[contains(text(),'Apply')]")
	List<WebElement> latestJobsApplyButton;

	@FindBy(xpath = "//img[@src=\"/static/media/img.3290a76e.png\"]")
	WebElement employerRegisterAnAccount;

	@FindBy(xpath = "//img[@src=\"/static/media/cand.41bc2985.jpg\"]")
	WebElement candidateRegisterAnAccount;

	@FindBy(xpath = "//input[@placeholder=\"Keyword e.g. (Job Title, Description, Tags)\"]")
	WebElement appliedJobPage;


	@FindBy(xpath="(//img[@src])[1]")
	protected WebElement ihplogo;
	
	@FindBy(xpath="//h4[contains(text(),'LOGIN AS JOBSEEKER')]")
	protected WebElement loginAsJobseeker;

	public index_page DisplayedPageTitle() {
		
		System.out.println("Index Page Title displayed is : " +driver.getTitle());
		return this;
		//Assert.assertEquals(driver.getTitle(),"Home - IHP");
	}
	
	public index_page ValidateLogo() {
		System.out.println("Logo displayed status is : " + ihplogo.isDisplayed());
		HighlightingElement(ihplogo);
		Assert.assertTrue(ihplogo.isDisplayed());
		return this;
	}

	public SignInAsJobSeeker ClickLoginAsJobSeeker() {
		HighlightingElement(loginAsJobseeker);
		click(loginAsJobseeker);
		return new SignInAsJobSeeker(driver,node);
	}
	
	
}