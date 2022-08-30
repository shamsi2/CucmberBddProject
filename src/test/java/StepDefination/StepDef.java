package StepDefination;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import PageObject.AddNewCustomerPage;
import PageObject.LoginPage;
import PageObject.SearchCustomerPage;
import Utilites.ReadConfig;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.*;

public class StepDef extends BaseClass {

	@Before
	public void setup() throws IOException
	{

		readConfig = new ReadConfig();


		//Initialize logger
		log = LogManager.getLogger("StepDef");

		System.out.println("Setup-Sanity method executed..");

		String browser = readConfig.getBrowser();

		//launch browser
		switch(browser.toLowerCase())
		{
		case "chrome":
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			break;

		case "msedge":
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
			break;

		case "firefox":
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			break;
		default:
			driver = null;
			break;

		}


		log.info("Setup executed...");
	}


	@Given("User Launch Chrome browser")
	public void user_launch_chrome_browser() {


		loginPg= new LoginPage(driver);
		addNewCustPg = new AddNewCustomerPage(driver);
		SearchCustPg = new SearchCustomerPage(driver);
		log.info("launched Chrome Browser...");

	}

	@When("User opens URL {string}")
	public void user_opens_url(String url) {
		driver.get(url);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		log.info("url Open...");

	}

	@When("User enters Email as {string} and Password as {string}")
	public void user_enters_email_as_and_password_as(String email, String paasword) {
		loginPg.enterEmail(email);
		loginPg.enterPassword(paasword);
		log.info("Enter Email- Password...");

	}
	//////////Login feature///////////////////////////

	@When("Click on Login")
	public void click_on_login() {
		loginPg.clickOnLoginButton();
		log.info("Login Button Clicked...");

	}

	@Then("Page Title should be {string}")
	public void page_title_should_be(String expectedTitle) throws InterruptedException {
		Thread.sleep(2000);
		String ActTitle = driver.getTitle();
		if (ActTitle.equals(expectedTitle))
		{

			Assert.assertTrue(true);// Passed
			log.warn("Test Passed : Login Title Page Found");
		}
		else
		{
			log.warn("Test Failed : Login Feature Page Title Not Matched...");
			Assert.assertTrue(false);// Failed
		}

	}

	@When("User click on Log out link")
	public void user_click_on_log_out_link() throws InterruptedException {
		Thread.sleep(2000);
		loginPg.clickOnLogOutButton();
		log.info("Logout Button Clicked...");
	}




	///////////////////////////Add new customer/////////////////////

	@Then("User can view Dashboad")
	public void user_can_view_dashboad() throws InterruptedException {
		Thread.sleep(2000);
		String actualTitle = addNewCustPg.getPageTitle();
		String expectedTitle = "Dashboard / nopCommerce administration";

		if(actualTitle.equals(expectedTitle))
		{

			Assert.assertTrue(true);
			log.info("Test Passed : User_can_view_dashboad...");
		}
		else
		{   
			log.info("Test Failed : Page Not Found");
			Assert.assertTrue(false);


		}
	}

	@When("User click on customers Menu")
	public void user_click_on_customers_menu() {
		addNewCustPg.clickOnCustomersMenu();
		log.info("Clicked on Customers Menu...");
	}

	@When("click on customers Menu Item")
	public void click_on_customers_menu_item() {
		addNewCustPg.clickOnCustomersMenuItem();
		log.info("Clicked on customers Menu Item...");
	}

	@When("click on Add new button")
	public void click_on_add_new_button() {
		addNewCustPg.clickOnAddnew();
		log.info("Clicked on Add new button...");
	}

	@Then("User can view Add new customer page")
	public void user_can_view_add_new_customer_page() throws InterruptedException {
		Thread.sleep(2000);
		String actualTitle = addNewCustPg.getPageTitle();
		String expectedTitle = "Add a new customer / nopCommerce administration";

		if(actualTitle.equals(expectedTitle))
		{


			Assert.assertTrue(true);//pass
			log.info("User can view Add new customer page: Test Passed...");
		}
		else
		{

			log.info("User cant view Add new customer page: Test Failed...");
			Assert.assertTrue(false);//fail
		}
	}

