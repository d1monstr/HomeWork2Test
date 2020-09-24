package ru.appline.frameworks.dns.objects;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    private List<Product> listPoducts = new ArrayList<>();
    private Product lastRemovedProduct;
    private static Cart cart;

    public List<Product> getListPoducts() {
        return listPoducts;
    }

    public static Cart getCart(){
        if (cart == null) {
            cart = new Cart();
        }
        return cart;
    }

    public void add(Product product){
        listPoducts.add(product);
    }

    public void remove(Product product){
        listPoducts.remove(product);
        lastRemovedProduct = product;
    }

    public Product getLastRemovedProduct(){
        return lastRemovedProduct;
    }

    public Product getLastAddedProduct(){
        return listPoducts.get(listPoducts.size()-1);
    }

    public int countProduct(){
        return listPoducts.size();
    }

    public int getPriceCart(){
        int sum = 0;
        for (Product product : listPoducts){
            sum += product.getPrice();
        }
        return sum;
    }
}
