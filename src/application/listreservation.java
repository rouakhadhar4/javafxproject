package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class listreservation implements Initializable {

    @FXML
    private TableView<Reservation> reservationTable;

    @FXML
    private TableColumn<Reservation, Integer> reservationIdColumn;

    @FXML
    private TableColumn<Reservation, String> usernameColumn;

    @FXML
    private TableColumn<Reservation, String> hotelNameColumn;

    @FXML
    private TableColumn<Reservation, String> companyNameColumn;

    @FXML
    private TableColumn<Reservation, Double> priceColumn;

    @FXML
    private TableColumn<Reservation, String> destinationColumn;

    @FXML
    private TableColumn<Reservation, String> addressColumn;

    @FXML
    private TableColumn<Reservation, String> phoneColumn;

    @FXML
    private TableColumn<Reservation, String> reservationDateColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeColumns();
        loadReservationsFromDatabase();
    }

    private void initializeColumns() {
        reservationIdColumn.setCellValueFactory(new PropertyValueFactory<>("reservationId"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        hotelNameColumn.setCellValueFactory(new PropertyValueFactory<>("hotelName"));
        companyNameColumn.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        destinationColumn.setCellValueFactory(new PropertyValueFactory<>("destination"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        reservationDateColumn.setCellValueFactory(new PropertyValueFactory<>("reservationDate"));
    }

    private void loadReservationsFromDatabase() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/agence", "root", "");
             PreparedStatement statement = conn.prepareStatement("SELECT * FROM reservations");
             ResultSet resultSet = statement.executeQuery()) {
            
            while (resultSet.next()) {
                int reservationId = resultSet.getInt("id");
                String username = resultSet.getString("username");
                String hotelName = resultSet.getString("hotel_name");
                String companyName = resultSet.getString("company_name");
                double price = resultSet.getDouble("price");
                String destination = resultSet.getString("destination");
                String address = resultSet.getString("address");
                String phone = resultSet.getString("phone");
                String reservationDate = resultSet.getString("reservation_date");

                Reservation reservation = new Reservation(reservationId, username, hotelName, companyName, price, destination, address, phone, reservationDate);
                reservationTable.getItems().add(reservation);
            }
        } catch (SQLException e) {
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

