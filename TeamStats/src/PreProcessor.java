import java.io.*;
import java.util.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author jilani
 */

class PreProcessor {

    private Map<Object, Object> getUIContent(){
        JSONArray obj;
        String general[] = {"FkfoulLost", "possessionpercentage", "wongoals"};
        String defence[] = {"accuratePass", "goals", "wonCorners"};
        ArrayList<Object> generalList = new ArrayList<>();
        ArrayList<Object> defenceList = new ArrayList<>();
//        JSONObject finalObject = new JSONObject();
        Map<Object, Object> finalObject = new HashMap<>();
        try {
            // C:/JSONExample.json
            obj = (JSONArray) new JSONParser().parse(new FileReader("/Users/jilani/Desktop/Soccor/TeamStats/src/JSONExample.json"));
            for (String typeName : general) {
//                    JSONObject newObj = new JSONObject();
                Map<Object, Object> newObj = new HashMap<>();
                newObj.put("type", typeName);
                for (Object team: obj)
                    for (Object type : ((JSONArray) ((JSONObject) team).get("stats"))) {
                        if (((JSONObject) type).get("type").equals(typeName)) {
                            newObj.put(((JSONObject) team).get("contestant_id"), ((JSONObject) type).get("value"));
                        }
                    }
                generalList.add(newObj);
            }
            finalObject.put("General", generalList);

            for (String typeName : defence) {
//                    JSONObject newObj = new JSONObject();
                Map<Object, Object> newObj = new HashMap<>();
                newObj.put("type", typeName);
                for (Object team: obj){
                    for(Object type : ((JSONArray)((JSONObject) team).get("stats"))){
                        if(((JSONObject) type).get("type").equals(typeName)){
                            newObj.put(((JSONObject) team).get("contestant_id"), ((JSONObject) type).get("value"));
                        }
                    }
                }
                defenceList.add(newObj);
            }
            finalObject.put("Defence", defenceList);
            return finalObject;
        } catch (IOException | ParseException e) {
            finalObject.put("Error", "Something went wrong. Please go through the error here \n" + e);
            return finalObject;
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        PreProcessor pp = new PreProcessor();
        Map finalResponse = pp.getUIContent();
        // C:/output.txt
        BufferedWriter writer = new BufferedWriter(new FileWriter("/Users/jilani/Desktop/Soccor/TeamStats/src/output.txt"));
        writer.write(finalResponse.toString());
        writer.close();
        System.out.println(finalResponse);
    }
}