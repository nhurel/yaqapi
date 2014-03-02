# Presentation
YAQ API is Yet Another Query API. It takes the typesafety from torpedo project and the 
fluent syntax from jooq to provide the best Hibernate Query DSL. YAQ API main advantages are :
* Easy to use API through its DSL
* No class generation needed, no specific annotation or any other tricky stuff
* Fluent query writing

# Installation
YAQ API is as easy to install as just including a jar in your classpath. 

## Maven support
Even if YAP API is not available yet on a maven central repository, you can easily reference it in 
your pom.xml with the system scope :
<pre	>
<dependency>
	<groupId>me.hurel</groupId>
	<artifactId>hql-builder</artifactId>
	<version>1.0.0</version>
	<scope>system</scope>
</dependency>
</pre>

#Usage
The first thing to do before writing your query is to create a proxy object on the entity you wan to query.
This is simply done with the queryOn static method: 
<pre>
User user = queryOn(new User());
</pre>

Then, simply write your query, starting with the select method and let you guide through the DSL :
<pre>
QueryBuilder query = select(user).from(user).where(user.getAge()).isGreaterThan(18).orderBy(user.getLastName());
</pre>

Finally, just build you query :
<pre>
List<User> adults =  (List<User>)query.build(sessionFactory.createSession()).list();
</pre> 

And that's it !

# Features
YAQ API currently supports :
* joins (inner and outer, specifying fetch)
* cross joins
* exists clause
* group by... having clause
* collections property (see the dollar function)
* count, min, max, sum, avg functions
* YAQ API declares parameters using the JPQL syntax and sets automatically the parameters

# $ function
The dollar function is a convenient way to query on properties of a collection property. 
See example below to understand :
<pre>
User user = queryOn(new User());
List<User> parents = select(user).from(user).innerJoin(user.getChildren()).where($(user.getChildren()).getAge()).isLessThan(10).build(sessionFactory.createSession()).list();
</pre> 
