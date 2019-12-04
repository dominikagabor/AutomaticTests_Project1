
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.sql.SQLException;

class Methods {

    // Variables and classes:
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

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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

    // Stop debbug:
    private void StopDebbug() {
        System.exit(0);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Find WebElement:
    private WebElement WebElementFind(String menu, String name, String type) throws SQLException {
        String xpath = database.GetStringValueFromDatabase("xpath", "xpath", "menu", menu, "name", name, "type", type);
        return driver.findElement(By.xpath(xpath));
    }

    // Wait until you find the WebElement
    private WebElement WebElementFindAndWait(String menu, String name, String type) throws SQLException {
        String xpath = database.GetStringValueFromDatabase("xpath", "xpath", "menu", menu, "name", name, "type", type);
        Wait(xpath);
        return driver.findElement(By.xpath(xpath));
    }

    // Wait until you find the WebElement with xpath:
    private WebElement WebElementFindWithXpath(String xpath) {
        WebElement element = driver.findElement(By.xpath(xpath));
        Wait(xpath);
        return element;
    }

    // Wait until you find the WebElement and click WebElement:
    private WebElement WebElementFindAndClick(String menu, String name, String type) throws SQLException {
        WebElement element = WebElementFindAndWait(menu, name, type);
        element.click();
        return element;
    }

    // Wait until you find the WebElement with xpath and click WebElement:
    private WebElement WebElementFindAndClickWithXpath(String xpath) {
        WebElement element = driver.findElement(By.xpath(xpath));
        Wait(xpath);
        element.click();
        return element;
    }

    // Wait until you find the WebElement, click WebElement and send keys:
    private void WebElementFindAndClickAndSendKeys(String menu, String name, String type, String sendKeys) throws SQLException {
        WebElement element = WebElementFindAndClick(menu, name, type);
        element.clear();
        element.sendKeys(sendKeys);
    }

    // Wait until you find the WebElement, click WebElement and send keys:
    private void WebElementFindAndClickAndSendKeysWithXpath(String xpath, String sendKeys) throws SQLException {
        WebElement element = driver.findElement(By.xpath(xpath));
        element.clear();
        element.sendKeys(sendKeys);
    }

    // Wait until you find the WebElement and get text:
    private String WebElementFindAndGetText(String menu, String name, String type) throws SQLException {
        WebElement element = WebElementFindAndWait(menu, name, type);
        return element.getText();
    }

    // Wait until you find the WebElement and get text:
    private String WebElementFindAndGetTextWithXpath(String xpath) throws SQLException {
        WebElement element = driver.findElement(By.xpath(xpath));
        return element.getText();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Go to the main link:
    void NavigateToPage(String url) {
        try {
            driver.navigate().to(url);
            WebElementFind("Logowanie", "Logowanie", "Label");
            driver.manage().window().maximize();
            statements.TestPassed("Navigate to page");
            statements.AddText("Url", url, "GREEN");
        } catch (Exception e) {
            statements.TestFailed("Navigate to page");
            statements.AddText("Error", String.valueOf(e), "RED");
            StopDebbug();
        }
    }

    // Go to the main link already login:
    void NavigateToPageAlreadyLogin(String url) {
        try {
            driver.navigate().to(url);
            WebElementFind("StronaGlowna", "Panel użytkownika", "Label");
            driver.manage().window().maximize();
            statements.TestPassed("Navigate to page");
            statements.AddText("Url", url, "GREEN");
        } catch (Exception e) {
            statements.TestFailed("Navigate to page");
            statements.AddText("Error", String.valueOf(e), "RED");
            StopDebbug();
        }
    }

    // Logging into the portal:
    boolean Login(String login, String password) throws SQLException {
        boolean correctorNotLogin;
        ChromeDriver(driver);
        WebElementFindAndClickAndSendKeys("Logowanie", "Nazwa użytkownika", "EditText", login);
        WebElementFindAndClickAndSendKeys("Logowanie", "Hasło", "EditText", password);
        WebElementFindAndClick("Logowanie", "Zaloguj", "Button");
        try {
            WebElementFind("StronaGlowna", "Panel użytkownika", "Label");
            statements.TestPassed("Login");
            statements.AddText("Login used", login, "GREEN");
            statements.AddText("Password used", password, "GREEN");
            correctorNotLogin = true;
        } catch (Exception e) {
            statements.TestFailed("Login");
            statements.AddText("Login used", login, "RED");
            statements.AddText("Password used", password, "RED");
            WebElementFind("Logowanie", "Error", "Label");
            String xPathError = WebElementFindAndGetText("Logowanie", "Error", "Label");
            statements.AddText("Error", xPathError, "RED");
            correctorNotLogin = false;
        }

        return correctorNotLogin;
    }

    //Login of all users to the portal:
    void LoginAll() throws SQLException {
        ChromeDriver(driver);
        int countUser = database.GetCountFromTable("login");
        int goodScore = 0;
        int badScore = 0;

        for (int numberUser = 1; numberUser <= countUser; numberUser++) {

            String login = database.GetStringValueFromDatabase("login", "login", "idUser", String.valueOf(numberUser));
            String password = database.GetStringValueFromDatabase("password", "login", "idUser", String.valueOf(numberUser));

            try {
                boolean correctorNotLogin = Login(login, password);
                if (correctorNotLogin) {
                    statements.AddTextNumber("User", numberUser, countUser, "GREEN");
                    Logout();
                    goodScore++;
                } else {
                    statements.AddTextNumber("User", numberUser, countUser, "RED");
                    badScore++;
                }
            } catch (Exception e) {
                statements.AddText("Error", String.valueOf(e), "RED");
            }
            System.out.println("---------------------------------------------------");
        }
        statements.AddTextNumber("Number of valid logins", goodScore, countUser, "GREEN");
        statements.AddTextNumber("Number of invalid logins", badScore, countUser, "RED");
    }

    // Logging out of the portal:
    private void Logout() throws SQLException {
        try {
            WebElementFindAndClick("Profil", "Nazwa profilu", "Button");
            WebElementFindAndClick("Profil", "Wyloguj", "Button");
            WebElementFind("Logowanie", "Logowanie", "Label");
            statements.TestPassed("Logout");
        } catch (Exception e) {
            statements.TestFailed("Logout");
            statements.AddText("Error", String.valueOf(e), "RED");
            StopDebbug();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Navigation to the selected element of the main menu:
    String NavigateToMainMenu(String nameMainDropdown, String nameLateralDropdown) throws SQLException {

        String idNameMainDropdown = database.GetStringValueFromDatabase("idmainDropdown", "maindropdown", "name", nameMainDropdown);
        String xPathNameMainDropdown = database.GetStringValueFromDatabase("xpathNumber", "maindropdown", "name", nameMainDropdown);
        String xPathNameLateralDropdown = database.GetStringValueFromDatabase("xpathNumber", "lateraldropdown", "idmainDropdown", idNameMainDropdown, "name", nameLateralDropdown);

        String mainMenuPartOne = database.GetStringValueFromDatabase("xpath", "xpath", "name", "MainMenuPartOne");
        String mainMenuPartTwo = database.GetStringValueFromDatabase("xpath", "xpath", "name", "MainMenuPartTwo");
        String lateralMenuPartOne = database.GetStringValueFromDatabase("xpath", "xpath", "name", "LateralMenuPartOne");
        String lateralMenuPartTwo = database.GetStringValueFromDatabase("xpath", "xpath", "name", "LateralMenuPartTwo");
        String lateralMenuPartThree = database.GetStringValueFromDatabase("xpath", "xpath", "name", "LateralMenuPartThree");

        String xPathToMainMenu = mainMenuPartOne + xPathNameMainDropdown + mainMenuPartTwo;
        String xPathToLateralMenu = lateralMenuPartOne + xPathNameMainDropdown + lateralMenuPartTwo + xPathNameLateralDropdown + lateralMenuPartThree;

        String passOrFail = null;
        try {
            WebElementFindAndClickWithXpath(xPathToMainMenu);
            WebElementFindAndClickWithXpath(xPathToLateralMenu);

            String complianceCheck = database.GetStringValueFromDatabase("checkName", "lateralDropdown", "idmainDropdown", idNameMainDropdown, "name", nameLateralDropdown);
            String xpathCheck = database.GetStringValueFromDatabase("checkXpath", "lateralDropdown", "checkName", complianceCheck);
            String checkText = WebElementFindAndGetTextWithXpath(xpathCheck);

            if (complianceCheck.equals(checkText)) {
                statements.TestPassed("Navigate to the selected menu");
                statements.AddText("Navigate to", nameMainDropdown + " - " + nameLateralDropdown, "GREEN");
                passOrFail = "pass";
            } else {
                statements.TestFailed("Navigate to main menu");
                statements.AddText("Valid value", complianceCheck, "RED");
                statements.AddText("Invalid value", WebElementFindAndGetTextWithXpath(xpathCheck), "RED");
                passOrFail = "fail";
            }
        } catch (Exception e) {
            statements.TestFailed("Navigate to main menu");
            statements.AddText("Navigate to", nameMainDropdown + " - " + nameLateralDropdown, "RED");
            statements.AddText("Error", String.valueOf(e), "RED");
        }
        return passOrFail;
    }

    // Navigation to all main menu items:
    void NavigateToAllMainMenu() throws SQLException {
        {
            int countAllMenuMain = database.GetCountFromTable("maindropdown");
            int countAllMenuLateral = database.GetCountFromTable("lateraldropdown");
            int mainCountMainMenu = 0;

            int goodScore = 0;
            int badScore = 0;

            for (int mainMenu = 1; mainMenu <= countAllMenuMain; mainMenu++) {

                int countLateralMenu = database.GetCountFromTableWhere("lateraldropdown", "idmainDropdown", String.valueOf(mainMenu));

                for (int lateralMenu = 1; lateralMenu <= countLateralMenu; lateralMenu++) {
                    String nameMainDropdown = database.GetStringValueFromDatabase("name", "maindropdown", "idmainDropdown", String.valueOf(mainMenu));
                    String nameLateralDropdown = database.GetStringValueFromDatabase("name", "lateraldropdown", "idmainDropdown", String.valueOf(mainMenu), "xpathNumber", String.valueOf(lateralMenu));
                    mainCountMainMenu++;

                    String passOrFail = null;
                    try {
                        passOrFail = NavigateToMainMenu(nameMainDropdown, nameLateralDropdown);

                        if (passOrFail.equals("pass")) {
                            goodScore++;
                            statements.AddTextNumber("Number", mainCountMainMenu, countAllMenuLateral, "GREEN");
                        }

                        if (passOrFail.equals("fail")) {
                            badScore++;
                            statements.AddTextNumber("Number", mainCountMainMenu, countAllMenuLateral, "RED");
                        }

                        System.out.println("---------------------------------------------------");

                    } catch (Exception e) {
                        statements.TestFailed("Navigate to main menu");
                        statements.AddText("Navigate to", nameMainDropdown + " - " + nameLateralDropdown, "RED");
                        statements.AddText("Error", String.valueOf(e), "RED");
                        badScore++;
                    }
                }
            }
            statements.AddTextNumber("Number of valid navigate", goodScore, countAllMenuLateral, "GREEN");
            statements.AddTextNumber("Number of invalid navigate", badScore, countAllMenuLateral, "RED");
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
