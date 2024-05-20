package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UsersController {

    @FXML
    private TableView<User> usersTable;

    @FXML
    private TableColumn<User, Integer> idColumn;

    @FXML
    private TableColumn<User, String> usernameColumn;

    @FXML
    private TableColumn<User, String> roleColumn;

    @FXML
    private TableColumn<User, String> emailColumn;

    private ObservableList<User> usersData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        loadUsersFromDatabase();
    }

    private void loadUsersFromDatabase() {
        String url = "jdbc:mysql://localhost:3306/agence"; // Modifier l'URL de connexion à votre base de données
        String user = "root"; // Modifier le nom d'utilisateur de la base de données
        String password = ""; // Modifier le mot de passe de la base de données

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT id, username, role, email FROM users";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int userId = resultSet.getInt("id");
                String username = resultSet.getString("username");
                String role = resultSet.getString("role");
                String email = resultSet.getString("email");

                User userObj = new User(username, role);
                userObj.setUserId(userId);
                userObj.setEmail(email);

                usersData.add(userObj);
            }

            usersTable.setItems(usersData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleUsersClick() {
    	try {
    	    // Charger le fichier FXML correspondant
    	    FXMLLoader loader = new FXMLLoader(getClass().getResource("users.fxml"));
    	    Parent root = loader.load();
    	    root.getStylesheets().add(getClass().getResource("styles1.css").toExternalForm());
    	    
    	    // Obtenir la référence de la scène actuelle
    	  
    	    
    	    // Remplacer le contenu de la scène actuelle par le nouveau contenu
    	   
    	} catch (IOException e) {
    	    e.printStackTrace();
    	}
    }
    @FXML
    private void handleHome(MouseEvent event) {
        try {
            // Récupérer le nœud source de l'événement
            Node sourceNode = (Node) event.getSource();
            // Récupérer la scène à partir du nœud source
            Scene currentScene = sourceNode.getScene();
            // Récupérer la fenêtre (Stage) à partir de la scène actuelle
            Stage stage = (Stage) currentScene.getWindow();
            // Charger le fichier FXML "tableau.fxml"
            FXMLLoader loader = new FXMLLoader(getClass().getResource("tableau.fxml"));
            Parent root = loader.load();
            // Créer une nouvelle scène avec le nouveau contenu
            Scene scene = new Scene(root);
            // Définir la nouvelle scène sur la fenêtre (Stage) actuelle
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Gérer l'erreur lors du chargement de la vue
        }
    }

   
    @FXML
    private void handleReservation() {
    	try {
    	    // Charger le fichier FXML correspondant
    	    FXMLLoader loader = new FXMLLoader(getClass().getResource("listerervation.fxml"));
    	    Parent root = loader.load();
    	    root.getStylesheets().add(getClass().getResource("styles1.css").toExternalForm());
    	    
    	    // Obtenir la référence de la scène actuelle
    	  
    	    
    	    // Remplacer le contenu de la scène actuelle par le nouveau contenu
    	   
    	} catch (IOException e) {
    	    e.printStackTrace();
    	}
    }

	// Méthode pour gérer l'événement de navigation vers la page de connexion
    @FXML
    private void handleLogin(MouseEvent event) {
    	 try {
             // Charger le fichier FXML correspondant
             FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
             Parent root = loader.load();
             root.getStylesheets().add(getClass().getResource("styles1.css").toExternalForm());

             // Obtenir la référence de la scène actuelle
             Scene currentScene = ((Node) event.getSource()).getScene();

             // Remplacer le contenu de la scène actuelle par le nouveau contenu
             currentScene.setRoot(root);
         } catch (IOException e) {
             e.printStackTrace();
         }
    }
     
    

        
    

 // Méthode pour gérer l'événement de gestion des voyages
    @FXML
    private void handleManageTrips(MouseEvent event) {
        try {
            // Charger le fichier FXML correspondant
            FXMLLoader loader = new FXMLLoader(getClass().getResource("list.fxml"));
            Parent root = loader.load();
            root.getStylesheets().add(getClass().getResource("styles1.css").toExternalForm());

            // Obtenir la référence de la scène actuelle
            Scene currentScene = ((Node) event.getSource()).getScene();

            // Remplacer le contenu de la scène actuelle par le nouveau contenu
            currentScene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour gérer l'événement de gestion des réservations
    @FXML
    private void handleManageBookings(MouseEvent event) {
        try {
            // Charger le fichier FXML correspondant
            FXMLLoader loader = new FXMLLoader(getClass().getResource("listecompagny.fxml"));
            Parent root = loader.load();
            root.getStylesheets().add(getClass().getResource("styles1.css").toExternalForm());

            // Obtenir la référence de la scène actuelle
            Scene currentScene = ((Node) event.getSource()).getScene();

            // Remplacer le contenu de la scène actuelle par le nouveau contenu
            currentScene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}