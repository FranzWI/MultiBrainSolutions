html.div('class':"page-container sidebar-collapsed"){
	div('class':"sidebar-menu"){
		header('class':"logo-env"){
			div('class':"logo"){
				a ('href':"index.groovy"){
					img('src':"assets/images/logo@2x.png", 'width':"120")
				}
			}
			div('class':"sidebar-collapse"){
				a ('href':"#",'class':"sidebar-collapse-icon with-animation") { i('class':"entypo-menu") }
			}
			div('class':"sidebar-mobile-menu visible-xs"){
				a('href':"#",'class':"with-animation"){ i('class':"entypo-menu") }
			}
		}
		ul(id:"main-menu"){
			li(id:"search"){
				form (method:"get", action:""){
					input (type:"text", name:"q", 'class':"search-input", placeholder:"Sucher")
					button(type:"submit"){ i('class':"entypo-search") }
				}
			}
			li{
				a(href:"index.groovy"){
					i('class':"entypo-gauge")
					span "Dashboard"
				}
				ul{
					li{
						a(href:"index.groovy"){
							span "Dashboard 1"
							span ('class':"badge badge-success badge-roundless", "1.7")
						}
					}
					li{
						a(href:"index.groovy"){ span "Dashboard 2" }
					}
					li{
						a(href:"index.groovy"){ span "Dashboard 3" }
					}
				}
			}
		}
	}
	div('class':"main-content"){
		div('class':"row"){
			div('class':"col-md-6 col-sm-8 clearfix"){
				ul('class':"user-info pull-left pull-none-xsm"){
					li('class':"profile-info dropdown", style:"margin-left: 40px; margin-top: 20px;"){
						a(href:"#", 'class':"dropdown-toggle", 'data-toggle':"dropdown", "Nutzername")
					}
				}
			}
			div('class':"col-md-6 col-sm-4 clearfix hidden-xs"){
				ul('class':"list-inline links-list pull-right"){
					li{
						a(href:"index.groovy?logout=true", "Logout" ){ i('class':"entypo-logout right") }
					}
				}
			}
		}
		hr()
		br()
		p "We have collapsed left sidebar on this page by adding <code>sidebar-collapsed</code> to <strong>.page-container</strong> element."
	}
}
