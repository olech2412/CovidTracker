package GUI;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static java.time.Duration.ofSeconds;
import static org.junit.jupiter.api.Assertions.*;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;


class HomeViewTest {
    @Test
    public void dataSourceAnchor() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        var driver = new ChromeDriver(options);
        try {
            driver.get("http://localhost:443");
            new WebDriverWait(driver, ofSeconds(30).getSeconds(), ofSeconds(1).getSeconds())
                    .until(titleIs("CovidTracker"));
            var anchor = driver.findElement(By.linkText("Datenquelle"));
            anchor.click();
            new WebDriverWait(driver, ofSeconds(30).getSeconds(), ofSeconds(1).getSeconds());
            assertEquals("https://experience.arcgis.com/experience/478220a4c454480e823b17327b2bf1d4", driver.getCurrentUrl());
        } finally {
            driver.quit();
        }
    }
    @Test
    public void textTest() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        var driver = new ChromeDriver(options);
        try {
            driver.get("http://localhost:443");
            new WebDriverWait(driver, ofSeconds(30).getSeconds(), ofSeconds(1).getSeconds())
                    .until(titleIs("CovidTracker"));

            var header = driver.findElement(By.tagName("h1")); //Header in AppLayout
            assertEquals(header.getText(), "CovidTracker");
            assertTrue(header.isDisplayed());
            new WebDriverWait(driver, ofSeconds(30).getSeconds(), ofSeconds(1).getSeconds());

            var description = driver.findElement(By.tagName("label")); //Description in AppLayout
            assertEquals(description.getText(), "Über das Menü auf der linken Seite können Sie unter dem Reiter \"Daten abrufen\" die gewünschten Daten abfragen");
            assertTrue(description.isDisplayed());
            new WebDriverWait(driver, ofSeconds(30).getSeconds(), ofSeconds(1).getSeconds());

            var homeViewHeader = driver.findElement(By.id("main-view-header")); //Header in HomeView
            assertEquals(homeViewHeader.getText(), "Willkommen bei CovidTracker");
            assertTrue(homeViewHeader.isDisplayed());
            new WebDriverWait(driver, ofSeconds(30).getSeconds(), ofSeconds(1).getSeconds());


        } finally {
            driver.quit();
        }
    }
}
