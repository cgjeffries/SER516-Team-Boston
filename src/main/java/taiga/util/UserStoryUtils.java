package taiga.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
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
            if(attribute.getName().equals("BV")){
                this.businessValueAttribute = attribute;
                break;
            }
        }

        if(this.businessValueAttribute == null){
            System.out.println(("Project doesn't have a business value custom attribute that matches the pattern!"));
        }
    }

    public Double getBusinessValueForUserStory(int userStoryId){
        AtomicReference<UserStoryCustomAttributesValues> values = new AtomicReference<>();
        userStoryCustomAttributesValuesAPI.getUserStoryCustomAttributeValue(userStoryId, result -> {
            values.set(result.getContent());
        }).join();

        String bvString = values.get().getAttributesValues().get(String.valueOf(businessValueAttribute.getId()));

        Double bv = 0.0;

        try{
            bv = Double.valueOf(bvString);
        }
        catch (NumberFormatException e){
            System.out.println("Business value attribute had non-number value " + bvString + " on User Story " + userStoryId + "!");
        }
        catch (Exception e){
            System.out.println("Exception during parsing BV attribute in US " + userStoryId);
        }

        return bv;
    }

    public static void main(String[] args){
        UserStoryUtils bar = new UserStoryUtils(1521722);
        Double value = bar.getBusinessValueForUserStory(5468121);
        int i = 4;
    }

}