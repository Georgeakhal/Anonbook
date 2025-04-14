package org.example.Servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.example.EntetieDaos.PostDao;
import org.example.Enteties.Post;

import java.io.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

public class PostServlet extends HttpServlet {

    private final PostDao postDao = new PostDao();
    private final ObjectMapper mapper = new ObjectMapper();
    private final List<Post> cachedPosts = new ArrayList<>();

    private static final String INDEX_HTML_PATH = "src/main/webapp/index.html";

    @Override
    public void init() throws ServletException {
        List<Post> posts = postDao.getPosts();
        super.init();
        File file = new File(INDEX_HTML_PATH);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {


            writer.write("<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <title>Post</title>\n" +
                    "    <script type=\"text/javascript\" src=\"src/main/webapp/anonbook.js\"></script>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "</head>\n" +
                    "<body>");
            writer.write("<button type=\"button\" onclick=\"openPopup()\">Add Post</button>\n");
            for (Post post : posts){
                writer.write("<br>");
                writer.write( "<h5>" + post.getHead() +"</h5>");
                writer.write("<img src=data:image/" + post.getImg() + " width=\"600\" height=\"400\" alt=\"Post image\" >");
                writer.write("<form method=\"get\" action=\"http://localhost:8080/comment\" enctype=\"multipart/form-data\">");
                writer.write("<input name = \"postId\" type=\"text\" id=\"myInput\" style=\"display: none;\" value=" + post.getId() + ">");
                writer.write("<button type=\"button\" onclick=\"handleClick(" + post.getHead() + "," + post.getImg() + ")\">View Post</button>");
                writer.write("</form>");
            }
            writer.write("</body>\n" +
                    "</html>");
        } catch (IOException e) {
            System.err.println("Error writing HTML file: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Post post = mapper.readValue(req.getReader(), Post.class);
        post.setId(UUID.randomUUID().toString());
        resp.setStatus(HttpServletResponse.SC_CREATED);
        postDao.createPost(post);
    }


}

