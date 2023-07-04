class Comment {
    constructor(data) {
        this.id = data.id;
        this.author = data.author;
        this.profile_pic_name = data.profile_pic_name;
        this.description = data.description;
        this.date_posted = new Date(data.date_posted);

        this.html = document.createElement("div");
        this.html.classList = "comment";

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
        name.innerHTML = this.author + ` <span>${
            this.date_posted.getMonth() + "/" +
            this.date_posted.getDate() + "/" +
            this.date_posted.getFullYear() + " " +
            this.date_posted.getHours() + ":" +
            this.date_posted.getMinutes()
        }</span>`;
        content.appendChild(name);
        
        let comment = document.createElement("p");
        comment.id = "comment";
        comment.innerHTML = this.description;
        content.appendChild(comment); 
    }
}