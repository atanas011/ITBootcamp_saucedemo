package saucedemo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class Inventory {

    public static final String URL = "https://www.saucedemo.com/inventory.html";
    private static final String SORT_CLASS = "product_sort_container";

    public static void sortByPrice(WebDriver driver) {
        driver.findElement(By.className(SORT_CLASS)).click();
        Select dropSelect = new Select(driver.findElement(By.className(SORT_CLASS)));
        dropSelect.selectByVisibleText("Price (low to high)");
    }
}
