package dk.tool;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Сервис тестирования сетевого соедининя
 */
class TestConnectionServive {

    /**
     * Проверить доступность порта на заданном хосту
     * @param host Хост
     * @param port Порт
     * @return true(доступ есть)/false(доступа нет)
     */
    boolean test(String host, Integer port) {

        SocketAddress remoteAddress = new InetSocketAddress(host, port);
        Socket socket = new Socket();
        try {
            socket.connect(remoteAddress, Const.CONNECTION_TIMEOUT);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

}
