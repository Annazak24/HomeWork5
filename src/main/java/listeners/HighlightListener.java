package listeners;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverListener;

public class HighlightListener implements WebDriverListener {

   private void highlight(WebDriver driver, WebElement element) {
       if (element == null) {
           return;
       }
      try {
         JavascriptExecutor js = (JavascriptExecutor) driver;
         // 🌟 Մնացական կարմիր շրջանակ և փայլ
         js.executeScript(
             "arguments[0].style.transition='all 0.2s ease';" +
                 "arguments[0].style.border='3px solid red';" +
                 "arguments[0].style.boxShadow='0 0 10px red';",
             element
         );
      } catch (Exception e) {
         System.out.println("⚠️ Highlight error: " + e.getMessage());
      }
   }
}
