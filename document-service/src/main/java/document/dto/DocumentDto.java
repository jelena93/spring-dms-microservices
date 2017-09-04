/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package document.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jelena
 */
public class DocumentDto {

    private Long id;
    private Long companyId;
    private String fileType;
    private String fileName;
    private List<DescriptorDto> descriptors = new ArrayList<DescriptorDto>();
}
