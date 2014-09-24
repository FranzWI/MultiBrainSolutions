html.div {
	div('class':"row"){
		div('class':"col-md-12"){
			h2(style:"float:left;display: inline;", "Cockpit")
			div('class':"btn-group"){
				button('class':"btn btn-default tooltip-primary dropdown-toggle", 'data-toggle':"dropdown", style:"float:left;display: inline;margin-top: 15px; margin-left: 15px;", "+")
				ul('class':"dropdown-menu dropdown-default", role:"menu"){
					li{
						a(href:"#portlet1", "Portlet 1")
					}
					li{
						a(href:"#portlet2", "Portlet 2")
					}
				}
			}
		}
	}
	br()
}