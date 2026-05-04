import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

final boolean DEBUG = false;

TodoListItems items;
Scanner scanner = new Scanner(System.in);
String[] priorities = {"No/Low", "Low-Medium", "Medium", "Medium-High", "High"};

void main() throws IOException {
    ObjectMapper m = new ObjectMapper();
    File tasksFile = new File("tasks.json");

    if (tasksFile.exists()) {
        items = m.readValue(tasksFile, new TypeReference<>() {
        });
    }

    LinkedHashMap<Integer, MenuOption> menuOptions = new LinkedHashMap<>();

    menuOptions.put(0, new MenuOption(() -> {
        m.writerWithDefaultPrettyPrinter().writeValue(tasksFile, items);
        System.exit(0);
        return null;
    }, "Exit."));
    menuOptions.put(1, new MenuOption(this::addTodoItemPrompt, "Add Todo List Item"));
    menuOptions.put(2, new MenuOption(this::removeTodoItemPrompt, "Remove Todo List Item"));
    menuOptions.put(3, new MenuOption(this::updateDescriptionPrompt, "Update Todo List Item Description"));
    menuOptions.put(4, new MenuOption(() -> displayAllItemsWithIndexes(true), "Display All Todo List Items"));
    menuOptions.put(5, new MenuOption(this::completeTaskPrompt, "Complete a Task"));
    menuOptions.put(6, new MenuOption(this::displayItemsWithPriority, "Display Items with a given priority"));

    Scanner scanner = new Scanner(System.in);

    while (true) {
        IO.println("Menu Options:");

        for (var entry : menuOptions.entrySet()) {
            IO.println("(" + entry.getKey() + "): " + entry.getValue().toString());
        }

        IO.println("Select an option:");

        var input = scanner.nextLine();
        var selectedOption = Integer.parseInt(input);

        if (menuOptions.containsKey(selectedOption)) {
            try {
                menuOptions.get(selectedOption).callable.call();
            } catch (Exception e) {
                if (DEBUG) {
                    IO.println("An error occurred with message: " + e.getMessage());
                }
                else {
                    IO.println("An error occurred while executing the selected option. Please try again.");
                }
            }
        } else {
            IO.println("Invalid option. Please try again.");
        }
    }
}

private Void displayItemsWithPriority() {
    if (items == null || items.isEmpty()) {
        IO.println("No todo list items found.");
        return null;
    }

    IO.println("What priority level would you like to filter by? (0-5, 0 = no/low, 5 = high)");

    var priorityAsked = Integer.parseInt(scanner.nextLine());
    boolean itemFound = false;

    for (int i = 0; i < items.size(); i++) {
        var todoItem = items.get(i);
        if (todoItem.getPriority() != priorityAsked) {
            continue;
        }

        itemFound = true;
        IO.println("(" + priorities[todoItem.getPriority() == 0 ? todoItem.getPriority() : todoItem.getPriority() - 1] + " Priority) (" + i + 1 + "): " + todoItem);
    }

    if (!itemFound) {
        IO.println("No items found with the specified priority.");
    }

    IO.println("Press Enter to continue...");
    scanner.nextLine();

    return null;
}
private Void displayAllItemsWithIndexes(boolean pause) {
    if (items == null || items.isEmpty()) {
        IO.println("No todo list items found.");
        return null;
    }

    for (int i = 0; i < items.size(); i++) {
        var todoItem = items.get(i);
        IO.println("(" + priorities[todoItem.getPriority() == 0 ? todoItem.getPriority() : todoItem.getPriority() - 1] + " Priority) (" + (i + 1) + "): " + todoItem);
    }

    if (pause) {
        IO.println("Press Enter to continue...");
        scanner.nextLine();
    }

    return null;
}
private Void addTodoItemPrompt() {
    IO.println("What is the title of the new todo list item?");
    var title = scanner.nextLine();

    IO.println("What is the description of the new todo list item?");
    var desc = scanner.nextLine();

    if (items == null) {
        items = new TodoListItems();
    }

    IO.println("What is the priority of this item? (0-5, 0 = no/low, 5 = high)");

    var priorityStr = scanner.nextLine();
    var isNumber = Pattern.matches("\\d+", priorityStr);

    if (!isNumber) {
        IO.println("Invalid input. Priority must be a number between 0 and 5. Setting priority to 0 (no/low) by default.");
        priorityStr = "0";
    }

    var priority = Integer.parseInt(priorityStr);
    items.add(new TodoListItem(title, desc, priority));

    return null;
}
private Void removeTodoItemPrompt() {
    if (items == null || items.isEmpty()) {
        IO.println("No todo list items to remove.");
        return null;
    }

    displayAllItemsWithIndexes(false);

    IO.println("Which item would you like to remove?");

    var removeIndexStr = scanner.nextLine();
    var removeIndex = Integer.parseInt(removeIndexStr);

    while (removeIndex < 1 || removeIndex > items.size()) {
        IO.println("Invalid index. Please enter a number between 1 and " + items.size() + ":");
        removeIndexStr = scanner.nextLine();
        removeIndex = Integer.parseInt(removeIndexStr);
    }

    items.remove(removeIndex - 1);

    return null;

}
private Void updateDescriptionPrompt() {
    displayAllItemsWithIndexes(false);

    IO.println("Which description would you like to update?");

    var updateIndexStr = scanner.nextLine();
    var updateIndex = Integer.parseInt(updateIndexStr) - 1;


    if (items != null && updateIndex >= 0 && updateIndex < items.size()) {
        IO.println("Enter the new description:");
        items.get(updateIndex).setDescription(scanner.nextLine());
    }

    return null;
}
private Void completeTaskPrompt() {
    displayAllItemsWithIndexes(false);
    IO.println("Which task would you like to complete?");

    var updateIndexStr = scanner.nextLine();
    var updateIndex = Integer.parseInt(updateIndexStr);

    if (items != null && updateIndex - 1 >= 0 && updateIndex - 1 < items.size()) {
        items.get(updateIndex - 1).markAsCompleted();
    }

    return null;
}