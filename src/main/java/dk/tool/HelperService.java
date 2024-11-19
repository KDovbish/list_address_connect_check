package dk.tool;

import java.io.File;

/**
 * Вспомогательный сервис - все что не может категоризировано как отдельный узконаправленный сервис
 */
class HelperService {

    /**
     * Получить имя файла на основе имени другого файла
     * @param fileName Имя файла без папки, которое, нужно по структуре(папка + имя) довести до имени файла fileNameTemplate
     * @param fileNameTemplate Имя файла, которое рассматривается как шаблон
     * @return Имя файла fileName доведенное по структуре(папка + имя) до структуры fileNameTemplate
     */
    String getFileNameFromAnotherFileNameTemplate(String fileName, String fileNameTemplate) {

        if (fileNameTemplate.contains(File.separator)) {
            return fileNameTemplate.substring(0, fileNameTemplate.lastIndexOf(File.separator) + 1) + fileName;
        } else {
            return fileName;
        }

    }

}
