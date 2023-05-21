package application;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;


/**
 * Class to execute Stream functions.
 * 
 * @author sgra64
 *
 */
public class PlayStreamsRunner {

    /*
     * Arguments passed from command line.
     */
    private final String[] args;

    /**
     * Constructor to initialize new NumbersRunner instance.
     * 
     * @param args arguments passed from command line.
     */
    PlayStreamsRunner(String[] args) {
        this.args = args;
    }


    /**
     * Entry point of program execution, main() function.
     * 
     * @param args arguments passed from command line.
     */
    public static void main(String[] args) {
        var instance = new PlayStreamsRunner(args); // create new NumbersRunner instance
        instance.run();     // invoke run()
    }

    /**
     * Entry point for task execution of the NumbersRunner instance.
     */
    void run() {
        String msg = PlayStreamsRunner.class.getSimpleName();
        //
        System.out.println(msg);    // print header with two numbers arrays
        runArgs(0, args);           // run tasks from args-specifications
    }

    /**
     * Private method that runs one task: [1..8] with the numbers[] array indicated
     * by index ni and invoking a function: [1..8] on a Numbers instance passing
     * parameters x and y to that function. The task is executed, results are printed.
     * 
     * @param task index of a task/function execute: [1..8].
     * @param ni index of numbers[] array from N.
     * @param x parameter passed to function.
     * @param y second parameter passed to function.
     */
    private void runTask(int task, int ni, int x, int y) {
        //
        final PlayStreams playStream = Factory.getPlayStreamsInstance();
        final StringBuffer sb = new StringBuffer();
        //
        switch(task) {

        case 0: // B3
            int sum = IntStream.range(0, 10)    // generate numbers 0..9
                .boxed()                        // convert IntStream to Stream<Integer>
                .filter(n -> n % 3 == 0)        // pass only numbers divisible by 3
                .sorted((n1, n2) ->             // sort remaining numbers in descending order
                    Integer.compare(n2, n1)
                )
                .map(n -> {                     // print remaining number in sorted order
                    System.out.print(n + ", ");
                    return n;                   // pass same number i on
            })
            .reduce(0, (res, next) -> res + next);  // reduce stream to sum of remaining numbers
            //
            System.out.println("sum: " + sum);

            // B3: map() example
            Arrays.asList(2, 7, 4, 2, 5, 2, 6)
                .stream()
                .map(i -> i * 10)           // input für map: i – output: i * 10
                .forEach(System.out::println);

            // B3: reduce() example
            sum = Arrays.asList(2, 7, 4, 2, 5, 2, 6)
                .stream()
                .reduce(0, (accumulator, next) -> accumulator + next);
            //
            System.out.println("sum: " + sum); 
            //
            break;

        case 1: // B4, Aufgabe 1.)
            playStream.tenRandomNumbers()
                .stream()
                .forEach(n -> collect(sb, n));
            //
            sb.insert(0, String.format(" - %s -> [", "tenRandomNumbers()")).append("]");
            break;

        case 2: // B4, Aufgabe 2.)
            playStream.tenEvenRandomNumbers()
                .stream()
                .forEach(n -> collect(sb, n));
            //
            sb.insert(0, String.format(" - %s -> [", "tenEvenRandomNumbers()")).append("]");
            break;

        case 3: // B4, Aufgabe 3.)
            playStream.tenSortedEvenRandomNumbers()
                .stream()
                .forEach(n -> collect(sb, n));
            //
            sb.insert(0, String.format(" - %s -> [", "tenSortedEvenRandomNumbers()")).append("]");
            break;

        case 4: // B4, Aufgabe 4.)
            playStream.filteredNumbers(ni)
                .stream()
                .forEach(n -> collect(sb, n));
            //
            var label = Map.of(0, "(50 even numbers)", 1, "(50 divisible by 3)", 2, "(50 prime numbers)");
            //
            sb.insert(0, String.format(" - %s, %s: -> [", "filteredNumbers()", label.get(ni))).append("]");
            break;

        case 5: // B4, Aufgabe 5.)
            playStream.filteredNames(PlayStreams.names, ".*ez$")
                .forEach(n -> collect(sb, n));
            //
            sb.insert(0, String.format(" - %s: -> [", "filteredNames()")).append("]");
            break;

        case 6: // B4, Aufgabe 6.)
            playStream.sortedNames(PlayStreams.names, ni)
                .forEach(n -> collect(sb, n));
            //
            sb.insert(0, String.format(" - %s: -> [", "sortedNames()")).append("]");
            break;

        case 7: // B4, Aufgabe 7.)
            playStream.sortedNamesByLength(PlayStreams.names)
                .forEach(n -> collect(sb, n));
            //
            sb.insert(0, String.format(" - %s: -> [", "sortedNamesByLength()")).append("]");
            break;

        case 8: // B4, Aufgabe 8.)
            long value = playStream.calculateValue(PlayStreams.orders);
            //
            sb.insert(0, String.format(" - %s: -> %d", "calculateValue(orders)", value));
            break;

        case 9: // B4, Aufgabe 9.)
            sb.append("\n");
            //
            playStream.sortByValue(PlayStreams.orders).stream()
                .map(o -> fmtOrder(o))
                .forEach(ostr -> sb.append(ostr));
            //
            value = playStream.calculateValue(PlayStreams.orders);
            //
            sb.insert(0, String.format(" - %s: -> [", "sortByValue()")).append("]");
            sb.append(" --------------------------    -----------\n");
            sb.append(String.format("%28d   (%6s EUR)\n ", value, fmtPrice(value)));
            sb.append(" --------------------------    ===========\n");
            break;

        }
        System.out.println(sb.toString());
    }


