package dk.tool;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HelperServiceTest {

    private HelperService helperService = new HelperService();

    @Test
    void getFileNameFromAnotherFileNameTemplate() {

        assertEquals( "d:\\file.ext", helperService.getFileNameFromAnotherFileNameTemplate("file.ext", "d:\\filetemplate.ext") );
        assertEquals( "d:\\path\\file.ext", helperService.getFileNameFromAnotherFileNameTemplate("file.ext", "d:\\path\\filetemplate.ext") );
        assertEquals( "file.ext", helperService.getFileNameFromAnotherFileNameTemplate("file.ext", "filetemplate.ext") );
        assertEquals( "..\\file.ext", helperService.getFileNameFromAnotherFileNameTemplate("file.ext", "..\\filetemplate.ext") );

    }
}