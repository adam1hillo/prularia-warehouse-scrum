"use strict"
import {byId,toon,verberg,setText} from "./util.js";
/*const response1=await fetch("bestellingen/aantal");//we moeten nog aanpassen de fetch met zijn controller methode!
if(response1.ok){
    const response1body = await response.text();
 setText("besteldId", response1body);
} else {
    toon("storing");
}*/
const data = [
    { rij: 'A', rek: 10, artikel: 'artikel1', aantal: 5, klaar: true },
    { rij: 'B', rek: 20, artikel: 'artikel2', aantal: 3, klaar: false },
    { rij: 'C', rek: 30, artikel: 'artikel3', aantal: 7, klaar: true },
    { rij: 'D', rek: 40, artikel: 'artikel4', aantal: 1, klaar: true },
    { rij: 'E', rek: 50, artikel: 'artikel5', aantal: 9, klaar: false }
];
const tbody= byId("bestellingenBody");
function tableInvullen(data){
    data.forEach(item =>{

        const tr = document.createElement('tr');


        let td = document.createElement('td');
        td.textContent = item.rij;
        tr.appendChild(td);

        td = document.createElement('td');
        td.textContent = item.rek;
        tr.appendChild(td);

        td = document.createElement('td');
        const a = document.createElement('a');
        a.href = "overzichtArtikel.html"; // dat moet nog bespreken worden!
        a.textContent = item.artikel;
        td.appendChild(a);
        tr.appendChild(td);



        td = document.createElement('td');
        td.textContent = item.aantal;
        tr.appendChild(td);


        td = document.createElement('td');
        const checkbox = document.createElement('input');
        checkbox.type = 'checkbox';
        checkbox.checked = item.klaar;
        td.appendChild(checkbox);
        tr.appendChild(td);


        tbody.appendChild(tr);

    });
}
tableInvullen(data);
