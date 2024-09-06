package permissions.utilities.file_utilities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static permissions.utilities.file_utilities.WriteReadFile.removeDirectory;

public class CompareJSON {
    private static final Logger LOG = LoggerFactory.getLogger(CompareJSON.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void compareJSONFiles(String expectedFilePath, String actualFilePath, String PRIMARY_KEY, String KEYS_TO_IGNORE) {
        try {
            JsonNode expectedJson = objectMapper.readTree(new File(expectedFilePath));
            JsonNode actualJson = objectMapper.readTree(new File(actualFilePath));

            // Compares the number of ROW_COUNT in the JSON files if they exist
            compareROWS_COUNT(expectedJson, actualJson);

            JsonNode expectedRowNode = expectedJson.path("ROW");
            JsonNode actualRowNode = actualJson.path("ROW");

            List<Map<String, JsonNode>> mismatchedRows = new ArrayList<>();

            Iterator<JsonNode> expectedRowIterator = expectedRowNode.elements();

            String[] keysToIgnore = getKeysToIgnore(KEYS_TO_IGNORE);

            while (expectedRowIterator.hasNext()) {
                JsonNode expectedRow = expectedRowIterator.next();
                String expectedRowText = expectedRow.path(PRIMARY_KEY).asText();

                boolean matchFound = false;
                Iterator<JsonNode> actualRowIterator = actualRowNode.elements();
                while (actualRowIterator.hasNext()) {
                    JsonNode actualRow = actualRowIterator.next();
                    String actualRowText = actualRow.path(PRIMARY_KEY).asText();

                    if (expectedRowText.equals(actualRowText)) {
                        // Remove the TRADE_ID field from the JSON objects before comparison
                        // as it is generated randomly each time the data is loaded
                        // Also remove the DETAILS field as it is generating PROCESS_REF each time with different values
                        if (keysToIgnore != null) {
                            for (String key : keysToIgnore) {
                                ((ObjectNode) expectedRow).remove(key);
                                ((ObjectNode) actualRow).remove(key);
                            }
                        }
                        if (!expectedRow.equals(actualRow)) {
                            Map<String, JsonNode> mismatch = Map.of(
                                    "expected", expectedRow,
                                    "actual", actualRow
                            );
                            mismatchedRows.add(mismatch);
                        }
                        matchFound = true;
                        break;
                    }
                }

                if (!matchFound) {
                    Map<String, JsonNode> mismatch = Map.of(
                            "expected", expectedRow,
                            "actual", objectMapper.createObjectNode()
                    );
                    mismatchedRows.add(mismatch);
                }
            }

            if (!mismatchedRows.isEmpty()) {
                LOG.info("Mismatched rows found:");
                for (Map<String, JsonNode> mismatch : mismatchedRows) {
                    LOG.error("Expected: {}", mismatch.get("expected"));
                    LOG.error("Actual: {}", mismatch.get("actual"));
                }
                throw new JSONComparisonException("Mismatches found in the JSON comparison.", mismatchedRows);
            }
            removeDirectory(actualFilePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void compareROWS_COUNT(JsonNode expectedJson, JsonNode actualJson) {
        if (!expectedJson.path("ROWS_COUNT").isMissingNode() && !actualJson.path("ROWS_COUNT").isMissingNode()) {
            int expectedRowsCount = expectedJson.path("ROWS_COUNT").asInt();
            int actualRowsCount = actualJson.path("ROWS_COUNT").asInt();
            assertEquals(expectedRowsCount, actualRowsCount, "ROWS_COUNT does not match");
        }
    }

    private static String[] getKeysToIgnore(String KEYS_TO_IGNORE) {
        if (KEYS_TO_IGNORE == null || KEYS_TO_IGNORE.isEmpty()) {
            return null;
        }
        if (KEYS_TO_IGNORE.contains(",")) {
            return KEYS_TO_IGNORE.split(",");
        } else {
            return new String[]{KEYS_TO_IGNORE};
        }
    }
}
