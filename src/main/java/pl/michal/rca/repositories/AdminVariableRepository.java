package pl.michal.rca.repositories;

import pl.michal.rca.models.AdminVariable;
import pl.michal.rca.repositories.exceptions.DaoException;
import pl.michal.rca.repositories.helpers.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminVariableRepository {
    private static final String DB_NAME = "Reimbursement";
    private static final String DROP_DATABASE_IF_EXISTS = "DROP DATABASE IF EXISTS " + DB_NAME;
    private static final String CREATE_DATABASE_IF_NOT_EXISTS = "CREATE DATABASE IF NOT EXISTS " + DB_NAME + " CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci";
    private static final String CREATE_ADMIN_VARIABLES_TABLE_IF_NOT_EXISTS = "CREATE TABLE IF NOT EXISTS admin_variables (\n" +
            "    id INT NOT NULL AUTO_INCREMENT,\n" +
            "    type VARCHAR(255) NOT NULL,\n" +
            "    name VARCHAR(255) NOT NULL,\n" +
            "    value DOUBLE NOT NULL,\n" +
            "    state VARCHAR(255) NOT NULL,\n" +
            "    PRIMARY KEY (id)\n" +
            ");";
    private static final String DROP_ADMIN_VARIABLES_TABLE_IF_EXITS = "DROP TABLE IF EXISTS admin_variables";
    private static final String CREATE_QUERY =
            "INSERT INTO admin_variables(type, name, value,state) VALUES (?, ?, ?,?)";
    private static final String READ_ADMIN_VARIABLE_QUERY =
            "SELECT * FROM admin_variables where id = ?";
    private static final String READ_ADMIN_VARIABLE_BY_NAME_QUERY =
            "SELECT * FROM admin_variables where name = ?";
    private static final String READ_ALL_ADMIN_VARIABLES_ACTIVE_TYPE_QUERY =
            "SELECT * FROM admin_variables where type = ? AND state = ?";
    private static final String READ_ALL_ADMIN_VARIABLES_TYPE_QUERY =
            "SELECT * FROM admin_variables where type = ?";
    private static final String UPDATE_ADMIN_VARIABLE_QUERY =
            "UPDATE admin_variables SET type = ?, name = ?, value = ?, state=? WHERE id = ?";
    private static final String UPDATE_ADMIN_VARIABLE_VALUE_QUERY =
            "UPDATE admin_variables SET value = ? WHERE id = ?";
    private static final String UPDATE_ADMIN_VARIABLE_STATE_QUERY =
            "UPDATE admin_variables SET state = ? WHERE id = ?";
    private static final String DELETE_ADMIN_VARIABLE_QUERY =
            "DELETE FROM admin_variables WHERE id = ?";
    private static final String FIND_ALL_ADMIN_VARIABLES_QUERY =
            "SELECT * FROM admin_variables";
    private static final String DELETE_ALL_ADMIN_VARIABLES_QUERY = "DELETE FROM admin_variables";

    public void dropDatabaseIfExists() {
        try (Connection conn = DBUtil.connect()) {
            conn.createStatement().executeUpdate(DROP_DATABASE_IF_EXISTS);
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DaoException("Błąd usuwania bazy danych", ex);
        }
    }

    public void dropAdminVariablesTableIfExits() {
        try (Connection conn = DBUtil.connect()) {
            conn.createStatement().executeUpdate(DROP_ADMIN_VARIABLES_TABLE_IF_EXITS);
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DaoException("Błąd usuwania tabeli zmiennych administratora", ex);
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

    public void createAdminVariablesTableIfNotExists() {
        try (Connection conn = DBUtil.connect()) {
            conn.createStatement().executeUpdate(CREATE_ADMIN_VARIABLES_TABLE_IF_NOT_EXISTS);
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DaoException("Błąd tworzenia tabeli Zmiennych Administratora", ex);
        }
    }

    public AdminVariable create(AdminVariable adminVariable) {
        try (Connection conn = DBUtil.connect()) {
            PreparedStatement statement =
                    conn.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, adminVariable.getType());
            statement.setString(2, adminVariable.getName());
            statement.setDouble(3,adminVariable.getValue());
            statement.setString(4, adminVariable.getState());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                adminVariable.setId(resultSet.getInt(1));
            }
            return adminVariable;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DaoException("Błąd tworzenia zmiennej", ex);
        }
    }

    public AdminVariable read(int adminVariableId) {
        try (Connection conn = DBUtil.connect()) {
            PreparedStatement statement = conn.prepareStatement(READ_ADMIN_VARIABLE_QUERY);
            statement.setInt(1, adminVariableId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                AdminVariable adminVariable = new AdminVariable();
               adminVariable.setId(resultSet.getInt("id"));
               adminVariable.setType(resultSet.getString("type"));
               adminVariable.setName(resultSet.getString("name"));
               adminVariable.setValue(resultSet.getDouble("value"));
               adminVariable.setState(resultSet.getString("state"));

                return adminVariable;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DaoException("Błąd pobierania zamiennej", ex);
        }
        return null;
    }
    public AdminVariable readByName(String adminVariableName) {
        try (Connection conn = DBUtil.connect()) {
            PreparedStatement statement = conn.prepareStatement(READ_ADMIN_VARIABLE_BY_NAME_QUERY);
            statement.setString(1, adminVariableName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                AdminVariable adminVariable = new AdminVariable();
                adminVariable.setId(resultSet.getInt("id"));
                adminVariable.setType(resultSet.getString("type"));
                adminVariable.setName(resultSet.getString("name"));
                adminVariable.setValue(resultSet.getDouble("value"));
                adminVariable.setState(resultSet.getString("state"));

                return adminVariable;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DaoException("Błąd pobierania zamiennej", ex);
        }
        return null;
    }
    public List<AdminVariable> findAllTypeVariablesActive(String type) {
        try (Connection conn = DBUtil.connect()) {
            List<AdminVariable> adminVariables = new ArrayList<>();
            PreparedStatement statement = conn.prepareStatement(READ_ALL_ADMIN_VARIABLES_ACTIVE_TYPE_QUERY);
            statement.setString(1, type);
            statement.setString(2, "active");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                AdminVariable adminVariable = new AdminVariable();
                adminVariable.setId(resultSet.getInt("id"));
                adminVariable.setType(resultSet.getString("type"));
                adminVariable.setName(resultSet.getString("name"));
                adminVariable.setValue(resultSet.getDouble("value"));
                adminVariable.setState(resultSet.getString("state"));
                adminVariables.add(adminVariable);
            }
            return adminVariables;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DaoException("Błąd pobierania zmiennych", ex);
        }
    }

    public void update(AdminVariable adminVariable) {
        try (Connection conn = DBUtil.connect()) {
            PreparedStatement statement = conn.prepareStatement(UPDATE_ADMIN_VARIABLE_QUERY);
            statement.setString(1, adminVariable.getType());
            statement.setString(2, adminVariable.getName());
            statement.setDouble(3, adminVariable.getValue());
            statement.setString(4, adminVariable.getState());
            statement.setInt(5, adminVariable.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DaoException("Błąd edycji zmiennej", ex);
        }

    }

    public void updateValue(int variableId, Double value) {
        try (Connection conn = DBUtil.connect()) {
            PreparedStatement statement = conn.prepareStatement(UPDATE_ADMIN_VARIABLE_VALUE_QUERY);
            statement.setDouble(1, value);
            statement.setInt(2, variableId);
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DaoException("Błąd zmiany zmiennej", ex);
        }
    }

    public List<AdminVariable> findAll() {
        try (Connection conn = DBUtil.connect()) {
            List<AdminVariable> adminVariables = new ArrayList<>();
            PreparedStatement statement = conn.prepareStatement(FIND_ALL_ADMIN_VARIABLES_QUERY);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                AdminVariable adminVariable = new AdminVariable();
                adminVariable.setId(resultSet.getInt("id"));
                adminVariable.setType(resultSet.getString("type"));
                adminVariable.setName(resultSet.getString("name"));
                adminVariable.setValue(resultSet.getDouble("value"));
                adminVariables.add(adminVariable);
            }
            return adminVariables;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DaoException("Błąd pobierania zmiennych", ex);
        }
    }

    public void delete(int adminVariableId) {
        try (Connection conn = DBUtil.connect()) {
            PreparedStatement statement = conn.prepareStatement(DELETE_ADMIN_VARIABLE_QUERY);
            statement.setInt(1, adminVariableId);
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DaoException("Błąd usuwania zmiennej", ex);
        }
    }

    public void deleteAll() {
        try (Connection conn = DBUtil.connect()) {
            Statement statement = conn.createStatement();
            statement.executeUpdate(DELETE_ALL_ADMIN_VARIABLES_QUERY);
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DaoException("Błąd usuwania zmiennych", ex);
        }
    }

    public List<AdminVariable> findAllTypeVariables(String type) {
        try (Connection conn = DBUtil.connect()) {
            List<AdminVariable> adminVariables = new ArrayList<>();
            PreparedStatement statement = conn.prepareStatement(READ_ALL_ADMIN_VARIABLES_TYPE_QUERY);
            statement.setString(1, type);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                AdminVariable adminVariable = new AdminVariable();
                adminVariable.setId(resultSet.getInt("id"));
                adminVariable.setType(resultSet.getString("type"));
                adminVariable.setName(resultSet.getString("name"));
                adminVariable.setValue(resultSet.getDouble("value"));
                adminVariable.setState(resultSet.getString("state"));
                adminVariables.add(adminVariable);
            }
            return adminVariables;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DaoException("Błąd pobierania zmiennych", ex);
        }
    }

    public void updateState(int id, String state) {
        try (Connection conn = DBUtil.connect()) {
            PreparedStatement statement = conn.prepareStatement(UPDATE_ADMIN_VARIABLE_STATE_QUERY);
            statement.setString(1, state);
            statement.setInt(2, id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DaoException("Błąd zmiany zmiennej", ex);
        }
    }
}
