package group3.hashmap;

import java.util.*;

public class HashMap<K, V> implements Map<K, V>
{

	/**
	 * Represents a key-value pair in the {@link HashMap}.
	 *
	 * @param <K> The key type.
	 * @param <V> The value type.
	 */
	private static class Entry<K, V> implements Map.Entry<K, V>
	{

		/**
		 * The hashCode of the key.
		 */
		private final int hash;

		/**
		 * The key of the key-value pair.
		 */
		private final K key;

		/**
		 * The value of the key-value pair.
		 */
		private V value;

		/**
		 * Creates a new key-value pair.
		 *
		 * @param key   The key in the key-value pair.
		 * @param value The value in the key-value pair.
		 */
		public Entry(int hash, K key, V value)
		{
			this.hash = hash;
			this.key = key;
			this.value = value;
		}

		/**
		 * Returns the key in the key-value pair.
		 *
		 * @return The key in the key-value pair.
		 */
		public K getKey()
		{
			return this.key;
		}

		/**
		 * Returns the value in the key-value pair.
		 *
		 * @return The value in the key-value pair.
		 */
		public V getValue()
		{
			return this.value;
		}

		/**
		 * Sets the value in the key-value pair.
		 *
		 * @param value The new value to set.
		 *
		 * @return The value just replaced.
		 */
		public V setValue(V value)
		{
			V before = value;
			this.value = value;
			return before;
		}
	}

	/**
	 * The default amount of open storage spaces in the {@link HashMap}. The internal storage of the {@link HashMap}
	 * continues to grow based on the <code>loadFactor</code>.
	 */
	private static final int DEFAULT_CAPACITY = 16;

	/**
	 * The default proportion of the storage that must be filled before the internal storage of the {@link HashMap}
	 * is doubled.
	 */
	private static final double DEFAULT_LOAD_FACTOR = 0.75;

	/**
	 * The proportion of the capacity that must be filled before the internal storage of the {@link HashMap}
	 * is doubled.
	 */
	private final double loadFactor;

	/**
	 * The internal storage of the {@link HashMap}.
	 */
	private Entry<K, V>[] storage;

	/**
	 * The number of entries in the {@link HashMap}.
	 */
	private int size = 0;

	/**
	 * Creates a new {@link HashMap} using the {@link HashMap#DEFAULT_CAPACITY} and
	 * {@link HashMap#DEFAULT_LOAD_FACTOR}.
	 */
	public HashMap()
	{
		this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
	}

	/**
	 * Creates a new {@link HashMap} using the provided <code>capacity</code> and the
	 * {@link HashMap#DEFAULT_LOAD_FACTOR}.
	 *
	 * @param capacity The amount of open storage spaces in the {@link HashMap}.
	 *
	 * @throws IllegalArgumentException When the provided capacity is less than 1.
	 */
	public HashMap(int capacity)
	{
		this(capacity, DEFAULT_LOAD_FACTOR);
	}

	/**
	 * Creates a new {@link HashMap} using the provided <code>loadFactor</code> and the
	 * {@link HashMap#DEFAULT_CAPACITY}.
	 *
	 * @param loadFactor The proportion of the capacity that must be filled before the internal storage of the
	 *                   {@link HashMap} is doubled.
	 *
	 * @throws IllegalArgumentException When the provided <code>loadFactor</code> is not between 0 and 1.
	 */
	public HashMap(double loadFactor)
	{
		this(DEFAULT_CAPACITY, loadFactor);
	}

	/**
	 * Creates a new {@link HashMap} using the provided <code>capacity</code> and <code>loadFactor</code>.
	 *
	 * @param capacity   The amount of open storage spaces in the {@link HashMap}.
	 * @param loadFactor The proportion of the capacity that must be filled before the internal storage of the
	 *                   {@link HashMap} is doubled.
	 *
	 * @throws IllegalArgumentException When the provided capacity is less than 1.
	 * @throws IllegalArgumentException When the provided <code>loadFactor</code> is not between 0 and 1.
	 */
	public HashMap(int capacity, double loadFactor)
	{
		if (capacity < 1)
			throw new IllegalArgumentException("capacity must be greater than 1.");

		if (loadFactor <= 0 || loadFactor >= 1)
			throw new IllegalArgumentException("loadFactor must be between 0 and 1.");

		this.loadFactor = loadFactor;
		this.storage = (Entry<K, V>[]) new Entry[capacity];
	}

