"use strict"

import {byId, verwijderChildElementenVan, toon, verberg} from "./util.js";

await getVijfOudsteBestellingen();



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