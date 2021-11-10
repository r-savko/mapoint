package net.mapoint;

import net.mapoint.util.jobs.api.RelaxApiJob;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Runner {

    public static void main(String[] args) {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("/common-spring-context.xml");
        RelaxApiJob relaxApiJob = ctx.getBean(RelaxApiJob.class);
        relaxApiJob.persistData();

    }
}