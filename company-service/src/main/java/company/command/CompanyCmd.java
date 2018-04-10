package company.command;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CompanyCmd {
    @NotEmpty(message = "{validation.company.name.empty}")
    @NotNull(message = "{validation.company.name.null}")
    private String name;

    @Digits(message = "{validation.company.pib.digits}", fraction = 0, integer = 9)
    @NotNull(message = "{validation.company.pib.null}")
    @Size(min = 9, max = 9, message = "{validation.company.pib.size}")
    private String pib;

    @Digits(message = "{validation.company.identificationNumber.digits}", fraction = 0, integer = 8)
    @Size(min = 8, max = 8, message = "{validation.company.identificationNumber.size}")
    @NotNull(message = "{validation.company.identificationNumber.null}")
    private String identificationNumber;

    @NotEmpty(message = "{validation.company.headquarters.empty}")
    @NotNull(message = "{validation.company.headquarters.null}")
    private String headquarters;

    public String getName() {
        return name;
    }

    public String getPib() {
        return pib;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public String getHeadquarters() {
        return headquarters;
    }

    @Override
    public String toString() {
        return "CompanyCmd{" +
                "name='" + name + '\'' +
                ", pib='" + pib + '\'' +
                ", identificationNumber='" + identificationNumber + '\'' +
                ", headquarters='" + headquarters + '\'' +
                '}';
    }
}
