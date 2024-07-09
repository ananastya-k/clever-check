package main.java.ru.clevertec.check.view;

import main.java.ru.clevertec.check.model.*;

public class Receipt {
    private Order order;
    private double balance;
    private DiscountCard discountCard;

    public Receipt(Order order, DiscountCard discountCard, double balanceDebitCard) {

        this.order = order;
        this.discountCard = discountCard;
        this.balance = balanceDebitCard;
    }
    public Receipt(Order order, double balanceDebitCard) {

        this.order = order;
        this.balance = balanceDebitCard;

    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public DiscountCard getDiscountCard() {
        return discountCard;
    }

    public void setDiscountCard(DiscountCard discountCard) {
        this.discountCard = discountCard;
    }
    @Override
    public String toString() {
        return "Receipt\n\n" +
                 order.toString() +
                 discountCard.toString() +
                 balance + "$ -> " + order.getCurrentBalance() + "$";
    }

}
