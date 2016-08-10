package WebCrawl;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class WebSpider
{
    private static final int max_page = 20;
    private Set<String> pagesVisited = new HashSet<String>();
    private List<String> pagesToVisit = new LinkedList<String>();
    
    //Method to determine not to visit same page again n again.
    
    private String nextUrl()
    {
        String nextUrl;
        do
        {
            nextUrl = this.pagesToVisit.remove(0);
        } while(this.pagesVisited.contains(nextUrl));
        this.pagesVisited.add(nextUrl);
        return nextUrl;
    }
    
    //Get the set of all url's on a page.
    public void search(String url)
    {
        while(this.pagesVisited.size() < max_page)
        {
            String currentUrl;
            SpiderLeg leg = new SpiderLeg();
            if(this.pagesToVisit.isEmpty())
            {
                currentUrl = url;
                this.pagesVisited.add(url);
            }
            else
            {
                currentUrl = this.nextUrl();
            }
            leg.crawl(currentUrl); 
            boolean NoImage= leg.noImage();
            if(NoImage){
                System.out.println(String.format("\n\n No Image tag found at %s\n\n", currentUrl));
                break;
            }
            this.pagesToVisit.addAll(leg.getLinks());
        }
        System.out.println(String.format("Visited %s web page(s)", this.pagesVisited.size()));
    }

}
