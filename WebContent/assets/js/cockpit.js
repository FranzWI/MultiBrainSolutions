
var $container;
// init
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
			$.ajax({
				  type: "POST",
				  url: "rest/user/removePortlet/"+portId
				}).done(function() {
					$par.slideUp();
				}).fail(function(jqXHR, textStatus ) {
					toastr.error( "error: "+textStatus );
				});
		}
	}]);
	
	$("#draggable-portlets").sortable({
			connectWith : ".sorted",
			handle : '.tile-stats, .panel-heading ',
			delay : 200,
			stop: function(ev, ui){
				var portletIds = "";
				$(".mbs-portlet").each(function(index,obj) {
					portletIds = portletIds+$(this).data('portlet-id')+",";
				});
				 $.ajax({
					  type: "POST",
					  url: "rest/user/setPortlets/"+portletIds
				  }).done(function() {
					  location.reload();
				  }).fail(function(jqXHR, textStatus ) {
						toastr.error( "error: "+textStatus );
				  });
			}
		}).disableSelection();

	$(function() {		
		$('.portlet > .panel-heading > .panel-options > a[data-rel="close"]').click(function() {
			var $par = $(this).closest('div[data-portlet-id]');
			var portId = $par.attr("data-portlet-id");
			$.ajax({
				  type: "POST",
				  url: "rest/user/removePortlet/"+portId
				}).done(function() {
					location.reload();
				}).fail(function(jqXHR, textStatus ) {
					toastr.error( "error: "+textStatus );
				});
		});
		
		$('.add-portlet').click(function() {
			var portletId = $(this).data('portlet-id');
			$.ajax({
				  type: "POST",
				  url: "rest/user/addPortlet/"+portletId
				}).done(function() {
					location.reload();
				}).fail(function(jqXHR, textStatus ) {
				    toastr.error( "error: "+textStatus );
				});
		});
		
	});
});