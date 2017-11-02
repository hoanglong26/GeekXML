/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Web application lifecycle listener.
 *
 * @author hoanglong
 */
@WebListener()
public class BackgroundListener implements ServletContextListener {

    private ScheduledExecutorService scheduler;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        final ServletContext context = sce.getServletContext();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Deploying...");

                CrawlData crawl = new CrawlData(context);

                String[] ictCategories = new String[]{"game", "internet", "cntt", "cntt", "cntt/phan-mem",
                    "the-gioi-so", "cntt/phan-cung", "the-gioi-so/di-dong", "internet/xa-hoi",
                    "kinh-doanh/ho-so", "the-gioi-so/may-anh-so"};
                crawl.saxParserForICTNewsHomepage("http://ictnews.vn/", ictCategories);

                String[] gamekCategories = new String[]{"pc-console", "mmo", "mobile-social",
                    "esport", "thi-truong", "gaming-gear"};
                crawl.saxParserForGamekHomepage("http://gamek.vn/", gamekCategories);

//                crawl.saxParserForGameRanking("https://www.gamerankings.com/browse.html?page=", 19);
            }
        };

        scheduler.scheduleAtFixedRate(runnable, 0, 120, TimeUnit.MINUTES);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        scheduler.shutdownNow();

    }

}
