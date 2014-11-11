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
			//TODO Panel entfernen
		}
	}]);
	
	$(function() {
		$(".draggable-portlets .sorted").sortable({
			connectWith : ".draggable-portlets, .sorted",
			handle : '.tile-stats, .panel-heading ',
			delay : 200,
			stop: function(ev, ui){
				//TODO Änderung der Reihnfolge der Portlets in DB schreiben
			}
		}).disableSelection();
		
		$('.portlet > .panel-heading > .panel-options > a[data-rel="close"]').click(function() {
			//TODO Portlet entfernen
		});
		
		$('.add-portlet').click(function() {
			var userId = $('body').data('user-id');
			var portletId = $(this).data('portlet-id');
			$.ajax({
				  type: "GET",
				  url: "rest/user/addPortlet/"+userId+"/"+portletId
				}).done(function() {
					location.reload();
				}).fail(function(jqXHR, textStatus ) {
				    alert( "error"+textStatus );
				});
		});
		
	});
});