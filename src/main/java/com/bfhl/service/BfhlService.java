package com.bfhl.service;

import com.bfhl.dto.BfhlRequest;
import com.bfhl.dto.BfhlResponse;

/**
 * Service interface for BFHL API processing logic.
 * Defines the contract for processing the input data array.
 */
public interface BfhlService {

    /**
     * Processes the given request and categorizes the data array into:
     * <ul>
     *   <li>Odd numbers</li>
     *   <li>Even numbers</li>
     *   <li>Alphabets (uppercase)</li>
     *   <li>Special characters</li>
     *   <li>Sum of numbers</li>
     *   <li>Concatenated alphabets in reverse order with alternating caps</li>
     * </ul>
     *
     * @param request the incoming BFHL request containing the data array
     * @return a fully populated {@link BfhlResponse}
     */
    BfhlResponse processData(BfhlRequest request);
}
