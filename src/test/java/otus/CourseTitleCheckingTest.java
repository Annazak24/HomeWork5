package otus;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.inject.Inject;
import extensions.UiExtensions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pages.CatalogPage;

@ExtendWith(UiExtensions.class)
public class CourseTitleCheckingTest {

   @Inject
   private CatalogPage catalogPage;

   @Test
   public void findCourseByNameTest() {
      String courseName = "Разработка прикладного ПО на Qt и ОС «Аврора»";

       catalogPage.open();
       catalogPage.clickCourseByName(courseName);

      String title = catalogPage.getCourseTitleByJsoup();

      assertEquals(courseName, title,
          String.format("Expected '%s', but found '%s'", courseName, title));
   }
}
