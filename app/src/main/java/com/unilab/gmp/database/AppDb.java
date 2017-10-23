package com.unilab.gmp.database;

public class AppDb extends EngineDatabase {

    public final static String DB_NAME = "db_gmp.sqlite";
    public final static int DB_VERSION = 1;

    public AppDb() {
        super(DB_NAME, DB_VERSION, true);
//        addTable(new ProductTable());
    }

}
