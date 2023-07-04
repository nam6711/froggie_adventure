class UserComment {
    constructor() {
        this.author = "Input Name";
        this.profile_pic_name = "default_pfp.jpg";
        this.description = "Type Something";

        this.html = document.createElement("div");
        this.html.classList = "comment user_comment";

        this.createHTML();
    }

    createHTML() {
        let img = document.createElement("img");
        img.src = "./site/media/pfp/" + this.profile_pic_name;
        img.alt = "Profile Picture";
        this.html.appendChild(img);

        let content = document.createElement("div");
        this.html.appendChild(content);

        let name = document.createElement("p");
        name.id = "name";
        name.contentEditable = true;
        name.innerHTML = this.author;
        content.appendChild(name);
        
        let comment = document.createElement("p");
        comment.id = "comment";
        comment.contentEditable = true;
        comment.innerHTML = this.description;
        content.appendChild(comment); 

        let button = document.createElement("button");
        button.innerHTML = "Post";
        button.id = "submit";
        content.appendChild(button);
    }
}