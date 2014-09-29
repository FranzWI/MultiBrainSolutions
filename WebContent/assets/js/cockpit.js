$(document).ready(function() {
	$(function() {
		$(".draggable-portlets .sorted").sortable({
			connectWith : ".draggable-portlets, .sorted",
			handle : '.tile-stats, .panel-heading ',
			delay : 500
		}).disableSelection();
	});
});