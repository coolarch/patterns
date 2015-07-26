/**
 * 
 */
package cool.arch.patterns.observer;

/*
 * #%L Patterns - Observable %% Copyright (C) 2015 CoolArch %% Licensed to the Apache Software
 * Foundation (ASF) under one or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information regarding copyright ownership. The ASF
 * licenses this file to you under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in
 * writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License. #L%
 */

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 */
public class NotifyableObservableTest {

	private NotifyableObservable<Object> specimen;

	private final CountingConsumer<Object> observer0 = new CountingConsumer<>();

	private final CountingConsumer<Object> observer1 = new CountingConsumer<>();

	private final CountingConsumer<Object> observer2 = new CountingConsumer<>();

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		specimen = NotifyableObservable.Factory.create(Object.class);
	}

	/**
	 * Test method for
	 * {@link cool.arch.patterns.observer.NotifyableObservable#notifyObservers(java.lang.Object)}
	 * .
	 */
	@Test
	public final void testNotifyObservers() {
		final Object publishable = new Object();

		specimen.addObserver(observer0);
		specimen.addObserver(observer1);
		specimen.addObserver(observer2);
		specimen.notifyObservers(publishable);

		final List<Object> observed0 = observer0.getPublishedObjects();
		final List<Object> observed1 = observer1.getPublishedObjects();
		final List<Object> observed2 = observer2.getPublishedObjects();

		assertEquals(1, observed0.size());
		assertEquals(1, observed1.size());
		assertEquals(1, observed2.size());

		assertSame(publishable, observed0.get(0));
		assertSame(publishable, observed1.get(0));
		assertSame(publishable, observed2.get(0));
	}

	/**
	 * Test method for
	 * {@link cool.arch.patterns.observer.NotifyableObservable#create(java.lang.Class)}.
	 */
	@Test
	public final void testCreate() {
		assertNotNull(specimen);
	}

	@SuppressWarnings("unused")
	@Test(
		expected = UnsupportedOperationException.class)
	public final void testFactoryInstantiation() {
		new NotifyableObservable.Factory();
	}

	/**
	 * Test method for
	 * {@link cool.arch.patterns.observer.Observable#addObserver(cool.arch.patterns.observer.Observer)}
	 * .
	 */
	@Test
	public final void testAddObserver() {
		final Object publishable = new Object();

		specimen.addObserver(observer0);
		specimen.notifyObservers(publishable);

		final List<Object> observed0 = observer0.getPublishedObjects();
		final List<Object> observed1 = observer1.getPublishedObjects();
		final List<Object> observed2 = observer2.getPublishedObjects();

		assertEquals(1, observed0.size());
		assertTrue(observed1.isEmpty());
		assertTrue(observed2.isEmpty());

		assertSame(publishable, observed0.get(0));
	}

	/**
	 * Test method for
	 * {@link cool.arch.patterns.observer.Observable#removeObserver(cool.arch.patterns.observer.Observer)}
	 * .
	 */
	@Test
	public final void testRemoveObserver() {
		final Object publishable = new Object();

		specimen.addObserver(observer0);
		specimen.addObserver(observer1);
		specimen.addObserver(observer2);

		specimen.removeObserver(observer1);

		specimen.notifyObservers(publishable);

		final List<Object> observed0 = observer0.getPublishedObjects();
		final List<Object> observed1 = observer1.getPublishedObjects();
		final List<Object> observed2 = observer2.getPublishedObjects();

		assertEquals(1, observed0.size());
		assertTrue(observed1.isEmpty());
		assertEquals(1, observed2.size());

		assertSame(publishable, observed0.get(0));
		assertSame(publishable, observed2.get(0));
	}

	/**
	 * Test method for {@link cool.arch.patterns.observer.Observable#clear()}.
	 */
	@Test
	public final void testClear() {
		final Object publishable = new Object();

		specimen.addObserver(observer0);
		specimen.addObserver(observer1);
		specimen.addObserver(observer2);

		specimen.clear();

		specimen.notifyObservers(publishable);

		final List<Object> observed0 = observer0.getPublishedObjects();
		final List<Object> observed1 = observer1.getPublishedObjects();
		final List<Object> observed2 = observer2.getPublishedObjects();

		assertTrue(observed0.isEmpty());
		assertTrue(observed1.isEmpty());
		assertTrue(observed2.isEmpty());
	}

	/**
	 * Test method for {@link cool.arch.patterns.observer.Observable#getPublishedType()}.
	 */
	@Test
	public final void testGetPublishedType() {
		assertSame(Object.class, specimen.getPublishedType());
	}

	public static final class CountingConsumer<T> implements Consumer<T> {

		private final List<T> publishedObjects = new LinkedList<>();

		@Override
		public void accept(T published) {
			publishedObjects.add(published);
		}

		/**
		 * @return the publishedObjects
		 */
		public final List<T> getPublishedObjects() {
			return publishedObjects;
		}
	}
}
