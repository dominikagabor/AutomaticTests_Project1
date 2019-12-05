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

    public void updateDatabase() throws SQLException {

        try {

          //  String yy = "Administratorzy";
         //   String xx = "Pracownicy";

          //  int employeeNumber = 1;


            int a = 1;
            int b = 1;
            int c = 3;

            String menu = WebElementFindAndGetTextWithXpath("/html/body/div[2]/div[2]/table[" + a + "]/thead/tr/th[1]");
            System.out.println(menu);

            String user = WebElementFindAndGetTextWithXpath("/html/body/div[2]/div[2]/table[" + a + "]/thead/tr/th[3]");
            System.out.println(user);


                do {

                    String podmenu = WebElementFindAndGetTextWithXpath("/html/body/div[2]/div[2]/table[1]/tbody/tr[" + b + "]/td[1]");
                    System.out.println(podmenu);

                    String check = WebElementFindAndGetTextWithXpath("/html/body/div[2]/div[2]/table[" + a + "]/tbody/tr[" + b + "]/td[" + c + "]/span");
                    System.out.println(check);

                    
               //     database.InsertStringValueFromDatabase("administrationpermission", "menu", menu, "podmenu", podmenu, "user", user, "checkoruncheck", check);
                    b++;

                } while (true);



            //

        } catch (Exception e) {
            statements.TestFailed("Update Database");
            statements.AddText("Error", String.valueOf(e), "RED");
        }
    }
}




        // Pracownicy     /html/body/div[2]/form/div[2]/table[1]/thead/tr/th[1]
        // Edycja umów
// Widok             /html/body/div[2]/form/div[2]/table[1]/tbody/tr[2]/td[1]

        // Klienci         /html/body/div[2]/form/div[2]/table[2]/thead/tr/th[1]
    // Edycja planow pr  /html/body/div[2]/form/div[2]/table[2]/tbody/tr[1]/td[1]
//widok planu przy    /html/body/div[2]/form/div[2]/table[2]/tbody/tr[2]/td[1]


