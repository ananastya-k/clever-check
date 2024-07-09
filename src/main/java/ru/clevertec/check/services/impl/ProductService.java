package main.java.ru.clevertec.check.services.impl;


import main.java.ru.clevertec.check.model.Product;
import main.java.ru.clevertec.check.model.loaders.ProductsLoader;
import main.java.ru.clevertec.check.services.IProductService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Service class for managing products.
 */
public class ProductService implements IProductService {
    // Repository for storing products, keyed by product ID
    private final Map<Integer, Product> productsRepository = new HashMap<>();
    private final String path;

    /**
     * Constructor.Initializes the path for the product data file.
     *
     * @param path the file path to load products from
     */
    public ProductService(String path) {
        this.path = path;
    }

    @Override
    public void addProduct(Product p){
        productsRepository.put(p.getId(),p);
    }

    @Override
    public Optional<Product> getProductById(int id){
        return Optional.ofNullable(productsRepository.get(id));
    }

    @Override
    public Optional<Product> changeQuantityProduct(int id, int purchasedGoodsQ){

        return Optional.ofNullable(productsRepository.get(id))
                .map(product -> {
                    product.setQuantity(product.getQuantity() - purchasedGoodsQ);
                    return product;
                });
    }

    @Override
    public void load() throws IOException {

        try (ProductsLoader loader = new ProductsLoader(path, ";")){
            loader.loadProducts(productsRepository);
        } catch (IOException e){
            throw new IOException("Data loading error:" + e + ". Path to loading file" + path);
        }
    }

    @Override
    public void updateQuantityProducts(Map<Integer, Integer> selectedProducts) {
        selectedProducts.forEach(this::changeQuantityProduct);
    }
}
