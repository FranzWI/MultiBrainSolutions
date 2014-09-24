import de.mbs.modules.ModulContainer
import de.mbs.modules.interfaces.Modul

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
		// Menü
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

			li{
				a(href:"index.groovy?page=module"){
					i('class':"entypo-floppy")
					span "Module"
				}
			}
			li{
				a(href:"index.groovy?page=user"){
					i('class':"entypo-user")
					span "Nutzer"
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
							i("Michael K\u00FCrbis")
						}
						ul('class':"dropdown-menu"){
							li('class':"caret")
							li{
								a(href:"#profil"){
									i('class':"entypo-user")
									println "Profil \u00E4ndern"
								}
							}
						}
					}
				}

				// Nachrichten
				ul('class':"user-info pull-left pull-right-xs pull-none-xsm"){
					li('class':"notifications dropdown"){
						a(href:"#", 'class':"dropdown-toggle", 'data-toggle':"dropdown", 'data-hover':"dropdown", 'data-close-others':"true"){
							i('class':"entypo-mail")
							span('class':"badge badge-secondary", "10")
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
								a(href:"#messages","Alle Nachrichten")
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
				if(page.equals("module")){
					include('/WEB-INF/includes/modules.groovy')
				}
				if(page.equals("user")){
					include('/WEB-INF/includes/user.groovy')
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
