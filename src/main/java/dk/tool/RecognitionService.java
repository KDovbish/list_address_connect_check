package dk.tool;

/**
 * Сервис распознавания
 */
class RecognitionService {

    /**
     * Получить хост из урла
     * @param url Урл
     * @return Хост из урла
     */
    String getHostFromUrl(String url) {

        String host = url;

        //  Первый этап:
        //  https://google.com:8080/path/ -> google.com:8080/path/
        //  http://google.com:8080/path/ -> google.com:8080/path/
        if (url.startsWith("https://")) host = url.substring(8);
        if (url.startsWith("http://")) host = url.substring(7);

        //  Второй этап:
        //  Проверяем наличие двоеточия перед портом
        if (host.contains(":")) {
            //  Двоеточие, а значит, предполагается что и порт, присутствует
            //  Делаем: google.com:8080/path/ -> google.com
            host = host.substring(0, host.indexOf(":"));
        } else {
            //  Двоеточие отсутствует. Т.е. имеем, на текущий момент или google.com/path/ или google.com
            //  Проверяем наличие слеша после хоста
            if (host.contains("/")) {
                host = host.substring(0, host.indexOf("/"));
            }
        }

        return host;
    }

    /**
     * Получить порт из урла
     * @param url Урл
     * @return Целочисленное значение порта из урла либо -1, если порт не может быть распознан
     */
    Integer getPortFromUrl(String url) {

        //  Проверка возможности получить номер порта исходя из префикса протокола
        if (url.contains("://")) {
            //  урл стопроцентно содержит префикс протокола

            //  Выделеям подстроку после префикса и проверяем, есть ли в ней символ двоеточия.
            //  Отсутствие символа двоеточия будет говорить о том, что порт в урле не задан явно и будет определяться префиксом протокола.
            if (url.substring(url.indexOf("://") + 3).contains(":") == false) {
                if (url.startsWith("https://")) return 443;
                if (url.startsWith("http://")) return 80;
                return -1;  //  определить порт невозможно - неизвестный протокол
            }
        }


        //  Извлечение номера порта, явно указанного в урле. Если, конечно, порт вообще указан.

        //  Убираем префикс, если он есть. Он больше не нужен для анализа порта.
        if (url.contains("://")) url = url.substring( url.indexOf("://") + 3 );

        //  В урле без префикса отсутствует двоеточие. Значит порт неуказан. Сообщаем пользователю: определить порт невозможно
        if (url.contains(":") == false) return -1;

        //  В урле без префикса двоеточие стороцентно присутствует. Т.е. сейчас рассматривается: google.com:8080/path/ или google.com:8080
        if (url.contains("/")) {
            //  Вырезаем подстроку порта с символа за двоеточием до символа перед слешем.
            return Integer.valueOf( url.substring( url.indexOf(":") + 1, url.indexOf("/")) );
        } else {
            //  Вырезаем подстроку порта с символа за двоеточием до конца строки
            return Integer.valueOf( url.substring( url.indexOf(":") + 1) );
        }

    }

}
