if (!session) {
	session = request.getSession(true)
}

if (!session.counter) {
	session.counter = 1
}

html.html('lang':"de"){
	head {
		meta(charset:"ISO-8859-1")
		meta('http-equiv':"X-UA-Compatible", IE:"edge")
		meta(name:"viewport", content:"width=device-width, initial-scale=1")
		title("kleiner Test")
		link(rel:"stylesheet", href:"asset/css/bootstrap.min.css")
		link(rel:"stylesheet", href:"asset/css/bootstrap-theme.min.css")
	}
	body {
		h1("Hello World")
		request.getRequestDispatcher("/WEB-INF/includes/login.groovy").include(request, response);
		p("Hello, ${request.remoteHost}: ${session.counter}! ${new Date()}")
	}
	script(src:"asset/js/jquery-2.1.1.min.js")
	script(src:"asset/js/bootstrap.min.js")
}
session.counter = session.counter + 1