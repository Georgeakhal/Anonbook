package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.annotation.WebServlet;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Wrapper;
import org.apache.catalina.startup.Tomcat;
import org.example.EntetieDaos.CommentDao;
import org.example.EntetieDaos.PostDao;
import org.example.Enteties.Comment;
import org.example.Enteties.Post;
import org.example.Servlets.CommentServlet;
import org.example.Servlets.PostServlet;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws LifecycleException {
        Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir("tomcatSvr");
        tomcat.setPort(8080);

        // Set context path and document base
        String contextPath = "";
        String docBase = new File("./src/main/webapp").getAbsolutePath();

        // Create context
        Context context = tomcat.addContext(contextPath, new File(docBase).getAbsolutePath());

        // Add DefaultServlet to handle static files (including index.html)
        Tomcat.addServlet(context, "default", "org.apache.catalina.servlets.DefaultServlet");
        context.addServletMappingDecoded("/", "default");

        // Add your DataServlet
        PostServlet postServlet = new PostServlet();
        Wrapper postWrapper = Tomcat.addServlet(context, "postServlet", postServlet);
        postWrapper.setLoadOnStartup(1);
        context.addServletMappingDecoded("/post", "postServlet");


        Tomcat.addServlet(context, "commentServlet", new CommentServlet());
        context.addServletMappingDecoded("/comment", "commentServlet");

        tomcat.start();
        tomcat.getConnector();
        tomcat.getServer().await();
    }
}