package com.damianonicotra.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Map;

@RestController
public class LogController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/bits/{n}")
    public Map<String, Object> getBits(@PathVariable int n) {
        // Calcola posizioni dei bit
        String binary = Integer.toBinaryString(n);
        StringBuilder positions = new StringBuilder();
        for (int i = 0; i < 32; i++) {
            if ((n & (1 << i)) != 0) {
                if (positions.length() > 0) positions.append(",");
                positions.append(i);
            }
        }

        Map<String, Object> response = Map.of(
            "number", n,
            "binary", binary,
            "bitPositions", positions.toString(),
            "timestamp", Instant.now().toString()
        );

        // Costruisci JSON validi per Snowflake
        String requestDataJson = "{\"n\":" + n + "}";
        String responseDataJson = "{\"number\":" + n + ",\"binary\":\"" + binary + "\",\"bitPositions\":\"" + positions.toString() + "\",\"timestamp\":\"" + Instant.now().toString() + "\"}";

        // Salva su Snowflake
        try {
            jdbcTemplate.update(
                "INSERT INTO SPRING_SNOWFLAKE_DB.PUBLIC.API_LOGS (ENDPOINT, REQUEST_DATA, RESPONSE_DATA, STATUS_CODE) " +
                "SELECT ?, PARSE_JSON(?), PARSE_JSON(?), ?",
                "/bits/" + n,
                requestDataJson,
                responseDataJson,
                200
            );
            System.out.println("Log salvato per /bits/" + n);
        } catch (Exception e) {
            System.err.println("Errore salvataggio Snowflake: " + e.getMessage());
        }

        return response;
    }

    @PostMapping("/roman")
    public Map<String, Object> toRoman(@RequestBody Map<String, Integer> request) {
        int number = request.get("number");
        String roman = intToRoman(number);

        Map<String, Object> response = Map.of(
            "number", number,
            "roman", roman,
            "timestamp", Instant.now().toString()
        );

        // Costruisci JSON validi per Snowflake
        String requestDataJson = "{\"number\":" + number + "}";
        String responseDataJson = "{\"number\":" + number + ",\"roman\":\"" + roman + "\",\"timestamp\":\"" + Instant.now().toString() + "\"}";

        // Salva su Snowflake
        try {
            jdbcTemplate.update(
                "INSERT INTO SPRING_SNOWFLAKE_DB.PUBLIC.API_LOGS (ENDPOINT, REQUEST_DATA, RESPONSE_DATA, STATUS_CODE) " +
                "SELECT ?, PARSE_JSON(?), PARSE_JSON(?), ?",
                "/roman",
                requestDataJson,
                responseDataJson,
                200
            );
            System.out.println("Log salvato per /roman con numero " + number);
        } catch (Exception e) {
            System.err.println("Errore salvataggio Snowflake: " + e.getMessage());
        }

        return response;
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "healthy", "timestamp", Instant.now().toString());
    }

    private String intToRoman(int num) {
        int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] symbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        StringBuilder roman = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            while (num >= values[i]) {
                roman.append(symbols[i]);
                num -= values[i];
            }
        }
        return roman.toString();
    }
}
