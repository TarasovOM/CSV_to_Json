import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class Main {
    static String fileName = "data.csv";

    public static void main(String[] args) throws IOException, CsvException {

        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};

        List<Employee> list = parseCSV(columnMapping, fileName);
        list.forEach(System.out::println);

        String json = listToJson(list);
        writeString(json);
    }

    private static String writeString(String json) {
        try (FileWriter file = new FileWriter("data.json")) {
            file.write(json.toString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    private static String listToJson(List<Employee> list) {

        Type listType = new TypeToken<List<Employee>>() {
        }.getType();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String json = gson.toJson(list, listType);
        return json;
    }

    private static List<Employee> parseCSV(String[] columnMapping, String fileName) throws IOException {

        try {
            CSVReader reader = new CSVReader(new FileReader(fileName));
            //List<String[]> allRows = reader.readAll();
            ColumnPositionMappingStrategy<Employee> strategy = new ColumnPositionMappingStrategy<>();
            strategy.setType(Employee.class);
            strategy.setColumnMapping(columnMapping);

            CsvToBean<Employee> csv = new CsvToBeanBuilder<Employee>(reader)
                    .withMappingStrategy(strategy)
                    .build();
            return csv.parse();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}



