let postSelected;
let image_id; 

window.addEventListener("load", findPost)

async function findPost() {
    image_id = new URLSearchParams(window.location.search).get("id"); // get image id
    
    document.querySelector("#left").addEventListener("click", getPrev);
    document.querySelector("#right").addEventListener("click", getNext);

    // id can be 0, so just make sure its not null
    if (image_id == null) {
        let data = await fetch(urlPost + "latest");
        let json = await data.json(); 
        postSelected = new Post(json);
        return;
    }

    // check to see if post exists
    let data = await fetch(urlPost + image_id);
    // if the post doesn't exist, send to the latest post
    
    if (data.status === 404) {
        location = "./"
        return;
    }

    let json = await data.json();
    postSelected = new Post(json);
}

async function getPrev() {
    let response = await fetch(urlPost + "prev/" + postSelected.id);
    let prev_id = await response.json();
    console.log(prev_id)

    if (prev_id === image_id)
        return;

    location = "./index.html?id=" + prev_id;
}

async function getNext() {
    let response = await fetch(urlPost + "next/" + postSelected.id);
    let next_id = await response.json();
    console.log(next_id)

    if (next_id === image_id)
        return;

    location = "./index.html?id=" + next_id;
}