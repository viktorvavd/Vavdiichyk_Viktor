package product;

public class Product {
    private int id;
    private int price;
    private int quantity;


    public Product(int id, int price, int quantity){
        this.id = id;
        this.price = price;
        this.quantity = quantity;
    }

    public Product(){}

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    public void increaseQuantity(int value) {
        this.quantity += value ;
    }

    public void reduceQuantity(int value) {
        this.quantity -= value ;
    }

    public int getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "product.Product{" +
                "id=" + id +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
