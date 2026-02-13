import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Scanner;

class Main {
    ArrayList<TodoListItem> items;
    Scanner scanner = new Scanner(System.in);


    public Void displayAllItemsWithIndexes(boolean pause) {
        if (items == null || items.isEmpty()) {
            System.out.println("No todo list items found.");
            return null;
        }

        for (int i = 0; i < items.size(); i++) {
            System.out.println("(" + i + 1 + "): " + items.get(i).toString());
        }

        if (pause) {
            System.out.println("Press Enter to continue...");
            scanner.nextLine();
        }

        return null;
    }

    void main() {
        LinkedHashMap<Integer, MenuOption> menuOptions = new LinkedHashMap<>();

        menuOptions.put(0, new MenuOption(() -> {
            System.exit(0);
            return null;
        }, "Exit."));

        menuOptions.put(1, new MenuOption(this::addTodoItemPrompt, "Add Todo List Item"));
        menuOptions.put(2, new MenuOption(this::removeTodoItemPrompt, "Remove Todo List Item"));
        menuOptions.put(3, new MenuOption(this::updateDescriptionPrompt, "Update Todo List Item Description"));
        menuOptions.put(4, new MenuOption(() -> displayAllItemsWithIndexes(true), "Display All Todo List Items"));
        menuOptions.put(5, new MenuOption(this::completeTaskPrompt, "Complete a Task"));

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Menu Options:");
            for (var entry : menuOptions.entrySet()) {
                System.out.println("(" + entry.getKey() + "): " + entry.getValue().toString());
            }
            System.out.println("Select an option:");

            var input = scanner.nextLine();
            var selectedOption = Integer.parseInt(input);

            if (menuOptions.containsKey(selectedOption)) {
                try {
                    menuOptions.get(selectedOption).callable.call();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }
    }

    public Void addTodoItemPrompt() {
        System.out.println("What is the description of the new todo list item?");
        var desc = scanner.nextLine();

        if (items == null) {
            items = new ArrayList<>();
        }

        items.add(new TodoListItem(desc));

        return null;
    }

    public Void removeTodoItemPrompt() {
        if (items == null || items.isEmpty()) {
            System.out.println("No todo list items to remove.");
            return null;
        }
        displayAllItemsWithIndexes(false);

        System.out.println("Which item would you like to remove?");

        var removeIndexStr = scanner.nextLine();
        var removeIndex = Integer.parseInt(removeIndexStr);

        while (removeIndex < 1 || removeIndex > items.size()) {
            System.out.println("Invalid index. Please enter a number between 1 and " + items.size() + ":");
            removeIndexStr = scanner.nextLine();
            removeIndex = Integer.parseInt(removeIndexStr);
        }

        items.remove(removeIndex - 1);

        return null;

    }

    private Void updateDescriptionPrompt() {
        displayAllItemsWithIndexes(false);

        System.out.println("Which description would you like to update?");

        var updateIndexStr = scanner.nextLine();
        var updateIndex = Integer.parseInt(updateIndexStr) - 1;

        if (items != null && updateIndex - 1 >= 0 && updateIndex - 1 < items.size()) {
            System.out.println("Enter the new description:");
            items.get(updateIndex - 1).description = scanner.nextLine();
        }
        return null;
    }

    private Void completeTaskPrompt() {
        displayAllItemsWithIndexes(false);
        System.out.println("Which task would you like to complete?");

        var updateIndexStr = scanner.nextLine();
        var updateIndex = Integer.parseInt(updateIndexStr);

        if (items != null && updateIndex - 1 >= 0 && updateIndex - 1 < items.size()) {
            items.get(updateIndex - 1).markAsCompleted();
        }

        return null;
    }
}