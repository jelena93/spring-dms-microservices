package document;

import document.elasticsearch.DocumentIndexer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartup implements InitializingBean {

    @Autowired
    DocumentIndexer documentIndexer;

    @Override
    public void afterPropertiesSet() {

        documentIndexer.createDocumentIndexIfNotExists();

    }
}
