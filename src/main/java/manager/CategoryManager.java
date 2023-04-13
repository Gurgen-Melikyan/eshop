package manager;

import db.DBConnectionProvider;
import model.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryManager {
    private Connection connection = DBConnectionProvider.getInstance().getConnection();

    public List<Category> getAll() {
        List<Category> companyList = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from product");
            while (resultSet.next()) {
                companyList.add(getCategoryFromResultSet(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return companyList;
    }

    public void save(Category category) {
        String sql = "INSERT INTO category(name) VALUES(?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, category.getName());
            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                category.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeById(int categoryId) {
        String sql = "DELETE FROM category where id = " + categoryId;
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Category> editCategoryById(int categoryId) {
        List<Category> categoryList = new ArrayList<>();
        String sql = "select * from category where id >=" + categoryId;
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                categoryList.add(getCategoryFromResultSet(resultSet));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categoryList;
    }
    public Category getById(int id) {

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("select * from category where id = " + id);
            if (resultSet.next()) {
                return getCategoryFromResultSet(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Category getCategoryFromResultSet(ResultSet resultSet) throws SQLException {
        Category category = new Category();
        category.setId(resultSet.getInt(1));
        category.setName(resultSet.getString(2));
        return category;
    }
}
