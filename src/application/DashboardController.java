package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class DashboardController {

    @FXML
    private Label dashboardLabel;
    @FXML
    private BorderPane mainPane;
    @FXML
    private Label numberOfHotelsLabel;

    @FXML
    private Label numberOfCompaniesLabel;
    @FXML
    private Label numberOfReviewsLabel;
    @FXML
    private Label  numberOfUsersLabel;

  

    public int calculateNumberOfHotels() {
        int numberOfHotels = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/agence", "root", "");
            String query = "SELECT COUNT(*) FROM hotel";
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            if (rs.next()) {
                numberOfHotels = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return numberOfHotels;
    }

    public int calculateNumberOfCompanies() {
        int numberOfCompanies = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/agence", "root", "");
            String query = "SELECT COUNT(*) FROM compagny";
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            if (rs.next()) {
                numberOfCompanies = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return numberOfCompanies;
    }

   
    
    @FXML
    private void handleHome() {
    	try {
    	    // Charger le fichier FXML correspondant
    	    FXMLLoader loader = new FXMLLoader(getClass().getResource("tableau.fxml"));
    	    Parent root = loader.load();
    	    root.getStylesheets().add(getClass().getResource("styles1.css").toExternalForm());
    	    
    	    // Obtenir la référence de la scène actuelle
    	    Scene currentScene = dashboardLabel.getScene();
    	    
    	    // Remplacer le contenu de la scène actuelle par le nouveau contenu
    	    currentScene.setRoot(root);
    	} catch (IOException e) {
    	    e.printStackTrace();
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
    	    Scene currentScene = dashboardLabel.getScene();
    	    
    	    // Remplacer le contenu de la scène actuelle par le nouveau contenu
    	    currentScene.setRoot(root);
    	} catch (IOException e) {
    	    e.printStackTrace();
    	}
    }

	// Méthode pour gérer l'événement de navigation vers la page de connexion
    @FXML
    private void handleLogin() {try {
	    // Charger le fichier FXML correspondant
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
	    Parent root = loader.load();
	    root.getStylesheets().add(getClass().getResource("styles1.css").toExternalForm());
	    
	    // Obtenir la référence de la scène actuelle
	    Scene currentScene = dashboardLabel.getScene();
	    
	    // Remplacer le contenu de la scène actuelle par le nouveau contenu
	    currentScene.setRoot(root);
	} catch (IOException e) {
	    e.printStackTrace();
	}
}

        
    

    public void initialize() {
        dashboardLabel.setText("Welcome to Dashboard Admin!");
        numberOfHotelsLabel.setText(String.valueOf(calculateNumberOfHotels()));
        numberOfCompaniesLabel.setText(String.valueOf(calculateNumberOfCompanies()));
        numberOfReviewsLabel.setText(String.valueOf(calculateNumberOfReviews()));
        numberOfUsersLabel.setText(String.valueOf(calculateNumberOfUsers()));
    }
    public int calculateNumberOfReviews() {
        int numberOfReviews = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/agence", "root", "");
            String query = "SELECT COUNT(*) FROM avis";
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            if (rs.next()) {
                numberOfReviews = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return numberOfReviews;
    }

    public int calculateNumberOfUsers() {
        int numberOfUsers = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/agence", "root", "");
            String query = "SELECT COUNT(*) FROM users";
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            if (rs.next()) {
                numberOfUsers = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return numberOfUsers;
    }

    // Méthode pour gérer l'événement de gestion des voyages
    @FXML
    private void handleManageTrips() {
    	try {
    	    // Charger le fichier FXML correspondant
    	    FXMLLoader loader = new FXMLLoader(getClass().getResource("list.fxml"));
    	    Parent root = loader.load();
    	    root.getStylesheets().add(getClass().getResource("styles1.css").toExternalForm());
    	    
    	    // Obtenir la référence de la scène actuelle
    	    Scene currentScene = dashboardLabel.getScene();
    	    
    	    // Remplacer le contenu de la scène actuelle par le nouveau contenu
    	    currentScene.setRoot(root);
    	} catch (IOException e) {
    	    e.printStackTrace();
    	}

    }

    // Méthode pour gérer l'événement de gestion des réservations
    @FXML
    private void handleManageBookings() {
    	try {
    	    // Charger le fichier FXML correspondant
    	    FXMLLoader loader = new FXMLLoader(getClass().getResource("listecompagny.fxml"));
    	    Parent root = loader.load();
    	    root.getStylesheets().add(getClass().getResource("styles1.css").toExternalForm());
    	    
    	    // Obtenir la référence de la scène actuelle
    	    Scene currentScene = dashboardLabel.getScene();
    	    
    	    // Remplacer le contenu de la scène actuelle par le nouveau contenu
    	    currentScene.setRoot(root);
    	} catch (IOException e) {
    	    e.printStackTrace();
    	}

    
        System.out.println("Gestion des compagnie");
    }
 ; // L'étiquette "users" dans votre FXML

    @FXML
    private void handleUsersClick() {
    	try {
    	    // Charger le fichier FXML correspondant
    	    FXMLLoader loader = new FXMLLoader(getClass().getResource("users.fxml"));
    	    Parent root = loader.load();
    	    root.getStylesheets().add(getClass().getResource("styles1.css").toExternalForm());
    	    
    	    // Obtenir la référence de la scène actuelle
    	    Scene currentScene = dashboardLabel.getScene();
    	    
    	    // Remplacer le contenu de la scène actuelle par le nouveau contenu
    	    currentScene.setRoot(root);
    	} catch (IOException e) {
    	    e.printStackTrace();
    	}
    }
}

    



