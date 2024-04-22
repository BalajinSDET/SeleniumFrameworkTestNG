package Selenium_Project_TestBase;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.WheelInput;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import Utils.Reporter;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.sukgu.Shadow;


public class BaseActions extends Reporter implements Browser, Element {

	static public RemoteWebDriver driver;
	public WebDriverWait wait;

	int i = 1;

	@Override
	public RemoteWebDriver startApp(String url) {
		return startApp("chrome", url);
	}

	@Override
	public RemoteWebDriver startApp(String browser, String url) {
		try {
			if (browser.equalsIgnoreCase("chrome")) {
				WebDriverManager.chromedriver().setup();
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--remote-allow-origins=*");
				driver = new ChromeDriver(options);
			} else if (browser.equalsIgnoreCase("firefox")) {
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
			} else if (browser.equalsIgnoreCase("ie")) {
				WebDriverManager.iedriver().setup();
				driver = new InternetExplorerDriver();
			} else if (browser.equalsIgnoreCase("Edge")) {
				WebDriverManager.edgedriver().setup();
				driver = new EdgeDriver();
			}
			driver.navigate().to(url);
			Thread.sleep(1000);
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
			driver.manage().window().maximize();
		} catch (Exception e) {
			reportStep("The Browser Could not be Launched. Hence Failed " +e+ "", "fail");
			throw new RuntimeException();
		}
		return driver;
	}

	@Override
	public WebElement locateElement(String locatorType, String value) {
		try {

			switch (locatorType.toLowerCase()) {
			case "id":
				return driver.findElement(By.id(value));
			case "name":
				return driver.findElement(By.name(value));
			case "class":
				return driver.findElement(By.className(value));
			case "link":
				return driver.findElement(By.linkText(value));
			case "xpath":
				return driver.findElement(By.xpath(value));
			case "css":
				return driver.findElement(By.cssSelector(value));
			}
		} catch (NoSuchElementException e) {
			reportStep("The Element with locator:" + locatorType + " Not Found with value: " + value, "fail");
			throw new RuntimeException();
		} catch (Exception e) {
			reportStep("The Element with locator:" + locatorType + " Not Found with value: " + value, "fail");
		}
		return null;
	}

	@Override
	public WebElement locateElement(String value) {
		WebElement findElementById;
		try {
			findElementById = driver.findElement(By.id(value));
		} catch (NoSuchElementException e) {
			reportStep("The Element Not Found with value: " + value, "fail");
			throw new RuntimeException();
		}
		return findElementById;
	}

	@Override
	public List<WebElement> locateElements(String type, String value) {
		try {
			switch (type.toLowerCase()) {
			case "id":
				return driver.findElements(By.id(value));
			case "name":
				return driver.findElements(By.name(value));
			case "class":
				return driver.findElements(By.className(value));
			case "link":
				return driver.findElements(By.linkText(value));
			case "xpath":
				return driver.findElements(By.xpath(value));
			case "css":
				return driver.findElements(By.cssSelector(value));
			}
		} catch (NoSuchElementException e) {
			System.err.println("The Element with locator:" + type + " Not Found with value: " + value);
			throw new RuntimeException();
		}
		return null;
	}

	public WebElement ShDomLocateElement(String value) {
		try {

			Shadow shDom = new Shadow(driver);
			return shDom.findElementByXPath(value);
		} catch (NoSuchElementException e) {
			reportStep("The Element with ShadowDom Xpath locator Not Found with value: " + value, "fail");
			throw new RuntimeException();
		} catch (Exception e) {
			reportStep("The Element with ShadowDom Xpath locator " + e + " Not Found with value:" + value, "fail");
		}
		return null;
	}

	public WebElement locateShadoDom(WebElement ele) {
		try {

			WebElement root = ele;
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			return (WebElement) executor.executeScript("arguments[0].shadowRoot;", root);
		} catch (NoSuchElementException e) {
			reportStep("The Element with ShadowDom Xpath locator Not Found with value: " + ele, "fail");
			throw new RuntimeException();
		} catch (Exception e) {
			reportStep("The Element with ShadowDom Xpath locator " + e + " Not Found with value:" + ele, "fail");
		}
		return null;
	}

