package domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex_ on 2017/4/13.
 */
public class Table {

    private String tableSchem;
    private String tableName;
    private String tableComment;
    private List<Column> columnList = new ArrayList<Column>();
    private String packName;
    private String packPath;
    private String className;
    private String classNameFirstLower;


    /**
     * 第一个主键
     * @return
     */
    public Column getFirstPrimaryKey(){
        for (Column column : columnList){
            if (column.getIsPrimary())
                return column;
        }
        return null;
    }




    public String getTableSchem() {
        return tableSchem;
    }

    public String getTableName() {
        return tableName;
    }

    public String getTableComment() {
        return tableComment;
    }

    public List<Column> getColumnList() {
        return columnList;
    }

    public String getPackName() {
        return packName;
    }

    public String getPackPath() {
        return packPath;
    }

    public String getClassName() {
        return className;
    }

    public String getClassNameFirstLower() {
        return classNameFirstLower;
    }

    public void setTableSchem(String tableSchem) {
        this.tableSchem = tableSchem;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }

    public void setColumnList(List<Column> columnList) {
        this.columnList = columnList;
    }

    public void setPackName(String packName) {
        this.packName = packName;
    }

    public void setPackPath(String packPath) {
        this.packPath = packPath;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setClassNameFirstLower(String classNameFirstLower) {
        this.classNameFirstLower = classNameFirstLower;
    }
}
