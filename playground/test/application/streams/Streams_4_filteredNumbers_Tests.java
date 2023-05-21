package application.streams;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import application.Factory;
import application.PlayStreams;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Streams_4_filteredNumbers_Tests {
    //
    private final PlayStreams pls = Factory.getPlayStreamsInstance();

    @Test
    @Order(400)
    void test400_filteredNumbers_50evenNumbers_regular() {
        //
        // 50 even numbers
        List<Integer> expected = Arrays.asList(
              2,   4,   6,   8,  10,  12,  14,  16,  18,  20,
             22,  24,  26,  28,  30,  32,  34,  36,  38,  40,
             42,  44,  46,  48,  50,  52,  54,  56,  58,  60,
             62,  64,  66,  68,  70,  72,  74,  76,  78,  80,
             82,  84, 86,   88,  90,  92,  94,  96,  98, 100)
            //
            .stream().collect(Collectors.toList());
        //
        List<Integer> actual = pls.filteredNumbers(0);
        //
        assertEquals(50, actual.size());
        assertEquals(expected, actual);
        //
        // alternative test to verify all numbers are even
        boolean testAllNumbers = actual.stream()
                .map(n -> n > 0 && n % 2 == 0)
                .reduce(true, (accumulator, n) -> accumulator && n);
        //
        assertTrue(testAllNumbers);
    }

    @Test
    @Order(410)
    void test410_filteredNumbers_50divisibleBy3Numbers_regular() {
        //
        // 50 numbers divisible by 3
        List<Integer> expected = Arrays.asList(
              3,   6,   9,  12,  15,  18,  21,  24,  27,  30,
             33,  36,  39,  42,  45,  48,  51,  54,  57,  60,
             63,  66,  69,  72,  75,  78,  81,  84,  87,  90,
             93,  96,  99, 102, 105, 108, 111, 114, 117, 120,
            123, 126, 129, 132, 135, 138, 141, 144, 147, 150)
            //
            .stream().collect(Collectors.toList());
        //
        List<Integer> actual = pls.filteredNumbers(1);
        //
        assertEquals(50, actual.size());
        assertEquals(expected, actual);
        //
        // alternative test to verify all numbers are divisible by 3
        boolean testAllNumbers = actual.stream()
                .map(n -> n > 0 && n % 3 == 0)
                .reduce(true, (accumulator, n) -> accumulator && n);
        //
        assertTrue(testAllNumbers);
    }

    @Test
    @Order(420)
    void test420_filteredNumbers_50primeNumbers_regular() {
        //
        // 50 prime numbers
        List<Integer> expected = Arrays.asList(
              2,   3,   5,   7,  11,  13,  17,  19,  23,  29,
             31,  37,  41,  43,  47,  53,  59,  61,  67,  71,
             73,  79,  83,  89,  97, 101, 103, 107, 109, 113,
            127, 131, 137, 139, 149, 151, 157, 163, 167, 173,
            179, 181, 191, 193, 197, 199, 211, 223, 227, 229)
            //
            .stream().collect(Collectors.toList());
        //
        List<Integer> actual = pls.filteredNumbers(2);
        //
        assertEquals(50, actual.size());
        assertEquals(expected, actual);
    }

    @Test
    @Order(490)
    void test490_filteredNumbers_50evenNumbers_irregularNegativeIndex() {
        //
        IndexOutOfBoundsException thrown = assertThrows(IndexOutOfBoundsException.class, () -> {
            // Code under test that is supposed to throws exception for illegal index
            pls.filteredNumbers(-1);    // -1 is an illegal index
        //
        });
        //
        assertEquals("index i=-1 is not in bounds of filterFunctions[].", thrown.getMessage());

        thrown = assertThrows(IndexOutOfBoundsException.class, () -> {
            // Code under test that is supposed to throws exception for illegal index
            pls.filteredNumbers(-36256);    // -1 is an illegal index
        //
        });
        //
        assertEquals("index i=-36256 is not in bounds of filterFunctions[].", thrown.getMessage());
    }

    @Test
    @Order(491)
    void test491_filteredNumbers_50evenNumbers_irregularIndex() {
        //
        IndexOutOfBoundsException thrown = assertThrows(IndexOutOfBoundsException.class, () -> {
            // Code under test that is supposed to throws exception for illegal index
            pls.filteredNumbers(36256);    // -1 is an illegal index
        //
        });
        //
        assertEquals("index i=36256 is not in bounds of filterFunctions[].", thrown.getMessage());
    }
}
