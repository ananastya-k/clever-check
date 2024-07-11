package main.java.ru.clevertec.check.view;

import main.java.ru.clevertec.check.model.*;

public class Receipt {
    private Order order;
    private double balance;
    private DiscountCard discountCard;

    private Receipt(Builder builder){
        this.order = builder.order;
        this.discountCard = builder.discountCard;
        this.balance = builder.balance;
    }

    public static class Builder {
        private Order order;
        private DiscountCard discountCard;
        private double balance;

        public Builder setOrder(Order order) {
            this.order = order;
            return this;
        }

        public Builder setDiscountCard(DiscountCard discountCard) {
            this.discountCard = discountCard;
            return this;
        }

        public Builder setBalanceDebitCard(double balanceDebitCard) {
            this.balance = balanceDebitCard;
            return this;
        }

        public Receipt build() {
            return new Receipt(this);
        }
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
