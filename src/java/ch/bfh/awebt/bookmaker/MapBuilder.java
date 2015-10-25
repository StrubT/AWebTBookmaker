package ch.bfh.awebt.bookmaker;

import java.util.HashMap;
import java.util.Map;

public class MapBuilder<K, V> {

	private final Map<K, V> map = new HashMap<>();

	private MapBuilder() {
		//use factory method
	}

	public static <K, V> MapBuilder<K, V> first(K key, V value) {
		return new MapBuilder<K, V>().add(key, value);
	}

	public static <K, V> Map<K, V> single(K key, V value) {
		return first(key, value).map;
	}

	public MapBuilder<K, V> add(K key, V value) {

		map.put(key, value);
		return this;
	}

	public Map<K, V> last(K key, V value) {
		return this.add(key, value).map;
	}
}
