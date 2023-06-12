import java.sql.*;

public class DuplicateFinder {
    public static void main(String[] args) {
        // Set up the mail sender
        MailSender mailSender = new MailSender("postur.simnet.is", "587");

        // Set up the database connection
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://nx0.247.is:3306/verkskraning?requireSSL=true", "query_employee", "whyDFFsiW$c6B&oadlGH")) {

            //test if connection was successful
            //System.out.println("Connected to the database successfully.");

            // First query
            try (Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery("SELECT Name, COUNT(Name) FROM employeegroups GROUP BY Name HAVING COUNT(Name) > 1");

                // Check the results
                if (rs.next()) {
                    // Build a string with the duplicate names
                    StringBuilder duplicates = new StringBuilder();
                    do {
                        duplicates.append(rs.getString("Name")).append("\n");
                    } while (rs.next());

                    // Send an email with the duplicates
                    mailSender.sendEmail("Duplicate Report", "Duplicates found:\n" + duplicates.toString(), "help.vhe.is@email.eu.autotask.net");
                }
            }

            // Second query
            try (Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery("SELECT Name, COUNT(Name) FROM employees GROUP BY Name HAVING COUNT(Name) > 1");

                // Check the results
                if (rs.next()) {
                    // Build a string with the duplicate names
                    StringBuilder duplicates = new StringBuilder();
                    do {
                        duplicates.append(rs.getString("Name")).append("\n");
                    } while (rs.next());

                    // Send an email with the duplicates
                    mailSender.sendEmail("Duplicate Report", "Duplicates found:\n" + duplicates.toString(), "help.vhe.is@email.eu.autotask.net");
                }
            }
        } catch (SQLException ex) {
            // Handle any errors
            ex.printStackTrace();
        }
    }
}
