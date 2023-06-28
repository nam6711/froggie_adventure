let image_exists = false;

window.addEventListener("load", findPost)

async function findPost() {
    let image_id = new URLSearchParams(window.location.search).get("id"); // get image id
    let post = document.querySelector("#main_image img"); // post 

    // TODO: query api and find the max post num, and then use it to establish the image id for default
    // if id is null, then just set to default
    if (!image_id) {
        post.src = "./site/media/placeholder.jpg"
        return;
    }

    // check if the image exists
    // as a png
    if (await checkIfImageExists(`./site/media/posts/${image_id}.png`, post)) 
        return;
    // as a jpg
    if (await checkIfImageExists(`./site/media/posts/${image_id}.jpg`, post))
        return;
    // as a gif
    if (await checkIfImageExists(`./site/media/posts/${image_id}.gif`, post))
        return;

    // if all else fails, then set the image ourselves
    post.src = "./site/media/placeholder.jpg";
    console.log("NO IMAGE FOUND WITH ID " + image_id + "PLACEHOLDER SELECTED");
}

async function checkIfImageExists(src, post) { 
    let response = await fetch(src);
    if (response.status !== 404) {
        post.src = src;
        post.alt = "frog post of the day"; 
        return true;
    } 
    console.log("NO IMAGE FOUND WITH FILE EXTENSION PROPOSED");
    return false;
}