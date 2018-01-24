package document.dto;

public class MessageDto {

    private String text;
    private Object data;

    public MessageDto(String text, Object data) {
        this.text = text;
        this.data = data;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "MessageDto{" + "text='" + text + '\'' + ", data=" + data + '}';
    }
}
