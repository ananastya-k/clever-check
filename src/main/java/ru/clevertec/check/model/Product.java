package main.java.ru.clevertec.check.model;

import java.util.Map;

public class Product {
    private int id;
    private String description;
    private double price;
    private int quantity;
    private boolean wholesale;

    /**
     * Constructs a Product object with specified id, description, price, quantity, and wholesale status.
     *
     * @param id          The unique identifier of the product.
     * @param description The description of the product.
     * @param price       The price of the product.
     * @param quantity    The quantity of the product in stock.
     * @param wholesale   Indicates if the product is available for wholesale.
     */
    public Product(int id, String description, double price, int quantity, boolean wholesale){
        this.id = id;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.wholesale = wholesale;
    }

    /**
     * Constructs a Product object using data from a map.
     *
     * @param map A map containing "id", "description", "price", "quantity_in_stock", and "wholesale_product" keys.
     */
    public Product(Map<String,String> map){
        this.id = Integer.parseInt(map.get("id"));
        this.description = map.get("description");
        this.price = Double.parseDouble(map.get("price"));
        this.quantity = Integer.parseInt(map.get("quantity_in_stock"));
        this.wholesale = map.get("wholesale_product").equals("+")
                      || map.get("wholesale_product").equals("true");
    }

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}
    public double getPrice() {return price;}
    public void setPrice(double price) {this.price = price;}
    public int getQuantity() {return quantity;}
    public void setQuantity(int quantity) {this.quantity = quantity;}
    public boolean isWholesale() {return wholesale;}
    public void setWholesale(boolean wholesale) {this.wholesale = wholesale;}
    public void setId(int id) {this.id = id;}
    public int getId() {return id;}

}
