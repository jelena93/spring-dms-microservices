package process.messaging.input.dto;

public class DocumentInputMessagingDto {
    private long id;
    private boolean input;
    private long activityId;

    public DocumentInputMessagingDto() {
    }

    public DocumentInputMessagingDto(long id, boolean input, long activityId) {
        this.id = id;
        this.input = input;
        this.activityId = activityId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isInput() {
        return input;
    }

    public void setInput(boolean input) {
        this.input = input;
    }

    public long getActivityId() {
        return activityId;
    }

    public void setActivityId(long activityId) {
        this.activityId = activityId;
    }

    @Override
    public String toString() {
        return "DocumentInputMessagingDto{" + "id=" + id + ", input=" + input + ", activityId=" + activityId + '}';
    }
}
