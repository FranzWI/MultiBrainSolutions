import java.io.File;

Runtime runtime = Runtime.getRuntime()
//TODO abhänig vom Tomcat verzeichniss setzen
File root = new File("/")

def long MBYTE = 1024L*1024L

html.div{ 
	div('class':"row"){
		div('class':"col-md-4"){
			h4("Java HEAP Nutzung (RAM)")
			div(id:"ramchart",style:"height: 250px")
			p("Maximal (GByte): "+((Float)(runtime.maxMemory()/(MBYTE*1024L))).trunc(2))
		}
		div('class':"col-md-4"){
			h4("Festplatten Kapazit\u00E4t")
			div(id:"hddchart",style:"height: 250px")
			p("Maximal (GByte): "+((Float)(root.getTotalSpace()/(MBYTE*1024L))).trunc(2))
		}
		div('class':"col-md-4"){
			h4("Dienste")
			//TODO Elastice search nicht erreichbar dann btn-red und anderes icon siehe kaputter Dienst
			button('class':"btn btn-lg btn-block btn-green disabled btn-icon icon-left", "Elasticsearch"){
				i('class':"entypo-check")
			}
			button('class':"btn btn-lg btn-block btn-red disabled btn-icon icon-left", "kaputter Dienst"){
				i('class':"entypo-cancel")
			}
		}
	}
	script(src:"assets/js/raphael-min.js")
	script(src:"assets/js/morris.min.js")
	
	
	def long ramfree = runtime.freeMemory()/MBYTE
	def long rammemory = (runtime.totalMemory() - ramfree)/MBYTE
	ramfree = runtime.maxMemory()/MBYTE - rammemory
	
	
	def long usablehdd = root.getUsableSpace()/MBYTE;
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
				data: [
					{label: "Frei (MByte)", value: """+usablehdd+ """},
					{label: "Benutzt (MByte)", value: """+usedhdd+ """},
				],
				labelColor: '#303641',
				colors: ['#00bff3', '#0072bc']
			});
		});
		"""		
	)
}