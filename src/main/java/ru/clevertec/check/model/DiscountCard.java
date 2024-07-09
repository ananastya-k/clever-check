package main.java.ru.clevertec.check.model;

import java.util.Map;

/**
 * Represents a discount card with fields for id, number, and discount percentage.
 *
 */
public class DiscountCard {
    private int id;
    private int number;
    private int discountPercentage = 0;

    /**
     * Constructs a DiscountCard object with specified id, number, and discount.
     *
     * @param id         The unique identifier of the discount card.
     * @param number     The number of the discount card.
     * @param discountPercentage   The discount amount associated with the card.
     */
    public DiscountCard(int id, int number, int discountPercentage){
        this.id = id;
        this.number = number;
        this.discountPercentage = discountPercentage;
    }

    /**
     * Constructs a DiscountCard object using data from a map.
     *
     * @param map       A map containing "id", "number", and "discount_amount" keys.
     */
    public DiscountCard(Map<String,String> map){
        this.id = Integer.parseInt(map.get("id"));
        this.number = Integer.parseInt(map.get("number"));
        this.discountPercentage = Integer.parseInt(map.get("discount_amount"));
    }

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {this.number = number;}
    public int getDiscountPercentage() {return discountPercentage;}
    public void setDiscountPercentage(int discountPercentage) {this.discountPercentage = discountPercentage;}

    @Override
    public String toString() {
        return String.format("%-20s\n%-15s\t%-15s\t%-15s\n%-15s\t%-15s\t%-15s\n",
                "Discount Card", "Id","Number", "Discount Percentage",
                id, number, discountPercentage);
    }
}
