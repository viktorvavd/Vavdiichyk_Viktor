import org.junit.Test;
import tabel_classes.Category;
import tabel_classes.Product;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class testDB {
    @Test
    public void shouldFindCreatedProduct(){
        DateBaseHandler handler = new DateBaseHandler();

        Category category = new Category();
        category.setCategory_number(1);
        category.setCategory_name("Milk products");

        handler.addCategory(category);

        Product product = new Product();
        product.setProduct_name("Milk");
        product.setProduct_price(new BigDecimal(29.90));
        product.setProduct_number(30);
        product.setProduct_category_number(1);

        handler.addProduct(product);
        ResultSet resultSet1 = handler.getProductByName("Milk");
        ResultSet resultSet2 = handler.getCategoryByName("Milk products");

        try {
            resultSet1.next();
            resultSet2.next();
            int number = resultSet1.getInt(Const.PRODUCT_NUMBER);
            if(number != product.getProduct_number()){
                throw new Exception("product is not in DB");
            }
            if(!resultSet2.getString(Const.CATEGORY_NAME).equals(category.getCategory_name())){
                throw new Exception("category is not in DB");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldUpdate(){
        DateBaseHandler handler = new DateBaseHandler();

        Category category = new Category();
        category.setCategory_number(1);
        category.setCategory_name("Fruits");

        Product product = new Product();
        product.setProduct_name("Apples");
        product.setProduct_price(new BigDecimal(29.90));
        product.setProduct_number(30);
        product.setProduct_category_number(1);


        handler.updateCategory(category);
        handler.updateProduct(product, Const.PRODUCT_NAME);

        ResultSet resultSet1 = handler.getProductByName("Milk");
        ResultSet resultSet2 = handler.getCategoryByName("Milk products");

        try {
            if(resultSet1.next()){
                throw new Exception("product is not updated");
            }
            if(resultSet2.next()){
                throw new Exception("category is not updated");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Test
    public void shouldBeDeleted() throws SQLException, ClassNotFoundException {
        DateBaseHandler handler = new DateBaseHandler();

        Category category = new Category();
        category.setCategory_number(1);
        category.setCategory_name("Milk products");

        Product product = new Product();
        product.setProduct_name("Milk");
        product.setProduct_price(new BigDecimal(29.90));
        product.setProduct_number(30);
        product.setProduct_category_number(1);

        handler.deleteProduct(product);         //спочатку видаляється продук, бо під час видалення категорії
        handler.deleteCategory(category); // його теж буде видаленно (CASCADE)

        ResultSet resultSet1 = handler.getProductByName("Milk");
        ResultSet resultSet2 = handler.getCategoryByName("Milk products");

        try {
            if(resultSet1.next()){
                throw new Exception("product is not deleted");
            }
            if(resultSet2.next()){
                throw new Exception("category is not deleted");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
