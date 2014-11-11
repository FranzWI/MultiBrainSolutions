$(document).ready(function() {
	
	
	
	context.init({
	    fadeSpeed: 100,
	    filter: function ($obj){},
	    above: 'auto',
	    preventDoubleContext: true,
	    compress: false
	});
	
	context.attach('.tile-stats, .panel', [{
		text: '<i class="entypo-trash"></i>entfernen',
		action: function(e){
			e.preventDefault();
			var callerId = $(this).closest('ul').attr('data-contextjs-callerid');
			var $par = $('[data-contextjs-id="' + callerId + '"]').closest('div[data-portlet-id]');
			var portId = $par.attr("data-portlet-id");
			var userId = $('body').data('user-id');
			$.ajax({
				  type: "POST",
				  url: "rest/user/removePortlet/"+userId+"/"+portId
				}).done(function() {
					$par.slideUp();
				}).fail(function(jqXHR, textStatus ) {
				    alert( "error "+textStatus );
				});
		}
	}]);
	
	$(function() {
		$(".draggable-portlets .sorted").sortable({
			connectWith : ".draggable-portlets, .sorted",
			handle : '.tile-stats, .panel-heading ',
			delay : 200,
			stop: function(ev, ui){
				var userId = $('body').data('user-id');
				var portletIds = "";
				$(".portlet").each(function(index,obj) {
					portletIds = portletIds+$(this).parent().data('portlet-id')+",";
				});
				$.ajax({
					  type: "POST",
					  url: "rest/user/setPortlets/"+userId+"/"+portletIds
					}).done(function() {
					}).fail(function(jqXHR, textStatus ) {
					    alert( "error "+textStatus );
					});
			}
		}).disableSelection();
		
		$('.portlet > .panel-heading > .panel-options > a[data-rel="close"]').click(function() {
			var $par = $(this).closest('div[data-portlet-id]');
			var portId = $par.attr("data-portlet-id");
			var userId = $('body').data('user-id');
			$.ajax({
				  type: "POST",
				  url: "rest/user/removePortlet/"+userId+"/"+portId
				}).done(function() {
					location.reload();
				}).fail(function(jqXHR, textStatus ) {
				    alert( "error"+textStatus );
				});
		});
		
		$('.add-portlet').click(function() {
			var userId = $('body').data('user-id');
			var portletId = $(this).data('portlet-id');
			$.ajax({
				  type: "POST",
				  url: "rest/user/addPortlet/"+userId+"/"+portletId
				}).done(function() {
					location.reload();
				}).fail(function(jqXHR, textStatus ) {
				    alert( "error"+textStatus );
				});
		});
		
	});
});