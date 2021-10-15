package tests;

import com.applitools.eyes.AccessibilityRegionType;
import com.applitools.eyes.AccessibilityStatus;
import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.TestResults;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.selenium.fluent.Target;
import org.junit.Test;
import org.openqa.selenium.By;
import utils.VisualGridRunnerConfig;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SearchTests extends BaseTests {

    public String appName = "BookStore";
    public Eyes eyes = VisualGridRunnerConfig.getEyes(appName);

    /**
     * Visual test on dashboard page
     */
    @Test
    public void dashboardVisualTest() {
        eyes.open(driver, appName, "dashboardVisualTest", new RectangleSize(800, 600));
        eyes.check(Target.window().fully().withName("My Page"));
        eyes.close();
    }

    /**
     * Search field by title
     */
    @Test
    public void searchByTitle(){
        String title = "agile";
        page.search(title);
        assertTrue(page.isBookVisible(title, "title"));
        assertEquals(1, page.getNumberOfVisibleBooks());
        //Visual Validations through Eyes
        eyes.open(driver, appName, "searchByTitle", new RectangleSize(800, 600));
        eyes.check(Target.window().fully().withName("My Page"));
        eyes.close();
        page.clearSearch();
    }

    /**
     * Search field by author
     */
    @Test
    public void searchByAuthor(){
        String author = "greg";
        page.search(author);
        assertTrue(page.isBookVisible(author, "author"));
        assertEquals(2, page.getNumberOfVisibleBooks());
        //Visual Validations through Eyes
        eyes.open(driver, appName, "searchByAuthor", new RectangleSize(800, 600));
        eyes.check(Target.window().fully().withName("My Page"));
        eyes.close();
        page.clearSearch();
    }

    /**
     * Search field by price
     */
    @Test
    public void searchByPrice(){
        String price = "$29.99";
        page.search(price);
        assertTrue(page.isBookVisible(price, "price"));
        assertEquals(2, page.getNumberOfVisibleBooks());
        //Visual Validations through Eyes
        eyes.open(driver, appName, "searchByPrice", new RectangleSize(800, 600));
        eyes.check(Target.window().fully().withName("My Page"));
        eyes.close();
        page.clearSearch();
    }

    /**
     * AB Test on dashboard page
     */
    @Test
    public void dashboardABTest(){
        eyes.open(driver,appName, "dashboardABTest", new RectangleSize(800, 600));
        eyes.checkWindow();
        eyes.close();
    }

    /**
     * Accessibility Test on dashboard following level AAA & WCAG 2.1
     */
    @Test
    public void accessibilityTest() {
        eyes.open(driver, appName, "accessibilityTest", new RectangleSize(800, 600));
        eyes.check("Accessibility test",
                Target
                        .window()
                        .fully()
                        .withName("My Page")
                        // Ignore contrast issue on searchBar
                        .accessibility(By.id("searchBar"), AccessibilityRegionType.IgnoreContrast));
        TestResults results = eyes.close(false);
        assertEquals(AccessibilityStatus.Passed, results.getAccessibilityStatus().getStatus());
    }
}
