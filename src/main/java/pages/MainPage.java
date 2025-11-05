package pages;

import annotations.Path;
import jakarta.inject.Inject;
import java.util.List;
import java.util.Random;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Path("/")
public class MainPage extends AbsBasePage<MainPage> {

    public MainPage(WebDriver driver) {
        super(driver);
    }

    @Inject
    private CatalogPage catalogPage;

    @FindBy(xpath = "//a[text() = \"Все курсы\"]")
    private WebElement allCourses;

    @FindBy(xpath = "//span[text()=\"Обучение\"]")
    private WebElement training;

    @FindBy(xpath = "//div[contains(@class, 'lhsLfs')]//a")
    private List<WebElement> categories;

    public CatalogPage clickAllCourses() {
        allCourses.click();
        return catalogPage;
    }

    public MainPage hoverTraining() {
        actions.moveToElement(training).perform();
        return this;
    }

    public String clickRandomCategory() {
        if (categories.isEmpty()) {
            throw new IllegalStateException("No categories found");
        }

        int randomIndex = new Random().nextInt(categories.size());
        String categoriesName = categories.get(randomIndex).getText();
        categories.get(randomIndex).click();

        return categoriesName;
    }
}
