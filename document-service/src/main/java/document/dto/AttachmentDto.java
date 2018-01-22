package document.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AttachmentDto implements Serializable {
    private static final long serialVersionUID = -7133985896972123292L;

    @JsonProperty("content_type")
    private String contentType;
    private String content;

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "AttachmentDto{" + "contentType='" + contentType + '\'' + ", content='" + content + '\'' + '}';
    }
}
