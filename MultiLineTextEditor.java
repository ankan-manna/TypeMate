import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class MultiLineTextEditor {
    private List<StringBuilder> lines;
    private int cursorRow;
    private int cursorCol;
    private Stack<List<StringBuilder>> undoStack;
    private Stack<List<StringBuilder>> redoStack;

    public MultiLineTextEditor() {
        lines = new LinkedList<>();
        lines.add(new StringBuilder());
        cursorRow = 0;
        cursorCol = 0;
        undoStack = new Stack<>();
        redoStack = new Stack<>();
    }

    private void saveState() {
        List<StringBuilder> stateCopy = new LinkedList<>();
        for (StringBuilder line : lines) {
            stateCopy.add(new StringBuilder(line));
        }
        undoStack.push(stateCopy);
        redoStack.clear();
    }

    public void insert(String text) {
        saveState();
        lines.get(cursorRow).insert(cursorCol, text);
        cursorCol += text.length();
        displayText();
    }

    public void insertMultipleLines(List<String> texts) {
        saveState();
        StringBuilder currentLine = lines.get(cursorRow);
        String remainingText = currentLine.substring(cursorCol);
        currentLine.delete(cursorCol, currentLine.length());

        for (String text : texts) {
            cursorRow++;
            lines.add(cursorRow, new StringBuilder(text));
        }
        
        cursorRow++;
        lines.add(cursorRow, new StringBuilder(remainingText));
        cursorCol = 0;
        displayText();
    }

    public void delete(Scanner scanner) {
        System.out.println("Use L, R, W, S to move the cursor to the position you want to delete.");
        System.out.println("Press 'D' again to delete at the new position, or any other key to cancel.");
    
        while (true) {
            String command = scanner.nextLine().trim();
    
            if (command.equalsIgnoreCase("D")) {
                if (cursorRow >= lines.size() || cursorCol >= lines.get(cursorRow).length()) {
                    System.out.println("Nothing to delete at this position!");
                    return;
                }
    
                saveState();
                lines.get(cursorRow).deleteCharAt(cursorCol);
    
                // Adjust cursor after deletion
                if (cursorCol > 0) {
                    cursorCol--;
                } else if (cursorRow > 0) {
                    cursorRow--;
                    cursorCol = lines.get(cursorRow).length();
                }
    
                displayText();
                return; // Exit loop after deletion
            } 
            // Move the cursor before confirming deletion
            else if (command.equalsIgnoreCase("L")) {
                moveLeft();
            } else if (command.equalsIgnoreCase("R")) {
                moveRight();
            } else if (command.equalsIgnoreCase("W")) {
                moveUp();
            } else if (command.equalsIgnoreCase("S")) {
                moveDown();
            } else {
                System.out.println("Deletion canceled.");
                return;
            }
    
            displayText();
            System.out.println("Now press 'D' to delete at this new position or move again.");
        }
    }
    
    

    public void moveLeft() {
        if (cursorCol > 0) {
            cursorCol--;
        } else if (cursorRow > 0) {
            cursorRow--;
            cursorCol = lines.get(cursorRow).length();
        }
        displayText();
    }

    public void moveRight() {
        if (cursorCol < lines.get(cursorRow).length()) {
            cursorCol++;
        } else if (cursorRow < lines.size() - 1) {
            cursorRow++;
            cursorCol = 0;
        }
        displayText();
    }

    public void moveUp() {
        if (cursorRow > 0) {
            cursorRow--;
            cursorCol = Math.min(cursorCol, lines.get(cursorRow).length());
        }
        displayText();
    }

    public void moveDown() {
        if (cursorRow < lines.size() - 1) {
            cursorRow++;
            cursorCol = Math.min(cursorCol, lines.get(cursorRow).length());
        }
        displayText();
    }

    public void newLine() {
        saveState();
        StringBuilder currentLine = lines.get(cursorRow);
        String remainingText = currentLine.substring(cursorCol);
        currentLine.delete(cursorCol, currentLine.length());
        
        cursorRow++;
        lines.add(cursorRow, new StringBuilder(remainingText));
        cursorCol = 0;
        displayText();
    }

    public void undo() {
        if (!undoStack.isEmpty()) {
            redoStack.push(new LinkedList<>(lines));
            lines = undoStack.pop();
            cursorRow = Math.min(cursorRow, lines.size() - 1);
            cursorCol = Math.min(cursorCol, lines.get(cursorRow).length());
        }
        displayText();
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            undoStack.push(new LinkedList<>(lines));
            lines = redoStack.pop();
            cursorRow = Math.min(cursorRow, lines.size() - 1);
            cursorCol = Math.min(cursorCol, lines.get(cursorRow).length());
        }
        displayText();
    }

    public void displayText() {
        System.out.println("------ Text Editor ------");
        for (int i = 0; i < lines.size(); i++) {
            if (i == cursorRow) {
                String line = lines.get(i).toString();
                System.out.println(line.substring(0, cursorCol) + "|" + line.substring(cursorCol));
            } else {
                System.out.println(lines.get(i));
            }
        }
        System.out.println("-------------------------");
    }

    public static void main(String[] args) {
        MultiLineTextEditor editor = new MultiLineTextEditor();
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("Enter command (I: Insert, M: Multiline Insert, D: Delete, L: Left, R: Right, U: Undo, Y: Redo, N: New Line, W: Up, S: Down): ");
            String command = scanner.nextLine().trim();

            switch (command) {
                case "I":
                    System.out.println("Enter text to insert: ");
                    String text = scanner.nextLine();
                    editor.insert(text);
                    break;
                case "M":
                    System.out.println("Enter multiple lines (Enter empty line to finish): ");
                    List<String> texts = new LinkedList<>();
                    while (true) {
                        String line = scanner.nextLine();
                        if (line.isEmpty()) break;
                        texts.add(line);
                    }
                    editor.insertMultipleLines(texts);
                    break;
                case "D":
                    editor.delete(scanner);
                    break;
                case "L":
                    editor.moveLeft();
                    break;
                case "R":
                    editor.moveRight();
                    break;
                case "W":
                    editor.moveUp();
                    break;
                case "S":
                    editor.moveDown();
                    break;
                case "N":
                    editor.newLine();
                    break;
                case "U":
                    editor.undo();
                    break;
                case "Y":
                    editor.redo();
                    break;
                default:
                    System.out.println("Invalid command!");
            }
        }
    }
}
