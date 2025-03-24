# Multi-Line Text Editor

A simple multi-line text editor implemented in Java, supporting basic text editing functionalities like insertion, deletion, movement, undo, and redo.

## Features

- **Insert text** at the current cursor position.
- **Multiline insert** with multiple new lines.
- **Delete characters** at a user-selected position.
- **Move the cursor** left, right, up, and down.
- **Undo & Redo** previous actions.
- **Support for new lines** to structure text.

## How to Use

When the program starts, you will be prompted to enter commands to manipulate the text.

### Commands

| Command | Description                                        |
| ------- | -------------------------------------------------- |
| `I`     | Insert text at the current cursor position         |
| `M`     | Insert multiple lines (Enter empty line to finish) |
| `D`     | Delete a character at a user-selected position     |
| `L`     | Move cursor left                                   |
| `R`     | Move cursor right                                  |
| `W`     | Move cursor up                                     |
| `S`     | Move cursor down                                   |
| `N`     | Insert a new line at the current position          |
| `U`     | Undo the last action                               |
| `Y`     | Redo the last undone action                        |

### Inserting Text

1. Type `I` and press Enter.
2. Enter the text you want to insert.
3. Press Enter to insert at the current cursor position.

### Inserting Multiple Lines

1. Type `M` and press Enter.
2. Enter multiple lines (Press Enter on an empty line to stop).
3. The text will be inserted line by line.

### Deleting a Character

1. Type `D` and press Enter.
2. Move the cursor using `L`, `R`, `W`, `S` to the position you want to delete.
3. Press `D` again to delete the character at that position.
4. Any other key cancels the deletion.

### Moving the Cursor

- `L` moves left.
- `R` moves right.
- `W` moves up.
- `S` moves down.

### Undo & Redo

- `U` to undo the last action.
- `Y` to redo an undone action.

### Example Usage

```
Enter command: I
Enter text to insert: Hello
------ Text Editor ------
Hello|
-------------------------
Enter command: N
------ Text Editor ------
Hello
|
-------------------------
Enter command: I
Enter text to insert: World
------ Text Editor ------
Hello
World|
-------------------------
Enter command: D
Use L, R, W, S to move the cursor to the position you want to delete.
Press 'D' again to delete at the new position, or any other key to cancel.

Enter command: L
------ Text Editor ------
Hello
Worl|d
-------------------------
Now press 'D' to delete at this new position or move again.

Enter command: D
------ Text Editor ------
Hello
Wor|d
-------------------------
```

## Running the Program

1. Compile the Java program:
   ```sh
   javac MultiLineTextEditor.java
   ```
2. Run the program:
   ```sh
   java MultiLineTextEditor
   ```





