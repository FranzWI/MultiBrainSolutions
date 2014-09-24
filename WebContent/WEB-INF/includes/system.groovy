html.div {
	div('class':"row"){ 
		div('class':"col-md-12"){
			h2 "System"
		} 
	}
	div('class':"row"){
		div('class':"col-md-12"){
			ul('class':"nav nav-tabs bordered"){
				li('class':"active"){
					a(href:"#status", 'data-toggle':"tab"){
						span('class':"visible-xs"){ i('class':"entypo-tools") }
						span('class':"hidden-xs", "System Status")
					}
				}
				li{
					a(href:"#user", 'data-toggle':"tab"){
						span('class':"visible-xs"){ i('class':"entypo-user") }
						span('class':"hidden-xs", "Nutzer")
					}
				}
				li{
					a(href:"#modules", 'data-toggle':"tab"){
						span('class':"visible-xs"){ i('class':"entypo-floppy") }
						span('class':"hidden-xs", "Module")
					}
				}
			}
			div('class':"tab-content"){
				div('class':"tab-pane active", id:"status"){
					span ""
					include('/WEB-INF/includes/system/status.groovy')
				}
				div('class':"tab-pane", id:"user"){
					span ""
					include('/WEB-INF/includes/system/user.groovy')
				}
				div('class':"tab-pane", id:"modules"){
					span ""
					include('/WEB-INF/includes/system/modules.groovy')
				}
			}
		}
	}
}