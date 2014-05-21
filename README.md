# Introduction
YAQ API is Yet Another Query API. It takes the typesafety from torpedo query project and the 
fluent syntax from jooq to provide the best Hibernate Query DSL. YAQ API main advantages are :
* Typesafety
* Easy to use API through its DSL
* No class generation needed, no specific annotation nor any other tricky stuff
* Fluent query writing
* **No more StringBuilders** :stuck_out_tongue:

# Setup
YAQ API is as easy to install as just including a jar in your classpath. 

## Maven support
Even if YAQ API is not yet available on a maven central repository, you can easily reference it in 
your pom.xml with the system scope :
```xml
<dependency>
	<groupId>me.hurel.hqlbuilder</groupId>
	<artifactId>yaqapi</artifactId>
	<version>1.0.0</version>
	<scope>system</scope>
	<systemPath>${project.basedir}/src/main/webapp/WEB-INF/lib/yaqapi-1.0.0.jar</systemPath>
</dependency>
```

#Usage

##Static import
The most convenient way to use YAQ API is to make a static import of the Yaqapi class :
```java
import static me.hurel.hqlbuilder.builder.Yaqapi.*;
```

##Write your query
The first thing to do before writing your query is to create a proxy object on the entity class you want to query.
This is simply done with the *queryOn* static method: 
```java
User user = queryOn(User.class);
```

Then, simply write your query, starting with the select method and let you drive by the DSL :
```java
QueryBuilder query = select(user).from(user)
						.where(user.getAge()).isGreaterThan(18)
						.orderBy(user.getLastName());
```
**Tip** : Use `selectFrom(user)` method as a shorthand for `select(user).from(user)`

Finally, just build you query :
```java
List<User> adults =  (List<User>)query.build(sessionFactory.createSession()).list();
```

And that's it ! 

 You can put it all together :
```java
User user = queryOn(User.class);
List<User> adults = selectFrom(user)
						.where(user.getAge()).isGreaterThan(18)
						.orderBy(user.getLastName())
						.build(sessionFactory.createSession()).list();
```


# Features
YAQ API currently supports :
* aliases in select clause
* joins (inner and outer, specifying fetch)
* cross joins
* exists clause
* group by... having clause
* case when... then... else... end syntax
* collections property (see the dollar function)
* count, min, max, sum, avg functions
* YAQ API declares parameters using the JPQL syntax and sets automatically the parameters

# $ function
The dollar function is a convenient way to query on properties of a collection property. 
See example below to understand :
```java
User user = queryOn(User.class);
List<User> parents = select(user).from(user)
							.innerJoin(user.getChildren())
							.where($(user.getChildren()).getAge()).isLessThan(10)
							.build(sessionFactory.createSession()).list();
```

The dollar function can be used as many time as needed to access a property
```java
select(user).from(user)
	.innerJoin(user.getChildren())
	.innerJoin($(user.getChildren()).getAdress())
	.where($($(user.getChildren()).getAdress()).getCity()).isLike("F%")
	.build(sessionFactory.createSession()).list();
```

# Tips and advanced usages
## Cross join
To query on two distinct entities, it is necessary to build two proxies. 
Since the `queryOn` function can be called only once, you have to create the second proxy with the `andQueryOn` method.
```java
User user = queryOn(User.class);
Country country = andQueryOn(Country.class);
List<User> usersWithACountryName = select(user).from(user).andFrom(country)
									.where(user.getLastName()).isEqualTo(country.getName())
									.build(sessionFactory.createSession()).list();
```

## Declare variables
Accessing deep properties can make your query hard to read. With Yaqapi, you can declare a variable for whatever entity.
```java
User user = queryOn(User.class);
Adress adress = user.getAdress();
City city = adress.getCity();
List<User> users = selectFrom(user).innerJoinFetch(user.getAdress())
									.innerJoinFetch(adress.getCity())
									.where(city.getName()).isLike("R%")
									.build(sessionFactory.createSession()).list();
```
**Warning** : You can declare variables for entities object. Final properties must be accessed via their getter in the DSL (like `city.getName()` in the previous example)


## Use all Hibernate features
Yaqapi is **just** a query builder. This means that once you have called the build() method, you get a pure Hibernate Query object.
So you can call setFirstResult(), setMaxResults(),... functions :
```java
Query hibernateQuery = select(user.getLastName()).as("name").and(count("*")).as("members")
						.from(user)
						.groupBy(user.getLastName())
						.build(sessionFactory.createSession());
hibernateQuery.setResultTransformer(new AliasToBeanResultTransformer(WhateverClass.class));
return hibernateQuery.list();
```


#License
YAQ API is distributed under Mozilla Public License v2.0 meaning you can use this library either in open source and proprietary project.

If you improve this project by adding functionnality or fixing a bug, you must share all your improvements.
