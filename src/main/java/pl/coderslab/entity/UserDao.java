package pl.coderslab.entity;

import DaoWork.DbUtil;
import DaoWork.User;


import java.sql.*;
import java.util.Arrays;

public class UserDao {
    private static final String CREATE_USER_QUERRY = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
    private static final String CHANGE_USER_DATA_QUERRY = "UPDATE users SET username = ?, email = ?, password = ? WHERE id = ?";
    private static final String READ_USER_DATA_QUERRY = "SELECT * FROM users WHERE id = ?";
    private static final String FIND_ALL_QUERRY = "SELECT * FROM users";
    private static final String DELETE_USER_QUERRY = "DELETE FROM users WHERE id = ?";

    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }


    public User createUser(User user) {
        try (Connection connection = DbUtil.connect();
             PreparedStatement statement = connection.prepareStatement(CREATE_USER_QUERRY, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getUser());
            statement.setString(2, user.getEmail());
            statement.setString(3, hashPassword(user.getPassword()));
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User read(int userId) {
        try (Connection conn = DbUtil.connect();
             PreparedStatement statement = conn.prepareStatement(READ_USER_DATA_QUERRY)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUser(resultSet.getString("username"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    public void update(User user) {
        try (Connection conn = DbUtil.connect();
             PreparedStatement statement = conn.prepareStatement(CHANGE_USER_DATA_QUERRY)) {
            statement.setString(1, user.getUser());
            statement.setString(2, user.getEmail());
            statement.setString(3, this.hashPassword(user.getPassword()));
            statement.setInt(4, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User[] readAll() {
        try (Connection conn = DbUtil.connect();
             PreparedStatement statement = conn.prepareStatement(FIND_ALL_QUERRY)) {
            User[] arr = new User[0];
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUser(resultSet.getString("username"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                arr = addToArray(user, arr);
            }
                return arr;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    private User[] addToArray(User u, User[] users){
        User[] tmpUsers = Arrays.copyOf(users, users.length +1);
        tmpUsers[tmpUsers.length -1] = u;
        return tmpUsers;
    }
    public void deleteUser(int userId){

        try (Connection conn = DbUtil.connect();
             PreparedStatement statement = conn.prepareStatement(DELETE_USER_QUERRY)){
            statement.setInt(1,userId);
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }

    }
}