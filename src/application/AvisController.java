package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class AvisController implements Initializable {

    @FXML
    private ComboBox<String> companyComboBox;

    @FXML
    private ComboBox<String> hotelComboBox;

    @FXML
    private RadioButton passableServiceRadioButton;

    @FXML
    private RadioButton mauvaiseServiceRadioButton;

    @FXML
    private RadioButton excellenteServiceRadioButton;

    @FXML
    private RadioButton passablePropreteRadioButton;

    @FXML
    private RadioButton mauvaisePropreteRadioButton;

    @FXML
    private RadioButton excellentePropreteRadioButton;

    @FXML
    private RadioButton passableExperienceRadioButton;

    @FXML
    private RadioButton mauvaiseExperienceRadioButton;

    @FXML
    private RadioButton excellenteExperienceRadioButton;

    @FXML
    private TextArea commentsArea;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadCompanies();
        loadHotels();
    }

    @FXML
    void handleHome(ActionEvent event) {
    	 try {
             // Charger le fichier FXML correspondant
             FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
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
    
      

    @FXML
    void handleVoyages(ActionEvent event) {
        try {
            // Récupérer le nœud source de l'événement
            Node sourceNode = (Node) event.getSource();
            // Récupérer la scène à partir du nœud source
            Scene currentScene = sourceNode.getScene();
            // Récupérer la fenêtre (Stage) à partir de la scène actuelle
            Stage stage = (Stage) currentScene.getWindow();
            // Charger le fichier FXML "avis.fxml"
            FXMLLoader loader = new FXMLLoader(getClass().getResource("avis.fxml"));
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
    void handleContact(ActionEvent event) {
        redirectToLogin(event);
    }

    private void redirectToLogin(ActionEvent event) {
        try {
            Node sourceNode = (Node) event.getSource();
            Scene currentScene = sourceNode.getScene();
            Stage stage = (Stage) currentScene.getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Gérer l'erreur lors du chargement de la vue
        }
    }


    private void loadCompanies() {
        try {
            Connection conn = MySQLConnection.getConnection();
            String query = "SELECT nom FROM compagny";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                companyComboBox.getItems().add(resultSet.getString("nom"));
            }
            preparedStatement.close();
            resultSet.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadHotels() {
        try {
            Connection conn = MySQLConnection.getConnection();
            String query = "SELECT nom FROM hotel";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                hotelComboBox.getItems().add(resultSet.getString("nom"));
            }
            preparedStatement.close();
            resultSet.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSubmit(ActionEvent event) {
        try {
            Connection conn = MySQLConnection.getConnection();
            String query = "INSERT INTO avis (id_hotel, nom_hotel, id, nom_compagnie, experience_globale, qualite_service_client, proprete, commentaires) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = conn.prepareStatement(query);
            // Get the ID of the selected hotel
            int hotelId = getHotelIdFromName(hotelComboBox.getValue());
            preparedStatement.setInt(1, hotelId);
            preparedStatement.setString(2, hotelComboBox.getValue());
            // Get the ID of the selected company
            int companyId = getCompanyIdFromName(companyComboBox.getValue());
            preparedStatement.setInt(3, companyId);
            preparedStatement.setString(4, companyComboBox.getValue());
            preparedStatement.setString(5, getSelectedRadioButtonText(passableExperienceRadioButton, mauvaiseExperienceRadioButton, excellenteExperienceRadioButton));
            preparedStatement.setString(6, getSelectedRadioButtonText(passableServiceRadioButton, mauvaiseServiceRadioButton, excellenteServiceRadioButton));
            preparedStatement.setString(7, getSelectedRadioButtonText(passablePropreteRadioButton, mauvaisePropreteRadioButton, excellentePropreteRadioButton));
            preparedStatement.setString(8, commentsArea.getText());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            conn.close();

            // Rediriger vers home.fxml après l'ajout d'un avis
            redirectToHome(event);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void redirectToHome(ActionEvent event) {
        try {
            Node sourceNode = (Node) event.getSource();
            Scene currentScene = sourceNode.getScene();
            Stage stage = (Stage) currentScene.getWindow();
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
            Parent root = loader.load();
            
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

            // Afficher une alerte après la redirection
            showAlert("Merci de donner votre avis!", "Votre avis a été ajouté avec succès.", Alert.AlertType.INFORMATION);
        } catch (IOException e) {
            e.printStackTrace();
            // Gérer l'erreur lors du chargement de la vue
        }
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private String getSelectedRadioButtonText(RadioButton passableRadioButton, RadioButton mauvaiseRadioButton, RadioButton excellenteRadioButton) {
        if (passableRadioButton.isSelected()) {
            return "Passable";
        } else if (mauvaiseRadioButton.isSelected()) {
            return "Mauvaise";
        } else if (excellenteRadioButton.isSelected()) {
            return "Excellente";
        } else {
            return ""; // Default value if no radio button is selected
        }
    }

    // Method to get the ID of the hotel from its name
    private int getHotelIdFromName(String hotelName) throws SQLException {
        Connection conn = MySQLConnection.getConnection();
        String query = "SELECT id_hotel FROM hotel WHERE nom = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, hotelName);
        ResultSet resultSet = preparedStatement.executeQuery();
        int hotelId = -1; // Default value if no result is found
        if (resultSet.next()) {
            hotelId = resultSet.getInt("id_hotel");
        }
        preparedStatement.close();
        resultSet.close();
        conn.close();
        return hotelId;
    }

    private int getCompanyIdFromName(String companyName) throws SQLException {
        Connection conn = MySQLConnection.getConnection();
        String query = "SELECT id FROM compagny WHERE nom = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, companyName);
        ResultSet resultSet = preparedStatement.executeQuery();
        int companyId = -1; // Valeur par défaut si aucun résultat n'est trouvé
        if (resultSet.next()) {
            companyId = resultSet.getInt("id");
        }
        preparedStatement.close();
        resultSet.close();
        conn.close();
        return companyId;
    }
}
