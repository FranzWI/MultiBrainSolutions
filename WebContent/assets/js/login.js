$(document).ready(function(){
	// beim schließen des Reg Mod zurücksetzen
	$("#register-close").click(function(){
		$('input.register').each(function(){
			$(this).parent().removeClass('has-error');
			$(this).val("");
		});
		$('#reg-error').html("");
		$('#reg-error').hide();
	});
	
	$("#register-button").click(function(){
		var json = {};
		var error = false;
		$('input.register').each(function(){
			if($(this).val() == ""){
				$(this).parent().addClass('has-error');
				error = true;
			}else{
				$(this).parent().removeClass('has-error');
				json[$(this).attr('id')] = $(this).val();
			}
		});
		if(json['pw'] != json['rpw']){
			$('#pw').parent().addClass('has-error');
			$('#rpw').parent().addClass('has-error');
			$('#reg-error').html("<p>Passwörter sind nicht identisch!</p>");
			$('#reg-error').slideDown();
			error = true;
		}
		if(error == false){
			$.ajax({
				  type: "POST",
				  url: "rest/user/register/"+escape(JSON.stringify(json).replace(/{/g,"(").replace(/}/g,")"))
			}).done(function() {
					toastr.success("Registrierung erfolgreich.");
			}).fail(function(jqXHR, textStatus ) {
				    alert( "error"+textStatus );
			});
		}
	});
});