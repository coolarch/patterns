package cool.arch.patterns.observable;

import static java.util.Objects.requireNonNull;

class Segment<T> {
	
	private final T item;
	
	private final Segment<T> tail;
	
	Segment(final T item, final Segment<T> tail) {
		this.item = requireNonNull(item, "item shall not be null");
		this.tail = tail;
	}

	/**
	 * @return the item
	 */
	public final T getItem() {
		return item;
	}

	/**
	 * @return the tail
	 */
	public final Segment<T> getTail() {
		return tail;
	}
}
