/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package main.java.com.hotel.ui.common;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import javax.swing.Timer;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Random;
import main.java.com.hotel.config.DatabaseConnection;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import org.jfree.data.time.Day;
import java.util.Date;
import java.util.Calendar;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author shalaka
 */
public class DashboardOverviewPanel extends javax.swing.JPanel {
    
    private ChartPanel jFreeChartPanel;
    private TimeSeries occupancySeries;
    private TimeSeries revenueSeries;
    private Timer animationTimer;
    private Timer dateTimeTimer;
    private Random random = new Random();
    private boolean isDatabaseConnected = false;

    public DashboardOverviewPanel() {
        initComponents();

        // Database connection check කරන්න
        if (testDatabaseConnection()) {
            System.out.println("Database connected - Loading real data");
            isDatabaseConnected = true;
            loadRealDashboardData();
        } else {
            System.out.println("Database not connected - Loading sample data");
            isDatabaseConnected = false;
            setSampleStatistics();
        }

        // Initialize bottom section with correct layout
        initializeBottomSection();

        setupAnimatedChart();
        startAnimation();
        startDateTimeDisplay();
    }
    
 

    /**
     * Test database connection
     */
    private boolean testDatabaseConnection() {
        try {
            String testQuery = "SELECT 1";
            ResultSet rs = DatabaseConnection.executeSearch(testQuery);
            boolean connected = rs.next();
            rs.close();
            return connected;
        } catch (Exception e) {
            System.out.println("Database connection failed: " + e.getMessage());
            return false;
        }
    }

    /**
     * Load real data from database
     */
    private void loadRealDashboardData() {
        loadStatistics();
        loadChartDataFromDatabase();
    }

    /**
     * Load statistics for the cards from database
     */
    private void loadStatistics() {
        try {
            // Total rooms
            String totalRoomsQuery = "SELECT COUNT(*) as total FROM room";
            ResultSet rs1 = DatabaseConnection.executeSearch(totalRoomsQuery);
            if (rs1.next()) {
                jLabel9.setText(rs1.getString("total")); // Total Rooms label
            }
            rs1.close();

            // Occupied rooms
            String occupiedQuery = "SELECT COUNT(*) as occupied FROM room WHERE status = 'Occupied'";
            ResultSet rs2 = DatabaseConnection.executeSearch(occupiedQuery);
            if (rs2.next()) {
                jLabel10.setText(rs2.getString("occupied")); // Occupied Rooms label
            }
            rs2.close();

            // Available rooms
            String availableQuery = "SELECT COUNT(*) as available FROM room WHERE status = 'Available'";
            ResultSet rs3 = DatabaseConnection.executeSearch(availableQuery);
            if (rs3.next()) {
                jLabel11.setText(rs3.getString("available")); // Available Rooms label
            }
            rs3.close();

            // Today's check-ins
            String checkinQuery = "SELECT COUNT(*) as checkins FROM booking WHERE DATE(check_in_date) = CURDATE() AND status = 'Confirmed'";
            ResultSet rs4 = DatabaseConnection.executeSearch(checkinQuery);
            if (rs4.next()) {
                jLabel12.setText(String.format("%02d", rs4.getInt("checkins"))); // Check-ins label
            }
            rs4.close();

            // Today's check-outs
            String checkoutQuery = "SELECT COUNT(*) as checkouts FROM booking WHERE DATE(check_out_date) = CURDATE() AND status = 'Checked-in'";
            ResultSet rs5 = DatabaseConnection.executeSearch(checkoutQuery);
            if (rs5.next()) {
                jLabel13.setText(String.format("%02d", rs5.getInt("checkouts"))); // Check-outs label
            }
            rs5.close();

            // Today's revenue
            String revenueQuery = "SELECT COALESCE(SUM(amount), 0) as revenue FROM payment WHERE DATE(payment_date) = CURDATE() AND status = 'Completed'";
            ResultSet rs6 = DatabaseConnection.executeSearch(revenueQuery);
            if (rs6.next()) {
                double revenue = rs6.getDouble("revenue");
                jLabel14.setText(String.format("$%.2f", revenue)); // Revenue label
            }
            rs6.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading statistics: " + e.getMessage());
            // Error නම් sample statistics set කරන්න
            setSampleStatistics();
        }
    }

