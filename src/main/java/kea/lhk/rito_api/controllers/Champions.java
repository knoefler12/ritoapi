package kea.lhk.rito_api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import kea.lhk.rito_api.models.Champion;
import kea.lhk.rito_api.models.Match;
import kea.lhk.rito_api.models.Summoner;
import kea.lhk.rito_api.repository.ChampionRepository;
import kea.lhk.rito_api.repository.MatchesRepository;
import kea.lhk.rito_api.repository.SummonerRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class Champions {

    @Autowired
    ChampionRepository champions;

    @GetMapping("/champions/import")
    public String importSummonersIntoDatabase(){



        try {
            String docChamps = Jsoup.connect(champions.url).ignoreContentType(true).get().toString();
            int count= StringUtils.countOccurrencesOf(docChamps,"name\"");

            for(int i = 0; i < count; i++) {

                Champion champion = new Champion();

                String champName = docChamps.substring(docChamps.indexOf("\"name\":")+8,
                        docChamps.indexOf("\"title\":")-2);
                champion.setChampionName(champName);

                Long champId = Long.parseLong(docChamps.substring(docChamps.indexOf("\"key\":")+7,docChamps.indexOf(",\"name\":")-1));
                champion.setChampionId(champId);

                String champTitle = docChamps.substring(docChamps.indexOf("\"title\":")+9,
                        docChamps.indexOf(",\"blurb\":")-1);
                champion.setChampionTitle(champTitle);


                docChamps = docChamps.substring(docChamps.indexOf("\"info\":")+10);
                champions.save(champion);


            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "characters been loaded into database";
    }
    @GetMapping("/champions")
    public List<Champion> findAllMatches(){
        return champions.findAll();
    }
    @GetMapping("/champions/{id}")
    public Champion findMatchById(@PathVariable long id){
        return champions.findById(id).get();
    }
    @DeleteMapping("/champions/{id}")
    public void deleteById(@PathVariable long id){
        champions.deleteById(id);
    }
    @PatchMapping("/champions/{id}")
    public String updateChampionsInfo(@PathVariable Long id, @RequestBody Champion championToPatch){
        return champions.findById(id).map(foundChampion ->{
            if(championToPatch.getChampionId()!=null)foundChampion.setChampionId(championToPatch.getChampionId());
            if(championToPatch.getChampionName()!=null)foundChampion.setChampionName(championToPatch.getChampionName());
            if(championToPatch.getChampionTitle()!=null)foundChampion.setChampionTitle(championToPatch.getChampionTitle());
            champions.save(foundChampion);
            return "champion was patched";
        }).orElse("champion was not patched");
    }
}
