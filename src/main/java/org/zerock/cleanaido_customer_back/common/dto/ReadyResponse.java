package org.zerock.cleanaido_customer_back.common.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReadyResponse {
    private String tid;                  // 결제 고유번호
    private String next_redirect_mobile_url;
}
