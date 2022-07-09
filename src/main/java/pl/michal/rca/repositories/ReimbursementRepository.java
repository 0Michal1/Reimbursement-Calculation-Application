package pl.michal.rca.repositories;

import pl.michal.rca.models.Reimbursement;
import pl.michal.rca.repositories.exceptions.DaoException;
import pl.michal.rca.repositories.helpers.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReimbursementRepository {
    private static final String DB_NAME = "Reimbursement";
    private static final String DROP_DATABASE_IF_EXISTS = "DROP DATABASE IF EXISTS " + DB_NAME;
    private static final String CREATE_DATABASE_IF_NOT_EXISTS = "CREATE DATABASE IF NOT EXISTS " + DB_NAME + " CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci";
    private static final String CREATE_REIMBURSEMENTS_TABLE_IF_NOT_EXISTS = "CREATE TABLE IF NOT EXISTS reimbursements (\n" +
            "    id INT NOT NULL AUTO_INCREMENT,\n" +
            "    user_id INT NOT NULL,\n" +
            "    receipts_value DOUBLE,\n" +
            "    days INT,\n" +
            "    mileage INT,\n" +
            "    total DOUBLE,\n" +
            "    acceptance VARCHAR(255),\n" +
            "    username VARCHAR(255) NOT NULL,\n" +
            "    PRIMARY KEY (id),\n" +
            "    FOREIGN KEY (user_id)\n" +
            "    REFERENCES users(id)\n" +
            ");";
    private static final String DROP_REIMBURSEMENTS_TABLE_IF_EXITS = "DROP TABLE IF EXISTS reimbursements";
    private static final String CREATE_QUERY =
            "INSERT INTO reimbursements(user_id, receipts_value, days, mileage, total, acceptance, username  ) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String CREATE_STARTING_QUERY =
            "INSERT INTO reimbursements(user_id, username, acceptance ) VALUES (?, ?, ?)";
    private static final String READ_REIMBURSEMENT_QUERY =
            "SELECT * FROM reimbursements where id = ?";
    private static final String READ_ALL_USER_REIMBURSEMENTS_QUERY =
            "SELECT * FROM reimbursements where user_id = ?";
    private static final String UPDATE_REIMBURSEMENT_QUERY =
            "UPDATE reimbursements SET receipts_value = ?, days = ?, mileage = ?, total=? WHERE id = ?";
    private static final String UPDATE_REIMBURSEMENT_ACCEPTANCE_QUERY =
            "UPDATE reimbursements SET acceptance = ? WHERE id = ?";
    private static final String DELETE_REIMBURSEMENT_QUERY =
            "DELETE FROM reimbursements WHERE id = ?";
    private static final String FIND_ALL_REIMBURSEMENT_QUERY =
            "SELECT * FROM reimbursements";
    private static final String DELETE_ALL_REIMBURSEMENT_QUERY = "DELETE FROM reimbursements";

    public void dropDatabaseIfExists() {
        try (Connection conn = DBUtil.connect()) {
            conn.createStatement().executeUpdate(DROP_DATABASE_IF_EXISTS);
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DaoException("Błąd usuwania bazy danych", ex);
        }
    }

    public void dropReimbursementsTableIfExists() {
        try (Connection conn = DBUtil.connect()) {
            conn.createStatement().executeUpdate(DROP_REIMBURSEMENTS_TABLE_IF_EXITS);
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DaoException("Błąd usuwania tabeli Reimbursement", ex);
        }
    }

    public void createDatabaseIfNotExists() {
        try (Connection conn = DBUtil.connect()) {
            conn.createStatement().executeUpdate(CREATE_DATABASE_IF_NOT_EXISTS);
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DaoException("Błąd tworzenia bazy danych", ex);
        }
    }

    public void createReimbursementsTableIfNotExists() {
        try (Connection conn = DBUtil.connect()) {
            conn.createStatement().executeUpdate(CREATE_REIMBURSEMENTS_TABLE_IF_NOT_EXISTS);
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DaoException("Błąd tworzenia tabeli Reimbursement", ex);
        }
    }

    public Reimbursement create(Reimbursement reimbursement) {
        try (Connection conn = DBUtil.connect()) {
            PreparedStatement statement =
                    conn.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, reimbursement.getUserId());
            statement.setDouble(2, reimbursement.getReceiptsValue());
            statement.setInt(3, reimbursement.getDays());
            statement.setInt(4, reimbursement.getMileage());
            statement.setDouble(5, reimbursement.getTotal());
            statement.setString(6, reimbursement.getAcceptance());
            statement.setString(7, reimbursement.getUsername());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                reimbursement.setId(resultSet.getInt(1));
            }
            return reimbursement;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DaoException("Błąd tworzenia zwrotu", ex);
        }
    }
    public Reimbursement createStartingReimbursement(String username, int id, String acceptance){
        try (Connection conn = DBUtil.connect()) {
            PreparedStatement statement =
                    conn.prepareStatement(CREATE_STARTING_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, id);
            statement.setString(2, username);
            statement.setString(3, acceptance);
            statement.executeUpdate();
            Reimbursement reimbursement = new Reimbursement();
            reimbursement.setUsername(username);
            reimbursement.setUserId(id);
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {

                reimbursement.setId(resultSet.getInt(1));
            }
            return reimbursement;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DaoException("Błąd tworzenia zwrotu", ex);
        }
    }

    public Reimbursement read(int reimbursementId) {
        try (Connection conn = DBUtil.connect()) {
            PreparedStatement statement = conn.prepareStatement(READ_REIMBURSEMENT_QUERY);
            statement.setInt(1, reimbursementId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Reimbursement reimbursement = new Reimbursement();
                reimbursement.setId(resultSet.getInt("id"));
                reimbursement.setUserId(resultSet.getInt("user_id"));
                reimbursement.setReceiptsValue(resultSet.getDouble("receipts_value"));
                reimbursement.setDays(resultSet.getInt("days"));
                reimbursement.setMileage(resultSet.getInt("mileage"));
                reimbursement.setAcceptance(resultSet.getString("acceptance"));
                reimbursement.setUsername(resultSet.getString("username"));
                reimbursement.setTotal(resultSet.getDouble("total"));
                return reimbursement;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DaoException("Błąd pobierania reimbursement", ex);
        }
        return null;
    }

    public void update(Reimbursement reimbursement) {
        try (Connection conn = DBUtil.connect()) {
            PreparedStatement statement = conn.prepareStatement(UPDATE_REIMBURSEMENT_QUERY);
            statement.setDouble(1, reimbursement.getReceiptsValue());
            statement.setInt(2, reimbursement.getDays());
            statement.setInt(3, reimbursement.getMileage());
            statement.setDouble(4, reimbursement.getTotal());
            statement.setInt(5, reimbursement.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DaoException("Błąd edycji Reimbursement", ex);
        }

    }

    public void updateAcceptance(int reimbursementId, String acceptance) {
        try (Connection conn = DBUtil.connect()) {
            PreparedStatement statement = conn.prepareStatement(UPDATE_REIMBURSEMENT_ACCEPTANCE_QUERY);
            statement.setString(1, acceptance);
            statement.setInt(2, reimbursementId);
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DaoException("Błąd zmiany akceptacji", ex);
        }
    }
    public List<Reimbursement> findAllUserReimbursements(int userId) {
        try (Connection conn = DBUtil.connect()) {
            List<Reimbursement> reimbursements = new ArrayList<>();
            PreparedStatement statement = conn.prepareStatement(READ_ALL_USER_REIMBURSEMENTS_QUERY);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Reimbursement reimbursement = new Reimbursement();
                reimbursement.setId(resultSet.getInt("id"));
                reimbursement.setUserId(resultSet.getInt("user_id"));
                reimbursement.setReceiptsValue(resultSet.getDouble("receipts_value"));
                reimbursement.setDays(resultSet.getInt("days"));
                reimbursement.setMileage(resultSet.getInt("mileage"));
                reimbursement.setAcceptance(resultSet.getString("acceptance"));
                reimbursement.setUsername(resultSet.getString("username"));
                reimbursement.setTotal(resultSet.getDouble("total"));
                reimbursements.add(reimbursement);
            }
            return reimbursements;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DaoException("Błąd pobierania zwrotów użytkownika", ex);
        }
    }
    public List<Reimbursement> findAll() {
        try (Connection conn = DBUtil.connect()) {
            List<Reimbursement> reimbursements = new ArrayList<>();
            PreparedStatement statement = conn.prepareStatement(FIND_ALL_REIMBURSEMENT_QUERY);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Reimbursement reimbursement = new Reimbursement();
                reimbursement.setId(resultSet.getInt("id"));
                reimbursement.setUserId(resultSet.getInt("user_id"));
                reimbursement.setReceiptsValue(resultSet.getDouble("receipts_value"));
                reimbursement.setDays(resultSet.getInt("days"));
                reimbursement.setMileage(resultSet.getInt("mileage"));
                reimbursement.setAcceptance(resultSet.getString("acceptance"));
                reimbursement.setUsername(resultSet.getString("username"));
                reimbursement.setTotal(resultSet.getDouble("total"));
                reimbursements.add(reimbursement);
            }
            return reimbursements;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DaoException("Błąd pobierania zwrotów", ex);
        }
    }

    public void delete(int reimbursementId) {
        try (Connection conn = DBUtil.connect()) {
            PreparedStatement statement = conn.prepareStatement(DELETE_REIMBURSEMENT_QUERY);
            statement.setInt(1, reimbursementId);
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DaoException("Błąd usuwania zwrotu", ex);
        }
    }

    public void deleteAll() {
        try (Connection conn = DBUtil.connect()) {
            Statement statement = conn.createStatement();
            statement.executeUpdate(DELETE_ALL_REIMBURSEMENT_QUERY);
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DaoException("Błąd usuwania zwrotów", ex);
        }
    }
}
