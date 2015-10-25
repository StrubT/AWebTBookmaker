package ch.bfh.awebt.bookmaker;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public final class Streams {

	private Streams() {
		//utility class
	}

	public static <T> Stream<T> iteratorStream(Iterator<T> iterator) {

		return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED), false);
	}

	public static <T> List<T> iteratorList(Iterator<T> iterator) {

		return iteratorStream(iterator).collect(Collectors.toList());
	}

	public static <T> Collector<T, ?, Optional<T>> optionalSingleCollector() {

		return Collectors.reducing((a, b) -> {
			throw new TooManyElementsException();
		});
	}

	public static <T> Collector<T, ?, T> nullableSingleCollector() {

		return Collector.of(() -> new OptionalBox<>(true), OptionalBox<T>::set, OptionalBox<T>::set, OptionalBox<T>::get);
	}

	public static <T> Collector<T, ?, T> singleCollector() {

		return Collector.of(() -> new OptionalBox<>(false), OptionalBox<T>::set, OptionalBox<T>::set, OptionalBox<T>::get);
	}

	private static class OptionalBox<T> {

		private final boolean optional;

		private T value = null;
		private boolean present = false;

		public OptionalBox(boolean optional) {

			this.optional = optional;
		}

		public OptionalBox<T> set(OptionalBox<T> optional) {

			return optional.present ? this.set(optional.value) : this;
		}

		public OptionalBox<T> set(T value) {

			if (present)
				throw new TooManyElementsException();

			present = true;
			this.value = value;
			return this;
		}

		public T get() {

			if (!present && !optional)
				throw new NoSuchElementException();

			return value;
		}
	}
}
