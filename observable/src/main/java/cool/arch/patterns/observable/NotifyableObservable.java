package cool.arch.patterns.observable;

public interface NotifyableObservable<T> extends Observable<T> {

	void notifyObservers();

	void notifyObservers(T arg);
	
	static <T> NotifyableObservable<T> create(final Class<T> publishedType) {
		return new NotifyableObservableImpl<>(publishedType);
	}

}
