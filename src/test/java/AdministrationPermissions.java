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
             int numberRow = 1;
             String numberXpath = database.GetStringValueFromDatabase("xpath", "xpath", "name", menu);
             System.out.println(numberXpath);
             String numberUser = database.GetStringValueFromDatabase("xpath", "xpath", "name", user);
             System.out.println(numberUser);

                do {
                    String submenu = WebElementFindAndGetTextWithXpath("/html/body/div[2]/div[2]/table[" + numberXpath + "]/tbody/tr[" + numberRow + "]/td[" + numberUser + "]");
                    String check = WebElementFindAndGetTextWithXpath("/html/body/div[2]/div[2]/table[" + numberXpath + "]/tbody/tr[" + numberRow + "]/td[" + numberUser + "]/span");

                    database.InsertStringValueFromDatabase("administrationpermission", "menu", menu, "podmenu", submenu, "user", user, "checkoruncheck", check);
                    numberRow++;
                }
                while(true);

        } catch (Exception e) {
            statements.TestFailed("Update Database");
            statements.AddText("Error", String.valueOf(e), "RED");
        }
    }

    public void updateDatabaseAll() throws SQLException {

        String[] menu = database.GetStringTableValueFromDatabase("name", "xpath", "menu", "Administration-Permissions");
        String[] user = database.GetStringTableValueFromDatabase("name", "xpath", "menu", "Administration-Permissions-User");
        for(int b = 0; b < user.length; b++ ) {
            for (int a = 0; a < menu.length; a++) {
                System.out.println(menu[a]);


                try {
                    int numberRow = 1;
                    String numberXpath = database.GetStringValueFromDatabase("xpath", "xpath", "name", menu[a]);
                    System.out.println(numberXpath);
                    String numberUser = database.GetStringValueFromDatabase("xpath", "xpath", "name", user[b]);
                    System.out.println(numberUser);

                    do {
                        String submenu = WebElementFindAndGetTextWithXpath("/html/body/div[2]/div[2]/table[" + numberXpath + "]/tbody/tr[" + numberRow + "]/td[" + numberUser + "]");
                        String check = WebElementFindAndGetTextWithXpath("/html/body/div[2]/div[2]/table[" + numberXpath + "]/tbody/tr[" + numberRow + "]/td[" + numberUser + "]/span");

                        database.InsertStringValueFromDatabase("administrationpermission", "menu", menu[a], "podmenu", submenu, "user", user[b], "checkoruncheck", check);
                        numberRow++;
                    }
                    while (true);

                } catch (Exception e) {
                    statements.TestFailed("Update Database");
                    statements.AddText("Error", String.valueOf(e), "RED");
                }

            }
        }
    }
}

