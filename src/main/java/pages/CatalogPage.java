package pages;

import annotations.Path;
import dto.CourseInfo;

import java.time.format.DateTimeFormatterBuilder;
import java.util.NoSuchElementException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import waiters.Waiter;

@Path("/catalog/courses")
public class CatalogPage extends AbsBasePage {

   public CatalogPage(WebDriver driver) {
      super(driver);
   }

    @FindBy(xpath = "//div[contains(@class,'sc-hrqzy3-1') and contains(@class,'jEGzDf') and not(contains(text(),'месяц'))]")
    private List<WebElement> courseTitles;

    @FindBy(xpath = "//div[contains(@class,'sc-hrqzy3-1') and contains(@class,'jEGzDf') and contains(text(),'месяц')]")
    private List<WebElement> courseDateBlocks;

    @FindBy(xpath = "//p[normalize-space(text())='Направление']" +
            "/ancestor::div[contains(@class,'sc-1w8jhjp-1')]" +
            "/following-sibling::div" +
            "//div[contains(@class,'sc-1fry39v-0') and @value='true']//label")
    private WebElement activeCategory;


   public void clickCourseByName(String courseName) {
      By courseLocator = By.xpath(
          "//div[contains(@class,'sc-hrqzy3-1') and contains(@class,'jEGzDf') and normalize-space(text())='" + courseName + "']"
      );

      WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

      try {
         WebElement course = wait.until(ExpectedConditions.presenceOfElementLocated(courseLocator));
         scrollAndHighlight(course);
         wait.until(ExpectedConditions.elementToBeClickable(course)).click();
         System.out.println("✅ Курс открыт: " + courseName);
      } catch (StaleElementReferenceException e) {
         System.out.println("♻️ DOM обновился, ищем курс заново...");
         WebElement course = wait.until(ExpectedConditions.presenceOfElementLocated(courseLocator));
         scrollAndHighlight(course);
         wait.until(ExpectedConditions.elementToBeClickable(course)).click();
      } catch (TimeoutException e) {
         throw new NoSuchElementException("⚠️ Курс не найден или не кликабелен: " + courseName);
      }
   }




   public String getCourseTitle() {
      WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
      WebElement title = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("h1")));
      return title.getText().trim();
   }

    /** 📅 Գտնում է ամենավաղ և ամենաուշ դասընթացները */
    public Map<String, List<String>> getEarliestAndLatestCourseNames(List<CourseInfo> courses) {
        if (courses == null || courses.isEmpty()) {
            throw new IllegalArgumentException("❌ Список курсов пустой");
        }

        // 📆 Գտնում ենք ամենավաղ և ամենաուշ ամսաթվերը
        LocalDate earliestDate = courses.stream()
                .map(CourseInfo::getDate)
                .min(LocalDate::compareTo)
                .orElseThrow();

        LocalDate latestDate = courses.stream()
                .map(CourseInfo::getDate)
                .max(LocalDate::compareTo)
                .orElseThrow();

        // 🟢 Վերցնում ենք բոլոր դասընթացները, որոնք ունեն նույն ամսաթվերը
        List<String> earliestCourses = courses.stream()
                .filter(c -> c.getDate().isEqual(earliestDate))
                .map(CourseInfo::getName)
                .toList();

        List<String> latestCourses = courses.stream()
                .filter(c -> c.getDate().isEqual(latestDate))
                .map(CourseInfo::getName)
                .toList();

        // 📦 Արդյունքը Map-ով
        Map<String, List<String>> result = new LinkedHashMap<>();
        result.put("earliest", earliestCourses);
        result.put("latest", latestCourses);

        // 🖨️ Տպում ենք արդյունքները
        System.out.println("📅 Earliest date: " + earliestDate + " → " + earliestCourses);
        System.out.println("🕓 Latest date: " + latestDate + " → " + latestCourses);

        return result;
    }


    private static final DateTimeFormatter RUS_DATE_FORMATTER = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .appendPattern("d MMMM yyyy")
            .toFormatter(new Locale("ru"));

    private LocalDate parseDate(String text) {
        if (text == null || text.isBlank()) return null;

        try {
            // Օր. "27 октября, 2025 · 5 месяцев" → "27 октября 2025"
            String clean = text
                    .replaceAll("[·•]", " ")
                    .replaceAll("месяц(ев|а)?", "")
                    .replaceAll(",", "")
                    .trim();

            Matcher matcher = Pattern.compile("\\d{1,2}\\s+\\p{IsCyrillic}+\\s+\\d{4}").matcher(clean);
            if (matcher.find()) {
                String datePart = matcher.group().trim();
                LocalDate parsed = LocalDate.parse(datePart, RUS_DATE_FORMATTER);
                System.out.println("✅ Parsed successfully: " + text + " → " + parsed);
                return parsed;
            } else {
                System.out.println("⚠️ Pattern not found in: " + text);
            }

        } catch (Exception e) {
            System.out.println("❌ Ошибка парсинга даты: " + text + " → " + e.getMessage());
        }
        return null;
    }



    /** 📦 Վերադարձնում է դասընթացների անուններն ու ամսաթվերը */
    public List<CourseInfo> getAllCourses() {
        List<CourseInfo> courses = new ArrayList<>();

        // Գտնում ենք բոլոր դասընթացների քարտերը
        List<WebElement> courseCards = driver.findElements(By.cssSelector("a.sc-zzdkm7-0"));

        for (WebElement card : courseCards) {
            try {
                // Դասընթացի անունը՝ առաջին jEGzDf
                WebElement titleElement = card.findElement(By.cssSelector(".sc-hrqzy3-1.jEGzDf"));
                String name = titleElement.getText().trim();

                // Ամսաթիվը՝ այն div-ը, որը պարունակում է “месяц”
                WebElement dateElement = card.findElement(By.xpath(".//div[contains(@class,'jEGzDf') and contains(text(),'месяц')]"));
                String dateText = dateElement.getText().trim();

                LocalDate parsedDate = parseDate(dateText);
                courses.add(new CourseInfo(name, parsedDate, titleElement));

                System.out.printf("✅ Parsed successfully: %s → %s%n", dateText, parsedDate);

            } catch (Exception e) {
                System.out.println("⚠️ Пропущен элемент: " + e.getMessage());
            }
        }

        System.out.println("📘 Найдено курсов: " + courses.size());
        return courses;
    }




    public String getCategoryText(){
       String text = activeCategory.getText();
       return text;
   }




   // ---------------- Օգտակար մեթոդներ ----------------

   private WebElement waitUntilClickable(WebElement element) {
      return new WebDriverWait(driver, Duration.ofSeconds(5))
          .until(ExpectedConditions.elementToBeClickable(element));
   }

   private void scrollAndHighlight(WebElement element) {
      ((JavascriptExecutor) driver).executeScript(
          "arguments[0].scrollIntoView({block:'center'}); " +
              "arguments[0].style.border='3px solid red'; " +
              "arguments[0].style.transition='0.3s';", element);
   }



   /** 📦 Բեռնում է բոլոր դասընթացները և վերադարձնում է դրանց CourseInfo-ների ցուցակը */
   public List<CourseInfo> loadAllCourses() {

      // 📋 երբ ամեն ինչ բեռնված է, վերցնում ենք բոլոր կուրսերը
      List<CourseInfo> allCourses = getAllCourses();
      System.out.println("📘 Ընդհանուր բեռնված կուրսեր՝ " + allCourses.size());
      return allCourses;
   }
}
