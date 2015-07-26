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

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

class NotifyableObservableImpl<T> implements NotifyableObservable<T> {

	private Class<T> publishedType;

	private AtomicReference<Segment<Consumer<T>>> observers = new AtomicReference<>();

	private Object lock = new Object();

	NotifyableObservableImpl(final Class<T> publishedType) {
		this.publishedType = requireNonNull(publishedType, "publishedType shall not be null");
	}

	@Override
	public void addObserver(Consumer<T> observer) {
		requireNonNull(observer, "observer shall not be null");

		synchronized (lock) {
			final List<Consumer<T>> collected = collectObservers();
			collected.add(observer);
			final Segment<Consumer<T>> segments = toSegments(collected);
			observers.set(segments);
		}
	}

	@Override
	public void removeObserver(Consumer<T> observer) {
		requireNonNull(observer, "observer shall not be null");

		synchronized (lock) {
			final List<Consumer<T>> collected = collectObservers();
			collected.remove(observer);
			final Segment<Consumer<T>> segments = toSegments(collected);
			observers.set(segments);
		}
	}

	@Override
	public void clear() {
		observers.set(null);
	}

	@Override
	public Class<T> getPublishedType() {
		return publishedType;
	}

	@Override
	public void notifyObservers(T published) {
		requireNonNull(published, "published shall not be null");

		Segment<Consumer<T>> head = observers.get();

		while (head != null) {
			final Consumer<T> observer = head.getItem();
			observer.accept(published);
			head = head.getTail();
		}
	}

	private List<Consumer<T>> collectObservers() {
		final List<Consumer<T>> collected = new LinkedList<>();

		Segment<Consumer<T>> head = observers.get();

		while (head != null) {
			final Consumer<T> observer = head.getItem();
			collected.add(observer);
			head = head.getTail();
		}

		return collected;
	}

	private Segment<Consumer<T>> toSegments(final Collection<Consumer<T>> items) {
		Segment<Consumer<T>> head = null;

		for (final Consumer<T> observer : items) {
			head = new Segment<>(observer, head);
		}

		return head;
	}
}
