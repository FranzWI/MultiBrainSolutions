function refreshGroupSelections() {
	$('select.group-select').html("");
	$('#group-edit-name').val('');
	$('#group-edit-desc').val('');
	$.ajax({
		type : "GET",
		url : "rest/group/getAll",
		timeout : 10000
	}).done(
			function(json) {
				jQuery.each(eval(json), function(i, val) {
					$('select.group-select').append(
							'<option value="' + val.id + '">' + val.name
									+ '</option>');
				});
			}).fail(
			function(jqXHR, textStatus) {
				$('select.group-select').html(
						"<option>Gruppen abholen fehlgeschlagen</option>");
			});

	$("select.group-select").select2({
		placeholder : "Gruppe auswählen"
	}).on("select2-selecting", function(e) {
		if ($(this).parent().parent().hasClass('edit')) {
			$.ajax({
				type : "GET",
				url : "rest/group/get/" + e.val,
				timeout : 10000
			}).done(function(json) {
				var data = JSON.parse(json);
				if (data) {
					$('#group-edit-name').val(data.name);
					$('#group-edit-desc').val(data.description);
				} else {
					$('#group-edit-desc').val("JSON Data ungültig");
				}
			}).fail(function(jqXHR, textStatus) {
				$('#group-edit-desc').val("Fehler bei abfrage");
			});
		}
	});
}

$(document).ready(
		function() {
			refreshGroupSelections();

			$('#add-group').click(
					function() {
						var json = {};
						json['name'] = $('#group-add-name').val();
						json['description'] = $('#group-add-desc').val();
						$.ajax(
								{
									type : "POST",
									url : "rest/group/add/"
											+ escape(JSON.stringify(json)
													.replace(/{/g, "(")
													.replace(/}/g, ")"))
								}).done(function() {
							refreshGroupSelections();
							toastr.success("Gruppe erfolgreich hinzugefügt");
							$('#group-add-name').val('');
							$('#group-add-desc').val('');
						}).fail(function(jqXHR, textStatus) {
							toastr.error("Hinzufügen fehlgeschlagen.");
						});
					});

			$('#edit-group').click(
					function() {
						var id = $('#group-edit-select').select2('data').id;
						if (id) {
							var json = {};
							json['id'] = id;
							json['name'] = $('#group-edit-name').val();
							json['description'] = $('#group-edit-desc').val();
							$.ajax(
									{
										type : "POST",
										url : "rest/group/edit/"
												+ escape(JSON.stringify(json)
														.replace(/{/g, "(")
														.replace(/}/g, ")"))
									}).done(function() {
								refreshGroupSelections();
								toastr.success("Gruppe erfolgreich geändert");
							}).fail(function(jqXHR, textStatus) {
								toastr.error("Ändern fehlgeschlagen.");
							});
						}
					});

			$('#rem-group').click(function() {
				var id = $('#group-rem-select').select2('data').id;
				if (id) {
					$.ajax({
						type : "POST",
						url : "rest/group/remove/" + id,
						timeout : 10000
					}).done(function() {
						refreshGroupSelections();
						toastr.success("Gruppe erfolgreich gelöscht.");
					}).fail(function(jqXHR, textStatus) {
						toastr.error("Löschen fehlgeschlagen.");
					});
				}
			});

			$('.refresh-group').click(function() {
				refreshGroupSelections();
			});
		});