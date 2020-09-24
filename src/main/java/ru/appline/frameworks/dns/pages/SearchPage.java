package ru.appline.frameworks.dns.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class SearchPage extends BasePage {

    @FindBy(xpath = "//div[@class = 'catalog-item']")
    List<WebElement> listProducts;

    public ProductPage chooseResult(String nameProduct){
        WebElement titleElement;
        ProductPage productPage = null;
        for (WebElement element : listProducts){
            titleElement = element.findElement(By.xpath(".//div[@class = 'product-info__title-link']/a"));
            if (titleElement.getText().toLowerCase().contains(nameProduct.toLowerCase())){
                titleElement.click();
                productPage = new ProductPage();
                break;
            }
        }
        return productPage;
    }

}
