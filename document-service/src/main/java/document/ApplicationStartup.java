package document;

import document.elasticsearch.DocumentIndexer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    DocumentIndexer documentIndexer;

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {

        documentIndexer.createDocumentIndexIfNotExists();

    }
}
