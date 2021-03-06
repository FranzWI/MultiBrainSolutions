import java.io.File;
import de.mbs.listener.SessionListener
import de.mbs.abstracts.db.DatabaseView
import de.mbs.abstracts.mail.MailView
import de.mbs.handler.ServiceHandler


// Datenbank verbindung
def DatabaseView es = ServiceHandler.getDatabaseView();
// Email Dienst
def MailView mail = ServiceHandler.getMailView();

def int totalUserCount = es.getUserView().getAll().size();
def int totalSessionCount = SessionListener.getSessionCount()


def String indexSizeDonutData = "", indexCountDonutData = ""
def long indexSize = 0l, indexCount = 0l
// closure, cooles zeusch ...
es.getDatabaseSize().each {key,value->
	indexSizeDonutData+="{label: \""+key+" (KByte)\", value: \""+((value/1024f).trunc(2))+"\"},\n"
	indexSize += value
}

es.getDatabases().each {key->
	long value = es.getEntryCount(key)
	indexCountDonutData+="{label: \""+key+"\", value: \""+value+"\"},\n"
	indexCount += value
}

Runtime runtime = Runtime.getRuntime()
//TODO abh�nig vom Tomcat verzeichniss setzen
File root = new File("/")

def long MBYTE = 1024L*1024L

html.div{
	//System Zeug
	div('class':"row"){
		div('class':"col-md-12"){h3 ("System")}
	}
	div('class':"row"){
		div('class':"col-md-3"){
			h4("Java HEAP Nutzung (RAM)")
			div(id:"ramchart",style:"height: 250px")
			p("Maximal (GByte): "+((Float)(runtime.maxMemory()/(MBYTE*1024L))).trunc(2))
		}
		div('class':"col-md-3"){
			h4("Festplatten Kapazit\u00E4t")
			div(id:"hddchart",style:"height: 250px")
			p("Gesamt (GByte): "+((Float)(root.getTotalSpace()/(MBYTE*1024L))).trunc(2))
		}
		div('class':"col-md-3"){
			h4("Angemeldete Nutzer")
			div(id:"userchart",style:"height: 250px")
			p("Maximal : "+totalUserCount)
		}
		div('class':"col-md-3"){
			//TODO Status asynchron abfragen mittels REST & ajax
			
			h4("Dienste")
			button('class':"btn btn-lg btn-block btn-warning disabled btn-icon icon-left",id:"db-status", "Datenbank "+es.getServiceName()){ i('class':"entypo-hourglass") }
			if(mail != null)button('class':"btn btn-lg btn-block btn-warning disabled btn-icon icon-left",id:"mail-status", "Mail "+mail.getServiceName()){ i('class':"entypo-hourglass") }
		}
	}
	div('class':"row"){
		div('class':"col-md-12"){hr('')}
	}
	// infos zum Status von Elasticsearch
	div('class':"row"){
		div('class':"col-md-12"){h3(es.getServiceName())}
	}
	div('class':"row"){
		if(es.isRunning() && es.getDatabases().size()>0){
			div('class':"col-md-3"){
				h4("Indizes Größe")
				div(id:"indexsizechart",style:"height: 250px")
				p("Gesamtgröße (KByte): "+((indexSize / 1024f).trunc(2)));
			}
			div('class':"col-md-3"){
				h4("Dokumenten Anzahl")
				div(id:"indexdoccountchart",style:"height: 250px")
				p("Gesamt Anzahl: "+indexCount);
			}
		}else{
			if(es.isRunning()){
				div('class':"col-md-12"){
					div('class':"alert alert-info"){ p("Oh! "+es.getServiceName()+" enthält keine Daten! Bitte Tomcat neustarten!") }
				}
			}else{
				div('class':"col-md-12"){
					div('class':"alert alert-danger"){ p("Oh Nein! "+es.getServiceName()+" wird nicht ausgeführt!") }
				}
			}
		}
	}

	script(src:"assets/js/raphael-min.js")
	script(src:"assets/js/morris.min.js")

	//FIXME mal pr�fen ob die Methoden wirklich das machen was sie sollen
	// Arbeitsspeicher bestimmen
	def long ramfree = runtime.freeMemory()/MBYTE
	def long rammemory = (runtime.totalMemory() - ramfree)/MBYTE
	ramfree = runtime.maxMemory()/MBYTE - rammemory

	// Festplatten kapazit�t bestimmen
	def long usablehdd = root.getUsableSpace()/MBYTE
	def long usedhdd = root.getTotalSpace()/MBYTE - usablehdd
	
	script(src:"assets/js/status.js")

	script(

			"""
		function getRandomInt(min, max) {
			return Math.floor(Math.random() * (max - min + 1)) + min;
		}

		\$(document).ready(function(){
			Morris.Donut({
				element: 'ramchart',
				resize: true,
				formatter: function(x, y){
					return x;
				},
				data: [
					{label: "benutzt (MByte)", value: """+rammemory+ """},
					{label: "frei (MByte)", value: """+ramfree+ """},
				],
				labelColor: '#303641',
				colors: ['#f26c4f', '#00a651', '#00bff3', '#0072bc']
			});
			Morris.Donut({
				element: 'hddchart',
				resize: true,
				formatter: function(x, y){
					return x;
				},
				data: [
					{label: "frei (MByte)", value: """+usablehdd+ """},
					{label: "benutzt (MByte)", value: """+usedhdd+ """},
				],
				labelColor: '#303641',
				colors: ['#00bff3', '#0072bc']
			});
			Morris.Donut({
				element: 'userchart',
				resize: true,
				formatter: function(x, y){
					return x;
				},
				data: [
					{label: "Angemeldet", value: """+totalSessionCount+ """},
					{label: "Nicht angemeldet", value: """+(totalUserCount-totalSessionCount)+ """},
				],
				labelColor: '#303641',
				colors: ['#C5D932', '#485859']
			});
			"""+((es.isRunning() && es.getDatabases().size()>0)?"""
			Morris.Donut({
				element: 'indexsizechart',
				resize: true,
				formatter: function(x, y){
					return x;
				},
				data: [
					"""+indexSizeDonutData+"""
				],
				labelColor: '#303641',
				colors: ['#7E3759','#C5403E','#B8D331','#EEB31B','#53C7CB','#C5D932', '#485859']
			});
			Morris.Donut({
				element: 'indexdoccountchart',
				resize: true,
				formatter: function(x, y){
					return x;
				},
				data: [
					"""+indexCountDonutData+"""
				],
				labelColor: '#303641',
				colors: ['#7E3759','#C5403E','#B8D331','#EEB31B','#53C7CB','#C5D932', '#485859']
			});""":"")+"""
		});
		"""
			)
}