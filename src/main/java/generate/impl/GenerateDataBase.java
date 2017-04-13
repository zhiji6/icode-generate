package generate.impl;

import domain.Column;
import domain.Database;
import domain.Table;
import generate.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex_ on 2017/4/13.
 */
public class GenerateDataBase extends IDataBase {

    private Connection conn = null;
    private String classDriver;
    private String url ;
    private String userName;
    private String passWord;
    private String schema;

    private final String[] s = new String[]{GType.TABLE.toString(), GType.VIEW.toString()};
    
    public GenerateDataBase(String classDriver, String url, String userName, String passWord, String schema){
        this.classDriver = classDriver;
        this.url = url;
        this.userName = userName;
        this.passWord = passWord;
        this.schema = schema;
    }


    @Override
    protected Connection getConnJDBC() {
        try{
            Class.forName(classDriver);
            conn = DriverManager.getConnection(url , userName ,passWord);
        }catch (ClassNotFoundException e) {
            System.out.println("Conn jdbc error: not found ..");
        } catch (SQLException e) {
            System.out.println("Conn jdbc error: the url have error");

        }
        return conn;
    }

    @Override
    protected Database getDBInfo(String tableNamePattern) throws SQLException {
        Database database = new Database();
        List<Table> tables = new ArrayList<Table>();

        this.getConnJDBC();

        DatabaseMetaData dbmd = conn.getMetaData();
        //TODO
        database.setDatabaseProductName(dbmd.getDatabaseProductName());
        ResultSet rs = dbmd.getTables(null, null ,tableNamePattern , s);
        Column column;
        while (rs.next()){
            Table t = new Table();
            t.setTableName(rs.getString(GType.TABLE_NAME.toString()));
            t.setTableComment(rs.getString(GType.REMARKS.toString()));
            t.setTableSchem(rs.getString(1));

            ResultSet rsc = dbmd.getColumns(null ,null,t.getTableName(),null);
            while (rsc.next()){
                column = new Column();
                column.setColumnName(rsc.getString(GType.COLUMN_NAME.toString()));
                column.setColumnType(Integer.parseInt(rsc.getString(GType.DATA_TYPE.toString())));

                String remarks = rsc.getString(GType.REMARKS.toString());
                if (remarks.length() < 1)
                    remarks = "";
                column.setColumnComment(remarks);
                column.setIsAutoIncrement(rsc.getString(GType.IS_AUTOINCREMENT.toString()).equals(GType.YES.toString()));
                column.setIsNullAble(rsc.getString(GType.IS_AUTOINCREMENT.toString()).equals(GType.YES.toString()));

                // 添加列到表中
                t.getColumnList().add(column);
            }
            // 设置主键列
            ResultSet rsPrimary = dbmd.getPrimaryKeys(null, null, t.getTableName());
            while (rsPrimary.next()) {
                if (rsPrimary.getString(GType.COLUMN_NAME.toString()) != null) {

                    for (int i = 0; i < t.getColumnList().size(); i++) {
                        Column col = t.getColumnList().get(i);
                        if (col.getColumnName().equals(rsPrimary.getString(GType.COLUMN_NAME.toString()))) {
                            col.setIsPrimary(true);
                        }
                    }

                }
            }
            tables.add(t);
        }

        database.setTableList(tables);
        return database;
    }
}
