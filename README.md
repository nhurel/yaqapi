# Presentation
YAQ API is Yet Another Query API. It takes the typesafety from torpedo project and the 
fluent syntax from jooq to provide the best Hibernate Query DSL. YAQ API main advantages are :
* Easy to use API through its DSL
* No class generation needed, no specific annotation nor any other tricky stuff
* Fluent query writing
* **No more StringBuilders** :stuck_out_tongue:

# Installation
YAQ API is as easy to install as just including a jar in your classpath. 

## Maven support
Even if YAP API is not yet available on a maven central repository, you can easily reference it in 
your pom.xml with the system scope :
```xml
<dependency>
	<groupId>me.hurel</groupId>
	<artifactId>hql-builder</artifactId>
	<version>1.0.0</version>
	<scope>system</scope>
	<systemPath>${project.basedir}/src/main/webapp/WEB-INF/lib/hql-builder.jar</systemPath>
</dependency>
```

#Usage

##Static import
The most convenient way to use YAQ API is to make a static import of the Yaqapi class :
```java
import static me.hurel.hqlbuilder.builder.Yaqapi.*;
```

##Write your query
The first thing to do before writing your query is to create a proxy object on the entity you want to query.
This is simply done with the *queryOn* static method: 
```java
User user = queryOn(new User());
```

Then, simply write your query, starting with the select method and let you drive by the DSL :
```java
QueryBuilder query = select(user).from(user).where(user.getAge()).isGreaterThan(18).orderBy(user.getLastName());
```

Finally, just build you query :
```java
List<User> adults =  (List<User>)query.build(sessionFactory.createSession()).list();
```

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
```java
User user = queryOn(new User());
List<User> parents = select(user).from(user).innerJoin(user.getChildren()).where($(user.getChildren()).getAge()).isLessThan(10).build(sessionFactory.createSession()).list();
```
