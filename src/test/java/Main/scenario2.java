package Main;

import com.google.inject.Inject;
import dto.CourseInfo;
import extensions.UiExtensions;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import pages.CatalogPage;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(UiExtensions.class)
public class scenario2 {

   @Inject
   private CatalogPage catalogPage;

   @Inject
   private WebDriver driver;

   @Test
   public void findEarliestAndLatestCoursesTest() {

       catalogPage.open();
       List <CourseInfo> allCourses = catalogPage.loadAllCourses();
      allCourses.forEach(c ->
          System.out.println(c.getName() + " — " + c.getDate())
      );

      Map<String, List<String>> earliestAndLatestCourses = catalogPage.getEarliestAndLatestCourseNames(allCourses);
      System.out.println(earliestAndLatestCourses);

      String earliestCourseName = earliestAndLatestCourses.get("earliest").get(0);
      String latestCourseName = earliestAndLatestCourses.get("latest").get(0);
      System.out.println( "earliest" + earliestCourseName);
      System.out.println( "latest" + latestCourseName);
       catalogPage.clickCourseByName(earliestCourseName);
       String title1 = catalogPage.getCourseTitle();
       assertEquals(earliestCourseName, title1,
               String.format("Սպասվում էր '%s', բայց ստացվել է '%s'", earliestCourseName, title1));


       driver.navigate().back();
       catalogPage.clickCourseByName(latestCourseName);
       String title2 = catalogPage.getCourseTitle();
       assertEquals(latestCourseName, title2,
               String.format("Սպասվում էր '%s', բայց ստացվել է '%s'", earliestCourseName, title2));


//
//      // բացում ենք էջը
//
//
//      // ստանում ենք բոլոր կուրսերը
//      List<CourseInfo> allCourses = catalogPage.getAllCourses();
//      System.out.println("📘 Найдено курсов: " + allCourses.size());
//
//      // գտնում ենք ամենավաղ և ամենաուշ կուրսերը
//      CourseInfo earliest = catalogPage.getEarliestCourse(allCourses);
//      CourseInfo latest = catalogPage.getLatestCourse(allCourses);
//
//      System.out.println("📅 Ամենավաղ դասընթաց → " + earliest.getName() + " — " + earliest.getStartDate());
//      System.out.println("📅 Ամենաուշ դասընթաց → " + latest.getName() + " — " + latest.getStartDate());
//
//      // բացում ենք ամենավաղ դասընթացը
//      catalogPage.openCourse(earliest);
//      catalogPage.verifyCoursePage(earliest.getName());
//
//      // վերադառնում ենք նախորդ էջը
//
//
//      // ✅ օգտագործում ենք Waiter՝ սպասելու մինչև էջը բեռնվի
//      Waiter waiter = new Waiter(driver);
//      waiter.waitForCondition(driver1 ->
//          !driver.findElements(
//                  By.xpath("//div[contains(@class,'sc-hrqzy3-1') and contains(@class,'jEGzDf')]"))
//              .isEmpty()
//      );
//
//      // նորից ստանում ենք կուրսերի ցանկը, որովհետև նախորդ WebElement-ները "stale" են
//      List<CourseInfo> refreshedCourses = catalogPage.getAllCourses();
//      CourseInfo latestRef = catalogPage.getLatestCourse(refreshedCourses);
//
//      // բացում ենք ամենաուշ դասընթացը
//      catalogPage.openCourse(latestRef);
//      catalogPage.verifyCoursePage(latestRef.getName());
//   }
   }}