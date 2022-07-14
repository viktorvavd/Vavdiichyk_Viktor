package product;

import product.Product;

import java.util.List;

public class ProductGroup {
    private int id;
    private List<Integer> productsId;

    public ProductGroup(int id, int[] productsId){
        this.id = id;
        for(int i = 0; i< productsId.length; i++) {
            assert false;
            this.productsId.set(i, productsId[i]);
        }
    }

    public ProductGroup(int name, List<Integer> productsId){
        this.id = id;
        this.productsId = productsId;
    }

    public void addProduct(int productId){
        this.productsId.add(productId);
    }

    public void addProduct(Product product){
        this.productsId.add(product.getId());
    }

    public int getId() {
        return id;
    }
}
