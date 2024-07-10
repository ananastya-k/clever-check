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
        return "\nRECEIPT\n\n" +
                 order.toString() +
                (discountCard == null ? " " : discountCard.toString()) +
                 String.format("\nInitial balance on the card: %.2f$\n\nBalance on the card after payment: %.2f$\n\n", balance, order.getCurrentBalance());
    }

}
