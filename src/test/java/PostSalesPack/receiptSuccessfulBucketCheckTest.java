package PostSalesPack;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.mail.EmailException;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.Status;

import adminPages.AdminDashboardPage;
import crm.selldo.LoginPage;
import dashboardBucketsPack.NewEnquiries_VerificationTest;
import postSales.PostSalesDashboardPage;
import utility.*;

public class receiptSuccessfulBucketCheckTest extends SetUp{
  
	WebDriverWait wait;

	final static Logger logger = Logger.getLogger(NewEnquiries_VerificationTest.class);

	@BeforeTest

	public void postsales_PostsalesManagerLogin() throws Exception {

		mysetUp();

		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		Properties property = new Properties();
		FileInputStream fileInputObj = new FileInputStream(
				System.getProperty("user.dir") + "//src//main//java//Config File//global.properties");
		property.load(fileInputObj);

		LoginPage login = new LoginPage(driver);
		logger.info("Logging in.......");
		login.login(property.getProperty("psManager_email"),
				property.getProperty("password"));

	}

	@AfterTest

	public void endingTest() throws Exception {

		Thread.sleep(3000);

		AdminDashboardPage adminDashboardPage = new AdminDashboardPage(driver);

		logger.info("Logging out of Selldo......");
		adminDashboardPage.loggingOut();

		logger.info("Closing Browser......");
		driver.close();
	}

	@Test

	public void SuccessfulBucketCount_VerificationTest() throws Exception {

		Properties property = new Properties();
		FileInputStream fileInputObj = new FileInputStream(
				System.getProperty("user.dir") + "//src//main//java//Config File//global.properties");
		property.load(fileInputObj);

		test = extent.createTest("SuccessfulBucketCount_VerificationTest");
		setExtentTest(test);

		PostSalesDashboardPage postsalesDashboardPage = new PostSalesDashboardPage(driver);

		getExtTest().log(Status.INFO, "Refreshing the User Dashboard.......");
		postsalesDashboardPage.refreshDashboardStats();

		Thread.sleep(3000);

		getExtTest().log(Status.INFO, "Getting count of receipts in Successful bucket......");
		WebElement count_b = driver.findElement(By.xpath("//div[text()='Successful']/following::span[1]"));
		String leadCount_b = count_b.getText();
		System.out.println(leadCount_b);

		getExtTest().log(Status.INFO, "Clicking on Successful bucket.......");
		postsalesDashboardPage.clickOnSuccessfulReceiptBucket();

		// ..........Getting Count from the label under List DropDownList for the receipts in Successful stage.....
		
		Thread.sleep(5000);

		getExtTest().log(Status.INFO, "Getting Count from the label under List DropDownList for the receipts in Successful stage...");
		
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='demand-letters-count-note']")));
		WebElement count_a = driver.findElement(By.xpath("//p[@class='text-muted small my-2']/span"));
		String leadCount_a = count_a.getText();
		System.out.println(leadCount_a);

		SoftAssert assertion = new SoftAssert();

		getExtTest().log(Status.INFO,
				"Validating count is not same after clicking on demand letters in Successful bucket......"); 
		
		Assert.assertNotEquals(leadCount_b,leadCount_a, "Count is same as before clicking bucket");

		assertion.assertAll();

	}
}
