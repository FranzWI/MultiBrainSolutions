import de.mbs.modules.ModulContainer
import de.mbs.modules.interfaces.Modul

html.div {
	h2("Module")
	br()
	table('class':"table table-bordered datatable", id:"modultable") {
		thead {
			tr{
				th "Name"
			}
		}
		tbody {
			def modules = ModulContainer.initialise()
			def i = 0
			for(mod in modules.getModules()){
				i++
				def trClass = ""
				if(i%2==0)
					trClass = "odd"
				else
					trClass = "even"
				tr('class':trClass) {
					td mod.getModulName()
				}
			}
		}
	}

	link(rel:"stylesheet", href:"assets/js/datatables/responsive/css/datatables.responsive.css")
	link(rel:"stylesheet", href:"assets/js/select2/select2-bootstrap.css")
	link(rel:"stylesheet", href:"assets/js/select2/select2.css")
	
	script('') {
		println """
		\$(document).ready(function() {
			\$('#modultable').dataTable();
		} );
		"""
	}
	
	script(src:"assets/js/jquery.dataTables.min.js")
	script(src:"assets/js/datatables/TableTools.min.js")
	script(src:"assets/js/dataTables.bootstrap.js")
	script(src:"assets/js/datatables/jquery.dataTables.columnFilter.js")
	script(src:"assets/js/datatables/lodash.min.js")
	script(src:"assets/js/datatables/responsive/js/datatables.responsive.js")
	script(src:"assets/js/select2/select2.min.js")
}