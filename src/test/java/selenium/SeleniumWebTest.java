package selenium;


import org.bonn.se.model.dao.UserDAO;
import org.bonn.se.services.db.exception.DatabaseException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.sql.SQLException;

public class SeleniumWebTest {

    private static WebDriver driver = null;
    String email = "seleniumtest@email.de";

    @BeforeClass
    public static void setUpClass(){
        //Den Pfad müsst ihr anpassen auf Driver den ihr hier runterladen könnt https://github.com/mozilla/geckodriver/releases
        System.setProperty("webdriver.gecko.driver","/Users/tobiasfellechner/Downloads/geckodriver");
        driver = new FirefoxDriver();
    }

    @Test
    public void testSearchInLacolsco() throws InterruptedException, DatabaseException {
        driver.get("http://localhost:8080/");

        driver.manage().window().maximize();

        driver.findElement(By.cssSelector("html>body>div>div>div:nth-of-type(2)>div:nth-of-type(3)>div>table>tbody>tr:nth-of-type(2)>td:nth-of-type(3)>input")).sendKeys("vorname");
        driver.findElement(By.cssSelector("html>body>div>div>div:nth-of-type(2)>div:nth-of-type(3)>div>table>tbody>tr:nth-of-type(3)>td:nth-of-type(3)>input")).sendKeys("nachname");
        driver.findElement(By.cssSelector("html>body>div>div>div:nth-of-type(2)>div:nth-of-type(3)>div>table>tbody>tr:nth-of-type(4)>td:nth-of-type(3)>input")).sendKeys(email);
        driver.findElement(By.cssSelector("html>body>div>div>div:nth-of-type(2)>div:nth-of-type(3)>div>table>tbody>tr:nth-of-type(5)>td:nth-of-type(3)>input")).sendKeys("11111111");

        Thread.sleep(1000);

        driver.findElement(By.cssSelector("html>body>div>div>div:nth-of-type(2)>div:nth-of-type(3)>div>table>tbody>tr:nth-of-type(6)>td:nth-of-type(3)>div>span>span")).click();


        try {
            UserDAO.deleteUser(email);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        driver.close();






    }
}
