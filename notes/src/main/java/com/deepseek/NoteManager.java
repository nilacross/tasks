package com.deepseek;

import java.util.Iterator;
import java.util.List;

public class NoteManager {
    private final List<Note> notes;

    public NoteManager() {
        System.out.println("\n=== " + LocalizationManager.get("menu.title") + "===\n");
        System.out.println(LocalizationManager.get("menu.auto_save"));
        notes = FileStorage.loadNotes();
    }

    public void addNote(Note note) {
        if (note.getTitle() == null || note.getTitle().trim().isEmpty()) {
            System.out.println(LocalizationManager.get("add.error.empty_title"));
            return;
        }

        for (Note n : notes) {
            if (n.getTitle().equalsIgnoreCase(note.getTitle())) {
                System.out.println(LocalizationManager.get("add.error.duplicate", note.getTitle()));
                return;
            }
        }
        notes.add(note);
        saveToFile(); // Автоматически сохраняем после добавления
        System.out.println(LocalizationManager.get("add.success"));
    }

    public void showAllNotes() {
        if (notes.isEmpty()) {
            System.out.println(LocalizationManager.get("show.empty"));
            return;
        }
        System.out.println(LocalizationManager.get("show.list"));

        for (int i = 0; i < notes.size(); i++) {
            Note note = notes.get(i);
            String preview = note.getContent();
            if (preview.length() > 20) preview = preview.substring(0, 20) + "...";
            System.out.println((i + 1) + ") " + note.getTitle() + " -> " + preview);
        }
    }

    public void deleteNote(String title) {
        Iterator<Note> iterator = notes.iterator();
        while (iterator.hasNext()) {
            Note note = iterator.next();
            if (note.getTitle().equalsIgnoreCase(title)) {
                iterator.remove();
                System.out.println(LocalizationManager.get("delete.success", title));
                saveToFile();// Автоматически сохраняем после удаления
                return;
            }
        }
        System.out.println(LocalizationManager.get("delete.not_found", title));
    }

    public void clearNoteList()
    {
        notes.clear();
        saveToFile();
    }

    public void findNote(String notePart) {
        if (notePart == null || notePart.trim().isEmpty()) {
            System.out.println(LocalizationManager.get("find.prompt"));
            return;
        }
        boolean found = false;

        for (Note note : notes) {
            if (note.getTitle().toLowerCase().contains(notePart.toLowerCase()) || note.getContent().toLowerCase().contains(notePart.toLowerCase())) {
                System.out.println(note.getTitle() + " -> " + note.getContent());
                found = true;
            }
        }
        if (!found) {
            System.out.println(LocalizationManager.get("find.not_found"));
        }
    }

    //сохранить текущий список в файл
    private void saveToFile() {
        FileStorage.saveNotes(notes);
    }

    public void saveLoadedNotes() {
        if (!notes.isEmpty()) {
            saveToFile();
        }
    }
}
