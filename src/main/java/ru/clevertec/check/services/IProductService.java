package main.java.ru.clevertec.check.services;

import main.java.ru.clevertec.check.model.Product;
import main.java.ru.clevertec.check.model.loaders.ProductsLoader;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

public interface IProductService {

    /**
     * Adds a product to the repository.
     *
     * @param p the product to add
     */
    public void addProduct(Product p);


    /**
     * Retrieves a product by its ID.
     *
     * @param id the ID of the product
     * @return an Optional containing the product if found, or empty if not found
     */
    public Optional<Product> getProductById(int id);


    /**
     * Changes the quantity of a product by a specified amount.
     * Decreases the quantity of the product in the repository.
     *
     * @param id the ID of the product
     * @param purchasedGoodsQ the quantity of goods purchased
     * @return an Optional containing the updated product if found, or empty if not found
     */
    public Optional<Product> changeQuantityProduct(int id, int purchasedGoodsQ);


    /**
     * Loads products from a file into the repository.
     *
     * @throws IOException if an I/O error occurs during loading
     */
    public void load() throws IOException ;


    /**
     * Updates the quantity of multiple products in the repository.
     *
     * @param selectedProducts a map containing product IDs and their respective quantities to update
     */
    public void updateQuantityProducts(Map<Integer, Integer> selectedProducts);
}
