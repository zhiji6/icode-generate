package domain;

/**
 * @author alex-jiayu
 * @create 2017-04-15 13:12
 **/
public class Config {

    protected boolean isBatchDeletes = false;
    protected boolean isBatchInsert = false;

    protected boolean isFileUpload = false;

    protected boolean generateHtml = false;


    public void setBatchDeletes(boolean batchDeletes) {
        isBatchDeletes = batchDeletes;
    }

    public void setBatchInsert(boolean batchInsert) {
        isBatchInsert = batchInsert;
    }

    public void setFileUpload(boolean fileUpload) {
        isFileUpload = fileUpload;
    }

    public void setGenerateHtml(boolean generateHtml) {
        this.generateHtml = generateHtml;
    }
}
