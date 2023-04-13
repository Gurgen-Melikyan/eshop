package manager;

import db.DBConnectionProvider;
import model.Category;
import model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductManager {
    private Connection connection = DBConnectionProvider.getInstance().getConnection();

    private CategoryManager categoryManager = new CategoryManager();

    public List<Product> getAll() {
        List<Product> companyList = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from product");
            while (resultSet.next()) {
                companyList.add(getProductFromResultSet(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return companyList;
    }

    public void save(Product product) {
        try (Statement statement = connection.createStatement()) {
            String sql = "INSERT INTO product(name,description,price,quantity,category_id) VALUES('%s','%s',%d,%d,%d)";
            statement.executeUpdate(String.format(sql, product.getName(), product.getDescription(), product.getPrice(),
                    product.getQuantity(), product.getCategory().getId()), Statement.RETURN_GENERATED_KEYS);
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                product.setId(generatedKeys.getInt(1));
            }
            System.out.println("all ok");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeById(int productId) {
        String sql = "DELETE FROM product where id = " + productId;
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Product> editProductIdById(int productId) {
        List<Product> productList = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * from product where id >=" + productId);
            while (resultSet.next()) {
                productList.add(getProductFromResultSet(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    public void printSum() {
        String sql = "select sum(quantity) from product";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int c = resultSet.getInt(1);
                System.out.println(c);


            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Product getProductFromResultSet(ResultSet resultSet) throws SQLException {
        Product product = new Product();
        product.setId(resultSet.getInt(1));
        product.setName(resultSet.getString(2));
        product.setDescription(resultSet.getString(3));
        product.setPrice(resultSet.getInt(4));
        product.setQuantity(resultSet.getInt(5));
        int categoryId = resultSet.getInt(6);
        product.setCategory(categoryManager.getById(categoryId));
        return product;
    }

    public int getMaxOfPrice() {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("select max(price) from product");
            if (resultSet.next()) {
                int anInt = resultSet.getInt(1);
                return anInt;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getMinOfPrice() {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("select min(price) from product");
            if (resultSet.next()) {
                int anInt = resultSet.getInt(1);
                return anInt;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getAvgOfPrice() {
        try (Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery("select avg(price) from product");
            if (resultSet.next()){
                int anInt = resultSet.getInt(1);
                return anInt;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
