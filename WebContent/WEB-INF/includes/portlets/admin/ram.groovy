
Runtime runtime = Runtime.getRuntime()

def long MBYTE = 1024L*1024L

html.div('class':"portlet panel panel-primary"){

	div('class':"panel-heading"){
		div('class':"panel-title", "RAM-Auslastung")
	}
	div('class':"panel-body"){ 
		div(id:"ramchart",style:"height: 250px")
		p("Maximal (GByte): "+((Float)(runtime.maxMemory()/(MBYTE*1024L))).trunc(2))
	}
	
	script(src:"assets/js/raphael-min.js")
	script(src:"assets/js/morris.min.js")

	
	//FIXME mal pr�fen ob die Methoden wirklich das machen was sie sollen
	// Arbeitsspeicher bestimmen
	def long ramfree = runtime.freeMemory()/MBYTE
	def long rammemory = (runtime.totalMemory() - ramfree)/MBYTE
	ramfree = runtime.maxMemory()/MBYTE - rammemory
	
	script("""
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
	});
	""")
		
}