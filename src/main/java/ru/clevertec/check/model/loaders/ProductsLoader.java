package main.java.ru.clevertec.check.model.loaders;

import main.java.ru.clevertec.check.model.Product;

import java.io.IOException;
import java.util.Map;

/**
 * ProductsLoader is a specialized DataLoader for loading products from a file.
 * Inherits from DataLoader and uses the header to create Product objects from each line.
 */
public class ProductsLoader extends DataLoader{

    /**
     * Constructor
     *
     * @param path          the path to the file
     * @param delimiter     the delimiter used in the file
     * @throws IOException  if an I/O error occurs
     */
    public ProductsLoader(String path, String delimiter) throws IOException {
        super(path,delimiter);
    }
    /**
     * Loads products from the file into the provided map.
     *
     * @param products      the map to load products into
     * @throws IOException  if an I/O error occurs
     */
    public void loadProducts( Map<Integer, Product> products) throws IOException {

        Map<String,String> productMap;

        while ((productMap = this.nextMap()) != null) {
            Product p = new Product(productMap);
            products.put(p.getId(),p);
        }
    }

}

