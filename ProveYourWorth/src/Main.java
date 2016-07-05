import org.omg.CORBA.NameValuePair;
import sun.net.www.http.HttpClient;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

import static java.lang.System.out;

public class Main {

    private static final int DEFAULT_BUFFER_SIZE = 256;
    private final String USER_AGENT = "Mozilla/5.0";

    /*

                template of fields to send

                Cache-Control →no-cache, must-revalidate
                Connection →close
                Content-Disposition →inline; filename=bmw_for_life.jpg
                Content-Length →492267
                Content-Type →image/jpeg
                Date →Tue, 05 Jul 2016 21:05:17 GMT
                Expires →Sat, 26 Jul 1997 05:00:00 GMT
                Pragma →no-cache
                Server →Apache/2.2.16 (Amazon)
                X-By-The-Way →Describe yourself in a few words, and why you think you are good at this. Post it back in the 'aboutme' field.
                X-Oh-Look →A new session cookie...
                X-Please-Also-Provide →email=,name=
                X-Post-Back-Fields →image=,code=,resume=
                X-Post-Back-To →http://www.proveyourworth.net/level3/reaper
                X-Powered-By →PHP/5.3.6
     */


    public static void main (String[] args) throws java.lang.Exception
    {
        //Self instance
        Main post = new Main();

        //send post request to proveyourworth
        post.sendPost();

    }

    private String readFile(String pathname) throws IOException {

        File file = new File(pathname);
        StringBuilder fileContents = new StringBuilder((int)file.length());
        Scanner scanner = new Scanner(file);
        String lineSeparator = System.getProperty("line.separator");

        try {
            while(scanner.hasNextLine()) {
                fileContents.append(scanner.nextLine() + lineSeparator);
            }
            return fileContents.toString();
        } finally {
            scanner.close();
        }
    }
    // HTTP POST request
    private void sendPost() throws Exception {

        String path = "C:\\Users\\Carlos\\Documents\\proveyourworthfiles\\bmw_for_life.txt";
        String Valores = readFile(path);

        // url formatting
        URL url = new URL("http://www.proveyourworth.net/level3/reaper");
        Map<String,Object> params = new LinkedHashMap<>();
        params.put("name", "Carlos Abreu");
        params.put("email", "carlosabreu1981@gmail.com");
        params.put("image", "bmw_for_life.jpg");
        params.put("resume", "https://db.tt/008aveEV");
        params.put("GitRepo", "https://db.tt/008aveEV");
        params.put("aboutme", "i'm a passionate developer who loves challenges and enjoy programming for mobiles i have the hope to one day make an impact in the life of everyone, regards.");
        params.put("code", Valores);

        // putting the string together
        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String,Object> param : params.entrySet()) {
            if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }

        //get the bytes to send from the formed string
        byte[] postDataBytes = postData.toString().getBytes("UTF-8");

        //connection object
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();

        //request method
        conn.setRequestMethod("POST");
        conn.setRequestProperty("User-Agent", USER_AGENT);
        //type of content to being send
        conn.setRequestProperty("Content-Type", "image/jpeg");

        //the lenght of the message
        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));

        //ok marked to send the data
        conn.setDoOutput(true);

        //make the magic
        conn.getOutputStream().write(postDataBytes);

        //get the response back
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

        //print the response to the console so you know what happened
        for (int c; (c = in.read()) >= 0;)
            out.print((char)c);

        int responseCode = conn.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + postData);
        System.out.println("Response Code : " + responseCode);

        // BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());


    }

    private void sendPostApache() throws Exception
    {

        String url = "http://www.proveyourworth.net/level3/reaper";
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());

    }


}
