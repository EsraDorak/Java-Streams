package application.streams;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Streams_0_always_pass_Test {

    @Test
    @Order(001)
    void test_001_always_pass() {
        int expected = 10;
        int actual = 10;
        assertEquals(expected, actual);
    }

//    @Test
//    @Order(010)
    void test_010_always_fail() {
        assertEquals(10984, 90);
    }
}
