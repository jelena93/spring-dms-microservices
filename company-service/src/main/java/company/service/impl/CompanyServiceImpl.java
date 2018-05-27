package company.service.impl;

import company.domain.Company;
import company.repository.CompanyRepository;
import company.repository.CompanySpecs;
import company.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    @Override
    public Company save(Company company) {
        System.out.println("save " + company.getId());
        return companyRepository.save(company);
    }

    @Override
    public Company findOne(long id) {
        return companyRepository.findOne(id);
    }

    @Override
    public List<Company> findByPib(String pib) {
        return companyRepository.findByPib(pib);
    }

    @Override
    public List<Company> findByIdentificationNumber(String identificationNumber) {
        return companyRepository.findByIdentificationNumber(identificationNumber);
    }

    public List<Company> search(String searchTerm) {
        return companyRepository.findAll(CompanySpecs.searchCompanies(searchTerm));
    }
}
