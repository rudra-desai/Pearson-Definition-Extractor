import java.io.*;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GetDefinition {

    public static void main(String[] args) throws IOException {
        File file = new File("loginCredentials.txt");
        BufferedReader input = new BufferedReader(new FileReader(file));
        String loginUser = input.readLine();
        String loginPassword = input.readLine();
        System.setProperty("webdriver.chrome.driver", "C:\\ChromeDriver\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        String url = "https://portal.mypearson.com/portal";
        driver.get(url);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebElement email_phone = driver.findElement(By.xpath("/html[@class='ng-scope']/body[@id='appRoot']/div[@id='page-top']/div[@id='e2e-comms-wrapper']/div[@class='auth-layer-wrapper ng-scope']/div[@class='auth-layer ng-scope']/div[@class='pe-template__double']/div[@class='pe-template__double--main']/div[@class='auth-content container ng-scope']/div[@class='auth-form ng-scope']/main[@class='ng-scope'][3]/form[@id='mainForm']/div[@class='pe-form--group'][1]/input[@id='username']"));
        email_phone.sendKeys(loginUser);
        WebElement password = driver.findElement(By.xpath("/html[@class='ng-scope']/body[@id='appRoot']/div[@id='page-top']/div[@id='e2e-comms-wrapper']/div[@class='auth-layer-wrapper ng-scope']/div[@class='auth-layer ng-scope']/div[@class='pe-template__double']/div[@class='pe-template__double--main']/div[@class='auth-content container ng-scope']/div[@class='auth-form ng-scope']/main[@class='ng-scope'][3]/form[@id='mainForm']/div[@class='pe-form--group'][2]/input[@id='password']"));
        password.sendKeys(loginPassword);
    }
}
