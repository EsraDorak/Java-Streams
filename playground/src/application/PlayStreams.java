package application;

import java.util.List;
import java.util.function.Function;


public interface PlayStreams {

    /*
     * Aufgabe 1: 
     */
    List<Integer> tenRandomNumbers();

    /*
     * Aufgabe 2: 
     */
    List<Integer> tenEvenRandomNumbers();

    /*
     * Aufgabe 3: 
     */
    List<Integer> tenSortedEvenRandomNumbers();

    /*
     * Aufgabe 4: 
     */
    Function<Integer, Boolean> getFilterFunction(int i);

    List<Integer> filteredNumbers(int filterFunctionIndex);

    /*
     * Aufgabe 5: 
     */
    final static List<String> names = List.of(
        "Hendricks", "Raymond", "Pena", "Gonzalez", "Nielsen", "Hamilton",
        "Graham", "Gill", "Vance", "Howe", "Ray", "Talley", "Brock", "Hall",
        "Gomez", "Bernard", "Witt", "Joyner", "Rutledge", "Petty", "Strong",
        "Soto", "Duncan", "Lott", "Case", "Richardson", "Crane", "Cleveland",
        "Casey", "Buckner", "Hardin", "Marquez", "Navarro"
    );

    List<String> filteredNames(List<String> names, String regex);

    /*
     * Aufgabe 6: 
     */
    List<String> sortedNames(List<String> names, int limit);

    /*
     * Aufgabe 7: 
     */
    List<String> sortedNamesByLength(List<String> names);

    /*
     * Aufgabe 8: class 'Order' defines an order (Bestellung) of an
     * article of n units at a price per unit (in Cent). 
     */
    class Order {
        final String article;
        final long units;
        final long unitPrice;
        //
        public Order(String description, long units, long unitPrice) {
            this.article = description;
            this.units = units;
            this.unitPrice = unitPrice;
        }
    }

    final static List<Order> orders = List.of(
        new Order("Becher", 2,  199),   // 2x  199 =  398
        new Order("Tasse",  7,  249),   // 7x  249 = 1743
        new Order("Stift",  4,   49),   // 4x   49 =  196
        new Order("Vase",   2,  999),   // 2x  999 = 1998
        new Order("Kanne",  5, 1499),   // 5x 1499 = 7495
        new Order("Lampe",  2, 1999),   // 2x 1999 = 3998
        new Order("Messer", 6,  789)    // 6x  789 = 4734
    );                                  // Summe:   20562 = 205,62€
    final static long orderValue = 20562L;

    long calculateValue(List<Order> orders);

    /*
     * Aufgabe 9: 
     */
    List<Order> sortByValue(List<Order> orders);

}
