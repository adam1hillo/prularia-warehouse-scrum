"use strict"
import {byId, setText, toon} from "./util.js";
/*const response1=await fetch("bestellingen/aantal");//we moeten nog aanpassen de fetch met zijn controller methode!
if(response1.ok){
    const response1body = await response.text();
 setText("besteldId", response1body);
} else {
    toon("storing");
}*/
/*const dataBesteldId = 123456;

const data = [
    { id:1,rij: 'A', rek: 10, artikel: 'artikel1', aantal: 5, klaar: true },
    { id:2,rij: 'B', rek: 20, artikel: 'artikel2', aantal: 3, klaar: false },
    { id:3,rij: 'C', rek: 30, artikel: 'artikel3', aantal: 7, klaar: true },
    { id:4,rij: 'D', rek: 40, artikel: 'artikel4', aantal: 1, klaar: true },
    { id:5,rij: 'E', rek: 50, artikel: 'artikel5', aantal: 9, klaar: false }
];*/
const tbody = byId("bestellingenBody");
let resp;

function tableInvullen(data) {
    data.forEach(item => {

        const tr = document.createElement('tr');

        let td = document.createElement('td');
        td.textContent = item.rij;
        tr.appendChild(td);

        td = document.createElement('td');
        td.textContent = item.rek;
        tr.appendChild(td);

        td = document.createElement('td');
        const a = document.createElement('a');
        a.href = "artikelDetail.html"; // dat moet nog bespreken worden!
        a.textContent = item.naam;
        a.onclick = function () {
            sessionStorage.setItem('artikelId', item.artikelId);
            getCheckedCheckboxes();
        }
        td.appendChild(a);
        tr.appendChild(td);

        td = document.createElement('td');
        td.textContent = item.aantal;
        tr.appendChild(td);

        td = document.createElement('td');
        const checkbox = document.createElement('input');
        checkbox.type = 'checkbox';
        td.appendChild(checkbox);
        tr.appendChild(td);

        tbody.appendChild(tr);

        checkbox.onclick = function () {
            getCheckedCheckboxes();
            if (checkbox.checked) {
                tr.classList.add("checked");
                tr.style.background = "#ABD7A8";
            } else {
                tr.classList.remove("checked");
                tr.style.background = "white";
            }

            // console.log(checkedList.length !== data.length)
            const checkedList = document.getElementsByClassName("checked");
            byId("afgewerkt").disabled = JSON.parse(sessionStorage.getItem("checkboxes")).length !== data.length;
        }
    });
}

function getCheckedCheckboxes() {
    const checkboxes = document.querySelectorAll('input[type="checkbox"]');

    let selectedCheckedBoxes = [];
    checkboxes.forEach((checkbox, index) => {
        if (checkbox.checked) {
            // console.log(`Checkbox ${index + 1} is checked`);
            selectedCheckedBoxes.push(index + 1)
        }
    });

    sessionStorage.setItem('checkboxes', JSON.stringify(selectedCheckedBoxes));
}

function setCheckedCheckboxes() {
    const checkboxes = document.querySelectorAll('input[type="checkbox"]');
    let selectedCheckedBoxes = (sessionStorage.getItem("checkboxes"));

    checkboxes.forEach((checkbox, index) => {
        if (selectedCheckedBoxes.includes(index + 1)) {
            checkbox.checked = true;
            checkbox.parentElement.parentElement.style.background = "#ABD7A8";
        }
    });
}

async function updateArtikelVoorraadPerPlaats(magazijnPlaatsPerLijn) {
    const url = `/artikelen/updateVoorraad/plaats`;

    try {
        const response = await fetch(url, {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(magazijnPlaatsPerLijn)
        });

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        const result = await response.text();
        const jsonResult = result ? JSON.parse(result) : {};
        console.log('Voorraad updated successfully:', jsonResult);
    } catch (error) {
        console.error('Error updating voorraad:', error);
    }
}

async function updateBestellingStatusToOnderweg(bestelId) {
    const url = `bestellingen/updateStatusOnderweg/${bestelId}`;

    try {
        const response = await fetch(url, {
            method: 'PATCH'
        });

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        const result = await response.json();
        console.log('Order status updated successfully:', result);
    } catch (error) {
        console.error('Error updating order status:', error);
    }
}

async function updateTotaleVoorraad(artikelId, aantal) {
    const url = `artikelen/updateVoorraad/${artikelId}/aantal`;
    const objectAantal = {aantal: aantal}
    try {
        const response = await fetch(url, {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(objectAantal)
        });

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        const result = await response.json();
        console.log('Voorraad updated successfully:', result);
    } catch (error) {
        console.error('Error updating voorraad:', error);
    }
}

async function afgewerktButton(responsebody) {
    //change Artikel voorraad
    //change magazijnPlaats Aantal
    //change Bestelling status
    const bestelId = responsebody.bestelId;
    const artikelLijn = responsebody.artikelLijn;
    const magazijnPlaatsAantalVeranderen = artikelLijn.map(item => {
        return {
            rij: item.rij,
            rek: item.rek,
            aantal: item.aantal
        };
    });
    const artikelenIdVoorraad = artikelLijn.reduce((acc, item) => {
        const found = acc.find(i => i.artikelId === item.artikelId);
        if (found) {
            found.aantal += item.aantal;
        } else {
            acc.push({artikelId: item.artikelId, totaalAantal: item.aantal});
        }
        return acc;
    }, []);
    await updateBestellingStatusToOnderweg(bestelId);

    // Collect promises for artikel voorraad updates
    const voorraadPromises = artikelenIdVoorraad.map(artikelVoorraad =>
        updateTotaleVoorraad(artikelVoorraad.artikelId, artikelVoorraad.totaalAantal)
    );

    // Add promise for magazijn plaats update
    voorraadPromises.push(updateArtikelVoorraadPerPlaats(magazijnPlaatsAantalVeranderen));

    // Wait for all promises to complete
    await Promise.all(voorraadPromises);

}


/*tableInvullen(data);
setText("bestelId",dataBesteldId);*/
byId("afgewerkt").addEventListener("click", async () => {
    await afgewerktButton(resp).then(() => {
            sessionStorage.removeItem('checkboxes');
            window.location.href = "bevestigingspagina-bestellingen.html";
        }
    );

});
const response = await fetch("artikelen/vanOudsteBestellingen");
if (response.ok) {
    const responseBody = await response.json();
    resp = responseBody;
    setText("bestelId", responseBody.bestelId);
    tableInvullen(responseBody.artikelLijn);
    setCheckedCheckboxes()
    const checkedList = document.getElementsByClassName("checked");
    byId("afgewerkt").disabled = JSON.parse(sessionStorage.getItem("checkboxes")).length !== responseBody.artikelLijn.length;


} else {
    toon("storing");
}



