package save.the.environment.wastelessclient.services;

import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ReaderService {
    public static String resultToString(InputStream inputStream){
        String result = "";
        try{
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();
            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);
            result = responseStrBuilder.toString();
            return result;
        }catch (Exception e){
            //TODO: Log Exception
            return result;
        }
    }

    public static JSONObject toJSONObject(InputStream inputStream){
        JSONObject jsonObject = null;
        try{
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();
            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);
            jsonObject = new JSONObject(responseStrBuilder.toString());
            return jsonObject;
        }catch (Exception e){
            //TODO: Log Exception
            return jsonObject;
        }
    }
}