    /**
     * Set sample statistics when database is not available
     */
    private void setSampleStatistics() {
        jLabel9.setText("50");   // Total Rooms
        jLabel10.setText("32");  // Occupied
        jLabel11.setText("18");  // Available
        jLabel12.setText("08");  // Check-ins
        jLabel13.setText("05");  // Check-outs
        jLabel14.setText("$3,250.00"); // Revenue
    }

    /**
     * Initialize the bottom section with quick actions and recent bookings
     */
    private void initializeBottomSection() {
        // jPanel1 layout change කරන්න BorderLayout එකට
        jPanel1.removeAll();
        jPanel1.setLayout(new BorderLayout(10, 0));

        // Quick actions panel - left side (30%)
        quickActionsPanel.setPreferredSize(new Dimension(280, 250));
        jPanel1.add(quickActionsPanel, BorderLayout.WEST);

        // Recent bookings panel - right side (70%)
        recentBookingsPanel.setPreferredSize(new Dimension(650, 250));
        jPanel1.add(recentBookingsPanel, BorderLayout.CENTER);

        // Setup recent bookings table
        setupRecentBookingsTable();

        // Load recent bookings data
        loadRecentBookingsData();

        // Add event listeners to buttons
        addQuickActionEventListeners();

        // Revalidate layout
        jPanel1.revalidate();
        jPanel1.repaint();
    }

    /**
     * Setup recent bookings table
     */
    private void setupRecentBookingsTable() {
        // Table model with proper columns
        String[] columnNames = {"Booking #", "Guest Name", "Room", "Check-In", "Status"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };

        recentBookingsTable.setModel(model);

        // Table appearance
        recentBookingsTable.setRowHeight(25);
        recentBookingsTable.setFont(new Font("Arial", Font.PLAIN, 12));
        recentBookingsTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        recentBookingsTable.getTableHeader().setBackground(new Color(240, 240, 240));
        recentBookingsTable.setSelectionBackground(new Color(232, 242, 254));
        recentBookingsTable.setGridColor(new Color(240, 240, 240));
        recentBookingsTable.setShowVerticalLines(true);

        // Column widths
        if (recentBookingsTable.getColumnModel().getColumnCount() >= 5) {
            recentBookingsTable.getColumnModel().getColumn(0).setPreferredWidth(80);  // Booking #
            recentBookingsTable.getColumnModel().getColumn(1).setPreferredWidth(150); // Guest Name
            recentBookingsTable.getColumnModel().getColumn(2).setPreferredWidth(60);  // Room
            recentBookingsTable.getColumnModel().getColumn(3).setPreferredWidth(100); // Check-In
            recentBookingsTable.getColumnModel().getColumn(4).setPreferredWidth(80);  // Status
        }

        // Center alignment for certain columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(javax.swing.JLabel.CENTER);

        if (recentBookingsTable.getColumnModel().getColumnCount() >= 5) {
            recentBookingsTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer); // Booking #
            recentBookingsTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer); // Room

