package domain;

import util.StringUtil;

/**
 * Created by Alex_ on 2017/4/13.
 */
public class Column {

    private String columnName;
    private Integer columnType;
    private String columnTypeStringName;//数据库字段类型名称
    private String columnComment;
    private Boolean isPrimary = false; //is primary ?
    private Boolean isAutoIncrement = false;  //is auto increment?
    private Boolean isNullAble = false;//is null?
    private Boolean isForeignKey = false; //abandon
    private String fieldName;
    private String fieldType;
    private String fieldGetMethod;
    private String fieldSetMethod;


    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public Integer getColumnType() {
        return columnType;
    }

    public void setColumnType(Integer columnType) {
        this.columnType = columnType;
    }

    public String getColumnComment() {
        return columnComment;
    }

    public void setColumnComment(String columnComment) {
        this.columnComment = columnComment;
    }

    public Boolean getIsPrimary() {
        return isPrimary;
    }

    public void setIsPrimary(Boolean isPrimary) {
        this.isPrimary = isPrimary;
    }

    public Boolean getIsAutoIncrement() {
        return isAutoIncrement;
    }

    public void setIsAutoIncrement(Boolean isAutoIncrement) {
        this.isAutoIncrement = isAutoIncrement;
    }

    public Boolean getIsNullAble() {
        return isNullAble;
    }

    public void setIsNullAble(Boolean isNullAble) {
        this.isNullAble = isNullAble;
    }

    public Boolean getIsForeignKey() {
        return isForeignKey;
    }

    public void setIsForeignKey(Boolean isForeignKey) {
        this.isForeignKey = isForeignKey;
    }

    public String getFieldName() {
        this.fieldName = StringUtil.getFirstLower(StringUtil.getDomainColumnName(this.columnName));
        return this.fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldType() {
        this.fieldType = StringUtil.getColumnType(this.columnType);
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getFieldGetMethod() {
        if(null != this.fieldName){
            this.fieldGetMethod = StringUtil.getGetMethod(this.fieldName);
        } else {
            if(null != this.columnName){
                this.fieldGetMethod = StringUtil.getGetMethod(this.columnName);
            }
        }
        return fieldGetMethod;
    }

    public void setFieldGetMethod(String fieldGetMethod) {
        this.fieldGetMethod = fieldGetMethod;
    }

    public String getFieldSetMethod() {
        if (null != this.fieldName)
            this.fieldSetMethod = StringUtil.getSetMethod(this.fieldName);
        else{
            if(null != this.columnName){
                this.fieldSetMethod = StringUtil.getSetMethod(this.columnName);
            }
        }
        return fieldSetMethod;
    }

    public void setFieldSetMethod(String fieldSetMethod) {
        this.fieldSetMethod = fieldSetMethod;
    }

    public Boolean getIsGenColumType(){
        String[] s = columnName.split("_");
        return s.length>2||s.length==2?true:false;
    }

    public String getColumnTypeStringName() {
        switch (this.columnType) {
            case java.sql.Types.DECIMAL:
                this.columnTypeStringName = "DECIMAL";
            case java.sql.Types.REAL:
                this.columnTypeStringName = "REAL";
                break;
            case java.sql.Types.INTEGER:
                this.columnTypeStringName = "INTEGER";
                break;
            case java.sql.Types.BIGINT:
                this.columnTypeStringName = "BIGINT";
                break;
            case java.sql.Types.TINYINT:
                this.columnTypeStringName = "TINYINT";
                break;
            case java.sql.Types.VARCHAR:
                this.columnTypeStringName = "VARCHAR";
                break;
            case java.sql.Types.CHAR:
                this.columnTypeStringName = "CHAR";
                break;
            case java.sql.Types.LONGVARBINARY:
                this.columnTypeStringName = "LONGVARBINARY";
                break;
            case java.sql.Types.LONGVARCHAR:
                this.columnTypeStringName = "LONGVARCHAR";
                break;

            case java.sql.Types.DATE:
            case java.sql.Types.TIMESTAMP:
                this.columnTypeStringName = "TIMESTAMP";
                break;
            default:
                break;
        }
        return columnTypeStringName;

    }

}
