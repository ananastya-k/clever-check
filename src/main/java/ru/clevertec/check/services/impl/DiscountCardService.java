package main.java.ru.clevertec.check.services.impl;

import main.java.ru.clevertec.check.model.DiscountCard;
import main.java.ru.clevertec.check.model.loaders.DiscountCardsLoader;
import main.java.ru.clevertec.check.services.IDiscountCardService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

/**
 * Service class for managing discount cards.
 */
public class DiscountCardService implements IDiscountCardService {

    // Repository for storing discount cards, keyed by card number
    private Map<Integer, DiscountCard> discountCardsRepository = new HashMap<>();
    private final String path;
    private final int defaultPrecentage = 2;

    public DiscountCardService(String path)  {
        this.path = path;
    }

    @Override
    public void addDiscountCard(DiscountCard discountCard) {
        discountCardsRepository.put(discountCard.getNumber(), discountCard);
    }

    @Override
    public Optional<DiscountCard> getDiscountByNumber(int number) {
        return number == 0
                ? Optional.empty()
                : Optional.of(discountCardsRepository.getOrDefault(number,
                new DiscountCard((new Random()).nextInt(255),number, defaultPrecentage)));
    }

    @Override
    public void updateDiscount(DiscountCard discountCard) {
        discountCardsRepository.put(discountCard.getNumber(), discountCard);
    }

    @Override
    public void load() throws IOException {
        try (DiscountCardsLoader loader = new DiscountCardsLoader(path, ";")) {
            loader.loadDiscount(discountCardsRepository);
        } catch (IOException e) {
            throw new IOException("Data loading error: " + e);
        }
    }
}
