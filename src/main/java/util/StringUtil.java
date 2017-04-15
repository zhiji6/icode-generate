package util;

import jdk.internal.util.xml.impl.Input;
import org.apache.log4j.Logger;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * @author alex-jiayu
 * @create 2017-04-14 16:04
 **/
public class StringUtil {

    private static final Logger logger = Logger.getLogger(StringUtil.class);
    private static final String methSet = "set";
    private static final String methGet = "get";

    public static String getFirstLower(String className){
        return className.substring(0,1).toLowerCase()+className.substring(1);
    }

    public static String getFirstUper(String className){
        return className.substring(0,1).toUpperCase()+className.substring(1);
    }

    /**
     * generate Set method
     * @param field
     * @return
     */
    public static String getSetMethod(String field){
        if (null == field || "".equals(field))
            return "";
        else{
            field = methSet + field.substring(0,1).toUpperCase()+field.substring(1);
            return field;
        }
    }

    /**
     * generate get method
     * @param field
     * @return
     */
    public static String getGetMethod(String field){
        if (null == field || "".equals(field))
            return "";
        else{
            field = methGet + field.substring(0,1).toUpperCase()+field.substring(1);
            return field;
        }
    }


    public static String getDomainColumnName(String databaseColumn){

        if (null == databaseColumn || "".equals(databaseColumn))
            return "";

        int _pos = -1;
        while (0 < databaseColumn.indexOf("_")){
            _pos = databaseColumn.indexOf("_");
            if (_pos < databaseColumn.length()-1){
                databaseColumn = databaseColumn.substring(0 , _pos)
                        +databaseColumn.substring(_pos+1 , _pos+2).toUpperCase()
                        +databaseColumn.substring(_pos+2 , databaseColumn.length());
            }else{
                databaseColumn = databaseColumn.replace("_" , "");
            }
        }
        return databaseColumn;
    }

    public static String getColumnType(int databaseType) {
        String colType = "";

        switch (databaseType) {
            case java.sql.Types.DECIMAL:
            case java.sql.Types.REAL:
                colType = "Float";
                break;

            case java.sql.Types.INTEGER:
                colType = "Integer";
                break;
            case java.sql.Types.BIGINT:
                colType = "Long";
                break;
            case java.sql.Types.TINYINT:
                colType = "Integer";
                break;
            case java.sql.Types.VARCHAR:
            case java.sql.Types.CHAR:
            case java.sql.Types.LONGVARBINARY:
            case java.sql.Types.LONGVARCHAR:
                colType = "String";
                break;

            case java.sql.Types.DATE:
            case java.sql.Types.TIMESTAMP:
                colType="Date";
                break;
            default:
                logger.warn("找不到数据类型："+databaseType);
                break;
        }

        return colType;
    }


    /*public synchronized static String getPropertyFromFile(String fileName , String key){

        String analysisPath = System.getProperty("user.dir") + "\\"+fileName;
        InputStream in ;
        ResourceBundle rb ;
        try{
            in = new BufferedInputStream(new FileInputStream(analysisPath));
            rb = new PropertyResourceBundle(in);
            return rb.getString(key).trim();
        }catch (Exception e){
            logger.error("getPropertyFromFile(): error");
        }

        return "";

    }*/



}
