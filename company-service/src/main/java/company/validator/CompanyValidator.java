package company.validator;

import company.command.CompanyCmd;
import company.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class CompanyValidator {
    private final MessageSource messageSource;
    private final CompanyService companyService;

    @Autowired
    public CompanyValidator(MessageSource messageSource, CompanyService companyService) {
        this.messageSource = messageSource;
        this.companyService = companyService;
    }

    public void validate(CompanyCmd companyCmd) throws Exception {
        if (!companyService.findByPib(companyCmd.getPib()).isEmpty())
            throw new Exception("Company with pib " + companyCmd.getPib() + " already exists");

        if (!companyService.findByIdentificationNumber(companyCmd.getIdentificationNumber()).isEmpty())
            throw new Exception("Company with identification number " + companyCmd.getIdentificationNumber() + " already exists");
    }
}
