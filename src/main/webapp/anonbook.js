

function addPost() {
   let head = document.querySelector('input.form1[name="head"]').value;
   let imgInput = document.querySelector('input.form1[name="img"]');
   let file = imgInput.files[0];

   const reader = new FileReader();

     reader.onload = function(event) {
       const base64Image = event.target.result;

       const jsonData = {
         head: head,
         img: base64Image
       };

       // Send JSON
       fetch("/post", {
         method: "POST",
         headers: {
           "Content-Type": "application/json"
         },
         body: JSON.stringify(jsonData)
       })
       .then(res => res.json())
       .then(data => console.log("Server response:", data))
       .catch(err => console.error("Error:", err));
     };

     reader.readAsDataURL(file)

}

function openPopup() {
  const popup = window.open("", "popupWindow", "width=400,height=300,left=100,top=100");

  popup.document.write(`
     <!DOCTYPE html>
     <html lang="en">
     <head>
         <title>Index</title>
         <script type="text/javascript" src="anonbook.js"></script>
         <meta charset="UTF-8">
     </head>
     <body>

     <h1>Create Post</h1>
     <br>

     <form id="form1" method="post" action="http://localhost:8080/post" enctype="multipart/form-data">
         Write some post here:
         <br>
         <input class="form1" type="text" name="head" value=""><br/>
         Img:
         <br>
         <input
                 class="form1"
                 name="img"
                 type="file"
                 id="docpicker"
                 accept="image/*"
         />
         <br>
         <button onclick="addPost()" type="button">Submit</button>

     </form>

     </body>
     </html>
  `);
}

 window.onload = function () {
      fetch("/get")
        .then(res => res.json())
        .then(posts => {
          const container = document.getElementById("postContainer");

          posts.reverse().forEach(post => {
            const wrapper = document.createElement("div");
            wrapper.innerHTML = `
              <h2>${post.head}</h2>
              <img src="data:image/jpeg;base64,${post.img}" style="max-width:300px;" />
              <hr/>
            `;
            container.prepend(wrapper); // Add to the top
          });
        })
        .catch(err => console.error("Failed to fetch posts", err));
    };


 handleClick(id, head, imgURL){
     window.location.assign("post.html");


          fetch(url, {
              method: "GET"
          })
          .then(response => {
              if (!response.ok) {
                  throw new Error(`HTTP error! Status: ${response.status}`);
              }
              return response.json();
          })
          .then(json => {
              const postId = document.querySelector('input.form1[name="postId"]');
              postId.Value = id

              const div = document.getElementsByTagName("div");

              const header =  document.createElement('h4');
              header.textContent = head;

              div.appendChild(header);

              const img = document.createElement('img');

              img.src = "data:image/" + imgURL;

              div.appendChild(img);


              for (let i = 0; i < json.length; i++){
                const p = document.createElement('p');
                p.textContent = json[i].text
                div.appendChild(p);
              }
          })
          .catch(error => {
              console.error('Error:', error);
              alert(error.message);
          });
      }
 }
