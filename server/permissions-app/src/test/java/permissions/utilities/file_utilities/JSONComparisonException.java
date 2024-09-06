package permissions.utilities.file_utilities;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;
import java.util.Map;

public class JSONComparisonException extends AssertionError {
    private final List<Map<String, JsonNode>> mismatchedRows;

    public JSONComparisonException(String message, List<Map<String, JsonNode>> mismatchedRows) {
        super(message + "\n" + formatMismatchedRows(mismatchedRows));
        this.mismatchedRows = mismatchedRows;
    }

    public List<Map<String, JsonNode>> getMismatchedRows() {
        return mismatchedRows;
    }

    private static String formatMismatchedRows(List<Map<String, JsonNode>> mismatchedRows) {
        StringBuilder sb = new StringBuilder();
        for (Map<String, JsonNode> mismatch : mismatchedRows) {
            sb.append("Expected: ").append(mismatch.get("expected")).append("\n");
            sb.append("Actual: ").append(mismatch.get("actual")).append("\n");
        }
        return sb.toString();
    }
}
