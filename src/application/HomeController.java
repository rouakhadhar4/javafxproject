package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class HomeController implements Initializable {

    @FXML
    private TextField searchField;
    @FXML
    private TextField destinationField;
    @FXML
    private TextField periodField;
    @FXML
    private GridPane cardsContainer;

    private User currentUser; // Supposez que cela soit défini après la connexion de l'utilisateur

    private void displayCompanies(String searchTerm, String destination, String flightPeriod) {
    	cardsContainer.getChildren().clear(); // Efface les anciennes cartes avant d'afficher les nouvelles

        String query = "SELECT c.nom AS companyName, c.pays AS companyCountry, c.adresse AS companyAddress, c.telephone, c.destination, " +
                       "c.periodeVole, c.prixVole, c.image, h.nom AS hotelName, h.adresse AS hotelAddress, " +
                       "h.ville AS hotelCity, h.pays AS hotelCountry, h.etoiles, h.description " +
                       "FROM compagny c " +
                       "JOIN hotel h ON c.id_hotel = h.id_hotel " +
                       "WHERE c.nom LIKE ?";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/agence", "root", "");
             PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setString(1, "%" + searchTerm + "%");
            ResultSet resultSet = statement.executeQuery();

            int row = 0;
            int col = 0;
            while (resultSet.next()) {
                // Récupérer les informations sur la compagnie
                String imageUrl = resultSet.getString("image");
                Image image = new Image(imageUrl);
                String companyName = resultSet.getString("companyName");
                String companyCountry = resultSet.getString("companyCountry");
                String companyAddress = resultSet.getString("companyAddress");
                String phone = resultSet.getString("telephone");
                String destination1 = resultSet.getString("destination");
                String flightPeriod1 = resultSet.getString("periodeVole");
                double price = resultSet.getDouble("prixVole");

                // Récupérer les informations sur l'hôtel
                String hotelName = resultSet.getString("hotelName");
                String hotelAddress = resultSet.getString("hotelAddress");
                String hotelCity = resultSet.getString("hotelCity");
                String hotelCountry = resultSet.getString("hotelCountry");
                int stars = resultSet.getInt("etoiles");
                String description = resultSet.getString("description");

                // Créer la carte de la compagnie
                Card card = new Card(image, companyName, companyCountry, companyAddress, phone, destination1, flightPeriod1, price);

                // Créer les détails de l'hôtel
                VBox hotelDetails = new VBox();
                hotelDetails.setSpacing(5);
                hotelDetails.setStyle("-fx-padding: 10; -fx-background-color: #f9f9f9; -fx-border-color: #ccc; -fx-border-width: 1; -fx-border-radius: 5; -fx-background-radius: 5;");

                Label detailsTitleLabel = new Label("Détails sur l'Hôtel:");
                detailsTitleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
                Label hotelNameLabel = new Label("Nom de l'Hôtel: " + hotelName);
                Label hotelAddressLabel = new Label("Adresse de l'Hôtel: " + hotelAddress);
                Label hotelCityLabel = new Label("Ville: " + hotelCity);
                Label hotelCountryLabel = new Label("Pays: " + hotelCountry);
                Label starsLabel = new Label("Étoiles: " + stars);
                Label descriptionLabel = new Label("Description: " + description);

                // Ajouter les détails de l'hôtel à la VBox
                hotelDetails.getChildren().addAll(detailsTitleLabel, hotelNameLabel, hotelAddressLabel, hotelCityLabel, hotelCountryLabel, starsLabel, descriptionLabel);

                // Créer le bouton "Réserver"
                Button reserveButton = new Button("Réserver");
                reserveButton.setOnAction(event -> handleReservation(currentUser, companyName, destination1, price));
                // Créer le bouton "Réserver"
               


                // Ajouter le bouton "Réserver" à la VBox des détails de l'hôtel
                hotelDetails.getChildren().add(reserveButton);

                // Ajouter la carte et les détails de l'hôtel à la grille
                VBox cardContainer = new VBox(card, hotelDetails);
                cardContainer.setSpacing(10);
                cardContainer.setStyle("-fx-padding: 10; -fx-background-color: #fff; -fx-border-color: #ccc; -fx-border-width: 1; -fx-border-radius: 5; -fx-background-radius: 5;");

                cardsContainer.add(cardContainer, col, row);

                col++;
                if (col == 3) { // Changer le nombre de colonnes selon la largeur souhaitée
                    col = 0;
                    row++;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
    }


    private static class Card extends VBox {
        public Card(Image image, String companyName, String country, String address, String phone, String destination, String flightPeriod, double price) {
            setSpacing(10);

            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(200);
            imageView.setPreserveRatio(true);

            Label labelCompany = new Label("Company: " + companyName);
            Label labelCountry = new Label("Country: " + country);
            Label labelAddress = new Label("Address: " + address);
            Label labelPhone = new Label("Phone: " + phone);
            Label labelDestination = new Label("Destination: " + destination);
            Label labelFlightPeriod = new Label("Flight Period: " + flightPeriod);
            Label labelPrice = new Label("Price: " + price);

            getChildren().addAll(imageView, labelCompany, labelCountry, labelAddress, labelPhone, labelDestination, labelFlightPeriod, labelPrice);
        }
    }

    @FXML
    void handleHotelDetails() {
        // Implémentez la logique pour afficher les détails de l'hôtel ici
    }

    @FXML
    void handleReservation(User currentUser, String companyName, String destination, double price) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("reservation.fxml"));
            Parent root = loader.load();

            ReservationController controller = loader.getController();
            controller.setReservationData(currentUser, companyName, destination, price);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleSearch() {
        String searchTerm = searchField.getText();
        String destination = destinationField.getText();
        String period = periodField.getText();
        displayCompanies(searchTerm, destination, period);
        System.out.println("Recherché : " + searchTerm);
    }


    @FXML
    void handleDetails(MouseEvent event) {
        // Ajoutez ici la logique pour gérer le clic sur le bouton de détails
    }

    @FXML
    void handleHome(ActionEvent event) {
        System.out.println("Clicked on Home");
    }

    @FXML
    void handleVoyages(ActionEvent event) {
        try {
            Node sourceNode = (Node) event.getSource();
            Scene currentScene = sourceNode.getScene();
            Stage stage = (Stage) currentScene.getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("avis.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
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
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        displayCompanies("","",""); // Affiche toutes les entreprises au démarrage
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }
}