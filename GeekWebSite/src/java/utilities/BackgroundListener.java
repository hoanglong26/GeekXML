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
//                crawl.saxParserForArticle("http://ictnews.vn/rss/game");
//                crawl.saxParserForArticle("http://ictnews.vn/rss/internet");
//                crawl.saxParserForArticle("http://ictnews.vn/rss/cntt");
//                crawl.saxParserForArticle("http://ictnews.vn/rss/cntt/phan-mem");
//                crawl.saxParserForArticle("http://ictnews.vn/rss/the-gioi-so");
//                crawl.saxParserForArticle("http://ictnews.vn/rss/cntt/phan-cung");
//                crawl.saxParserForArticle("http://ictnews.vn/rss/the-gioi-so/di-dong");
//                crawl.saxParserForArticle("http://ictnews.vn/rss/internet/xa-hoi");
//                crawl.saxParserForArticle("http://ictnews.vn/rss/kinh-doanh/ho-so");
//                
//                crawl.saxParserForArticle("http://gamek.vn//pc-console.rss");
//                crawl.saxParserForArticle("http://gamek.vn/game-online.rss");
//                crawl.saxParserForArticle("http://gamek.vn/mobile-social.rss");
//                crawl.saxParserForArticle("http://gamek.vn/esport.rss");
//                crawl.saxParserForArticle("http://gamek.vn/thi-truong.rss");
//                crawl.saxParserForArticle("http://gamek.vn/gaming-gear.rss");
//                crawl.saxParserForArticle("http://gamek.vn/thi-truong.rss");

//                crawl.saxParserForGameRanking("https://www.gamerankings.com/browse.html?page=", 13);
            }
        };

        scheduler.scheduleAtFixedRate(runnable, 0, 24, TimeUnit.HOURS);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        scheduler.shutdownNow();

    }

}
