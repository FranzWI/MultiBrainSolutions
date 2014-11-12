html.div {
	html.div{
		div('class':"row"){
			div('class':"col-md-12"){ h2 "Nutzer & Gruppen" }
		}
		div('class':"row"){
			div('class':"col-md-12"){
				div('class':"tabs-vertical-env"){
	
					ul('class':"nav tabs-vertical"){
						li('class':"active"){
							a(href:'#useradd', 'data-toggle':"tab", "Nutzer hinzufügen")
						}
						li {
							a(href:'#useredit', 'data-toggle':"tab", "Nutzer ändern")
						}
						li {
							a(href:'#userremove', 'data-toggle':"tab", "Nutzer entfernen")
						}
						li{
							a(href:'#groupadd', 'data-toggle':"tab", "Gruppe hinzufügen")
						}
						li {
							a(href:'#groupedit', 'data-toggle':"tab", "Gruppe ändern")
						}
						li {
							a(href:'#groupremove', 'data-toggle':"tab", "Gruppe entfernen")
						}
					}
	
					div('class':"tab-content"){
						div('class':"tab-pane active", id:"useradd"){
							p("Test 1")
						}
						div('class':"tab-pane", id:"useredit"){ p("Test 2") }
						div('class':"tab-pane", id:"userremove"){ p("Test 3") }
						div('class':"tab-pane", id:"groupadd"){
							p("Test 4")
						}
						div('class':"tab-pane", id:"groupedit"){ p("Test 5") }
						div('class':"tab-pane", id:"groupremove"){ p("Test 6") }
					}
				}
			}
		}
	}
}