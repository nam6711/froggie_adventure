class CreatePost {
    constructor(data) {
        this.id = data.id;
        this.image_name = data.pictureName;
        this.title = data.title;
        this.new_file;

        this.comments = data.comments;

        this.html = document.createElement("div");
        this.html.id = "main_image";
        document.querySelector("main").appendChild(this.html);
         
        this.createHTML(); 
    }

    async createHTML() { 
        let file_upload = document.createElement("div");
        let url = urlPostImage + "posts/" + this.image_name;
        
        file_upload.innerHTML = `
        <form action="${urlPostImage}posts" method="POST" enctype="multipart/form-data">
            <label for="file"><img src="${url}" alt="post of the day"></label>
            <input id="file" name="file" type="file"/>
        </form>`; 
        this.html.appendChild(file_upload);

        const form = document.querySelector('form');
        form.addEventListener('change', (e) => this.handleSubmit(e, this))

        let header = document.createElement("h1");
        header.innerHTML = this.title;
        header.contentEditable = true;
        this.html.appendChild(header);

        this.html.appendChild(document.createElement("hr"));

        if (editor) {
            let button = document.createElement("button");
            button.innerHTML = "POST changes?"
            button.style.color = "black"
            button.style.marginTop = "1vh"
            button.addEventListener("click", () => this.submitPostUpdate(this))
            this.html.appendChild(button)
        }
    }

    handleSubmit(e, post) {
        console.log(e.currentTarget)

        const form = e.currentTarget; 
		const url = new URL(form.action);
		const formData = new FormData(form);
		const searchParams = new URLSearchParams(formData); 
 
        console.log(searchParams);

		const fetchOptions = {
			method: "POST",
		};

		if (form.method.toLowerCase() === 'post') {
			if (form.enctype === 'multipart/form-data') {
				fetchOptions.body = formData;
			} else {
				fetchOptions.body = searchParams;
			}
		} else {
			url.search = searchParams;
		}

		post.new_file = {
            url: url,
            fetchOptions: fetchOptions
        }
    }

    async submitPostUpdate(post) {
        let json = {
            "id": 0,
            "pictureName": post.image_name,
            "title": document.querySelector("h1").innerHTML,
            "comments": [
            ]
        }

        console.log(json)

        if (post.new_file) { 
		    let response = await fetch(post.new_file.url, post.new_file.fetchOptions);
            if (response.status !== 404) {
                json.pictureName = document.querySelector("input").files[0].name;
                let response2 = await fetch(
                    urlPost + "addPost", {    
                        method: 'POST',
                        headers: {
                           'Content-type': 'application/json'
                        },
                        body: JSON.stringify(json)
                    }
                );

                if (response2.status !== 404)
                    location = "../";
                else
                    alert("POST COULD NOT BE UPDATED");

                return;
            } else {
                alert("IMAGE COULD NOT UPLOAD PROCESS HALTED");
            }

        }
        
        let response = await fetch(
            urlPost + "addPost", {    
                method: 'POST',
                headers: {
                   'Content-type': 'application/json'
                },
                body: JSON.stringify(json)
            }
        );

        if (response.status !== 404)
            location = "../";
        else
            alert("POST COULD NOT BE UPDATED")
    } 
}