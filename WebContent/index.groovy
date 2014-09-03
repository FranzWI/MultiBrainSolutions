if (!session) {
	session = request.getSession(true)
}

//logout
if(session && request.getParameter('logout')){
	request.getSession(true).invalidate();
	session = request.getSession(true)
}

if (!session.counter) {
	session.counter = 1
}

def user = request.getParameter('user')
def password = request.getParameter('password')

if(!session.login && user && password){
	//TODO login
	session.login = true
}

html.html('lang':"de"){
	head {
		meta(charset:"ISO-8859-1")
		meta('http-equiv':"X-UA-Compatible", IE:"edge")
		meta(name:"viewport", content:"width=device-width, initial-scale=1")
		title("MBS Dozentenspass :)")
		link(rel:"stylesheet", href:"assets/js/jquery-ui/css/no-theme/jquery-ui-1.10.3.custom.min.css")
		link(rel:"stylesheet", href:"assets/css/font-icons/entypo/css/entypo.css")
		link(rel:"stylesheet", href:"http://fonts.googleapis.com/css?family=Noto+Sans:400,700,400italic")
		link(rel:"stylesheet", href:"assets/css/bootstrap.css")
		link(rel:"stylesheet", href:"assets/css/neon-core.css")
		link(rel:"stylesheet", href:"assets/css/neon-theme.css")
		link(rel:"stylesheet", href:"assets/css/neon-forms.css")
		link(rel:"stylesheet", href:"assets/css/custom.css")
		script(src:"assets/js/jquery-1.11.0.min.js")
		
	}
	body ('class':"page-body") {
		// body
		i(style:"display:none;")
		if(!session.login){
			include('/WEB-INF/includes/login.groovy')
		}else{
			include('/WEB-INF/includes/main.groovy')
		}
	}
	
	script(src:"assets/js/gsap/main-gsap.js")
	script(src:"assets/js/jquery-ui/js/jquery-ui-1.10.3.minimal.min.js")
	script(src:"assets/js/bootstrap.js")
	script(src:"assets/js/joinable.js")
	script(src:"assets/js/resizeable.js")
	script(src:"assets/js/neon-api.js")
	script(src:"assets/js/neon-chat.js")
	script(src:"assets/js/neon-custom.js")
	script(src:"assets/js/neon-demo.js")
}
session.counter = session.counter + 1