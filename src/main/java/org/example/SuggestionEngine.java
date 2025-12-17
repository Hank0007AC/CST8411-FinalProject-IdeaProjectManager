/*
 * Name / Nom : Rachid Hankir
 * Student Number / Numéro étudiant : 041142952
 * Course / Cours : CST8411 - Information Systems Development and Deployment
 * Project / Projet : Final Project
 * File / Fichier : SuggestionEngine.java
 * Date : 2025-12-16
 * Professor / Professeur : (Add Instructor Name)
 *
 * EN: Generates default to-do lists and tool/AI suggestions based on the idea category and task.
 * FR: Génère des to-do lists par défaut et des suggestions d'outils/IA selon la catégorie et la tâche.
 */

package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SuggestionEngine {

    /**
     * EN: Generate a default to-do list for an idea category.
     * FR: Génère une liste de tâches par défaut selon la catégorie de l'idée.
     */
    public List<Task> generateDefaultTasks(String ideaId, String category) {
        String cat = (category == null) ? "" : category.toLowerCase(Locale.ROOT);

        List<String> titles = new ArrayList<>();

        if (cat.contains("marketing")) {
            titles.add("Define the target audience / Définir la cible");
            titles.add("Create a brand kit (logo, colors) / Créer l'identité visuelle");
            titles.add("Design social media posts / Concevoir des posts");
            titles.add("Launch Facebook/Instagram ads / Lancer des pubs FB/IG");
            titles.add("Analyze results and optimize / Analyser et optimiser");
        } else if (cat.contains("website") || cat.contains("site")) {
            titles.add("Choose a platform (Wix/WordPress) / Choisir une plateforme");
            titles.add("Buy a domain name / Acheter un nom de domaine");
            titles.add("Create pages (Home, About, Contact) / Créer les pages");
            titles.add("Add SEO basics / Ajouter le SEO de base");
            titles.add("Publish and test / Publier et tester");
        } else if (cat.contains("e-commerce") || cat.contains("ecommerce") || cat.contains("shop")) {
            titles.add("Choose products / Choisir les produits");
            titles.add("Create product descriptions / Rédiger les descriptions");
            titles.add("Set payment & shipping / Configurer paiement & livraison");
            titles.add("Create store visuals / Créer les visuels boutique");
            titles.add("Launch first marketing campaign / Lancer la 1ère campagne");
        } else {
            // Generic default tasks
            titles.add("Clarify the project goal / Clarifier l'objectif du projet");
            titles.add("List required resources / Lister les ressources");
            titles.add("Create a timeline / Créer un planning");
            titles.add("Execute first milestone / Réaliser le 1er jalon");
            titles.add("Review and improve / Revoir et améliorer");
        }

        List<Task> tasks = new ArrayList<>();
        for (int i = 0; i < titles.size(); i++) {
            tasks.add(new Task(IdUtil.taskId(ideaId, i + 1), titles.get(i)));
        }
        return tasks;
    }

    /**
     * EN: Suggest tools + AI assistants for a given task title.
     * FR: Suggère des outils + IA pour un titre de tâche.
     */
    public ToolSuggestion suggestForTask(String taskTitle) {
        String t = (taskTitle == null) ? "" : taskTitle.toLowerCase(Locale.ROOT);

        List<String> tools = new ArrayList<>();
        List<String> ai = new ArrayList<>();

        // EN: Very simple rule-based suggestion (prototype-friendly).
        // FR: Suggestion rule-based très simple (adapté au prototype).
        if (t.contains("logo") || t.contains("brand") || t.contains("visual") || t.contains("design")) {
            tools.add("Canva");
            tools.add("Figma");
            tools.add("Renderforest");
            ai.add("ChatGPT (copywriting + ideas)");
            ai.add("Adobe Firefly (visual generation)");
        } else if (t.contains("ads") || t.contains("campaign") || t.contains("marketing")) {
            tools.add("Meta Ads Manager (Facebook/Instagram)");
            tools.add("Google Ads");
            tools.add("Mailchimp");
            ai.add("ChatGPT (ad copy variations)");
            ai.add("Perplexity (research)");
        } else if (t.contains("domain") || t.contains("website") || t.contains("seo") || t.contains("publish")) {
            tools.add("Wix");
            tools.add("WordPress");
            tools.add("Google Search Console");
            ai.add("ChatGPT (SEO text)");
            ai.add("Gemini (content drafts)");
        } else if (t.contains("shipping") || t.contains("payment") || t.contains("product")) {
            tools.add("Shopify");
            tools.add("Stripe");
            tools.add("Shippo");
            ai.add("ChatGPT (product descriptions)");
            ai.add("Claude (structured writing)");
        } else {
            tools.add("Notion");
            tools.add("Trello");
            tools.add("Google Docs");
            ai.add("ChatGPT (planning assistant)");
        }

        return new ToolSuggestion(tools, ai);
    }
}
