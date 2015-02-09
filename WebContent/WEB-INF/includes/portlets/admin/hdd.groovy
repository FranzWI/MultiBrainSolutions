def File root = new File("/")

def long MBYTE = 1024L*1024L

html.div('class':"portlet panel panel-primary"){

	div('class':"panel-heading"){
		div('class':"panel-title", "Festplattenauslastung")
	}
	div('class':"panel-body"){
		div(id:"hddchart",style:"height: 250px")
		p("Gesamt (GByte): "+((Float)(root.getTotalSpace()/(MBYTE*1024L))).trunc(2))
	}

	// Festplatten kapazit�t bestimmen
	def long usablehdd = root.getUsableSpace()/MBYTE
	def long usedhdd = root.getTotalSpace()/MBYTE - usablehdd


	script(src:"assets/js/raphael-min.js")
	script(src:"assets/js/morris.min.js")

	script("""
	\$(document).ready(function(){
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
	});
	""")

}