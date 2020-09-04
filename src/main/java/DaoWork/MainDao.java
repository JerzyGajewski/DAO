package DaoWork;

import pl.coderslab.entity.UserDao;

import java.sql.SQLException;

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
        showReadResult(userDao.read(1));
        readAllUsers(userDao);
        userDao.deleteUser(1);





    }



    public static void showReadResult(User read) {
        if (read == null) {
            System.out.println("Wrong id");
        } else
            System.out.println(read.getId() + " " + read.getEmail() + " " + read.getUser() + " " + read.getPassword());
    }

    public static void readAllUsers(UserDao userDao) {
        User[] all = userDao.readAll();
        for (int i = 0; i < all.length; i++) {
            System.out.println(all[i].getId() + " " + all[i].getEmail() + " " + all[i].getUser() + " " + all[i].getPassword());
        }
    }
}
