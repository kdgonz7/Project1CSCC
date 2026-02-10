public class TodoListItem {
    String title;
    String description;
    boolean isCompleted;
    int priority = 0; // 0 = no/low, 5 = high

    public TodoListItem(String description) {
        this.description = description;
        this.isCompleted = false;
        this.priority = 0;
    }

    // keep backward compat with old constructor
    public TodoListItem(String description, String title, int priority) {
        this.title = title;
        this.description = description;
        this.isCompleted = false;
        this.priority = priority;
    }


    public void setPriority(int number) {
        if (number < 0) {
            this.priority = 0;
        } else this.priority = Math.min(number, 5);
    }

    public int getPriority() {
        return priority;
    }

    public void markAsCompleted() {
        this.isCompleted = true;
    }

    @Override
    public String toString() {
        return (isCompleted ? "[x] " : "[ ] ") + title + "\n\t" + description;
    }
}
