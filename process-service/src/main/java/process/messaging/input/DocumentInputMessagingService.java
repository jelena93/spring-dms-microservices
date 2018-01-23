package process.messaging.input;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;
import process.domain.Activity;
import process.messaging.input.dto.DocumentInputMessagingDto;
import process.service.ActivityService;

@Service
public class DocumentInputMessagingService {
    private final ActivityService activityService;

    @Autowired
    public DocumentInputMessagingService(ActivityService activityService) {
        this.activityService = activityService;
    }

    @StreamListener(DocumentInputChannel.DOCUMENT_ADDED_INPUT)
    public void handleDocumentAdded(DocumentInputMessagingDto documentInputMessagingDto) {
        System.out.println("handleDocumentAdded " + documentInputMessagingDto);
        Activity activity = activityService.findOne(documentInputMessagingDto.getActivityId());
        System.out.println(activity);
        if (documentInputMessagingDto.isInput()) {
            activity.getInputList().add(documentInputMessagingDto.getId());
        } else {
            activity.getOutputList().add(documentInputMessagingDto.getId());
        }
        System.out.println("saving list " + activity);
        activityService.save(activity);
    }
}
