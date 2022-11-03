package prr.core;

import java.util.Collection;
import java.util.Comparator;
import java.util.function.Predicate;

public class ShowEntities {
	private ShowEntities() {
	}

	/**
	 * Sort the list using the comparator and return the sorted list.
	 * 
	 * @param list       The list of objects to be sorted.
	 * @param comparator A comparator to sort the list.
	 * @return A list of all the elements in the collection, sorted by the
	 *         comparator.
	 */
	public static <T> Collection<T> showAll(Collection<T> list,
			Comparator<? super T> comparator) {
		return list.stream().sorted(comparator).toList();
	}

	/**
	 * Returns a filtered and sorted list of the given collection.
	 * 
	 * @param list       The list of objects to be sorted.
	 * @param filter     A Predicate to filter the list
	 * @param comparator A comparator that will be used to sort the list.
	 * @return A list of the elements in the collection that pass the filter,
	 *         sorted by the comparator.
	 */
	public static <T> Collection<T> showFiltered(Collection<T> list,
			Predicate<? super T> filter, Comparator<? super T> comparator) {
		return list.stream().filter(filter).sorted(comparator).toList();
	}
}
