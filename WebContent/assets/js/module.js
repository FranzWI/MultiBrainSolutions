var responsiveHelper;
var breakpointDefinition = {
	tablet : 1024,
	phone : 480
};


var tableContainer;


function reloadPage(){
	window.location.reload();
}

$(document).ready(function() {
		
			$('button.btn-install').click(function(){
				var mod = $(this).data('mod');
				var version = $(this).data('version');
				$.ajax({
	        		type : "POST",
	        		url : "rest/modules/install/"+mod+"/"+version,
	        		timeout : 10000
	        	}).done(function(json) {
	        		toastr.success("Modul "+mod+" installiert, System wird in 10sek neugestartet");
	        	}).fail(function(jqXHR, textStatus) {
	        		toastr.error("Modul "+mod+" installalation fehlgeschlagen.");
	        	}).always(function(){
	        		setTimeout(reloadPage, 5000);
	        	});
			});
			
			tableContainer = $("#table-1");

			tableContainer.dataTable({
				//"sPaginationType" : "bootstrap",
				"aLengthMenu" : [ [ 10, 25, 50, -1 ], [ 10, 25, 50, "Alle" ] ],
				"bStateSave" : true,
				"oLanguage":{
				    "sEmptyTable":      "Keine Daten in der Tabelle vorhanden",
				    "sInfo":            "_START_ bis _END_ von _TOTAL_ Einträgen",
				    "sInfoEmpty":       "0 bis 0 von 0 Einträgen",
				    "sInfoFiltered":    "(gefiltert von _MAX_ Einträgen)",
				    "sInfoPostFix":     "",
				    "sInfoThousands":   ".",
				    "sLengthMenu":      "_MENU_ Einträge anzeigen",
				    "sLoadingRecords":  "Wird geladen...",
				    "sProcessing":      "Bitte warten...",
				    "sSearch":          "Suchen",
				    "sZeroRecords":     "Keine Einträge vorhanden.",
				    "oPaginate": {
				        "sFirst":       "Erste",
				        "sPrevious":    "Zurück",
				        "sNext":        "Nächste",
				        "sLast":        "Letzte"
				    },
				    "oAria": {
				        "sSortAscending":  ": aktivieren, um Spalte aufsteigend zu sortieren",
				        "sSortDescending": ": aktivieren, um Spalte absteigend zu sortieren"
				    }
				},

				// Responsive Settings
				bAutoWidth : false,
				fnPreDrawCallback : function() {
					// Initialize the responsive datatables helper once.
					if (!responsiveHelper) {
						responsiveHelper = new ResponsiveDatatablesHelper(
								tableContainer, breakpointDefinition);
					}
				},
				fnRowCallback : function(nRow, aData, iDisplayIndex,
						iDisplayIndexFull) {
					responsiveHelper.createExpandIcon(nRow);
				},
				fnDrawCallback : function(oSettings) {
					responsiveHelper.respond();
				}
			});

			$(".dataTables_wrapper select").select2({
				minimumResultsForSearch : -1
			});
		});