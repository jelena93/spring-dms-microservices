package process.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import process.domain.Activity;
import process.service.ActivityService;

@org.springframework.stereotype.Service
public class DocumentMessagingService {
    private final ActivityService activityService;

    @Autowired
    public DocumentMessagingService(ActivityService activityService) {
        this.activityService = activityService;
    }

    @StreamListener(DocumentInputChannel.DOCUMENT_ADDED_INPUT)
    public void handleDocumentAdded(DocumentMessagingDto documentMessagingDto) {
        System.out.println("handleDocumentAdded " + documentMessagingDto);
        Activity activity = activityService.findOne(documentMessagingDto.getActivityId());
        System.out.println(activity);
        if (documentMessagingDto.isInput()) {
            activity.getInputList().add(documentMessagingDto.getId());
        } else {
            activity.getOutputList().add(documentMessagingDto.getId());
        }
        System.out.println("saving list " + activity);
        activityService.save(activity);
    }
}