    private StringBuffer collect(StringBuffer sb, String str) {
        return sb.append(sb.length()==0?"" : ", ").append(str);
    }

    private StringBuffer collect(StringBuffer sb, Integer n) {
        return collect(sb, String.valueOf(n));
    }

    private String fmtOrder(PlayStreams.Order order) {
        long value = order.units * order.unitPrice;
        var art = String.format("%-8s", order.article + ",");
        var uts = String.format("%2d", order.units);
        var upr = String.format("%5d", order.unitPrice);
        var val = String.format("%4d", value);
        var eur = String.format("(%5s EUR)", fmtPrice(value));
        return String.format("   - %s%sx%s = %s%15s\n", art, uts, upr, val, eur);
    }

    private String fmtPrice(long p) {
        return String.format("%,d.%02d", p/100L, Math.abs(p % 100L));
    }

    /**
     * Private method that parses args[] starting from index i and recursively
     * analyzing patterns: {i {{x,y,sum,n}=<val>}*}* to run task i followed by
     * parameters. For example, e.g. 1 4 x=4 n=2 4 x=4 n=1 means that first,
     * task 1 is executed (no parameters) followed by task 4 with parameters
     * x=4 n=2 followed by another execution of task 4 with x=4 and n=1.
     * 
     * @param i index of task/function to execute.
     * @param args arguments passed from command line.
     * @return i.
     */
    private int runArgs(int i, String[] args) {
        int task = -1;
        var pmap = new HashMap<String, Integer>(Map.of("n", 0, "x", -1, "y", -1));
        for( ; i < args.length; i++) {
            try {
                int t = Integer.parseInt(args[i]);
                if(task < 0) task = t; else break;
            //
            } catch(NumberFormatException nfe) {
                //
                try {
                    Arrays.stream(args[i].split("=")).reduce(null, (a, b) ->
                        Optional.ofNullable(a).map(_a -> "" +
                            pmap.put(a.equals("sum")? "x" : a, Integer.parseInt(b))).orElse(b)
                    );
                } catch(NumberFormatException nfe2) { }
            }
        }
        if(task >= 0) {
            runTask(task, pmap.get("n"), pmap.get("x"), pmap.get("y"));
        }
        return i < args.length? runArgs(i, args) : i;   // recurse args for next task
    }
}
