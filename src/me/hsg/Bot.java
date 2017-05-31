package me.hsg;

import javax.swing.*;

import com.samczsun.skype4j.*;
import com.samczsun.skype4j.events.*;
import com.samczsun.skype4j.events.chat.message.MessageReceivedEvent;
import com.samczsun.skype4j.exceptions.*;
import com.samczsun.skype4j.formatting.Message;
import com.samczsun.skype4j.formatting.Text;

@SuppressWarnings("unused")
public class Bot {
	
	static JTextArea logs;
	static JFrame f;
	
	public static void main(String[] args){
		f = new JFrame("Skype Bot - atinywhitedog");
		f.setSize(640, 480);
		logs = new JTextArea();
		f.add(logs);
		f.setVisible(true);
		new Bot();
	}
	
	public static void log(String s){
		logs.setText(logs.getText() + s + "\n");
		f.revalidate();
	}
	
	public Bot(){
		Skype skype = new SkypeBuilder("username", "password").withAllResources().build();
		try {
			skype.login();
			log("Logged in");
		} catch (NotParticipatingException | InvalidCredentialsException | ConnectionException e) {
			e.printStackTrace();
		}
		
		registerEvents(skype);
		log("Registered events");
		
		try {
			skype.subscribe();
			log("Subscribed");
		} catch (ConnectionException e) {
			e.printStackTrace();
		}
	}
	
	public void registerEvents(Skype skype){
		skype.getEventDispatcher().registerListener(new UserChat());
	}
}
