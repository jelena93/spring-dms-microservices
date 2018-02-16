package company.mapper;

import company.domain.Company;
import company.dto.CompanyDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CompanyMapper {
    CompanyDto mapToModel(Company company);

    List<CompanyDto> mapToModelList(List<Company> companyList);

}
