package main.java.ru.clevertec.check.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Represents an Order that holds Goods, with functionality to track total price,
 * total price with discount and current balance.
 */
public class Order {
    private List<Goods> goodsList = new ArrayList<>();
    private double totalPrice = 0;
    private double totalWithDiscount = 0;
    private double currentBalance = 0;


    /**
     * Increases the total price by the given amount.
     *
     * @param price the amount to increase the total price by
     */
    public void increaseTotalPrice(double price) {
        this.totalPrice += price;
    }

    /**
     * Increases the total price with discount by the given amount and
     * decreases the current balance by the same amount.
     *
     * @param price the amount to increase the total price with discount by
     */
    public void increaseTotalWithDiscount(double price) {
        this.totalWithDiscount += price;
        this.currentBalance -= price;
    }


    public void setGoodsList(List<Goods> goodsList) { this.goodsList = goodsList; }
    public List<Goods> getGoodsList() { return goodsList; }
    public double getTotalWithDiscount() { return totalWithDiscount; }
    public void setTotalWithDiscount(double totalDiscount) { this.totalWithDiscount = totalDiscount; }
    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
    public double getCurrentBalance() {return currentBalance; }
    public void setCurrentBalance(double currentBalance) {this.currentBalance = currentBalance;}

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        String separator = "|" + "-".repeat(5) + "|"
                + "-".repeat(30) + "|"
                + "-".repeat(5) + "|"
                + "-".repeat(10) + "|"
                + "-".repeat(10) + "|"
                + "-".repeat(10) + "|"
                + "-".repeat(15) + "|"
                + "-".repeat(10) + "|" + "\n";
        str.append(separator);
        str.append(String.format("|%-5s|%-30s|%-5s|%-10s|%-10s|%-10s|%-15s|%-10s| \n",
                "Id", "Name", "QTY", "Price", "Total", "Discount","With discount", "Stock")).append(separator);
        goodsList.forEach(goods -> {
            str.append(goods.toString()).append(separator);
        });
        str.append(String.format("|%-5s|%-30s|%-5s|%-10s|$%-9.2f|$%-9.2f|$%-14.2f|%-10s| \n",
                "", "", "", "", totalPrice , totalPrice-totalWithDiscount, totalWithDiscount, "")).append(separator);
        return str.toString();
    }
}
