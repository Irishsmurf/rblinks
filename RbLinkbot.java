import com.mongodb.*;
import org.jibble.pircbot.*;
import java.net.UnknownHostException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class RbLinkbot extends PircBot{
    public RbLinkbot() {
        this.setName("rblinks");
    }
    
    public boolean containsReg(String mesg){
        String REGEX = "^((https?|ftp)://|(www|ftp)\\.)?[a-z0-9-]+(\\.[a-z0-9-]+)+([/?].*)?$";
        Pattern p = Pattern.compile(REGEX);
        Matcher msg_m = p.matcher(mesg);
        if(msg_m.find())    return true;
        else    return false;
    }

    public void onMessage(String chan, String sender,
                       String login, String hostname, String msg){
        if (msg.equalsIgnoreCase("rblinks: version")){
            sendMessage(chan, Colors.RED + "Rb" + Colors.NORMAL + "Linkbot 1.1111111");
        }

        if (msg.equalsIgnoreCase("rblinks: where")){
            sendMessage(chan, "https://api.mongolab.com/api/1/databases/redbricklinks/collections/links?apiKey=8sF5VRmL3C2NGv8rnoFJn_fz6UOaQuVj");
        }

        if(containsReg(msg)){
            String[] msgArr = msg.split(" ");
            for(int i=0; i<msgArr.length; i++){
                if((containsReg(msgArr[i])) && (sender != "TinyURL")){
                    try{
                        System.out.println("Sender: "+sender+" - " +msgArr[i]);
                        addLink(msgArr[i].trim(), sender);
                    }catch(UnknownHostException e){
                        e.printStackTrace();
                    }
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
        String url = "mongodb://XXXXXXXXXX";
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
