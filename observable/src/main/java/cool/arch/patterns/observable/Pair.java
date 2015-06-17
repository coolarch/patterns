package cool.arch.patterns.observable;

class Pair<T,U> {
	
	private final T item0;
	
	private final U item1;
	
	Pair(final T item0, final U item1) {
		this.item0 = item0;
		this.item1 = item1;
	}

	/**
	 * @return the item0
	 */
	public final T getItem0() {
		return item0;
	}

	/**
	 * @return the item1
	 */
	public final U getItem1() {
		return item1;
	}
}
