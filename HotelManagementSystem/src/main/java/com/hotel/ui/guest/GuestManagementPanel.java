/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package main.java.com.hotel.ui.guest;

import java.awt.Container;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import main.java.com.hotel.config.DatabaseConnection;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

;

/**
 *
 * @author shalaka
 */
public class GuestManagementPanel extends javax.swing.JPanel {

    private List<GuestData> allGuests = new ArrayList<>();
    private List<GuestData> filteredGuests = new ArrayList<>();
    private int currentPage = 1;
    private int itemsPerPage = 10;
    private int totalPages = 1;
    private GuestData selectedGuest = null;

    /**
     * Creates new form guestManagementPanel
     */
    public GuestManagementPanel() {
        initComponents();
        initializeGuestManagementPanel();
    }

    // Guest data class to hold guest information
public static class GuestData {
    public int guestId;
    public String firstName;
    public String lastName;
    public String email;
    public String phone;
    public String address;
    public String city;
    public String state;
    public String country;
    public String postalCode;
    public String nationality;
    public String gender;
    public String dateOfBirth;
    public String idType;
    public String idNumber;
    public boolean vipStatus;
    public int loyaltyPoints;
    public String specialRequests;
    public String createdAt;
    public String currentBooking;
    
    public String getFullName() {
        return firstName + " " + lastName;
    }
    
    public String getFullAddress() {
        StringBuilder addr = new StringBuilder();
        if (address != null && !address.trim().isEmpty()) {
            addr.append(address);
        }
        if (city != null && !city.trim().isEmpty()) {
            if (addr.length() > 0) addr.append(", ");
            addr.append(city);
        }
        if (state != null && !state.trim().isEmpty()) {
            if (addr.length() > 0) addr.append(", ");
            addr.append(state);
        }
        if (country != null && !country.trim().isEmpty()) {
            if (addr.length() > 0) addr.append(", ");
            addr.append(country);
        }
        if (postalCode != null && !postalCode.trim().isEmpty()) {
            if (addr.length() > 0) addr.append(" ");
            addr.append(postalCode);
        }
        return addr.toString();
    }
    
