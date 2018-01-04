package descriptor.mapper;

import descriptor.domain.Descriptor;
import descriptor.dto.DescriptorDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class DescriptorMapperDecorator implements DescriptorMapper {
    @Autowired
    DescriptorMapper delegate;

    @Override
    public Descriptor mapToEntity(DescriptorDto descriptorDto) {
        Descriptor descriptor = delegate.mapToEntity(descriptorDto);
        descriptor.setValue(descriptor);
        return descriptor;
    }

    @Override
    public DescriptorDto mapToModel(Descriptor descriptor) {
        DescriptorDto descriptorDto = delegate.mapToModel(descriptor);
        Object value = descriptor.getValue();
        if (value != null) {
            if (Date.class.equals(descriptor.getDescriptorType().getParamClass())) {
                descriptorDto.setDescriptorValue(new SimpleDateFormat("dd.MM.yyyy").format((Date) value));
            } else {
                descriptorDto.setDescriptorValue(String.valueOf(value));
            }
        }
        return descriptorDto;
    }

    @Override
    public List<DescriptorDto> mapToModelList(List<Descriptor> descriptorList) {
        List<DescriptorDto> descriptorDtos = new ArrayList<>();
        for (Descriptor descriptor : descriptorList) {
            Object value = descriptor.getValue();
            DescriptorDto descriptorDto = delegate.mapToModel(descriptor);
            if (value != null) {
                if (Date.class.equals(descriptor.getDescriptorType().getParamClass())) {
                    descriptorDto.setDescriptorValue(new SimpleDateFormat("dd.MM.yyyy").format((Date) value));
                } else {
                    descriptorDto.setDescriptorValue(String.valueOf(value));
                }
            }
            descriptorDtos.add(descriptorDto);
        }
        return descriptorDtos;
    }
}
