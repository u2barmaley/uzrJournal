package pfr.evgen;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/** Parse a ODT File to a HTML Page/
 * This tool parses a odt file to a html page. But just text and images.
 * https://github.com/vonWeihermuehle/ODTParser
 *
 * Output could be:
 * <p>Hello World</p>
 * <img src="data:image;base64,/9j/4AAQSk .... />
 *
 * Usage:
 * public static final String path_to_odt = "C:\\Users\\Max\\repo\\Bootsfreund\\src\\main\\webapp\\freizeit\\von_Saal_nach_Tulln.odt";
 *
 * public static void main(String[] args) {
 *     ODTParser parser = new ODTParser(path_to_odt);
 *     System.out.println(parser.transform());
 * }
 */

public class Main {
    public static void main(String[] args) throws IOException {
        if(!checkInput(args)){ //проверка не пустые ли аргументы при запуске, если пустые то конец работы
            return;
        }
        ODTParser parser = new ODTParser(args [0]); //1й аргумент = это файл ODT, который парсим
        Files.write(Paths.get(args[1]), parser.transform().getBytes()); //2й аргумент - файл HTML, в который при парсинге трансформируется файл после парсинга

//        ArrayList records = parseOdt();
        System.out.println("The end.");
    }

    private static boolean checkInput(String[] args) {
            if (args.length != 2){ //проверка если не 2 аргумента, то сообщение и выход
                System.out.println("Usage: java -jar ODTParser.jar <path_to_input_file.odt> <output_file.html>");
                return false;
            }
            return true;
    }

//    private static ArrayList parseOdt() throws IOException {
//        File odt_file = new File("journal.odt");
//        ArrayList records = new ArrayList();
//
//            InputStream inputStream = getStreamToDocumentXml(odt_file);
//            String text = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines().collect(Collectors.joining("\n"));
//            System.out.println(text);
//        return records;
//    }
//
//    private static InputStream getStreamToDocumentXml(File odt_file) throws NullPointerException, IOException {
//        ZipFile zipFile = new ZipFile(odt_file);
//        ZipEntry zipEntry = zipFile.getEntry("document.xml");
//        return zipFile.getInputStream(zipEntry);
//    }
}