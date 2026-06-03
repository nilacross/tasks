package com.deepseek;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class LocalizationManager {
    private static final Preferences prefs = Preferences.userNodeForPackage(LocalizationManager.class);
    private static final String PREF_LANGUAGE = "language";
    private static final String DEFAULT_LANGUAGE = "en";

    private static ResourceBundle bundle;
    private static String currentLanguage;

    static {
        loadLanguage();
    }

    private static void loadLanguage() {
        currentLanguage = prefs.get(PREF_LANGUAGE, DEFAULT_LANGUAGE);
        try {
            bundle = ResourceBundle.getBundle("messages", new Locale(currentLanguage));
        } catch (MissingResourceException e) {
            bundle = ResourceBundle.getBundle("messages", new Locale(DEFAULT_LANGUAGE));
        }
    }

    public static String get(String key) {
        try {
            return bundle.getString(key);
        } catch (MissingResourceException e) {
            return "???" + key + "???";
        }
    }

    public static String get(String key, Object... args) {
        String pattern = get(key);
        return MessageFormat.format(pattern, args);
    }

    public static void setLanguage(String langCode) {
        if (!langCode.equals(currentLanguage)) {
            prefs.put(PREF_LANGUAGE, langCode);
            currentLanguage = langCode;
            loadLanguage();
        }
    }

    public static String getCurrentLanguage() {
        return currentLanguage;
    }

    public static void showLanguageMenu() {
        System.out.println("\n" + get("language.select"));
        System.out.println(get("language.option1"));
        System.out.println(get("language.option2"));
        System.out.println(">");
    }

    public static boolean handelLanguageChoice(String input) {
        if (input.equals(1)) {
            setLanguage("en");
            System.out.println(get("language.changed"));
            return true;
        } else if (input.equals(2)) {
            setLanguage("ru");
            System.out.println(get("language.changed"));
            return true;
        }
        return false;
    }

    public static void printCurrentLanguage() {
        String langName = currentLanguage.equals("en") ? "English" : "Русский";
        System.out.println(get("language.current") + ": " + langName);
    }
}
