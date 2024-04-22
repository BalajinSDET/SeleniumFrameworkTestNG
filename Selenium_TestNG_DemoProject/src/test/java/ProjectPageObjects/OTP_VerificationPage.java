package ProjectPageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentTest;

import ProjectSpecificMethods.ProjectSpecificMethods;
import Selenium_Project_TestBase.BaseActions;
import Utils.OtpReader;


public class OTP_VerificationPage extends ProjectSpecificMethods {

	public OTP_VerificationPage(RemoteWebDriver driver, ExtentTest node){
		this.driver = driver;
		this.node = node;
	}
	BaseActions baseActions = new BaseActions();

    @FindBy(xpath = " //div[contains(text(),'OTP Verification')]")
    public WebElement verifyHeading;

    @FindBy(xpath = "//input[@value='email']")
    public WebElement emailRBtn;

    @FindBy(xpath = "//button[contains (text(),'Submit')]")
    public WebElement submitBtn;

    @FindBy(xpath = "/html/body/div[3]/div/div/div[3]/div[1]")
    public WebElement ClickOTPfield;

    @FindBy(xpath = "//input[@placeholder='Enter OTP']")
    public WebElement enterOTP;

    @FindBy(xpath = "//h5[contains(text(),'Submit ')]")
    public WebElement VerifyOTPSubmitBtn;


    public OTP_VerificationPage VerifyOTPHeading() {
        try {
            Thread.sleep(500);
            HighlightingElement(verifyHeading);
            String ActualHeading = verifyHeading.getText();
            String ExpectedHeading = "OTP Verification";
            Assert.assertEquals(ActualHeading, ExpectedHeading);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public OTP_VerificationPage ClickEmailRadioBtn() {
      //  BaseClass.HighlightingElement(emailRBtn);
    	explicitWait(emailRBtn);
       // emailRBtn.click();
    	click(emailRBtn);
    	return this;
    }

    public OTP_VerificationPage ClickSubmitBtn() {
        //BaseClass.HighlightingElement(submitBtn);
       explicitWait(submitBtn);
       click(submitBtn);
       return this;
        
    }

    public OTP_VerificationPage EnterOTP() {
        HighlightingElement(enterOTP);
        String otp = OtpReader.getOtp();
        enterOTP.sendKeys(otp);
        return this;
    }

    public JobSeekerDashboardPage ClickVerifiedOtpSubmitBtn() {
      //  BaseClass.HighlightingElement(VerifyOTPSubmitBtn);
       // VerifyOTPSubmitBtn.click();
    	click(VerifyOTPSubmitBtn);
    	return new JobSeekerDashboardPage(driver,node);
    }
}