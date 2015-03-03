
var $container;
// init
$(document).ready(function() {
	
	$container = $('#draggable-portlets');
	
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
	
	$container.packery({
		 itemSelector: '.mbs-portlet',
		  layout: 'meticulous'
	});
	
	$container.find('.mbs-portlet').each( function( i, itemElem ) {
		  var draggie = new Draggabilly( itemElem );
		  $container.packery( 'bindDraggabillyEvents', draggie );
	});
	
	// show item order after layout
	function orderItems() {
	  var itemElems = $container.packery('getItemElements');
	  var portletIds = "";
	  $( itemElems ).each( function( i, itemElem ) {
		  portletIds = portletIds+$(this).data('portlet-id')+",";
	  });
	  //portletIds = portletIds.substring(0, (portletIds.length()-1));
	  $.ajax({
		  type: "POST",
		  url: "rest/user/setPortlets/"+portletIds
	  }).fail(function(jqXHR, textStatus ) {
			toastr.error( "error: "+textStatus );
	  });
	}

	//$container.packery( 'on', 'layoutComplete', orderItems );
	$container.packery( 'on', 'dragItemPositioned', orderItems );
	
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