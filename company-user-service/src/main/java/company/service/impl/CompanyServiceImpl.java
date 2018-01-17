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
        return companyRepository.save(company);
    }

    @Override
    public Company findOne(long id) {
        return companyRepository.findOne(id);
    }

    public List<Company> search(String searchTerm) {
        return companyRepository.findAll(CompanySpecs.searchCompanies(searchTerm));
    }
}
