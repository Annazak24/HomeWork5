package factory;

import exceptions.BrowserNotSupportedException;
import factory.settings.ChromeSettings;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebDriverFactory {

   private final String browser = System.getProperty("browser.name", "chrome");

   public WebDriver create() {
      switch (browser.toLowerCase()) {
         case "chrome":
            WebDriverManager.chromedriver().setup();
            return new ChromeDriver((ChromeOptions) new ChromeSettings().settings());

         default:
            throw new BrowserNotSupportedException("Browser not supported: " + browser);
      }
   }

   public void init() {
      switch (browser.toLowerCase()) {
         case "chrome":
            WebDriverManager.chromedriver().setup();
            break;

         default:
            throw new BrowserNotSupportedException("Browser not supported: " + browser);
      }
   }
}