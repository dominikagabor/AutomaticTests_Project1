import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Statements {

    private WebDriver driver;
    void TransferDriver(WebDriver transferDriver)
    {
        driver = transferDriver;
    }

    // Colours:
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    // Information about the test passed:
    // For example:
    // TEST PASSED
    // Method: Login
    void TestPassed(String method) {
        System.out.println(ANSI_GREEN + " TEST PASSED" + ANSI_RESET);
        System.out.println(ANSI_GREEN + "Method: " + ANSI_RESET + method);
    }

    // Information about the failed test:
    // For example:
    // TEST FAILED
    // Method: Login
    void TestFailed(String method) {
        System.out.println(ANSI_RED + " TEST FAILED" + ANSI_RESET);
        System.out.println(ANSI_RED + "Method: " + ANSI_RESET + method);
    }

    // Scenarios title:
    void ScenarioTitle(String title)
    {
        System.out.println(ANSI_PURPLE + title + ANSI_RESET);
    }

    // Add text:
    // For example:
    // Error: Niepoprawna nazwa użytkownika lub hasło.
    void AddText(String mainText, String text, String color) {
        if(color.equals("GREEN")) {
            System.out.println(ANSI_GREEN + mainText + ": " + ANSI_RESET + text);
        }

        if(color.equals("RED")) {
            System.out.println(ANSI_RED + mainText + ": " + ANSI_RESET + text);
        }

        if(color.equals("BLUE")) {
            System.out.println(ANSI_BLUE + mainText + ": " + ANSI_RESET + text);
        }
    }

    // Add text with the number of elements:
    // For example:
    // Number of users: 3 / 10
    void AddTextNumber(String mainText, int numberOne, int numberTwo, String color)
    {
        if(color.equals("GREEN")) {
            System.out.println(ANSI_GREEN + mainText + ": " + ANSI_RESET + numberOne + "/" + numberTwo);
        }

        if(color.equals("RED")) {
            System.out.println(ANSI_RED + mainText + ": " + ANSI_RESET + numberOne + "/" + numberTwo);
        }
    }
}
