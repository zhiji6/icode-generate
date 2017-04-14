package generate;

import generate.impl.GenerateDataBase;

/**
 * @author alex-jiayu
 * @create 2017-04-14 17:12
 **/
public class Generate implements ICodeGenerate{

    public static final String AUTHOR = "ALEX/JIAYU";
    public static final String CONTACT = "467146659@qq.com/alexdennis.lam@gmail.com";
    public static String PREFIX = "";

    ICodeGenerate iCodeGenerate = null;

    public Generate(String classDriver, String url, String userName, String passWord){
        this(classDriver , url , userName , passWord , null);
    }

    public Generate(String classDriver, String url, String userName, String passWord , String schema){
        iCodeGenerate = new GenerateDataBase(classDriver , url , userName , passWord , schema);
    }

    public boolean generate(String classPackage, String author, String contact, String codePath) {
        return iCodeGenerate.generate(classPackage , author , contact , codePath);
    }
}
