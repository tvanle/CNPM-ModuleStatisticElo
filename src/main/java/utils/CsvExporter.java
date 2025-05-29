package utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.TableModel;

import model.ChessPlayer;

public class CsvExporter {

    /**
     * Exports the given players list to a CSV file
     * 
     * @param players The list of ChessPlayer objects to export
     * @param filePath The complete file path where the CSV should be saved
     * @param tournamentName The name of the tournament for the report header
     * @return true if export was successful, false otherwise
     */
    public static boolean exportPlayersToCsv(List<ChessPlayer> players, String filePath, String tournamentName) {
        if (players == null || players.isEmpty()) {
            return false;
        }

        try (FileWriter writer = new FileWriter(filePath)) {
            // Write CSV header with timestamp
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            writer.append("Chess Tournament Elo Statistics Report\n");
            writer.append("Tournament: " + tournamentName + "\n");
            writer.append("Generated on: " + now.format(formatter) + "\n\n");

            // Write column headers
            writer.append("ID,Name,Birth Year,Nationality,Initial Elo,Final Elo,Elo Change\n");

            // Write data rows
            for (ChessPlayer player : players) {
                int eloChange = player.getFinalElo() - player.getInitialElo();
                String eloChangeStr = eloChange > 0 ? "+" + eloChange : String.valueOf(eloChange);

                writer.append(player.getId())
                      .append(",")
                      .append(escapeCsvField(player.getName()))
                      .append(",")
                      .append(String.valueOf(player.getBirthYear()))
                      .append(",")
                      .append(escapeCsvField(player.getNationality()))
                      .append(",")
                      .append(String.valueOf(player.getInitialElo()))
                      .append(",")
                      .append(String.valueOf(player.getFinalElo()))
                      .append(",")
                      .append(eloChangeStr)
                      .append("\n");
            }

            writer.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Exports a JTable's data to a CSV file
     * 
     * @param tableModel The table model containing the data
     * @param filePath The complete file path where the CSV should be saved
     * @param tournamentName The name of the tournament for the report header
     * @return true if export was successful, false otherwise
     */
    public static boolean exportTableToCsv(TableModel tableModel, String filePath, String tournamentName) {
        if (tableModel == null || tableModel.getRowCount() == 0) {
            return false;
        }

        try (FileWriter writer = new FileWriter(filePath)) {
            // Write CSV header with timestamp
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            writer.append("Chess Tournament Elo Statistics Report\n");
            writer.append("Tournament: " + tournamentName + "\n");
            writer.append("Generated on: " + now.format(formatter) + "\n\n");

            // Write column headers
            for (int i = 0; i < tableModel.getColumnCount(); i++) {
                writer.append(tableModel.getColumnName(i));
                if (i < tableModel.getColumnCount() - 1) {
                    writer.append(",");
                }
            }
            writer.append("\n");

            // Write data rows
            for (int row = 0; row < tableModel.getRowCount(); row++) {
                for (int col = 0; col < tableModel.getColumnCount(); col++) {
                    Object value = tableModel.getValueAt(row, col);
                    String cellValue = value == null ? "" : value.toString();
                    writer.append(escapeCsvField(cellValue));
                    if (col < tableModel.getColumnCount() - 1) {
                        writer.append(",");
                    }
                }
                writer.append("\n");
            }

            writer.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Escape special characters in CSV fields
     * If a field contains commas, quotes, or newlines, it needs to be enclosed in quotes
     */
    private static String escapeCsvField(String field) {
        if (field == null) {
            return "";
        }

        // Check if the field contains characters that need escaping
        if (field.contains(",") || field.contains("\"") || field.contains("\n") || field.contains("\r")) {
            // Escape any quotes by doubling them
            field = field.replace("\"", "\"\"");
            // Enclose the field in quotes
            return "\"" + field + "\"";
        }

        return field;
    }
}
