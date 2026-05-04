public class TodoListItem implements Comparable<TodoListItem> {
    private String title;
    private String description;
    private boolean isCompleted;
    private int priority = 0; // 0 = no/low, 5 = high

    public TodoListItem() {
    }

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
        this.priority = Math.clamp(priority, 0, 5);
    }


    public void setPriority(int number) {
        if (number > 5) {
            this.priority = 5;
        } else this.priority = Math.max(number, 0);
    }

    public int getPriority() {
        return priority;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public void markAsCompleted() {
        this.isCompleted = true;
    }

    @Override
    public String toString() {
        return (isCompleted ? "[x] " : "[ ] ") + title + "\n\t" + description;
    }


    @Override
    public int compareTo(TodoListItem o) {
        if (this.title.equals(o.title) && this.priority == o.priority) {
            return 0;
        }

        return Integer.compare(this.priority, o.priority);
    }
}
