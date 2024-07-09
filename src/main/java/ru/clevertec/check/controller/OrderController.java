package main.java.ru.clevertec.check.controller;

import main.java.ru.clevertec.check.exceptions.BadRequestException;
import main.java.ru.clevertec.check.model.Goods;
import main.java.ru.clevertec.check.model.Order;
import main.java.ru.clevertec.check.model.Product;
import main.java.ru.clevertec.check.services.impl.ProductService;

import java.util.ArrayList;
import java.util.List;

public class OrderController {
    private final int WHOLESALE_DISCOUNT = 10;
    private final int QTY_FOR_WHOLESALE_DISCOUNT = 5;

    AppController.Parameters parameters;
    int currentDiscount = 0;


    public OrderController(AppController.Parameters parameters){
        this.parameters = parameters;
    }
    public Order createOrder(ProductService ps, int discountPrecentage) throws BadRequestException{

        this.currentDiscount = discountPrecentage;
        List<Goods> itemsToAdd = assemblyGoods(ps);
        return assemblyOrder(itemsToAdd);
    }

    private List<Goods> assemblyGoods(ProductService ps) throws BadRequestException {

        List<Goods> itemsToAdd = new ArrayList<>();

        for (Integer key : parameters.selectedProducts.keySet()) {

            Product product = ps.getProductById(key)
                    .orElseThrow(() -> new BadRequestException("Product с id: " + key + " not found"));

            if (product.getQuantity() < parameters.selectedProducts.get(key)) {
                throw new BadRequestException("Insufficient quantity for product with id: "
                        + key + ". There are " + product.getQuantity() + " in stock");
            }

            Goods item = createGoods(product, parameters.selectedProducts.get(key));
            itemsToAdd.add(item);
        }
        return itemsToAdd;
    }
    private Goods createGoods(Product product, int quantity) {

        Goods item = new Goods(product, quantity);
        if (item.isWholesale() && quantity >= QTY_FOR_WHOLESALE_DISCOUNT) {
            item.setDiscountPercentage(WHOLESALE_DISCOUNT);
        } else {
            item.setDiscountPercentage(currentDiscount);
        }
        return item;
    }

    private Order assemblyOrder(List<Goods> items){

        Order order = new Order();
        order.setCurrentBalance(parameters.balanceDebitCard);
        items.stream().forEach(item-> {
            order.increaseTotalWithDiscount(item.getTotalWithDiscount());
            order.increaseTotalPrice(item.getTotalPrice());}
        );
        order.setGoodsList(items);

        return order;
    }
}
