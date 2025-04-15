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
import java.time.LocalDateTime;
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
        int num = 1;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {


            writer.write("<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <title>Anonbook</title>\n" +
                    "    <script src=\"anonbook.js\"></script>" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "</head>\n" +
                    "<body>");
            writer.write("<h1>Anonbook</h1>");
            writer.write("<button type=\"button\" onclick=\"openPopup()\">Add Post</button>\n");
            for (Post post : posts){
                LocalDateTime time = post.getDateTime().toLocalDateTime();
                String stringTime = time.getHour() + ": " + time.getMinute() + " " + time.getDayOfMonth() + "/" + time.getMonthValue() + "/" + time.getYear();

                writer.write("<br>");
                writer.write("<button type=\"button\" onclick=\"handleClick('" + post.getId() + "', '" + post.getHead() + "', '" + post.getImg() + "', '" + stringTime + "')\">View Post</button>");
                writer.write("<p style=\"opacity: 0.5;\">N" + num + " " + stringTime + "</p>");
                writer.write( "<h5>" + post.getHead() +"</h5>");

                if (post.getImg() == null || post.getImg().isBlank()) {
                    continue;
                } else{
                    writer.write("<img src=data:image/" + post.getImg() + " width=\"300\" height=\"200\" alt=\"Post image\" >");
                }

                writer.write("<form method=\"get\" action=\"http://localhost:8080/comment\" enctype=\"multipart/form-data\">");
                writer.write("</form>");

                num ++;
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

        System.out.println(post.getHead());

        if (post.getHead().isBlank() | post.getHead().isEmpty()){
            System.out.println("doPost:: Head input must be filled");
            resp.sendError(HttpServletResponse.SC_UNPROCESSABLE_CONTENT, "Head input must be filled");
            return;
        }

        post.setId(UUID.randomUUID().toString());

        postDao.getPostById(post.getId());

        post.setDateTimeToNow();
        resp.setStatus(HttpServletResponse.SC_CREATED);
        postDao.createPost(post);
    }


}

