////////////////////
// HTML Elements //
//////////////////
const btnHome = document.querySelector("#button-home");
const btnNext = document.querySelector("#button-next");
const messageTitle = document.querySelector("#message-title");


/////////////////////
// Function Calls //
///////////////////
setMessage("De bestelling is volledig afgewerkt.");


////////////////
// Functions //
//////////////
function setMessage(message){
    messageTitle.innerHTML = message;
}

function handleClickNext(event){
    // Add button logic here

    window.location.href= "overzichtBestelling.html";
    console.log(event);
}

function handleClickHome(event){
    window.location.href = "index.html";
}

//////////////////////
// Event Listeners //
////////////////////
btnNext.addEventListener('click', (event) =>{
    handleClickNext(event);
});

btnHome.addEventListener('click', (event) => {
    handleClickHome(event);
});