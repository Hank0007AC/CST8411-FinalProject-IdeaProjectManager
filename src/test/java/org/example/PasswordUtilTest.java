/*
 * Name / Nom : Rachid Hankir
 * Student Number / Numéro étudiant : 041142952
 * Course / Cours : CST8411 - Information Systems Development and Deployment
 * Project / Projet : Final Project
 * File / Fichier : PasswordUtilTest.java
 * Date : 2025-12-16
 * Professor / Professeur : (Add Instructor Name)
 *
 * EN: Unit tests for password hashing and verification.
 * FR: Tests unitaires pour le hachage et la vérification de mot de passe.
 */

package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PasswordUtilTest {

    @Test
    void hashAndVerifyWorks() {
        String stored = PasswordUtil.hashPassword("secret123".toCharArray());
        assertTrue(PasswordUtil.verifyPassword("secret123".toCharArray(), stored));
        assertFalse(PasswordUtil.verifyPassword("wrong".toCharArray(), stored));
    }

    @Test
    void invalidStoredFormatFails() {
        assertFalse(PasswordUtil.verifyPassword("x".toCharArray(), "bad-format"));
    }
}
