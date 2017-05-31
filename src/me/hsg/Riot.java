package me.hsg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.rithms.riot.api.*;
import net.rithms.riot.constant.PlayerStatSummaryType;
import net.rithms.riot.constant.QueueType;
import net.rithms.riot.constant.Region;
import net.rithms.riot.constant.Season;
import net.rithms.riot.dto.Champion.Champion;
import net.rithms.riot.dto.Champion.ChampionList;
import net.rithms.riot.dto.League.League;
import net.rithms.riot.dto.League.LeagueEntry;
import net.rithms.riot.dto.Stats.PlayerStatsSummary;
import net.rithms.riot.dto.Stats.PlayerStatsSummaryList;
import net.rithms.riot.dto.Stats.RankedStats;
import net.rithms.riot.dto.Summoner.Summoner;

public class Riot {
	
	public static long getLevel(String name) throws RiotApiException{
		RiotApi api = new RiotApi("");
		Summoner summoner = api.getSummonerByName(Region.NA, name);
		return summoner.getSummonerLevel();
	}
	
	public static String getFree() throws RiotApiException{
		RiotApi api = new RiotApi("5f6f7cdb-3e0b-42a9-bf7e-94619d246b6e");
		List<Champion> freeChamps = api.getFreeToPlayChampions().getChampions();
		String free = "";
		
		for (Champion c : freeChamps){
			free += api.getDataChampion((int) c.getId()).getName() + ", ";
		}
		
		free = free.substring(0, free.length() - 2);
		
        return free;
	}
	
	public static String getRank(String name) throws RiotApiException{
		RiotApi api = new RiotApi("5f6f7cdb-3e0b-42a9-bf7e-94619d246b6e");
		long id = api.getSummonerByName(name).getId();

        List<League> leagues = api.getLeagueEntryBySummoner(id);

        String res = "Ranked info for " + name + ": \n";
        
        for(League league : leagues) {
            if(league.getQueue().equals(QueueType.RANKED_SOLO_5x5.name())) {
                res += "League Name: " + league.getName() + "\n";
                res += "Tier: " + league.getTier() + "\n";
                LeagueEntry entry = league.getEntries().get(0);
                res += "Division: " + entry.getDivision() + "\n";
                res += "LP: " + entry.getLeaguePoints() + "\n";
                res += "Wins: " + entry.getWins() + "\n";
                res += "Losses: " + entry.getLosses();
            }

        }
		
        return res;
	}
	
	public static String getInfo(String name) throws RiotApiException{
		RiotApi api = new RiotApi("5f6f7cdb-3e0b-42a9-bf7e-94619d246b6e");
		long id = api.getSummonerByName(name).getId();

        PlayerStatsSummaryList statsList = api.getPlayerStatsSummary(Region.NA, api.getSeason(), id);
        List<PlayerStatsSummary> summaries = statsList.getPlayerStatSummaries();
        
        String res = "Game info for " + name + ":\n";
        
        for (PlayerStatsSummary s : summaries){
            res += PlayerStatSummaryType.valueOf(s.getPlayerStatSummaryType()) + "\n";
            res += "Wins: " + s.getWins() + "\n";
            res += "Losses: " + s.getLosses() + "\n";
            res += "------------" + "\n";
        }
		
        return res;
	}
}
