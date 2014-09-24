html.div {
	div('class':"row"){
		div('class':"col-md-9 col-sm-7"){
			h2("Nutzer")
		}
		div('class':"col-md-3 col-sm-5"){
			form(method:"get", role:"form", 'class':"search-form-full"){
				div('class':"form-group"){
					input(type:"text",'class':"form-control", name:"search",id:"search-input",placeholder:"Suchen...")
					i('class':"entypo-search")
				}
			}
		}
	}
	// einzelner Mitgliedseintrag
	div('class':"member-entry"){
		a(href:"#", 'class':"member-img"){
			img (src:"assets/images/member.jpg", 'class':"img-rounded")
			i ('class':"entypo-forward")
		}
		div('class':"member-details"){
			h4{
				a(href:"#","Max Mustermann")
			}
			div('class':"row info-list"){
				div( 'class':"col-sm-4"){
					i('class':"entypo-briefcase", "Co-Founder at")
					a( href:"#","Complete Tech")
				}
				div( 'class':"col-sm-4"){
					i('class':"entypo-briefcase", "Co-Founder at")
					a( href:"#","Complete Tech")
				}
				div( 'class':"col-sm-4"){
					i('class':"entypo-briefcase", "Co-Founder at")
					a( href:"#","Complete Tech")
				}
				// nach 3 Einträgen mit der breite umbrechen ...
				div('class':"clear")
				div( 'class':"col-sm-4"){
					i('class':"entypo-briefcase", "Co-Founder at")
					a( href:"#","Complete Tech")
				}
				div( 'class':"col-sm-4"){
					i('class':"entypo-briefcase", "Co-Founder at")
					a( href:"#","Complete Tech")
				}
				div( 'class':"col-sm-4"){
					i('class':"entypo-briefcase", "Co-Founder at")
					a( href:"#","Complete Tech")
				}
				div('class':"clear")
			}
		}
	}
}