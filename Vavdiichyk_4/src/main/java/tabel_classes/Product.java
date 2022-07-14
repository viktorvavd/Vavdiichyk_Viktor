package tabel_classes;

import java.math.BigDecimal;

public class Product {
    private int id_product;
    private int product_category_number;
    private String product_name;
    private int product_number;
    private BigDecimal product_price;



    public Product(){}

    public Product(int id_product, int product_category_number, String product_name, int product_number, BigDecimal product_price) {
        this.id_product = id_product;
        this.product_category_number = product_category_number;
        this.product_name = product_name;
        this.product_number = product_number;
        this.product_price = product_price;
    }

    public Product(int product_category_number, String product_name, int product_number, BigDecimal product_price) {
        this.id_product = id_product;
        this.product_category_number = product_category_number;
        this.product_name = product_name;
        this.product_number = product_number;
        this.product_price = product_price;
    }

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public int getProduct_number() {
        return product_number;
    }

    public void setProduct_number(int product_number) {
        this.product_number = product_number;
    }

    public BigDecimal getProduct_price() {
        return product_price;
    }

    public void setProduct_price(BigDecimal product_price) {
        this.product_price = product_price;
    }

    public int getProduct_category_number() {
        return product_category_number;
    }

    public void setProduct_category_number(int product_category_number) {
        this.product_category_number = product_category_number;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }


}
