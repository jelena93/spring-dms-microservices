/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gateway;

import gateway.dto.ActivityDto;
import gateway.dto.DocumentType;
import gateway.model.Activity;
import java.util.List;

public class Serializator {

    public static ActivityDto toDto(Activity activity, List<DocumentType> inputListDocumentTypes, List<DocumentType> outputListDocumentTypes) {
        ActivityDto dto = new ActivityDto();
        dto.setId(activity.getId());
        dto.setName(activity.getName());
        dto.setInputListDocumentTypes(inputListDocumentTypes);
        dto.setOutputListDocumentTypes(outputListDocumentTypes);
        return dto;
    }

}
