package permissions.utilities.file_utilities;

import org.jetbrains.annotations.NotNull;

public class FileConstants {
    public static @NotNull String generateJSONFilePath(String username, String endpoint, String filename) {
        return "src/test/resources/result/" + username + "/" + filename + "/" + endpoint + "_" + filename + ".json";
    }

    public static @NotNull String generateTxtFilePath(String username, String endpoint, String filename) {
        return "src/test/resources/result/" + username + "/" + filename + "/" + endpoint + "_" + filename + ".txt";
    }

    public static @NotNull String generatePNGFilePath(String username, String endpoint, String filename) {
        return "src/test/resources/result/" + username + "/" + filename + "/" + endpoint + "_" + filename + ".png";
    }
}