package Sprint3.tests;

import Sprint3.util.WebDriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class Tasks_Tab {

    WebDriver driver;
    String browserType = "chrome";
    //Truck driver Credentials
    String URL = "https://login2.nextbasecrm.com/";
    String userName = "helpdesk28@cybertekschool.com";
    String password = "UserUser";

    @BeforeMethod
    public void setUp(){
        driver = WebDriverFactory.getDriver(browserType);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get(URL);
        driver.findElement(By.xpath("//input[@name='USER_LOGIN']")).sendKeys(userName);
        driver.findElement(By.xpath("//input[@name='USER_PASSWORD']")).sendKeys(password);
        driver.findElement(By.xpath("//input[@type='submit']")).click();
    }

    @Test
    public void sprint3_story2_AC2(){

        //Navigate to Task tab
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//span[. = 'Task']/span")).click();

        //Selecting Visual editor option
        driver.findElement(By.xpath("(//span[@class = 'feed-add-post-form-editor-btn'])[2]")).click();

        //Verifying text bar is displayed
        WebElement actualResult =driver.findElement(By.xpath("(//div[@class ='bxhtmled-toolbar'])[3]"));
        Assert.assertTrue(actualResult.isDisplayed(),"Text editor bar is NOT displayed. Failed!");
    }




    @AfterMethod
    public void tearDown(){
        driver.quit();
    }
}
