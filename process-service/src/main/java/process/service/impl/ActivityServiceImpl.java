package process.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import process.domain.Activity;
import process.repository.ActivityRepository;
import process.service.ActivityService;

@Service
public class ActivityServiceImpl implements ActivityService {
    @Autowired
    ActivityRepository activityRepository;

    @Override
    public Activity save(Activity activity) {
        return activityRepository.save(activity);
    }

    @Override
    public Activity findOne(Long id) {
        return activityRepository.findOne(id);
    }
}
