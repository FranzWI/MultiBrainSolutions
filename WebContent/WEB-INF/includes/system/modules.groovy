import groovy.swing.factory.TDFactory;
import de.mbs.modules.ModulContainer
import de.mbs.modules.interfaces.Modul

html.div {
	table('class':"table table-bordered datatable", id:"table-1") {
		thead {
			tr{
				th "Name"
				th "Version"
				th "installiert"
				th "Aktionen"
			}
		}
		tfoot{
			tr {
				th "Name"
				th "Version"
				th "installiert"
				th "Aktionen"
			}
		}
		tbody {
			def modules = ModulContainer.initialise()
			for(mod in modules.getModules()){
				tr{
					td mod.getModulName()
					td mod.getVersion()
					td {
						if(mod.isInstalled())
							i('class':"entypo-check","style":"color:green")
						else
							i('class':"entypo-cancel-circled","style":"color:red")
					}
					td {
						if(mod.isInstalled()){
							
						}else{
							button('class':"btn btn-default","data-mod":mod.getModulName(),"data-version":mod.getVersion()){
								i('class':"entypo-install")
								println "installieren"
							}
						}
					}
				}
			}
		}
	}

	link(rel:"stylesheet", href:"assets/js/datatables/responsive/css/datatables.responsive.css")
	link(rel:"stylesheet", href:"assets/js/select2/select2-bootstrap.css")
	link(rel:"stylesheet", href:"assets/js/select2/select2.css")

	script(src:"assets/js/jquery.dataTables.min.js")
	script(src:"assets/js/datatables/TableTools.min.js")
	script(src:"assets/js/dataTables.bootstrap.js")
	script(src:"assets/js/datatables/jquery.dataTables.columnFilter.js")
	script(src:"assets/js/datatables/lodash.min.js")
	script(src:"assets/js/datatables/responsive/js/datatables.responsive.js")
	script(src:"assets/js/select2/select2.min.js")

	script('') { println """
		var responsiveHelper;
		var breakpointDefinition = {
		    tablet: 1024,
		    phone : 480
		};
		var tableContainer;
		\$(document).ready(function() {
			tableContainer = \$("#table-1");
		
			tableContainer.dataTable({
				"sPaginationType": "bootstrap",
				"aLengthMenu": [[10, 25, 50, -1], [10, 25, 50, "All"]],
				"bStateSave": true,
				
	
			    // Responsive Settings
			    bAutoWidth     : false,
			    fnPreDrawCallback: function () {
			        // Initialize the responsive datatables helper once.
			        if (!responsiveHelper) {
			            responsiveHelper = new ResponsiveDatatablesHelper(tableContainer, breakpointDefinition);
			        }
			    },
			    fnRowCallback  : function (nRow, aData, iDisplayIndex, iDisplayIndexFull) {
			        responsiveHelper.createExpandIcon(nRow);
			    },
			    fnDrawCallback : function (oSettings) {
			        responsiveHelper.respond();
			    }
			});
			
			\$(".dataTables_wrapper select").select2({
				minimumResultsForSearch: -1
			});
		});
		""" }
}