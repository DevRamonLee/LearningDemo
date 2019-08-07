package top.betterramon.okhttpdemo2.bean;

import java.io.Serializable;

/**
 * Created by Ramon Lee on 2019/8/7.
 */
public class FileBean extends BaseDataBean{
    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {

        private String attachAssignId;
        private String attachId;
        private String attachType;
        private String extension;
        private int width;
        private int height;
        private String bucket;
        public String path;
        private String remark;
        private String source;
        private String localPath;

        public String getAttachAssignId() {
            return attachAssignId;
        }

        public void setAttachAssignId(String attachAssignId) {
            this.attachAssignId = attachAssignId;
        }

        public String getAttachId() {
            return attachId;
        }

        public void setAttachId(String attachId) {
            this.attachId = attachId;
        }

        public String getAttachType() {
            return attachType;
        }

        public void setAttachType(String attachType) {
            this.attachType = attachType;
        }

        public String getExtension() {
            return extension;
        }

        public void setExtension(String extension) {
            this.extension = extension;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public String getBucket() {
            return bucket;
        }

        public void setBucket(String bucket) {
            this.bucket = bucket;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getLocalPath() {
            return localPath;
        }

        public void setLocalPath(String localPath) {
            this.localPath = localPath;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "attachAssignId='" + attachAssignId + '\'' +
                    ", attachId='" + attachId + '\'' +
                    ", attachType='" + attachType + '\'' +
                    ", extension='" + extension + '\'' +
                    ", width=" + width +
                    ", height=" + height +
                    ", bucket='" + bucket + '\'' +
                    ", path='" + path + '\'' +
                    ", remark='" + remark + '\'' +
                    ", source='" + source + '\'' +
                    ", localPath='" + localPath + '\'' +
                    '}';
        }
    }
}
