/*
 * Name / Nom : Rachid Hankir
 * Student Number / Numéro étudiant : 041142952
 * Course / Cours : CST8411 - Information Systems Development and Deployment
 * Project / Projet : Final Project
 * File / Fichier : JsonUtil.java
 * Date : 2025-12-16
 * Professor / Professeur : (Add Instructor Name)
 *
 * EN: Provides a configured Jackson ObjectMapper for JSON persistence.
 * FR: Fournit un ObjectMapper Jackson configuré pour la persistance JSON.
 */

package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public final class JsonUtil {

    private static final ObjectMapper MAPPER = new ObjectMapper()
            // EN: Write readable JSON for easier review.
            // FR: JSON lisible pour faciliter la revue.
            .enable(SerializationFeature.INDENT_OUTPUT);

    private JsonUtil() {}

    public static ObjectMapper mapper() {
        return MAPPER;
    }
}
