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
        img.src = urlImage + "pfp/" + this.profile_pic_name;
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

        if (editor) {
            let can = document.createElement("span");
            can.id = "trash";
            can.innerHTML = `<i class="fa-regular fa-trash-can"></i>`;
            can.addEventListener("click", () => this.deleteComment(this))
            this.html.appendChild(can);
        }
    }

    async deleteComment(comment) {
        const post_id = postSelected.id;
        const comment_id = comment.id;
        const url = urlPost + "deleteComment/" + post_id + "/" + comment_id;

        console.log(comment)

        const response = await fetch(url, {
            method: 'DELETE',
            headers: {
            'Content-type': 'application/json'
            }
        });

        if (response.status !== 404)
            comment.html.remove()
        else 
            alert("POST COULD NOT BE DELETED");
    }
}