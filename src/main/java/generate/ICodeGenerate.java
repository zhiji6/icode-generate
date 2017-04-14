package generate;

import domain.Database;

/**
 * @author alex-jiayu
 * @create 2017-04-14 16:48
 **/
public interface ICodeGenerate{

    boolean generate(String classPackage, String author, String contact, String codePath);

}
