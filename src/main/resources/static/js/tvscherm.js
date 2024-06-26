"use strict"

import {byId, verwijderChildElementenVan, toon, verberg, setText} from "./util.js";



async function getVijfOudsteBestellingen() {
    verberg("storing");
    const response = await fetch("bestellingen/vijfoudstebestellingen");
    if (response.ok) {
        const oudsteBestellingenBody = byId("oudsteBestellingenBody");
        verwijderChildElementenVan(oudsteBestellingenBody);
        const oudsteBestellingen = await response.json();
        for (const oudsteBestelling of oudsteBestellingen) {
            const tr = oudsteBestellingenBody.insertRow();
            tr.insertCell().innerText = oudsteBestelling.id;
            tr.insertCell().innerText = oudsteBestelling.aantalArtikelen;
            tr.insertCell().innerText = `${oudsteBestelling.totaleGewicht} kg`;
        }
    } else {
        toon("storing");
    }
}

async function getAantal(){
    const response = await fetch("bestellingen/aantal");

    if (response.ok) {
        const body = await response.text();
        setText("aantal", body);
    } else {
        toon("storing");
    }

}
async function main(){
    window.setInterval(function(){
         getVijfOudsteBestellingen();
         getAantal();
    }, 10000);

}

window.onload = main;
