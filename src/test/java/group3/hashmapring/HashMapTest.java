package group3.hashmapring;

import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.*;

public class HashMapTest
{

	private HashMap<Integer, Integer> instance;

	@Before
	public void setUp()
	{
		this.instance = new HashMap<>();
	}

	@Test
	public void size() throws Exception
	{
		assertEquals(0, instance.size());
		instance.put(0, 0);
		assertEquals(1, instance.size());
		instance.put(0, 0);
		assertEquals(1, instance.size());
		instance.put(1, 0);
		assertEquals(2, instance.size());
	}


	@Test
	public void isEmpty() throws Exception
	{
		assertTrue(instance.isEmpty());
		instance.put(0, 0);
		assertFalse(instance.isEmpty());
	}

	@Test
	public void containsKey() throws Exception
	{
		assertFalse(instance.containsKey(0));
		instance.put(0, 0);
		assertTrue(instance.containsKey(0));
	}

	@Test(expected = NullPointerException.class)
	public void containsKeyThrowsNullPointerException() throws Exception
	{
		instance.containsKey(null);
	}

	@Test
	public void containsValue() throws Exception
	{
		assertFalse(instance.containsValue(1));
		instance.put(0, 1);
		assertTrue(instance.containsValue(1));
		instance.remove(0);
		assertFalse(instance.containsValue(1));
	}

	@Test
	public void get() throws Exception
	{
		assertNull(instance.get(0));
		instance.put(0, 15);
		assertEquals(15, (long) instance.get(0));
	}

	@Test(expected = NullPointerException.class)
	public void getThrowsNullPointerException() throws Exception
	{
		instance.get(null);
	}

	@Test
	public void put() throws Exception
	{
//		instance.put(0, 156);
//		assertEquals(156, (long) instance.get(0));
//		assertEquals(156, (long) instance.put(0, 200));

		instance = new HashMap<>(10);
		instance.put(2, 1);
		instance.put(12, 2);
		instance.put(22, 3);
		instance.put(32, 4);
		instance.remove(12);
		instance.put(32, 5);
		instance.clear();
	}

	@Test(expected = NullPointerException.class)
	public void putThrowsNullPointerException() throws Exception
	{
		instance.put(null, 1);
	}

	@Test
	public void remove() throws Exception
	{
		instance.put(0, 250);
		assertFalse(instance.isEmpty());
		assertEquals(250, (long) instance.remove(0));
		assertTrue(instance.isEmpty());
	}

	@Test(expected = NullPointerException.class)
	public void removeThrowsNullPointerException() throws Exception
	{
		instance.remove(null);
	}

	@Test
	public void putAll() throws Exception
	{
		java.util.HashMap<Integer, Integer> parameter = new java.util.HashMap<>();
		parameter.put(0, 0);
		parameter.put(1, 1);
		parameter.put(2, 2);

		assertEquals(0, instance.size());
		instance.putAll(parameter);
		assertEquals(3, instance.size());
	}

	@Test(expected = NullPointerException.class)
	public void putAllThrowsNullPointerException()
	{
		instance.putAll(null);
	}

	@Test
	public void clear() throws Exception
	{
		instance.put(0, 0);
		instance.put(1, 1);

		assertFalse(instance.isEmpty());
		assertEquals(2, instance.size());

		instance.clear();

		assertTrue(instance.isEmpty());
		assertEquals(0, instance.size());
	}

	@Test
	public void values() throws Exception
	{
		instance.put(0, 0);
		instance.put(1, 1);
		instance.put(2, 2);
		instance.put(9, 9);

		Collection<Integer> values = instance.values();

		assertTrue(values.contains(0));
		assertTrue(values.contains(1));
		assertTrue(values.contains(2));
		assertTrue(values.contains(9));
	}

	@Test
	public void getCapacity() throws Exception
	{
		assertEquals(16, instance.getCapacity());
		instance = new HashMap<>(32);
		assertEquals(32, instance.getCapacity());
	}

	@Test
	public void getLoadFactor() throws Exception
	{
		assertEquals(0.75, instance.getLoadFactor(), 0.0001);
		instance = new HashMap<>(0.1);
		assertEquals(0.1, instance.getLoadFactor(), 0.0001);
	}

	@Test
	public void canHandleCollisions()
	{
		instance = new HashMap<>(10);

		instance.put(7, 1);
		instance.put(17, 2);
		instance.put(27, 3);
		instance.put(37, 4);
		instance.put(47, 5);

		assertEquals(1, (long) instance.get(7));
		assertEquals(2, (long) instance.get(17));
		assertEquals(3, (long) instance.get(27));
		assertEquals(4, (long) instance.get(37));
		assertEquals(5, (long) instance.get(47));

		assertEquals(5, instance.size());
	}

	@Test
	public void canExpand()
	{
		instance = new HashMap<>(4);
		assertEquals(4, instance.getCapacity());
		instance.put(1, 1);
		instance.put(2, 1);
		instance.put(3, 1);
		assertEquals(8, instance.getCapacity());
	}
}