package dto;

import java.time.LocalDate;
import org.openqa.selenium.WebElement;

public class CourseInfo {

   private final String name;
   private final LocalDate date;

   public CourseInfo(String name, LocalDate date, WebElement element) {
      this.name = name;
      this.date = date;
   }

   public String getName() {
      return name;
   }

   public LocalDate getDate() {
      return date;
   }
}

