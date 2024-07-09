package main.java.ru.clevertec.check.model.loaders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * DataLoader is responsible for reading data from a file and converting it into a map.
 * Implements AutoCloseable to ensure the reader is properly closed.
 */

public class DataLoader implements AutoCloseable{
    private BufferedReader reader;
    private String delimiter;
    private String[] header;

    /**
     * Constructor + Reads the header line to initialize the header array.
     *
     * @param path the path to the file
     * @param delimiter the delimiter used to split the lines in the file
     * @throws IOException if an I/O error occurs
     */
    public DataLoader(String path, String delimiter) throws IOException {

        this.reader = new BufferedReader(new FileReader(path));
        this.delimiter = delimiter;

        String headerLine = reader.readLine();
        if (headerLine != null) {
            this.header = headerLine.split(delimiter);
        }
    }
    /**
     * Reads the next line from the file and converts it into a map.
     * The map is created according to the column names in the header and the values in the line.
     *
     * @return a map where the keys are the column names from the header and the values
     * are the corresponding values from the line
     *         Returns null if the end of the file is reached
     * @throws IOException if an I/O error occurs
     */
    public Map<String, String> nextMap() throws IOException {

        String nextLine = reader.readLine();
        if(nextLine == null) return null;

        String[] values = nextLine.split(delimiter);
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < header.length && i < values.length; i++)
            map.put(header[i], values[i]);

        return map;
    }
    /**
     * Closes the BufferedReader.
     *
     * @throws IOException if an I/O error occurs
     */
    public void close() throws IOException {
        if (reader != null)
            reader.close();
    }
}