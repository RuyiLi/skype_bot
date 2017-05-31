package me.hsg;

import java.io.File;
import java.io.IOException;

import com.samczsun.skype4j.chat.Chat;
import com.samczsun.skype4j.exceptions.ConnectionException;

public class Utils {
	
	public static void sendMessage(Chat chat, String msg){
		try {
			chat.sendMessage(msg);
		} catch (ConnectionException e) {
			e.printStackTrace();
		}
	}
	
	public static void sendImage(Chat chat, File path){
		try {
			chat.sendImage(path);
		} catch (ConnectionException | IOException e) {
			e.printStackTrace();
		}
	}
	
}
