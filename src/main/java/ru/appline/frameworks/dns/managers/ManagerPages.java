package ru.appline.frameworks.dns.managers;

import ru.appline.frameworks.dns.pages.CartPage;
import ru.appline.frameworks.dns.pages.ProductPage;
import ru.appline.frameworks.dns.pages.SearchPage;
import ru.appline.frameworks.dns.pages.StartPage;

public class ManagerPages {

    private static ManagerPages managerPages;


    StartPage startPage;

    SearchPage searchPage;

    CartPage cartPage;

    ProductPage productPage;

    private ManagerPages() {
    }

    public static ManagerPages getManagerPages() {
        if (managerPages == null) {
            managerPages = new ManagerPages();
        }
        return managerPages;
    }

    public StartPage getStartPage() {
        if (startPage == null) {
            startPage = new StartPage();
        }
        return startPage;
    }

    public SearchPage getSearchPage() {
        if (searchPage == null) {
            searchPage = new SearchPage();
        }
        return searchPage;
    }

    public CartPage getCartPage() {
        if (cartPage == null) {
            cartPage = new CartPage();
        }
        return cartPage;
    }

    public ProductPage getProductPage() {
        if (productPage == null) {
            productPage = new ProductPage();
        }
        return productPage;
    }

}
