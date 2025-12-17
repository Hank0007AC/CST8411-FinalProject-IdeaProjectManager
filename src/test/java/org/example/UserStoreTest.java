/*
 * Name / Nom : Rachid Hankir
 * Student Number / Numéro étudiant : 041142952
 * Course / Cours : CST8411 - Information Systems Development and Deployment
 * Project / Projet : Final Project
 * File / Fichier : UserStoreTest.java
 * Date : 2025-12-16
 * Professor / Professeur : (Add Instructor Name)
 *
 * EN: Unit tests for user registration and authentication (JSON persistence).
 * FR: Tests unitaires pour inscription et authentification (persistance JSON).
 */

package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class UserStoreTest {

    @TempDir
    Path tmp;

    @Test
    void registerThenAuthenticate() {
        UserStore store = new UserStore(tmp);

        assertTrue(store.register("rachid_1", "pass123".toCharArray()));
        assertTrue(store.authenticate("rachid_1", "pass123".toCharArray()));
        assertFalse(store.authenticate("rachid_1", "wrong".toCharArray()));
    }

    @Test
    void duplicateUsernameRejected() {
        UserStore store = new UserStore(tmp);

        assertTrue(store.register("user", "pass123".toCharArray()));
        assertFalse(store.register("user", "pass123".toCharArray()));
        assertFalse(store.register("USER", "pass123".toCharArray())); // case-insensitive
    }

    @Test
    void invalidUsernameRejected() {
        UserStore store = new UserStore(tmp);
        assertFalse(store.register("a", "pass123".toCharArray()));
        assertFalse(store.register("bad space", "pass123".toCharArray()));
    }
}
