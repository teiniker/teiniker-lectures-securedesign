package org.se.lab;

import java.io.File;
import javax.servlet.ServletException;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

public class EmbeddedTomcatMain
{
    public static void main(String[] args)
            throws LifecycleException, InterruptedException, ServletException
    {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);
        tomcat.getConnector();

        Context ctx = tomcat.addContext("", new File(".").getAbsolutePath());
        Tomcat.addServlet(ctx, "hello", new SimpleServlet());
        ctx.addServletMappingDecoded("/*", "hello");

        tomcat.start();
        tomcat.getServer().await();
    }

}
