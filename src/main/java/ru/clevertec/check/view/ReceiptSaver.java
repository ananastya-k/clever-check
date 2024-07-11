package main.java.ru.clevertec.check.view;

import main.java.ru.clevertec.check.model.DiscountCard;
import main.java.ru.clevertec.check.model.Goods;
import main.java.ru.clevertec.check.model.Order;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
/**
 *  Class for saving a receipt (to CSVFile)
 */

public class ReceiptSaver {

    private static final String[] META_HEADER = {"Date", "Time"};
    private static final String[] BODY_HEADER = {"QTY", "DESCRIPTION", "PRICE", "DISCOUNT", "TOTAL"};
    private static final String[] DISCOUNT_SECTION_HEADER = {"DISCOUNT CARD", "DISCOUNT PERCENTAGE"};
    private static final String[] FOOTER_HEADER = {"TOTAL PRICE", "TOTAL DISCOUNT", "TOTAL WITH DISCOUNT"};

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#0.00");
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    private Path path;
    private final String CSVDelimiter = ";";

    /**
     * Generates and saves the receipt.
     *
     * @param receipt the receipt to generate
     * @param filePath the file path to save the receipt
     * @throws IOException if there is an IO error
     */
    public void generateCheck(Receipt receipt, String filePath) throws IOException {
        this.path = Path.of(filePath);
        Files.write(path, new ArrayList<>(), StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        composeCheck(receipt);
    }

    /**
     * Composes the receipt by creating its different sections.
     *
     * @param receipt the receipt to compose
     * @throws IOException if there is an IO error
     */
    public void composeCheck(Receipt receipt) throws IOException {
        createHeader();
        createBody(receipt.getOrder());
        createDiscountSection(receipt.getDiscountCard());
        createFooter(receipt.getOrder());
    }

    /**
     * Creates the header section of the receipt.
     *
     * @throws IOException if there is an IO error
     */
    private void createHeader() throws IOException {
        LocalDateTime now = LocalDateTime.now();
        List<String> lines = new ArrayList<>();
        lines.add(concat(META_HEADER));
        lines.add(concat(new String[]{now.format(dateFormatter), now.format(timeFormatter)}));
        Files.write(path, lines, StandardCharsets.UTF_8, StandardOpenOption.APPEND);
    }

    /**
     * Creates the body section of the receipt.
     *
     * @param order the order to include in the receipt
     * @throws IOException if there is an IO error
     */
    private void createBody(Order order) throws IOException {
        List<String> lines = new ArrayList<>();
        lines.add(concat(BODY_HEADER));
        order.getGoodsList().forEach(item -> addItemToList(item, lines));
        Files.write(path, lines, StandardCharsets.UTF_8, StandardOpenOption.APPEND);
    }

    /**
     * Creates the discount section of the receipt.
     *
     * @param discountCard the discount card to include in the receipt
     * @throws IOException if there is an IO error
     */
    private void createDiscountSection(DiscountCard discountCard) throws IOException {
        if (discountCard != null) {
            List<String> lines = new ArrayList<>();
            lines.add(concat(DISCOUNT_SECTION_HEADER));
            addDiscountToList(discountCard, lines);
            Files.write(path, lines, StandardCharsets.UTF_8, StandardOpenOption.APPEND);
        }
    }

    /**
     * Creates the footer section of the receipt.
     *
     * @param order the order to include in the receipt
     * @throws IOException if there is an IO error
     */
    private void createFooter(Order order) throws IOException {
        List<String> lines = new ArrayList<>();
        lines.add(concat(FOOTER_HEADER));
        lines.add(String.join(CSVDelimiter,
                formatPrice(order.getTotalPrice()) + "$",
                formatPrice(order.getTotalPrice() - order.getTotalWithDiscount()) + "$",
                formatPrice(order.getTotalWithDiscount()) + "$"));
        Files.write(path, lines, StandardCharsets.UTF_8, StandardOpenOption.APPEND);
    }

    // Helper methods for adding items and formatting

    private void addDiscountToList(DiscountCard card, List<String> lines) {
        lines.add(String.join(CSVDelimiter,
                String.valueOf(card.getNumber()),
                String.valueOf(card.getDiscountPercentage()) + "%"
        ));
    }

    private void addItemToList(Goods item, List<String> lines) {
        lines.add(String.join(CSVDelimiter,
                String.valueOf(item.getAmount()),
                item.getDescription(),
                formatPrice(item.getPrice()) + "$",
                formatPrice(item.getTotalPrice() - item.getPrice()) + "$",
                formatPrice(item.getTotalPrice()) + "$"
        ));
    }

    private String formatPrice(double price) {
        return DECIMAL_FORMAT.format(price);
    }

    private String concat(String[] elements) {
        return String.join(CSVDelimiter, elements);
    }
}