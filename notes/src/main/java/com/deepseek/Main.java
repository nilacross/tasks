package com.deepseek;

import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final NoteManager noteManager = new NoteManager();

    static void main() {
        System.out.println("===== Note Management System =====");

        while (true) {
            printMenu();
            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    addNoteFlow();
                    break;
                case "2":
                    noteManager.showAllNotes();
                    break;
                case "3":
                    findNoteFlow();
                    break;
                case "4":
                    deleteNoteFlow();
                    break;
                case "5":
                    System.out.println("Good by!");
                    return;
                default:
                    System.out.println("Invalid input! Try again!");

            }
            System.out.println();
        }
    }

    private static void printMenu() {
        System.out.println("Choose an option:");
        System.out.println("1. Add note");
        System.out.println("2. Show all notes");
        System.out.println("3. Find note");
        System.out.println("4. Delete note");
        System.out.println("5. Exit");
        System.out.println(">");
    }

    public static void addNoteFlow() {
        System.out.println("Enter title:");
        String title = scanner.nextLine();
        System.out.println("Enter note content:");
        String note = scanner.nextLine();
        noteManager.addNote(new Note(title, note));
    }

    public static void findNoteFlow() {
        System.out.println("Enter word or text part for search:");
        String notePart = scanner.nextLine();
        noteManager.findNote(notePart);
    }

    public static void deleteNoteFlow() {
        System.out.println("Enter exact note title for delete:");
        String title = scanner.nextLine();
        noteManager.deleteNote(title);
    }
}
