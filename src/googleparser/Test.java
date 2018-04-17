/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googleparser;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;

//import com.google.api.client.auth.oauth2.Credential;
//import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
//import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
//import com.google.api.client.http.HttpTransport;
//import com.google.api.client.json.JsonFactory;
//import com.google.api.client.json.jackson2.JacksonFactory;
//import com.google.api.services.sheets.v4.Sheets;
//import com.google.api.services.sheets.v4.SheetsScopes;
//import com.google.api.services.sheets.v4.model.ValueRange;
/**
 *
 * @author Damang
 */
public class Test
{

//    private static JsonFactory getJsonFactory()
//    {
//            return JacksonFactory.getDefaultInstance();  
//    }
//    
//    private static HttpTransport getHttpTransport() 
//       throws GeneralSecurityException, IOException
//    {
//            return GoogleNetHttpTransport.newTrustedTransport();
//    }
//    public static Credential getCredentials() 
//        throws GeneralSecurityException, IOException
//    {
//        URL path = Test.class.getResource("xxx.p12");
//        File p12 = new File(path.getFile());
//        System.out.println(p12.getAbsoluteFile());
//        List<String> SCOPES_ARRAY = 
//                  Arrays.asList(SheetsScopes.SPREADSHEETS_READONLY);
//        
//        Credential credential = new GoogleCredential.Builder()
//                    .setTransport(getHttpTransport())
//                    .setJsonFactory(getJsonFactory())
//                    .setServiceAccountId("your serviceaccount.com")
//                    .setServiceAccountScopes(SCOPES_ARRAY)
//                    .setServiceAccountPrivateKeyFromP12File(p12)                    
//                    .build();
//       
//        return credential;                         
//    }    
//
//    public static List<List<Object>> getValues(String sheetName) 
//        throws GeneralSecurityException, IOException
//    {
//        Credential credential = getCredentials();
//        Sheets sheets = new Sheets.Builder(getHttpTransport(), 
//                                           getJsonFactory(), 
//                                           credential)
//                                  .setApplicationName("your application")
//                                  .build();
//        String range = "range_name";
//
//        ValueRange response = sheets.spreadsheets()
//                                    .values()
//                                    .get(sheetName, range)
//                                    .execute();
//
//        System.out.println(response.values().toString());
//        return response.getValues();
//    }
//    
//    public static void main(String[] args) 
//         throws GeneralSecurityException, IOException
//    {
//        for (List<Object> row : getValues("sheet name")) {
//            System.out.printf("%s, %s\n", row.get(0), row.get(1));
//          }   
//    }
}
