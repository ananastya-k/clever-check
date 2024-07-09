package main.java.ru.clevertec.check.model;

/**
 * Represents goods in the order, inheriting from Product.
 * Contains fields for total price with discount, total price, discount percentage, and amount.
 */
public class Goods extends Product{

    private double totalWithDiscount;
    private double totalPrice;
    private int discountPercentage;
    private int amount;

    /**
     * Constructor to create an item on order based on an existing product and specified amount.
     *
     * @param p             The product from which the basket item is created.
     * @param amount        The quantity of the item in the basket.
     */
    public Goods(Product p, int amount) {
        super(p.getId(), p.getDescription(), p.getPrice(), p.getQuantity(), p.isWholesale());
        this.amount = amount;
        this.totalPrice = p.getPrice()*amount;
        this.totalWithDiscount = this.totalPrice;
        this.discountPercentage = 0;
    }

    public int getDiscountPercentage() {return discountPercentage;}
    public void setDiscountPercentage(int discountPercentage) {
        this.discountPercentage = discountPercentage;
        this.totalWithDiscount = totalPrice *(100 - discountPercentage)/100;
    }
    public int getAmount() {return amount;}
    public void setAmount(int amount) {
        this.amount = amount;
        this.totalPrice = super.getPrice() * amount;
        this.totalWithDiscount = totalPrice *(100 - discountPercentage)/100;

    }
    public double getTotalWithDiscount() {return totalWithDiscount;}
    public void setTotalWithDiscount(double price) {this.totalWithDiscount = price;}
    public double getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return String.format("%-5s | %-30s | %-5d | %-10.2f$ | %-10.2f$ | %-10.2f$ | %-15.2f$ | %-10d | \n",
                        this.getId(), this.getDescription(), amount, this.getPrice(),
                        totalPrice, totalPrice - totalWithDiscount, totalWithDiscount, this.getQuantity() - amount);

    }
}