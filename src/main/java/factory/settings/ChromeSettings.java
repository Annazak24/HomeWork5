package factory.settings;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.AbstractDriverOptions;

public class ChromeSettings implements ISettings {

   @Override
   public AbstractDriverOptions settings() {

      ChromeOptions chromeOptions = new ChromeOptions();

      chromeOptions.addArguments("--start-maximized");
      chromeOptions.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
      chromeOptions.setExperimentalOption("useAutomationExtension", false);
      chromeOptions.addArguments("--disable-blink-features=AutomationControlled");
      chromeOptions.addArguments("--no-sandbox");
      chromeOptions.addArguments("--disable-dev-shm-usage");
      chromeOptions.addArguments("--disable-infobars");
      chromeOptions.addArguments("--disable-gpu");
      chromeOptions.addArguments("--remote-allow-origins=*");

      return chromeOptions;
   }
}
