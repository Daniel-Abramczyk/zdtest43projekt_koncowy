import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FirstTests {

    WebDriver driver; // utworzenie pustego pola driver, aby bylo dostępne we wszyskiech metodach testowych
    WebDriverWait wait;

    private void sleep(int miliSec) {
        try {
            Thread.sleep(miliSec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void highlightElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);
    }

    @Before //Warunki początkowe testów, wykona sie przed każdą metodą testów
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver\\chromedriver.exe");
        //ustawiamy property ze wskazaniem na chromedriver, którego użyjemy w testach
        driver = new ChromeDriver(); //otworzy nam przeglądarkę
        System.out.println("Wykonuję się tutaj! przed metodą testową");
        wait = new WebDriverWait(driver, 10);
    }

    @Test //kroki testowe - po prostu test do wykonania
    public void firstTest() {
        driver.get("https://dev.to"); //przejdź na stronę dev.to
        WebElement sideBarVideo = driver.findElement(By.xpath("/html/body/div[9]/div/div/div[1]/aside/nav[1]/div/a[3]")); //znajdz element week
        highlightElement(sideBarVideo);
        //sideBarVideo.click(); //klikamy w weekBtn
    }
//
//    @Test //kroki testowe -po prostu test do wykonania
//    public void firstTest(){
//        driver.get("https://dev.to"); //przejdź na stronę dev.to
//        WebElement sideBarVideo = driver.findElement(By.xpath("//a[@href='https://dev.to/videos']")); // znajdź element week
//        highlightElement(sideBarVideo);
//    }

    @Test
    public void openFirstVideoPage() {
        driver.get("https://dev.to");
        WebElement sideBarVideo = driver.findElement(By.partialLinkText("Videos"));
        highlightElement(sideBarVideo);
        sideBarVideo.click();
        //przechodzimy na strone z video
        //powinniśmy poczekać na załadowanie nowej strony
                wait.until(ExpectedConditions.urlToBe("https://dev.to/videos"));
        WebElement firstVideo = driver.findElement(By.className("video-image"));
        highlightElement(firstVideo);
        firstVideo.click();
    }
    @Test
    public void searchTest()
    {
        driver.get("https://dev.to");
        driver.findElement(By.name("q")).sendKeys("testing\n");
        sleep(4000);
        String firstResultText = driver.findElement(By.id("article-link-57091")).getText().toLowerCase();
        Assert.assertTrue(firstResultText.contains("testing"));
        String secondResultText = driver.findElement(By.id("article-link-81023")).getText().toLowerCase();
        Assert.assertTrue(secondResultText.contains("testing"));
        String thirdResultText = driver.findElement(By.id("article-link-267204")).getText().toLowerCase();
        Assert.assertTrue(thirdResultText.contains("testing"));
    }

        @Test
        public void highlightFirstVideo(){
            driver.get("https://dev.to/videos");
            WebElement firstVideo = driver.findElement(By.className("video-image"));
            highlightElement(firstVideo);
            firstVideo.click();
        }
        //wejdz na stronę dev.to
        //kliknąć w podcasts
        //wybrać pierwszy podcast
        //sprawdzić czy jestem na odpowiedniej stronie - czy tytuł podcastu się zgadza
        //sprawdzić czy mogę nacisnąć play

        @Test
        public void selectFirstPodcast(){
            driver.get("https://dev.to");
            WebElement podcasts = driver.findElement(By.partialLinkText("Podcasts"));
            podcasts.click();
            wait.until(ExpectedConditions.urlToBe("https://dev.to/pod"));

            WebElement firstPodcasts = driver.findElement(By.cssSelector(".content > h3:first-child"));
            String podcastTitleFromList = firstPodcasts.getText();

            String firstPodcastsFromListLink = driver.findElement(By.cssSelector("#substories > a:first-child")).getAttribute("href");

            firstPodcasts.click();
            wait.until(ExpectedConditions.urlToBe(firstPodcastsFromListLink));
            WebElement podcastTitle = driver.findElement(By.cssSelector(".title > h1:nth-child(2)"));
            String podcastTitleText = podcastTitle.getText();
            assertTrue(podcastTitleFromList.contains(podcastTitleText));
            //znajdz record
            //kliknij record
            WebElement record = driver.findElement(By.cssSelector(".record"));
            record.click();

            WebElement initializing = driver.findElement(By.className("status-message"));
            wait.until(ExpectedConditions.invisibilityOf(initializing));
            WebElement recordWrapper = driver.findElement(By.className("record-wrapper"));
            Boolean isPodcastPlayed = recordWrapper.getAttribute("class").contains("playing");
            //String classAttribute = recordWrapper.getAttribute ("class");
            //Boolean isPodcastPlayed = classAttribute.contains("playing");
            
            assertTrue(isPodcastPlayed);


        }
    }


//    @After
//    public void tearDown(){
//        driver.quit();
//        System.out.println("po każdej metodzie testowej");
//    }


