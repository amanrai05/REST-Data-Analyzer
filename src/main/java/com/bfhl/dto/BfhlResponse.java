package com.bfhl.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Response Data Transfer Object for the /bfhl endpoint.
 * Contains processed results categorized from the input data array.
 */
public class BfhlResponse {

    @JsonProperty("is_success")
    private boolean isSuccess;

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("email")
    private String email;

    @JsonProperty("roll_number")
    private String rollNumber;

    @JsonProperty("odd_numbers")
    private List<String> oddNumbers;

    @JsonProperty("even_numbers")
    private List<String> evenNumbers;

    @JsonProperty("alphabets")
    private List<String> alphabets;

    @JsonProperty("special_characters")
    private List<String> specialCharacters;

    @JsonProperty("sum")
    private String sum;

    @JsonProperty("concat_string")
    private String concatString;

    // ── Private constructor — use builder ──────────────────────────────────
    private BfhlResponse() {}

    // ── Getters ────────────────────────────────────────────────────────────
    @JsonIgnore
    public boolean isSuccess()                   { return isSuccess; }
    public String getUserId()                    { return userId; }
    public String getEmail()                     { return email; }
    public String getRollNumber()                { return rollNumber; }
    public List<String> getOddNumbers()          { return oddNumbers; }
    public List<String> getEvenNumbers()         { return evenNumbers; }
    public List<String> getAlphabets()           { return alphabets; }
    public List<String> getSpecialCharacters()   { return specialCharacters; }
    public String getSum()                       { return sum; }
    public String getConcatString()              { return concatString; }

    // ── Builder ────────────────────────────────────────────────────────────
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private boolean isSuccess;
        private String userId;
        private String email;
        private String rollNumber;
        private List<String> oddNumbers;
        private List<String> evenNumbers;
        private List<String> alphabets;
        private List<String> specialCharacters;
        private String sum;
        private String concatString;

        public Builder isSuccess(boolean isSuccess)                     { this.isSuccess = isSuccess; return this; }
        public Builder userId(String userId)                            { this.userId = userId; return this; }
        public Builder email(String email)                              { this.email = email; return this; }
        public Builder rollNumber(String rollNumber)                    { this.rollNumber = rollNumber; return this; }
        public Builder oddNumbers(List<String> oddNumbers)              { this.oddNumbers = oddNumbers; return this; }
        public Builder evenNumbers(List<String> evenNumbers)            { this.evenNumbers = evenNumbers; return this; }
        public Builder alphabets(List<String> alphabets)               { this.alphabets = alphabets; return this; }
        public Builder specialCharacters(List<String> specialCharacters){ this.specialCharacters = specialCharacters; return this; }
        public Builder sum(String sum)                                  { this.sum = sum; return this; }
        public Builder concatString(String concatString)               { this.concatString = concatString; return this; }

        public BfhlResponse build() {
            BfhlResponse r = new BfhlResponse();
            r.isSuccess         = this.isSuccess;
            r.userId            = this.userId;
            r.email             = this.email;
            r.rollNumber        = this.rollNumber;
            r.oddNumbers        = this.oddNumbers;
            r.evenNumbers       = this.evenNumbers;
            r.alphabets         = this.alphabets;
            r.specialCharacters = this.specialCharacters;
            r.sum               = this.sum;
            r.concatString      = this.concatString;
            return r;
        }
    }
}
