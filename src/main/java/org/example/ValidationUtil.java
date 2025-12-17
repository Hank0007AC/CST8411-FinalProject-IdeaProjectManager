/*
 * Name / Nom : Rachid Hankir
 * Student Number / Numéro étudiant : 041142952
 * Course / Cours : CST8411 - Information Systems Development and Deployment
 * Project / Projet : Final Project
 * File / Fichier : ValidationUtil.java
 * Date : 2025-12-16
 * Professor / Professeur : (Add Instructor Name)
 *
 * EN: Input validation helpers (username and password rules).
 * FR: Aides de validation (règles username et mot de passe).
 */

package org.example;

import java.util.Locale;

public final class ValidationUtil {
    private ValidationUtil() {}

    // EN: Normalize and validate username.
    // FR: Normaliser et valider le nom d'utilisateur.
    public static String normalizeUsername(String username) {
        if (username == null) return null;
        String u = username.trim().toLowerCase(Locale.ROOT);
        if (u.length() < 3 || u.length() > 20) return null;
        if (!u.matches("[a-z0-9_]+")) return null;
        return u;
    }

    // EN: Basic password strength for a course project.
    // FR: Force basique du mot de passe pour un projet de cours.
    public static boolean isStrongEnough(char[] password) {
        if (password == null || password.length < 6) return false;
        return true;
    }
}
