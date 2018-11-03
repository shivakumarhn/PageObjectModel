package com.crm.qa.testcases;

import com.crm.qa.base.TestBase;
import com.crm.qa.pages.LoginPage;
import com.crm.qa.util.TestUtil;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.IOException;

public class ExtentReportScreenshotTest extends TestBase {


    LoginPage loginPage;
    public ExtentReports extent;
    public ExtentTest extentTest;

    public ExtentReportScreenshotTest() {
        super();
    }

    @BeforeTest
    public void setExtent() {
        extent = new ExtentReports(System.getProperty("user.dir") + "/test-output/extentScreenshotReport.html", true);
        extent.addSystemInfo("user name", "Shivakumar");
        extent.addSystemInfo("Environment", "UAT");
    }

    @BeforeMethod
    public void setUp() {
        initialization();
        loginPage = new LoginPage();
    }


    @Test(priority = 1)
    public void loginPageTitleTest() {
        extentTest = extent.startTest("loginPageTitleTest");
        String title = loginPage.validateLoginPageTitle();
        Assert.assertEquals(title, "#1 Free CRM software in the cloud for sales and service");
    }

    @Test(priority = 2)
    public void crmLogoImageTest() {
        extentTest = extent.startTest("crmLogoImageTest");
        boolean flag = loginPage.validateCrmImage();
        Assert.assertTrue(flag);
    }

    @AfterMethod
    public void tearDown(ITestResult result) throws IOException {
        if (result.getStatus() == ITestResult.FAILURE) {
            extentTest.log(LogStatus.FAIL, "TEST CASE FAILED IS " + result.getName()); //To add name in extent report
            extentTest.log(LogStatus.FAIL, "TEST CASE FAILED IS " + result.getThrowable()); // To add error/exception in extent report
            String screenShotPath = TestUtil.getScreenshot(driver, result.getName());
//            extentTest.log(LogStatus.FAIL, extentTest.addScreencast(screenShotPath)); //To add screenCast or Video in extent report
            extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(screenShotPath)); //To add screenshot in extent report

        } else if (result.getStatus() == ITestResult.SKIP) {
            extentTest.log(LogStatus.SKIP, "TEST CASE SKIPPED IS " + result.getName());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            extentTest.log(LogStatus.PASS, "TEST CASE PASSED IS " + result.getName());
        }
        extent.endTest(extentTest); //ending test and ends the current test and prepare to create html report


        driver.quit();
    }

    @AfterTest
    public void endReport() {
        extent.flush();
        extent.close();
    }
}
