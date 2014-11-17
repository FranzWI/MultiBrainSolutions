html.div {
	html.div{
		div('class':"row"){
			div('class':"col-md-12"){ h2 "Gruppen" }
		}
		div('class':"row"){
			div('class':"col-md-12"){
				div('class':"tabs-vertical-env"){

					ul('class':"nav tabs-vertical"){
						li('class':"active"){
							a(href:'#groupadd', 'data-toggle':"tab", "Gruppe hinzufügen")
						}
						li {
							a(href:'#groupedit', 'data-toggle':"tab", "Gruppe ändern")
						}
						li {
							a(href:'#groupremove', 'data-toggle':"tab", "Gruppe entfernen")
						}
					}

					div('class':"tab-content"){
						div('class':"tab-pane active", id:"groupadd"){
							div('class':"row"){
								div('class':'col-md-12'){
									div('class':"form-horizontal",role:"form"){
										div('class':"form-group"){
											label('for':"name",'class':"col-sm-1 control-label", "Name")
											div('class':"col-sm-6"){
												input(type:"text",id:'group-add-name','class':"form-control", 'placeholder':"Name")
											}
										}
										div('class':"form-group"){
											label('for':"desc",'class':"col-sm-1 control-label", "Beschreibung")
											div('class':"col-sm-6"){
												input(type:"text",id:'group-add-desc','class':"form-control", 'placeholder':"Beschreibung")
											}
										}
										div('class':"form-group"){
											div('class':"col-sm-offset-1 col-sm-6"){
												button('class':"btn btn-info", id:"add-group", "Gruppe anlegen");
											}
										}
									}
								}
							}
						}
						div('class':"tab-pane", id:"groupedit"){
							div('class':"row"){
								div('class':'col-md-12'){
									div('class':"form-horizontal",role:"form"){
										div('class':"form-group edit"){
											label('for':"name",'class':"col-sm-1 control-label", "Gruppe")
											div('class':"col-sm-3"){
												select(id:"group-edit-select",'class':"group-select"){
													option("")
												}
											}
											div('class':"col-sm-1"){
												button('class':"btn btn-info btn-lg refresh-group"){
													i ('class':"entypo-arrows-ccw")
												}
											}
										}
										div('class':"form-group"){
											label('for':"desc",'class':"col-sm-1 control-label", "Neuer Name")
											div('class':"col-sm-6"){
												input(type:"text",id:'group-edit-name','class':"form-control", 'placeholder':"Name")
											}
										}
										div('class':"form-group"){
											label('for':"desc",'class':"col-sm-1 control-label", "Beschreibung")
											div('class':"col-sm-6"){
												input(type:"text",id:'group-edit-desc','class':"form-control", 'placeholder':"Beschreibung")
											}
										}
										div('class':"form-group"){
											div('class':"col-sm-offset-1 col-sm-6"){
												button('class':"btn btn-info", id:"edit-group", "Gruppe ändern");
											}
										}
									}
								}
							}
						}
						div('class':"tab-pane", id:"groupremove"){
							div('class':"row"){
								div('class':'col-md-12'){
									div('class':"form-horizontal",role:"form"){
										div('class':"form-group remove"){
											label('for':"name",'class':"col-sm-1 control-label", "Gruppe")
											div('class':"col-sm-3"){
												select(id:"group-rem-select",'class':"group-select"){
													option("")
												}
											}
											div('class':"col-sm-1"){
												button('class':"btn btn-info btn-lg refresh-group"){
													i ('class':"entypo-arrows-ccw")
												}
											}
										}
										div('class':"form-group"){
											div('class':"col-sm-offset-1 col-sm-6"){
												button('class':"btn btn-info", id:"rem-group", "Gruppe entfernen");
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
	script(src:"assets/js/group.js")
}