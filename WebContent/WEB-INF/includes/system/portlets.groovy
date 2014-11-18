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
											input(type:"text",name:'name','class':"form-control portlet-add", 'placeholder':"Name")
										}
									}
									div('class':"form-group"){
										label('for':"desc",'class':"col-sm-1 control-label", "Beschreibung")
										div('class':"col-sm-6"){
											input(type:"text",name:'description','class':"form-control portlet-add", 'placeholder':"Beschreibung")
										}
									}
									div('class':"form-group"){
										label('for':"path",'class':"col-sm-1 control-label", "Pfad")
										div('class':"col-sm-6"){
											input(type:"text",name:'path','class':"form-control portlet-add", 'placeholder':"Pfad")
										}
									}
									div('class':"form-group"){
										label('for':"groups",'class':"col-sm-1 control-label", "Gruppen")
										div('class':"col-sm-6"){
											select(name:"groups",'class':"form-control portlet-group portlet-add-select", 'multiple':""){
												option('')
											}
										}
									}
									div('class':"form-group"){
										label('for':"xs",'class':"col-sm-1 control-label", "XS")
										div('class':"col-sm-6"){
											input(type:"number",name:'xs','class':"form-control portlet-add", 'placeholder':"XS")
										}
									}
									div('class':"form-group"){
										label('for':"sm",'class':"col-sm-1 control-label", "SM")
										div('class':"col-sm-6"){
											input(type:"number",name:'sm','class':"form-control portlet-add", 'placeholder':"SM")
										}
									}
									div('class':"form-group"){
										label('for':"md",'class':"col-sm-1 control-label", "MD")
										div('class':"col-sm-6"){
											input(type:"number",name:'md','class':"form-control portlet-add", 'placeholder':"MD")
										}
									}
									div('class':"form-group"){
										label('for':"lg",'class':"col-sm-1 control-label", "LG")
										div('class':"col-sm-6"){
											input(type:"number",name:'lg','class':"form-control portlet-add", 'placeholder':"LG")
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
										label('for':"name",'class':"col-sm-1 control-label", "Portlet")
										div('class':"col-sm-6"){
											select(id:"portlet-select-edit",'class':"portlet-select"){ option('') }
										}
									}
									div('class':"form-group"){
										label('for':"name",'class':"col-sm-1 control-label", "Name")
										div('class':"col-sm-6"){
											input(type:"text",id:'portlet-edit-name','class':"form-control", 'placeholder':"Name")
										}
									}
									div('class':"form-group"){
										label('for':"desc",'class':"col-sm-1 control-label", "Beschreibung")
										div('class':"col-sm-6"){
											input(type:"text",id:'portlet-edit-desc','class':"form-control", 'placeholder':"Beschreibung")
										}
									}
									div('class':"form-group"){
										label('for':"path",'class':"col-sm-1 control-label", "Pfad")
										div('class':"col-sm-6"){
											input(type:"text",id:'portlet-edit-path','class':"form-control", 'placeholder':"Pfad")
										}
									}
									div('class':"form-group"){
										label('for':"groups",'class':"col-sm-1 control-label", "Gruppen")
										div('class':"col-sm-6"){
											select(id:'portlet-edit-groups','class':"form-control portlet-group",'multiple':""){
												option('')
											}
										}
									}
									div('class':"form-group"){
										label('for':"xs",'class':"col-sm-1 control-label", "XS")
										div('class':"col-sm-6"){
											input(type:"number",id:'portlet-edit-xs','class':"form-control", 'placeholder':"XS")
										}
									}
									div('class':"form-group"){
										label('for':"sm",'class':"col-sm-1 control-label", "SM")
										div('class':"col-sm-6"){
											input(type:"number",id:'portlet-edit-sm','class':"form-control", 'placeholder':"SM")
										}
									}
									div('class':"form-group"){
										label('for':"md",'class':"col-sm-1 control-label", "MD")
										div('class':"col-sm-6"){
											input(type:"number",id:'portlet-edit-md','class':"form-control", 'placeholder':"MD")
										}
									}
									div('class':"form-group"){
										label('for':"lg",'class':"col-sm-1 control-label", "LG")
										div('class':"col-sm-6"){
											input(type:"number",id:'portlet-edit-lg','class':"form-control", 'placeholder':"LG")
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
					div('class':"tab-pane", id:"remove"){
						div('class':"row"){
							div('class':'col-md-12'){
								div('class':"form-horizontal",role:"form"){
									div('class':"form-group"){
										label('for':"name",'class':"col-sm-1 control-label", "Portlet")
										div('class':"col-sm-6"){
											select(id:"portlet-select-rem",'class':"portlet-select"){ option('') }
										}
									}
									div('class':"form-group"){
										div('class':"col-sm-offset-1 col-sm-6"){
											button('class':"btn btn-info", id:"rem-portlet", "Portlet entfernen");
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
	script(src:"assets/js/portlet.js")
}