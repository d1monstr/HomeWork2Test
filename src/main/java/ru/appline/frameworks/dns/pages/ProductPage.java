package ru.appline.frameworks.dns.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import ru.appline.frameworks.dns.objects.Product;

import java.util.HashMap;
import java.util.Map;

import static ru.appline.frameworks.dns.objects.Cart.getCart;

public class ProductPage extends BasePage {

    @FindBy(xpath = "//span[contains(@class, 'product-card-price__current')]")
    WebElement priceElement;

    @FindBy(xpath = "//h1[@data-product-param = 'name']")
    WebElement productNameElement;

    @FindBy(xpath = "//select[@class = 'form-control select']")
    WebElement selectedGuarant;

    @FindBy(xpath = "//button[text() = 'Купить']")
    WebElement addToCartButton;

    @FindBy(xpath = "//div[@data-id = 'product']")
    WebElement productInfo;

    public Map<String, Integer> mapBufferedProducts = new HashMap<>();

    public ProductPage buy(){
        elementToBeVisible(productInfo);
        getCart().add(getProduct());
        elementToBeClickable(addToCartButton);
        addToCartButton.click();
        return this;
    }

    public ProductPage chooseGuarant(String guarant){
        Select guarantSelect = new Select(selectedGuarant);
        guarantSelect.selectByVisibleText(guarant);
        wait.until(ExpectedConditions.attributeToBe(priceElement, "class", "product-card-price__current product-card-price__current_active"));
        return this;
    }

    public String getChoosenGuarant(){
        if (isElementPresent("//select[@class = 'form-control select']")){
            Select select = new Select(selectedGuarant);
            return select.getFirstSelectedOption().getText();
        }
        return "-------------------";
    }

    public String getProductName(){
        return productNameElement.getText().toLowerCase();
    }

    public int getPrice(){
        elementToBeVisible(priceElement);
        return priceToInt(priceElement.getText());
    }

    public ProductPage addToBufferPrices(){
        mapBufferedProducts.put(getProduct().getProductName(), getProduct().getPrice());
        return this;
    }

    public Product getProduct(){
        return new Product(getProductName(), getPrice(), getChoosenGuarant());
    }

}
