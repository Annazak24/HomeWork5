package Main;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.inject.Inject;
import extensions.UiExtensions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pages.CatalogPage;
import pages.MainPage;

@ExtendWith(UiExtensions.class)
public class scenario3 {

    @Inject
    private CatalogPage catalogPage;

    @Inject
    private MainPage mainPage;

    @Test
    public void findCourseByCategoryTest() throws InterruptedException {
        mainPage.open();
        mainPage.hoverTraining();

        String categoriesName = mainPage.clickRandomCategory().replaceAll("\\s*\\(\\d+\\)", "").trim();
        String actualCategoriesName = catalogPage.getCategoryText();

        assertEquals(categoriesName, actualCategoriesName,
                String.format("Expected '%s', but found '%s'", categoriesName, actualCategoriesName));
    }
}
