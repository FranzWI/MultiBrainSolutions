html.div{
	div('class':"row"){
		div('class':"col-md-12"){ h2 "Portlets" }
	}
	div('class':"row"){
		div('class':"col-md-12"){
			div('class':"tabs-vertical-env"){

				ul('class':"nav tabs-vertical"){
					li('class':"active"){
						a(href:'#add', 'data-toggle':"tab", "hinzufügen")
					}
					li {
						a(href:'#edit', 'data-toggle':"tab", "ändern")
					}
					li {
						a(href:'#remove', 'data-toggle':"tab", "entfernen")
					}
				}

				div('class':"tab-content"){
					div('class':"tab-pane active", id:"add"){
						div('class':"row"){
							div('class':'col-md-12'){
								div('class':"form-horizontal",role:"form"){
									div('class':"form-group"){
										label('for':"name",'class':"col-sm-1 control-label", "Name")
										div('class':"col-sm-6"){
											input(type:"text",id:'name','class':"form-control", 'placeholder':"Name")
										}
									}
									div('class':"form-group"){
										label('for':"desc",'class':"col-sm-1 control-label", "Beschreibung")
										div('class':"col-sm-6"){
											input(type:"text",id:'desc','class':"form-control", 'placeholder':"Beschreibung")
										}
									}
									div('class':"form-group"){
										label('for':"path",'class':"col-sm-1 control-label", "Pfad")
										div('class':"col-sm-6"){
											input(type:"text",id:'path','class':"form-control", 'placeholder':"Pfad")
										}
									}
									div('class':"form-group"){
										label('for':"groups",'class':"col-sm-1 control-label", "Gruppen")
										div('class':"col-sm-6"){
											input(type:"text",id:'groups','class':"form-control", 'placeholder':"Gruppen")
										}
									}
									div('class':"form-group"){
										label('for':"xs",'class':"col-sm-1 control-label", "XS")
										div('class':"col-sm-6"){
											input(type:"number",id:'xs','class':"form-control", 'placeholder':"XS")
										}
									}
									div('class':"form-group"){
										label('for':"sm",'class':"col-sm-1 control-label", "SM")
										div('class':"col-sm-6"){
											input(type:"number",id:'sm','class':"form-control", 'placeholder':"SM")
										}
									}
									div('class':"form-group"){
										label('for':"md",'class':"col-sm-1 control-label", "MD")
										div('class':"col-sm-6"){
											input(type:"number",id:'md','class':"form-control", 'placeholder':"MD")
										}
									}
									div('class':"form-group"){
										label('for':"lg",'class':"col-sm-1 control-label", "LG")
										div('class':"col-sm-6"){
											input(type:"number",id:'lg','class':"form-control", 'placeholder':"LG")
										}
									}
									div('class':"form-group"){
										div('class':"col-sm-offset-1 col-sm-6"){
											button('class':"btn btn-info", id:"add-portlet", "Portlet anlegen");
										}
									}
								}
							}
						}
					}
					div('class':"tab-pane", id:"edit"){
						div('class':"row"){
							div('class':'col-md-12'){
								div('class':"form-horizontal",role:"form"){
									div('class':"form-group"){
										label('for':"name",'class':"col-sm-1 control-label", "Name")
										div('class':"col-sm-6"){
											input(type:"text",id:'name','class':"form-control", 'placeholder':"Name")
										}
									}
									div('class':"form-group"){
										label('for':"desc",'class':"col-sm-1 control-label", "Beschreibung")
										div('class':"col-sm-6"){
											input(type:"text",id:'desc','class':"form-control", 'placeholder':"Beschreibung")
										}
									}
									div('class':"form-group"){
										label('for':"path",'class':"col-sm-1 control-label", "Pfad")
										div('class':"col-sm-6"){
											input(type:"text",id:'path','class':"form-control", 'placeholder':"Pfad")
										}
									}
									div('class':"form-group"){
										label('for':"groups",'class':"col-sm-1 control-label", "Gruppen")
										div('class':"col-sm-6"){
											input(type:"text",id:'groups','class':"form-control", 'placeholder':"Gruppen")
										}
									}
									div('class':"form-group"){
										label('for':"xs",'class':"col-sm-1 control-label", "XS")
										div('class':"col-sm-6"){
											input(type:"number",id:'xs','class':"form-control", 'placeholder':"XS")
										}
									}
									div('class':"form-group"){
										label('for':"sm",'class':"col-sm-1 control-label", "SM")
										div('class':"col-sm-6"){
											input(type:"number",id:'sm','class':"form-control", 'placeholder':"SM")
										}
									}
									div('class':"form-group"){
										label('for':"md",'class':"col-sm-1 control-label", "MD")
										div('class':"col-sm-6"){
											input(type:"number",id:'md','class':"form-control", 'placeholder':"MD")
										}
									}
									div('class':"form-group"){
										label('for':"lg",'class':"col-sm-1 control-label", "LG")
										div('class':"col-sm-6"){
											input(type:"number",id:'lg','class':"form-control", 'placeholder':"LG")
										}
									}
									div('class':"form-group"){
										div('class':"col-sm-offset-1 col-sm-6"){
											button('class':"btn btn-info", id:"add-portlet", "Portlet anlegen");
										}
									}
								}
							}
						}
					}
					div('class':"tab-pane", id:"remove"){ p("Test 3") }
				}
			}
		}
	}
}