class Post {
    constructor(data) {
        this.id = data.id;
        this.image_name = data.pictureName;
        this.title = data.title;

        this.comments = data.comments;

        this.html = document.createElement("div");
        this.html.id = "main_image";
        document.querySelector("main").appendChild(this.html);

        this.commentSection = document.createElement("div");
        this.commentSection.id = "comment_section";
        document.querySelector("main").appendChild(this.commentSection);

        this.userComment = new UserComment();
        this.commentSection.appendChild(this.userComment.html);

        this.createHTML();
        this.createComments();
    }

    createHTML() {
        let img = document.createElement("img");
        img.src = "./site/media/posts/" + this.image_name;
        img.alt = "Main Post";
        this.html.appendChild(img);

        let header = document.createElement("h1");
        header.innerHTML = this.title;
        this.html.appendChild(header);

        this.html.appendChild(document.createElement("hr"));
    }

    createComments() {
        let newCommentArray = [];

        for (let comment of this.comments) {
            let newComment = new Comment(comment);
            newCommentArray.push(newComment);
            this.commentSection.appendChild(newComment.html);
        }
        
        this.comments = newCommentArray;
    }
}