            // Custom renderer for status column with colors
            recentBookingsTable.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(javax.swing.JTable table, Object value,
                        boolean isSelected, boolean hasFocus, int row, int column) {
                    Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                    if (value != null) {
                        String status = value.toString();

                        if (status.equals("Confirmed")) {
                            c.setForeground(new Color(46, 204, 113)); // Green
                        } else if (status.equals("Checked-in")) {
                            c.setForeground(new Color(41, 128, 185)); // Blue
                        } else if (status.equals("Checked-out")) {
                            c.setForeground(new Color(52, 73, 94)); // Dark gray
                        } else if (status.equals("Cancelled")) {
                            c.setForeground(new Color(231, 76, 60)); // Red
                        } else {
                            c.setForeground(Color.BLACK);
                        }
                    }

                    setHorizontalAlignment(javax.swing.JLabel.CENTER);
                    return c;
                }
            });
        }
    }

    /**
     * Load recent bookings data from database
     */
    private void loadRecentBookingsData() {
        try {
            if (!isDatabaseConnected) {
                loadSampleBookingsData();
                return;
            }

            // Get table model
            DefaultTableModel model = (DefaultTableModel) recentBookingsTable.getModel();

            // Clear existing data
            model.setRowCount(0);

            // Query for recent bookings (last 10)
            String query = "SELECT b.booking_number, "
                    + "CONCAT(g.first_name, ' ', g.last_name) AS guest_name, "
                    + "r.room_number, "
                    + "b.check_in_date, "
                    + "b.status "
                    + "FROM booking b "
                    + "JOIN guest g ON b.guest_id = g.guest_id "
                    + "JOIN booking_room br ON b.booking_id = br.booking_id "
                    + "JOIN room r ON br.room_id = r.room_id "
                    + "ORDER BY b.created_at DESC "
                    + "LIMIT 10";

            ResultSet rs = DatabaseConnection.executeSearch(query);

            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd");

            while (rs.next()) {
                String bookingNumber = rs.getString("booking_number");
                String guestName = rs.getString("guest_name");
                String roomNumber = rs.getString("room_number");
                Date checkInDate = rs.getDate("check_in_date");
                String status = rs.getString("status");

                // Format check-in date
                String checkIn = checkInDate != null ? dateFormat.format(checkInDate) : "N/A";

                // Add row to table
                Object[] row = {bookingNumber, guestName, roomNumber, checkIn, status};
                model.addRow(row);
            }

            rs.close();

            // If no data found, show message
            if (model.getRowCount() == 0) {
                Object[] noDataRow = {"No recent bookings found", "", "", "", ""};
                model.addRow(noDataRow);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading recent bookings: " + e.getMessage());
            loadSampleBookingsData();
        }
    }

    /**
     * Load sample bookings data when database is not available
     */
    private void loadSampleBookingsData() {
        DefaultTableModel model = (DefaultTableModel) recentBookingsTable.getModel();
        model.setRowCount(0);

        // Sample data
        Object[][] sampleData = {
            {"B202401", "John Smith", "101", "Dec 15", "Confirmed"},
            {"B202402", "Mary Johnson", "203", "Dec 14", "Checked-in"},
            {"B202403", "Robert Brown", "305", "Dec 13", "Checked-out"},
            {"B202404", "Lisa Wilson", "102", "Dec 12", "Confirmed"},
            {"B202405", "David Miller", "401", "Dec 11", "Cancelled"},
            {"B202406", "Sarah Davis", "204", "Dec 10", "Checked-in"},
            {"B202407", "Michael Garcia", "301", "Dec 09", "Checked-out"}
        };

        for (Object[] row : sampleData) {
            model.addRow(row);
        }
    }

    /**
     * Add event listeners to quick action buttons
     */
    private void addQuickActionEventListeners() {
        newBookingBtn.addActionListener(e -> {
            System.out.println("New Booking clicked");
            javax.swing.JOptionPane.showMessageDialog(this,
                    "New Booking feature will be implemented soon!",
                    "Coming Soon",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE);
        });

        checkinBtn.addActionListener(e -> {
            System.out.println("Check-in clicked");
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Check-in feature will be implemented soon!",
                    "Coming Soon",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE);
        });

        checkoutBtn.addActionListener(e -> {
            System.out.println("Check-out clicked");
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Check-out feature will be implemented soon!",
                    "Coming Soon",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE);
        });

        roomStatusBtn.addActionListener(e -> {
            System.out.println("Room Status clicked");
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Room Status feature will be implemented soon!",
                    "Coming Soon",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE);
        });

        // "See all bookings" label click listener
        lblSeeAllBookingsButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                System.out.println("See all bookings clicked");
                javax.swing.JOptionPane.showMessageDialog(DashboardOverviewPanel.this,
                        "Booking Management feature will be implemented soon!",
                        "Coming Soon",
                        javax.swing.JOptionPane.INFORMATION_MESSAGE);
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                lblSeeAllBookingsButton.setForeground(new Color(41, 128, 185));
                lblSeeAllBookingsButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                lblSeeAllBookingsButton.setForeground(new Color(52, 73, 94));
            }
        });
    }

    /**
     * Load chart data from database
     */
    private void loadChartDataFromDatabase() {
        try {
            loadHistoricalOccupancyData();
            loadHistoricalRevenueData();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading chart data: " + e.getMessage());
            loadSampleChartData();
        }
    }

    /**
     * Load historical occupancy data for chart
     */
    private void loadHistoricalOccupancyData() throws Exception {
        if (occupancySeries != null) {
            occupancySeries.clear();
        } else {
            occupancySeries = new TimeSeries("Room Occupancy %");
        }

        // Generate last 7 days data with current occupancy as base
        double currentOccupancy = getCurrentOccupancyPercentage();

        Calendar calendar = Calendar.getInstance();
        for (int i = 6; i >= 0; i--) {
            calendar.setTime(new Date());
            calendar.add(Calendar.DAY_OF_MONTH, -i);
            Day day = new Day(calendar.getTime());

            // Use current occupancy with some variation for historical data
            double occupancy = currentOccupancy + (random.nextDouble() * 20 - 10); // ±10% variation
            if (occupancy < 0) {
                occupancy = 0;
            }
            if (occupancy > 100) {
                occupancy = 100;
            }

            occupancySeries.add(day, occupancy);
        }
    }

    /**
     * Load historical revenue data for chart
     */
    private void loadHistoricalRevenueData() throws Exception {
        if (revenueSeries != null) {
            revenueSeries.clear();
        } else {
            revenueSeries = new TimeSeries("Daily Revenue ($)");
        }

        // Get today's revenue as base
        double todayRevenue = getTodayRevenue();

        Calendar calendar = Calendar.getInstance();
        for (int i = 6; i >= 0; i--) {
            calendar.setTime(new Date());
            calendar.add(Calendar.DAY_OF_MONTH, -i);
            Day day = new Day(calendar.getTime());

            // Generate historical revenue data based on today's revenue
            double revenue = todayRevenue + (random.nextDouble() * 2000 - 1000); // ±$1000 variation
            if (revenue < 0) {
                revenue = 0;
            }

            revenueSeries.add(day, revenue / 50); // Scale down for chart display
        }
    }

    /**
     * Get current occupancy percentage
     */
    private double getCurrentOccupancyPercentage() throws Exception {
        String query = "SELECT (COUNT(CASE WHEN status = 'Occupied' THEN 1 END) * 100.0 / COUNT(*)) as occupancy FROM room";
        ResultSet rs = DatabaseConnection.executeSearch(query);

        double occupancy = 0;
        if (rs.next()) {
            occupancy = rs.getDouble("occupancy");
        }
        rs.close();

        return occupancy;
    }

    /**
     * Get today's revenue
     */
    private double getTodayRevenue() throws Exception {
        String query = "SELECT COALESCE(SUM(amount), 0) as revenue FROM payment WHERE DATE(payment_date) = CURDATE() AND status = 'Completed'";
        ResultSet rs = DatabaseConnection.executeSearch(query);

        double revenue = 0;
        if (rs.next()) {
            revenue = rs.getDouble("revenue");
        }
        rs.close();

        return revenue;
    }

    /**
     * Load sample chart data when database is not available
     */
    private void loadSampleChartData() {
        if (occupancySeries != null) {
            occupancySeries.clear();
        } else {
            occupancySeries = new TimeSeries("Room Occupancy %");
        }

        if (revenueSeries != null) {
            revenueSeries.clear();
        } else {
            revenueSeries = new TimeSeries("Daily Revenue ($)");
        }

        Calendar calendar = Calendar.getInstance();
        for (int i = 6; i >= 0; i--) {
            calendar.setTime(new Date());
            calendar.add(Calendar.DAY_OF_MONTH, -i);
            Day day = new Day(calendar.getTime());

            double occupancy = 60 + (random.nextDouble() * 40); // 60-100%
            double revenue = 2000 + (random.nextDouble() * 3000); // $2000-5000

            occupancySeries.add(day, occupancy);
            revenueSeries.add(day, revenue / 50); // Scale down
        }
    }

    /**
     * Setup animated chart
     */
    private void setupAnimatedChart() {
        // Time series data create කරන්න (if not already created)
        if (occupancySeries == null) {
            occupancySeries = new TimeSeries("Room Occupancy %");
        }
        if (revenueSeries == null) {
            revenueSeries = new TimeSeries("Daily Revenue ($)");
        }

        // If no data loaded yet, load sample data
        if (occupancySeries.getItemCount() == 0) {
            loadSampleChartData();
        }

        // Dataset create කරන්න
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(occupancySeries);
        dataset.addSeries(revenueSeries);

        // Chart create කරන්න
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Hotel Performance Trends",
                "Date",
                "Value",
                dataset,
                true, // legend
                true, // tooltips
                false // URLs
        );

        // Chart customize කරන්න
        customizeLineChart(chart);

        // Chart panel create කරන්න
        jFreeChartPanel = new ChartPanel(chart);
        jFreeChartPanel.setPreferredSize(new Dimension(380, 280));
        jFreeChartPanel.setBackground(Color.WHITE);

        // NetBeans chart panel එකට add කරන්න
        if (chartPanel != null) {
            chartPanel.setLayout(new BorderLayout());
            chartPanel.removeAll();
            chartPanel.add(jFreeChartPanel, BorderLayout.CENTER);
            chartPanel.revalidate();
            chartPanel.repaint();
        }
    }

    /**
     * Customize line chart appearance
     */
    private void customizeLineChart(JFreeChart chart) {
        XYPlot plot = chart.getXYPlot();

        // Background colors
        plot.setBackgroundPaint(new Color(250, 250, 250));
        plot.setDomainGridlinePaint(new Color(200, 200, 200));
        plot.setRangeGridlinePaint(new Color(200, 200, 200));

        // Line renderer
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer(true, true);

        // Occupancy line (Blue curve)
        renderer.setSeriesPaint(0, new Color(52, 152, 219)); // Blue
        renderer.setSeriesStroke(0, new BasicStroke(3.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        renderer.setSeriesShape(0, new Ellipse2D.Double(-3, -3, 6, 6));
        renderer.setSeriesShapesVisible(0, true);
        renderer.setSeriesShapesFilled(0, true);

        // Revenue line (Green curve)
        renderer.setSeriesPaint(1, new Color(46, 204, 113)); // Green
        renderer.setSeriesStroke(1, new BasicStroke(3.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        renderer.setSeriesShape(1, new Ellipse2D.Double(-3, -3, 6, 6));
        renderer.setSeriesShapesVisible(1, true);
        renderer.setSeriesShapesFilled(1, true);

        plot.setRenderer(renderer);

        // Chart styling
        chart.setBackgroundPaint(Color.WHITE);
        chart.getTitle().setFont(new Font("Arial", Font.BOLD, 16));

        // Legend styling
        if (chart.getLegend() != null) {
            chart.getLegend().setBackgroundPaint(Color.WHITE);
            chart.getLegend().setFrame(org.jfree.chart.block.BlockBorder.NONE);
        }
    }

    /**
     * Start animation timer for real-time updates
     */
    private void startAnimation() {
        // Real-time animation (every 5 seconds)
        animationTimer = new Timer(5000, e -> {
            updateChartData();
        });
        animationTimer.start();
    }

    /**
     * Start date/time display timer
     */
    private void startDateTimeDisplay() {
        dateTimeTimer = new Timer(1000, e -> {
            updateDateTime();
        });
        dateTimeTimer.start();
        updateDateTime(); // Initial update
    }

    /**
     * Update date/time display
     */
    private void updateDateTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("EEEE, MMMM dd, yyyy - hh:mm:ss a");
        Date date = new Date();
        if (jLabel2 != null) {
            jLabel2.setText(formatter.format(date));
        }
    }

    /**
     * Update chart data (called by timer)
     */
    private void updateChartData() {
        try {
            if (isDatabaseConnected) {
                // Update statistics
                loadStatistics();

                // Update chart data
                loadLatestOccupancyData();
                loadLatestRevenueData();

                // Update recent bookings (every few updates)
                if (random.nextInt(3) == 0) { // Update bookings every ~15 seconds (3 * 5 seconds)
                    loadRecentBookingsData();
                }
            } else {
                // Add random data points for demo
                addRandomDataPoint();
            }

            // Chart refresh කරන්න
            if (jFreeChartPanel != null) {
                jFreeChartPanel.repaint();
            }

        } catch (Exception e) {
            e.printStackTrace();
            // Error නම් random data add කරන්න
            addRandomDataPoint();
        }
    }

    /**
     * Load latest occupancy data point
     */
    private void loadLatestOccupancyData() throws Exception {
        double occupancy = getCurrentOccupancyPercentage();

        Day today = new Day(new Date());
        if (occupancySeries.getItemCount() > 10) {
            occupancySeries.delete(0, 0); // Remove old data
        }
        occupancySeries.addOrUpdate(today, occupancy);
    }

    /**
     * Load latest revenue data point
     */
    private void loadLatestRevenueData() throws Exception {
        double revenue = getTodayRevenue();

        Day today = new Day(new Date());
        if (revenueSeries.getItemCount() > 10) {
            revenueSeries.delete(0, 0); // Remove old data
        }
        revenueSeries.addOrUpdate(today, revenue / 50); // Scale down
    }

    /**
     * Add random data point for demo purposes
     */
    private void addRandomDataPoint() {
        Day today = new Day(new Date());

        double occupancy = 60 + (random.nextDouble() * 40);
        double revenue = (2000 + (random.nextDouble() * 3000)) / 50;

        if (occupancySeries.getItemCount() > 10) {
            occupancySeries.delete(0, 0);
            revenueSeries.delete(0, 0);
        }

        occupancySeries.addOrUpdate(today, occupancy);
        revenueSeries.addOrUpdate(today, revenue);
    }

    /**
     * Stop all timers
     */
    public void stopAllTimers() {
        stopAnimation();

        if (dateTimeTimer != null && dateTimeTimer.isRunning()) {
            dateTimeTimer.stop();
        }
    }

    /**
     * Stop animation timer
     */
    public void stopAnimation() {
        if (animationTimer != null && animationTimer.isRunning()) {
            animationTimer.stop();
        }
    }

    /**
     * Panel cleanup when removed
     */
    @Override
    public void removeNotify() {
        stopAllTimers();
        super.removeNotify();
    }

    /**
     * Refresh dashboard data manually
     */
    public void refreshData() {
        if (isDatabaseConnected) {
            loadRealDashboardData();
        } else {
            setSampleStatistics();
            loadSampleChartData();
        }

        loadRecentBookingsData();

        if (jFreeChartPanel != null) {
            jFreeChartPanel.repaint();
        }
    }

    /**
     * Refresh recent bookings data
     */
    public void refreshRecentBookings() {
        loadRecentBookingsData();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        gridPanel = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        chartPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        quickActionsPanel = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        newBookingBtn = new javax.swing.JButton();
        checkinBtn = new javax.swing.JButton();
        checkoutBtn = new javax.swing.JButton();
        roomStatusBtn = new javax.swing.JButton();
        recentBookingsPanel = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        recentBookingsTable = new javax.swing.JTable();
        lblSeeAllBookingsButton = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(930, 695));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Dashboard Overview");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Current Date Time");

        gridPanel.setLayout(new java.awt.GridLayout(2, 0, 6, 8));

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel3.setText("Total Rooms");

        jLabel9.setText("50");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel9))
                .addContainerGap(228, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addContainerGap())
        );

        gridPanel.add(jPanel2);

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel4.setText("Occupied Rooms");

        jLabel10.setText("32");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel10))
                .addContainerGap(203, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addContainerGap())
        );

        gridPanel.add(jPanel3);

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel5.setText("Available Rooms");

        jLabel11.setText("18");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel11))
                .addContainerGap(206, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addComponent(jLabel11)
                .addContainerGap())
        );

        gridPanel.add(jPanel4);

        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel6.setText("Today's Check-ins");

        jLabel12.setText("08");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel12))
                .addContainerGap(198, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addComponent(jLabel12)
                .addContainerGap())
        );

        gridPanel.add(jPanel5);

        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel7.setText("Today's Check-outs");

        jLabel13.setText("05");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel13))
                .addContainerGap(190, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addComponent(jLabel13)
                .addContainerGap())
        );

        gridPanel.add(jPanel6);

        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel8.setText("Today's Revenue");

        jLabel14.setText("$3,250.00");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel14))
                .addContainerGap(206, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addComponent(jLabel14)
                .addContainerGap())
        );

        gridPanel.add(jPanel7);

        javax.swing.GroupLayout chartPanelLayout = new javax.swing.GroupLayout(chartPanel);
        chartPanel.setLayout(chartPanelLayout);
        chartPanelLayout.setHorizontalGroup(
            chartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        chartPanelLayout.setVerticalGroup(
            chartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 181, Short.MAX_VALUE)
        );

        jPanel1.setLayout(new java.awt.GridLayout(1, 0, 6, 0));

        quickActionsPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        quickActionsPanel.setPreferredSize(new java.awt.Dimension(400, 102));

        jLabel15.setText("Quick Actions");

        newBookingBtn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        newBookingBtn.setText("New Booking");

        checkinBtn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        checkinBtn.setText("Check-in");

        checkoutBtn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        checkoutBtn.setText("Check-out");

        roomStatusBtn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        roomStatusBtn.setText("Room Status");

        javax.swing.GroupLayout quickActionsPanelLayout = new javax.swing.GroupLayout(quickActionsPanel);
        quickActionsPanel.setLayout(quickActionsPanelLayout);
        quickActionsPanelLayout.setHorizontalGroup(
            quickActionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(quickActionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(quickActionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(newBookingBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 442, Short.MAX_VALUE)
                    .addComponent(checkinBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(checkoutBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(roomStatusBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        quickActionsPanelLayout.setVerticalGroup(
            quickActionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(quickActionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(newBookingBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(checkinBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(checkoutBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(roomStatusBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(quickActionsPanel);

        recentBookingsPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        recentBookingsPanel.setPreferredSize(new java.awt.Dimension(512, 102));

        jLabel16.setText("Recent Bookings");

        recentBookingsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Booking", "Guest", "Room", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(recentBookingsTable);

        lblSeeAllBookingsButton.setText("See all bookings");

        javax.swing.GroupLayout recentBookingsPanelLayout = new javax.swing.GroupLayout(recentBookingsPanel);
        recentBookingsPanel.setLayout(recentBookingsPanelLayout);
        recentBookingsPanelLayout.setHorizontalGroup(
            recentBookingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(recentBookingsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(recentBookingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 442, Short.MAX_VALUE)
                    .addGroup(recentBookingsPanelLayout.createSequentialGroup()
                        .addComponent(lblSeeAllBookingsButton)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        recentBookingsPanelLayout.setVerticalGroup(
            recentBookingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(recentBookingsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblSeeAllBookingsButton)
                .addContainerGap())
        );

        jPanel1.add(recentBookingsPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(gridPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2))
                    .addComponent(chartPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addComponent(gridPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(chartPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel chartPanel;
    private javax.swing.JButton checkinBtn;
    private javax.swing.JButton checkoutBtn;
    private javax.swing.JPanel gridPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblSeeAllBookingsButton;
    private javax.swing.JButton newBookingBtn;
    private javax.swing.JPanel quickActionsPanel;
    private javax.swing.JPanel recentBookingsPanel;
    private javax.swing.JTable recentBookingsTable;
    private javax.swing.JButton roomStatusBtn;
    // End of variables declaration//GEN-END:variables
}