	@Override
	public void click(WebElement ele) {
		String text = "";
		try {
			wait = new WebDriverWait(driver, Duration.ofSeconds(30));
			wait.until(ExpectedConditions.elementToBeClickable(ele));
			text = ele.getText();
			//			System.out.println(text);
			ele.click();
			reportStep("The Element " + text + " clicked", "pass");
		} catch (StaleElementReferenceException e) {
			reportStep("The Element " + text + " could not be clicked", "fail");
			throw new RuntimeException();
		} catch (Exception e) {
			reportStep("The Element " + e + " could not be clicked", "fail");
			throw new RuntimeException();
		}
	}

	public void waitAndClick(WebElement ele) {
		String text = "";
		try {
			wait = new WebDriverWait(driver, Duration.ofSeconds(122));
			wait.until(ExpectedConditions.elementToBeClickable(ele));
			text = ele.getText();
			//			System.out.println(text);
			ele.click();
			reportStep("The Element " + text + " clicked", "pass");
		} catch (StaleElementReferenceException e) {
			reportStep("The Element " + text + " could not be clicked", "fail");
			throw new RuntimeException();
		} catch (Exception e) {
			reportStep("The Element " + e + " could not be clicked", "fail");
			throw new RuntimeException();
		}
	}



	public void actionClick(WebElement ele) throws InterruptedException {
		String text = "";
		try {
			wait = new WebDriverWait(driver, Duration.ofSeconds(30));
			wait.until(ExpectedConditions.elementToBeClickable(ele));
			Actions action = new Actions(driver);
			text = ele.getText();
			action.moveToElement(ele).click().build().perform();
			reportStep("The Element " + text + " clicked", "pass");
		} catch (StaleElementReferenceException e) {
			reportStep("The Element " + text + " could not be clicked", "fail");
			throw new RuntimeException();
		} catch (Exception e) {
			reportStep("The Element " + e + " could not be clicked", "fail");
			throw new RuntimeException();
		}
	}

	public void actionVisibleClick(WebElement ele) throws InterruptedException {
		String text = "";
		try {
			wait = new WebDriverWait(driver, Duration.ofSeconds(30));
			wait.until(ExpectedConditions.visibilityOf(ele));
			Actions action = new Actions(driver);
			text = ele.getText();
			action.moveToElement(ele).click().build().perform();
			reportStep("The Element " + text + " clicked", "pass");
		} catch (StaleElementReferenceException e) {
			reportStep("The Element " + text + " could not be clicked", "fail");
			throw new RuntimeException();
		} catch (Exception e) {
			reportStep("The Element " + e + " could not be clicked", "fail");
			throw new RuntimeException();
		}
	}


	public void actionScrollClick(WebElement ele) throws InterruptedException {
		String text = "";
		try {

			WheelInput.ScrollOrigin scrollOrigin = WheelInput.ScrollOrigin.fromElement(ele);
			Actions action = new Actions(driver);
			action.scrollFromOrigin(scrollOrigin, 100, 200)
			.perform();
			actionClick(ele);
			reportStep("The Element " + text + " clicked", "pass");
		} catch (StaleElementReferenceException e) {
			reportStep("The Element " + text + " could not be clicked", "fail");
			throw new RuntimeException();
		} catch (Exception e) {
			reportStep("The Element " + e + " could not be clicked", "fail");
			throw new RuntimeException();
		}
	}

	public void jsClick(WebElement ele) throws InterruptedException {
		String text = "";
		try {
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			wait = new WebDriverWait(driver, Duration.ofSeconds(30));
			wait.until(ExpectedConditions.elementToBeClickable(ele));
			text = ele.getText();
			executor.executeScript("arguments[0].click();", ele);
			reportStep("The Element " + text + " clicked", "pass");

		} catch (StaleElementReferenceException e) {
			reportStep("The Element " + text + " could not be clicked", "fail");
			throw new RuntimeException();
		} catch (Exception e) {
			reportStep("The Element " + e + " could not be clicked", "fail");
			throw new RuntimeException();
		}
	}

