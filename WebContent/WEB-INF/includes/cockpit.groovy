import java.awt.GraphicsConfiguration.DefaultBufferCapabilities;

import de.mbs.abstracts.db.DatabaseView
import de.mbs.abstracts.db.views.UserView
import de.mbs.abstracts.db.views.PortletView
import de.mbs.abstracts.db.views.UserPortletView
import de.mbs.abstracts.db.objects.UserPortlet
import de.mbs.abstracts.db.objects.Portlet
import de.mbs.abstracts.db.objects.User
import de.mbs.handler.ServiceHandler

def dbView = ServiceHandler.getDatabaseView();
def userView = dbView.getUserView();
def portletView = dbView.getPortletView();
def userportletView = dbView.getUserPortletView();
def user = userView.get(session.user);

html.div {
	link(rel:"stylesheet", href:"assets/css/context.bootstrap.css")
	div('class':"row"){
		div('class':"col-md-12", style:"margin-bottom: 10px;"){
			h2(style:"float:left;display: inline;", "Cockpit")
			div('class':"btn-group"){
				button('class':"btn btn-default tooltip-primary dropdown-toggle", 'data-toggle':"dropdown", style:"float:left;display: inline;margin-top: 15px; margin-left: 15px;", "+")
				ul('class':"dropdown-menu dropdown-default", role:"menu"){
					Vector<Portlet> possiblePortlets = portletView.getPossiblePortletsForUser(session.user);
					if(!possiblePortlets || possiblePortlets.size() == 0){
						li { a ("keine Portlets vorhanden") }
					}else{
						for(Portlet p: possiblePortlets){
							li{
								//TODO Portlets hinzufügen
								a(href:"#",'class':"add-portlet","data-portlet-id":p.getId(), p.getName())
							}
						}
					}
				}
			}
		}
	}
	div('id':"draggable-portlets", 'class':"row"){
		Vector<UserPortlet> userPortlets = userportletView.byOwner(session.user);
		//Vector<Map<String,String>> userPortlets = user.getPortlets();
		if(!userPortlets || userPortlets.size() == 0){
			div('class':"col-xs-12"){
				div('class':"alert alert-info"){ p("keine Portlets auf dem Dashboard") }
			}
		}else{
			//TODO Portlets laden
			for(UserPortlet p: userportletView.getAll()){
				System.out.println(p.getId());
			}
			for(UserPortlet map: userPortlets){
				System.out.println(map.getId());
				Portlet p = portletView.get(map.getPortletId());
				if(p){
					String size = "";
					if(p.getSizeXS()>0)
						size+=" col-xs-"+p.getSizeXS();
					if(p.getSizeSM()>0)
						size+=" col-sm-"+p.getSizeSM();
					if(p.getSizeMD()>0)
						size+=" col-md-"+p.getSizeMD();
					if(p.getSizeLG()>0)
						size+=" col-lg-"+p.getSizeLG();
					div('class':size+" mbs-portlet",'data-portlet-id':map.getId()){
						i('style':"display:none")
						include('/WEB-INF/includes/portlets/'+p.getPath())
					}
				}else{
					//TODO Fehler Portlet ID ungültig
				}
			}
		}
	}
	script(src:"assets/js/draggabilly.pkgd.min.js")
	script(src:"assets/js/packery.pkgd.min.js")
	script(src:"assets/js/context.js")
	script(src:"assets/js/cockpit.js")
}