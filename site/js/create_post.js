let postSelected; 

window.addEventListener("load", findPost)

async function findPost() {
    let json = {
        id: 0,
        comments : [],
        pictureName : "placeholder.jpg",
        title : "Placeholder Title"
    };
        
    postSelected = new CreatePost(json);
}