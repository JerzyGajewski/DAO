package DaoWork;

import pl.coderslab.entity.UserDao;

import java.sql.SQLException;
import java.util.Arrays;

public class MainDao {
    public static void main(String[] args) {
        try {
            DbUtil.connect();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        String create = "CREATE TABLE IF NOT EXISTS users (\n" +
                "    id INT(11) AUTO_INCREMENT,\n" +
                "    email VARCHAR(255) NOT NULL UNIQUE,\n" +
                "    username VARCHAR(255) NOT NULL,\n" +
                "    password VARCHAR(60) NOT NULL,\n" +
                "    PRIMARY KEY (id)\n" +
                ")";
        DbUtil.create(create);

        UserDao userDao = new UserDao();
        UserDao.readAllUsers(userDao);
    }




}
