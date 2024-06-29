package com.hotel;

import java.sql.*;
import java.util.Scanner;

public class HotelManagemntSystem {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/hotel_management_system";
    private static final String USER = "root";
    private static final String PASS = "root";
    private static Connection conn = null;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            boolean exit = false;
            while (!exit) {
                System.out.println("Welcome to Hotel Management System!");
                System.out.println("1. Manage Hotels");
                System.out.println("2. Manage Rooms");
                System.out.println("3. Manage Customers");
                System.out.println("4. Manage Payments");
                System.out.println("5. Manage Reservations");
                System.out.println("6. Exit");

                System.out.print("Select an operation you want to perform: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // consume newline

                switch (choice) {
                    case 1:
                        manageHotels();
                        break;
                    case 2:
                        manageRooms();
                        break;
                    case 3:
                        manageCustomers();
                        break;
                    case 4:
                        managePayments();
                        break;
                    case 5:
                        manageReservations();
                        break;
                    case 6:
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
            System.out.println("Thank you for using Hotel Management System.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void manageHotels() throws SQLException {
        boolean back = false;
        while (!back) {
            System.out.println("\nManage Hotels");
            System.out.println("1. Add Hotel");
            System.out.println("2. View Hotels");
            System.out.println("3. Update Hotel");
            System.out.println("4. Delete Hotel");
            System.out.println("5. Back");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    addHotel();
                    break;
                case 2:
                    viewHotels();
                    break;
                case 3:
                    updateHotel();
                    break;
                case 4:
                    deleteHotel();
                    break;
                case 5:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addHotel() throws SQLException {
        System.out.print("Enter hotel name: ");
        String name = scanner.nextLine();
        System.out.print("Enter hotel address: ");
        String address = scanner.nextLine();

        String query = "INSERT INTO Hotel (Hotel_Name, Hotel_Add) VALUES (?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, name);
            pstmt.setString(2, address);
            pstmt.executeUpdate();
            System.out.println("Hotel added successfully.");
        } catch (SQLException e) {
            System.out.println("Error adding hotel: " + e.getMessage());
        }
    }

    private static void viewHotels() throws SQLException {
        String query = "SELECT * FROM Hotel";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            System.out.println("\nHotels List:");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("Hotel_ID") + ", Name: " + rs.getString("Hotel_Name") +
                        ", Address: " + rs.getString("Hotel_Add"));
            }
        }
    }

    private static void updateHotel() throws SQLException {
        System.out.print("Enter hotel ID to update: ");
        int hotelId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter new hotel name: ");
        String name = scanner.nextLine();
        System.out.print("Enter new hotel address: ");
        String address = scanner.nextLine();

        String query = "UPDATE Hotel SET Hotel_Name = ?, Hotel_Add = ? WHERE Hotel_ID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, name);
            pstmt.setString(2, address);
            pstmt.setInt(3, hotelId);
            pstmt.executeUpdate();
            System.out.println("Hotel updated successfully.");
        } catch (SQLException e) {
            System.out.println("Error updating hotel: " + e.getMessage());
        }
    }

