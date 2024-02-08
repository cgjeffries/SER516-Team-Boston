package taiga.model.query.sprint;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.Date;
import java.util.List;

/**
 * Utility class for User Stories current functionality: extract BV, .
 */
public class UserStoryAPIUtils {

    /**
     * Extracts the business value (BV) from the user story tags.
     * Each tag is expected to be a list of strings, where the first element is the tag name,
     * and the second element (if present) is its value. This method specifically looks for a tag
     * named "BV" and attempts to parse its associated value as an integer.
     * 
     * @param userStoryDetail The user story detail object containing tags.
     * @return The business value as an Integer if the "BV" tag is found and its value is a valid integer,
     *         or null otherwise.
     */
    public static Integer extractBusinessValue(UserStoryDetail userStoryDetail) {
        if (userStoryDetail.getTags() != null) {
            for (List<String> tag : userStoryDetail.getTags()) {
                // Check if the tag name is "BV" and it has an associated value
                if (!tag.isEmpty() && "BV".equals(tag.get(0)) && tag.size() > 1) {
                    try {
                        // Attempt to parse the associated value as an integer
                        return Integer.parseInt(tag.get(1));
                    } catch (NumberFormatException e) {
                        // If parsing fails, log the error or handle it as needed
                        System.err.println("Error parsing BV value from tag: " + e.getMessage());
                        return null;
                    }
                }
            }
        }
        // Return null if the "BV" tag is not found or tags are null
        return null;
    }
}