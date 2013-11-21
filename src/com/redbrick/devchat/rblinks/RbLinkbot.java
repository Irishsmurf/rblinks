package com.redbrick.devchat.rblinks;

import com.mongodb.*;
import org.jibble.pircbot.*;
import java.net.UnknownHostException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class RbLinkbot extends PircBot{
    private static final String urlRegex = "^(.+ )?(((ht|f)tps?://|www\\S+\\.)([^/\\?&]+)(\\S+))( .+)?$";
    private static final String triggerRegex = "^rblinks: (.+)$";
    private static final String dbURL = "https://api.mongolab.com/api/1/databases/redbricklinks/collections/links?apiKey=8sF5VRmL3C2NGv8rnoFJn_fz6UOaQuVj";
    private static final String version = Colors.RED + "Rb" + Colors.NORMAL + "Linkbot 1.1111111";
    
    private static final Pattern urlPattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
    private static final Pattern triggerPattern = Pattern.compile(triggerRegex, Pattern.CASE_INSENSITIVE);
    
    private Matcher patternMatcher;
    
    public RbLinkbot() {
        this.setName("rblinks");
    }
    
    private static String nullStringFix(String input) {
        return input == null ? "" : input;
    }

    public void onMessage(String chan, String sender, String login, String hostname, String msg) {
    	patternMatcher = triggerPattern.matcher(msg);
    	if (patternMatcher.find()) { // Respond to specific commands via "rblinks: command"
    		String trigger = patternMatcher.group(1);
    		String response = "Command unknown, try again.";
    		if (trigger.equalsIgnoreCase("version"))
    			response = version;
    		else if (trigger.equalsIgnoreCase("where"))
    			response = dbURL;
    			
    		sendMessage(chan, response);
    	}
    	else { // Otherwise check for a URL in a string
            patternMatcher = urlPattern.matcher(msg);
            if (patternMatcher.find() && !sender.equals("TinyURL")) {
                String beforeUrl = nullStringFix(patternMatcher.group(1)).trim();
                String url = patternMatcher.group(2).trim();
                String protocol = patternMatcher.group(3);
                String domain = patternMatcher.group(5);
                String path = nullStringFix(patternMatcher.group(6)).trim();
                String afterUrl = nullStringFix(patternMatcher.group(6)).trim();
                
                System.out.println("Sender: " + sender + ", URL: " + url + ", domain:" + domain + ", path: " + path);
                
                try {
                	addLink(protocol, domain, path, sender);
                }
                catch(UnknownHostException e) {
                	e.printStackTrace();
                }
            }
    	}
    }

    private void addLink(String protocol, String domain, String path, String nick) throws UnknownHostException {
        String time = new java.util.Date().toString();
        String url = "mongodb://XXXXXXXXXX";
        
        MongoClientURI uri = new MongoClientURI(url);
        MongoClient client = new MongoClient(uri);
        
        DB db = client.getDB(uri.getDatabase());
        DBCollection rblinks = db.getCollection("links");
        
        BasicDBObject domainID = new BasicDBObject();
        BasicDBObject linkData = new BasicDBObject();

    	DBObject domainData = rblinks.findOne(domainID);
    	domainID.append("domain", domain);
        domainID.append("parent", null);
    	
        if (domainData == null) { // Domain not seen before
        	System.out.println("New domain: " + domain + ". Adding to DB.");
        	rblinks.insert(domainID, WriteConcern.NORMAL);
        	domainData = rblinks.findOne(domainID);
        }
    	
        Object parentID = domainData.get("_id");
        
        linkData.append("parent", parentID);
    	linkData.append("protocol", protocol);
    	linkData.append("path", path);
    	linkData.append("nick", nick);
    	linkData.append("datetime", time);
    	linkData.append("count", 1);
        
        rblinks.insert(linkData, WriteConcern.NORMAL);
        client.close();
    }
}