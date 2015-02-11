function refreshPortlets() {
	$('select.portlet-select').html("");

	
	$.ajax({
		type : "GET",
		url : "rest/portlet/getAll",
		timeout : 10000
	}).done(
			function(json) {
				jQuery.each(eval(json), function(i, val) {
					$('select.portlet-select').append(
							'<option value="' + val.id + '">' + val.name
									+ '</option>');
				});
			}).fail(
			function(jqXHR, textStatus) {
				$('select.portlet-select').html(
						"<option>Gruppen abholen fehlgeschlagen</option>");
			});

	$('#portlet-select-rem').select2({
		placeholder : "Porlet auswählen"
	});

	$('#portlet-select-edit').select2({
		placeholder : "Porlet auswählen"
	}).on("select2-selecting", function(e) {
		$('#portlet-edit-name').val('');
		$('#portlet-edit-desc').val('');
		$('#portlet-edit-path').val('');
		// TODO Groups
		$('#portlet-edit-xs').val('');
		$('#portlet-edit-sm').val('');
		$('#portlet-edit-md').val('');
		$('#portlet-edit-lg').val('');
		$.ajax({
			type : "GET",
			url : "rest/portlet/get/" + e.val,
			timeout : 10000
		}).done(function(json) {
			var data = JSON.parse(json);
			if (data) {
				console.log(JSON.stringify(data));
				$('#portlet-edit-name').val(data.name);
				$('#portlet-edit-desc').val(data.description);
				$('#portlet-edit-path').val(data.path);
				//refreshGroups();
				
				$('#portlet-edit-groups option').each(function(){
						$(this).removeAttr("selected");
				});
				
				jQuery.each(data.groups, function(i, val) {
					$('#portlet-edit-groups option').each(function(){
						if($(this).val() == val.id){
							$(this).attr("selected","selected");
						}
					});
				});
				
				$('select.portlet-group').select2({
					placeholder : "Gruppen auswählen"
				});
				$('#portlet-edit-xs').val(data.xs);
				$('#portlet-edit-sm').val(data.sm);
				$('#portlet-edit-md').val(data.md);
				$('#portlet-edit-lg').val(data.lg);
			} else {
				$('#portlet-edit-desc').val("JSON Data ungültig");
			}
		}).fail(function(jqXHR, textStatus) {
			$('#portlet-edit-desc').val("Fehler bei abfrage");
		});
	});
	//refreshGroups();
}

$(document).ready(
		function() {
			refreshPortlets();
			$('select.portlet-group').html("<option></option>");
			$('select.portlet-group').select2({
				placeholder : "Gruppen auswählen"
			});
			
			$.ajax({
				type : "GET",
				url : "rest/group/getAll",
				timeout : 10000
			}).done(
					function(json) {
						jQuery.each(eval(json), function(i, val) {
							$('select.portlet-group').append(
									'<option value="' + val.id + '">' + val.name
											+ '</option>');
						});
			}).fail(function(jqXHR, textStatus) {
				$('select.portlet-group').html(
							"<option>Gruppen abholen fehlgeschlagen</option>");
			});
			
			$('#add-portlet').click(
					function() {
						var json = {};
						$('.portlet-add').each(function() {
							json[$(this).attr('name')] = $(this).val();
						});
						json[$('select.portlet-add-select').attr('name')] = $(
								'select.portlet-add-select').val();
						$.ajax(
								{
									type : "POST",
									url : "rest/portlet/add/"
											+ escape(JSON.stringify(json)
													.replace(/{/g, "(")
													.replace(/}/g, ")"))
								}).done(function() {							
							toastr.success("Portlet erfolgreich hinzugefügt");
							$('.portlet-add').val('');
						}).fail(function(jqXHR, textStatus) {
							toastr.error("Hinzufügen fehlgeschlagen.");
							$('.portlet-add').val('');
						}).always(function(){
							refreshPortlets();
							$('#portlet-add-groups option').each(function(){
								$(this).removeAttr("selected");
							});
							$('select.portlet-group').select2({
								placeholder : "Gruppen auswählen"
							});
						});
					});
			
			$('#edit-portlet').click(
					function() {
						var json = {};
						var data = $('#portlet-select-edit').select2('data').id;
						if(data){
							json['id']= data;
							$('.portlet-edit').each(function() {
								json[$(this).attr('name')] = $(this).val();
							});
							json[$('select.portlet-edit-select').attr('name')] = $(
									'select.portlet-edit-select').val();
							$.ajax(
									{
										type : "POST",
										url : "rest/portlet/edit/"
												+ escape(JSON.stringify(json).replace(/\//g, "SLasH")
														.replace(/{/g, "(")
														.replace(/}/g, ")"))
									}).done(function() {
								toastr.success("Portlet erfolgreich geändert");
								$('.portlet-edit').val('');
							}).fail(function(jqXHR, textStatus) {
								toastr.error("ändern fehlgeschlagen.");
								$('.portlet-edit').val('');
							}).always(function(){
								refreshPortlets();
								$('#portlet-edit-groups option').each(function(){
									$(this).removeAttr("selected");
								});
								$('select.portlet-group').select2({
									placeholder : "Gruppen auswählen"
								});
							});
						}
					});

			$('#rem-portlet').click(function() {
				var data = $('#portlet-select-rem').select2('data').id;
				if (data) {
					$.ajax({
						type : "POST",
						url : "rest/portlet/remove/" + data,
						timeout : 10000
					}).done(function() {
						toastr.success("Portlet erfolgreich gelöscht.");
					}).fail(function(jqXHR, textStatus) {
						toastr.error("Löschen fehlgeschlagen.");
					}).always(function(){
						refreshPortlets();
					});
				}
			});
		});