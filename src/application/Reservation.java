package application;

public class Reservation {
    private int reservationId;
    private String username;
    private String hotelName;
    private String companyName;
    private double price;
    private String destination;
    private String address;
    private String phone;
    private String reservationDate;

    public Reservation(int reservationId, String username, String hotelName, String companyName, double price, String destination, String address, String phone, String reservationDate) {
        this.reservationId = reservationId;
        this.username = username;
        this.hotelName = hotelName;
        this.companyName = companyName;
        this.price = price;
        this.destination = destination;
        this.address = address;
        this.phone = phone;
        this.reservationDate = reservationDate;
    }

    public int getReservationId() {
        return reservationId;
    }

    public String getUsername() {
        return username;
    }

    public String getHotelName() {
        return hotelName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public double getPrice() {
        return price;
    }

    public String getDestination() {
        return destination;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getReservationDate() {
        return reservationDate;
    }
}
