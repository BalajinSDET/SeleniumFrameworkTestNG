package TestCases;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import ProjectPageObjects.OTP_VerificationPage;
import ProjectPageObjects.SignInAsJobSeeker;
import ProjectPageObjects.index_page;
import ProjectSpecificMethods.ProjectSpecificMethods;


public class TC_001_SignInAsJobSeeker extends ProjectSpecificMethods{

	
	@BeforeTest
	public void setValues() {
		// These values are given for Extent report
		testCaseName = "Sign In As Job Seeker";
		testDescription = "Sign In As Job Seeker test cases with valid registered email address";
		nodes = "Apply a Posted Jobs";
		authors = "Balaji";
		category = "Smoke Testing";
		browserName = "Chrome";
		portal = "JobSeeker";
	//	dataSheetName = "";
	}
	

	@Test(priority=3) //,timeOut=3000
	public void Validate_SignINAsJobSeeker() {
		index_page ip = new index_page(driver,node);
		ip.ClickLoginAsJobSeeker();
		SignInAsJobSeeker js = new SignInAsJobSeeker(driver,node);
		js.ValidateSignInAsJobSeekerPage();
		js.SetRegisteredEmailId();
		js.SetPassword();
		js.ClickSignInButton();
		OTP_VerificationPage OTP =new OTP_VerificationPage(driver,node);
		OTP.VerifyOTPHeading();
		OTP.ClickEmailRadioBtn();
		OTP.ClickSubmitBtn();
		OTP.EnterOTP();
		OTP.ClickVerifiedOtpSubmitBtn();
	}
	
	
}
