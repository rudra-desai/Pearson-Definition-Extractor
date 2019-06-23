import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class GetDefinition {

    public static void main(String[] args) throws IOException, InterruptedException {
        File file = new File("loginCredentials.txt");
        BufferedReader input = new BufferedReader(new FileReader(file));
        String loginUser = input.readLine();
        String loginPassword = input.readLine();
        System.setProperty("webdriver.gecko.driver", "C:\\ChromeDriver\\geckodriver.exe");
        WebDriver driver = new FirefoxDriver();
        driver.manage().window().maximize();

        //login
        String url = "https://portal.mypearson.com/portal";
        driver.get(url);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebElement email_phone = driver.findElement(By.xpath("/html[@class='ng-scope']/body[@id='appRoot']/div[@id='page-top']/div[@id='e2e-comms-wrapper']/div[@class='auth-layer-wrapper ng-scope']/div[@class='auth-layer ng-scope']/div[@class='pe-template__double']/div[@class='pe-template__double--main']/div[@class='auth-content container ng-scope']/div[@class='auth-form ng-scope']/main[@class='ng-scope'][3]/form[@id='mainForm']/div[@class='pe-form--group'][1]/input[@id='username']"));
        email_phone.sendKeys(loginUser);
        WebElement password = driver.findElement(By.xpath("/html[@class='ng-scope']/body[@id='appRoot']/div[@id='page-top']/div[@id='e2e-comms-wrapper']/div[@class='auth-layer-wrapper ng-scope']/div[@class='auth-layer ng-scope']/div[@class='pe-template__double']/div[@class='pe-template__double--main']/div[@class='auth-content container ng-scope']/div[@class='auth-form ng-scope']/main[@class='ng-scope'][3]/form[@id='mainForm']/div[@class='pe-form--group'][2]/input[@id='password']"));
        password.sendKeys(loginPassword);
        driver.findElement(By.xpath("/html[@class='ng-scope']/body[@id='appRoot']/div[@id='page-top']/div[@id='e2e-comms-wrapper']/div[@class='auth-layer-wrapper ng-scope']/div[@class='auth-layer ng-scope']/div[@class='pe-template__double']/div[@class='pe-template__double--main']/div[@class='auth-content container ng-scope']/div[@class='auth-form ng-scope']/main[@class='ng-scope'][3]/form[@id='mainForm']/div[@class='pe-form--group'][4]/button[@id='mainButton']")).click();

        //click class
        driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
        driver.findElement(By.xpath("/html/body[@class='crs-home']/main/ng-view/div/div/div[@class='fluid-container']/div[@id='tab-outer-container']/div[@class='container-align']/course-card-container/div[@class='container-fluid']/div[@class='row'][3]/div[@class='col-xs-12']/div[@class='gridview']/div[@class='card-groups enable']/div[@class='card-group']/div[@class='card-group-body']/ul[@class='cards ng-pristine ng-untouched ng-valid ui-sortable ng-not-empty']/li[@class='draggable'][1]/card-view/ng-include/div[@class='li-placeholder']/div[@class='tile']/div[@class='card-container']/div[2]/div[@class='card-header']/div[@class='title-wrapper pointer']")).click();

        Thread.sleep(5000);
        //click chapter resources
        driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
        driver.findElement(By.cssSelector("div.container-fluid.ng-scope:nth-child(1) div.row.content:nth-child(3) div.ov-left-nav.col-lg-2.col-sm-3.col-md-3.inner ul:nth-child(2) li.item.ng-isolate-scope:nth-child(14) div.menu-item.hover span:nth-child(3) > a.ng-binding")).click();

        //click chapter resources again
        driver.manage().timeouts().implicitlyWait(300, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//*[@id=\"ov-menu-item-list\"]/li[2]")).click();

        driver.switchTo().frame("centerIframe");

        //click Glossary flash cards
        driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
        driver.findElement(By.linkText("Glossary Flash Cards")).click();

        //click interactive flash cards
        driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
        driver.findElement(By.linkText("Interactive Flash Cards")).click();


        //switch to new window
        for (String handle : driver.getWindowHandles())
        {
            driver.switchTo().window(handle);
        }

        driver.manage().window().maximize();

        //click let's study
        Thread.sleep(5000);
        driver.findElement(By.xpath("//*[@id=\"gotolessonpage\"]")).click();

        //Click all chapters
        //driver.findElement(By.xpath("//strong[contains(text(),'Select all')]")).click();

        int chapterCompleteFlag = 0;
        String chapterCode = "";
        for(int numChapter = 17; numChapter <= 19; numChapter++) {
            //click chapter and get terms
            Thread.sleep(5000);
            String checker = "//*[@id=\"topiclistnew\"]/div/div[1]/div[" + numChapter +"]/a";
            driver.findElement(By.xpath(checker)).click();
            //String terms =  driver.findElement(By.xpath("//*[@id=\"lessoncount_" + numChapter + "\"]")).getText();


            //System.out.println(terms);

            //click study now
            Thread.sleep(500);
            driver.findElement(By.xpath("//*[@id=\"topicstudyspecific\"]")).click();

            //click select all
            Thread.sleep(500);


            //Click all terms
            try {
                driver.findElement(By.xpath("//div[contains(@class,'gselect darkbg')]//a[contains(@class,'checkall')]")).click();
            }catch(Exception e)
            {
                System.out.println("Catch");
                driver.findElement(By.xpath("/html/body/article[5]/div/div/a")).click();
            }

            //click study now
            Thread.sleep(500);
            driver.findElement(By.xpath("//*[@id=\"glossstudy\"]")).click();

            FileWriter out = new FileWriter("ECON Chapter " + numChapter +" definitions.txt");
            PrintWriter print = new PrintWriter(out);

            while(chapterCompleteFlag == 0) {
                List<WebElement> termPulled = driver.findElements(By.tagName("h5"));
                Iterator<WebElement> iter = termPulled.iterator();
                List<WebElement> definitionOfTerm = driver.findElements(By.xpath("//li[starts-with(@id, 'glossdetail')]"));

                int i = 0;
                while (iter.hasNext()) {

                    Thread.sleep(1000);
                    WebElement item = iter.next();
                    Thread.sleep(1000);
                    String label = item.getText();
                    String definition = "";
                    definitionOfTerm = driver.findElements(By.xpath("//li[starts-with(@id, 'glossdetail')]"));
                    definition = definitionOfTerm.get(i).getAttribute("textContent");
                    int lastPeriod = definition.lastIndexOf(".");
                    print.println(label.charAt(0) + label.substring(1).toLowerCase() + ": " + definition.substring(0, lastPeriod + 1).trim().replaceAll(" +", " "));
                    driver.findElement(By.linkText("Correct")).click();
                    i++;

                    String temp = driver.findElement(By.xpath("//*[@id=\"page5\"]")).getAttribute("textContent");
                    if (temp.contains("FromLearnedSkippedCorrectChapter") && !temp.equals(chapterCode))
                    {
                        chapterCode = temp;
                        chapterCompleteFlag = 1;
                        print.close();
                        break;
                    }
                }
                if(chapterCompleteFlag == 1) {
                    Thread.sleep(1000);
                    driver.findElement(By.xpath("//*[@id=\"againlesson\"]")).click();
                    break;
                }
            }
            chapterCompleteFlag = 0;
        }
    }
}
