import a2.Example;
import a2.erd.*;
import a2.sql.*;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World");
        Database dtest = Example.loadDatabase();
        System.out.println(dtest);
        ERD etest = Example.loadERD();
        System.out.println(etest.relationships);
    }
}
