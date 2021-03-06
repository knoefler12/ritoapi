const tbodyElement = document.getElementById("summoner-tbody");

fetch(baseURL+"/summoners")
    .then(response => response.json())
    .then(result => {
        result.map(summoner => createTable(summoner));
    });


function createTable(summoner) {
    const cardElement = document.createElement("tr");
    cardElement.innerHTML = `
        <td>${escapeHTML(summoner.name)}</td>
        <td>${escapeHTML(summoner.accountId)}</td>
        <td>${escapeHTML(summoner.summonerLevel.toString())}</td>
        <td>${escapeHTML(summoner.puuid)}</td>
        <td>${escapeHTML(summoner.profileIconId.toString())}</td>
        <td>${escapeHTML(summoner.revisionDate.toString())}</td>
        
        
    `;

    tbodyElement.appendChild(cardElement);
}

/*
fetch(baseURL + "/summoners/" + summonerId)
    .then(response => response.json())
    .then(summoner => {
        document.getElementById("summoner-account-id").innerText = summoner.accountId;
            document.getElementById("summoner-puuid").innerText = summoner.puuid;
            document.getElementById("summoner-name").innerText = summoner.name;
            document.getElementById("summoner-profile-icon-id").innerText = summoner.profileIconId;
        document.getElementById("summoner-revision-date").innerText = summoner.revisionDate;
        document.getElementById("summoner-level").innerText = summoner.summonerLevel;
    });
*/