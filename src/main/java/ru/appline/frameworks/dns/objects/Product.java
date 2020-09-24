package ru.appline.frameworks.dns.objects;

public class Product {

    private String productName;
    private int price;
    private String guarant;

    public Product(String productName, int price) {
        this.productName = productName;
        this.price = price;
        this.guarant = "-------------------";
    }

    public Product(String productName, int price, String guarant) {
        this.productName = productName;
        this.price = price;
        this.guarant = guarant;
    }

    public String getProductName() {
        return productName;
    }

    public int getPrice() {
        return price;
    }

    public String getGuarant() {
        return guarant;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setGuarant(String guarant) {
        this.guarant = guarant;
    }
}
