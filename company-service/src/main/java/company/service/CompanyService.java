package company.service;

import company.domain.Company;

import java.util.List;

public interface CompanyService {

    List<Company> findAll();

    Company save(Company company);

    List<Company> findByNameContainingOrHeadquartersContaining(String name, String headquarters);

    Company findOne(long id);
}
