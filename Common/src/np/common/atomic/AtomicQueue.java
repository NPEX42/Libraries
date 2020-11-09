package np.common.atomic;

import java.util.Collection;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author george
 * @param <E>
 */
public class AtomicQueue<E> implements Queue<E> {

	private LinkedList<E> list = new LinkedList<>();
	
	@Override
	public synchronized boolean addAll(Collection<? extends E> collection) {
		return list.addAll(collection);
	}

	@Override
	public synchronized void clear() {
		list.clear();
	}

	@Override
	public synchronized boolean contains(Object o) {
		return list.contains(o);
	}

	@Override
	public synchronized boolean containsAll(Collection<?> arg0) {
		return list.containsAll(arg0);
	}

	@Override
	public synchronized boolean isEmpty() {
		return list.isEmpty();
	}

	@Override
	public synchronized Iterator<E> iterator() {
		return list.iterator();
	}

	@Override
	public synchronized boolean remove(Object arg0) {
		return list.remove(arg0);
	}

	@Override
	public synchronized boolean removeAll(Collection<?> arg0) {
		return list.removeAll(arg0);
	}

	@Override
	public synchronized boolean retainAll(Collection<?> arg0) {
		return list.retainAll(arg0);
	}

	@Override
	public synchronized int size() {
		return list.size();
	}

	@Override
	public synchronized Object[] toArray() {
		return list.toArray();
	}

	@Override
	public synchronized <T> T[] toArray(T[] arg0) {
		return list.toArray(arg0);
	}

	@Override
	public synchronized boolean add(E arg0) {
		return list.add(arg0);
	}

	@Override
	public synchronized E element() {
		return list.element();
	}

	@Override
	public synchronized boolean offer(E arg0) {
		return list.offer(arg0);
	}

	@Override
	public synchronized E peek() {
		return list.peek();
	}

	@Override
	public synchronized E poll() {
		return list.poll();
	}

	@Override
	public synchronized E remove() {
		return list.remove();
	}
	
	public synchronized E dequeue() {
		return list.pollFirst();
	}
	
	public synchronized void enqueue(E e) {
		list.addLast(e);
	}
	
}
