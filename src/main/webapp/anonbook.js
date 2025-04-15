
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

//window.onload = function () {
//      fetch("/get")
//        .then(res => res.json())
//        .then(posts => {
//          const container = document.getElementById("postContainer");
//
//          posts.reverse().forEach(post => {
//            const wrapper = document.createElement("div");
//            wrapper.innerHTML = `
//              <h2>${post.head}</h2>
//              <img src="data:image/jpeg;base64,${post.img}" style="max-width:300px;" />
//              <hr/>
//            `;
//            container.prepend(wrapper); // Add to the top
//          });
//        })
//        .catch(error => console.error('Error:', error));;
//    };


function handleClick(id, head, imgURL) {
    sessionStorage.setItem("postId", id);
    sessionStorage.setItem("postHead", head);
    sessionStorage.setItem("postImg", imgURL);
    window.location.assign("post.html");
}

document.addEventListener("DOMContentLoaded", () => {
    const id = sessionStorage.getItem("postId");
    const head = sessionStorage.getItem("postHead");
    const imgURL = sessionStorage.getItem("postImg");

    document.querySelector('input[name="postId"]').value = id;

    const container = document.querySelector("div");

    const header = document.createElement("h4");
    header.textContent = head;
    container.appendChild(header);

    const img = document.createElement("img");
    img.src = "data:image/" + imgURL;
    container.appendChild(img);

    fetch(`http://localhost:8080/comment?postId=${encodeURIComponent(id)}`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json'
      }
    })
      .then(response => {
        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }
        return response.json();
      })
      .then(data => {

        data.forEach(comment => {
                const p = document.createElement("p");
                p.textContent = comment.text;
                container.appendChild(p);
        });

      })
      .catch(error => {
        console.error("Fetch failed", error);
      });
});

function handleComments() {
  const postId = document.querySelector('input[name="postId"]').value;
  const text = document.querySelector('input[name="text"]').value;

  fetch('http://localhost:8080/comment', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({ postId, text })
  })
    .then(async response => {
      if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
      }
      const text = await response.text();
      return text ? JSON.parse(text) : {};
    })
    .then(data => {
      console.log("Comment added:", data);
      location.reload();
    })
    .catch(error => {
      console.error("Error:", error);
      alert("Failed to post comment.");
    });
}

