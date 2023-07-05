// purpose is to load all widgets
let widgets = [];

window.addEventListener("load", loadWidgets)

async function loadWidgets() {
    let data = await fetch("http://localhost:8080/post/");
    let response = await data.json(data);
    for (let i = 0; i < response.length; i++) {
        let widget = {
            obj : response[i],
            html : createWidget(response[i], i + 1)
        }

        widgets.push(widget);
    }
}

function createWidget(widget, num) {
    let html = document.createElement("div");
    html.classList = "card";
    document.querySelector("main").appendChild(html);
    html.addEventListener("click", () => {
        location = "../index.html?id=" + widget.id;
    })

    let img = document.createElement("img");
    img.src = "./media/posts/" + widget.pictureName;
    img.alt = "Post " + num;
    html.appendChild(img);

    let container = document.createElement("div");
    container.classList = "container";
    html.appendChild(container);

    let title = document.createElement("p");
    title.innerHTML = num + " - " + widget.title;
    container.appendChild(title);

    let comments = document.createElement("p");
    comments.innerHTML = `<i class="fa-solid fa-comments"></i> ` + widget.comments.length;
    container.appendChild(comments);

    return html;
}