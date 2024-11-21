// DTO 클래스 생성
package org.zerock.cleanaido_customer_back.ai.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SolutionDTO {
    private String extractedCategory;
    private String solution;
}
