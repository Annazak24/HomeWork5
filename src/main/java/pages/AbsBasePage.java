package pages;

import annotations.Path;
import commons.AbsCommon;
import exceptions.PathNotFoundException;
import org.openqa.selenium.WebDriver;

public abstract class AbsBasePage<T> extends AbsCommon {

   private String baseUrl = System.getProperty("base.url");

   public AbsBasePage(WebDriver driver) {
      super(driver);
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
}
