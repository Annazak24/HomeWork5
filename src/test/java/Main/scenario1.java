package Main;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.inject.Inject;
import extensions.UiExtensions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pages.CatalogPage;

@ExtendWith(UiExtensions.class)
public class scenario1 {

    @Inject
    private CatalogPage catalogPage;

    @Test
    public void findCourseByNameTest() {
        String courseName = "Vue.js разработчик";

        catalogPage.open();
        catalogPage.clickCourseByName(courseName);

        String title = catalogPage.getCourseTitle();

        assertEquals(courseName, title,
                String.format("Expected '%s', but found '%s'", courseName, title));
    }
}