	/**
	 * Returns the number of key-value mappings in this map.  If the
	 * map contains more than <tt>Integer.MAX_VALUE</tt> elements, returns
	 * <tt>Integer.MAX_VALUE</tt>.
	 *
	 * @return the number of key-value mappings in this map
	 */
	@Override public int size()
	{
		return size;
	}

	/**
	 * Returns <tt>true</tt> if this map contains no key-value mappings.
	 *
	 * @return <tt>true</tt> if this map contains no key-value mappings
	 */
	@Override public boolean isEmpty()
	{
		return size == 0;
	}

	/**
	 * Returns <tt>true</tt> if this map contains a mapping for the specified
	 * key.  More formally, returns <tt>true</tt> if and only if
	 * this map contains a mapping for a key <tt>k</tt> such that
	 * <tt>(key==null ? k==null : key.equals(k))</tt>.  (There can be
	 * at most one such mapping.)
	 *
	 * @param key key whose presence in this map is to be tested
	 *
	 * @return <tt>true</tt> if this map contains a mapping for the specified
	 * key
	 * @throws NullPointerException When the provided key is <code>null</code>.
	 */
	@Override public boolean containsKey(Object key)
	{
		if (key == null)
			throw new NullPointerException("Keys cannot be null.");

		int hashCode            = key.hashCode();
		int originalBucketIndex = Math.abs(hashCode) % storage.length;
		int currentBucketIndex  = originalBucketIndex;
		int counter             = 0;

		while (counter++ < storage.length) {
			Entry<K, V> currentEntry = storage[currentBucketIndex];
			if (currentEntry != null && currentEntry.hash == hashCode && currentEntry.key.equals(key)) {
				return true;
			}

			currentBucketIndex = (currentBucketIndex == storage.length - 1) ? 0 : currentBucketIndex + 1;
		}

		return false;
	}

