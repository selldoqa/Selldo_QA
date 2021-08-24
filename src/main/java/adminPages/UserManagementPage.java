package adminPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import utility.SetUp;

public class UserManagementPage extends SetUp {

	By manageTeam = By.xpath("//span[text()='manage teams']");
	By manageUsers = By.xpath("//span[text()='manage users']");

	WebDriver driver = null;
	WebDriverWait wait;

	public UserManagementPage(WebDriver driver) {

		this.driver = driver;
		wait = new WebDriverWait(driver, 8);
	}

	public void clickOnManageTeam() {
		driver.findElement(manageTeam).click();
	}
	
	public void clickOnManageUsers() {
		driver.findElement(manageUsers).click();
	}
}
