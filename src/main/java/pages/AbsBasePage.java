package pages;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import annotations.Path;
import annotations.UrlTamplate;
import annotations.Urls;
import commons.AbsCommon;
import exceptions.PathNotFoundException;
import java.util.Arrays;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public abstract class AbsBasePage<T> extends AbsCommon {

   private String baseUrl = System.getProperty("base.url");

   public AbsBasePage(WebDriver driver) {
      super(driver);
   }

   @FindBy(tagName = "h1")
   private WebElement header;

   private String getPathWithData(String name, String... data) {
      Class<T> clazz = (Class<T>) this.getClass();

      UrlTamplate urlTamplate = null;

      if (clazz.isAnnotationPresent(Urls.class)) {
         Urls urls = clazz.getDeclaredAnnotation(Urls.class);
         UrlTamplate[] urlTemplates = urls.urlTemplate();
         urlTamplate = Arrays.stream(urlTemplates)
             .filter((UrlTamplate urlTemplateFilter) -> urlTemplateFilter
                 .name().equals(name)).findFirst().get();
      }

      if (clazz.isAnnotationPresent(UrlTamplate.class)) {
         urlTamplate = clazz.getDeclaredAnnotation(UrlTamplate.class);

         if (urlTamplate != null) {
            String template = urlTamplate.value();

            for (int i = 0; i < data.length; i++) {
               template.replace(String.format("{%d}", i + 1), data[i]);
            }
            return template;
         }
      }
      return "";
   }

   private String getPath() {
      Class<T> clazz = (Class<T>) this.getClass();
      if (clazz.isAnnotationPresent(Path.class)) {
         Path pathObj = clazz.getDeclaredAnnotation(Path.class);
         return pathObj.value();
      }

      throw new PathNotFoundException();
   }

   public T open() {
      driver.get(baseUrl + getPath());
      return (T) this;

   }

   public T open(String name, String... data) {
      driver.get(baseUrl + getPathWithData(name, data));
      return (T) this;
   }
}
