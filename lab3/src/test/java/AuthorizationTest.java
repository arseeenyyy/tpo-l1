import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import static org.junit.jupiter.api.Assertions.*;

import com.example.Utils;
import com.example.pages.HomePage;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthorizationTest {

    private static Utils utils;
    private static WebDriver driver;
    private static HomePage homePage;

    private static final String VALID_LOGIN = "rubtsovesenya05@mail.ru";
    private static final String VALID_PASSWORD = "rubtsove05";
    private static final String INVALID_LOGIN = "wrong_user@example.com";
    private static final String INVALID_PASSWORD = "wrong_password";

    private static final String URL_PROJECTS = "https://promopult.ru/projects";
    private static final String URL_HOME = "https://promopult.ru/";

    @BeforeAll
    public static void setUp() {
        utils = new Utils();
        utils.setupDriver();
        driver = utils.getDriver();
        homePage = new HomePage(driver);
    }

    @AfterAll
    public static void tearDown() {
        if (utils != null) {
            utils.quitDriver();
        }
    }

    @Test
    @Order(1)
    @DisplayName("TS-01-03: Невалидные данные для входа")
    public void invalidLoginTest() {
        driver.get(URL_HOME);
        homePage.clickLoginOpenButton();
        utils.getWaitTime().until(d -> homePage.isLoginFormDisplayed());
        homePage.enterCredentialsAndSubmit(INVALID_LOGIN, INVALID_PASSWORD);
        utils.getWaitTime().until(d -> homePage.isErrorMessageDisplayed());
        assertTrue(homePage.isErrorMessageDisplayed());
    }

    @Test
    @Order(2)
    @DisplayName("TS-01-01: Успешная авторизация")
    public void successfulLoginTest() {
        driver.get(URL_HOME);
        homePage.clickLoginOpenButton();
        utils.getWaitTime().until(d -> homePage.isLoginFormDisplayed());
        homePage.enterCredentialsAndSubmit(VALID_LOGIN, VALID_PASSWORD);
        utils.getWaitTime().until(d -> driver.getCurrentUrl().startsWith(URL_PROJECTS));
        assertEquals(URL_PROJECTS, driver.getCurrentUrl());
    }

    @Test
    @Order(3)
    @DisplayName("TS-01-02: Выход из системы")
    public void logoutTest() {
        homePage.clickLogoutButton();
        utils.getWaitTime().until(d -> driver.getCurrentUrl().equals(URL_HOME) ||
                                        driver.getCurrentUrl().startsWith(URL_HOME + "?"));
        assertTrue(driver.getCurrentUrl().startsWith(URL_HOME));
    }
}