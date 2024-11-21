package org.zerock.cleanaido_customer_back.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TempUploadDTO {

    private MultipartFile file;

    private List<String> uploadedFileName;
}
