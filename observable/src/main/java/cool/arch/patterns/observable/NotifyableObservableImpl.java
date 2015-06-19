package cool.arch.patterns.observable;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

class NotifyableObservableImpl<T> implements NotifyableObservable<T> {

	private Class<T> publishedType;

	private AtomicReference<Segment<Observer<T>>> observers;

	private Object lock = new Object();

	NotifyableObservableImpl(final Class<T> publishedType) {
		this.publishedType = requireNonNull(publishedType, "publishedType shall not be null");
	}

	@Override
	public void addObserver(Observer<T> observer) {
		synchronized (lock) {
			final List<Observer<T>> collected = collectObservers();
			collected.remove(observer);
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

	Segment<Observer<T>> toSegments(final Collection<Observer<T>> items) {
		Segment<Observer<T>> head = null;

		for (final Observer<T> observer : items) {
			head = new Segment<>(observer, head);
		}

		return head;
	}
}
