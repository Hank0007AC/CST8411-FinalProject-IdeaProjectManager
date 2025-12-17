/*
 * Name / Nom : Rachid Hankir
 * Student Number / Numéro étudiant : 041142952
 * Course / Cours : CST8411 - Information Systems Development and Deployment
 * Project / Projet : Final Project
 * File / Fichier : IdeaProjectManagerApp.java
 * Date : 2025-12-16
 * Professor / Professeur : (Add Instructor Name)
 *
 * EN: JavaFX GUI application implementing login/register, per-user sessions, idea management, and suggestions.
 * FR: Application JavaFX avec login/register, sessions par utilisateur, gestion d'idées et suggestions.
 */

package org.example;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * EN: Final Project version of the Idea Project Manager.
 * FR: Version Final Project de Idea Project Manager.
 *
 * Main features:
 *  - Register / Login / Logout
 *  - Persistent session (auto-login)
 *  - Per-user idea & task storage (JSON)
 *  - Auto-generated default To-Do list per category
 *  - Assistant suggestions for tools + AI per task
 */
public class IdeaProjectManagerApp extends Application {

    // ---------------------------
    // Services / persistence
    // ---------------------------
    private final Path baseDir = AppPaths.baseDir();
    private final UserStore userStore = new UserStore(baseDir);
    private final SessionManager sessionManager = new SessionManager(baseDir);
    private final IdeaStore ideaStore = new IdeaStore(baseDir);
    private final SuggestionEngine suggestionEngine = new SuggestionEngine();

    // ---------------------------
    // Session state
    // ---------------------------
    private String currentUser;

    // ---------------------------
    // Scenes
    // ---------------------------
    private Scene loginScene;
    private Scene registerScene;
    private Scene mainScene;

    // ---------------------------
    // Main UI state
    // ---------------------------
    private ObservableList<Idea> ideasObs = FXCollections.observableArrayList();
    private ListView<Idea> ideaListView;

    private ObservableList<Task> tasksObs = FXCollections.observableArrayList();
    private ListView<Task> taskListView;

    private TextArea assistantArea;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Idea Project Manager");

        loginScene = buildLoginScene(primaryStage);
        registerScene = buildRegisterScene(primaryStage);
        mainScene = buildMainScene(primaryStage);

        // EN: Auto-login using a saved session if possible.
        // FR: Auto-login via une session sauvegardée si possible.
        Session s = sessionManager.loadSession();
        if (s != null && s.getUsername() != null && userStore.exists(s.getUsername())) {
            doLogin(primaryStage, s.getUsername(), null, true);
        } else {
            primaryStage.setScene(loginScene);
        }

