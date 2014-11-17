html.div {
	html.div{
		div('class':"row"){
			div('class':"col-md-12"){ h2 "Nutzer" }
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
					}

					div('class':"tab-content"){
						div('class':"tab-pane active", id:"useradd"){ p("Test 1") }
						div('class':"tab-pane", id:"useredit"){ p("Test 2") }
						div('class':"tab-pane", id:"userremove"){ p("Test 3") }
					}
				}
			}
		}
	}
}