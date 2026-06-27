package com.bfhl.dto;

import jakarta.validation.constraints.NotNull;
import java.util.List;

/**
 * Request Data Transfer Object for the /bfhl endpoint.
 * Contains the input array of mixed strings to be processed.
 */
public class BfhlRequest {

    @NotNull(message = "The 'data' field must not be null")
    private List<String> data;

    public BfhlRequest() {}

    public BfhlRequest(List<String> data) {
        this.data = data;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
