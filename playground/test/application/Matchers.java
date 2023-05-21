package application;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * Hamcrest matcher and other helper functions.
 * 
 * @author sgra64
 *
 */
public class Matchers {

    /**
     * Matcher for collections ignoring the order or elements, including lists
     * where order matters and duplicates may occur.
     * 
     * @param expected collection to match.
     * @param actual collection to match.
     * @return true if both collections are equal in size and element values.
     */
    public static <T> boolean matchIgnoreOrder(Collection<T> expected, Collection<T> actual) {
        return expected.size() == actual.size() && expected.containsAll(actual) && actual.containsAll(expected);
    }


    /**
     * Matcher for collections ignoring the order or elements, including lists
     * where order matters and duplicates may occur.
     * 
     * @param expected int[][] to match.
     * @param actual collection to match.
     * @return true if both collections are equal in size and element values.
     */
    public static boolean matchIgnoreOrder(int[][] expected, List<List<Integer>> actual) {
        // convert int[][] to List<List<T>>
        var exp = Arrays.stream(expected)
                .map(a -> Arrays.stream(a).boxed().collect(Collectors.toList())
            ).collect(Collectors.toList());
        //
        var result = exp.size() == actual.size();
        if(result) {
            for(int i=0; result && i < exp.size(); i++) {
                var a = actual.get(i);
                boolean res_2 = false;
                for(int j=0; ! res_2 && j < exp.size(); j++) {
                    var e = exp.get(j);
                    res_2 = a.size() == e.size() && a.containsAll(e) && e.containsAll(a);
                }
                result = result && res_2;
            }
        }
        return result;
    }


    private static Set<Class<?>> wrapperTypes = Set.of(
        Integer.class, Long.class, String.class, Boolean.class,
        Character.class, Byte.class, Short.class, Double.class, Float.class
    );

    /**
     * Helper function to map a nested collection into a textual form.
     * 
     * @param obj untyped nested collection.
     * @param sbArg optional StringBuffer arg to store output.
     * @return
     */
    @SuppressWarnings("unchecked")
    public static StringBuffer toString(Object obj, StringBuffer... sbArg) {
        @SuppressWarnings("rawtypes")
        var clazz = Optional.ofNullable(obj).map(o -> o.getClass()).orElse((Class)Object.class);
        var sb = Arrays.stream(sbArg).filter(s -> s != null).findFirst().orElse(new StringBuffer());
        //
        if(obj==null || clazz.isPrimitive() || wrapperTypes.contains(clazz)) {
            sb.append(obj==null? "null" : obj);
        } else {
            if(Collection.class.isAssignableFrom(obj.getClass())) {
                sb.append("[");
                int len = sb.length();
                ((Collection<?>)obj).forEach(o -> {
                    sb.append(sb.length()==len? "" : ", ");
                    toString(o, sb);
                });
                sb.append("]");
            }
        }
        return sb;
    }
}
