import com.example.Utils;
import com.example.pages.HomePage;
import com.example.pages.VkPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class VkParserTest {

    private static Utils utils;
    private static WebDriver driver;
    private static HomePage homePage;
    private static VkPage vkPage;

    private static final String VALID_LOGIN = "rubtsovesenya05@mail.ru";
    private static final String VALID_PASSWORD = "rubtsove05";
    private static final String URL_HOME = "https://promopult.ru/";
    private static final String URL_PROJECTS = "https://promopult.ru/projects";

    private static final String TEST_VK_COMMUNITY_URL = "https://vk.com/pikt21";

    @BeforeAll
    public static void setUp() {
        utils = new Utils();
        utils.setupDriver();
        driver = utils.getDriver();
        homePage = new HomePage(driver);
        vkPage = new VkPage(driver);
    }

    @AfterAll
    public static void tearDown() {
        if (utils != null) {
            utils.quitDriver();
        }
    }

    @BeforeEach
    public void ensureLoggedIn() {
        if (!driver.getCurrentUrl().startsWith(URL_PROJECTS)) {
            driver.get(URL_HOME);
            homePage.clickLoginOpenButton();
            utils.getWaitTime().until(d -> homePage.isLoginFormDisplayed());
            homePage.enterCredentialsAndSubmit(VALID_LOGIN, VALID_PASSWORD);
            utils.getWaitTime().until(d -> driver.getCurrentUrl().startsWith(URL_PROJECTS));
        }
    }

    @Test
    @Order(1)
    @DisplayName("TS-09-01: Задача на парсинг сообщества ВК создана и выполнена")
    public void testVkCommunityParsingSuccess() {
        driver.get(URL_PROJECTS);
        vkPage.createVkParsingTask(TEST_VK_COMMUNITY_URL);
        boolean completed = vkPage.waitForTaskCompletion(300);
        assertTrue(completed);
    }
}