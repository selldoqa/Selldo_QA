package workingTestscripts;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.mail.EmailException;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.Status;

import adminPages.AdminDashboardPage;
import crm.selldo.EmailPage;
import crm.selldo.LeadProfilePage;
import crm.selldo.LoginPage;
import crm.selldo.SalesPresalesDashboardPage;
import utility.SetUp;

public class SendingEmailWithTemplateTest extends SetUp {

	final static Logger logger = Logger.getLogger(SendingEmailWithTemplateTest.class);

	// Description:

	@BeforeTest

	public void sales_presalesLogin() throws Exception {

		mysetUp();

		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		Properties property = new Properties();
		FileInputStream fileInputObj = new FileInputStream(
				System.getProperty("user.dir") + "//src//main//java//Config File//global.properties");
		property.load(fileInputObj);

		LoginPage login = new LoginPage(driver);

		logger.info("Logging in.......");
		login.login(property.getProperty("name") + "+" + property.getProperty("user_sendingEmailWithTemplateTest"),
				property.getProperty("password"));
	}

	@Test

	public void sendingEmailWithTemplateTest() throws Exception {

		Properties property = new Properties();
		FileInputStream fileInputObj = new FileInputStream(
				System.getProperty("user.dir") + "//src//main//java//Config File//global.properties");
		property.load(fileInputObj);

		test = extent.createTest("sendingEmailWithTemplateTest");
		setExtentTest(test);

		AdminDashboardPage adminDashboardPage = new AdminDashboardPage(driver);

		getExtTest().log(Status.INFO, "Searching lead by Id.......");
		adminDashboardPage.searchLead(property.getProperty("sending_Email_lead_id"));

		LeadProfilePage leadProfilePage = new LeadProfilePage(driver);

		getExtTest().log(Status.INFO, "Clicking on Email Link.......");
		leadProfilePage.clickOnEmailLink();
		
		Thread.sleep(2000);

		EmailPage emailPage = new EmailPage(driver);

		getExtTest().log(Status.INFO, "Selecting Email template from dropdown.......");
		emailPage.selectTemplate(property.getProperty("email_template"));

		Thread.sleep(1000);
		getExtTest().log(Status.INFO, "Clicking on Send Email Button.......");
		emailPage.clickOnSendEmailButton();

		Thread.sleep(3000);

		getExtTest().log(Status.INFO, "Clicking on Email link under activities section.......");
		leadProfilePage.clickEmail_d();

		Thread.sleep(4000);
		
		SalesPresalesDashboardPage salesPresalesDashboardPage = new SalesPresalesDashboardPage(driver);
		salesPresalesDashboardPage.pageRefresh();

		getExtTest().log(Status.INFO, "Fetching the text appeared after sending email....");
		String text = driver.findElement(By.cssSelector(
				"#tab-activity > div.activities_list > div:nth-child(1) > div > div.card > div > div:nth-child(3) > div:nth-child(2) > span"))
				.getText();

		SoftAssert assertion = new SoftAssert();

		getExtTest().log(Status.INFO, "Verifying the text under Email activities....");
		Assert.assertEquals(text, "outgoing  |  delivered");

		assertion.assertAll();

	}

	@AfterTest

	public void endingTest() throws Exception {

		Thread.sleep(3000);

		logger.info("browser closed.......");
		driver.close();

	}

}
