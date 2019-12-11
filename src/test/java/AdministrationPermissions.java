
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
        element.clear();
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

    // Download values from checkboxes to the selected tab to the database:
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

    // Click the Edit Button:
    public void EditPermissions() throws SQLException {
        WebElementFindAndClick("Administration-Permissions-Edit", "Edytuj", "Button");
    }

    // Download values from checkboxes to the database:
    public void updateDatabaseAll(String table) throws SQLException {

        String part = "";
        String check = "brak";
        String numberCheck = "";

        if(table.equals("Administration-Permissions"))
        {
            part = "Administration-Permissions-Part";
            table = "administrationpermission";
            System.out.println("AP im here");
            numberCheck = "1";
        }

        if(table.equals("Administration-Permissions-Edit")) {
            part = "Administration-Permissions-Part-Edit";
            table = "administrationpermissionedit";
            EditPermissions();
            System.out.println("APE im here");
            numberCheck = "2";
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

                        if(numberCheck.equals("1"))
                        {
                            check = WebElementFindAndGetTextWithXpath(partOne + numberXpath + partTwo + numberRow + partFour + numberUser + partFive);

                        }
                        if(numberCheck.equals("2"))
                        {
                            Boolean isTrueOrFalse = driver.findElement(By.xpath(partOne + numberXpath + partTwo + numberRow + partFour + numberUser + partFive)).isSelected();
                            System.out.println(partOne + numberXpath + partTwo + numberRow + partFour + numberUser + partFive);
                            System.out.println(isTrueOrFalse);
                            if (isTrueOrFalse) {
                                check = "Tak";
                            }

                            if (!isTrueOrFalse) {
                                check = "Nie";
                            }
                        }

                        String submenu = WebElementFindAndGetTextWithXpath(partOne + numberXpath + partTwo + numberRow + partThree);

                       statements.AddText("Menu", menu.get(a), "BLUE");
                       statements.AddText("Submenu", submenu, "BLUE");
                       statements.AddText("User", user.get(b), "BLUE");
                       statements.AddText("Check", check, "BLUE");
                       System.out.println("---------------------------------------------------");

                        database.InsertStringValueFromDatabase(table, "menu", menu.get(a), "submenu", submenu, "user", user.get(b), "checkoruncheck", check);
                        numberRow++;

                    }
                    while (true);

                } catch (Exception e) {
                    statements.AddText("Error", String.valueOf(e), "RED");
                }
            }
        }
    }

    // Check compatibility:
    public void Check() throws SQLException {

        List<String> check1 = database.GetStringTableValueFromDatabase("checkoruncheck", "administrationpermissionedit");
        List<String> check2 = database.GetStringTableValueFromDatabase("checkoruncheck", "administrationpermission");

        int goodScore = 0;
        int wrongScore = 0;

       for(int a = 0; a < check1.size(); a++)
       {
           String odp1 = check1.get(a);
           String odp2 = check2.get(a);

           if(odp1.equals(odp2))
           {
               statements.AddTextNumber("Check", a+1, check1.size(), "GREEN");
               goodScore++;
           }

           else
           {
               statements.AddTextNumber("Check", a+1, check1.size(), "RED");
               wrongScore++;
           }
       }

        statements.AddText("Summary", "Check Administration Permissions", "BLUE");
        statements.AddTextNumber("Good", goodScore, check1.size(), "GREEN");
        statements.AddTextNumber("Wrong", wrongScore, check1.size(), "RED");
    }
}

