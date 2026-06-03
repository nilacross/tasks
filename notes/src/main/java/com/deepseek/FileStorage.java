package com.deepseek;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.prefs.Preferences;

public class FileStorage {
    private static final String FILE_NAME = "notes.dat";
    private static final Preferences prefs = Preferences.userNodeForPackage(FileStorage.class);
    private static final String PREF_STORAGE_PATH = "storage_path";
    private static String storagePath;
    private static boolean pathSelected = false;

    static {
        String savedPath = prefs.get(PREF_STORAGE_PATH, null);

        if (savedPath != null && !savedPath.isEmpty()) {
            storagePath = savedPath;
            pathSelected = true;
        } else {
            storagePath = null;
        }
    }

    private static void selectPath() {
        if (pathSelected) return;

        Scanner scanner = new Scanner(System.in);

        System.out.println("\n" + LocalizationManager.get("file.storage.setup"));
        System.out.println("\n" + LocalizationManager.get("file.storage.prompt"));
        System.out.println("\n" + LocalizationManager.get("file.storage.option1"));
        System.out.println("\n" + LocalizationManager.get("file.storage.option2"));
        System.out.println("\n" + LocalizationManager.get("file.storage.option3"));
        System.out.println("\n" + LocalizationManager.get("file.storage.choice"));

        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1":
                String jarPath = FileStorage.class.getProtectionDomain()
                        .getCodeSource()
                        .getLocation()
                        .getPath();
                try {
                    jarPath = java.net.URLDecoder.decode(jarPath, "UTF-8");
                    File jarFile = new File(jarPath);
                    if (jarFile.isFile()) {
                        storagePath = jarFile.getParent() + File.separator;
                    } else {
                        storagePath = System.getProperty("user.dir") + File.separator;
                    }
                } catch (UnsupportedEncodingException e) {
                    storagePath = System.getProperty("user.dir") + File.separator;
                }
                break;

            case "2":
                String userHome = System.getProperty("user.home");
                storagePath = userHome + File.separator + "Documents" + File.separator + "NoteApp" + File.separator;
                break;

            case "3":
                System.out.println(LocalizationManager.get("file.storage.custom_path"));
                String customPath = scanner.nextLine().trim();
                if (!customPath.endsWith(File.separator)) {
                    customPath = customPath + File.separator;
                }
                storagePath = customPath;
                break;

            default:
                storagePath = System.getProperty("user.dir") + File.separator;

        }

        File dir = new File(storagePath);
        if (!dir.exists()) {
            if (dir.mkdirs()) {
                System.out.println(LocalizationManager.get("file.storage.created_folder", storagePath));
            }
            else  {
                System.out.println(LocalizationManager.get("file.storage.failed_create"));
                storagePath = System.getProperty("user.dir") + File.separator;
            }
        }

        prefs.put(PREF_STORAGE_PATH, storagePath);
        pathSelected = true;

        System.out.println(LocalizationManager.get("file.storage.path_set", storagePath) );
        System.out.println("=======================================\n");
    }

    public static String getFilePath(){
        if (storagePath == null) {
            selectPath();
        }
        return storagePath + FILE_NAME;
    }

    // Сохранить все заметки в файл
    public static void saveNotes(List<Note> notes) {
        String filePath =  getFilePath();
        File file = new File(filePath);

        File dir = file.getParentFile();
        if (dir != null && !dir.exists()) {
            dir.mkdirs();
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(notes);
            System.out.println(LocalizationManager.get("file.save", filePath));
        } catch (IOException e) {
            System.out.println(LocalizationManager.get("file.error.save", e.getMessage()));
        }
    }


    @SuppressWarnings("unchecked")
    public static List<Note> loadNotes() {
        String filePath = getFilePath();
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println(LocalizationManager.get("file.not_found", filePath));
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            List<Note> notes = (List<Note>) ois.readObject();
            System.out.println(LocalizationManager.get("file.load", notes.size(), filePath));
            return notes;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(LocalizationManager.get("file.error.load" , e.getMessage()));
            return new ArrayList<>();
        }
    }
    public static void resetPath() {
        prefs.remove(PREF_STORAGE_PATH);
        storagePath = null;
        pathSelected = false;
        System.out.println(LocalizationManager.get("file.storage.path_reset"));
    }
}