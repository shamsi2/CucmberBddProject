package TestRunner;
//import org.junit.runner.RunWith;

//import org.junit.runner.RunWith;

import io.cucumber.testng.CucumberOptions;

//import io.cucumber.junit.Cucumber;
//import io.cucumber.junit.CucumberOptions;
//import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.AbstractTestNGCucumberTests;

//@RunWith(Cucumber.class)
@CucumberOptions(

		features = {".//Features/LoginFeature.feature",".//Features/Customer.feature"},
		//features = ".//Features/",
		glue="StepDefination",
		dryRun = false,
		monochrome = true,
		tags = "@Sanity",//scenarios under @sanity tag will be executed

		//plugin = {"pretty","html:target/cucumber-reports/reports_html.html"}
		plugin = {"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"}
		)


// We Can Use All 3 Format by separated comma ,,
//plugin = {"pretty","junit:target/cucumber-reports/reports_xml.xml"}
//plugin = {"pretty","json:target/cucumber-reports/report_json.json"}

public class Run extends AbstractTestNGCucumberTests{
	/* This Class Will Be Empty*/

}
