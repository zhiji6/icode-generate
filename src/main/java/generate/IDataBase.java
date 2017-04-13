package generate;

import domain.Database;
import generate.impl.GenerateDataBase;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Alex_ on 2017/4/13.
 */
public abstract class IDataBase {

    public static IDataBase init(String classDriver, String url, String userName, String passWord){
        return init(classDriver , url ,userName ,passWord , null);
    }

    public static IDataBase init(String classDriver, String url, String userName, String passWord, String schema){
        return new GenerateDataBase(classDriver , url ,userName , passWord , schema);
    }


    protected abstract Connection getConnJDBC();

    protected abstract Database getDBInfo(String tableNamePattern)throws SQLException;

}
