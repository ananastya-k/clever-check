package main.java.ru.clevertec.check.model.loaders;

import main.java.ru.clevertec.check.model.DiscountCard;
import java.io.IOException;
import java.util.Map;

/**
 * DiscountLoader is a specialized DataLoader for loading discount cards from a file.
 * Inherits from DataLoader and uses the header to create DiscountCard objects from each line.
 */
public class DiscountCardsLoader extends DataLoader{

    /**
     * Constructor
     *
     * @param path           the path to the file
     * @param delimiter      the delimiter used in the file
     * @throws IOException   if an I/O error occurs
     */
    public DiscountCardsLoader(String path, String delimiter) throws IOException {
        super(path,delimiter);
    }

    /**
     * Loads discount cards from the file into the provided map.
     *
     * @param discounts     the map to load discounts into
     * @throws IOException  if an I/O error occurs
     */
    public void loadDiscount( Map<Integer, DiscountCard> discounts) throws IOException {

        Map<String,String> discountMap;

        while ((discountMap = this.nextMap()) != null) {
            DiscountCard d = new DiscountCard(discountMap);
            discounts.put(d.getNumber(),d);
        }
    }

}
