package prr.core;

import java.text.CollationElementIterator;
import java.util.Collection;
import java.util.Comparator;
import java.util.function.Predicate;

public class ShowEntities {
	private ShowEntities() {
	}
	public static <T> Collection<T> showAll(Collection<T> list,Comparator<? super T> comparator){
		return list.stream().sorted(comparator).toList();
	}
	public static <T> Collection<T> showFiltered(Collection<T> list,Predicate<? super T> filter,Comparator<? super T> comparator){
		return list.stream().filter(filter).sorted(comparator).toList();
	}
}
