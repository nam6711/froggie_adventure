let postSelected;
let editor = true;
let image_changed = false;

window.addEventListener("load", findPost)

async function findPost() {
    let image_id = new URLSearchParams(window.location.search).get("id"); // get image id
     
    // id can be 0, so just make sure its not null
    if (image_id == null) {
        let data = await fetch("http://localhost:8080/post/latest");
        let json = await data.json(); 
        postSelected = new Post(json);
        return;
    }

    // check to see if post exists
    let data = await fetch("http://localhost:8080/post/" + image_id);
    // if the post doesn't exist, send to the latest post
    
    if (data.status === 404) {
        location = "./"
        return;
    }

    let json = await data.json();
    postSelected = new Post(json);
}