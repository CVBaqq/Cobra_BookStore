package pages;

import models.Book;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Map;

import static java.lang.String.format;
import static org.openqa.selenium.support.ui.ExpectedConditions.numberOfElementsToBe;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class SearchPage {

  private WebDriver driver;

  private By searchBar = By.id("searchBar");

  private By visibleBooks = By.xpath("//li[not(contains(@class, 'ui-screen-hidden'))]");

  private By hiddenBooks = By.xpath("//li[contains(@class, 'ui-screen-hidden')]");

  private By titleAttribute = By.xpath(".//h2[contains(@id, '_title')]");

  private By authorAttribute = By.xpath(".//p[contains(@id, '_author')]");

  private By priceAttribute = By.xpath(".//p[contains(@id, '_price')]");

  private By imageAttribute = By.xpath(".//img[contains(@id, '_thumb')]");

  private Map<String, By> queryTypeMap = Map.of("title",titleAttribute, "author", authorAttribute, "price", priceAttribute);

  public SearchPage(WebDriver driver){
    this.driver = driver;
  }

  public void search(String text) {
    search(text, true);
  }

  public void search(String text, boolean waitForHidden) {
    clearSearch();

    driver.findElement(searchBar).sendKeys(text);

    if(waitForHidden) {
      WebDriverWait wait = new WebDriverWait(driver, 5);
      wait.until(presenceOfElementLocated(hiddenBooks));
    }
  }

  public void clearSearch() {
    driver.findElement(searchBar).clear();

    WebDriverWait wait = new WebDriverWait(driver, 5);
    wait.until(numberOfElementsToBe(hiddenBooks, 0));
  }

  public int getNumberOfVisibleBooks() {
    return findVisibleBooks().size();
  }

  public boolean isBookVisible(String query, String queryType) {
    List<WebElement> books = findVisibleBooks();
    return books.stream()
            .map(b -> b.findElement(queryTypeMap.get(queryType)).getText())
            .anyMatch(b -> b.toLowerCase().contains(query.toLowerCase()));
  }
//
//  public boolean isBookVisibleByTitle(String title){
//    List<WebElement> books = findVisibleBooks();
//    return books.stream()
//            .map(b -> b.findElement(titleAttribute).getText())
//            .anyMatch(b -> b.toLowerCase().contains(title.toLowerCase()));
//  }
//
//  public boolean isBookVisibleByAuthor(String author){
//    List<WebElement> books = findVisibleBooks();
//    return books.stream()
//            .map(b -> b.findElement(authorAttribute).getText())
//            .anyMatch(b -> b.toLowerCase().contains(author.toLowerCase()));
//  }
//
//  public boolean isBookVisibleByPrice(String price){
//    List<WebElement> books = findVisibleBooks();
//    return books.stream()
//            .map(b -> b.findElement(priceAttribute).getText())
//            .anyMatch(b -> b.toLowerCase().contains(price.toLowerCase()));
//  }

  public boolean isBookVisibleByTitle(Book book){
    List<WebElement> books = findVisibleBooks();
    boolean correct = true;

    for(WebElement b: books) {
      correct = true;

      if(!compare(book.getTitle(), b, titleAttribute)){
        correct = false;
      }
      if(!compare(book.getAuthor(), b, authorAttribute)){
        correct = false;
      }
      if(!compare(book.getPrice(), b, priceAttribute)){
        correct = false;
      }
      if(!b.findElement(imageAttribute).getAttribute("src")
            .endsWith(book.getImage())){
        correct = false;
      }
    }

    return correct;
  }

  private List<WebElement> findVisibleBooks(){
    return driver.findElements(visibleBooks);
  }

  /**
   *
   * @param expectedValue property of a Book (author, title, etc)
   * @param book WebElement of a specific book
   * @param locator locator for sub element of book to compare its text with property
   * @return
   */
  private boolean compare(String expectedValue, WebElement book, By locator){
    String actualValue = book.findElement(locator).getText();
    System.out.println(format("Comparing %s with %s",
            expectedValue, actualValue));
    return expectedValue.equalsIgnoreCase(actualValue);
  }
}
