package com.deepseek;

import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static NoteManager manager;

    static void main() {
        // FileStorage.resetPath(); //временно, чтоб каждый раз появлялась менюшка с сохранением файла
        LocalizationManager.printCurrentLanguage(); //показываем текущий язык
        manager = new NoteManager();  //инициализация не сразу, чтобы не опередить язык

        FileStorage.ensurePathSelected();

        while (true) {
            printMenu();
            String input = scanner.nextLine().trim();

            switch (input) {
                case "0":
                    changeLanguageFlow();
                    break;
                case "1":
                    addNoteFlow();
                    break;
                case "2":
                    manager.showAllNotes();
                    break;
                case "3":
                    findNoteFlow();
                    break;
                case "4":
                    deleteNoteFlow();
                    break;
                case "5":
                    clearNoteListFlow();
                    break;
                case "6":
                    tuneFileSavingFlow();
                    break;
                case "7":
                    System.out.println(LocalizationManager.get("exit.goodbye"));
                    return;
                default:
                    System.out.println(LocalizationManager.get("error.invalid_input"));

            }
            System.out.println();
        }
    }

    private static void printMenu() {
        System.out.println(LocalizationManager.get("menu.prompt"));
        System.out.println(LocalizationManager.get("menu.option0"));
        System.out.println(LocalizationManager.get("menu.option1"));
        System.out.println(LocalizationManager.get("menu.option2"));
        System.out.println(LocalizationManager.get("menu.option3"));
        System.out.println(LocalizationManager.get("menu.option4"));
        System.out.println(LocalizationManager.get("menu.option5"));
        System.out.println(LocalizationManager.get("menu.option6"));
        System.out.println(LocalizationManager.get("menu.option7"));
        System.out.println(">");
    }

    public static void addNoteFlow() {
        System.out.println(LocalizationManager.get("add.title_prompt"));
        String title = scanner.nextLine();
        System.out.println(LocalizationManager.get("add.content_prompt"));
        String note = scanner.nextLine();
        manager.addNote(new Note(title, note));
    }

    public static void findNoteFlow() {
        System.out.println(LocalizationManager.get("find.prompt"));
        String notePart = scanner.nextLine();
        manager.findNote(notePart);
    }

    public static void deleteNoteFlow() {
        System.out.println(LocalizationManager.get("delete.prompt"));
        String title = scanner.nextLine();
        manager.deleteNote(title);
    }

    public static void clearNoteListFlow() {
        System.out.println(LocalizationManager.get("delete.clear_note_list"));
        manager.clearNoteList();
    }

    private static void
    changeLanguageFlow() {
        LocalizationManager.showLanguageMenu();
        String choice = scanner.nextLine().trim();
        if (LocalizationManager.handelLanguageChoice(choice))
            manager = new NoteManager(); //пересоздаем менеджер для обновления сообщений
        else {
            System.out.println(LocalizationManager.get("error.invalid_input"));
            changeLanguageFlow();
        }
    }

    private static void tuneFileSavingFlow() {
        if (!FileStorage.ensurePathSelected()) {
            System.out.println(LocalizationManager.get("file.storage.path_current", FileStorage.getStoragePath()));
            System.out.println(LocalizationManager.get("file.storage.path_change"));
            System.out.println(LocalizationManager.get("file.storage.path_change.option1"));
            System.out.println(LocalizationManager.get("file.storage.path_change.option2"));
            System.out.println(LocalizationManager.get("file.storage.path_change.choice"));
            String answer = scanner.nextLine().trim();
            switch (answer) {
                case "1":
                    FileStorage.resetPath();
                    FileStorage.ensurePathSelected();
                    manager.saveLoadedNotes();
                case "2":
                    return;
                default:
                    System.out.println(LocalizationManager.get("error.invalid_input"));
                    tuneFileSavingFlow();
            }
        }
    }

}
