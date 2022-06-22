package hello.upload.domain;

import lombok.Data;

@Data
public class UploadFile {

    // 고객이 같은 이름으로 저장할 수 도 있으니까 서버에 저장하는 파일명은 겹치지 않도록 해야함.
    private String uploadFileName;
    private String storeFileName;

    public UploadFile(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }
}
