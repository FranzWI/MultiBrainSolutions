
def search = request.getParameter("q")

html.div{
	section('class':"search-results-env"){
		div('class':"row"){
			div('class':"col-md-12"){
				ul('class':"nav nav-tabs right-aligned"){
					li('class':"tab-title pull-left"){
						div('class':"search-string"){
							p ("10 Ergebnisse gefunden fuer: "){
								strong("\"${search}\"")
							}
						}
					}
					li('class':"active"){
						a(href:"#pages", "Alle"){
							strong('class':"disabled-text"){ "(31)" }
						}
					}
					li(""){
						a(href:"#user", "Anwender"){
							strong('class':"disabled-text"){ "(31)" }
						}
					}
					li(""){
						a(href:"#messages", "Nachrichten"){
							strong('class':"disabled-text"){ "(31)" }
						}
					}
					//TODO an die Module anpassen
					li(""){
						a(href:"#Documents", "Dokumente"){
							strong('class':"disabled-text"){ "(29)" }
						}
					}
					li(""){
						a(href:"#RSS","RSS Feeds"){
							strong('class':"disabled-text"){ "(2)" }
						}
					}
				}
				// Suchformular
				form(method:"get", 'class':"search-bar", action:"", enctype:"application/x-www-form-urlencoded"){
					div('class':"input-group"){
						input(type:"text",'class':"form-control input-lg", name:"q", value:"${search}", placeholder:"Such nach etwas ...")
						div('class':"input-group-btn"){
							button(type:"submit", 'class':"btn btn-lg btn-primary btn-icon", "Suchen"){
								i('class':"entypo-search")
							}
						}
					}
				}
				// Suchergebnisse
				div('class':"search-results-panes"){
					div('class':"search-results-pane active", id:"pages"){
						ul('class':"search-results"){
							// Ein Suchergebniss
							li('class':"search-result"){
								div('class':"sr-inner"){
									h4(''){
										a(href:"#", "Test Ergebniss")
									}
									p("Toller Text des Ergebisses")
									a(href:"#","Weiterer Toller link zum ergebniss")
								}
							}
						}
					}
					
					div('class':"search-results-pane", id:"user"){
					
					}
					
					div('class':"search-results-pane", id:"messages"){
					
					}
					
					// für jedes module hier dokumente
					div('class':"search-results-pane", id:"Documents"){
					
					}
					// für jedes module hier RSS
					div('class':"search-results-pane", id:"RSS"){
					
					}
				}
			}
		}
	}
}