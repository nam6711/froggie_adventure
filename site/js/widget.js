// purpose is to load all widgets
let widgets = []; 

window.addEventListener("load", loadWidgets)

async function loadWidgets() {
    let data = await fetch(urlPost);
    let response = await data.json(data);
    if (editor) {
        createEditWidget();
    }
    for (let i = 0; i < response.length; i++) {
        let widget = {
            obj : response[i],
            html : createWidget(response[i], i + 1)
        }

        widgets.push(widget);
    }
}

function createEditWidget() {
    let widget = {};
    
    widget.html = document.createElement("div");
    widget.html.classList = "card";
    document.querySelector("main").appendChild(widget.html);
    
    let img = document.createElement("img");
    img.src = urlImage + "posts/placeholder.jpg";
    img.alt = "CREATE POST";
    widget.html.appendChild(img);
    img.addEventListener("click", () => {
        location = "../create_post.html";
    })

    let container = document.createElement("div");
    container.classList = "container";
    widget.html.appendChild(container);

    let title = document.createElement("p");
    title.innerHTML = "+ CREATE POST +";
    container.appendChild(title);

    return widget.html;
}

function createWidget(widget, num) {
    widget.html = document.createElement("div");
    widget.html.classList = "card";
    document.querySelector("main").appendChild(widget.html);
    
    let img = document.createElement("img");
    img.src = urlImage + "posts/" + widget.pictureName;
    img.alt = "Post " + num;
    widget.html.appendChild(img);
    img.addEventListener("click", () => {
        if (editor) {
            location = "../editor.html?id=" + widget.id;
        } else {
            location = "../index.html?id=" + widget.id;
        }
    })

    let container = document.createElement("div");
    container.classList = "container";
    widget.html.appendChild(container);

    let title = document.createElement("p");
    title.innerHTML = num + " - " + widget.title;
    container.appendChild(title);

    let comments = document.createElement("p");
    comments.innerHTML = `<i class="fa-solid fa-comments"></i> ` + widget.comments.length;
    container.appendChild(comments);

    if (editor) {
        let can = document.createElement("span");
        can.id = "trash";
        can.innerHTML = `<i class="fa-regular fa-trash-can"></i>`;
        can.addEventListener("click", () => deletePost(widget))
        container.appendChild(can);
    }

    return widget.html;
}

async function deletePost(post) {
    const post_id = post.id; 
    const url = urlPost + "deletePost/" + post_id;

    const response = await fetch(url, {
        method: 'DELETE',
        headers: {
        'Content-type': 'application/json'
        }
    });

    if (response.status !== 404)
    post.html.remove()
    else 
        alert("POST COULD NOT BE DELETED");
}