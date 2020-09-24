package dns.objects;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    private List<Product> listPoducts = new ArrayList<>();

    public List<Product> getListPoducts() {
        return listPoducts;
    }

    public void add(Product product){
        listPoducts.add(product);
    }

    public void remove(Product product){
        listPoducts.remove(product);
    }
}
