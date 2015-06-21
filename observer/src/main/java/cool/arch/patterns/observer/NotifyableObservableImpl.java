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

class NotifyableObservableImpl<T> implements NotifyableObservable<T> {

	private Class<T> publishedType;

	private AtomicReference<Segment<Observer<T>>> observers = new AtomicReference<>();

	private Object lock = new Object();

	NotifyableObservableImpl(final Class<T> publishedType) {
		this.publishedType = requireNonNull(publishedType, "publishedType shall not be null");
	}

	@Override
	public void addObserver(Observer<T> observer) {
		synchronized (lock) {
			final List<Observer<T>> collected = collectObservers();
			collected.add(observer);
			final Segment<Observer<T>> segments = toSegments(collected);
			observers.set(segments);
		}
	}

	@Override
	public void removeObserver(Observer<T> observer) {
		synchronized (lock) {
			final List<Observer<T>> collected = collectObservers();
			collected.remove(observer);
			final Segment<Observer<T>> segments = toSegments(collected);
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
	public void notifyObservers() {
		notifyObservers(null);
	}

	@Override
	public void notifyObservers(T published) {
		Segment<Observer<T>> head = observers.get();

		while (head != null) {
			final Observer<T> observer = head.getItem();

			if (observer != null) {
				observer.onPublished(published);
			}

			head = head.getTail();
		}
	}

	private List<Observer<T>> collectObservers() {
		final List<Observer<T>> collected = new LinkedList<>();

		Segment<Observer<T>> head = observers.get();

		while (head != null) {
			final Observer<T> observer = head.getItem();

			if (observer != null) {
				collected.add(observer);
			}

			head = head.getTail();
		}

		return collected;
	}

	private Segment<Observer<T>> toSegments(final Collection<Observer<T>> items) {
		Segment<Observer<T>> head = null;

		for (final Observer<T> observer : items) {
			head = new Segment<>(observer, head);
		}

		return head;
	}
}
