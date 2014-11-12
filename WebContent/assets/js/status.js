$(document).ready(function(){
	$.ajax({
		  type: "GET",
		  url: "rest/system/services/db",
		  timeout: 10000
		}).done(function(json) {
			var data = JSON.parse(json);
			$('#db-status').removeClass('btn-warning');
			$('#db-status > i').removeClass('entypo-hourglass');
			if(data.status){
				$('#db-status').addClass('btn-success');
				$('#db-status > i').addClass('entypo-check');
			}else{
				$('#db-status').addClass('btn-danger');
				$('#db-status > i').addClass('entypo-cancel');
			}
		}).fail(function(jqXHR, textStatus ) {
			$('#db-status').removeClass('btn-warning');
			$('#db-status > i').removeClass('entypo-hourglass');
			$('#db-status').addClass('btn-danger');
			$('#db-status > i').addClass('entypo-cancel');
		});
	$.ajax({
		  type: "GET",
		  url: "rest/system/services/mail",
		  timeout: 10000
		}).done(function(json) {
			var data = JSON.parse(json);
			$('#mail-status').removeClass('btn-warning');
			$('#mail-status > i').removeClass('entypo-hourglass');
			if(data.status){
				$('#mail-status').addClass('btn-success');
				$('#mail-status > i').addClass('entypo-check');
			}else{
				$('#mail-status').addClass('btn-danger');
				$('#mail-status > i').addClass('entypo-cancel');
			}
			
		}).fail(function(jqXHR, textStatus ) {
			$('#mail-status').removeClass('btn-warning');
			$('#mail-status > i').removeClass('entypo-hourglass');
			$('#mail-status').addClass('btn-danger');
			$('#mail-status > i').addClass('entypo-cancel');
		});
});