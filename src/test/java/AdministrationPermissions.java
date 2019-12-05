import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.SQLException;

public class AdministrationPermissions {

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


    // Wait until you find the WebElement and get text:
    private String WebElementFindAndGetTextWithXpath(String xpath) throws SQLException {
        WebElement element = driver.findElement(By.xpath(xpath));
        Wait(xpath);
        return element.getText();

    }



    public void updateDatabase(String menu, String user) throws SQLException {

        try {

            int a = 1;
            int b = 1;
            int c = 3;
             database.DeleteTableFromDatabase();

             String numberXpath = database.GetStringValueFromDatabase("xpath", "xpath", "name", menu);
             String xpathPartOne = database.GetStringValueFromDatabase("xpath", "xpath", "name", "menuxpathPartOne");
             String xpathPartTwo = database.GetStringValueFromDatabase("xpath", "xpath", "name", "menuxpathPartTwo");
             String xpathMenu = database

                do {

                    String podmenu = WebElementFindAndGetTextWithXpath("/html/body/div[2]/div[2]/table[1]/tbody/tr[" + b + "]/td[1]");
                    System.out.println(podmenu);
                    String check = WebElementFindAndGetTextWithXpath("/html/body/div[2]/div[2]/table[" + a + "]/tbody/tr[" + b + "]/td[" + c + "]/span");
                    System.out.println(check);

                    database.InsertStringValueFromDatabase("administrationpermission", "menu", menu, "podmenu", podmenu, "user", user, "checkoruncheck", check);
                    b++;

                }
                while(true);

        } catch (Exception e) {
            statements.TestFailed("Update Database");
            statements.AddText("Error", String.valueOf(e), "RED");
        }
    }
}

