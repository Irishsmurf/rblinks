package com.redbrick.devchat.rblinks;

public class RbLinks{
    public static void main(String[] args) throws Exception{
        
        RbLinkbot bot = new RbLinkbot();
        bot.setVerbose(true);
        bot.connect("irc.redbrick.dcu.ie");
        bot.joinChannel("#rblinks");        
    }
}
    
