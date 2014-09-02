html.div {
	link(rel:"stylesheet", href:"assets/css/login.css")
	div ('class':"container") {
		form('class':'form-signin', action:'index.groovy',method:"post", role:"form"){
			h2('class':"form-signin-heading","Anmeldung")
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