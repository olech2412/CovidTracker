package GUI;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static java.time.Duration.ofSeconds;
import static org.junit.jupiter.api.Assertions.*;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;


class HomeViewTest {
    @Test
    public void sourceAnchor() {
        WebDriverManager.chromedriver().setup();
        var driver = new ChromeDriver();
        try {
            driver.get("http://localhost:443");
            new WebDriverWait(driver, ofSeconds(30).getSeconds(), ofSeconds(1).getSeconds())
                    .until(titleIs("Home"));
            var anchor = driver.findElement(By.linkText("Datenquelle"));
            anchor.click();
            new WebDriverWait(driver, ofSeconds(30).getSeconds(), ofSeconds(1).getSeconds());
            assertEquals("https://experience.arcgis.com/experience/478220a4c454480e823b17327b2bf1d4", driver.getCurrentUrl());
            new WebDriverWait(driver, ofSeconds(30).getSeconds());
        } finally {
            driver.quit();
        }
    }
}
