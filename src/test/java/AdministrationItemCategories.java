import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.SQLException;

public class AdministrationItemCategories {

    private WebDriver driver;
    private Database database = new Database();
    private Statements statements = new Statements();
    private Methods methods = new Methods();

    // Transfer driver to another class:
    void TransferDriver(WebDriver transferDriver) {
        driver = transferDriver;
    }

    // ChromeDriver:
    private void ChromeDriver(WebDriver driver) {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        statements.TransferDriver(driver);
    }

    // Wait for a specific item:
    private void Wait(String xpath) {
        WebDriverWait wait = new WebDriverWait(driver, 10000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
    }

    // Close the Website:
    private void Close() {
        driver.close();
        driver.quit();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Find WebElement:
    WebElement WebElementFind(String menu, String name, String type) throws SQLException {
        String xpath = database.GetStringValueFromDatabase("xpath", "xpath", "menu", menu, "name", name, "type", type);
        return driver.findElement(By.xpath(xpath));
    }

    // Wait until you find the WebElement:
    WebElement WebElementFindAndWait(String menu, String name, String type) throws SQLException {
        String xpath = database.GetStringValueFromDatabase("xpath", "xpath", "menu", menu, "name", name, "type", type);
        Wait(xpath);
        return driver.findElement(By.xpath(xpath));
    }

    // Wait until you find the WebElement with xpath:
    WebElement WebElementFindWithXpath(String xpath) {
        WebElement element = driver.findElement(By.xpath(xpath));
        Wait(xpath);
        return element;
    }

    // Wait until you find the WebElement and click WebElement:
    WebElement WebElementFindAndClick(String menu, String name, String type) throws SQLException {
        WebElement element = WebElementFindAndWait(menu, name, type);
        element.click();
        return element;
    }

    // Wait until you find the WebElement and click WebElement with xpath:
    WebElement WebElementFindAndClickWithXpath(String xpath) {
        WebElement element = driver.findElement(By.xpath(xpath));
        Wait(xpath);
        element.click();
        return element;
    }

    // Wait until you find the WebElement, click WebElement and send keys:
    void WebElementFindAndClickAndSendKeys(String menu, String name, String type, String sendKeys) throws SQLException {
        WebElement element = WebElementFindAndClick(menu, name, type);
        element.sendKeys(sendKeys);
    }

    // Wait until you find the WebElement, click WebElement and send keys with xpath:
    void WebElementFindAndClickAndSendKeysWithXpath(String xpath, String sendKeys) throws SQLException {
        WebElement element = driver.findElement(By.xpath(xpath));
        element.clear();
        element.sendKeys(sendKeys);
    }

    // Wait until you find the WebElement and get text:
    String WebElementFindAndGetText(String menu, String name, String type) throws SQLException {
        WebElement element = WebElementFindAndWait(menu, name, type);
        return element.getText();
    }

    // Wait until you find the WebElement and get text with xpath:
    String WebElementFindAndGetTextWithXpath(String xpath) throws SQLException {
        WebElement element = driver.findElement(By.xpath(xpath));
        return element.getText();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public void Add(String name, String shortcut, String color) throws SQLException {
      try {
          WebElementFindAndClick("Administration-ItemCategories", "Dodaj", "Button");
          statements.TestPassed("AdministrationItemCategories - Add");

          WebElementFindAndClickAndSendKeys("Administration-ItemCategories-Add", "Nazwa", "EditText", name);
          statements.TestPassed("AdministrationItemCategoriesAdd - Nazwa");
          WebElementFindAndClickAndSendKeys("Administration-ItemCategories-Add", "Skrót", "EditText", shortcut);
          statements.TestPassed("AdministrationItemCategoriesAdd - Skrót");

          //todo - color

          WebElementFindAndClick("Administration-ItemCategories-Add", "Dodaj", "Button");

      }
      catch(Exception e)
      {
          statements.AddText("Error", String.valueOf(e), "RED");
      }
    }

    public void Check() throws SQLException {
       try
       {
           for(int a = 1; a <= 100; a++)
           {
               String partOne = database.GetStringValueFromDatabase("xpath", "xpath", "menu", "Administration-ItemCategories", "name", "partOne");
               String partTwo = database.GetStringValueFromDatabase("xpath", "xpath", "menu", "Administration-ItemCategories", "name", "partTwo");
               String partThree = database.GetStringValueFromDatabase("xpath", "xpath", "menu", "Administration-ItemCategories", "name", "partThree");
               //String nameXpath = partOne + a + partTwo + b + partThree;
            //   String name = WebElementFindAndGetTextWithXpath(nameXpath);
            //   database.InsertStringValueFromDatabase("administrationitemcategories", "nazwa", name);

           }
       }
       catch(Exception e)
       {
           statements.AddText("Error", String.valueOf(e), "RED");
       }
    }
}
