package site;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

public class SiteTest {

    public WebDriver driver;

    @Test

    public void testMethod() throws InterruptedException {
        ChromeOptions options = new ChromeOptions();
        String extensionPath = "D:\\ProiectFinal\\ublockoriginlite_2025.624.1503_0";
        options.addArguments("--disable-extensions-except=" + extensionPath);
        options.addArguments("--load-extension=" + extensionPath);

        //deschidem o instanta de Chrome
        driver = new ChromeDriver(options);

        //accesam pagina web
        driver.get("https://magento.softwaretestingboard.com/customer/account/create/");

        //facem browser-ul in modul maximize
        driver.manage().window().maximize();

//        WebElement consentButtonElement = driver.findElement(By.xpath("//button[@class='fc-button fc-cta-consent fc-primary-button']"));
//        consentButtonElement.click();

        WebElement createAnAccountElement = driver.findElement(By.xpath("(//a[text()='Create an Account'])[1]"));
        createAnAccountElement.click();

        //creare cont
        WebElement firstnameElement = driver.findElement(By.id("firstname"));
        String firstnameValue = "Andreea";
        firstnameElement.sendKeys(firstnameValue);

        WebElement lastnameElement = driver.findElement(By.id("lastname"));
        String lastnameValue = "Iurean";
        lastnameElement.sendKeys(lastnameValue);

        WebElement emailElement = driver.findElement(By.id("email_address"));
        var time = String.valueOf(Instant.now().getEpochSecond());
        String emailValue = "proiect+" + time + "@test.com";
      //  String emailValue ="proiect@test.com";
        emailElement.sendKeys(emailValue);

        WebElement passwordElement = driver.findElement(By.id("password"));
        String passwordValue = "Abc123456.";
        passwordElement.sendKeys(passwordValue);

        WebElement passwordConfirmationElement = driver.findElement(By.id("password-confirmation"));
        String passwordConfirmationValue = "Abc123456.";
        passwordConfirmationElement.sendKeys(passwordConfirmationValue);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,400)");

        WebElement createAccountElement = driver.findElement(By.xpath("//button[@class='action submit primary']"));
        createAccountElement.click();

        WebElement info = driver.findElement(By.xpath("//div[@class='box box-information']//div[@class='box-content']/p"));
        String[] lines = info.getText().split("\\R");

        Assert.assertEquals(lines[0].trim(), "Andreea Iurean", "Numele nu este afișat corect");
        String normalizedEmail = lines[1].trim().replaceAll("\\+[^@]+", "");
        Assert.assertEquals(normalizedEmail, "proiect@test.com", "Emailul nu este afișat corect");

        // click pe meniul de utilizator
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement accountMenu = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[data-action='customer-menu-toggle']")));
        accountMenu.click();
        // acum putem da click pe Sign Out
        WebElement signOutElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[normalize-space()='Sign Out']")));
        signOutElement.click();

        //customer login
        driver.get("https://magento.softwaretestingboard.com/customer/account/login/");

        WebElement emaiLoginlElement = driver.findElement(By.id("email"));
        String emaiLoginlValue = "proiect@test.com";
        emaiLoginlElement.sendKeys(emaiLoginlValue);

        WebElement passwordLoginElement = driver.findElement(By.id("pass"));
        String passwordLoginValue = "Abc123456.";
        passwordLoginElement.sendKeys(passwordLoginValue);

        // JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, 400);");

        WebElement signInElement = driver.findElement(By.xpath("//button[@class='action login primary']"));
        signInElement.click();

        //accesam pagina cu produse pentru femei
        driver.get("https://magento.softwaretestingboard.com/women.html");
//        WebElement womenElement= driver.findElement(By.xpath("//li[@class='level0 nav-2 category-item active level-top parent ui-menu-item']"));
//        womenElement.click();

        WebElement womenElement = driver.findElement(By.xpath("//a[@id='ui-id-4']"));
        womenElement.click();

        js.executeScript("window.scrollTo(0, 400);");

        //reclama ascunsa care blocheaza
        List<WebElement> adCloseButtons = driver.findElements(
                By.cssSelector("div[aria-label='Close ad'], div[id^='dismiss-button']"));
        if (!adCloseButtons.isEmpty()) {
            adCloseButtons.get(0).click();
        }

        //alegem un produs, selectam marime si culoare si facem click pe add to cart
        WebElement selectSizeElement = driver.findElement(By.id("option-label-size-143-item-167"));
        //selectSizeElement.click();
        js.executeScript("arguments[0].scrollIntoView(true);", selectSizeElement);
        js.executeScript("arguments[0].click();", selectSizeElement);

