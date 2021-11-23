package kea.lhk.rito_api.repository;

import kea.lhk.rito_api.models.Match;
import kea.lhk.rito_api.models.Summoner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchesRepository extends JpaRepository<Match,String> {

    String key = "&api_key=RGAPI-8923c021-46fc-46c4-8539-a6a83b8d23b1";
    String startAndCount = "/ids?start=0&count=4";
    String url = "https://europe.api.riotgames.com/lol/match/v5/matches/by-puuid/";



}