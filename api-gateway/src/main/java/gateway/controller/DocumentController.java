package gateway.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/document")
public class DocumentController {

    @GetMapping(path = "/add")
    public String addDocument() {
        return "add_document";
    }

    @GetMapping(path = "/search")
    public String searchDocuments() {
        return "search_documents";
    }
}