	public void jsScrollClick(WebElement ele) throws InterruptedException {
		String text = "";
		try {
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].scrollIntoView(true);", ele);
			text = ele.getText();
			ele.click();
			reportStep("The Element " + text + " clicked", "pass");

		} catch (StaleElementReferenceException e) {
			reportStep("The Element " + text + " could not be clicked", "fail");
			throw new RuntimeException();
		} catch (Exception e) {
			reportStep("The Element " + e + " could not be clicked", "fail");
			throw new RuntimeException();
		}
	}

	public void clickWithNoSnap(WebElement ele) {
		String text = "";
		try {
			text = ele.getText();
			wait = new WebDriverWait(driver, Duration.ofSeconds(15));
			wait.until(ExpectedConditions.elementToBeClickable(ele));
			ele.click();
			reportStep("The Element with text: " + text + " clicked", "pass", false);
		} catch (StaleElementReferenceException e) {
			reportStep("The Element " + ele + " could not be clicked", "fail");
			throw new RuntimeException();
		} catch (Exception e) {
			System.err.println(e);
		}

	}

	@Override
	public void append(WebElement ele, String data) {
		ele.sendKeys(data);
	}


	@Override
	public void clear(WebElement ele) {
		try {
			ele.clear();
			reportStep("The field is cleared Successfully", "pass");
		} catch (ElementNotInteractableException e) {
			reportStep("The field is not Interactable", "fail");
			throw new RuntimeException();
		}catch (Exception e) {
			reportStep("The Element " + e + " could not be clicked", "fail");
			throw new RuntimeException();
		}
	}

	@Override
	public void clearAndType(WebElement ele, String data) {
		try {
			ele.clear();
			ele.sendKeys(data);
			reportStep("The Data :" + data + " entered Successfully", "pass");
		} catch (ElementNotInteractableException e) {
			reportStep("The Element " + ele + " is not Interactable", "fail");
			throw new RuntimeException();
		}catch (Exception e) {
			reportStep("The Element " + e + " could not be clicked", "fail");
			System.out.println(e);
			throw new RuntimeException();
		}
	}

	public void jsType(WebElement ele, String data) {
		try {
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].value='"+data+"'", ele);

			reportStep("The Data :" + data + " entered Successfully", "pass");
		} catch (ElementNotInteractableException e) {
			reportStep("The Element " + ele + " is not Interactable", "fail");
			throw new RuntimeException();
		}catch (Exception e) {
			reportStep("The Element " + e + " could not be clicked", "fail");
			throw new RuntimeException();
		}
	}
	/**
	 * 
	 * @param ele
	 * @param data
	 * @throws InterruptedException
	 */
	public void actionType(WebElement ele,String data) throws InterruptedException {
		String text = "";
		try {
			wait = new WebDriverWait(driver, Duration.ofSeconds(15));
			wait.until(ExpectedConditions.elementToBeClickable(ele));
			Actions action = new Actions(driver);
			text = ele.getText();
			action.moveToElement(ele).sendKeys(data).build().perform();
			reportStep("The Element " + data + " entered", "pass");
		} catch (StaleElementReferenceException e) {
			reportStep("The Element " + data + " could not be entered", "fail");
			throw new RuntimeException();
		} catch (Exception e) {
			reportStep("The Element " + e + " could not be entered", "fail");
			throw new RuntimeException();
		}
	}

	public void clickAndType(WebElement ele, String data) {
		String text = "";
		try {
			wait = new WebDriverWait(driver, Duration.ofSeconds(40));
			wait.until(ExpectedConditions.elementToBeClickable(ele));
			text = ele.getText();
			ele.click();
			ele.sendKeys(data);
			Thread.sleep(1000);
			ele.sendKeys(Keys.ARROW_DOWN);
			Thread.sleep(1000);
			ele.sendKeys(Keys.ENTER);
			reportStep("The Element " + text + " clicked", "pass");
		} catch (StaleElementReferenceException e) {
			reportStep("The Element " + text + " could not be clicked", "fail");
			throw new RuntimeException();
		} catch (Exception e) {
			reportStep("The Element " + e + " could not be clicked", "fail");
			throw new RuntimeException();
		}
	}

	/**
	 * 
	 * @param ele
	 * @param data
	 * @throws InterruptedException
	 */
	public void actionKeyType(WebElement ele,String data) throws InterruptedException {
		String text = "";
		try {
			wait = new WebDriverWait(driver, Duration.ofSeconds(15));
			wait.until(ExpectedConditions.elementToBeClickable(ele));
			ele.clear();
			new Actions(driver)
			.click()
			.sendKeys(ele, data)
			.perform();
			reportStep("The Element " + data + " entered", "pass");
		} catch (StaleElementReferenceException e) {
			reportStep("The Element " + data + " could not be entered", "fail");
			throw new RuntimeException();
		} catch (Exception e) {
			reportStep("The Element " + e + " could not be entered", "fail");
			throw new RuntimeException();
		}
	}

	@Override
	public String getElementText(WebElement ele) {
		String text = ele.getText();
		return text;
	}

	@Override
	public String getBackgroundColor(WebElement ele) {
		String cssValue = ele.getCssValue("color");
		return cssValue;
	}

	@Override
	public String getTypedText(WebElement ele) {
		String attributeValue = ele.getAttribute("value");
		return attributeValue;
	}

	@Override
	public void selectDropDownUsingText(WebElement ele, String value) {
		new Select(ele).selectByVisibleText(value);
	}

	@Override
	public void selectDropDownUsingIndex(WebElement ele, int index) {
		new Select(ele).selectByIndex(index);
	}

	@Override
	public void selectDropDownUsingValue(WebElement ele, String value) {
		new Select(ele).selectByValue(value);
	}
	/**
	 * 
	 * @param ele
	 * @param data
	 */
	public void searchAndSelect(List<WebElement> ele, String data) {
		try {
			List<WebElement> l1 = ele;
			for (WebElement val : l1) {
				if (val.getText().equals(data)) {
					actionClick(val);		
					break;
				}
			}	
			reportStep("The Data :" + data + " selected Successfully", "pass");
		} catch (ElementNotInteractableException e) {
			reportStep("The Element " + ele + " is not Interactable", "fail");
			throw new RuntimeException();
		}catch (Exception e) {
			reportStep("The Element " + e + " could not be clicked", "fail");
			throw new RuntimeException();
		}
	}

	/*
	 * This method is used to search and select the element
	 * @param ele
	 * @param data
	 */
	public void searchAndSelect(WebElement ele, String data) throws InterruptedException {
		if(ele.getText().equals(data)) {
			actionClick(ele);
			reportStep("The Data :" + data + " selected Successfully", "pass");
		}

	}
	/**
	 * This method is used to draw signature 
	 * @param ele is the locator of the signature canvas
	 */
	public void drawSignature(WebElement ele) {
		try {
			Actions actionBuilder=new Actions(driver);          
			Action drawOnCanvas=actionBuilder
					.moveToElement(ele,8,8)
					.clickAndHold(ele)
					.moveByOffset(120, 120)
					.moveByOffset(60,70)
					.moveByOffset(-140,-140)
					.release(ele)
					.build();
			drawOnCanvas.perform();
			reportStep("Signature drawn successfully", "pass");
		} catch (StaleElementReferenceException e) {
			reportStep("Signature could not been drawn successfully", "fail");
			throw new RuntimeException();
		} catch (Exception e) {
			reportStep("Signature "+e+" could not been drawn successfully", "fail");
			throw new RuntimeException();
		}

	}

	public void sendImageFile(String querySelector, String imageName) throws InterruptedException {
		String text = "";
		try {
			JavascriptExecutor jse = ( JavascriptExecutor ) driver ;
			WebElement chooseImage = ( WebElement ) jse.executeScript ( " return "+querySelector+"");
			File file = new File("./file/"+imageName+".png");
			chooseImage.sendKeys(file.getAbsolutePath());

			reportStep("The Image " + imageName + " Uploaded Successfully", "pass");
		} catch (StaleElementReferenceException e) {
			reportStep("The Element " + e + " could not be Uploaded Successfully", "fail");
			throw new RuntimeException();
		} catch (Exception e) {
			reportStep("The Element " + e + " could not be Uploaded Successfully", "fail");
			throw new RuntimeException();
		}
	}

	@Override
	public boolean verifyExactText(WebElement ele, String expectedText) {
		try {			
			if (ele.getText().equals(expectedText)) {
				reportStep("The expected text " + ele.getText() + "contains the actual " + expectedText, "pass");
				return true;
			} else {
				reportStep("The expected text doesn't contain the actual "+ele.getText() + expectedText, "fail");
			}
		} catch (WebDriverException e) {
			System.out.println("Unknown exception occured while verifying the Text");
		}
		return false;
	}

	@Override
	public boolean verifyPartialText(WebElement ele, String expectedText) {
		try {

			if (ele.getText().contains(expectedText)) {
				reportStep("The expected text contains the actual " + expectedText, "pass");
				return true;
			} else {
				reportStep("The expected text doesn't contain the actual " + expectedText, "fail");
			}
		} catch (WebDriverException e) {
			System.out.println("Unknown exception occured while verifying the Text");
		}
		return false;
	}

	@Override
	public boolean verifyExactAttribute(WebElement ele, String attribute, String value) {
		try {
			if (ele.getAttribute(attribute).equals(value)) {
				reportStep("The expected attribute :" + attribute + " value contains the actual " + value, "pass");
				return true;
			} else {
				reportStep("The expected attribute :" + attribute + " value does not contains the actual " + value,
						"fail");
			}
		} catch (WebDriverException e) {
			System.out.println("Unknown exception occured while verifying the Attribute Text");
		}
		return false;
	}

	@Override
	public void verifyPartialAttribute(WebElement ele, String attribute, String value) {
		try {
			if (ele.getAttribute(attribute).contains(value)) {
				reportStep("The expected attribute :" + attribute + " value contains the actual " + value, "pass");
			} else {
				reportStep("The expected attribute :" + attribute + " value does not contains the actual " + value,
						"fail");
			}
		} catch (WebDriverException e) {
			System.out.println("Unknown exception occured while verifying the Attribute Text");
		}
	}

	@Override
	public boolean verifyDisplayed(WebElement ele) {
		try {
			if (ele.isDisplayed()) {
				reportStep("The element " + ele + " is visible", "pass");
				return true;
			} else {
				reportStep("The element " + ele + " is not visible", "fail");
			}
		} catch (WebDriverException e) {
			System.out.println("WebDriverException : " + e.getMessage());
		}
		return false;

	}

	@Override
	public boolean verifyDisappeared(WebElement ele) {
		return false;

	}

	@Override
	public boolean verifyEnabled(WebElement ele) {
		try {
			if (ele.isEnabled()) {
				reportStep("The element " + ele + " is Enabled", "pass");
				return true;
			} else {
				reportStep("The element " + ele + " is not Enabled", "fail");
			}
		} catch (WebDriverException e) {
			System.out.println("WebDriverException : " + e.getMessage());
		}
		return false;
	}

	@Override
	public void verifySelected(WebElement ele) {
		try {
			if (ele.isSelected()) {
				reportStep("The element " + ele + " is selected", "pass");
				// return true;
			} else {
				reportStep("The element " + ele + " is not selected", "fail");
			}
		} catch (WebDriverException e) {
			System.out.println("WebDriverException : " + e.getMessage());
		}
		// return false;

	}


	@Override
	public void switchToAlert() {
		driver.switchTo().alert();
	}

	@Override
	public void acceptAlert() {
		String text = "";
		try {
			wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.alertIsPresent());
			Alert alert = driver.switchTo().alert();
			text = alert.getText();
			alert.accept();
			reportStep("The alert " + text + " is accepted.", "pass");
		} catch (NoAlertPresentException e) {
			reportStep("There is no alert present.", "fail");
		} catch (WebDriverException e) {
			System.out.println("WebDriverException : " + e.getMessage());
		}

	}

	@Override
	public void dismissAlert() {
		String text = "";
		try {
			Alert alert = driver.switchTo().alert();
			text = alert.getText();
			alert.dismiss();
			System.out.println("The alert " + text + " is accepted.");
		} catch (NoAlertPresentException e) {
			System.out.println("There is no alert present.");
		} catch (WebDriverException e) {
			System.out.println("WebDriverException : " + e.getMessage());
		}
	}

	@Override
	public String getAlertText() {
		String text = "";
		try {
			Alert alert = driver.switchTo().alert();
			text = alert.getText();
		} catch (NoAlertPresentException e) {
			System.out.println("There is no alert present.");
		} catch (WebDriverException e) {
			System.out.println("WebDriverException : " + e.getMessage());
		}
		return text;
	}

	@Override
	public void typeAlert(String data) {
		driver.switchTo().alert().sendKeys(data);
	}

	@Override
	public void switchToWindow(int index) {
		try {
			Set<String> allWindows = driver.getWindowHandles();
			List<String> allhandles = new ArrayList<String>(allWindows);
			String exWindow = allhandles.get(index);
			driver.switchTo().window(exWindow);
			System.out.println("The Window With index: " + index + " switched successfully");
		} catch (NoSuchWindowException e) {
			System.err.println("The Window With index: " + index + " not found");
		}
	}

	@Override
	public void switchToWindow(String title) {
		try {
			Set<String> allWindows = driver.getWindowHandles();
			for (String eachWindow : allWindows) {
				driver.switchTo().window(eachWindow);
				if (driver.getTitle().equals(title)) {
					break;
				}
			}
			System.out.println("The Window With Title: " + title + "is switched ");
		} catch (NoSuchWindowException e) {
			System.err.println("The Window With Title: " + title + " not found");
		} finally {
			takeSnap();
		}
	}

	@Override
	public void switchToFrame(int index) {
		driver.switchTo().frame(index);

	}

	@Override
	public void switchToFrame(WebElement ele) {
		driver.switchTo().frame(ele);

	}

	@Override
	public void switchToFrame(String idOrName) {
		driver.switchTo().frame(idOrName);

	}

	@Override
	public void defaultContent() {
		driver.switchTo().defaultContent();

	}

	@Override
	public boolean verifyUrl(String url) {
		if (driver.getCurrentUrl().equals(url)) {
			System.out.println("The url: " + url + " matched successfully");
			return true;
		} else {
			System.out.println("The url: " + url + " not matched");
		}
		return false;
	}

	@Override
	public boolean verifyTitle(String title) {
		if (driver.getTitle().equals(title)) {
			System.out.println("Page title: " + title + " matched successfully");
			return true;
		} else {
			System.out.println("Page url: " + title + " not matched");
		}
		return false;
	}

	public long takeSnap() {
		long number = (long) Math.floor(Math.random() * 900000000L) + 10000000L;
		try {
			FileUtils.copyFile(driver.getScreenshotAs(OutputType.FILE),
					new File("./reports/images/" + number + ".jpg"));
		} catch (WebDriverException e) {
			System.out.println("The browser has been closed.");
		} catch (IOException e) {
			System.out.println("The snapshot could not be taken");
		}
		return number;
	}

	@Override
	public void close() {
		driver.close();

	}

	@Override
	public void quit() {
		driver.quit();

	}

	public String generatePhoneNumber() {
		String phNo="";
		for(int i=0;i<10;i++) {
			phNo=generateRandomNumber();
		}
		return phNo;
	}

	public String generateRandomNumber() {
		int min_prefix=700;
		int max_prefix=999;
		int min_no=1000000;
		int max_no=9999999;
		Random random=new Random();
		int prefix=random.nextInt(max_prefix - min_prefix+1)+min_prefix;
		int number=random.nextInt(max_no-min_no+1)+min_no;
		return prefix+String.format("%07d", number);
	}

	public String generateName() {
		int length = 10;
		boolean useLetters = true;
		boolean useNumbers = false;
		String generatedName = RandomStringUtils.random(length, useLetters, useNumbers);
		return generatedName;
	}

	public void HighlightingElement(WebElement ele) {
		explicitWait(ele);

		JavascriptExecutor JS = (JavascriptExecutor) driver;
		JS.executeScript("arguments[0].style.border='3px solid blue'", ele);
	}

	public void explicitWait(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.visibilityOf(element));
	}

}
