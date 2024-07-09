package main.java.ru.clevertec.check.services;

import main.java.ru.clevertec.check.model.DiscountCard;

import java.io.IOException;
import java.util.Optional;

public interface IDiscountCardService {
     /**
      * Adds a discount card to the repository.
      *
      * @param discountCard the discount card to add
      */
     void addDiscountCard(DiscountCard discountCard);

     /**
      * Retrieves a discount card by its number.
      *
      * @param number the number of the discount card
      * @return an Optional containing the discount card if found, or empty if not found
      */
     Optional<DiscountCard> getDiscountByNumber(int number);

     /**
      * Updates the discount card in the repository.
      *
      * @param discountCard the discount card to update
      */
     void updateDiscount(DiscountCard discountCard);

     /**
      * Loads discount cards from a file into the repository.
      *
      * @throws IOException if an I/O error occurs during loading
      */
     void load() throws IOException;

}