    public String getIdString() {
        if (idType != null && idNumber != null) {
            return idType + " #" + idNumber;
        }
        return "No ID provided";
    }
}

// Initialize the panel with data loading
public void initializeGuestManagementPanel() {
    initializeComboBoxes();
    loadGuestsData();
    setupTableSelectionListener();
    clearSelectedGuestDetails();
}

// Initialize combo boxes with proper values
private void initializeComboBoxes() {
    // Filter options
    filter_guests.setModel(new DefaultComboBoxModel<>(new String[] {
        "All Guests", "VIP Guests", "Regular Guests", "With Current Booking", "No Current Booking"
    }));
    
    // Sort options
    sortBy.setModel(new DefaultComboBoxModel<>(new String[] {
        "Name (A-Z)", "Name (Z-A)", "Registration Date (Newest)", "Registration Date (Oldest)", 
        "Loyalty Points (High-Low)", "Loyalty Points (Low-High)"
    }));
    
    // Add action listeners
    filter_guests.addActionListener(e -> filterAndDisplayGuests());
    sortBy.addActionListener(e -> filterAndDisplayGuests());
    search_button.addActionListener(e -> searchGuests());
    pageNumber_previouse_button.addActionListener(e -> previousPage());
    pageNumber_next_button.addActionListener(e -> nextPage());
    
    // Add reset button functionality
    jButton3.addActionListener(e -> resetFilters());
}

// Load all guests data from database
private void loadGuestsData() {
    try {
        allGuests.clear();
        
        // Simplified query to avoid GROUP BY issues
        String query = "SELECT g.*, " +
                      "(SELECT CONCAT('Booking #', b.booking_number, ', Room ', r.room_number, ', ', " +
                      "DATE_FORMAT(b.check_in_date, '%b %d'), '-', " +
                      "DATE_FORMAT(b.check_out_date, '%d')) " +
                      "FROM booking b " +
                      "JOIN booking_room br ON b.booking_id = br.booking_id " +
                      "JOIN room r ON br.room_id = r.room_id " +
                      "WHERE b.guest_id = g.guest_id " +
                      "AND b.status IN ('Confirmed', 'Checked-in') " +
                      "AND b.check_out_date >= CURDATE() " +
                      "ORDER BY b.check_in_date DESC " +
                      "LIMIT 1) as current_booking " +
                      "FROM guest g " +
                      "ORDER BY g.created_at DESC";
        
        ResultSet rs = DatabaseConnection.executeSearch(query);
        
        while (rs.next()) {
            GuestData guest = new GuestData();
            guest.guestId = rs.getInt("guest_id");
            guest.firstName = rs.getString("first_name");
            guest.lastName = rs.getString("last_name");
            guest.email = rs.getString("email");
            guest.phone = rs.getString("contact_number");
            guest.address = rs.getString("address");
            guest.city = rs.getString("city");
            guest.state = rs.getString("state");
            guest.country = rs.getString("country");
            guest.postalCode = rs.getString("postal_code");
            guest.nationality = rs.getString("nationality");
            guest.gender = rs.getString("gender");
            guest.dateOfBirth = rs.getString("date_of_birth");
            guest.idType = rs.getString("id_type");
            guest.idNumber = rs.getString("id_number");
            guest.vipStatus = rs.getBoolean("vip_status");
            guest.loyaltyPoints = rs.getInt("loyalty_points");
            guest.specialRequests = rs.getString("special_requests");
            guest.createdAt = rs.getString("created_at");
            guest.currentBooking = rs.getString("current_booking");
            
            allGuests.add(guest);
        }
        
        filterAndDisplayGuests();
        
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, 
            "Error loading guests data: " + e.getMessage(), 
            "Database Error", 
            JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}

// Filter and display guests based on selected criteria
private void filterAndDisplayGuests() {
    filteredGuests.clear();
    
    String filterValue = filter_guests.getSelectedItem().toString();
    String searchText = search_field.getText().toLowerCase().trim();
    
    for (GuestData guest : allGuests) {
        boolean includeGuest = true;
        
        // Apply search filter
        if (!searchText.isEmpty()) {
            boolean matchesSearch = guest.getFullName().toLowerCase().contains(searchText) ||
                                  (guest.email != null && guest.email.toLowerCase().contains(searchText)) ||
                                  (guest.phone != null && guest.phone.contains(searchText)) ||
                                  (guest.nationality != null && guest.nationality.toLowerCase().contains(searchText));
            if (!matchesSearch) {
                includeGuest = false;
            }
        }
        
        // Apply category filter
        if (includeGuest) {
            switch (filterValue) {
                case "VIP Guests":
                    includeGuest = guest.vipStatus;
                    break;
                case "Regular Guests":
                    includeGuest = !guest.vipStatus;
                    break;
                case "With Current Booking":
                    includeGuest = guest.currentBooking != null;
                    break;
                case "No Current Booking":
                    includeGuest = guest.currentBooking == null;
                    break;
                // "All Guests" - no additional filtering
            }
        }
        
        if (includeGuest) {
            filteredGuests.add(guest);
        }
    }
    
    // Apply sorting
    sortGuests();
    
    // Update pagination
    updatePagination();
    
    // Display current page
    displayCurrentPage();
}

// Sort guests based on selected criteria
private void sortGuests() {
    String sortValue = sortBy.getSelectedItem().toString();
    
    switch (sortValue) {
        case "Name (A-Z)":
            filteredGuests.sort((a, b) -> a.getFullName().compareToIgnoreCase(b.getFullName()));
            break;
        case "Name (Z-A)":
            filteredGuests.sort((a, b) -> b.getFullName().compareToIgnoreCase(a.getFullName()));
            break;
        case "Registration Date (Newest)":
            filteredGuests.sort((a, b) -> b.createdAt.compareTo(a.createdAt));
            break;
        case "Registration Date (Oldest)":
            filteredGuests.sort((a, b) -> a.createdAt.compareTo(b.createdAt));
            break;
        case "Loyalty Points (High-Low)":
            filteredGuests.sort((a, b) -> Integer.compare(b.loyaltyPoints, a.loyaltyPoints));
            break;
        case "Loyalty Points (Low-High)":
            filteredGuests.sort((a, b) -> Integer.compare(a.loyaltyPoints, b.loyaltyPoints));
            break;
    }
}

// Update pagination information
private void updatePagination() {
    totalPages = (int) Math.ceil((double) filteredGuests.size() / itemsPerPage);
    if (totalPages == 0) totalPages = 1;
    
    if (currentPage > totalPages) {
        currentPage = totalPages;
    }
    if (currentPage < 1) {
        currentPage = 1;
    }
    
    pageNumber.setText("Page " + currentPage + " of " + totalPages);
    pageNumber_previouse_button.setEnabled(currentPage > 1);
    pageNumber_next_button.setEnabled(currentPage < totalPages);
}

// Display current page data in table
private void displayCurrentPage() {
    String[] columnNames = {"Name", "Contact", "Nationality", "Status"};
    DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    
    int startIndex = (currentPage - 1) * itemsPerPage;
    int endIndex = Math.min(startIndex + itemsPerPage, filteredGuests.size());
    
    for (int i = startIndex; i < endIndex; i++) {
        GuestData guest = filteredGuests.get(i);
        Object[] row = {
            guest.getFullName(),
            guest.phone + " / " + (guest.email != null ? guest.email : "No email"),
            guest.nationality != null ? guest.nationality : "Not specified",
            guest.vipStatus ? "VIP" : "Regular"
        };
        model.addRow(row);
    }
    
    guestsTable.setModel(model);
    
    // Clear selection if no data
    if (filteredGuests.isEmpty()) {
        clearSelectedGuestDetails();
    }
}

// Setup table selection listener
private void setupTableSelectionListener() {
    guestsTable.getSelectionModel().addListSelectionListener(e -> {
        if (!e.getValueIsAdjusting()) {
            int selectedRow = guestsTable.getSelectedRow();
            if (selectedRow >= 0) {
                int guestIndex = (currentPage - 1) * itemsPerPage + selectedRow;
                if (guestIndex < filteredGuests.size()) {
                    selectedGuest = filteredGuests.get(guestIndex);
                    displaySelectedGuestDetails();
                }
            }
        }
    });
}

// Display selected guest details
private void displaySelectedGuestDetails() {
    if (selectedGuest != null) {
        selectedGuestDetails_name.setText(selectedGuest.getFullName());
        selectedGuestDetails_email.setText(selectedGuest.email != null ? selectedGuest.email : "No email provided");
        selectedGuestDetails_id.setText(selectedGuest.getIdString());
        selectedGuestDetails_phone.setText(selectedGuest.phone);
        selectedGuestDetails_address.setText(selectedGuest.getFullAddress());
        selectedGuestDetails_vipStatus.setText(selectedGuest.vipStatus ? "VIP Guest" : "Regular Guest");
        selectedGuestDetails_loyaltyPoints.setText(String.valueOf(selectedGuest.loyaltyPoints));
        selectedGuestDetails_currentBooking.setText(
            selectedGuest.currentBooking != null ? selectedGuest.currentBooking : "No current booking"
        );
    }
}

// Clear selected guest details
private void clearSelectedGuestDetails() {
    selectedGuestDetails_name.setText("Select a guest to view details");
    selectedGuestDetails_email.setText("");
    selectedGuestDetails_id.setText("");
    selectedGuestDetails_phone.setText("");
    selectedGuestDetails_address.setText("");
    selectedGuestDetails_vipStatus.setText("");
    selectedGuestDetails_loyaltyPoints.setText("");
    selectedGuestDetails_currentBooking.setText("");
}

// Search functionality
private void searchGuests() {
    currentPage = 1;
    filterAndDisplayGuests();
}

// Reset all filters
private void resetFilters() {
    search_field.setText("");
    filter_guests.setSelectedIndex(0);
    sortBy.setSelectedIndex(0);
    currentPage = 1;
    filterAndDisplayGuests();
}

// Pagination methods
private void previousPage() {
    if (currentPage > 1) {
        currentPage--;
        displayCurrentPage();
        updatePagination();
        guestsTable.clearSelection();
        clearSelectedGuestDetails();
    }
}

private void nextPage() {
    if (currentPage < totalPages) {
        currentPage++;
        displayCurrentPage();
        updatePagination();
        guestsTable.clearSelection();
        clearSelectedGuestDetails();
    }
}

// Refresh data method - call this when a new guest is added
public void refreshGuestsData() {
    loadGuestsData();
}

// Get selected guest for other operations
public GuestData getSelectedGuest() {
    return selectedGuest;
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        addGuest_button = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        search_field = new javax.swing.JTextField();
        search_button = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        filter_guests = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        sortBy = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        guestsTable = new javax.swing.JTable();
        pageNumber = new javax.swing.JLabel();
        pageNumber_previouse_button = new javax.swing.JButton();
        pageNumber_next_button = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        selectedGuestDetails_name = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        selectedGuestDetails_email = new javax.swing.JLabel();
        selectedGuestDetails_id = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        selectedGuestDetails_phone = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        selectedGuestDetails_address = new javax.swing.JLabel();
        selectedGuestDetails_vipStatus = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        selectedGuestDetails_loyaltyPoints = new javax.swing.JLabel();
        selectedGuestDetails_currentBooking = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        selectedGuestDetails_viewDetailsButton = new javax.swing.JButton();
        selectedGuestDetails_edit_Button = new javax.swing.JButton();
        selectedGuestDetails_viewHistory_button = new javax.swing.JButton();
        selectedGuestDetails_Contact_button = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(930, 660));

        jLabel1.setText("Guest Management");

        addGuest_button.setText("+ Add Guest");
        addGuest_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addGuest_buttonActionPerformed(evt);
            }
        });

        jLabel2.setText("Search :");

        search_button.setText("search");

        jButton3.setText("Reset");

        jLabel3.setText("Filter :");

        filter_guests.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All Guests" }));

        jLabel4.setText("Sort by :");

        sortBy.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Name" }));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(search_field, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(search_button))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(filter_guests, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sortBy, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton3)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(search_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(search_button)
                        .addComponent(jButton3))
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(filter_guests, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(sortBy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel5.setText("Guest List");

        guestsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Name", "Contact", "Nationality", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(guestsTable);

        pageNumber.setText("Page 1 of 5");

        pageNumber_previouse_button.setText("< previouse");

        pageNumber_next_button.setText("next >");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 902, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(pageNumber_previouse_button)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(pageNumber)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(pageNumber_next_button)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pageNumber)
                    .addComponent(pageNumber_previouse_button)
                    .addComponent(pageNumber_next_button))
                .addContainerGap())
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel9.setText("Selected Guest Details");

        jLabel10.setText("Name :");

        selectedGuestDetails_name.setText("John Smith");

        jLabel12.setText("Email :");

        selectedGuestDetails_email.setText("john@gmail.com");

        selectedGuestDetails_id.setText("Passport  #A1234567");

        jLabel15.setText("ID :");

        jLabel16.setText("Phone :");

        selectedGuestDetails_phone.setText("555-1234");

        jLabel18.setText("Address :");

        selectedGuestDetails_address.setText("123 Main St, New York, NY 10001, USA");

        selectedGuestDetails_vipStatus.setText("Gold");

        jLabel21.setText("VIP Status :");

        jLabel22.setText("Current Booking :");

        selectedGuestDetails_loyaltyPoints.setText("500");

        selectedGuestDetails_currentBooking.setText("#B202301, Room 101, May 20-23");

        jLabel25.setText("Loyalty Points :");

        selectedGuestDetails_viewDetailsButton.setText("View Details");

        selectedGuestDetails_edit_Button.setText("Edit");

        selectedGuestDetails_viewHistory_button.setText("View History");

        selectedGuestDetails_Contact_button.setText("Contact");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(selectedGuestDetails_name))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(selectedGuestDetails_email)))
                        .addGap(87, 87, 87)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(selectedGuestDetails_id))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(selectedGuestDetails_phone))))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(selectedGuestDetails_address))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(selectedGuestDetails_vipStatus)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel25)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(selectedGuestDetails_loyaltyPoints))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(selectedGuestDetails_currentBooking))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(selectedGuestDetails_viewDetailsButton)
                        .addGap(18, 18, 18)
                        .addComponent(selectedGuestDetails_edit_Button)
                        .addGap(18, 18, 18)
                        .addComponent(selectedGuestDetails_viewHistory_button)
                        .addGap(18, 18, 18)
                        .addComponent(selectedGuestDetails_Contact_button)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(selectedGuestDetails_name))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(selectedGuestDetails_email)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(selectedGuestDetails_id))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(selectedGuestDetails_phone))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(selectedGuestDetails_address))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(selectedGuestDetails_vipStatus)
                    .addComponent(jLabel25)
                    .addComponent(selectedGuestDetails_loyaltyPoints))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(selectedGuestDetails_currentBooking))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(selectedGuestDetails_viewDetailsButton)
                    .addComponent(selectedGuestDetails_edit_Button)
                    .addComponent(selectedGuestDetails_viewHistory_button)
                    .addComponent(selectedGuestDetails_Contact_button))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(addGuest_button))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addGuest_button)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jScrollPane1.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 660, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void addGuest_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addGuest_buttonActionPerformed
        // Get the parent GuestMainPanel
        Container parent = this.getParent();
        while (parent != null && !(parent instanceof GuestMainPanel)) {
            parent = parent.getParent();
        }

        if (parent instanceof GuestMainPanel) {
            GuestMainPanel guestMainPanel = (GuestMainPanel) parent;
            // Switch to the New Guest tab (index 1)
            guestMainPanel.getGuestsTabs().setSelectedIndex(1);
        }
    }//GEN-LAST:event_addGuest_buttonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addGuest_button;
    private javax.swing.JComboBox<String> filter_guests;
    private javax.swing.JTable guestsTable;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel pageNumber;
    private javax.swing.JButton pageNumber_next_button;
    private javax.swing.JButton pageNumber_previouse_button;
    private javax.swing.JButton search_button;
    private javax.swing.JTextField search_field;
    private javax.swing.JButton selectedGuestDetails_Contact_button;
    private javax.swing.JLabel selectedGuestDetails_address;
    private javax.swing.JLabel selectedGuestDetails_currentBooking;
    private javax.swing.JButton selectedGuestDetails_edit_Button;
    private javax.swing.JLabel selectedGuestDetails_email;
    private javax.swing.JLabel selectedGuestDetails_id;
    private javax.swing.JLabel selectedGuestDetails_loyaltyPoints;
    private javax.swing.JLabel selectedGuestDetails_name;
    private javax.swing.JLabel selectedGuestDetails_phone;
    private javax.swing.JButton selectedGuestDetails_viewDetailsButton;
    private javax.swing.JButton selectedGuestDetails_viewHistory_button;
    private javax.swing.JLabel selectedGuestDetails_vipStatus;
    private javax.swing.JComboBox<String> sortBy;
    // End of variables declaration//GEN-END:variables
}
