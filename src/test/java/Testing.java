import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.sql.*;

public class Testing {

    // Classes:
    private Methods methods = new Methods();
    private Database database = new Database();
    private Statements statements = new Statements();

    // ChromeDriver:
    private WebDriver ChromeDriver() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        methods.TransferDriver(driver);
        statements.TransferDriver(driver);
        return driver;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

   // @Test
    public void Scenario001() throws SQLException {

        // For editing:
        String numberUrl = "1"; // table: agdb.url
        String numberUser = "2"; // table agdb.login
        String nameMainDropdown = "Pracownicy"; // table agdb.maindropdown
        String nameLateralDropdown = "Umowy"; // table agdb.lateraldropdown

        String mainUrl = database.GetStringValueFromDatabase("url", "url", "idUrl", numberUrl);
        String login = database.GetStringValueFromDatabase("login", "login", "idUser", numberUser);
        String password = database.GetStringValueFromDatabase("password", "login", "idUser", numberUser);
        ChromeDriver();
        statements.ScenarioTitle("Scenario - Navigate");
        methods.NavigateToPage(mainUrl);
        System.out.println("---------------------------------------------------");
        methods.Login(login, password);
        System.out.println("---------------------------------------------------");
        methods.NavigateToMainMenu(nameMainDropdown, nameLateralDropdown);
        System.out.println("---------------------------------------------------");
    }

    @Test
    public void Scenario002() throws SQLException {

        // For editing:
        String numberUrl = "1"; // table: agdb.url
        String numberUser = "2"; // table agdb.login

        String mainUrl = database.GetStringValueFromDatabase("url", "url", "idUrl", numberUrl);
        String login = database.GetStringValueFromDatabase("login", "login", "idUser", numberUser);
        String password = database.GetStringValueFromDatabase("password", "login", "idUser", numberUser);
        ChromeDriver();
        statements.ScenarioTitle("Scenario - Navigate");
        methods.NavigateToPage(mainUrl);
        System.out.println("---------------------------------------------------");
        methods.Login(login, password);
        System.out.println("---------------------------------------------------");
        methods.NavigateToAllMainMenu();
        System.out.println("---------------------------------------------------");
    }
}