import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

public class TodoListItems implements Iterable<TodoListItem> {
    private final LinkedList<TodoListItem> items;

    public TodoListItems() {
        this.items = new LinkedList<>();
    }

    public void add(TodoListItem item) {
        items.add(item);
        Collections.sort(items);
    }

    public void remove(int index) {
        if (index >= 0 && index < items.size()) {
            items.remove(index);
        }
    }

    public TodoListItem get(int index) {
        if (index >= 0 && index < items.size()) {
            return items.get(index);
        }
        return null;
    }

    public int size() {
        return items.size();
    }

    @Override
    public Iterator<TodoListItem> iterator() {
        return items.iterator();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

}
