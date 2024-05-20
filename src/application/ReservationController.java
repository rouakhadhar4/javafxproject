package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Pair;

public class ReservationController {

    @FXML
    private Label companyLabel;
    @FXML
    private Label destinationLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private TextField addressField;
    @FXML
    private TextField phoneField;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Label userLabel;


    private User currentUser;
    private String companyName;
    private String destination;
    private double price;

    public void setReservationData(User currentUser, String companyName, String destination, double price) {
        this.currentUser = currentUser;
        this.companyName = companyName;
        this.destination = destination;
        this.price = price;

        if (currentUser != null) {
            userLabel.setText("Utilisateur : " + currentUser.getUsername());
       
        } else {
            userLabel.setText("Utilisateur : Non connecté");
         
        }

        companyLabel.setText("Compagnie : " + companyName);
        destinationLabel.setText("Destination : " + destination);
        priceLabel.setText("Prix : " + price + " €");
    }
    @FXML
    private void handleConfirmReservation() {
        String address = addressField.getText();
        String phone = phoneField.getText();
        LocalDate date = datePicker.getValue(); // Obtain the selected date

        // Check if all fields are filled
        if (address.isEmpty() || phone.isEmpty() || date == null) {
            System.out.println("Veuillez remplir tous les champs !");
            return;
        }

        // Convert the date to SQL Date format
        java.sql.Date sqlDate = java.sql.Date.valueOf(date);

        // Get the user ID based on the username
        try {
            int userId = getUserId(currentUser.getUsername());

            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/agence", "root", "")) {
                String query = "INSERT INTO reservations (user_id, username, company_name, destination, price, address, phone, reservation_date, hotel_id, hotel_name) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

                statement.setInt(1, userId); // Set the user ID obtained from getUserId method
                statement.setString(2, currentUser.getUsername());
                statement.setString(3, companyName);
                statement.setString(4, destination);
                statement.setDouble(5, price);
                statement.setString(6, address);
                statement.setString(7, phone);
                statement.setDate(8, sqlDate);

                // Call the method to get hotel ID and name
                Pair<Integer, String> hotelInfo = getHotelIdAndName(); // Assuming you have a method to retrieve hotel ID and name
                statement.setInt(9, hotelInfo.getKey());
                statement.setString(10, hotelInfo.getValue());

                int affectedRows = statement.executeUpdate();

                if (affectedRows == 0) {
                    throw new SQLException("La création de la réservation a échoué, aucun enregistrement ajouté.");
                }

                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        System.out.println("ID de la nouvelle réservation: " + generatedKeys.getLong(1));
                    } else {
                        throw new SQLException("La création de la réservation a échoué, aucun ID récupéré.");
                    }
                }

                System.out.println("Réservation effectuée avec succès !");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Pair<Integer, String> getHotelIdAndName() throws SQLException {
        int hotelId = 0;
        String hotelName = "";

        // Connexion à la base de données
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/agence", "root", "")) {
            // Requête pour récupérer l'ID de l'hôtel et le nom de l'hôtel correspondant au nom de la compagnie
            String query = "SELECT hotel.id_hotel, hotel.nom " +
                           "FROM hotel " +
                           "JOIN compagny ON hotel.id_hotel = compagny.id_hotel " +
                           "WHERE compagny.nom = ?"; // Remplacez ? par le nom de la compagnie que vous recherchez

            try (PreparedStatement statement = conn.prepareStatement(query)) {
                // Remplacez companyName par le nom de la compagnie que vous recherchez
                statement.setString(1, companyName); 

                try (ResultSet resultSet = statement.executeQuery()) {
                    // Vérifier s'il y a des résultats
                    if (resultSet.next()) {
                        // Récupérer l'ID de l'hôtel
                        hotelId = resultSet.getInt("id_hotel");
                        // Récupérer le nom de l'hôtel
                        hotelName = resultSet.getString("nom");
                    }
                }
            }
        }

        return new Pair<>(hotelId, hotelName);
    }



    private int getUserId(String username) throws SQLException {
        int userId = 0; // Initialisez l'ID de l'utilisateur à une valeur par défaut

        // Établissez la connexion à la base de données
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/agence", "root", "")) {
            // Préparez et exécutez la requête SQL pour récupérer l'ID de l'utilisateur en fonction du nom d'utilisateur
            String query = "SELECT id FROM users WHERE username = ?";
            try (PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setString(1, username);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        userId = resultSet.getInt("id");
                    }
                }
            }
        }

        return userId;
    }
    



}
