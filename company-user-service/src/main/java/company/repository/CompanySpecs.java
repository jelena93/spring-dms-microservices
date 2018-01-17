package company.repository;

import company.domain.Company;
import org.springframework.data.jpa.domain.Specification;

public class CompanySpecs {

    public static Specification<Company> searchCompanies(String searchTerm) {
        return (root, query, builder) -> builder.or(builder.like(root.<String>get("name"), '%' + searchTerm + '%'),
                                                    builder.like(root.<String>get("pib"), '%' + searchTerm + '%'),
                                                    builder.like(root.<String>get("identificationNumber"),
                                                                 '%' + searchTerm + '%'),
                                                    builder.like(root.<String>get("headquarters"),
                                                                 '%' + searchTerm + '%'));
    }
}
