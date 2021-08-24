package adminPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import utility.SetUp;

public class ManageTeamsPage extends SetUp {

	By newTeamLink = By.xpath("//a[text()='New Team']");
	By actionBar = By.xpath("//th[text()='Actions']/following::i[1]");
	By edit = By.xpath("//th[text()='Actions']/following::a[text()='Edit'][1]");
	public By funnelIcon = By.xpath("//i[@class='ion-funnel']");
	By searchInputField = By.xpath("//input[@name='team_filters[search_string]']");
	By applyButton = By.xpath("//input[@type='submit'][@value='Apply']");

	WebDriver driver = null;
	WebDriverWait wait;

	public ManageTeamsPage(WebDriver driver) {

		this.driver = driver;
		wait = new WebDriverWait(driver, 8);
	}

	public void clickOnNewTeam() {
		driver.findElement(newTeamLink).click();
	}

	public void clickOnActionBar() {
		driver.findElement(actionBar).click();
	}

	public void clickOnEdit() {
		driver.findElement(edit).click();
	}
	
	
	public void searchTeam(String teamName) {
		driver.findElement(funnelIcon).click();
		driver.findElement(searchInputField).sendKeys(teamName);
		driver.findElement(applyButton).click();
	}

}
