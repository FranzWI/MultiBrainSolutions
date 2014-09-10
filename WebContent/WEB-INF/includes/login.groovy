html.div {
	link(rel:"stylesheet", href:"assets/css/login.css")
	div (style:"width: 132px; height: 99px; margin: 0px auto; background-image: url('assets/images/logo_grey.jpg');background-size: 100% auto;") {
		
	}
	div(style:"width: 100%;text-align:center;"){
		h3 "Management Cockpit"
	}
	div ('class':"container") {
		form('class':'form-signin', action:'index.groovy',method:"post", role:"form"){
			input(type:"text",name:'user', 'class':"form-control", placeholder:"Nutzername", required:"", autofocus:"")
			input(type:"password",name:'password', 'class':"form-control", placeholder:"Passwort", required:"", autofocus:"")
			label('class':"checkbox"){
				input(type:"checkbox", value:"remember-me", "angemeldet bleiben")
			}
			button('class':"btn btn-lg btn-primary btn-block", type:"submit", "anmelden")
		}
		//TODO Fehlermeldungen bei fehlgeschlagener Anmeldung
	}
}