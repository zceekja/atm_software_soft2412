package project1;

import java.util.Scanner;
import java.io.FileInputStream;
import java.io.IOException;
import com.google.api.gax.paging.Page;
import com.google.auth.oauth2.ComputeEngineCredentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.common.collect.Lists;
import java.util.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
public class App {
    public static final String CREDENTIALS_STRING = "jdbc:mysql://google/bank?cloudSqlInstance=prime-heuristic-320203:australia-southeast1:toby-1234&socketFactory=com.google.cloud.sql.mysql.SocketFactory&useSSL=false&user=test&password=password";

    public static void main(String[] args) throws Exception{
        /*
        try {
            //This for my local database
            //Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank?useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "secret");
            Connection connection = DriverManager.getConnection(CREDENTIALS_STRING);
            Statement statement = connection.createStatement();
            //statement.executeUpdate("insert into card value(5,'Batman')");
            ResultSet resultSet = statement.executeQuery("select * from Card");
            while (resultSet.next()) {
                //if resultSet.getString('card_number') == ""
                System.out.println(resultSet.getString("firstname"));
            }
        } catch (Exception e){
            e.printStackTrace();
        }*/
        //authExplicit("cred2.json");
        Atm atm = new Atm();
    }
    /*
    JSON database
    public static void readConfiguration(String file) {

        JSONParser parser = new JSONParser();
        try {
            FileReader f = new FileReader(file);
            Object obj = parser.parse(f);
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray mode = (JSONArray) jsonObject.get("card");
            String name;
            for (Object c : mode)
            {
                jsonObject = (JSONObject) c;
                name = (String)jsonObject.get("name");
                System.out.println(name);
            }
        }
		catch(FileNotFoundException e){
                e.printStackTrace();
            }
		catch(IOException e){
                e.printStackTrace();
            }
		catch(ParseException e){
                e.printStackTrace();
            }

    }
     */
    /*
    static void authExplicit(String jsonPath) throws IOException {
        // You can specify a credential file by providing a path to GoogleCredentials.
        // Otherwise credentials are read from the GOOGLE_APPLICATION_CREDENTIALS environment variable.
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(jsonPath))
              .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
      
        System.out.println("Buckets:");
        Page<Bucket> buckets = storage.list();
        for (Bucket bucket : buckets.iterateAll()) {
          System.out.println(bucket.toString());
        }
      }
      */
}
