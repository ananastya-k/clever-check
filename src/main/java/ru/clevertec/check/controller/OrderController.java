package main.java.ru.clevertec.check.controller;

import main.java.ru.clevertec.check.exceptions.BadRequestException;
import main.java.ru.clevertec.check.model.Goods;
import main.java.ru.clevertec.check.model.Order;
import main.java.ru.clevertec.check.model.Product;
import main.java.ru.clevertec.check.services.impl.ProductService;

import java.util.ArrayList;
import java.util.List;
/**
 * Controller for managing orders, applying discounts, and creating order objects.
 */
public class OrderController {
    private final int WHOLESALE_DISCOUNT = 10;
    private final int QTY_FOR_WHOLESALE_DISCOUNT = 5;

    Parameters parameters;
    int currentDiscount = 0;

    /**
     * Constructor to initialize parameters.
     *
     * @param parameters the parameters for the order
     */
    public OrderController(Parameters parameters){
        this.parameters = parameters;
    }

    /**
     * Creates an order with the given product service and discount percentage.
     *
     * @param ps the product service
     * @param discountPrecentage the discount percentage
     * @return the created order
     * @throws BadRequestException if there is a problem with the request
     */
    public Order createOrder(ProductService ps, int discountPrecentage) throws BadRequestException{

        this.currentDiscount = discountPrecentage;
        List<Goods> itemsToAdd = assemblyGoods(ps);
        return assemblyOrder(itemsToAdd);
    }

    /**
     * Assembles an order from a list of goods.
     *
     * @param items the list of goods
     * @return the assembled order
     */
    private Order assemblyOrder(List<Goods> items){

        Order order = new Order();
        order.setCurrentBalance(parameters.balanceDebitCard);
        items.forEach(item-> {
            order.increaseTotalWithDiscount(item.getTotalWithDiscount());
            order.increaseTotalPrice(item.getTotalPrice());}
        );
        order.setGoodsList(items);

        return order;
    }

    /**
     * Assembles a list of goods from the product service.
     *
     * @param ps the product service
     * @return the list of goods
     * @throws BadRequestException if there is a problem with the request
     */
    private List<Goods> assemblyGoods(ProductService ps) throws BadRequestException {

        List<Goods> itemsToAdd = new ArrayList<>();

        for (Integer key : parameters.selectedProducts.keySet()) {
            Product product = ps.getProductById(key).orElseThrow(() -> new BadRequestException("Product with id: " + key + " not found"));

            if (product.getQuantity() < parameters.selectedProducts.get(key)) {
                throw new BadRequestException("Insufficient quantity for product with id: " + key + ". There are " + product.getQuantity() + " in stock");
            }

            Goods item = createGoods(product, parameters.selectedProducts.get(key));
            itemsToAdd.add(item);
        }
        return itemsToAdd;
    }


    /**
     * Creates a goods item from a product and quantity.
     *
     * @param product the product
     * @param quantity the quantity
     * @return the created goods item
     */
    private Goods createGoods(Product product, int quantity) {

        Goods item = new Goods(product, quantity);

        if (item.isWholesale() && (quantity >= QTY_FOR_WHOLESALE_DISCOUNT)) {
            item.setDiscountPercentage(WHOLESALE_DISCOUNT);
        } else {
            item.setDiscountPercentage(currentDiscount);
        }

        return item;
    }

}
