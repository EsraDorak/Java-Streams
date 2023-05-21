package application.streams;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import application.Factory;
import application.PlayStreams;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Streams_9_sortByOrderValue_Tests {
    //
    private final PlayStreams pls = Factory.getPlayStreamsInstance();

    @Test
    @Order(900)
    void test900_sortByOrderValue_regular() {
        //
        var expected = List.of(4, 6, 5, 3, 1, 0, 2).stream()
            .map(i -> PlayStreams.orders.get(i))
            .collect(Collectors.toList());
        //
        var actual = pls.sortByValue(PlayStreams.orders);
        //
        assertEquals(expected.size(), actual.size());
        assertEquals(expected, actual);
    }

    @Test
    @Order(901)
    void test901_sortByOrderValue_regular() {
        //
        var extendedOrders = new ArrayList<PlayStreams.Order>(PlayStreams.orders);
        extendedOrders.addAll(List.of(
            new PlayStreams.Order("Teller", 4,  649),   // [7]:  4x  649 = 2596
            new PlayStreams.Order("Glas",  12,  249)    // [8]: 12x  249 = 2988
        ));
        //
        var expected = List.of(4, 6, 5, 8, 7, 3, 1, 0, 2).stream()
            .map(i -> extendedOrders.get(i))
            .collect(Collectors.toList());
        //
        var actual = pls.sortByValue(extendedOrders);
        //
        assertEquals(expected.size(), actual.size());
        assertEquals(expected, actual);
    }

    @Test
    @Order(910)
    void test910_sortByOrderValue_emptyOrders() {
        //
        var emptyOrders = new ArrayList<PlayStreams.Order>();
        long actual = pls.calculateValue(emptyOrders);
        assertEquals(0L, actual);
    }

    @Test
    @Order(990)
    void test990_sortByOrderValue_irregular_orders_Null() {
        //
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            pls.calculateValue(null);    // throw exception if orders arg is null
        });
        assertEquals("orders argument is null.", thrown.getMessage());
    }
}
