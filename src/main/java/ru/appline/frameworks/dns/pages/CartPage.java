package ru.appline.frameworks.dns.pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.appline.frameworks.dns.objects.Product;

import java.util.List;

import static ru.appline.frameworks.dns.objects.Cart.getCart;

public class CartPage extends BasePage {

    @FindBy(xpath = "//div[@class = 'cart-items__product']")
    List<WebElement> listProductsInCart;

    @FindBy(xpath = "//span[contains(@class, 'base-ui-radio-button__icon_checked')]")
    WebElement selectedGuarant;

    @FindBy(xpath = "//div[@class = 'total-amount']//span[@class = 'price__current']")
    WebElement totalAmount;

    @FindBy(xpath = "//span[contains(text(), 'Вернуть удалённый товар')]")
    WebElement ReturnDeletedProduct;


    public CartPage returnDeletedProduct(){
        WebElement titleElement, priceElement;
        elementToBeVisible(ReturnDeletedProduct);
        elementToBeClickable(ReturnDeletedProduct);
        int beforeTotalAmount = priceToInt(totalAmount.getText());
        ReturnDeletedProduct.click();
        getCart().add(getCart().getLastRemovedProduct());

        for (WebElement element : listProductsInCart) {
            titleElement = element.findElement(By.xpath(".//a[@class = 'cart-items__product-name-link']"));
            String productName = titleElement.getText();
            if (productName.toLowerCase().contains(getCart().getLastRemovedProduct().getProductName().toLowerCase())){
                priceElement = element.findElement(By.xpath(".//span[@class = 'price__current']"));
                elementToBeVisible(totalAmount);
                Assert.assertTrue("Проверка на возвращение последнего удаленного продукта не пройдена", productName.toLowerCase().contains(getCart().getLastRemovedProduct().getProductName().toLowerCase()));
                Assert.assertEquals("Сумма цен продуктов не соответствует их количеству", beforeTotalAmount + priceToInt(priceElement.getText()), priceToInt(totalAmount.getText()));
                break;
            }
        }
        return this;
    }

    public CartPage plusProduct(String name){
        WebElement titleElement, plusElement, countElement;
        int productAmount = getProductPrice(name);
        for (WebElement element : listProductsInCart) {
            titleElement = element.findElement(By.xpath(".//a[@class = 'cart-items__product-name-link']"));
            plusElement = element.findElement(By.xpath(".//i[@class = 'count-buttons__icon-plus']"));
            String productName = titleElement.getText();
            if (productName.toLowerCase().contains(name.toLowerCase())){
                plusElement.click();
                for (Product product : getCart().getListPoducts()){
                    if (product.getProductName().toLowerCase().equals(productName.toLowerCase())){
                        productAmount += product.getPrice();
                        getCart().add(product);
                        break;
                    }
                }
                wait.until(ExpectedConditions.attributeContains(By.xpath("//div[@class = 'total-amount__count']"), "textContent", String.valueOf(getCart().countProduct())));
                break;
            }
        }
        wait.until(ExpectedConditions.attributeContains(By.xpath("//div[@class = 'total-amount__count']"), "textContent", String.valueOf(getCart().countProduct())));
        Assert.assertEquals("Сумма цен продуктов не соответствует их количеству", productAmount, priceToInt(totalAmount.getText()));
        return this;
    }

    public int getProductPrice(String name){
        WebElement titleElement, priceElement;
        int productPrice = 0;
        for (WebElement element : listProductsInCart) {
            titleElement = element.findElement(By.xpath(".//a[@class = 'cart-items__product-name-link']"));
            priceElement = element.findElement(By.xpath(".//span[@class = 'price__current']"));
            String productName = titleElement.getText();
            if (productName.toLowerCase().contains(name.toLowerCase())){
                productPrice = priceToInt(priceElement.getText());
                for (Product product : getCart().getListPoducts()){
                    if (product.getProductName().toLowerCase().equals(productName.toLowerCase())){
                        if (isElementPresent(element, ".//div[@class='base-ui-radio-button additional-warranties-row__radio']")){
                            productPrice += priceToInt(selectedGuarant.findElement(By.xpath("./../../span[@class = 'additional-warranties-row__price']")).getText());
                        }
                    }
                }
                break;
            }
        }
        return productPrice;
    }

    public CartPage deleteFromCart(String name){
        WebElement titleElement, priceElement, deleteElement;
        int beforeTotalAmount = 0, afterTotalAmount, productPrice = 0;
        for (WebElement element : listProductsInCart){
            titleElement = element.findElement(By.xpath(".//a[@class = 'cart-items__product-name-link']"));
            deleteElement = element.findElement(By.xpath(".//button[contains(text(), 'Удалить')]"));
            priceElement = element.findElement(By.xpath(".//span[@class = 'price__current']"));
            String productName = titleElement.getText();
            if (productName.toLowerCase().contains(name.toLowerCase())){
                beforeTotalAmount = priceToInt(totalAmount.getText());
                productPrice = priceToInt(priceElement.getText());
                deleteElement.click();
                break;
            }
            for (Product product : getCart().getListPoducts()){
                if (product.getProductName().toLowerCase().contains(name.toLowerCase())){
                    getCart().remove(product);
                    break;
                }
            }
        }
        elementToBeVisible(totalAmount);
        afterTotalAmount = getPriceCart();
        Assert.assertEquals("Суммарная стоимость корзины не соответствует сумме цен товаров", beforeTotalAmount - productPrice, afterTotalAmount);
        return this;
    }

    public CartPage checkPrices(){
        WebElement titleElement, priceElement;
        int sumPrices = 0;
        for (WebElement element : listProductsInCart){
            titleElement = element.findElement(By.xpath(".//a[@class = 'cart-items__product-name-link']"));
            priceElement = element.findElement(By.xpath(".//span[@class = 'price__current']"));
            elementToBeVisible(priceElement);
            String productName = titleElement.getText();
            for (Product product : getCart().getListPoducts()){
                if (product.getProductName().toLowerCase().contains(productName.toLowerCase())){
                    if (isElementPresent(element, ".//div[@class='base-ui-radio-button additional-warranties-row__radio']")){
                        sumPrices += priceToInt(selectedGuarant.findElement(By.xpath("./../../span[@class = 'additional-warranties-row__price']")).getText());
                        Assert.assertEquals("Цена " + productName + " в корзине не соответствует цене записанной ранее", product.getPrice(), priceToInt(priceElement.getText()) + priceToInt(selectedGuarant.findElement(By.xpath("./../../span[@class = 'additional-warranties-row__price']")).getText()));
                    }
                    break;
                }
            }
            sumPrices += priceToInt(priceElement.getText());
        }
        elementToBeVisible(totalAmount);

        Assert.assertEquals("Суммарная стоимость корзины не соответствует сумме цен товаров", sumPrices, priceToInt(totalAmount.getText()));
        return this;
    }

    public CartPage checkGuarant(String productName, String expectGuarant){
        WebElement titleElement, radioGuarant;
        for (WebElement element : listProductsInCart){
            titleElement = element.findElement(By.xpath(".//a[@class = 'cart-items__product-name-link']"));
            if (titleElement.getText().toLowerCase().contains(productName.toLowerCase())){
                radioGuarant = element.findElement(By.xpath(".//span[contains(@class, 'base-ui-radio-button__icon') and contains(text(), '" + expectGuarant + "')]"));
                Assert.assertEquals("Выбранная гарантия не соответствует требуемой", "base-ui-radio-button__icon base-ui-radio-button__icon_checked", radioGuarant.getAttribute("class"));
                break;
            }
        }
        return this;
    }

}
