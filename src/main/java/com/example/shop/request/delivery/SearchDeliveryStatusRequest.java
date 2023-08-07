package com.example.shop.request.delivery;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Data
@NoArgsConstructor
public class SearchDeliveryStatusRequest {

    private List<String> selectedStatuses;

    public void SearchDeliveryStatusRequest(List<String> selectedStatuses) {
        this.selectedStatuses = selectedStatuses;
    }
}
