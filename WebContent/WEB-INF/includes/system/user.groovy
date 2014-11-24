html.div {
	html.div{
		div('class':"row"){
			div('class':"col-md-12"){ h2 "Nutzer" }
		}
		div('class':"row"){
			div('class':"col-md-12"){
				div('class':"tabs-vertical-env"){

					ul('class':"nav tabs-vertical"){
						li('class':"active"){
							a(href:'#useradd', 'data-toggle':"tab", "Nutzer hinzufügen")
						}
						li {
							a(href:'#useredit', 'data-toggle':"tab", "Nutzer ändern")
						}
						li {
							a(href:'#pwreset', 'data-toggle':"tab", "Passwort zurücksetzen")
						}
						li {
							a(href:'#userremove', 'data-toggle':"tab", "Nutzer entfernen")
						}
					}

					div('class':"tab-content"){
						div('class':"tab-pane active", id:"useradd"){
							div('class':"row"){
								div('class':'col-md-12'){
									div('class':"form-horizontal",role:"form"){
										div('class':"form-group"){
											label('for':"username",'class':"col-sm-1 control-label", "Anmeldename")
											div('class':"col-sm-6"){
												input(type:"text",name:'username','class':"form-control user-add", 'placeholder':"Anmeldename")
											}
										}
										div('class':"form-group"){
											label('for':"firstname",'class':"col-sm-1 control-label", "Vorname")
											div('class':"col-sm-6"){
												input(type:"text",name:'firstname','class':"form-control user-add", 'placeholder':"Vorname")
											}
										}
										div('class':"form-group"){
											label('for':"lastname",'class':"col-sm-1 control-label", "Nachname")
											div('class':"col-sm-6"){
												input(type:"text",name:'lastname','class':"form-control user-add", 'placeholder':"Nachname")
											}
										}
										div('class':"form-group"){
											label('for':"email",'class':"col-sm-1 control-label", "Email")
											div('class':"col-sm-6"){
												input(type:"text",name:'email','class':"form-control user-add", 'placeholder':"Email")
											}
										}
										div('class':"form-group"){
											label('for':"activ",'class':"col-sm-1 control-label", "Aktiv")
											div('class':"col-sm-1"){
												input(type:"checkbox",name:'active','class':"form-control user-add")
											}
										}
										div('class':"form-group"){
											label('for':"groups",'class':"col-sm-1 control-label", "Gruppen")
											div('class':"col-sm-6"){
												select(id:'user-add-groups',name:"groups",'class':"form-control user-add-select", 'multiple':""){ option('') }
											}
										}
										div('class':"form-group"){
											div('class':"col-sm-offset-1 col-sm-6"){
												button('class':"btn btn-info", id:"add-user", "Nutzer anlegen");
											}
										}
									}
								}
							}
						}
						div('class':"tab-pane", id:"useredit"){
							div('class':"row"){
								div('class':'col-md-12'){
									div('class':"form-horizontal",role:"form"){
										div('class':"form-group"){
											label('for':"groups",'class':"col-sm-1 control-label", "Nutzer")
											div('class':"col-sm-6"){
												select(id:'select-user-edit','class':"form-control"){ option('') }
											}
										}
										div('class':"form-group"){
											label('for':"username",'class':"col-sm-1 control-label", "Anmeldename")
											div('class':"col-sm-6"){
												input(type:"text",name:'username','class':"form-control user-edit", 'placeholder':"Anmeldename")
											}
										}
										div('class':"form-group"){
											label('for':"firstname",'class':"col-sm-1 control-label", "Vorname")
											div('class':"col-sm-6"){
												input(type:"text",name:'firstname','class':"form-control user-edit", 'placeholder':"Vorname")
											}
										}
										div('class':"form-group"){
											label('for':"lastname",'class':"col-sm-1 control-label", "Nachname")
											div('class':"col-sm-6"){
												input(type:"text",name:'lastname','class':"form-control user-edit", 'placeholder':"Nachname")
											}
										}
										div('class':"form-group"){
											label('for':"email",'class':"col-sm-1 control-label", "Email")
											div('class':"col-sm-6"){
												input(type:"text",name:'email','class':"form-control user-edit", 'placeholder':"Email")
											}
										}
										div('class':"form-group"){
											label('for':"activ",'class':"col-sm-1 control-label", "Aktiv")
											div('class':"col-sm-1"){
												input(type:"checkbox",name:'active','class':"form-control user-edit")
											}
										}
										div('class':"form-group"){
											label('for':"apikey",'class':"col-sm-1 control-label", "Apikey")
											div('class':"col-sm-5"){
												input(type:"text",id:"user-apikey",name:'apikey','class':"form-control user-edit", 'placeholder':"Apikey", readonly:"")
											}
											div('class':"col-sm-1"){
												button('class':"btn btn-info", id:"edit-refresh-api"){ i('class':"entypo-arrows-ccw") }
											}
										}
										div('class':"form-group"){
											label('for':"groups",'class':"col-sm-1 control-label", "Gruppen")
											div('class':"col-sm-6"){
												select(id:'user-edit-groups',name:"groups",'class':"form-control user-edit-select", 'multiple':""){ option('') }
											}
										}
										div('class':"form-group"){
											div('class':"col-sm-offset-1 col-sm-6"){
												button('class':"btn btn-info", id:"edit-user", "Nutzer ändern");
											}
										}
									}
								}
							}
						}
						div('class':"tab-pane", id:"pwreset"){
							div('class':"row"){
								div('class':'col-md-12'){
									div('class':"form-horizontal",role:"form"){
										div('class':"form-group"){
											label('for':"groups",'class':"col-sm-1 control-label", "Nutzer")
											div('class':"col-sm-6"){
												select(id:'select-user-pwreset','class':"form-control"){ option('') }
											}
										}
										div('class':"form-group"){
											div('class':"col-sm-offset-1 col-sm-6"){
												button('class':"btn btn-info", id:"pwreset-user", "Passwort zurücksetzen");
											}
										}
									}
								}
							}
						}
						div('class':"tab-pane", id:"userremove"){
							div('class':"row"){
								div('class':'col-md-12'){
									div('class':"form-horizontal",role:"form"){
										div('class':"form-group"){
											label('for':"groups",'class':"col-sm-1 control-label", "Nutzer")
											div('class':"col-sm-6"){
												select(id:'select-user-rem','class':"form-control"){ option('') }
											}
										}
										div('class':"form-group"){
											div('class':"col-sm-offset-1 col-sm-6"){
												button('class':"btn btn-info", id:"rem-user", "Nutzer löschen");
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
	script(src:"assets/js/user.js")
}