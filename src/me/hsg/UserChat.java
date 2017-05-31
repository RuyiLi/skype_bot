package me.hsg;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.samczsun.skype4j.events.EventHandler;
import com.samczsun.skype4j.events.Listener;
import com.samczsun.skype4j.events.chat.message.MessageReceivedEvent;

import net.rithms.riot.api.*;
import net.rithms.riot.constant.PlayerStatSummaryType;
import net.rithms.riot.constant.QueueType;
import net.rithms.riot.constant.Region;
import net.rithms.riot.dto.League.League;
import net.rithms.riot.dto.League.LeagueEntry;
import net.rithms.riot.dto.Stats.PlayerStatsSummary;
import net.rithms.riot.dto.Stats.PlayerStatsSummaryList;
import net.rithms.riot.dto.Summoner.Summoner;

public class UserChat implements Listener{
	
	boolean annoy = false;
	
	@EventHandler
	public void onChat(MessageReceivedEvent e) throws IOException{
		
		if (annoy){
			Utils.sendMessage(e.getChat(), "______________________________________________________________________________________________________________________________");
			Bot.log("Annoyed someone");
		}
		
		String msg = e.getMessage().getContent().asPlaintext();
		
		if(msg.equals("!help")){
			Utils.sendMessage(e.getChat(), "hi i'm a bot\ntype !commands for a list of commands");
			Bot.log("Gave someone help");
		
		}else if(msg.equals("!commands")){
			Utils.sendMessage(e.getChat(), "!bark - makes me bark\n"
					+ "!annoy - turns off/on annoying mode\n"
					+ "!rock or !paper or !scissors - play rock-paper-scissors\n"
					+ "!levelof {summonername} - gets the level of the summoner\n"
					+ "!getrank {summonername} - gets ranked data for a summoner\n"
					+ "!gameinfo {summonername} - gets summoner's game info\n"
					+ "!free - returns all the free champions\n"
					+ "!joke - tells a random one-liner\n"
					+ "!xkcd {pagenumber}/new - returns an xkcd page\n"
					+ "!lmgtfy {tosearch} - creates an lmgtfy link");
			Bot.log("Listed commands");

		}else if(msg.equals("stupid bot")){
			Utils.sendMessage(e.getChat(), "funny coming from you");
			Bot.log("Made a comeback");
			
		}else if(msg.equals("!bark")){
			Utils.sendMessage(e.getChat(), "bark bark");
			Bot.log("Barked");	
		}else if(msg.equals("!annoy")){
			annoy = !annoy;
			Utils.sendMessage(e.getChat(), "__");
			Bot.log("Someone turned on annoying mode");
			
		}else if(msg.equals("!rock")){
			Utils.sendMessage(e.getChat(), "paper");
			Bot.log("Won with paper");
			
		}else if(msg.equals("!paper")){
			Utils.sendMessage(e.getChat(), "scissors");
			Bot.log("Won with scissors");
			
		}else if(msg.equals("!scissors")){
			Utils.sendMessage(e.getChat(), "rock");
			Bot.log("Won with rock");
			
		}else if(msg.equals("what is an integer?")){
			Utils.sendMessage(e.getChat(), "Whole numbers less than zero are called negative integers. The integer zero is neither positive nor negative, and has no sign. Two integers are opposites if they are each the same distance away from zero, but on opposite sides of the number line. Positive integers can be written with or without a sign.");
			Bot.log("Integers");
			
		}else if(msg.contains("!levelof")){
			if(msg.substring(0, 8).equals("!levelof")){
				String username = msg.replace("!levelof", "");
				username = username.replace(" ", "");
				try {
					Utils.sendMessage(e.getChat(), username + "'s level is " + Riot.getLevel(username));
				} catch (RiotApiException e1) {
					Utils.sendMessage(e.getChat(), "unable to find summoner.");
					e1.printStackTrace();
				}
				Bot.log("Got level of someone");
				
			}
		}else if(msg.contains("!getrank")){
			if(msg.substring(0, 8).equals("!getrank")){
				String name = msg.replace("!getrank ", "");
				try{
					RiotApi api = new RiotApi("5f6f7cdb-3e0b-42a9-bf7e-94619d246b6e");
					long id = api.getSummonerByName(name).getId();
	
			        List<League> leagues = api.getLeagueEntryBySummoner(id);
	
			        String res = "Ranked info for " + name + ": \n";
			        
			        for(League league : leagues) {
			            if(league.getQueue().equals(QueueType.RANKED_SOLO_5x5.name())) {
			                res += "League Name: " + league.getName() + "\n";
			                LeagueEntry entry = league.getEntries().get(0);
			                res += "Tier: " + league.getTier() + " " + entry.getDivision() + "\n";
			                res += "LP: " + entry.getLeaguePoints() + "\n";
			                res += "Wins: " + entry.getWins() + "\n";
			                res += "Losses: " + entry.getLosses();
			            }
			        }
			        Utils.sendMessage(e.getChat(), res);
					Bot.log("Got rank info");
				}catch (RiotApiException e1) {
					Utils.sendMessage(e.getChat(), "unable to get ranked info.");
					e1.printStackTrace();
				}
				
			}
		}else if(msg.equals("!free")){
			try {
				Utils.sendMessage(e.getChat(), Riot.getFree());
				Bot.log(Riot.getFree());
			} catch (RiotApiException e1) {
				Utils.sendMessage(e.getChat(), "unable to get free champions.");
				e1.printStackTrace();
			}
			Bot.log("Free champions");
			
		}else if(msg.equals("!joke")){
			Document doc = Jsoup.connect("http://www.jokesclean.com/OneLiner/Random/").get();
			String text = doc.body().html();
			String res = text.split("</font>")[0];
			String joke = res.split("<font size=")[1];
			joke = joke.substring(5, joke.length());
			
			Utils.sendMessage(e.getChat(), joke);
			Bot.log("Told joke: " + joke);
			
		}else if(msg.equals("!xkcd")){
			String imageLink = ParseJ.parse("http://xkcd.com/info.0.json");
			
			URL url = new URL(imageLink);
			
			BufferedImage img = ImageIO.read(url);
			
			File file = new File("downloaded.png");
			
			ImageIO.write(img, "png", file);
			
			Utils.sendImage(e.getChat(), file);
			
			Bot.log("Sent latest xkcd comic.");
			
		}else if(msg.contains("!xkcd")){
			if(msg.substring(0, 5).equals("!xkcd")){
				String page = msg.replace("!xkcd", "");
				page = page.replace(" ", "");
				if(page.equals("new")){
					String imageLink = ParseJ.parse("http://xkcd.com/info.0.json");
					URL url = new URL(imageLink);
					BufferedImage img = ImageIO.read(url);
					File file = new File("downloaded.png");
					ImageIO.write(img, "png", file);
					Utils.sendImage(e.getChat(), file);
					Bot.log("Sent latest xkcd comic.");
				}else{
					String imageLink = ParseJ.parse("http://xkcd.com/" + page + "/info.0.json");
					URL url = new URL(imageLink);
					BufferedImage img = ImageIO.read(url);
					File file = new File("downloaded.png");
					ImageIO.write(img, "png", file);
					Utils.sendImage(e.getChat(), file);
					Bot.log("Sent issue " + page + " of xkcd");
				}
				
			}
		}else if(msg.contains("!lmgtfy")){
			if(msg.substring(0, 7).equals("!lmgtfy")){
				String search = msg.replace("!lmgtfy ", "");
				search = search.replace(" ", "%20");
				Utils.sendMessage(e.getChat(), "http://lmgtfy.com/?q=" + search + "&l=1");
				Bot.log("LMGTFY");
			}
		}else if(msg.contains("!gameinfo")){
			if(msg.substring(0, 9).equals("!gameinfo")){
				try{
					String name = msg.replace("!gameinfo ", "");
	
					RiotApi api = new RiotApi("5f6f7cdb-3e0b-42a9-bf7e-94619d246b6e");
					long id = api.getSummonerByName(name).getId();
	
			        PlayerStatsSummaryList statsList = api.getPlayerStatsSummary(Region.NA, api.getSeason(), id);
			        List<PlayerStatsSummary> summaries = statsList.getPlayerStatSummaries();
			        
			        String res = "Game info for " + name + ":\n";
			        
			        for (PlayerStatsSummary s : summaries){ 
			            res += s.getPlayerStatSummaryType() + "\n";
			            res += "Wins: " + s.getWins() + "\n";
			            res += "Losses: " + s.getLosses() + "\n";
			            res += "------------" + "\n";
			        }
					Utils.sendMessage(e.getChat(), res);
				}catch(RiotApiException e1){
					Utils.sendMessage(e.getChat(), "unable to get game info.");
				}
				
				Bot.log("Game Info");
			}
		}
	}
	
	public static int randInt(int min, int max) {
	    Random rand = new Random();
	    int randomNum = rand.nextInt((max - min) + 1) + min;
	    return randomNum;
	}
}
