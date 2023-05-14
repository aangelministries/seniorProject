package service;

import java.sql.*;

public class JDBCConnection {
        private static final String USERNAME = "root";
        private static final String PASSWORD = "root123!";
        private static final String CONN_STRING = "jdbc:mysql://127.0.0.1:3306/senior_project/?user=root";
//    private static final String CONN_STRING="jdbc:mysql://127.0.0.1:3306/?user=root/senior_projects";

    //throw new RedirectToUrlException("http://localhost/home:8080");
        static Connection conn;
        public static Connection connectToDatabase() {

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException exc) {
                throw new RuntimeException(exc);
            }

            // connect way #1
            String url1 = "jdbc:mysql://localhost:3306/senior_project";
            String user = "root";
            String password = "root123!";

            try {
                conn = DriverManager.getConnection(url1, user, password);
                if (conn != null) {
                    System.out.println("Connected to the database");
                    return conn;
                }
            } catch (SQLException exc) {
                throw new RuntimeException(exc);
            }
            return null;
        }

        public int getUserLogin(String username, String password){
            Statement stmt =null;
            ResultSet rs =null;
            try
            {
                stmt=conn.createStatement();
                rs =stmt.executeQuery("select * from user where username="+username+"and password="+password);
               // System.out.println(rs.next());
                return rs.findColumn("ID");
            } catch (SQLException e)
            {
                e.printStackTrace();
                return 0;
            }

        }

        public long getAccountBalance(int userId){
            Statement stmt =null;
            ResultSet rs =null;
            try
            {
                stmt=conn.createStatement();
                rs =stmt.executeQuery("select * from account where userId=" + userId);
                System.out.println(rs.next());
                return rs.findColumn("value");
            } catch (SQLException e)
            {
                e.printStackTrace();
                return 0;
            }

        }
        public boolean createDeposit(long value, int userId){
            Statement stmt =null;
            ResultSet rs =null;
            try
            {
                stmt=conn.createStatement();
                long balanceValue = getAccountBalance(userId)+value;
                rs =stmt.executeQuery("update account set value=" + balanceValue +
                        + userId + value + ")");
                System.out.println(rs.next());
                return true;
            } catch (SQLException e)
            {
                e.printStackTrace();
                return false;
            }

        }

        public boolean createDispense(long value, int userId){
            Statement stmt =null;
            ResultSet rs =null;
            try
            {
                long accountBalance = getAccountBalance(userId);
                if(accountBalance>value) {
                    stmt = conn.createStatement();
                    rs =stmt.executeQuery("update account set value=" + accountBalance +
                            + userId + value + ")");
                    System.out.println(rs.next());
                    return true;
                } else {
                    return false;
                }
            } catch (SQLException e)
            {
                e.printStackTrace();
                return false;
            }

        }
        }
