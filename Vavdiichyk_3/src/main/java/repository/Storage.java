package repository;

import product.Product;
import product.ProductGroup;

import java.util.List;

public class Storage {
    private List<Product> products;
    private List<ProductGroup> groups;

    private int commandId = 1;

    public Storage(List<Product> products){
        this.products = products;
    }

    public Storage(){}


    public int getState() { return commandId; }

    public Product getProduct(int id){
        commandId = 4;
        for(int i = 0; i<products.size(); i++){
            if(products.get(i).getId() == id){
                return products.get(i);
            }
        }
        throw new RuntimeException("Wrong product id");
    }

    public ProductGroup getGroup(int id){
        commandId = 1;
        for(int i = 0; i<products.size(); i++){
            if(groups.get(i).getId() == id){
                return groups.get(i);
            }
        }
        throw new RuntimeException("Wrong group id");
    }

    public void addProduct(Product product){
        commandId = 2;
        this.products.add(product);
    }

    public void addProduct(int id, int price, int quantity){
        commandId = 2;
        this.products.add(new Product(id,price,quantity));
    }

    public void addGroup(ProductGroup group){
        commandId = 3;
        this.groups.add(group);
    }

    public void addGroup(int id, int[] productsId){
        commandId = 3;
        this.groups.add(new ProductGroup(id,productsId));
    }



    public void command1(){
        System.out.println("Command 1");
        this.commandId = 2;
    }

    public void command2(){
        System.out.println("Command 2");
        this.commandId = 3;
    }

    public void command3(){
        System.out.println("Command 3");
        this.commandId = 4;
    }

    public void command4(){
        System.out.println("Command 4");
        this.commandId = 1;
    }
}
