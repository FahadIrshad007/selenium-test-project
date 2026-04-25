package com.example;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.util.concurrent.TimeUnit;

public class LoginTest {

    @Test
    void test_login_with_incorrect_credentials() {
        // Note: For Windows local testing, you might need:
        // System.setProperty("webdriver.chrome.driver", "C:\\ChromeDriver\\chromedriver.exe");
        
        // For markhobson/maven-chrome (Linux Docker), ChromeDriver is usually in /usr/bin/chromedriver
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            System.setProperty("webdriver.chrome.driver", "C:\\ChromeDriver\\chromedriver.exe");
        } else {
            System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
        }

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");               // Run in headless mode
        options.addArguments("--no-sandbox");             // Required in some Docker environments
        options.addArguments("--disable-dev-shm-usage");  // Required in some Docker environments

        WebDriver driver = new ChromeDriver(options);
        
        try {
            driver.navigate().to("http://103.139.122.250:4000/"); 
            
            // Wait for elements if necessary
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

            driver.findElement(By.name("email")).sendKeys("qasim@malik.com");
            driver.findElement(By.name("password")).sendKeys("abcdefg");
            
            // The provided snippet used id="m_login_signin_submit"
            driver.findElement(By.id("m_login_signin_submit")).click();

            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            
            // XPath provided in the prompt
            String errorText = driver.findElement(By.xpath("/html/body/div/div/div[1]/div/div/div/div[2]/form/div[1]")).getText();
            
            assert(errorText.contains("Incorrect email or password"));
            System.out.println("Test Passed: Error message found as expected.");
            
        } finally {
            driver.quit();
        }
    }
}
