package saucedemo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Home {

    public static final String URL = "https://www.saucedemo.com/";

    public static void enterUsername(WebDriver driver, String str) {
        driver.findElement(By.id("user-name")).sendKeys(str);
    }

    public static void enterPassword(WebDriver driver, String str) {
        driver.findElement(By.id("password")).sendKeys(str);
    }

    public static void clickLoginBtn(WebDriver driver) {
        driver.findElement(By.id("login-button")).click();
    }
}
