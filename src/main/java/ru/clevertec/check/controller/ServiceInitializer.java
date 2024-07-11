package main.java.ru.clevertec.check.controller;

import main.java.ru.clevertec.check.services.impl.DiscountCardService;
import main.java.ru.clevertec.check.services.impl.ProductService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Initializes and processes the required services for the application.
 */
public class ServiceInitializer {
    private ProductService productService;
    private DiscountCardService discountCardService;
    private final String PATH_TO_CARDS_FILE = "./src/main/resources/discountCards.csv";
    private final String PATH_TO_PRODUCT_FILE = "./src/main/resources/products.csv";


    /**
     * Processes the services by checking file existence and loading the data.
     *
     * @throws IOException if there is an IO error
     */
    public void processServices() throws IOException{

            checkFileExistence(PATH_TO_PRODUCT_FILE);
            checkFileExistence(PATH_TO_CARDS_FILE);

            productService = new ProductService(PATH_TO_PRODUCT_FILE);
            productService.load();

            discountCardService = new DiscountCardService(PATH_TO_CARDS_FILE);
            discountCardService.load();

    }

    /**
     * Checks if a file exists at the given path.
     *
     * @param path the path to the file
     * @throws IOException if the file does not exist
     */
    private void checkFileExistence(String path) throws IOException {
        if (!Files.exists(Path.of(path))) {
            throw new IOException("File not found: " + path);
        }
    }

    public ProductService getProductService() {
        return productService;
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    public DiscountCardService getDiscountCardService() {
        return discountCardService;
    }

    public void setDiscountCardService(DiscountCardService discountCardService) {
        this.discountCardService = discountCardService;
    }
}
