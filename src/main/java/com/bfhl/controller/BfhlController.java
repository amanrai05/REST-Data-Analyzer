package com.bfhl.controller;

import com.bfhl.dto.BfhlRequest;
import com.bfhl.dto.BfhlResponse;
import com.bfhl.service.BfhlService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller exposing the BFHL API endpoint.
 *
 * <p>Route: POST /bfhl</p>
 * <p>Returns HTTP 200 on success.</p>
 */
@RestController
@RequestMapping("/bfhl")
public class BfhlController {

    private final BfhlService bfhlService;

    public BfhlController(BfhlService bfhlService) {
        this.bfhlService = bfhlService;
    }

    /**
     * Processes the incoming data array and returns categorized results.
     *
     * @param request the request body containing the "data" array
     * @return HTTP 200 with a {@link BfhlResponse} on success
     */
    @PostMapping
    public ResponseEntity<BfhlResponse> processData(@Valid @RequestBody BfhlRequest request) {
        BfhlResponse response = bfhlService.processData(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Standard GET endpoint for BFHL challenge.
     * @return HTTP 200 with operation_code: 1
     */
    @GetMapping
    public ResponseEntity<java.util.Map<String, Integer>> getOperationCode() {
        return ResponseEntity.ok(java.util.Collections.singletonMap("operation_code", 1));
    }
}