        WebElement selectColorElement = driver.findElement(By.xpath("//div[@id='option-label-color-93-item-57']"));
        selectColorElement.click();

        // WebElement addToCartElement= driver.findElement(By.xpath("//button[@class='action tocart primary']"));
        // addToCartElement.click();
        WebElement addToCartElement = driver.findElement(By.xpath("//button[@class='action tocart primary']"));
        addToCartElement.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.message-success.success.message")));

//        JavascriptExecutor js = (JavascriptExecutor) driver;
//        js.executeScript("window.scrollTo(0, 0);");
//        try {
//           // wait.wait(2000);
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//
//        }

        Thread.sleep(5000);
        WebElement cartIcon = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.action.showcart")));
        cartIcon.click();
        WebElement viewCartLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@class='action viewcart']")));
        viewCartLink.click();

        wait.until(ExpectedConditions.urlContains("checkout/cart"));

        //edit button
        WebElement editButtonELement = driver.findElement(By.xpath("//a[@class='action action-edit']"));
        editButtonELement.click();

        //inchidere reclama
     //   WebElement reclamaELement = driver.findElement(By.cssSelector("iframe[id='aswift_1']"));
     //   reclamaELement.click();

//        WebElement rejectReclamaElement = driver.findElement(By.xpath("//div[@class='mask-2wi8r']"));
//        rejectReclamaElement.click();

//        driver.switchTo().frame("aswift_1");
//        WebElement inchidereReclamaElement =wait.until(ExpectedConditions.presenceOfElementLocated(By.id("dismiss-button")));
//        //WebElement inchidereReclamaElement = driver.findElement(By.id("dismiss-button"));
//        inchidereReclamaElement.click();

//        WebElement clearQuantity = driver.findElement(By.xpath("//a[@class='action delete']"));
//        clearQuantity.clear();

       js.executeScript("window.scrollTo(0, 400);");
//
//        WebDriverWait waitScroll = new WebDriverWait(driver, Duration.ofSeconds(10));
//        waitScroll.until(ExpectedConditions.alertIsPresent());

//        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("form#product_addtocart_form")));
//        js.executeScript("window.scrollTo(0, 400);");

        //modificam cantitatea, culoarea si facem update cart
        WebElement quantityElement = driver.findElement(By.cssSelector("input[class='input-text qty']"));
        quantityElement.clear();
        quantityElement.sendKeys("1");

        WebElement colorElement = driver.findElement(By.id("option-label-color-93-item-56"));
        colorElement.click();
//
//        WebElement inchidereReclamaV2= driver.findElement(By.xpath("//div[@id='dismiss-button']"));
//        inchidereReclamaV2.click();

        // WebElement updateCartElement= driver.findElement(By.xpath("//button[@class='action primary tocart']"));
        WebElement updateCartElement = wait.until(ExpectedConditions.elementToBeClickable(By.id("product-updatecart-button")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", updateCartElement);
        updateCartElement.click();

        //add to wishlist din cos
        //move to wish list
        WebElement addToWishListElement = driver.findElement(By.xpath("//a[@class='action towishlist']"));
        addToWishListElement.click();

//        WebElement addToWishListElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@class='action towishlist']")));
//        addToWishListElement.click();
        wait.until(ExpectedConditions.urlContains("/wishlist"));

        //mergem din nou pe pagina de cos
        driver.get("https://magento.softwaretestingboard.com/checkout/cart");

        List<WebElement> deleteButtons = driver.findElements(By.xpath("//a[@class='action action-delete']"));
        if (deleteButtons.isEmpty()) {
            System.out.println("Produsul a fost mutat în wishlist. Coșul este gol.");
        } else {
            System.out.println("Produsul este încă în coș. Se poate șterge.");
            // Șterge-l dacă e cazul
            deleteButtons.get(0).click();
        }

        //delete cart button
        // WebElement deleteCartButtonElement= driver.findElement(By.xpath("//a[@class='action action-delete']"));
        //WebDriverWait wait= new WebDriverWait(driver,Duration.ofSeconds(10));
//        WebElement deleteCartButtonElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@class='action action-delete']")));
//        deleteCartButtonElement.click();
//        wait.until(ExpectedConditions.alertIsPresent());
//        WebElement deleteButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@class='action action-delete']//span[text()='Remove item']")));
//        deleteButton.click();

    }
}

