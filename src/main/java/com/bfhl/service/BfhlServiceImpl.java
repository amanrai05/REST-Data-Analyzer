package com.bfhl.service;

import com.bfhl.dto.BfhlRequest;
import com.bfhl.dto.BfhlResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link BfhlService} containing the core business logic.
 *
 * <p>This service processes an input array of strings and categorizes each element
 * into numbers (odd/even), alphabets (uppercase), or special characters,
 * while computing the numerical sum and an alternating-caps reverse-order
 * concatenation of all alphabetical characters.</p>
 */
@Service
public class BfhlServiceImpl implements BfhlService {

    // ─── User Constants (configure via application.properties) ──────────────
    @Value("${bfhl.user.id}")
    private String userId;

    @Value("${bfhl.user.email}")
    private String email;

    @Value("${bfhl.user.roll-number}")
    private String rollNumber;

    // ────────────────────────────────────────────────────────────────────────

    /**
     * {@inheritDoc}
     *
     * <p>Processing rules per element in {@code data}:</p>
     * <ul>
     *   <li>If the element is purely numeric (digits only, optionally multi-digit),
     *       it is classified as odd or even based on its integer value.</li>
     *   <li>If the element consists solely of alphabetic characters,
     *       it is uppercased and added to the alphabets list.</li>
     *   <li>Everything else is treated as a special character element.</li>
     * </ul>
     *
     * <p>The {@code concat_string} is built by:</p>
     * <ol>
     *   <li>Extracting every single alphabetical character from all alphabet elements
     *       (in order of appearance).</li>
     *   <li>Reversing that full character list.</li>
     *   <li>Applying alternating-caps starting from uppercase (index 0 → upper,
     *       index 1 → lower, …).</li>
     * </ol>
     */
    @Override
    public BfhlResponse processData(BfhlRequest request) {

        List<String> oddNumbers       = new ArrayList<>();
        List<String> evenNumbers      = new ArrayList<>();
        List<String> alphabets        = new ArrayList<>();
        List<String> specialChars     = new ArrayList<>();
        List<Character> allAlphaChars = new ArrayList<>();  // for concat_string
        long sum = 0;

        for (String item : request.getData()) {

            if (item == null || item.isEmpty()) {
                continue;
            }

            if (isNumeric(item)) {
                // ── Number element ──────────────────────────────────────────
                long num = Long.parseLong(item);
                sum += num;
                if (num % 2 == 0) {
                    evenNumbers.add(item);
                } else {
                    oddNumbers.add(item);
                }

            } else if (isAlphabetic(item)) {
                // ── Alphabet element ─────────────────────────────────────────
                alphabets.add(item.toUpperCase());
                // Collect individual characters for concat_string
                for (char c : item.toCharArray()) {
                    allAlphaChars.add(c);
                }

            } else {
                // ── Special character element ─────────────────────────────────
                specialChars.add(item);
            }
        }

        // ── Build concat_string ───────────────────────────────────────────────
        // Reverse the collected character list
        List<Character> reversed = new ArrayList<>(allAlphaChars);
        java.util.Collections.reverse(reversed);

        StringBuilder concatBuilder = new StringBuilder();
        for (int i = 0; i < reversed.size(); i++) {
            char c = reversed.get(i);
            // alternating caps: even index → uppercase, odd index → lowercase
            if (i % 2 == 0) {
                concatBuilder.append(Character.toUpperCase(c));
            } else {
                concatBuilder.append(Character.toLowerCase(c));
            }
        }

        return BfhlResponse.builder()
                .isSuccess(true)
                .userId(userId)
                .email(email)
                .rollNumber(rollNumber)
                .oddNumbers(oddNumbers)
                .evenNumbers(evenNumbers)
                .alphabets(alphabets)
                .specialCharacters(specialChars)
                .sum(String.valueOf(sum))
                .concatString(concatBuilder.toString())
                .build();
    }

    // ─── Private helpers ─────────────────────────────────────────────────────

    /**
     * Returns {@code true} if the given string consists entirely of digit characters
     * (0–9), allowing multi-digit numbers like "334".
     */
    private boolean isNumeric(String s) {
        for (char c : s.toCharArray()) {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }

    /**
     * Returns {@code true} if the given string consists entirely of alphabetic
     * characters (a–z, A–Z), allowing multi-character words like "ABCD".
     */
    private boolean isAlphabetic(String s) {
        for (char c : s.toCharArray()) {
            if (!Character.isLetter(c)) return false;
        }
        return true;
    }
}
