html.div {
	link(rel:"stylesheet", href:"assets/css/login.css")
	div (style:"width: 132px; height: 99px; margin: 0px auto; background-image: url('assets/images/logo_grey.jpg');background-size: 100% auto;") {
	}
	div(style:"width: 100%;text-align:center;"){ h3 "Management Cockpit" }
	div ('class':"container") {
		form('class':'form-signin', action:'index.groovy',method:"post", role:"form"){
			input(type:"text",name:'user', 'class':"form-control", placeholder:"Nutzername", required:"", autofocus:"")
			input(type:"password",name:'password', 'class':"form-control", placeholder:"Passwort", required:"", autofocus:"")
			if(session.false_login){
				div('class':"alert alert-danger"){
					p("Login fehlgeschlagen")
				}
			}
			label('class':"checkbox"){
				input(type:"checkbox", value:"remember-me", "angemeldet bleiben")
			}
			button('class':"btn btn-lg btn-primary btn-block", type:"submit", "anmelden")
			a(href:"javascript:;", onclick:"jQuery('#reg').modal('show');", 'class':"btn btn-xs btn-white btn-block","registrieren")
		}
		div('class':"modal fade", id:"reg"){
			div('class':"modal-dialog"){
				div('class':"modal-content"){
					div('class':"modal-header"){
						button(type:"button", 'class':"close", 'data-dismiss':"modal", 'aria-hidden':"true", "\u00D7")
						h4('class':"modal-title", "Registrierung")
					}
					div('class':"modal-body"){
						div('class':"row"){
							div('class':"col-md-6"){
								div('class':"form-group"){
									label('for':"vorname", 'class':"control-label", "Vorname")
									input(type:"text", 'class':"form-control", id:"vorname", placeholder:"Vorname")
								}
							}
							div('class':"col-md-6"){
								div('class':"form-group"){
									label('for':"name", 'class':"control-label", "Name")
									input(type:"text", 'class':"form-control", id:"name", placeholder:"Name")
								}
							}
						}
						div('class':"row"){
							div('class':"col-md-12"){
								div('class':"form-group"){
									label('for':"pos", 'class':"control-label", "Position")
									input(type:"text", 'class':"form-control", id:"pos", placeholder:"Position")
								}
							}
						}
						div('class':"row"){
							div('class':"col-md-12"){
								div('class':"form-group"){
									label('for':"mail", 'class':"control-label", "E-Mail")
									input(type:"text", 'class':"form-control", id:"mail", placeholder:"E-Mail")
								}
							}
						}
						div('class':"row", ""){ div(style:"width: 100%; height: 45px;") }
						div('class':"row"){
							div('class':"col-md-12"){
								div('class':"form-group"){
									label('for':"pw", 'class':"control-label", "Passwort")
									input(type:"password", 'class':"form-control", id:"pw", placeholder:"Passwort")
								}
							}
						}
						div('class':"row"){
							div('class':"col-md-12"){
								div('class':"form-group"){
									label('for':"rpw", 'class':"control-label", "Passwort wiederholen")
									input(type:"password", 'class':"form-control", id:"rpw", placeholder:"Passwort wiederholen")
								}
							}
						}
					}
					div('class':"modal-footer"){
						button(type:"button", 'class':"btn btn-xs btn-default", 'data-dismiss':"modal", "schlie\u00DFen");
						button(type:"button", 'class':"btn btn-info", "registrieren")
					}
				}
			}
		}
		//TODO Fehlermeldungen bei fehlgeschlagener Anmeldung
	}
}