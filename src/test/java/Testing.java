import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.sql.*;

public class Testing {

    // Classes:
    private Methods methods = new Methods();
    private Database database = new Database();
    private Statements statements = new Statements();
    private AdministrationPermissions administrationPermissions = new AdministrationPermissions();
    private AdministrationItemCategories administrationItemCategories = new AdministrationItemCategories();

    // ChromeDriver:
    private WebDriver ChromeDriver() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        methods.TransferDriver(driver);
        statements.TransferDriver(driver);
        administrationPermissions.TransferDriver(driver);
        administrationItemCategories.TransferDriver(driver);
        return driver;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //@Test
    public void Scenario001_Login() throws SQLException {

        // For editing:
        String numberUrl = "1"; // table: url
        String numberUser = "2"; // table login

        String mainUrl = database.GetStringValueFromDatabase("url", "url", "idUrl", numberUrl);
        String login = database.GetStringValueFromDatabase("login", "login", "idUser", numberUser);
        String password = database.GetStringValueFromDatabase("password", "login", "idUser", numberUser);
        ChromeDriver();
        statements.ScenarioTitle("Scenario001_Login");
        methods.NavigateToPage(mainUrl);
        System.out.println("---------------------------------------------------");
        methods.Login(login, password);
        System.out.println("---------------------------------------------------");
    }

    //@Test
    public void Scenario002_LoginAllUsers() throws SQLException {

        // For editing:
        String numberUrl = "1"; // table: url

        String mainUrl = database.GetStringValueFromDatabase("url", "url", "idUrl", numberUrl);
        ChromeDriver();
        statements.ScenarioTitle("Scenario002_LoginAllUsers");
        methods.NavigateToPage(mainUrl);
        System.out.println("---------------------------------------------------");
        methods.LoginAll();
        System.out.println("---------------------------------------------------");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //@Test
    public void Scenario003_Navigate() throws SQLException {

        // For editing:
        String numberUrl = "1"; // table: url
        String numberUser = "2"; // table: login
        String nameMainDropdown = "Pracownicy"; // table: maindropdown
        String nameLateralDropdown = "Umowy"; // table: lateraldropdown

        String mainUrl = database.GetStringValueFromDatabase("url", "url", "idUrl", numberUrl);
        String login = database.GetStringValueFromDatabase("login", "login", "idUser", numberUser);
        String password = database.GetStringValueFromDatabase("password", "login", "idUser", numberUser);
        ChromeDriver();
        statements.ScenarioTitle("Scenario003_Navigate");
        methods.NavigateToPage(mainUrl);
        System.out.println("---------------------------------------------------");
        methods.Login(login, password);
        System.out.println("---------------------------------------------------");
        methods.NavigateToMainMenu(nameMainDropdown, nameLateralDropdown);
        System.out.println("---------------------------------------------------");
    }

    //@Test
    public void Scenario004_NavigateToAllMainMenu() throws SQLException {

        // For editing:
        String numberUrl = "1"; // table: url
        String numberUser = "2"; // table login

        String mainUrl = database.GetStringValueFromDatabase("url", "url", "idUrl", numberUrl);
        String login = database.GetStringValueFromDatabase("login", "login", "idUser", numberUser);
        String password = database.GetStringValueFromDatabase("password", "login", "idUser", numberUser);
        ChromeDriver();
        statements.ScenarioTitle("Scenario004_NavigateToAllMainMenu");
        methods.NavigateToPage(mainUrl);
        System.out.println("---------------------------------------------------");
        methods.Login(login, password);
        System.out.println("---------------------------------------------------");
        methods.NavigateToAllMainMenu();
        System.out.println("---------------------------------------------------");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //@Test
    public void Scenario005_CheckAdministrationPermission() throws SQLException {
        // For editing:
        String numberUrl = "1"; // table: url
        String numberUser = "2"; // table login

        String mainUrl = database.GetStringValueFromDatabase("url", "url", "idUrl", numberUrl);
        String login = database.GetStringValueFromDatabase("login", "login", "idUser", numberUser);
        String password = database.GetStringValueFromDatabase("password", "login", "idUser", numberUser);
        ChromeDriver();
        statements.ScenarioTitle("Scenario005_CheckAdministrationPermission");
        methods.NavigateToPage(mainUrl);
        System.out.println("---------------------------------------------------");
        methods.Login(login, password);
        System.out.println("---------------------------------------------------");
        methods.NavigateToMainMenu("Administracja", "Uprawnienia");
        System.out.println("---------------------------------------------------");
        database.DeleteTableFromDatabase("administrationpermission");
        System.out.println("---------------------------------------------------");
        database.DeleteTableFromDatabase("administrationpermissionedit");
        System.out.println("---------------------------------------------------");
        administrationPermissions.updateDatabaseAll("Administration-Permissions");
        System.out.println("---------------------------------------------------");
        administrationPermissions.updateDatabaseAll("Administration-Permissions-Edit");
        System.out.println("---------------------------------------------------");
        administrationPermissions.Check();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void Scenario006_xxx() throws SQLException {
        // For editing:
        String numberUrl = "1"; // table: url
        String numberUser = "2"; // table login

        String mainUrl = database.GetStringValueFromDatabase("url", "url", "idUrl", numberUrl);
        String login = database.GetStringValueFromDatabase("login", "login", "idUser", numberUser);
        String password = database.GetStringValueFromDatabase("password", "login", "idUser", numberUser);
        ChromeDriver();
        statements.ScenarioTitle("Scenario005_CheckAdministrationPermission");
        methods.NavigateToPage(mainUrl);
        System.out.println("---------------------------------------------------");
        methods.Login(login, password);
        System.out.println("---------------------------------------------------");
        methods.NavigateToMainMenu("Administracja", "Kategorie przedmiotów");
        System.out.println("---------------------------------------------------");
        administrationItemCategories.Add("test", "test", "test");
        System.out.println("---------------------------------------------------");
    }
}