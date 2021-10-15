package tests;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.SearchPage;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BaseTests {

  protected static WebDriver driver;
  protected static SearchPage page;

  @BeforeClass
  public static void setUp() {
    Logger.getLogger("org.openqa.selenium").setLevel(Level.SEVERE);

    WebDriverManager.chromedriver().setup();
    Properties props = System.getProperties();
    try {
      props.load(
              new FileInputStream(new File("resources/test.properties")));
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(-1);
    }

    driver = new ChromeDriver();
    driver.get(System.getProperty("app.url"));
    page = new SearchPage(driver);
  }


  @AfterClass
  public static void tearDown() {
    driver.quit();
  }
}
