import groovy.xml.MarkupBuilder;

import java.util.Map;
import java.util.Vector;

import de.mbs.handler.ServiceHandler
import de.mbs.abstracts.db.DatabaseView
import de.mbs.abstracts.db.views.UserView
import de.mbs.abstracts.db.objects.User
import de.mbs.abstracts.db.utils.SearchResult
import de.mbs.abstracts.db.utils.Pair

def search = request.getParameter("q")
def dbView = ServiceHandler.getDatabaseView();
def userView = dbView.getUserView();
def user = userView.get(session.user);


long start = System.currentTimeMillis();
Map<String,Vector<Pair<SearchResult,String>>> maps = dbView.search(search, user);
start = System.currentTimeMillis()-start;

if(!maps.get("all")){
	System.out.println ("Suchfunktion fehlerhaft implementiert")
	maps.put("all", new TreeMap<String,Vector<Pair<SearchResult,String>>>());
}

long totalCount = maps.get("all").size();

html.div{
	section('class':"search-results-env"){
		div('class':"row"){
			div('class':"col-md-12"){
				ul('class':"nav nav-tabs right-aligned"){
					li('class':"tab-title pull-left"){
						div('class':"search-string"){
							p {
								i totalCount+" Ergebnisse gefunden fuer: "
								strong("\"${search}\"")
								i " in "+start+" ms"
							}
						}
					}
					li('class':"active"){
						a(href:"#pages", "Alle"){
							strong('class':"disabled-text", "("+totalCount+")")
						}
					}
					for(String key : maps.keySet()){
						Vector<Pair<SearchResult,String>> data = maps.get(key);
						long count = data.size();
						if(!key.equals("all")){
							li{
								a(href:"#"+key.toLowerCase().replaceAll(" ", ""), key){
									if(count > 0)
										strong('class':"disabled-text", "("+count+")")
								}
							}
						}
					}
				}
				// Suchformular
				form(method:"get", 'class':"search-bar", action:"", enctype:"application/x-www-form-urlencoded"){
					div('class':"input-group"){
						input(type:"text",'class':"form-control input-lg", name:"q", value:"${search}", placeholder:"Such nach etwas ...")
						div('class':"input-group-btn"){
							button(type:"submit", 'class':"btn btn-lg btn-primary btn-icon", "Suchen"){ i('class':"entypo-search") }
						}
					}
				}
				// Suchergebnisse
				div('class':"search-results-panes"){
					div('class':"search-results-pane active", id:"pages"){
						ul('class':"search-results"){
							if(totalCount == 0){
								li('class':"search-result"){
									div('class':"sr-inner text-center"){
										h2 'Sorry, keine Treffer'
										hr('')
									}
								}
							}else{
								for(Pair<SearchResult,String> result : maps.get("all")){
									String heading = result.getA().getHeading();
									String content = result.getA().getContent();
									String link = result.getAt().getLink() == null ? "#":result.getAt().getLink();
									if(result.getB() != null ){
										html.mkp.yieldUnescaped(result.getB())
									}else{
										li('class':"search-result"){
											div('class':"sr-inner"){
												h4(''){
													a(href:link, heading)
												}
												p{ html.mkp.yieldUnescaped content }
											}
										}
									}
								}
							}
						}
					}

					for(String key : maps.keySet()){
						Vector<Pair<SearchResult,String>> data = maps.get(key);
						div('class':"search-results-pane", id:key.toLowerCase().replaceAll(" ", "")){
							ul('class':"search-results"){
								if(!data || data.size() == 0){
									li('class':"search-result"){
										div('class':"sr-inner text-center"){
											h2 'Sorry, keine Treffer'
											hr('')
										}
									}
								}else {
									for(Pair<SearchResult,String> result : data){
										String heading = result.getA().getHeading();
										String content = result.getA().getContent();
										String link = result.getAt().getLink() == null ? "#":result.getAt().getLink();
										if(result.getB() != null ){
											html.mkp.yieldUnescaped(result.getB())
										}else{
											li('class':"search-result"){
												div('class':"sr-inner"){
													h4(''){
														a(href:link, heading)
													}
													p{ html.mkp.yieldUnescaped content }
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
}