package pl.michal.rca.repositories;

import pl.michal.rca.models.Receipt;
import pl.michal.rca.repositories.exceptions.DaoException;
import pl.michal.rca.repositories.helpers.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReceiptRepository {
    private static final String DB_NAME = "Reimbursement";
    private static final String DROP_DATABASE_IF_EXISTS = "DROP DATABASE IF EXISTS " + DB_NAME;
    private static final String CREATE_DATABASE_IF_NOT_EXISTS = "CREATE DATABASE IF NOT EXISTS " + DB_NAME + " CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci";
    private static final String CREATE_RECEIPTS_TABLE_IF_NOT_EXISTS = "CREATE TABLE IF NOT EXISTS receipts (\n" +
            "    id INT NOT NULL AUTO_INCREMENT,\n" +
            "    type_id  INT NOT NULL,\n" +
            "    value DOUBLE NOT NULL,\n" +
            "    reimbursement_id INT NOT NULL,\n" +
            "    PRIMARY KEY (id),\n" +
            "    FOREIGN KEY (reimbursement_id)\n" +
            "    REFERENCES reimbursements(id),\n" +
            "    FOREIGN KEY (type_id)\n" +
            "    REFERENCES admin_variables(id)\n" +
            ");";
    private static final String DROP_RECEIPTS_TABLE_IF_EXITS = "DROP TABLE IF EXISTS receipts";
    private static final String CREATE_QUERY =
            "INSERT INTO receipts(type_id, value, reimbursement_id) VALUES (?, ?, ?)";
    private static final String READ_RECEIPT_QUERY =
            "SELECT * FROM receipts where id = ?";
    private static final String READ_ALL_REIMBURSEMENT_RECEIPTS_QUERY =
            "SELECT * FROM receipts where reimbursement_id = ?";
    private static final String UPDATE_RECEIPT_QUERY =
            "UPDATE receipts SET type_id = ?, value = ?, reimbursement_id = ? WHERE id = ?";
    private static final String UPDATE_RECEIPT_VALUE_QUERY =
            "UPDATE receipts SET value = ? WHERE id = ?";
    private static final String DELETE_RECEIPT_QUERY =
            "DELETE FROM receipts WHERE id = ?";
    private static final String FIND_ALL_RECEIPT_QUERY =
            "SELECT * FROM receipts";
    private static final String DELETE_ALL_RECEIPTS_QUERY = "DELETE FROM receipts";

    public void dropDatabaseIfExists() {
        try (Connection conn = DBUtil.connect()) {
            conn.createStatement().executeUpdate(DROP_DATABASE_IF_EXISTS);
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DaoException("Błąd usuwania bazy danych", ex);
        }
    }

    public void dropReceiptTableIfExits() {
        try (Connection conn = DBUtil.connect()) {
            conn.createStatement().executeUpdate(DROP_RECEIPTS_TABLE_IF_EXITS);
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DaoException("Błąd usuwania tabeli paragonów", ex);
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

    public void createReceiptsTableIfNotExists() {
        try (Connection conn = DBUtil.connect()) {
            conn.createStatement().executeUpdate(CREATE_RECEIPTS_TABLE_IF_NOT_EXISTS);
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DaoException("Błąd tworzenia tabeli paragonów", ex);
        }
    }

    public Receipt create(Receipt receipt) {
        try (Connection conn = DBUtil.connect()) {
            PreparedStatement statement =
                    conn.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, receipt.getTypeId());
            statement.setDouble(2, receipt.getValue());
            statement.setInt(3, receipt.getReimbursementId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                receipt.setId(resultSet.getInt(1));
            }
            return receipt;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DaoException("Błąd tworzenia paragonu", ex);
        }
    }

    public Receipt read(int receiptId) {
        try (Connection conn = DBUtil.connect()) {
            PreparedStatement statement = conn.prepareStatement(READ_RECEIPT_QUERY);
            statement.setInt(1, receiptId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Receipt receipt= new Receipt() ;
                receipt.setId(resultSet.getInt("id"));
                receipt.setTypeId(resultSet.getInt("type_id"));
                receipt.setReimbursementId(resultSet.getInt("reimbursement_id"));
                receipt.setValue(resultSet.getDouble("value"));

                return receipt;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DaoException("Błąd pobierania paragonu", ex);
        }
        return null;
    }
    public List<Receipt> findAllReimbursementReceipts(int reimbursementId) {
        try (Connection conn = DBUtil.connect()) {
            List<Receipt> receipts = new ArrayList<>();
            PreparedStatement statement = conn.prepareStatement(READ_ALL_REIMBURSEMENT_RECEIPTS_QUERY);
            statement.setInt(1, reimbursementId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Receipt receipt = new Receipt();
                receipt.setId(resultSet.getInt("id"));
                receipt.setTypeId(resultSet.getInt("type_id"));
                receipt.setReimbursementId(resultSet.getInt("reimbursement_id"));
                receipt.setValue(resultSet.getDouble("value"));
                receipts.add(receipt);
            }
            return receipts;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DaoException("Błąd pobierania paragonów", ex);
        }
    }

    public void update(Receipt receipt) {
        try (Connection conn = DBUtil.connect()) {
            PreparedStatement statement = conn.prepareStatement(UPDATE_RECEIPT_QUERY);
            statement.setInt(1, receipt.getTypeId());
            statement.setDouble(2, receipt.getValue());
            statement.setInt(3, receipt.getReimbursementId());
            statement.setInt(4, receipt.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DaoException("Błąd edycji paragonu", ex);
        }

    }

    public void updateValue(Receipt receipt, Double value) {
        try (Connection conn = DBUtil.connect()) {
            PreparedStatement statement = conn.prepareStatement(UPDATE_RECEIPT_VALUE_QUERY);
            statement.setDouble(1, value);
            statement.setInt(2, receipt.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DaoException("Błąd zmiany paragonu", ex);
        }
    }

    public List<Receipt> findAll() {
        try (Connection conn = DBUtil.connect()) {
            List<Receipt> receipts = new ArrayList<>();
            PreparedStatement statement = conn.prepareStatement(FIND_ALL_RECEIPT_QUERY);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Receipt receipt = new Receipt();
                receipt.setId(resultSet.getInt("id"));
                receipt.setTypeId(resultSet.getInt("type_id"));
                receipt.setReimbursementId(resultSet.getInt("reimbursement_id"));
                receipt.setValue(resultSet.getDouble("value"));
                receipts.add(receipt);
            }
            return receipts;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DaoException("Błąd pobierania paragonów", ex);
        }
    }

    public void delete(int receiptId) {
        try (Connection conn = DBUtil.connect()) {
            PreparedStatement statement = conn.prepareStatement(DELETE_RECEIPT_QUERY);
            statement.setInt(1, receiptId);
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DaoException("Błąd usuwania paragonu", ex);
        }
    }

    public void deleteAll() {
        try (Connection conn = DBUtil.connect()) {
            Statement statement = conn.createStatement();
            statement.executeUpdate(DELETE_ALL_RECEIPTS_QUERY);
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DaoException("Błąd usuwania paragonów", ex);
        }
    }
}
