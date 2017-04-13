package domain;

/**
 * Created by Alex_ on 2017/4/13.
 */
public class Column {

    private String columnName;
    private String columnType;
    private String columnComment;
    private Boolean isPrimary = false; //主键？
    private Boolean isAutoIncrement = false;  //自动递增？
    private Boolean isNullAble = false;//允许空
    private Boolean isForeignKey = false; //暂时弃用
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

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
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
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getFieldGetMethod() {
        return fieldGetMethod;
    }

    public void setFieldGetMethod(String fieldGetMethod) {
        this.fieldGetMethod = fieldGetMethod;
    }

    public String getFieldSetMethod() {
        return fieldSetMethod;
    }

    public void setFieldSetMethod(String fieldSetMethod) {
        this.fieldSetMethod = fieldSetMethod;
    }
}
