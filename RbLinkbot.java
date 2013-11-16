import com.mongodb.*;
import org.jibble.pircbot.*;
import java.net.UnknownHostException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class RbLinkbot extends PircBot{
    public RbLinkbot() {
        this.setName("rblinks");
    }
    
    public final String REGEX = "^((https?|ftp)://|(www|ftp)\\.)?[a-z0-9-]+(\\.[a-z0-9-]+)+([/?].*)?$";

    public void onMessage(String chan, String sender,
                       String login, String hostname, String msg){
        if (msg.equalsIgnoreCase("rblinks: version")){
            sendMessage(chan, Colors.RED + "Rb" + Colors.NORMAL + "Linkbot 1.0");
        }

        if (msg.equalsIgnoreCase("rblinks: where")){
            sendMessage(chan, "https://api.mongolab.com/api/1/databases/redbricklinks/collections/links?apiKey=b-IUxITU2DZ6rn46uboWKdlfn3SpPHmG");
        }

        String[] msgArr = msg.split(" ");
        Pattern p = Pattern.compile(REGEX);
        for(int i=0; i<msgArr.length; i++){
            Matcher msg_m = p.matcher(msgArr[i]);
            if(msg_m.find()){
                try{
                    addLink(msgArr[i].trim(), sender);
                }catch(UnknownHostException e){
                    e.printStackTrace();
                }
            }
        }
    }

   public BasicDBObject[] createLinkObj(String URL, String NICK, 
                            String DATETIME, int COUNT){
        BasicDBObject rblink = new BasicDBObject();
        rblink.put("url", URL);
        rblink.put("nick", NICK);
        rblink.put("datetime", DATETIME);
        rblink.put("count", COUNT);
        final BasicDBObject[] links = {rblink};
        return links;
    }

    public void addLink(String link, String nick) throws UnknownHostException{
        String time = new java.util.Date().toString();
        String url = "mongodb://redbrick:rblinks_X@ds057877.mongolab.com:57877/redbricklinks";
        MongoClientURI uri = new MongoClientURI(url);
        MongoClient client = new MongoClient(uri);
        DB db = client.getDB(uri.getDatabase());
        DBCollection rblinks = db.getCollection("links");

        /*
        //maybe pull database to check if link has been before
        BasicDBObject find = new BasicDBObject("links", new BasicDBObject("$gte",link));
        DBCursor docs = rblinks.find(find);
        */

        final BasicDBObject[] links = createLinkObj(link,nick,time,1);
        rblinks.insert(links);
        client.close();
    }
     
}
