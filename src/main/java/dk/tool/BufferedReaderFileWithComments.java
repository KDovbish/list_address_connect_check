package dk.tool;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Класс для чтения конфигурационного файла.
 * <br><br>
 * Понятие конфигурационный файл, в данном случае, подразумевает следующее...
 * <ul>
 *     <li>есть строки со значащей информацией</li>
 *     <li>есть комментарии, начинающиеся с символа #</li>
 *     <li>есть пустые строки</li>
 * </ul>
 * Класс вычитыает только строки со значащей информацией.
 */
class BufferedReaderFileWithComments extends BufferedReader {

    public BufferedReaderFileWithComments(String fileName) throws FileNotFoundException {
        super(new FileReader(fileName));
    }

    /**
     * Прочитать очередную строку со значащей информацией
     * @return строка или null(в случае, если обнаружен конец файла)
     * @throws IOException IOException
     */
    @Override
    public String readLine() throws IOException {
        String line;
        while (true) {
            line = super.readLine();
            if (line == null) return null;
            if ( !(line.trim().startsWith("#") || line.trim().equals("")) ) return line;
        }
    }


}
