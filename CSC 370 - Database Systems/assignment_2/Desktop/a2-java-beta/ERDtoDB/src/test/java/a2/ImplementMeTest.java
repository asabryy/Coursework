package a2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class ImplementMeTest {

    @Test
    @DisplayName("Test the ERD to Database convertor method")
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    void convertToDatabase() {
        ImplementMe studentImplementation = new ImplementMe();
        assertEquals(Example.loadDatabase(), studentImplementation.convertToDatabase(Example.loadERD()));
    }
}