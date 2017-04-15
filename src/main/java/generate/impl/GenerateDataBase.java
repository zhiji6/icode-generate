package generate.impl;

import domain.Column;
import domain.Database;
import domain.Table;
import generate.*;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import util.VelocityUtil;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * generate 中心实现类
 * Created by Alex_ on 2017/4/13.
 */
public class GenerateDataBase implements IDataBase  , ICodeGenerate{
    private static final Logger logger = Logger.getLogger(GenerateDataBase.class);
    private Connection conn = null;
    private String classDriver;
    private String url ;
    private String userName;
    private String passWord;
    private String schema;
    private String tableName;

    private final String[] s = new String[]{GType.TABLE.toString(), GType.VIEW.toString()};

    public  GenerateDataBase(String classDriver , String url , String userName , String passWord){
        this(classDriver ,url , userName , passWord , null , null);
    }
    public GenerateDataBase(String classDriver, String url, String userName, String passWord,String tableName, String schema){
        init(classDriver , url , userName , passWord , tableName , schema);
    }

    private GenerateDataBase init(String classDriver, String url, String userName, String passWord,String tableName, String schema){
        this.classDriver = classDriver;
        this.url = url;
        this.userName = userName;
        this.passWord = passWord==null? "" :passWord;
        this.tableName = tableName;
        this.schema = schema;
        return this;
    }


    public Connection getConnJDBC() {
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


    public Database getDBInfo(String tableNamePattern) throws SQLException {
        Database database = new Database();
        this.getConnJDBC();
        DatabaseMetaData dbmd = conn.getMetaData();
        database.setDatabaseProductName(dbmd.getDatabaseProductName());
        ResultSet rs = dbmd.getTables(null, null ,tableNamePattern , s);

        database.setTableList(getTableList(rs , dbmd));
        return database;
    }

    public Database getDBInfo() throws SQLException {

        Database databaseBean = new Database();
        getConnJDBC();
        DatabaseMetaData dbmd = conn.getMetaData();
        databaseBean.setDatabaseProductName(dbmd.getDatabaseProductName());

        ResultSet rs = dbmd.getTables(null, this.schema, null, s);

        databaseBean.setTableList(getTableList(rs ,dbmd));
        return databaseBean;
    }


    private List<Table> getTableList(ResultSet rs , DatabaseMetaData dbmd)throws SQLException{

        List<Table> tableList = new ArrayList<Table>();

        while (rs.next()){
            Table t = new Table();
            t.setTableName(rs.getString(GType.TABLE_NAME.toString()));
            t.setTableComment(rs.getString(GType.REMARKS.toString()));
            t.setTableSchem(rs.getString(1));

            ResultSet rsc = dbmd.getColumns(null ,null,t.getTableName(),null);

            Column column ;
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

                t.getColumnList().add(column);
            }

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
            /*//
            ResultSet rsFPrimary = dbmd.getImportedKeys(null, null, table.getTableName());
            while (rsFPrimary.next()) {

                for (int i = 0; i < table.getColumnList().size(); i++) {
                    Column coltemp = table.getColumnList().get(i);
                    if (coltemp.getColumnName().equals(rsFPrimary.getString("FKCOLUMN_NAME"))) {
                        //System.out.println("FKCOLUMN_NAME "+rsFPrimary.getString("FKCOLUMN_NAME"));
                        coltemp.setForeignKey(true);
                    }
                }
            }*/
            tableList.add(t);
        }
        return tableList;
    }


    public boolean generate(String classPackage, String author, String contact, String codePath) {

        String sourcePath = codePath + File.separator + "src/";
        Long start = System.currentTimeMillis();
        Database databaseBean ;
        try{
            if (null == this.schema|| "".equals(this.schema))
                new NullPointerException();

            if (null != tableName && !"".equals(tableName.trim()))
                databaseBean = getDBInfo(tableName);
            else
                databaseBean = getDBInfo();

            List<Table> tableList = databaseBean.getTableList();
            logger.info("----------- start -----------");

            for (Table table : tableList){
                table.setPackName(classPackage);
                Map<String , Object> map = new HashedMap();
                map.put("table" , table);
                map.put("contact" , contact);
                map.put("author", author);

                //TODO ...
                VelocityUtil.generatorCode("domain.vm", map, sourcePath + table.getPackPath() + "/domain", table.getClassName() + ".java");
                VelocityUtil.generatorCode("controller.vm", map, sourcePath + table.getPackPath() + "/controller", table.getClassName() + "Controller.java");
            }

            logger.info("--------------- end time:" + (System.currentTimeMillis() - start) + "ms-----");
            logger.info("the code path:" + codePath);
            logger.info("package:" + classPackage);
            logger.info("author:" + author);
            logger.info("contact:" + contact);
            logger.info("------------------------------------");
            return true;

        }catch (Exception e){
            logger.error("generate(): generate error!!!");
            return false;
        }
    }
}
