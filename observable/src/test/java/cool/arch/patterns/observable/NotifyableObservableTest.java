/**
 * 
 */
package cool.arch.patterns.observable;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 */
public class NotifyableObservableTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link cool.arch.patterns.observable.NotifyableObservable#notifyObservers()}.
	 */
	@Test
	public final void testNotifyObservers() {
	}

	/**
	 * Test method for
	 * {@link cool.arch.patterns.observable.NotifyableObservable#notifyObservers(java.lang.Object)}
	 * .
	 */
	@Test
	public final void testNotifyObserversT() {
	}

	/**
	 * Test method for
	 * {@link cool.arch.patterns.observable.NotifyableObservable#create(java.lang.Class)}.
	 */
	@Test
	public final void testCreate() {
		final NotifyableObservable observable = NotifyableObservable.create(Object.class);
		
		assertNotNull(observable);
	}

	/**
	 * Test method for
	 * {@link cool.arch.patterns.observable.Observable#addObserver(cool.arch.patterns.observable.Observer)}
	 * .
	 */
	@Test
	public final void testAddObserver() {
	}

	/**
	 * Test method for
	 * {@link cool.arch.patterns.observable.Observable#removeObserver(cool.arch.patterns.observable.Observer)}
	 * .
	 */
	@Test
	public final void testRemoveObserver() {
	}

	/**
	 * Test method for {@link cool.arch.patterns.observable.Observable#clear()}.
	 */
	@Test
	public final void testClear() {
	}

	/**
	 * Test method for {@link cool.arch.patterns.observable.Observable#getPublishedType()}.
	 */
	@Test
	public final void testGetPublishedType() {
	}
}