	/**
	 * Returns <tt>true</tt> if this map maps one or more keys to the
	 * specified value.  More formally, returns <tt>true</tt> if and only if
	 * this map contains at least one mapping to a value <tt>v</tt> such that
	 * <tt>(value==null ? v==null : value.equals(v))</tt>.  This operation
	 * will probably require time linear in the map size for most
	 * implementations of the <tt>Map</tt> interface.
	 *
	 * @param value value whose presence in this map is to be tested
	 *
	 * @return <tt>true</tt> if this map maps one or more keys to the
	 * specified value
	 */
	@Override public boolean containsValue(Object value)
	{
		for (Entry<K, V> entry : storage) {
			if (entry != null && entry.value.equals(value)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Returns the value to which the specified key is mapped,
	 * or {@code null} if this map contains no mapping for the key.
	 * <p>
	 * <p>More formally, if this map contains a mapping from a key
	 * {@code k} to a value {@code v} such that {@code (key==null ? k==null :
	 * key.equals(k))}, then this method returns {@code v}; otherwise
	 * it returns {@code null}.  (There can be at most one such mapping.)
	 * <p>
	 * <p>If this map permits null values, then a return value of
	 * {@code null} does not <i>necessarily</i> indicate that the map
	 * contains no mapping for the key; it's also possible that the map
	 * explicitly maps the key to {@code null}.  The {@link #containsKey
	 * containsKey} operation may be used to distinguish these two cases.
	 *
	 * @param key the key whose associated value is to be returned
	 *
	 * @return the value to which the specified key is mapped, or
	 * {@code null} if this map contains no mapping for the key
	 */
	@Override public V get(Object key)
	{
		if (key == null)
			throw new NullPointerException("Null keys not allowed");

		int hashCode            = key.hashCode();
		int originalBucketIndex = Math.abs(hashCode) % storage.length;
		int currentBucketIndex  = originalBucketIndex;
		int counter             = 0;

		while (counter++ < storage.length) {
			Entry<K, V> currentEntry = storage[currentBucketIndex];
			if (currentEntry != null && currentEntry.hash == hashCode && currentEntry.key.equals(key)) {
				return currentEntry.value;
			}

			currentBucketIndex = (currentBucketIndex == storage.length - 1) ? 0 : currentBucketIndex + 1;
		}

		return null;
	}

	/**
	 * Associates the specified value with the specified key in this map
	 * (optional operation).  If the map previously contained a mapping for
	 * the key, the old value is replaced by the specified value.  (A map
	 * <tt>m</tt> is said to contain a mapping for a key <tt>k</tt> if and only
	 * if {@link #containsKey(Object) m.containsKey(k)} would return
	 * <tt>true</tt>.)
	 *
	 * @param key   key with which the specified value is to be associated
	 * @param value value to be associated with the specified key
	 *
	 * @return the previous value associated with <tt>key</tt>, or
	 * <tt>null</tt> if there was no mapping for <tt>key</tt>.
	 * (A <tt>null</tt> return can also indicate that the map
	 * previously associated <tt>null</tt> with <tt>key</tt>,
	 * if the implementation supports <tt>null</tt> values.)
	 * @throws NullPointerException When the provided key is <code>null</code>.
	 */
	@Override public V put(K key, V value)
	{
		if (key == null)
			throw new NullPointerException("Null keys not allowed");

		int hashCode            = key.hashCode();
		int originalBucketIndex = Math.abs(hashCode) % storage.length;
		int currentBucketIndex  = originalBucketIndex;
		int counter             = 0;

		while (counter++ < storage.length) {

			if (storage[currentBucketIndex] == null) {
				storage[currentBucketIndex] = new Entry<>(hashCode, key, value);
				size++;
				if (needsExpansion(size))
					expand();
				return null;
			}

			Entry<K, V> currentEntry = storage[currentBucketIndex];
			if (currentEntry.hash == hashCode && currentEntry.key.equals(key)) {
				V before = currentEntry.value;
				currentEntry.setValue(value);
				return before;
			}

			currentBucketIndex = (currentBucketIndex == storage.length - 1) ? 0 : currentBucketIndex + 1;
		}

		throw new IllegalStateException();
	}

	/**
	 * Doubles the internal storage of the {@link HashMap}.
	 */
	private void expand()
	{
		Entry<K, V>[] newArray = (Entry<K, V>[]) new Entry[storage.length * 2];
		for (int x = 0; x < storage.length; x++) {
			if (storage[x] != null) {
				int hashCode            = storage[x].hashCode();
				int originalBucketIndex = Math.abs(hashCode) % newArray.length;
				int currentBucketIndex  = originalBucketIndex;
				int counter             = 0;

				while (counter++ < newArray.length) {
					if (newArray[currentBucketIndex] == null) {
						newArray[currentBucketIndex] = storage[x];
						break;
					}
				}
			}
		}

		this.storage = newArray;
	}

	/**
	 * Checks if the {@link HashMap} needs an expansion with the provided number of entries. The {@link HashMap}
	 * needs to be expanded when the number of entries equals or exceeds the <code>capacity * loadFactor</code>.
	 *
	 * @param entries The number of entries to use, when checking whether or not the {@link HashMap} needs to be
	 *                expanded.
	 *
	 * @return Whether or not the {@link HashMap} needs an expansion.
	 */
	private boolean needsExpansion(int entries)
	{
		return entries >= storage.length * loadFactor;
	}

	/**
	 * Removes the mapping for a key from this map if it is present
	 * (optional operation).   More formally, if this map contains a mapping
	 * from key <tt>k</tt> to value <tt>v</tt> such that
	 * <code>(key==null ?  k==null : key.equals(k))</code>, that mapping
	 * is removed.  (The map can contain at most one such mapping.)
	 * <p>
	 * <p>Returns the value to which this map previously associated the key,
	 * or <tt>null</tt> if the map contained no mapping for the key.
	 * <p>
	 * <p>If this map permits null values, then a return value of
	 * <tt>null</tt> does not <i>necessarily</i> indicate that the map
	 * contained no mapping for the key; it's also possible that the map
	 * explicitly mapped the key to <tt>null</tt>.
	 * <p>
	 * <p>The map will not contain a mapping for the specified key once the
	 * call returns.
	 *
	 * @param key key whose mapping is to be removed from the map
	 *
	 * @return the previous value associated with <tt>key</tt>, or
	 * <tt>null</tt> if there was no mapping for <tt>key</tt>.
	 * @throws NullPointerException When the provided key is <code>null</code>.
	 */
	@Override public V remove(Object key)
	{
		if (key == null)
			throw new NullPointerException("Keys cannot be null");

		int hashCode            = key.hashCode();
		int originalBucketIndex = Math.abs(hashCode) % storage.length;
		int currentBucketIndex  = originalBucketIndex;
		int counter             = 0;

		while (counter++ < storage.length) {
			Entry<K, V> currentEntry = storage[currentBucketIndex];
			if (currentEntry != null && currentEntry.hash == hashCode && currentEntry.key.equals(key)) {
				V value = currentEntry.value;
				storage[currentBucketIndex] = null;
				size--;
				return value;
			}

			currentBucketIndex = (currentBucketIndex == storage.length - 1) ? 0 : currentBucketIndex + 1;
		}

		return null;
	}

	/**
	 * Copies all of the mappings from the specified map to this map
	 * (optional operation).  The effect of this call is equivalent to that
	 * of calling {@link #put(Object, Object) put(k, v)} on this map once
	 * for each mapping from key <tt>k</tt> to value <tt>v</tt> in the
	 * specified map.  The behavior of this operation is undefined if the
	 * specified map is modified while the operation is in progress.
	 *
	 * @param m mappings to be stored in this map
	 *
	 * @throws NullPointerException When the provided map is <code>null</code>.
	 * @throws NullPointerException When any of the provided map keys are <code>null</code>.
	 */
	@Override public void putAll(Map<? extends K, ? extends V> m)
	{
		if (m == null)
			throw new NullPointerException("Map cannot be null.");

		for (Map.Entry<? extends K, ? extends V> entry : m.entrySet()) {
			K key = entry.getKey();
			if (key == null)
				throw new NullPointerException("Null keys not allowed.");
			put(key, entry.getValue());
		}
	}

	/**
	 * Removes all of the mappings from this map (optional operation).
	 * The map will be empty after this call returns.
	 */
	@Override public void clear()
	{
		Arrays.fill(storage, null);
		size = 0;
	}

	/**
	 * Returns a {@link Set} view of the keys contained in this map.
	 * The set is backed by the map, so changes to the map are
	 * reflected in the set, and vice-versa.  If the map is modified
	 * while an iteration over the set is in progress (except through
	 * the iterator's own <tt>remove</tt> operation), the results of
	 * the iteration are undefined.  The set supports element removal,
	 * which removes the corresponding mapping from the map, via the
	 * <tt>Iterator.remove</tt>, <tt>Set.remove</tt>,
	 * <tt>removeAll</tt>, <tt>retainAll</tt>, and <tt>clear</tt>
	 * operations.  It does not support the <tt>add</tt> or <tt>addAll</tt>
	 * operations.
	 *
	 * @return a set view of the keys contained in this map
	 */
	@Override public Set<K> keySet()
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * Returns a {@link Collection} view of the values contained in this map.
	 * The collection is backed by the map, so changes to the map are
	 * reflected in the collection, and vice-versa.  If the map is
	 * modified while an iteration over the collection is in progress
	 * (except through the iterator's own <tt>remove</tt> operation),
	 * the results of the iteration are undefined.  The collection
	 * supports element removal, which removes the corresponding
	 * mapping from the map, via the <tt>Iterator.remove</tt>,
	 * <tt>Collection.remove</tt>, <tt>removeAll</tt>,
	 * <tt>retainAll</tt> and <tt>clear</tt> operations.  It does not
	 * support the <tt>add</tt> or <tt>addAll</tt> operations.
	 *
	 * @return a collection view of the values contained in this map
	 */
	@Override public Collection<V> values()
	{
		Collection<V> result = new ArrayList<>();
		for (Entry<K, V> entry : storage) {
			if (entry != null) {
				result.add(entry.value);
			}
		}

		return result;
	}

	/**
	 * Returns a {@link Set} view of the mappings contained in this map.
	 * The set is backed by the map, so changes to the map are
	 * reflected in the set, and vice-versa.  If the map is modified
	 * while an iteration over the set is in progress (except through
	 * the iterator's own <tt>remove</tt> operation, or through the
	 * <tt>setValue</tt> operation on a map entry returned by the
	 * iterator) the results of the iteration are undefined.  The set
	 * supports element removal, which removes the corresponding
	 * mapping from the map, via the <tt>Iterator.remove</tt>,
	 * <tt>Set.remove</tt>, <tt>removeAll</tt>, <tt>retainAll</tt> and
	 * <tt>clear</tt> operations.  It does not support the
	 * <tt>add</tt> or <tt>addAll</tt> operations.
	 *
	 * @return a set view of the mappings contained in this map
	 */
	@Override public Set<Map.Entry<K, V>> entrySet()
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * Returns the maximum number of storage spaces in the {@link HashMap}. The internal storage of the
	 * {@link HashMap} will expand depending on the <code>capacity</code> and the <code>loadFactor</code>.
	 *
	 * @return The <code>capacity</code>.
	 */
	public int getCapacity()
	{
		return storage.length;
	}

	/**
	 * Returns the <code>loadFactor</code>. This is the proportion of the capacity that must be filled before the
	 * internal storage of the {@link HashMap} is doubled.
	 *
	 * @return The <code>loadFactor</code>.
	 */
	public double getLoadFactor()
	{
		return this.loadFactor;
	}
}
