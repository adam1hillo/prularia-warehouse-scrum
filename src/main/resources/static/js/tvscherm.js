"use strict";
import {setText, toon} from "./util.js";
//const response = await fetch("bestellingen/aantal");
const response = await fetch("http://localhost:63342/ScrumJava202406/src/main/resources/static/js/aantalbestellingen.json");
console.log(response);
if (response.ok) {
    const body = await response.text();
    setText("aantal", body);
} else {
    toon("storing");
}