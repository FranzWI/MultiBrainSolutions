import java.awt.GraphicsConfiguration.DefaultBufferCapabilities;

import de.mbs.modules.ModulContainer
import de.mbs.modules.interfaces.Modul
import de.mbs.abstracts.db.DatabaseView
import de.mbs.abstracts.db.views.UserView
import de.mbs.abstracts.db.objects.User
import de.mbs.handler.ServiceHandler

def dbView = ServiceHandler.getDatabaseView();
def userView = dbView.getUserView();
def user = userView.get(session.user);



html.div('class':"page-container sidebar-collapsed"){
	div('class':"sidebar-menu"){
		header('class':"logo-env"){
			div('class':"logo"){
				a ('href':"index.groovy"){
					img('src':"assets/images/logo_dark.jpg", 'width':"80", style:"margin: 0px auto;")
				}
			}
			div('class':"sidebar-collapse"){
				a ('href':"#",'class':"sidebar-collapse-icon with-animation") { i('class':"entypo-menu") }
			}
			div('class':"sidebar-mobile-menu visible-xs"){
				a('href':"#",'class':"with-animation"){ i('class':"entypo-menu") }
			}
		}
		// Men�
		ul(id:"main-menu"){
			li(id:"search"){
				form (method:"get", action:""){
					input (type:"text", name:"q", 'class':"search-input", placeholder:"Suche")
					button(type:"submit"){ i('class':"entypo-search") }
				}
			}
			li{
				a(href:"index.groovy"){
					i('class':"entypo-gauge")
					span "Cockpit"
				}
			}
			//TODO mittels db nutzer bezogen entscheiden
			def modules = ModulContainer.initialise()
			for(mod in modules.getModules()){
				if(mod.isInstalled()){
					li {
						a(href:"index.groovy?modul="+mod.getModulName()){
							i('class':mod.getMenuIcon())
							span mod.getMenuName();
						}
					}
				}
			}
			//TODO nur f�r Admin
			li{
				a(href:"index.groovy?page=system"){
					i('class':"entypo-tools")
					span "System"
				}
			}
		}
	}
	div('class':"main-content"){
		div('class':"row"){
			// Nutzername
			div('class':"col-md-6 col-sm-8 clearfix"){
				ul('class':"user-info pull-left pull-none-xsm"){
					li('class':"profile-info dropdown"){
						a(href:"#", 'class':"dropdown-toggle", 'data-toggle':"dropdown"){
							img( src:"assets/images/thumb-1@2x.png", alt:"", 'class':"img-circle", width:"44")
							i(user.getFirstname()+" "+user.getLastname())
						}
						ul('class':"dropdown-menu"){
							li('class':"caret")
							li{
								a(href:"index.groovy?page=profil"){
									i('class':"entypo-user")
									println "Profil \u00E4ndern"
								}
							}
						}
					}
				}
				// Benachrichtigungen
				ul('class':"user-info pull-left pull-right-xs pull-none-xsm"){
					li('class':"notifications dropdown"){
						a(href:"#", 'class':"dropdown-toggle", 'data-toggle':"dropdown", 'data-hover':"dropdown", 'data-close-others':"true"){
							i('class':"entypo-attention")
							span('class':"badge badge-info", "2")
						}
						ul('class':"dropdown-menu"){
							// Alle Benachrichtigungen markieren
							li('class':"top"){
								p('class':"small"){
									a(href:"#", 'class':"pull-right"," Alle als gelesen markieren")
									i "Sie haben"
									strong "2"
									i "neue Benachrichtigungen"
								}
							}

							li{
								ul('class':"dropdown-menu-list scroller"){
									li('class':"unread notification-success"){
										a(href:"#"){
											i('class':"entypo-user-add pull-right")
											span('class':"line"){ strong "Neuer Nutzer ist registriert" }
											span('class':"line small", "vor 30 Sekunden")
										}
									}
									li('class':"unread notification-secondary"){
										a(href:"#"){
											i('class':"entypo-heart pull-right")
											span('class':"line"){ strong "Es wurde ein Dokument mit Ihnen geteilt" }
											span('class':"line small", "vor 2 Minuten")
										}
									}
								}
							}
							li('class':"external"){
								a(href:"index.groovy?page=notifications","Alle Benachrichtigungen zeigen")
							}
						}
					}
				}

				// Nachrichten
				ul('class':"user-info pull-left pull-right-xs pull-none-xsm"){
					li('class':"notifications dropdown"){
						a(href:"#", 'class':"dropdown-toggle", 'data-toggle':"dropdown", 'data-hover':"dropdown", 'data-close-others':"true"){
							i('class':"entypo-mail")
							span('class':"badge badge-secondary", "1")
						}
						// Dropdown bei den Nachrichten
						ul('class':"dropdown-menu"){
							li(""){
								ul('class':"dropdown-menu-list scroller", tabindex:"5002", style:"overflow: hidden; outline: none;"){
									// neue Nachricht....
									li('class':"active"){
										a(href:"#"){
											span('class':"image pull-right"){
												img(src:"assets/images/thumb-1.png", alt:"", 'class':"img-circle")
											}
											span('class':"line"){
												strong("Michael K\u00FCrbis")
												i("gestern")
											}
											span('class':"line desc small", "Betreff")
										}
									}

								}
							}
							li('class':"external"){
								a(href:"index.groovy?page=messages","Alle Nachrichten zeigen")
							}
						}

					}
				}
			}
			// Logout Button
			div('class':"col-md-6 col-sm-4 clearfix hidden-xs"){
				ul('class':"list-inline links-list pull-right"){
					li{
						a(href:"index.groovy?logout=true", "Abmelden" ){ i('class':"entypo-logout right") }
					}
				}
			}
		}
		hr()
		def page = request.getParameter("page")
		def modul = request.getParameter("modul")
		def search = request.getParameter("q")

		i(style:"display:none;")
		if(!page && !modul && !search){
			include('/WEB-INF/includes/cockpit.groovy')
		}else{
			if(page){
				//TODO nur f�r Admin
				if(page.equals("system")){
					include('/WEB-INF/includes/system.groovy')
				}
				if(page.equals("notifications")){
					include('/WEB-INF/includes/notifications.groovy')
				}
				if(page.equals("messages")){
					include('/WEB-INF/includes/messages.groovy')
				}
				if(page.equals("profil")){
					include('/WEB-INF/includes/profil.groovy')
				}
			}
			if(modul){
				def modules = ModulContainer.initialise()
				for(mod in modules.getModules()){
					if(mod.getModulName().equals(modul)){
						include('/WEB-INF/includes/'+mod.getFrontendFile());
					}
				}
			}
			if(search){
				include('/WEB-INF/includes/search.groovy')
			}
		}
	}
}
