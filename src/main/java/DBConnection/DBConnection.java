package DBConnection;

import lombok.Getter;


public class DBConnection {

    private static DBConnection instance;

    @Getter
    private int connection; // connection ref
    private DBConnection(){
        //Sql connection code
        connection = 100;
    }


    public static DBConnection getInstance() {
        return instance==null?instance = new DBConnection():instance;
    }

}
