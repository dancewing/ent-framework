package io.entframework.kernel.rule.util;

import java.util.HashMap;
import java.util.Map;

public final class Maps {

	private Maps() {
	}

	public static Map<String, Object> empty() {
		return new HashMap<>();
	}

	public static MapBuilder<String, Object> of(String key, Object value) {
		return new HashMapBuilder().and(key, value);
	}

	public interface MapBuilder<K, V> {

		MapBuilder<K, V> and(K var1, V var2);

		Map<K, V> build();

	}

	private static class HashMapBuilder implements MapBuilder<String, Object> {

		private final Map<String, Object> data;

		private HashMapBuilder() {
			this.data = new HashMap<>();
		}

		public MapBuilder<String, Object> and(String key, Object value) {
			this.data.put(key, value);
			return this;
		}

		public Map<String, Object> build() {
			return this.data;
		}

	}

}
