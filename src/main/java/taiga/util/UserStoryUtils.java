package taiga.util;

import java.util.ArrayList;
import java.util.List;
import taiga.api.UserStoryCustomAttributesAPI;
import taiga.api.UserStoryCustomAttributesValuesAPI;
import taiga.model.query.customattributes.UserStoryCustomAttribute;
import taiga.model.query.customattributes.UserStoryCustomAttributesValues;
import taiga.model.query.sprint.UserStoryDetail;

/**
 * Utility class for User Stories current functionality: extract BV, .
 */
public class UserStoryUtils {

    private UserStoryCustomAttributesAPI userStoryCustomAttributesAPI;
    private UserStoryCustomAttributesValuesAPI userStoryCustomAttributesValuesAPI;

    private List<UserStoryCustomAttribute> userStoryCustomAttributes;
    private UserStoryCustomAttribute businessValueAttribute;

    private int projectId;

    public UserStoryUtils(int projectId){
        this.userStoryCustomAttributesAPI = new UserStoryCustomAttributesAPI();
        this.userStoryCustomAttributesValuesAPI = new UserStoryCustomAttributesValuesAPI();
        this.projectId = projectId;
        init();
    }

    private void init(){
        userStoryCustomAttributesAPI.getUserStoryCustomAttributeList(this.projectId, results ->{
            userStoryCustomAttributes = List.of(results.getContent());
        }).join();

        for(UserStoryCustomAttribute attribute : userStoryCustomAttributes){
            if(attribute.getDescription().equals("Business Value")){
                this.businessValueAttribute = attribute;
                break;
            }
        }

        if(this.businessValueAttribute == null){
            System.out.println(("Project doesn't have a business value custom attribute that matches the pattern!"));
        }
    }

    public Double getBusinessValueForUserStory(int userStoryId){
        UserStoryCustomAttributesValues values;
        userStoryCustomAttributesValuesAPI.getUserStoryCustomAttributeValue(userStoryId, result -> {

        }).join();

        return 0.0;
    }

    public static void main(String[] args){
        UserStoryUtils bar = new UserStoryUtils(1527011);
    }

}