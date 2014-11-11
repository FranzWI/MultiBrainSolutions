import java.awt.GraphicsConfiguration.DefaultBufferCapabilities;

import de.mbs.abstracts.db.DatabaseView
import de.mbs.abstracts.db.views.UserView
import de.mbs.abstracts.db.views.PortletView
import de.mbs.abstracts.db.objects.Portlet
import de.mbs.abstracts.db.objects.User
import de.mbs.handler.ServiceHandler

def dbView = ServiceHandler.getDatabaseView();
def userView = dbView.getUserView();
def portletView = dbView.getPortletView();
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
	div('class':"draggable-portlets"){
		div('class':"row sorted"){
			Vector<String> userPortlets = user.getPortlets();
			if(!userPortlets || userPortlets.size() == 0){
				div('class':"col-xs-12"){
					div('class':"alert alert-danger"){
						p("keine Portlets auf dem Dashboard")
					}
				}
			}else{
				//TODO Portlets laden
				for(String pId: userPortlets){
					Portlet p = portletView.get(pId);
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
						div('class':size,'data-portlet-id':p.getId(),"style":"float:left"){
							i('style':"display:none")
							include('/WEB-INF/includes/portlets/'+p.getPath())
						}
					}else{
						//TODO Fehler Portlet ID ungültig
					}
				}
			}	
		}
	}
	script(src:"assets/js/context.js")
	script(src:"assets/js/cockpit.js")
}