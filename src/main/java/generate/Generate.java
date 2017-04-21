package generate;

import generate.impl.GenerateDataBase;

/**
 * @author alex-jiayu
 * @create 2017-04-14 17:12
 **/
public class Generate {

    public static final String AUTHOR = "ALEX/JIAYU";
    public static final String CONTACT = "467146659@qq.com/alexdennis.lam@gmail.com";
    public static String PREFIX = "";

    //protected String tableName;

    ICodeGenerate iCodeGenerate = null;
    Setting setting = null;

    public Generate(String classDriver, String url, String userName, String passWord , String tableName){
        this(classDriver , url , userName , passWord , tableName , null);
    }

    public Generate(String classDriver, String url, String userName, String passWord ,String tableName, String schema){
        iCodeGenerate = new GenerateDataBase(classDriver , url , userName , passWord ,tableName ,  schema);
    }

    public boolean generate(String classPackage, String author, String contact, String codePath) {
        return iCodeGenerate.generate(classPackage , author , contact , codePath);
    }
}
