import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ListMaker {
    private static ArrayList<String> myList = new ArrayList<>();
    private static final Scanner console = new Scanner(System.in);
    private static boolean needsToBeSaved = false;
    private static String currentFile = null;

    public static void main(String[] args) {
        boolean keepGoing = true;

        while (keepGoing) {
            displayMenu();
            char choice = SafeInput.getRegExString(console, "Enter a command: [AaDdIiMmOoPpQqSsVvCc]", "[AaDdIiMmOoPpQqSsVvCc]").charAt(0);
            switch (Character.toLowerCase(choice)) {
                case 'a':
                    addItem();
                    break;
                case 'd':
                    deleteItem();
                    break;
                case 'i':
                    insertItem();
                    break;
                case 'm':
                    moveItem();
                    break;
                case 'o':
                    openList();
                    break;
                case 's':
                    saveList();
                    break;
                case 'c':
                    clearList();
                    break;
                case 'v':
                    printList();
                    break;
                case 'q':
                    if (needsToBeSaved) {
                        keepGoing = !SafeInput.getYNConfirm(console, "You have unsaved changes. Are you sure you want to quit without saving? (y/n): ");
                    } else {
                        keepGoing = false;
                    }
                    break;
            }
        }
    }

    private static void displayMenu() {
        System.out.println("\nMenu Options:");
        System.out.println("A - Add an item");
        System.out.println("D - Delete an item");
        System.out.println("I - Insert an item");
        System.out.println("M - Move an item");
        System.out.println("O - Open a list file from disk");
        System.out.println("S - Save the current list file to disk");
        System.out.println("C - Clear all items");
        System.out.println("V - View the list");
        System.out.println("Q - Quit the program");
    }

    private static void addItem() {
        String item = SafeInput.getString(console, "Enter the item to add: ");
        myList.add(item);
        needsToBeSaved = true;
        System.out.println("Item added.");
    }

    private static void deleteItem() {
        if (myList.isEmpty()) {
            System.out.println("List is empty. Nothing to delete.");
            return;
        }
        printListWithNumbers();
        int index = SafeInput.getRangedInt(console, "Enter the number of the item to delete: ", 1, myList.size()) - 1;
        myList.remove(index);
        needsToBeSaved = true;
        System.out.println("Item deleted.");
    }

    private static void insertItem() {
        if (myList.isEmpty()) {
            System.out.println("List is empty. Adding at the end.");
            addItem();
            return;
        }
        printListWithNumbers();
        int index = SafeInput.getRangedInt(console, "Enter the number where you want to insert the item: ", 1, myList.size() + 1) - 1;
        String item = SafeInput.getString(console, "Enter the item to insert: ");
        myList.add(index, item);
        needsToBeSaved = true;
        System.out.println("Item inserted.");
    }

    private static void moveItem() {
        if (myList.isEmpty()) {
            System.out.println("List is empty. Nothing to move.");
            return;
        }
        printListWithNumbers();
        int fromIndex = SafeInput.getRangedInt(console, "Enter the number of the item to move: ", 1, myList.size()) - 1;
        int toIndex = SafeInput.getRangedInt(console, "Enter the number where you want to move the item to: ", 1, myList.size()) - 1;
        String item = myList.remove(fromIndex);
        myList.add(toIndex, item);
        needsToBeSaved = true;
        System.out.println("Item moved.");
    }

    private static void openList() {
        if (needsToBeSaved) {
            if (SafeInput.getYNConfirm(console, "You have unsaved changes. Do you want to save before opening a new list? (y/n): ")) {
                saveList();
            }
        }
        String filename = SafeInput.getString(console, "Enter the filename to open: ");
        try {
            myList = FileUtils.readFile(filename);
            currentFile = filename;
            needsToBeSaved = false;
            System.out.println("List loaded from " + filename + ".txt");
        } catch (IOException e) {
            System.out.println("Error opening file: " + e.getMessage());
        }
    }

    private static void saveList() {
        if (currentFile == null) {
            currentFile = SafeInput.getString(console, "Enter the filename to save as: ");
        }
        try {
            FileUtils.writeFile(currentFile, myList);
            needsToBeSaved = false;
            System.out.println("List saved to " + currentFile + ".txt");
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    private static void clearList() {
        myList.clear();
        needsToBeSaved = true;
        System.out.println("All items cleared.");
    }

    private static void printList() {
        if (myList.isEmpty()) {
            System.out.println("The list is empty.");
            return;
        }
        System.out.println("\nList:");
        for (String item : myList) {
            System.out.println("- " + item);
        }
    }

    private static void printListWithNumbers() {
        System.out.println("\nList:");
        for (int i = 0; i < myList.size(); i++) {
            System.out.println((i + 1) + ": " + myList.get(i));
        }
    }
}
