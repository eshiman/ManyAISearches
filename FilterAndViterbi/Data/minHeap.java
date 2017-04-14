
package Data;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import Grid.gridSquare;
/*
 * Really a max heap... just didn't feel like changing the title
 */
public class minHeap {

	/* Mode: min or max heap */
	final static int MIN_HEAP = 1;
	final static int MAX_HEAP = 2;

	private ArrayList<gridSquare> items;

	public minHeap() {
		items = new ArrayList<gridSquare>();
	}

	private void siftUp() {
		int k = items.size() - 1; // index of last item (just inserted)
		while (k > 0) {
			int p = (k - 1) / 2; // k's parent index
			gridSquare kid = items.get(k);
			gridSquare parent = items.get(p);
			if (kid.compareTo(parent) < 0) {
				// swap kid with parent
				items.set(k, parent);
				items.set(p, kid);
				// move up one level
				k = p;
			} else {
				break;
			}
		}
	}

	public void insert(gridSquare item) {
		items.add(item);
		siftUp();
	}

	private void siftDown() {
		int k = 0; // root index
		int l = 2 * k + 1; // left child index
		while (l < items.size()) {
			int r = l + 1; // right child index
			int max = l;
			if (r < items.size() && (items.get(r).compareTo(items.get(l)) < 0)) {
				max = r;
			}
			if (items.get(max).compareTo(items.get(k)) < 0) {
				// swap
				gridSquare parent = items.get(k);
				items.set(k, items.get(max));
				items.set(max, parent);
				// move one level down
				k = max;
				l = 2 * k + 1;
			} else {
				break;
			}
		}
	}

	public gridSquare delete() {

		if (items.size() == 0) {
			throw new NoSuchElementException();
		}
		if (items.size() == 1) {
			return items.remove(0);
		}
		gridSquare root = items.get(0); // get the root
		gridSquare lastItem = items.remove(items.size() - 1);
		items.set(0, lastItem); // move the last item into the root
		siftDown();
		return root;
	}

	public void delete(gridSquare V) {

		if (items.size() == 0) {
			throw new NoSuchElementException();
		}
		if (items.size() == 1) {
			items.remove(0);
			return;
		}
		for (int j = 0; j < items.size(); j++) {
			if (items.get(j) == V) {
				gridSquare lastItem = items.get(items.size() - 1);
				items.set(j, lastItem);
				items.remove(items.size()-1);
				siftDown();
			}
		}
		return;
	}

	public boolean isEmpty() {
		return items.isEmpty();
	}

	public gridSquare peek() {
		return this.items.get(0);
	}
	
	public int size(){
		return this.items.size();
	}

	public void print() {
		for (int i = 0; i < this.items.size(); i++) {
			//System.out.print(this.items.get(i).getKey(0) + " ");
		}
		System.out.println("");
	}

	public boolean Contains(gridSquare s) {
		if (this.items.contains(s)) {
			return true;
		}
		return false;
	}
}
