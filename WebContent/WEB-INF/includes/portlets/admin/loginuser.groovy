import de.mbs.listener.SessionListener
import de.mbs.abstracts.db.DatabaseView
import de.mbs.abstracts.mail.MailView
import de.mbs.handler.ServiceHandler


// Datenbank verbindung
def DatabaseView es = ServiceHandler.getDatabaseView();

def int totalUserCount = es.getUserView().getAll().size();
def int totalSessionCount = SessionListener.getSessionCount()

html.div('class':"portlet panel panel-primary"){

	div('class':"panel-heading"){
		div('class':"panel-title", "Angemeldete Nutzer")
	}
	div('class':"panel-body"){ 
		div(id:"userchart",style:"height: 250px")
			p("Maximal : "+totalUserCount)
	}
	
	script(src:"assets/js/raphael-min.js")
	script(src:"assets/js/morris.min.js")
	
	script("""
	\$(document).ready(function(){
		Morris.Donut({
				element: 'userchart',
				resize: true,
				formatter: function(x, y){
					return x;
				},
				data: [
					{label: "Angemeldet", value: """+totalSessionCount+ """},
					{label: "Nicht angemeldet", value: """+((totalUserCount-totalSessionCount)< 0 ? 0:(totalUserCount-totalSessionCount))+ """},
				],
				labelColor: '#303641',
				colors: ['#C5D932', '#485859']
			});
	});
	""")
		
}