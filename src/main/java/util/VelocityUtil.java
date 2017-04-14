package util;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

import java.io.*;
import java.util.Map;

/**
 * @author alex-jiayu
 * @create 2017-04-14 17:37
 **/
public class VelocityUtil {

    public static void generatorCode(String templateFile, Map<String, Object> contextMap, String path, String fileName) {
        VelocityContext context = new VelocityContext();

        VelocityEngine ve = new VelocityEngine();
        String vPath = System.getProperty("user.dir") + "\\src\\main\\resources\\temp";
        ve.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, vPath);
        ve.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
        ve.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");

        ve.init();

        for (Object key : contextMap.keySet()) {
            context.put(key.toString(), contextMap.get(key));
        }

        Template template = null;

        try {
            template = ve.getTemplate(templateFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

        StringWriter sw = new StringWriter();
        if (template != null){
            template.merge(context, sw);
        }
        File pathTemp = new File(path);
        if (!pathTemp.exists()) {
            pathTemp.mkdirs();
        }

        /**
         * file writer to stream
         */
        writeFile(pathTemp + "/" + fileName, sw.toString());
    }

    /**
     * writer file
     * @Title: writeFile
     * @param filePathAndName
     * @param fileContent
     */
    public static void writeFile(String filePathAndName, String fileContent) {
        try {
            File f = new File(filePathAndName);
            if (!f.exists()) {
                f.createNewFile();
            }
            OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(f), "UTF-8");
            BufferedWriter writer = new BufferedWriter(write);
            writer.write(fileContent);
            writer.close();
        } catch (Exception e) {
            System.out.println("写文件内容操作出错");
            e.printStackTrace();
        }
    }

}
