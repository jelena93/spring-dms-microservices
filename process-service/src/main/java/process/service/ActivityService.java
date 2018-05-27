package process.service;

import process.domain.Activity;

import java.util.List;

public interface ActivityService {

    Activity save(Activity activity);

    Activity findOne(Long id);

    List<Activity> findByNameAndProcessOwnerId(String name, long ownerId);

}
