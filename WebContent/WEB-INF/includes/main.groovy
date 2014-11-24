import java.awt.GraphicsConfiguration.DefaultBufferCapabilities;

import de.mbs.modules.ModulContainer
import de.mbs.modules.interfaces.Modul
import de.mbs.abstracts.db.DatabaseView
import de.mbs.abstracts.db.views.UserView
import de.mbs.abstracts.db.views.GroupView
import de.mbs.abstracts.db.views.MessageView
import de.mbs.abstracts.db.views.NotificationView
import de.mbs.abstracts.db.objects.User
import de.mbs.abstracts.db.objects.Group
import de.mbs.abstracts.db.objects.Message
import de.mbs.abstracts.db.objects.Notification
import de.mbs.frontend.FrontendHelper
import de.mbs.handler.ServiceHandler

def dbView = ServiceHandler.getDatabaseView();
def userView = dbView.getUserView();
def groupView = dbView.getGroupView();
def user = userView.get(session.user);
def messagesView = dbView.getMessageView();



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
			// Ist der Nutzer Admin?
			if(user.getMembership().contains(groupView.getAdminGroupId())){
				li{
					a(href:"index.groovy?page=system"){
						i('class':"entypo-tools")
						span "System"
					}
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
							img( 'data-src':"holder.js/45x45/"+FrontendHelper.getColors(user)+"/text:"+user.getFirstname().substring(0, 1), alt:"", 'class':"img-circle", width:"44")
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
						NotificationView notificationView = dbView.getNotificationView();
						Vector<Notification> notifications = notificationView.getNotificationsForUser(session.user);
						if(notifications != null){
							a(href:"#", 'class':"dropdown-toggle", 'data-toggle':"dropdown", 'data-hover':"dropdown", 'data-close-others':"true"){
								i('class':"entypo-attention")
								if(notifications.size() > 0)
									span('class':"badge badge-info", notifications.size())
							}
							if(notifications.size() > 0){
								ul('class':"dropdown-menu"){
									// Alle Benachrichtigungen markieren
									li('class':"top"){
										p('class':"small"){
											a(href:"#",id:"read-all-notifications", 'class':"pull-right"," Alle als gelesen markieren")
											i "Sie haben"
											strong (""+notifications.size())
											i "neue Benachrichtigungen"
										}
									}

									li{
										ul('class':"dropdown-menu-list scroller"){
											for(Notification n : notifications){
												//TODO typen unterscheiden
												li('class':"unread notification-success"){
													a(href:"#"){
														i('class':n.getIcon()+" pull-right")
														span('class':"line"){ strong (n.getSubject())}
														span('class':"line small"){
															time('class':"timeago", datetime:FrontendHelper.getTimeagoString(n.getReleaseTimestamp()));
														}
													}
												}
											}
											/*
											 li('class':"unread notification-secondary"){
											 a(href:"#"){
											 i('class':"entypo-heart pull-right")
											 span('class':"line"){ strong "Es wurde ein Dokument mit Ihnen geteilt" }
											 span('class':"line small", "vor 2 Minuten")
											 }
											 }
											 */
										}
									}
									//li('class':"external"){
									//	a(href:"index.groovy?page=notifications","Alle Benachrichtigungen zeigen")
									//}
								}
							}
						}
					}
				}

				Vector<Message> unreadMessages = messagesView.getUnreadMessagesForUser(session.user);
				// Nachrichten
				ul('class':"user-info pull-left pull-right-xs pull-none-xsm"){
					li('class':"notifications dropdown"){
						a(href:"#", 'class':"dropdown-toggle", 'data-toggle':"dropdown", 'data-hover':"dropdown", 'data-close-others':"true"){
							i('class':"entypo-mail")
							if(unreadMessages && unreadMessages.size() > 0){
								span('class':"badge badge-secondary", unreadMessages.size())
							}
						}
						// Dropdown bei den Nachrichten
						ul('class':"dropdown-menu"){
							if(unreadMessages && unreadMessages.size() > 0){
								li(""){
									ul('class':"dropdown-menu-list scroller", tabindex:"5002", style:"overflow: hidden; outline: none;"){
										for(Message m: unreadMessages){
											// neue Nachricht....
											li('class':"active"){
												a(href:"#"){
													User muser = userView.get(m.getFromUser());
													span('class':"image pull-right"){
														img( 'data-src':"holder.js/45x45/"+FrontendHelper.getColors(muser)+"/text:"+muser.getFirstname().substring(0, 1), alt:"", 'class':"img-circle", width:"44")
													}
													span('class':"line"){
														strong(m.getTopic())
														i(muser.getFirstname()+" "+muser.getLastname())
													}
													span('class':"line desc small"){
														time('class':"timeago", datetime:FrontendHelper.getTimeagoString(m.getSendDate()));
													}
												}
											}
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
	script(src:'assets/js/notifications.js')
	def modules = ModulContainer.initialise()
	for(mod in modules.getModules()){
		if(mod.isInstalled() && mod.getJavascripts()){
			for(String jscript:mod.getJavascripts()){
				script(src:jscript)
			}
		}
	}
	script('') { println """
			jQuery(document).ready(function() {
			  jQuery("time.timeago").timeago();
			});
		""" }
}
