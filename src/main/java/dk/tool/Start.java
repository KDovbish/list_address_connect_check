package dk.tool;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Start {
    public static void main(String[] args) throws FileNotFoundException, IOException {

        if (args.length != 1) {
            System.out.println("List Address Connect Check - Проверить сетевые соедения по списку адресов");
            System.out.println("Проверить сетевой доступ для всех домен:порт, которые будут найдены в списке адресов(обычно, урлов).");
            System.out.println("Модуль отдает два файла: уникальный список всех найденных домен:порт и список домен:порт для который есть проблемы сетевого соединения");
            System.out.println("java -jar lacc.jar <список урлов>");
            return;
        }

        //  Создаем по объекту сервисных классов
        RecognitionService recognitionService = new RecognitionService();
        TestConnectionServive testConnectionServive = new TestConnectionServive();
        HelperService helperService = new HelperService();

        //  Инициализация имен файлов, с которыми будет работать модуль
        String urlListFileName = args[0];
        String resultHostPortListFileName = helperService.getFileNameFromAnotherFileNameTemplate("result_all_host_port.txt", urlListFileName);
        String resultConnectProblemListFileName = helperService.getFileNameFromAnotherFileNameTemplate("result_connect_problem.txt", urlListFileName);

        //  Входной список урлов
        BufferedReaderFileWithComments urlListFile = new BufferedReaderFileWithComments(urlListFileName);

        //  Файл на выходе: Уникальный список хост:порт полученный из входного списка урлов
        PrintWriter resultHostPortListFile = new PrintWriter(resultHostPortListFileName);
        //  Файл на выходе: Список хост:порт на которые не удалось соединиться
        PrintWriter resultConnectProblemListFile = new PrintWriter(resultConnectProblemListFileName);

        //  Коллекция Хост:Порт без дублирующихся элементов. Заполняется на первом этапе.
        Set<String> hostportList = new HashSet<>();

        //  Этап первый: создаем уникальный список Хост:Порт
        String url;
        while ( (url = urlListFile.readLine()) != null ) {
            hostportList.add(recognitionService.getHostFromUrl(url) + ":" + recognitionService.getPortFromUrl(url));
        }

        //  Выводим в файл весь полученный список Хост:Порт, в том числе и записи с портом = -1
        //hostportList.forEach(resultHostPortListFile::println);
        hostportList.forEach(e -> { if (e.contains(":-1")) resultHostPortListFile.println(e.substring(0, e.indexOf(":"))) ; else resultHostPortListFile.println(e); });

        //  Этап второй. Проверка сетевого соединеняи по списку
        Iterator<String> iterator = hostportList.iterator();
        String hostportString;
        String host; Integer port;
        while (iterator.hasNext()) {
            hostportString = iterator.next();

            //if (hostportString.contains(":-1")) System.out.print(hostportString.substring(0, hostportString.indexOf(":")));
            //    else System.out.print(hostportString);

            host = hostportString.substring(0, hostportString.indexOf(":"));
            port = Integer.valueOf(hostportString.substring(hostportString.indexOf(":") + 1));

            if (port != -1) System.out.print(hostportString); else System.out.print(host);  //  вывод на консоль хост:порт

            if ( port != -1) {
                if (testConnectionServive.test(host, port) == false) {
                    resultConnectProblemListFile.println(hostportString);   //  вывод в файл об ошибках соединения
                    System.out.println("  not connected");  //  вывод на консоль: неуспешное соединение
                } else {
                    System.out.println("  connected"); //  вывод на консоль: успешное соединение
                }
            } else {
                resultConnectProblemListFile.println(host);
                System.out.println("  unrecognized port");  //  вывод на консоль: нераспознанный порт
            }
        }

        resultConnectProblemListFile.close();
        resultHostPortListFile.close();
        urlListFile.close();


    }
}
