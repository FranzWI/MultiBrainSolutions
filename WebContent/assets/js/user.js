function loadUser(){
	$('#select-user-edit').html("<option></option>");
	$('#select-user-pwreset').html("<option></option>");
	$('#select-user-rem').html("<option></option>");
	$.ajax({
		type : "GET",
		url : "rest/user/getAll",
		timeout : 10000
	}).done(
			function(json) {
				jQuery.each(eval(json), function(i, val) {
					$('#select-user-edit').append('<option value="' + val.id + '">' + val.username + ' ('+val.firstname+' '+val.lastname+')</option>');
					$('#select-user-pwreset').append('<option value="' + val.id + '">' + val.username + ' ('+val.firstname+' '+val.lastname+')</option>');
					$('#select-user-rem').append('<option value="' + val.id + '">' + val.username + ' ('+val.firstname+' '+val.lastname+')</option>');
				});
			}).fail(
			function(jqXHR, textStatus) {
				$('select.portlet-select').html(
						"<option>Gruppen abholen fehlgeschlagen</option>");
			});
}

function loadGroups(){
	$('#user-edit-groups').html("<option></option>");
	$('#user-add-groups').html("<option></option>");
	
	$.ajax({
		type : "GET",
		url : "rest/group/getAll",
		timeout : 10000
	}).done(
			function(json) {
				jQuery.each(eval(json), function(i, val) {
					$('#user-edit-groups').append('<option value="' + val.id + '">' + val.name+ '</option>');
					$('#user-add-groups').append('<option value="' + val.id + '">' + val.name+ '</option>');
				});
	}).fail(function(jqXHR, textStatus) {
	});
}


$(document).ready(function(){
	
	// hinzufügen 
	$('#add-user').click(function() {
		alert('hinzufügen');
	});
	$('#user-add-groups').select2({placeholder:"Gruppen"});
	
	// ändern
	$('#select-user-edit').select2({
		placeholder:"Nutzer"
	}).on("select2-selecting", function(e) {
		
	});
	$('#user-edit-groups').select2({placeholder:"Gruppen"});
	$('#edit-user').click(function() {
		var id = $('#select-user-edit').select2('data').id;
		if(id){
			alert('ändern');
		}
	});
	
	
	// Passwort zurücksetzen
	$('#select-user-pwreset').select2({
		placeholder:"Nutzer"
	});
	$('#pwreset-user').click(function() {
		var id = $('#select-user-pwreset').select2('data').id;
		if(id){
			$.ajax({
				type : "GET",
				url : "rest/user/resetPassword/"+id,
				timeout : 10000
			}).done(function(json) {
				toastr.success("Passwort erfolgreich zurückgesetzt");
			}).fail(function(jqXHR, textStatus) {
				toastr.error("Fehlschlag!");
			});
		}
	});
	
	// Nutzer löschen
	$('#select-user-rem').select2({
		placeholder:"Nutzer"
	});
	
	$('#rem-user').click(function() {
		var id = $('#select-user-rem').select2('data').id;
		if(id){
			alert('löschen');
		}
	});
	
	loadUser();
	loadGroups();
});