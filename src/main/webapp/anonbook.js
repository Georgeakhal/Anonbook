function addPost() {
  let head = document.querySelector('input.form1[name="head"]').value;
  let imgInput = document.querySelector('input.form1[name="img"]');
  let file = imgInput.files[0];

  if (!file) {
    const jsonData = {
      head: head,
      img: null
    };

    fetch("/post", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(jsonData)
    })
    .then(res => res.json())
    .then(data => {
      console.log("Server response:", data);
      window.close();
      window.opener.location.reload();
    })
    .catch(err => console.error("Error:", err));
  } else {
    const reader = new FileReader();

    reader.onload = function(event) {
      const base64Image = event.target.result;

      const jsonData = {
        head: head,
        img: base64Image
      };

      fetch("/post", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify(jsonData)
      })
      .then(res => res.json())
      .then(data => {
        console.log("Server response:", data);
        window.opener.location.reload();
      })
      .catch(err => console.error("Error:", err));
    };

    reader.readAsDataURL(file);
  }
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

function handleClick(id, head, imgURL, time) {
    sessionStorage.setItem("postId", id);
    sessionStorage.setItem("postHead", head);
    sessionStorage.setItem("postImg", imgURL);
    sessionStorage.setItem("dateTime", time);
    window.location.assign("post.html");
}

document.addEventListener("DOMContentLoaded", () => {
    const id = sessionStorage.getItem("postId");
    const head = sessionStorage.getItem("postHead");
    const imgURL = sessionStorage.getItem("postImg");
    const time = sessionStorage.getItem("dateTime");

    document.querySelector('input[name="postId"]').value = id;

    const container = document.querySelector("div");


    const pa = document.createElement("p");

    pa.textContent = time;
    pa.style.opacity = "0.5";
    container.appendChild(pa);

    const header = document.createElement("h4");
    header.textContent = head;
    container.appendChild(header);

    if (
      imgURL &&
      typeof imgURL === "string" &&
      imgURL.trim().toLowerCase() !== "null" &&
      imgURL.trim() !== ""
    ) {
      const img = document.createElement("img");
      img.width = 400;
      img.height = 400;
      img.src = "data:image/" + imgURL;
      container.appendChild(img);
    } else {
      console.log("Image not provided or invalid.");
    }


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
        for (let i = 0; i < data.length; i++) {
                    const p = document.createElement("p");

                    let num = i + 1;

                    p.textContent = "N" + num.toString() + " " + data[i].text;
                    container.appendChild(p);
                }
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

