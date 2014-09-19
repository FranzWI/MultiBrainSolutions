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
					li('class':"profile-info dropdown", style:"margin-left: 40px; margin-top: 20px;"){
						a(href:"#", 'class':"dropdown-toggle", 'data-toggle':"dropdown", "Nutzername")
					}
				}
			}
			// Logout Button
			div('class':"col-md-6 col-sm-4 clearfix hidden-xs"){
				ul('class':"list-inline links-list pull-right"){
					li{
						a(href:"index.groovy?logout=true", "Logout" ){ i('class':"entypo-logout right") }
					}
				}
			}
		}
		hr()
		def page = request.getParameter("page")
		def modul = request.getParameter("modul")
		
		i(style:"display:none;")
		if(!page && !modul){
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
		}
	}
}
