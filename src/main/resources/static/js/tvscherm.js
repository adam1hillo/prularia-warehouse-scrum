"use strict"

import {byId, verwijderChildElementenVan, toon, verberg, setText} from "./util.js";

const TIMING_INTERVAL = 10000; // in millisecond 10s = 10000
async function getVijfOudsteBestellingen() {
    verberg("storingBestelling");
    const response = await fetch("bestellingen/vijfoudstebestellingen");
    if (response.ok) {
        verberg("storingBestelling");
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
        toon("storingBestelling");
    }
}

async function getAantal(){
    const response = await fetch("bestellingen/aantal");

    if (response.ok) {
        verberg("storingAantal");
        const body = await response.text();
        setText("aantal", body);
    } else {
        toon("storingAantal");
    }

}
async function main(){
    window.setInterval(function(){
         getVijfOudsteBestellingen();
         getAantal();
    }, TIMING_INTERVAL);
}

window.onload = main;
