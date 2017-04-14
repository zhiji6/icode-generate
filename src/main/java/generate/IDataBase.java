package generate;

import domain.Database;
import generate.impl.GenerateDataBase;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Alex_ on 2017/4/13.
 */
public interface IDataBase {

    Connection getConnJDBC();

    Database getDBInfo(String tableNamePattern)throws SQLException;

    Database getDBInfo()throws SQLException;



}
