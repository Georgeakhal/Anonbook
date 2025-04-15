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

public class PostDao {
    private final Connector con = Connector.getInstance();

    public static PostDao dao = null;


    public PostDao(){
    }

    public void createPost(Post post){
        con.getEm().getTransaction().begin();
        con.getEm().persist(post);
        con.getEm().getTransaction().commit();
    }

    public List<Post> getPosts(){
        CriteriaQuery<Post> cq = con.getCb().createQuery(Post.class);
        Root<Post> root = cq.from(Post.class);

        cq.select(root);
        cq.orderBy(con.getCb().asc(root.get("dateTime")));

        TypedQuery<Post> query = con.getEm().createQuery(cq);

        return query.getResultList();
    }

    public Post getPostById(String id){
        return con.getEm().find(Post.class, id);
    }
}
