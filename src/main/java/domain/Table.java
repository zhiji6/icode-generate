package domain;

import generate.Generate;
import org.apache.commons.lang3.StringUtils;
import util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex_ on 2017/4/13.
 */
public class Table extends Config{

    private String tableSchem;
    private String tableName;
    private String tableComment;
    private List<Column> columnList = new ArrayList<Column>();
    private String packName;
    private String packPath;
    private String className;
    private String classNameFirstLower;



    public Column getFirstPrimaryKey(){
        for (Column column : columnList){
            if (column.getIsPrimary())
                return column;
        }
        return null;
    }

    public String getLowerDomainClassName() {
        return StringUtil.getFirstLower(this.getClassName());
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
        this.packPath = getPackName().replace(".", "/");
        return packPath;
    }

    public String getClassName() {
        if (Generate.PREFIX.equals("")) {
            // 直接输出
            this.className = StringUtil.getFirstUper(StringUtil.getDomainColumnName(this.tableName));
        } else {
            // 去除前缀
            if (this.tableName.startsWith(Generate.PREFIX)) {
                int pos = this.tableName.indexOf(Generate.PREFIX) + Generate.PREFIX.length();
                this.className = StringUtil.getFirstUper(StringUtil.getDomainColumnName(this.tableName.substring(pos, this.tableName.length())));
            } else if (this.tableName.indexOf("_") != -1) {
                this.className = StringUtil.getFirstUper(StringUtil.getDomainColumnName(this.tableName.substring( this.tableName.indexOf("_") + 1, this.tableName.length())));
            }
        }

        return this.className;
    }

    public String getClassNameFirstLower() {
        this.classNameFirstLower = StringUtils.uncapitalize(this.getClassName());
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
