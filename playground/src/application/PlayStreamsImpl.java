package application;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.*;


class PlayStreamsImpl implements PlayStreams {

    /*
     * Function variable initialized with Lambda expression that accepts
     * one argument and returns a Stream of random Integer numbers between
     * [0, upperBound-1].
     */
    private final Function<Integer, Stream<Integer>> randIntStream =
        //
        (upperBound)  -> {  // Lambda expression returning Stream<Integer>
            final Random rand = new Random();
            return Stream.generate(() -> rand.nextInt(upperBound));
        };

    /*
     * Function variable that accepts one argument (upperBound) and returns
     * a sequential Stream of Integer numbers in range: [0, 1, 2, ... upperBound-1].
     */
    private final Function<Integer, Stream<Integer>> intStream =
        //
        //lambda(...) -> { function that returns Stream<Integer> }
        (upperBound)  -> IntStream.range(1, upperBound).boxed();


    /*
     * Aufgabe 1: 
     */
    @Override
    public List<Integer> tenRandomNumbers() {
        List<Integer> result = List.of();
        /*
         * invoke Function variable using apply()
         */
        result = randIntStream.apply(1000)
                .limit(10)
                .collect(Collectors.toList());
        //
        return result;
    }


    /*
     * Aufgabe 2: 
     */
    @Override
    public List<Integer> tenEvenRandomNumbers() {
        List<Integer> result = List.of();
       
         result = randIntStream.apply(1000)
        .filter(a-> a%2==0)
        .limit(10)
        .collect(Collectors.toList());
        return result;
    }


    /*
     * Aufgabe 3: 
     */
    @Override
    public List<Integer> tenSortedEvenRandomNumbers() {
        List<Integer> result = List.of();
        
        result=randIntStream.apply(1000)
        .filter(s-> s%2==0)
        .limit(10)
        .sorted()
        .collect(Collectors.toList());
        return result;
    }


    /*
     * Aufgabe 4: 
     */
    private final static List<Function<Integer, Boolean>> filterFunctions =
        //
        Arrays.asList(      // initialize with Lambda expressions
            (n) -> isEven(n),        // 0: correct to filter even numbers
            (n) -> dividesByThree(n),     // 1: correct to filter numbers divisible by three
            (n) -> isPrime(n)  // 2: implement method
        );
        
        private static boolean isEven(int number) {
            return number % 2 == 0;
        }
        private static boolean dividesByThree(int number) {
            return number % 3 == 0;
        }

    private static boolean isPrime(int number) {
        boolean flag = false;
        for (int i = 2; i <= number / 2; ++i) {
          if (number % i == 0) {
            flag = true;
            break;
          }
        }
        if(!flag){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public Function<Integer, Boolean> getFilterFunction(int i) {
        if(i < 0 || i >= filterFunctions.size())
            throw new IndexOutOfBoundsException(
                    String.format("index i=%d is not in bounds of filterFunctions[].", i));
        //
        return filterFunctions.get(i);
    }

    @Override
    public List<Integer> filteredNumbers(int filterFunctionIndex) {
        Function<Integer, Boolean> filter = getFilterFunction(filterFunctionIndex); //welcher index
        List<Integer> result = IntStream.range(2, Integer.MAX_VALUE) //zahlen range 
            .boxed()                //Pipline von Integer wird erzeugt
            .filter(filter::apply)  //apply bedeutet auf jedes Element im stream 
            .limit(50)
            .collect(Collectors.toList());
        return result;
    }


    /*
     * Aufgabe 5: 
     */
    @Override
    public List<String> filteredNames(List<String> names, String regex) {
        if(names==null || regex==null){
            throw new IllegalArgumentException("names or regex argument is null.");
        }
        List<String> result = List.of();
       result=names.stream()
       .filter(namen-> namen.matches(regex))
       .collect(Collectors.toList());
        return result;
    }


    /*
     * Aufgabe 6: 
     */
    @Override
    public List<String> sortedNames(List<String> names, int limit) {
        if(names==null){
            throw new IllegalArgumentException("names argument is null.");
        }
        if(limit<0){
            throw new IllegalArgumentException("limit argument is negative: -10.");
        }
        List<String> result = List.of();
       result=names.stream()
       .sorted()
       .limit(limit)
       .collect(Collectors.toList());
        return result;
    }


    /*
     * Aufgabe 7: 
     */
    @Override
    public List<String> sortedNamesByLength(List<String> names) {
        if(names==null){
            throw new IllegalArgumentException("names argument is null.");
        }
        List<String> result = List.of();
        result=names.stream()
        //kürzerer weg .sorted(Comparator.comparingInt(String::length).thenComparing(Comparator.naturalOrder()))
        .sorted((s1,s2)-> {
            if(s1.length()>s2.length()){
                return +1;
            }
            else if(s1.length()<s2.length()){
                return -1;
            }
            //Wenn gleich dann sortier nach alphabet 
            else if(s1.length()==s2.length()){
                return s1.compareTo(s2);
            }
            else{
                throw new IllegalArgumentException("sonderall wie auch immer der eintritt ");
            }
        })
        
        .collect(Collectors.toList());
        return result;
    }


    /*
     * Aufgabe 8: 
     */
    @Override
    public long calculateValue(List<Order> orders) {
       
        if(orders==null){
            throw new IllegalArgumentException("orders argument is null.");
        }
        return orders.stream()
        //multi preis mit anzahl 
        .mapToLong(order -> order.unitPrice * order.units)
        //addiere alle zwischen ergebnisse in einem LONG 0L steht auch für LONG
        .reduce(0L,Long :: sum);
    }


    /*
     * Aufgabe 9: 
     */
    public List<Order> sortByValue(List<Order> orders) {
        if(orders==null){
            throw new IllegalArgumentException("orders argument is null.");
        }

        List<Order> result = orders.stream()
        //.reversed geht nciht weil unitprce und unit keine felder sind 
        .sorted(Comparator.comparingLong(order -> order.unitPrice * order.units))
        .collect(Collectors.toList());
    //gehört nicht mehr zum stream 
    Collections.reverse(result);

    return result;
    }
}
