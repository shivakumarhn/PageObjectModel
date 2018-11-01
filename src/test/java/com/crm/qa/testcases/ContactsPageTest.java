package com.crm.qa.testcases;

import com.crm.qa.base.TestBase;
import com.crm.qa.pages.ContactsPage;
import com.crm.qa.pages.HomePage;
import com.crm.qa.pages.LoginPage;
import com.crm.qa.util.TestUtil;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ContactsPageTest extends TestBase {

    LoginPage loginPage;
    HomePage homePage;
    ContactsPage contactsPage;
    TestUtil testUtil;

    String sheetName = "contacts";

    public ContactsPageTest() {
        super();
    }

    @BeforeMethod
    public void setUp() {
        initialization();
        testUtil = new TestUtil();
        loginPage = new LoginPage();
        contactsPage = new ContactsPage();
        homePage = loginPage.login(prop.getProperty("username"), prop.getProperty("password"));
        testUtil.switchToFrame();
        contactsPage = homePage.clickOnContactsLink();
    }

    @Test(priority = 1)
    public void verifyContactsPageLabel() {
        Assert.assertTrue(contactsPage.VerifyContactsLabel(), "Contacts label is missing on the page");
    }

    @Test(priority = 2)
    public void selectSingleContactTest() {
        contactsPage.selectContacts("Adame Zach");
    }

    @Test(priority = 3)
    public void selectMultipleContactsTest() {
        contactsPage.selectContacts("Gautam Gambhir");
        contactsPage.selectContacts("David Cris");
    }

    @DataProvider
    public Object[][] getCRMTestData() {
        Object data[][] = testUtil.getTestdata(sheetName);
        return data;
    }

    @Test(priority = 4, dataProvider = "getCRMTestData")
    public void validateCreateNewContactTest(String title, String firstName, String lastName, String company) {
        homePage.clickOnNewContactLink();
        contactsPage.createNewContact(title, firstName, lastName, company);
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }


}
