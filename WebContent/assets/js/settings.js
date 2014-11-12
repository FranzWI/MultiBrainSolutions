$(document).ready(function() {
	
	function save(target){
		var jsonData = {};
		$('input.'+target).each(function(){
			jsonData[$(this).data('propertie-key')] = $(this).val();
		});
		$.ajax({
			  type: "POST",
			  url: "rest/settings/setProperties/"+target+"/"+escape(JSON.stringify(jsonData).replace(/{/g,"(").replace(/}/g,")"))
			}).done(function() {
				toastr.success("Änderung erfolgreich übernommen");
			}).fail(function(jqXHR, textStatus ) {
			    alert( "error"+textStatus );
			});
	}
	
	$('#save-proxy').click(function() {
		save('proxy');
	});
	
	$('#save-mail').click(function() {
		save('mail');
	});
	
	$('#save-db').click(function() {
		save('db');
	});
	
});