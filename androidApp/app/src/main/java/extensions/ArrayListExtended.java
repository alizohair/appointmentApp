package extensions;

import java.util.ArrayList;
import java.util.function.Predicate;

/**
 * An extension of <code>{@link ArrayList}</code> to add some filtering functions.
 *
 * @param <T> Type for extended array list.
 * @see ArrayList
 */
public class ArrayListExtended<T> extends ArrayList<T> {

    /**
     * Filters and returns the list of items based on the given predicate.
     * @param predicate A predicate accepting an object of same type given to the class
     *                  to be applied and evaluated on each item of the list for filtration.
     * @return <code>{@link ArrayListExtended}</code> of same type as given to the class with filtered items.
     */
    public ArrayListExtended<T> where(final Predicate<? super T> predicate) {
        ArrayListExtended<T> list = new ArrayListExtended<T>();
        for (T item : this) {
            if (predicate.test(item)) {
                list.add(item);
            }
        }
        return list;
    }

    /**
     * Filters and returns the first item found based on the given predicate or <code>null</code> if not found.
     *
     * @param predicate A predicate accepting an object of same type given to the class
     *                  to be applied and evaluated on each item of the list for filtration.
     * @return Instance of same type as given to the class found first or <code>null</code> otherwise.
     */
    public T first(final Predicate<? super T> predicate) {
        for (T item : this) {
            if (predicate.test(item)) {
                return item;
            }
        }
        return null;
    }

    /**
     * Filters and returns the first item found based on the given predicate or default value given if not found.
     *
     * @param predicate A predicate accepting an object of same type given to the class
     *                  to be applied and evaluated on each item of the list for filtration.
     * @param def default value, to be returned if predicate filtered all records from the list.
     * @return Instance of same type as given to the class found first or default value otherwise.
     */
    public T first(final Predicate<? super T> predicate, final T def) {
        T var = first(predicate);
        return var == null ? def : var;
    }
}
