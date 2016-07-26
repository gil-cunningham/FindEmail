package com.gil.example;

import java.util.HashSet;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DiscoverLinks {

	private String _url;
	private HashSet<String> _links;
	private HashSet<String> _discoverLinks;
	
	public DiscoverLinks(String url)
	{			
		_url = url;
	
		// seeds for collecting all links		
		_links = new HashSet<String>();
		_links.add(url);
						
		_discoverLinks = new HashSet<String>();
		_discoverLinks.add(url);
	}
	
	public Set<String> discoverAllLinks()	
	{
		return discoverAllLinksImpl(_links, _discoverLinks);		
	}
	
	// recursively discover links from page
	private Set<String> discoverAllLinksImpl(Set<String> allLinks, Set<String> discoverLinks)
	{				
		Set<String> newDiscoverLinks = new HashSet<String>();
		
		for (String link : discoverLinks) {
			
			try {			
				Document doc = Jsoup.connect("http://" + link).get();			
				Elements elements = doc.select("a[href]");				
				
				for (Element e : elements) {
		        	
		        	String l = e.attr("href");
		        	if (l.contains(_url)) {						
						allLinks.add(l);
						newDiscoverLinks.add(l);
					}		        	
		        }
			}
			catch (Exception e) {
				//e.printStackTrace();
			}			
		}
		
		if (newDiscoverLinks.size() > 0) {
			return discoverAllLinksImpl(allLinks, newDiscoverLinks);
		}
		
		return allLinks;
	}
	
}
