import java.util.List;

public class Storage {
    private List<Product> products;
    private List<ProductGroup> groups;

    public Storage(List<Product> products){
        this.products = products;
    }

    public Product product(int id){
        for(int i = 0; i<products.size(); i++){
            if(products.get(i).getId() == id){
                return products.get(i);
            }
        }
        throw new RuntimeException("Wrong product id");
    }

    public ProductGroup group(int id){
        for(int i = 0; i<products.size(); i++){
            if(groups.get(i).getId() == id){
                return groups.get(i);
            }
        }
        throw new RuntimeException("Wrong group id");
    }

    public void addProduct(Product product){
        this.products.add(product);
    }

    public void addProduct(int id, int price, int quantity){
        this.products.add(new Product(id,price,quantity));
    }
}
