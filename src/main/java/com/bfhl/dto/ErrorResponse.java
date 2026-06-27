package com.bfhl.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Error Response DTO returned when an exception or validation error occurs.
 */
public class ErrorResponse {

    @JsonProperty("is_success")
    private boolean isSuccess;

    @JsonProperty("error")
    private String error;

    @JsonProperty("message")
    private String message;

    private ErrorResponse() {}

    // ── Getters ────────────────────────────────────────────────────────────
    @JsonIgnore
    public boolean isSuccess() { return isSuccess; }
    public String getError()   { return error; }
    public String getMessage() { return message; }

    // ── Builder ────────────────────────────────────────────────────────────
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private boolean isSuccess;
        private String error;
        private String message;

        public Builder isSuccess(boolean isSuccess) { this.isSuccess = isSuccess; return this; }
        public Builder error(String error)          { this.error = error; return this; }
        public Builder message(String message)      { this.message = message; return this; }

        public ErrorResponse build() {
            ErrorResponse r = new ErrorResponse();
            r.isSuccess = this.isSuccess;
            r.error     = this.error;
            r.message   = this.message;
            return r;
        }
    }
}
