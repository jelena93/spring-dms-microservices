/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package company;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class CompanyServiceController {

    @Autowired
    CompanyRepository companyRepository;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Company> getCompanies() {
        return companyRepository.findAll();
    }

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public Company save(@RequestBody Company company) throws Exception {
        company = companyRepository.save(company);
        return company;
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseEntity<List<Company>> search(String query) {
        if (query == null || query.isEmpty()) {
            return new ResponseEntity<>(companyRepository.findAll(), HttpStatus.OK);
        }
        return new ResponseEntity<>(companyRepository.findByNameContainingOrHeadquartersContaining(query, query), HttpStatus.OK);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public Company editCompany(@RequestBody Company company) {
        System.out.println("edit " + company);
        return companyRepository.save(company);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public Company showCompany(@PathVariable("id") long id) throws Exception {
        Company company = companyRepository.findOne(id);
        if (company == null) {
            throw new Exception("There is no company with id " + id);
        }
        return company;
    }
}
