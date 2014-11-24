var User = {};

User.manager = (function(){         
    return{
        loadUser: function(){
        	$('#select-user-edit').html("<option></option>");
        	$('#select-user-pwreset').html("<option></option>");
        	$('#select-user-rem').html("<option></option>");
        	$.ajax({
        		type : "GET",
        		url : "rest/user/getAll",
        		timeout : 10000
        	}).done(function(json) {
        		jQuery.each(eval(json), function(i, val) {
        			$('#select-user-edit').append('<option value="' + val.id + '">' + val.username + ' ('+val.firstname+' '+val.lastname+')</option>');
        			$('#select-user-pwreset').append('<option value="' + val.id + '">' + val.username + ' ('+val.firstname+' '+val.lastname+')</option>');
        			$('#select-user-rem').append('<option value="' + val.id + '">' + val.username + ' ('+val.firstname+' '+val.lastname+')</option>');
        		});
        	}).fail(function(jqXHR, textStatus) {
        		//TODO Fehlermeldung
        	}).always(function(){
        		$('#select-user-edit').select2({placeholder:"Nutzer auswählen"});
            	$('#select-user-pwreset').select2({placeholder:"Nutzer auswählen"});
            	$('#select-user-rem').select2({placeholder:"Nutzer auswählen"});		
        	});
        },
        loadGroups: function (){
        	$('#user-edit-groups').html("<option></option>");
        	$('#user-add-groups').html("<option></option>");
        	
        	$.ajax({
        		type : "GET",
        		url : "rest/group/getAll",
        		timeout : 10000
        	}).done(function(json) {
        		jQuery.each(eval(json), function(i, val) {
        			$('#user-edit-groups').append('<option value="' + val.id + '">' + val.name+ '</option>');
        			$('#user-add-groups').append('<option value="' + val.id + '">' + val.name+ '</option>');
        		});
        	}).fail(function(jqXHR, textStatus) {
        		//TODO Fehlermeldung
        	}).always(function (){
        		$('#user-edit-groups').select2({placeholder:"Gruppen auswählen"});
            	$('#user-add-groups').select2({placeholder:"Gruppen auswählen"});
        	});
        }
    }
})();

$(document).ready(function(){
	
	// hinzufügen 
	$('#add-user').click(function() {
		var json = {};
		$('.user-add').each(function() {
			json[$(this).attr('name')] = $(this).val();
		});
		json['active'] = $('input.user-add[name="active"]').is(":checked");
		json['groups'] = $('#user-add-groups').val();
		$.ajax({
					type : "POST",
					url : "rest/user/add/"
							+ escape(JSON.stringify(json)
									.replace(/{/g, "(")
									.replace(/}/g, ")"))
		}).done(function() {
			toastr.success("Nutzer erfolgreich angelegt");
		}).fail(function(jqXHR, textStatus) {
			toastr.error("anlegen fehlgeschlagen.");
		}).always(function(){
			$('.user-add').val('');
			$('input.user-add[name="active"]').prop('checked', false);
			$('#user-add-groups').val([]);
			$('#user-add-groups').select2({placeholder:"Gruppe auswählen"});
			
		});
	});
	$('#user-add-groups').select2({placeholder:"Gruppen"});
	
	
	// Nutzer ändern
	$('#select-user-edit').select2({
		placeholder:"Nutzer"
	}).on("select2-selecting", function(e) {
		$(".user-edit").val("");
		$('#user-edit-groups').val([]);
		$.ajax({
			type : "GET",
			url : "rest/user/get/"+e.val,
			timeout : 10000
		}).done(function(json) {
			var data = JSON.parse(json);
			if (data) {
				$('input.user-edit[name="username"]').val(data.username);
				$('input.user-edit[name="firstname"]').val(data.firstname);
				$('input.user-edit[name="lastname"]').val(data.lastname);
				$('input.user-edit[name="email"]').val(data.email);
				$('input.user-edit[name="active"]').prop('checked', data.active);
				$('input.user-edit[name="apikey"]').val(data.apikey);
				jQuery.each(eval(data.groups), function(i, val) {
					$('#user-edit-groups option').each(function(){
						if($(this).val() == val.id){
							$(this).attr("selected","selected");
						}
						console.log($(this).val() +"=="+ val.id);
					});
				});
				$('#user-edit-groups').select2({placeholder:"Gruppen auswählen"});
			} else {
				toastr.error("JSON Data ungültig");
			}
			
		}).fail(function(jqXHR, textStatus) {
			toastr.error("Abfrage des Nutzers fehlgeschlagen!");
		});
	});
	
	$('#user-edit-groups').select2({placeholder:"Gruppen auswählen"});
	$('#edit-user').click(function() {
		var id = $('#select-user-edit').select2('data').id;
		if(id){
			var json = {};
			json['id'] = id;
			$('.user-edit').each(function() {
				json[$(this).attr('name')] = $(this).val();
			});
			json['active'] = $('input.user-edit[name="active"]').is(":checked");
			json['groups'] = $('#user-edit-groups').val();
			$.ajax({
						type : "POST",
						url : "rest/user/edit/"
								+ escape(JSON.stringify(json)
										.replace(/{/g, "(")
										.replace(/}/g, ")"))
			}).done(function() {
				toastr.success("Nutzer erfolgreich geändert");
			}).fail(function(jqXHR, textStatus) {
				toastr.error("ändern fehlgeschlagen.");
			}).always(function(){
				$('.user-edit').val('');
				$('input.user-edit[name="active"]').prop('checked', false);
				$('#user-edit-groups').val([]);
				$('#user-edit-groups').select2({placeholder:"Gruppe auswählen"});
				
			});
		}
	});
	
	
	//apikey anfordern
	$('#edit-refresh-api').click(function(){
		var id = $('#select-user-edit').select2('data').id;
		if(id){
			$.ajax({
				type : "GET",
				url : "rest/user/apikey",
				timeout : 10000
			}).done(function(json) {
				$('#user-apikey').val(json);
			}).fail(function(jqXHR, textStatus) {
				toastr.error("Fehlschlag!");
			});
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
			$.ajax({
				type : "GET",
				url : "rest/user/remove/"+id,
				timeout : 10000
			}).done(function(json) {
				toastr.success("Nutzer erfolgreich entfernt.");
			}).fail(function(jqXHR, textStatus) {
				toastr.error("Nutzer konnte nicht entfernt werden.");
			}).always(function(){
				User.manager.loadUser();
			});
		}
	});
	
	User.manager.loadUser();
	User.manager.loadGroups();
});