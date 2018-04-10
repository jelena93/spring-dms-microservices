package company.mapper;

import company.command.CompanyCmd;
import company.domain.Company;
import company.dto.CompanyDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface CompanyMapper {
    CompanyDto mapToModel(Company company);

    Company mapToEntity(CompanyCmd companyCmd);

    void updateEntityFromModel(CompanyCmd companyCmd, @MappingTarget Company company);

    List<CompanyDto> mapToModelList(List<Company> companyList);

}
