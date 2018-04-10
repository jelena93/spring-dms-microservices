package descriptor.validator;

import descriptor.domain.Descriptor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DescriptorValidator {

    public void validate(List<Descriptor> descriptors) throws Exception {
        for (Descriptor d : descriptors) {
            if (d.getValue() == null)
                throw new Exception("Value for descriptor " + d.getDescriptorKey()
                        + " is not correct. Expecting descriptor of type " + d.getDescriptorType().getStringMessageByParamClass());
        }
    }

}
