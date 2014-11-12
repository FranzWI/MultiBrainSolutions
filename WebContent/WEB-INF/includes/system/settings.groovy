import de.mbs.abstracts.db.DatabaseView
import de.mbs.abstracts.db.views.SettingsView
import de.mbs.abstracts.db.objects.Settings
import de.mbs.handler.ServiceHandler

import java.util.Properties

def dbView = ServiceHandler.getDatabaseView();
def settingsView = dbView.getSettingsView();
def settings = settingsView.getAll().get(0);

html.div {
	div('class':"row"){
		div('class':"col-md-12"){ h2 "Einstellungen" }
	}

	if(settings){
		String source = "";
		Properties prop = null;
		div('class':"row"){
			div('class':"col-md-12"){ h3 "Datenbank" }
			if(settings.getDbProperties().size() == 0){
				div('class':"col-md-12"){
					div('class':"alert alert-info"){ p("Keine Einstellung für die DB möglich"); }
				}
			}else{
				source = "db";
				prop = settings.getDbProperties();
				div('class':'col-md-12'){
					div('class':"form-horizontal",role:"form"){
						for(String key: prop.stringPropertyNames()){
							String labelText = key.replaceAll("_"," ").trim();
							String type = "text";
							String value = prop.getProperty(key);
							boolean isPassword = labelText.toLowerCase().startsWith("pw");
							boolean isNumber = labelText.toLowerCase().startsWith("number");
							boolean isBool = labelText.toLowerCase().startsWith("bool");
							if(isPassword){
								type = "password";
								value= "";
								labelText=labelText.replaceFirst("PW","").trim();
							}
							if(isNumber){
								type = "number";
								labelText=labelText.replaceFirst("NUMBER","").trim();
							}
							if(isBool){
								type = "checkbox";
								labelText=labelText.replaceFirst("BOOL","").trim();
							}

							div('class':"form-group"){
								label('for':key,'class':"col-sm-1 control-label", labelText)
								div('class':"col-sm-6"){
									input('type':type,'id':key,'data-propertie-key':key, 'class':"form-control "+source, 'value':value, 'placeholder':labelText)
								}
							}
						}
						div('class':"form-group"){
							div('class':"col-sm-offset-1 col-sm-6"){
								button('class':"btn btn-info", id:"save-db", "Änderung speichern");
							}
						}
					}
				}
			}
		}
		div('class':"row"){
			div('class':"col-md-12"){ h3 "Mail" }
			if(settings.getMailProperties().size() == 0){
				div('class':"col-md-12"){
					div('class':"alert alert-info"){ p("Keine Einstellungen für den Mail-Dienst möglich"); }
				}
			}else{
				source = "mail";
				prop = settings.getMailProperties();
				div('class':'col-md-12'){
					div('class':"form-horizontal",role:"form"){
						for(String key: prop.stringPropertyNames()){
							String labelText = key.replaceAll("_"," ").trim();
							String type = "text";
							String value = prop.getProperty(key);
							boolean isPassword = labelText.toLowerCase().startsWith("pw");
							boolean isNumber = labelText.toLowerCase().startsWith("number");
							boolean isBool = labelText.toLowerCase().startsWith("bool");
							if(isPassword){
								type = "password";
								value= "";
								labelText=labelText.replaceFirst("PW","").trim();
							}
							if(isNumber){
								type = "number";
								labelText=labelText.replaceFirst("NUMBER","").trim();
							}
							if(isBool){
								type = "checkbox";
								labelText=labelText.replaceFirst("BOOL","").trim();
							}

							div('class':"form-group"){
								label('for':key,'class':"col-sm-1 control-label", labelText)
								div('class':"col-sm-6"){
									input('type':type,'id':key,'data-propertie-key':key, 'class':"form-control "+source, 'value':value, 'placeholder':labelText)
								}
							}
						}
						div('class':"form-group"){
							div('class':"col-sm-offset-1 col-sm-6"){
								button('class':"btn btn-info", id:"save-mail", "Änderung speichern");
							}
						}
					}
				}
			}
		}
		div('class':"row"){
			div('class':"col-md-12"){ h3 "Proxy" }
			if(settings.getProxyProperties().size() == 0){
				div('class':"col-md-12"){
					div('class':"alert alert-info"){ p("Keine Einstellung für die Proxy-Konfiguration möglich"); }
				}
			}else{
				source = "proxy";
				prop = settings.getProxyProperties();
				div('class':'col-md-12'){
					div('class':"form-horizontal",role:"form"){
						for(String key: prop.stringPropertyNames()){
							String labelText = key.replaceAll("_"," ").trim();
							String type = "text";
							String value = prop.getProperty(key);
							boolean isPassword = labelText.toLowerCase().startsWith("pw");
							boolean isNumber = labelText.toLowerCase().startsWith("number");
							boolean isBool = labelText.toLowerCase().startsWith("bool");
							if(isPassword){
								type = "password";
								value= "";
								labelText=labelText.replaceFirst("PW","").trim();
							}
							if(isNumber){
								type = "number";
								labelText=labelText.replaceFirst("NUMBER","").trim();
							}
							if(isBool){
								type = "checkbox";
								labelText=labelText.replaceFirst("BOOL","").trim();
							}

							div('class':"form-group"){
								label('for':key,'class':"col-sm-1 control-label", labelText)
								div('class':"col-sm-6"){
									input('type':type,'id':key,'data-propertie-key':key, 'class':"form-control "+source, 'value':value, 'placeholder':labelText)
								}
							}
						}
						div('class':"form-group"){
							div('class':"col-sm-offset-1 col-sm-6"){
								button('class':"btn btn-info", id:"save-proxy", "Änderung speichern");
							}
						}
					}
				}
			}
		}
	}else{
		div('class':"row"){
			div('class':"col-md-12"){
				div('class':"alert alert-danger"){ p("Fehler beim laden der Einstellungen") }
			}
		}
	}
	script(src:"assets/js/settings.js")
}