import java.io.File;
import de.mbs.listener.SessionListener
import de.mbs.elasticsearch.ElasticsearchContainer


//TODO maximale Nutzer anzahl aus Datenbank ermitteln
def int totalUserCount = 20;
def int totalSessionCount = SessionListener.getSessionCount()
// Datenbank verbindung
def ElasticsearchContainer es = ElasticsearchContainer.initialise();

def String indexDonutData = ""
def long indexSize = 0l
// closure, cooles zeusch ...
es.getESIndiceSize().each {key,value-> 
	indexDonutData+="{label: \""+key+" (KByte)\", value: \""+((value/1024f).trunc(2))+"\"},\n"
	indexSize += value
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
			h4("Dienste")
			//TODO Elastice search nicht erreichbar dann btn-red und anderes icon siehe kaputter Dienst
			if(es.isRunning()){
				button('class':"btn btn-lg btn-block btn-green disabled btn-icon icon-left", "Elasticsearch"){ i('class':"entypo-check") }
			}else{
				button('class':"btn btn-lg btn-block btn-red disabled btn-icon icon-left", "Elasticsearch"){ i('class':"entypo-cancel") }
			}
		}
	}
	div('class':"row"){
		div('class':"col-md-12"){hr('')}
	}
	// infos zum Status von Elasticsearch
	div('class':"row"){
		div('class':"col-md-12"){h3("Elasticsearch")}
	}
	div('class':"row"){
		if(es.isRunning()){
			div('class':"col-md-3"){
				h4("Indizes Größe")
				div(id:"indexsizechart",style:"height: 250px")
				p("Gesamtgröße (KByte): "+((indexSize / 1024f).trunc(2)));
			}
		}else{
			div('class':"col-md-12"){
				div('class':"alert alert-danger"){
					strong("Oh Nein! Elasticsearch wird nicht ausgeführt!")
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
					{label: "Benutzer RAM (MByte)", value: """+rammemory+ """},
					{label: "Freier RAM (MByte)", value: """+ramfree+ """},
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
					{label: "Frei (MByte)", value: """+usablehdd+ """},
					{label: "Benutzt (MByte)", value: """+usedhdd+ """},
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
			Morris.Donut({
				element: 'indexsizechart',
				resize: true,
				formatter: function(x, y){
					return x;
				},
				data: [
					"""+indexDonutData+
	"""
				],
				labelColor: '#303641',
				colors: ['#7E3759','#C5403E','#B8D331','#EEB31B','#53C7CB','#C5D932', '#485859']
			});
		});
		"""
	)
}