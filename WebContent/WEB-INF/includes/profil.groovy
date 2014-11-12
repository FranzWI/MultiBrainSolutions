import de.mbs.abstracts.db.DatabaseView
import de.mbs.abstracts.db.views.UserView
import de.mbs.abstracts.db.objects.User
import de.mbs.handler.ServiceHandler

def dbView = ServiceHandler.getDatabaseView();
def userView = dbView.getUserView();
def user = userView.get(session.user);

//TODO REST Service Daten speichern
html.div{
	div('class':"row"){
		div('class':"col-md-12"){ h2 "Profil" }
	}
	div('class':"row"){
		div('class':"col-md-6") {
			div('class':"panel panel-default"){
				div('class':"panel-body"){
					div('class':"row"){
						div('class':"col-xs-12"){
							div('class':"form-group"){
								label('for':"username", 'class':"control-label", "Anmeldename")
								input(type:"text", 'class':"form-control", id:"username",value: user.getUsername(), placeholder:"Anmeldename")
							}
						}
					}
					div('class':"row"){
						div('class':"col-xs-6"){
							div('class':"form-group"){
								label('for':"vorname", 'class':"control-label", "Vorname")
								input(type:"text", 'class':"form-control", id:"vorname", value: user.getFirstname(), placeholder:"Vorname")
							}
						}
						div('class':"col-xs-6"){
							div('class':"form-group"){
								label('for':"name", 'class':"control-label", "Name")
								input(type:"text", 'class':"form-control", id:"name", value: user.getLastname(), placeholder:"Name")
							}
						}
					}
					div('class':"row"){
						div('class':"col-xs-12"){
							div('class':"form-group"){
								label('for':"apikey", 'class':"control-label", "API Key")
								input(type:"text", 'class':"form-control", id:"apikey",value: user.getApikey(), placeholder:"API Key", 'readonly':"")
							}
						}
					}
					div('class':"row"){
						div('class':"col-xs-12"){
							div('class':"form-group"){
								label('for':"mail", 'class':"control-label", "E-Mail")
								input(type:"text", 'class':"form-control", id:"mail",value: user.getEmail(), placeholder:"E-Mail")
							}
						}
					}
					div('class':"row", ""){ div(style:"width: 100%; height: 45px;") }
					div('class':"row"){
						div('class':"col-xs-12"){
							div('class':"form-group"){
								label('for':"pw", 'class':"control-label", "aktuelles Passwort")
								input(type:"password", 'class':"form-control", id:"pw", placeholder:"Passwort")
							}
						}
					}
					div('class':"row"){
						div('class':"col-xs-12"){
							div('class':"form-group"){
								label('for':"npw", 'class':"control-label", "neues Passwort")
								input(type:"password", 'class':"form-control", id:"npw", placeholder:"neues Passwort")
							}
						}
					}
					div('class':"row"){
						div('class':"col-xs-12"){
							div('class':"form-group"){
								label('for':"rpw", 'class':"control-label", "neues Passwort wiederholen")
								input(type:"password", 'class':"form-control", id:"rpw", placeholder:"neues Passwort wiederholen")
							}
						}
					}
					div('class':"row"){
						div('class':"col-xs-12"){
							button(type:"button", 'class':"btn btn-block btn-info", "ändern")
						}
					}
				}
			}
		}
	}
}