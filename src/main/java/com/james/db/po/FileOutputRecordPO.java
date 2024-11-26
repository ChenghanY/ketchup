package com.james.db.po;

import com.james.db.base.BasePO;

public class FileOutputRecordPO extends BasePO {

    private String fileName;

    private String fileRef;

    private String outputType;

    private String outputState;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileRef() {
        return fileRef;
    }

    public void setFileRef(String fileRef) {
        this.fileRef = fileRef;
    }

    public String getOutputType() {
        return outputType;
    }

    public void setOutputType(String outputType) {
        this.outputType = outputType;
    }

    public String getOutputState() {
        return outputState;
    }

    public void setOutputState(String outputState) {
        this.outputState = outputState;
    }
}
