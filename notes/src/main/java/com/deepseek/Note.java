package com.deepseek;

import java.io.Serializable;
import java.util.Objects;

public class Note implements Serializable {
    private final String title;
    private final String content;

    public Note(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        Note other = (Note) object;
        return Objects.equals(title, other.title);
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + content.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Note{" +
                "title='" + title + '\'' +
                ", text='" + content + '\'' +
                '}';
    }
}
