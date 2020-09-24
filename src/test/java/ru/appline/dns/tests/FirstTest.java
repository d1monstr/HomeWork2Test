package ru.appline.dns.tests;

import org.junit.Test;
import ru.appline.dns.base.BaseTests;


public class FirstTest extends BaseTests{

    @Test
    public void test(){
        app.getStartPage().searchToSearchPage("playstation")
                .chooseResult("playstation 4 slim black")
                .addToBufferPrices()
                .chooseGuarant("2 года")
                .addToBufferPrices()
                .buy()
                .searchToProductPage("Detroit")
                .addToBufferPrices()
                .buy()
                .checkPriceCart()
                .goToCart()
                .checkGuarant("playstation", "24")
                .checkPrices()
                .deleteFromCart("Detroit")
                .plusProduct("playstation")
                .plusProduct("playstation")
                .returnDeletedProduct();
    }
}
