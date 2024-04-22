package ProjectPageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;

import com.aventstack.extentreports.ExtentTest;

import ProjectSpecificMethods.ProjectSpecificMethods;
import Selenium_Project_TestBase.BaseActions;


public class SignInAsJobSeeker extends ProjectSpecificMethods {

	public SignInAsJobSeeker(RemoteWebDriver driver, ExtentTest node){
		this.driver = driver;
		this.node = node;
	}
	BaseActions baseActions = new BaseActions();

	@FindBy(xpath="//h4[contains(text(),'Sign in')]")
	protected WebElement SignInJobSeekerHeader;

	@FindBy(xpath="//input[@type='email']")
	protected WebElement emailfield;

	@FindBy(xpath="//input[@type='password']")
	protected WebElement passwordfield;

	@FindBy(xpath="//button[@type='submit']")
	protected WebElement signinbutton;

	public SignInAsJobSeeker ValidateSignInAsJobSeekerPage() {
		HighlightingElement(SignInJobSeekerHeader);
		SignInJobSeekerHeader.isDisplayed();
		return this;
	}

	public SignInAsJobSeeker SetRegisteredEmailId() {
		//	BaseClass.HighlightingElement(emailfield);
		clickAndType(emailfield, "lgstester50@gmail.com");
		//emailfield.sendKeys("lgstester50@gmail.com");
		return this;
	}

	public SignInAsJobSeeker SetPassword() {
		//BaseClass.HighlightingElement(passwordfield);
		clickAndType(passwordfield,"Test123" );
		//passwordfield.sendKeys("Test123");
		return this;
	}

	public OTP_VerificationPage ClickSignInButton() {
		//	BaseClass.HighlightingElement(signinbutton);
		click(signinbutton);
		
		return new OTP_VerificationPage(driver,node);
		//signinbutton.click();
	}

}
