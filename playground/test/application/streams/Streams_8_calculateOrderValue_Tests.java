package application.streams;

import java.util.ArrayList;
import java.util.List;

import application.Factory;
import application.PlayStreams;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Streams_8_calculateOrderValue_Tests {
    //
    private final PlayStreams pls = Factory.getPlayStreamsInstance();

    @Test
    @Order(800)
    void test800_calculateValue_regular() {
        //
        long actual = pls.calculateValue(PlayStreams.orders);
        assertEquals(PlayStreams.orderValue, actual);
    }

    @Test
    @Order(801)
    void test801_calculateValue_regular() {
        //
        var extendedOrders = new ArrayList<PlayStreams.Order>(PlayStreams.orders);
        extendedOrders.addAll(List.of(
            new PlayStreams.Order("Teller", 4,  649),   //  4x  649 = 2596
            new PlayStreams.Order("Glas",  12,  249)    // 12x  249 = 2988
        ));
        long actual = pls.calculateValue(extendedOrders);
        long expected = PlayStreams.orderValue + 2596 + 2988;
        assertEquals(expected, actual);
    }

    @Test
    @Order(810)
    void test810_calculateValue_emptyOrders() {
        //
        var emptyOrders = new ArrayList<PlayStreams.Order>();
        long actual = pls.calculateValue(emptyOrders);
        assertEquals(0L, actual);
    }

    @Test
    @Order(890)
    void test890_calculateValue_irregular_orders_Null() {
        //
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            pls.calculateValue(null);    // throw exception if orders arg is null
        });
        assertEquals("orders argument is null.", thrown.getMessage());
    }
}