    private static void deleteHotel() throws SQLException {
        System.out.print("Enter hotel ID to delete: ");
        int hotelId = scanner.nextInt();
        scanner.nextLine(); // consume newline

        String query = "DELETE FROM Hotel WHERE Hotel_ID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, hotelId);
            pstmt.executeUpdate();
            System.out.println("Hotel deleted successfully.");
        } catch (SQLException e) {
            System.out.println("Error deleting hotel: " + e.getMessage());
        }
    }

    private static void manageRooms() throws SQLException {
        boolean back = false;
        while (!back) {
            System.out.println("\nManage Rooms");
            System.out.println("1. Add Room");
            System.out.println("2. View Rooms");
            System.out.println("3. Update Room");
            System.out.println("4. Delete Room");
            System.out.println("5. Back");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    addRoom();
                    break;
                case 2:
                    viewRooms();
                    break;
                case 3:
                    updateRoom();
                    break;
                case 4:
                    deleteRoom();
                    break;
                case 5:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addRoom() throws SQLException {
        System.out.print("Enter hotel ID: ");
        int hotelId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter room type: ");
        String type = scanner.nextLine();

        String query = "INSERT INTO Rooms (H_ID, Type) VALUES (?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, hotelId);
            pstmt.setString(2, type);
            pstmt.executeUpdate();
            System.out.println("Room added successfully.");
        } catch (SQLException e) {
            System.out.println("Error adding room: " + e.getMessage());
        }
    }

    private static void viewRooms() throws SQLException {
        String query = "SELECT * FROM Rooms";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            System.out.println("\nRooms List:");
            while (rs.next()) {
                System.out.println("Room ID: " + rs.getInt("Room_ID") + ", Hotel ID: " + rs.getInt("H_ID") +
                        ", Type: " + rs.getString("Type"));
            }
        }
    }

    private static void updateRoom() throws SQLException {
        System.out.print("Enter room ID to update: ");
        int roomId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter new hotel ID: ");
        int hotelId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter new room type: ");
        String type = scanner.nextLine();

        String query = "UPDATE Rooms SET H_ID = ?, Type = ? WHERE Room_ID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, hotelId);
            pstmt.setString(2, type);
            pstmt.setInt(3, roomId);
            pstmt.executeUpdate();
            System.out.println("Room updated successfully.");
        } catch (SQLException e) {
            System.out.println("Error updating room: " + e.getMessage());
        }
    }

    private static void deleteRoom() throws SQLException {
        System.out.print("Enter room ID to delete: ");
        int roomId = scanner.nextInt();
        scanner.nextLine(); // consume newline

        String query = "DELETE FROM Rooms WHERE Room_ID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, roomId);
            pstmt.executeUpdate();
            System.out.println("Room deleted successfully.");
        } catch (SQLException e) {
            System.out.println("Error deleting room: " + e.getMessage());
        }
    }

    private static void manageCustomers() throws SQLException {
        boolean back = false;
        while (!back) {
            System.out.println("\nManage Customers");
            System.out.println("1. Add Customer");
            System.out.println("2. View Customers");
            System.out.println("3. Update Customer");
            System.out.println("4. Delete Customer");
            System.out.println("5. Back");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    addCustomer();
                    break;
                case 2:
                    viewCustomers();
                    break;
                case 3:
                    updateCustomer();
                    break;
                case 4:
                    deleteCustomer();
                    break;
                case 5:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addCustomer() throws SQLException {
        System.out.print("Enter customer name: ");
        String name = scanner.nextLine();
        System.out.print("Enter customer address: ");
        String address = scanner.nextLine();
        System.out.print("Enter customer phone number: ");
        String phoneNo = scanner.nextLine();
        System.out.print("Enter customer email: ");
        String email = scanner.nextLine();

        String query = "INSERT INTO Customer (Name, Address, PhoneNo, Email) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, name);
            pstmt.setString(2, address);
            pstmt.setString(3, phoneNo);
            pstmt.setString(4, email);
            pstmt.executeUpdate();
            System.out.println("Customer added successfully.");
        } catch (SQLException e) {
            System.out.println("Error adding customer: " + e.getMessage());
        }
    }

    private static void viewCustomers() throws SQLException {
        String query = "SELECT * FROM Customer";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            System.out.println("\nCustomers List:");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("Customer_ID") + ", Name: " + rs.getString("Name") +
                        ", Address: " + rs.getString("Address") + ", Phone: " + rs.getString("PhoneNo") +
                        ", Email: " + rs.getString("Email"));
            }
        }
    }

    private static void updateCustomer() throws SQLException {
        System.out.print("Enter customer ID to update: ");
        int customerId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter new customer name: ");
        String name = scanner.nextLine();
        System.out.print("Enter new customer address: ");
        String address = scanner.nextLine();
        System.out.print("Enter new customer phone number: ");
        String phoneNo = scanner.nextLine();
        System.out.print("Enter new customer email: ");
        String email = scanner.nextLine();

        String query = "UPDATE Customer SET Name = ?, Address = ?, PhoneNo = ?, Email = ? WHERE Customer_ID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, name);
            pstmt.setString(2, address);
            pstmt.setString(3, phoneNo);
            pstmt.setString(4, email);
            pstmt.setInt(5, customerId);
            pstmt.executeUpdate();
            System.out.println("Customer updated successfully.");
        } catch (SQLException e) {
            System.out.println("Error updating customer: " + e.getMessage());
        }
    }

    private static void deleteCustomer() throws SQLException {
        System.out.print("Enter customer ID to delete: ");
        int customerId = scanner.nextInt();
        scanner.nextLine(); // consume newline

        String query = "DELETE FROM Customer WHERE Customer_ID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, customerId);
            pstmt.executeUpdate();
            System.out.println("Customer deleted successfully.");
        } catch (SQLException e) {
            System.out.println("Error deleting customer: " + e.getMessage());
        }
    }

    private static void managePayments() throws SQLException {
        boolean back = false;
        while (!back) {
            System.out.println("\nManage Payments");
            System.out.println("1. Add Payment");
            System.out.println("2. View Payments");
            System.out.println("3. Back");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    addPayment();
                    break;
                case 2:
                    viewPayments();
                    break;
                case 3:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addPayment() throws SQLException {
        System.out.print("Enter customer ID: ");
        int customerId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter payment method: ");
        String method = scanner.nextLine();
        System.out.print("Enter payment amount: ");
        double amount = scanner.nextDouble();

        String query = "INSERT INTO Payment (Customer_ID, P_Method, P_Amount) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, customerId);
            pstmt.setString(2, method);
            pstmt.setDouble(3, amount);
            pstmt.executeUpdate();
            System.out.println("Payment added successfully.");
        } catch (SQLException e) {
            System.out.println("Error adding payment: " + e.getMessage());
        }
    }

    private static void viewPayments() throws SQLException {
        String query = "SELECT * FROM Payment";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            System.out.println("\nPayments List:");
            while (rs.next()) {
                System.out.println("Payment ID: " + rs.getInt("P_Id") + ", Customer ID: " + rs.getInt("Customer_ID") +
                        ", Method: " + rs.getString("P_Method") + ", Amount: " + rs.getDouble("P_Amount"));
            }
        }
    }

    private static void manageReservations() throws SQLException {
        boolean back = false;
        while (!back) {
            System.out.println("\nManage Reservations");
            System.out.println("1. Add Reservation");
            System.out.println("2. View Reservations");
            System.out.println("3. Back");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    addReservation();
                    break;
                case 2:
                    viewReservations();
                    break;
                case 3:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addReservation() throws SQLException {
        System.out.print("Enter room ID: ");
        int roomId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter customer ID: ");
        int customerId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter check-in date (YYYY-MM-DD): ");
        String checkinDate = scanner.nextLine();
        System.out.print("Enter check-out date (YYYY-MM-DD): ");
        String checkoutDate = scanner.nextLine();

        String query = "INSERT INTO Reservation (Room_ID, Customer_ID, Checkin_Date, Checkout_Date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, roomId);
            pstmt.setInt(2, customerId);
            pstmt.setString(3, checkinDate);
            pstmt.setString(4, checkoutDate);
            pstmt.executeUpdate();
            System.out.println("Reservation added successfully.");
        } catch (SQLException e) {
            System.out.println("Error adding reservation: " + e.getMessage());
        }
    }

    private static void viewReservations() throws SQLException {
        String query = "SELECT * FROM Reservation";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            System.out.println("\nReservations List:");
            while (rs.next()) {
                System.out.println("Reservation ID: " + rs.getInt("Reservation_ID") + ", Room ID: " + rs.getInt("Room_ID") +
                        ", Customer ID: " + rs.getInt("Customer_ID") + ", Check-in: " + rs.getString("Checkin_Date") +
                        ", Check-out: " + rs.getString("Checkout_Date"));
            }
        }
    }
}
