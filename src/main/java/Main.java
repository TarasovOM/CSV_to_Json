import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvException;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static java.lang.module.ModuleDescriptor.read;

public class Main {
    static String fileName = "data.csv";

    public static void main(String[] args) throws IOException, CsvException, ParserConfigurationException, SAXException {

        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};

        List<Employee> list = parseCSV(columnMapping, fileName);
        //list.forEach(System.out::println);

        String json = listToJson(list);
        writeString(json);

       parseXML("data.xml");
    }

    private static List<Employee> parseXML(String s) throws IOException, SAXException, ParserConfigurationException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new File(s));

        Node root = doc.getDocumentElement();
        NodeList nodeList = root.getChildNodes();

        for (int i = 0; i < nodeList.getLength(); i++) {

            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {

                System.out.println(STR."Текущий узел: \{node.getNodeName()}");
                Element employee = (Element) node;
                NodeList nodeList1 = employee.getChildNodes();
                for (int a = 1; a < nodeList1.getLength(); a++) {
                    Node node1 = nodeList1.item(a);
                    if(node1.getNodeName() == "#text") {continue;}
                    System.out.println(STR."Текущий узел: \{node1.getNodeName()}");
                    System.out.println(STR."Текущий узел: \{node1.getTextContent()}");

                }                 
            }
        }
        return null;
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



