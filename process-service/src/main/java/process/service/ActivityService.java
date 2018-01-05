package process.service;

import process.domain.Activity;

public interface ActivityService {

    Activity save(Activity activity);

    Activity findOne(Long id);
}
