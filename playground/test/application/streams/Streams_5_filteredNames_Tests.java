package application.streams;

import java.util.List;

import application.Factory;
import application.PlayStreams;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Streams_5_filteredNames_Tests {
    //
    private final PlayStreams pls = Factory.getPlayStreamsInstance();

    @Test
    @Order(500)
    void test500_filteredNames_regular() {
        //
        List<String> expected = List.of("Gonzalez", "Gomez", "Marquez");
        List<String> actual = pls.filteredNames(PlayStreams.names, ".*ez$");
        //
        assertEquals(3, actual.size());
        assertEquals(expected, actual);
    }

    @Test
    @Order(590)
    void test590_filteredNames_irregularNamesNull() {
        //
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            pls.filteredNames(null, ".*ez$");    // throw exception if names arg is null
        });
        assertEquals("names or regex argument is null.", thrown.getMessage());
    }

    @Test
    @Order(591)
    void test591_filteredNames_irregularRegexNull() {
        //
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            pls.filteredNames(PlayStreams.names, null);    // throw exception if regex arg is null
        });
        assertEquals("names or regex argument is null.", thrown.getMessage());
    }

    @Test
    @Order(592)
    void test592_filteredNames_irregularNamesAndRegexNull() {
        //
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            pls.filteredNames(null, null);    // throw exception if both args are null
        });
        assertEquals("names or regex argument is null.", thrown.getMessage());
    }
}
