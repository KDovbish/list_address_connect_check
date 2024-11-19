package dk.tool;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RecognitionServiceTest {

    private RecognitionService recognitionService = new RecognitionService();

    @Test
    void getHostFromUrl() {
        assertEquals("google.com", recognitionService.getHostFromUrl("https://google.com:8080/path/"));
        assertEquals("google.com", recognitionService.getHostFromUrl("https://google.com/path/"));
        assertEquals("google.com", recognitionService.getHostFromUrl("google.com"));
        assertEquals("google.com", recognitionService.getHostFromUrl("google.com:8080"));
        assertEquals("google.com", recognitionService.getHostFromUrl("google.com:8080/"));
        assertEquals("google.com", recognitionService.getHostFromUrl("http://google.com:8080/path/"));
        assertEquals("google.com", recognitionService.getHostFromUrl("http://google.com/path/"));
    }

    @Test
    void getPortFromUrl() {
        assertEquals(443, recognitionService.getPortFromUrl("https://google.com"));
        assertEquals(8080, recognitionService.getPortFromUrl("https://google.com:8080"));
        assertEquals(8080, recognitionService.getPortFromUrl("https://google.com:8080/path/"));
        assertEquals(8080, recognitionService.getPortFromUrl("google.com:8080"));
        assertEquals(8080, recognitionService.getPortFromUrl("google.com:8080/path/"));
        assertEquals(80, recognitionService.getPortFromUrl("http://google.com"));
        assertEquals(8080, recognitionService.getPortFromUrl("http://google.com:8080"));
        assertEquals(8080, recognitionService.getPortFromUrl("http://google.com:8080/path/"));
        assertEquals(-1, recognitionService.getPortFromUrl("google.com"));
        assertEquals(-1, recognitionService.getPortFromUrl("ftp://google.com"));
    }

}