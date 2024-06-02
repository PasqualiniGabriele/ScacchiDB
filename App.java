import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class App {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/ScacchiDB";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "rootpassword";

    public static void main(String[] args) {
        printGameMoves(Integer.parseInt(args[0]));
    }

    private static void printGameMoves(int gameId) {
        Connection connection;
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            String query = "SELECT Numero_mossa, Tempo_residuo, Codice_mossa FROM Mossa WHERE ID_partita = ? ORDER BY Numero_mossa";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, gameId);

            resultSet = preparedStatement.executeQuery();

            System.out.println("Moves for Game ID: " + gameId);
            while (resultSet.next()) {
                int moveNumber = resultSet.getInt("Numero_mossa");
                int timeRemaining = resultSet.getInt("Tempo_residuo");
                String moveCode = resultSet.getString("Codice_mossa");

                System.out.println("Move Number: " + moveNumber + ", Time Remaining: " + timeRemaining + ", Move Code: " + moveCode);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}