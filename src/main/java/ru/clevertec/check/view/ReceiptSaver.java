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

public class ReceiptSaver {

    private static final String[] META_HEADER = {"Date", "Time"};
    private static final String[] BODY_HEADER = {"QTY", "DESCRIPTION", "PRICE", "DISCOUNT", "TOTAL"};
    private static final String[] DISCOUNT_SECTION_HEADER = {"DISCOUNT CARD", "DISCOUNT PERCENTAGE"};
    private static final String[] FOOTER_HEADER = {"TOTAL PRICE", "TOTAL DISCOUNT", "TOTAL WITH DISCOUNT"};

    private List<String> components = new ArrayList<>();
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#0.00");
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    private Path path;
    private final String CSVDelimiter = ";";

    public void generateCheck(Receipt receipt, String filePath) throws IOException {
        // Устанавливаем путь к файлу
        this.path = Path.of(filePath);
        // Создаем новый файл, если он не существует, и очищаем его
        Files.write(path, new ArrayList<>(), StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        // Формируем чек и записываем его по частям
        composeCheck(receipt);
    }


    public void composeCheck(Receipt receipt) throws IOException {
        createHeader();
        createBody(receipt.getOrder());
        createDiscountSection(receipt.getDiscountCard());
        createFooter(receipt.getOrder());
    }

    private void createHeader() throws IOException {
        LocalDateTime now = LocalDateTime.now();
        List<String> lines = new ArrayList<>();
        lines.add(concat(META_HEADER));
        lines.add(concat(new String[]{now.format(dateFormatter), now.format(timeFormatter)}));
        // Записываем в файл после формирования заголовка
        Files.write(path, lines, StandardCharsets.UTF_8, StandardOpenOption.APPEND);
    }

    private void createBody(Order order) throws IOException {
        List<String> lines = new ArrayList<>();
        lines.add(concat(BODY_HEADER));
        order.getGoodsList().forEach(item -> addItemToList(item, lines));
        // Записываем в файл после формирования тела
        Files.write(path, lines, StandardCharsets.UTF_8, StandardOpenOption.APPEND);
    }

    private void createDiscountSection(DiscountCard discountCard) throws IOException {
        if (discountCard != null) {
            List<String> lines = new ArrayList<>();
            lines.add(concat(DISCOUNT_SECTION_HEADER));
            addDiscountToList(discountCard, lines);
            // Записываем в файл после формирования секции скидок
            Files.write(path, lines, StandardCharsets.UTF_8, StandardOpenOption.APPEND);
        }
    }

    private void createFooter(Order order) throws IOException {
        List<String> lines = new ArrayList<>();
        lines.add(concat(FOOTER_HEADER));
        lines.add(String.join(CSVDelimiter,
                formatPrice(order.getTotalPrice()) + "$",
                formatPrice(order.getTotalPrice() - order.getTotalWithDiscount()) + "$",
                formatPrice(order.getTotalWithDiscount()) + "$"));
        // Записываем в файл после формирования подвала
        Files.write(path, lines, StandardCharsets.UTF_8, StandardOpenOption.APPEND);
    }

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
