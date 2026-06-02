package com.deepseek;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileStorage {
    private static final String FILE_NAME = "notes.dat";

    // Сохранить все заметки в файл
    public static void saveNotes(List<Note> notes) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(notes);
            System.out.println("Notes are saved in file " + FILE_NAME);
        } catch (IOException e) {
            System.out.println("Notes saving error: " + e.getMessage());
        }
    }

    // Загрузить заметки из файла
    @SuppressWarnings("unchecked")
    public static List<Note> loadNotes() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("File with notes not found. New list will be created.");
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            List<Note> notes = (List<Note>) ois.readObject();
            System.out.println("Loading " + notes.size() + " notes from file.");
            return notes;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Notes loading error " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public static void showCurrentDirectory() {
        String currentDir = System.getProperty("user.dir");
        System.out.println("Текущая директория: " + currentDir);
        System.out.println("Файл будет создан по пути: " + currentDir + File.separator + FILE_NAME);
    }
}