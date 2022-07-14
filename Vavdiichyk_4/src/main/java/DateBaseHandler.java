import tabel_classes.Category;
import tabel_classes.Product;

import java.sql.*;

public class DateBaseHandler extends Configs {
   private Connection dbConnection;

    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionStr = "jdbc:mysql://" + dbHost + ":" + dbPort +"/" + dbName + "?useUnicode=true&characterEncoding=UTF-8";
        Class.forName("com.mysql.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connectionStr, dbUser, dbPassword);

        return dbConnection;
    }

    public void addCategory(Category category){
        String insert = "INSERT INTO " + Const.CATEGORY_TABLE +
                "(" + Const.CATEGORY_ID +","+ Const.CATEGORY_NAME + ")"
                + "VALUES (?,?)";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setInt(1, category.getCategory_number());
            prSt.setString(2, category.getCategory_name());
            prSt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getAllCategory(){
        ResultSet resultSet = null;

        String select = "SELECT * FROM " + Const.CATEGORY_TABLE + " ORDER BY " + Const.CATEGORY_NAME;

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            resultSet = prSt.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public void updateCategory(Category category){
        String update = "UPDATE " + Const.CATEGORY_TABLE + " SET " +
                Const.CATEGORY_NAME +" = ?"  + " WHERE " + Const.CATEGORY_ID + " = ?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(update);
            prSt.setString(1, category.getCategory_name());
            prSt.setInt(2,category.getCategory_number());
            prSt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void deleteCategory(Category category) throws SQLException, ClassNotFoundException {
        String delete = "DELETE FROM "+ Const.CATEGORY_TABLE +" WHERE " + Const.CATEGORY_NAME + " = ?";
        PreparedStatement prSt = getDbConnection().prepareStatement(delete);
        prSt.setString(1,category.getCategory_name());
        prSt.executeUpdate();
    }

    public ResultSet getCategoryByName(String name){
        ResultSet resultSet = null;

        String select = "SELECT * FROM " + Const.CATEGORY_TABLE + " WHERE " + Const.CATEGORY_NAME + " = ?";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1,name);
            resultSet = prSt.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resultSet;
    }


    public  void addProduct(Product product){
        String insert = "INSERT INTO " + Const.PRODUCT_TABLE +
                "(" + Const.PRODUCT_NAME +","+ Const.PRODUCT_PRICE +","+ Const.PRODUCT_NUMBER + ","+ Const.PRODUCT_CATEGORY_ID + ")"
                + "VALUES (?,?,?,?)";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, product.getProduct_name());
            prSt.setBigDecimal(2, product.getProduct_price());
            prSt.setInt(3, product.getProduct_number());
            prSt.setInt(4, product.getProduct_category_number());
            prSt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public ResultSet getAllProducts(){
        ResultSet resultSet = null;

        String select = "SELECT * FROM " + Const.PRODUCT_TABLE + " ORDER BY " + Const.PRODUCT_NAME;;

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            resultSet = prSt.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public void updateProduct(Product product, String column){
        String update = "UPDATE " + Const.PRODUCT_TABLE + " SET " +
                column +" = ?"  + " WHERE " + Const.PRODUCT_ID + " = ?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(update);
            prSt.setInt(2,product.getId_product());
            if (column.equals(Const.PRODUCT_CATEGORY_ID)) {
                prSt.setInt(1,product.getProduct_category_number());
            }else if(column.equals(Const.PRODUCT_NAME)) {
                prSt.setString(1, product.getProduct_name());
            }else if(column.equals(Const.PRODUCT_PRICE)) {
                prSt.setBigDecimal(1, product.getProduct_price());
            }else if(column.equals(Const.PRODUCT_NUMBER)) {
                prSt.setInt(1, product.getProduct_number());
            }else{
                throw new RuntimeException("wrong column");
            }
            prSt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void deleteProduct(Product product) throws SQLException, ClassNotFoundException {
        String delete = "DELETE FROM "+ Const.PRODUCT_TABLE +" WHERE " + Const.PRODUCT_ID + " = ?";
        PreparedStatement prSt = getDbConnection().prepareStatement(delete);
        prSt.setInt(1,product.getId_product());
        prSt.executeUpdate();
    }

    public ResultSet getProductByName(String name){
        ResultSet resultSet = null;

        String select = "SELECT * FROM " + Const.PRODUCT_TABLE + " WHERE " + Const.PRODUCT_NAME + " = ?";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1,name);
            resultSet = prSt.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resultSet;
    }


    public ResultSet getProductsByCategory(int category_id){
        ResultSet resultSet = null;

        String select = "SELECT * FROM " + Const.PRODUCT_TABLE + " WHERE " + Const.PRODUCT_CATEGORY_ID + " = ?";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setInt(1,category_id);
            resultSet = prSt.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

}
