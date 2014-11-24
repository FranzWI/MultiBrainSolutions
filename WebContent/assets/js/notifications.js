var Notifications = {};
Notifications.handler = (function() {
	
	return {
		refreshView:function(){
			window.location.reload();
		},
		readAll:function(){
			$.ajax({
				type : "GET",
				url : "rest/notifications/removeAll",
				timeout : 10000
			}).done(function(json) {
				toastr.success("Alle Benachrichtigungen entfernt.");
			}).fail(function(jqXHR, textStatus) {
				toastr.error("Entfernen der Benachrichtigungen fehlgeschlagen");			
			}).always(function(){
				setTimeout(Notifications.handler.refreshView,1000);
			});
		}
	}
})();

$(document).ready(function(){
	$('#read-all-notifications').click(function(){
		Notifications.handler.readAll();
	});
});