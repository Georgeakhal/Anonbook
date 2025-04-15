package org.example.EntetieDaos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.example.Enteties.Comment;
import org.example.Enteties.Post;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommentDao {
    private final Connector con = Connector.getInstance();

    private static CommentDao dao = null;

    public CommentDao() {
    }

    public void createComment(Comment comment){
        con.getEm().getTransaction().begin();
        con.getEm().persist(comment);
        con.getEm().getTransaction().commit();
    }

    public List<Comment> getCommentsByPostId(String postId){
        CriteriaQuery<Comment> cq = con.getCb().createQuery(Comment.class);
        Root<Comment> root = cq.from(Comment.class);

        cq.select(root).where(con.getCb().equal(root.get("postId"), postId));

        TypedQuery<Comment> query = con.getEm().createQuery(cq);

        return query.getResultList();
    }
}
