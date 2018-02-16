package document;

import document.elasticsearch.DocumentIndexer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationStartup implements InitializingBean {

    private final DocumentIndexer documentIndexer;

    @Autowired
    public ApplicationStartup(DocumentIndexer documentIndexer) {
        this.documentIndexer = documentIndexer;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        documentIndexer.createIndex();
    }
}
