package sauce_test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import saucedemo.Home;
import saucedemo.Inventory;

/**
 * Testiranje sajta https://www.saucedemo.com/.
 * Pokusati logovanje prvo sa nevalidnim, a potom sa validnim kredencijalima
 * i proveriti da li se nakon toga korisnik nalazi na odgovarajucoj stranici.
 * Kredencijale procitati iz datoteke data.xlsx.
 * Na stranici https://www.saucedemo.com/inventory.html sortirati proizvode po ceni
 * (od najnize do najvise). Proveriti da li je sortiranje ispravno.
 * Program pisati postujuci page object model.
 */

public class TestSauce {

    private WebDriver driver;

    @BeforeClass
    public void createDriver() {
        driver = new EdgeDriver();
    }

    @Test
    public void testInvalidLogin() {
        File file = new File("sauce_data.xlsx");

        try {
            InputStream in = new FileInputStream(file);
            XSSFWorkbook wb = new XSSFWorkbook(in);

            Sheet sheet = wb.getSheetAt(0);
            Row row = sheet.getRow(1);
            Cell c0 = row.getCell(0);
            Cell c1 = row.getCell(1);

            String username = c0.toString();
            String password = c1.toString();

            driver.navigate().to(Home.URL);
            Home.enterUsername(driver, username);
            Home.enterPassword(driver, password);
            Home.clickLoginBtn(driver);

            String currentUrl = driver.getCurrentUrl();
            String expectedUrl = Home.URL;
            Assert.assertEquals(currentUrl, expectedUrl);
            wb.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testValidLogin() {
        File file = new File("sauce_data.xlsx");

        try {
            InputStream in = new FileInputStream(file);
            XSSFWorkbook wb = new XSSFWorkbook(in);
            Sheet sheet = wb.getSheetAt(0);
            Row row = sheet.getRow(0);
            Cell c0 = row.getCell(0);
            Cell c1 = row.getCell(1);

            String username = c0.toString();
            String password = c1.toString();

            driver.navigate().to(Home.URL);
            Home.enterUsername(driver, username);
            Home.enterPassword(driver, password);
            Home.clickLoginBtn(driver);

            String currentUrl = driver.getCurrentUrl();
            String expectedUrl = Inventory.URL;
            Assert.assertEquals(currentUrl, expectedUrl);
            wb.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test(priority = 1) // !
    public void testSortByPrice() {
        driver.navigate().to(Inventory.URL);
        Inventory.sortByPrice(driver);

        Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies
                .viewportPasting(1000)).takeScreenshot(driver);
        try {
            ImageIO.write(screenshot.getImage(), "jpg",
                    new File("./sauce_test.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public void quitDriver() {
        driver.quit();
    }
}
