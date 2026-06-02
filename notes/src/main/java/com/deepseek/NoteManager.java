package com.deepseek;

import java.util.Iterator;
import java.util.List;

public class NoteManager {
    private final List<Note> notes;

    public NoteManager() {
        notes = FileStorage.loadNotes();
    }

    public void addNote(Note note) {
        if (note.getTitle() == null || note.getTitle().trim().isEmpty()) {
            System.out.println("Title cannot be null or empty");
            return false;
        }

        for (Note n : notes) {
            if (n.getTitle().equalsIgnoreCase(note.getTitle())) {
                System.out.println("Note already exists");
                return false;
            }
        }
        notes.add(note);
        saveToFile(); // Автоматически сохраняем после добавления
        System.out.println("Note is added");
        return true;
    }

    public void showAllNotes() {
        System.out.println("List of Notes:");
        for (Note note : notes) {
            System.out.println(note.getTitle());
            String croppedText = note.getContent();
            if (croppedText.length() > 20) croppedText = croppedText.substring(0, 20);
            System.out.println("\t" + croppedText);
        }
    }

    public void deleteNote(String title) {
        //notes.removeIf(note1 -> note1.getTitle().equalsIgnoreCase(title));
        Iterator<Note> iterator = notes.iterator();
        while (iterator.hasNext()) {
            Note note = iterator.next();
            if (note.getTitle().equalsIgnoreCase(title)) {
                iterator.remove();
                System.out.println("Note '" + note.getTitle() + "' deleted");
                saveToFile();// Автоматически сохраняем после удаления
                return true;
            }
        }
        System.out.println("Note '" + title + "' not found");
        return false;
    }

    public void findNote(String notePart) {
        if (notePart == null || notePart.trim().isEmpty()) {
            System.out.println("Enter search query");
            return;
        }
        boolean found = false;

        for (Note note : notes) {
            if (note.getTitle().toLowerCase().contains(notePart.toLowerCase())
                    || note.getContent().toLowerCase().contains(notePart.toLowerCase())) {
                System.out.println(note.getTitle());
                System.out.println("\t" + note.getContent());
                found = true;
            }
        }
        if (!found) {
            System.out.println("Note '" + notePart + "' not found");
        }
    }

    //сохранить текущий список в файл
    private void saveToFile() {
        FileStorage.saveNotes(notes);
    }

}
