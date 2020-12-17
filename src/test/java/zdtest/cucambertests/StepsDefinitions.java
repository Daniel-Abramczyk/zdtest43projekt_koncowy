package zdtest.cucambertests;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.assertTrue;

public class StepsDefinitions {

    WebDriver driver;
    WebDriverWait wait;
    String podcastTitleFromList;
    String firstPodcastsFromListLink;

    @Before
    public void setup(){
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver\\chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver,10);
    }

    @Given("DevTo main page is open")
    public void dev_to_main_page_is_open() {
        driver.get("https://dev.to");
    }

    @When("User click on podcasts")
    public void user_click_on_podcasts() {
        WebElement podcasts = driver.findElement(By.partialLinkText("Podcasts"));
        podcasts.click();
    }
    @When("User select first podcasts")
    public void user_select_first_podcasts() {
        wait.until(ExpectedConditions.urlToBe("https://dev.to/pod"));
        WebElement firstPodcasts = driver.findElement(By.cssSelector(".content > h3:first-child"));
        podcastTitleFromList = firstPodcasts.getText();
        firstPodcastsFromListLink = driver.findElement(By.cssSelector("#substories > a:first-child")).getAttribute("href");
        firstPodcasts.click();
    }
    @Then("User can see its title")
    public void user_can_see_its_title() {
        wait.until(ExpectedConditions.urlToBe(firstPodcastsFromListLink));
        WebElement podcastTitle = driver.findElement(By.cssSelector(".title > h1:nth-child(2)"));
        String podcastTitleText = podcastTitle.getText();
        assertTrue(podcastTitleFromList.contains(podcastTitleText));
    }
    @Then("User can play it")
    public void user_can_play_it() {
        WebElement record = driver.findElement(By.cssSelector(".record"));
        record.click();

        WebElement initializing = driver.findElement(By.className("status-message"));
        wait.until(ExpectedConditions.invisibilityOf(initializing));
        WebElement recordWrapper = driver.findElement(By.className("record-wrapper"));
        Boolean isPodcastPlayed = recordWrapper.getAttribute("class").contains("playing");
        assertTrue(isPodcastPlayed);
    }
    @When("User click on videos")
    public void user_click_on_videos() {
        WebElement sideBarVideo = driver.findElement(By.partialLinkText("Videos"));
        sideBarVideo.click();
    }

    @When("User select first video")
    public void user_select_first_video() {
        wait.until(ExpectedConditions.urlToBe("https://dev.to/videos"));
    }
    @Then("User see video title")
    public void user_see_video_title() {
        WebElement firstVideo = driver.findElement(By.className("video-image"));
        firstVideo.click();
    }

    @When("User search {string}")
    public void userSearch(String searchingPhrase) {
        WebElement searchBar = driver.findElement(By.name("q"));
        searchBar.sendKeys(searchingPhrase); //wyszuka element z parametru
        searchBar.sendKeys(Keys.RETURN); //wciśnie enter
    }

    @Then("Top result should contain the work {string}")
    public void topResultShouldContainTheWork(String expectedPhrase) {
        wait.until(ExpectedConditions.urlContains("search?q=" + expectedPhrase));
        WebElement topResult = driver.findElement(By.className("crayons-story_title"));
        String topResultTitle = topResult.getText();
        topResultTitle = topResultTitle.toLowerCase();
        assertTrue(topResultTitle.contains(expectedPhrase));
    }
}
