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
        options.addArguments("--headless=new");           // Use the newer headless mode
        options.addArguments("--no-sandbox");             // Required for Docker
        options.addArguments("--disable-dev-shm-usage");  // Prevents memory issues in Docker
        options.addArguments("--remote-allow-origins=*"); // FIXES: ConnectionFailedException
        options.addArguments("--disable-gpu");            // Recommended for headless

        WebDriver driver = new ChromeDriver(options);
        
        try {
            System.out.println("Navigating to: http://103.139.122.250:4000/");
            driver.navigate().to("http://103.139.122.250:4000/"); 
            
            // Wait for elements (up to 20 seconds) - sometimes pages take time in Docker
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

            System.out.println("Current URL: " + driver.getCurrentUrl());
            System.out.println("Page Title: " + driver.getTitle());

            // Try to find email field
            driver.findElement(By.name("email")).sendKeys("qasim@malik.com");
            driver.findElement(By.name("password")).sendKeys("abcdefg");
            
            // Login button
            driver.findElement(By.id("m_login_signin_submit")).click();

            // Wait for error message (up to 10 seconds)
            Thread.sleep(3000); // Give it a moment to process the click
            
            String errorText = driver.findElement(By.xpath("/html/body/div/div/div[1]/div/div/div/div[2]/form/div[1]")).getText();
            System.out.println("Found Error Text: " + errorText);
            
            assert(errorText.contains("Incorrect email or password"));
            System.out.println("Test Status: PASSED");
            
        } catch (Exception e) {
            System.out.println("Test Failed with Exception: " + e.getMessage());
            // Print page source to help find the right elements if it fails
            // System.out.println("Page Source Snippet: " + driver.getPageSource().substring(0, Math.min(driver.getPageSource().length(), 1000)));
            throw e; 
        } finally {
            driver.quit();
        }
    }
}
