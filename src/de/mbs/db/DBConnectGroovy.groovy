package de.mbs.db
import groovy.sql.Sql
class DBConnectGroovy 
{
	
	static final def username = "myUserName";
	static final def password = "myPassword";
	
	/**
	 * @return this method returns a groovy Sql variable... 
	 * use it like this:  DBConnectGroovy.getMysqlInstance().eachRow("select * from FOOD where type=${foo}") 
	 * 										{
     *											println "Gromit likes ${it.name}"
	 *										}
	 */
	
	Sql getMySqlInstance()
	{
		def sql = Sql.newInstance("jdbc:mysql://localhost:3306/mydb", username,password, "com.mysql.jdbc.Driver")
		return sql; 	
	}
	
	
}
