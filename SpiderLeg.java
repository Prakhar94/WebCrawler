package WebCrawl;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SpiderLeg
{
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
    private List<String> links = new LinkedList<String>(); // Just a list of URLs
    private Document htmlDocument; // This is our web page, or in other words, our document
    
    public boolean crawl(String url)
    {
        try
        {
            Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
            Document htmlDocument = connection.get();
            this.htmlDocument = htmlDocument;
            
            if(connection.response().statusCode() == 200) // 200 is the HTTP OK status code
                // indicating that everything is great.
                {
                    System.out.println("\n**Visiting** Received web page at " + url);
                }
            if(!connection.response().contentType().contains("text/html"))
                {
                    System.out.println("**Failure** Retrieved something other than HTML");
                    return false;
                }

            System.out.println("Received web page at " + url);

            Elements linksOnPage = htmlDocument.select("a[href]");
            System.out.println("Found (" + linksOnPage.size() + ") links");
            for(Element link : linksOnPage)
            {
                if((link.toString()).contains("http://timesofindia"))
                    this.links.add(link.absUrl("href"));
                else
                    System.out.println(link.attr("href"));
            }
            return true;
        }
        catch(IOException ioe)
        {
            // We were not successful in our HTTP request
            System.out.println("Error in out HTTP request " + ioe);
            return false;
        }
       
    }
    
    public boolean noImage()
    {
        // Defensive coding. This method should only be used after a successful crawl.
        if(this.htmlDocument == null)
        {
            return true;
        }
        System.out.println("Searching for the page which doesnot have any image. ");
        Elements ImageOnPage = htmlDocument.select("img[src]");
        System.out.println("Found (" + ImageOnPage.size() + ") images");
        while(ImageOnPage.size()==0)
        {
            return true;
        }
        return false;
    }
    //Getter
    
    public List<String> getLinks()
    {
        return this.links;
    }

}
