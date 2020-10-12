package Sprint3.tests;

import Sprint3.util.BrowserUtil;
import Sprint3.util.WebDriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Message_Tab {

    WebDriver driver;
    String browserType = "chrome";
    //Truck driver Credentials
    String URL = "https://login2.nextbasecrm.com/";
    String userName = "helpdesk28@cybertekschool.com";
    String password = "UserUser";
    protected WebDriverWait wait;


    @BeforeMethod
    public void setUp(){
        driver = WebDriverFactory.getDriver(browserType);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get(URL);
        wait = new WebDriverWait(driver,5);
        driver.findElement(By.xpath("//input[@name='USER_LOGIN']")).sendKeys(userName);
        driver.findElement(By.xpath("//input[@name='USER_PASSWORD']")).sendKeys(password);
        driver.findElement(By.xpath("//input[@type='submit']")).click();
    }

    @Test
    public void verify_topic_icon(){
        driver.findElement(By.xpath("//span[.='Message']")).click();
        driver.findElement(By.xpath("//span[@title='Topic']")).click();
        boolean topic_displayed = driver.findElement(By.xpath("//input[@id='POST_TITLE']")).isDisplayed();

        Assert.assertTrue(topic_displayed,"topic box is not displayed");
    }

    @Test
    public void verify_upload_local_file(){
        driver.findElement(By.xpath("//span[.='Message']")).click();
        driver.findElement(By.xpath("//span[@id='bx-b-uploadfile-blogPostForm']")).click();
        driver.findElement(By.xpath("//input[@name='bxu_files[]']")).sendKeys("C:/Users/Aji/Desktop/CRM24/screenshot.PNG");
        By file_uploaded = By.xpath("//span[@class='files-text']");
        boolean file_displayed = wait.until(ExpectedConditions.presenceOfElementLocated(file_uploaded))
                .isDisplayed();
        Assert.assertTrue(file_displayed,"File is not uploaded");
    }

    @Test
    public void verify_upload_Bitrix24(){
        driver.findElement(By.xpath("//span[.='Message']")).click();
        driver.findElement(By.xpath("//span[@id='bx-b-uploadfile-blogPostForm']")).click();

        String main_window = driver.getWindowHandle();
        driver.findElement(By.xpath("//div[@style='display: block;']//span[.='Select document from Bitrix24']")).click();

        String remote_drive = driver.getWindowHandle();

        driver.switchTo().window(remote_drive);

        //recent items
        driver.findElement(By.xpath("//span[.='Recent items']")).click();
        driver.findElement(By.xpath("//a[.='uploadFile (26).txt']")).click();
        driver.findElement(By.xpath("//a[.='uploadFile (25).txt']")).click();
        driver.findElement(By.xpath("//a[.='day55 class notes.txt']")).click();
        driver.findElement(By.xpath("//span[.='Select document']")).click();

        driver.switchTo().window(main_window);
        driver.findElement(By.xpath("//div[@class='diskuf-extended'][@style='display: block;']//" +
                "span[.='Select document from Bitrix24']")).click();

        driver.switchTo().window(remote_drive);
        //company drive
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[.='Sales and marketing']")));
        driver.findElement(By.xpath("//span[.='Sales and marketing']")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[.='Quotes']")));
        driver.findElement(By.xpath("//a[.='Quotes']")).click();
        driver.findElement(By.xpath("//a[.='Quote.docx']")).click();
        driver.findElement(By.xpath("//span[.='Select document']")).click();

        driver.switchTo().window(main_window);

        driver.findElement(By.xpath("//span/span[.='uploadFile (26).txt']")).click();
        driver.findElement(By.xpath("//span/span[.='uploadFile (25).txt']")).click();
        driver.findElement(By.xpath("//span/span[.='day55 class notes.txt']")).click();
        driver.findElement(By.xpath("//span/span[.='Quote.docx']")).click();

        WebElement iframe = driver.findElement(By.xpath("//div[@id='bx-html-editor-iframe-cnt-" +
                "idPostFormLHE_blogPostForm']/iframe"));
        driver.switchTo().frame(iframe);

        String actual = driver.findElement(By.xpath("//body")).getText();
        String expected = "uploadFile (26).txt uploadFile (25).txt day55 class notes.txt Quote.docx ";

        driver.switchTo().defaultContent();
        Assert.assertEquals(actual,expected,"uploaded files do not match");
    }

    @Test
    public void download_external_drive(){
        driver.findElement(By.xpath("//span[.='Message']")).click();
        driver.findElement(By.xpath("//span[@id='bx-b-uploadfile-blogPostForm']")).click();
        driver.findElement(By.xpath("//div[@style='display: block;']//span[.='Download from external drive']")).click();
        String remote_drive = driver.getWindowHandle();
        driver.switchTo().window(remote_drive);
        String actual_msg = driver.findElement(By.xpath("//div[@class='ui-notification-balloon-message']")).getText();
        String expected_msg = "The social networking service Google Docs is not configured. " +
                "Please contact your Bitrix24 administrator.";
        driver.findElement(By.xpath("//span[.='Cancel'][preceding-sibling::span[.='Select document']]")).click();
        Assert.assertEquals(actual_msg,expected_msg,"error msg does not match");
    }

    @Test
    public void create_using_google_docs(){
        driver.findElement(By.xpath("//span[.='Message']")).click();
        driver.findElement(By.xpath("//span[@id='bx-b-uploadfile-blogPostForm']")).click();
        driver.findElement(By.xpath("//div[@style='display: block;']//span[@class='wd-fa-add-file-editor-text']")).click();

        String main_window = driver.getWindowHandle();
        System.out.println(main_window);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[span[.='Google Docs']]"))).click();
        driver.findElement(By.xpath("//div[@style='display: block;']//span[.='Document']")).click();

        Set<String> windows = driver.getWindowHandles();
        for (String each: windows){
            if (!each.contains(main_window)){
                driver.switchTo().window(each);
            }
        }
        WebElement error_msg = driver.findElement(By.xpath("//span[@id='error-text']"));
        String expected_error_msg = "The social networking service Google Docs is not configured. " +
                "Please contact your Bitrix24 administrator.";
        wait.until(ExpectedConditions.textToBePresentInElement(error_msg,expected_error_msg));
        String actual_error_msg = error_msg.getText();
        Assert.assertEquals(actual_error_msg,expected_error_msg,"error msg does not match");
    }

    @AfterMethod
    public void tearDown(){
        driver.quit();
    }
}
