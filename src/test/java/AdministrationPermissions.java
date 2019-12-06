import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void updateDatabase(String menu, String user) throws SQLException {

        try {
             int numberRow = 1;
             String numberXpath = database.GetStringValueFromDatabase("xpath", "xpath", "name", menu);
             System.out.println(numberXpath);
             String numberUser = database.GetStringValueFromDatabase("xpath", "xpath", "name", user);
             System.out.println(numberUser);

                do {
                    String submenu = methods.WebElementFindAndGetTextWithXpath("/html/body/div[2]/div[2]/table[" + numberXpath + "]/tbody/tr[" + numberRow + "]/td[" + numberUser + "]");
                    String check = methods.WebElementFindAndGetTextWithXpath("/html/body/div[2]/div[2]/table[" + numberXpath + "]/tbody/tr[" + numberRow + "]/td[" + numberUser + "]/span");

                    database.InsertStringValueFromDatabase("administrationpermission", "menu", menu, "podmenu", submenu, "user", user, "checkoruncheck", check);
                    numberRow++;
                }
                while(true);

        } catch (Exception e) {
            statements.TestFailed("Update Database");
            statements.AddText("Error", String.valueOf(e), "RED");
        }
    }

    // Wait until you find the WebElement and get text:
    String WebElementFindAndGetTextWithXpath(String xpath) throws SQLException {
        WebElement element = driver.findElement(By.xpath(xpath));
        return element.getText();
    }

    public void updateDatabaseAll(String table) throws SQLException {

        String part = "";
        if(table.equals("Administration-Permissions"))
        {
            part = "Administration-Permissions-Part";
        }

        List<String> menu = database.GetStringTableValueFromDatabase("name", "xpath", "menu", "Administration-Permissions");
        List<String> user = database.GetStringTableValueFromDatabase("name", "xpath", "menu", "Administration-Permissions-User");

        for(int b = 0; b < user.size(); b++ ) {
            for (int a = 0; a < menu.size(); a++) {
                try {

                    int numberRow = 1;
                    String numberXpath = database.GetStringValueFromDatabase("xpath", "xpath", "name", menu.get(a));
                    System.out.println(numberXpath);
                    String numberUser = database.GetStringValueFromDatabase("xpath", "xpath", "name", user.get(b));

                    do {

                        String partOne = database.GetStringValueFromDatabase("xpath", "xpath", "menu", part, "name", "partOne");
                        String partTwo = database.GetStringValueFromDatabase("xpath", "xpath", "menu", part, "name", "partTwo");
                        String partThree = database.GetStringValueFromDatabase("xpath", "xpath", "menu", part, "name", "partThree");
                        String partFour = database.GetStringValueFromDatabase("xpath", "xpath", "menu", part, "name", "partFour");
                        String partFive = database.GetStringValueFromDatabase("xpath", "xpath", "menu", part, "name", "partFive");

                        String check = WebElementFindAndGetTextWithXpath(partOne + numberXpath + partTwo + numberRow + partFour + numberUser + partFive);
                        String submenu = WebElementFindAndGetTextWithXpath(partOne + numberXpath + partTwo + numberRow + partThree);

                       statements.AddText("Menu", menu.get(a), "BLUE");
                       statements.AddText("Submenu", submenu, "BLUE");
                       statements.AddText("User", user.get(b), "BLUE");
                       statements.AddText("Check", check, "BLUE");
                       System.out.println("---------------------------------------------------");

                        database.InsertStringValueFromDatabase("administrationpermission", "menu", menu.get(a), "podmenu", submenu, "user", user.get(b), "checkoruncheck", check);
                        numberRow++;
                    }
                    while (true);

                } catch (Exception e) {
                    statements.AddText("Error", String.valueOf(e), "RED");
                }
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


}

