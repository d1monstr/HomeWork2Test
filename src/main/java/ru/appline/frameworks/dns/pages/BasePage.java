package ru.appline.frameworks.dns.pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.appline.frameworks.dns.managers.ManagerPages;
import ru.appline.frameworks.dns.managers.TestPropManager;

import java.util.concurrent.TimeUnit;

import static ru.appline.frameworks.dns.managers.DriverManager.getDriver;
import static ru.appline.frameworks.dns.objects.Cart.getCart;
import static ru.appline.frameworks.dns.utils.PropConst.IMPLICITLY_WAIT;

public class BasePage {

    @FindBy(xpath = "//input[@placeholder = 'Поиск по сайту']")
    WebElement searchField;

    @FindBy(xpath = "//input[@placeholder = 'Поиск по сайту']/..//span[contains(@class, 'ui-input-search__icon_search')]")
    WebElement searchButton;

    @FindBy(xpath = "//span[@class = 'cart-link__price']")
    WebElement priceCartElem;

    @FindBy(xpath = "//span[@class='cart-link__icon']")
    WebElement cartButton;



    protected ManagerPages app = ManagerPages.getManagerPages();
    protected Actions action = new Actions(getDriver());
    protected JavascriptExecutor js = (JavascriptExecutor) getDriver();
    protected WebDriverWait wait = new WebDriverWait(getDriver(), 10, 1000);
    public static TestPropManager props = TestPropManager.getTestPropManager();

    public BasePage() {
        PageFactory.initElements(getDriver(), this);
    }

    public CartPage goToCart(){
        elementToBeClickable(cartButton);
        cartButton.click();
        return new CartPage();
    }

    public BasePage checkPriceCart(){
        Assert.assertEquals("Сумма цен товаров не соответствует значению корзины", getCart().getPriceCart(), getPriceCart());
        return this;
    }

    protected int priceToInt(String priceString){
        String price = "";
        String numbers = "0987654321";
        for (int i = 0; i < priceString.length(); ++i) {
            if (numbers.contains("" + priceString.charAt(i))) {
                price += priceString.charAt(i);
            }
        }
        return Integer.parseInt(price);
    }

    protected int getPriceCart(){
        wait.until(ExpectedConditions.textToBe(By.xpath("//span[@class = 'cart-link__badge']"), String.valueOf(getCart().countProduct())));
        return priceToInt(priceCartElem.getText());
    }

    public ProductPage searchToProductPage(String searchRequest){
        searchField.sendKeys(searchRequest);
        searchButton.click();
        return new ProductPage();
    }

    public SearchPage searchToSearchPage(String searchRequest){
        searchField.sendKeys(searchRequest);
        searchButton.click();
        return new SearchPage();
    }

    protected boolean isElementPresent(WebElement element, String xpath) {
        getDriver().manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        try {
            element.findElement(By.xpath(xpath));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
        finally {
            getDriver().manage().timeouts().implicitlyWait(Integer.parseInt(props.getProperty(IMPLICITLY_WAIT)), TimeUnit.SECONDS);
        }
    }

    public boolean isElementPresent(String xpath) {
        getDriver().manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        try {
            getDriver().findElement(By.xpath(xpath));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
        finally {
            getDriver().manage().timeouts().implicitlyWait(Integer.parseInt(props.getProperty(IMPLICITLY_WAIT)), TimeUnit.SECONDS);
        }
    }

    protected void scrollToElementJs(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    protected WebElement elementToBeClickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    protected WebElement elementToBeVisible(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void fillInputField(WebElement field, String value) {
        scrollToElementJs(field);
        elementToBeClickable(field).click();
        field.sendKeys(value);
    }

    public void fillDateField(WebElement field, String value) {
        scrollToElementJs(field);
        field.sendKeys(value);
    }

}
