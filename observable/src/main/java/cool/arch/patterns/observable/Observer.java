package cool.arch.patterns.observable;

public interface Observer<T> {
	
	void onPublished(T published);
	
}
