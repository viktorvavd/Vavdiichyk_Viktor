import org.junit.Test;
import java.util.LinkedList;
import java.util.List;


public class Main {
    @Test
    public void shouldHandleType(){
        Product product1 = new Product(1,25,3);
        Product product2 = new Product(2,50,9);
        Product product3 = new Product(3,5,7);

        List<Product> products = new LinkedList<>();
        products.add(product1);
        products.add(product2);
        products.add(product3);
        Storage storage = new Storage(products);

        RandomMessage randomMessage1 = new RandomMessage(3, 10, storage);
        RandomMessage randomMessage2 = new RandomMessage(2, 12, storage);
        RandomMessage randomMessage3 = new RandomMessage(1, 14, storage);
    }

}
