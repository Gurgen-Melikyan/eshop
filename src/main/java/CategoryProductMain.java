import manager.CategoryManager;
import manager.ProductManager;
import model.Category;
import model.Product;

import java.util.List;
import java.util.Scanner;

public class CategoryProductMain {
    private static Scanner scanner = new Scanner(System.in);
    private static CategoryManager categoryManager = new CategoryManager();
    private static ProductManager productManager = new ProductManager();

    public static void main(String[] args) {
        boolean isRun = true;

        while (isRun) {
            System.out.println("Please input 0 for exit");
            System.out.println("Please input 1 for addCategory");
            System.out.println("Please input 2 for editCategoryById");
            System.out.println("Please input 3 for deleteCategory");
            System.out.println("Please input 4 for addProduct");
            System.out.println("Please input 5 for editProductById");
            System.out.println("Please input 6 for deleteProductById");
            System.out.println("Please input 7 for printSumOfProducts");
            System.out.println("Please input 8 for printMaxOfPriceProduct");
            System.out.println("Please input 9 for printMinOfPriceProduct");
            System.out.println("Please input 10 for printAvgOfPriceProduct");
            String command = scanner.nextLine();
            switch (command) {
                case "0":
                    isRun = false;
                    break;
                case "1":
                    addCategory();
                    break;
                case "2":
                    editCategoryById();
                    break;
                case "3":
                    deleteCategory();
                    break;
                case "4":
                    addProduct();
                    break;
                case "5":
                    editProductById();
                    break;
                case "6":
                    deleteProductById();
                    break;
                case "7":
                    printSumOfProducts();
                    break;
                case "8":
                    printMaxOfPriceProduct();
                    break;
                case "9":
                    printMinOfPriceProduct();
                    break;
                case "10":
                   printAvgOfPriceProduct();
                   break;
                default:
                    System.out.println("Invalid Command");
            }
        }
    }

    private static void printAvgOfPriceProduct() {
        System.out.println(productManager.getAvgOfPrice());
    }

    private static void printMinOfPriceProduct() {
        System.out.println(productManager.getMinOfPrice());
    }

    private static void printMaxOfPriceProduct() {
        int maxOfPrice = productManager.getMaxOfPrice();
        System.out.println(maxOfPrice);
    }

    private static void printSumOfProducts() {
        productManager.printSum();
    }

    private static void deleteProductById() {
        List<Product> all = productManager.getAll();
        for (Product product : all) {
            System.out.println(product);
        }
        System.out.println("please choose category id");
        int id = Integer.parseInt(scanner.nextLine());
        productManager.removeById(id);
        System.out.println("Product removed");
    }

    private static void editProductById() {
        List<Product> all = productManager.getAll();
        for (Product product : all) {
            System.out.println(product);
        }
        System.out.println("please choose category id");
        int id = Integer.parseInt(scanner.nextLine());
        List<Product> products = productManager.editProductIdById(id);
        for (Product product : products) {
            System.out.println(product);
        }

    }

    private static void addProduct() {
        List<Category> all = categoryManager.getAll();
        for (Category category : all) {
            System.out.println(category);
        }
        System.out.println("please choose category id");
        int id = Integer.parseInt(scanner.nextLine());
        Category category = categoryManager.getById(id);
        if (category != null) {
            System.out.println("Please input product name,Description,Price,Quantity");
            String productStr = scanner.nextLine();
            String[] productData = productStr.split(",");
            Product product = new Product();
            product.setCategory(category);
            product.setName(productData[0]);
            product.setDescription(productData[1]);
            product.setPrice(Integer.parseInt(productData[2]));
            product.setQuantity(Integer.parseInt(productData[3]));
            productManager.save(product);
        }
    }

    private static void deleteCategory() {
        System.out.println("please choose category id");
        int id = Integer.parseInt(scanner.nextLine());
        categoryManager.removeById(id);
        System.out.println("Category removed");
    }

    private static void editCategoryById() {
        System.out.println("please choose category id");
        int id = Integer.parseInt(scanner.nextLine());
        List<Category> categoryList = categoryManager.editCategoryById(id);
        for (Category category : categoryList) {
            System.out.println(category);
        }
    }


    private static void addCategory() {
        System.out.println("Please input category name");
        String categoryStr = scanner.nextLine();
        Category category = new Category();
        category.setName(categoryStr);
        categoryManager.save(category);
    }
}
