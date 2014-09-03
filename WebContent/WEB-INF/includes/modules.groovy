import java.util.Set;

import de.mbs.modules.interfaces.Modul;
import net.xeoh.plugins.base.PluginManager;
import net.xeoh.plugins.base.impl.PluginManagerFactory;
import net.xeoh.plugins.base.options.GetPluginOption;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

// JAR Dateien auslesen, Plugin bereitstellen
def readJar(file){
	PluginManager pm = PluginManagerFactory.createPluginManager();
	pm.addPluginsFrom(file.toURI());
	Modul mod = pm.getPlugin(Modul.class, new GetPluginOption[0]);
	println "<br>"+mod.getMenuName()
}

html.div {
	h2("Module")
	br()
	def path = request.getServletContext().getRealPath("/")+"WEB-INF/lib/modules"
	def modulesDir = new File(path)
	println modulesDir.getPath()+" "+modulesDir.exists()
	for (File fileEntry : modulesDir.listFiles()) {
		if(!fileEntry.isDirectory() && fileEntry.getName().endsWith("jar")){
			readJar(fileEntry)
		}
	}
}