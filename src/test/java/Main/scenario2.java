package Main;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.inject.Inject;
import dto.CourseInfo;
import extensions.UiExtensions;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import pages.CatalogPage;

@ExtendWith(UiExtensions.class)
public class scenario2 {

   @Inject
   private CatalogPage catalogPage;

   @Inject
   private WebDriver driver;

   @Test
   public void findEarliestAndLatestCoursesTest() {

      catalogPage.open();
      List<CourseInfo> allCourses = catalogPage.getAllCourses();

      Map<String, List<String>> earliestAndLatestCourses = catalogPage.getEarliestAndLatestCourseNames(
          allCourses);

      String earliestCourseName = earliestAndLatestCourses.get("earliest").get(0);
      String latestCourseName = earliestAndLatestCourses.get("latest").get(0);

      catalogPage.clickCourseByName(earliestCourseName);
      String title1 = catalogPage.getCourseTitleByJsoup();
      assertTrue(title1.contains(earliestCourseName),
          String.format("Expected'%s', but found'%s'", earliestCourseName, title1));

      driver.navigate().back();

      catalogPage.clickCourseByName(latestCourseName);
      String title2 = catalogPage.getCourseTitleByJsoup();
      assertTrue(title2.contains(latestCourseName),
          String.format("Expected '%s', but found '%s'", latestCourseName, title2));
   }
}