        primaryStage.setMinWidth(1100);
        primaryStage.setMinHeight(650);
        primaryStage.show();
    }

    // ============================================================
    // Login / Register
    // ============================================================

    private Scene buildLoginScene(Stage stage) {
        Label title = new Label("Login");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username (letters/numbers/underscore)");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Button loginBtn = new Button("Login");
        loginBtn.setDefaultButton(true);

        Hyperlink toRegister = new Hyperlink("Create an account (Register)");

        Label info = new Label();
        info.setStyle("-fx-text-fill: #cc0000;");

        loginBtn.setOnAction(e -> {
            String u = usernameField.getText();
            char[] p = passwordField.getText().toCharArray();
            doLogin(stage, u, p, false);
        });

        passwordField.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) loginBtn.fire();
        });

        toRegister.setOnAction(e -> stage.setScene(registerScene));

        VBox box = new VBox(12, title, usernameField, passwordField, loginBtn, toRegister, info);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(30));
        box.setMaxWidth(420);

        BorderPane root = new BorderPane(box);
        root.setPadding(new Insets(20));

        return new Scene(root, 1100, 650);
    }

    private Scene buildRegisterScene(Stage stage) {
        Label title = new Label("Register");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Choose a username (3-20 chars)");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Choose a password (min 6 chars)");

        PasswordField confirmField = new PasswordField();
        confirmField.setPromptText("Confirm password");

        Button registerBtn = new Button("Create Account");
        registerBtn.setDefaultButton(true);

        Hyperlink backToLogin = new Hyperlink("Back to Login");

        Label info = new Label();
        info.setStyle("-fx-text-fill: #cc0000;");

        registerBtn.setOnAction(e -> {
            String u = usernameField.getText();
            String p1 = passwordField.getText();
            String p2 = confirmField.getText();

            if (!p1.equals(p2)) {
                info.setText("Passwords do not match.");
                return;
            }

            boolean ok = userStore.register(u, p1.toCharArray());
            if (ok) {
                info.setStyle("-fx-text-fill: #008800;");
                info.setText("Account created. You can login now.");
            } else {
                info.setStyle("-fx-text-fill: #cc0000;");
                info.setText("Registration failed (username exists or invalid).");
            }
        });

        backToLogin.setOnAction(e -> stage.setScene(loginScene));

        VBox box = new VBox(12, title, usernameField, passwordField, confirmField, registerBtn, backToLogin, info);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(30));
        box.setMaxWidth(420);

        BorderPane root = new BorderPane(box);
        root.setPadding(new Insets(20));

        return new Scene(root, 1100, 650);
    }

    private void doLogin(Stage stage, String username, char[] password, boolean silent) {
        String u = ValidationUtil.normalizeUsername(username);
        if (u == null) {
            if (!silent) showError("Invalid username. Use 3-20 chars: a-z, 0-9, underscore.");
            stage.setScene(loginScene);
            return;
        }

        boolean ok;
        if (silent) {
            // EN: Silent login (session-based). We do not re-check password here.
            // FR: Connexion silencieuse (session). On ne revérifie pas le mot de passe ici.
            ok = true;
        } else {
            ok = userStore.authenticate(u, password);
        }

        if (!ok) {
            showError("Login failed. Check your username/password.");
            stage.setScene(loginScene);
            return;
        }

        this.currentUser = u;
        sessionManager.saveSession(u);

        // EN: Load per-user data.
        // FR: Charger les données par utilisateur.
        List<Idea> loaded = ideaStore.loadIdeas(currentUser);
        ideasObs.setAll(loaded);

        // EN: Clear selection UI.
        // FR: Réinitialiser l'UI.
        tasksObs.clear();
        assistantArea.clear();

        stage.setScene(mainScene);
    }

    private void logout(Stage stage) {
        currentUser = null;
        ideasObs.clear();
        tasksObs.clear();
        assistantArea.clear();
        sessionManager.clearSession();
        stage.setScene(loginScene);
    }

    // ============================================================
    // Main UI
    // ============================================================

    private Scene buildMainScene(Stage stage) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(12));

        // Top bar
        Label userLabel = new Label();
        userLabel.setStyle("-fx-font-weight: bold;");

        Button logoutBtn = new Button("Logout");
        logoutBtn.setOnAction(e -> logout(stage));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox top = new HBox(10, new Label("Idea Project Manager"), spacer, userLabel, logoutBtn);
        top.setAlignment(Pos.CENTER_LEFT);
        top.setPadding(new Insets(8));
        top.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #dddddd;");

        root.setTop(top);

        // LEFT: Ideas
        VBox ideasPane = buildIdeasPane();

        // CENTER: Tasks
        VBox tasksPane = buildTasksPane();

        // RIGHT: Assistant
        VBox assistantPane = buildAssistantPane();

        HBox content = new HBox(12, ideasPane, tasksPane, assistantPane);
        HBox.setHgrow(tasksPane, Priority.ALWAYS);
        HBox.setHgrow(assistantPane, Priority.ALWAYS);

        ideasPane.setPrefWidth(320);
        tasksPane.setPrefWidth(420);
        assistantPane.setPrefWidth(360);

        root.setCenter(content);

        // EN: Update current user label when scene is shown.
        // FR: Mettre à jour le label user quand la scène est affichée.
        mainScene = new Scene(root, 1100, 650);
        mainScene.windowProperty().addListener((obs, oldW, newW) -> {
            userLabel.setText(currentUser == null ? "" : "User: " + currentUser);
        });

        return mainScene;
    }

    private VBox buildIdeasPane() {
        Label label = new Label("Ideas / Idées");
        label.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        ideaListView = new ListView<>(ideasObs);
        ideaListView.setPrefHeight(450);

        TextField titleField = new TextField();
        titleField.setPromptText("Idea title");

        TextField categoryField = new TextField();
        categoryField.setPromptText("Category (Marketing, Website, E-commerce)");

        Button addIdeaBtn = new Button("Add Idea");
        Button removeIdeaBtn = new Button("Remove");

        HBox buttons = new HBox(10, addIdeaBtn, removeIdeaBtn);
        buttons.setAlignment(Pos.CENTER_LEFT);

        addIdeaBtn.setOnAction(e -> {
            String title = titleField.getText() == null ? "" : titleField.getText().trim();
            String cat = categoryField.getText() == null ? "" : categoryField.getText().trim();

            if (title.isBlank() || cat.isBlank()) {
                showError("Please enter both title and category.");
                return;
            }

            Idea idea = new Idea(IdUtil.newIdeaId(), title, cat);

            // EN: Auto-generate default tasks and attach.
            // FR: Générer automatiquement les tâches par défaut.
            idea.setTasks(suggestionEngine.generateDefaultTasks(idea.getId(), idea.getCategory()));

            ideasObs.add(idea);
            titleField.clear();
            categoryField.clear();
            ideaListView.getSelectionModel().select(idea);
            saveCurrentUserIdeas();
        });

        removeIdeaBtn.setOnAction(e -> {
            Idea selected = ideaListView.getSelectionModel().getSelectedItem();
            if (selected == null) return;
            ideasObs.remove(selected);
            tasksObs.clear();
            assistantArea.clear();
            saveCurrentUserIdeas();
        });

        // EN: When selecting an idea, load its tasks in the center list.
        // FR: Quand on sélectionne une idée, charger ses tâches.
        ideaListView.getSelectionModel().selectedItemProperty().addListener((obs, oldIdea, newIdea) -> {
            if (newIdea == null) {
                tasksObs.clear();
                assistantArea.clear();
                return;
            }
            tasksObs.setAll(newIdea.getTasks());
            assistantArea.setText("Selected Idea: " + newIdea.getTitle() + "\n\nSelect a task to get suggestions.");
        });

        VBox box = new VBox(10, label, ideaListView, titleField, categoryField, buttons);
        box.setPadding(new Insets(10));
        box.setStyle("-fx-border-color: #dddddd; -fx-border-radius: 6px; -fx-background-radius: 6px;");
        return box;
    }

    private VBox buildTasksPane() {
        Label label = new Label("To-Do List / Liste de tâches");
        label.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        taskListView = new ListView<>(tasksObs);
        taskListView.setPrefHeight(450);

        TextField taskField = new TextField();
        taskField.setPromptText("New task title");

        Button addTaskBtn = new Button("Add Task");
        Button removeTaskBtn = new Button("Remove");
        Button toggleDoneBtn = new Button("Toggle Done");

        Button regenerateBtn = new Button("Regenerate Default Tasks");

        HBox row1 = new HBox(10, addTaskBtn, removeTaskBtn, toggleDoneBtn);
        row1.setAlignment(Pos.CENTER_LEFT);

        HBox row2 = new HBox(10, regenerateBtn);
        row2.setAlignment(Pos.CENTER_LEFT);

        addTaskBtn.setOnAction(e -> {
            Idea idea = ideaListView.getSelectionModel().getSelectedItem();
            if (idea == null) {
                showError("Select an idea first.");
                return;
            }
            String t = taskField.getText() == null ? "" : taskField.getText().trim();
            if (t.isBlank()) return;

            Task task = new Task(IdUtil.taskId(idea.getId(), idea.getTasks().size() + 1), t);
            idea.addTask(task);

            tasksObs.add(task);
            taskField.clear();
            saveCurrentUserIdeas();
        });

        removeTaskBtn.setOnAction(e -> {
            Idea idea = ideaListView.getSelectionModel().getSelectedItem();
            Task task = taskListView.getSelectionModel().getSelectedItem();
            if (idea == null || task == null) return;

            idea.removeTaskById(task.getId());
            tasksObs.remove(task);

            saveCurrentUserIdeas();
        });

        toggleDoneBtn.setOnAction(e -> {
            Task task = taskListView.getSelectionModel().getSelectedItem();
            if (task == null) return;
            task.setDone(!task.isDone());
            // EN: Refresh ListView display.
            // FR: Rafraîchir l'affichage.
            taskListView.refresh();
            saveCurrentUserIdeas();
        });

        regenerateBtn.setOnAction(e -> {
            Idea idea = ideaListView.getSelectionModel().getSelectedItem();
            if (idea == null) return;

            List<Task> defaults = suggestionEngine.generateDefaultTasks(idea.getId(), idea.getCategory());
            idea.setTasks(defaults);

            tasksObs.setAll(defaults);
            taskListView.refresh();
            saveCurrentUserIdeas();
        });

        // EN: When selecting a task, show tool suggestions in assistant panel.
        // FR: Quand on sélectionne une tâche, afficher les suggestions dans l'assistant.
        taskListView.getSelectionModel().selectedItemProperty().addListener((obs, oldTask, newTask) -> {
            if (newTask == null) return;
            ToolSuggestion suggestion = suggestionEngine.suggestForTask(newTask.getTitle());
            assistantArea.setText("Task: " + newTask.getTitle() + "\n\n" + suggestion.toDisplayText());
        });

        VBox box = new VBox(10, label, taskListView, taskField, row1, row2);
        box.setPadding(new Insets(10));
        box.setStyle("-fx-border-color: #dddddd; -fx-border-radius: 6px; -fx-background-radius: 6px;");
        return box;
    }

    private VBox buildAssistantPane() {
        Label label = new Label("Assistant / Chatbot (Simple)");
        label.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        assistantArea = new TextArea();
        assistantArea.setEditable(false);
        assistantArea.setWrapText(true);
        assistantArea.setPrefHeight(500);

        TextField input = new TextField();
        input.setPromptText("Ask something (e.g., \"What tools for ads?\")");

        Button askBtn = new Button("Ask");
        askBtn.setDefaultButton(true);

        askBtn.setOnAction(e -> {
            String msg = input.getText() == null ? "" : input.getText().trim();
            if (msg.isBlank()) return;

            String answer = generateAssistantAnswer(msg);
            assistantArea.appendText("\n\nYou: " + msg + "\nAssistant: " + answer);
            input.clear();
        });

        input.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) askBtn.fire();
        });

        VBox box = new VBox(10, label, assistantArea, new HBox(10, input, askBtn));
        box.setPadding(new Insets(10));
        box.setStyle("-fx-border-color: #dddddd; -fx-border-radius: 6px; -fx-background-radius: 6px;");
        return box;
    }

    private String generateAssistantAnswer(String message) {
        // EN: If user selected a task, we respond with suggestions for that task.
        // FR: Si une tâche est sélectionnée, on répond avec ses suggestions.
        Task selectedTask = (taskListView == null) ? null : taskListView.getSelectionModel().getSelectedItem();
        if (selectedTask != null) {
            ToolSuggestion s = suggestionEngine.suggestForTask(selectedTask.getTitle());
            return "Based on your selected task, here are recommendations:\n\n" + s.toDisplayText();
        }

        // EN: Otherwise, we use the typed message as a "task-like" string.
        // FR: Sinon, on utilise le message comme un "titre de tâche".
        ToolSuggestion s = suggestionEngine.suggestForTask(message);
        return "Here are some tools/AI that match your question:\n\n" + s.toDisplayText();
    }

    // ============================================================
    // Persistence helpers
    // ============================================================

    private void saveCurrentUserIdeas() {
        if (currentUser == null) return;
        ideaStore.saveIdeas(currentUser, new ArrayList<>(ideasObs));
    }

    // ============================================================
    // Small UI helpers
    // ============================================================

    private void showError(String message) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("Error");
        a.setHeaderText(null);
        a.setContentText(message);
        a.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
