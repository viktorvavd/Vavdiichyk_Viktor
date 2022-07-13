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
