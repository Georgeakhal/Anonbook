package org.example.Servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.EntetieDaos.CommentDao;
import org.example.EntetieDaos.PostDao;
import org.example.Enteties.Comment;
import org.example.Enteties.Post;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CommentServlet extends HttpServlet {
    private final CommentDao commentDao = new CommentDao();
    private final ObjectMapper mapper = new ObjectMapper();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String postId = req.getParameter("postId");
        mapper.writeValue(resp.getWriter(), commentDao.getCommentsByPostId(postId));
        resp.setContentType("application/json");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Comment comment = mapper.readValue(req.getReader(), Comment.class);
        resp.setStatus(HttpServletResponse.SC_CREATED);
        commentDao.createComment(comment);
    }
}
