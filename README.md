# Project 1 - Todolist Manager

_Create a program that allows a user to add, remove, edit, and list to-do items by presenting the user with a menu similar to the following..._

## Classes Used

### Todolist Item

The task was completed using Java. A class called **TodoListItem** was created that stores information about a singular item in a todo list.

An item has a description and has a `isCompleted` status that changes.

The todolist item class has two functions: `markAsCompleted` and `toString`, where `toString` converts it to a markdown-style list format: `[x] description`.

### MenuOption

The menu options were implemented by creating a menu option class that has a callable function attached. This infrastructure allows for calling a function that is associated with the class itself.

This is used in a hashmap within the main function to allow for different extensible functionality instead of hardcoded statements.


## Main Functions

### `displayAllItemsWithIndexes`

This function displays all the items in the todo list with their respective indexes.

```
(01): [ ] Buy groceries
(02): [x] Walk the dog
(03): [ ] Read a book
```

### `addTodoItemPrompt`

This shows a prompt that adds a task to the todo list (using the `add` function from the ArrayList).

### `removeTodoItemPrompt`

This shows a prompt that removes a task from the todo list (using the `remove` function from the ArrayList).

### `updateDescriptionPrompt`

This shows a prompt that allows the user to update the description of a task (using `.description` from the TodoListItem class).

### `completeTaskPrompt`

This shows a prompt that allows the user to complete a task (using `markAsCompleted` function from the TodoListItem class).