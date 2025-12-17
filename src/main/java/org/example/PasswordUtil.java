/*
 * Name / Nom : Rachid Hankir
 * Student Number / Numéro étudiant : 041142952
 * Course / Cours : CST8411 - Information Systems Development and Deployment
 * Project / Projet : Final Project
 * File / Fichier : PasswordUtil.java
 * Date : 2025-12-16
 * Professor / Professeur : (Add Instructor Name)
 *
 * EN: Utility for hashing and verifying passwords (PBKDF2).
 * FR: Utilitaire pour hacher et vérifier les mots de passe (PBKDF2).
 */

package org.example;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

public final class PasswordUtil {

    // EN: PBKDF2 parameters (balanced for a student project).
    // FR: Paramètres PBKDF2 (équilibrés pour un projet étudiant).
    private static final int ITERATIONS = 120_000;
    private static final int KEY_LENGTH_BITS = 256;
    private static final int SALT_LENGTH_BYTES = 16;

    private static final SecureRandom RNG = new SecureRandom();

    private PasswordUtil() {}

    /**
     * EN: Hash a password and return "iterations:saltBase64:hashBase64".
     * FR: Hache un mot de passe et retourne "iterations:saltBase64:hashBase64".
     */
    public static String hashPassword(char[] password) {
        byte[] salt = new byte[SALT_LENGTH_BYTES];
        RNG.nextBytes(salt);

        byte[] hash = pbkdf2(password, salt, ITERATIONS, KEY_LENGTH_BITS);

        return ITERATIONS + ":" + Base64.getEncoder().encodeToString(salt) + ":" +
                Base64.getEncoder().encodeToString(hash);
    }

    /**
     * EN: Verify a candidate password against a stored PBKDF2 string.
     * FR: Vérifie un mot de passe candidat contre une chaîne PBKDF2 stockée.
     */
    public static boolean verifyPassword(char[] candidate, String stored) {
        if (stored == null || stored.isBlank()) return false;

        String[] parts = stored.split(":");
        if (parts.length != 3) return false;

        int iterations;
        try {
            iterations = Integer.parseInt(parts[0]);
        } catch (NumberFormatException e) {
            return false;
        }

        byte[] salt = Base64.getDecoder().decode(parts[1]);
        byte[] storedHash = Base64.getDecoder().decode(parts[2]);

        byte[] candidateHash = pbkdf2(candidate, salt, iterations, storedHash.length * 8);

        // EN: Constant-time compare to reduce timing attacks (good practice).
        // FR: Comparaison en temps constant pour réduire le timing attack (bonne pratique).
        return constantTimeEquals(storedHash, candidateHash);
    }

    private static byte[] pbkdf2(char[] password, byte[] salt, int iterations, int keyLengthBits) {
        try {
            PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, keyLengthBits);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            return skf.generateSecret(spec).getEncoded();
        } catch (Exception e) {
            // EN: In a real app, we'd handle this more carefully.
            // FR: Dans une vraie app, on gérerait ça plus finement.
            throw new IllegalStateException("Password hashing failed", e);
        }
    }

    private static boolean constantTimeEquals(byte[] a, byte[] b) {
        if (a == null || b == null) return false;
        if (a.length != b.length) return false;

        int result = 0;
        for (int i = 0; i < a.length; i++) {
            result |= a[i] ^ b[i];
        }
        return result == 0;
    }
}