	@When("User enter customer info")
	public void user_enter_customer_info() {
		//addNewCustPg.enterEmail("raj1@gmail.com");
		addNewCustPg.enterEmail(generateEmailId() + "@gmail.com");
		addNewCustPg.enterPassword("test1");
		addNewCustPg.enterFirstName("Shan");
		addNewCustPg.enterLastName("Shamsi");
		addNewCustPg.enterGender("Male");
		addNewCustPg.enterDob("6/13/1988");
		addNewCustPg.enterCompanyName("TimesNow");
		addNewCustPg.enterAdminContent("Admin content");
		addNewCustPg.enterManagerOfVendor("Vendor 1");

		log.info("Coustumer Information Entred...");
	}

	@When("click on Save button")
	public void click_on_save_button() {
		addNewCustPg.clickOnSave();
		log.info("clicked on Save button...");


	}

	@Then("User can view confirmation message {string}")
	public void user_can_view_confirmation_message(String exptectedConfirmationMsg) {

		String bodyTagText = driver.findElement(By.tagName("Body")).getText();
		if(bodyTagText.contains(exptectedConfirmationMsg))
		{

			Assert.assertTrue(true);//pass
			log.info("User can view confirmation message : Test Passed...");

		}
		else
		{

			log.info("User cant view confirmation message : Test Failed...");
			Assert.assertTrue(false);//fail

		}

	}

	/*@Then("close browser")
	public void close_browser() {
		driver.close();
		log.info("Browser Closed...");

	}*/

	////////////Search Customer//////////////////////////
	@When("Enter customer EMail")
	public void enter_customer_e_mail() {
		SearchCustPg.enterEmailAdd("victoria_victoria@nopCommerce.com");
		log.info("Customer EMail Entered");

	}

	@When("Click on search button")
	public void click_on_search_button() {
		SearchCustPg.clickOnSearchButton();
		log.info("Clicked on search button...");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Then("User should found Email in the Search table")
	public void user_should_found_email_in_the_search_table() {
		String expectedEmail = "victoria_victoria@nopCommerce.com";

		//   We Can Also Use This line of code instead	of if else
		//   Assert.assertTrue(SearchCustPg.searchCustomerByEmail(expectedEmail));

		if( SearchCustPg.searchCustomerByEmail(expectedEmail) ==true)
		{

			Assert.assertTrue(true);
			log.info("User found Email in the Search table: Test Passed...");

		}
		else {

			log.info("User Cant find Email in the Search table: Test Failed...");
			Assert.assertTrue(false);

		}

	}

	///////////////search customer by name////////////////////


	@When("Enter customer FirstName")
	public void enter_customer_first_name() {
		SearchCustPg.enterFirstName("Victoria");
		log.info("Customer FirstName Entred...");
	}

	@When("Enter customer LastName")
	public void enter_customer_last_name() {
		SearchCustPg.enterLastName("Terces");
		log.info("Customer LastName Entred...");

	}

	@Then("User should found Name in the Search table")
	public void user_should_found_name_in_the_search_table() {
		String expectedName = "Victoria Terces";


		if( SearchCustPg.searchCustomerByName(expectedName) ==true)
		{

			Assert.assertTrue(true);
			log.info("User found First Name in the Search table: Test Passed...");
		}
		else

			log.info("User Cant Find First Name in the Search table: Test Failed...");
		Assert.assertTrue(false);

	}

	/*@After
	public void TearDown(Scenario sc)
	{
		System.out.println("\nTear Down method executed..\n");
		if(sc.isFailed()==true)
		{
			log.info("Screen Shot Captured");
			//Convert web driver object to TakeScreenshot

			String fileWithPath = "C:\\Users\\user\\Desktop\\workspace\\CucmberFrameWork\\screenshot\\failedScreenshot.png";
			TakesScreenshot scrShot =((TakesScreenshot)driver);

			//Call getScreenshotAs method to create image file
			File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);

			//Move image file to new destination
			File DestFile=new File(fileWithPath);

			//Copy file at destination

			try {
				FileUtils.copyFile(SrcFile, DestFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		log.info("Driver Closed");
		driver.quit();
	}*/

	@AfterStep
	public void Addscreenshot(Scenario scenario)
	{
		if(scenario.isFailed())
		{
			final byte[] screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
			scenario.attach(screenshot, "image/png",scenario.getName());
		}
		

	}

}




